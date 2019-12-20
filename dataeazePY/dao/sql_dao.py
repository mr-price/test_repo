import mysql.connector
import logging
logger = logging.getLogger() 

class sql_dao(object):
    connection_url = ''
    port = 0
    username = ''
    password = ''
    database = ''
    mydb = None

    def __init__(self, connection_url, port, username, password, database):
        self.connection_url = connection_url
        self.port = port
        self.username = username
        self.password = password
        self.database = database
        
        self.mydb = mysql.connector.connect(
            host=self.connection_url,
            user=self.username,
            passwd=self.password,
            database=self.database,
            port=self.port
            )
    
    def fetch_table_content(self, tablename):
        if not self.check_if_table_exist(tablename):
            logger.info("No Table with this name found. Try again with a vaild tablename")
            raise Exception("No Table with this name found. Try again with a vaild tablename")
        query = "select * from {} ;".format(tablename)
        logger.info("Dump for tabele : {} requested".format(tablename))
        mycursor = self.mydb.cursor()
        mycursor.execute(query)
        myresult = mycursor.fetchall()
        return myresult

    def check_if_table_exist(self, tablename):
        mycursor = self.mydb.cursor()
        mycursor.execute("SHOW TABLES")
        for tablenames in mycursor:
            if tablename in tablenames:
                return True
        return False


# if __name__ == "__main__":
#     
