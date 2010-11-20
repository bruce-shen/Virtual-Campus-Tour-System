/**
 * @(#)User.java
 *
 * @function: The User class for clients
 * @author: Hao Shen 
 * @version 1.00 2010/11/8
 * @version 1.03 2010/11/19
 */
import java.io.Serializable;

public class User implements Serializable{
	private int userId;
	private String userName;
	
	public User(){}
	
    public User(int id, String aName) {
    	userId = id;
    	userName = aName;
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