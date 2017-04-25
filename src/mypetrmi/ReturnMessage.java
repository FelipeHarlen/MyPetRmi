/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypetrmi;

/**

 * This class implements the return message.

 * 

 * @author Yichen Wang

 *

 */

public class ReturnMessage extends RMIMessage {



	/**

	 * 

	 */

	private static final long serialVersionUID = -5852754919268157376L;



	public ReturnMessage(RemoteObjectRef r, String m, Object[] args) {

		super(r, m, args);

	}



	@Override

	String getMessageType() {

		return "return";

	}

	

	/**

	 * Get the return object from the message

	 * 

	 * @return the return object

	 */

	public Object getReturnObject() {

		Object[] obj = getObjects();

		return obj != null && obj.length > 0 ? obj[0]: null;

	}



}
