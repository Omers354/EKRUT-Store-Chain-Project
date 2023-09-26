package gui;

import java.util.ArrayList;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Command;

/**

The LoginFrameController class is a JavaFX controller for a Login Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Login and to handle events.

@author [Shahar]

@version [4.0]
*/
public class LoginFrameController extends FrameController {
	
	@FXML
	private TextField username_field;
	
	@FXML
	private PasswordField password_field;
	
	@FXML
	private TextField subscriber_id_field;
	
	@FXML
	private ImageView fast_login_logo;
	
	/**

	getLoginBtn is a method that is called when the user clicks the login button. It checks if the client is connected to the server, if it is,
	it checks if the username and password fields have input. If they do, it sends a login_attempt command to the server with the input as the information.
	@param event the event of clicking the login button.
	@throws Exception
	*/
	public void getLoginBtn(ActionEvent event) throws Exception {
		if(EkrutClientUI.client_control.ekrut_client.isConnected) {
			message_to_gui.setText("");
			String username = username_field.getText();
			String password = password_field.getText();
			if(username.trim().isEmpty() || password.trim().isEmpty()) {
				message_to_gui.setText("You must enter Username and Password in order to connect!");
			}
			else {
				ArrayList<String> info = new ArrayList<String>();
				info.add(username);
				info.add(password);
				EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("login_attempt", info));
			}
		}
	}
	/**
	getFastLogin is a method that is called when the user clicks the fast login button. It checks if the client is connected to the server,
	if it is, it checks if the subscriber number field has input. If it does, it sends a login_attempt command to the server with the input as the information.
	@param event the event of clicking the fast login button.
	@throws Exception
	*/
	public void getFastLogin(ActionEvent event) throws Exception {
		if(EkrutClientUI.client_control.ekrut_client.isConnected) {
			message_to_gui.setText("");
			String sub_num = subscriber_id_field.getText();
			if(sub_num.trim().isEmpty()) {
				message_to_gui.setText("You must enter Subscriber Number to connect!");
			}
			else {
				try {
					int sub_num_int = Integer.parseInt(sub_num);
					if (sub_num_int>99999 && sub_num_int<1000000) {
						EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("login_attempt", sub_num_int));
					}
					else {
						message_to_gui.setText("Subscriber Number must be a 6 digit number");
					}
				}
				catch (NumberFormatException ex) {
					message_to_gui.setText("Subscriber Number must be a number");
				}
			}
		}
	}
	/**
	additionalChanges is a method that is called after the frame is loaded. It sets the image for the fast_login_logo.
	*/
	@Override
	public void additionalChanges() {
		fast_login_logo.setImage(new Image(this.getClass().getResourceAsStream("/fast_payment.png")));
	}
}