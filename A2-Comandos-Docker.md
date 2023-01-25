## NOTA: Reemplazar nombreobjeto por el nombre o id de imagen o contenedor segun coresponda en el comando

Ejemplo: 

- Iniciar Docker: `sudo systemctl start docker`
- Descargar la imagen billingapp: `docker pull sotobotero/udemy-devops:0.0.1`
- Crear el contenedor billingapp: `docker run -p 80:80 -p  8080:8080 --name billingapp sotobotero/udemy-devops:0.0.1`

Ver

Interfaz de Aplicacion: `http://localhost:80`

Interfza del Microservicio: (swagger): `http://localhost:8080/swagger-ui/index.html`
    - u: admin
    - P: admin

Interfaz de Adminer: `http://localhost:9090/`


## Comandos utiles

- Docker 
  - Iniciar Docker: `sudo systemctl start docker`
  - Descargar la imagen: `docker pull sotobotero/udemy-devops:0.0.1`
  - Levantar el contendor para probar: `docker run -d -p 80:80 -p  8080:8080 --name billingapp billingapp:prod`
  - Listar las imagenes locales: `docker image ls`
  - Listar los contenedores existentes: `docker ps -a`
  - Iniciar un contenedor detenido (en background): `docker start NAME`
  - Detener un contenedor: `docker stop NAME`
  - Ver estado del servicio docker: `service docker status`
  - Ver estado del servicio docker: `systemctl status docker`
  - Ver los logs de un contenedor: `docker logs NAME`
  - Eliminar un contenedor: `docker rm NAME` // se puede usar el ID
  - Eliminar una imagen: `docker image rm REPOSITORY` // se puede usar el IMAGE ID
  - Descargar las imagenes usando docker-compose: `docker-compose -f stackdb.yml pull`
  - Inicar los contenedores: `docker-compose -f stackdb.yml up -d`
  - Costruir la imagen: `docker build -t <nombre_imagen>:prod --no-cache --build-arg JAR_FILE=target/\*.jar .`
  - Asignar un nuevo tag: `docker tag billingapp:prod sotobotero/udemy-devops:0.0.2`
  - Loguearse en el docker engine hacia docker hub: `docker login`
    - (usar tu usario y contraseña)
  - Hacer un push de la imagen Docker: `docker push sotobotero/udemy-devops:0.0.2`
  - Lanzar Docker Desktop: `systemctl --user start docker-desktop`
  - Detener Docker Desktop: `systemctl --user stop docker-desktop`
  - Agregar TAG imagen: `docker tag nombre_imagen:prod repositorio:tag_nuevo`
  - Subir imagen DockerHub: `docker push repositorio:tag`


## Docker Hub

1. Buscar imagen
2. En description la información de version de las imaganes
3. Bajar imagen:
  - Metodo 1: Con el comando: `docker pull postgres`
  - Metodo 2: Con el archivo yml: 
    - Uso por via psql stack.yml para ejecutar con docker-compose
    - Copiar contenido stack.yml
    - ver ".../2-practica1-postgres/docker-compose.yml" // se renombr el archivo a docker-compose.yml
    - ejecutar:
      - Metodo 1: `docker-compose pull` //cuando el archivo se llama docker-compose.yml
      - Metodo 2: `docker-compose -f stackdb.yml pull` // archivo con nombre acualquiera.yml
4. Listar imaganes y verificar que esten: postgres y adminer `docker image ls`
5. Crear contenedores simultaneamente (ejecutar):
    - ejecutar: Como uno depende de otro, el otro se levanta cuando el primero inicia
      - Metodo 1: `docker-compose up -d` // para archivo docker-compose.yml, -d: backgroud
      - Metodo 2: `docker-compose -f stackdb.yml up -d` // para otro nombre acualquiera.yml
6. Listar contenedores y verificar: `docker ps -a`
7. Comprobar: `http://localhost:9090/`
  - Ingresar con credenciales al adminer:
    - Motor: Postgres, servidor: db, u: postgres, p: example, base de datos: postgres
8. Detener contenedores: `docker stop adminer` y `docker stop postgres`



## Crear imagen

Se crea una imagen con el archivo *Dockerfile*: .../3-practica2-billingApp: 
Imagen en base a la imagen nginx:alpine. // Alpine es una distro de linux muy liviana.

Imagen nginx:alpine
- Instalado:
  - Ngingx
- Por instalar: (esto se hace en DockerFile)
  - openJDK8

Buenas practicas:
- Crear una imagen por servicio y luego hacer la orquestacion

Crear archivo DockerFile

- Comandos para el archivo DockerFile:
  - `FROM`: Imagen base y variante
  - `RUN`: Para instalar:  
    - '\ &&' para continuar en la siguiente linea, termina instruccion con ;
  - `ENV`: Variables
  - `ARG`: Referencias 
    - Recibe el valor de ruutas o archivos como parametro en la construcción de la imagen, aca va vacio por eso
  - `ADD <source> <destination>`: Copiar archivos/directorios a la imagen
  - `VOLUME`: Volumenes 
    - Almacenamiento persistente que se comparte con el host
  - `COPY`: Copiar
  - `EXPOSE`: Definir puertos
  - `ENTRYPOINT`: Indica en que orden se ejcuta el codigo

Crear archivo nginx.conf
Crear archivo appshell.sh
 - Realiza tareas de verificación y sube los servicios

CREAR IMAGEN
  - comando: `docker build -t billingappmio:prod --no-cache --build-arg JAR_FILE=target/\*.jar .`

VERIFICAR IMAGEN:
  - Deberoa estar listada: `docker image ls`
  - Puede ser inicializado el contenedor: `docker run -p 80:80 -p  8080:8080 --name billingappmio billingappmio:prod`


CARGAR IMAGEN EN DOCKERHUB
- Crear repositorio en DockerHub
  - nombre: sergiopereze/3-practiva2-billingapp
- (Opcional) Agregar TAG a la imagen para diferenciar de otras versiones de la misma imagen:
  - con TAG 1.0.0: `docker tag billingappmio:prod sergiopereze/3-practiva2-billingapp:1.0.0`
  - Nombre imagen: billingappmio
  - Nombre Repositorio DockerHub: sergiopereze/3-practiva2-billingapp
- Login en DockerHub desde la terminal: `docker login`
- Subir imagen: `docker push sergiopereze/3-practiva2-billingapp:1.0.0`
  - el TAG usado es: 1.0.0





Referencia:
- [DockerFile reference](https://docs.docker.com/engine/reference/builder/)