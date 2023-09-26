package logic;

import java.io.Serializable;

/**
 * Subscriber constructor, creates a new instance of the Subscriber class
 * 
 * @param id the id of the user
 * @param Subscriber_Number the Subscriber Number of the user
 * @param last_name the last name of the user
 * @param Is_First_Purchase is it the Subscriber first purchase
 */
public class Subscriber implements EkrutIF,Serializable{

   private String ID;
   private int Subscriber_Number;
   private int Is_First_Purchase;
	
	
	public Subscriber(String ID, int Subscriber_Number,int Is_First_Purchase) {
		this.ID=ID;
		this.Subscriber_Number=Subscriber_Number;
		this.Is_First_Purchase=Is_First_Purchase;
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

	/**

	Gets the Subscriber Number
	@return The Subscriber Number
	*/
	public int getSubscriber_Number() {
		return Subscriber_Number;
	}

	/**

	Sets the Subscriber Number
	@return The Subscriber Number
	*/
	public void setSubscriber_Number(int subscriber_Number) {
		Subscriber_Number = subscriber_Number;
	}

	/**

	Gets the Is First Purchase
	@return The Is First Purchase
	*/
	public int getIs_First_Purchase() {
		return Is_First_Purchase;
	}

	/**

	Sets the Is First Purchase
	@return The Is First Purchase
	*/
	public void setIs_First_Purchase(int is_First_Purchase) {
		Is_First_Purchase = is_First_Purchase;
	}

	/**

	Gets the ID
	@return The ID
	*/
	public String getID() {
		return ID;
	}

	/**

	Sets the ID
	@return The ID
	*/
	public void setID(String iD) {
		ID = iD;
	}

}
