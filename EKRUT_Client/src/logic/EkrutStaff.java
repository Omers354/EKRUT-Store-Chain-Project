package logic;

/**
 * Ekrut Staff constructor, creates a new instance of the EkrutStaff class
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
public class EkrutStaff extends User {

	public EkrutStaff(String username, String id, String first_name, String last_name, Role role, String email,
			String phone_number, int is_logged_in) {
		super(username, id, first_name, last_name, role, email, phone_number, is_logged_in);
		
	}
}