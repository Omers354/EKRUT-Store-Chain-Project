package gui;

import client.EkrutClientUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Command;
import logic.Customer;
import logic.Customer.CustomerStatus;
import logic.CustomerBranch;
import logic.Order;

/**
 * The CustomerMainScreenFrameController class is a JavaFX controller class for the customer main screen frame.
 * It extends the FrameController class and has several @FXML annotations to indicate that the following properties 
 * are associated with the corresponding elements in the FXML file that defines the GUI layout.
 * 
 * This class also overrides the additionalChanges() method, which is used to make changes to the GUI elements after 
 * the layout has been loaded. It sets the images for several ImageView elements, sets the text for a Label element, 
 * and disables or changes the opacity of certain buttons based on the customer's status (active, frozen, or blocked). 
 * The method also enables or disables the approve_delivery button and collect_order_btn depending on the branch of the customer.
 * If the customer is a subscriber, it sets the subscriber_number text with the subscriber number.
 */
public class CustomerMainScreenFrameController extends FrameController {
	
	/**
	 * The view_catalog_btn is a button that allows the user to view the catalog of available products.
	 * It is associated with a method in the CustomerMainScreenFrameController class through the @FXML annotation.
	 */

	@FXML
	private Button view_catalog_btn;
	
	/**
	 * The create_new_order_btn is a button that allows the user to create a new order.
	 * It is associated with a method in the CustomerMainScreenFrameController class through the @FXML annotation.
	 */

	@FXML
	private Button create_new_order_btn;
	
	/**
	 * The manage_cart_btn is a button that allows the user to manage their cart.
	 * It is associated with a method in the CustomerMainScreenFrameController class through the @FXML annotation.
	 */

	
	@FXML
	private Button manage_cart_btn;
	
	/**
	 * The collect_order_btn is a button that allows the user to collect their order.
	 * It is associated with a method in the CustomerMainScreenFrameController class through the @FXML annotation.
	 */

	
	@FXML
	private Button collect_order_btn;
	
	/**
	 * The view_catalog is an ImageView that displays an image related to view catalog.
	 * It is associated with a method in the CustomerMainScreenFrameController class through the @FXML annotation.
	 */

	
	@FXML
	private ImageView view_catalog;
	
	/**
	 * The create_new_order is an ImageView that displays an image related to create new order.
	 * It is associated with a method in the CustomerMainScreenFrameController class through the @FXML annotation.
	 */

	
	@FXML
	private ImageView create_new_order;

	
	/**
	 * The manage_cart is an ImageView that displays an image related to manage cart.
	 * It is associated with a method in the CustomerMainScreenFrameController class through the @FXML annotation.
	 */

	@FXML
	private ImageView manage_cart;
	
	/**
	 * The approve_logo is an ImageView that displays an image related to approve logo or drop shipping.
	 * It is associated with a method in the CustomerMainScreenFrameController class through the @FXML annotation.
	 */

	@FXML
	private ImageView approve_logo;
	
	/**
	 * The collect_order is an ImageView that displays an image related to collect order.
	 * It is associated with a method in the CustomerMainScreenFrameController class through the @FXML annotation.
	 */

	@FXML
	private ImageView collect_order;
	
	/**
	 * The approve_delivery is a button that allows the user to approve delivery.
	 * It is associated with a method in the CustomerMainScreenFrameController class through the @FXML annotation.
	 */

	
	@FXML
	private Button approve_delivery;
	
	/**
	 * The subscriber_number is a label that displays the subscriber number.
	 * It is associated with a method in the CustomerMainScreenFrameController class through the @FXML annotation.
	 */

	@FXML
	private Label subscriber_number;
	/**
	 * additionalChanges() method is used to make changes to the GUI elements after the layout has been loaded.
	 * This method sets the images for several ImageView elements, sets the text for a Label element, 
	 * and disables or changes the opacity of certain buttons based on the customer's status (active, frozen, or blocked). 
	 * The method also enables or disables the approve_delivery button and collect_order_btn depending on the branch of the customer.
	 * If the customer is a subscriber, it sets the subscriber_number text with the subscriber number.
	 */
	@Override
	public void additionalChanges() {
		view_catalog.setImage(new Image(this.getClass().getResourceAsStream("/view_catalog.png")));
		create_new_order.setImage(new Image(this.getClass().getResourceAsStream("/create_new_order.png")));
		manage_cart.setImage(new Image(this.getClass().getResourceAsStream("/manage_cart.png")));
		collect_order.setImage(new Image(this.getClass().getResourceAsStream("/collect_order.png")));
		approve_logo.setImage(new Image(this.getClass().getResourceAsStream("/drop_shipping.png")));
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		Customer customer = (Customer)EkrutClientUI.client_control.ekrut_client.user;
		if(customer.getCustomer_status() == CustomerStatus.Active) {
			status.setText(customer.getCustomer_status().toString());
			status.setStyle("-fx-text-fill: #00FF00");
		}
		if(customer.getCustomer_status() == CustomerStatus.Frozen) {
			status.setText(customer.getCustomer_status().toString());
			status.setStyle("-fx-text-fill: #40E0D0");
			create_new_order.setOpacity(0.4);
			manage_cart.setOpacity(0.4);
			create_new_order_btn.setDisable(true);
			manage_cart_btn.setDisable(true);
		}
		if(customer.getCustomer_status() == CustomerStatus.Blocked) {
			status.setText(customer.getCustomer_status().toString());
			status.setStyle("-fx-text-fill: #FF3131");
			view_catalog.setOpacity(0.4);
			create_new_order.setOpacity(0.4);
			manage_cart.setOpacity(0.4);
			collect_order.setOpacity(0.4);
			view_catalog_btn.setDisable(true);
			create_new_order_btn.setDisable(true);
			manage_cart_btn.setDisable(true);
			collect_order_btn.setDisable(true);
		}
		if(customer.getBranch().equals(CustomerBranch.Online)) {
			approve_delivery.setDisable(false);
			collect_order_btn.setDisable(false);
		}
		if (customer.getIsSubscriber()==1) {
			subscriber_number.setText("Subscriber " + customer.getSubscriber_number());
			
		}

	}
	
	public void getCatalog(ActionEvent event) throws Exception{
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("view_catalog", null));
	}
	
	public void getStartOrder(ActionEvent event) throws Exception{
		Customer customer = (Customer)EkrutClientUI.client_control.ekrut_client.user;
		if(customer.getBranch().equals(CustomerBranch.Online)) {
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("choose_branch", null));
		}
		else {
			EkrutClientUI.client_control.ekrut_client.curr_order= new Order(String.valueOf(customer.getBranch()),"Physical");
			EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("order_menu", null));
		}
	}
	
	public void getCart(ActionEvent event) throws Exception{
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("manage_cart", null));
	}
	
	public void getCollectOrder(ActionEvent event) throws Exception{
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("collect_order", null));
	}
	
	public void getApproveDelivery(ActionEvent event) throws Exception{
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("approve_delivery", null));
	}
}