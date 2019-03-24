package controller;

import java.sql.Connection;
import java.sql.DriverManager;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.StockManager;

public class InitLoaderController extends Controller<StockManager> {
	@FXML private Text progressTxt;
	@FXML private Text errorTxt;
	private Connection connection;
	private final String url = "jdbc:postgresql://localhost/postgres";
	
	public void initialize() {
		stage.initStyle(StageStyle.UNDECORATED);
		Platform.setImplicitExit(false);
		
		try {
			progressTxt.setText("Establishing Connection with Database...");
			connection = DriverManager.getConnection(url, "postgres", "tarzan");
			if(connection.isValid(5)) {
				model.setConnection(connection);
				progressTxt.setText("Connection established.");
				Thread.sleep(2000);
				progressTxt.setText("Redirecting to Login Page.");
				Thread.sleep(2000);
				ViewLoader.showStage(model, "/view/Login.fxml", "Login", new Stage());
				stage.hide();
			}
		} catch (Exception e) {
			progressTxt.setText("Connection Unsuccessful");
			errorTxt.setText(e.toString());			
		}
	}
}
