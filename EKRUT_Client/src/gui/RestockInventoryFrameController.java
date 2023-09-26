package gui;

import java.util.ArrayList;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import logic.Command;
import logic.Inventory;
import logic.Restock;
import logic.User;

/**

The RestockInventoryFrameController class is a JavaFX controller for a Restock Inventory Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Restock Inventory Screen and to handle events.

@author [Shahar]

@version [4.0]
*/
public class RestockInventoryFrameController extends FrameController {

	@FXML
	private ComboBox branch_combo;

	@FXML
	private ComboBox product_combo;

	@FXML
	private Text quantity_to_restock;

	private User user;
	
	private ArrayList<Restock> arr_restock_table;
	
	private boolean isUpdateButtonEnabled;
	
	private String chosenBranch;
	
	private String chosenProduct;
	
	private Restock restock;

	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	It send from the client new Command("get_restock_table")
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		user = EkrutClientUI.client_control.ekrut_client.user;
		full_name.setText(user.getFirstName() + " " + user.getLastName());
		product_combo.setDisable(true);
		isUpdateButtonEnabled = false;
		EkrutClientUI.client_control.ekrut_client
				.handleMessageFromClientUI(new Command("get_restock_table", null));
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		arr_restock_table = EkrutClientUI.client_control.ekrut_client.arr_restock_table;
		for (Restock restock : arr_restock_table) {
			if(restock.getStatus().toString().equals("Pending")) {
				branch_combo.getItems().add(restock.getBranch());
			}
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
		EkrutClientUI.home_frame.start(primaryStage, "/gui/OperationWorkerMainScreenFrame.fxml");
	}
	
	/**
    This method is called when the branch_combo is pressed.
	Its from the user product_combo
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	public void enableProduct(ActionEvent event) {
		message_to_gui.setText("");
		quantity_to_restock.setText("");
		isUpdateButtonEnabled = false;
		product_combo.setDisable(false);
		chosenBranch = branch_combo.getValue().toString();
		for (Restock restock : arr_restock_table) {
			if (chosenBranch.equals(restock.getBranch())) {
				product_combo.getItems().add(restock.getProduct_name());
			}
		}
	}

	/**
    This method is called when the product_combo is pressed.
	Its from the user arr_restock_table
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	public void enableUpdate(ActionEvent event) {
		message_to_gui.setText("");
		isUpdateButtonEnabled = true;
		if (product_combo.getItems().size() > 0) {
		chosenProduct = product_combo.getValue().toString();
			for(Restock restock : arr_restock_table) {
				if(restock.getBranch().equals(chosenBranch) && restock.getProduct_name().equals(chosenProduct)) {
					this.restock = restock;
					quantity_to_restock.setText("2. The amount of inventory required for restock product " + chosenProduct + " in " + chosenBranch + " is: " + this.restock.getDesired_quantity());
					break;
				}
			}
		}
	}

	/**

	This method is responsible for updating the stock of a selected product in a selected branch.
	It first checks if the update button is enabled, and if it is, it sends a command to the client to set the new stock.
	If the update button is not enabled, it sets the message_to_gui text to inform the user that they must choose a branch and product first.
	@param event - the ActionEvent that triggered this method.
	*/
	public void getUpdateBtn(ActionEvent event) {
		if(isUpdateButtonEnabled) {
			EkrutClientUI.client_control.ekrut_client
			.handleMessageFromClientUI(new Command("set_new_stock", restock));
		}
		else {
			message_to_gui.setText("You must choose branch and product in order to update");
		}
	}
}