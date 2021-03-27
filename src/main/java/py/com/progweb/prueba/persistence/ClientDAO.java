package py.com.progweb.prueba.persistence;

import org.infinispan.notifications.cachelistener.annotation.TransactionCompleted;
import py.com.progweb.prueba.model.Client;
import py.com.progweb.prueba.model.PointsSac;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class ClientDAO {
    @PersistenceContext(unitName = "pparcial")
    private EntityManager entityManager;

    public Long createClient(Client client){
        entityManager.persist(client);
        return client.getClientId();
    }

    public Client getClient(Long id){
        return entityManager.find(Client.class, id);
    }

    public List<Client> getClients(){
        Query q = this.entityManager.createQuery("select c from Client c");
        List<Client> result = (List<Client>) q.getResultList();
        return result;
    }

    public void updateClient(Client client){
        if(entityManager.find(Client.class, client.getClientId()) != null){
            entityManager.merge(client);
        }
    }

    public void deleteClient(Client client){
        entityManager.remove(client);
    }
}
