FROM nginx:alpine
#Install java 8
RUN apk -U add openjdk8 \
    && rm -rf /var/cache/apk/*;
RUN apk add ttf-dejavu

#Install java microservice
ENV JAVA_OPTS=""
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
#Install app on nginx serve
 # use a volume is mor efficient and speed that filesystem
VOLUME /tmp
RUN rm -rf /usr/share/nginx/html/*
COPY nginx.conf /etc/nginx/nginx.conf
COPY dist/billingApp /usr/share/nginx/html
COPY appshell.sh appshell.sh
#expose ports 8080 for java swagger app and 80 for nginx app
EXPOSE 80 8080
ENTRYPOINT ["sh", "/appshell.sh"]

