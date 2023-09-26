package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;




import logic.Active_Sale;
import logic.Branch;
import logic.Ceo;
import logic.Command;
import logic.Customer;
import logic.CustomerBranch;
import logic.Delivery;
import logic.DeliveryOperator;
import logic.DeliveryStatus;
import logic.Inventory;
import logic.MarketingWorker;
import logic.Order;
import logic.Order.ProductInOrder;
import logic.Order_Status;
import logic.Payment_Method;
import logic.Product;
import logic.RegionalManager;
import logic.Report;
import logic.Restock;
import logic.Role;
import logic.Sale;
import logic.Status;
import logic.Subscriber;
import logic.Supply_Method;
import logic.User;
/**
 * mysqlConnection will configure the connection and will handle all the
 * queries that the server requests.
 *
 * @version 4.0
 */
public class mysqlConnection {

	static Connection connect;

	public static boolean awaitResponse = false;
	public static boolean isMySqlConnectSucceed = false;
	public static boolean isDriverSuccssed = false;
	
	/**

	This method is used to print the SQL errors that occur while interacting with the database.
	It prints the SQLException message, SQLState, and VendorError to the console and displays them on the server's GUI.
	@param ex SQLException that occurred during database interaction
	*/
	private static void printSqlError(SQLException ex) {
		System.out.println("SQLException: " + ex.getMessage());
		EkrutServerUI.display("SQLException: " + ex.getMessage());
		System.out.println("SQLState: " + ex.getSQLState());
		EkrutServerUI.display("SQLState: " + ex.getSQLState());
		System.out.println("VendorError: " + ex.getErrorCode());
		EkrutServerUI.display("VendorError: " + ex.getErrorCode());
	}

	/**

	This method connects to the database.
	@param dbUrl the URL of the database.
	@param dbUsername the username to connect to the database.
	@param dbPassword the password to connect to the database.
	*/
	public static void ConnectDb(String dbUrl, String dbUsername, String dbPassword) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
			isDriverSuccssed = true;
		} catch (Exception ex) {
			System.out.println("Driver definition failed");
			isDriverSuccssed = false;
		}
		try {
			Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
			System.out.println("SQL connection succeed");
			connect = conn;
			isMySqlConnectSucceed = true;
		} catch (SQLException ex) {
			printSqlError(ex);
			isMySqlConnectSucceed = false;
		}
		awaitResponse = false;
	}

	/**

	This method imports users from a CSV file and insert them into the users table in the database.
	@return boolean - returns true if the import process is successful, false otherwise.
	*/
	public static boolean importUsers() {
		String sql = "DELETE FROM users";
		try {
			Statement stmt = connect.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String csvFilePath = "./users.csv";
		int batchSize = 20;
		try {
			connect.setAutoCommit(false);
			sql = "INSERT INTO users (Username, Password, ID, First_Name, Last_Name, Role, Email, Phone_Number, Is_Logged_In) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connect.prepareStatement(sql);
			BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
			String lineText = null;
			int count = 0;
			lineReader.readLine(); // skip header line
			while ((lineText = lineReader.readLine()) != null) {
				String[] data = lineText.split("	");
				String username = data[0];
				String password = data[1];
				String id = data[2];
				String first_name = data[3];
				String last_name = data[4];
				String role = data[5];
				String email = data[6];
				String phone_number = data[7];
				String isloggedin = data[8];
				statement.setString(1, username);
				statement.setString(2, password);
				statement.setString(3, id);
				statement.setString(4, first_name);
				statement.setString(5, last_name);
				statement.setString(6, role); // ROLE ENUM SET
				statement.setString(7, email);
				statement.setString(8, phone_number);
				int isloggedin_int = 0;
				try {
					isloggedin_int = Integer.parseInt(isloggedin);
				}
				catch (Exception ex) {
					System.out.println("is logged in value is not a number");
				}
				statement.setInt(9, isloggedin_int);
				statement.addBatch();
				if (count % batchSize == 0) {
					statement.executeBatch();
				}
				count++;
			}
			lineReader.close();
			// execute the remaining queries
			statement.executeBatch();
			connect.commit();
			connect.setAutoCommit(true);
			return true;

		} 
		catch (IOException ex) {
			System.err.println(ex);
		} 
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**

	This method check the provided username and password against the database and returns a command object with a boolean value indicating whether the username and password match or not.
	The command object also contains the user data if the login was successful.
	@param info  The information to be checked, it should be an ArrayList containing the username and password
	@return Command  A command object containing the result of the check, "user_check" and the value indicating if the login was successful or not. If successful, the value of the command will contain the user data.
	*/
	public static Command checkUsernameAndPassword(Object info) {
		if (info == null) {
			return new Command("user_check", false);
		} else if (info instanceof ArrayList) {

			@SuppressWarnings("unchecked")
			ArrayList<String> checkInfo = (ArrayList<String>) info;
			if (checkInfo.size() < 2) {
				return new Command("user_check", false);
			}
			User user;
			try {
				Statement stmt = connect.createStatement();
				// Import users result set
				ResultSet resSet = stmt.executeQuery("SELECT * FROM users");
				while (resSet.next()) {
					// Checking for username
					if (resSet.getString(1).equals(checkInfo.get(0))) {
						// Checking if password is ok
						if (resSet.getString(2).equals(checkInfo.get(1))) {
							user = new User(resSet.getString(1), resSet.getString(3), resSet.getString(4),
									resSet.getString(5), Role.valueOf(resSet.getString(6)), resSet.getString(7),
									resSet.getString(8), resSet.getInt(9));
						
							resSet.close();
							return new Command("user_check", getUserTypeData(user));
						}
					}
				}
				resSet.close();
			} catch (SQLException ex) {
				printSqlError(ex);
			}
		}
		return new Command("user_check", false);
	}

	/**

	This method retrieves the user data from the database based on the user's role.
	@param user_info user object
	@return the User object with additional data based on the user's role
	*/
	private static User getUserTypeData(Object user_info) {
		User user = (User) user_info;
		Role role = user.getRole();
		String id = user.getId();
		try {
			Statement stmt = connect.createStatement();
			ResultSet resSet;
			if (role == Role.Customer) {
				resSet = stmt.executeQuery("SELECT * FROM customers");
				while (resSet.next()) {
					if (resSet.getString(1).equals(id)) {
						Customer customer = new Customer(user, resSet.getInt(2), resSet.getString(3),
								Customer.CustomerStatus.valueOf(resSet.getString(4)), resSet.getInt(5),
								CustomerBranch.valueOf(resSet.getString(6)));
						if (customer.getIsSubscriber()==1) {
							PreparedStatement ps = mysqlConnection.connect
									.prepareStatement("SELECT * FROM subscribers WHERE ID = ?");
							ResultSet resSet2;
							ps.setString(1, customer.getId());
							resSet2 = ps.executeQuery();
							resSet2.next();
							customer.setSubscriber_number(resSet2.getInt(2));						
						}
						resSet.close();
						return customer;
					}
				}
				resSet.close();
			} 
			/**

			This method retrieves the user data from the database based on the user's role.
			if role=Regional_Manager, get the user region from ekrutstaff table
			@param user_info user object
			@return the User object with additional data based on the user's role
			*/
			else if (role == Role.Regional_Manager) {
				resSet = stmt.executeQuery("SELECT * FROM ekrutstaff");
				while (resSet.next()) {
					if (resSet.getString(1).equals(id)) {
						RegionalManager regMan = new RegionalManager(user, resSet.getString(3));
						resSet.close();
						return regMan;
					}
				}
				resSet.close();
			} 
			/**

			This method retrieves the user data from the database based on the user's role.
			if role=Service_Representative.
			@param user_info user object
			@return the User object with additional data based on the user's role
			*/
			else if (role == Role.Service_Representative) {
				return user;
			}
			/**

			This method retrieves the user data from the database based on the user's role.
			if role=Operation_Worker.
			@param user_info user object
			@return the User object with additional data based on the user's role
			*/
			else if (role == Role.Operation_Worker) {
				return user;
			}
			/**

			This method retrieves the user data from the database based on the user's role.
			if role=Marketing_Manager.
			@param user_info user object
			@return the User object with additional data based on the user's role
			*/
			else if (role == Role.Marketing_Manager) {
				return user;
			}
			/**

			This method retrieves the user data from the database based on the user's role.
			if role=Marketing_Worker, get the user region from ekrutstaff table
			@param user_info user object
			@return the User object with additional data based on the user's role
			*/
			else if (role == Role.Marketing_Worker) {
				resSet = stmt.executeQuery("SELECT * FROM ekrutstaff");
				while (resSet.next()) {
					if (resSet.getString(1).equals(id)) {
						MarketingWorker MarkWork = new MarketingWorker(user, resSet.getString(3));
						resSet.close();
						return MarkWork;
					}
				}
				resSet.close();
			}
			/**

			This method retrieves the user data from the database based on the user's role.
			if role=Delivery_Coordinator, get the user region from ekrutstaff table
			@param user_info user object
			@return the User object with additional data based on the user's role
			*/
			else if (role == Role.Delivery_Coordinator) {
				resSet = stmt.executeQuery("SELECT * FROM ekrutstaff");
				while (resSet.next()) {
					if (resSet.getString(1).equals(id)) {
						DeliveryOperator DeliveryOp = new DeliveryOperator(user, resSet.getString(3));
						resSet.close();
						return DeliveryOp;
					}
				}
				resSet.close();
			}
			/**

			This method retrieves the user data from the database based on the user's role.
			if role=CEO.
			@param user_info user object
			@return the User object with additional data based on the user's role
			*/
			else if (role == Role.CEO) {
				resSet = stmt.executeQuery("SELECT * FROM ekrutstaff");
				while (resSet.next()) {
					if (resSet.getString(1).equals(id)) {
						Ceo ceo = new Ceo(user);
						resSet.close();
						return ceo;
					}
				}
				resSet.close();
			} 
		}
		catch (SQLException ex) {
			printSqlError(ex);
		}
		return null;
	}

	/**

	This method changes the login status of a user based on the given user object.
	If the user is currently logged in, the method will change the user login status to logged out,
	and if the user is currently logged out, the method will change the user login status to logged in.
	@param info the user object containing the username and current login status of the user
	*/
	public static void changeUserLoginStatus(Object info) {
		User user = (User) info;
		String username = user.getUsername();
		int isLoggedIn = user.getIsLoggedIn();
		try {
			PreparedStatement ps = mysqlConnection.connect
					.prepareStatement("UPDATE users SET is_Logged_In = ? WHERE Username = ?");
			// User is connected, logging out
			if (isLoggedIn == 1) {
				ps.setInt(1, 0);
				ps.setString(2, username);
				ps.executeUpdate();
			}
			// User is disconnected, logging in
			else {
				ps.setInt(1, 1);
				ps.setString(2, username);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure: User login status can't be changed");
		}
	}

	/**

	exitServerTerminate method is used to terminate the server and log out all the users.
	It creates a statement object to execute the query to select all the users from the 'users' table.
	The method then loops through the result set and updates the 'Is_Logged_In' field of each user to 0, indicating that they are logged out.
	The result set is then closed.
	*/
	public static void exitServerTerminate() {
		try {
			Statement stmt = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// Import users result set
			ResultSet resSet = stmt.executeQuery("SELECT Username, Is_Logged_In FROM users");
			while (resSet.next()) {
				resSet.updateInt(2, 0);
				resSet.updateRow();
			}
			resSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**

	collectOrder method is used to compare pickup code from the user to pickup code from an open order from the 'orders' table.
	If the code and the customer id match the order status is change to collect by changeCollectOrderStatus method
	The result set is then closed.
	*/
	public static boolean collectOrder(Object info) {
		ArrayList<Object> arr = (ArrayList<Object>) info;
		Customer customer = (Customer) arr.get(0);
		String pickup_code = (String) arr.get(1);
		try {
			PreparedStatement ps = mysqlConnection.connect
					.prepareStatement("SELECT * FROM orders WHERE ID = ? AND Supply_Method = ? AND Order_Status = ?");
			ResultSet resSet;
			ps.setString(1, customer.getId());
			ps.setString(2, "Store_PickUp");
			ps.setString(3, "Active");
			resSet = ps.executeQuery();
			while (resSet.next()) {
				if (resSet.getString(2).equals(pickup_code)) {
					changeCollectOrderStatus(resSet.getInt(1));
					resSet.close();
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**

	updateOrder method is used to update orders from the 'orders' table.
	Payment_Method, Order_Status, Order_Number can be updated
	The result set is then closed.
	*/
	public static void updateOrder(Object info) {
		Order new_order = (Order) info;
		List<ProductInOrder> products = new_order.getItems();
		System.out.println(String.valueOf(new_order.getOrder_status()));
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement(
					"UPDATE orders SET ID = ? , Price = ? , Branch = ? , Order_Date = ? , Supply_Method = ? , Payment_Method = ? , Order_Status = ?  WHERE Order_Number = ?");
			ps.setString(1, new_order.getID());
			ps.setFloat(2, new_order.getPrice());
			ps.setString(3, new_order.getBranch());
			ps.setString(4, new_order.getOrder_Date().toString());
			ps.setString(5, String.valueOf(new_order.getSupply_method()));
			ps.setString(6, String.valueOf(new_order.getPayment_method()));
			ps.setString(7, String.valueOf(new_order.getOrder_status()));
			ps.setInt(8, new_order.getOrder_Number());
			ps.executeUpdate();
			System.out.println("Order changed succssesfuly!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure: the order status cannot be changed!");
		}

		if (new_order.getOrder_status().equals(Order_Status.Canceled)) {
			for (ProductInOrder prod : products) {
				try {
					PreparedStatement ps1 = mysqlConnection.connect.prepareStatement(
							"UPDATE inventory SET Product_Quantity = ? WHERE Branch = ? AND Product_ID = ?");

					ps1.setInt(1, prod.prod.getProduct_Quantity());
					ps1.setString(2, new_order.getBranch());
					ps1.setString(3, String.valueOf(prod.prod.getProductID()));
					ps1.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**

	updateSaleTable method is used to update Sale from the 'Sale' table when the marketing manager create new sale template.
	The result set is then closed.
	*/
	public static void updateSaleTable(Object info) {
		int SaleId = (int) info;
		try {
			PreparedStatement ps = mysqlConnection.connect
					.prepareStatement("UPDATE sales SET Start_Sale = ?, End_Sale = ?  WHERE Sale_ID = ?");
			ps.setString(1, "14:00:00");
			ps.setString(2, "16:00:00");
			ps.setInt(3, SaleId);
			ps.executeUpdate();

			System.out.println("Sale has updated succssesfuly!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure: the change coult not be!");
		}

	}

	/**

	updateSubscriberIsFirstPurchase method is used to update subscribers table from the 'subscribers' table 
	when the subscribers makes its first purchase for 20% off.
	The result set is then closed.
	*/
	public static void updateSubscriberIsFirstPurchase(Object info) {
		String subscriberId = (String) info;
		try {
			PreparedStatement ps = mysqlConnection.connect
					.prepareStatement("UPDATE subscribers SET Is_First_Purchase = ?  WHERE ID = ?");
			ps.setInt(1, 1);
			ps.setString(2, subscriberId);
			ps.executeUpdate();

			System.out.println("Subscriber changed succssesfuly!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure: the subscriber status cannot be changed!");
		}

	}

	/**

	changeCollectOrderStatus method is used to update Order Status table from the 'Orders' table when Collected
	The result set is then closed.
	*/
	private static void changeCollectOrderStatus(Object info) {
		int order_number = (int) info;
		try {
			PreparedStatement ps = mysqlConnection.connect
					.prepareStatement("UPDATE orders SET Order_Status = ? WHERE Order_Number = ?");
			ps.setString(1, "Delivered");
			ps.setInt(2, order_number);
			ps.executeUpdate();
			System.out.println("Order status changed succssesfuly!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure: the order status cannot be changed!");
		}
	}

	/**

	This method retrieves the inventory information from a MySQL database and stores it in an ArrayList of ArrayLists of Inventory objects.
	@param info an Object that should be casted to an ArrayList of Strings representing the branches to retrieve inventory information for.
	@return An ArrayList of ArrayLists of Inventory objects representing the inventory information for the specified branches. Returns null if an SQLException occurs.
	*/
	public static ArrayList<ArrayList<Inventory>> getInventoryTable(Object info) {
		ArrayList<String> region_branches = (ArrayList<String>) info;
		ArrayList<ArrayList<Inventory>> inventory = new ArrayList<ArrayList<Inventory>>();
		ArrayList<Inventory> temp = new ArrayList<Inventory>();
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement("SELECT * FROM inventory WHERE Branch = ?");
			ResultSet resSet;
			for (String s : region_branches) {
				ps.setString(1, s);
				resSet = ps.executeQuery();
				while (resSet.next()) {
					temp.add(new Inventory(resSet.getString(1), resSet.getString(2), resSet.getString(3),
							resSet.getInt(4), resSet.getInt(5)));
				}
				inventory.add(temp);
				temp = new ArrayList<Inventory>();
				resSet.close();
			}
			return inventory;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**

	This method set a new ThresholdPoints for a product information from a MySQL database and update the "inventory" table.
	The result set is then closed.
	else set a new ThresholdPoints for all of the products.
	The result set is then closed.
	*/
	public static boolean setNewThresholdPoints(Object info) {

		if (info instanceof ArrayList) {
			ArrayList<String> arr = (ArrayList<String>) info;
			try {
				PreparedStatement ps = mysqlConnection.connect
						.prepareStatement("UPDATE inventory SET Threshold_Point=? WHERE Branch=?");
				for (int i = 0; i < arr.size() - 1; i++) {
					ps.setInt(1, Integer.parseInt(arr.get(arr.size() - 1)));
					ps.setString(2, arr.get(i));
					ps.executeUpdate();
				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Inventory(branch_combo.getValue(), null, product_combo.getValue(), 0,
			// threshold)
			Inventory inventory = (Inventory) info;
			try {
				if (inventory.getProduct_name().equals("Include All Products")) {
					PreparedStatement ps = mysqlConnection.connect
							.prepareStatement("UPDATE inventory SET Threshold_Point=? WHERE Branch=?");
					ps.setInt(1, inventory.getThreshold_point());
					ps.setString(2, inventory.getBranch());
					ps.executeUpdate();
				} else {
					PreparedStatement ps = mysqlConnection.connect.prepareStatement(
							"UPDATE inventory SET Threshold_Point=? WHERE Branch=? AND Product_Name=?");
					ps.setInt(1, inventory.getThreshold_point());
					ps.setString(2, inventory.getBranch());
					ps.setString(3, inventory.getProduct_name());
					ps.executeUpdate();
				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**

	This method set a new Sale Template for a product from a MySQL database and update the "sales" table.
	For any one product there can be only one Sale Template or the same Sale Template  for all the products
	The result set is then closed.
	*/
	public static void CreateSaleTemplate(Object info) {
		Sale sale = (Sale)info;
		try {
		PreparedStatement ps = mysqlConnection.connect
				.prepareStatement("INSERT INTO sales (Product_ID,Start_Sale,End_Sale,Original_Price,Discount_Percentage) VALUES(?,?,?,?,?) ON DUPLICATE KEY UPDATE Discount_Percentage=?");
		ps.setString(1,sale.getProduct_ID());
		ps.setString(2, null);
		ps.setString(3, null);
		ps.setFloat(4, sale.getOriginal_Price());
		ps.setFloat(5, sale.getDiscount_Percentage());
		ps.setFloat(6, sale.getDiscount_Percentage());
		ps.executeUpdate();
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}

	/**

	This method retrieves user information from a MySQL database and stores it in an ArrayList of User objects.
	@param info a String representing the role of the users to retrieve information for, either "Pending", "None" or "Customer"
	@return An ArrayList of User objects representing the user information for the specified role. Returns null if an SQLException occurs.
	*/
	public static ArrayList<User> getNoneOrPendingCustomers(Object info) {
		String role = (String) info; // Role is either "Pending" or "None" or "Customer"
		ArrayList<User> pending_customers = new ArrayList<User>();
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement("SELECT * FROM users WHERE Role=?");
			ps.setString(1, role);
			ResultSet resSet = ps.executeQuery();
			while (resSet.next()) {
				// Checking for username
				if (Role.valueOf(resSet.getString(6)).toString().equals(role)) {
					pending_customers.add(new User(resSet.getString(1), resSet.getString(3), resSet.getString(4),
							resSet.getString(5), Role.valueOf(resSet.getString(6)), resSet.getString(7),
							resSet.getString(8), resSet.getInt(9)));
				}
			}
			resSet.close();
			return pending_customers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**

	This method retrieves Customer information from a MySQL database and stores it in an ArrayList of Customer objects.
	@param info a String representing the Customer information.
	@return An ArrayList of Customer objects representing the user information for the specified role. Returns null if an SQLException occurs.
	*/
	public static ArrayList<Customer> getAllCustomers(Object info) {
		ArrayList<User> users = getNoneOrPendingCustomers("Customer");
		ArrayList<Customer> all_customers = new ArrayList<Customer>();
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement("SELECT * FROM customers");
			ResultSet resSet = ps.executeQuery();
			while (resSet.next()) {
				for (User user : users) {
					if (user.getId().equals(resSet.getString(1))) {
						all_customers.add(new Customer(user, resSet.getInt(2), resSet.getString(3),
								Customer.CustomerStatus.valueOf(resSet.getString(4)), resSet.getInt(5),
								CustomerBranch.valueOf(resSet.getString(6))));
						break;
					}
				}
			}
			resSet.close();
			return all_customers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**

	This method change a Customer to be a Subscriber.
	@param info a String representing the Customer information.
	@return A Customer objects representing the user information for the specified role. Returns null if an SQLException occurs.
	*/
	public static boolean setCustomerAsSubscriber(Object info) {
		Customer customer = (Customer) info;
		try {
			PreparedStatement ps = mysqlConnection.connect
					.prepareStatement("UPDATE customers SET Is_Subscriber=? WHERE ID=?");
			ps.setInt(1, customer.getIsSubscriber());
			ps.setString(2, customer.getId());
			ps.executeUpdate();
			if (customer.getIsSubscriber() == 0) {
				ps = mysqlConnection.connect.prepareStatement("DELETE FROM subscribers WHERE ID=?");
				ps.setString(1, customer.getId());
				ps.executeUpdate();
			} else {
				ps = mysqlConnection.connect
						.prepareStatement("INSERT INTO subscribers (ID,Subscriber_Number, Is_First_Purchase) VALUES(?,?,?)");
				ps.setString(1, customer.getId());				
				ps.setInt(2, setSubscriberNumber());
				ps.setInt(3, 0);
				ps.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**

	This method set a Subscriber Number for a new Subscriber.
	@param random 6 digit number that not repeat.
	@return random 6 digit number for a Subscriber Number.
	*/
	private static int setSubscriberNumber() {
		PreparedStatement ps;
		Integer dummy = null;
		try {
			ps = mysqlConnection.connect.prepareStatement("SELECT * FROM subscribers");
			Random rand = new Random();
			dummy= rand.nextInt(899999)+100000;
			ResultSet resSet = ps.executeQuery();
			while (resSet.next()) {
				if (resSet.getInt(2)==dummy) {
					dummy = rand.nextInt(899999)+100000;
					resSet.absolute(0);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dummy;
	}
	
	/**

	This method set a Change user role to be a "Customer".
	@return true if succeed else false.
	*/
	public static boolean approveCustomer(Object info) {
		String username = (String) info;
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement("UPDATE users SET Role=? WHERE Username=?");
			ps.setString(1, "Customer");
			ps.setString(2, username);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**

	This method change a New User role "None" to "Pending".
	@return true if succeed else false.
	*/
	public static boolean setNewUserAsCustomer(Object info) {
		ArrayList<Object> arr = (ArrayList<Object>) info;
		User user = (User) arr.get(0);
		String credit_card_text = (String) arr.get(1);
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement(
					"INSERT INTO customers (ID, Credit_Card_Number, Customers_Status, Is_Subscriber, Branch) VALUES(?,?,?,?,?)");
			ps.setString(1, user.getId());
			ps.setString(2, credit_card_text);
			ps.setString(3, "Active");
			ps.setInt(4, 0);
			ps.setString(5, "Online");
			ps.executeUpdate();
			// Now we will change the Role from "None" to "Pending"
			ps = mysqlConnection.connect.prepareStatement("UPDATE users SET Role=? WHERE Username=?");
			ps.setString(1, "Pending");
			ps.setString(2, user.getUsername());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	public static boolean setRequestRestockTable(Object info) {
		ArrayList<String> arr_request_restock = (ArrayList<String>) info;
		String branch;
		String product_id;
		int desired_quantity;
		if (arr_request_restock != null) {
			branch = arr_request_restock.get(0);
			product_id = arr_request_restock.get(1);
			desired_quantity = Integer.parseInt(arr_request_restock.get(2));
			try {

				PreparedStatement ps = mysqlConnection.connect.prepareStatement(
						"INSERT INTO restock (Branch, Product_ID, Desired_Quantity, Status) VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE Desired_Quantity=? , Status=?");
				ps.setString(1, branch);
				ps.setString(2, product_id);
				ps.setInt(3, desired_quantity);
				ps.setString(4, String.valueOf(Status.Pending));
				ps.setInt(5, desired_quantity);
				ps.setString(6, String.valueOf(Status.Pending));
				ps.executeUpdate();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**

	This method retrieves the Restock Table information from a MySQL database and stores it in an ArrayList of Restock objects.
	@param info an Object that should be casted to an ArrayList of Strings representing the Branch to retrieve Restock information for.
	@return An ArrayList of Restock objects representing the Restock information for the specified branches. Returns null if an SQLException occurs.
	*/
	public static ArrayList<Restock> getRestockTable(Object info) {
		ArrayList<Restock> arr_restock_table = new ArrayList<Restock>();
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement("SELECT * FROM restock");
			ResultSet resSet = ps.executeQuery();
			while (resSet.next()) {
				arr_restock_table.add(new Restock(resSet.getString(1), resSet.getString(2), resSet.getInt(3), Status.valueOf(resSet.getString(4))));
			}
			resSet.close();
			ps = mysqlConnection.connect.prepareStatement("SELECT Branch, Product_ID, Product_Name FROM inventory WHERE Branch=? AND Product_ID=?");
			for(Restock restock : arr_restock_table) {
				if(!restock.getProduct_id().equals("All")) {
					ps.setString(1, restock.getBranch());
					ps.setString(2, restock.getProduct_id());
					resSet = ps.executeQuery();
					resSet.next();
					restock.setProduct_name(resSet.getString(3));
				}
			}
			resSet.close();
			return arr_restock_table;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure: cannot retrive restock table!");
		}
		return null;
	}
	
	/**

	This method retrieves the Sale Table information from a MySQL database and stores it in an ArrayList of Sale objects.
	@param info an Object that should be casted to an ArrayList of Strings representing the Branch to retrieve Sale information for.
	@return An ArrayList of Sale objects representing the Sale information for the specified branches. Returns null if an SQLException occurs.
	*/
	public static ArrayList<Sale> getSaleTable(Object info) {
		ArrayList<Sale> arr_sale_table = new ArrayList<>();
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement("SELECT * FROM sales");
			ResultSet resSet = ps.executeQuery();
			while (resSet.next()) {
				arr_sale_table.add(new Sale(resSet.getInt(1), resSet.getString(2),resSet.getString(3),resSet.getString(4),
						 resSet.getFloat(5), resSet.getFloat(6)));
			}
			resSet.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure: cannot retrive restock table!");
		}
		return arr_sale_table;
	}

	/**

	This method retrieves the Product Table information from a MySQL database and stores it in an ArrayList of Product objects.
	@param info an Object that should be casted to an ArrayList of Strings representing the Branch to retrieve Product and inventory information for.
	@return An ArrayList of Product objects representing the Product information for the specified branches. Returns null if an SQLException occurs.
	*/
	public static void ActivateSale(Object info) {
		Active_Sale Activate_Sale =(Active_Sale)info;
		ArrayList<Product> products =getProductsTable();
		
		if(!Activate_Sale.getProduct_Id().equals("All")){
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement("UPDATE products SET Discount_Price = ? WHERE Product_ID = ?");
			PreparedStatement ps1 = mysqlConnection.connect.prepareStatement("UPDATE inventory SET Active_Sale = ? WHERE Branch = ? AND Product_ID=?");
			ps.setString(1, String.format("%.2f", Activate_Sale.getOriginal_Price()-(Activate_Sale.getOriginal_Price()*(Activate_Sale.getDiscount_Percentage()/100))));
			ps.setString(2, Activate_Sale.getProduct_Id());
			ps.executeUpdate();
			ps1.setInt(1, 1);
			ps1.setString(2, Activate_Sale.getBranch());
			ps1.setString(3, Activate_Sale.getProduct_Id());
			ps1.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure: cannot Activate the sale!");
		   }
		}
		else {
			
			try {
				PreparedStatement ps = mysqlConnection.connect.prepareStatement("UPDATE products SET Discount_Price = ? WHERE Product_ID = ?");
				PreparedStatement ps1 = mysqlConnection.connect.prepareStatement("UPDATE inventory SET Active_Sale = ? WHERE Branch = ?");
				for(Product product : products) {
				ps.setString(1, String.format("%.2f", product.getPrice()-(product.getPrice()*(Activate_Sale.getDiscount_Percentage()/100))));
				ps.setInt(2, product.getProductID());
				ps.executeUpdate();
				}
				ps1.setInt(1, 1);
				ps1.setString(2, Activate_Sale.getBranch());
				ps1.executeUpdate();
			}
			catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Failure: cannot Activate the sale!");
			   }
		}
	}
	
	/**

	This method End the Sale of a Product.
	@param info an Object that should be casted to an ArrayList of Strings representing the Branch to retrieve Product and inventory information for.
	@return An Product of Product objects representing the Product information for the specified branches. Returns null if an SQLException occurs.
	*/
	public static void EndSale(Object info) {
		Active_Sale End_Sale =(Active_Sale)info;
		ArrayList<Product> products =getProductsTable();
		System.out.println(End_Sale.getProduct_Id());
		
		if(!End_Sale.getProduct_Id().equals("All")){
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement("UPDATE products SET Discount_Price = ? WHERE Product_ID = ?");
			PreparedStatement ps1 = mysqlConnection.connect.prepareStatement("UPDATE inventory SET Active_Sale = ? WHERE Branch = ? AND Product_ID=?");
			ps.setString(1, String.format("%.2f", End_Sale.getOriginal_Price()));
			ps.setString(2, End_Sale.getProduct_Id());
			ps.executeUpdate();
			ps1.setInt(1, 0);
			ps1.setString(2, End_Sale.getBranch());
			ps1.setString(3, End_Sale.getProduct_Id());
			ps1.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure: cannot Activate the sale!");
		   }
		}
		else {
			try {
				PreparedStatement ps = mysqlConnection.connect.prepareStatement("UPDATE products SET Discount_Price = ? WHERE Product_ID = ?");
				PreparedStatement ps1 = mysqlConnection.connect.prepareStatement("UPDATE inventory SET Active_Sale = ? WHERE Branch = ?");
				for(Product product : products) {
				ps.setString(1, String.format("%.2f", product.getPrice()));
				ps.setInt(2, product.getProductID());
				ps.executeUpdate();
				}
				ps1.setInt(1, 0);
				ps1.setString(2, End_Sale.getBranch());
				ps1.executeUpdate();
			}
			catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Failure: cannot Activate the sale!");
			   }
		}
	}
	
	/**

	This method set a New Stock a Product after restock.
	@param the Branch to retrieve Product and inventory information for.
	@return An Product of Product objects representing the Product information for the specified branches. Returns null if an SQLException occurs.
	*/
	public static boolean setNewStock(Object info) {
		Restock restock = (Restock)info;
		try {
			PreparedStatement ps;
			if(restock.getProduct_id().equals("All")) {
				ps = mysqlConnection.connect.prepareStatement(
						"UPDATE inventory SET Product_Quantity = ? WHERE Branch = ?");
				ps.setInt(1, restock.getDesired_quantity());
				ps.setString(2, restock.getBranch());
				ps.executeUpdate();
			}
			else {
				ps = mysqlConnection.connect.prepareStatement(
						"UPDATE inventory SET Product_Quantity = ? WHERE Branch = ? AND Product_ID = ?");
				ps.setInt(1, restock.getDesired_quantity());
				ps.setString(2, restock.getBranch());
				ps.setString(3, restock.getProduct_id());
				ps.executeUpdate();
			}
			ps = mysqlConnection.connect.prepareStatement(
					"UPDATE restock SET Status = ? WHERE Branch = ? AND Product_ID = ?");
			ps.setString(1, String.valueOf(Status.Done));
			ps.setString(2, restock.getBranch());
			ps.setString(3, restock.getProduct_id());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure: cannot set new stock in inventory table!");
		}
		return false;
	}
	
	/**

	This method get Products Table from "inventory" table and the price from "products" table.
	@param the Branch to retrieve Product and inventory information for.
	@return An Product of Product objects representing the Product information for the specified branches. Returns null if an SQLException occurs.
	*/
	public static ArrayList<Product> getProductsTable(Object info) {
		Order order = (Order) info;
		ArrayList<Product> products = new ArrayList<>();
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement("SELECT * FROM inventory WHERE Branch = ?");
			PreparedStatement ps1 = mysqlConnection.connect.prepareStatement("SELECT * FROM products");
			ResultSet resSet;
			ResultSet resSet1;
			ps.setString(1, order.getBranch());
			resSet = ps.executeQuery();
			resSet1 = ps1.executeQuery();
			while (resSet.next()) {
				products.add(new Product(resSet.getInt(2), resSet.getString(3), resSet.getInt(4), resSet.getInt(5),resSet.getInt(6)));
			}
			for (Product p : products) {
				while (resSet1.next()) {
					if (p.getProductID() == resSet1.getInt(2)) {
						p.setImageURL(resSet1.getString(1));
						p.setPrice(resSet1.getFloat(4));
						p.setDiscount_Price(resSet1.getFloat(5));
						p.setProductDescription(resSet1.getString(6));
						System.out.println(p.getProductDescription());
						break;
					}
				}
				resSet1.absolute(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure: cannot retrive inventory table!");
		}
		return products;
	}

	/**

	This method get the Subscriber Table from "subscribers" table.
	@param info an Object that should be casted to an ArrayList of subscribers to retrieve information for.
	@return An ArrayList of subscribers objects representing the subscribers. Returns null if an SQLException occurs.
	*/
	public static ArrayList<Subscriber> getSubscriberTable() {
		ArrayList<Subscriber> subscribers = new ArrayList<>();
		try {
			Statement stmt = connect.createStatement();
			ResultSet resSet;
			resSet = stmt.executeQuery("SELECT * FROM subscribers");
			while (resSet.next()) {
				subscribers.add(new Subscriber(resSet.getString(1), resSet.getInt(2), resSet.getInt(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subscribers;
	}

	/**

	This method get the Branches Table from "Branches" table.
	@param info an Object that should be casted to an ArrayList of branches to retrieve information for.
	@return An branches of Branches objects representing the Branches. Returns null if an SQLException occurs.
	*/
	public static ArrayList<Branch> getBranchesTable() {
		ArrayList<Branch> branches = new ArrayList<>();
		try {
			Statement stmt = connect.createStatement();
			ResultSet resSet;
			resSet = stmt.executeQuery("SELECT * FROM branch Order By Region");
			while (resSet.next()) {
				branches.add(new Branch(resSet.getString(1), resSet.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return branches;
	}

	/**

	This method get the Products Table Table from "products" table.
	@param info an Object that should be casted to an ArrayList of products to retrieve information for.
	@return An branches of products objects representing the products. Returns null if an SQLException occurs.
	*/
	public static ArrayList<Product> getProductsTable() {
		ArrayList<Product> products = new ArrayList<>();
		try {
			Statement stmt = connect.createStatement();
			ResultSet resSet;
			resSet = stmt.executeQuery("SELECT * FROM products");
			while (resSet.next()) {
				products.add(
						new Product(resSet.getString(1), resSet.getInt(2), resSet.getString(3), resSet.getFloat(4),resSet.getFloat(5)));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < products.size(); i++) {
			System.out.println(products.get(i).toString());
		}

		return products;
	}

	/**

	This method get Codes Table  Table from "orders" table.
	@param info an Object that should be casted to an ArrayList of codes to retrieve information for.
	@return An branches of codes objects representing the codes. Returns null if an SQLException occurs.
	*/
	public static ArrayList<String> getCodesTable() {
		ArrayList<String> codes = new ArrayList<>();
		try {
			Statement stmt = connect.createStatement();
			ResultSet resSet;
			resSet = stmt.executeQuery("SELECT * FROM orders");
			while (resSet.next()) {
				codes.add(resSet.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(codes.toArray().toString());
		return codes;
	}

	/**

	This method set a new Order Number in  "orders" table.
	@param random 4 digit number that not repeat.
	@return random 4 digit number that not repeat.
	*/
	public static int setOrderNumber() {
		int orderNumber = 1;
		try {
			Statement stmt = connect.createStatement();
			ResultSet resSet;
			resSet = stmt.executeQuery("SELECT Order_number FROM orders Order By Order_Number DESC LIMIT 1");
			resSet.next();
			orderNumber = (resSet.getInt(1) + 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderNumber;
	}

	/**

	Creates a new order in the database by inserting values into the "orders" table.
	Also updates the inventory of the products in the order if the supply method is set to store pickup.
	@param info The order object containing all the information for the new order.
	*/
	public static void createNewOrder(Object info) {
		Order newOrder = (Order) info;
		List<ProductInOrder> products = newOrder.getItems();
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement(
					"INSERT INTO orders (Order_Number,PickUp_Code,ID,Price,Branch,Order_Date,Supply_Method,"
							+ "Payment_Method,Order_Status,Region) VALUES(?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, newOrder.getOrder_Number());
			ps.setString(2, newOrder.getPickUp_Code());
			ps.setString(3, newOrder.getID());
			ps.setFloat(4, newOrder.getPrice());
			ps.setString(5, newOrder.getBranch());
			ps.setString(6, newOrder.getOrder_Date().toString());
			ps.setString(7, String.valueOf(newOrder.getSupply_method()));
			ps.setString(8, String.valueOf(newOrder.getPayment_method()));
			ps.setString(9, String.valueOf(newOrder.getOrder_status()));
			ps.setString(10, String.valueOf(newOrder.getRegion()));
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (newOrder.getSupply_method().equals(Supply_Method.Store_PickUp)) {
			for (ProductInOrder prod : products) {
				try {
					PreparedStatement ps1 = mysqlConnection.connect.prepareStatement(
							"UPDATE inventory SET Product_Quantity = ? WHERE Branch = ? AND Product_ID = ?");

					ps1.setInt(1, (prod.prod.getProduct_Quantity() - prod.quantity));
					ps1.setString(2, newOrder.getBranch());
					ps1.setString(3, String.valueOf(prod.prod.getProductID()));
					ps1.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**

	Creates a new Products In Order in the database by inserting values into the "productsinorder" table.
	Also updates the inventory of the products in the order.
	@param info The order object containing all the information for the new order.
	*/
	public static void createNewProductsInOrder(Object info) {
		Order newOrder = (Order) info;
		List<ProductInOrder> products = newOrder.getItems();
		for (ProductInOrder prod : products) {
			try {
				PreparedStatement ps = mysqlConnection.connect.prepareStatement(
						"INSERT INTO productsinorder (Order_Number,Product_ID,Quantity,Product_Name,Product_Price) VALUES(?,?,?,?,?)");
				PreparedStatement ps1 = mysqlConnection.connect
						.prepareStatement("UPDATE inventory SET Product_Qunatity = ? WHERE Product_Name = ?)");
				ps.setInt(1, newOrder.getOrder_Number());
				ps.setInt(2, prod.prod.getProductID());
				ps.setInt(3, prod.quantity); 
				ps.setString(4, prod.prod.getProductName());
				ps.setFloat(5, (prod.quantity * prod.prod.getPrice()));
				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**

	Creates a new Delivery  in the database by inserting values into the "deliveries" table.
	@param info The order object containing all the information for the new delivery.
	*/
	public static void createNewDelivery(Object info) {
		Delivery newDelivery = (Delivery) info;
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement(
					"INSERT INTO deliveries (Order_Number,Address,Receiver_Name,Receiver_Email,Receiver_Phone_Number,"
							+ "Confirmation_Date,Delivery_Date,Status) VALUES(?,?,?,?,?,?,?,?)");
			ps.setInt(1, newDelivery.getOrder_Number());
			ps.setString(2, newDelivery.getAddress());
			ps.setString(3, newDelivery.getReceiver_Name());
			ps.setString(4, newDelivery.getReceiver_Email());
			ps.setString(5, newDelivery.getReceiver_Phone_Number());
			ps.setString(6, newDelivery.getConformation_Date().toString());
			ps.setString(7, newDelivery.getDelivery_Date().toString());
			ps.setString(8, String.valueOf(newDelivery.getStatus()));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**

	Retrieves the order table for a specific region, month, and year from the database.
	Also updates the inventory of the products in the order if the supply method is set to store pickup.
	@param info The report object containing the region, month, year, and branch information to retrieve the order table.
	@return report object with the order table and pie chart report filled in.
	*/
	public static Report getOrderTable(Object info){
		Report report = (Report) info;
		String region = report.getRegion();
		String month = report.getMonth();
		String year = report.getYear();
		String branch = report.getBranch();
		Map <String , Integer> branchMap = new HashMap<>();
		List<Order> orderTable = new ArrayList<>();
		try {
			PreparedStatement ps1 = mysqlConnection.connect
					.prepareStatement("SELECT * FROM monthlyordersreports WHERE Region = ? AND Month = ? AND Year = ?");
			ResultSet resSet1;
			ps1.setString(1, region);
			ps1.setString(2, month);
			ps1.setString(3, year);
			resSet1 = ps1.executeQuery();
			while(resSet1.next()) {
					branchMap.put(resSet1.getString(3) , resSet1.getInt(4));
			}
				
			if(branchMap.isEmpty()) {
				PreparedStatement ps = mysqlConnection.connect.prepareStatement("SELECT * FROM orders WHERE Region = ? AND Order_Date LIKE ? AND Supply_Method = ?");
				ResultSet resSet;
				ps.setString(1, report.getRegion());
				ps.setString(2, year+"-"+month+"-%");
				ps.setString(3, report.getSupply().toString());
				resSet = ps.executeQuery();
				while (resSet.next()) {
					orderTable.add(new Order(resSet.getInt(1),resSet.getString(2),resSet.getString(3),resSet.getFloat(4) , 
							resSet.getString(5) ,	resSet.getString(6) , Supply_Method.valueOf( resSet.getString(7) ) ,
							Payment_Method.valueOf(resSet.getString(8)  ) , Order_Status.valueOf(resSet.getString(9)), resSet.getString(10)));
				}
				//to know the number of orders for each branch 
				for(Order order : orderTable) {
					System.out.println(order.getBranch());
					if(branchMap.containsKey(order.getBranch()))
						branchMap.put(order.getBranch() , branchMap.get(order.getBranch())+1);
					else
						branchMap.put(order.getBranch() , 1);
				}
				report.setPiechartReport(branchMap);
				createNewOrderReport(report);
				
			}else {
				report.setPiechartReport(branchMap);	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return report;
}

	/**

	Creates a new customer report in the database by inserting values into the "monthlycustomerreport" table.
	@param data The report object containing the customer report information to be inserted into the table.
	*/
	public static void createNewCustomerReport(Object data) {
		Report report = (Report) data;
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement
					("INSERT INTO monthlycustomerreport (Report_ID , CustomerID ,Number_Of_Orders ,Region ,Branch , Month , Year) VALUES (?,?,?,?,?,?,?)");
			int num = setCustomerReportNumber();
			for(Entry<String, Integer> s :report.getCustomerReport().entrySet()) {
				ps.setInt(1, num);
				ps.setString(2, s.getKey());
				ps.setInt(3, s.getValue());
				ps.setString(4, report.getRegion());
				ps.setString(5, report.getBranch());
				ps.setString(6, report.getMonth());
				ps.setString(7,  report.getYear());
				ps.executeUpdate();
			}
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**

	Retrieves the customer activity report for a specific region and month from the database.
	@param data The report object containing the region and month information to retrieve the customer activity report.
	@return report object with the customer activity report filled in.
	*/
	public static Report getCustomerActivityReport(Object data) {
		Report report = (Report) data;
		Map<String , Integer>map = new HashMap<>();
		String year = report.getYear();
		String month = report.getMonth();

		try {
			PreparedStatement ps1 = mysqlConnection.connect.prepareStatement("SELECT * FROM monthlycustomerreport WHERE Region = ? AND Month = ? AND Year = ?  ");
			ResultSet resSet1;
			ps1.setString(1, report.getRegion());
			ps1.setString(2, month );
			ps1.setString(3, year);
			resSet1 = ps1.executeQuery();
			while (resSet1.next()) {
				map.put(Integer.toString(resSet1.getInt(2)) , (resSet1.getInt(3)));
			}
			if(map.isEmpty()) {
				PreparedStatement ps = mysqlConnection.connect.prepareStatement("SELECT * FROM orders WHERE Region = ? AND Order_Date LIKE ? ");
				ResultSet resSet;
				ps.setString(1, report.getRegion());
				ps.setString(2, year+"-"+month+"-%");
				System.out.println(year+"-"+month+"-");
				resSet = ps.executeQuery();
				while (resSet.next()) {
						if(map.containsKey(resSet.getString(3)))
							map.put(resSet.getString(3) , map.get(resSet.getString(3))+1);
						else {
							map.put(resSet.getString(3) , 1);
						}
					}
				report.setCustomerReport(map);
				createNewCustomerReport(data);
			}else {
				report.setCustomerReport(map);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure: cannot retrive inventory table!");
		}
		return report;
	}

	/**

	Method that returns 2D StringArray (Mat) of given ResultSet.
	@param Method that returns 2D StringArray (Mat) of given ResultSet. report.
	@return Method that returns 2D StringArray (Mat) of given ResultSet.
	*/
	private static ArrayList<ArrayList<String>> convertResultSetToTable(ResultSet rs) {
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			while (rs.next()) {
				ArrayList<String> row = new ArrayList<String>();
				for (int i = 1; i <= numberOfColumns; i++)
					row.add(rs.getString(i));
				table.add(row);
			}
		} catch (SQLException ex) { // Handle any errors
			printSqlError(ex);
		}
		return table;
	}
	
	/**

	This method set a new Order Report Number in "monthlyordersreports" table.
	@param orderNumber.
	@return orderNumber.
	*/
	public static int setOrderReportNumber() {
		int orderNumber = 1,counter=1;
		  try {
			    Statement stmt = connect.createStatement();
			    ResultSet resSet;
			    resSet = stmt.executeQuery("SELECT Report_ID FROM monthlyordersreports Order By Report_ID DESC LIMIT 1");
			    if (resSet.next()) {
			      orderNumber = (resSet.getInt(1) + 1);
			    }
			  } catch (SQLException e) {
			    e.printStackTrace();
			  }
			  return orderNumber;
	}
	
	/**

	This method set a new Customer Report  Number in "monthlycustomerreport" table.
	@param orderNumber.
	@return orderNumber.
	*/
	public static int setCustomerReportNumber() {
		int orderNumber = 1,counter=1;
		  try {
			    Statement stmt = connect.createStatement();
			    ResultSet resSet;
			    resSet = stmt.executeQuery("SELECT Report_ID FROM monthlycustomerreport Order By Report_ID DESC LIMIT 1");
			    if (resSet.next()) {
			      orderNumber = (resSet.getInt(1) + 1);
			    }
			  } catch (SQLException e) {
			    e.printStackTrace();
			  }
			  return orderNumber;
	}

	/**

	create a New Order Report for a specific region and month from the database.
	@param data The report object containing the region and month information to retrieve the customer activity report.
	*/
	public static void createNewOrderReport(Object data) {

		Report report = (Report) data;
		int num = setOrderReportNumber();
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement
					("INSERT INTO monthlyordersreports (Report_ID , Region ,Branch ,OrdersAmount , Month , Year) VALUES (?,?,?,?,?,?)");
			for(Entry<String, Integer> s : report.getPiechartReport().entrySet()) {
				System.out.println(s.getKey()+ "  " + s.getValue());
				ps.setInt(1, num);
				ps.setString(2, report.getRegion());
				ps.setString(3, s.getKey());
				ps.setInt(4, s.getValue());
				ps.setString(5, report.getMonth());
				ps.setString(6, report.getYear());
				ps.executeUpdate();
			}
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**

	Retrieves the Threshold Point Counter from the database.
	@param The date a product has been restock for a Branch.
	@return product.
	*/
	public static ArrayList<Product> getThresholdPointCounter(Object data) {
		Report report = (Report) data;
		String year = report.getYear();
		String month = report.getMonth();
		String region = report.getRegion();
		ArrayList<Product> product = getPrductTableForInventory((Object)report);
		ArrayList<Product> reportProducr = new ArrayList<>();;
		try {
				PreparedStatement ps = mysqlConnection.connect.prepareStatement("SELECT * FROM restock WHERE branch = ? AND Date LIKE ? AND Status =?");
				ResultSet resSet;
				ps.setString(1, report.getBranch());
				ps.setString(2, year+"-"+month+"-%");
				ps.setString(3, "Done");
				System.out.println(year+"-"+month+"-");
				resSet = ps.executeQuery();
				System.out.println("getCustomerActivityReport");
				while (resSet.next()) {
					if(resSet.getString(2).equals("All")) {
						for(Product p : product) {
							p.setThresholdPointCounter(p.getThresholdPointCounter() +1);
							System.out.println(p.getProductID());
						}
					}
					else {
						for(Product p : product) {
							if(p.getProductID() == Integer. parseInt(resSet.getString(2))) {
								p.setThresholdPointCounter(p.getThresholdPointCounter() +1);
							}
						}
					}		
				}
				PreparedStatement ps1 = mysqlConnection.connect.prepareStatement(
						"UPDATE monthlyupdateinventory SET Threshold_PointCounter = ? WHERE Branch = ? AND Product_ID = ?");
				for(Product p : product) {
					ps1.setInt(1 , p.getThresholdPointCounter());
					ps1.setString(2, report.getBranch());
					ps1.setInt(3, p.getProductID());
					ps1.executeUpdate();
				}
		}catch(Exception e) { e.printStackTrace();}
		return product;
	}
	
	/**

	Retrieves the Prduct Table For Inventory from the database.
	@param The Inventory status in the last day of the month.
	@return products.
	*/
	public static ArrayList<Product> getPrductTableForInventory(Object info) {
		Report report = (Report) info;
		String branch = report.getBranch();
		String year = report.getYear();
		String month = report.getMonth();
		ArrayList<Product> products = new ArrayList<>();
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement("SELECT * FROM monthlyupdateinventory WHERE Branch = ? AND Date LIKE ?");
			PreparedStatement ps1 = mysqlConnection.connect.prepareStatement("SELECT * FROM products");
			ResultSet resSet;
			ResultSet resSet1;
			ps.setString(1, branch);
			ps.setString(2 , year+"-" +month+ "-%" );
			resSet = ps.executeQuery();
			resSet1 = ps1.executeQuery();
			while (resSet.next()) {
				products.add(new Product(resSet.getInt(3), resSet.getString(4), resSet.getInt(5), resSet.getInt(6)));
			}
			for (Product p : products) {
				while (resSet1.next()) {
					if (p.getProductID() == resSet1.getInt(2)) {
						p.setImageURL(resSet1.getString(1));
						p.setPrice(resSet1.getFloat(4));
						p.setDiscount_Price(resSet1.getFloat(5));
						p.setProductDescription(resSet1.getString(6));
						break;
					}
				}
				resSet1.absolute(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure: cannot retrive inventory table!");
		}
		return products;
	}
	/**

	This method  get Product Amount Number in "inventory" table and insert into "monthlyupdateinventory" table.
	@param the date.
	*/
	public static void setProductAmount(Object data) {
		LocalDate date = (LocalDate) data;
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement("SELECT Branch ,Product_ID , Product_Name , Product_Quantity , Threshold_Point  "
					+ "FROM inventory");
			ResultSet resSet;
			int num = setInventNumber();
			PreparedStatement ps1 = mysqlConnection.connect.prepareStatement("INSERT INTO monthlyupdateinventory(ReportID ,Branch ,Product_ID , Product_Name , Product_Quantity , Threshold_Point ,Date)  VALUES(?,?,?,?,?,?,?)");
			resSet = ps.executeQuery();
			while (resSet.next()) {
				ps1.setInt(1, num);
				ps1.setString(2, resSet.getString(1));
				ps1.setString(3, resSet.getString(2));
				ps1.setString(4, resSet.getString(3));
				ps1.setInt(5, resSet.getInt(4));
				ps1.setInt(6, resSet.getInt(5));
				ps1.setString(7 , date.toString());
				ps1.executeUpdate();
			}		
		}catch(Exception e) { e.printStackTrace();}
	}
	
	/**

	This method set a new Inventory Number in "monthlyupdateinventory" table.
	@param orderNumber.
	@return orderNumber.
	*/
	public static int setInventNumber() {
		int orderNumber = 1;
		try {
			Statement stmt = connect.createStatement();
			ResultSet resSet;
			resSet = stmt.executeQuery("SELECT ReportID FROM monthlyupdateinventory Order By ReportID DESC LIMIT 1");
			if(resSet.next()) {
				  orderNumber = (resSet.getInt(1) + 1);
				} else {
				  orderNumber = 1;
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderNumber;
	}

	/**

	This method retrieves the Deliveries Table information from a MySQL database and stores it in an ArrayList of Delivery objects.
	@param info an Object that should be casted to an ArrayList of Strings representing the order type Delivery to retrieve Delivery information for.
	@return An ArrayList of Delivery objects representing the Delivery information for the specified order. Returns null if an SQLException occurs.
	*/
	public static ArrayList<Delivery> getDeliveries(Object data) {
		DeliveryOperator user = (DeliveryOperator)data;
		ArrayList<Delivery> arr = new ArrayList<>();
		try {
			Statement stmt = connect.createStatement();
			ResultSet resSet;
			PreparedStatement ps = mysqlConnection.connect.prepareStatement("SELECT * FROM deliveries INNER JOIN orders ON orders.Order_Number = deliveries.Order_Number WHERE Region=?");
			ps.setString(1, user.getRegion());
			resSet = ps.executeQuery();
			ResultSetMetaData resMeta = resSet.getMetaData();
			System.out.println(resMeta.getColumnCount());
			while (resSet.next()) {
			
				// JOIN COLUMNS :
				// Order_Number, Address, Receiver_Name, Receiver_Email, Receiver_Phone_Number, Confirmation_Date, Delivery_Date, Status
				// Order_Number, PickUp_Code, ID, Price, Branch, Order_Date, Supply_Method, Payment_Method, Order_Status, Region
				// int Order_Number,String Address,String Receiver_Name,String Receiver_Email,String Receiver_Phone_Number,
				//LocalDate Conformation_Date,LocalDate Delivery_Date,DeliveryStatus status
				arr.add(new Delivery(resSet.getInt(1),resSet.getString(2),resSet.getString(3),resSet.getString(4),resSet.getString(5),resSet.getString(6),resSet.getString(7),DeliveryStatus.valueOf(resSet.getString(8))));
							
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return arr;
	}
	
	/**

	This method retrieves the Deliveries Table information from a MySQL database and uptade.
	@param when a deliverie has deliveried its status is change to Delivered.
	*/
	public static void updateDelivery(Object data) {
		Delivery new_deliv = (Delivery)data;
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement(
					"UPDATE deliveries SET Status = ? WHERE Order_Number = ?");
			ps.setString(1, String.valueOf(new_deliv.getStatus()));
			ps.setInt(2, new_deliv.getOrder_Number());			
			ps.executeUpdate();
			if (new_deliv.getStatus()==DeliveryStatus.Delivered) {
				PreparedStatement ps1 = mysqlConnection.connect.prepareStatement(
						"UPDATE orders SET Order_Status = ? WHERE Order_Number = ?");
				ps1.setString(1, String.valueOf(Order_Status.Delivered));
				ps1.setInt(2, new_deliv.getOrder_Number());			
				ps1.executeUpdate();
			}
			System.out.println("Delivery changed succssesfuly!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure: the order status cannot be changed!");
		}
		
	}

	/**

	This method retrieves only the active Deliveries from "deliveries" Table  from a MySQL database.
	@param when a deliverie has aprrove its status is change to Pending.
	@return An ArrayList of Delivery objects representing the Delivery information for the specified order. Returns null if an SQLException occurs.

	*/
	public static ArrayList<Delivery> getActiveDeliveries(Object data) {
		Customer user = (Customer)data;
		ArrayList<Delivery> arr = new ArrayList<>();
		try {
			Statement stmt = connect.createStatement();
			ResultSet resSet;
			PreparedStatement ps = mysqlConnection.connect.prepareStatement("SELECT * FROM deliveries WHERE Receiver_Email=? AND Status=?");
			ps.setString(1, user.getEmail());
			ps.setString(2, String.valueOf(DeliveryStatus.Pending));
			resSet = ps.executeQuery();
			ResultSetMetaData resMeta = resSet.getMetaData();	
			while (resSet.next()) {
				arr.add(new Delivery(resSet.getInt(1),resSet.getString(2),resSet.getString(3),resSet.getString(4),resSet.getString(5),resSet.getString(6),resSet.getString(7),DeliveryStatus.valueOf(resSet.getString(8))));
						
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}

	/**

	This method check the Subscriber Number from "subscribers" Table from a MySQL database.
	@param when a user enter its Subscriber Number in order to use the fast login its check against all the  Subscriber Numbers
	@return if exist the user can connect else a messege.

	*/
	public static Command checkSubscriberNumber(Object info) {
		if (info == null) {
			return new Command("user_check", false);
		} 
		ArrayList<String> arr = new ArrayList<String>();
		@SuppressWarnings("unchecked")
		int checkInfo = (int) info;
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement("SELECT * FROM subscribers INNER JOIN users ON subscribers.ID = users.ID WHERE Subscriber_Number=? AND Role=?");
			ps.setInt(1, checkInfo);
			ps.setString(2, String.valueOf(Role.Customer));
			ResultSet resSet = ps.executeQuery();
			if(!resSet.next()) {
				return new Command("Not_Subscriber",null);
			}
			arr.add(resSet.getString(4));
			arr.add(resSet.getString(5));
			resSet.close();
		} catch (SQLException ex) {
			printSqlError(ex);
		}
		return checkUsernameAndPassword(arr);
	}
	
	/**

	create a three Reports for a specific region and month from the database.
	@param data The reports object containing the region and month information to retrieve the customer activity report.
	*/
	public static void getMonthlyReportsCreated() {
		ArrayList<Branch> branchTable = getBranchesTable();
		LocalDate date = LocalDate.now();
		int currentDay = date.getDayOfMonth();
		int currentMonth = date.getMonthValue();
		int currentYear  = date.getYear();
		boolean piechart = false;
		boolean customerActivity = false;
		boolean stockreport = false;
		PreparedStatement ps1;
		PreparedStatement ps2;
		PreparedStatement ps3;
		try {
			ps1 = mysqlConnection.connect.prepareStatement("SELECT * FROM monthlyordersreports WHERE year = ? AND month = ?");
			ps1.setString(1,Integer.toString( currentYear));
		    ps1.setString(2, Integer.toString( currentMonth));
		    // Execute the prepared statement
		    ResultSet rs1 = ps1.executeQuery();
		    // Check the number of rows returned by the query
		    if (rs1.next()) {
		    	piechart = true;
		    }
		    if(!customerActivity) {
		    	for(Branch b: branchTable) {
		    		Report r1 =new Report(Integer.toString( currentYear), Integer.toString( currentMonth),b.getBranch_Name()  , null , b.getRegion_Name());
		    		r1.setSupply(Supply_Method.Delivery);
		    		Report r2 =new Report(Integer.toString( currentYear), Integer.toString( currentMonth),b.getBranch_Name()  , null , b.getRegion_Name());
		    		r2.setSupply(Supply_Method.Store_PickUp);
		    		getOrderTable(r1);
		    		getOrderTable(r2);
		    	}
		    }	
			ps2 = mysqlConnection.connect.prepareStatement("SELECT * FROM monthlycustomerreport WHERE year = ? AND month = ?");
			ps2.setString(1,Integer.toString( currentYear));
		    ps2.setString(2, Integer.toString( currentMonth));
		    // Execute the prepared statement
		    ResultSet rs2 = ps2.executeQuery();
		    // Check the number of rows returned by the query
		    if (rs2.next()) {
		    	customerActivity = true;
		    }
		    if(!piechart) {
		    	for(Branch b: branchTable) {
		    		Report r1 =new Report(Integer.toString( currentYear), Integer.toString( currentMonth),b.getBranch_Name()  , null , b.getRegion_Name());
		    		Report r2 =new Report(Integer.toString( currentYear), Integer.toString( currentMonth),b.getBranch_Name()  , null , b.getRegion_Name());
		    		getCustomerActivityReport(r1);
		    		getCustomerActivityReport(r2);
		    	}
		    }
			ps3 = mysqlConnection.connect.prepareStatement("SELECT * FROM monthlyupdateinventory WHERE Date LIKE ?");
			ps3.setString(1,Integer.toString( currentYear) + "-" + Integer.toString( currentMonth)+ "-%");
		    // Execute the prepared statement
		    ResultSet rs3 = ps1.executeQuery();
		    // Check the number of rows returned by the query
		    if (rs3.next()) {
		    	stockreport = true;
		    }
		    if(!stockreport) {
		    	for(Branch b: branchTable) {
					setProductAmount(LocalDate.now());
		    		Report r1 =new Report(Integer.toString( currentYear), Integer.toString( currentMonth),b.getBranch_Name()  , "Snack" , b.getRegion_Name());
		    		Report r2 =new Report(Integer.toString( currentYear), Integer.toString( currentMonth),b.getBranch_Name()  , "Beverage" , b.getRegion_Name());
		    		getThresholdPointCounter(r1);
		    		getThresholdPointCounter(r2);
		    	}
		    }   
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}