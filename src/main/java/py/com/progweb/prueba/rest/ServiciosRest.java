package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.model.*;
import py.com.progweb.prueba.persistence.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

@Path("servicios")
@Consumes("application/json")
@Produces("application/json")
@Stateless
public class ServiciosRest {
    @Inject
    private ClientDAO clientDAO;

    @Inject
    private PointsSacDAO pointsSacDAO;

    @Inject
    private UseConceptDAO useConceptDAO;

    @Inject
    private PointsUseDAO pointsUseDAO;

    @Inject
    private UseDetailDAO useDetailDAO;

    @POST
    @Path("/cargar")
    public Response cargar(@QueryParam("clienteId") Long clienteId, @QueryParam("montoDeLaOperacion") Double amount){
        PointsSac pointsSac = new PointsSac();
        Client client = this.clientDAO.getClient(clienteId);
        if(client==null){
            return Response.status(404).build();
        }
        if(pointsSacDAO.calculatePoints(amount)<=0){
            return Response.ok("Monto de operacion muy pequeno para generar puntos").build();
        }
        pointsSac.setClient(this.clientDAO.getClient(clienteId));
        pointsSac.setAssignedPoints(pointsSacDAO.calculatePoints(amount));
        pointsSac.setPurchaseAmount(amount);
        Long id = pointsSacDAO.addPSac(pointsSac);
        return Response.ok(pointsSacDAO.getPointsSac(id)).build();
    }

    @POST
    @Path("/canjear")
    public Response canjear(@QueryParam("clienteId") Long clienteId, @QueryParam("conceptoId") Long conceptoId, @QueryParam("mail") String mail) {
        UseConcept concept = useConceptDAO.getUseConcept(conceptoId);
        Client client = clientDAO.getClient(clienteId);
        if(client==null || concept == null){
            return Response.status(404).build();
        }

        Long requiredPoints = concept.getRequiredPoints();
        List<PointsSac> pointsSacList = pointsSacDAO.listWhereClienteOrderByDate(clienteId);
        Long pointsCounter = 0L;
        for(PointsSac pointsSac : pointsSacList){
            pointsCounter =+ pointsSac.getBalance();
            if(pointsCounter >= requiredPoints){
                break;
            }
        }
        if(pointsCounter < requiredPoints){
            return Response
                    .notModified("Puntos Del Cliente "+pointsCounter+" \nPuntos Requeridos "+requiredPoints)
                    .build();
        }
        PointsUse pointsUse = new PointsUse();
        pointsUse.setUsedPoints(requiredPoints);
        pointsUse.setClient(clientDAO.getClient(clienteId));
        pointsUse.setUseConcept(useConceptDAO.getUseConcept(conceptoId));
        Long pointsUseId = pointsUseDAO.createPointsUse(pointsUse);
        for(PointsSac pointsSac : pointsSacList){
            Long avaiblePoints = pointsSac.getBalance();
            if(avaiblePoints<=0){
                continue;
            }
            if(avaiblePoints<requiredPoints){
                requiredPoints = requiredPoints - avaiblePoints;
                UseDetail detail = new UseDetail();
                detail.setPointsSac(pointsSac);
                detail.setUsedPoints(avaiblePoints);
                detail.setPointsUse(pointsUseDAO.getPointsUse(pointsUseId));
                useDetailDAO.createUseDetail(detail);
                pointsSac.setBalance(0L);
                pointsSac.setUsedPoints(pointsSac.getAssignedPoints());
                pointsSacDAO.updatePointsSac(pointsSac);
            }else{
                UseDetail detail = new UseDetail();
                detail.setPointsSac(pointsSac);
                detail.setUsedPoints(requiredPoints);
                detail.setPointsUse(pointsUseDAO.getPointsUse(pointsUseId));
                useDetailDAO.createUseDetail(detail);
                pointsSac.setBalance(avaiblePoints - requiredPoints);
                pointsSac.setUsedPoints(pointsSac.getUsedPoints() + requiredPoints);
                pointsSacDAO.updatePointsSac(pointsSac);
                break;
            }
        }
        if(mail!=null){
            try {
                this.sendMail(client.getEmail(), pointsUse.getUseConcept().getDescription(), requiredPoints);
            } catch (MessagingException e) {
                System.out.println("Email invalido "+client.getEmail());
                return Response.status(400).build();
            }
        }
        return Response.ok(pointsUseDAO.getPointsUse(pointsUseId)).build();
    }

    private void sendMail(String receipient, String concept, Long points) throws MessagingException {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        Session newSession = Session.getDefaultInstance(properties, null);

        String emailReceipient = receipient;
        String emailSubject = "Canjeo de Puntos";
        String emailBody = "Has usado "+ points + " puntos para canjear \n"+
                concept + ".";

        MimeMessage mimeMessage = new MimeMessage(newSession);
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipient));
        mimeMessage.setSubject(emailSubject);
        MimeMultipart multipart = new MimeMultipart();

        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(emailBody, "text/html; charset=UTF-8");
        multipart.addBodyPart(bodyPart);
        mimeMessage.setContent(multipart);;

        String emailHost = "smtp.gmail.com";
        Transport transport = newSession.getTransport("smtp");
        transport.connect("giul1297.g@gmail.com", "ccp191231");
        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
        transport.close();
    }

}
