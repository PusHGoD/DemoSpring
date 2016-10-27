package com.spring.utils;

import java.sql.Connection;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class MySQLUtils implements DBUtils {

	public Connection getConnection() {
		Connection con = null;
		try {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setDatabaseName("springdemo");
			dataSource.setUser("root");
			dataSource.setPassword("@huanvip@");
			dataSource.setServerName("localhost");
			con = dataSource.getConnection();
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}
		return con;
	}
}
