/**
 * @(#)ServerImpl.java

 *
 * @function: the implement of Server interface
 * @author: Hao Shen
 * @version 1.00 2010/11/8 * @modify 1.02 2010/11/18, implement getLocationImageById() function * @modify 1.03 2010/11/19, Hao Shen, implementation of added functions
 */

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;  
import java.io.File;  
import java.awt.*;  
import java.awt.image.BufferedImage;  
import java.io.*;



@SuppressWarnings("serial")
public class ServerImpl extends UnicastRemoteObject implements Server{
	
	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<Message> messages = new ArrayList<Message>();
	
	private static int userIdFlag = 0;
	private static int messageIdFlag = 0;
	
	//management online users' info(status & userId)
	private ArrayList<String> onlineUsersListStatuses = new ArrayList<String>();
	private ArrayList<String> onlineUsersIds = new ArrayList<String>();
	
	//management message status for each user
	private ArrayList<String> messageStatuses = new ArrayList<String>();
	private ArrayList<String> messageUserIds = new ArrayList<String>();
	
    public ServerImpl() throws RemoteException 
	{
		super();	
    }
    
    public int generateUserId()
    {
    	System.out.println("userId Flag: " + userIdFlag);
		return userIdFlag++;
    }
	
	public int generateMessageId()
	{
		return messageIdFlag++;
	}
	
	public void setOnlineUsersListStatus(String userId, String status)
	{
		for(int i = 0; i < onlineUsersIds.size(); i++)
		{
			if(userId.equals(onlineUsersIds.get(i)))
			{
				onlineUsersListStatuses.set(i, status);
			}
		}
	}
	
	public String getOnlineUsersListStatus(String userId)
	{
		String status = "";
		for(int i = 0; i < onlineUsersIds.size(); i++)
		{
			if(userId.equals(onlineUsersIds.get(i)))
			{
				status = onlineUsersListStatuses.get(i);
				break;
			}
		}
		
		return status;
	}
	
	public void setMessageStatus(String userId, String status)
	{
		for(int i = 0; i < messageUserIds.size(); i++)
		{
			if(userId.equals(messageUserIds.get(i)))
			{
				messageStatuses.set(i, status);
			}
		}
	}
	
	public String getMessageStatus(String userId)
	{
		String status = "";
		
		for(int i = 0; i < messageUserIds.size(); i++)
		{
			if(userId.equals(messageUserIds.get(i)))
			{
				status = messageStatuses.get(i);
				break;
			}
		}
		
		return status;
	}
	
    
    //init a User with a given name;
    public void addUser(User aUser)
    {
		//add user
    	users.add(aUser);
		
		//add userslist info
		for(int i = 0; i < onlineUsersListStatuses.size(); i++)
		{
			onlineUsersListStatuses.set(i, "1");
		}
		onlineUsersListStatuses.add("1");
		onlineUsersIds.add(Integer.toString(aUser.getUserId()));
		
		//add messagelist info
		for(int i = 0; i < messageStatuses.size(); i++)
		{
			messageStatuses.set(i, "1");
		}
		messageStatuses.add("1");
		messageUserIds.add(Integer.toString(aUser.getUserId()));
    }
	
	public void removeOnlineUsersListInfo(String userId)
	{
		//tell clients to update again
		for(int i = 0; i < onlineUsersListStatuses.size(); i++)
		{
			onlineUsersListStatuses.set(i, "1");
		}
		
		for(int i = 0; i < onlineUsersIds.size(); i++)
		{
			if(userId.equals(onlineUsersIds.get(i)))
			{
				onlineUsersListStatuses.remove(i);
				onlineUsersIds.remove(i);
				return;
			}
		}		
	}
	
	public void removeMessageListInfo(String userId)
	{
		for(int i = 0; i < messageUserIds.size(); i++)
		{
			if(userId.equals(messageUserIds.get(i)))
			{
				messageStatuses.remove(i);
				messageUserIds.remove(i);
				return;
			}
		}	
		
	}
    
    //remove the user with the given userId
    public void removeUser(int userId)
    {
		//remove userslist info
		removeOnlineUsersListInfo(Integer.toString(userId));
	
		//remove message status info
		removeMessageListInfo(Integer.toString(userId));
		
		//remove user
    	for(int i = 0; i < users.size(); i++)
    	{
    		if(users.get(i).getUserId() == userId)
    		{
    			users.remove(i);
    			return;
    		}
    	}
    }
    
    //change the username
    public void changeUserName(int id, String newName)
    {
    	for(int i = 0; i < users.size(); i++)
    	{
    		if(users.get(i).getUserId() == id)
    		{
    			users.get(i).setUserName(newName);
    			break;
    		}
    	}
		
		//once changed name, call all users to update online users list
		for(int i = 0; i < onlineUsersListStatuses.size(); i++)
		{
			onlineUsersListStatuses.set(i, "1");
		}
    }
    
    //get all users's info
    public ArrayList<User> getAllUsers()
    {
    	return users;
    }
    
    //add new message to public chatting
    public void addNewMessage(int userId, Message newMessage)
    {
    	messages.add(newMessage);
		
		//add message list info
		for(int i = 0; i < messageStatuses.size(); i++)
		{
			messageStatuses.set(i, "1");
		}
		messageStatuses.add("1");
		messageUserIds.add(Integer.toString(userId));
    }
    
    
    //get all message
    public ArrayList<Message> getAllMessage()
    {
    	return messages;
    }
    
	public ArrayList<byte[]> getLocationImagesById(int id)
	{
		ArrayList<byte[]> imgs = new ArrayList<byte[]>();
		for(int i = 0; i < 6; i++)
		{
			try
			{
				BufferedImage originalImage = ImageIO.read(new File("resources/3d_imgs/" + id + "/" + i + ".jpg"));	
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write( originalImage, "jpg", baos );
				baos.flush();
				byte[] imageInByte = baos.toByteArray();
				baos.close();
				
				imgs.add(imageInByte);
			}
			catch(Exception e)
			{
				System.out.println("in initMages() in Location.java");
				e.printStackTrace();
			}
		}
		
		return imgs;
	}

	public String welcomeInfo()
	{
		return "Welcome to VCTS!";
	}
    
    public static void main(String args[])
    {
    	//set the security manager
    	try
      	{
        	System.setSecurityManager(new RMISecurityManager());

			//create a local instance of the object
			ServerImpl Server = new ServerImpl();

			//put the local instance in the registry
			Naming.rebind("//localhost/SERVER" , Server);

			System.out.println("Server waiting.....");
      	}
    	catch (java.net.MalformedURLException me)
      	{
         	System.out.println("Malformed URL: " + me.toString());
      	}
    	catch (RemoteException re)
      	{
         	System.out.println("Remote exception: " + re.toString());
      	}

  	}
}