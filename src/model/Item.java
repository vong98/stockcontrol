package model;

import java.sql.ResultSet;

public class Item {
	private int UPC;
	private String name;
	private double price;
	private int stock;
	
	public Item(ResultSet resultSet) {
		try {
			UPC = resultSet.getInt(1);
			name = resultSet.getString(2);
			price = resultSet.getDouble(3);
			stock = resultSet.getInt(4);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getUPC() {
		return UPC;
	}
	
	public String getName() {
		return name;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getStock() {
		return stock;
	}

}
