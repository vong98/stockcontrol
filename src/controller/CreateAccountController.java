package controller;

import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import au.edu.uts.ap.javafx.Controller;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.StockManager;
import model.LoginAuth;

public class CreateAccountController extends Controller<StockManager> {
	@FXML private Text idText;
	@FXML private TextField nameTextField;
	@FXML private PasswordField passPasswordField;
	@FXML private PasswordField confirmPasswordField;
	@FXML private ToggleButton adminToggle;
	@FXML private Text feedbackText;
	@FXML private Button createButton;
	private LoginAuth loginAuth = new LoginAuth();
	private Connection conn;
	String sqlString = "SELECT user_id FROM users;";
	
	@FXML private void initialize() {
		conn = model.getConnection();
		updateText();
		adminToggle.setText("Yes");
	}
	
	private int getLastID() {
		try {
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rSet = statement.executeQuery(sqlString);
			rSet.last();
			return rSet.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	private void updateText() {
		Integer nextID = getLastID() + 1;
		idText.setText(nextID.toString());
	}
	
	@FXML private void handleCreate() {
		String password = passPasswordField.getText();
		String confirm = confirmPasswordField.getText();
		String name = nameTextField.getText();
		if(password.equals(confirm)) {
			byte[] salt = loginAuth.createSalt();
			try {
				String hexPass = loginAuth.generateHash(password, "SHA-256", salt);
				String sqlString = "INSERT INTO users (user_name, user_password, user_salt, user_admin, user_creation)"
						+ " VALUES (?,?,?,?,?);";
				PreparedStatement statement = conn.prepareStatement(sqlString);
				statement.setString(1, name);
				statement.setString(2, hexPass);
				statement.setBytes(3, salt);
				statement.setBoolean(4, false);
				
				Date date = new Date();
				long time = date.getTime();
				Timestamp ts = new Timestamp(time);
				statement.setTimestamp(5, ts);
				
				if(statement.executeUpdate() == 1 ) {
					feedbackText.setText("Account Created Successfully! ID: " + getLastID() + "\nThis form will reset in 3 seconds.");
					feedbackText.setFill(Color.GREEN);
					PauseTransition pauseTransition = new PauseTransition(Duration.millis(3000));
					pauseTransition.setOnFinished(event -> {
						updateText();
						nameTextField.setText("");
						passPasswordField.clear();
						confirmPasswordField.clear();
						adminToggle.setSelected(false);
						feedbackText.setText("");
					}
					);
					pauseTransition.play();					
				}
			} catch (NoSuchAlgorithmException | SQLException e) {		
				e.printStackTrace();
			}
		} else {
			feedbackText.setText("Passwords don't match");
			feedbackText.setFill(Color.RED);
		}
		
	}
	
}
