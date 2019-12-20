package api;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import dao.MysqlDAO;
import utils.AppProperties;
import utils.FileUtils;

public class Cli {
	private final static Logger LOGGER = Logger.getLogger(Cli.class.getName());
	Properties props = null;
	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
	}

	public Cli() throws SecurityException, IOException, Exception {
		this.props = AppProperties.getProperties();
		LOGGER.setLevel(Level.FINEST);
		FileHandler logfilehandler = new FileHandler(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + this.props.getProperty("logging.filename"), 20000, 10);

		logfilehandler.setFormatter(new SimpleFormatter());
		LOGGER.addHandler(logfilehandler);

	}

	public static void main(String[] args) {

		String tablename = null;
		String filePath = null;
		try {
			Cli cli = new Cli();
			tablename = cli.validateTableName(cli.parseArgs(args));
			filePath = FileUtils.generateFile(tablename);
			MysqlDAO dao = new MysqlDAO(cli.props.getProperty("db.connectionUrl"), cli.props.getProperty("db.username"),
					cli.props.getProperty("db.password"));
			FileUtils.writeToCsv(dao.fetch_from_DB(tablename), filePath);
		} catch (Exception e) {
			if (filePath != null) {
				FileUtils.deleteFile(filePath);
			}

			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}

	}

	private String parseArgs(String[] args) {
		String tablename = null;
		if (args.length == 1 && "-help".compareToIgnoreCase(args[0]) == 0) {
			System.out.print("Please use the code with : \n-table [tablename]\nexample : -table products");
			System.exit(1);
		} else if (args.length == 2 && "-tablename".compareToIgnoreCase(args[0]) == 0 && !args[1].isBlank()) {
			tablename = args[1];
		} else {
			System.out.println("please provide a vaild argument\nTry -help for supported arguments");
			System.exit(1);
		}
		return tablename;
	}

	private String validateTableName(String tablename) throws Exception {
		if (tablename.contains(" ")) {
			System.out.println("Please enter a valid tablename");
			throw new Exception("Invalid Tablename provided");
		}
		return tablename;
	}
}
