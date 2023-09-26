package logic;

import java.io.Serializable;

/**

The Command class represents a command that will be executed by the server.
It contains an id that identifies the command and an Object data that holds
any additional information needed for the command to be executed.
@author (Shahar)

@version (4.0)
*/
@SuppressWarnings("serial")
public class Command implements Serializable {
	private String id; // Command to execute
	private Object data; // Object type of ArrayList<ArrayList<String>>

	/**
	 * Constructor for the Command class
	 * 
	 * @param id - the identifier of the command
	 * @param data - additional data needed for the command to be executed 
	 */
	public Command(String id, Object data) {
		this.id = id;
		this.data = data;
	}

	/**
	 * Returns the id of the command
	 * 
	 * @return id of the command
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of the command
	 * 
	 * @param id - the new id for the command
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the additional data of the command
	 * 
	 * @return additional data of the command
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Sets the additional data of the command
	 * 
	 * @param data - the new additional data for the command
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
