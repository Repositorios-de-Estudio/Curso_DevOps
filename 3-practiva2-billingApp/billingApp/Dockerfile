
FROM nginx:alpine


RUN apk -U add openjdk8 \
    && rm -rf /var/cache/apk/*;
RUN apk add ttf-dejavu


ENV JAVA_OPTS=""
ARG JAR_FILE

# parametro dado en el comando docker build y reemplaza su nombre por app.jar
ADD ${JAR_FILE} app.jar 

# uso de /tmp del host como almacenamiento
VOLUME /tpm

# elimina el contenido del directorio de configuracion de nginx
RUN rm -rf /us/share/nginx/html/*

# TODOS LOS COPY HACEN REFERENCIA A ARCHIVOS DENTRO DE LA CARPETA ACTUAL DE ESTE ARCHIVO
# copia configuracion de ngingx del host a la imagen
COPY nginx.conf /etc/nginx/nginx.conf

# copia la aplicacion al repositorio de instalacion de nginx 
COPY dist/billingApp /usr/share/nginx/html

# copia script
COPY appshell.sh appshell.sh

# expone el puerto 80 para el servidor nginx y el 8080 para el microservicio
EXPOSE 80 8080

# ejecuta todo el docigo excepto el entrypoint al momento de compilacion,
# ejecuta el appshell.sh cuando se levanta el contenedor
# appshell hace tareas de inicilizacion de java, ngix y se queda verificando que los dos servicios esten
# running
ENTRYPOINT ["sh", "/appshell.sh"]