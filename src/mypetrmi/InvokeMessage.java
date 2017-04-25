/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypetrmi;

/**

 * This class implements the invoke message.

 * 

 * @author Yichen Wang

 *

 */

public class InvokeMessage extends RMIMessage {

	private static final long serialVersionUID = 8337918234548130917L;

	public InvokeMessage(RemoteObjectRef r, String m, Object[] args) {

		super(r, m, args);

	}

	@Override
	String getMessageType() {

		return "invoke";

	}
}
