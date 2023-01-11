# Curso_DevOpsKubernetes-CI--CD
# Curso DevOps con Docker, Jenkins, Kubernetes, git, GitFlow CI y CD

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
- Imagen: Paquete ligero y ejecutable de software con todo lo necesario para la aplicacion
  - La Imagen una ves desplegados pasarian a ser Contenedores
- Docker Engine: Motot de ejecucion de contenedores
- Docker Hub: Repositorio por defecto para las imaganes de docker
- Podman: Alternativa Opensource a Docker
- Docker Compose: Orquestador ligero de contenedores
- Docker Swarm: Orquestador de contenedores que permite manejar un cluster
- Kubernetes: Sistema para la administracion de clusters y Orquestador Empresarial de contenedores








Referencias: 
1. Lectura recomendada clase 3 [Tutorial de DevOps: introducción](https://azure.microsoft.com/es-es/solutions/devops/tutorial/)
2. [Documentación Docker](https://docs.docker.com/engine/)
3. 