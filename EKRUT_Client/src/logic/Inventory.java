package logic;

import java.io.Serializable;

/**
 * The Inventory class represents a Inventory of the branches.
 * It contains information such as the branch name, product_id, product_name, 
 * product_quantity, ethreshold_point. 
 * 
@author (Shahar)

@version (4.0)
 */
public class Inventory implements Serializable {
	private String branch;
	private String product_id;
	private String product_name;
	private int product_quantity;
	private int threshold_point;

	/**
	 * Creates a new Delivery object with the given information.
	 * 
	 * @param branch The branch name
	 * @param product_id The product number
	 * @param product_name The name of a product
	 * @param product_quantity The quantity of a product
	 * @param threshold_point The threshold point number of a product
	 */
	public Inventory(String branch, String product_id, String product_name, int product_quantity, int threshold_point) {
		super();
		this.branch = branch;
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_quantity = product_quantity;
		this.threshold_point = threshold_point;
	}

	/**

	Gets the Branch name 
	@return The Branch name 
	*/
	public String getBranch() {
		return branch;
	}

	/**

	Sets the Branch name 
	@return The Branch name 
	*/
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**

	Gets the Product id
	@return The Product id 
	*/
	public String getProduct_id() {
		return product_id;
	}

	/**

	Sets the Product id
	@return The Product id 
	*/
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	/**

	Gets the Product name
	@return The Product name
	*/
	public String getProduct_name() {
		return product_name;
	}

	/**

	Sets the Product name
	@return The Product name
	*/
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	/**

	Gets the Product quantity
	@return The Product quantity
	*/
	public int getProduct_quantity() {
		return product_quantity;
	}

	/**

	Sets the Product quantity
	@return The Product quantity
	*/
	public void setProduct_quantity(int product_quantity) {
		this.product_quantity = product_quantity;
	}

	/**

	Gets the Threshold point
	@return The Threshold point
	*/
	public int getThreshold_point() {
		return threshold_point;
	}

	/**

	Sets the Threshold point
	@return The Threshold point
	*/
	public void setThreshold_point(int threshold_point) {
		this.threshold_point = threshold_point;
	}
}