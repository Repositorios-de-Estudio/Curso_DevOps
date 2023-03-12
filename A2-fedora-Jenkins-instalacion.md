# Uso de Jenkins en una imagen Docker

Se va a usar la imagen de Jenkins en Docker y se va a extender con Maven para su funcionamiento con una aplicación Java.

### Tambien se puede instalar directamente sobre el SO

1. DockerFile en: *11-jenkins-en-docker/Dockerfile*
2. Buscar en DockerHub la imagen de Jenkins [jenkins/jenkins](https://hub.docker.com/r/jenkins/jenkins)
   1. Nombre Imagen: jenkins/jenkins
3. Paquete de instalación de Maven
   1. Ruta: 'https://downloads.apache.org/maven/maven-3/3.9.0/binaries/'
   2. Ruta del binario: 'https://downloads.apache.org/maven/maven-3/3.9.0/binaries/apache-maven-3.9.0-bin.tar.gz'
   3. Version: 3.9.0
   4. Hacer la instalación con comandos apt ya que la imagen en derivada de ubuntu
   5. Hacer configuración de maven en el sistema
4. Contruir imagen y nombrarla jenkins-cicd: `docker build -t jenkins-cicd --no-cache .`
5. Verficar imagen *jenkins-cicd*: `docker image ls`
6. levantar contenedor: `docker run -d -p 8080:8080 -p 50000:50000 --name jenkinsCont jenkins-cicd`
   1. puertos en documentación de jenkins docker: *Usage*
   2. nombre del contenedor: *jenkinsCont*
   3. nombre de la imagen: *jenkins-cicd*
7. Verificar contenedor *jenkinsCont*: `docker ps -a`
8. Entrar a jenkins
   1. Saber contraseña del portal Jenkins en los logs de instalación: `docker logs id-de-imagen`
   2. Buscar mensaje: *enkins initial setup is required. An admin user has been created and a password generated...*
   3. Copiar contraseña: en este caso es *5c557d5481294776aa408ddc72b2b7bf*
   4. Entrar a web jenkins: 'http://localhost:8080/' y usar contraseña
   5. Usar *Install sugged plugins* esperar algunos minutos
   6. En *Create First Admin User* crear el usuario
      1. en este caso usar admin en todo y email: *admin@admin.com*
      2. el email es a donde llegan las notificaciones CI/CD
   7. listo para crear con **Pipelines**





# REFERENCIAS

1. [Documentación Jenkins - extend the image](https://github.com/jenkinsci/docker/blob/master/README.md)