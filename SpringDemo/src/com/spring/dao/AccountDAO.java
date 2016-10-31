package com.spring.dao;

import java.sql.SQLException;

import com.spring.model.Account;

/**
 * @author HuanPM Interface of account DAO (Data Access Object)
 */
public interface AccountDAO {

	/**
	 * @param username
	 * @param password
	 * @return account information
	 * @throws SQLException
	 */
	public Account checkLogin(String username, String password) throws SQLException;

	/**
	 * @param account
	 * @return update result (true/false)
	 * @throws SQLException
	 */
	public boolean updateInfo(Account account) throws SQLException;
}
