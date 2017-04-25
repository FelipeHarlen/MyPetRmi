/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypetrmi;


import java.io.Serializable;



/**

 * This class implements remote object reference.

 * 

 * @author Yichen Wang

 *

 */

public class RemoteObjectRef implements Serializable {

	private static final long serialVersionUID = 1631581795692753053L;

	String IP_adr;

	int Port;

	int Obj_Key;

	String Remote_Interface_Name;



	/**

	 * Construct a Remote Object Reference.

	 * 

	 * @param ip

	 *            the IP address of host of the remote object

	 * @param port

	 *            of the host

	 * @param obj_key

	 *            the key of the remote object

	 * @param riname

	 *            the name of the remote interface

	 */

	public RemoteObjectRef(String ip, int port, int obj_key, String riname) {

		IP_adr = ip;

		Port = port;

		Obj_Key = obj_key;

		Remote_Interface_Name = riname;

	}



	/**

	 * Localize the remote object reference to a client stub.

	 * 

	 * @return the client stub of the remote object reference

	 * @throws My440RemoteException

	 */

	Object localise() throws My440RemoteException {

		// Implement this as you like: essentially you should

		// create a new stub object and returns it.

		// Assume the stub class has the name e.g.

		//

		// Remote_Interface_Name + "_stub".

		//

		// Then you can create a new stub as follows:

		//

		Class<?> c = null;

		Object o = null;

		try {

			c = Class.forName(Remote_Interface_Name + "_stub");

			o = c.newInstance();

			((ClientStub) o).setRemoteObjectRef(this);

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

			return new My440RemoteException(e.getMessage());

		} catch (InstantiationException e) {

			e.printStackTrace();

			return new My440RemoteException(e.getMessage());

		} catch (IllegalAccessException e) {

			e.printStackTrace();

			return new My440RemoteException(e.getMessage());
		}

		return o;

	}
        
	@Override
	public boolean equals(Object obj) {

		if (obj.getClass() == this.getClass()) {

			RemoteObjectRef ror = (RemoteObjectRef) obj;

			return IP_adr.equals(ror.IP_adr) && Port == ror.Port

					&& Obj_Key == ror.Obj_Key

					&& Remote_Interface_Name.equals(ror.Remote_Interface_Name);
		}

		return false;
	}
        
	@Override
	public int hashCode() {

		int code = this.IP_adr.hashCode() + this.Port

				+ this.Remote_Interface_Name.hashCode() + this.Obj_Key;

		return code;

	}
}
