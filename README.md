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
- Cluster: Agrupación de un conjunto de hosts de Docker gestionados centraliazadamente, donde hay un host maestro (nodo maestro o **manager**) y otros esclavos (nodos esclavos o **worker**)
- Servicio: (service en Docker) designa una estructura abstracta con la que se pueden definir aquellas tareas que deben ejecutarse en el clúster. Cada servicio está formado por un conjunto de tareas individuales que se procesan en contenedores independientes en uno de los nodos del clúster.  - Cuando se crea un servicio, el usuario determina la imagen de contenedor en la que se basa y los comandos que se ejecutan en el contenedor, operándose sobre la base de la imagen.
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

## Docker
Tecnologia que permite empaquetar aplicaciones con todas sus dependencias para hacerlas postables y conseguir ejecución independiente del SO.

### Sobre los archivos DockerFile
**FROM**: Indica la imagen base la cual será la base para crear una nueva imagen. ej: imagen base y variante
**RUN**: Se usa para ejecutar instrucciones propias del SO del contenedor cuando se esta contruyendo la imagen. ej: Instalación de cosas en el SO
**CMD**: Sirve para especificar el comando por defecto que se ejecuta al iniciar el contenedor si no se especifica ningun servicio como argumento.
**ENTRYPOINT**: Sirve para especificar el comando por defecto que se ejecuta al iniciar el contenedor, si no se especifica ningun servicio como argumento, la diferencia con CMD radica en la manera como de como trata los argumentos docker. (Indica en que orden se ejecuta el codigo)

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

## Docker Swarm

Similar y reemplaza a Docker Compose, se basa en una arquitectura maestro-esclavo. Cada clúster de Docker está formado un manager y tantos workers como sea necesario. Mientras que el nodo maestro es responsable de la gestión del clúster y la delegación de tareas, el esclavo se encarga de ejecutar las unidades de trabajo (tasks).

**Se usa un cluster cuando con varias maquinas (cluster) se quiere tener toleracia a fallos y alta disponibilidad.**

Docker Swarm soporta dos modos de definir servicios swarm: servicios globales o replicado:

1- Servicios replicados: Desde un nodo master se crear n replicas (nodos workers) que permite que en caso de que algun nodo no este disponible otro nodo responda. 
2- Servicios globales: si un servicio se ejecuta en modo global, cada nodo disponible en el clúster inicia una tarea para el servicio correspondiente. Si al clúster se le añade un nodo nuevo, el nodo maestro le atribuye una tarea para el servicio global de forma inmediata. Este tipo de servicios se recomiendan para las aplicaciones de monitoreo o los programas antivirus.

## Docker Network - virtual environments
Cuando se requiere tener varios entornos (ej: pruebas, producción) fisicamente en una misma maquina pero funcionando aislados uno de otro, se puede optar por tenerlos en segmentos de red diferentes. De esta manera estaran en redes separadas ási cada contenedor tendrá una IP diferente a las que se puede acceder independiente.

La solución no optima sería tener dos maquinas fisicas donde cada contenedor se ejecute de manera indpendiente, teniendo así una maquina para pruebas y otra para produccion.

Ej campo de aplicació de Docker Swarm: Reparto de cargas, pues con el modo enjambre Docker dispone de funciones integradas de balanceo de carga. Si se ejecuta, por ejemplo, un servidor web NGINX con cuatro instancias, Docker distribuye las consultas entrantes de forma inteligente entre las instancias del servidor web disponibles.


***

# Referencias: 
1. Lectura recomendada clase 3 [Tutorial de DevOps: introducción](https://azure.microsoft.com/es-es/solutions/devops/tutorial/)
2. Lectura recomendada clase 14 [volumes y mapeo de puertos en docker](https://www.youtube.com/watch?v=GwnDA-oXShI&ab_channel=Digitalthinkingwithsotobotero)
3. [Documentación Docker](https://docs.docker.com/engine/)
4. DockerHub [Repositorio de imaganes](https://hub.docker.com/)
5. [Matriz de compatibilidad](https://docs.docker.com/compose/compose-file/compose-versioning/#versioning)
6. [DockerFile reference](https://docs.docker.com/engine/reference/builder/)
7. [Docker swarm oficial](https://docs.docker.com/engine/swarm/)
8. [Docker swarm explicacion](https://www.icm.es/2020/10/24/docker-swarm/)



