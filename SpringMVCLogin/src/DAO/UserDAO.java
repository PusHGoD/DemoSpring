package DAO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entities.User;
import utils.MySqlConnection;

public class UserDAO implements Serializable, DAO {

	private User user;

	public User getUser() {
		return user;
	}

	@Override
	public boolean login(String username, String password) {
		try {
			Connection con = MySqlConnection.getConnection();
			String sql = "SELECT * FROM user WHERE username = ? and password = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				int id = rs.getInt(1);
				String fname = rs.getString(4);
				String lname = rs.getString(5);
				user = new User(username, password, fname, lname, id);
				return true;
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public int update(int id, String fname, String lname){
		try {
			Connection con = MySqlConnection.getConnection();
			String sql = "UPDATE user SET firstname = ?, lastname = ? WHERE id = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, fname);
			statement.setString(2, lname);
			statement.setInt(3, id);
			int up = statement.executeUpdate();
			return up;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
