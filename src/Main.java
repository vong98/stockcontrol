import au.edu.uts.ap.javafx.*;
import javafx.application.Application;
import javafx.stage.Stage;
import model.*;

public class Main extends Application {
	
	private static StockManager stockManager;

	public static void main(String[] args) {
		launch(args);
		
		// Application Close Code
		try {
			stockManager.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage stage) throws Exception{
		stockManager = new StockManager();
		ViewLoader.showStage(stockManager, "/view/Login.fxml", "StockerManager - Login", stage);
	}

}
