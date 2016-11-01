package com.spring.utils.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.spring.utils.DBUtils;

/**
 * @author HuanPM MySQL DB implementation of DBUtils
 */
public class MySQLUtils implements DBUtils {

	/**
	 * Log4j logger
	 */
	private static Logger logger = Logger.getLogger(MySQLUtils.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.utils.DBUtils#getConnection()
	 */
	public Connection getConnection() {
		Connection con = null;
		try {
			// Get database from properties file
			Properties dbConnectionProperties = new Properties();
			InputStream in = getClass().getClassLoader().getResourceAsStream("database.properties");
			dbConnectionProperties.load(in);
			in.close();

			// MySQL data source configuration
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setDatabaseName(dbConnectionProperties.getProperty("dbName"));
			dataSource.setUser(dbConnectionProperties.getProperty("username"));
			dataSource.setPassword(dbConnectionProperties.getProperty("password"));
			dataSource.setServerName(dbConnectionProperties.getProperty("serverName"));

			// Get connection from data source
			con = dataSource.getConnection();

		} catch (Exception e) {
			logger.error("Cannot connect to MySQL DB !");
		}
		// Return database connection
		return con;
	}
}
