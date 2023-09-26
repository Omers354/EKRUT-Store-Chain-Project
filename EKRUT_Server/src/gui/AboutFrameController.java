package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import server.EkrutServerUI;

/**

The AboutFrameController class is a JavaFX controller for an About Frame in EKRUT Server.

The class uses the FXML framework to define the layout of the About Frame and to handle events.

@author [Shahar]

@version [4.0]
*/
public class AboutFrameController {

/**

FXML ImageView element for displaying the ekrut logo
*/
@FXML
private ImageView ekrut_logo;
/**

FXML ImageView element for displaying the server logo
*/
@FXML
private ImageView server_logo;
/**

FXML ImageView element for displaying the import users logo
*/
@FXML
private ImageView import_users_logo;
/**

FXML Label element for displaying the status of the server
*/
@FXML
private Label status;
/**

FXML Label element for displaying the host IP address
*/
@FXML
private Label host_ip;
/**

Scene variable for holding the scene of the server frame
*/
private Scene sceneServer;
/**
The start method is used to initialize and display the About Frame. It takes in the primary stage, the scene of the server frame, the status label, the host IP label, and the resource path as its parameters.
@param primaryStage the main stage of the program
@param sceneServer the scene of the server frame
@param status the label for displaying the status of the server
@param host_ip the label for displaying the host IP address
@param resource the path of the FXML resource for the about frame
@throws Exception if an error occurs while loading the FXML resource
*/
	public void start(Stage primaryStage, Scene sceneServer, Label status, Label host_ip, String resource) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
		loader.setController(this); // You need to set this instance as the controller.
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ekrut_logo.setImage(new Image(this.getClass().getResourceAsStream("/ekrut.png")));
		server_logo.setImage(new Image(this.getClass().getResourceAsStream("/server.png")));
		import_users_logo.setImage(new Image(this.getClass().getResourceAsStream("/import_users.png")));
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/ekrut.png")));
		primaryStage.setTitle("EKRUT Server");
		primaryStage.setScene(scene);
		primaryStage.show();
		this.status.setText(status.getText());
		if(status.getText().equals("Online")){
			this.host_ip.setStyle("-fx-text-fill: #00FF00");
			this.status.setStyle("-fx-text-fill: #00FF00");
		}
		this.host_ip.setText(host_ip.getText());
		this.sceneServer = sceneServer;
	}
	
	/**
	The getExitBtn method is used to handle the event when the exit button is pressed. It ends the server and exits the program.
	@param event the ActionEvent triggered by the exit button being pressed
	@throws Exception if an error occurs while ending the server
	*/
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("Exit EKRUT Server");
		EkrutServerUI.endServer();
		System.exit(0);
	}
	
	/**
	The getServerBtn method is used to handle the event when the server button is pressed. It navigates the user to the server frame.
	*/
	public void getServerBtn() {
		ServerFrameController next = new ServerFrameController();
		Platform.runLater(() -> {
			try {
				EkrutServerUI.primaryStage.setScene(sceneServer);
				EkrutServerUI.primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}