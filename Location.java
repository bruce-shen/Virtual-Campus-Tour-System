/**
 * @(#)Location.java

 * @function: A Location class which is for 3D view info
 *
 * @author: Hao Shen
 * @version 1.00 2010/11/8
 * @modify 1.01 2010/11/16 * @modify 1.02 2010/11/18, change image format to byte[] which can be transfered through RMI
 * @version 1.03 2010/11/19
 * @modify 2.0  2010/12/08, Hao Shen, final version for final presentation
 */import javax.imageio.ImageIO;  
import java.io.File;  
import java.awt.*;  
import java.awt.image.BufferedImage;  
import java.io.*;
import java.util.ArrayList;
import java.io.Serializable;
 
public class Location implements Serializable{

	private int locationId;
	private ArrayList<byte[]> images;
	
    public Location(int id, ArrayList<byte[]> imgs) {
		locationId = id;
		images = new ArrayList<byte[]>();
		images = imgs;
    }
	
    
    /*gets functions*/
    public int getLocationId()
    {
    	return locationId;
    }
   

	public ArrayList<byte[]> getLocationImages()
	{
		return  images;
	}   		 
		
	/*sets functions*/
	public void setLocationId(int id)
	{
		locationId = id;
	}
	
}