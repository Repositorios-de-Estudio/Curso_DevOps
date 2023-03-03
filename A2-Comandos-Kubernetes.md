# Kubernetes

## Ejemplo primera prueba

Todos los comandos deben tener una salida... \

*start* al final debe indicar *Done!* `minikube start` \

Debe indicar que hay un contenedor que corresponde a mnikube: `docker ps -a` \

Ver el status del cluster con el comando, esto no debe mostrar errores: `minikube status` \

Ver el dashboar con: `minikube dashboard --url` \

La aplicación se abre en la url y permite ver y administrar todos los componentes/objetos en la insfraestructura: <http://127.0.0.1:36855/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/>

## Comandos utiles

- status de minikube: `minikube status`
- dasboard: `minikube dashboard`
  - se ven servicios, pods, secrets, configmaps, volumenes y persistencia, pods
- crear un pod partiendo de una imagen en dockerhub: `kubectl run kbillingapp --image=sotobotero/udemy-devops:0.0.1 --port=80 80`
- crear objetos en el cluster: `kubectl apply -f secret-de.yaml`
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
- Eliminar un pod: `kubectl delete service kbillingapp`
- Eliminar un servicio: `kubectl delete service kbillingapp`
- Consultar la version del api server: `kubectl api-versions`
- Codificar un parametro: `echo -n 'qwerty' | base64`
- Descodificar un parametro: `echo  "cXdlcnR5" | base64 -d`
- Comandos para apuntar el docker engine local hacia el registro de minikube:
    1. `minikube docker-env`
    2. `eval $(minikube -p minikube docker-env)`
- consultar la ip de minikube: `minikube ip`
- cifrar palabra: `echo -n "postgres" | base64`
- descifrar palabra: `echo "cG9zdGdyZXM=" | base64 -d`

