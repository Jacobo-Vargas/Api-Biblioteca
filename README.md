# Api-Biblioteca
Prueba de creacion de api con java

# Configuracion DB
En el archivo [properties](src/main/resources/application.properties) se puede observar la configuracion a la conexion de la base de datos ya que es alli donde se aloja los datos administrados desde la api, de ser el caso modifique la ruta a la DB proporcione usuario y contraseña para que la conexion sea creada satisfactoriamente.

# Api compilada
El jar [Biblioteca](src/main/java/com/example/Biblioteca/Biblioteca-0.0.1-SNAPSHOT.jar)  es el ejecutable que pone en funcion la api para esto primero debe tener en cuenta el archivo de configuracion y de ser necesario compilar de nuevo el proyecto para que use la nueva oconexion y no arroje errores de inicializacion, esto ya que es de forma remota que se tiene la api, no esta creada la conexion a un DB en un servidor,
tambien se puede[descargar](https://drive.google.com/drive/folders/1rEW0SsZAKPcaFV0ILigsvkKnsExYQxv5) desde una carpeta alojada en Google Drive.

# Ejecucion desde IntelliJ
Para la prueba desde el editor solo basta con correr [BibliotecaApplication](src/main/java/com/example/Biblioteca/BibliotecaApplication.java) esto ya iniciara la api desde springboot.

# Interfaz web
para esto se debe tener la configuracion a la base de datos y la api corriendo una vez esto solo basta con abrir la [interfaz](src/main/resources/static/Libros.html) para empezar a usar la interfaz, cuando se este abriendo desde el ejecutable jar la ruta para ver la interfaz es [Libros.html](http://localhost:8080/Libros.html).

# Pruebas unitarias

estan a lojadas en la siguiente [direccion](src/test/java/com/example/Biblioteca/ApiTest.java)