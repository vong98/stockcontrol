package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StockManager {
	private Connection connection;
	private ObservableList<User> users;
	
	public StockManager() {
		users = FXCollections.observableArrayList();
	}
	
	public void setConnection(Connection conn) {
		connection = conn;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public ObservableList<User> getUsers() {
		ResultSet rSet;
		try {
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sqlString = "SELECT * FROM users";
			rSet = statement.executeQuery(sqlString);
			rSet.first();
			int id;
			String name;
			boolean admin;
			Timestamp creation;
			do {
				id = rSet.getInt(1);
				name = rSet.getString(2);
				admin = rSet.getBoolean(5);
				creation = rSet.getTimestamp(6);
				User tempUser = new User(id, name, admin, creation);
				users.add(tempUser);
			} while(rSet.next());		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public void closeConnection() throws SQLException {
		connection.close();
		System.out.println("Connection closed");
	}
	
}
