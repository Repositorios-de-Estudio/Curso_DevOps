# Curso: GIT+GitHub: Todo un sistema de control de versiones de cero
Curso de: [GIT+GitHub por Fernando Herrera](https://www.udemy.com/course/git-github/)


# CONCEPTOS Y DEFINICIONES

Git es un sistema de control de versiones distribuido, lo que significa que un clon local del proyecto es un repositorio de control de versiones completo. Estos repositorios locales plenamente funcionales permiten trabajar sin conexión o de forma remota con facilidad. Los desarrolladores confirman su trabajo localmente y, a continuación, sincronizan su copia del repositorio con la copia en el servidor. Este paradigma es distinto del control de versiones centralizado, donde los clientes deben sincronizar el código con un servidor antes de crear nuevas versiones.

**Ref:** [Que es Git: AzureDevOps](https://learn.microsoft.com/es-es/devops/develop/git/what-is-git)


## Repositorio Local
Copia local del repositorio (archivos de trabajo) por cada version nueva o cambios que se hayan hecho.Se puede trabajar con git de esta manera.

## Repositorio Distribuido
Copia del repositorio en un servidor externo de todos los archivos con todos los cambios realizados, esta es la manera como Git funciona. ej: GitHub, GitLab.

## Stage
Es el espacio de trabajo temporal donde se van guardando los cambios a los archivos con seguimiento. No estar en el stage sigfinica que los archivos en cuestion no tiene seguimiento.

## Apuntador
Es la referencia de donde se encuentra ubicado actualmente en el tiempo. generalmente el apuntador siempre apunta a HEAD.

## Commit
Se usa para para guardar el estado actual de todos los archivos en el stage sin cambios guardados. El commit es como un screenshot el cual se identifica mediante un hash y se pueden agregar comentarios a los commits.

Regresar a un commit anterior restauraria todos los archivos a ese estado y eliminaria los que no estan. Esto significa que el apuntador se mueve a ese commit.

## Tags
Son etiquetas que ahcen referencia hace un commit y a todo el estado del proyecto en ese punto.
    - Los tags se ven en el log, el tag siempre va a apuntar al commit donde se crea el tag

## Rama
Una rama o branch es una versión del repositorio (temporal y espacial) desde un commit en especifico lo cual crea una bifurcación de este que a su vez es como si fuera un repositorio a parte,el cual sirve en la practica para desarrollar cosas diferentes que afecta el proyecto paralelamente pero los cambios se realizan sobre otra rama distinta a la principal. Luego se pueden introducir los cambios a otras ramas o a la rama principal con uniones.

- La rama principal es: **main**
- Los commit se comparten entre todas las ramas
- Inicialmente el HEAD apunta al main y a la nueva rama
- Cuando hay un commit nuevo el HEAD apuntaria a la rama con de ese commit

## MERGE (unión) de Ramas y Conflictos

Al unir las ramas a demas de introducir cambios a la rama principal, tambien se agregan los commits que se hayan hecho. Git siempre intentará resolver el merge de manera automatica.

- **Fast-forward**: Unión rapida se da cuando git no encuentra ningun commit en la rama principal y los cambios de la otra rama pueden ser incorporados sin problema.

- **Automaticas**: Unión automatica se da cuando git detecta que en la rama principal hubieron cambios que no estan presentes en la otra rama pero no se cruzan en los mismos lugares en los archivos con cambios en la otra rama.

- **Manual**: Unión manual se da cuando se deben hacer ajustes manualmente a los archivos cuyos cambios estan en conflicto entre la rama principal y la otra rama. La solución del conflicto crea un commit llamada *Merge Commit*.

# Stash
Se usa para guardar todos los cambios que aún no estan en el stage luego del último commit en otro lugar dejando de esta manera todo el repositorio en el mismo estado del último commit, posteriormente se pueden unir estos cambio, cuando se unen estos cambios del stash el stage los va a reconocer como cambios sin guardar. Se pueden crear stash con nombres y estos se apilan entre si.
- Esto es util cuando se requiere coninuar con el trabajo tal cual como esta en el último commit.
- Los stash pueden crearce con nombres
- Los stash se ven en el log y tiene su hash similar a un commit
- Los stash se ven como bifurcaciones en el log, cuando se restaura tambien se elimina del log siempre y cuando haya generado conflictos
- Cuando se restaura desde el stash resolviendo conflictos en la pila de stashes queda un resgistro que se puede borrar
- Cuando se restaura un stash puede ocasionar conflictos, por lo que se deben solucionar

## (POP) unión desde Stash y Conflictos

Al unir los cambios desde el stash se pueden presentar conflictos de la misma manera que en el merge de ramas. Git siempre intentará resolver el merge de manera automatica.

- **Fast-forward**: Unión rapida se da cuando git no encuentra ningun conlicto.

- **Automaticas**: Unión automatica se da cuando git detecta que los mismos archivos fueron modificados pero no en las lismas lineas.

- **Manual**: Unión manual se da cuando se deben hacer ajustes manualmente a los archivos cuyos cambios estan en conflicto entre. Se soluciona de la misma manera que un *Merge Commit*.

# Rebase

## CONFIGURACIÓN INICIAL

Configuración de usuario como información para los commits:

Usuario: \
`git config --global user.name "Mi Nombre"`

Correo: \
`git config --global user.mail "minombre@correo.com"`

Archivo configuraciones git: \
`~/.gitconfig`

Establecer rama por defecto de los repositorios inicializados:  \
`git config --global init.defaultBranch nombreRama` \
*Esto crea una rama llamada nombreRama, inicializa el repositorio y asigna la arama nombreRama como la principal.*

Información git del repositorio: \
`ProyectoCualquiera/.git` \
*Eliminar esta carpeta eliminaria git del proyecto*

Inicializar y agregar cambios: 
```
git init
git add .
git commit -m "Mi primer commit"
```

Omitir o ignorar del stage archivos y directorios en concreto: \
`Proyecto/.gitignore` \
*OMITE los cambios realiazos sobre los directorios, subdirectorios y archivos especificados* \
*Estos archivos no se cargan al repositorio*

Cración de alias global para comandos: \
` git config --global alias.{alitas} "{comando y opciones}"`

ejemplo para usar `git s` como `git status --shot`:  \
` git config --global alias.s "status --short"`

**VER Y CAMBIAR CONFIGURACIÓN GLOBAL:** \
`git config --global -e`


***
# COMANDOS DE GIT

## Convenciones

1. **REQ:SG** = REQUIERE SEGUIMIENTO / REQUIERE SER AGREGADO AL STAGE

Significa que el archivo tuvo que haber sido agregado al control de cambios (git add), de lo contrario no se verá afectado por el comando en cuestion.

2. **HEAD^**

Es el commit anterior al actual, este se puede reemplazar por el hash de un commit.

# Basicos de GIT

traer cambios del repositorio remoto al local: \
`git pull` \
*Esto solo trae los cambios de la rama en la que se esta localmente*

cargar o enviar cambios del repositorio local al remoto: \
`git push` \
*Esto solo envia los cambios de la rama en la que se esta localmente*

uso de comandos con palabras completa: \
`git --palabra`

uso de comandos con abreviaciones: \
`git -abreviatura`

version git: \
`git --version`

ayuda de git: \
`git --help` \
`git --help palabra`

listar configuración: \
`git config --list`

eliminar alias: \
`git config --global --unset alias.{alias}`

estado del repositorio, modificaciones, eliminaciones y archvios que no estan en el satage: \
`git status`

git status con solo los cambios presentes: \
`git status --short` \
*Modificado: M - Eliminado: D - Sin seguimiento: U, ?? - Añadido: A - Renombrado: R*  \
*Color verde: ESTA en el stage - Color verde: NO esta en el stage*

agregar archivo al stage: \
`git add nombreArchivo`

agregar todos archivos y directorios al stage: \
`git add .`

agregar todos archivos .html desde el root al stage: \
`git add *.html` \
*Esto NO es recurrente sobre los subdirectiorios*

agregar todos los subdirectorios y su contenido al stage: \
`git add carpeta/` \
*Esto SI es recurrente sobre los subdirectiorios*

agregar al stage subdirectorios con archivos .html: \
`git add carpeta/*.html`

agregar todos los cambios y crear commit (REQ:SG): \
`git commit -am "commit"`

eliminar commit sin borrar cambios: \
`git reset --soft HEAD^` \
*Regresa al commit señalado y automaticamente agrega los cambios realizados* \

eliminar commit sin borrar cambios: \
`git reset --mixed HEAD^` \
*Regresa al commit señalado y NO agrega los cambios realizados, quita todos los archivos agregados al stage que no estaban en ese commit por lo que será necesario hacer git add .* 

eliminar commit y borrar todos los cambios: \
`git reset --hard HEAD^` \
*Regresa al commit señalado y borra todos los cambios realizados*

revertir los cambios eliminados con reset --hard: \
`git reflog` \
`git reset --hard hash-de-commit` \
*Se regresa al estado de un estado anterior, se usa reflog para ver todos los registros y escocoger uno anterior a la eliminación que se quiere restaurar*

renombrar ultimo commit: \
`git --amend -m "Comentario correcto"`

quitar archivo del stage: \
`git reset nombreArchivo` \
*Predeterminadamente usa la opción --mixed*

renombrar o mover archivo (REQ:SG): \
`git mv ruta/nombreViejo.algo ruta(nombreNuevo.algo` \
*ruta puede ser la misma ruta o una diferente*

eliminar archivo: \
`git rm -f nombreArchivo` \
*-f opción para forzar eliminacion* \
*Se puede restaurar con git reset --hard si (REQ:SG)*

descargar cambios sin haber hecho add . (REQ:SG): \
`git restore archivoNombre`

descartar todos los cambios sin haber hecho commit (REQ:SG): \
`git restore .`
*descartar, omitir, ignorar*

regresar repositorio al commit anterior (REQ:SG): \
`git checkout -- .`

diferencia entre archivos estado actual vs ultimo commit:
`git diff` \
*archivo anterior: a/archivo - archivo actual: b/archivo* \
*linea eliminada: - (en rojo), linea agreada: + (en verde)* \
*Con editores de texto se puede ver las fiferencias sobre el archivo*

logs, ver los ultimos n registros:  \
```
git log
git log -n
git log --oneline
```
*Se ven los commit realizados con su hash, su fecha, comentario y a donde apunta el HEAD, normelmente al main (HEAD -> main)* \
*Este comando no muestra los commit desechos realizados por git reset*

Ver todos los registros:  \
`git log` \
*Este comando registra todo, incluyendo los cambios desechos por git reset*

logs con grafico para ver commits y ramas: \
`git log --oneline --decorate --all --graph`

ver tags: \
`git tag`

agregar un tag: \
`git tag nombre-de-tag`

eliminar tag: \
`git tag -d nombre-del-tag`

crear tag anotado: \
`git tag -a v1.0.0 -m "Version mensaje"` \
*El mensaje del tag no se ve en el log, solo se ve el tag*

agregar a tag anotado a commit: \
`git tag -a v0.0.0 hash-del-commit -m "Version mensaje"`

ver detalles del tag: \
`git show v0.1.0` \
*Muestra cuando se hizo, por quien, el commit al que pertenece e información sobre los cambios de ese commit*

agregar stash: \
`git stash`

agregar stash con nombre o mensaje: \
`git stash save "Mensaje"`

ver stashes: \
`git stash list` \
*Mustra los stashes como una pila*

restaurar cambios desde stash: \
`git stash pop` \
*Restaura el primer stash de la pila stash@{0} y lo elimina de la pila*

restaurar cambios desde un stash particular: \
`git stash apply stash@{2}` \
*Esto no elimina el stash de la pila, sería necesario usar git drop o clear*

ver los archivos con cambios contenidos en un stash: \
`git stash show stash@{2}`

ver lista de stashes con detalles: \
`git stash list --stat`

eliminar un stash particular: \
`git stash clear` \
*Se pueden recuperar los cambios de los stash con el hash en reflog*

vaciar o eliminar todos los stash: \
`git stash drop stash@{3}` \
*Si no se indica el stash por fecto elimina el primero: {0}*

# Trabajo con Ramas

ver ramas del repositorio: \
`git branch`
*Indica * la rama actual*

eliminar rama: \
`git branch -d otraRama`

cambiar nombre de la rama: \
`git branch -m nombreActual nombreFuturo`
*Si hubieran cambios sin unir a otra rama git lo alerta*

crear rama: \
`git branch otraRama`

crear rama y moverse a esa rama: \
`git checkout -b otraRama`

cambiar de rama: \
`git checkout otraRama`
*Solo se actualizan los archivos (REQ:SG), los que no estan en el stage siempre se veran hasta que los agreguemos al stage de alguna rama*

unir ramas, introducir cambios de otraRama a main: 
```
git checkout master
git merge otraRama
```
*Trae los cambios de otraRama a main - Luego de la unión el HEAD apuntaria a main y otraRama*
*Puede resultar en Fast-forward o Automatic o manual*

resolver conflictos: \

    - Mensaje:
    ```
    Auto-fusionando archivo.md
    CONFLICTO (contenido): Conflicto de fusión en archivo.md
    ```
    - Se deben solver los siguientes comentarios en *archivo.md* en el editor de texto: *Se deben hacer las modificaciones necesarias segun lo que sequiere, al final no debe haber '<<<' ni '>>>' ni tampoco'===='*
    ```
    <<<<<<< HEAD
    3. Buscar nuevos miembros que luchen por la justicia.
    =======
    3. Buscar nuevos miembros que sean super heroes
    >>>>>>> nueva-rama
    ```
    - Solución:
    ```
    3. Buscar nuevos miembros que luchen por la justicia y sean super heroes.
    ```
    - Luego se guardan los cambios para quedar con la rama con los cambios de las dos rama. `git commit -am "resuelve conficto"`

    # Agregar cambios y cargar al repositorio remoto
    ```
    git add .
    git commit -m "comentario"
    git pull
    ```


# ERRORES COMUNES

- LF will be replaced by CRLF in archivoNombre, solución: \
`git config core.autocrlf true`

- Al revertir los cambios asegurarse de que todos los archivos esten en el stage para prevenir perder cambios nuevos. Puede ayudar crear una rama nueva con los cambios actuales.

# NOTAS
- Las carpetas vacias no se pueden agregar al stage
- Usar los alias facilita el trabajo
- No retroceder y hacer cambios a un commit muy anterior, puede traer problemas al tener un estado muy anterior. Se recomienda generar una rama desde ese commit y trabajar sobre ella y luego unir los cambios con la rama principal.
- Versionamiento: v{1}.{2}.{3}: 
    1. Gran cambio
    2. Funcionalidad agregada o cambiada
    3. Solución de bug

# RECOMENDACIONES
- Alias s para status short: 
```
git config --global alias.s "status --short"
```
- Alias lg para Log: Log, commit, fecha, mensaje, quien hizo el commit y a donde apunta el HEAD
```
git config --global alias.lg "log --graph --abbrev-commit --decorate --format=format:'%C(bold blue)%h%C(reset) - %C(bold green)(%ar)%C(reset) %C(white)%s%C(reset) %C(dim white)- %an%C(reset)%C(bold yellow)%d%C(reset)' --all"
```
- Al nombrar, o eliminar y volver a restaurar el archivo puede hacer que se pierda todo el historial de cambios del archivo, se recomienda usar los comandos de git: mv, rm
- Para versionamiento es recomendable usar tag anotado: `git tag -a`
- No se recomienda usar mas de un stash, es mejor opción usar ramas y es mas seguro
- Se recomienda vaciar la pila de stash si ya se tiene el estado deseado en el proyecto

***
# REFERENCIAS
- [Documentación Oficial](https://git-scm.com/docs)


