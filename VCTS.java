/**
 * @(#)VCTS.java

 * @function: The enterance to the VCTS
 *
 * @author: Hao Shen
 * @version 1.00 2010/11/8
 * @version 1.03 2010/11/19
 * @modify 2.0  2010/12/08, Hao Shen, final version for final presentation
 */

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.rmi.*;
import java.util.Timer;

public class VCTS {
	
	public static void main(String[] args) {
	

        ClientModel	model = new ClientModel();
        ClientMainFrame view = new ClientMainFrame(model);
        ClientController controller = new ClientController(model, view);
        view.setVisible(true);
		
		
    }
}