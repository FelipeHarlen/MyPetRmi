/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypetrmi;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class implements the client stub.
 * 
 * @author Yichen Wang
 *
 */

public class ClientStub implements My440Remote {
	private RemoteObjectRef ror;
	/**
	 * Set the remote object reference of the client stub
	 * @param ror the remote object reference
	 */

	public void setRemoteObjectRef(RemoteObjectRef ror) {
		this.ror = ror;
	}
        
	/**
	 * Get the remote object reference of the client stub
	 * @return the remote object reference of the client stub
	 */

	public RemoteObjectRef getRemoteObjectRef() {
		return ror;
	}

	/**
	 * Use the invoke message to remotely invoke a method
	 * @param msg the invoke message
	 * @return the return value or remote exception
	 * @throws My440RemoteException
	 */

	public Object remoteInvoke(InvokeMessage msg) throws My440RemoteException {
		RemoteObjectRef r = msg.getROR();
		if (!r.equals(ror)) {
			throw new My440RemoteException("Incorrent invoke message!");
		}

		RMIMessage responseMsg = null;
		try {
			Socket socket = new Socket(r.IP_adr, r.Port);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(msg);
			oos.flush();
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			responseMsg = (RMIMessage) ois.readObject();
			socket.close();
                        
		} catch (UnknownHostException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

			throw new My440RemoteException("Host not found!");

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

			throw new My440RemoteException("IOException!");

		} catch (ClassNotFoundException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

			throw new My440RemoteException("Incorrent Response: not a RMIMessage object");

		}

		if (responseMsg == null)

			return null;
                
		if (responseMsg.getMessageType().equals("return")) {
			return ((ReturnMessage) responseMsg).getReturnObject();

		} else if (responseMsg.getMessageType().equals("exception")) {

			throw ((ExceptionMessage) responseMsg).getRemoteException();

		} else {

			return null;

		}
	}
}
