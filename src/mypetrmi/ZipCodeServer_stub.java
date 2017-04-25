/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypetrmi;

import java.io.Serializable;

public class ZipCodeServer_stub extends ClientStub implements ZipCodeServer {

	/**
	 * 
	 */

	private static final long serialVersionUID = -5497177114838126675L;



	@Override

	public void initialise(ZipCodeList newlist) throws My440RemoteException {

		// TODO Auto-generated method stub

		InvokeMessage msg = new InvokeMessage(getRemoteObjectRef(), "initialise", new Object[]{newlist});

		remoteInvoke(msg);

	}



	@Override

	public String find(String city) throws My440RemoteException {

		// TODO Auto-generated method stub

		InvokeMessage msg = new InvokeMessage(getRemoteObjectRef(), "find", new Object[]{city});

		return (String) remoteInvoke(msg);

	}



	@Override

	public ZipCodeList findAll() throws My440RemoteException {

		// TODO Auto-generated method stub

		InvokeMessage msg = new InvokeMessage(getRemoteObjectRef(), "findAll", new Object[]{});

		return (ZipCodeList) remoteInvoke(msg);

	}



	@Override

	public void printAll() throws My440RemoteException {

		InvokeMessage msg = new InvokeMessage(getRemoteObjectRef(), "printAll", new Object[]{});

		remoteInvoke(msg);

	}
}
