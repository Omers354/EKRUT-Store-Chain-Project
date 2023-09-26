package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.Branch;
import logic.Command;
import logic.Customer;
import logic.CustomerBranch;
import logic.Order;
import logic.Product;
import logic.Supply_Method;
import logic.Order.ProductInOrder;
import logic.Order_Status;
import logic.Payment_Method;

/**

The ManageCartFrameController class is a JavaFX controller for a Manage Cart Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Manage Cart and to handle events.

@author [Shahar]

@version [4.0]
*/
public class ManageCartFrameController extends FrameController{

	@FXML
	private Button HomePagebtn;
	
	@FXML
	private Button Continuebtn;
	
	@FXML
	private Button CnclOrderbtn;
	
	@FXML
	private Label TotalPrice;
	
	@FXML
	private ImageView cancel_order;
	
	@FXML
	private HBox ItemHbox;
	
	@FXML
	private VBox CatalogVbox;
	
	private Customer customer;
	
	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	also show the products in the cart and can remove them
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		customer = (Customer)EkrutClientUI.client_control.ekrut_client.user;
		status.setText(customer.getCustomer_status().toString());
		status.setStyle("-fx-text-fill: #00FF00");
		cancel_order.setImage(new Image(this.getClass().getResourceAsStream("/cancel_order.png")));
		
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(EkrutClientUI.client_control.ekrut_client.curr_order!=null) {
		List<ProductInOrder> productsInOrder = EkrutClientUI.client_control.ekrut_client.curr_order.getItems();
		float sum=0;
		URL itemURL = getClass().getResource("ItemInManageCart.fxml");
		Parent anchor;
		for(ProductInOrder productInOrder : productsInOrder) {
			System.out.println(productInOrder.prod.getProductName());
			try {
				anchor = FXMLLoader.load(itemURL);
				HBox item = (HBox) anchor.getChildrenUnmodifiable().get(0);
				ImageView image = (ImageView) item.getChildren().get(0);
			    VBox desc = (VBox) item.getChildren().get(1);
			    VBox desc2 = (VBox) item.getChildren().get(2);
			    Text name = (Text) desc.getChildren().get(0);
			    ImageView delete_from_cart = new ImageView();
			    delete_from_cart.setFitHeight(25);
			    delete_from_cart.setPreserveRatio(true);
			    delete_from_cart.setImage(new Image(this.getClass().getResourceAsStream("/delete_from_cart.png")));
			    ((Button)desc2.getChildren().get(1)).setGraphic(delete_from_cart);
			    name.setText(productInOrder.prod.getProductName());
			    System.out.println(productInOrder.prod.getImageURL());
			    image.setImage(new Image(this.getClass().getResourceAsStream(productInOrder.prod.getImageURL())));
			    ((Label)desc.getChildren().get(1)).setText("Item ID"+productInOrder.prod.getProductID());
			    ((Label)desc.getChildren().get(2)).setText("Quantity: "+productInOrder.quantity);
			    ((Label)((HBox)desc2.getChildren().get(0)).getChildren().get(1)).setText("Item Total Price:");
			    if(customer.getIsSubscriber()==0 || productInOrder.prod.getHasActiveSale()==0){
			    ((Label)((HBox)desc2.getChildren().get(0)).getChildren().get(1)).setText
			    (String.format("%.2f$",productInOrder.prod.getPrice()*productInOrder.quantity));
			    sum+=productInOrder.prod.getPrice()*productInOrder.quantity;
			    }
			    else if(productInOrder.prod.getHasActiveSale()==1 && customer.getIsSubscriber()==1) {
			    	((Label)((HBox)desc2.getChildren().get(0)).getChildren().get(1)).setText
				    (String.format("%.2f$",productInOrder.prod.getDiscount_Price()*productInOrder.quantity));
				    sum+=productInOrder.prod.getDiscount_Price()*productInOrder.quantity;
			    }
				CatalogVbox.getChildren().add(item);
				Separator separator = new Separator();
				separator.setStyle("-fx-background-color: #3D4956; -fx-border-width: 5");
				CatalogVbox.getChildren().add(separator);
				Button remove = (Button) desc2.getChildren().get(1);
				remove.setOnAction((event)-> {
					EkrutClientUI.client_control.ekrut_client.curr_order.remove_item(productInOrder.prod);
					System.out.println(productInOrder.prod.getProductName()+" Has been removed!");
					EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("refresh_manage_cart", null));
				});
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
		EkrutClientUI.client_control.ekrut_client.curr_order.setPrice(sum);
		TotalPrice.setText(String.format("%.2f$",sum));
		EkrutClientUI.client_control.ekrut_client.curr_order.setID(EkrutClientUI.client_control.ekrut_client.user.getId());
		EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_Date(LocalDate.now());
		EkrutClientUI.client_control.ekrut_client.curr_order.setPayment_method(Payment_Method.credit_card);
	  }
	}
	
	/**

	CancelOrder is a method that is called when the user clicks the cancel order button. It checks if the client has an active order, if it does,
	it sets the order status to Canceled, sends an update_order command to the server and removes the order from the client's current order.
	It also sends a refresh_manage_cart command to the server to update the cart.
	@param event the event of clicking the cancel order button.
	@throws Exception
	*/
	public void CancelOrder(ActionEvent event) throws Exception {
		if(EkrutClientUI.client_control.ekrut_client.curr_order != null) {
			EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Canceled);
			EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("update_order",null));
			EkrutClientUI.client_control.ekrut_client.curr_order.remove_order();
			EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("refresh_manage_cart", null));
		}
	}
	
	/**

	getContinue is a method that is called when the user clicks the continue button. It checks if the client has an active order, if it does,
	it sets the region of the order based on the customer's branch or current branch if it is an online branch. If the order is delivery, it sends
	a create_order command to the server and a checkout_screen command to update the GUI. If the order is pickup, it generates a pickup code,
	sends a create_order command to the server, and a payment_screen command to update the GUI.
	@param event the event of clicking the continue button.
	@throws Exception
	*/
	public void getContinue(ActionEvent event) throws Exception{
		Order order = EkrutClientUI.client_control.ekrut_client.curr_order;
		if(customer.getBranch().equals(CustomerBranch.Online)) {
		for(Branch branch : EkrutClientUI.client_control.ekrut_client.BranchTable) {
			if(branch.getBranch_Name().equals(EkrutClientUI.client_control.ekrut_client.onlineBranch)) {
			EkrutClientUI.client_control.ekrut_client.curr_order.setRegion(branch.getRegion_Name());
			}
		}
	}
		else {
			EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_branches_table", null));
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String curr_branch = String.valueOf(customer.getBranch());
			System.out.println(curr_branch);
			for(Branch branch : EkrutClientUI.client_control.ekrut_client.BranchTable) {
				if(branch.getBranch_Name().equals(curr_branch)) {
				EkrutClientUI.client_control.ekrut_client.curr_order.setRegion(branch.getRegion_Name());
				System.out.println(branch.getRegion_Name());
				}
			}
		}
		
		if(order != null && order.getItems().size() != 0) {
			if(EkrutClientUI.client_control.ekrut_client.curr_order.getOrder_type().equals("Delivery")) {
				//inserting order data for db
				EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Pending);
				EkrutClientUI.client_control.ekrut_client.curr_order.setPickUp_Code(null);
				EkrutClientUI.client_control.ekrut_client.curr_order.setSupply_method(Supply_Method.Delivery);
			    EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("create_order", null));
			    EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("checkout_screen", null));
			}
			else if(EkrutClientUI.client_control.ekrut_client.curr_order.getOrder_type().equals("Pickup")){
				EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Pending);
				EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_pickup_codes", null));
				EkrutClientUI.client_control.ekrut_client.curr_order.setPickUp_Code(GeneratePickUpCode());
				EkrutClientUI.client_control.ekrut_client.curr_order.setSupply_method(Supply_Method.Store_PickUp);
				EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("create_order", null));
				EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("payment_screen", null));
			}
			else {
				EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Pending);
				EkrutClientUI.client_control.ekrut_client.curr_order.setPickUp_Code(null);
				EkrutClientUI.client_control.ekrut_client.curr_order.setSupply_method(Supply_Method.Store_PickUp);
				EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("create_order", null));
				EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("payment_screen", null));
			}
		}
	}
	
public String GeneratePickUpCode() {
	String pickUp_code;
	Integer dummy;
	Random rand = new Random();
	dummy= rand.nextInt(9000)+1000;
	if(EkrutClientUI.client_control.ekrut_client.PickupCodes.contains(dummy.toString())) {
		dummy= rand.nextInt(9000)+1000;
	}
	 pickUp_code=dummy.toString();
	 return pickUp_code;
}

}