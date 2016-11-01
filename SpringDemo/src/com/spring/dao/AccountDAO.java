package com.spring.dao;

import com.spring.entity.Account;

/**
 * @author HuanPM Interface of account DAO (Data Access Object)
 */
public interface AccountDAO {

	/**
	 * @param username
	 * @param password
	 * @return account information
	 */
	public Account checkLogin(String username, String password);

	/**
	 * @param account
	 * @return update result (true/false)
	 */
	public boolean updateInfo(Account account);
}
