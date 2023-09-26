package gui;

import java.time.LocalTime;
import java.util.ArrayList;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import logic.Command;
import logic.Inventory;
import logic.Product;
import logic.Sale;

/**

The SetSaleTemplateFrameController class is a JavaFX controller for a Set Sale Template Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Set Sale Template Screen and to handle events.

@author [Shahar]

@version [4.0]
*/
public class SetSaleTemplateFrameController extends FrameController{

	@FXML
	private AnchorPane option1_anchor;
	
	@FXML
	private ComboBox<String> product_combo;
	
	@FXML
	private TextField percentage;
	
	@FXML
	private Label message_to_gui;
	
	private Sale sale;
	
	ArrayList<Product> products;
	
	
	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	It send from the client new Command("get_products")
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_products",null));

		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		products=EkrutClientUI.client_control.ekrut_client.getProductsTable();
		if(products.size()>0)
			product_combo.getItems().add("Include All Products");
		for(Product product : products) {
			product_combo.getItems().add(product.getProductName());
		}
	}
	
	/**

	This method is used to create a new sale template.
	It takes the product selected in the combo box and the discount percentage provided by the user.
	It validates the discount percentage by calling the CheckValidDiscount method and creates a new sale template if it's valid.
	If the user selected "Include All Products" option, the sale template will be created for all products.
	The method also sends a message to the client_control to create the sale template and updates the message_to_gui with the result of the operation.
	@param event the event that triggered the method call.
	*/
	public void getCreateBtn(ActionEvent event) {
		Product prod = null;
		String chosenProduct = product_combo.getValue();
		if(CheckValidDiscount(percentage.getText())) {
		float Percentage =Float.parseFloat(percentage.getText());
		if(!chosenProduct.equals("Include All Products")) {
		for(Product product : products) {
			if(product.getProductName().equals(product_combo.getValue())) {
				sale = new Sale(0,String.valueOf(product.getProductID()),null,null,product.getPrice(),Percentage);
				prod=product;
			}
		}
	}
	  else {
		  sale = new Sale(0,"All",null,null,(float)0,Percentage);
		}
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("create_sale_template",sale));
		if(!chosenProduct.equals("Include All Products")) 
		message_to_gui.setText(String.format("Created a new sale: %s in %.2f%% ",prod.getProductName(),sale.getDiscount_Percentage()));
		else
			message_to_gui.setText(String.format("Created a new sale: %s in %.2f%% ","All",sale.getDiscount_Percentage()));
	  }
		else
			message_to_gui.setText("You must enter a positive number! (0-100)");
	}
	
	/**

	This method is to check whether the discount entered is valid or not.
	@param discount The discount entered in string format.
	@return boolean value indicating whether the discount is valid or not.
	*/
	public boolean CheckValidDiscount(String discount) {
		float dis;
		try {
			dis=Float.parseFloat(discount);
		}catch(NumberFormatException e) {
			return false;
		}
		if(dis>100 || dis<0) {
			return false;
		}
		else
			return true;
	}
	
}


