package gui;

import java.util.ArrayList;
import java.util.TimerTask;

import client.EkrutClient;
import client.EkrutClientUI;
import client.EkrutClient.Seconds;
import client.EkrutClient.UpSecond;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.Command;
import logic.Customer;
import logic.CustomerBranch;
import logic.Order_Status;
import logic.Payment_Method;
import logic.Subscriber;
import logic.Supply_Method;
import logic.User;

/**

The PaymentFrameController class is a JavaFX controller for a Payment Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Payment Screen  and to handle events.

@author [Shahar]

@version [4.0]
*/
public class PaymentFrameController extends FrameController{
	
	@FXML
	private TextField creditCardField;
	
	@FXML
	private TextField expiryDateYearField;
	
	@FXML
	private TextField expiryDateMonthField;
	
	@FXML
	private TextField CVVField;
	
	@FXML
	private Label TotalPrice;
	
	@FXML
	private Text messageText;
	
	@FXML
	private Text discountText;
	
	@FXML
	private TextField OwnerIDField;
	
	@FXML
	private CheckBox PsotponePaymentbox;
 
	@FXML
	private Button CnclOrderbtn;
	
	@FXML
	private Button FastPaymentBtn;
	
	@FXML
	private ImageView cancel_order;
	
	@FXML
	private ImageView fast_payment;
	
	@FXML
	private HBox ItemHbox;
	
	@FXML
	private VBox CatalogVbox;
	
	@FXML
	private Label timer;
	
	Subscriber subscriber; 

	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	It send from the client new Command("get_subscribers_table")
	if customer is a subscriber and its his fist Purchase he get 20% off
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		Customer customer = (Customer)EkrutClientUI.client_control.ekrut_client.user;
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_subscribers_table",null));
		try {
			Thread.sleep(150);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		if(customer.getIsSubscriber()==1) {
			FastPaymentBtn.setDisable(false);
			PsotponePaymentbox.setVisible(true);	
		}
		for(Subscriber subscriber : EkrutClientUI.client_control.ekrut_client.SubscriberTable) {
			if(subscriber.getID().equals(customer.getId()) && subscriber.getIs_First_Purchase()==0){
				this.subscriber=subscriber;
				EkrutClientUI.client_control.ekrut_client.curr_order.setPrice((float) (EkrutClientUI.client_control.ekrut_client.curr_order.getPrice()*0.8));
				discountText.setVisible(true);
			}
		}
		status.setText(customer.getCustomer_status().toString());
		status.setStyle("-fx-text-fill: #00FF00");
		cancel_order.setImage(new Image(this.getClass().getResourceAsStream("/cancel_order.png")));
		fast_payment.setImage(new Image(this.getClass().getResourceAsStream("/fast_payment.png")));
		creditCardField.setText(customer.getCredit_card_number());
		OwnerIDField.setText(customer.getId());
		TotalPrice.setText(String.format("Amount To Pay: %.2f$", EkrutClientUI.client_control.ekrut_client.curr_order.getPrice()));
		
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

	The CancelOrder method is responsible for canceling the current order and updating its status on the server.
	It also cancels the countdown timer and redirects the user back to the home page.
	It send from the client new Command("update_order")
	@param event The action event that triggers the method.
	@throws Exception if any error occurs while canceling the order.
	*/
	public void CancelOrder(ActionEvent event) throws Exception {
		EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Canceled);
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("update_order",null));
		EkrutClientUI.client_control.ekrut_client.curr_order.remove_order();
		EkrutClientUI.client_control.ekrut_client.countdown.cancel();
		EkrutClientUI.client_control.ekrut_client.countdown= null;
		EkrutClientUI.client_control.ekrut_client.up_second = null;
		EkrutClientUI.back_frames.clear();
		EkrutClientUI.home_frame.start(primaryStage, "/gui/CustomerMainScreenFrame.fxml");
	}
	
	
	/**

	FinishOrder is a method that is called when the user clicks the finish order button. It validates the inputted payment data, updates the order's status and payment method, creates a new delivery if necessary, updates the subscriber's first purchase status, and sends a finish_order_screen command to the server to display the finish order screen.
	@param event the event of clicking the finish order button.
	@throws Exception
	*/
	public void FinishOrder(ActionEvent event) throws Exception {
	
		if(PsotponePaymentbox.isSelected()) {
			EkrutClientUI.client_control.ekrut_client.PostponePayment = true;
		}
		String creditCardNumber = creditCardField.getText();
		String expiryDateMonth = expiryDateMonthField.getText();
		String expiryDateYear = expiryDateYearField.getText();
		String CVV = CVVField.getText(); 
		String OwnerId = OwnerIDField.getText();
		if(creditCardNumber.trim().isEmpty()||expiryDateMonth.trim().isEmpty()||expiryDateYear.trim().isEmpty() ||
				CVV.trim().isEmpty() || OwnerId.trim().isEmpty()) {
			messageText.setVisible(true);
			messageText.setText("One or more of your fields is empty! please try again.");
			messageText.setWrappingWidth(500);
		}
		else if(!ValidatePaymentData(creditCardNumber,expiryDateMonth,expiryDateYear,CVV,OwnerId)) {
		    
		}
		else {
			EkrutClientUI.client_control.ekrut_client.curr_order.setPayment_method(Payment_Method.credit_card);
			EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Active);
			if (!(((Customer) EkrutClientUI.client_control.ekrut_client.user).getBranch()==CustomerBranch.Online)) {
	    		EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Delivered);
	    	}
			EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("update_order",null));
			EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("create_productInOrder", null));
			
			if(EkrutClientUI.client_control.ekrut_client.curr_order.getOrder_type().equals(String.valueOf(Supply_Method.Delivery))) {
			   EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("create_delivery", EkrutClientUI.client_control.ekrut_client.curr_delivery));
			   
			}
			if(subscriber!=null) {
			EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("update_subscriber_is_First_Purchase",subscriber.getID()));
			subscriber.setIs_First_Purchase(1);
			}
			EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("finish_order_screen",null));
		}
	}
	
	/**

	getFastPaymentBtn is a method that is called when the user clicks the finish order button. It creates a new delivery if necessary, updates the subscriber's first purchase status, and sends a finish_order_screen command to the server to display the finish order screen.
	@param event the event of clicking the getFastPaymentBtn button.
	@throws Exception
	*/
    public void getFastPaymentBtn(ActionEvent event) throws Exception{

    	if(PsotponePaymentbox.isSelected()) {
			EkrutClientUI.client_control.ekrut_client.PostponePayment = true;
		}
    	EkrutClientUI.client_control.ekrut_client.curr_order.setPayment_method(Payment_Method.EKT);
    	EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Active);
    	if (!(((Customer) EkrutClientUI.client_control.ekrut_client.user).getBranch()==CustomerBranch.Online)) {
    		EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Delivered);
    	}
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("update_order",null));
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("create_productInOrder", null));
		if(EkrutClientUI.client_control.ekrut_client.curr_order.getOrder_type().equals(String.valueOf(Supply_Method.Delivery))) {
			   EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("create_delivery", EkrutClientUI.client_control.ekrut_client.curr_delivery));
			}
		if(subscriber!=null) {
			EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("update_subscriber_is_First_Purchase",subscriber.getID()));
			subscriber.setIs_First_Purchase(1);
			}
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("finish_order_screen",null));
	}
	public void getContinue(ActionEvent event) throws Exception{
		
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
		EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Canceled);
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("update_order",null));
		EkrutClientUI.client_control.ekrut_client.curr_order = null;
		EkrutClientUI.client_control.ekrut_client.countdown.cancel();
		EkrutClientUI.client_control.ekrut_client.countdown = null;
		EkrutClientUI.client_control.ekrut_client.up_second = null;
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
	
	/**

	The method validate the payment data and ensures that the data entered is accurate.
	@param creditCardNumber the credit card number entered by the user
	@param expiryDateMonth the expiry date month entered by the user
	@param expiryDateYear the expiry date year entered by the user
	@param CVV the CVV entered by the user
	@param OwnerId the owner id entered by the user
	@return returns a boolean indicating if the data is valid or not
	*/
	public boolean ValidatePaymentData(String creditCardNumber,String expiryDateMonth,String expiryDateYear,String CVV, String OwnerId) {
		int expirydatemonth,expirydateyear,cvv,ownerid;
		long creditcardnumber;
		creditCardNumber=creditCardNumber.replace("-","");
		try {
		creditcardnumber=Long.parseLong(creditCardNumber);
		System.out.println(creditcardnumber);
		expirydatemonth=Integer.parseInt(expiryDateMonth);
		System.out.println(expirydatemonth);
		expirydateyear=Integer.parseInt(expiryDateYear);
		System.out.println(expirydateyear);
		cvv=Integer.parseInt(CVV);
		System.out.println(cvv);
		ownerid=Integer.parseInt(OwnerId);
		System.out.println(ownerid);
		}catch(NumberFormatException e) {
			messageText.setVisible(true);
			messageText.setText("One or more of your input is incorrect! please check again.");
			return false;
		}
		
		if(cvv<100 || cvv>999) {
			messageText.setVisible(true);
			messageText.setText("your CVV input is incorrect! please check again.");
			return false;
		}
		
		if(expirydatemonth<1 || expirydatemonth>12) {
			messageText.setVisible(true);
			messageText.setText("your expiry date input is incorrect! please check again.");
			return false;
		}
		
		if(expirydateyear<23 || expirydateyear>33) {
		messageText.setVisible(true);
		messageText.setText("your expiry date input is incorrect! please check again.");
		return false;
		}
		return true;
	}
	
	/**

	Overrides the getBack method of the FrameController interface. This method is used to navigate back to the customer previous screen.
	It cancels the current order, resat the countdown timer.
	It send from the client new Command("update_order")
	@param event the event that triggered this method.
	@throws Exception if an error occurs while starting the customer main screen frame.
	*/
	@Override
	public void getBack(ActionEvent event) throws Exception{
		int back_frames_size = EkrutClientUI.back_frames.size();
		FrameController back = EkrutClientUI.back_frames.get(back_frames_size - 1);
		EkrutClientUI.back_frames.remove(back_frames_size - 1);
		back.start(primaryStage, back.curr_resource);
		if(EkrutClientUI.client_control.ekrut_client.curr_order.getSupply_method().equals(Supply_Method.Store_PickUp)) {
			EkrutClientUI.client_control.ekrut_client.countdown.cancel();
			EkrutClientUI.client_control.ekrut_client.countdown= null;
			EkrutClientUI.client_control.ekrut_client.up_second = null;
			EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_status(Order_Status.Canceled);
			EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("update_order",null));
			EkrutClientUI.client_control.ekrut_client.curr_order.setOrder_Number(EkrutClientUI.client_control.ekrut_client.curr_order.getOrder_Number()+1);
			
		}
	}
	
}
