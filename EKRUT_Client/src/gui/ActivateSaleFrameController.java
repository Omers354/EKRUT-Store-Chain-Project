package gui;

import java.time.LocalTime;
import java.util.ArrayList;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import logic.Active_Sale;
import logic.Branch;
import logic.Command;
import logic.Inventory;
import logic.MarketingWorker;
import logic.Product;
import logic.Sale;

/**

The ActivateSaleFrameController class is a JavaFX controller for an Activate Sale in EKRUT Client.

The class uses the FXML framework to define the layout of the Activate Sale and to handle events.

@author [Shahar]

@version [4.0]
*/
public class ActivateSaleFrameController extends FrameController{

	@FXML
	private AnchorPane option1_anchor;
	
	@FXML
	private CheckBox IsDeadHours;
	
	@FXML
	private ComboBox<String> branch_combo;
	
	@FXML
	private TextField sale_discription;
	
	@FXML
	private ComboBox<String> product_combo;
	
	private LocalTime startTime;
	
	private LocalTime endTime;
	
	private ArrayList<Sale> sales = new ArrayList<>();
	
	private ArrayList<Branch> branches = new ArrayList<>();
	
	private ArrayList<Product> products = new ArrayList<>();
	
	private float Sale_Discount;
	
	private float Original_Price;
	
	private String Chosen_Product_Id;
	
	private Active_Sale active_sale;
	
	/**
	get from the server the sale table
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	@Override
	public void additionalChanges() {
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		MarketingWorker MarkWrok = (MarketingWorker)EkrutClientUI.client_control.ekrut_client.user;
		status.setText(MarkWrok.getRegion());
		product_combo.setDisable(true);
		sale_discription.setDisable(true);
		IsDeadHours.setDisable(true);
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_sale_table",null));
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sales=EkrutClientUI.client_control.ekrut_client.getSales();
		
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_branches_table",null));
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		branches=EkrutClientUI.client_control.ekrut_client.BranchTable;
		
		for(Branch branch : branches) {
			if(branch.getRegion_Name().equals(MarkWrok.getRegion())) {
				branch_combo.getItems().add(branch.getBranch_Name());
			}
		}
		
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_products",null));
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		products=EkrutClientUI.client_control.ekrut_client.getProductsTable();
		
		for(Sale sale : sales) {
			if(sale.getProduct_ID().equals("All")) {
				product_combo.getItems().add("All");	
			}
		}
		
		for(int i=0;i<products.size();i++) {
			for(int j=0;j<sales.size();j++) {
				if(sales.get(j).getProduct_ID().equals(String.valueOf(products.get(i).getProductID()))) {
					product_combo.getItems().add(products.get(i).getProductName());	
					
				}
			}
		}
	}
	
	/**
    This method is called when the product_combo is pressed.
	Its from the user combobox Product Name
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	public void enableProduct(ActionEvent event) throws Exception{
	
		sale_discription.setText("sale discription:");
		product_combo.setDisable(true);
		sale_discription.setDisable(true);
		IsDeadHours.setDisable(true);
		product_combo.valueProperty().addListener((obs, oldVal, newVal) -> {
			   if(newVal == null) product_combo.setPromptText("Choose Product");
			});
		//product_combo.setValue("Choose Product");
		product_combo.setDisable(false);
		//sale_discription.setText("sale discription:");
	}
	
	/**
    This method is called when the product_combo is pressed.
	Its from the user combobox Product Name and Discount Percentage
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	public void enableSaleDiscription(ActionEvent event) throws Exception{
		sale_discription.setDisable(false);
		IsDeadHours.setDisable(false);
		sale_discription.setText("");
		
		for(Product product: products) {
			if(product.getProductName().equals(product_combo.getValue())) {
				Chosen_Product_Id= String.valueOf(product.getProductID());
				Original_Price = product.getPrice();
				
		   }
		}
		if(product_combo.getValue().equals("All")) {
			Chosen_Product_Id=product_combo.getValue();
			for(Sale sale: sales) {
				if(sale.getProduct_ID().equals("All")) {
					Sale_Discount=sale.getDiscount_Percentage();
			}
		  }
		}
			else {
		for(Sale sale: sales) {
			if(sale.getProduct_ID().equals(Chosen_Product_Id)) {
				Sale_Discount=sale.getDiscount_Percentage();
		  }
		}
	  }
		sale_discription.setText(String.format("%.2f%% discount on %s in %s branch",
				Sale_Discount,product_combo.getValue(),branch_combo.getValue()));
	}
	
	/**

	This method is called when the "Activate" button is pressed.
	It send from the client new Command("update_sale_table") and sale.getSale_ID.
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	public void getActivateBtn(ActionEvent event) throws Exception{
		if(IsDeadHours.isSelected()) {
			for(Sale sale: sales) {
				if(sale.getProduct_ID().equals(product_combo.getValue())) {
					EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("update_sale_table", sale.getSale_ID()));
			  }
			}
		}
		
		
		active_sale= new Active_Sale(branch_combo.getValue(),Chosen_Product_Id,Original_Price,Sale_Discount);
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("activate_sale", active_sale));
		System.out.println("The sale has been activated!");
		message_to_gui.setText("The sale has been activated!");
	}


}
