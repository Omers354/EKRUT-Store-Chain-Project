package gui;

import java.time.LocalTime;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logic.Command;
import logic.MarketingWorker;
import logic.RegionalManager;

/**

The MarketingWorkerMainScreenFrameController class is a JavaFX controller for a Marketing Worker Main Screen Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Marketing Worker Main Screen  and to handle events.

@author [Shahar]

@version [4.0]
*/
public class MarketingWorkerMainScreenFrameController extends FrameController{
	
	@FXML
	private ImageView activate_sale_logo;
	
	@FXML
	private ImageView end_sale_logo;

	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		activate_sale_logo.setImage(new Image(this.getClass().getResourceAsStream("/Activate_Sale.png")));
		end_sale_logo.setImage(new Image(this.getClass().getResourceAsStream("/Cancel_Sale.png")));
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		MarketingWorker MarkWrok = (MarketingWorker)EkrutClientUI.client_control.ekrut_client.user;
		status.setText(MarkWrok.getRegion());
	}
	
	/**

	getActivateSaleBtn is a method that is called when the user clicks the Activate Sale button. It sends a activate_sale_screen command to the server to update the sale.
	@param event the event of clicking the set sale template button.
	@throws Exception
	*/
	public void getActivateSaleBtn(ActionEvent event) throws Exception{
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("activate_sale_screen", null));
	}
	
	/**

	getEndSaleBtn is a method that is called when the user clicks the End Sale button. It sends a end_sale_screen command to the server to update the sale.
	@param event the event of clicking the set sale template button.
	@throws Exception
	*/
	public void getEndSaleBtn(ActionEvent event) throws Exception{
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("end_sale_screen", null));
	}

}
