package gui;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Ceo;
import logic.Command;
import logic.RegionalManager;


/**
 * The CEOMainScreenFrameController is a class that controls the behavior of the CEO main screen frame.
 * It extends the FrameController class, which provides basic functionality for all frame controllers.
 * It is associated with the CEOMainScreenFrame.fxml layout file through the @FXML annotations.
 *
 * The class provides additional changes for the CEO main screen frame by setting the images for the GUI elements and display CEO's name
 * and also it handle the event of clicking on the view report button by sending a command to the server to choose CEO report.
 */
public class CEOMainScreenFrameController extends FrameController {
	
	/**
	 * The set_threshold_point ImageView is used to display an image related to setting threshold points for products.
	 * It is associated with a method in the CEOMainScreenFrameController class through the @FXML annotation.
	 */


	@FXML
	private ImageView set_threshold_point;
	
	/**
	 * The customer_management ImageView is used to display an image related to customer management.
	 * It is associated with a method in the CEOMainScreenFrameController class through the @FXML annotation.
	 */

	
	@FXML
	private ImageView customer_management;
	
	
	/**
	 * The request_restock_logo ImageView is used to display an image related to requesting restock.
	 * It is associated with a method in the CEOMainScreenFrameController class through the @FXML annotation.
	 */

	@FXML
	private ImageView request_restock_logo;
	
	/**
	 * The view_reports ImageView is used to display an image related to view reports.
	 * It is associated with a method in the CEOMainScreenFrameController class through the @FXML annotation.
	 */

	
	@FXML
	private ImageView view_reports;
	
	/**
	 * The additionalChanges() method is used to make additional changes to the CEO main screen frame after it has been loaded. 
	 * It sets the image for the view_reports ImageView by loading it from the resource directory, sets the full name of the CEO user and instantiates the ceo object.
	 */

	
	@Override
	public void additionalChanges() {
		view_reports.setImage(new Image(this.getClass().getResourceAsStream("/view_reports.png")));
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		Ceo ceo = (Ceo)EkrutClientUI.client_control.ekrut_client.user;
	}
	
	
	public void getViewReports(ActionEvent event) throws Exception{
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("choose_CEOreport", null));
	}
}