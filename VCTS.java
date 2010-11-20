/**
 * @(#)VCTS.java

 * @function: The enter to the VCTS
 *
 * @author: Hao Shen
 * @version 1.00 2010/11/8
 * @version 1.03 2010/11/19
 */

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.*;
import java.util.Timer;

public class VCTS {
    //... Create model, view, and controller.  They are
    //    created once here and passed to the parts that
    //    need them so there is only one copy of each.
    
	
	
	public static void main(String[] args) {
	

        ClientModel	model = new ClientModel();
        ClientMainFrame view = new ClientMainFrame(model);
        ClientController controller = new ClientController(model, view);
        view.setVisible(true);
		
		
    }
}