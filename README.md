# Curso_DevOpsKubernetes CI / CD

## Curso DevOps con Docker, Jenkins, Kubernetes, git, GitFlow CI y CD

### Curso de: 
https://www.udemy.com/course/devops-con-dockers-kubernetes-jenkins-y-gitflow-cicd/
https://www.youtube.com/channel/UCzX4ldiZpIwjqMJ9UMY2fMg

# Introduccion

## Funcionalidad Tradicional:
### Equipo de Desarrollo
- Creacion de un producto
- Aislamiento de equipos
- Friccion entre los equipos
- Uso de correo (no comunicacion agil)
- Baja automatizacion
### Equipo de Infraestructura (operaciones):
- Velar por el funcionamiento 7/24
- Script rudimentarios
- Calendarios de releases fijos
- Puesta en produccion lenta

## ¿Que es DevOPS?
- Es la union de personas, procesos y tecnologia, con el fin de proporcionar valor continuamente a los clientes.
- DevOps: Development + Operation
- Rol de un integrante de un equipo Devops: Site Reliability Engieneer (SRE)

### Ciclo de vida DevOps - (es un ciclo continuo entre Dev y Ops)
1. (Dev) Plan de proyecto
2. (Dev) Codificacion
3. (Dev) Construccion del entregable
4. (Dev) Pruebas
5. (Ops) Creacion de Release
6. (Ops) Despliegue
7. (Ops) Probando/ejecucanto
8. (Ops) Monitorizacion

### Caracterisiticas
- Equilibrio entre Mayor calidad, Menor tiempo y Menor costo
- Mejores objetivos comerciales
- Mayor satiscaccion del cliente
- Agilidad en las entregas y alto rendimiento
- Mejores productos
- Armonioa entre los equipos
- Menos promblemas en produccion
- Facilidad en el diagnostico y solucion de incidentes

## Aspectos fundamentales de DevOps
- Control de versiones: git, svn
- CI Integracion continua: Automatizacion de compilaciones y pruebas tras commit >> Pipelines Jenkins
- CD Entrega continua: Suministro de software rapido y confiable en cualquier momento
- Infraestrutura como codigo: Infraestrutura en forma de codigo >> Terraform
  - Se utiliza con el objetivo de establecer y aprovisionar una infraestructura completa, haciendo uso de un lenguaje declarativo sencillo de aprender, que permite almacenar una configuración de la infraestructura en formato de codigo
- Supervision y Registro: Monitorizacion, recopilacion de metricas y vinculacion de datos de performance >> Prometheus, Granafa
- Aprendizaje validado: Analisis de datos para mejorar los procesos en cada ciclo


# Definiciones basicas
- Contenedor: Unidad de software que empaqueta el codigo y todas las dependencias de una aplicacion
  - Dockerfile: Archivo de configuración donde se define la imagen, sus componentes y que se debe ejecutar para funcionar
- Imagen: Paquete ligero y ejecutable de software con todo lo necesario para la aplicacion
  - La Imagen una ves desplegados pasarian a ser Contenedores
- Docker Engine: (o solo Docker) Motor de ejecucion de contenedores
- Docker Hub: Repositorio por defecto para las imaganes de docker
- Podman: Alternativa Opensource a Docker
- Docker Compose: Sistema que permite orquestar/gestionar ligero de contenedores
  - Yamel file: Archivo de configuración  donde se define la version de Docker Engine con la que es compatible (ver matriz de compatibilidad)
- Docker Desktop: Aplicacion de escritorio que integra Docker Engine y Docker Compose
- Docker Swarm: Orquestador de contenedores que permite manejar un cluster
- Orquestacion: Mediante un archivo yml se automatiza de la mayoría de las operaciones necesarias para ejecutar un conjunto de contenedores que trabajan en conjunto. Esto reduce la complejidad en sistemas de gran escala
- Kubernetes: Sistema para la administracion de clusters y Orquestador Empresarial de contenedores
- Volumenes: Son el almacenamiento externo a los contenedores
  - compuesto por la ruta local : el punto de montaje dentro del contenedor: `/var/lib/postgres_data:/var/lib/postgresql/data`
  - Cuando ya existe el archivo en el host del volumen, este no se vuelve a crear y puede generar errorres en los contenedores por lo que es mejor borrarlos antes
- Mapeo de Puertos:
  - Los contenedores esta compuestos por sockets, la cual recibe la peticion desde internet
    - La peticion rl host la recibe mediante un puerto el cual redirige la peticion el servicio que debe atender
    - Si el puerto corresponde a un contenedor, el host mapea el puerto al puerto del contener (que conoce el docker engine) y redirige la peticion al servicio del contenedor
  - Socket del host: socket = IP+Port
  - Se recibe una peticion por el puerto 8082, el host mapea al puerto 8080 del contendor: https://minute.com:8082 -> contenedor:8080
- DockerHub: Es un repositorio de imagenes de acceso publico: https://hub.docker.com/
- Adminer (anteriormente phpMinAdmin) Administrador grafico de bases de datos: MySQL, SQLite, Oracle, PostgreSQL de manera efectiva.



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

### Imagen nginx:alpine
- Instalado:
  - Ngingx
- Por instalar: (esto se hace en DockerFile)
  - openJDK8

### Buenas practicas:
- Crear una imagen por servicio y luego hacer la orquestacion

### Crear archivo DockerFile

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

### Crear archivo nginx.conf
### Crear archivo appshell.sh
 - Realiza tareas de verificación y sube los servicios

### CREAR IMAGEN
  - comando: `docker build -t billingappmio:prod --no-cache --build-arg JAR_FILE=target/\*.jar .`

### VERIFICAR IMAGEN:
  - Deberoa estar listada: `docker image ls`
  - Puede ser inicializado el contenedor: `docker run -p 80:80 -p  8080:8080 --name billingappmio billingappmio:prod`


### CARGAR IMAGEN EN DOCKERHUB
- Crear repositorio en DockerHub
  - nombre: sergiopereze/3-practiva2-billingapp
- (Opcional) Agregar TAG a la imagen para diferenciar de otras versiones de la misma imagen:
  - con TAG 1.0.0: `docker tag billingappmio:prod sergiopereze/3-practiva2-billingapp:1.0.0`
  - Nombre imagen: billingappmio
  - Nombre Repositorio DockerHub: sergiopereze/3-practiva2-billingapp
- Login en DockerHub desde la terminal: `docker login`
- Subir imagen: `docker push sergiopereze/3-practiva2-billingapp:1.0.0`
  - el TAG usado es: 1.0.0


### PRACTICA ORQUESTACION (DOCKER COMPOSE)

Ruta: ".../4-practica3-billingApp2-docker_compose"
Aplicación: BillingApp2
Servicios:
- BD: postgres
  - crea una bd por defecto
- Backend: java
  - se agrega un grupo y usuario nuevo
- Frontend: angular - nginx

Notas:
- Generalmente servicio/aplicacion tiene su Dockerfile
- los servicios de la BD no tienen Dockerfile debido a que se usa el del respositorio de Dockerhub, esto se espifica en el yml de la orquestacion.
- jstack-billing.yml hace la orquestación

Servicios/aplicaciones orquestadas:
- Java
- Angular
- Postgres
- Adminer


Luego se construye las imagenes e inicializan los contenedores, se debe tener en cuenta:
- Eliminar volumenes que coincidan con los que se van a usar
- Se puede limpiar todo: eliminar contenedores, eliminar imagenes, eliminar volumenes




Referencias: 
1. Lectura recomendada clase 3 [Tutorial de DevOps: introducción](https://azure.microsoft.com/es-es/solutions/devops/tutorial/)
2. Lectura recomendada clase 14 [volumes y mapeo de puertos en docker](https://www.youtube.com/watch?v=GwnDA-oXShI&ab_channel=Digitalthinkingwithsotobotero)
3. [Documentación Docker](https://docs.docker.com/engine/)
4. DockerHub [Repositorio de imaganes](https://hub.docker.com/)
5. [Matriz de compatibilidad](https://docs.docker.com/compose/compose-file/compose-versioning/#versioning)
6. [DockerFile reference](https://docs.docker.com/engine/reference/builder/)