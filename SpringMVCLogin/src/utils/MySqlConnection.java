package utils;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;

public class MySqlConnection {
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		 
	     String connectionURL = "jdbc:mysql://localhost:3306/springdemo?autoReconnect=true&useSSL=false";
	     
	     Connection conn = DriverManager.getConnection(connectionURL, "root",
	             "duyquang12");
	     return conn;
	}
}
