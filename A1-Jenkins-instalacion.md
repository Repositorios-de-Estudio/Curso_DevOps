# Uso de Jenkins en una imagen Docker y uso de ngrok

Se va a usar la imagen de Jenkins en Docker, se va a extender con Maven para su funcionamiento con una aplicación Java y se va a extenedor con Docker Engine para construir imagenes. El Docker file fue actualizado para poder realizar esto. Otra alternativa es instalar manualmente Docker Engine como lo muestra *Problemas > Mensaje 1 > Alternativa > Procedimiento*.

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
   2. Buscar mensaje: *Jenkins initial setup is required. An admin user has been created and a password generated...*
   3. Copiar contraseña: en este caso es *5c557d5481294776aa408ddc72b2b7bf*
   4. Entrar a web jenkins: 'http://localhost:8080/' y usar contraseña
   5. Usar *Install sugged plugins* esperar algunos minutos
   6. En *Create First Admin User* crear el usuario
      1. en este caso usar admin en todo y email: *admin@admin.com*
      2. el email es a donde llegan las notificaciones CI/CD
      3. ahora la contraseña es *admin*
   7. listo para crear con **Pipelines**
9. Se debe tener un *token de acceso* para usar en las credenciales de git en jenkins

# Uso de ngrok y webooks

1. Crea una cuenta
2. Descargar el binario de: 'https://dashboard.ngrok.com/get-started/setup'
3. Descomprimir
4. Conectar la cuenta con el token de la pagina de gnrok
   1. `./ngrok config add-authtoken token-de-la-pagina-de-ngrok`
5. Configurar el servicio
   1. El servidor CI/CD esta por el *puerto 8080*
   2. `./ngrok http 8080`
6. Crear Webhook
   1. toma la url que dice *Forwarding*: 'https://bbc8-201-244-248-50.ngrok.io'
   2. Ir al repositorio de github > configuracion
   3. webhooks > add webhooks
   4. Payload url: pegar la url + /github-webhook/ 'https://bbc8-201-244-248-50.ngrok.io/github-webhook/'
   5. Content type: application/json
   6. Eventos: usar solamente *Just the push event*
   7. Active: enable
7. Verificar que este funcionando
   1. webhooks > selecionar > recent Deliveries
   2. en evento ping debe estar en verde
   3. Cuando se hace un push en la consola de ngrok sale *POST /github-webhook/          200 OK*
8. **IMPORANTE!** Terminar el proceso cuando se terminen las pruebas

***

# CONSTRUCCION AUTOMATICA DE IMAGENES DOCKER Y CARGAR EN DOCKER HUB

1. instalacion plugin
   1. jenkins > administrar > jenkins > plugins >> CloudBees Docker Build and Publish
   2. instalar sin reiniciar
   3. luego reiniciar manualmente
2. Configuracion de la pipeline
   1. jenkins > pipeline > configurar > build steps
      1. añadir nuevo paso > Docker Build and Publish
         1. Repository Name: reposiotrio dockerhub + / + Nombre de la imagen que se va a publicar
         2. tag: numero tag de la imagen que se va a publicar
         3. Docker Host URI: ubicación de instalación de docker (debe ser accesible desde jenkins)
            1. Si jenkins y docker estan instalados en la misma red no se debe hacer nada mas
            2. Si jenkins y docker estan en redes diferentes
               1. *[ESTA NO FUNCIONA]* se debe crear una red compartida **Ver creación de red puente Jenkins y Docker**
               2. *[ESTA FUNCIONA]* se debe instalar docker en el contenedjor de jenkins, ir a *Problemas > Mensaje 1 > Alternativa > Procedimiento*
            3. Agregar URL: 'tcp://172.17.0.1:2375'
         4. Server credential: ninguna, no tenemos docker con seguridad
         5. Docker registry url: aca va la url de dockerhub por defecto, si se usa otro repositorio de imagenes se debe colocar la url aca
         6. Registry credentials
            1. Crear Credenciales de dockerhub
               1. add > jenkis
               2. Kind: username with password
               3. U: usuario en dockerhub
               4. P: contraseña de dockerhub
               5. Marcar treat username as secret
               6. ID: docker-hub
            2. Registry credentials > docker-hub
         7. Advance
            1. Build Context: billing/
            2. Additional Build Arguments: --build-arg  JAR_FILE=target/*.jar
         8. Apply
         9. Save

## Ver creación de red puente Jenkins y Docker

En esta instalación, Jenkis esta dentro de un contenedor de Docker y Docker Engine esta localmente, así que se debe crear un puente entre la red local que y la del contenedor de Jenkins.

- Crear red puente entre la red local y la de un contenedor
  - crear puente: `ip route show default | awk '/default/ {print $3}'`
  - ver configuración IP (addr) y se saca el segmento de red (inet): `ip a` aca se busca la interfaz de interes -> '172.17.0.1'
  - armar direccion/url: **tcp://**+{ip inet}+**:2375** ej: 'tcp://172.17.0.1:2375'
- Exponer Docker
  - Editar para exponer recurso Docker en: `sudo gedit /lib/systemd/system/docker.service`
  - Exponer, en [service] reemplazar *--containerd=/run/containerd/containerd.sock* por *-H=tcp://0.0.0.0:2375*
  - Reulstado: *ExecStart=/usr/bin/dockerd -H fd:// -H=tcp://0.0.0.0:2375*
  - recargar docker: `sudo systemctl daemon-reload ; sudo service docker restart ; docker stop $(docker ps -a -q)`
  - probar, la dirección debe ser accesible: `curl http://localhost:2375/images/json` // ambien se puede probar desde el navegador

# ERRORES

## Mensaje 1

Error cuando se ejecuta la pipeline:

```text
ERROR: Cannot run program "docker" (in directory "/var/jenkins_home/workspace/pipeline_ejercicio_16"): error=2, No such file or directory
```

### Causa

No se puede conectar el contenedor al daemon de Docker de la maquina local, configuración realizada en *Ver creación de red puente Jenkins y Docker*.

### Solución

**No existe solución** facil de implementar, puede ser un bug de la version de docker actual = Docker version 23.0.1, build a5ee5b1.

### Alternativa

Instalar en el contenedor Docker. En el curso alguien escribio el procedimiento realizando nuevamente la creación de la imagen y usa la version = Docker version 18.03.1-ce, build 9ee9f40 / fedora 37.

### Procedimiento

```bash
# conectarse como root
docker exec -it -u root id-contenedor /bin/bash

# realizar instalacion y configuracion
DOCKERVERSION=18.03.1-ce
mkdir tmp2 && cd tmp2
curl -fsSLO https://download.docker.com/linux/static/stable/x86_64/docker-${DOCKERVERSION}.tgz
tar xzvf docker-${DOCKERVERSION}.tgz
rm tar xzvf docker-${DOCKERVERSION}.tgz
cp docker/docker /usr/local/bin
cd .. && rm -r tmp2

# verficar
docker --version
```

***

# RECOMENDACIONES

1. En Jenkins limpiar el espacio de trabajo para evitar errrores con git cuando se cambia de proyecto, otra alternativa es conectarse con docker exce y eliminar los espacios de trabajo manualmente

***

# REFERENCIAS

1. [Documentación Jenkins - extend the image](https://github.com/jenkinsci/docker/blob/master/README.md)
