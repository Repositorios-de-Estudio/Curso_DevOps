FROM jenkins/jenkins
USER root
# VARIABLES, VARIABLE DE VERSION
ENV MAVEN_VERSION 3.9.0
ENV DOCKERVERSION=18.03.1-ce

# INSTALA WGET
RUN apt-get update && apt-get install -y wget
# DESCARGA (-P) EL BINARIO TAR.GZ EN /tmp/
# ruta directa
RUN wget https://downloads.apache.org/maven/maven-3/3.9.0/binaries/apache-maven-3.9.0-bin.tar.gz --no-verbose -P /tmp/
# ruta con variable
RUN wget --no-verbose https://downloads.apache.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz -P /tmp/
# z: archivo gzip, f:nombre archivo, x: extrae, descomprime dentro de /opt/
RUN tar zfx /tmp/apache-maven-$MAVEN_VERSION-bin.tar.gz -C /opt/ 
# configuracion
RUN ln -s  /opt/apache-maven-$MAVEN_VERSION /opt/maven 
RUN ln -s /opt/maven/bin/mvn /usr/local/bin 

# limpiar residuos
RUN rm /tmp/apache-maven-$MAVEN_VERSION-bin.tar.gz 


# instalacion necesaria de docker para poder construir imagenes
# Update https://stackoverflow.com/questions/38675925/is-it-possible-to-install-only-the-docker-cli-and-not-the-daemon
RUN curl -fsSLO https://download.docker.com/linux/static/stable/x86_64/docker-${DOCKERVERSION}.tgz \ 
	&& tar xzvf docker-${DOCKERVERSION}.tgz --strip 1 \ -C /usr/local/bin docker/docker \ 
	&& rm docker-${DOCKERVERSION}.tgz

# configuracion
# cambia dueño de /opt/maven a usuario:grupo para dar permisos a usuario jenkins
RUN chown jenkins:jenkins /opt/maven;
# variabla de entorno para ser uasada por la imagen docker
ENV MAVEN_HOME=/opt/mvn
USER jenkins
