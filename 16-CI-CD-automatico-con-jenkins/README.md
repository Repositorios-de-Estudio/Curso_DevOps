# conectarse como root
docker exec -it -u root c3fba2e14ac7 /bin/bash

# realizar instalacion y configuracion
DOCKERVERSION=18.03.1-ce
mkdir tmp2 && cd tmp2
curl -fsSLO https://download.docker.com/linux/static/stable/x86_64/docker-${DOCKERVERSION}.tgz
tar xzvf docker-${DOCKERVERSION}.tgz
rm tar xzvf docker-${DOCKERVERSION}.tgz
cp docker/docker /usr/local/bin
cd .. && rm -r tmp2