package logic;

/**
 * Delivery Operator constructor, creates a new instance of the DeliveryOperator class
 * 
 * @param username the username of the user
 * @param id the id of the user
 * @param first_name the first name of the user
 * @param last_name the last name of the user
 * @param role the role of the user
 * @param email the email of the user
 * @param phone_number the phone number of the user
 * @param is_logged_in the login status of the user
 */
public class DeliveryOperator extends User{
	
    private String region;
	
	/**
	 * Delivery Operator constructor, creates a new instance of the DeliveryOperator class using an existing user object.
	 *
	 * @param user an existing user object
	 */
	public DeliveryOperator(User user, String region) {
		super(user.username, user.id, user.first_name, user.last_name, user.role, user.email, user.phone_number, user.is_logged_in);
		this.region = region;
	}
	
	/**

	Gets the Region name 
	@return The Region name 
	*/
	public String getRegion() {
		return region;
	}

}
