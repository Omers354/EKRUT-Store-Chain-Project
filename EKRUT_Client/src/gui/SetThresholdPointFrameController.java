package gui;

import java.util.ArrayList;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import logic.Branch;
import logic.Command;
import logic.Inventory;
import logic.RegionalManager;

/**

The SetThresholdPointFrameController class is a JavaFX controller for a Set ThresholdPoint Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Set ThresholdPoint Screen and to handle events.

@author [Shahar]

@version [4.0]
*/
public class SetThresholdPointFrameController extends FrameController {
	
	@FXML
	private AnchorPane option1_anchor;
	
	@FXML
	private AnchorPane option2_anchor;
	
	@FXML
	private RadioButton option1;
	
	@FXML
	private RadioButton option2;
	
	@FXML
	private ComboBox<String> branch_combo;
	
	@FXML
	private ComboBox<String> product_combo;
	
	@FXML
	private TextField unified_threshold;
	
	@FXML
	private TextField separate_threshold;
		
	ArrayList<ArrayList<Inventory>> inventory;
	
	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	It send from the client new Command("get_threshold_points")
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		RegionalManager regMan = (RegionalManager)EkrutClientUI.client_control.ekrut_client.user;
		full_name.setText(regMan.getFirstName() + " " + regMan.getLastName());
		status.setText(regMan.getRegion());
		option1.setSelected(true);
		option2_anchor.setDisable(true);
		product_combo.setDisable(true);
		separate_threshold.setDisable(true);
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_threshold_points", regMan.getRegion()));
		try {
			Thread.sleep(150);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.inventory = EkrutClientUI.client_control.ekrut_client.threshold_table;
		for(ArrayList<Inventory> branch : inventory) {
			branch_combo.getItems().add(branch.get(0).getBranch());
		}
	}
	
	/**

	The getHomeBtn method is used to navigate back to the home frame.
	@param event the ActionEvent that triggers the method
	@throws Exception when an error occurs while navigating to the home frame or exiting the application
	*/
	@Override
	public void getHomeBtn(ActionEvent event) throws Exception{
		EkrutClientUI.back_frames.clear();
		EkrutClientUI.home_frame.start(primaryStage, "/gui/RegionalManagerMainScreenFrame.fxml");
	}
	
	/**

	Enables the option1_anchor and disables option2_anchor, also clears any previous input in separate_threshold
	@param event the ActionEvent that fired this method
	*/
	public void getOption1Btn(ActionEvent event) {
		message_to_gui.setText("");
		separate_threshold.setText("");
		option1_anchor.setDisable(false);
		option2_anchor.setDisable(true);
	}
	
	/**

	Enables the option2_anchor and disables option1_anchor, also clears any previous input in unified_threshold
	@param event the ActionEvent that fired this method
	*/
	public void getOption2Btn(ActionEvent event) {
		message_to_gui.setText("");
		unified_threshold.setText("");
		option1_anchor.setDisable(true);
		option2_anchor.setDisable(false);
	}
	
	/**

	Enables the product_combo, clears previous input and fills it with products in the chosen branch
	@param event the ActionEvent that fired this method
	*/
	public void enableProduct(ActionEvent event) {
		message_to_gui.setText("");
		product_combo.getItems().clear();
		separate_threshold.setDisable(true);
		separate_threshold.setText("");
		for(ArrayList<Inventory> branch : inventory) {
			if(branch_combo.getValue().equals(branch.get(0).getBranch())) {
				product_combo.setDisable(false);
				product_combo.getItems().add("Include All Products");
				for(Inventory in : branch) {
					product_combo.getItems().add(in.getProduct_name());
				}
			}
		}
	}
	
	/**

	Enables the separate_threshold textfield
	@param event the ActionEvent that fired this method
	*/
	public void enableSeparate(ActionEvent event) {
		message_to_gui.setText("");
		separate_threshold.setDisable(false);
	}
	
	
	/**

	This method handles the update button event when it is clicked by the user. It checks whether the option 1 or option 2 is selected and based on that it performs certain actions.
	If option 1 is selected it checks whether the input entered in the unified_threshold text field is a valid number and whether the number is greater than or equal to 0 or not.
	If option 2 is selected it checks whether the input entered in the separate_threshold text field is a valid number and whether the number is greater than or equal to 0 or not.
	If any of the above conditions are not met, it displays an appropriate error message to the user.
	*/
	public void getUpdateBtn(ActionEvent event) {
		message_to_gui.setText("");
		boolean isNumber = true;
		int threshold;
		if(option1.isSelected()) {
			try {
				threshold = Integer.parseInt(unified_threshold.getText());
			}
			catch (Throwable t) {
				isNumber = false;
			}
			if (!isNumber) {
				System.out.println("Please enter a whole number threshold point");
				message_to_gui.setText("Please enter a whole number threshold point");
			}
			else {
				threshold = Integer.parseInt(unified_threshold.getText());
				if(threshold < 0) {
					System.out.println("Please enter a whole positive number threshold point");
					message_to_gui.setText("Please enter a whole positive number threshold point");
				}
				else {
					EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("set_threshold_point", unified_threshold.getText()));
				}
			}
		}
		else if(option2.isSelected()) {
			if(!separate_threshold.isDisable()) {
				try {
					threshold = Integer.parseInt(separate_threshold.getText());
				}
				catch (Throwable t) {
					isNumber = false;
				}
				if (!isNumber) {
					System.out.println("Please enter a whole number threshold point");
					message_to_gui.setText("Please enter a whole number threshold point");
				}
				else {
					threshold = Integer.parseInt(separate_threshold.getText());
					if(threshold < 0) {
						System.out.println("Please enter a whole positive number threshold point");
						message_to_gui.setText("Please enter a whole positive number threshold point");
					}
					else {
						EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("set_threshold_point", new Inventory(branch_combo.getValue(), null, product_combo.getValue(), 0, threshold)));
					}
				}
			}
			else {
				message_to_gui.setText("You must choose branch and product in order to send");
			}
		}
	}
}