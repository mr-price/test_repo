import os
from datetime import datetime
import csv
import logging
logger = logging.getLogger()

def create_csv_file(data_list, file_path):
    with open(file_path, 'w', newline='') as csvfile:
        logger.info("Creating SQL dump at {}".format(file_path))
        csvwriter = csv.writer(csvfile, delimiter=',', quoting=csv.QUOTE_NONNUMERIC)
        for row_data in data_list:
            csvwriter.writerow(row_data)


def generate_filename(name):
    cwd = os.getcwd()
    file_path = os.path.join(cwd, name + ".csv")
    if os.path.exists(file_path):
        logger.info("{} already exist usning new filename".format(file_path))
        file_path = os.path.join(cwd, name + str(datetime.now().strftime("%d_%m_%y_%H_%M_%S")) + ".csv")
    return file_path
