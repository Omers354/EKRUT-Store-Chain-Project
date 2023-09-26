package logic;

import java.io.Serializable;

/**
 * The Restock class represents an Restock. It contains information
 * about the Restock such as the branch, product_id, desired_quantity
 * status, product_name.
 * 
@author (Shahar)

@version (4.0)
 */
public class Restock implements Serializable {

	private String branch;

	private String product_id;

	private int desired_quantity;

	private Status status;
	
	private String product_name;

	public Restock(String branch, String product_id, int desired_quantity, Status status) {
		super();
		this.branch = branch;
		this.product_id = product_id;
		this.desired_quantity = desired_quantity;
		this.status = status;
		this.product_name = "All";
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

	Gets the Desired quantity to restock
	@return The Desired quantity to restock
	*/
	public int getDesired_quantity() {
		return desired_quantity;
	}

	/**

	Sets the Desired quantity to restock
	@return The Desired quantity to restock
	*/
	public void setDesired_quantity(int desired_quantity) {
		this.desired_quantity = desired_quantity;
	}

	/**

	Gets the status of the restock
	@return The status of the restock
	*/
	public Status getStatus() {
		return status;
	}

	/**

	Sets the status of the restock
	@return The status of the restock
	*/
	public void setStatus(Status status) {
		this.status = status;
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
}