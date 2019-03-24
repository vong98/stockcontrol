package controller;
import model.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class LoginController extends Controller<StockManager>{
	@FXML private TextField userIDTField;
	@FXML private PasswordField passwordPField;
	
	@FXML private Text resultsText;
	@FXML private Text saltText;
	
	@FXML private Button loginButton;
	private LoginAuth loginAuth = new LoginAuth();
	
	private final int INVALID = -1;
	private final int ADMIN = 1;
	
	private Connection tempConnection;
	
	private final String url = "jdbc:postgresql://localhost/postgres";
	
	public void initialize() {
		try {
			tempConnection = DriverManager.getConnection(url, "postgres", "tarzan");
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
	
	public void handleLogin() {
		loginAuth.setConnection(tempConnection);
		String userID = userIDTField.getText();
		String password = passwordPField.getText();		
		int credentials = loginAuth.credentialsValidAndType(userID, password); 
		
		if(credentials != INVALID) {
			resultsText.setText("CREDENTIALS VALID");
			resultsText.setFill(javafx.scene.paint.Color.GREEN);
			model.setConnection(tempConnection);
			PauseTransition pauseTransition = new PauseTransition(Duration.millis(800));
			if(credentials == ADMIN) {
				pauseTransition.setOnFinished(event -> 
					{
						try {
							ViewLoader.showStage(model, "/view/Admin.fxml", "Administrator View", stage);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				);
				pauseTransition.play();
			} else {
				pauseTransition.setOnFinished(event -> 
				{
					try {
						ViewLoader.showStage(model, "/view/StaffDashboard.fxml", "Stock Manager", stage);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				pauseTransition.play();
			}
		} else {
			resultsText.setText("Incorrect username and/or password");
			resultsText.setFill(javafx.scene.paint.Color.RED);
		}
	}	
}
