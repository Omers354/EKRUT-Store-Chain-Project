package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
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

The CustomerActivityReportController class is a controller class for the customer activity report frame.

It is responsible for handling the logic of displaying a bar chart that shows the number of orders

made by customers in the past period, grouped by the number of orders they made: up to 10, between 10 to 20, and more than 20.

*/
public class CustomerActivityReportController extends FrameController {
	
	/**

	The bar chart that shows the number of orders made by customers in the past period
	*/
	
	@FXML
	private  BarChart<String, Number> barChart;
	/**

	This method is called after the frame is loaded. It sets the full name of the user, and their role,

	and then it retrieves the customer report from the EkrutClient and uses it to populate the bar chart.

	It groups the customers by the number of orders they made: up to 10, between 10 to 20, and more than 20.
	*/
	
	@Override
	public void additionalChanges() {
	full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());	
		
		Map<String ,Integer> CustomerMap= EkrutClientUI.client_control.ekrut_client.getReport().getCustomerReport();
		System.out.println(CustomerMap);
		if(EkrutClientUI.client_control.ekrut_client.user instanceof  RegionalManager) {
			RegionalManager regMan = (RegionalManager)EkrutClientUI.client_control.ekrut_client.user;
			status.setText(regMan.getRegion());
		}
		else {
			Ceo ceo = (Ceo)EkrutClientUI.client_control.ekrut_client.user;
			user_type.setText(ceo.getRole().toString());
		}
		//ArrayList <Product> productsType = new ArrayList<>();
		//XYChart.Series<String, Number> series = new XYChart.Series<>();
		Map<String ,Integer> counter = new HashMap<>();
		counter.put("Up to 10 orders", 0);
		counter.put("Between 10 to 20 orders", 0);
		counter.put("More than 20 orders", 0);
		//up 10
		// 10<x < 20
		//20+
		HashMap<String, XYChart.Series<String, Number>> map = new HashMap<>();
		
		for(Entry<String, Integer> entry  : CustomerMap.entrySet()) {
			if(entry.getValue() <=10)
				counter.put("Up to 10 orders" , counter.get("Up to 10 orders")+ 1 );
			else if(entry.getValue() > 10 && entry.getValue()< 20)
				counter.put("Between 10 to 20 orders" , counter.get("Between 10 to 20 orders")+1);
			else
				counter.put("More than 20 orders" , counter.get("More than 20 orders")+1);
		}
		//populate the HashMap with data

		for(Map.Entry<String, Integer> entry : counter.entrySet()){
			XYChart.Series<String, Number> series = new XYChart.Series<>();
		    series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
		    map.put(entry.getKey(),series);
		}
		for (XYChart.Series<String, Number> series : map.values()) {
		    barChart.getData().add(series);
		}
		barChart.setCategoryGap(20.0);
		barChart.setBarGap(-20.0);
		barChart.getYAxis().setTickLength(1);
		barChart.setLegendSide(Side.BOTTOM);
		barChart.setLegendVisible(false);
		//barChart.getData().add(series);
	
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
