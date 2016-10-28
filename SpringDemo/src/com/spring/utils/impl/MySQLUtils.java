package com.spring.utils.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.spring.utils.DBUtils;

public class MySQLUtils implements DBUtils {

	public Connection getConnection() {
		Connection con = null;
		try {
			Properties dbConnectionProperties = new Properties();
			InputStream in = getClass().getClassLoader().getResourceAsStream("database.properties");
			dbConnectionProperties.load(in);

			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setDatabaseName(dbConnectionProperties.getProperty("dbName"));
			dataSource.setUser(dbConnectionProperties.getProperty("username"));
			dataSource.setPassword(dbConnectionProperties.getProperty("password"));
			dataSource.setServerName(dbConnectionProperties.getProperty("serverName"));
			con = dataSource.getConnection();
			in.close();
		} catch (Exception e) {
			
		}
		return con;
	}
}
