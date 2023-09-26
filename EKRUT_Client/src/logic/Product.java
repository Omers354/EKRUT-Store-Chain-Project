package logic;

import java.io.Serializable;

/**
 * The Product class represents an Product. It contains information
 * about the Product such as the product Name, product ID, product Description, ImageURL, 
 * Price, Discount Price, ThresholdPoint, hasActiveSale.
 * 
@author (Shahar)

@version (4.0)
 */
public class Product implements EkrutIF, Serializable{
	private String productName;
	private int productID;
	private String productDescription;
	private String ImageURL;
	private float Price;
	private float Discount_Price;
	private int ThresholdPoint;
	private int hasActiveSale;
	private int Product_Quantity;
	private ProductStatus status;
	
	private String Date;
	private int ThresholdPointCounter =0;
	
	public Product(String productName, int productID, String productDescription, int ThresholdPoint,
			int Product_Quantity,ProductStatus status) {
		this.productName = productName;
		this.productID = productID;
		this.productDescription = productDescription;
		this.ThresholdPoint = ThresholdPoint;
		this.Product_Quantity = Product_Quantity;
		this.status = status.available;
	}
	
	public Product(int productID,String productName,int Product_Quantity,int ThresholdPoint,int hasActiveSale) {
		this.productID = productID;
		this.productName = productName;
		this.Product_Quantity = Product_Quantity;
		this.ThresholdPoint = ThresholdPoint;
		this.hasActiveSale=hasActiveSale;
	}
	
	public Product(String ImageURL,int productID,String productName,float Price,float Discount_Price) {
		this.ImageURL = ImageURL;
		this.productName = productName;
		this.productID = productID;
		this.Price = Price;
		this.Discount_Price=Discount_Price;
	}
	

	public Product(int productID , String productName,int Product_Quantity,int ThresholdPoint) {
		this.productID = productID;
		this.productName = productName;
		this.Product_Quantity = Product_Quantity;
		this.ThresholdPoint = ThresholdPoint;
	}
	
	/**

	Gets the product Name 
	@return The product Name 
	*/
	public String getProductName() {
		return productName;
	}

	/**

	Sets the product Name 
	@return The product Name 
	*/
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**

	Gets the product ID 
	@return The product ID 
	*/
	public int getProductID() {
		return productID;
	}

	/**

	Sets the product ID 
	@return The product ID 
	*/
	public void setProductID(int productID) {
		this.productID = productID;
	}

	/**

	Gets the product Description 
	@return The product Description 
	*/
	public String getProductDescription() {
		return productDescription;
	}

	/**

	Sets the product Description 
	@return The product Description 
	*/
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	/**

	Gets the product ImageURL 
	@return The product ImageURL 
	*/
	public String getImageURL() {
		return ImageURL;
	}

	/**

	Sets the product ImageURL 
	@return The product ImageURL 
	*/
	public void setImageURL(String ImageURL) {
		this.ImageURL = ImageURL;
	}

	/**

	Gets the product ThresholdPoint 
	@return The product ThresholdPoint 
	*/
	public int getThresholdPoint() {
		return ThresholdPoint;
	}

	/**

	Sets the product ThresholdPoint 
	@return The product ThresholdPoint 
	*/
	public void setThresholdPoint(int thresholdPoint) {
		ThresholdPoint = thresholdPoint;
	}

	/**

	Gets the product Quantity 
	@return The product Quantity 
	*/
	public int getProduct_Quantity() {
		return Product_Quantity;
	}

	/**

	Sets the product Quantity 
	@return The product Quantity 
	*/
	public void setProduct_Quantity(int product_Quantity) {
		Product_Quantity = product_Quantity;
	}

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}
	
	/**

	Gets the product Price 
	@return The product Price 
	*/
	public float getPrice() {
		return Price;
	}

	/**

	Sets the product Price 
	@return The product Price 
	*/
	public void setPrice(float price) {
		Price = price;
	}

	public boolean GetProduct(int amaunt) {
		if(amaunt >=Product_Quantity)
			return false;
		Product_Quantity -= amaunt;
		changeStatuse();
		return true;
			
	}


	private void changeStatuse() {
		// TODO Auto-generated method stub
		if(Product_Quantity ==0)
		{
			status = status.unavailable;		}
		else if(Product_Quantity <= ThresholdPoint) {
			status = status.Limit;	
		}
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
	
    public String toString() {
    	return String.format("ImageUrl: %s, Id: %d, Name: %s, Price: %f ",ImageURL,productID,productName,Price);
    }

	/**

	Gets the product Discount Price 
	@return The product Discount Price  
	*/
	public float getDiscount_Price() {
		return Discount_Price;
	}

	/**

	Sets the product Discount Price 
	@return The product Discount Price  
	*/
	public void setDiscount_Price(float discount_Price) {
		Discount_Price = discount_Price;
	}

	/**

	Gets if the product has Active Sale
	@return If the product has Active Sale  
	*/
	public int getHasActiveSale() {
		return hasActiveSale;
	}

	/**

	Sets if the product has Active Sale
	@return If the product has Active Sale  
	*/
	public void setHasActiveSale(int hasActiveSale) {
		this.hasActiveSale = hasActiveSale;
	}

	/**

	Gets the product Threshold Point Counter
	@return The product hreshold Point Counter
	*/
	public int getThresholdPointCounter() {
		return ThresholdPointCounter;
	}

	/**

	Sets the product Threshold Point Counter
	@return The product hreshold Point Counter
	*/
	public void setThresholdPointCounter(int thresholdPointCounter) {
		ThresholdPointCounter = thresholdPointCounter;
	}

	/**

	Gets the Date
	@return The Date
	*/
	public String getDate() {
		return Date;
	}

	/**

	Sets the Date
	@return The Date
	*/
	public void setDate(String date) {
		Date = date;
	}
}
