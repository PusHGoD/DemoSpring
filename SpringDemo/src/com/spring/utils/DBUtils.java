package com.spring.utils;

import java.sql.Connection;

/**
 * @author HuanPM Interface of database utility
 */
public interface DBUtils {

	/**
	 * @return database connection object
	 */
	public Connection getConnection();
}
