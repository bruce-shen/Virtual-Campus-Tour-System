/**
 * @(#)Message.java

 * @function: The enter to the VCTS
 *
 * @author: Hao Shen
 * @version 1.03 2010/11/19, Hao Shen. Add this class for management of messages
 */

import java.util.*;
import java.io.Serializable;

public class Message implements Serializable {
	private int messageId;
	private int messageSenderId;
	private String messageSenderName;
	private String messageContent;
	private Date dateTime;
	
	public Message(int mId, int mSenderId, String mSenderName, String mContent, Date mDateTime)
	{
		messageId = mId;
		messageSenderId = mSenderId;
		messageSenderName = mSenderName;
		messageContent = mContent;
		dateTime = mDateTime;
	}
	
	public int getMessageId()
	{
		return messageId;
	}
	
	public String getMessageContent()
	{
		return messageContent;
	}
	
	public int getMessageSenderId()
	{
		return messageSenderId;
	}
	
	public String getMessageSenderName()
	{
		return messageSenderName;
	}
	
	public Date getDateTime()
	{
		return dateTime;
	}
}