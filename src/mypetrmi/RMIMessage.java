/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypetrmi;

import java.io.Serializable;



/**

 * This class implements the message used in our RMI facility.

 * 

 * @author Yichen Wang

 *

 */

public abstract class RMIMessage implements Serializable {



	private static final long serialVersionUID = 2131510874174393140L;

	private RemoteObjectRef ror;

	private String methodName;

	private Object[] objects;

	private Class<?>[] classes;



	abstract String getMessageType();

	

	/**

	 * Construct a new RMIMessage with the remote object reference, method name, and

	 * the arguments

	 * @param r the remote object reference

	 * @param m the method name

	 * @param objects in the mesagef

	 */

	public RMIMessage(RemoteObjectRef r, String m, Object[] args) {

		ror = r;

		methodName = m;

		objects = args;

		classes = new Class<?>[args.length];

		for (int i = 0; i < args.length; i++) {

			if (args[i] != null) {

				classes[i] = args[i].getClass();

			}

		}

	}
        
	/**

	 * Return the remote object reference

	 * @return the remote object reference

	 */

	public RemoteObjectRef getROR() {
		return ror;
	}

	/**

	 * Return method name in the message

	 * @return the method name

	 */

	public String getMethod() {
		return methodName;
	}	

	/**

	 * Return the objects in the message

	 * @return the objects

	 */

	public Object[] getObjects() {
		return objects;
	}

	/**

	 * Return the classes of the objects

	 * @return the classes of the objects

	 */

	public Class<?>[] getClasses() {

		return classes;

	}
}
