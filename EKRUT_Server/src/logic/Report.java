package logic;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * The Report class represents an Report. It contains information
 * about the Report such as the year, month, branch, region, 
 * itemType, if exsist, reportNum.
 * 
@author (Shahar)

@version (4.0)
 */
public class Report implements Serializable {
	
	private String year;
	private String month;
	private String branch;
	private String region;
	private String itemType;
	private boolean exsist = false;
	private int reportNum = 0;
	private String reportTable;
	private Map<String , Integer> customerReport;
	private Map<String ,Integer> piechartReport;
	private ArrayList <Product> inventoryProduct;
	private DeliveryStatus DeliveryType;
	private Order_Status orderStatus;
	private Supply_Method supply;
	private LocalDate date;


	public Report(String year, String month, String branch , String itemType , String region) {
		this.year = year;
		this.month = month;
		this.branch = branch;
		this.itemType = itemType;
		this.region = region;
	}
	
	/**

	Gets the Report Number
	@return The Report Number
	*/
	public int getReportNum() {
		return reportNum;
	}

	/**

	Sets the Report Number
	@return The Report Number
	*/
	public void setReportNum(int reportNum) {
		this.reportNum = reportNum;
	}

	/**

	Gets the Year
	@return The Year
	*/
	public String getYear() {
		return year;
	}

	/**

	Sets the Year
	@return The Year
	*/
	public void setYear(String year) {
		this.year = year;
	}

	/**

	Gets the Month
	@return The Month
	*/
	public String getMonth() {
		return month;
	}

	/**

	Sets the Month
	@return The Month
	*/
	public void setMonth(String month) {
		this.month = month;
	}
	
	/**

	Gets the Branch
	@return The Branch
	*/
	public String getBranch() {
		return branch;
	}

	
	/**

	Sets the Branch
	@return The Branch
	*/
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	/**

	Gets the Item Type
	@return The Item Type
	*/
	public String getItemType() {
		return itemType;
	}

	/**

	Sets the Item Type
	@return The Item Type
	*/
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public boolean isExsist() {
		return exsist;
	}

	public void setExsist(boolean exsist) {
		this.exsist = exsist;
	}
	public String getReportTable() {
		return reportTable;
	}
	public void setReportTable(String reportTable) {
		this.reportTable = reportTable;
	}
	public Map<String, Integer> getCustomerReport() {
		return customerReport;
	}
	public void setCustomerReport(Map<String, Integer> customerReport) {
		this.customerReport = customerReport;
	}
	public Map<String, Integer> getPiechartReport() {
		return piechartReport;
	}
	public void setPiechartReport(Map<String, Integer> piechartReport) {
		this.piechartReport = piechartReport;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public DeliveryStatus getDeliveryType() {
		return DeliveryType;
	}
	public void setDeliveryType(DeliveryStatus deliveryType) {
		DeliveryType = deliveryType;
	}
	public Order_Status getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Order_Status orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Supply_Method getSupply() {
		return supply;
	}
	public void setSupply(Supply_Method supply) {
		this.supply = supply;
	}
	public ArrayList<Product> getInventoryProduct() {
		return inventoryProduct;
	}
	public void setInventoryProduct(ArrayList<Product> inventoryProduct) {
		this.inventoryProduct = inventoryProduct;
	}
	/**

	Gets the Date
	@return The Date
	*/
	public LocalDate getDate() {
		return date;
	}
	
	/**

	Sets the Date
	@return The Date
	*/
	public void setDate(LocalDate date) {
		this.date = date;
	}

}
