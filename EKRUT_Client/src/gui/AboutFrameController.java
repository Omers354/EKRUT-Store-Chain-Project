package gui;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import logic.User;

/**

The AboutFrameController class is a JavaFX controller for an About Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the About Frame and to handle events.

@author [Shahar]

@version [4.0]
*/
public class AboutFrameController extends FrameController{

	@FXML
	private Text disable_text_about;
	
	@FXML
	private Text disable_text_home_page;
	
	@FXML
	private Text disable_text_logout;
	
	@FXML
	private Button about_btn;
	
	@FXML
	private Button home_btn;
	
	@FXML
	private Button logout_btn;
	
	private User user;
	
	@Override
	public void additionalChanges() {
		if(EkrutClientUI.client_control.ekrut_client.user != null) {
			user = (User)EkrutClientUI.client_control.ekrut_client.user;
			full_name.setText(user.getFirstName() + " " + user.getLastName());
		}
		else {
			full_name.setText("Guest");
			home_btn.setDisable(true);
			disable_text_about.setDisable(true);
			disable_text_home_page.setDisable(true);
			disable_text_logout.setDisable(true);
			about_btn.setDisable(true);
			home_btn.setDisable(true);
			logout_btn.setDisable(true);
		}
		about_btn.setDisable(true);
	}
	
	/**
    This method is called when the Home button is pressed.
	Its sent the user back to his home screen
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	@Override
	public void getHomeBtn(ActionEvent event) throws Exception{
		EkrutClientUI.back_frames.clear();
		if(user != null) {
			if(user.getRole().toString().equals("CEO")) {
				EkrutClientUI.home_frame.start(primaryStage, "/gui/CEOMainScreenFrame.fxml");
			}
			
			else if(user.getRole().toString().equals("Regional_Manager")) {
				EkrutClientUI.home_frame.start(primaryStage, "/gui/RegionalManagerMainScreenFrame.fxml");
			}
			
			else if(user.getRole().toString().equals("Service_Representative")) {
				EkrutClientUI.home_frame.start(primaryStage, "/gui/ServiceRepresentativeMainScreenFrame.fxml");
			}
			
			else if(user.getRole().toString().equals("Customer")) {
				EkrutClientUI.home_frame.start(primaryStage, "/gui/CustomerMainScreenFrame.fxml");
			}
			
			else if(user.getRole().toString().equals("Subscriber")) {
				EkrutClientUI.home_frame.start(primaryStage, "/gui/CustomerMainScreenFrame.fxml");
			}
			
			else if(user.getRole().toString().equals("Marketing_Manager")) {
				EkrutClientUI.home_frame.start(primaryStage, "/gui/MarketingManagerMainScreenFrame.fxml");
			}
			
			else if(user.getRole().toString().equals("Marketing_Worker")) {
				EkrutClientUI.home_frame.start(primaryStage, "/gui/MarketingWorkerMainScreenFrame.fxml");
			}
			
			else if(user.getRole().toString().equals("Operation_Worker")) {
				EkrutClientUI.home_frame.start(primaryStage, "/gui/OperationWorkerMainScreenFrame.fxml");
			}
			
			else if(user.getRole().toString().equals("Delivery_Operator")) {
				EkrutClientUI.home_frame.start(primaryStage, "/gui/DeliveryOperatorMainScreenFrame.fxml");
			}
		}
	}
}