package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;



import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.Command;
import logic.Customer;
import logic.Order;
import logic.Product;
/**

The CatalogFrameController class is a JavaFX controller for a Catalog Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Catalog Frame and to handle events.

@author [Shahar]

@version [4.0]
*/
public class CatalogFrameController extends FrameController {
	
	@FXML
	private Button HomePagebtn;
	
	@FXML
	private Button Continuebtn;
	
	@FXML
	private ImageView view_catalog;
	
	@FXML
	private ImageView create_new_order;
	
	@FXML
	private ImageView manage_cart;
	
	@FXML
	private ImageView collect_order;
	
	@FXML
	private HBox ItemHbox;
	
	@FXML
	private VBox CatalogVbox;
	
    ArrayList<Product> products;
	
	/**
    The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame, as well as view the catalog show two Products for each row.
	It send from the client new Command("get_products")
	@throws Exception when an error occurs while exiting the application.
	*/
	@Override
	public void additionalChanges() {
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		Customer customer = (Customer)EkrutClientUI.client_control.ekrut_client.user;
		status.setText(customer.getCustomer_status().toString());
		status.setStyle("-fx-text-fill: #00FF00");
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_products",null));

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		products=EkrutClientUI.client_control.ekrut_client.getProductsTable();
		System.out.println(products.get(0).getProductID());
		URL itemURL = getClass().getResource("ItemInCatalogFrame.fxml");
		Parent anchor;
		
		for(int i=0;i<products.size();i+=2) {
			try {
				anchor = FXMLLoader.load(itemURL);
				HBox item = (HBox) anchor.getChildrenUnmodifiable().get(0);
				ImageView image1 = (ImageView) item.getChildren().get(0);
				ImageView image2 = (ImageView) item.getChildren().get(3);
			    VBox desc = (VBox) item.getChildren().get(1);
			    Separator separator1= (Separator) item.getChildren().get(2);
			    separator1.setStyle("-fx-background-color: #3D4956; -fx-border-width: 5");
			    VBox desc2 = (VBox) item.getChildren().get(4);
			    Text name1 = (Text) desc.getChildren().get(0);
			    Text name2 = (Text) desc2.getChildren().get(0);
			    if(products.size()%2==1 && i==products.size()-1) {
			    	 name1.setText(products.get(i).getProductName());
					 name1.setWrappingWidth(110);
					 name2.setText("");
					 name2.setWrappingWidth(110);
					 image1.setImage(new Image(this.getClass().getResourceAsStream(products.get(i).getImageURL())));
					 ((Label)desc.getChildren().get(1)).setText("Item ID:"+products.get(i).getProductID());
					 ((Label)desc2.getChildren().get(1)).setText("");
					 ((Label)desc.getChildren().get(2)).setText("Price:"+String.valueOf(products.get(i).getPrice()+"$"));
					 ((Label)desc2.getChildren().get(2)).setText("");
					 CatalogVbox.getChildren().add(item);
					 Separator separator2 = new Separator();
					 separator2.setStyle("-fx-background-color: #3D4956; -fx-border-width: 5");
					 CatalogVbox.getChildren().add(separator2);
					 break;
			    }
			    name1.setText(products.get(i).getProductName());
			    name1.setWrappingWidth(110);
			    name2.setText(products.get(i+1).getProductName());
			    name2.setWrappingWidth(110);
			    System.out.println(products.get(i).getProductName());
			    System.out.println(products.get(i+1).getProductName());
			    image1.setImage(new Image(this.getClass().getResourceAsStream(products.get(i).getImageURL())));
			    image2.setImage(new Image(this.getClass().getResourceAsStream(products.get(i+1).getImageURL())));
			    ((Label)desc.getChildren().get(1)).setText("Item ID:"+products.get(i).getProductID());
			    ((Label)desc2.getChildren().get(1)).setText("Item ID:"+products.get(i+1).getProductID());
			    
			    ((Label)desc.getChildren().get(2)).setText("Price:"+String.valueOf(products.get(i).getPrice()+"$"));
			    ((Label)desc2.getChildren().get(2)).setText("Price:"+String.valueOf(products.get(i+1).getPrice()+"$"));
				CatalogVbox.getChildren().add(item);
				Separator separator2 = new Separator();
				separator2.setStyle("-fx-background-color: #3D4956; -fx-border-width: 5");
				CatalogVbox.getChildren().add(separator2);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	  }
	}
	
	/**
	@param getContinue button is disable
	*/
	public void getContinue(ActionEvent event) throws Exception{

	}

}