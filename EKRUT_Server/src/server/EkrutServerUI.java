package server;

import javafx.application.Application;
import javafx.stage.Stage;
import logic.Command;
import server.EkrutServer;

import java.io.IOException;
import java.util.Vector;

import gui.ServerFrameController;

/**
 * The EkrutServerUI class is the main class of a server application built using JavaFX and the EkrutServer class.
 * It extends the Application class and overrides its start method, which sets up the GUI for the server using
 * the ServerFrameController class and the ServerFrame.fxml file.
 * The class also contains a main method which launches
 * the application, as well as several other methods for running and stopping the server, displaying messages on the GUI,
 * getting the server instance and handling the database connection.
 * The server listens for connections on a specified
 * port (default is 5555) and uses the mysqlConnection class for database connections. 
 * The class also contains the
 * isServerOpen variable to check whether the server is currently running or not.
 *
 * @author Shahar
 * @version 4.0
 */
public class EkrutServerUI extends Application {

	final public static int DEFAULT_PORT = 5555;

	public static EkrutServer Eserver;

	public static ServerFrameController serverFrameCtrl;
	
	public static boolean isServerOpen = false;
		
	public static Stage primaryStage;
	
	/**

	The main method is the entry point of the application and it calls the launch method of the Application class to start the JavaFX application.
	@param args arguments passed to the main method
	@throws Exception any exception that can be thrown during the execution of the main method
	*/
	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	/**

	The getServer method returns the instance of the EkrutServer class.
	@return EkrutServer instance of the EkrutServer class
	*/
	public static EkrutServer getServer() {
		return Eserver;
	}

	/**

	The start method is overridden from the Application class and it sets up the GUI for the server using the ServerFrameController class and the ServerFrame.fxml file.
	@param primaryStage the primary stage for the application
	@throws Exception any exception that can be thrown during the execution of the start method
	*/
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		serverFrameCtrl = new ServerFrameController();
		serverFrameCtrl.start(primaryStage, "/gui/ServerFrame.fxml");
	}

	/**

	The runServer method starts the server on the specified port number and listens for client connections.
	If the provided port number is not a number, it displays an error message.
	If the connection to the database fails, it also displays an error message.
	@param p port number on which the server should listen for connections
	*/
	public static void runServer(String p) {
		int port = 5555; //Port is setted by default to 5555
		boolean isNumber = true;
		try {
			port = Integer.parseInt(p);
		}
		catch (Throwable t) {
			System.out.println("Port must be a number");
			display("Port must be a number");
			isNumber = false;
		}
		if(isNumber) {
			Eserver = new EkrutServer(port);
			if (mysqlConnection.isMySqlConnectSucceed) {
				try {
					Eserver.listen(); // Start listening for connections
					isServerOpen = true;
				}
				catch (Exception ex) {
					System.out.println("ERROR - Could not listen for clients!");
					display("ERROR - Could not listen for clients!");
				}
			}
			else {
				System.out.println("ERROR - Could not connect to database");
				EkrutServerUI.display("ERROR - Could not connect to database");
			}
		}
	}

	/**

	The display method displays the provided message on the console of the server's GUI.
	@param msg the message to be displayed on the console
	*/
	public static void display(String msg) {
		serverFrameCtrl.displayConsoleMessage(msg);
	}

	/**

	The endServer method stops the server and closes all client connections. It first sends a "stopping_server" command to all clients,
	then stops listening for new connections, closes the existing connections, and sets the server status to closed.
	If the server is not currently running, the method does nothing.
	*/
	public static void endServer() {
		if(isServerOpen) {
			try {
				Command stop = new Command("stopping_server", null);
				mysqlConnection.exitServerTerminate();
				Eserver.sendToAllClients(stop);
				Thread.sleep(100);
				Eserver.stopListening();
				Eserver.close();
				isServerOpen = false;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}