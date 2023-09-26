package gui;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Command;

/**

The MarketingManagerMainScreenFrameController class is a JavaFX controller for a Marketing Manager Main Screen Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Marketing Manager Main Screen  and to handle events.

@author [Shahar]

@version [4.0]
*/
public class MarketingManagerMainScreenFrameController extends FrameController{

	@FXML
	private ImageView set_sale_logo;
	
	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		set_sale_logo.setImage(new Image(this.getClass().getResourceAsStream("/Set_Sale_Template2.png")));
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
	}
	
	/**

	getSetSaleTemplateBtn is a method that is called when the user clicks the set sale template button. It sends a set_sale_template command to the server to update the sale template.
	@param event the event of clicking the set sale template button.
	@throws Exception
	*/
    public void getSetSaleTemplateBtn(ActionEvent event) throws Exception{
    	EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("set_sale_template", null));
    }

}
