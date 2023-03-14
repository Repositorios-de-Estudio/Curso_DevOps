# Comandos Jenkins

Configuración para Pipeline free style con proyecto Java y Maven. Este pipeline tiene dos tareas.

1. descargar codigo de repositorio de github
2. `clean install` de maven

Basado en: *12-pipelines-jenkis*
Repositorio: 'https://github.com/Repositorios-de-Estudio/devops-pipeline-1'

## Crear Pipeline

1. Create Job
2. Indicar nombre
3. Escoger estilo, ej: fresstyle

## Token de acceso GitHub

1. Settings
2. Seleccionar perfil (no funciona disponible en organizaciones)
3. Developer settings
4. Personal access tokens
5. Tokens (classic)
6. Generate new token classic
   1. Permisos en (**no estoy seguro de que sean los minimos requeridos**)
      1. repo (full control)
      2. workflow
      3. notifications
      4. write discussion
      5. audit log
      6. project (full control)

## Enlazar codigo fuente

1. Configure > Source code management
2. Git
   1. URL, la url.git de *code https* para que funcione con el *personal token*
   2. Credenciales
      1. add > jenkins
      2. scope: global
      3. username: nombre de usuario en github
      4. contraseña: token
      5. Marcar *TREAT USERNAME AS SECRET*
      6. ID: ID para reconocer las credenciales que se usan ej: github-token-jenkins
      7. ADD
   3. Credenciales
      1. seleccionar credenciales guardades, ej: github-token-jenkins
   4. Branches to build
      1. branch: indicar nombre de la rama, ej: main
   5. Apply

## Configurar ejecución

1. Configure
   1. Build triggers
      1. Add build step > invoke top-level maven targets
      2. Goals: fases u opcioones de maven
         1. escribir: clean install
   2. SAVE - para guardar el pipeline

## Workspace - Confguraciones adicionales

1. Configure
   1. Build steps
   2. Advance
      1. POM: indicar ruta del POMP, ej: *billing/pom.xml*
      2. LA RUTA no debe incluir la carpeta raiz ya que en el workspace ya esta
   3. Guardar

## Para ejecutar y diagnosticar

1. Dentro del Pipeline
   1. Build Now
2. Desde el deasboard de jenkins
   1. (icono flecha verde) schedule a build
3. Luego de ejecutar
   1. ir a Console output
   2. sobre el indicador de # dar click
   3. ver el logs para indentificar errores

## Conectarse al contenedor

Esto es una mala practica no tenia que instalarse la aplicación en el mismo contenedor de Jenkins, esto se hace asi a modo de ejemplo.

1. `docker exec -it c3fba2e14ac7 /bin/bash`
   1. Verificar contenido de la instalacion del JAR
      1. la ruta esta en los logs de ejecución del pipeline
      2. `ls -al /var/jenkins_home/.m2/repository/com/paymentchain/billing/0.0.1-SNAPSHOT/`

## Administrar credenciales de github

Ir a: *Dashboard > manage jenkins > credentiales > system > global credentials*

## Conectar sevidor github con jenkins

Ir a: *Jenkins > dashboard > configure > configuración del sistema > Github*

1. Add github server
2. Asignar nombre, ej: servidor devops curso
3. Add credentials > Jenkins
   1. Kind: secret text
   2. Secret: usar el token de github
   3. ID: dar un identificar, ej: token-github-devops
   4. Add
4. Add credentials > token-github-devops
5. Test connection: debe salir *"credentiales varified for user"*
6. Apply
7. Save

# Crear pipeline usando webhook

Recomendable debe tener primero configurado *Conectar sevidor github con jenkins*.

1. Jenkins > Dashboard > Nueva tarea
2. nombre: webhook_pipeline_1
3. estilo libre
4. ok
5. github project
   1. project url: del repostorio sin el .git: 'https://github.com/Repositorios-de-Estudio/pipeline-java-angular'
6. configurar origen del codigo
   1. Git
   2. repository url: del repositorio del clone https con .git: 'https://github.com/Repositorios-de-Estudio/pipeline-java-angular.git'
   3. Credentials: usar *github-token-jenkins* (este token es el de enlazar codigo fuente)
   4. Branches to build: usar la rama secundaria creada con *origin/*: *origin/feature/addtest*
      1. aca se pueden usar comodines
7. Disparadores > GitHub hook trigger for GITScm polling
8. Build Steps
   1. ejecutar tareas maven de nivel superior
   2. Goles >> clean install
   3. Avanzado >> billing/pom.xml
9. Apply
10. Save
11. Para ejecutar
    1. Hacer cualquier cambio y push en el repositorio
    2. En la consola ngrok debe salir: *POST /github-webhook/          200 OK*
    3. En github hooks debe salir el evento
    4. En el servidor CI/CD debe aparecer que se ejecutó el pipeline
       1. en los logs y console output debe haber un *Finish. Success*
