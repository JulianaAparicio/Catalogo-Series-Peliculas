# Catalogo de series y peliculas

Proyecto desarrollado en el marco de la materia Backend I - Microservicios (Certified Tech Developer - Especialización en Backend)

Se desarrollaron 3 microservicios: Catalogo, Peliculas y Series.

## Se utilizaron algunos de los siguientes patrones de diseño:

  	1) Service registry (eureka-server)
  
	2) Service discovery
  
	3) Central configuration (config-server): aloja todas las configuraciones generales de la aplicación. De esta forma cualquier cambio que se realice se hace directamente acá.
  
	4) Log aggregation: concentra todos los logs de los microservicios.
  
	5) Distributed tracing (Zipkin): unifica todos los logs de la aplicación.
  
	6) Circuit Breaker (ResilienceJ4): en caso de producirse una caída de alguno de los microservicios de película o serie, se consume la base de datos de api-catalog para cubrir momentaneamente la caída y mostrarle al usuario la pelicula o serie que se esté consultando.
  
 	7) Edge Server (Spring Cloud Gateway): se accede a través del gateway a toda la aplicación.
  
  
## Otras tecnologías utilizadas:

- MongoDB Embedded (api-serie y api-catalog): base de datos del microservicio de series y base de datos de respaldo del microservicio de catalogo.

- MySQL (api-peliculas): base de datos del microservicio de películas.

- RabbitMQ (para manejo de cola de mensajes): se envía un mensaje por cada serie o película que se crea, dicho mensaje es consumido por api-catalog a fin de guardar en su base de datos la información.

- Docker: para la ejecución completa de la aplicación

