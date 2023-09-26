package server;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

import logic.Branch;
import logic.Command;
import logic.Customer;
import logic.Delivery;
import logic.Inventory;
import logic.Order;
import logic.Product;
import logic.Report;
import logic.Restock;
import logic.Sale;
import logic.Subscriber;
import logic.User;
import ocsf.server.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *

 * @author Shahar
 * @version 4.0
 */
public class EkrutServer extends AbstractServer {
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the ekrut server.
	 *
	 * @param port The port number to connect on.
	 */
	public EkrutServer(int port) {
		super(port);
		mysqlConnection.awaitResponse = true;
		mysqlConnection.ConnectDb(
				"jdbc:mysql://localhost/" + EkrutServerUI.serverFrameCtrl.getDbName() + "?serverTimezone=IST",
				EkrutServerUI.serverFrameCtrl.getDbUsername(), EkrutServerUI.serverFrameCtrl.getDbPass());
		while (mysqlConnection.awaitResponse) {
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (mysqlConnection.isDriverSuccssed) {
			EkrutServerUI.display("Driver definition succeed");
		}
		else {
			EkrutServerUI.display("Driver definition failed");
		}
		if (mysqlConnection.isMySqlConnectSucceed) {
			EkrutServerUI.display("SQL connection succeed");
		}
		else {
			EkrutServerUI.display("SQL connection failed");
		}
		EndOfMonthThread endOfMonthThread = new EndOfMonthThread();
		Thread thread = new Thread(endOfMonthThread);
		thread.start();
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		if (msg instanceof Command) {
			
			Command cmd = (Command) msg;
			
			/**
			 * Checks if the incoming message is a command and handles the "connect" command.
			 * 
			 * @param msg The incoming message
			 * @param client The client that sent the message
			 */
			
			if (cmd.getId().equals("connect")) {
				// First connection
				System.out.println("Client " + client.getId() + " Connected successfully");
				EkrutServerUI.display("Client " + client.getId() + " Connected successfully");
			}
			
			/**
			 * This method handles the "login_attempt" command received by the server.
			 * It retrieves an ArrayList of users objects from the "mysqlConnection" class 
			 * using the "checkUsernameAndPassword" method, passing in the data from the command.
			 * The ArrayList is then sent back to the client as a new command with the same ID and 
			 * the ArrayList as the data.
			 * For Fast Login using the "checkSubscriberNumber" method, passing in the data from the command.
			 * 
			 * @param cmd A command object with an ID of "login_attempt" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			if (cmd.getId().equals("login_attempt")) {

				if (cmd.getData() instanceof ArrayList) {
					Command send_back = mysqlConnection.checkUsernameAndPassword(cmd.getData());
					try {
						if (send_back.getData() instanceof User) {
							if(((User)send_back.getData()).getIsLoggedIn() == 0) {
								mysqlConnection.changeUserLoginStatus((User)send_back.getData());
							}
							System.out.println("Login info is verified and sent back to the client " + client.getId());
							EkrutServerUI.display("Login info is verified and sent back to the client " + client.getId());
						}
						else {
							System.out.println("Login info is denied and sent back to the client " + client.getId());
							EkrutServerUI.display("Login info is denied and sent back to the client " + client.getId());
						}
						client.sendToClient((Object) send_back);
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				else {
					Command send_back = mysqlConnection.checkSubscriberNumber(cmd.getData());
					try {
						if (send_back.getData() instanceof User) {
							if(((User)send_back.getData()).getIsLoggedIn() == 0) {
								mysqlConnection.changeUserLoginStatus((User)send_back.getData());
							}
							System.out.println("Login info is verified and sent back to the client " + client.getId());
							EkrutServerUI.display("Login info is verified and sent back to the client " + client.getId());
						}
						else {
							System.out.println("Login info is denied and sent back to the client " + client.getId());
							EkrutServerUI.display("Login info is denied and sent back to the client " + client.getId());
						}
						client.sendToClient((Object) send_back);
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			/**
			 * This method handles the "change_collect_order_status" command received by the server.
			 * It retrieves boolean isCollected objects from the "mysqlConnection" class 
			 * using the "is_order_status_changed" method, passing in the data from the command.
			 * The ArrayList is then sent back to the client as a new command with the same ID and 
			 * the ArrayList as the data.
			 * 
			 * @param cmd A command object with an ID of "change_collect_order_status" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			if (cmd.getId().equals("change_collect_order_status")) {
				boolean isCollected = mysqlConnection.collectOrder(cmd.getData());
				try {
					client.sendToClient(new Command("is_order_status_changed", (Object)isCollected));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * This method handles the "disconnect" command received by the server.
			 * It disconnect from the server 
			 * using the "changeUserLoginStatus" method, change User Login Status in mysql.
			 * The ArrayList is then sent back to the client as a new command with the same ID and 
			 * the ArrayList as the data.
			 * 
			 * @param cmd A command object with an ID of "disconnect" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			if (cmd.getId().equals("disconnect")) {
				if(((User)cmd.getData()).getIsLoggedIn() == 1) {
					mysqlConnection.changeUserLoginStatus((User)cmd.getData());
					try {
						Command send_back = new Command("disconnected", null);
						client.sendToClient((Object) send_back);
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	
			/**
			 * This method handles the "get_inventory" command received by the server.
			 * It retrieves an ArrayList of inventory objects from the "mysqlConnection" class 
			 * using the "getProductsTable" method, passing in the data from the command.
			 * The ArrayList is then sent back to the client as a new command with the same ID and 
			 * the ArrayList as the data.
			 * 
			 * @param cmd A command object with an ID of "get_inventory" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			if (cmd.getId().equals("get_inventory")) {
				ArrayList<Product> inventoryTable = mysqlConnection.getProductsTable(cmd.getData());
				try {
					client.sendToClient(new Command("get_inventory", (Object) inventoryTable));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
          
			/**
			 * This method handles the "get_ThresholdPoint" command received by the server.
			 * It retrieves an ArrayList of Product objects from the "mysqlConnection" class 
			 * using the "getThresholdPointCounter" method, passing in the data from the command.
			 * The ArrayList is then sent back to the client as a new command with the same ID and 
			 * the ArrayList as the data.
			 * 
			 * @param cmd A command object with an ID of "get_ThresholdPoint" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			if (cmd.getId().equals("get_ThresholdPoint")) {
				ArrayList<Product> product = mysqlConnection.getThresholdPointCounter(cmd.getData());
				try {
					client.sendToClient(new Command("get_ThresholdPoint", (Object) product));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * This method handles the "get_orders" command received by the server.
			 * It retrieves a Report objects from the "mysqlConnection" class 
			 * using the "getOrderTable" method, passing in the data from the command.
			 * The Report is then sent back to the client as a new command with the same ID and 
			 * the Report as the data.
			 * 
			 * @param cmd A command object with an ID of "get_orders" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			 if (cmd.getId().equals("get_orders")) {
					Report report = mysqlConnection.getOrderTable(cmd.getData());
					try {
						client.sendToClient(new Command("get_Report", (Object)report));
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			 
				/**
				 * This method handles the "create_numToOrderReport" command received by the server.
				 * It retrieves a report Numer from the "mysqlConnection" class 
				 * using the "setOrderReportNumber" method, passing in the data from the command.
				 * The Report is then sent back to the client as a new command with the same ID and 
				 * the Report as the data.
				 * 
				 * @param cmd A command object with an ID of "create_numToOrderReport" and data.
				 * @param mysqlConnection an object of the class that handles the database connection.
				 * @param client the client that sent the command
				 * 
				 * @throws IOException if there is a problem sending the data to the client.
				 */
			 if (cmd.getId().equals("create_numToOrderReport")) {
					int reportNum = mysqlConnection.setOrderReportNumber();
					
					try {
						client.sendToClient(new Command("create_numToOrderReport", (Object)reportNum));
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}

			 
				/**
				 * This method handles the "create_activityReport" command received by the server.
				 * It retrieves a Report objects from the "mysqlConnection" class 
				 * using the "getCustomerActivityReport" method, passing in the data from the command.
				 * The Report is then sent back to the client as a new command with the same ID and 
				 * the Report as the data.
				 * 
				 * @param cmd A command object with an ID of "create_activityReport" and data.
				 * @param mysqlConnection an object of the class that handles the database connection.
				 * @param client the client that sent the command
				 * 
				 * @throws IOException if there is a problem sending the data to the client.
				 */
			 if (cmd.getId().equals("create_activityReport")) {
				 Report report= mysqlConnection.getCustomerActivityReport(cmd.getData());
					try {
						client.sendToClient(new Command("create_activityReport", (Object) report));
					}
					catch (IOException e) {
						e.printStackTrace();
					}	
			 }
			 
				/**
				 * This method handles the "get_threshold_points" command received by the server.
				 * It retrieves an ArrayList of Branchs and regions objects from the "mysqlConnection" class 
				 * using the "getBranchesTable" method, passing in the data from the command.
				 * The ArrayList is then sent back to the client as a new command with the same ID and 
				 * the ArrayList as the data.
				 * 
				 * @param cmd A command object with an ID of "get_threshold_points" and data.
				 * @param mysqlConnection an object of the class that handles the database connection.
				 * @param client the client that sent the command
				 * 
				 * @throws IOException if there is a problem sending the data to the client.
				 */
			if (cmd.getId().equals("get_threshold_points")) {
				String region = (String)cmd.getData();
				ArrayList<Branch> all_branches = mysqlConnection.getBranchesTable();
				ArrayList<String> region_branches = new ArrayList<String>();
				for(Branch b : all_branches) {
					if(b.getRegion_Name().equals(region)) {
						region_branches.add(b.getBranch_Name());
					}
				}
				ArrayList<ArrayList<Inventory>> threshold_table = mysqlConnection.getInventoryTable(region_branches);
				try {
					client.sendToClient(new Command("get_threshold_points", (Object) threshold_table));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * This method handles the "set_threshold_point" command received by the server.
			 * It retrieves boolean is_threshold_updated from the "mysqlConnection" class 
			 * using the "setNewThresholdPoints" method, passing in the data from the command.
			 * 
			 * @param cmd A command object with an ID of "set_threshold_point" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			if (cmd.getId().equals("set_threshold_point")) {
				boolean is_threshold_updated = mysqlConnection.setNewThresholdPoints(cmd.getData());
				
				try {
					client.sendToClient(new Command("set_threshold_point", (Object) is_threshold_updated));
					if(is_threshold_updated) {
						EkrutServerUI.display("Threshold point updated successfully, client " + client.getId() + " has been notified");
					}
					else {
						EkrutServerUI.display("An error has occured while updating threshold point, client " + client.getId() + " has been notified");
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * This method handles the "create_sale_template" command received by the server.
			 * using the "CreateSaleTemplate" method, passing in the data from the command.
			 * 
			 * @param cmd A command object with an ID of "create_sale_template" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			if (cmd.getId().equals("create_sale_template")) {
				mysqlConnection.CreateSaleTemplate(cmd.getData());
				EkrutServerUI.display("Threshold point updated successfully, client " + client.getId() + " has been notified");
			}
					
			/**
			 * This method handles the "get_customer_data" command received by the server.
			 * It retrieves an ArrayList of pending_customers objects from the "mysqlConnection" class 
			 * using the "getNoneOrPendingCustomers" method, passing in the data from the command.
			 * The ArrayList is then sent back to the client as a new command with the same ID and 
			 * the ArrayList as the data.
			 * 
			 * @param cmd A command object with an ID of "get_customer_data" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			if (cmd.getId().equals("get_customer_data")) {
				ArrayList<User> pending_customers = mysqlConnection.getNoneOrPendingCustomers(cmd.getData());
				try {
					client.sendToClient(new Command("get_customer_data", (Object)pending_customers));
					if(pending_customers != null) {
						EkrutServerUI.display("Pending customers sent successfully to client " + client.getId());
					}
					else {
						EkrutServerUI.display("No pending customers to send, client " + client.getId() + " has been notified");
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * This method handles the "get_all_customers" command received by the server.
			 * It retrieves an ArrayList of all_customers objects from the "mysqlConnection" class 
			 * using the "getAllCustomers" method, passing in the data from the command.
			 * The ArrayList is then sent back to the client as a new command with the same ID and 
			 * the ArrayList as the data.
			 * 
			 * @param cmd A command object with an ID of "get_all_customers" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			else if (cmd.getId().equals("get_all_customers")) {
				ArrayList<Customer> all_customers = mysqlConnection.getAllCustomers(cmd.getData());
				try {
					client.sendToClient(new Command("return_all_customers", (Object)all_customers));
					if(all_customers != null) {
						EkrutServerUI.display("Pending customers sent successfully to client " + client.getId());
					}
					else {
						EkrutServerUI.display("No pending customers to send, client " + client.getId() + " has been notified");
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * This method handles the "set_customer_as_subscriber" command received by the server.
			 * It retrieves boolean is_set_succeed objects from the "mysqlConnection" class 
			 * using the "setCustomerAsSubscriber" method, passing in the data from the command.
			 * 
			 * @param cmd A command object with an ID of "set_customer_as_subscriber" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			else if (cmd.getId().equals("set_customer_as_subscriber")) {
				boolean is_set_succeed = mysqlConnection.setCustomerAsSubscriber(cmd.getData());
				try {
					client.sendToClient(new Command("set_customer_as_subscriber", (Object)is_set_succeed));
					if(is_set_succeed) {
						EkrutServerUI.display("Customer set successfully to subscriber, and sent to client " + client.getId());
					}
					else {
						EkrutServerUI.display("No pending customers to send, client " + client.getId() + " has been notified");
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * This method handles the "approve_customer" command received by the server.
			 * It retrieves boolean is_setting_succeed objects from the "mysqlConnection" class 
			 * using the "approveCustomer" method, passing in the data from the command.
			 * 
			 * @param cmd A command object with an ID of "approve_customer" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			else if (cmd.getId().equals("approve_customer")) {
				String username = (String)cmd.getData();
				boolean is_setting_succeed = mysqlConnection.approveCustomer(username);
				try {
					client.sendToClient(new Command("approve_customer", (Object)is_setting_succeed));
					if(is_setting_succeed) {
						EkrutServerUI.display("Username " + username + " has been set as a customer successfully, client " + client.getId() + " has been notified");
					}
					else {
						EkrutServerUI.display("An error has occured while setting username " + username + " as a customer, client " + client.getId() + " has been notified");
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * This method handles the "set_new_user_as_customer" command received by the server.
			 * It retrieves an ArrayList of user objects from the "mysqlConnection" class 
			 * using the "setNewUserAsCustomer" method, passing in the data from the command.
			 * The ArrayList is then sent back to the client as a new command with the same ID and 
			 * the ArrayList as the data.
			 * 
			 * @param cmd A command object with an ID of "set_new_user_as_customer" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			else if (cmd.getId().equals("set_new_user_as_customer")) {
				ArrayList<Object> arr = (ArrayList<Object>)cmd.getData();
				User user = (User)arr.get(0);
				boolean is_registration_succeed = mysqlConnection.setNewUserAsCustomer(cmd.getData());
				try {
					client.sendToClient(new Command("set_new_user_as_customer", (Object)is_registration_succeed));
					if(is_registration_succeed) {
						EkrutServerUI.display("Username " + user.getUsername() + " has been set as a customer successfully, client " + client.getId() + " has been notified");
					}
					else {
						EkrutServerUI.display("An error has occured while setting username " + user.getUsername() + " as a customer, client " + client.getId() + " has been notified");
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * This method handles the "set_request_restock" command received by the server.
			 * It retrieves boolean is_request_succeed objects from the "mysqlConnection" class 
			 * using the "setRequestRestockTable" method, passing in the data from the command.
			 * 
			 * @param cmd A command object with an ID of "set_request_restock" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			else if (cmd.getId().equals("set_request_restock")) {
				ArrayList<String> arr = (ArrayList<String>)cmd.getData();
				boolean is_request_succeed = mysqlConnection.setRequestRestockTable(cmd.getData());
				try {
					client.sendToClient(new Command("set_request_restock", (Object)is_request_succeed));
					if(is_request_succeed) {
						EkrutServerUI.display("Restock request has been inserted successfully");
					}
					else {
						EkrutServerUI.display("A problem has occurred trying to insert request to the restock table");
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * This method handles the "get_restock_table" command received by the server.
			 * It retrieves an ArrayList of arr_restock_table objects from the "mysqlConnection" class 
			 * using the "getRestockTable" method, passing in the data from the command.
			 * The ArrayList is then sent back to the client as a new command with the same ID and 
			 * the ArrayList as the data.
			 * 
			 * @param cmd A command object with an ID of "get_restock_table" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			else if (cmd.getId().equals("get_restock_table")) {
				ArrayList<Restock> arr_restock_table = mysqlConnection.getRestockTable(cmd.getData());
				try {
					client.sendToClient(new Command("send_restock_table", (Object)arr_restock_table));
					if(arr_restock_table != null) {
						EkrutServerUI.display("Restock table has been sent to client: " + client.getId());
					}
					else {
						EkrutServerUI.display("A problem has occurred while trying to send the restock table to client: " + client.getId());
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}

			/**
			 * This method handles the "get_sale_table" command received by the server.
			 * It retrieves an ArrayList of arr_sale_table objects from the "mysqlConnection" class 
			 * using the "getSaleTable" method, passing in the data from the command.
			 * The ArrayList is then sent back to the client as a new command with the same ID and 
			 * the ArrayList as the data.
			 * 
			 * @param cmd A command object with an ID of "get_sale_table" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			
			else if (cmd.getId().equals("get_sale_table")) {
				ArrayList<Sale> arr_sale_table = mysqlConnection.getSaleTable(cmd.getData());
				try {
					client.sendToClient(new Command("send_sale_table", (Object)arr_sale_table));
					if(arr_sale_table != null) {
						EkrutServerUI.display("Sale table has been sent to client: " + client.getId());
					}
					else {
						EkrutServerUI.display("A problem has occurred while trying to send the sale table to client: " + client.getId());
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			/**
			 * This method handles the "activate_sale" command received by the server.
			 * using the "ActivateSale" method, passing in the data from the command.
			 * 
			 * @param cmd A command object with an ID of "activate_sale" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			else if (cmd.getId().equals("activate_sale")) {
				mysqlConnection.ActivateSale(cmd.getData());
			}
			
			/**
			 * This method handles the "end_sale" command received by the server.
			 * using the "EndSale" method, passing in the data from the command.
			 * 
			 * @param cmd A command object with an ID of "end_sale" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			else if (cmd.getId().equals("end_sale")) {
				mysqlConnection.EndSale(cmd.getData());
			}
			
			/**
			 * This method handles the "set_new_stock" command received by the server.
			 * * It retrieves boolean is_set_new_stock_succeed objects from the "mysqlConnection" class 
			 * using the "setNewStock" method, passing in the data from the command.
			 * 
			 * @param cmd A command object with an ID of "set_new_stock" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			else if (cmd.getId().equals("set_new_stock")) {
				
				boolean is_set_new_stock_succeed = mysqlConnection.setNewStock(cmd.getData());
				try {
					client.sendToClient(new Command("set_new_stock", (Object)is_set_new_stock_succeed));
					if(is_set_new_stock_succeed) {
						EkrutServerUI.display("Updating new stock done successfully");
					}
					else {
						EkrutServerUI.display("A problem has occurred trying to update the new stock");
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * This method handles the "get_products" command received by the server.
			 * It retrieves an ArrayList of productsTable objects from the "mysqlConnection" class 
			 * using the "getProductsTable" method, passing in the data from the command.
			 * The ArrayList is then sent back to the client as a new command with the same ID and 
			 * the ArrayList as the data.
			 * 
			 * @param cmd A command object with an ID of "get_products" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			 if (cmd.getId().equals("get_products")) {
					ArrayList<Product> productsTable = mysqlConnection.getProductsTable();
					
					try {
						client.sendToClient(new Command("return_products", (Object)productsTable));
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			 
				/**
				 * This method handles the "update_order" command received by the server.
				 * using the "updateOrder" method, passing in the data from the command.
				 * 
				 * @param cmd A command object with an ID of "update_order" and data.
				 * @param mysqlConnection an object of the class that handles the database connection.
				 * @param client the client that sent the command
				 * 
				 * @throws IOException if there is a problem sending the data to the client.
				 */
			 if (cmd.getId().equals("update_order")) {
					mysqlConnection.updateOrder(cmd.getData());					
				}
			 
				/**
				 * This method handles the "update_sale_table" command received by the server.
				 * using the "updateSaleTable" method, passing in the data from the command.
				 * 
				 * @param cmd A command object with an ID of "update_sale_table" and data.
				 * @param mysqlConnection an object of the class that handles the database connection.
				 * @param client the client that sent the command
				 * 
				 * @throws IOException if there is a problem sending the data to the client.
				 */
			 if (cmd.getId().equals("update_sale_table")) {
					mysqlConnection.updateSaleTable(cmd.getData());					
				}
			 
				/**
				 * This method handles the "get_order_number" command received by the server.
				 * using the "setOrderNumber" method, passing in the data from the command.
				 * 
				 * @param cmd A command object with an ID of "get_order_number" and data.
				 * @param mysqlConnection an object of the class that handles the database connection.
				 * @param client the client that sent the command
				 * 
				 * @throws IOException if there is a problem sending the data to the client.
				 */
			 if (cmd.getId().equals("get_order_number")) {
					int orderNumber = mysqlConnection.setOrderNumber();
					try {
						client.sendToClient(new Command("return_order_number", (Object)orderNumber));
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			 
				/**
				 * This method handles the "get_pickup_codes" command received by the server.
				 * using the "getCodesTable" method, passing in the data from the command.
				 * 
				 * @param cmd A command object with an ID of "get_pickup_codes" and data.
				 * @param mysqlConnection an object of the class that handles the database connection.
				 * @param client the client that sent the command
				 * 
				 * @throws IOException if there is a problem sending the data to the client.
				 */
			 if (cmd.getId().equals("get_pickup_codes")) {
				 ArrayList<String> codesTable = mysqlConnection.getCodesTable();
					try {
						client.sendToClient(new Command("return_pickup_codes",(Object)codesTable));
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			 
				/**
				 * This method handles the "get_subscribers_table" command received by the server.
			     * It retrieves an ArrayList of subscribersTable objects from the "mysqlConnection" class 
			     * using the "getSubscriberTable" method, passing in the data from the command.
			     * The ArrayList is then sent back to the client as a new command with the same ID and 
			     * the ArrayList as the data.
			     * 
				 * @param cmd A command object with an ID of "get_subscribers_table" and data.
				 * @param mysqlConnection an object of the class that handles the database connection.
				 * @param client the client that sent the command
				 * 
				 * @throws IOException if there is a problem sending the data to the client.
				 */
			 if (cmd.getId().equals("get_subscribers_table")) {
				 ArrayList<Subscriber> subscribersTable = mysqlConnection.getSubscriberTable();
					try {
						client.sendToClient(new Command("return_subscribers_table",(Object)subscribersTable));
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			 
				/**
				 * This method handles the "get_branches_table" command received by the server.
			     * It retrieves an ArrayList of branchesTable objects from the "mysqlConnection" class 
			     * using the "getBranchesTable" method, passing in the data from the command.
			     * The ArrayList is then sent back to the client as a new command with the same ID and 
			     * the ArrayList as the data.
			     * 
				 * @param cmd A command object with an ID of "get_branches_table" and data.
				 * @param mysqlConnection an object of the class that handles the database connection.
				 * @param client the client that sent the command
				 * 
				 * @throws IOException if there is a problem sending the data to the client.
				 */
			 if (cmd.getId().equals("get_branches_table")) {
				 ArrayList<Branch> branchesTable = mysqlConnection.getBranchesTable();
					try {
						client.sendToClient(new Command("return_branches_table",(Object)branchesTable));
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			 
				/**
				 * This method handles the "create_delivery" command received by the server.
				 * using the "createNewDelivery" method, passing in the data from the command.
				 * 
				 * @param cmd A command object with an ID of "create_delivery" and data.
				 * @param mysqlConnection an object of the class that handles the database connection.
				 * @param client the client that sent the command
				 * 
				 * @throws IOException if there is a problem sending the data to the client.
				 */
			 if (cmd.getId().equals("create_delivery")) {
					mysqlConnection.createNewDelivery(cmd.getData());
				}
			 
				/**
				 * This method handles the "create_order" command received by the server.
				 * using the "createNewOrder" method, passing in the data from the command.
				 * 
				 * @param cmd A command object with an ID of "create_order" and data.
				 * @param mysqlConnection an object of the class that handles the database connection.
				 * @param client the client that sent the command
				 * 
				 * @throws IOException if there is a problem sending the data to the client.
				 */
			 if (cmd.getId().equals("create_order")) {
					mysqlConnection.createNewOrder(cmd.getData());
				}
			 
				/**
				 * This method handles the "create_productInOrder" command received by the server.
				 * using the "createNewProductsInOrder" method, passing in the data from the command.
				 * 
				 * @param cmd A command object with an ID of "create_productInOrder" and data.
				 * @param mysqlConnection an object of the class that handles the database connection.
				 * @param client the client that sent the command
				 * 
				 * @throws IOException if there is a problem sending the data to the client.
				 */
			 if (cmd.getId().equals("create_productInOrder")) {
					mysqlConnection.createNewProductsInOrder(cmd.getData());
				}

				/**
				 * This method handles the "update_subscriber_is_First_Purchase" command received by the server.
				 * using the "updateSubscriberIsFirstPurchase" method, passing in the data from the command.
				 * 
				 * @param cmd A command object with an ID of "update_subscriber_is_First_Purchase" and data.
				 * @param mysqlConnection an object of the class that handles the database connection.
				 * @param client the client that sent the command
				 * 
				 * @throws IOException if there is a problem sending the data to the client.
				 */
			if (cmd.getId().equals("update_subscriber_is_First_Purchase")) {
				mysqlConnection.updateSubscriberIsFirstPurchase(cmd.getData());
			}
			
			/**
			 * This method handles the "get_delivery_table" command received by the server.
		     * It retrieves an ArrayList of arr_deliv objects from the "mysqlConnection" class 
		     * using the "getDeliveries" method, passing in the data from the command.
		     * The ArrayList is then sent back to the client as a new command with the same ID and 
		     * the ArrayList as the data.
		     * 
			 * @param cmd A command object with an ID of "get_delivery_table" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			if (cmd.getId().equals("get_delivery_table")) {
				ArrayList<Delivery> arr_deliv = mysqlConnection.getDeliveries(cmd.getData());
				
				Command send_table = new Command("set_delivery_table", arr_deliv);
				try {
					client.sendToClient(send_table);
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			/**
			 * This method handles the "update_delivery" command received by the server.
			 * using the "updateDelivery" method, passing in the data from the command.
			 * 
			 * @param cmd A command object with an ID of "update_delivery" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			if (cmd.getId().equals("update_delivery")) {
				mysqlConnection.updateDelivery(cmd.getData());
			}
			
			/**
			 * This method handles the "get_active_deliveries" command received by the server.
		     * It retrieves an ArrayList of arr_deliv objects from the "mysqlConnection" class 
		     * using the "getActiveDeliveries" method, passing in the data from the command.
		     * The ArrayList is then sent back to the client as a new command with the same ID and 
		     * the ArrayList as the data.
		     * 
			 * @param cmd A command object with an ID of "get_active_deliveries" and data.
			 * @param mysqlConnection an object of the class that handles the database connection.
			 * @param client the client that sent the command
			 * 
			 * @throws IOException if there is a problem sending the data to the client.
			 */
			if (cmd.getId().equals("get_active_deliveries")) {
				ArrayList<Delivery> arr_deliv = mysqlConnection.getActiveDeliveries(cmd.getData());
				
				Command send_table = new Command("set_delivery_table", arr_deliv);
				try {
					client.sendToClient(send_table);
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
		EkrutServerUI.display("Server listening for connections on port " + getPort());
	}
	@Override
	protected void serverStopped() {
		System.out.println("Server stopped listening for connections");
		EkrutServerUI.display("Server stopped listening for connections");
	}

	public static String getIP() {
		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip.toString();
	}

	public static String getHost() {
		InetAddress ip = null;
		String hostname = null;
		try {
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return hostname;
	}
}
//End of EkrutServer class