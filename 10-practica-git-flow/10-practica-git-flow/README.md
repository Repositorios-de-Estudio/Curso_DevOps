# EJEMPLO README DE LA CLASE

# prueba tecnica microservicios spring boot

> Atendiendo el reto Code Challenge 
> Se desarrolla un microservicio en spring boot usuando buenas practicas de diseño y codificaion, 
> como los principios SOLID el enfoque ATDD y diferentes frameworks y librerias para optimizar el trabajo. 
> Asi como la implementación de comunicación asincrona

## Para usar este repositorio
Este proyecto contiene tanto el codigo fuente como un jar listo apra su ejecucion, el codigo fuente 
es una aplicacion spring boot gestionada con maven, por lo cual podra clonarse como un proyecto o ejecutar la aplicacion usando el jar.
La palicacion es autocontenida, cuando se inicia crea una base de datos H2 en memoria.

La aplicacion cuenta con una interface grafica para ver la documentacion de la API y probar los diferentes endpoints.

Se ha desarrollado un set de preubas para validar las diferentes reglas de negocio, 
todas han sido probadas con exito, si alguna prueba falla la aplicacion no compila.

Todas las dependencias estan descritas en el fichero de configuracion de maven pom.xml

El puerto por defecto es el 8084 para evitar posibles conflictos

### Requisitos previos
java 8 o superior
navegador web
opcional 
git 
IDE de desarrollo

### Probar la aplicación mediante la interface web
1- Descargar como zip o bien clonar el repositorio usando git o cualqueir asistente grafico
   Una vez descargado puede optar por ejecutar el jar y levantar la aplicacion o  recompilar el proyecto si lo habre en su IDE favorito
   
2- Descomprimir el paque descargado.

3- Para ejecutar el jar use el siguiente comando sustituyendo $HOME por la ubicacion donde ha descargado el proyecto java -jar  $HOME/BANK_TECHNICAL_TEST/build/bank_transactions-0.0.1-SNAPSHOT.jar

4- Verificar que ha inicado correctamente, si tinee problemas validar que el fichero .jar exista y rectificar la ruta 

5- Para ver la documentacion y probar los endpoints navegue a la siguiente en su navegdor web url http://localhost:8084/swagger-ui.html#/bank-transaction-controller
   debe sustituir localhost por la ip de la maquina donde esta ejecutando el jar.
   En la interface podra ver las diferentes operaciones y al hacer clic en cada una de ellas podra ver los detalles, como la documentacion
   datos de preuba  con los cuale spodra realizar peticiones para validar los endpoint.
6- Si ha desidido clonar el proyecto debera habrirlo en su IDE favorito.

7- Si desea realizar pruebas sonumiento la APi , podra ven en la interface grafica los detalles y datos de ejemplo de las peticiones.


### Prueba Unitarias
Se ha realizado un desarrollo basado en el enfoque ATDD, donde se han escrito todos los casos de prueba para validar las diferentes 
reglas de negocio y criterios de aceptacion.
Dichas pruebas se lanzan de manera automática.

Podrá encontar el detalle de los casos en /BANK_TECHNICAL_TEST/src/test/java/com/sotobotero/bank/BankBusinessRuleTests.java

## Más informacion

Echo por Carlos Adrian Soto Botero.
http://localhost:8080/amil@gmail.com

