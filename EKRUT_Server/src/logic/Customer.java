package logic;

import logic.Customer.CustomerStatus;

/**

This class represents a customer object in the system. It holds all the necessary information for a customer, such as their customer number, credit card number, status, and subscriber status.
It extends the User class and includes a customer status enumeration, which can be set to Active, Frozen, Pending, or Blocked.
@author (Shahar)

@version (4.0)
*/
public class Customer extends User {

	/**
	 * Constructor for the Customer class, which initializes all the attributes of a customer object.
	 * 
	 * @param user A User object representing the customer's basic information
	 * @param customer_number The unique customer number assigned to the customer
	 * @param credit_card_number The credit card number associated with the customer's account
	 * @param customer_status The current status of the customer's account (Active, Frozen, Pending, or Blocked)
	 * @param isSubscriber A boolean value indicating whether the customer is a subscriber or not
	 */
	public enum CustomerStatus {
		Active, Frozen, Pending, Blocked
	}

	private int customer_number;
	private String credit_card_number;
	private CustomerStatus customer_status;
	private int isSubscriber;
	private CustomerBranch branch;
	private int subscriber_number;

	public Customer(User user, int customer_number, String credit_card_number, CustomerStatus customer_status, int isSubscriber, CustomerBranch branch) {
		super(user.username, user.id, user.first_name, user.last_name, Role.Customer, user.email, user.phone_number, user.is_logged_in);
		this.customer_number = customer_number;
		this.credit_card_number = credit_card_number;
		this.customer_status = customer_status;
		this.isSubscriber = isSubscriber;
		this.branch = branch;
		this.subscriber_number=0;	
	}

	/**
	 * Get the customer number.
	 *
	 * @return The customer number
	 */
	public int getCustomer_number() {
		return customer_number;
	}

	/**
	 * Get the subscriber number.
	 *
	 * @return The subscriber number
	 */
	public int getSubscriber_number() {
		return subscriber_number;
	}

	/**
	 * Set the subscriber number.
	 *
	 * @return The subscriber number
	 */
	public void setSubscriber_number(int subscriber_number) {
		this.subscriber_number = subscriber_number;
	}

	/**
	 * Set the customer number.
	 *
	 * @return The customer number
	 */
	public void setCustomer_number(int customer_number) {
		this.customer_number = customer_number;
	}

	/**
	 * Get the credit card number.
	 *
	 * @return The credit card number
	 */
	public String getCredit_card_number() {
		return credit_card_number;
	}

	/**
	 * Set the credit card number.
	 *
	 * @return The credit card number
	 */
	public void setCredit_card_number(String credit_card_number) {
		this.credit_card_number = credit_card_number;
	}

	/**
	 * Get the customer status.
	 *
	 * @return The customer status
	 */
	public CustomerStatus getCustomer_status() {
		return customer_status;
	}

	/**
	 * Set the customer status.
	 *
	 * @return The customer status
	 */
	public void setCustomer_status(CustomerStatus customer_status) {
		this.customer_status = customer_status;
	}

	public int getIsSubscriber() {
		return isSubscriber;
	}

	public void setIsSubscriber(int isSubscriber) {
		this.isSubscriber = isSubscriber;
	}
	
	/**
	 * Get the customer branch.
	 *
	 * @return The customer branch
	 */
	public CustomerBranch getBranch() {
		return branch;
	}
}