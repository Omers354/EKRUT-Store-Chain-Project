package gui;

import client.EkrutClientUI;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import logic.Branch;
import logic.Command;
import logic.Customer;
import logic.Order;

/**

The ChooseBranchFrameController class is a JavaFX controller for a Choose Branch Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Choose Branch Frame and to handle events.

@author [Shahar]

@version [4.0]
*/
public class ChooseBranchFrameController extends FrameController {
	
	@FXML
	private ComboBox<String> branch_combo;
	
	@FXML
	private ComboBox<String> order_type;

	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	It send from the client new Command("get_branches_table")
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_branches_table", null));
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Branch branch : EkrutClientUI.client_control.ekrut_client.BranchTable) {
			branch_combo.getItems().add(branch.getBranch_Name());
		}
		order_type.getItems().addAll("Pickup", "Delivery");
		Customer customer = (Customer)EkrutClientUI.client_control.ekrut_client.user;
		status.setText(customer.getCustomer_status().toString());
		status.setStyle("-fx-text-fill: #00FF00");
		
	}
	
	/**

	The getContinue method is responsible for getting the user's order details and sending them to the server for processing.
	It send from the client new Command("order_menu")
	@param event The action event that triggers the method.
	@throws Exception if any error occurs while processing the order.
	*/
	public void getContinue(ActionEvent event) throws Exception{
		try {
			String branch = branch_combo.getValue().toString();
			EkrutClientUI.client_control.ekrut_client.onlineBranch=branch_combo.getValue();
			String type = order_type.getValue().toString();
			if(!branch.equals(null) && !type.equals(null)) {
				
				EkrutClientUI.client_control.ekrut_client.curr_order = new Order(branch_combo.getValue().toString(), order_type.getValue().toString());
				EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("order_menu", null));
			}
		}
		catch(Exception e) {
			message_to_gui.setText("You must enter branch and order type in order to continue");
		}
	}
}