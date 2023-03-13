# Comandos Jenkins

Configuración para Pipeline free style con proyecto Java y Maven. Este pipeline tiene dos tareas.

1. descargar codigo de repositorio de github
2. `clean install` de maven

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
      1. POM: indicar ruta del POMP, ej: billing/pom.xml
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
