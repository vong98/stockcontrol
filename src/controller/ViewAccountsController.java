package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import model.StockManager;
import model.User;

public class ViewAccountsController extends Controller<StockManager>{
	@FXML private TableView<User> accountsTableView;
	
	@FXML public void initialize() {
		accountsTableView.setItems(model.getUsers());
	}
}
