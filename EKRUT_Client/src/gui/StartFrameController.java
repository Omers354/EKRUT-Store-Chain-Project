package gui;

import client.EkrutClientController;
import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**

The StartFrameController class is a JavaFX controller for a  Start Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Start Screen and to handle events.

@author [Shahar]

@version [4.0]
*/
public class StartFrameController extends FrameController {

	@FXML
	private TextField ip_field;

	@FXML
	private TextField port_field;

	//Override because we can't check: EkrutClientUI.client_control.ekrut_client.isConnected ---> No ClientController!!!
	/**

	This method is used to exit the EKRUT client application when the Exit button is clicked.
	@param event the action event that triggered the method, in this case the Exit button being clicked.
	@throws Exception if an exception occurs while exiting the application.
	*/
	@Override
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("Exit EKRUT Client");
		System.exit(0);
	}

	/**

	This method is used to connect the EKRUT client to the server when the Connect button is clicked.
	@param event the action event that triggered the method, in this case the Connect button being clicked.
	@throws Exception if an exception occurs while connecting to the server.
	*/
	public void getConnectBtn(ActionEvent event) throws Exception {
		String ip = ip_field.getText();
		String port = port_field.getText();
		boolean isNumber = true;
		if (ip.trim().isEmpty() || port.trim().isEmpty()) {
			message_to_gui.setText("You must enter IP and Port in order to connect!");
		} 
		else {
			try {
				Integer.parseInt(port); // Getting other port
			}
			catch (Throwable t) {
				isNumber = false;
			}
			if (!isNumber) {
				System.out.println("Port must be a number");
				message_to_gui.setText("Port must be a number");
			}
			else {
				try {
					EkrutClientUI.client_control = new EkrutClientController(ip, Integer.parseInt(port)); // Create connection
					EkrutClientUI.Connect();
					System.out.println("Connected to server!");
					LoginFrameController next = new LoginFrameController();
					next.start(primaryStage, "/gui/LoginFrame.fxml");
				} catch (Exception e) {
					System.out.println("Error: Can't setup connection! Must open connection in server first");
					message_to_gui.setText("Error: Can't setup connection!");
				}
			}
		}
	}
	
	@Override
	public void additionalChanges() {
	}
}