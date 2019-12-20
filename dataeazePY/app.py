import argparse 
import os

import yaml

from  dao.sql_dao  import sql_dao
from utils import file_utils

import logging 
from logging.handlers import RotatingFileHandler
rotatingFilehandler = RotatingFileHandler("app.log", maxBytes=20000, backupCount=10)
rotatingFilehandler.setFormatter(logging.Formatter('%(asctime)s %(message)s'))
logger = logging.getLogger() 
logger.addHandler(rotatingFilehandler)
logger.setLevel(logging.DEBUG) 


def app():
    parser = argparse.ArgumentParser(description="A sql sump utility") 
    parser.add_argument("-t", "--tablename" , type=str, nargs=1,
                        metavar="table_name",
                     help="name of the sql table to create csv dump") 
  
    args = parser.parse_args()
    try:
        config = read_configs()
        if args.tablename != None and validate_tablename(args.tablename[0]):
            create_dump(args.tablename[0],config)
    except IOError :
        print("Encountered an error while accessing file. Please refer logs for more details")
        logger.exception("Error while trying to access the file")
    except Exception :
        print("Encountered an error. Please try again")
        logger.exception("Got following exception")
        

        
def create_dump(tablename,config):
    dao = sql_dao(config["connectionUrl"], config["port"], config["username"], config["password"], config["database"])
    file_utils.create_csv_file(dao.fetch_table_content(tablename), file_utils.generate_filename(tablename))
    
    
def read_configs():
    with open(os.path.join(os.getcwd(), "config.yml"), 'r') as ymlfile:
        config = yaml.load(ymlfile, yaml.Loader)
        return config

def validate_tablename(tablename):
    if ',' in tablename or ';' in tablename:
        print("Please enter a vaild Tablename")
        logger.error("Invaid Tablename attemped".format(tablename))
    return True

if __name__ == '__main__':
    app()
    
