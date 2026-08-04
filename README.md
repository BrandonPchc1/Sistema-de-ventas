Proyecto final para 2do semestre de ingenieria en sistemas computacionales, realizado el 28/05/2026 

#!!LEER!!
##Recomendado tener java 24.0.2+ para evitar problemas de version

Para ejecutar el programa, descomprime el zip y ejecuta el proyecto en netbeans(Preferiblemente NetBeans IDE 28, que fue la que utilice al crear)

1._ La cuenta admin tiene nombre admin y contraseña 1234

<img width="897" height="647" alt="image" src="https://github.com/user-attachments/assets/ae376f99-272a-40ef-9a36-3ce5b8962340" />

2._ No necesita de la creacion de una base de datos, el archivo utiliza sqllite-jdbc-3.53.00 por lo que ya viene integrado y se creara una carpeta llamada data al ejecutar, alli vendran las bases de datos.

<img width="183" height="52" alt="image" src="https://github.com/user-attachments/assets/67077945-92d3-4552-a5bb-c211825efcba" />

Link del sqllite https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc/3.53.0.0 (descargarlo en .jar) e incorporarlo en librerias(Es posible que al solo tenerlo descargado ya no sea necesario incorporarla).

3._ Al crear un reporte se creara una carpeta reportes con los reportes

<img width="223" height="34" alt="image" src="https://github.com/user-attachments/assets/5963999c-db5a-4c9c-8d67-0dde1be846cd" />

Estas carpetas se crean de forma local en la misma carpeta del ejecutable

4.En caso de hacer click al boton de login y que no pase nada, vuelve a pulsarlo

##Instrucciones:
Puedes ingresar como admin o crear un usuario con la contraseña que quieras, el admin tendra permisos especiales sobre los usuarios normales.

Usuario: En esta seccion puedes ver tu informacion personal como usuario, el admin puede ver todos los usuarios creados y sus contraseñas.

<img width="905" height="644" alt="image" src="https://github.com/user-attachments/assets/bbe58f1a-8fc1-4192-8c64-d89ea3e14831" />

Realizar ventas: En esta seccion puedes realizar las ventas, debes darle a mostrar para poder ver como se vera la tabla en al final.

<img width="901" height="643" alt="image" src="https://github.com/user-attachments/assets/03844fd8-ddad-43ec-bf3d-3a3cd75ab2ea" />

Una vez mostrada se desbloqueara el boton de aceptar, el cual permitira agregar los elementos a la base de datos.
Es obligatorio poner datos en todos por excepcion de Descripcion.

Para modificar un elemento, basta con poner el mismo nombre en realizar ventas, los demas datos se modificaran automaticamente.

Ventas: Aqui podras ver las ventas realizadas por el usuario y crear un reporte en .txt,el admin puede ver todas las ventas realizadas por todos los usuarios.

<img width="909" height="649" alt="image" src="https://github.com/user-attachments/assets/e4529117-d3e4-450e-8f0a-4c8d48819c56" />

