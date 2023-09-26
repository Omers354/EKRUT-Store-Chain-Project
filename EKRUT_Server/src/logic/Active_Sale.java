package logic;

import java.io.Serializable;

/**

The Active_Sale class represents a sale product in the system. It contains information about the branch where the sale is active, the product's id, the original price and the discount percentage.

This class implements the EkrutIF interface, which defines the basic CRUD operations that can be performed on the data.

@author (Shahar)

@version (4.0)
*/
public class Active_Sale implements EkrutIF,Serializable {

	private String Branch;
	private String Product_Id;
	private float Discount_Percentage;
	private float Original_Price;
	
	/**

	Constructs a new Active_Sale object with the given parameters
	@param Branch The branch where the sale is active
	@param Product_Id The product's id
	@param Original_Price The original price of the product
	@param Discount_Percentage The discount percentage of the product
	*/
	public Active_Sale(String Branch,String Product_Id,float Original_Price,float Discount_Percentage) {
		this.Branch=Branch;
		this.Product_Id=Product_Id;
		this.Original_Price=Original_Price;
		this.Discount_Percentage=Discount_Percentage;
	}
	
	
	
	@Override
	public void insertData() {
		// TODO Auto-generated method stub
		
	}

	/**

	Gets the branch where the sale is active
	@return The branch where the sale is active
	*/
	public String getBranch() {
		return Branch;
	}


	/**

	Sets the branch where the sale is active
	@param branch The branch where the sale is active
	*/
	public void setBranch(String branch) {
		Branch = branch;
	}

	/**

	Gets the product's id
	@return The product's id
	*/
	public String getProduct_Id() {
		return Product_Id;
	}

	/**

	Sets the product's id
	@param product_Id The product's id
	*/
	public void setProduct_Id(String product_Id) {
		Product_Id = product_Id;
	}

	/**

	Gets the discount percentage of the product
	@return The discount percentage of the product
	*/
	public float getDiscount_Percentage() {
		return Discount_Percentage;
	}

	/**

	Sets the discount percentage of the product
	@param discount_Percentage The discount percentage of the product
	*/
	public void setDiscount_Percentage(float discount_Percentage) {
		Discount_Percentage = discount_Percentage;
	}

	/**

	Gets the Original Price of the product
	@return The Original Price of the product
	*/
	public float getOriginal_Price() {
		return Original_Price;
	}

	/**

	Sets the Original Price of the product
	@return The Original Price of the product
	*/
	public void setOriginal_Price(float original_Price) {
		Original_Price = original_Price;
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
