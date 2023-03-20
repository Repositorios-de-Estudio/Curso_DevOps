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
               1. Se deben aplicar los dos procedimientos siguientes para que funcione
               2. *[ESTA NO FUNCIONA]* se debe crear una red compartida **Ver creación de red puente Jenkins y Docker**
               3. *[ESTA FUNCIONA]* se debe instalar docker en el contenedjor de jenkins, ir a *Problemas > Mensaje 1 > Alternativa > Procedimiento*
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

Instalar en el contenedor Docker y realizar *Ver creación de red puente Jenkins y Docker* para que funcione. En el curso alguien escribio el procedimiento realizando nuevamente la creación de la imagen y usa la version = Docker version 18.03.1-ce, build 9ee9f40 / fedora 37.

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

# CONFIGURACIÓN Kubectl EN JENKINS

Instalación y configuración de plunging de Kubernetes en Jenkins para despliegues automatizados. \

Es necesario tener instalado *kubectl* en el contenedor de Jenkis antes de instalar el Plugin en Jenkins. \

La Pipeline se configura automaticamente con el archivo *Jenkinsfile*. \

Esta configuración es usada para la practica *18 PRACTICA - Despligue automatizado con Jenkins y Kuberneste* en *Z-2-Practicas-Kubernetes.md*

## 1 Instalación Kubectl en contenedor Jenkins

1. conectarse como root: `docker exec -it --user=root jenkinsCont /bin/bash`
2. Instalación

```bash
mkdir tmp2 && cd tmp2
curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
chmod +x ./kubectl
mv ./kubectl /usr/local/bin/kubectl
cd .. && rm -r tmp2
```

3. Verificar instalación: `kubectl version --client`

## 2 Conectar a la red

Conectar contenedor de Jenkins a la misma red donde esta minikube.

1. Verificar redes existentes: `docker network ls`
   1. La de interes es la red de minicube: *minikube*
2. Conectar a la red: `docker network connect minikube jenkinsCont`
   1. Verificar: `docker container inspect jenkinsCont`
   2. debe estar la red minikube
3. AL FINALIZAR SE RECOMIENDA desconectar de la red porque puede provocar que el contenedor no inicie si no esta disponible la red
   1. desconectar de la red: `docker network disconnect minikube jenkinsCont`

## 3 Instalación plugin

1. Jenkins > administrar jenkins > admnistrar plugins > buscar >> Kubernetes
   1. Install whitout restar
   2. Luego reiniciar manualmente `docker restart jenkinsCont`

## 4 Configuración kubernetes

### Generar datos de cluster de kubernetes

Generar el token y la url del cluster de kubernetes asociados al servicio definido en jenkins-account.yaml*.

```bash
# aplicar configuracion
kubectl apply -f jenkins-account.yaml

# aplicar configuración para token
kubectl apply -f jenkins-token.yml

# ver ubicacion del certificado
kubectl config view | grep -i certificate-authority

# ver url del cluster
kubectl config view | grep -i server

# consultar token
kubectl describe secret/jenkins-token-rk2mg
```

Datos obtenidos:

1. certificate-authority: '/home/user/.minikube/ca.crt'
2. server: 'https://192.168.49.2:8443'
3. Token con nombre *jenkins-token-rk2mg*

## 5 Configuración de Jenkins

Agregar token cluster kubernetes a Jenkins y configurarlo.

1. Jenkins > Administrar > Security > Manage credentials > Stores from parent >> System
2. En *Global credentials (unrestricted)* > Add credentials
   1. Kind: Secret text
   2. Secret: aca va el token de kubernetes
   3. ID: kubernete-jenkis-server-account (este nombre es el que usa el pipeline creado en Jenkinsfile.yml)
3. Jenkins > Administrar > Manage nodes and clouds
   1. Configure Clouds > añadir nueva nuve >> kubernetes
      1. name: kubernetes-cloud
      2. Kubernetes cloud details
         1. Kubernetes URL: 'https://192.168.49.2:8443' (es la url del cluster de kubernetes)
         2. Kubernetes server certificate key: es el contenido de '/home/user/.minikube/ca.crt' (es la ubicacion del certificado)
            1. Copiar contenido y usar: `gedit /home/user/.minikube/ca.crt`
            2. Copiar inlucyendo *-----BEGIN CERTIFICATE-----* y *-----END CERTIFICATE-----*
      3. Credentials: kubernete-jenkis-server-account
      4. Test connection: debe salir *Connected to Kubernetes v1.26.1*

### 6 Crear Pipeline Nueva

1. Jenkins > Dashboard > Nueva tarea
   1. Nombre: deploy-kubernetes
   2. Tipo: Pipeline (ya no es estilo libre)
2. Configuraciones de la Pipeline:
   1. GitHub project: 'https://github.com/Repositorios-de-Estudio/18-despliegue-kubernetes-jenkins'
      1. url del repositorio, sin el .git
   2. Pipeline > Pipeline script from SCM
      1. SCM: Git
      2. Repository
         1. URL: 'https://github.com/Repositorios-de-Estudio/18-despliegue-kubernetes-jenkins.git'
            1. url de descarga del repositorio, con el .git
      3. Credentials: github-token-jenkins
      4. Branches to build: */main
      5. Script Path: Jenkinsfile
         1. es es el nombre del archivo que crea el pipeline y debe estar subido en el repositorio
3. Apply
4. Save

### 7 Ejecutar

1. Ejecutar pipeline

#### 8 Comprobar

1. Consoleoutput debe tener *Finished: SUCCESS*
2. En la pipeline de tuvo que haber crado la sección: *Stage View*
3. En el dashboard de kubernetes debe hjaber un nuevo deploymen
4. Se debe poder acceder a la aplicación en el anvegador
   1. Para el ejercicio se usa swagger: 'http://192.168.49.2:31780/swagger-ui/index.html'

## PROBLEMAS

### Mensaje 1

Al ejecutar `kubectl --namespace default get serviceaccount` no se vio un secret creado (SECRETS default 0). En los comentarios de la clase alguien indico la creación de *jenkins-token.yml* y posterior ejecución.

### Solución

Ejecutar lo que se indica en *4 Configuración kubernetes* > *Generar datos de cluster de kubernetes*.

***

# RECOMENDACIONES

1. En Jenkins limpiar el espacio de trabajo para evitar errrores con git cuando se cambia de proyecto, otra alternativa es conectarse con docker exce y eliminar los espacios de trabajo manualmente

***

# REFERENCIAS

1. [Documentación Jenkins - extend the image](https://github.com/jenkinsci/docker/blob/master/README.md)
