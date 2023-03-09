# actualizacion del sistema

[Componentes e Docker](./media/DockerComponents.png)

```
sudo dnf clean all
sudo dnf check-update
sudo dnf update -y
sudo dnf clean all
```

# Instalacion de Docker Engine Fedora 37 - repositorio

Instalacion en Fedora 37 mediante repositorio [documentacion oficial](https://docs.docker.com/engine/install/fedora/)

```
sudo dnf -y install dnf-plugins-core
```

```
sudo dnf config-manager \
    --add-repo \
    https://download.docker.com/linux/fedora/docker-ce.repo
```
    
```
sudo dnf install docker-ce docker-ce-cli containerd.io docker-compose-plugin
```


Iniciar Docker: 

```
sudo systemctl start docker
sudo systemctl enable docker
```

# prueba docker

```bash
docker version
```


# Instalacion Docker Compose

Si se instala Docker Desktop no es necesario instalar Docker Compose ya que viene en el Desktop.

Instalacion en Fedora 37 [Documentacion no oficial](https://computingforgeeks.com/install-and-use-docker-compose-on-fedora/)

```bash
sudo dnf -y install wget
```

```bash
sudo curl -s https://api.github.com/repos/docker/compose/releases/latest \
  | grep browser_download_url \
  | grep docker-compose-linux-x86_64 \
  | cut -d '"' -f 4 \
  | wget -qi -
```

```bash
sudo chmod +x docker-compose-linux-x86_64
sudo mv docker-compose-linux-x86_64 /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose --version
```

# Instalación Docker Desktop

Se puede instalar Docker desktop, no es obligatorio:

1. Instalar Docker Engine mediante _Repositorio_
2. Descargar RPM [web oficial para Fedora](https://docs.docker.com/desktop/install/fedora/)
3. Configuracion de virtualizacion: sudo usermod -aG kvm $USER
4. Instalar: sudo dnf install `./docker-desktop-<version>-<arch>.rpm`
5. Lanzar Docker Desktop: `systemctl --user start docker-desktop`

Docker desktop permite interactuar visualmente:

- Volumenes
- Imagenes
- Contendeores
  - Estado: en ejecuión o detenido

# configuracion sudo de docker / docker compose / docker desktop

```bash
sudo groupadd docker
sudo usermod -aG docker $USER
```

reboot

```bash
docker run hello-world
```

### Si hay problemas con run hello-world (esto esta en la documentacion oficial)

`sudo rm -r .docker/`

reboot

```bash
sudo chown "$USER":"$USER" /home/"$USER"/.docker -R
sudo chmod g+rwx "$HOME/.docker" -R
```

# PROBLEMAS

## Mensaje error al construir imagen
El problema surgio luego de haber desinstalado Docker Desktop.
Mensajes:

- _=> ERROR [internal] load metadata for docker.io/library/openjdk:17-jdk-slim-bullseye_
- _> [internal] load metadata for docker.io/library/openjdk:17-jdk-slim-bullseye:_

### Solución

```bash
sudo systemctl stop docker 
sudo systemctl status docker
rm ~/.docker/config.json
sudo systemctl start docker
```

Fuente [Manage Docker as a non-root user](https://docs.docker.com/engine/install/linux-postinstall/)
