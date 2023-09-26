package gui;

import java.util.ArrayList;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.Command;
import logic.Inventory;
import logic.RegionalManager;

/**

The RequestRestockFrameController class is a JavaFX controller for a Request Restock Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Request Restock Screen and to handle events.

@author [Shahar]

@version [4.0]
*/
public class RequestRestockFrameController extends FrameController {

	@FXML
	private ComboBox branch_combo;

	@FXML
	private ComboBox product_combo;

	@FXML
	private TextField quantity_text;
	
	@FXML
	private Label max_threshold;
	
	private ArrayList<ArrayList<Inventory>> inventory;
	
	private int maxThreshold;
	
	private Inventory chosenProduct;
	
	private String chosenBranch;

	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	@throws Exception if any error occurs while processing the order.
	*/
	
	@Override
	public void additionalChanges() {
		RegionalManager regMan = (RegionalManager) EkrutClientUI.client_control.ekrut_client.user;
		full_name.setText(regMan.getFirstName() + " " + regMan.getLastName());
		status.setText(regMan.getRegion());
		product_combo.setDisable(true);
		quantity_text.setDisable(true);
		maxThreshold = 0;
		EkrutClientUI.client_control.ekrut_client
				.handleMessageFromClientUI(new Command("get_threshold_points", regMan.getRegion()));
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.inventory = EkrutClientUI.client_control.ekrut_client.threshold_table;
		for (ArrayList<Inventory> branch : inventory) {
			branch_combo.getItems().add(branch.get(0).getBranch());
		}
	}

	/**

	The getHomeBtn method is used to navigate back to the home frame.
	@param event the ActionEvent that triggers the method
	@throws Exception when an error occurs while navigating to the home frame or exiting the application
	*/
	@Override
	public void getHomeBtn(ActionEvent event) throws Exception {
		EkrutClientUI.back_frames.clear();
		EkrutClientUI.home_frame.start(primaryStage, "/gui/RegionalManagerMainScreenFrame.fxml");
	}

	/**
    This method is called when the branch_combo is pressed.
	Its from the user product_combo
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	public void enableProduct(ActionEvent event) {
		message_to_gui.setText("");
		boolean isIncludeAllProductsAlreadyWritten = false;
		maxThreshold = 0;
		product_combo.getItems().clear();
		quantity_text.setDisable(true);
		quantity_text.setText("");
		for (ArrayList<Inventory> branch : inventory) {
			if (branch_combo.getValue().equals(branch.get(0).getBranch())) {
				product_combo.setDisable(false);
				for (Inventory in : branch) {
					if (in.getProduct_quantity() <= in.getThreshold_point()) {
						if(!isIncludeAllProductsAlreadyWritten) {
							product_combo.getItems().add("Include All Products");
							isIncludeAllProductsAlreadyWritten = true;
						}
						if (in.getThreshold_point() >= maxThreshold) {
							maxThreshold = in.getThreshold_point();
						}
						product_combo.getItems().add(in.getProduct_name());
					}
				}
			}
		}
	}

	/**
    This method is called when the product_combo is pressed.
	Its from the user maxThreshold
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	public void enableQuantity(ActionEvent event) {
		message_to_gui.setText("");
		quantity_text.setDisable(false);
		if (product_combo.getItems().size() > 0) {
			if(product_combo.getValue().toString().equals("Include All Products")) {
				max_threshold.setText("(Min quantity for all products: " + (maxThreshold + 1) + ")");
				chosenBranch = branch_combo.getValue().toString();
			}
			else {
				for (ArrayList<Inventory> branch : inventory) {
					if (branch_combo.getValue().equals(branch.get(0).getBranch())) {
						for (Inventory in : branch) {
							if (in.getProduct_name().equals(product_combo.getValue().toString())) {
								max_threshold.setText("(Min quantity for this product: " + (in.getThreshold_point() + 1) + ")");
								chosenProduct = in;
							}
						}
					}
				}
			}
		}
		else {
			max_threshold.setText("");
		}
	}

	/**

	This method is called when the "Send" button is pressed.
	It send from the client new Command("set_request_restock") and arr_request_restock.
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	public void getSendBtn() {
		message_to_gui.setText("");
		if(!quantity_text.isDisable()) {
			boolean isNumber = true;
			int quantity_to_restock;
			try {
				quantity_to_restock = Integer.parseInt(quantity_text.getText());
			}
			catch (Throwable t) {
				isNumber = false;
			}
			if (!isNumber) {
				System.out.println("Please enter a whole number quantity");
				message_to_gui.setText("Please enter a whole number quantity");
			}
			else {
				quantity_to_restock = Integer.parseInt(quantity_text.getText());
				if (quantity_to_restock < 0) {
					System.out.println("Please enter a whole positive number quantity");
					message_to_gui.setText("Please enter a whole positive number quantity");
				}
				else {
					ArrayList<String> arr_request_restock = new ArrayList<String>();
					if(product_combo.getValue().toString().equals("Include All Products")) {
						if(quantity_to_restock <= maxThreshold) {
							message_to_gui.setText("Quantity number must be higher than the max threshold point");
						}
						else {
							arr_request_restock.add(chosenBranch);
							arr_request_restock.add("All");
							arr_request_restock.add(quantity_text.getText());
							EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("set_request_restock", arr_request_restock));
						}
					}
					else {
						if(quantity_to_restock <= chosenProduct.getThreshold_point()) {
							message_to_gui.setText("Quantity number must be higher than the max threshold point");
						}
						else {
							arr_request_restock.add(chosenProduct.getBranch());
							arr_request_restock.add(chosenProduct.getProduct_id());
							arr_request_restock.add(quantity_text.getText());
							EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("set_request_restock", arr_request_restock));
						}
					}
				}
			}
		}
		else {
			message_to_gui.setText("You must choose branch and product in order to send");
		}
	}
}