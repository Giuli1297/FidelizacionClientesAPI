# REST API Sistema de Fidelizacion de Clientes (Primer parcial)

Este es un programa que busca implementar una asignacion de puntos por compra, que serviran para que clientes obtengan premios a canjear

Esta aplicacion utiliza maven y java ee con librerias como JPA - Hibernate, EJB, JAX-RS


## Install

    mvn clean install

## Run the app

    standalone.bat - windows
    standalone.sh - linux

# REST API

Las REST API es descrita abajo.

## Obtener Clientes

### Request

`GET /prueba/cliente`

### Response

    HTTP/1.1 200 OK
    Content-Type: application/json
    []

## Obtener Usos de Puntos de acuerdo a parametros

### Request

`GET /prueba/consulta/listarUsoDePuntos`
`QueryParam` conceptoDeUso (id)
`QueryParam` fechaDeUso (dd-mm-yyyy)
`QueryParam` cliente (id)


### Response

    HTTP/1.1 200 OK
    Content-Type: application/json
    []
    
 
## Obtener Bolsas de Puntos de un cliente en un rango.

### Request

`GET /prueba/consulta/listarBolsaDePuntos`
`QueryParam` cliente (id)
`QueryParam` puntosRangoInf (numeroEntero)
`QueryParam` puntosRangoSup (numeroEntero)


### Response

    HTTP/1.1 200 OK
    Content-Type: application/json
    []
    
    
## Obtener Clientes con puntos a vencer en X dias.

### Request

`GET /prueba/consulta/listarClientesConPuntosAVencer`
`QueryParam` xDias (numeroEntero)

### Response

    HTTP/1.1 200 OK
    Content-Type: application/json
    []

## Obtener Clientes de acuerdo a parametros.

### Request

`GET /prueba/consulta/listarClientesPorNombreApellidoNacimiento`
`QueryParam` nombre (string)
`QueryParam` apellido (string)
`QueryParam` nacimiento (dd-mm-yyyy)


### Response

    HTTP/1.1 200 OK
    Content-Type: application/json
    []

## Obtener Puntos otorgados por cierto monto de compra.

### Request

`GET /prueba/consulta/calcularPuntosPorMonto`
`QueryParam` monto (numero)

### Response

    HTTP/1.1 200 OK
    Content-Type: application/json
    Long numero

## Cargar puntos a un cliente.

### Request

`POST /prueba/servicios/cargar`
`QueryParam` clienteId (id)
`QueryParam` montoDeLaOperacion (numero)


### Response

    HTTP/1.1 200 OK
    Content-Type: application/json
    Bolsa De Puntos
    
## Cargar puntos a un cliente.

### Request

`POST /prueba/servicios/canjear`
`QueryParam` clienteId (id)
`QueryParam` conceptoId (id)


### Response

    HTTP/1.1 200 OK
    Content-Type: application/json
    Uso De Puntos
