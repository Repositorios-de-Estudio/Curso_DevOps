## NOTA: Reemplazar nombreobjeto por el nombre o id de imagen o contenedor segun coresponda en el comando

Ejemplo: 

- Iniciar Docker: `sudo systemctl start docker`
- Descargar la imagen billingapp: `docker pull sotobotero/udemy-devops:0.0.1`
- Crear el contenedor billingapp: `docker run -p 80:80 -p  8080:8080 --name billingapp sotobotero/udemy-devops:0.0.1`

Ver

Interfaz de Aplicacion: `http://localhost:80`

Interfza del Microservicio: (swagger): `http://localhost:8080/swagger-ui/index.html`
    - u: admin
    - P: admin

Interfaz de Adminer: `http://localhost:9090/`


## Comandos utiles

- General
- Docker Compose
- Imagenes
- Volumenes

  - Iniciar Docker: `sudo systemctl start docker`
  - Descargar la imagen: `docker pull sotobotero/udemy-devops:0.0.1`
  - Levantar el contendor para probar: `docker run -d -p 80:80 -p  8080:8080 --name billingapp billingapp:prod`
  - Listar las imagenes locales: `docker image ls`
  - Listar los contenedores existentes: `docker ps -a`
  - Iniciar un contenedor detenido (en background): `docker start NAME`
  - Detener un contenedor: `docker stop NAME`
  - Ver estado del servicio docker: `service docker status`
  - Ver estado del servicio docker: `systemctl status docker`
  - Ver los logs de un contenedor: `docker logs NAME`
  - Eliminar un contenedor: `docker rm NAME` // se puede usar el ID
  - Eliminar una imagen: `docker image rm REPOSITORY` // se puede usar el IMAGE ID
  - Descargar las imagenes usando docker-compose: `docker-compose -f stackdb.yml pull`
  - Inicar los contenedores: `docker-compose -f stackdb.yml up -d`
  - Costruir la imagen: `docker build -t <nombre_imagen>:prod --no-cache --build-arg JAR_FILE=target/\*.jar .`
  - Asignar un nuevo tag: `docker tag billingapp:prod sotobotero/udemy-devops:0.0.2`
  - Loguearse en el docker engine hacia docker hub: `docker login`
    - (usar tu usario y contraseña)
  - Hacer un push de la imagen Docker: `docker push sotobotero/udemy-devops:0.0.2`
  - Lanzar Docker Desktop: `systemctl --user start docker-desktop`
  - Detener Docker Desktop: `systemctl --user stop docker-desktop`
  - Agregar TAG imagen: `docker tag nombre_imagen:prod repositorio:tag_nuevo`
  - Subir imagen DockerHub: `docker push repositorio:tag`
  - Eliminar todos los contenedores detenidos: `docker system prune`
  - Eliminar todas las imagenes: `docker rmi -f $(docker images -aq)` 
  - Listar los volumenes: `docker volume ls `
  - Eliminar todos los volumenes sin uso: `docker volume prune`
  - Eliminar todos los volumenes: `docker volume rm $(docker volume ls)`
  - Eliminar todos los directorios de volumenes `*_data`: `sudo rm -r /var/lib/postgres_*`
`
  - Construir las imagenes definidas en la orquestación: `docker-compose -f stack-billing.yml build`
  - Inicializar los contenedores de los servicios de la orquestación: `docker-compose -f stack-billing.yml up -d`
  - Detener todos los servicios de la orquestación: `docker-compose -f stack-billing.yml stop`
  - Detener todos los contenedores: `docker stop $(docker ps -a -q)`
  - Listar las redes virtuales: `docker network ls`
  - Eliminar las redes virtuales: `docker network prune`
  - reconstruir las imagenes: `docker-compose -f stack-billing.yml build --no-cache`
  - reconstruir los contenedores de la orquestación: `docker-compose -f stack-billing.yml up -d --force-recreate`
  - Escalar un servicio al iniciar la orquestación: `docker-compose -f stack-billing.yml up --scale billingapp-front=3 -d --force-recreate`
  - Revsiar consumo de recursos: `docker stats`
  - Obtener informacion de docker: `docker info`
  - Activar el cluster de swarm: `docker swarm init`
  - Desplegar el stack en docker swarm: `docker stack deploy -c archivo.yml nombre_alias`
    - el alias sería el nombre del cluster
  - Listar los servicio se docker swarm: `docker service ls`
  - Listar detalle de una orquestacion docker swarm`docker stack ps billing`
  - Eliminar la orquestacion o stack de docker swarm: `docker stack rm billing`
  - Desactivar docker swarm: `docker swarm leave --force`


## Recursos
- Monitorizar recursos: `docker stats`
- Limitar recursos: Modificar el archivo **.yml** etiqueta `deploy:`
- Recrear para aplicar limites de recursos: `docker-compose -f stack-billing.yml up -d --force-recreate`