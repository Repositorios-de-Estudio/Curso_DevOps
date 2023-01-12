# actualizacion del sistema

`sudo dnf clean all`
`sudo dnf check-update`
`sudo dnf update -y`
`sudo dnf clean all`

# instalacion de Docker Engine Fedora 37 - repositorio

Instalacion en Fedora 37 mediante repositorio [documentacion oficial](https://docs.docker.com/engine/install/fedora/)

`sudo dnf -y install dnf-plugins-core`

```
sudo dnf config-manager \
    --add-repo \
    https://download.docker.com/linux/fedora/docker-ce.repo
```
    
`sudo dnf install docker-ce docker-ce-cli containerd.io docker-compose-plugin`


Iniciar Docker: `sudo systemctl start docker`

# prueba docker

`docker version`

`sudo docker run hello-world`

# Instalacion Docker Compose

Instalacion en Fedora 37 [Documentacion no oficial](https://computingforgeeks.com/install-and-use-docker-compose-on-fedora/)


`sudo dnf -y install wget`

```
sudo curl -s https://api.github.com/repos/docker/compose/releases/latest \
  | grep browser_download_url \
  | grep docker-compose-linux-x86_64 \
  | cut -d '"' -f 4 \
  | wget -qi -
```

`sudo chmod +x docker-compose-linux-x86_64`
`sudo mv docker-compose-linux-x86_64 /usr/local/bin/docker-compose`
`sudo chmod +x /usr/local/bin/docker-compose`
`docker-compose --version`

# configuracion sudo de docker / docker compose / docker desktop

`sudo groupadd docker`
`sudo usermod -aG docker ${USER}`

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