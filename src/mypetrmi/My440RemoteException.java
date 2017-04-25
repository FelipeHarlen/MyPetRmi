/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypetrmi;

/**

 * This class implements the remote exception in our RMI facility

 * 

 * @author Yichen Wang

 *

 */

public class My440RemoteException extends Exception {



	private static final long serialVersionUID = 4805149747910033210L;

	private String message;

	

	public My440RemoteException(String msg) {

		message = msg;

	}

	

	public void printException() {

		System.out.println("440RemoteException: " + message);

	}

}
