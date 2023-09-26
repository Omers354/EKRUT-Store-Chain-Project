package gui;

import java.util.ArrayList;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import logic.Command;
import logic.User;

/**

The CustomerManagementFrameController class is a JavaFX controller for a Customer Management Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Choose Customer Management and to handle events.

@author [Shahar]

@version [4.0]
*/
public class CustomerRegistrationFrameController extends FrameController {

	@FXML
	private AnchorPane user_anchor;
	
	@FXML
	private AnchorPane information_anchor;
	
	@FXML
	private ComboBox user_combo;
	
	@FXML
	private Label user_id_label;
	
	@FXML
	private Label user_full_name_label;
	
	@FXML
	private Label user_email_label;
	
	@FXML
	private Label user_telephone_label;
	
	@FXML
	private TextField credit_card_text;
	
	ArrayList<User> none_role_users;
	
	private boolean isCustomerChosen;
	
	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	It send from the client new Command("get_customer_data") and "None"
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		User service_representative = EkrutClientUI.client_control.ekrut_client.user;
		full_name.setText(service_representative.getFirstName() + " " + service_representative.getLastName());
		information_anchor.setDisable(true);
		isCustomerChosen = false;
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_customer_data", "None"));
		try {
			Thread.sleep(100);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		none_role_users = EkrutClientUI.client_control.ekrut_client.pending_customers;
		if(none_role_users != null) {
			for(User user : none_role_users) {
				user_combo.getItems().add(user.getUsername());
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
    This method is called when the user_combo is pressed.
	Its from the user none_role_users
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	public void enableInformation(ActionEvent event) {
		message_to_gui.setText("");
		information_anchor.setDisable(false);
		isCustomerChosen = true;
		String username = user_combo.getValue().toString();
		for(User user : none_role_users) {
			if(user.getUsername().equals(username)) {
				user_id_label.setText(user.getId());;
				user_full_name_label.setText(user.getFirstName() + " " + user.getLastName());;
				user_email_label.setText(user.getEmail());;
				user_telephone_label.setText(user.getPhoneNumber());;
			}
		}
	}
	
	/**

	This method is called when the "Update" button is pressed.
	It send from the client new Command("set_new_user_as_customer") and arr.
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	public void getUpdateBtn(ActionEvent event) {
		message_to_gui.setText("");
		if(isCustomerChosen) {
			String str = credit_card_text.getText();
			if(str.isEmpty()) {
				message_to_gui.setText("You must enter credit card number");
			}
			else {
				for(User user : none_role_users) {
					if(user.getUsername().equals(user_combo.getValue().toString())) {
						ArrayList<Object> arr = new ArrayList<>();
						arr.add(user);
						arr.add(str);
						EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("set_new_user_as_customer", arr));
						break;
					}
				}
			}
		}
		else {
			message_to_gui.setText("You must choose customer in order to handle data");
		}
	}
}