package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Branch;
import logic.Command;
import logic.Customer;
import logic.DeliveryStatus;
import logic.Order;
import logic.Order_Status;
import logic.RegionalManager;
import logic.Report;
import logic.Supply_Method;

/**
*The ChooseReportFrameController class is a controller class that manages the Choose Report frame in the GUI.
*It allows the regional manager to choose the type of report they want to view, such as customer, inventory, or order report.
*It also allows the regional manager to select the year and month for the report and select a specific branch for the inventory report.

*The class extends the FrameController class and overrides the additionalChanges method to handle the specific functionality for the Choose Report frame.
*It uses JavaFX components such as ComboBox to provide the user with options for selecting the report type, year, month, and branch.
*It also utilizes the LocalDate class to get the current year and month to limit the options for the year and month ComboBox.
*It also communicates with the EkrutClientUI class to handle commands and retrieve data for the report.

*@author the regional manger will go to the report if exist
*/
public class ChooseReportFrameController extends FrameController {
	
	/**
	 * The year_combo ComboBox is used for the user to select a year when generating a report.
	*/

	@FXML
	private ComboBox<String> year_combo;
	
	/**
	 * The month_combo ComboBox is used for the user to select a month when generating a report.
	*/
	
	@FXML
	private ComboBox<String> month_combo;
	
	/**
	 * The report_combo ComboBox is used for the user to select the type of report they wish to generate, such as a customer report, inventory report, or order report.
	*/
	
	@FXML
	private ComboBox<String> report_combo;

	/**
	 * The branch_combo ComboBox is used for the user to select a specific branch when generating a report.
	*/
	@FXML
	private ComboBox<String> branch_combo;
	
	/**
	 * The item_combo ComboBox is used for the user to select a specific item when generating an inventory report.
	*/
	
	@FXML
	private ComboBox<String> item_combo;
	
	
	//@FXML
	//private ComboBox<String> deliveryMethod_combo;
	
	RegionalManager regMan;
	
	/**
	 * The additionalChanges() method sets the full name of the user and allows the Regional Manager to choose between different types of
	 * reports and options, and it will redirect the Regional Manager to the relevant report if it exists. 
	 * It also sets the status of the Regional Manager to the region they are managing.
	*/
	
	@Override
	public void additionalChanges() {
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());	
		regMan = (RegionalManager)EkrutClientUI.client_control.ekrut_client.user;
		status.setText(regMan.getRegion());
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_branches_table", null));		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		report_combo.getItems().addAll("Customer report" , "Inventory Report" , "Order report");
		report_combo.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // If the first combo box is selected, show the second combo box
                if (report_combo.getValue().equals("Inventory Report")) {
                	branch_combo.setVisible(true);
                	item_combo.setVisible(true);
                }
                else {
            		branch_combo.setVisible(false);
            		item_combo.setVisible(false);
                }
            }
        });	
		
		//year_combo = new ComboBox<>();
		//month_combo = new ComboBox<>();
		LocalDate current = LocalDate.now();
		int currentYear = current.getYear();
		int currentMonth = current.getMonthValue();
		
		for (int year = currentYear; year >= currentYear - 5; year--) {
			year_combo.getItems().add(Integer.toString(year));
		}

		for (int month = 1; month <= 12; month++) {
		        LocalDate date = LocalDate.of(currentYear, month, 1);
		        String formattedMonth = date.format(DateTimeFormatter.ofPattern("MM"));
		        month_combo.getItems().add(formattedMonth);
		}
		        
		for(Branch branch : EkrutClientUI.client_control.ekrut_client.BranchTable) {
			if(regMan.getRegion().equals(branch.getRegion_Name()))
					branch_combo.getItems().add(branch.getBranch_Name());	
		}
		branch_combo.setVisible(false);
		item_combo.getItems().addAll("Beverage" , "Snack" );
		item_combo.setVisible(false);
		status.setStyle("-fx-text-fill: #00FF00");
	}
	
	@Override
	public void getHomeBtn(ActionEvent event) throws Exception{
		EkrutClientUI.back_frames.clear();
		EkrutClientUI.home_frame.start(primaryStage, "/gui/RegionalManagerMainScreenFrame.fxml");
	}
	
	public void getContinue(ActionEvent event) throws Exception{
		try {
			String year = year_combo.getValue();
			String month = month_combo.getValue();
			String report = report_combo.getValue();
			String branch = branch_combo.getValue();
			String itemType = item_combo.getValue();
			LocalDate current = LocalDate.now();
			int currentYear = current.getYear();
			int currentMonth = current.getMonthValue();
			if(year == null || month ==null   ||report == null)
			{
				message_to_gui.setText("You must enter year , month and report type");
				throw new Exception();
			}
			
			if(Integer.parseInt(month) == currentMonth && Integer.parseInt(year) ==currentYear ) {
				message_to_gui.setText("The report is not ready yet.\nPlease try again at the start of the next month.");
				throw new Exception();
			}
			if(Integer.parseInt(month) > currentMonth && Integer.parseInt(year) >=currentYear ) {
				message_to_gui.setText("The report you required is too far along.");
				throw new Exception();
			}
			
			if(year !=null && month != null && report!= null) {
				if(report.equals("Inventory Report")) {
					if(branch == null || itemType == null) {
						message_to_gui.setText("You must enter mechine and item type");
						throw new Exception();
					}
				}
			}
				// new Report(year , month , branch)
				EkrutClientUI.client_control.ekrut_client.setReport(new Report(year , month , branch , itemType ,regMan.getRegion()));
				switch(report) {
					case "Order report":
						EkrutClientUI.client_control.ekrut_client.getReport().setSupply(Supply_Method.Store_PickUp);
						EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_orders",null));
						
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if(EkrutClientUI.client_control.ekrut_client.getReport().getPiechartReport().isEmpty()) {
							message_to_gui.setText("There is no report for this month");
							throw new NullPointerException();
						}
						EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("choose_pieChartReport" , null));
						break;
					case "Inventory Report":
						EkrutClientUI.client_control.ekrut_client.curr_order = new Order(branch , "pick_Up");
						EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_ThresholdPoint",EkrutClientUI.client_control.ekrut_client.getReport()));	    	
						EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_inventory",null));	    	
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						EkrutClientUI.client_control.ekrut_client.setItemType(itemType);
						if(EkrutClientUI.client_control.ekrut_client.getReport().getInventoryProduct().isEmpty()) {
							message_to_gui.setText("There is no report for this month");
							throw new NullPointerException();
						}
						EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("choose_InventoryReport" , null));
						
	
						break;
						
					case "Customer report":
						EkrutClientUI.client_control.ekrut_client.getReport().setReportTable("monthlycustomerreport");
						EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("create_activityReport" , null));
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if(EkrutClientUI.client_control.ekrut_client.getReport().getCustomerReport().isEmpty()) {
							message_to_gui.setText("There is no report for this month");
							throw new NullPointerException();
						}
						EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("choose_CustomerActivityReport" , null));
						break;
				}
		}
		catch(NullPointerException e) {
			message_to_gui.setText("There is no report for this month");
		}
		catch(Exception e) {
			System.out.println(e);
			//message_to_gui.setText("You must enter year , month and report type");
		}
	}
}
