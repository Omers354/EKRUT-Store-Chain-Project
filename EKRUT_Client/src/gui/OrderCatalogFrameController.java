package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.Command;
import logic.Customer;
import logic.Order;
import logic.Product;

/**

The OrderCatalogFrameController class is a JavaFX controller for a Order Catalog Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Order Catalog Screen  and to handle events.

@author [Shahar]

@version [4.0]
*/
public class OrderCatalogFrameController extends FrameController {
	
public int amount = 0;
	
	@FXML
	private Button HomePagebtn;
	
	@FXML
	private Button Continuebtn;
	
	@FXML
	private Button plusbtn;
	
	@FXML
	private Button minusbtn;
	
	@FXML
	private ImageView view_catalog;
	
	@FXML
	private ImageView create_new_order;
	
	@FXML
	private ImageView manage_cart;
	
	@FXML
	private ImageView collect_order;
	
	@FXML
	private TextField quantityField;
	
	@FXML
	private HBox ItemHbox;
	
	@FXML
	private VBox CatalogVbox;
	
	ArrayList<Product> inventory;
	
	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame, as well as setting the cancel order image and starting a countdown timer for the order.
	It send from the client new Command("get_inventory")
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		Customer customer = (Customer)EkrutClientUI.client_control.ekrut_client.user;
		status.setText(customer.getCustomer_status().toString());
		status.setStyle("-fx-text-fill: #00FF00");
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_inventory",null));
    
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inventory=EkrutClientUI.client_control.ekrut_client.getInventoryTable();
		int count=0;
		URL itemURL = getClass().getResource("ItemOrder.fxml");
		Parent anchor;
		for(Product product : inventory) {
			try {
				amount=0;
				anchor = FXMLLoader.load(itemURL);
				HBox item = (HBox) anchor.getChildrenUnmodifiable().get(0);
				ImageView image = (ImageView) item.getChildren().get(0);
			    VBox desc = (VBox) item.getChildren().get(1);
			    VBox desc2 = (VBox) item.getChildren().get(2);
			    Text name = (Text) desc.getChildren().get(0);
			    name.setText(product.getProductName());
			   // name.setWrappingWidth(200);
			    if(product.getProductName().equals("Coca Cola bottle")) {
			    System.out.println(product.getDiscount_Price());
			    }
			    image.setImage(new Image(this.getClass().getResourceAsStream(product.getImageURL())));
			    ((Label)desc.getChildren().get(1)).setText("Item ID: "+product.getProductID());
			    if(customer.getIsSubscriber()==0)
			    ((Label)desc.getChildren().get(2)).setText("Price: "+product.getPrice()+"$");
			    else if(customer.getIsSubscriber()==1 && product.getHasActiveSale()==0){
			    	((Label)desc.getChildren().get(2)).setText("Price: "+product.getPrice()+"$");
			    }
			    else
			    	if(customer.getIsSubscriber()==1 && product.getHasActiveSale()==1) {
			    		((Label)desc.getChildren().get(2)).setText("Price: "+product.getDiscount_Price()+"$");
			    	}
			    if(!EkrutClientUI.client_control.ekrut_client.curr_order.getOrder_type().equals("Delivery")) {
			    ((Label)desc.getChildren().get(3)).setText("Available Quantity: "+product.getProduct_Quantity());
			     }
			    else {
			    	((Label)desc.getChildren().get(3)).setText("");
			    }
			    if(product.getProduct_Quantity()==0 && !EkrutClientUI.client_control.ekrut_client.curr_order.getOrder_type().equals("Delivery")) {
			    	desc2.setDisable(true);
			    }
				CatalogVbox.getChildren().add(item);
				Separator separator = new Separator();
				separator.setStyle("-fx-background-color: #3D4956; -fx-border-width: 5");
				CatalogVbox.getChildren().add(separator);				
				Button add = (Button) desc2.getChildren().get(1);
				add.setOnAction((event)-> {
				 amount=0;
				 quantityField = ((TextField)((HBox)desc2.getChildren().get(0)).getChildren().get(2));
					try {  
					    Integer.parseInt(quantityField.getText());  
					    int quantity = Integer.valueOf(quantityField.getText());
					    if(quantity>0) {
                        if(quantity<=product.getProduct_Quantity() || EkrutClientUI.client_control.ekrut_client.curr_order.getOrder_type().equals("Delivery")) {
                        	message_to_gui.setText("");
						EkrutClientUI.client_control.ekrut_client.curr_order.add_item(product, quantity);
						amount=0;
						message_to_gui.setTextFill(Color.BLACK);
						message_to_gui.setText(String.format("%d %s has been added to cart.",quantity,product.getProductName()));
                        System.out.println(product.getProductName()+" "+ quantity);
                        }
                        else {
                        	message_to_gui.setTextFill(Color.RED);
                        	message_to_gui.setText(String.format("There is only %d of %s in stock!",product.getProduct_Quantity(),product.getProductName()));
					      }
					    }
					    else {
					    	message_to_gui.setTextFill(Color.RED);
					    	message_to_gui.setText("Invalid input, please try again.");
					    }
                       } catch(NumberFormatException e){  
					     System.out.println("Quantity field must be a number/not empty");
					     message_to_gui.setTextFill(Color.RED);
					     message_to_gui.setText("Quantity field must be a number/not empty");
					  }  
				});
				minusbtn=((Button)((HBox)desc2.getChildren().get(0)).getChildren().get(1));
				minusbtn.setOnAction((event)-> {
					quantityField = ((TextField)((HBox)desc2.getChildren().get(0)).getChildren().get(2));
					amount=Integer.parseInt(quantityField.getText()); 
						try {  
							if(amount>0) {
								amount--;
							}
							quantityField.setText(String.valueOf(amount));
							
	                       } catch(Exception e){  
						     System.out.println("Something went wrong");
						     message_to_gui.setTextFill(Color.RED);
						     message_to_gui.setText("Something went wrong");
						  }  
					});
				
				plusbtn=((Button)((HBox)desc2.getChildren().get(0)).getChildren().get(3));
				plusbtn.setOnAction((event)-> {
					quantityField = ((TextField)((HBox)desc2.getChildren().get(0)).getChildren().get(2));
					amount=Integer.parseInt(quantityField.getText()); 
						try {  
							if(amount<product.getProduct_Quantity()) {
								amount++;
							}
							quantityField.setText(String.valueOf(amount));
							
	                       } catch(Exception e){  
	                    	   System.out.println("Something went wrong");
							     message_to_gui.setTextFill(Color.RED);
							     message_to_gui.setText("Something went wrong");
						  }  
					});
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_order_number", null));
	}	
		
	/**

	getContinue is a method that is called when the user clicks the get Continue button. It sends a manage_cart command to the server to update.
	@param event the event of clicking the set sale template button.
	@throws Exception
	*/
	public void getContinue(ActionEvent event) throws Exception{
		//System.out.println(EkrutClientUI.client_control.ekrut_client.curr_order.PrintProductsInOrder());
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("manage_cart", null));
	}
}