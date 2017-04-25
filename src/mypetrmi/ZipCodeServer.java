/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypetrmi;

import java.io.Serializable;

public interface ZipCodeServer extends My440Remote // extends YourRemote or whatever

{

    public void initialise(ZipCodeList newlist) throws My440RemoteException;

    public String find(String city) throws My440RemoteException;

    public ZipCodeList findAll() throws My440RemoteException;

    public void printAll() throws My440RemoteException;

}
