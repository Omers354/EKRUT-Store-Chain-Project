package gui;

import client.EkrutClientUI;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Command;
import logic.DeliveryOperator;
import logic.RegionalManager;

/**

The DeliveryWorkerMainScreenFrameController class is a JavaFX controller for a  Delivery Worker Main Screen Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Delivery Worker Main Screen  and to handle events.

@author [Shahar]

@version [4.0]
*/
public class DeliveryWorkerMainScreenFrameController extends FrameController {

	@FXML
	private ImageView Active_Delivery_Orders;
	
	@FXML
	private ImageView Finish_Delivery;
	
	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		Active_Delivery_Orders.setImage(new Image(this.getClass().getResourceAsStream("/Active_Delivery_Orders.png")));
		Finish_Delivery.setImage(new Image(this.getClass().getResourceAsStream("/Finish_Delivery.png")));
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		DeliveryOperator DeliveryOp =(DeliveryOperator)EkrutClientUI.client_control.ekrut_client.user;
		status.setText(DeliveryOp.getRegion());
	}
	
	/**

	It send from the client new Command("delivery")
	*/
	public void getDelivery(){
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("delivery", null));
	}
	
	/**

	It send from the client new Command("done_delivery")
	*/
	public void getDone(){
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("done_delivery", null));
	}
}