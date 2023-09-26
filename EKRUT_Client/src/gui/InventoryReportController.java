package gui;

import java.util.ArrayList;
import client.EkrutClientUI;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Ceo;
import logic.Command;
import logic.Customer;
import logic.Order;
import logic.Product;
import logic.RegionalManager;
import logic.Report;
import javafx.scene.chart.PieChart;


/**
* The InventoryReportController class extends the FrameController class and is responsible for handling the GUI for 
* the Inventory Report screen. It includes methods for populating the line chart with inventory data, setting the full 
* name and status of the user, and handling the home button event.
*
* The line chart in the Inventory Report screen displays three lines:
* 1. The amount of each product at the end of the month
* 2. The threshold point for each product
* 3. The number of times each product has been refilled.
*
* The class also provides a way to filter the products by type.
*/
public class InventoryReportController extends FrameController {
	
	/**
	 * This is the LineChart that will be used to display the data for the inventory report.
	 */
	@FXML
	private  LineChart<String, Number> lineChart;

	/**
	 * This method is responsible for updating the screen with the current user's information and
	 * populating the LineChart with the inventory data. It is called when the screen is loaded.
	 */
	
	@Override
	public void additionalChanges() {
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		//.InventoryReportController.clas.client_control.ekrut_client.handleMessageFromClientUI(new Command("get_inventory",null));	
		
		//if the user is  regional manager show the region  
		if(EkrutClientUI.client_control.ekrut_client.user instanceof  RegionalManager) {
			RegionalManager regMan = (RegionalManager)EkrutClientUI.client_control.ekrut_client.user;
			status.setText(regMan.getRegion());
		}
		else {
			Ceo ceo = (Ceo)EkrutClientUI.client_control.ekrut_client.user;
			user_type.setText(ceo.getRole().toString());
		}
		
		//To get the products with the relevant information from the Data base
		//and to set the lines we wish to see
		ArrayList <Product> products = EkrutClientUI.client_control.ekrut_client.getReport().getInventoryProduct();
		ArrayList <Product> productsType = new ArrayList<>();
		XYChart.Series<String, Number> productAmountLine = new XYChart.Series<>();
		productAmountLine.setName("Product Amount");
		XYChart.Series<String, Number> thresholdPointLine = new XYChart.Series<>();
		thresholdPointLine.setName("Threshold Point");
		XYChart.Series<String, Number> threshholdPointRestokLine = new XYChart.Series<>();
		threshholdPointRestokLine.setName("The Amaunt of time that product has been restok");
		String type = EkrutClientUI.client_control.ekrut_client.getItemType();
		for(Product p : products) {
			if(type.equals(p.getProductDescription())) {
				productsType.add(p);
			}
		}
		//set the three line based on the data 
		for(Product p : productsType) {
			productAmountLine.getData().add(new XYChart.Data<>(p.getProductName() , p.getProduct_Quantity()));
			thresholdPointLine.getData().add(new XYChart.Data<>(p.getProductName() , p.getThresholdPoint()));
			threshholdPointRestokLine.getData().add(new XYChart.Data<>(p.getProductName() , p.getThresholdPointCounter()));
		}
		
		lineChart.getData().addAll(productAmountLine ,thresholdPointLine ,threshholdPointRestokLine  );
		lineChart.getXAxis().setAutoRanging(true);
	}
	@Override
	public void getHomeBtn(ActionEvent event) throws Exception{
		EkrutClientUI.back_frames.clear();
		if(EkrutClientUI.client_control.ekrut_client.user instanceof  RegionalManager) {
			EkrutClientUI.home_frame.start(primaryStage, "/gui/RegionalManagerMainScreenFrame.fxml");
		}
		else {
			EkrutClientUI.home_frame.start(primaryStage, "/gui/CEOMainScreenFrame.fxml");

		}
	}
}
