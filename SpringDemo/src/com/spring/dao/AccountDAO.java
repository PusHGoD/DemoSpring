package com.spring.dao;

import java.sql.SQLException;

import com.spring.model.Account;

public interface AccountDAO {

	public Account checkLogin(String username, String password) throws SQLException;

	public boolean updateInfo(Account account) throws SQLException;
}
