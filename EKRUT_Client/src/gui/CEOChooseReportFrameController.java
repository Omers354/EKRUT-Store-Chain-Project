package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import client.EkrutClientUI;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Branch;
import logic.Ceo;
import logic.Command;
import logic.Customer;
import logic.DeliveryStatus;
import logic.Order;
import logic.Order_Status;
import logic.RegionalManager;
import logic.Report;
import logic.Supply_Method;

/**

The CEOChooseReportFrameController class extends the FrameController class and is responsible for handling the GUI for
the CEO report selection screen. The CEO can select from different types of reports such as customer report,
order report, and inventory report. The class contains several ComboBox elements for the CEO to choose the report type,
year, month, branch, item, region, and supply method.

The ComboBox class is a part of the JavaFX library and is used to create a drop-down list of items that a user can select from.
In this class, the ComboBox elements are used to allow the CEO to choose the report type, year, month, branch, item, region, and supply method.
*/



public class CEOChooseReportFrameController extends FrameController {

	/**
	* The year_combo ComboBox allows the user to select a specific year they want to view the reports for.
	*/
	@FXML
	private ComboBox<String> year_combo;
	/**
	* The month_combo ComboBox allows the user to select a specific month they want to view the reports for.
	*/
	
	@FXML
	private ComboBox<String> month_combo;
	
	/**
	* The report_combo ComboBox allows the user to select a specific type of report they want to view.
	*/
	
	@FXML
	private ComboBox<String> report_combo;

	/**
	* The branch_combo ComboBox allows the user to select a specific branch they want to view the reports for.
	*/
	
	@FXML
	private ComboBox<String> branch_combo;
	
	/**
	* The item_combo ComboBox allows the user to select a specific item they want to view the reports for.
	*/
	
	@FXML
	private ComboBox<String> item_combo;
	
	/**
	* The region_combo ComboBox allows the user to select a specific region they want to view the reports for.
	* */
	
	//region_combo
	@FXML
	private ComboBox<String> region_combo;
	
	/**
	* The supply_combo ComboBox allows the user to select a specific supply method they want to view the reports for.
	*/

	//supply_combo
	@FXML
	private ComboBox<String> supply_combo;
	
	/**
	 *  *
 * The additionalChanges() method sets the full name of the user and allows the CEO to choose between different types of 
 * reports and options, and it will redirect the CEO to the relevant report if it exists.
	 */
	
	@Override
	public void additionalChanges() {
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());	
		//regMan = (RegionalManager)EkrutClientUI.client_control.ekrut_client.user;
		Ceo ceo = (Ceo)EkrutClientUI.client_control.ekrut_client.user;
		//status.setText("All");
		
		//get all of the regions and tables and present it to the user
		EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_branches_table", null));		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		branch_combo.setPromptText("Choose Mechine");
		branch_combo.setVisible(false);
		report_combo.getItems().addAll("Customer Report" , "Inventory Report" , "Order Report");
		report_combo.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // If the first combo box is selected, show the second combo box
            	if(report_combo.getValue().equals("Order Report")) {
            		supply_combo.setVisible(true);
                	item_combo.setVisible(false);


            	}
            	else if (report_combo.getValue().equals("Inventory Report")) {
                	//branch_combo.setVisible(true);
                	item_combo.setVisible(true);
            		supply_combo.setVisible(false);

                }
                else {
            		branch_combo.setVisible(false);
            		item_combo.setVisible(false);
            		supply_combo.setVisible(false);

                }
            }
        });	
		
		//only after the user choose the region choose the machine
		
		region_combo.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // If the first combo box is selected, show the second combo box
            	branch_combo.getItems().clear();
            	if(region_combo.getValue() !=null) {
            		branch_combo.setVisible(true);
            	}
        		for(Branch b :  EkrutClientUI.client_control.ekrut_client.BranchTable) {
        			if((region_combo.getValue().equals(b.getRegion_Name())))
        					branch_combo.getItems().add(b.getBranch_Name());
        		}
            }
        });	
		//the supply methods that available at the moment 
		supply_combo.getItems().addAll("Delivery" , "Store_PickUp" );
		supply_combo.setVisible(false);
		for(Branch b :  EkrutClientUI.client_control.ekrut_client.BranchTable) {
			if(!region_combo.getItems().contains(b.getRegion_Name()))
				region_combo.getItems().add(b.getRegion_Name());
		}
		
		//show the current year plus 5 years before
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

		branch_combo.setVisible(false);
		item_combo.getItems().addAll("Beverage" , "Snack" );
		item_combo.setVisible(false);
		status.setStyle("-fx-text-fill: #00FF00");

	}
	
	@Override
	public void getHomeBtn(ActionEvent event) throws Exception{
		EkrutClientUI.back_frames.clear();
		EkrutClientUI.home_frame.start(primaryStage, "/gui/CEOMainScreenFrame.fxml");
	}
	
	/**
	* Handles the event when the user clicks on the "Continue" button in the CEO report selection screen.
	* It performs several checks such as validating the inputs, checking if the report is available for the 
	* selected date and redirecting the user to the relevant report. If any required input is missing, it will throw an exception
	* with an appropriate error message.
	*/
	
	public void getContinue(ActionEvent event) throws Exception{
		try {
			String year = year_combo.getValue();
			String month = month_combo.getValue();
			String report = report_combo.getValue();
			String branch = branch_combo.getValue();
			String itemType = item_combo.getValue();
			String region = region_combo.getValue();
			String supply = supply_combo.getValue();
			LocalDate current = LocalDate.now();
			int currentYear = current.getYear();
			int currentMonth = current.getMonthValue();
			if(year ==null || month == null ||report == null || region == null )
			{
				message_to_gui.setText("You must enter year , month , report and region type");
				throw new Exception();
			}
			
			if(Integer.parseInt(month) == currentMonth && Integer.parseInt(year) ==currentYear ) {
				message_to_gui.setText("The report is not ready yet.Please try again at the start of the next month.");
				throw new Exception();
			}
			if(Integer.parseInt(month) > currentMonth && Integer.parseInt(year) >=currentYear ) {
				message_to_gui.setText("The report you required is too far along.");
				throw new Exception();
			}
			
			if(!year.equals(null) && !month.equals(null) && !report.equals(null)) {
				if(report.equals("Inventory Report")) {
					if(branch == null) {
							message_to_gui.setText("You must enter year , month and report-type.");
							throw new Exception();
					}	
				}else if(report.equals("Order Report")) {
					if(supply == null) {
						message_to_gui.setText("You must enter supply method");
						throw new Exception("You must enter supply method");
					}	
					
				}
			}
				// new Report(year , month , branch)
				EkrutClientUI.client_control.ekrut_client.setReport(new Report(year , month , branch , itemType ,region));
				switch(report) {
					case "Order Report":
						EkrutClientUI.client_control.ekrut_client.getReport().setSupply(Supply_Method.valueOf(supply));
						EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_orders",null));
						
						try {
							///////////////////////////
							Thread.sleep(300);
							//////////////////////////
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
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
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						EkrutClientUI.client_control.ekrut_client.setItemType(itemType);
						EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("choose_InventoryReport" , null));
						break;
						
					case "Customer Report":
						EkrutClientUI.client_control.ekrut_client.getReport().setReportTable("monthlycustomerreport");
						EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(new Command("create_activityReport" , null));
						try {
							/////////////////////////
							Thread.sleep(300);
							////////////////////////
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
			message_to_gui.setText(e.getMessage());
			//System.out.println(e.getMessage());
			//System.out.println(e);
			//e.printStackTrace();
		}
		catch(Exception e) {
			//System.out.println(e);
			//message_to_gui.setText("You must enter year , month and report type");
		}
	}
}
