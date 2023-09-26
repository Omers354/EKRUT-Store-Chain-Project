package logic;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * The Sale class represents a Sale. It contains information
 * about the Sale such as the Sale_ID, Product_ID, Start_Sale
 * end_Sale, Original_Price,Discount_Percentage.
 * 
@author (Shahar)

@version (4.0)
 */
public class Sale implements EkrutIF,Serializable {

	private int Sale_ID;
	private String Product_ID;
	private String Start_Sale;
	private String end_Sale;
	private float Original_Price;
	private float Discount_Percentage;
	
	public Sale(int Sale_ID,String Product_ID,String Start_Sale,String end_Sale,float Original_Price,float Discount_Percentage) {
		this.Sale_ID=Sale_ID;
		this.Product_ID=Product_ID;
		this.Start_Sale=Start_Sale;
		this.end_Sale=end_Sale;
		this.Original_Price=Original_Price;
		this.Discount_Percentage=Discount_Percentage;
	}
	
	/**

	Gets the Sale ID 
	@return The Sale ID 
	*/
	public int getSale_ID() {
		return Sale_ID;
	}

	/**

	Sets the Sale ID 
	@return The Sale ID 
	*/
	public void setSale_ID(int sale_ID) {
		Sale_ID = sale_ID;
	}

	/**

	Gets the Product ID
	@return The Product ID 
	*/
	public String getProduct_ID() {
		return Product_ID;
	}

	/**

	Sets the Product ID
	@return The Product ID 
	*/
	public void setProduct_ID(String product_ID) {
		Product_ID = product_ID;
	}

	/**

	Gets the Start Sale
	@return The Start Sale
	*/
	public String getStart_Sale() {
		return Start_Sale;
	}

	/**

	Sets the Start Sale
	@return The Start Sale
	*/
	public void setStart_Sale(String start_Sale) {
		Start_Sale = start_Sale;
	}

	/**

	Gets the End Sale
	@return The End Sale
	*/
	public String getEnd_Sale() {
		return end_Sale;
	}

	/**

	Sets the End Sale
	@return The End Sale
	*/
	public void setEnd_Sale(String end_Sale) {
		this.end_Sale = end_Sale;
	}

	/**

	Gets the Original Price
	@return The Original Price
	*/
	public float getOriginal_Price() {
		return Original_Price;
	}

	/**

	Sets the Original Price
	@return The Original Price
	*/
	public void setOriginal_Price(float original_Price) {
		Original_Price = original_Price;
	}

	/**

	Gets the Discount Percentage
	@return The Discount Percentage
	*/
	public float getDiscount_Percentage() {
		return Discount_Percentage;
	}

	/**

	Sets the Discount Percentage
	@return The Discount Percentage
	*/
	public void setDiscount_Percentage(float discount_Percentage) {
		Discount_Percentage = discount_Percentage;
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
