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
5. 





# REFERENCIAS

1. [Documentación Jenkins - extend the image](https://github.com/jenkinsci/docker/blob/master/README.md)