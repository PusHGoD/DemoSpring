package com.spring.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.spring.dao.AccountDAO;
import com.spring.model.Account;
import com.spring.utils.DBUtils;

/**
 * @author HuanPM Implementation of account DAO
 */
@SuppressWarnings("serial")
public class AccountDAOImpl implements AccountDAO, Serializable {

	/**
	 * Autowired database utility
	 */
	@Autowired
	private DBUtils utils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.AccountDAO#checkLogin(java.lang.String,
	 * java.lang.String)
	 */
	public Account checkLogin(String username, String password) throws SQLException {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			// Get connection from database
			con = utils.getConnection();
			// If connection is made, continue operation
			if (con != null) {
				// Set up SQL query
				String sql = "SELECT * FROM account WHERE user_name = ? and password = ?";
				statement = con.prepareStatement(sql);
				statement.setString(1, username);
				statement.setString(2, password);
				// Execute SQL query and get ResultSet
				rs = statement.executeQuery();
				// If the query has result, get account information
				if (rs.next()) {
					int id = rs.getInt("id");
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					Date dob = rs.getDate("date_of_birth");
					Account account = new Account(id, username, password, firstName, lastName, dob);
					// Return account information
					return account;
				}
			}
		} finally {
			// Close connection of ResultSet
			if (rs != null) {
				rs.close();
			}
			// Close connection of PreparedStatement
			if (statement != null) {
				statement.close();
			}
			// Close connection of Connection
			if (con != null) {
				con.close();
			}
		}
		// Return null if none of above conditions are met
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.spring.dao.AccountDAO#updateInfo(com.spring.model.Account)
	 */
	public boolean updateInfo(Account account) throws SQLException {
		Connection con = null;
		PreparedStatement statement = null;
		try {
			// Get connection from database
			con = utils.getConnection();
			// If connection is made, continue operation
			if (con != null) {
				// Get account information from argument
				int id = account.getId();
				String firstName = account.getFirstName();
				String lastName = account.getLastName();
				Date dob = account.getDateOfBirth();
				// Set up SQL update statement
				String sql = "UPDATE account SET first_name = ?, last_name = ?, date_of_birth = ? WHERE id = ?";
				statement = con.prepareStatement(sql);
				statement.setString(1, firstName);
				statement.setString(2, lastName);
				// Convert java.util.Date to java.sql.Date
				java.sql.Date sqlDOB = new java.sql.Date(dob.getTime());
				statement.setDate(3, sqlDOB);
				statement.setInt(4, id);
				// Execute update
				int up = statement.executeUpdate();
				// If there are any rows affected, return true
				if (up > 0) {
					return true;
				}
			}
		} finally {
			// Close connection of PreparedStatement
			if (statement != null) {
				statement.close();
			}
			// Close connection of Connection
			if (con != null) {
				con.close();
			}
		}
		// Return false if none of above conditions are met
		return false;
	}
}
