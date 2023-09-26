// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import logic.Branch;
import logic.Ceo;
import logic.Command;
import logic.Customer;
import logic.Delivery;
import logic.DeliveryOperator;
import logic.Inventory;
import logic.MarketingWorker;
import logic.Order;
import logic.Product;
import logic.RegionalManager;
import logic.Report;
import logic.Restock;
import logic.Role;
import logic.Sale;
import logic.Subscriber;
import logic.User;
import logic.UserType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import gui.AboutFrameController;
import gui.ActivateSaleFrameController;
import gui.ApproveDeliveryFrameController;
import gui.CEOChooseReportFrameController;
import gui.CEOMainScreenFrameController;
import gui.CatalogFrameController;
import gui.CheckOutFrameController;
import gui.ChooseBranchFrameController;
import gui.ChooseReportFrameController;
import gui.CollectOrderFrameController;
import gui.CustomerActivityReportController;
import gui.CustomerMainScreenFrameController;
import gui.CustomerManagementFrameController;
import gui.CustomerRegistrationFrameController;
import gui.DeliveryFrameController;
import gui.DeliveryWorkerMainScreenFrameController;
import gui.DoneDeliveryFrameController;
import gui.EndSaleFrameController;
import gui.FinishOrderFrameController;
import gui.InventoryReportController;
import gui.LoginFrameController;
import gui.ManageCartFrameController;
import gui.MarketingManagerMainScreenFrameController;
import gui.MarketingWorkerMainScreenFrameController;
import gui.OperationWorkerMainScreenFrameController;
import gui.OrderCatalogFrameController;
import gui.PaymentFrameController;
import gui.PieChartReportController;
import gui.RegionalManagerMainScreenFrameController;
import gui.RequestRestockFrameController;
import gui.RestockInventoryFrameController;
import gui.ServiceRepresentativeMainScreenFrameController;
import gui.SetSaleTemplateFrameController;
import gui.SetSubscriberFrameController;
import gui.SetThresholdPointFrameController;
import gui.StartFrameController;

import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class EkrutClient extends AbstractClient {

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	EkrutClientController client_ctrl;

	/**
	 * User type variable. allows for saving the current Client User
	 */
	public User user;
	
	/**
	 * List of Product describing the inventory table, for further use with some client functions
	 */
	private ArrayList<Product> inventoryTable = new ArrayList<>();
	
	/**
	 * List of Product describing the products table, for further use with some client functions
	 */
	private ArrayList<Product> productsTable = new ArrayList<>();
	
	/**
	 * List of String describing the pick up codes from the orders table, for further use with some client functions
	 */
	public ArrayList<String> PickupCodes = new ArrayList<>(); // to store all pickup codes that were given (prevent duplicates).
	
	/**
	 * List of Subscribers type describing the subscriber table, for further use with some client functions
	 */
	public ArrayList<Subscriber> SubscriberTable = new ArrayList<>();
	
	/**
	 * List of Branch type describing the branch table, for further use with some client functions
	 */
	public ArrayList<Branch> BranchTable = new ArrayList<>();
	
	/**
	 * String variable for describing the branch of the current User (if it has one)
	 */
	public String onlineBranch;
	
	/**
	 * List of Inventory Lists describing the inventory table, separated by branches, for further use with some client functions
	 */
	public ArrayList<ArrayList<Inventory>> threshold_table;
	
	/**
	 * List of Users type describing the pending customers from customers table, for further use with some client functions
	 */
	public ArrayList<User> pending_customers;
	
	/**
	 * List of Customers type describing the customers from customers table, for further use with some client functions
	 */
	public ArrayList<Customer> all_customers;
	
	/**
	 * List of Restock type describing the details of all entries in Restock table, for further use with some client functions
	 */
	public ArrayList<Restock> arr_restock_table;
	
	/**
	 * List of Delivery type describing the details of all entries in Delivery table, for further use with some client functions
	 */
	public ArrayList<Delivery> arr_delivery_table;
	
	/**
	 * Boolean flag describing if the client is waiting for a response from server
	 */	
	public boolean awaitResponse = false;

	/**
	 * Boolean flag describing if the client is connected to the server
	 */
	public boolean isConnected = false;
	
	/**
	 * Boolean flag describing an option to postpone payment, for further use during the order process
	 */
	public boolean PostponePayment = false;

	/**
	 * Order type variable describing the current order of the current customer. for further use with some client functions
	 */
	public Order curr_order = null;
	
	/**
	 * Order type variable describing the current delivery of the current delivery worker. for further use with some client functions
	 */
	public Delivery curr_delivery = null;
	
	/**
	 * Timer type variable describing the time-out Timer in the order process.
	 */
	public Timer countdown = null;

	/**
	 * UpSecond(Extends Timer) type variable describing the secondary 'tick' every second during the main Timer in the order process.
	 */
	public UpSecond up_second = null;
	
	/**
	 * String variable describing an item type. for further use with some client functions
	 */
	private String itemType = "";
	
	/**
	 * Report type variable describing a report. for further use with some client functions
	 */
	private Report report = null;
	
	/**
	 * List of Order type describing the orders in order table, for further use with some client functions
	 */
	private List<Order> orderTable;
	
	/**
	 * List of Sale type describing the sales in sales table, for further use with some client functions
	 */
	private ArrayList<Sale> sales = new ArrayList<>();
	
	// Constructors ****************************************************

	/**
	 * Constructs an instance of the ekrut client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param client_ctrl The Controller for the client interface
	 */

	public EkrutClient(String host, int port, EkrutClientController client_ctrl) throws IOException {
		super(host, port); // Call the superclass constructor
		this.client_ctrl = client_ctrl;
		openConnection();
	}

	// Instance methods ************************************************
	
	
	/**
	 * @return the item type.
     */
	public String getItemType() {
		return itemType;
	}

	 /**
	   * Sets the item type variable
	   *
	   * @param itemType the item type String.
	   */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	/**
	 * @return the report.
     */
	public Report getReport() {
		return report;
	}

	/**
     * Sets the report variable
     *
     * @param report the Report type input.
     */
	public void setReport(Report report) {
		this.report = report;
	}
	
	/**
	 * @return the orderTable list.
     */
	public List<Order> getOrderTable() {
		return orderTable;
	}

	/**
     * Sets the orderTable list.
     *
     * @param orderTable List of Order input.
     */
	public void setOrderTable(List<Order> orderTable) {
		this.orderTable = orderTable;
	}
	
	/**
	 * @return the inventoryTable list.
     */
	public ArrayList<Product> getInventoryTable() {
		return inventoryTable;
	}
	
	/**
     * Sets the orderTable list.
     *
     * @param inventoryTable List of Product input.
     */
	public ArrayList<Product> getProductsTable() {
		return productsTable;
	}
	
	/**
	 * @return the PickupCodes list.
     */
	public ArrayList<String> getPickupCodes() {
		return PickupCodes;
	}

	/**
     * Sets the PickupCodes list.
     *
     * @param PickupCodes List of String input.
     */
	public void setPickupCodes(ArrayList<String> pickupCodes) {
		PickupCodes = pickupCodes;
	}


	/**
	 * This method handles all data that comes in from the server.
	 * Every different 'Command' has a separate if case relevant to it
	 * @param msg The message from the server, Command type (look Command logic for further details).
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void handleMessageFromServer(Object msg) {
		// If the object message is a Command class ---> String to execute, The Table
		if (msg instanceof Command) {
			
			Command cmd = (Command) msg;			
			/**
			 * This section handles the "user_check" command received by the server.
			 * It receives the appropriate User object and assigns it to the current client user
			 * Moves the interface to the relevant page 
			 */
			if (cmd.getId().equals("user_check")) {
				
				if (cmd.getData() instanceof User && ((User)cmd.getData()).getIsLoggedIn() == 1) {
					System.out.println("The user is already logged in to the system");
					EkrutClientUI.frame_ctrl.displayMessage("The user is already logged in to the system");
				}
				else if (cmd.getData() instanceof Customer) {
					((User)cmd.getData()).setIsLoggedIn(1);
					this.user = (Customer) cmd.getData();
					System.out.println("A customer has logged in");
					CustomerMainScreenFrameController next = new CustomerMainScreenFrameController();
					EkrutClientUI.home_frame = next;
					Platform.runLater(() -> {
						try {
							next.start(EkrutClientUI.frame_ctrl.primaryStage, UserType.Customer.toString());
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					});
				}
				
				else if (cmd.getData() instanceof RegionalManager) {
					((User)cmd.getData()).setIsLoggedIn(1);
					this.user = (RegionalManager) cmd.getData();
					System.out.println("A regional manager has logged in");
					RegionalManagerMainScreenFrameController next = new RegionalManagerMainScreenFrameController();
					EkrutClientUI.home_frame = next;
					Platform.runLater(() -> {
						try {
							next.start(EkrutClientUI.frame_ctrl.primaryStage, "RegionalManagerMainScreenFrame.fxml");
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					});
				}
				
				else if (cmd.getData() instanceof MarketingWorker) {
					((User)cmd.getData()).setIsLoggedIn(1);
					this.user = (MarketingWorker) cmd.getData();
					System.out.println("A marketing worker has logged in");
					MarketingWorkerMainScreenFrameController next = new MarketingWorkerMainScreenFrameController();
					EkrutClientUI.home_frame = next;
					Platform.runLater(() -> {
						try {
							next.start(EkrutClientUI.frame_ctrl.primaryStage, "MarketingWorkerMainScreenFrame.fxml");
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					});
				}
				
				else if (cmd.getData() instanceof User) {
					((User)cmd.getData()).setIsLoggedIn(1);
					this.user = (User)cmd.getData();
					if(this.user.getRole().toString().equals("Service_Representative")) {
						System.out.println("A service representative has logged in");
						ServiceRepresentativeMainScreenFrameController next = new ServiceRepresentativeMainScreenFrameController();
						EkrutClientUI.home_frame = next;
						Platform.runLater(() -> {
							try {
								next.start(EkrutClientUI.frame_ctrl.primaryStage, "ServiceRepresentativeMainScreenFrame.fxml");
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						});
					}
					else if (this.user.getRole().toString().equals("Operation_Worker")) {
						System.out.println("An operation worker has logged in");
						OperationWorkerMainScreenFrameController next = new OperationWorkerMainScreenFrameController();
						EkrutClientUI.home_frame = next;
						Platform.runLater(() -> {
							try {
								next.start(EkrutClientUI.frame_ctrl.primaryStage, "OperationWorkerMainScreenFrame.fxml");
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						});
					}
					
					else if (this.user.getRole().toString().equals("Marketing_Manager")) {
						System.out.println("An marketing manager has logged in");
						MarketingManagerMainScreenFrameController next = new MarketingManagerMainScreenFrameController();
						EkrutClientUI.home_frame = next;
						Platform.runLater(() -> {
							try {
								next.start(EkrutClientUI.frame_ctrl.primaryStage, "MarketingManagerMainScreenFrame.fxml");
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						});
					}
					else if (this.user.getRole().toString().equals("Delivery_Coordinator")) {
						System.out.println("An delivery operator has logged in");
						DeliveryWorkerMainScreenFrameController next = new DeliveryWorkerMainScreenFrameController();
						EkrutClientUI.home_frame = next;
						Platform.runLater(() -> {
							try {
								next.start(EkrutClientUI.frame_ctrl.primaryStage, "DeliveryWorkerMainScreenFrame.fxml");
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						});
					}
					
					else if (cmd.getData() instanceof Ceo) {
						((User)cmd.getData()).setIsLoggedIn(1);
						this.user = (Ceo) cmd.getData();
						System.out.println("The CEO has logged in");
						CEOMainScreenFrameController next = new CEOMainScreenFrameController();
						EkrutClientUI.home_frame = next;
						Platform.runLater(() -> {
							try {
								next.start(EkrutClientUI.frame_ctrl.primaryStage, "CEOMainScreenFrame.fxml");
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						});
					}
				}
				else {
					client_ctrl.display("The Username or Password is incorrect");
				}
			}
			
			/**
			 * This section handles the "Not_Subscriber" command received by the server.
			 * this command is received when a fast login attempt was unsuccessful
			 */
			if(cmd.getId().equals("Not_Subscriber")) {
				client_ctrl.display("If you want to fast login you have to be subscriber!");
			}
			
			/**
			 * This section handles the "disconnected" command received by the server.
			 * this command is received when a client's request to disconnect has been handled properly
			 */
			
			if(cmd.getId().equals("disconnected")) {
				user.setIsLoggedIn(0);
				System.out.println("Logged out");
				user = null;
				EkrutClientUI.back_frames.clear();
				EkrutClientUI.home_frame = null;
				try {
					EkrutClientUI.client_control.ekrut_client = new EkrutClient(EkrutClientUI.client_control.host, EkrutClientUI.client_control.port, EkrutClientUI.client_control);
					EkrutClientUI.client_control.ekrut_client.isConnected = true;
				}
				catch (IOException e1) {
					e1.printStackTrace();
				}
				LoginFrameController next = new LoginFrameController();
				Platform.runLater(() -> {
					try {
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/LoginFrame.fxml");
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
			
			/**
			 * This section handles the "stopping_server" command received by the server.
			 * this command is received when the server shuts down, initiating a disconnection of all clients
			 */
			if (cmd.getId().equals("stopping_server")) {
				isConnected = false;
				System.out.println("Server is disconnected!");
				StartFrameController next = new StartFrameController();
				user = null;
				EkrutClientUI.back_frames.clear();
				EkrutClientUI.home_frame = null;
				Platform.runLater(() -> {
					try {
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/StartFrame.fxml");
						EkrutClientUI.frame_ctrl.displayMessage("Server is closed. Please try again later.");
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
			
			/**
			 * This section handles the "is_order_status_changed" command received by the server.
			 * this command is received when the customer attempts to pick up an order using the relevant pick up code
			 */
			if (cmd.getId().equals("is_order_status_changed")) {
				if((boolean) cmd.getData()) {
					EkrutClientUI.frame_ctrl.displayMessage("Order collected successfully");
				}
				else {
					EkrutClientUI.frame_ctrl.displayMessage("Wrong pickup code or no such order exists");
				}
			}
			
			
			/**
			 * This section handles the "get_inventory" command received by the server.
			 * this command is received when the server sends the relevant inventory table for further use
			 * sets the inventoryTable variable
			 */
			if (cmd.getId().equals("get_inventory")) {
				 this.inventoryTable = (ArrayList<Product>)cmd.getData();
			}
			
			/**
			 * This section handles the "send_restock_table" command received by the server.
			 * this command is received when the server sends the relevant restock table for further use
			 * sets the arr_restock_table variable
			 */
			else if (cmd.getId().equals("send_restock_table")) {
				 this.arr_restock_table = (ArrayList<Restock>)cmd.getData();
			}
			
			/**
			 * This section handles the "get_threshold_points" command received by the server.
			 * this command is received when the server sends the relevant inventory table for further use
			 * sets the threshold_table variable
			 */
			else if (cmd.getId().equals("get_threshold_points")) {
				this.threshold_table = (ArrayList<ArrayList<Inventory>>)cmd.getData();
			}
			
			/**
			 * This section handles the "send_sale_table" command received by the server.
			 * this command is received when the server sends the relevant sale table for further use
			 * sets the sales variable
			 */
			else if (cmd.getId().equals("send_sale_table")) {
				this.sales = (ArrayList<Sale>)cmd.getData();
			}
			
			/**
			 * This section handles the "set_threshold_point" command received by the server.
			 * this command is received when the server sends the relevant flag to inform if the threshold point setting operation was successful
			 */
			else if (cmd.getId().equals("set_threshold_point")) {
				if((boolean) cmd.getData()) {
					EkrutClientUI.frame_ctrl.displayMessage("Threshold point updated successfully");
				}
				else {
					EkrutClientUI.frame_ctrl.displayMessage("An error has occured while updating threshold point");
				}
			}
			
			/**
			 * This section handles the "get_customer_data" command received by the server.
			 * this command is received when the server sends the relevant customer table for further use
			 * sets the pending_customers variable
			 */
			else if (cmd.getId().equals("get_customer_data")) {
				if(cmd.getData() instanceof ArrayList) {
					pending_customers = (ArrayList<User>)cmd.getData();
				}
				else {
					pending_customers = null;
					EkrutClientUI.frame_ctrl.displayMessage("No pending customers to view");
				}
			}
			
			/**
			 * This section handles the "return_all_customers" command received by the server.
			 * this command is received when the server sends the relevant customer table for further use
			 * sets the all_customers variable
			 */
			else if (cmd.getId().equals("return_all_customers")) {
				if(cmd.getData() instanceof ArrayList) {
					all_customers = (ArrayList<Customer>)cmd.getData();
				}
				else {
					all_customers = null;
					EkrutClientUI.frame_ctrl.displayMessage("No customers to view");
				}
			}
			
			/**
			 * This section handles the "set_customer_as_subscriber" command received by the server.
			 * this command is received when the server sends the relevant flag to inform if the subscriber setting operation was successful
			 */
			else if (cmd.getId().equals("set_customer_as_subscriber")) {
				if((boolean)cmd.getData()) {
					EkrutClientUI.frame_ctrl.displayMessage("Succeed. The customer has been notified via email and phone");
				}
				else {
					EkrutClientUI.frame_ctrl.displayMessage("An error has occured while changing user status");
				}
			}
			
			/**
			 * This section handles the "approve_customer" command received by the server.
			 * this command is received when the server sends the relevant flag to inform if the customer approval operation was successful
			 */
			else if (cmd.getId().equals("approve_customer")) {
				if((boolean)cmd.getData()) {
					EkrutClientUI.frame_ctrl.displayMessage("Approved. The customer has been notified via email and phone");
				}
				else {
					EkrutClientUI.frame_ctrl.displayMessage("An error has occured while changing user status");
				}
			}
			
			/**
			 * This section handles the "set_new_user_as_customer" command received by the server.
			 * this command is received when the server sends the relevant flag to inform if the new customer setting operation was successful
			 */
			else if (cmd.getId().equals("set_new_user_as_customer")) {
				if((boolean)cmd.getData()) {
					EkrutClientUI.frame_ctrl.displayMessage("The customer has been created successfully");
				}
				else {
					EkrutClientUI.frame_ctrl.displayMessage("An error has occured while creating new customer");
				}
			}
			
			/**
			 * This section handles the "set_new_stock" command received by the server.
			 * this command is received when the server sends the relevant flag to inform if the new stock setting operation was successful
			 */
			else if (cmd.getId().equals("set_new_stock")) {
				if((boolean)cmd.getData()) {
					EkrutClientUI.frame_ctrl.displayMessage("Updating new stock done successfully");
				}
				else {
					EkrutClientUI.frame_ctrl.displayMessage("A problem has occurred trying to update the new stock");
				}
			}
			
			/**
			 * This section handles the "set_request_restock" command received by the server.
			 * this command is received when the server sends the relevant flag to inform if the new Restock request operation was successful
			 */
			else if (cmd.getId().equals("set_request_restock")) {
				if((boolean)cmd.getData()) {
					EkrutClientUI.frame_ctrl.displayMessage("Restock request has been sent to operation worker");
				}
				else {
					EkrutClientUI.frame_ctrl.displayMessage("A problem has occurred trying to insert request");
				}
			}
			
			/**
			 * This section handles the "set_delivery_table" command received by the server.
			 * this command is received when the server sends the relevant delivery table for further use
			 * sets the arr_delivery_table variable
			 */
			else if (cmd.getId().equals("set_delivery_table")) {
				this.arr_delivery_table = (ArrayList<Delivery>)cmd.getData();			
			}
			
			/**
			 * This section handles the "return_products" command received by the server.
			 * this command is received when the server sends the relevant product table for further use
			 * sets the productsTable variable
			 */
			if (cmd.getId().equals("return_products")) {
				 this.productsTable = (ArrayList<Product>)cmd.getData();
			}
			
			/**
			 * This section handles the "return_order_number" command received by the server.
			 * this command is received when the server sends the relevant current order for further use
			 * sets the curr_order variable
			 */
			if (cmd.getId().equals("return_order_number")) {
				 this.curr_order.setOrder_Number((int)cmd.getData());
			}
			
			/**
			 * This section handles the "return_pickup_codes" command received by the server.
			 * this command is received when the server sends the relevant pick up codes from the order table for further use
			 * sets the PickupCodes variable
			 */
			if (cmd.getId().equals("return_pickup_codes")) {
				this.PickupCodes=(ArrayList<String>)cmd.getData();
			}
			
			/**
			 * This section handles the "return_subscribers_table" command received by the server.
			 * this command is received when the server sends the relevant subscriber table for further use
			 * sets the SubscriberTable variable
			 */
			if (cmd.getId().equals("return_subscribers_table")) {
				this.SubscriberTable=(ArrayList<Subscriber>)cmd.getData();
			}
			
			/**
			 * This section handles the "return_branches_table" command received by the server.
			 * this command is received when the server sends the relevant branch table for further use
			 * sets the BranchTable variable
			 */
			if (cmd.getId().equals("return_branches_table")) {
				this.BranchTable=(ArrayList<Branch>)cmd.getData();
			}
			/**
			 * This section handles the "get_Report" command received by the server.
			 * this command is received when the server sends the relevant Report for further use
			 * sets the report variable
			 */
			if (cmd.getId().equals("get_Report")) {
				setReport((Report)cmd.getData());
			}
			
			/**
			 * This section handles the "create_activityReport" command received by the server.
			 * this command is received when the server sends the relevant Report for further use
			 * sets the report variable
			 */
			if (cmd.getId().equals("create_activityReport")) {
				setReport((Report) cmd.getData());
			}
			
			/**
			 * This section handles the "get_ThresholdPoint" command received by the server.
			 * this command is received when the server sends the relevant Product list for further use
			 * sets the InventoryProduct variable of the current report
			 */
			if (cmd.getId().equals("get_ThresholdPoint")) {
				report.setInventoryProduct((ArrayList<Product>)cmd.getData());
			}
		} 
		else {
			System.out.println("EkrutClient/handleMessageFromServer: Object must be a Command class");
		}
	}

	/**
	 * This method handles all data coming from the UI
	 * Every different 'Command' has a separate if case relevant to it
	 * @param array The message from the UI.
	 */

	public void handleMessageFromClientUI(Object msg) {
		if (msg instanceof Command) {
			Command cmd = (Command) msg;

			/**
			 * This section handles the "about" command received by the UI.
			 * this command is received when the UI sends the command to switch pages
			 */
			if(cmd.getId().equals("about")) {
				AboutFrameController next = new AboutFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/AboutFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "connect" command received by the UI.
			 * this command is received when the UI user attempts to connect to the server
			 * Sends the relevant information and initiates the connect operation
			 */
			else if (cmd.getId().equals("connect")) {
				try {
					openConnection();
					sendToServer(msg); // Sending the first message
					isConnected = true;
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
			}

			/**
			 * This section handles the "login_attempt" command received by the UI.
			 * this command is received when the UI user attempts to login to the system
			 * Sends the relevant information and initiates the login operation
			 */
			else if (cmd.getId().equals("login_attempt")) {
				try {
					openConnection();
					sendToServer(cmd); // Sending the first message
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "disconnect" command received by the UI.
			 * this command is received when the UI user attempts to disconnect from the server
			 * Sends the relevant information and initiates the disconnect operation
			 */
			else if (cmd.getId().equals("disconnect")) {
				try {
					openConnection();
					sendToServer(cmd); // Sending the first message
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "update_order" command received by the UI.
			 * this command is received when the UI requests to update the currently saved order in the database
			 * Sends the relevant information and initiates the order update operation
			 */
			else if (cmd.getId().equals("update_order")) {
				try {
					openConnection();
					sendToServer(new Command("update_order",curr_order)); // Sending the first message
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "update_sale_table" command received by the UI.
			 * this command is received when the UI requests to update the currently saved sale table in the database
			 * Sends the relevant information and initiates the sale table update operation
			 */
			else if (cmd.getId().equals("update_sale_table")) {
				try {
					openConnection();
					sendToServer(new Command("update_sale_table",cmd.getData())); // Sending the first message
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "change_collect_order_status" command received by the UI.
			 * this command is received when the UI requests to update the currently saved order in the database
			 * Sends the relevant information and initiates the order update operation
			 */
			else if (cmd.getId().equals("change_collect_order_status")) {
				try {
					openConnection();
					sendToServer(cmd); // Sending the first message
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
			}

			/**
			 * This section handles the "update_table" command received by the UI.
			 * this command is received when the UI requests to update the currently saved table in the database
			 * Sends the relevant information and initiates the table update operation
			 */
			else if (cmd.getId().equals("update_table")) {
				Command updateTable = new Command("updateData", cmd.getData());
				try {
					openConnection(); // In order to send more than one message
					awaitResponse = true;
					sendToServer(updateTable);
				} catch (IOException e) {
					client_ctrl.display("Could not send message to server. Terminating client.");
					e.printStackTrace();
					quit();
				}
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "update_subscriber_is_First_Purchase" command received by the UI.
			 * this command is received when the UI requests to update the selected customer to turn subscriber in the database
			 * Sends the relevant information and initiates the table update operation
			 */
			else if (cmd.getId().equals("update_subscriber_is_First_Purchase")) {
				try {
					openConnection(); 
					sendToServer(new Command("update_subscriber_is_First_Purchase",cmd.getData()));
				} catch (IOException e) {
					client_ctrl.display("Could not send message to server. Terminating client.");
					e.printStackTrace();
					quit();
				}
				awaitResponse = false;
			}

			/**
			 * This section handles the "refresh_table" command received by the UI.
			 * this command is received when the UI requests to get the saved table in the database
			 * Sends the relevant information and initiates the table retrieve operation
			 */
			else if (cmd.getId().equals("refresh_table")) {
				Command send = new Command("get_table", null);
				try {
					openConnection();
					sendToServer(send); // Sending the first message
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "threshold_point" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("threshold_point")) {
				SetThresholdPointFrameController next = new SetThresholdPointFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/SetThresholdPointFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "customer_management" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("customer_management")) {
				CustomerManagementFrameController next = new CustomerManagementFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/CustomerManagementFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "request_restock" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("request_restock")) {
				RequestRestockFrameController next = new RequestRestockFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/RequestRestockFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "customer_registration" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("customer_registration")) {
				CustomerRegistrationFrameController next = new CustomerRegistrationFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/CustomerRegistrationFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "set_subscriber" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("set_subscriber")) {
				SetSubscriberFrameController next = new SetSubscriberFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/SetSubscriberFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "set_sale_template" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("set_sale_template")) {
				SetSaleTemplateFrameController next = new SetSaleTemplateFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/SetSaleTemplateFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "activate_sale_screen" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("activate_sale_screen")) {
				ActivateSaleFrameController next = new ActivateSaleFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/ActivateSaleFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "end_sale_screen" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("end_sale_screen")) {
				EndSaleFrameController next = new EndSaleFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/EndSaleFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "view_catalog" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("view_catalog")) {
				CatalogFrameController next = new CatalogFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/CatalogFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "manage_cart" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("manage_cart")) {
				ManageCartFrameController next = new ManageCartFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/ManageCartFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "refresh_manage_cart" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("refresh_manage_cart")) {
				ManageCartFrameController next = new ManageCartFrameController();
				Platform.runLater(() -> {
					try {
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/ManageCartFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "checkout_screen" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("checkout_screen")) {
				CheckOutFrameController next = new CheckOutFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/CheckoutFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "payment_screen" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("payment_screen")) {
				PaymentFrameController next = new PaymentFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/PaymentFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "finish_order_screen" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("finish_order_screen")) {
				FinishOrderFrameController next = new FinishOrderFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage,"/gui/FinishOrderFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "choose_branch" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("choose_branch")) {
				ChooseBranchFrameController next = new ChooseBranchFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/ChooseBranchFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "order_menu" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			if (cmd.getId().equals("order_menu")) {
				OrderCatalogFrameController next = new OrderCatalogFrameController();			
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/OrderCatalogFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "get_inventory" command received by the UI.
			 * this command is received when the UI requests to get the saved relevant inventory in the database
			 * Sends the relevant information and initiates the inventory retrieve operation
			 */
			if (cmd.getId().equals("get_inventory")) {
				try {
					openConnection();
					sendToServer(new Command("get_inventory",curr_order)); 
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
		   }
			
			/**
			 * This section handles the "get_restock_table" command received by the UI.
			 * this command is received when the UI requests to get the saved relevant restock in the database
			 * Sends the relevant information and initiates the restock retrieve operation
			 */
			else if (cmd.getId().equals("get_restock_table")) {
				try {
					openConnection();
					sendToServer(cmd);
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
		   }
			
			/**
			 * This section handles the "get_ThresholdPoint" command received by the UI.
			 * this command is received when the UI requests to get the saved relevant Threshold Point in the database
			 * Sends the relevant information and initiates the ThresholdPoint retrieve operation
			 */
			else if (cmd.getId().equals("get_ThresholdPoint")) {
				try {
					openConnection();
					sendToServer(cmd);
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
		   }

			/**
			 * This section handles the "get_sale_table" command received by the UI.
			 * this command is received when the UI requests to get the saved relevant sale table in the database
			 * Sends the relevant information and initiates the sale table retrieve operation
			 */
			else if (cmd.getId().equals("get_sale_table")) {
				try {
					openConnection();
					sendToServer(cmd);
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
		   }
			
			/**
			 * This section handles the "activate_sale" command received by the UI.
			 * this command is received when the UI requests to activate the relevant sale in the database
			 * Sends the relevant information and initiates the sale activation operation
			 */
			else if (cmd.getId().equals("activate_sale")) {
				try {
					openConnection();
					sendToServer(cmd);
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
		   }
			
			/**
			 * This section handles the "end_sale" command received by the UI.
			 * this command is received when the UI requests to end the relevant sale in the database
			 * Sends the relevant information and initiates the sale end operation
			 */
			else if (cmd.getId().equals("end_sale")) {
				try {
					openConnection();
					sendToServer(cmd);
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
		   }
			
			/**
			 * This section handles the "set_new_stock" command received by the UI.
			 * this command is received when the UI requests to set new stock request in the database
			 * Sends the relevant information and initiates the stock request operation
			 */
			else if (cmd.getId().equals("set_new_stock")) {
				try {
					openConnection();
					sendToServer(cmd);
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
		   }
			
			/**
			 * This section handles the "get_threshold_points" command received by the UI.
			 * this command is received when the UI requests to get the relevant threshold points in the database
			 * Sends the relevant information and initiates the threshold request operation
			 */
			else if (cmd.getId().equals("get_threshold_points")) {
				try {
					openConnection();
					sendToServer(cmd); 
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
		   }
			/**
			 * This section handles the "set_threshold_point" command received by the UI.
			 * this command is received when the UI requests to set the relevant threshold point in the database
			 * Sends the relevant information and initiates the threshold change operation
			 */
			else if (cmd.getId().equals("set_threshold_point")) {
				if(cmd.getData() instanceof Inventory) {
					try {
						openConnection();
						sendToServer(cmd);
					}
					catch (IOException e) {
						e.printStackTrace();
						client_ctrl.display("Could not send to server. Terminating client.");
						quit();
					}
				}
				else {
					try {
						ArrayList<String> arr = new ArrayList<String>();
						for(ArrayList<Inventory> branch : threshold_table) {
							arr.add(branch.get(0).getBranch());
						}
						arr.add((String)cmd.getData());
						Command send = new Command("set_threshold_point", arr);
						openConnection();
						sendToServer(send);
					}
					catch (IOException e) {
						e.printStackTrace();
						client_ctrl.display("Could not send to server. Terminating client.");
						quit();
					}
				}
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "create_sale_template" command received by the UI.
			 * this command is received when the UI requests to create a new sale template and send it to the database
			 * Sends the relevant information and initiates the create sale template operation
			 */
			else if (cmd.getId().equals("create_sale_template")) {
				try {
					openConnection();
					sendToServer(cmd);
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
		   }
			
			/**
			 * This section handles the "approve_customer" command received by the UI.
			 * this command is received when the UI requests to approve a new customer and update in the database
			 * Sends the relevant information and initiates the approve customer operation
			 */
			else if (cmd.getId().equals("approve_customer")) {
				try {
					openConnection();
					sendToServer(cmd); 
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "set_new_user_as_customer" command received by the UI.
			 * this command is received when the UI requests to set a new user as customer and update in the database
			 * Sends the relevant information and initiates the create customer operation
			 */
			else if (cmd.getId().equals("set_new_user_as_customer")) {
				try {
					openConnection();
					sendToServer(cmd); 
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "get_customer_data" command received by the UI.
			 * this command is received when the UI requests to get customer data from the database
			 * Sends the relevant information and initiates the get customer data operation
			 */
			else if (cmd.getId().equals("get_customer_data")) {
				try {
					openConnection();
					sendToServer(cmd); 
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
		   }
			
			/**
			 * This section handles the "get_all_customers" command received by the UI.
			 * this command is received when the UI requests to all customers data from the database
			 * Sends the relevant information and initiates the get all customers data operation
			 */
			else if (cmd.getId().equals("get_all_customers")) {
				try {
					openConnection();
					sendToServer(cmd); 
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
		   }
			
			/**
			 * This section handles the "set_customer_as_subscriber" command received by the UI.
			 * this command is received when the UI requests to set customer as subscriber and update in the database
			 * Sends the relevant information and initiates the set subscriber operation
			 */
			else if (cmd.getId().equals("set_customer_as_subscriber")) {
				try {
					openConnection();
					sendToServer(cmd); 
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
		   }
			
			/**
			 * This section handles the "set_request_restock" command received by the UI.
			 * this command is received when the UI requests to upload a new stock request to the database
			 * Sends the relevant information and initiates the set restock operation
			 */
			else if (cmd.getId().equals("set_request_restock")) {
				try {
					openConnection();
					sendToServer(cmd); 
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
				awaitResponse = false;
		   }
			
			/**
			 * This section handles the "collect_order" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("collect_order")) {
				CollectOrderFrameController next = new CollectOrderFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/CollectOrderFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "restock_inventory" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("restock_inventory")) {
				RestockInventoryFrameController next = new RestockInventoryFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/RestockInventoryFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "delivery" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("delivery")) {
				DeliveryFrameController next = new DeliveryFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/DeliveryFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "done_delivery" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("done_delivery")) {
				DoneDeliveryFrameController next = new DoneDeliveryFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/DoneDeliveryFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "approve_delivery" command received by the UI.
			 * this command is received when the UI sends the command to switch pages to the relevant page
			 */
			else if (cmd.getId().equals("approve_delivery")) {
				ApproveDeliveryFrameController next = new ApproveDeliveryFrameController();
				Platform.runLater(() -> {
					try {
						EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
						next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/ApproveDeliveryFrame.fxml");
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				awaitResponse = false;
			}
			
			/**
			 * This section handles the "get_products" command received by the UI.
			 * this command is received when the UI requests to get the product table from the database
			 * Sends the relevant information and initiates the get products operation
			 */
	          if (cmd.getId().equals("get_products")) {
					try {
						openConnection();
						sendToServer(new Command("get_products",null)); 
					} catch (IOException e) {
						e.printStackTrace();
						client_ctrl.display("Could not send to server. Terminating client.");
						quit();
					}
					awaitResponse = false;
			   }
	          
	          /**
				 * This section handles the "get_order_number" command received by the UI.
				 * this command is received when the UI requests to get the order number from the database
				 * Sends the relevant information and initiates the get order number operation
				 */
	          if (cmd.getId().equals("get_order_number")) {
					try {
						openConnection();
						sendToServer(new Command("get_order_number",null)); 
					} catch (IOException e) {
						e.printStackTrace();
						client_ctrl.display("Could not send to server. Terminating client.");
						quit();
					}
					awaitResponse = false;
			   }
	          
	          /**
				 * This section handles the "get_pickup_codes" command received by the UI.
				 * this command is received when the UI requests to get the pickup codes from the database
				 * Sends the relevant information and initiates the get pickup codes operation
				 */
	          if (cmd.getId().equals("get_pickup_codes")) {
					try {
						openConnection();
						sendToServer(new Command("get_pickup_codes",null)); 
					} catch (IOException e) {
						e.printStackTrace();
						client_ctrl.display("Could not send to server shahar. Terminating client.");
						quit();
					}
					awaitResponse = false;
			   }
	          
	          /**
				 * This section handles the "get_subscribers_table" command received by the UI.
				 * this command is received when the UI requests to get the subscriber table from the database
				 * Sends the relevant information and initiates the get subscriber table operation
				 */
	          if (cmd.getId().equals("get_subscribers_table")) {
					try {
						openConnection();
						sendToServer(new Command("get_subscribers_table",null)); 
					} catch (IOException e) {
						e.printStackTrace();
						client_ctrl.display("Could not send to server shahar. Terminating client.");
						quit();
					}
					awaitResponse = false;
			   }
	          
	          /**
				 * This section handles the "get_branches_table" command received by the UI.
				 * this command is received when the UI requests to get the branch table from the database
				 * Sends the relevant information and initiates the get branch table operation
				 */
	          if (cmd.getId().equals("get_branches_table")) {
					try {
						openConnection();
						sendToServer(new Command("get_branches_table",null)); 
					} catch (IOException e) {
						e.printStackTrace();
						client_ctrl.display("Could not send to server shahar. Terminating client.");
						quit();
					}
					awaitResponse = false;
			   }
	          
	          /**
				 * This section handles the "create_order" command received by the UI.
				 * this command is received when the UI requests to create a new order and upload it to the database
				 * Sends the relevant information and initiates the create order operation
				 */
	          if (cmd.getId().equals("create_order")) {
					try {
						openConnection();
						sendToServer(new Command("create_order",curr_order)); 
					} catch (IOException e) {
						e.printStackTrace();
						client_ctrl.display("Could not send to server. Terminating client.");
						quit();
					}
					awaitResponse = false;
			   }
	          
	          /**
				 * This section handles the "create_productInOrder" command received by the UI.
				 * this command is received when the UI requests to create a new product in order and upload it to the database
				 * Sends the relevant information and initiates the create product in order operation
				 */
	          if (cmd.getId().equals("create_productInOrder")) {
					try {
						openConnection();
						sendToServer(new Command("create_productInOrder",curr_order)); 
					} catch (IOException e) {
						e.printStackTrace();
						client_ctrl.display("Could not send to server. Terminating client.");
						quit();
					}
					awaitResponse = false;
			   }
	          
	          /**
				 * This section handles the "create_delivery" command received by the UI.
				 * this command is received when the UI requests to create a new delivery and upload it to the database
				 * Sends the relevant information and initiates the create delivery operation
				 */
	          if (cmd.getId().equals("create_delivery")) {
	        	  curr_delivery=(Delivery)cmd.getData();
					try {
						openConnection();
						sendToServer(new Command("create_delivery",curr_delivery)); 
					} catch (IOException e) {
						e.printStackTrace();
						client_ctrl.display("Could not send to server. Terminating client.");
						quit();
					}
					awaitResponse = false;
			   }
	          
	          /**
				 * This section handles the "get_orders" command received by the UI.
				 * this command is received when the UI requests to get order table the database
				 * Sends the relevant information and initiates the get orders operation
				 */
	          else if(cmd.getId().equals("get_orders")) {
	        	  
		  			try {
						openConnection();
						sendToServer(new Command("get_orders",report));  
					} catch (IOException e) {
						e.printStackTrace();
						client_ctrl.display("Could not send to server. Terminating client.");
						quit();
					}
		  			awaitResponse = false;
		  			
		  	  }
	          /**
				 * This section handles the "choose_report" command received by the UI.
				 * this command is received when the UI sends the command to switch pages to the relevant page
				 */
			  else if(cmd.getId().equals("choose_report")) {
					ChooseReportFrameController next = new ChooseReportFrameController();
					Platform.runLater(() -> {
						try {
							EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
							next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/ChooseReportFrame.fxml");
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					awaitResponse = false;
				}
	          
	          /**
				 * This section handles the "choose_CEOreport" command received by the UI.
				 * this command is received when the UI sends the command to switch pages to the relevant page
				 */
				else if(cmd.getId().equals("choose_CEOreport")) {
					CEOChooseReportFrameController next = new CEOChooseReportFrameController();
					Platform.runLater(() -> {
						try {
							EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
							next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/CEOChooseReportFrame.fxml");
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					awaitResponse = false;
				}

	          /**
				 * This section handles the "choose_pieChartReport" command received by the UI.
				 * this command is received when the UI sends the command to switch pages to the relevant page
				 */
				else if(cmd.getId().equals("choose_pieChartReport")) {
					PieChartReportController next = new PieChartReportController();
					Platform.runLater(() -> {
						try {
							EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
							next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/pieChartManagerFrame.fxml");
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					
					awaitResponse = false;
				}
				
	          /**
				 * This section handles the "choose_InventoryReport" command received by the UI.
				 * this command is received when the UI sends the command to switch pages to the relevant page
				 */
				else if(cmd.getId().equals("choose_InventoryReport")) {
					InventoryReportController next = new InventoryReportController();
					Platform.runLater(() -> {
						try {
							EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
							next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/InventoryReportManagerFrame.fxml");
							System.out.println("inventory");
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					
					awaitResponse = false;
				}
				
	          /**
				 * This section handles the "choose_InventoryReport" command received by the UI.
				 * this command is received when the UI sends the command to switch pages to the relevant page
				 */
				else if(cmd.getId().equals("choose_InventoryReport")) {
					InventoryReportController next = new InventoryReportController();
					Platform.runLater(() -> {
						try {

							EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
							next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/CustomerActivityManagerFrame.fxml");
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					
					awaitResponse = false;
				}
				
	          /**
				 * This section handles the "choose_CustomerActivityReport" command received by the UI.
				 * this command is received when the UI sends the command to switch pages to the relevant page
				 */
				else if (cmd.getId().equals("choose_CustomerActivityReport")) {
					CustomerActivityReportController next = new CustomerActivityReportController();
					Platform.runLater(() -> {
						try {
							EkrutClientUI.back_frames.add(EkrutClientUI.frame_ctrl);
							next.start(EkrutClientUI.frame_ctrl.primaryStage, "/gui/CustomerActivityManagerFrame.fxml");
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					awaitResponse = false;
				}
				
	          /**
				 * This section handles the "create_activityReport" command received by the UI.
				 * this command is received when the UI requests to create a new activity report and upload it to the database
				 * Sends the relevant information and initiates the create activity report operation
				 */	          
				else if (cmd.getId().equals("create_activityReport")) {
					try {
						openConnection();
						Report r = getReport();
						System.out.println("report");
						sendToServer(new Command("create_activityReport",r)); 
					} catch (IOException e) {
						e.printStackTrace();
						client_ctrl.display("Could not send to server. Terminating client.");
						quit();
					}
					awaitResponse = false;
			   }

	          /**
				 * This section handles the "get_delivery_table" command received by the UI.
				 * this command is received when the UI requests to get delivery table from the database
				 * Sends the relevant information and initiates the get delivery table operation
				 */	
				else if (cmd.getId().equals("get_delivery_table")) {
					try {
						openConnection();
						sendToServer(new Command("get_delivery_table",user)); 
					} catch (IOException e) {
						e.printStackTrace();
						client_ctrl.display("Could not send to server. Terminating client.");
						quit();
					}
					awaitResponse = false;
			   }
	          
	          /**
				 * This section handles the "update_delivery" command received by the UI.
				 * this command is received when the UI requests to update relevant delivery in the database
				 * Sends the relevant information and initiates the update delivery operation
				 */	
				else if (cmd.getId().equals("update_delivery")) {
					try {
						openConnection();
						sendToServer(cmd); 
					} catch (IOException e) {
						e.printStackTrace();
						client_ctrl.display("Could not send to server. Terminating client.");
						quit();
					}
					awaitResponse = false;
			   }
	          
	          /**
				 * This section handles the "get_active_deliveries" command received by the UI.
				 * this command is received when the UI requests to get the active deliveries from the database
				 * Sends the relevant information and initiates the get active delivery table operation
				 */	
				else if (cmd.getId().equals("get_active_deliveries")) {
					try {
						openConnection();
						sendToServer(new Command("get_active_deliveries",user)); 
					} catch (IOException e) {
						e.printStackTrace();
						client_ctrl.display("Could not send to server. Terminating client.");
						quit();
					}
					awaitResponse = false;
			   }
	          /////////
	          
		}
	}

	/**
	 * This method terminates the client.
	 * 
	 * @throws IOException
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
			System.out.println("IOException! No connection open");
		}
		System.exit(0);
	}


	/**
	 * @return the sales list variable.
     */
	public ArrayList<Sale> getSales() {
		return sales;
	}

	/**
     * Sets the sales list.
     *
     * @param sales List of sales input.
     */
	public void setSales(ArrayList<Sale> sales) {
		this.sales = sales;
	}



	/**
	 * This class serves as a wrapper to an Integer value, to allow editing by reference
	 * for use with the order process timer
	 */
	public static class Seconds {
	    public Integer value;
	    public Seconds(int value) {
	    	this.value=value;
	    }
	}
	
	/**
	 * this class extends TimerTask type , serves as a TimerTask specific to our requirements
	 * for use with the order process timer
	 */
	public static class UpSecond extends TimerTask  {
	     Label timer;
	     Seconds seconds;

	     public UpSecond(Label timer, Seconds seconds) {
	         this.timer = timer;
	         this.seconds = seconds;
	     }

	     public void setLabel(Label timer) {
	    	 this.timer = timer;
	     }
	     
	     @Override
	     public void run() {
	    	 Platform.runLater(() -> {
	    		 seconds.value--;
		         String display = String.format("%02d:%02d", seconds.value / 60, seconds.value % 60);
		         timer.setText(display);
				});
	     }
	}

}
//End of EkrutClient class