The bundled code will let you create a comma seaparated dump from mysql database.
JAVA
----
- use mvn install to build the java code 
- using jar with args -tablename <tablename> will let you create the dump.

Python
----------
- Use app.py file to create dump using the python file.

(Please update the config files in both of the modules for the database connection details)



SQL Database info
-----------------
database name dataeaze
```
CREATE TABLE products (
         productID    INT UNSIGNED  NOT NULL AUTO_INCREMENT,
         productCode  CHAR(3)       NOT NULL DEFAULT '',
         name         VARCHAR(30)   NOT NULL DEFAULT '',
         quantity     INT UNSIGNED  NOT NULL DEFAULT 0,
         PRIMARY KEY  (productID)
       );
```
       
1001,PEN,PenRed,5000,
1002,PEN,PenBlue,8000,
1003,PEN,PenBlack,2000,
1004,PEC,Pencil2B,10000,
1005,PEC,Pencil2H,8000,
1006,PEC,PencilHB,0,
