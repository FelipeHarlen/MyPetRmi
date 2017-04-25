/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypetrmi;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;

import java.io.PrintWriter;

import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;

import java.net.InetAddress;

import java.net.ServerSocket;

import java.net.Socket;

import java.net.UnknownHostException;

import java.util.HashMap;

import java.util.Map;



public class RMIServer {

	// Port number for registry

	public static int REGISTRY_PORT = 54321;

	// Port number for proxy

	public static int PROXY_PORT = 55555;

	// IP address

	private String ip = "";

	// RemoteObjectRef table, mapping reference to real object

	private RORtbl rorTable = new RORtbl();

	// Registry on the server

	private RegistryListener register;

	// Proxy on the server

	private ProxyListener proxy;



	/**

	 * Constructor Set up ip address, registry and proxy.

	 */

	public RMIServer() {

		try {

			// Set up ip address

			String ipAddr = InetAddress.getLocalHost().getHostAddress();

			register = new RegistryListener();

			register.start();

			proxy = new ProxyListener();

			proxy.start();

			this.ip = ipAddr;

			System.out.println("RMI Server address is : " + ipAddr);

		} catch (UnknownHostException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}



	}



	/**

	 * Execute the method given by the specification received from request.

	 * 

	 * @param msg

	 *            Invoke message

	 * @return Return message

	 * @throws My440RemoteException

	 */

	public RMIMessage execute(InvokeMessage msg) throws My440RemoteException {

		// System.out.println("Execute.");

		RemoteObjectRef ror = msg.getROR();

		Object localObject = rorTable.findObj(ror);

		try {

			if (msg == null) {

				return new ExceptionMessage(ror, msg.getMethod(),

						new Object[] { new My440RemoteException(

								"Invalid message!") });

			}

			if (localObject == null) {

				return new ExceptionMessage(ror, msg.getMethod(),

						new Object[] { new My440RemoteException(

								"No such real object!") });

			}

			Method method = localObject.getClass().getMethod(msg.getMethod(),

					msg.getClasses());

			Object[] params = msg.getObjects();



			// If parameter is a remoteobjectref tyoe,

			// after we call that, we need to convert

			// it into the real object on the server.

			for (int i = 0; i < params.length; i++) {

				Object o = params[i];

				if (o instanceof RemoteObjectRef) {

					RemoteObjectRef ro = (RemoteObjectRef) o;

					Object realObj = this.rorTable.findObj(ro);

					if (realObj == null) {

						return new ExceptionMessage(ror, msg.getMethod(),

								new Object[] { new My440RemoteException(

										"No such remote reference!") });

					} else {

						params[i] = realObj;

					}

				}

			}

			// Receive the return value and wrap it into the return message

			Object returnVal = method.invoke(localObject, params);



			// Return a stub instead of the object if the return value type is a

			// Remote object

			if (returnVal instanceof My440Remote) {

				System.out.println("Return a remote object!");

				RemoteObjectRef newROR = this.rorTable.addObj(this.ip,

						RMIServer.PROXY_PORT, returnVal);

				returnVal = newROR.localise();

			}

			ReturnMessage rMsg = new ReturnMessage(ror, msg.getMethod(),

					returnVal == null ? new Object[0]

							: new Object[] { returnVal });

			return rMsg;

		} catch (NoSuchMethodException e) {

			e.printStackTrace();

			return new ExceptionMessage(

					ror,

					msg.getMethod(),

					new Object[] { new My440RemoteException("No such method!") });

		} catch (SecurityException e) {

			e.printStackTrace();

			return new ExceptionMessage(ror, msg.getMethod(),

					new Object[] { new My440RemoteException("Security!") });

		} catch (IllegalAccessException e) {

			e.printStackTrace();

			return new ExceptionMessage(

					ror,

					msg.getMethod(),

					new Object[] { new My440RemoteException("Illegal Access!") });

		} catch (IllegalArgumentException e) {

			e.printStackTrace();

			return new ExceptionMessage(

					ror,

					msg.getMethod(),

					new Object[] { new My440RemoteException("Illegal Argument") });

		} catch (InvocationTargetException e) {

			e.printStackTrace();

			return new ExceptionMessage(ror, msg.getMethod(),

					new Object[] { new My440RemoteException(

							"Invocation Target Error!") });

		}

	}



	/**

	 * Service for handling RMI call

	 */

	private class ProxyListener extends Thread {

		public void run() {

			Socket socket = null;

			try {

				ServerSocket serverSocket = new ServerSocket(

						RMIServer.PROXY_PORT);

				while (true) {

					socket = serverSocket.accept();

					System.out.println(socket.getInetAddress() + "  "

							+ socket.getPort());

					System.out.println("RMI Call Accept");

					InputStream in = socket.getInputStream();

					ObjectInputStream ois = new ObjectInputStream(in);



					InvokeMessage message = (InvokeMessage) ois.readObject();



					ObjectOutputStream out = new ObjectOutputStream(

							socket.getOutputStream());



					out.writeObject(RMIServer.this.execute(message));



					out.flush();

					ois.close();

					out.close();

					socket.close();



				}

			} catch (IOException e) {

				e.printStackTrace();

			} catch (ClassNotFoundException e) {

				e.printStackTrace();

			} catch (My440RemoteException e) {

				e.printStackTrace();

			}

		}

	}



	/**

	 * Server for handling registry request

	 */

	private class RegistryListener extends Thread {

		private HashMap<String, RemoteObjectRef> registryTable = new HashMap<String, RemoteObjectRef>();



		public void run() {

			Socket socket = null;

			try {

				ServerSocket serverSocket = new ServerSocket(

						RMIServer.REGISTRY_PORT);

				System.out.println("Establish server socket.");

				while (true) {

					socket = serverSocket.accept();

					System.out.println("Registry Accept");

					BufferedReader in = new BufferedReader(

							new InputStreamReader(socket.getInputStream()));

					PrintWriter out = new PrintWriter(socket.getOutputStream(),

							true);

					String command = in.readLine();

					System.out.println(socket.getInetAddress());

					System.out.println("Received command: ");

					if (command.equals("who are you?")) {

						// For handling the ACK

						out.println("I am a simple registry.");

					} else if (command.equals("lookup")) {

						// For handling the lookup

						String serviceName = in.readLine();

						System.out.println(serviceName);

						if (registryTable.containsKey(serviceName)) {

							RemoteObjectRef ror = registryTable

									.get(serviceName);

							out.println("found");

							out.println(ror.IP_adr);

							out.println(ror.Port);

							out.println(ror.Obj_Key);

							out.println(ror.Remote_Interface_Name);

						} else {

							out.println("not found");

						}

					} else if (command.equals("rebind")) {

						// For handling the rebind

						String serviceName = in.readLine();

						String ipAddr = in.readLine();

						int portNum = Integer.valueOf(in.readLine());

						int objectKey = Integer.valueOf(in.readLine());

						String interfaceName = in.readLine();



						RemoteObjectRef ror = new RemoteObjectRef(ipAddr,

								portNum, objectKey, interfaceName);



						registryTable.put(serviceName, ror);



						out.println("rebinded");

					} else {

						// Unknown command

						out.println("Sorry I don't understand.");

					}

					in.close();

					out.close();

					socket.close();

				}

			} catch (IOException e) {



			}

		}

	}



	public static void main(String[] args) {

		RMIServer rmiServ = new RMIServer();

		ZipCodeServer zipServ = new ZipCodeServerImpl();

		RemoteObjectRef ror = rmiServ.rorTable.addObj(rmiServ.ip,

				RMIServer.PROXY_PORT, zipServ);



		ZipCodeRList zipRList = new ZipCodeRListImpl();

		RemoteObjectRef ror2 = rmiServ.rorTable.addObj(rmiServ.ip,

				RMIServer.PROXY_PORT, zipRList);

		

		SimpleRegistry registry = LocateSimpleRegistry.getRegistry(rmiServ.ip,

				RMIServer.REGISTRY_PORT);

		try {

			registry.rebind("zipcode", ror);

			registry.rebind("rlist", ror2);

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
