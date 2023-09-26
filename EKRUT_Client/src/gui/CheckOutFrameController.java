package gui;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import client.EkrutClient;
import client.EkrutClient.Seconds;
import client.EkrutClientUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.Command;
import logic.Customer;
import logic.Delivery;
import logic.DeliveryStatus;
import logic.Order_Status;
import logic.Supply_Method;
import logic.User;

/**

The CheckOutFrameController class is a JavaFX controller for a CheckOut Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the CheckOut Frame and to handle events.

@author [Shahar]

@version [4.0]
*/
public class CheckOutFrameController extends FrameController{
	
	

	@FXML
	private TextField addressField;
	
	@FXML
	private TextField apartmentField;
	
	@FXML
	private TextField floorField;
	
	@FXML
	private TextField emailField;
	
	@FXML
	private TextField phonenumberField;
	
	@FXML
	private Button HomePagebtn;
	
	@FXML
	private Button Continuebtn;
	
	@FXML
	private Button CnclOrderbtn;
	
	@FXML
	private ImageView cancel_order;
	
	@FXML
	private HBox ItemHbox;
	
	@FXML
	private VBox CatalogVbox;
	
	@FXML
	private Label timer;
	
	
	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame, as well as setting the cancel order image and starting a countdown timer for the order.
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		Customer customer = (Customer)EkrutClientUI.client_control.ekrut_client.user;
		status.setText(customer.getCustomer_status().toString());
		status.setStyle("-fx-text-fill: #00FF00");
		cancel_order.setImage(new Image(this.getClass().getResourceAsStream("/cancel_order.png")));
		emailField.setText(EkrutClientUI.client_control.ekrut_client.user.getEmail());
		phonenumberField.setText(EkrutClientUI.client_control.ekrut_client.user.getPhoneNumber());
        if (EkrutClientUI.client_control.ekrut_client.countdown == null) {
        	Seconds time_s = new EkrutClient.Seconds(120);
    		String display = String.format("%02d:%02d", time_s.value / 60, time_s.value % 60);       
            timer.setText(display);
        	EkrutClientUI.client_control.ekrut_client.countdown = new java.util.Timer();
    		TimerTask close_order = new TimerTask()
    		{
    		        public void run()
    		        {
    		        	Platform.runLater(() -> {
    			    		if (EkrutClientUI.frame_ctrl instanceof CheckOutFrameController) {
    			    			try {
									((CheckOutFrameController) EkrutClientUI.frame_ctrl).CancelOrder(null);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
    			    		}
    			    		if (EkrutClientUI.frame_ctrl instanceof PaymentFrameController) {
    			    			try {
									((PaymentFrameController) EkrutClientUI.frame_ctrl).CancelOrder(null);
								} catch (Exception e) {
									
									e.printStackTrace();
								}
    			    		}
    						});  
    		        }

    		};
    		EkrutClientUI.client_control.ekrut_client.up_second = new EkrutClient.UpSecond(timer,time_s);
    		EkrutClientUI.client_control.ekrut_client.countdown.schedule(close_order, 120000);
    		EkrutClientUI.client_control.ekrut_client.countdown.scheduleAtFixedRate(EkrutClientUI.client_control.ekrut_client.up_second,0,1000);
        }
        else {
			 
			 EkrutClientUI.client_control.ekrut_client.up_second.setLabel(timer);
		 }
	}
	
	/**

	The getContinue method is responsible for getting the user's order details and sending them to the server for processing.
	@param event The action event that triggers the method.
	It send from the client new Command("payment_screen")
	@throws Exception if any error occurs while processing the order.
	*/
	public void getContinue(ActionEvent event) throws Exception{
		int orderID = EkrutClientUI.client_control.ekrut_client.curr_order.getOrder_Number();
		String fullAddress = addressField.getText();
		String apartment = apartmentField.getText();
		String floor = floorField.getText();
		String email = emailField.getText();
		String receiverName = EkrutClientUI.client_control.ekrut_client.user.getFirstName(); 
		String recevierPhoneNumber = phonenumberField.getText();
		LocalDate conformationDate = LocalDate.now();
		Instant instant = Instant.now();  // get the current instant
		Instant newInstant = instant.plus(CalculateDeliveryTime(), ChronoUnit.DAYS);  
		LocalDate  deliveryDate = newInstant.atZone(ZoneId.systemDefault()).toLocalDate();
		DeliveryStatus status = DeliveryStatus.Active;
		if(fullAddress.trim().isEmpty()||email.trim().isEmpty()||recevierPhoneNumber.trim().isEmpty()) {
		   message_to_gui.setVisible(true);
		}
		else {
		Delivery delivery = new Delivery(orderID,fullAddress+","+apartment+","+floor,receiverName,email,recevierPhoneNumber,
				conformationDate,deliveryDate,status);
		EkrutClientUI.client_control.ekrut_client.curr_delivery=delivery;
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("payment_screen", null));
		}
	}
	
	/**

	The CancelOrder method is responsible for canceling the current order and updating its status on the server.
	It also cancels the countdown timer and redirects the user back to the home page.
	It send from the client new Command("update_order")
	@param event The action event that triggers the method.
	@throws Exception if any error occurs while canceling the order.
	*/
	public void CancelOrder(ActionEvent event) throws Exception {
		EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Canceled);
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("update_order",null));
		EkrutClientUI.client_control.ekrut_client.curr_order = null;
		EkrutClientUI.client_control.ekrut_client.countdown.cancel();
		EkrutClientUI.client_control.ekrut_client.countdown = null;
		EkrutClientUI.client_control.ekrut_client.up_second = null;
		this.getHomeBtn(event);
	}
	
	/**

	The getBack method is responsible for canceling the current order and updating its status on the server.
	It also cancels the countdown timer, redirects the user back to the previous frame, and updates the order status.
	It send from the client new Command("update_order")
	@param event The action event that triggers the method.
	@throws Exception if any error occurs while canceling the order or redirecting the user.
	*/
	public void getBack(ActionEvent event) throws Exception{
		EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Canceled);
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("update_order",null));
		EkrutClientUI.client_control.ekrut_client.countdown.cancel();
		int back_frames_size = EkrutClientUI.back_frames.size();
		FrameController back = EkrutClientUI.back_frames.get(back_frames_size - 1);
		EkrutClientUI.back_frames.remove(back_frames_size - 1);
		back.start(primaryStage, back.curr_resource);
		if(EkrutClientUI.client_control.ekrut_client.curr_order.getSupply_method().equals(Supply_Method.Delivery)) {
			EkrutClientUI.client_control.ekrut_client.countdown.cancel();
		}
		EkrutClientUI.client_control.ekrut_client.countdown = null;
	    EkrutClientUI.client_control.ekrut_client.up_second = null;
		EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Canceled);
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("update_order",null));
		EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_Number(EkrutClientUI.client_control.ekrut_client.curr_order.getOrder_Number()+1);
	}
	
	/**

	The getHomeBtn method is used to navigate back to the home frame and cancel the current order. It also cancels the countdown timer and sets the current order to null.
	It send from the client new Command("update_order")
	@param event the ActionEvent that triggers the method
	@throws Exception when an error occurs while navigating to the home frame or exiting the application
	*/
	@Override
	public void getAboutBtn(ActionEvent event) throws Exception{
		EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Canceled);
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("update_order",null));
		EkrutClientUI.client_control.ekrut_client.curr_order = null;
		EkrutClientUI.client_control.ekrut_client.countdown.cancel();
		EkrutClientUI.client_control.ekrut_client.countdown = null;
		EkrutClientUI.client_control.ekrut_client.up_second = null;
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("about", null));
	}
	/**

	Overrides the getHomeBtn method of the FrameController interface. This method is used to navigate back to the customer main screen.
	It cancels the current order, cancels the countdown timer, clears the back frames, and starts the customer main screen frame.
	It send from the client new Command("update_order")
	@param event the event that triggered this method.

	@throws Exception if an error occurs while starting the customer main screen frame.
	*/
	@Override
	public void getHomeBtn(ActionEvent event) throws Exception{
		
			if(EkrutClientUI.client_control.ekrut_client.curr_order != null) {
				EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Canceled);
				EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("update_order",null));
				EkrutClientUI.client_control.ekrut_client.curr_order = null;
			}
			if(EkrutClientUI.client_control.ekrut_client.countdown != null) {
				EkrutClientUI.client_control.ekrut_client.countdown.cancel();
				EkrutClientUI.client_control.ekrut_client.countdown = null;
				EkrutClientUI.client_control.ekrut_client.up_second = null;
			}
			EkrutClientUI.back_frames.clear();
			EkrutClientUI.home_frame.start(primaryStage, "/gui/CustomerMainScreenFrame.fxml");
		}
	/**

	Overrides the getLogoutBtn method of the FrameController interface. This method is used to logout the current user.
	It cancels the current order, cancels the countdown timer, cancels the current order, sends the disconnect command and user object to the server,
	and starts the customer main screen frame.
	It send from the client new Command("update_order")
	@param event the event that triggered this method.
	@throws Exception if an error occurs while sending the disconnect command or starting the customer main screen frame.
	*/
	@Override
	public void getLogoutBtn(ActionEvent event) throws Exception{	
		EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Canceled);
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("update_order",null));
		EkrutClientUI.client_control.ekrut_client.curr_order = null;
		EkrutClientUI.client_control.ekrut_client.countdown.cancel();
		EkrutClientUI.client_control.ekrut_client.countdown = null;
		EkrutClientUI.client_control.ekrut_client.up_second = null;
		User user = EkrutClientUI.client_control.ekrut_client.user;
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("disconnect", user));
	}
	
	private int CalculateDeliveryTime() {
		Random rand = new Random();
		int deliveryTime = rand.nextInt(5);
		return deliveryTime;
	}

}
