FROM openjdk:17-jdk-slim-bullseye
RUN addgroup -system devopsc && useradd -G devopsc javams 
USER javams:devopsc
ENV JAVA_OPTS=""
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
 # use a volume is mor efficient and speed that filesystem
VOLUME /tmp
EXPOSE 7080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]

