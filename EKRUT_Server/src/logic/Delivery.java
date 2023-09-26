package logic;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

/**
 * The Delivery class represents a delivery order made by a customer.
 * It contains information such as the order number, delivery address, 
 * receiver's name, email, and phone number, confirmation date, 
 * delivery date and the current status of the delivery.
 * 
@author (Shahar)

@version (4.0)
 */
public class Delivery implements EkrutIF,Serializable{
	
	private int Order_Number;
	private String Address;
	private String Receiver_Name;
	private String Receiver_Email;
	private String Receiver_Phone_Number;
	private LocalDate Conformation_Date;
	private LocalDate Delivery_Date;
	private DeliveryStatus status;
	
	/**
	 * Creates a new Delivery object with the given information.
	 * 
	 * @param Order_Number The order number of the delivery
	 * @param Address The delivery address
	 * @param Receiver_Name The name of the receiver
	 * @param Receiver_Email The email of the receiver
	 * @param Receiver_Phone_Number The phone number of the receiver
	 * @param Conformation_Date The date the delivery was confirmed
	 * @param Delivery_Date The scheduled delivery date
	 * @param status The current status of the delivery
	 */
	public Delivery(int Order_Number,String Address,String Receiver_Name,String Receiver_Email,String Receiver_Phone_Number,
			LocalDate Conformation_Date,LocalDate Delivery_Date,DeliveryStatus status) {
		
		this.Order_Number=Order_Number;
		this.Address=Address;
		this.Receiver_Name=Receiver_Name;
		this.Receiver_Email=Receiver_Email;
		this.Receiver_Phone_Number=Receiver_Phone_Number;
		this.Conformation_Date=Conformation_Date;
		this.Delivery_Date=Delivery_Date;
		this.status=status;
	}
	
	/**
	 * Creates a new Delivery object with the given information.
	 * 
	 * @param Order_Number The order number of the delivery
	 * @param Address The delivery address
	 * @param Receiver_Name The name of the receiver
	 * @param Receiver_Email The email of the receiver
	 * @param Receiver_Phone_Number The phone number of the receiver
	 * @param Conformation_Date The date the delivery was confirmed in string format
	 * @param Delivery_Date The scheduled delivery date in string format
	 * @param status The current status of the delivery
	 */
	public Delivery(int Order_Number,String Address,String Receiver_Name,String Receiver_Email,String Receiver_Phone_Number,
			String Conformation_Date,String Delivery_Date,DeliveryStatus status) {
		
		this.Order_Number=Order_Number;
		this.Address=Address;
		this.Receiver_Name=Receiver_Name;
		this.Receiver_Email=Receiver_Email;
		this.Receiver_Phone_Number=Receiver_Phone_Number;
		this.Conformation_Date=LocalDate.parse(Conformation_Date);
		this.Delivery_Date=LocalDate.parse(Delivery_Date);
		this.status=status;
	}

	/**
	 * Get the Order number.
	 *
	 * @return The Order number
	 */
	public int getOrder_Number() {
		return Order_Number;
	}

	/**
	 * Set the Order number.
	 *
	 * @return The Order number
	 */
	public void setOrder_Number(int order_Number) {
		Order_Number = order_Number;
	}

	/**
	 * Get the Address.
	 *
	 * @return The Address
	 */
	public String getAddress() {
		return Address;
	}


	/**
	 * Set the Address.
	 *
	 * @return The Address
	 */
	public void setAddress(String address) {
		Address = address;
	}

	/**
	 * Get the Receiver Name.
	 *
	 * @return The Receiver Name
	 */
	public String getReceiver_Name() {
		return Receiver_Name;
	}

	/**
	 * Set the Receiver Name.
	 *
	 * @return The Receiver Name
	 */
	public void setReceiver_Name(String receiver_Name) {
		Receiver_Name = receiver_Name;
	}

	/**
	 * Get the Receiver Email.
	 *
	 * @return The Receiver Email
	 */
	public String getReceiver_Email() {
		return Receiver_Email;
	}

	/**
	 * Set the Receiver Email.
	 *
	 * @return The Receiver Email
	 */
	public void setReceiver_Email(String receiver_Email) {
		Receiver_Email = receiver_Email;
	}


	/**
	 * Get the Receiver Phone Number.
	 *
	 * @return The Receiver Phone Number
	 */
	public String getReceiver_Phone_Number() {
		return Receiver_Phone_Number;
	}

	/**
	 * Set the Receiver Phone Number.
	 *
	 * @return The Receiver Phone Number
	 */
	public void setReceiver_Phone_Number(String receiver_Phone_Number) {
		Receiver_Phone_Number = receiver_Phone_Number;
	}


	/**
	 * Get the Conformation Date.
	 *
	 * @return The Conformation Date
	 */
	public LocalDate getConformation_Date() {
		return Conformation_Date;
	}

	/**
	 * Set the Conformation Date.
	 *
	 * @return The Conformation Date
	 */
	public void setConformation_Date(LocalDate conformation_Date) {
		Conformation_Date = conformation_Date;
	}

	/**
	 * Get the Delivery Date.
	 *
	 * @return The Delivery Date
	 */
	public LocalDate getDelivery_Date() {
		return Delivery_Date;
	}

	/**
	 * Set the Delivery Date.
	 *
	 * @return The Delivery Date
	 */
	public void setDelivery_Date(LocalDate delivery_Date) {
		Delivery_Date = delivery_Date;
	}

	/**
	 * Get the Delivery status.
	 *
	 * @return The Delivery status
	 */
	public DeliveryStatus getStatus() {
		return status;
	}

	/**
	 * Set the Delivery status.
	 *
	 * @return The Delivery status
	 */
	public void setStatus(DeliveryStatus status) {
		this.status = status;
	}


	@Override
	public void insertData() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeData() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateData() {
		// TODO Auto-generated method stub
		
	}
	

}
