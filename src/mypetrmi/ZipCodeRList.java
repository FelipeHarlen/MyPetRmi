/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypetrmi;

import java.io.Serializable;



public interface ZipCodeRList extends My440Remote // extends YourRemote or whatever

{

	public String find(String city) throws My440RemoteException;



	public ZipCodeRList add(String city, String zipcode)

			throws My440RemoteException;



	public ZipCodeRList next() throws My440RemoteException;
 
}
