package gui;

import client.EkrutClientUI;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Command;
import logic.RegionalManager;

/**

The OperationWorkerMainScreenFrameController class is a JavaFX controller for a Operation Worker Main Screen Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Operation Worker Main Screen  and to handle events.

@author [Shahar]

@version [4.0]
*/
public class OperationWorkerMainScreenFrameController extends FrameController {

	@FXML
	private ImageView restock_logo;
	
	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		restock_logo.setImage(new Image(this.getClass().getResourceAsStream("/restock.png")));
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
	}
	
	/**

	getRestockInventory is a method that is called when the user clicks the Restock Inventory button. It sends a restock_inventory command to the server to update the stock.
	@param event the event of clicking the set sale template button.
	@throws Exception
	*/
	public void getRestockInventory(){
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("restock_inventory", null));
	}
}