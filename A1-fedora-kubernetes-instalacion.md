# Actualizacion del sistema

```bash
sudo dnf clean all
sudo dnf check-update
sudo dnf update -y
sudo dnf clean all
```
# Pre-requitisitos

- Tener actividado y habilitada la virtualización `grep -E --color 'vmx|svm' /proc/cpuinfo`
- Tener instalado Docker


# Instalacion de Kubectl Fedora 37 - binario

```bash
curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl
```

# Prueba Kubectl

`kubectl version --client`


# Instalación de Minikube Fedora 37 - binario

``` bash
curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
chmod +x ./minikube
sudo install minikube /usr/local/bin/minikube
```

## Problemas de la instalación

Mensaje al ejecutar cualquier comando de minikube: *W0301 15:30:49.170353  111087 main.go:291] Unable to resolve the current Docker CLI context "default": context "default" does not exist* \

Otro mensaje que surge al usar minikube al usar el comando *minikube docker-env*: *Please re-eval your docker-env, To ensure your environment variables have updated ports*

Al parecer el problem era porque docker no estaba usando un contexto defaul que es lo que expera minikube, entonces se soluciona con `docker context use default`

### La solcuión es

```bash
docker context ls
docker context inspect default
docker context inspect desktop-linux
docker context rm desktop-linux
docker context ls
minikube start 
minikube config set driver docker
# para exportar y ver configuracion, si hay algun problema la salida del comando recomienda ejecutar el eval
minikube docker-env  
eval $(minikube -p minikube docker-env)
docker context use default
minikube stop
minikube delete
# LUEGO ES OBLIGATORIO REINICIAR
```

# Prueba Minikube

```bash
minikube start
minikube version
docker ps -a
minikube status
```

## Salida de Minikube - start (🏄  Done! kubectl is now...)

```text
😄  minikube v1.29.0 en Fedora 37
✨  Using the docker driver based on user configuration
❗  docker is currently using the btrfs storage driver, consider switching to
...
...
🌟  Complementos habilitados: storage-provisioner, default-storageclass
🏄  Done! kubectl is now configured to use "minikube" cluster and "default" namespace by default
```

## Salida de Docker

```text
CONTAINER ID   IMAGE                                 COMMAND                  CREATED         STATUS         PORTS                                                                                                                                  NAMES
18a5e4c7cde4   gcr.io/k8s-minikube/kicbase:v0.0.37   "/usr/local/bin/entr…"   2 minutes ago   Up 2 minutes   127.0.0.1:32787->22/tcp, 127.0.0.1:32786->2376/tcp, 127.0.0.1:32785->5000/tcp, 127.0.0.1:32784->8443/tcp, 127.0.0.1:32783->32443/tcp   minikube
```

## Salida de Minikube - status

```text
minikube
type: Control Plane
host: Running
kubelet: Running
apiserver: Running
kubeconfig: Configured
```

## Solución Docker btrfs

Mensaje al ejecutar minikube:*docker is currently using the btrfs storage driver, consider switching to overlay2* \


**Contenido:**

```json
{
    "storage-driver": "overlay2"
}
```

**Comandos:**

```bash
#ver configuración actual
sudo docker info | grep -i "Storage Driver"
sudo systemctl stop docker
sudo nano /etc/docker/daemon.json
sudo systemctl start docker
# comprobar con
sudo docker info | grep -i "Storage Driver"
# LUEGO ES OBLIGATORIO REINICIAR
# comprobar de nuevo
docker info | grep -i "Storage Driver"
```

***

# Fuente

- [Instalacion Kubectl](https://k8s-docs.netlify.app/en/docs/tasks/tools/install-kubectl/)
- [Instalacion Minikube](https://k8s-docs.netlify.app/en/docs/tasks/tools/install-minikube/)


