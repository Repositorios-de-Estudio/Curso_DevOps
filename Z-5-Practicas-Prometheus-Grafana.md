# Practicas Kubernetes con Prometheus Grafana

# Introducción Prometheus

Se creará un clusterRol (rol prometheus) para otorgar los permisos necesarios para Prometheus. Cualquier usario que pertenezca a ese rol recibirá los permisos del rol. \

La configuración se realiza por medio de un archivo: *authorization-prometheus.yaml* que contiene basicamente lo siguiente. \

Permisos

1. obtener
2. listar
3. consultar

Objetos a los que tiene permitido acceder

1. nodes
2. nodes/proxy
3. services
4. endpoints
5. pods

La configuración del rol debe ser almacenada persistente para que se pueda aplicar a cualquier pods que se cree en cualquier momento y no se elimine cuando sea eliminado el pod, esto se puede hacer globalmente con un configmap, esta configuración esta en *configmap-prometheus.yaml*, esta configuración se monta como un volumen. Este archivo usa el **namespace monitoring** definido en *authorization-prometheus.yaml*. \

En *configmap-prometheus.yaml* se crea *prometheus.rules* y *prometheus.yml* que es donde se configuran las reglas, para el ejercicio solo se crea una regla.

Reglas

1. Alerta cuando el contenedor supere 1 byte de memoria
   1. Se realiza la verificación cada 5s
   2. Se define que la regla se envie al alert manager por el puerto 9093

A medida que se vayan probando las reglas se puede ir ajustando, asi mismo, este valor de 1 se puede ir cambiando. La estructura basica de una reglas es la que se define entre *gropus:* y *summary*. \

Tambien se definen los *exportes* o jobs con job_name que define su funcionamiento y que información recopila. \

En *job_name: 'k8-state-metrics'* usa la configuración definida en el deploymente *kube-state-metrics* en el archivo *kube-state-metrics/deployment-ksm.yaml* \

La instalación de Prometheus se hará por medio de un contenedor definido en *deployment-ksm.yaml* con una imagen del repositorio de Prometheus.

### Configuración del sevicio k8-state-metrics

En el archivo *kube-state-metrics/cluster-role-ksm.yaml* se definen las propiedades del servicio.

Entre otros objetos a los que tiene permitido acceder estan

1. configmaps
2. secrets
3. nodes
4. pods
5. services
6. resourcequotas
7. replicationcontrollers
8. limitranges
9. persistentvolumeclaims
10. persistentvolumes
11. namespaces
12. endpoints

Acá tambien se definen que grupo de recursos tienen que permisos. Por cada grupo se define un set de permisos. \

Se crea un service account en *kube-state-metrics/authorization-ksm.yaml* que usa el *kube-state-metrics/cluster-role-ksm.yaml*, la definición de como es el servicio y la imagen que usa se encuentra en *kube-state-metrics/deployment-ksm.yaml*. Se define requiere que se defina como servicio, porque se requiere que sea un servicio accesible por IP por el servicio por Prometheus. 

# Configuración Roles para Prometheus

1. Ver namespaces: `kubectl get namespace`
2. Crear namespace para monitoring llamado monitoring: `kubectl create namespace monitoring`
3. Aplicar role de monitorizacion llamado prometheus: `kubectl apply -f authorization-prometheus.yaml`
   1. Verificar rol: ir a dashboard kubenetes > Cluster Roles >> prometheus
4. Aplicar configuración global de configuración para Prometheus: `kubectl apply -f configmap-prometheus.yaml`
   1. Verificar condigmap: ir a dashboard kubenetes > Config Maps >> filtrar por namespace monitoring >> prometheus-server-conf

# Instalación de Prometheus

1. Hacer deploy de la imagen de Prometehus: `kubectl apply -f deployment-prometheus.yaml`
2. Verificar los pods filtrado por el namespace monitoring: `kubectl get all --namespace=monitoring`
3. Ver interfaz de Prometheus
   1. Fijarse en el puerto interno de *prometheus-service* > 30000
   2. Ir al navegador: 'minikube ip'+:+puerto > '192.168.49.2:30000'
4. Instalación de servicio state-metrics
   1. Ejecutar despliege `kubectl apply -f kube-state-metrics/`
5. Verificar servicio kube-state-metrics
   1. Prometheus > Status >> Targets, el servicio debe estar UP.
6. Si aun no esta el servicio en UP, se debe recrear el primer deploy
   1. Eliminar definición: `kubectl delete -f deployment-prometheus.yaml`
   2. Hacer deploy de la imagen de Prometehus nuevament: `kubectl apply -f deployment-prometheus.yaml`
   3. Desde el Pods se pueden ver los errores, Kubernestes > Pods >> Filtrar por namespace monitoring

# Un poco Sobre Prometheus

Aca se ven todos los Jobs definidos. Si sale el Job state-metrics esta Down debido a que aún no se ha definido el servicio.

1. Prometheus > Status >> Targets

Se pueden ejecutar los querys PromQL en la lupa de la parte superior y se pueden ver un grafico basico en Garph. En Alets se ven las aletas que han sido configuradas. \

# Instalación de Alert Manager

Los archivos de configuración e instalación estan en *alert-manager/*. Se puede configurar correo electronico para las notificación, pero para efectos practicos se usrá Slack. \

El despligue y el servicio se definen en *deployment-alert-manager.yaml*, en *deployment-alert-manager.yaml* se define el tipo de almacenamiento como persistente y se le asigna un tamaño de 500MB. La configuración del servicio esta en *configmap-alert-manager.yaml* en donde de crea un archivo *alertmanager.yml* y es donde va la configuración del canal de Slack, esto requiere usar un webhook de Slack. Para el ejercicio cree el canal en slack llamado *curso-prometheus*.

## Confuguración de WebHook en Slack

1. Crear canal en Slack: curso-prometheus
2. Ir a More > Apps, buscar: incoming webhooks
   1. Add to Slack
   2. Post to Channel > curso-prometheus
   3. Add Incoming Webhook integrations
   4. usar la url que da Slack que dice *Webhook URL Send your JSON payloads to this URL:* 'https://hooks.slack.com/services/T100AAAA/blablablabla'
   5. Pegar esta URL en el campo *slack_api_url* del archivo *configmap-alert-manager.yaml*
   6. Pegar nombre del canal en el campo *slack_configs > channel*
3. En Slack se verá la notificación: añadió una integración a este canal: incoming-webhook
4. Agregar nombre del canal al configmap
   1. En **


***

# REFERENCIA

- [Documentación namespaces](https://kubernetes.io/docs/concepts/overview/working-with-objects/namespaces/)
- [Crear role de monitorizacion - Referencia Authoriation](https://kubernetes.io/docs/reference/access-authn-authz/rbac/)