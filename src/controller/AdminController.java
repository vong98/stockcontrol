package controller;

import java.io.IOException;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.StockManager;

public class AdminController extends Controller<StockManager> {
	@FXML private Button viewAccButton;
	@FXML private Button viewItemsButton;
	@FXML private Button viewSuppButton;
	@FXML private Button viewSalesButton;
	
	@FXML private Button createAccButton;
	@FXML private Button createItemButton;
	@FXML private Button createSuppButton;
	
	@FXML
	private void handleViewAcc() {
		try {
			ViewLoader.showStage(model, "/view/ViewAccounts.fxml", "View All Accounts", stage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleViewItems() {
		
	}
	
	@FXML
	private void handleViewSuppliers() {
		
	}
	
	@FXML
	private void handleViewSales() {
		
	}
	
	@FXML
	private void handleCreateAcc() {
		try {
			ViewLoader.showStage(model, "/view/CreateAccount.fxml", "Create New Account", new Stage());
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleCreateItem() {
		
	}
	
	@FXML
	private void handleCreateSupplier() {
		
	}

}
