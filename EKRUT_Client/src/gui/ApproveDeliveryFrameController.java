package gui;

import java.util.ArrayList;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import logic.Command;
import logic.Customer;
import logic.Delivery;
import logic.DeliveryStatus;
import logic.Inventory;
import logic.Restock;
import logic.User;

/**

The ApproveDeliveryFrameController class is a JavaFX controller for an Approve Delivery Frame in EKRUT Client.

The class uses the FXML framework to define the layout of the Approve Delivery Frame and to handle events.

@author [Shahar]

@version [4.0]
*/
public class ApproveDeliveryFrameController extends FrameController {

	@FXML
	private ComboBox order_combo;
	
	@FXML
	private Text delivery_details;
	
	@FXML
	private Button apprv_btn;

	private User user;
	
	private ArrayList<Delivery> arr_delivery_table;
	
	private boolean isUpdateButtonEnabled;
	
	private int chosenOrder;
	
	private Delivery chosenDelivery;

	/**

	The additionalChanges method is responsible for setting the user's full name, customer status, email, and phone number
	on the frame.
	It send from the client new Command("get_active_deliveries")
	@throws Exception if any error occurs while processing the order.
	*/
	@Override
	public void additionalChanges() {
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		Customer customer = (Customer)EkrutClientUI.client_control.ekrut_client.user;
		status.setText(customer.getCustomer_status().toString());
		status.setStyle("-fx-text-fill: #00FF00");
		apprv_btn.setDisable(true);
		isUpdateButtonEnabled = false;
		order_combo.getItems().clear();
		EkrutClientUI.client_control.ekrut_client
				.handleMessageFromClientUI(new Command("get_active_deliveries", null));
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		arr_delivery_table = EkrutClientUI.client_control.ekrut_client.arr_delivery_table;
		for (Delivery deliv : arr_delivery_table) {
			if(deliv.getStatus().toString().equals("Pending")) {
				order_combo.getItems().add(deliv.getOrder_Number());
				
			}
		}
	}
	
	/**
    This method is called when the order_combo is pressed.
	Its from the user combobox delivery
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	public void enableApprove(ActionEvent event) {
		message_to_gui.setText("");
		isUpdateButtonEnabled = true;
		apprv_btn.setDisable(false);
		if (order_combo.getItems().size() > 0) {
		chosenOrder = Integer.parseInt(order_combo.getValue().toString());
			for(Delivery deliv : arr_delivery_table) {
				if(deliv.getOrder_Number()==chosenOrder) {
					this.chosenDelivery = deliv;
									
					delivery_details.setText(deliv.getOrder_Number() + "\n" + deliv.getReceiver_Name() + "\n" + deliv.getAddress() + "\n" + deliv.getReceiver_Email() 
					+ "\n" + deliv.getConformation_Date() + "\n" + deliv.getDelivery_Date());
					delivery_details.autosize();
					break;
				}
			}
		}
	}

	/**

	This method is called when the "Approv" button is pressed.
	It send from the client new Command("update_delivery") and chosenDelivery.
	@param event the action event that triggers this method
	@throws Exception when an error occurs while exiting the application.
	*/
	public void getApproveBtn(ActionEvent event) {
		if(isUpdateButtonEnabled) {
			chosenDelivery.setStatus(DeliveryStatus.Delivered);
			EkrutClientUI.client_control.ekrut_client
			.handleMessageFromClientUI(new Command("update_delivery", chosenDelivery));
			delivery_details.setText("Delivery has been confirmed successfully");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			additionalChanges();
		}
		else {
			message_to_gui.setText("You must choose Order in order to approve");
		}
	}
}