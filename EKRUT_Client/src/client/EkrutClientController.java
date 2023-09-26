package client;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;

import logic.Command;

/**
 * This class constructs the controller for an ekrut client. 
 * It serves the purpose of initiating a Client object with the relevant information
 * from the user UI
 */
public class EkrutClientController {
	// Class variables *************************************************

	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;

	// Instance variables **********************************************

	/**
	 * The instance of the ekrut client in this interface.
	 */
	public EkrutClient ekrut_client;
	
	/**
	 * String describing the server host address
	 */
	public String host;
	
	/**
	 * int describing the port of the server
	 */
	public int port;
	
	// Constructors ****************************************************

	/**
	 * Constructs an instance of the EkrutClientController.
	 *
	 * @param host The host to connect to.
	 * @param port The port to connect on.
	 */
	public EkrutClientController(String host, int port) {
		try {
			ekrut_client = new EkrutClient(host, port, this);
			System.out.println("Connection Succeed");
			this.host = host;
			this.port = port;
		}
		catch (IOException exception) {}
	}

	// Instance methods ************************************************

	/**
	 * This method waits for input from the UI. Once it is received, it sends
	 * the client's instance a request to connect.
	 */
	public void accept() {
		
		ekrut_client.handleMessageFromClientUI(new Command("connect", null)); // First connection
	}

	/**
	 * This method displays a message into the screen
	 * @param message The string to be displayed.
	 */
	public void display(String message) {
		System.out.println("> " + message);
		EkrutClientUI.frame_ctrl.displayMessage(message);
	}
	
	/**
	 * This method terminates the client.
	 */
	public void quit() {
		ekrut_client.quit();
	}
}
