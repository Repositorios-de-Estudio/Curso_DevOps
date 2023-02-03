# Curso_DevOpsKubernetes CI / CD

## Curso DevOps con Docker, Jenkins, Kubernetes, git, GitFlow CI y CD

### Curso de: 
https://www.udemy.com/course/devops-con-dockers-kubernetes-jenkins-y-gitflow-cicd/
https://www.youtube.com/channel/UCzX4ldiZpIwjqMJ9UMY2fMg

***

# SECCIÓN DE INTRODUCCIÓN Y DEFINICIONES

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


## Docker Network - virtual environments
Cuando se requiere tener varios entornos (ej: pruebas, producción) fisicamente en una misma maquina pero funcionando aislados uno de otro, se puede optar por tenerlos en segmentos de red diferentes. De esta manera estaran en redes separadas ási cada contenedor tendrá una IP diferente a las que se puede acceder independiente.

La solución no optima sería tener dos maquinas fisicas donde cada contenedor se ejecute de manera indpendiente, teniendo así una maquina para pruebas y otra para produccion.

***

# SECCIÓN DE PRACTICAS Y EJERCICIOS

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

### Solución de problemas ERROR DEL COMANDO zsh
Para zhs problema por rutas `/` es necesario usar con el caracter de espape a`/\` en la ruta del JAR cuando se ingresa por consola así: `JAR_FILE=target/\*.jar .` 

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


# PRACTICA ORQUESTACION (DOCKER COMPOSE)

**Ruta detrabajo:** *".../4-practica3-billingApp2-docker_compose"*
Aplicación: BillingApp2
Servicios:
- BD: postgres
  - crea una bd por defecto
- Backend: java
  - se agrega un grupo y usuario nuevo
- Frontend: angular - nginx
- Red virtual: Todas las imagenes estan dentro de una red virtual

Notas:
- Generalmente servicio/aplicacion tiene su propio Dockerfile
- los servicios de la BD no tienen Dockerfile debido a que se usa el del respositorio de Dockerhub, esto se espifica en el yml de la orquestacion.
- stack-billing.yml hace la orquestación
- la etiqueta **build** indica que imagen se va a construir
- la equiqueta **image** indica que imagen se descarga del dockerhub
- el archivo yml hace dos cosas:
  - construye las imagenes que se indiquen
  - genera los contenedores

Servicios/aplicaciones orquestadas:
- Java
- Angular
- Postgres
- Adminer


Luego se construllen las imagenes definidas en la orquestación:
- Eliminar volumenes que coincidan con los que se van a usar
- Se puede limpiar todo: eliminar contenedores, eliminar imagenes, eliminar volumenes
- Construir las imagenes: `docker-compose -f stack-billing.yml build`
  - es igual a como se construye una sola imagen
  - se verifican las imagenes creadas segun lo indicado en el .yml con `docker image ls`
    - billingapp_v2-billingapp-front
    - billingapp_v2-billingapp-back

Luego se inicializan los contenedores de los servicios de la orquestación:
- Inicializar contenedores: `docker-compose -f stack-billing.yml up`
  - recomendable no usar `-d` para poder ver los errores
- verificar contenedores: `docker ps -a`
  - billingapp_v2-billingapp-front **UP**
  - billingapp_v2-billingapp-back  **UP**
  - adminer **UP**
  - postgres:latest **UP**

## Solucion de errores "Error executing DDL"
Me di cuenta que en la bd: postgres_db-billingapp_db no hay tablas.
Me salio el siguiente error: **Error executing DDL "create sequence hibernate_sequence start 1 increment 1** y **ERROR: permission denied for schema public**

Es debido a que no se tiene permisos en la bd y no pudo ejecutar: .../db_files/init-user-db.sh
Solución:
- detener contenedores: `docker stop $(docker ps -a -q)`
- eliminar contenedores: `docker system prune`
- eliminar todas las imagenes: `docker rmi -f $(docker images -aq)`
- eliminar volumenes: `docker volume prune`

- RESUMEN COMANDOS: `docker stop $(docker ps -a -q) ; docker system prune ; docker rmi -f $(docker images -aq) ; docker volume prune`

- agregar permisos al schema en **init-user-db.sh**: `GRANT ALL ON SCHEMA public TO billingapp;`
- dar permisos de ejecución: `chmod 777 init-user-db.sh`
- volver a inicializar los contenedores `docker-compose -f stack-billing.yml up`


## Verificar y probar funcionamiento de los servicios:
- Aplicacion web: http://localhost:80
- adminer: http://localhost:9090
  - info en: stack-billing.yml
  - servidor: postgres_db
  - U: postgres, P: qwerty
  - BD: postgres
- Ruta de los archivos persistentes: 
  - */var/lib/postgres_data*

## Detener Orquestacion
`docker-compose -f stack-billing.yml stop`


# Practica Docker Network - virtual environments

**Ruta detrabajo:**: *"..../5-practica-virtual-environment/billingApp_v3"*

Las aplicaciones son para producción y preproducción las cuales estan separadas cada una por una red virtual. Se usa solo una imagen para cada servicio, se replica la configuración cambiando los nombres y la red para crear contenedores diferentes, esta configuración esta en el *.yml*.

Aplicación: billingApp_v3
Aplicaciones:
  - Front: Angular-Nginx 
    - producción, preproducción
  - Back: Java
    - producción, preproducción
    - imagen a construir
  - DB: Postgres
    - producción, preproducción
    - imagen de DockeHub
Red virtual: 
  - env_prod: 172.16.232.0/24
  - env_prep: 172.16.235.0/24

Notas:
- Cada ambiente tiene sus datos persistentes separados
- La configuración de las redes estan en el *.yml* sección `networks`
- Con un mismo adminer se puede acceder a las dos BD en la configuración se especifica a que redes tienes acceso: `adminer: networks`
- Generalmente servicio/aplicacion tiene su propio Dockerfile
- los servicios de la BD no tienen Dockerfile debido a que se usa el del respositorio de Dockerhub, esto se espifica en el yml de la orquestacion.
- stack-billing.yml hace la orquestación
- la etiqueta **build** indica que imagen se va a construir
- la equiqueta **image** indica que imagen se descarga del dockerhub
- el archivo yml hace dos cosas:
  - construye las imagenes que se indiquen
  - genera los contenedores

Servicios/aplicaciones orquestadas:
- Java x2
- Angular x2
- Postgres x2
- Adminer x1

Almacenamiento:
- prod: var/lib/postgres_data_prod
- pre: /var/lib/postgres_data_prep


## Solución de errores conocidos
Aplicar configuración de **Solucion de errores "Error executing DDL"**
Editar y agregar permisos: **init-user-db.sh**

### Eliminar todo, incluido las redes
RESUMEN COMANDOS:
`docker stop $(docker ps -a -q) && docker system prune && docker rmi -f $(docker images -aq) && docker volume prune && docker network prune`

### Construir las imagenes de la Orquestación
`docker-compose -f stack-billing.yml build --no-cache`
usar: `--no-cache`

### Comprobar: 
`docker image ls`

```
REPOSITORY                         
billingapp_v3-billingapp-front_prod
billingapp_v3-billingapp-front-prep
billingapp_v3-billingapp-back-prep 
billingapp_v3-billingapp-back-prod 
```

### Inicializar Contenedoresde de la Orquestación:
`docker-compose -f stack-billing.yml up --force-recreate`

Usar: `--force-recreate`

### Verificar y probar funcionamiento de los servicios:

```
CONTAINER ID   IMAGE                                
4057b64b7222   billingapp_v3-billingapp-front_prod  
00cb830a9b74   billingapp_v3-billingapp-front-prep  
207dea23fb74   billingapp_v3-billingapp-back-prod   
dcebeb00285f   adminer                              
ceb918ae7422   billingapp_v3-billingapp-back-prep   
ad6b4fa25e85   postgres:latest                      
b643d16c0659   postgres:latest                      
```

- PRODUCCION
  - Aplicacion web (8081-8082:): http://localhost:8082
- PRE-PRODUCCION
 - Aplicacion web (7081-7082): http://localhost:7082
- adminer: http://localhost:9090
  - info en: stack-billing.yml
  - servidor (se puede especificar el puerto de cada una): 
    - postgres_db_prod
    - postgres_db_prep
  - http://localhost:7082
  - U: postgres, P: qwerty
  - BD: billingapp_db

***

# Referencias: 
1. Lectura recomendada clase 3 [Tutorial de DevOps: introducción](https://azure.microsoft.com/es-es/solutions/devops/tutorial/)
2. Lectura recomendada clase 14 [volumes y mapeo de puertos en docker](https://www.youtube.com/watch?v=GwnDA-oXShI&ab_channel=Digitalthinkingwithsotobotero)
3. [Documentación Docker](https://docs.docker.com/engine/)
4. DockerHub [Repositorio de imaganes](https://hub.docker.com/)
5. [Matriz de compatibilidad](https://docs.docker.com/compose/compose-file/compose-versioning/#versioning)
6. [DockerFile reference](https://docs.docker.com/engine/reference/builder/)
