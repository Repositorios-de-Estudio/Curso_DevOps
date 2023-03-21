# Comandos Kubernetes

## Ejemplo primera prueba

Todos los comandos deben tener una salida... \

*start* al final debe indicar *Done!* `minikube start` \

Debe indicar que hay un contenedor que corresponde a mnikube: `docker ps -a` \

Ver el status del cluster con el comando, esto no debe mostrar errores: `minikube status` \

Ver el dashboar con: `minikube dashboard --url` \

La aplicación se abre en la url y permite ver y administrar todos los componentes/objetos en la insfraestructura: <http://127.0.0.1:36855/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/>

# Comandos utiles

# Contenedores

- Ver contenedor de kubernetes en docker: `docker ps -a`
- Ingresar a contenedor de kubernetes como admin: `docker exec -it minikube /bin/bash`
- Status de minikube: `minikube status`
  - El contenedor se llama similar a:
    - Image: *gcr.io/k8s-minikube/kicbase:v0.0.37*
    - Name: *minikube*
- Dasboard: `minikube dashboard`
  - Se ven servicios, pods, secrets, configmaps, volumenes y persistencia, pods
- Crear un pod partiendo de una imagen en dockerhub: `kubectl run kbillingapp --image=sotobotero/udemy-devops:0.0.1 --port=80 80`
- Crear objetos en el cluster con definición: `kubectl apply -f definicion.yaml`
- Crear todos los objetos en el cluster: `kubectl apply -f ./`
- Ver todos los objetos creados: `kubectl get all`
- Listar todos los pod del namespace por defecto: `kubectl get pods`
- Obtener detalles del Pod: `kubectl describe pod kbillingapp`
- Crear el servicio: `kubectl expose pod kbillingapp --type=LoadBalancer --port 8080 --target-port=80`
- Listar servicios: `kubectl get services`
  - Aca se puede tomar el puerto externo PORTS interno:externo
- Obtener ip externa del cluster: `minikube ip`
- Obtener la ip externa del servicio: `minikube service --url kbillingapp`
- Obtener la ip externa del servicio: `minikube service kbillingapp`
- Obtener los logs de un servicio: `kubectl logs kbillingapp`
- Eliminar objetos creado con yaml: `kubectl delete -f definicion.yaml`
- Eliminar todos los objetos con definicion: `kubectl delete -f ./`
- Eliminar un pod: `kubectl delete service kbillingapp`
- Eliminar un servicio: `kubectl delete service kbillingapp`
- Consultar la version del api server: `kubectl api-versions`
- Codificar un parametro: `echo -n 'qwerty' | base64`
- Descodificar un parametro: `echo  "cXdlcnR5" | base64 -d`
- Comandos para apuntar el docker engine local hacia el registro de minikube:
    1. `minikube docker-env`
    2. `eval $(minikube -p minikube docker-env)`
- Consultar la ip de minikube: `minikube ip`
- Cifrar palabra: `echo -n "postgres" | base64`
- Descifrar palabra: `echo "cG9zdGdyZXM=" | base64 -d`

# Namespaces

- verificar namespaces: `kubectl get namespace`
- Crear el namespace si no existe: `kubectl create namespace monitoring`
- Aplicar role de monitorizacion: `kubectl apply -f moniring-role.yaml`
- Crear fichero de configuracion para externalizar la configuracion de prometheus: `kubectl apply -f configmap-prometheus.yaml`
  - (independiente del ciclo de vida del contenedor)
- Crear el contenedor y el servicio de prometheus (el contenedor): `kubectl apply -f deployment-prometheus.yaml`
- verificar los pods del namespace  monitoring: `kubectl get all --namespace=monitoring`