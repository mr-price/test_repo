package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	public static void writeToCsv(List<ArrayList<String>> list, String filePath) throws Exception {
//		include quotes

		try (PrintWriter out = new PrintWriter(filePath)) {
			for (ArrayList<String> rowData : list) {
				out.println(convertToCSV(rowData));
			}
		} catch (FileNotFoundException e) {
			throw new Exception("File noth found at Path: "+filePath,e);
		}
	}

	public static String generateFile(String nameForFile) throws Exception {
		String cwd = System.getProperty("user.dir");
		String filename = nameForFile + ".csv";
		String fileSeparator = System.getProperty("file.separator");
		File file = new File(cwd + fileSeparator + filename);
		try {
			if (!file.createNewFile()) {
				filename = nameForFile
						+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("_yyyy_MM_dd_HH_mm_ss")).toString()
						+ ".csv";
				file = new File(cwd + fileSeparator + filename);
				if (!file.createNewFile()) {
					throw new Exception("Unable to create File : "+file.getPath());
				}
			}

		} catch (IOException e) {
			throw e;
		}
		return file.getAbsolutePath();

	}

	public static String convertToCSV(List<String> listOfData) {
		StringBuilder csv = new StringBuilder();
		for (String data : listOfData) {
			csv.append(data);
			csv.append(",");
		}
		csv.subSequence(0, csv.length() - 1).toString();
		return csv.subSequence(0, csv.length() - 1).toString();
	}

	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		file.delete();

	}
}
