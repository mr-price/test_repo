/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlDAO {
	String db_connection_ip;
	String db_username;
	String db_password;

	MysqlDAO() {
		this.db_connection_ip = "mysql://localhost:3306/dataeaze2";
		this.db_username = "root";
		this.db_password = "";

	}

	public MysqlDAO(String connection_ip, String username, String password) {
		this.db_connection_ip = connection_ip;
		this.db_username = username;
		this.db_password = password;
	}

	public List<ArrayList<String>> fetch_from_DB(String tablename) throws Exception {
		List<ArrayList<String>> stringfiedRows = null;
		try (Connection db_conn = DriverManager.getConnection("jdbc:" + this.db_connection_ip, this.db_username,
				this.db_password)) {

			String select_statement = "select * from " + tablename + " ;";
			try (PreparedStatement ps = db_conn.prepareStatement(select_statement)) {
				ResultSet resultSet = ps.executeQuery();
				java.sql.ResultSetMetaData rsd = resultSet.getMetaData();
				int column_length = rsd.getColumnCount();
				stringfiedRows = new ArrayList<ArrayList<String>>();
				if (column_length > 0) {
					while (resultSet.next()) {
						ArrayList<String> rowText = new ArrayList<String>();
						for (int i = 1; i <= column_length; i++) {

							rowText.add(resultSet.getObject(i).toString());
						}
						stringfiedRows.add(rowText);
					}

				}
				resultSet.close();
				return stringfiedRows;
			}

		} catch (SQLException e) {
			throw new Exception("SQL State: " + e.getSQLState() + "\n" + e.getMessage());
		}
	}

}
