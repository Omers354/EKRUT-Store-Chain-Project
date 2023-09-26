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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
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
import logic.RegionalManager;
import logic.Report;
import javafx.scene.chart.PieChart;

/**

The PieChartReportController class is responsible for controlling the PieChart Report frame.
It displays a pie chart representing the data that was requested in the report. The data can be displayed as percentages and order amounts by 
hovering over the sections of the pie chart.
The class also includes functionality for returning to the main screen for the user.

*/

public class PieChartReportController extends FrameController {
	/**
	* The pieChart is used to represent the data in a pie chart format.
	*/
	@FXML
	private PieChart pieChart ;

	/**
	* The pieChartData is used to store the data that will be used to populate the pie chart.
	*/
	@FXML
	private ObservableList<Data> pieChartData;

	/**
	* The caption is used to display the title of the pie chart.
	*/
	@FXML
	private Label caption;

	/**
	* The additionalChanges method is used to set the data for the pie chart and the user's information on the frame.
	* It also makes sure that the correct data is displayed depending on the user's role.
	* This method is called when the frame is loaded.
	*/
	
	@Override
	public void additionalChanges() {
			full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
			Report report = (Report) EkrutClientUI.client_control.ekrut_client.getReport();

			if(EkrutClientUI.client_control.ekrut_client.user instanceof  RegionalManager) {
				RegionalManager regMan = (RegionalManager)EkrutClientUI.client_control.ekrut_client.user;
				status.setText(regMan.getRegion());
			}
			else {
				Ceo ceo = (Ceo)EkrutClientUI.client_control.ekrut_client.user;
				user_type.setText(ceo.getRole().toString());
			}
			int amaunt=0;
			for(String key : report.getPiechartReport().keySet()) {
				System.out.println( report.getPiechartReport().keySet());					 
			}
			pieChartData = FXCollections.observableArrayList();
			try {
				for(Entry<String, Integer> s : report.getPiechartReport().entrySet()) {
					pieChartData.add(new Data(s.getKey() , s.getValue()));
					amaunt +=s.getValue();
				}
				final int count = amaunt;
				pieChart.setData(pieChartData);
			    pieChart.setLabelsVisible(true);
			    pieChart.getData().forEach(data-> {
			    	String precentage = String.format("Percentage : %.2f%%\nOrder Amount : %d", (data.getPieValue() / count)* 100 ,(int)data.getPieValue()  );
			    	Tooltip tooltip = new Tooltip(precentage);
			    	Tooltip.install(data.getNode(), tooltip);			
			    });  
				
			}catch(Exception e) {e.printStackTrace();}
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
