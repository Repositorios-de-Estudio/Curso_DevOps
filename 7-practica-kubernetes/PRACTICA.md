# Practica

Servicio en cluster de kubernetes que tiene una BD (postgresql) y un administrador web. El usuario final puede cnectarse al motor de la BD con otro cliente. Se va a definir un *deployment* para Postgres y otro para el PgAdmin.


# Arquitectura
Cluester conformado por dos pods, cada uno para la BD y otro para la aplicación. Se montará un servicio de acceso remoto o local para la administración de los pods.

1. Pod Postgres
    - Imagen de postgres
    - Volumen para almacenar datos
    - Segundo volumen para ejecutar scripts
2. Pod PgAdmin
    - Aplicación web

![Arquiectura-AppDevOps](arquitectura.png)


# Creación ficheros de configuración

