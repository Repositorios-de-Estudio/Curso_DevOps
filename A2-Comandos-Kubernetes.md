# Kubernetes

## Ejemplo primera prueba

Todos los comandos deben tener una salida... \

*start* al final debe indicar *Done!* `minikube start` \

Debe indicar que hay un contenedor que corresponde a mnikube: `docker ps -a` \

Ver el status del cluster con el comando, esto no debe mostrar errores: `minikube status` \

Ver el dashboar con: `minikube dashboard --url` \

La aplicación se abre en la url y permite ver y administrar todos los componentes/objetos en la insfraestructura: *http://127.0.0.1:36855/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/*

## Creacion de POD: Prueba con imagen
1. Tener imagen dockerisada: *https://hub.docker.com/r/sotobotero/udemy-devops/tags*
2. Recomiendan tener solo el docker de minikube `docker ps -a`
3. crear pod: `kubectl run nombre_cualquiera -- image=url-imagen --port=puerto1 puerto2`
    - puerto1 = puerto expuesto a internet (externo)
    - puerto2 = puerto expuesto dentro del Pod
    - `kubectl run kbillingapp --image=sotobotero/udemy-devops:0.0.1 --port=80 80`
4. Salida de la consola: *pod/kbillingapp created*
5. Comprobar pod:
    - por dashboard: Pods : kbillingapp
    - por kubectl: `kubectl get pods`
        - detalles: `kubectl describe pod kbillingapp`
6. 

## Comandos utiles

- status de minikube: `minikube status`
- dasboard: `minikube dashboard`

crear un pod partiendo de una imagen en dockerhub

kubectl run kbillingapp --image=sotobotero/udemy-devops:0.0.1 --port=80 80

Listar todos los pod del namespace por defecto

 kubectl get pods

Obtener detalles del Pod

kubectl describe pod kbillingapp

Crear el servicio

kubectl expose pod kbillingapp --type=LoadBalancer --port 8080 --target-port=80  

Obtener la ipexterna del servicio en minikube

minikube service kbillingapp

Obtener los logs de un servicio

kubectl logs kbillingapp

Eliminar un pod

kubectl delete service kbillingapp

Eliminar un servicio

kubectl delete service kbillingapp

Consultar la version del apiserver

kubectl api-versions

Codificar un parametro

echo -n 'qwerty' | base64

Descodificar un parametro

echo  "cXdlcnR5" | base64 -d
Comandos necesario para apuntar el docker engine local hacia el registro de minikube

minikube docker-env

eval $(minikube -p minikube docker-env)

consultar la ip de minikube

minikube ip

