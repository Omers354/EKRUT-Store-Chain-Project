package logic;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import logic.Order.ProductInOrder;

/**
 * The Order class represents an order made by a customer. It contains information
 * about the order such as the order number, pick-up code, customer ID, price, 
 * branch, region, order date, supply method, payment method, and order status.
 * It also has a nested class, ProductInOrder, which represents a product in the order 
 * and contains information about the product and its quantity.
 * 
@author (Shahar)

@version (4.0)
 */
public class Order implements EkrutIF,Serializable {
	
	private int Order_Number;
	private String PickUp_Code;
	private String ID;
	private float Price;
	private String Branch;
	private String Region;
	private LocalDate Order_Date;
	private String Order_Date1;
	private Supply_Method supply_method;
	
	/**

	Gets the Region name 
	@return The Region name 
	*/
	public String getRegion() {
		return Region;
	}

	/**

	Sets the Region name 
	@return The Region name 
	*/
	public void setRegion(String region) {
		Region = region;
	}

	private Payment_Method payment_method;
	private Order_Status order_status;

	/**
	 * The class ProductInOrder represents a product in the order and contains
	 * information about the product and its quantity.
	 */
	public class ProductInOrder implements EkrutIF,Serializable {	
		
		
		public Product prod;
		public int quantity;
		public ProductInOrder(Product prod, int quantity) {
			super();
			this.prod = prod;
			this.quantity = quantity;
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
	private String order_type;
	
	private List<ProductInOrder> items;
	
 
	/**
	 * The Order contains the order type the branch the order from
	 * and information about the product in the order.
	 */
	public Order(String branch, String order_type) {
		super();
		this.order_type = order_type;
		this.Branch = branch;
		items = new ArrayList<ProductInOrder>();
	}
	
	public Order(String branch) {
		super();
		this.Branch = branch;
		items = new ArrayList<ProductInOrder>();
	}
	
	public Order(int Order_Number,String PickUp_Code,String ID,float Price,String Branch,
			LocalDate Order_Date,Supply_Method supply_method,Payment_Method payment_method,
			Order_Status order_status) {
		
		this.Order_Number=Order_Number;
		this.PickUp_Code=PickUp_Code;
		this.ID=ID;
		this.Price=Price;
		this.Branch=Branch;
		this.Order_Date=Order_Date;
		this.supply_method=supply_method;
		this.payment_method=payment_method;
		this.order_status=order_status;
	}
	
	public Order(int Order_Number,String PickUp_Code,String ID,float Price,String Branch,
			String Order_Date1,Supply_Method supply_method,Payment_Method payment_method,
			Order_Status order_status,String Region) {
		
		this.Order_Number=Order_Number;
		this.PickUp_Code=PickUp_Code;
		this.ID=ID;
		this.Price=Price;
		this.Branch=Branch;
		this.Region = Region;
		this.Order_Date1=Order_Date1;
		this.supply_method=supply_method;
		this.payment_method=payment_method;
		
		this.order_status=order_status;
	}
	
	/**

	Gets the Order Number
	@return The Order Number
	*/
	public int getOrder_Number() {
		return Order_Number;
	}

	/**

	Sets the Order Number
	@return The Order Number
	*/
	public void setOrder_Number(int order_Number) {
		Order_Number = order_Number;
	}

	/**

	Gets the PickUp Code
	@return The PickUp Code
	*/
	public String getPickUp_Code() {
		return PickUp_Code;
	}

	/**

	Sets the PickUp Code
	@return The PickUp Code
	*/
	public void setPickUp_Code(String pickUp_Code) {
		PickUp_Code = pickUp_Code;
	}

	/**

	Gets the Customer ID
	@return The Customer ID
	*/
	public String getID() {
		return ID;
	}

	/**

	Sets the Customer ID
	@return The Customer ID
	*/
	public void setID(String iD) {
		ID = iD;
	}

	/**

	Gets the Item Price
	@return The Item Price
	*/
	public float getPrice() {
		return Price;
	}

	/**

	Sets the Item Price
	@return The Item Price
	*/
	public void setPrice(float price) {
		Price = price;
	}

	/**

	Gets the Branch
	@return The Branch
	*/
	public String getBranch() {
		return Branch;
	}

	/**

	Sets the Branch
	@return The Branch
	*/
	public void setBranch(String branch) {
		Branch = branch;
	}

	/**

	Gets the Order Date
	@return The Order Date
	*/
	public LocalDate getOrder_Date() {
		return Order_Date;
	}

	/**

	Sets the Order Date
	@return The Order Date
	*/
	public void setOrder_Date(LocalDate order_Date) {
		Order_Date = order_Date;
	}

	/**

	Gets the supply method
	@return The supply method
	*/
	public Supply_Method getSupply_method() {
		return supply_method;
	}

	/**

	Sets the supply method
	@return The supply method
	*/
	public void setSupply_method(Supply_Method supply_method) {
		this.supply_method = supply_method;
	}

	/**

	Gets the payment method
	@return The payment method
	*/
	public Payment_Method getPayment_method() {
		return payment_method;
	}

	/**

	Sets the payment method
	@return The payment method
	*/
	public void setPayment_method(Payment_Method payment_method) {
		this.payment_method = payment_method;
	}

	/**

	Gets the order status
	@return The order status
	*/
	public Order_Status getOrder_status() {
		return order_status;
	}

	/**

	Sets the order status
	@return The order status
	*/
	public void setOrder_status(Order_Status order_status) {
		this.order_status = order_status;
	}

	/**

	Gets the order type
	@return The order type
	*/
	public String getOrder_type() {
		return order_type;
	}

	/**

	Sets the order type
	@return The order type
	*/
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public List<ProductInOrder> getItems() {
		return items;
	}

	public void setItems(List<ProductInOrder> items) {
		this.items = items;
	}
	
	/**

	Remove the order from DB

	*/
	public synchronized void remove_order() {
		
		items.removeAll(items);

	}

	/**

	Add items to the order

	*/
	public synchronized void add_item(Product item, int quantity) {
		ProductInOrder temp = null;
		for(ProductInOrder productInOrder : items) {
			if(productInOrder.prod.getProductName().equals(item.getProductName())) {
				temp = productInOrder;
			}
		}
		if (temp != null)
			items.remove(temp);
		items.add(new ProductInOrder(item,quantity));
	}
	
	/**

	Remove items to the order

	*/
	public synchronized void remove_item(Product item) {
		ProductInOrder temp = null;
		for(ProductInOrder productInOrder : items) {
			if(productInOrder.prod.getProductName().equals(item.getProductName())) {
				temp = productInOrder;
			}
		}
		items.remove(temp);
	}
	
	public String PrintProductsInOrder() {
		StringBuilder strbuild = new StringBuilder();
		for(ProductInOrder prodInOrder : items) {
			strbuild.append(prodInOrder.prod.getProductName()+" "+prodInOrder.quantity);
			strbuild.append("\n");
		}
		return strbuild.toString();
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

	public String getOrder_Date1() {
		return Order_Date1;
	}

	public void setOrder_Date1(String order_Date1) {
		Order_Date1 = order_Date1;
	}
	
}
