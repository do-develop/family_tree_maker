package assignment2;

import java.io.Serializable;

/**
 * @author Doyoung Oh 34061746
 * @version 0.1
 * @since 17/06/2021
 * @filename Address.java
 * */


public class Address implements Serializable {

	//default serial UID for serializable class
	private static final long serialVersionUID = 1L;

	private String streetNumber;
	private String streetName;
	private String suburb;
	private String postcode;
	
	/**
	 * @category constructor
	 * */
	public Address(String streetNumber, String streetName, String suburb, String postcode){
		setStreetNumber(streetNumber);
		setStreetName(streetName);
		setSuburb(suburb);
		setPostcode(postcode);
	}
	
//=================================================================================================
//GETTER
//=================================================================================================
	
	/**
	 * @return street number
	 * */
	public String getStreetNumber() {
		return streetNumber;
	}

	/**
	 * @return street name
	 * */
	public String getStreetName() {
		return streetName;
	}

	/**
	 * @return suburb name
	 * */
	public String getSuburb() {
		return suburb;
	}
	
	/**
	 * @return post code
	 * */
	public String getPostcode() {
		return postcode;
	}

//=================================================================================================
//SETTER
//=================================================================================================
	
	/**
	 * @param street number
	 * */
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	
	/**
	 * @param street name
	 * */
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	
	/**
	 * @param surburb name
	 * */
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	/**
	 * @param post code
	 * */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
}
