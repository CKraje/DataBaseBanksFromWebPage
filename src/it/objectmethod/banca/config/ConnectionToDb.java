package it.objectmethod.banca.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionToDb {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/banca";
	static final String USER = "root";
	static final String PASS = "root";
	public static Connection getConnection() {
		Connection conn=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL+"?useSSL=false",USER,PASS);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
