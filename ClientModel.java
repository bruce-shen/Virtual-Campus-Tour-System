/**
 * @(#)ClientModel.java

 * @function: A Client Model class which manipulates client model(data) 
 *
 * @author: Hao Shen
 * @version 1.00 2010/11/8
 * @modify 1.01 2010/11/16 * @modify 1.03 2010/11/19, Hao Shen, add onlineusers&message parameters to check the view needs to update or not
 */
import java.lang.String;
import java.util.ArrayList;

//rmi package
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.io.Serializable;

import java.util.*;

public class ClientModel{
	
	private User currentUser;
	
	@SuppressWarnings("unused")
	private Location currentLocation;
	private Server remoteObj;
	
	private Date loginDateTime;
	
	public ClientModel()
	{
		initRMI();
		
		loginDateTime = new Date();
	}
	
	private void initRMI()
	{
		 //load security manager
		 System.setSecurityManager(new RMISecurityManager());
		 
		 //get the remote object from the registry
	     try
	     {
	         String url = "rmi://localhost/SERVER";
	         remoteObj = (Server)Naming.lookup(url);
			 System.out.println("Got remote object");
			 System.out.println(remoteObj.welcomeInfo());
	     }
	     catch (RemoteException exc)
	     {
	         System.out.println("Error in lookup: " + exc.toString());
	     }
	     catch (java.net.MalformedURLException exc)
	     {
	         System.out.println("Malformed URL: " + exc.toString());
	     }
	     catch (java.rmi.NotBoundException exc)
	     {
	         System.out.println("NotBound: " + exc.toString());
	     }
	}
	
	public Date getLoginDateTime()
	{
		return loginDateTime;
	}
	
	public void initUser(String name)
	{
		try
		{
			currentUser = new User(remoteObj.generateUserId(), name);
			remoteObj.addUser(currentUser);
		}
		catch (Exception e)
	    {
	        e.printStackTrace();  
	    }

	}
	
	public User getCurrentUser()
	{
		return currentUser;
	}
	
	public void removeUser(int id)
	{
		try
		{
			remoteObj.removeUser(id);
		}
		catch (Exception e)
	    {
	        e.printStackTrace();  
	    }
	}
	
	public void changeUserName(int id, String name)
	{
		try
		{
			currentUser.setUserName(name);
			remoteObj.changeUserName(id, name);
		}
		catch (Exception e)
	    {
			e.printStackTrace();  
	    }
	}
	
	public void initLocation(int id)
	{
		try
		{
			currentLocation= new Location(id, remoteObj.getLocationImagesById(id));
			System.out.println("location initialized with " + "id = " +currentLocation.getLocationId());
		}
		catch (Exception e)
	    {
	        e.printStackTrace();  
	    }
	}
	
	public void updateLocation(int id)
	{
		try
		{
			currentLocation= new Location(id, remoteObj.getLocationImagesById(id));
		}
		catch (Exception e)
	    {
	        e.printStackTrace();  
	    }
	}
	
	public Location getCurrentLocation()
	{
		return currentLocation;
	}
	
	public ArrayList<Message> getAllMessages()
	{
		ArrayList<Message> tmpMessage = new ArrayList<Message>();
		try
		{
			tmpMessage = remoteObj.getAllMessage();
		}
		catch (Exception e)
	    {
			e.printStackTrace();  
	    }
		finally
		{
			return tmpMessage;
		}
	}
	
	public void sendMessage(Message ms)
	{
		try
		{
			remoteObj.addNewMessage(currentUser.getUserId(), ms);
		}
		catch (Exception e)
	    {
	        e.printStackTrace();  
	    }
	}
	
	public int generateMessageIdFromServer()
	{
		int messageId = 0;
		try
		{
			messageId = remoteObj.generateMessageId();
		}
		catch (Exception e)
	    {
	        e.printStackTrace();  
	    }
		finally
		{
			return messageId;
		}
	}
	
	public void setOnlineUsersListStatus(String status)
	{
		try
		{
			remoteObj.setOnlineUsersListStatus(Integer.toString(currentUser.getUserId()), status);
		}
		catch (Exception e)
	    {
	        e.printStackTrace();  
	    }
		
	}
	
	public String getOnlineUsersListStatus()
	{
		String status = "";
		try
		{
			status = remoteObj.getOnlineUsersListStatus(Integer.toString(currentUser.getUserId()));
		}
		catch (Exception e)
	    {
	        e.printStackTrace();  
	    }
		finally
		{
			return status;
		}
	}
	
	public void setMessageStatus(String status)
	{
		try
		{
			remoteObj.setMessageStatus(Integer.toString(currentUser.getUserId()), status);
		}
		catch (Exception e)
	    {
	        e.printStackTrace();  
	    }
		
	}
	
	public String getMessageStatus()
	{
		String status = "";
		try
		{
			status = remoteObj.getMessageStatus(Integer.toString(currentUser.getUserId()));
		}
		catch (Exception e)
	    {
	        e.printStackTrace();  
	    }
		finally
		{
			return status;
		}
	}
	

	public ArrayList<User> getOnlineUserNames()
	{
		ArrayList<User> tmpUsers = new ArrayList<User>();
		try
		{
			tmpUsers = remoteObj.getAllUsers();
		}
		catch (Exception e)
	    {
	        System.out.println("In getOnlineUserNames()");
			e.printStackTrace();  
	    }
		finally
		{
			return tmpUsers;
		}
	}
	
	public Server getRemoteObj()
	{
		return remoteObj;
	}
}
