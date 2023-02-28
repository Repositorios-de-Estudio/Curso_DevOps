# actualizacion del sistema

```
sudo dnf clean all
sudo dnf check-update
sudo dnf update -y
sudo dnf clean all
```
# Pre-requitisitos

- Tener actividado y habilitada la virtualización `grep -E --color 'vmx|svm' /proc/cpuinfo`
- Tener instalado Docker


# Instalacion de Kubectl Fedora 37 - binario

```
curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl
```

# Prueba Kubectl
`kubectl version --client`


# Instalación de Minikube Fedora 37 - binario

```
curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
chmod +x ./minikube
sudo install minikube /usr/local/bin/minikube
```
## Configuraciones adicionales

```
sudo minikube config set driver docker
sudo minikube delete
minikube delete
```

# Prueba Minikube
```
minikube start
minikube version
docker ps -a
minikube status
```

## Salida de Minikube - start (🏄  Done! kubectl is now...)
```
😄  minikube v1.29.0 en Fedora 37
✨  Using the docker driver based on user configuration
❗  docker is currently using the btrfs storage driver, consider switching to
...
...
🌟  Complementos habilitados: storage-provisioner, default-storageclass
🏄  Done! kubectl is now configured to use "minikube" cluster and "default" namespace by default
```

## Salida de Docker
```
CONTAINER ID   IMAGE                                 COMMAND                  CREATED         STATUS         PORTS                                                                                                                                  NAMES
18a5e4c7cde4   gcr.io/k8s-minikube/kicbase:v0.0.37   "/usr/local/bin/entr…"   2 minutes ago   Up 2 minutes   127.0.0.1:32787->22/tcp, 127.0.0.1:32786->2376/tcp, 127.0.0.1:32785->5000/tcp, 127.0.0.1:32784->8443/tcp, 127.0.0.1:32783->32443/tcp   minikube
```

## Salida de Minikube - status
```
minikube
type: Control Plane
host: Running
kubelet: Running
apiserver: Running
kubeconfig: Configured
```

***

# Fuente

- [Instalacion Kubectl](https://k8s-docs.netlify.app/en/docs/tasks/tools/install-kubectl/)
- [Instalacion Minikube](https://k8s-docs.netlify.app/en/docs/tasks/tools/install-minikube/)


