# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
FROM nginx:alpine
 # use a volume is mor efficient and speed that filesystem
VOLUME /tmp
RUN rm -rf /usr/share/nginx/html/*
ARG DIST=/dist
ARG CONFIG_FILE=nginx.conf
COPY ${CONFIG_FILE} /etc/nginx/nginx.conf
COPY ${DIST} /usr/share/nginx/html
#Docker doesn't expose any ports from the container by default; you need to expose this ports 80 and 443 for nginx app
EXPOSE 80 443
CMD ["nginx", "-g", "daemon off;"]