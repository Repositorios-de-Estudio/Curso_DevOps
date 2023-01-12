# Caso de estudio

Se requiere desplegar la aplicacion de facturacion de la compañia en los entornos de integracion, preproduccion y produccion. La instalacion debe contar con alta disponibilidad (excepto en el entorno de integracion)

## Requisitos Tecnicos
- Java 1.8
- Servidor WEB Ngix
- Base de datos Postgres


## En caso de no usar metodologias DevOps

Se tendria que hacer la instalación de los 3 ambiantes (5 servidores).

1. Instalacion y configuracion de cada servidor x 5 veces
2. Instalacion y configuracion de JRE x 5 veces
3. Instalacion y configuracion de Ngix x 5 veces
4. Instalacion y configuracion de Postgres x5 veces
5. Instalacion y configuracion del balanceador de carga x 2 veces


## En caso usar metodologias DevOps

Solo se tendria que tener Docker Engine instalado de los 3 ambientes (5 servidores).

1. Instalacion y configuracion de cada servidor x 5 veces
2. Instalacion y configuracion de Docker Engine x 5 veces
   1. La configuración necesesaria estaria dado por las imagenes de Docker
3. Instalacion y configuracion del balanceador de carga x 2 veces



