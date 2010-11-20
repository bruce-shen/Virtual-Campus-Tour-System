/**
 * @(#)Server.java
 *
 * @function: an interface for Server end;
 * @author: Hao Shen
 * @version 1.00 2010/11/8
 * @modify 1.02 2010/11/18, add getLocationImageById() function * @modify 1.03 2010/11/19, Hao Shen, add onlineusers&message info functions
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.imageio.ImageIO;  
import java.io.File;  
import java.awt.*;  
import java.awt.image.BufferedImage;  
import java.io.*;
import java.util.ArrayList;

public interface Server extends Remote {

	public int generateUserId() throws RemoteException;
	
	public int generateMessageId() throws RemoteException;
	
	public void addUser(User aUser) throws RemoteException;
	
    public void removeUser(int userId) throws RemoteException;
    
    public void changeUserName(int id, String newName) throws RemoteException;
    
    public ArrayList<User> getAllUsers() throws RemoteException;  
    
    public void addNewMessage(int userId, Message newMessage) throws RemoteException;
    
    public ArrayList<Message> getAllMessage() throws RemoteException;
    
    public ArrayList<byte[]> getLocationImagesById(int id) throws RemoteException;
	
	public String welcomeInfo() throws RemoteException;
	
	public void setOnlineUsersListStatus(String userId, String status) throws RemoteException;
	
	public String getOnlineUsersListStatus(String userId) throws RemoteException;

	public void setMessageStatus(String userId, String status) throws RemoteException;
	
	public String getMessageStatus(String userId) throws RemoteException;
	
    
}