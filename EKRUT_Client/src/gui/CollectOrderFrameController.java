package gui;

import java.util.ArrayList;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.Command;
import logic.Customer;
import logic.Order;

/**

The CollectOrderFrameController class is a JavaFX controller for a Collect Order Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Collect Order Frame and to handle events.

@author [Shahar]

@version [4.0]
*/
public class CollectOrderFrameController extends FrameController{
	
	@FXML
	public Label CollectOrders;
	
	@FXML
	public TextField PickUp_Code;

	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		Customer customer = (Customer)EkrutClientUI.client_control.ekrut_client.user;
		status.setText(customer.getCustomer_status().toString());
		status.setStyle("-fx-text-fill: #00FF00");
	}
	
	/**

	The getContinue method is responsible for getting the user's order details and sending them to the server for processing.
	It send from the client new Command("change_collect_order_status") and arr 
	@param event The action event that triggers the method.
	@throws Exception if any error occurs while processing the order.
	*/
	public void getContinue(ActionEvent event) throws Exception{
		String pickup_code = PickUp_Code.getText();
		Customer customer = (Customer)EkrutClientUI.client_control.ekrut_client.user;
		ArrayList<Object> arr = new ArrayList<Object>();
		arr.add(customer);
		arr.add(pickup_code);
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("change_collect_order_status", arr));
	}
}