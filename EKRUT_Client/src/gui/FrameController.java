package gui;

import client.EkrutClientUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.Command;
import logic.User;

public abstract class FrameController {
	
	@FXML
	protected Label full_name;
	
	@FXML
	protected Label status;
	
	@FXML
	protected Label user_type;
	
	@FXML
	private Button exit_btn;
	
	@FXML
	protected Button about_btn;
	
	@FXML
	private ImageView ekrut_logo;
	
	@FXML
	private ImageView about_logo;
	
	@FXML
	private ImageView home_logo;
	
	@FXML
	private ImageView logout_logo;
	
	@FXML
	protected ImageView progress_bar;
	
	@FXML
	protected Label message_to_gui;
		
	public Stage primaryStage;
	
	public String curr_resource; 
		
	public void start(Stage primaryStage, String resource) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
		loader.setController(this);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		this.curr_resource = resource;
		ekrut_logo.setImage(new Image(this.getClass().getResourceAsStream("/ekrut.png")));
		about_logo.setImage(new Image(this.getClass().getResourceAsStream("/about.png")));
		home_logo.setImage(new Image(this.getClass().getResourceAsStream("/home.png")));
		logout_logo.setImage(new Image(this.getClass().getResourceAsStream("/logout.png")));
		additionalChanges();
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/ekrut.png")));
		primaryStage.setTitle("Ekrut Client");
		primaryStage.setScene(scene);
		primaryStage.show();
		EkrutClientUI.frame_ctrl = this;
		this.primaryStage = primaryStage;
	}
	
	public abstract void additionalChanges();
	
	public void displayMessage(String msg) {
		Platform.runLater(()->{message_to_gui.setText(msg);});
	}
	
	public void getExitBtn(ActionEvent event) throws Exception{
		System.out.println("Exit EKRUT Client");
		if(EkrutClientUI.client_control.ekrut_client.isConnected) {
			User user = EkrutClientUI.client_control.ekrut_client.user;
			EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("disconnect", user));
			EkrutClientUI.client_control.quit();
		}
		System.exit(0);
	}
	
	public void getAboutBtn(ActionEvent event) throws Exception{
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("about", null));
	}
	
	public void getHomeBtn(ActionEvent event) throws Exception{
		EkrutClientUI.back_frames.clear();
		EkrutClientUI.home_frame.start(primaryStage, "/gui/CustomerMainScreenFrame.fxml");
	}
	
	public void getLogoutBtn(ActionEvent event) throws Exception{		
		User user = EkrutClientUI.client_control.ekrut_client.user;
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("disconnect", user));
	}
	
	public void getBack(ActionEvent event) throws Exception{
		int back_frames_size = EkrutClientUI.back_frames.size();
		FrameController back = EkrutClientUI.back_frames.get(back_frames_size - 1);
		EkrutClientUI.back_frames.remove(back_frames_size - 1);
		back.start(primaryStage, back.curr_resource);
	}
}