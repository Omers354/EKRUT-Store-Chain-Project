package client;

import javafx.application.Application;

import javafx.stage.Stage;
import logic.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import gui.CustomerMainScreenFrameController;
import gui.FrameController;
import gui.StartFrameController;


/**
 * Extends Application
 * This class constructs the UI for an ekrut client. 
 * It serves the purpose of initiating a javafx scene to be used by the user of our system
 */
public class EkrutClientUI extends Application {
	
	/**
	 * The instance of ekrut client controller
	 */
	public static EkrutClientController client_control; //Only one instance
	
	/**
	 * FrameController type variable to describe the controller of the currently viewed frame
	 */
	public static FrameController frame_ctrl = null;
	
	/**
	 * List of FrameController type representing the list of previous frames
	 */
	public static ArrayList<FrameController> back_frames;
	
	/**
	 * FrameController type variable to describe the controller of the home frame of the current user type
	 */
	public static FrameController home_frame;
	
	  /**
	   * opens the main frame of the program and relevant instances.
	   *
	   * @exception Exception if an error occurs when opening.
	   */
	public static void main(String args[]) throws Exception {
		launch(args);
	} //End main

	/**
	   * Overrides the start method of Application
	   * Starts the UI of the program
	   * @exception Exception if an error occurs when opening.
	   */
	@Override
	public void start(Stage primaryStage) throws Exception {
		frame_ctrl = new StartFrameController(); // Create StudentFrame
		frame_ctrl.start(primaryStage, "/gui/StartFrame.fxml");
		back_frames = new ArrayList<FrameController>();
	}
	
	/**
	   * this method is activated when relevant input from the start frame controller
	   * is sent to the UI
	   * Connects to server
	   */
	public static void Connect() {
		client_control.ekrut_client.awaitResponse = true;
		client_control.accept();
		while(client_control.ekrut_client.awaitResponse) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}