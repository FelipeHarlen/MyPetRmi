/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypetrmi;

public class ZipCodeRList_stub extends ClientStub implements ZipCodeRList {



	@Override

	public String find(String city) throws My440RemoteException {

		InvokeMessage msg = new InvokeMessage(getRemoteObjectRef(), "find", new Object[]{city});

		return (String) remoteInvoke(msg);

	}



	@Override

	public ZipCodeRList add(String city, String zipcode)

			throws My440RemoteException {

		InvokeMessage msg = new InvokeMessage(getRemoteObjectRef(), "add", new Object[]{city, zipcode});

		return (ZipCodeRList) remoteInvoke(msg);

	}



	@Override

	public ZipCodeRList next() throws My440RemoteException {

		InvokeMessage msg = new InvokeMessage(getRemoteObjectRef(), "next", new Object[0]);

		return (ZipCodeRList) remoteInvoke(msg);

	}
}
