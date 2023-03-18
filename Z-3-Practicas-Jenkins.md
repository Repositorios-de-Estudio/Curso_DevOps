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
Herramientas: Jenkis, Webhook y ngrok
Configuración local: Netbeans 17 + java openjdk 17

1. ejecutar ngrok
2. crear repositorio con billing y angularWorkSpace
3. configurar webhook en el repositorio apuntando al ngrok
   1. 'https://4405-201-244-248-50.ngrok.io/github-webhook/'
   2. configuración: eventos push
4. jenkins > crear pipeline
   1. crear tarea
   2. estilo libre
   3. nombre: webhook_pipeline_1
   4. github project
      1. url: 'https://github.com/Repositorios-de-Estudio/pipeline-java-angular'
   5. origen del proyecto > git
      1. repository url: 'https://github.com/Repositorios-de-Estudio/pipeline-java-angular.git'
      2. credential:  *github-token-jenkins*
      3. Branches to build: _origin/feature/**_
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

# 14 PRACTICA pipeline CI/CD

Pipeline CI/CD completamente automatizado end to end, ejecuta pruebas automaticas, validación, hace el merge y elimina la rama.

El merge lo hace automaticamente cuando en github ya existe un pull request creado pero no aceptado. Luego de esto, la rama es eliminada.

Repositorio: 'https://github.com/Repositorios-de-Estudio/pipeline-java-angular' \
Aplicación: java (billing) + Angular + springtest + junit + mockvc \
Ubicación: *14-pipeline-CI-CD* \
Herramientas: Webhook y ngrok
Automatizado: captura de evento push, build, test, merge, eliminación de rama

## 1 Github

### Automatically delete head branches

1. Repositorio > config
   1. Pull Requests >> Automatically delete head branches

### webhook

1. configurar webhook en el repositorio apuntando al ngrok
2. 'https://4405-201-244-248-50.ngrok.io/github-webhook/'
3. configuración: eventos push

## 2 Verificar proyecto

Verificar que el proyecto compila sin errores y ejecuta las pruebas, luego de hacer esto localmente se deberia continuar.

## 3 Pipeline

1. jenkins > crear pipeline
2. crear tarea
3. estilo libre
4. nombre: webhook_pipeline_1
5. github project
   1. url: 'https://github.com/Repositorios-de-Estudio/pipeline-java-angular'
6. origen del proyecto > git
   1. repository url: 'https://github.com/Repositorios-de-Estudio/pipeline-java-angular.git'
   2. credential:  *github-token-jenkins*
   3. Branches to build: _origin/feature/**_
   4. Configurar datos para push automatico de github
      1. Additional Behaviours > add
      2. Custom user name/e-mail address
      3. aregar datos ficticios para name y email (estos serian los datos del commit que hace push)
7. Disparadores > GitHub hook trigger for GITScm polling (**NO**)
8. Build Steps
   1. ejecutar tareas maven de nivel superior
   2. Goles >> clean install
   3. Avanzado >> billing/pom.xml
9. Build Steps
   1. añadir nuevo paso
   2. ejecutar linea de comandos (shell)
   3. Comando  >> agregar estas lineas sin los numeros
      1. git branch
      2. git checkout main
      3. git merge origin/feature/addtest
10. Acciones para ejecutar despues
    1. Añadir una acción > Git Publisher
       1. Push only if build succeeds
       2. Branches >> add branch
          1. branch to push: main
          2. target remote name: origin
11. Aplicar
12. Guardar

## 4 Procedimiento

1. crear y usar rama: feature/addtest
2. probar codigo que funcione bien
3. el .gitignore debe estar configurado para no incorporara */target/
4. realizar cualquier cambio
5. commitar y hacer push
6. crear pull request, importante no aprobar, solo dejarlo hecho
7. ejecutar pipeline
8. verificar
   1. el pipeline debio haberse ejecutado, en la console output debe salir los comandos de git y succes
   2. en github debio haberse borrado la rama secundaria

## Errores

### Mensaje

Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.5.1:compile (default-compile) on project billing: Fatal error compiling: java.lang.IllegalAccessError: class lombok.javac.apt.LombokProcessor (in unnamed module @0x1132baa3) cannot access class com.sun.tools.javac.processing.JavacProcessingEnvironment (in module jdk.compiler) because module jdk.compiler does not export com.sun.tools.javac.processing to unnamed module @0x1132baa3 -> [Help 1]

### Motivo

Extensión *lombok* esta desactualizada para la version actual de Java.

### Solución

En el pom.xml > usar

```xml
<path>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.20</version>
</path>
```

# 15 PRACTICA pipeline CI/CD y Slack notificaciones

Pipeline CI/CD completamente automatizado end to end, ejecuta pruebas automaticas, validación, hace el merge, elimina la rama y envia notificaciones al canal de Slack.

El merge lo hace automaticamente cuando en github ya existe un pull request creado pero no aceptado. Luego de esto, la rama es eliminada.

Repositorio: 'https://github.com/Repositorios-de-Estudio/pipeline-java-angular' \
Aplicación: java (billing) + Angular + springtest + junit + mockvc \
Ubicación: *15-pipeline-CI-CD-slack* \
Herramientas: Jenkis, Webhook, ngrok y Slack
Automatizado: captura de evento push, build, test, merge, eliminación de rama

## 1 Github

### Eliminación automatico de ramas Github

1. Repositorio > config
   1. Pull Requests >> Automatically delete head branches

### webhook

1. configurar webhook en el repositorio apuntando al ngrok
2. 'https://4405-201-244-248-50.ngrok.io/github-webhook/'
3. configuración: eventos push

## 2 Verificar proyecto

Verificar que el proyecto compila sin errores y ejecuta las pruebas, luego de hacer esto localmente se deberia continuar.

## 3 Pipeline

1. jenkins > crear pipeline
2. crear tarea
3. estilo libre
4. nombre: webhook_pipeline_1
5. github project
   1. url: 'https://github.com/Repositorios-de-Estudio/pipeline-java-angular'
6. origen del proyecto > git
   1. repository url: 'https://github.com/Repositorios-de-Estudio/pipeline-java-angular.git'
   2. credential:  *github-token-jenkins*
   3. Branches to build: _origin/feature/**_
   4. Configurar datos para push automatico de github
      1. Additional Behaviours > add
      2. Custom user name/e-mail address
      3. aregar datos ficticios para name y email (estos serian los datos del commit que hace push)
7. Disparadores > GitHub hook trigger for GITScm polling (**NO**)
8. Build Steps
   1. ejecutar tareas maven de nivel superior
   2. Goles >> clean install
   3. Avanzado >> billing/pom.xml
9. Build Steps
   1. añadir nuevo paso
   2. ejecutar linea de comandos (shell)
   3. Comando  >> agregar estas lineas sin los numeros
      1. git branch
      2. git checkout main
      3. git merge origin/feature/addtest
10. Acciones para ejecutar despues
    1. Añadir una acción > Git Publisher
       1. Push only if build succeeds
       2. Branches >> add branch
          1. branch to push: main
          2. target remote name: origin
    2. Añadir reportes de JUnit
11. Aplicar
12. Guardar

## 4 Slack y Jenkins

1. Crear canal en Slack
   1. Canal: slack-curso
2. Configurar Slack Plugin en Jenkins
3. Configurar pipeline *webhook_pipeline_1*
   1. . workspace: *Team subdomain*
   2. credential
      1. Add Jenkins
      2. Kind: secret text: *Integration token credential ID*
      3. ID: dar un nombre, ej: slack-curso
      4. Add
   3. Credential > slack-curso
4. Test connection
   1. Debe salir: success
5. Apply
6. Guardar

## 4 Procedimiento

Aun se utitliza la rama *feature/addtest* debido a que la configuración del pipeline apunta a esta rama.

1. crear y usar rama: feature/addtest
2. probar codigo que funcione bien
3. el .gitignore debe estar configurado para no incorporara */target/
4. realizar cualquier cambio
5. commitar y hacer push
6. crear pull request, importante no aprobar, solo dejarlo hecho
7. ejecutar pipeline
8. verificar
   1. el pipeline debio haberse ejecutado, en la console output debe salir los comandos de git y succes
   2. en github debio haberse borrado la rama secundaria

# 16 PRACTICA CI/CD DESDE JENKINS

Con Jenkis, construir imagenes de Docker en base a codigo en un repositorio Github de manera automatica por medio de Pipelines y luego publicar la imagene en Dockerhub. \

Se usa Pipeline: *webhook_pipeline_1*.. \

Repositorio: '' \
Aplicación: Java (billing) + Angular + springtest + junit + mockvc \
Ubicación: *16-CI-CD-automatico-con-jenkins* \
Herramientas: Github, DockerHub, Dokcker, Jenkis, Webhook, ngrok y Slack
Automatizado: todos los pasos para la construccion de una imagen y cargala en dockerhub

1. Build Step, modificar con
   1. clean test install
2. Docker build and Publish
   1. Repository Name: billingapp-backend-clase (nombre de la imagen a crear)
   2. tag: 1.0.0
   3. Docker Host URI: 'tcp://172.17.0.1:2375'
      1. Se tuvo que crear puente entre el contejedor de jenkins y la maquina local, como ambas no estan en la misma red no es accesible docker engine desde jenkins. Ver todos los pasos *Ver creación de red puente Jenkins y Docker* en *A1-Jenkins-instalacion.md*.
   4. Server credentials: ninguna
   5. Docker registry URL: ninguna,dockerhub por defecto
   6. Registry credentials


***

# REFERENCIAS
