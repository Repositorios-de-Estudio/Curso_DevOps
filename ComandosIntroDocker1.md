## NOTA: Reemplazar nombreobjeto por el nombre o id de imagen o contenedor segun coresponda en el comando, ejemplo con sotobotero

### 1- Descargar la imagen billingapp

docker pull sotobotero/udemy-devops:0.0.1

### Crear el contenedor billingapp

docker run -p 80:80 -p  8080:8080 --name billingapp sotobotero/udemy-devops:0.0.1

### Ver

Aplicacion: http://localhost:80

Microservicio: http://localhost:8080/swagger-ui/index.html
    - u: admin
    - P: admin



### Listar los contenedores existentes 

docker ps -a

Detener un contenedor

docker stop nombreobjeto 

Ver los logs de un contenedor

docker logs nombreobjeto 

Iniciar un contenedor detenido

docker start nombreobjeto 

Listar las imagenes locales

 docker image ls

Eliminar una imagen 

docker image rm nombreobjeto

Comandos usados en la clase de docker compose

Descargar las imagenes usndo docker-compose

docker-compose -f stackdb.yml pull

Inicar los contenedores

docker-compose -f stackdb.yml up -d

Url de la inteface de adminer

http://localhost:9090/

Los comandos nuevos utilizados son para crear una imagen:

Costruir la imagen

docker build -t billingapp:prod --no-cache --build-arg JAR_FILE=target/*.jar .

Levantar el contendor para probar

docker run -p 80:80 -p  8080:8080 --name billingapp billingapp:prod

Darle un nuevo tag

docker tag billingapp:prod sotobotero/udemy-devops:0.0.2

loguearse en el docker engine hacia docker hub

docker login (usas tu usario y contraseña)

hacer un push de la imagen

docker push sotobotero/udemy-devops:0.0.2