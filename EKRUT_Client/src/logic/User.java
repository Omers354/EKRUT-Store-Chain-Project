package logic;

import java.io.Serializable;

/**
 * User constructor, creates a new instance of the User class
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
@SuppressWarnings("serial")
public class User implements EkrutIF, Serializable {
	
	protected String username;
	protected String id;
	protected String first_name;
	protected String last_name;
	protected Role role;
	protected String email;
	protected String phone_number;
	protected int is_logged_in;
	
	public User(String username, String id, String first_name, String last_name, Role role, String email,
			String phone_number, int is_logged_in) {
		super();
		this.username = username;
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.role = role;
		this.email = email;
		this.phone_number = phone_number;
		this.is_logged_in = is_logged_in;
	}

	/**

	Gets the username
	@return The username
	*/
	public String getUsername() {
		return username;
	}

	/**

	Sets the username
	@return The username
	*/
	public void setUsername(String username) {
		this.username = username;
	}

	/**

	Gets the ID
	@return The ID
	*/
	public String getId() {
		return id;
	}

	/**

	Sets the ID
	@return The ID
	*/
	public void setId(String id) {
		this.id = id;
	}

	/**

	Gets the first name
	@return The first name
	*/
	public String getFirstName() {
		return first_name;
	}

	/**

	Sets the first name
	@return The first name
	*/
	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	/**

	Gets the last name
	@return The last name
	*/
	public String getLastName() {
		return last_name;
	}

	/**

	Sets the last name
	@return The last name
	*/
	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	/**

	Gets the Role
	@return The Role
	*/
	public Role getRole() {
		return role;
	}

	/**

	Sets the Role
	@return The Role
	*/
	public void setRole(Role role) {
		this.role = role;
	}

	/**

	Gets the email
	@return The email
	*/
	public String getEmail() {
		return email;
	}

	/**

	Sets the email
	@return The email
	*/
	public void setEmail(String email) {
		this.email = email;
	}

	/**

	Gets the phone number
	@return The phone number
	*/
	public String getPhoneNumber() {
		return phone_number;
	}

	/**

	Sets the phone number
	@return The phone number
	*/
	public void setPhoneNumber(String phone_number) {
		this.phone_number = phone_number;
	}

	/**

	Gets the is logged in
	@return The is logged in
	*/
	public int getIsLoggedIn() {
		return is_logged_in;
	}

	/**

	Sets the is logged in
	@return The is logged in
	*/
	public void setIsLoggedIn(int is_logged_in) {
		this.is_logged_in = is_logged_in;
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