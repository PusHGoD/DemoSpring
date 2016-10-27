package entities;

public class User {
	private String username;
	private String password;
	private String fname;
	private String lname;
	private int id;
	public User() {
	}
	public User(String username, String password, String fname, String lname, int id) {
		super();
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
