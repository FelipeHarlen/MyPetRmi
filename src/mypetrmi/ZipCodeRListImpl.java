/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypetrmi;

public class ZipCodeRListImpl 

    implements  ZipCodeRList

{

    /**

	 * 

	 */

	private static final long serialVersionUID = -8190382587119161707L;

	String city;

    String zipcode;

    ZipCodeRList next;



    // this constructor creates the terminal of the list.

    // it is assumed this is called at the outset.

    public ZipCodeRListImpl()	

    {

	city = null;

	zipcode = null;

	next = null;

    }



    // this is the standard constructor.

    public ZipCodeRListImpl(String c, String z, ZipCodeRList n)

    {

	city = c;

	zipcode = z;

	next = n;

    }



    // finding the zip code only for that cell.

    // its client can implement recursive search.

    public String find(String c)

    {

    	System.out.println("c "  + c);

    	System.out.println("city: " + city);

	if (c.equals(city))

	    return zipcode;

	else

	    return null;

    }



    // this is essentially cons.

    public ZipCodeRList add(String c, String z)

    {

	return new ZipCodeRListImpl(c, z, this);

    }



    // this is essentially car.

    public ZipCodeRList next()

    {

	return next;

    }

    

    public static void main(String[] args) {

    	ZipCodeRListImpl z = new ZipCodeRListImpl();

    	System.out.println(z instanceof My440Remote);
        
    }

}
