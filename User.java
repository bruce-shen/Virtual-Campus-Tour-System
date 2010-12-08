/**
 * @(#)User.java
 *
 * @function: The User class for clients
 * @author: Hao Shen 
 * @version 1.00 2010/11/8
 * @version 1.03 2010/11/19
 * @modify 2.0  2010/12/08, Hao Shen, final version for final presentation
 */
import java.io.Serializable;

public class User implements Serializable{
	private int userId;
	private String userName;
	private int currentLocationId;
	
	public User(){}
	
    public User(int id, String aName) {
    	userId = id;
    	userName = aName;
    	currentLocationId = 1;
    }
    
    public User(int id, String aName, int locationId) {
    	userId = id;
    	userName = aName;
    	currentLocationId = locationId;
    }
    
    /*gets functions*/
    public int getUserId()
    {
    	return userId;
    }
    
    public String getUserName()
    {
    	return userName;
    }
    
    public int getCurrentLocationId()
    {
    	return currentLocationId;
    }
    
    public void setCurrentLocationId(int id)
    {
    	currentLocationId = id;
    }
    
    /*sets functiosn*/
    public void setUserId(int id)
    {
    	userId = id;
    }
    
    public void setUserName(String name)
    {
    	userName = name;
    }
}