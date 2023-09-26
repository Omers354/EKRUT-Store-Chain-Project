package gui;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Command;
import logic.User;

/**

The ServiceRepresentativeMainScreenFrameController class is a JavaFX controller for a Service Representative Main Screen Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Service Representative Main Screen and to handle events.

@author [Shahar]

@version [4.0]
*/
public class ServiceRepresentativeMainScreenFrameController extends FrameController {

	@FXML
	private ImageView customer_registration;
	
	@FXML
	private ImageView set_subscriber;
	
	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		User user = EkrutClientUI.client_control.ekrut_client.user;
		full_name.setText(user.getFirstName() + " " + user.getLastName());
		customer_registration.setImage(new Image(this.getClass().getResourceAsStream("/customer_management.png")));
		set_subscriber.setImage(new Image(this.getClass().getResourceAsStream("/create_new_order.png")));
	}
	
	/**

	getCustomerRegistration is a method that is called when the user clicks the Customer Registration button. It sends a customer_registration command to the server.
	@param event the event of clicking the set sale template button.
	@throws Exception
	*/
	public void getCustomerRegistration(ActionEvent event) {
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("customer_registration", null));
	}
	
	/**

	getSetSubscriber is a method that is called when the user clicks the Set Subscriber button. It sends a set_subscriber command to the server.
	@param event the event of clicking the set sale template button.
	@throws Exception
	*/
	public void getSetSubscriber(ActionEvent event) {
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("set_subscriber", null));
	}
}