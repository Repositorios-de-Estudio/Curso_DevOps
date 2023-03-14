# SECCIÓN DE PRACTICAS Y EJERCICIOS

# 12 PRACTICA Pipeline sencillo Jenkins (Freestyle Project)

Es Jenkins se va a definir un pipeline conactado a un repositorio de guthub, hace pull del repositorio, luego lo compila y lo instala dentro de un servidor CI/CD. \

Los desarrolladores envian cambios al repositorio github y es disparado cuando se detecta un pull request. \

Es una mala practica no tenia que instalarse la aplicación en el mismo contenedor de Jenkins, esto se hace asi a modo de ejemplo. \

![Arquitectura](./media/pipeline1.png)

- El pipeline tiene 3 tareas sencillas y manualmente vamos a hacer de trigger.
- La aplicación Billing es un microservicio en Java
- Debe estar running Jenkins: 'http://localhost:8080/login?from=%2F'
- Projecto Jenkins de tipo Freestyle Project
- Se debe tener un token de acceso para usar en las credenciales de git en jenkins

## 1 Repositorio Github

Repositorio: [pipeline-1-github](https://github.com/Repositorios-de-Estudio/devops-pipeline-1) \
Aplicación: Billing \
Ubicación: *12-pipelines-jenkis*

## 2 Configuración en Jenkins

1. Create a job
   1. Dar nombre: devops_test1
2. Condigo fuente
   1. git
   2. url: 'https://github.com/Repositorios-de-Estudio/devops-pipeline-1.git'
   3. credenciales: usar personal token
   4. branches: main
3. entorno
   1. build steps
      1. maven de nivel superior
      2. goles: clean install
      3. Advance >> POM billing/pom.xml

# PROBLEMAS

## Error

### Mensaje

*[ERROR] The goal you specified requires a project to execute but there is no POM in this directory (/var/jenkins_home/workspace/devops_test1). Please verify you invoked Maven from the correct directory. -> [Help 1]*

### Solución

Se debe indicar el POM.XML en *Build steps*. El path del pom.xml no debe incluir el root del repositorio.

# 13 PRACTICA pipeline + webhook + ngrok

Repositorio: 'https://github.com/Repositorios-de-Estudio/pipeline-java-angular' \
Aplicación: java (billing) + Angular \
Ubicación: *13-pipeline-webhook-ngrok* \
Herramientas: Webhook y ngrok

1. ejecutar ngrok
2. crear repositorio con billing y angularWorkSpace
3. configurar webhook en el repositorio apuntando al ngrok
   1. configuración: eventos push
4. jenkins > crear pipeline
   1. crear tarea
   2. estilo libre
   3. nombre: webhook_pipeline_1
   4. github project
      1. url: 'https://github.com/Repositorios-de-Estudio/pipeline-java-angular'
   5. origen del proyecto > git
      1. repository url: 'https://github.com/Repositorios-de-Estudio/pipeline-java-angular.git'
      2. credential:  *github-token-jenkins*
      3. Branches to build: *origin/feature/addtest*
   6. Disparadores > GitHub hook trigger for GITScm polling
   7. Build Steps
      1. ejecutar tareas maven de nivel superior
      2. Goles >> clean install
      3. Avanzado >> billing/pom.xml

## Ejecución

Como el webhook esta configurad con *solo push*, el trigger asociado a *GitHub hook trigger* se dispara con los *push* a la rama *feature/addtest*.

1. Hacer cualquier cambio y push en el repositorio
2. En la consola ngrok debe salir: *POST /github-webhook/          200 OK*
3. En github hooks debe salir el evento
4. En el servidor CI/CD debe aparecer que se ejecutó el pipeline
5. en los logs y console output debe haber un *Finish. Success*

***

# REFERENCIAS
