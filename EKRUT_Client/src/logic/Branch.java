package logic;

import java.io.Serializable;

/**

The Branch class represents a branch in the system.

It implements the EkrutIF interface, allowing it to be inserted, removed, and updated in the database.

@author (Shahar)

@version (4.0)
*/
public class Branch implements EkrutIF, Serializable{

	private String Branch_Name;
	private String Region_Name;
	
	public Branch(String Branch_Name, String Region_Name) {
		this.Branch_Name = Branch_Name;
		this.Region_Name = Region_Name;
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

	Gets the Branch name 
	@return The Branch name 
	*/
	public String getBranch_Name() {
		return Branch_Name;
	}

	/**

	Sets the Branch name 
	@return The Branch name 
	*/
	public void setBranch_Name(String branch_Name) {
		Branch_Name = branch_Name;
	}

	/**

	Gets the Region name 
	@return The Region name 
	*/
	public String getRegion_Name() {
		return Region_Name;
	}

	/**

	Sets the Region name 
	@return The Region name 
	*/
	public void setRegion_Name(String region_Name) {
		Region_Name = region_Name;
	}
}