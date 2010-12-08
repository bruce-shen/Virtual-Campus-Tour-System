/**
 * @(#)ClientMainFrame.java

 * @function: A Client View class for displaying GUI information and interaction
 *
 * @author: Hao Shen
 * @version 1.00 2010/11/8, Hao Shen
 * @modify 1.01 2010/11/16, Hao Shen
 * @modify 1.02 2010/11/18, Hao Shen, add edit&save buttons, add vertical scroll pane
 * @modify 1.03 2010/11/19, Hao Shen, change onlineusers from JList to JTextArea type, the eneditable for Jlists
 * @modify 2.0  2010/12/08, Hao Shen, final version for final presentation
 */
 
 /* Java UI packages & Events*/
import java.awt.EventQueue;
import java.awt.Frame;
import javax.swing.JFrame;
import java.awt.Canvas;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTable;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.applet.Applet;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.Robot;
import javax.swing.BorderFactory;

/* Java3d packages */
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom; 
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*; 

//others
import java.lang.Math;
import java.util.*;
import javax.imageio.ImageIO;  
import java.io.File;  
import java.awt.*;  
import java.awt.image.BufferedImage;  
import java.io.*;

public class ClientMainFrame extends JFrame{

	/* 2D components */
	private ClientModel clientModel;
	private JTextField textField_Message;
	private JButton btnSubmit;
	private DefaultTableModel  onlineUsersTableModel;
	private JTextArea textArea;
	private JPanel panel_3DView;
	private JTextField textField_Name;
	private JButton btnEditName;
	private JButton btnSave;
	private JTextArea textArea_OnlineUses;
	private JLabel label_2DCampus;
	private JScrollPane locationInfoScrollPane;
	private JTextArea locationNamesTextArea;
	
	/*3D things here*/
	private VirtualUniverse universe;			// Virtual Universe object.
	private javax.media.j3d.Locale locale;		// Locale of the scene graph.
	private BranchGroup contentBranch;			// BranchGroup for the Content Branch of the scene
	private TransformGroup contentsTransGr;		// TransformGroup  node of the scene contents
	private BranchGroup viewBranch;				// BranchGroup for the View Branch of the scene
	private ViewPlatform viewPlatform;			// ViewPlatform node, defines from where the scene is viewed.
	private TransformGroup vpTransGr;			// Transform group for the ViewPlatform node
	private View view;							// View node, defines the View parameters.
	
	PhysicalBody body;  						// A PhysicalBody object can specify the user's head

	// A PhysicalEnvironment object can specify the physical
	// environment in which the view will be generated
	PhysicalEnvironment environment;

	private Canvas3D canvas; 					// Drawing canvas for 3D rendering
	private Bounds bounds;  					//bounding
	
	/* Static variables*/
	private static double perspectiveViewAngle = 85;
	private static final double widthHeightRatio = 1.487;
	
	//constructor with a given data model
	public ClientMainFrame(ClientModel model) {
		clientModel = model;
		onlineUsersTableModel = new DefaultTableModel();
		
		// initialize 2D views
		initialize2D();
		
		// initialize 3D view
		initialize3D();
	}

	/***************************************
	 ************* 2D View ***************** 
	***************************************/
	private void initialize2D() {
		
		
		//init the main frame
		setResizable(false);
		setTitle("Virtual Campus Tour System");
		setBounds(100, 100, 895, 695);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
			
		
		//add the user info panel
		JPanel panel_Info = new JPanel();
		panel_Info.setBounds(621, 0, 253, 105);
		getContentPane().add(panel_Info);
		panel_Info.setLayout(null);	
			
			
		//add a vatar 
		JLabel label_Avatar = new JLabel("");
		label_Avatar.setIcon(new ImageIcon("resources/icons/msn.png"));
		label_Avatar.setBounds(0, 20, 70, 70);
		label_Avatar.setBorder(BorderFactory.createLineBorder(Color.gray));
		panel_Info.add(label_Avatar);
		
		//add name textfield		
		textField_Name = new JTextField();
		textField_Name.setEditable(false);
		textField_Name.setBounds(79, 25, 160, 27);
		panel_Info.add(textField_Name);
		textField_Name.setColumns(10);
			
		//add edit name button
		btnEditName = new JButton("Edit Name");
		btnEditName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEditName.setBounds(79, 63, 82, 27);
		panel_Info.add(btnEditName);
			
		//add save button
		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setBounds(178, 63, 61, 27);
		panel_Info.add(btnSave);
			
		//add the 3D view panel
		panel_3DView = new JPanel();
		panel_3DView.setToolTipText("");
		panel_3DView.setBackground(SystemColor.activeCaption);
		panel_3DView.setBounds(0, 0, 611, 410);
		getContentPane().add(panel_3DView);
			
		//add the online users panel
		JPanel panel_Onlineusers = new JPanel();
		panel_Onlineusers.setBounds(625, 105, 253, 250);
		getContentPane().add(panel_Onlineusers);
		panel_Onlineusers.setLayout(null);
			
		//add online usrs label
		JLabel label_Onlineusers = new JLabel("Online users:");
		label_Onlineusers.setBounds(0, 0, 175, 18);
		panel_Onlineusers.add(label_Onlineusers);
			
		JScrollPane onlineUsersScrollPane = new JScrollPane((Component) null);
		onlineUsersScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		onlineUsersScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		onlineUsersScrollPane.setBounds(0, 25, 253, 226);
		panel_Onlineusers.add(onlineUsersScrollPane);
			
		textArea_OnlineUses = new JTextArea();
		textArea_OnlineUses.setWrapStyleWord(true);
		textArea_OnlineUses.setLineWrap(true);
		textArea_OnlineUses.setEditable(false);
		onlineUsersScrollPane.setViewportView(textArea_OnlineUses);
			
		//add 2D campus label and let it fixs the label size
		label_2DCampus = new JLabel("");
		label_2DCampus.setBounds(0, 407, 611, 255);
		ImageIcon campus = new ImageIcon("resources/imgs/0.jpg");
		campus.setImage(campus.getImage().getScaledInstance(label_2DCampus.getWidth(), label_2DCampus.getHeight(), Image.SCALE_DEFAULT));
		label_2DCampus.setIcon(campus);
		getContentPane().add(label_2DCampus);
			
		//add message panel
		JPanel panel_Message = new JPanel();
		panel_Message.setBounds(620, 394, 264, 272);
		getContentPane().add(panel_Message);
		panel_Message.setLayout(null);
			
		//add the message label
		JLabel lblAllMessage = new JLabel("All messages:");
		lblAllMessage.setBounds(3, 0, 103, 14);
		panel_Message.add(lblAllMessage);
			
		//add a text field for typing message
		textField_Message = new JTextField();
		textField_Message.setBounds(5, 245, 176, 25);
		panel_Message.add(textField_Message);
		textField_Message.setColumns(10);
			
		//add a submit button
		btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSubmit.setBounds(179, 245, 79, 25);
		panel_Message.add(btnSubmit);
			
		//add message area
		textArea = new JTextArea();
		textArea.setBounds(1, 1, 257, 224);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		panel_Message.add(textArea);
			
		//add scroll bar to the text area
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(5, 20, 253, 226);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_Message.add(scrollPane);
			
	}
	
	/***************************************
	 ************* 3D View ***************** 
	 ***************************************/
	private void initialize3D()
	{
		//3D View 
		panel_3DView.setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		canvas = new Canvas3D(config);
		panel_3DView.add("Center", canvas);
		
		// Setting the VirtualUniverse and the Locale nodes
	    setUniverse();

	    // Setting the view branch
	    setViewing();

	    //set the bounding
	    bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE);
	}
	
	// Init a virtual universe
	private void setUniverse()
	{
		// Creating the VirtualUniverse and the Locale nodes
	    universe = new VirtualUniverse();
	    locale = new javax.media.j3d.Locale(universe);
	}
	  
	
	// Set 3D scene's content
	private void setContent(Location currentLocation)
	{
		//content branch
		contentBranch = new BranchGroup();
	    contentBranch.setCapability( BranchGroup.ALLOW_DETACH );
		
	    ArrayList<byte[]> currentImages = new ArrayList<byte[]>();
		currentImages = currentLocation.getLocationImages();  
		  
		contentsTransGr = new TransformGroup();
	    contentsTransGr.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);


	    // Create a transformation node for cube transformation
	    contentsTransGr = new TransformGroup();
	    contentsTransGr.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    contentsTransGr.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
	    /*create 6 surfaces of this cube*/
		//!!!!!!!!Set the back surface of each face as the front surface!!!!!!!
		PolygonAttributes polyAppear = new PolygonAttributes();
	 	polyAppear.setCullFace(PolygonAttributes.CULL_NONE);
	 			
	 	// Define 6 Appearance objects
		Appearance[] apperarance = new Appearance[6];
	 	for(int i = 0; i < 6; i++)
	 	{ 
	 		try
			{
	 			// Get one texture image
				InputStream in = new ByteArrayInputStream(currentImages.get(i));
				
				// Setup a texture loader
				TextureLoader myloader = new TextureLoader(ImageIO.read(in), panel_3DView);
				
				// Create texture
				Texture2D mytext=(Texture2D) myloader.getTexture();
				mytext.setBoundaryModeS(Texture.CLAMP_TO_EDGE);
				mytext.setBoundaryModeT(Texture.CLAMP_TO_EDGE);
					
				// Bound the texture to the appearance
				apperarance[i] = new Appearance();
					
				// Make the inside surface visible
				apperarance[i].setPolygonAttributes(polyAppear);
				apperarance[i].setTexture(mytext);
					
				TextureAttributes myTexAtt = new TextureAttributes();
				myTexAtt.setTextureMode(TextureAttributes.MODULATE);
				apperarance[i].setTextureAttributes(myTexAtt);
					
				Material mat = new Material();
				apperarance[i].setMaterial(mat);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	 	}
	 		
			
		// Create a cube with texture on each surface inside the cube
		Box box = new Box(1.0f, 1.0f, 1.0f, Box.GENERATE_TEXTURE_COORDS, new Appearance()) ;
			
		/* Apply appearance to each surface */
		box.getShape(Box.BACK).setAppearance(apperarance[0]);
		box.getShape(Box.FRONT).setAppearance(apperarance[1]);
		box.getShape(Box.TOP).setAppearance(apperarance[2]);
		box.getShape(Box.BOTTOM).setAppearance(apperarance[3]);
		box.getShape(Box.LEFT).setAppearance(apperarance[4]);
		box.getShape(Box.RIGHT).setAppearance(apperarance[5]);
			
		// Add trans node to the sence root node
		contentBranch.addChild(contentsTransGr);
			
		// Add the cube to trans	
		contentsTransGr.addChild(box);
			
	   	// Compiling the branch graph before making it live
	    contentBranch.compile();

	    // Adding a branch graph into a locale makes its nodes live (drawable)
	    locale.addBranchGraph(contentBranch);
	 
	}
	
	// Set 3D's viewing
	private void setViewing()
	{
		// Creating the viewing branch
		viewBranch = new BranchGroup();

	    // Setting the viewPlatform
		viewPlatform = new ViewPlatform();
		viewPlatform.setActivationRadius(Float.MAX_VALUE);
	    viewPlatform.setBounds(bounds);

	    Transform3D t = new Transform3D();
	    t.set(new Vector3f(0.0f, 0.0f, 0.0f));
	    vpTransGr = new TransformGroup(t);

	    // Node capabilities control (granding permission) read and write access
	    //  after a node is live or compiled
	    //  The number of capabilities small to allow more optimizations during compilation
	    vpTransGr.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	    vpTransGr.setCapability( TransformGroup.ALLOW_TRANSFORM_READ);

	    vpTransGr.addChild(viewPlatform);
	    viewBranch.addChild(vpTransGr);

	    // Setting the view
	    view = new View();
	    view.setCompatibilityModeEnable(true);
	    view.setProjectionPolicy(View.PERSPECTIVE_PROJECTION );
	    Transform3D tt = new Transform3D();
	    tt.perspective(Math.PI * perspectiveViewAngle / 180, widthHeightRatio, 0.1f, 2.0f);
	    view.setLeftProjection(tt);
	    view.addCanvas3D(canvas);

	    body = new PhysicalBody();
	    view.setPhysicalBody(body);
	    environment = new PhysicalEnvironment();
	    view.setPhysicalEnvironment(environment);

	    view.attachViewPlatform(viewPlatform);
	    view.setWindowResizePolicy(View.PHYSICAL_WORLD);
	    
	    locale.addBranchGraph(viewBranch);

	} 
	
	
	// Change the camera's perspective view angle with mouse wheel event
	public void changePerspectiveViewAngle(int increaseView)
	{
		Transform3D tmp = new Transform3D();
		perspectiveViewAngle += 5 * increaseView;
		if(perspectiveViewAngle >= 120)
		{
			perspectiveViewAngle = 120;
		}
		
		if(perspectiveViewAngle <= 50)
		{
			perspectiveViewAngle = 50;
		}

		tmp.perspective(Math.PI * perspectiveViewAngle / 180, widthHeightRatio, 0.1f, 2.0f);
	    view.setLeftProjection(tmp);
	}
	
	// Rotate the cube with a given angle
	public void rotate(double angle)
	{
		Transform3D tmp = new Transform3D();	
		tmp.rotY(-1 * angle * Math.PI / 900);
		contentsTransGr.setTransform(tmp);
	}
	
	// Change the camera's lookAt view angle
	public void changeCameraLookAtAngle(double angle)
	{
		Transform3D newLookAt = new Transform3D();
		newLookAt.lookAt(new Point3d(0,0,0), new Point3d(0, angle / 50, -10), new Vector3d(0, 1, 0));
	    view.setVpcToEc(newLookAt);
	}
	
	// Enalbe or disable the editing of the name textfield
	public void enableNameDiting(boolean status)
	{
		textField_Name.setEditable(status);
	}
	
	// Get the name from the name text field
	public String getNewName()
	{
		return textField_Name.getText();
	}
	
	// Add listener  to the submit Button
	public void addSubmitListener(ActionListener cal) {
        btnSubmit.addActionListener(cal);
    }
	
	// Add listner to the editButton
	public void addEditButtonListener(ActionListener cal) {
        btnEditName.addActionListener(cal);
    }
	
	// Add listner to the SavaButton
	public void addSaveButtonListener(ActionListener cal) {
        btnSave.addActionListener(cal);
    }
	
	// Add window closing listener
	public void addWindowClosingListener(WindowAdapter cal)
	{
		this.addWindowListener(cal);
	}
	
	// Add textFiled Listener
	public void addTextFieldKeyListener(ActionListener cal)
	{
		textField_Message.addActionListener(cal);
	}
	
	// Add mouse listener to the 2D campus map
	public void addMapMouseListener(MouseListener cal)
	{
		label_2DCampus.addMouseListener(cal);
	}
	
	// Add mouse motion listener to the 2D campus map
	public void addMapMouseMotionListener(MouseMotionListener cal)
	{
		label_2DCampus.addMouseMotionListener(cal);
	}
	
	// Add mouse motion listener to the 3D canvas
	public void add3DMouseMotionListener(MouseMotionListener cal)
	{
		canvas.addMouseMotionListener(cal);
	}
	
	// Add mouse listner to the 3D canvas
	public void add3DMouseListener(MouseListener cal)
	{
		canvas.addMouseListener(cal);
	}
	
	// Add mouse whell listener to the 3D canvas
	public void add3DMouseWheelListener(MouseWheelListener cal)
	{
		canvas.addMouseWheelListener(cal);
	}
	
	// Get the input message from the textField
	public String getMessageInput()
	{
		return textField_Message.getText();
	}
	
	// Update nameText field
	public void updateNameTextField(String str)
	{
		textField_Name.setText(str);
	}
	// Udpate message table
	public void udpateMessageTable(Date loginDateTime, ArrayList<Message> messages)
	{
		String displayMessage = "";
		for(int i = 0; i < messages.size(); i++)
		{
			if(!messages.get(i).getDateTime().before(loginDateTime))
			{
				displayMessage += messages.get(i).getMessageSenderName() + " says: " + messages.get(i).getMessageContent() + "\n";
			}
		}
		textArea.setText(displayMessage);
		textField_Message.setText("");
	}
	
	
	// Update onlineusers table
	public void updateOnlineUsers(ArrayList<User> users, ArrayList<String> locationNames)
	{
		String textToShow = "";
		for(int i = 0; i < users.size(); i++)
		{
			String currentLocation = locationNames.get(users.get(i).getCurrentLocationId() - 1);
			textToShow += users.get(i).getUserName() + " [" + currentLocation + "]\n";
		}
		
		textArea_OnlineUses.setText(textToShow);				
	}

	// Display location info Scroll panel with mouse event
	public void displayLocationInfo(Point point, String locationName, int locationId, ArrayList<User> users)
	{	
		// Remove the scroll panel first
		if(locationInfoScrollPane != null)
		{
			this.label_2DCampus.remove(locationInfoScrollPane);
		}
		
		// Set scroll panel's position and size
		int draw_x = (int)point.getX();
		int draw_y = (int)point.getY();
		draw_x += 10;
		draw_y += 10;
		if(draw_x >= 400)
		{
			draw_x = 400;
		}
		if(draw_y >= 150)
		{
			draw_y = 150;
		}
		locationInfoScrollPane = new JScrollPane((Component) null);
		locationInfoScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		locationInfoScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		locationInfoScrollPane.setBounds(draw_x, draw_y, 200, 100);
		
		// Add info(locationName and usrs who are online)
		locationNamesTextArea = new JTextArea();
		String textToShow = locationName + "\n" + "-----------------------------------------\n" 
										 + "Who's here:\n";
		
		// Get who is online
		for(int i = 0; i < users.size(); i++)
		{
			if(users.get(i).getCurrentLocationId() == locationId + 1)
			{
				textToShow += users.get(i).getUserName() + "\n";
			}
		}
		
		// Set up a textArea for displaying location info
		locationNamesTextArea.setText(textToShow);		
		locationNamesTextArea.setWrapStyleWord(true);
		locationNamesTextArea.setLineWrap(true);
		locationNamesTextArea.setEditable(false);
		locationInfoScrollPane.setViewportView(locationNamesTextArea);
		locationInfoScrollPane.repaint();
		
		// Add this scroll pane to the 2D campus map label container
		this.label_2DCampus.add(locationInfoScrollPane);
		
		//update the canvas if new components added in
		this.label_2DCampus.repaint();
		this.label_2DCampus.validate();

	}
	
	// Remove the location info scroll panel
	public void clearLocationInfo()
	{
		if(locationNamesTextArea != null)
		{
			this.label_2DCampus.remove(locationInfoScrollPane);
			this.label_2DCampus.repaint();
			this.label_2DCampus.validate();
		}
	}
	
	// Clean the 3d scene for repainting later 
	public void cleanScene()
	{
		contentBranch.detach();	
	}
		
	// Update the 3D scene with a current location object
	public void update3DView(Location currentLocation)
	{
		setContent(currentLocation);
	}
	
	
	// Map animation with mouse event
	public void change2DMap(int mapId)
	{
		ImageIcon campus = new ImageIcon("resources/imgs/" + mapId + ".jpg");
		campus.setImage(campus.getImage().getScaledInstance(label_2DCampus.getWidth(), label_2DCampus.getHeight(), Image.SCALE_DEFAULT));
		label_2DCampus.setIcon(campus);
	}
}

