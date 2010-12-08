/**
 * @(#)Server.java
 *
 * @function: an interface for Server end;
 * @author: Hao Shen
 * @version 1.00 2010/11/8
 * @modify 1.02 2010/11/18, add getLocationImageById() function * @modify 1.03 2010/11/19, Hao Shen, add onlineusers&message info functions
 * @modify 2.0  2010/12/08, Hao Shen, final version for final presentation
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

	// Generate a new user ID
	public int generateUserId() throws RemoteException;
	
	// Generate a new message ID
	public int generateMessageId() throws RemoteException;
	
	// add a new user
	public void addUser(User aUser) throws RemoteException;
	
	// remove a user
    public void removeUser(int userId) throws RemoteException;
    
    // Change user name
    public void changeUserName(int id, String newName) throws RemoteException;
    
    // Get all users info
    public ArrayList<User> getAllUsers() throws RemoteException;  
    
    // add a new Message
    public void addNewMessage(int userId, Message newMessage) throws RemoteException;
    
    // Get all message
    public ArrayList<Message> getAllMessage() throws RemoteException;
    
    // Get location images with location ID
    public ArrayList<byte[]> getLocationImagesById(int id) throws RemoteException;
	
    // A welcome info
	public String welcomeInfo() throws RemoteException;
	
	// Set a user's status(latest, old)
	public void setOnlineUsersListStatus(String userId, String status) throws RemoteException;
	
	// Get a users's status(latest, old)
	public String getOnlineUsersListStatus(String userId) throws RemoteException;

	// Set message status for a user
	public void setMessageStatus(String userId, String status) throws RemoteException;
	
	// Get message status of a user
	public String getMessageStatus(String userId) throws RemoteException;
	
	// Change user's locatioin
	public void changeUserLocation(int userId, int locationid) throws RemoteException;
	
    
}