package gui;

import java.util.ArrayList;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import logic.Command;
import logic.Customer;
import logic.User;

/**

The SetSubscriberFrameController class is a JavaFX controller for a Set Subscriber Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Set Subscriber Screen and to handle events.

@author [Shahar]

@version [4.0]
*/
public class SetSubscriberFrameController extends FrameController {

	@FXML
	private AnchorPane customer_anchor;
	
	@FXML
	private AnchorPane information_anchor;
	
	@FXML
	private ComboBox customer_combo;
	
	@FXML
	private Label cus_id_label;
	
	@FXML
	private Label cus_full_name_label;
	
	@FXML
	private Label cus_email_label;
	
	@FXML
	private Label cus_telephone_label;
	
	@FXML
	private RadioButton set_subscriber_radio;
	
	@FXML
	private RadioButton not_subscriber_radio;
	
	private ArrayList<Customer> all_customers;
	
	private boolean isCustomerChosen;
	
	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	It send from the client new Command("get_all_customers")
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		User user = EkrutClientUI.client_control.ekrut_client.user;
		full_name.setText(user.getFirstName() + " " + user.getLastName());
		information_anchor.setDisable(true);
		isCustomerChosen = false;
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_all_customers", null));
		try {
			Thread.sleep(150);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		all_customers = EkrutClientUI.client_control.ekrut_client.all_customers;
		if(all_customers != null) {
			for(Customer customer : all_customers) {
				customer_combo.getItems().add(customer.getUsername());
			}
		}
	}
	
	/**

	The getHomeBtn method is used to navigate back to the home frame.
	@param event the ActionEvent that triggers the method
	@throws Exception when an error occurs while navigating to the home frame or exiting the application
	*/
	@Override
	public void getHomeBtn(ActionEvent event) throws Exception{
		EkrutClientUI.back_frames.clear();
		EkrutClientUI.home_frame.start(primaryStage, "/gui/ServiceRepresentativeMainScreenFrame.fxml");
	}
	
	/**

	This method is used to enable the information of the customer when selected from the combo box.
	It sets the customer's ID, full name, email, telephone number, and subscriber status on the corresponding labels.
	If the customer is a subscriber, the subscriber radio button will be selected, otherwise the non-subscriber radio button will be selected.
	@param event the event that triggers this method, usually a button click
	*/
	public void enableInformation(ActionEvent event) {
		message_to_gui.setText("");
		information_anchor.setDisable(false);
		isCustomerChosen = true;
		String username = customer_combo.getValue().toString();
		for(Customer customer : all_customers) {
			if(customer.getUsername().equals(username)) {
				cus_id_label.setText(customer.getId());
				cus_full_name_label.setText(customer.getFirstName() + " " + customer.getLastName());
				cus_email_label.setText(customer.getEmail());
				cus_telephone_label.setText(customer.getPhoneNumber());
				if(customer.getIsSubscriber() == 1) {
					set_subscriber_radio.setSelected(true);
				}
				else {
					not_subscriber_radio.setSelected(true);
				}
			}
		}
	}
	
	/**

	This method allows updating the status of a customer as a subscriber or non-subscriber.
	When the update button is pressed, the method will check if a customer has been chosen and if the customer's current status is different than the one selected by the user.
	If the customer's current status is different than the one selected by the user, the method will update the customer's status in the database and send a command to the server.
	If the customer's current status is the same as the one selected by the user, the method will display a message to inform the user.
	@param event the event that triggers the method when the update button is pressed.
	*/
	public void getUpdateBtn(ActionEvent event) {
		message_to_gui.setText("");
		if(isCustomerChosen) {
			String username = customer_combo.getValue().toString();
			for(Customer customer : all_customers) {
				if(customer.getUsername().equals(username)) {
					if(customer.getIsSubscriber() == 0) {
						if(set_subscriber_radio.isSelected()) {
							customer.setIsSubscriber(1);
							EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("set_customer_as_subscriber", customer));
						}
						else {
							message_to_gui.setText("The customer is already a non-subscriber");
						}
					}
					else {
						if(set_subscriber_radio.isSelected()) {
							message_to_gui.setText("The customer is already a subscriber");
						}
						else {
							customer.setIsSubscriber(0);
							EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("set_customer_as_subscriber", customer));
						}
					}
					break;
				}
			}
		}
		else {
			message_to_gui.setText("You must choose customer in order to handle data");
		}
	}
}