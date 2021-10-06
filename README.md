# SWM Batch Loader
Aplicativo para la actualización de las tablas geométricas de IMGIS (Terminal Enclosure, Building & Hub) y FACTUNIF (Xygo Address). La herramienta consiste en la extracción de la información de tablas no geométricas (Id., la longitúd y la latitúd), y generar registros en sus homólogos geométricos.

### Requisitos
1. Java 8 o superior.

2. Apache Maven 3

3. En el esquema _IMGIS_, debe existir las tablas :
    - __MIT_TERMINAL_ENCLOSURE__
    - __MIT_HUB__
    - __BUILDING__
    - __GEO_MIT_TERMINAL_ENCLOSURE__
    - __GEO_BUILDING__
    - __GEO_MIT_HUB__

4. En el esquema _FACTUNIF_,debe existir las tablas:
    - __FU_XYGO__
    - __FU_GEO_XYGO__

### Instalación
##### Clonar el proyecto desde Github.
```bash
git clone https://github.com/RinoVargas/smw-batch-loader.git
cd smw-batch-loader
```
<br>

##### Construir el proyecto
```bash
mvn clean verify
```
_Nota_: Se generará un directorio con el nombre de `dist/`.

<br>

##### Mover el aplicativo generado
```shell
mv dist/ smw-batch-loader
sudo mv smw-batch-loader/ /opt
cd /opt
sudo chown -R username:username smw-batch-loader/
```
_Nota:_ En caso tenga que cambiar de permisos al directorio generado deberá hacerlo

<br>

##### Definir las siguientes variables del script de inicialización
El script de inicialización es `smw-batch-loader.sh`, y  se encuentra dentro del directorio generado. Se recomienda solo definir las siguientes variables

```bash
APP_HOME="/opt/smw-batch-loader"  

JAVA_HOME="/opt/oracle_jdk1.8.0_291/jre"  

JAVA_OPTS=""  
```
_Donde:_

- `APP_HOME` : Es la ruta de la ubicación del directorio de instalación.
- `JAVA_HOME` : Ruta donde se aloja el home de JAVA.
- `JAVA_OPTS` : Argumentos adicionales para la JVM.

_El valor de las variables puede cambiar dependiendo del entorno de instalación._

<br>

### Detalles de la configuración
La configuración del aplicativo se define en el archivo `smw-batch-loader.properties`

##### Definir las credenciales de Acceso al esquema IMGIS
```properties
# IMGIS Database Credentials  
imgis.db.url=jdbc:oracle:thin:@localhost:1521:orcl  
imgis.db.username=development  
imgis.db.password=development123456  
```

<br>

##### Definir las credenciales de Acceso al esquema FACTUNIF
```properties
# FACTUNIF Database Credentials  
factunif.db.url=jdbc:oracle:thin:@localhost:1521:orcl  
factunif.db.username=development  
factunif.db.password=development123456  
```

<br>

##### Configurar carga para cada entidad, respectivamente.
Las entidades son _hub_, _building_, _terminalEnclosure_ y _xygoAddress_.
```properties
# Batch Loader Properties  
batch.loader.*.enabled=true  
batch.loader.*.chunksize=2000  
batch.loader.*.erase-geometry-table=true  
```
_Donde:_

- `batch.loader.*.enabled`: Puede ser _true_ o _false_. En caso valor sea _false_, se ignorará el proceso carga de la correspondiente entidad, como también se ignorarán las propiedades _batch.loader.*.chunksize_ y _batch.loader.*.erase-geometry-table_.

- `batch.loader.*.chunksize`: Define la cantidad de registros que se procesarán por cada lote. Debe ser un valor numérico-entero mayor a 0.

- `batch.loader.*.erase-geometry-table`: Puede ser _true_ o _false_. En caso sea _true_, da la posibilidad de eliminar los registros existentes en la tabla geométrica de la respectiva entidad, antes de iniciar con la carga.

<br>

### Como usar
##### Asociar el aplicativo a un cron job

- Definir en el archivo de propiedades `smw-batch-loader.properties` la ruta __absoluta__ del directorio donde se generarán los logs
```properties
app.logging.path=/opt/smw-batch-loader/logs
```


- Ejecutar el comando `crontab -e` en la terminal y definir un nuevo cron job.
```bash
# SMW Batch Loader
0 6 * * * /bin/bash /opt/smw-batch-loader/smw-batch-loader.sh
```

_Nota:_
En este caso se está ejecutando el aplicativo todos los días a las 6:00 am, sin embargo puede definir el periodo de tiempo que requiera.

- Guardar cambios y salir.