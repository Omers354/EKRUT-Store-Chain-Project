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
import logic.Inventory;
import logic.RegionalManager;
import logic.User;

/**

The CustomerManagementFrameController class is a JavaFX controller for a Customer Management Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Choose Customer Management and to handle events.

@author [Shahar]

@version [4.0]
*/
public class CustomerManagementFrameController extends FrameController {

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
	private RadioButton approve_cus;
	
	@FXML
	private RadioButton decline_cus;
	
	ArrayList<User> pending_customers;
	
	private boolean isCustomerChosen;
	
	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	It send from the client new Command("get_branches_table")
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		RegionalManager regMan = (RegionalManager)EkrutClientUI.client_control.ekrut_client.user;
		full_name.setText(regMan.getFirstName() + " " + regMan.getLastName());
		status.setText(regMan.getRegion());
		decline_cus.setSelected(true);
		information_anchor.setDisable(true);
		isCustomerChosen = false;
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_customer_data", "Pending"));
		try {
			Thread.sleep(100);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		pending_customers = EkrutClientUI.client_control.ekrut_client.pending_customers;
		if(pending_customers != null) {
			for(User user : pending_customers) {
				customer_combo.getItems().add(user.getUsername());
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
		EkrutClientUI.home_frame.start(primaryStage, "/gui/RegionalManagerMainScreenFrame.fxml");
	}
	
	/**
    This method is called when the customer_combo is pressed.
	Its from the user pending_customers
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	public void enableInformation(ActionEvent event) {
		message_to_gui.setText("");
		information_anchor.setDisable(false);
		isCustomerChosen = true;
		String username = customer_combo.getValue().toString();
		for(User user : pending_customers) {
			if(user.getUsername().equals(username)) {
				cus_id_label.setText(user.getId());;
				cus_full_name_label.setText(user.getFirstName() + " " + user.getLastName());;
				cus_email_label.setText(user.getEmail());;
				cus_telephone_label.setText(user.getPhoneNumber());;
			}
		}
	}
	
	/**

	This method is called when the "Update" button is pressed.
	It send from the client new Command("approve_customer") and customer_combo.getValue().toString().
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	public void getUpdateBtn(ActionEvent event) {
		message_to_gui.setText("");
		if(isCustomerChosen) {
			if(approve_cus.isSelected()) {
				EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("approve_customer", customer_combo.getValue().toString()));
			}
			else if(decline_cus.isSelected()) {
				message_to_gui.setText("Declined. The customer has been notified via email and phone");
			}
		}
		else {
			message_to_gui.setText("You must choose customer in order to handle data");
		}
	}
}