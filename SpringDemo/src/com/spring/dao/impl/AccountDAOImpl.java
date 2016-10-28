package com.spring.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.spring.dao.AccountDAO;
import com.spring.model.Account;
import com.spring.utils.DBUtils;

@SuppressWarnings("serial")
public class AccountDAOImpl implements AccountDAO, Serializable {

	@Autowired
	private DBUtils utils;

	public Account checkLogin(String username, String password) throws SQLException {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			con = utils.getConnection();
			String sql = "SELECT * FROM account WHERE user_name = ? and password = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			rs = statement.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date dob = rs.getDate("date_of_birth");
				try {
					dob = sdf.parse(sdf.format(dob));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Account account = new Account(id, username, password, firstName, lastName, dob);
				return account;
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return null;
	}

	public boolean updateInfo(Account account) throws SQLException {
		Connection con = null;
		PreparedStatement statement = null;
		try {
			con = utils.getConnection();
			int id = account.getId();
			String firstName = account.getFirstName();
			String lastName = account.getLastName();
			Date dob = account.getDateOfBirth();
			String sql = "UPDATE account SET first_name = ?, last_name = ?, date_of_birth = ? WHERE id = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			java.sql.Date sqlDOB = new java.sql.Date(dob.getTime());
			statement.setDate(3, sqlDOB);
			statement.setInt(4, id);
			int up = statement.executeUpdate();
			if (up > 0) {
				return true;
			}
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return false;
	}
}
