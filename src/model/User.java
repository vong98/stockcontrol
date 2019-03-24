package model;

import java.sql.Timestamp;

public class User {

	private int id;
	private String name;
	private boolean admin;
	private Timestamp creationTimestamp;
	
	public User(int idParam, String nameParam,boolean adminParam, Timestamp creationParam) {
		id = idParam;
		name = nameParam;
		admin = adminParam;
		creationTimestamp = creationParam;	
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getAdmin() {
		return admin;
	}
	
	public Timestamp getCreationTimestamp() {
		return creationTimestamp;
	}
}
