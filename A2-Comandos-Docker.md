## NOTA: Reemplazar nombreobjeto por el nombre o id de imagen o contenedor segun coresponda en el comando

Ejemplo: 

- Iniciar Docker: `sudo systemctl start docker`
- Descargar la imagen billingapp: `docker pull sotobotero/udemy-devops:0.0.1`
- Crear el contenedor billingapp: `docker run -p 80:80 -p  8080:8080 --name billingapp sotobotero/udemy-devops:0.0.1`

Ver

Interfaz de Aplicacion: `http://localhost:80`

Microservicio: `http://localhost:8080/swagger-ui/index.html`
    - u: admin
    - P: admin

Interfaz de Adminer: `http://localhost:9090/`


## Comandos utiles

- Docker 
  - Iniciar Docker: `sudo systemctl start docker`
  - Levantar el contendor para probar: `docker run -p 80:80 -p  8080:8080 --name billingapp billingapp:prod`
  - Ver estado del servicio docker: `sudo service docker status`
  - Listar los contenedores existentes: `docker ps -a`
  - Detener un contenedor: `docker stop nombreobjeto`
  - Ver los logs de un contenedor: `docker logs nombreobjeto`
  - Iniciar un contenedor detenido: `docker start nombreobjeto `
  - Listar las imagenes locales: `docker image ls`
  - Eliminar una imagen: `docker image rm nombreobjeto`
  - Descargar las imagenes usando docker-compose: `docker-compose -f stackdb.yml pull`
  - Inicar los contenedores: `docker-compose -f stackdb.yml up -d`
  - Costruir la imagen: `docker build -t billingapp:prod --no-cache --build-arg JAR_FILE=target/* jar .`
  - Asignar un nuevo tag: `docker tag billingapp:prod sotobotero/udemy-devops:0.0.2`
  - Loguearse en el docker engine hacia docker hub: `docker login`
    - (usar tu usario y contraseña)
  - Hacer un push de la imagen Docker: `docker push sotobotero/udemy-devops:0.0.2`
  - Lanzar Docker Desktop: `systemctl --user start docker-desktop`
  - Detener Docker Desktop: `systemctl --user stop docker-desktop`
