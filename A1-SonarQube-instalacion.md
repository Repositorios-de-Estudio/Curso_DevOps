# Uso de SonarQube en una imagen Docker

## Instalación y configuración

1. Descargar imagen de sonarqube: `docker pull sonarqube`
2. Levantar contenedor: `docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube`
3. Verificar contederos de Jenkins y SonarQube
4. Crear red virtual y comunicar ambos contenedores
   1. crear: `docker network create jenkins-sonarqube`
   2. Conectar contenedores, uno a la vez:
      1. `docker network connect jenkins-sonarqube sonarqube`
      2. `docker network connect jenkins-sonarqube jenkinsCont`
   3. verificar debe aparecer la red jenkins-sonarqube:
      1. `docker container inspect sonarqube | grep -iA 19 "networks"`
      2. `docker container inspect jenkinsCont | grep -iA 19 "networks"`
5. Configurar SonarQube:
   1. 'http://localhost:9000/'
      1. U: admin
      2. P: admin
   2. administration > security > users
   3. Crear Toquen para conectar con Jenkins
      1. sobre la fila del usuario administrador en Tokens dar click sobre las lineas con pubntos en forma de parrador
      2. En generate Token
         1. name: jenkins
         2. no expiration
         3. generate
         4. Luego copiar el token de sonar: *squ_da6911b21788e569867f634753b00f3928d97efc*
6. Configurar Jenkins
   1. 'http://localhost:8080'
   2. Instalar Plugin SonarQube
      1. Administrar jenkins
      2. administrar plugins
      3. instalar: SonarQube Scanner
      4. reiniciar el contenedor
   3. Configuracion plugin
      1. administrar jenkins > configuracion del sistema > Buscar SonarQube servers
      2. Marcar: *Environment variables Enable injection of SonarQube server configuration as build environment variables*
      3. Add SonarQube
      4. nombre: dar nombre > sonarqubetest
      5. Url: http://+nombre-de-contenedor-sonarqube+:9000 > 'http://sonarqube:9000'
      6. Add credentials > jenkis
         1. Kind: secret text
         2. secret: token-de-sonarqube > squ_da6911b21788e569867f634753b00f3928d97efc
         3. ID: sonarqube
         4. Apply
         5. Server authentication token > usar *sonarqube*
         6. Apply
         7. Save
   4. Configuiración adicional
      1. administrar jenkis > global tool configuration > buscar SonarQube Scanner ( no usar MSBuild)
      2. añadir SonarQube Scanner
         1. name: nombre cualquiera > sonarscanner
         2. marcar Instalar automaticamnete
            1. Install from maven central > 4.8.X
            2. Apply
            3. Save

## Agregar escaneo de codigo automatico

Se usa la carpeta *15-pipeline-CI-CD-slack* y se va a habilitar el escaneo de codigo automatico solamente a *billing*.

1. Jenkins > dashboard > entrar al pipeline > seleccionar sonarqube (lista borde izquierdo)
2. configurar > buscar Build Steps (donde estan los comandos git)
3. Ejecutar > añadir un nuevo paso > execute SonarQube Scanner
   1. Task to run: scan
   2. JDK: dejar por defecto
   3. Analys properties: agregar la siguiente lista
      1. sonar.projectKey=sonarqube
      2. sonar.sources=billing/src/main/java
      3. sonar.java.binaries=billing/target/classes
   4. Habilitar debud
      1. Additional arguments: -X
   5. Explicación
      1. projectKey: se va a crear un proyecto con este parametro como nombre
      2. source: ruta clases a analizar
      3. java.binaries: ruta de binarios
4. Ahora es recomendable que el analisis de codigo sea lo primero que se jecute
   1. Arrastrar y colocar de primero del todo en *Ejecutar*
      1. debe quedar por encima de *clean install* y los comandos de git
5. Apply
6. Save

## Probar

1. Ejecutar el pipeline
   1. Se crea un Project nuevo en SonarQube:9000
   2. En el Pipeline > Console output, verificar *Finished: SUCCESS*

# Errores

## Mensaje

mensaje de error al darle *Contruir Ahora* al pipeline

```java
org.sonarsource.scanner.api.internal.ScannerException: Unable to execute SonarScanner analysis
at org.sonarsource.scanner.api.internal.IsolatedLauncherFactory.lambda$createLauncher$0(IsolatedLauncherFactory.java:85)
at java.base/java.security.AccessController.doPrivileged(Native Method) 
```

El error estaba en la configuración de la url de la dirección de sonarqube, el nombre del contenedor estaba incorrecto: 'http://sonarqubetest:9000' > 'http://sonarqube:9000'

# REFERENCIAS

1. [Documentación SonarQube - extend the image](https://docs.sonarqube.org/latest/analyzing-source-code/ci-integration/jenkins-integration/)
2. [Guia de usuario SonarQube](https://docs.sonarqube.org/latest/user-guide/metric-definitions/)