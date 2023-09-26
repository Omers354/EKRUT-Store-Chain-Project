package gui;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Command;
import logic.RegionalManager;

/**

The RegionalManagerMainScreenFrameController class is a JavaFX controller for a Regional Manager Main Screen Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Regional Manager Main Screen and to handle events.

@author [Shahar]

@version [4.0]
*/
public class RegionalManagerMainScreenFrameController extends FrameController {

	@FXML
	private ImageView set_threshold_point;
	
	@FXML
	private ImageView customer_management;
	
	@FXML
	private ImageView request_restock_logo;
	
	@FXML
	private ImageView view_reports;
	
	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		set_threshold_point.setImage(new Image(this.getClass().getResourceAsStream("/threshold_point.png")));
		customer_management.setImage(new Image(this.getClass().getResourceAsStream("/customer_management.png")));
		request_restock_logo.setImage(new Image(this.getClass().getResourceAsStream("/request_restock.png")));
		view_reports.setImage(new Image(this.getClass().getResourceAsStream("/view_reports.png")));
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		RegionalManager regMan = (RegionalManager)EkrutClientUI.client_control.ekrut_client.user;
		status.setText(regMan.getRegion());
	}
	
	/**

	getSetThresholdPoint is a method that is called when the user clicks the Set ThresholdPoint button. It sends a threshold_point command to the server.
	@param event the event of clicking the set sale template button.
	@throws Exception
	*/
	public void getSetThresholdPoint(ActionEvent event) throws Exception{
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("threshold_point", null));
	}
	
	/**

	getCustomerManagement is a method that is called when the user clicks the Set Customer Management button. It sends a customer_management command to the server.
	@param event the event of clicking the set sale template button.
	@throws Exception
	*/
	public void getCustomerManagement(ActionEvent event) throws Exception{
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("customer_management", null));
	}
	
	/**

	getRequestRestock is a method that is called when the user clicks the Request Restock button. It sends a request_restock command to the server.
	@param event the event of clicking the set sale template button.
	@throws Exception
	*/
	public void getRequestRestock(ActionEvent event) throws Exception{
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("request_restock", null));
	}
	
	/**

	getViewReports is a method that is called when the user clicks the View Reports button. It sends a choose_report command to the server.
	@param event the event of clicking the set sale template button.
	@throws Exception
	*/
	public void getViewReports(ActionEvent event) throws Exception{
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("choose_report", null));
	}
}