/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypetrmi;

/**

 * This class implements the exception message

 * 

 * @author Yichen Wang

 *

 */

public class ExceptionMessage extends RMIMessage {



	private static final long serialVersionUID = -3756561519251816829L;

	

	public ExceptionMessage(RemoteObjectRef r, String m, Object[] args) {

		super(r, m, args);

	}



	@Override

	String getMessageType() {

		return "exception";

	}

	

	/**

	 * Get the exception

	 * @return the exception

	 */

	public My440RemoteException getRemoteException() {

		Object[] obj = getObjects();

		return (My440RemoteException) obj[0];

	}

}
