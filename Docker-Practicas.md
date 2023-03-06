
# SECCIÓN DE PRACTICAS Y EJERCICIOS

## Crear imagen

Se crea una imagen con el archivo *Dockerfile*: .../3-practica2-billingApp: 
Imagen en base a la imagen nginx:alpine. // Alpine es una distro de linux muy liviana.

### Imagen nginx:alpine

- Instalado:
  - Ngingx
- Por instalar: (esto se hace en DockerFile)
  - openJDK8

### Buenas practicas

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
  - `ENTRYPOINT`: Indica en que orden se ejecuta el codigo

### Crear archivo nginx.conf

### Crear archivo appshell.sh

Realiza tareas de verificación y sube los servicios

### CREAR IMAGEN

comando: `docker build -t billingappmio:prod --no-cache --build-arg JAR_FILE=target/\*.jar .`

### Solución de problemas ERROR DEL COMANDO zsh

Para zhs por problemas con las rutas `/` es necesario usar con el caracter de espape a`/\` en la ruta del JAR cuando se ingresa por consola así: `JAR_FILE=target/\*.jar .`

### VERIFICAR IMAGEN

Deberoa estar listada: `docker image ls`
Puede ser inicializado el contenedor: `docker run -p 80:80 -p  8080:8080 --name billingappmio billingappmio:prod`

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

# 4 PRACTICA ORQUESTACION (DOCKER COMPOSE)

**Ruta detrabajo:** *".../4-practica3-billingApp2-docker_compose"*
Aplicación: BillingApp2
Servicios:
- BD: postgres
  - crea una bd por defecto
- Backend: java
  - se agrega un grupo y usuario nuevo
- Frontend: angular - nginx
- Red virtual: Todas las imagenes estan dentro de una red virtual

## Notas

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

## Verificar y probar funcionamiento de los servicios

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

Activar el cluster de swarm: `docker swarm init`

# 5 Practica Docker Network - virtual environments

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

## Notas

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
`docker stop $(docker ps -a -q) ; docker system prune ; docker rmi -f $(docker images -aq) ; docker volume prune ; docker network prune`

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

```text
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


# 6 Practica Docker Swarm

**Ruta detrabajo:**: *".../6-practica3-docker-swarm"*

Recomendación de uso para:
- Ambientes Profesional y Productivo: Kubernetes
- Ambientes de Pruebas o Pequeños productivo: Docker Compose
- Ambientes Pequeños: Docker Swarm

Se va a usar el mismo archivo *.yml* para realizar la practica con Docker Swarm, el cual tiene muchas similitudes.

### pre-requisitos
Basado en la practica 4. Se puede usar docker swarm como orquestador en vez de docker compose, con esto se orquesta y se crea un cluster.

1. **Antes de iniciar dejar creadas las imagenes *build* de la practica 4.**
2. **Antes de iniciar dejar ejecutar los contenedores *up* de la practica 4.**
3. **Antes de iniciar detener y eliminar todos practica 4.**

**Imagenes**
```
REPOSITORY                             TAG       IMAGE ID       CREATED          SIZE
billingapp_v3_swarm-billingapp-front   latest    1de8f613a988   15 minutes ago   47.7MB
billingapp_v3_swarm-billingapp-back    latest    7a7efa671ea6   15 minutes ago   153MB
postgres                               latest    f462f91720c0   3 days ago       379MB
adminer                                latest    471e5d2fb746   3 days ago       250MB
```

### Eliminar lo necesario, incluido las redes
`docker stop $(docker ps -a -q) && docker system prune`


### Modificar *stack-billing.yml* para docker swarm

Dentro de *deploy* agregar n replicas con *replicas: n* para el **Front**:


```
#Billin app frontend service
  billingapp-front:
    build:
      context: ./angular
    image: billingapp_v2_billingapp-front 
    deploy: 
        replicas: 3
        resources:
```
Es necesario agregar *imagen* y solocar que imagen se va a usar para el servicio de Front y Back, docker swarm no supone (docker compose cuando se hacia buiuld lo suponia) y se debe colocar explicitamente. Los nombres de las imagenes se pueden sacar con `docker image ls` Ademas se debe quitar la etiqueta *container_name* al ser incompatible con docker swarm, los nombres se crean automaticamente.

```
....
#database engine service
  postgres_db:
    image: postgres:latest
    restart: always
....
#database admin service
  adminer:
    image: adminer
...
#Billin app backend service
  billingapp-back:
    build:
      context: ./java
      args:
        - JAR_FILE=*.jar
    image: billingapp_v2_billingapp-back:latest
```
No son necesarios los rangos de puertos en *ports* ya que la asignación de puertos y balanceo la hace docker swarm, sin embargo esta configuración es valida.
```
#rango de puertos para escalar    
    ports:
      #- 80-85:80 
      - 80:80 
```

### Activar docker swarm
Ver información con `docker info`

Activar cluster: `docker swarm init`

Luego de activar docker swarm ver *Node Address* dirección IP del nodo en el cluster:
```
  Node Address: 192.168.0.13
  Manager Addresses:
   192.168.0.13:2377
```

### Desplegar con docker swarm

`docker stack deploy -c stack-billing.yml billing`


Ver contenedores de todas las orquestaciones: `docker service ls` la palabra *billing* es el alias que se usa para identificar la orquestación. **TODAS LAS REPLICAS DEBEN ESTAR CON n/n, n diferente de 0**
```
ID             NAME                       MODE         REPLICAS   IMAGE                                         PORTS
si35hfr4iqjf   billing_adminer            replicated   1/1        adminer:latest                                *:9090->8080/tcp
nohrtzw5onsa   billing_billingapp-back    replicated   1/1        billingapp_v3_swarm-billingapp-back:latest    *:8080->8080/tcp
42rpv6bu7qdq   billing_billingapp-front   replicated   3/3        billingapp_v3_swarm-billingapp-front:latest   *:80-85->80/tcp
arlm405q80o4   billing_postgres_db        replicated   1/1        postgres:latest                               *:5432->5432/tcp
```
Ver contenedores de solo una orquestación: `docker stack ps billing` 

```
ID             NAME                            IMAGE                                         NODE           DESIRED STATE   CURRENT STATE                     ERROR                       PORTS
pff84abhg2py   billing_adminer.1               adminer:latest                                192.168.0.13   Running         Running 23 minutes ago                                        
xqocpw62t2zd   billing_billingapp-back.1       billingapp_v3_swarm-billingapp-back:latest    192.168.0.13   Running         Starting less than a second ago                               
zpaelmfnws21    \_ billing_billingapp-back.1   billingapp_v3_swarm-billingapp-back:latest    192.168.0.13   Shutdown        Failed 5 seconds ago              "task: non-zero exit (1)"   
wzr4680hy1yn    \_ billing_billingapp-back.1   billingapp_v3_swarm-billingapp-back:latest    192.168.0.13   Shutdown        Failed 17 seconds ago             "task: non-zero exit (1)"   
29uihstbfovf    \_ billing_billingapp-back.1   billingapp_v3_swarm-billingapp-back:latest    192.168.0.13   Shutdown        Failed 28 seconds ago             "task: non-zero exit (1)"   
ovnhpp7pwxtx    \_ billing_billingapp-back.1   billingapp_v3_swarm-billingapp-back:latest    192.168.0.13   Shutdown        Failed 39 seconds ago             "task: non-zero exit (1)"   
x9hwhlisgsrh   billing_billingapp-front.1      billingapp_v3_swarm-billingapp-front:latest   192.168.0.13   Running         Running 23 minutes ago                                        
wp6bsyomlclq   billing_billingapp-front.2      billingapp_v3_swarm-billingapp-front:latest   192.168.0.13   Running         Running 23 minutes ago                                        
qce5n4tdu1rn   billing_billingapp-front.3      billingapp_v3_swarm-billingapp-front:latest   192.168.0.13   Running         Running 23 minutes ago                                        
xm7mo1zeqtik   billing_postgres_db.1           postgres:latest                               192.168.0.13   Running         Running 23 minutes ago              
```

### Verificación
Ver rapidamente la IP del contenedor: `docker info | grep -A 2 "Node Address"`

```
  Node Address: 192.168.0.13
  Manager Addresses:
   192.168.0.13:2377
```

App web: *192.168.0.13:80*
adminer: *192.168.0.13:9090*

### Eliminar cluster y desactivar docker swarm
`docker stack rm billing ; docker swarm leave --force` Con `docker info | grep -A 1 "Swarm"  ` se puede verificar que haya sido desactivado.


### Eliminar todo, incluido las redes, docker swarm
RESUMEN COMANDOS:
`docker stack rm billing ; docker swarm leave --force ; docker stop $(docker ps -a -q) ; docker system prune ; docker rmi -f $(docker images -aq) ; docker volume prune ; docker volume rm $(docker volume ls) ;docker network prune ; sudo rm -r /var/lib/postgres_*`
