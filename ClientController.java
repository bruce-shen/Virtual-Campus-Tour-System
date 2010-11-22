/**
 * @(#)ClientController.java

 * @function: A Client Controller class which cooperate with client GUI view and client model(data) 
 *
 * @author: Hao Shen
 * @version 1.00 2010/11/8, Hao Shen
 * @modify 1.01 2010/11/16, Hao Shen
 * @modify 1.02 2010/11/18, Hao Shen, add edit&save buttons, and listeners of them.
 * @modify 1.03 2010/11/19, Hao Shen, add timer to update view every second if possible
 */

 //events hander packages
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener; 
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener; 
import java.awt.Point;

//networking packages
import java.net.InetAddress;

//Time & Timer
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

import java.lang.Math;

public class ClientController {
	
	//private properties
	private ClientModel m_Model;
	private ClientMainFrame m_View;
	private static int usersSize = 0;
	private static double distance_Threshold = 8.0;
	
	//constructor
	public ClientController(ClientModel model, ClientMainFrame view)
	{
		m_Model = model;
		m_View = view;
		
		/*init components of model and view*/
		initModel();
		initView();
		
		
		//... Add listeners to the add message button.
		m_View.addSubmitListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				//only send unempty message
				if(m_View.getMessageInput().length() > 0)
				{
					//generate a message object
					int messageId = m_Model.generateMessageIdFromServer();
					int senderId = m_Model.getCurrentUser().getUserId();
					String senderName = m_Model.getCurrentUser().getUserName();
					String messageContent = m_View.getMessageInput();
					Date dateTime = new Date();
					Message newMessage = new Message(messageId, senderId, senderName, messageContent, dateTime);
					
					//tell model to update
					m_Model.sendMessage(newMessage);
					
					//tell view to udpate
					m_View.udpateMessageTable(m_Model.getLoginDateTime(), m_Model.getAllMessages());
				}
            }
		});
		
		//... Add listeners to the edit button.
		m_View.addEditButtonListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				//enable name editing first
				m_View.enableNameDiting(true);
            }
		});
		
		//... Add listeners to the save button.
		m_View.addSaveButtonListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				//tell model to update username
				m_Model.changeUserName(m_Model.getCurrentUser().getUserId(), m_View.getNewName());
				
				//tell view to update onlineusers list
				//m_View.updateOnlineUsers(m_Model.getOnlineUserNames());
				
				//disable the name editing
				m_View.enableNameDiting(false);
            }
		});
		
		//... Add textField_Message key listener
		m_View.addWindowListener(new WindowAdapter() {
		
			public void windowClosing(WindowEvent e) {
				//tell model to delete the user
				m_Model.removeUser(m_Model.getCurrentUser().getUserId());
				//System.exit(0);
			}
		});
		
		m_View.addTextFieldKeyListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e){
				
				//only send unempty message
				if(m_View.getMessageInput().length() > 0)
				{
					//generate a message object
					int messageId = m_Model.generateMessageIdFromServer();
					int senderId = m_Model.getCurrentUser().getUserId();
					String senderName = m_Model.getCurrentUser().getUserName();
					String messageContent = m_View.getMessageInput();
					Date dateTime = new Date();
					Message newMessage = new Message(messageId, senderId, senderName, messageContent, dateTime);
					
					//tell model to update
					m_Model.sendMessage(newMessage);
					
					//tell view to udpate
					m_View.udpateMessageTable(m_Model.getLoginDateTime(), m_Model.getAllMessages());
				}
			}
		});
		
		m_View.addWindowMouseListener(new MouseListener() {
		
			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
			   
			}

			public void mouseEntered(MouseEvent e) {
			   
			}

			public void mouseExited(MouseEvent e) {	
			  
			}

			public void mouseClicked(MouseEvent e) {
				Point currentPoint = e.getPoint();
				
				for(int i = 0; i < m_Model.getLocationPoints().size(); i++)
				{
					if(getDistance(currentPoint, m_Model.getLocationPoints().get(i)) <= distance_Threshold)
					{
						m_Model.setLocation(i + 1);
						m_View.cleanScene();
						m_View.update3DView(m_Model.getCurrentLocation());
						System.out.println("Location " + i + " clicked.");
					}				
				}
			}
		});
		
		
		Timer timer = new Timer();
		timer.schedule(new RemindTask(m_View, m_Model), 0, 1 * 1000); //subsequent rate
	}
	
	
	private double getDistance(Point p1, Point p2)
	{
		double distance = 0;
		
		double x1 = p1.getX();
		double y1 = p1.getY();
		double x2 = p2.getX();
		double y2 = p2.getY();
		
		distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		
		return distance;
	}
	
	private void initModel()
	{
		//init name with IP
		try 
		{
			m_Model.initUser(InetAddress.getLocalHost().toString());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//init location with id=1
		m_Model.setLocation(1);
	}
	
	private void initView()
	{
		m_View.updateNameTextField(m_Model.getCurrentUser().getUserName());
		m_View.updateOnlineUsers(m_Model.getOnlineUserNames());
		m_View.update3DView(m_Model.getCurrentLocation());
	}
	
}

class RemindTask extends TimerTask {
	
	private ClientMainFrame view;
	private ClientModel model;
	
	public RemindTask(ClientMainFrame v, ClientModel m)
	{
		view = v;
		model = m;
	}
	
    public void run()
	{
		//check if need to update online users list
		if(model.getOnlineUsersListStatus().equals("1"))
		{
			view.updateOnlineUsers(model.getOnlineUserNames());
			model.setOnlineUsersListStatus("0");
		}

		//check if need to update message list
		if(model.getMessageStatus().equals("1"))
		{
			view.udpateMessageTable(model.getLoginDateTime(), model.getAllMessages());
			model.setMessageStatus("0");
		}
    }}
