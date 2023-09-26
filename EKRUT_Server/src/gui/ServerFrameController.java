package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.EkrutServerUI;
import server.mysqlConnection;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**

The ServerFrameController class serves as the controller for the JavaFX application that provides a user interface for a server.

It handles the events and interactions of the user interface, such as button clicks and text field inputs.

The class uses the FXML file format to define the layout of the user interface, and it is loaded by the FXMLLoader class.

The class also uses the EkrutServerUI class to start the server and the mysqlConnection class to establish the connection to the MySQL database.

@author (Shahar)

@version (4.0)

*/
public class ServerFrameController {

/**

The exit_btn variable is the button for exiting the application.
*/
@FXML
private Button exit_btn = null;
/**

The connect_btn variable is the button for connecting to the server.
*/
@FXML
private Button connect_btn = null;
/**

The disconnect_btn variable is the button for disconnecting from the server.
*/
@FXML
private Button disconnect_btn = null;
/**

The ekrut_logo variable is the image view for displaying the ekrut logo.
*/
@FXML
private ImageView ekrut_logo;
/**

The about_logo variable is the image view for displaying the about logo.
*/
@FXML
private ImageView about_logo;
/**

The import_users_logo variable is the image view for displaying the import users logo.
*/
@FXML
private ImageView import_users_logo;
/**

The status variable is the label for displaying the status of the server and the database connection.
*/
@FXML
private Label status;
/**

The host_ip variable is the label for displaying the IP address of the server.
*/
@FXML
private Label host_ip;
/**

The port_field variable is the text field for entering the port number for the server.
*/
@FXML
private TextField port_field;
/**

The db_name variable is the text field for entering the name of the MySQL database.
*/
@FXML
private TextField db_name;
/**

The db_username variable is the text field for entering the username for the MySQL database.
*/
@FXML
private TextField db_username;
/**

The db_password variable is the password field for entering the password for the MySQL database.
*/
@FXML
private PasswordField db_password;
/**

The message_to_gui variable is the text area for displaying messages to the user.
*/
@FXML
private TextArea message_to_gui;
/**

The scene variable is the scene of the JavaFX application.
*/
private Scene scene;
		
/**

The start method is called to start the JavaFX application and display the user interface.

It loads the FXML file, sets the controller to this instance, sets the scene, and displays the stage.

It also sets the ekrut logo, about logo, import users logo, primary stage, and message to gui

@param primaryStage the primary stage of the JavaFX application.

@param resource the path of the FXML file that defines the layout of the user interface.

@throws Exception
*/
	public void start(Stage primaryStage, String resource) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
		loader.setController(this); // You need to set this instance as the controller.
		Parent root = loader.load();
		this.scene = new Scene(root);
		ekrut_logo.setImage(new Image(this.getClass().getResourceAsStream("/ekrut.png")));
		about_logo.setImage(new Image(this.getClass().getResourceAsStream("/about.png")));
		import_users_logo.setImage(new Image(this.getClass().getResourceAsStream("/import_users.png")));
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/ekrut.png")));
		primaryStage.setTitle("EKRUT Server");
		primaryStage.setScene(scene);
		primaryStage.show();
		message_to_gui.setText("Please enter port number (default: 5555)\n");
		EkrutServerUI.primaryStage = primaryStage;
	}
	
	/**

	The getPort method is used to get the port number entered by the user in the port_field text field.
	@return the port number entered by the user.
	*/
	public String getPort() {
		return port_field.getText();
	}
	
	/**

	The getDbName method is used to get the name of the MySQL database entered by the user in the db_name text field.
	@return the name of the MySQL database entered by the user.
	*/
	public String getDbName() {
		return db_name.getText();
	}
	
	/**

	The getDbUsername method is used to get the username for the MySQL database entered by the user in the db_username text field.
	@return the username for the MySQL database entered by the user.
	*/
	public String getDbUsername() {
		return db_username.getText();
	}
	
	/**

	The getDbPass method is used to get the password for the MySQL database entered by the user in the db_password password field.
	@return the password for the MySQL database entered by the user.
	*/
	public String getDbPass() {
		return db_password.getText();
	}

	/**

	The getConnectBtn method is called when the connect button is clicked.
	It gets the port number entered by the user, starts the server, and updates the status of the server and the IP address displayed in the user interface.
	If the port number is not entered, it will display an error message.
	It will also update the status of the connect and disconnect buttons and the message to gui.
	@param event the ActionEvent object
	@throws Exception
	*/
	public void getConnectBtn(ActionEvent event) throws Exception {
		String port, ip;
		port = getPort();
		if (port.trim().isEmpty()) {
			System.out.println("You must enter a port number.\n");
			message_to_gui.setText("You must enter a port number.\n");
		}
		else {
			EkrutServerUI.runServer(port);
			if(EkrutServerUI.isServerOpen && mysqlConnection.isMySqlConnectSucceed) {
				ip = EkrutServerUI.getServer().getIP();
				host_ip.setText(ip);
				host_ip.setStyle("-fx-text-fill: #00FF00");
				status.setText("Online"); // Printing to client
				status.setStyle("-fx-text-fill: #00FF00");
				connect_btn.setDisable(true);
				disconnect_btn.setDisable(false);
			}
		}
	}
	
	/**

	This method is called when the "Disconnect" button is pressed.
	It stops the server, updates the GUI to show that the server is now offline and disables the "Disconnect" button
	and enables the "Connect" button.
	@param event the action event that triggers this method
	@throws Exception when an error occurs while disconnecting the server.
	*/
	public void getDisconnectBtn(ActionEvent event) throws Exception{
		EkrutServerUI.endServer();
		host_ip.setText("Not Connected");
		host_ip.setStyle("-fx-text-fill: #FF3131");
		status.setText("Offline"); // Printing to client
		status.setStyle("-fx-text-fill: #FF3131");
		connect_btn.setDisable(false);
		disconnect_btn.setDisable(true);
	}
	
	/**
	* This method is used to display the message in the console.
	* @param msg The message to be displayed in the console.
	*/
	public void displayConsoleMessage(String msg) {
		message_to_gui.appendText(msg + "\n");
	}
	
	/**

	This method is called when the "Exit" button is pressed.
	It stops the server and exits the application.
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("Exit EKRUT Server");
		EkrutServerUI.endServer();
		System.exit(0);
	}
	
	/**

	This method is called when the "About" button is pressed.
	It opens a new window containing information about the application.
	@param event the action event that triggers this method
	@throws Exception when an error occurs while opening the About window.
	*/
	public void getAboutBtn(ActionEvent event) throws Exception{
		AboutFrameController next = new AboutFrameController();
		Platform.runLater(() -> {
			try {
				next.start(EkrutServerUI.primaryStage, scene, status, host_ip, "/gui/AboutFrame.fxml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	/**

	This method is called when the "Import" button is pressed.
	It imports the users table to the MySQL database from an external file.
	@param event the action event that triggers this method
	@throws Exception when an error occurs while importing the users table.
	*/
	public void getImportBtn(ActionEvent event) throws Exception{
		if(EkrutServerUI.isServerOpen) {
			if(mysqlConnection.importUsers()) {
				message_to_gui.appendText("Users has been imported seccessfully\n");
			}
			else {
				message_to_gui.appendText("An error occured while importing users table\n");
			}
		}
		else {
			message_to_gui.appendText("In order to import users you must connect to the server\n");
		}
	}
}