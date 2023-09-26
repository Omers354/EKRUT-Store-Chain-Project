package gui;

import client.EkrutClientUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import logic.Customer;
import logic.Supply_Method;

/**

The FinishOrderFrameController class is a JavaFX controller for a Finish Order Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Finish Order and to handle events.

@author [Shahar]

@version [4.0]
*/
public class FinishOrderFrameController extends FrameController{

	@FXML
	private Button HomePagebtn;
	
	@FXML
	private Button Continuebtn;
	
	@FXML
	private Text goodByeText;
	
	@FXML
	private Text PickUp_codeText;
	
	@FXML
	private Text EmailANDPhoneField;
		
	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	if the order is local or delivery the customer dont recive pickup code.
	if the order is pickup the customer recive pickup code.
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		Customer customer = (Customer)EkrutClientUI.client_control.ekrut_client.user;
		status.setText(customer.getCustomer_status().toString());
		status.setStyle("-fx-text-fill: #00FF00");
		if(EkrutClientUI.client_control.ekrut_client.PostponePayment) {
			goodByeText.setText("Thank you for your purchase on ekrut!\nYour payment postponed to next month and an invoice has been sent to your email and phone number.");
		}
		EmailANDPhoneField.setText("Email: "+EkrutClientUI.client_control.ekrut_client.user.getEmail()
		+"\nPhone: "+EkrutClientUI.client_control.ekrut_client.user.getPhoneNumber());
		EmailANDPhoneField.setWrappingWidth(400);
		if(EkrutClientUI.client_control.ekrut_client.curr_order.getSupply_method().equals(Supply_Method.Store_PickUp) && EkrutClientUI.client_control.ekrut_client.curr_order.getPickUp_Code()!=null) {
			PickUp_codeText.setVisible(true);
			PickUp_codeText.setText("Your pickup code is: "+EkrutClientUI.client_control.ekrut_client.curr_order.getPickUp_Code());
			PickUp_codeText.setWrappingWidth(400);
		}
		EkrutClientUI.client_control.ekrut_client.curr_order = null;
		EkrutClientUI.client_control.ekrut_client.curr_delivery = null;
		EkrutClientUI.client_control.ekrut_client.countdown.cancel();
		EkrutClientUI.client_control.ekrut_client.countdown = null;
		EkrutClientUI.client_control.ekrut_client.up_second.cancel();
		EkrutClientUI.client_control.ekrut_client.up_second = null;
	}

}
