package logic;

/**
 * Ceo constructor, creates a new instance of the Ceo class
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
public class Ceo extends User{

	public Ceo(String username, String id, String first_name, String last_name, Role role, String email,
			String phone_number, int is_logged_in) {
		super(username, id, first_name, last_name, role, email, phone_number, is_logged_in);
		
	}

	/**
	 * Ceo constructor, creates a new instance of the Ceo class using an existing user object.
	 *
	 * @param user an existing user object
	 */
	public Ceo(User user) {
		super(user.username, user.id, user.first_name, user.last_name, user.role, user.email, user.phone_number, user.is_logged_in);
		// TODO Auto-generated constructor stub
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
