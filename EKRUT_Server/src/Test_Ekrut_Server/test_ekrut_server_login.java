package Test_Ekrut_Server;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.Command;
import logic.Role;
import logic.User;
import server.EkrutServer;
import server.mysqlConnection;

class test_ekrut_server_login {
	public mysqlConnection mysql;
	public EkrutServer Eserver;
	
	//public ArrayList<ArrayList<String>> userTable;
	private ArrayList<String> loginValidAccount;
	private ArrayList<String> loginNotValidAccount;
	private ArrayList<String> loginValidAccountWrongPassword;
	private ArrayList<String> loginNoPassword;
	private ArrayList<String> loginNoInput;
	private ArrayList<String> loginNullAccount;
	private User ValidCeoUser;
	

	@BeforeEach
	void setUp() throws Exception {
		loginValidAccount = new ArrayList<>(Arrays.asList("Ceo","123456"));
		loginNoPassword = new ArrayList<>(Arrays.asList("Customer1"));
		loginNoInput = new ArrayList<>();
		ValidCeoUser = new User("Ceo","214637336","Elizabeth","II",Role.CEO,"waceya1484@dmonies.com","052433951",0);
		mysqlConnection.ConnectDb("jdbc:mysql://localhost/ekrut?serverTimezone=IST","root","bbznot123");
		
	}

	
	// Functionality: checking the checkUsernameAndPassword functionality with a valid user account
	// Input: "Ceo","123456" (ArrayList<>())
	// Expected Result: ValidCeoUser
	@Test
	void TestLoginWithValidAccount() {
		Command cmd = mysqlConnection.checkUsernameAndPassword(loginValidAccount);
		User expected =(User)cmd.getData();
		assertEquals(expected.getUsername(),ValidCeoUser.getUsername());
	}
	
	
	// Functionality: checking the checkUsernameAndPassword functionality with not exist user account
	// Input: "Beo","123456" (ArrayList<>())
	// Expected Result: false
	@Test
	void TestLoginWithNonValidAccount() {
		loginNotValidAccount = new ArrayList<>(Arrays.asList("Beo","123456"));
		Command cmd = mysqlConnection.checkUsernameAndPassword(loginNotValidAccount);
		Command cmdExpected = new Command("user_check",false);
		assertEquals(cmd.getData(),cmdExpected.getData());
	}
	
	
	// Functionality: checking the checkUsernameAndPassword functionality with valid user account
	// and wrong password
	// Input: "Customer69","123456" (ArrayList<>())
	// Expected Result: false
	@Test
	void TestLoginWithValidAccountWrongPassword() {
		loginValidAccountWrongPassword = new ArrayList<>(Arrays.asList("Customer69","123456"));
		Command cmd = mysqlConnection.checkUsernameAndPassword(loginValidAccountWrongPassword);
		Command cmdExpected = new Command("user_check",false);
		assertEquals(cmd.getData(),cmdExpected.getData());
	}
	
	
	// Functionality: checking the checkUsernameAndPassword functionality with null username
	// and null password
	// Input: null,null (ArrayList<>())
	// Expected Result: false
	@Test
	void TestLoginWithNullAccount() {
		loginNullAccount = new ArrayList<>(Arrays.asList(null,null));
		Command cmd = mysqlConnection.checkUsernameAndPassword(loginNullAccount);
		Command cmdExpected = new Command("user_check",false);
		assertEquals(cmd.getData(),cmdExpected.getData());
	}
	
	
	// Functionality: checking the checkUsernameAndPassword functionality with null object
	// Input: null (Object)
	// Expected Result: false
	@Test
	void TestLoginWithNullObject() {
		Command cmd = mysqlConnection.checkUsernameAndPassword(null);
		Command cmdExpected = new Command("user_check",false);
		assertEquals(cmd.getData(),cmdExpected.getData());
	}
	
	
	// Functionality: checking the checkUsernameAndPassword functionality with only username
	// Input: "Customer1"," " (ArrayList<>())
	// Expected Result: false
	@Test
	void TestLoginWithNoPassword() {
		Command cmd = mysqlConnection.checkUsernameAndPassword(loginNoPassword);
		Command cmdExpected = new Command("user_check",false);
		assertEquals(cmd.getData(),cmdExpected.getData());
	}
	

	// Functionality: checking the checkUsernameAndPassword functionality with no input
	// Input: " "," " (ArrayList<>())
	// Expected Result: false
	@Test
	void TestLoginWithNoInput() {
		Command cmd = mysqlConnection.checkUsernameAndPassword(loginNoInput);
		Command cmdExpected = new Command("user_check",false);
		assertEquals(cmd.getData(),cmdExpected.getData());
	}
	
	// Functionality: checking the checkUsernameAndPassword functionality with already logged user
	// Input: "Ceo","123456" (ArrayList<>())
	// Expected Result: true
	@Test
	void TestLoginUserAlreadyLoggedIn() {
		Command cmd = mysqlConnection.checkUsernameAndPassword(loginValidAccount);
		User expected =(User)cmd.getData();
		assertEquals(expected.getIsLoggedIn(),ValidCeoUser.getIsLoggedIn());
	}

}
