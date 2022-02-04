package assignment2;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Doyoung Oh 34061746
 * @version 0.2
 * @since 17/06/2021
 * @filename FamilyMember.java
 * */


/**
 * @purpose to limit gender input and to ensure type safety
 * */
enum Gender{
	MALE,
	FEMALE
}

public class FamilyMember implements Serializable {


	 //default serial UID for serializable class
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String birthLastName; //will not disappear after marriage
	private String marryLastName;
	private Gender gender;
	private Address address;
	private String description;
	
	private FamilyMember mother;
	private FamilyMember father;
	private FamilyMember spouse;
	private ArrayList<FamilyMember> children = new ArrayList<>();
	
	/**
	 * @category constructor
	 * */
	public FamilyMember(String firstName, String birthLastName, String marryLastName, String gender, 
				Address address, String description){
		setFirstName(firstName);
		setBirthLastName(birthLastName);
		setMarryLastName(marryLastName);
		setGender(gender);
		setAddress(address);
		setDescription(description);
	}
	
//=================================================================================================
//GETTER
//=================================================================================================
	
	/**
	 * @return first name of the family member
	 * */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return birth last name of the family member
	 * */
	public String getBirthLastName() {
		return birthLastName;
	}

	/**
	 * @return last name after marriage of the family member
	 * */
	public String getMarryLastName() {
		return marryLastName;
	}
	
	/**
	 * @return full name of the member
	 * */
	public String getFullName() {
		String fullName="";
		
		if(marryLastName.isEmpty()) {
			fullName += (firstName + " " + birthLastName);
		}else {
			fullName += (firstName + " " + marryLastName);
		}
		return fullName;
		
	}
	/**
	 * @return gender of the family member
	 * */
	public String getGender() {
		return gender.toString();
	}

	/**
	 * @return address of the family member
	 * */
	public Address getAddress() {
		return address;
	}

	/**
	 * @return short description of the family member
	 * */
	public String getDescription() {
		return description;
	}	

	/**
	 * @return mother of the family member
	 * */
	public FamilyMember getMother() {
		return mother;
	}

	/**
	 * @return father of the family member
	 * */
	public FamilyMember getFather() {
		return father;
	}

	/**
	 * @return spouse of the family member
	 * */
	public FamilyMember getSpouse() {
		return spouse;
	}

	/**
	 * @return children of the family member
	 * */
	public ArrayList<FamilyMember> getChildren() {
		return children;
	}

//=================================================================================================
//SETTER
//=================================================================================================
	
	/**
	 * @param first name of the family member
	 * */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @param birth last name of the family member
	 * */
	public void setBirthLastName(String birthLastName) {
		this.birthLastName = birthLastName;
	}
	
	/**
	 * @param last name after marriage of the family member
	 * */
	public void setMarryLastName(String marryLastName) {
		this.marryLastName = marryLastName;
	}
	
	/**
	 * @param gender of the family member
	 * */
	public void setGender(String gender) {
		if(Gender.MALE.toString().equalsIgnoreCase(gender)) {
			this.gender = Gender.MALE;
		}
		if(Gender.FEMALE.toString().equalsIgnoreCase(gender)) {
			this.gender = Gender.FEMALE;
		}
	}

	/**
	 * @param address of the family member
	 * */
	public void setAddress(Address address) {
		this.address = address;
	}
	
	/**
	 * @param short description of the family member
	 * */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @param mother of the family member
	 * */
	public void setMother(FamilyMember mother) {
		this.mother = mother;
	}
	
	/**
	 * @param father of the family member
	 * */
	public void setFather(FamilyMember father) {
		this.father = father;
	}
	
	/**
	 * @purpose 
	 * */
	public void setParentRelationship() {
		//if the member has mother and father, mother and father refer each other as spouse
    	FamilyMember memberFather = this.getFather();
    	FamilyMember memberMother = this.getMother();
    	if(memberFather!=null && memberMother!=null) {
    		memberFather.setSpouse(memberMother);
    		memberMother.setSpouse(memberFather);
    	}
    	//the parents refer myself as a child
    	if(memberFather != null)
    		memberFather.addChild(this);
    	if(memberMother != null)
    		memberMother.addChild(this);
	}

	/**
	 * @param spouse of the family member
	 * */
	public void setSpouse(FamilyMember spouse) {
		this.spouse = spouse;
	}
	
	/**
	 * @purpose set family relationship around a spouse
	 * */
	public void setSpouseRelationship() {
		FamilyMember spouse = this.getSpouse();
    	//my spouse also set myself as a spouse
    	if(spouse.getSpouse()==null) {
    		spouse.setSpouse(this);
    	}
	}
	
//=================================================================================================
// CLASS METHOD
//=================================================================================================
	
	/**
	 * @param a child of the family member
	 * */
	public void addChild(FamilyMember newChild) {
		if(!isDuplicateChild(newChild)) {
			children.add(newChild);
		}
	}
	
	/**
	 * @purpose set family relationship around children
	 * */
	public void setChildrenRelationship() {
		for(int i=0; i<this.getChildren().size(); ++i) {
        	//child refers mom and dad
        	FamilyMember child = this.getChildren().get(i);
        	if(this.getGender().equals("FEMALE")) {
        		child.setMother(this);
        		if(spouse!=null) {
        			child.setFather(spouse);
        			spouse.addChild(child);
        		}
        	}else {
        		child.setFather(this);
        		if(spouse!=null) {
        			child.setMother(spouse);
        			spouse.addChild(child);
        		}
        	}
        }
	}
	
	/**
	 * @param a parent family member
	 * @return true if the specified member is removed from this family member relationship
	 * */
	public boolean removeParent(FamilyMember parent) {
		boolean isRemoved = false;
		if(getFather()!=null) {
			if(getFather().getFullName().equalsIgnoreCase(parent.getFullName())) {
				setFather(null);
				//mother will delete spouse relationship
				if(getMother() != null) {
		    		getMother().setSpouse(null);
		    	}
				isRemoved  = true;
			}
		}
		if(getMother()!=null) {
			if(getMother().getFullName().equalsIgnoreCase(parent.getFullName())) {
				setMother(null);
				//father will delete spouse relationship
		    	if(getFather() != null) {
		    		getFather().setSpouse(null);
		    	}
				isRemoved  = true;
			}	
		}
		return isRemoved;	
	}
	
	/**
	 * @param a spouse family member
	 * @return true if the specified member is removed from this family member relationship
	 * */
	public boolean removeSpouse(FamilyMember spouse) {
		boolean isRemoved = false;
		
		if(getSpouse().getFullName().equalsIgnoreCase(spouse.getFullName())) {
			setSpouse(null);
			//if I have a child, the child will remove the parent relationship for the deleted spouse
			if(children.size() != 0) {
				for(FamilyMember child : children)
					child.removeParent(spouse);
	    	}
			isRemoved = true;
		}
		return isRemoved;
	}
	

	
	/**
	 * @param a family member
	 * @return true if the compared member is the same
	 * */
	public boolean isEqualMember(FamilyMember member) {
		if(getFirstName().equals(member.getFirstName())) {
			return true;
		}
		return false;
	}
	
	/**
	 * @purpose remove the child of matching name from the children array list
	 * @param full name of a child
	 * @return true if the child is successfully removed
	 * */
	public boolean removeChild(String childFullName) {
		for(int i=0; i<children.size(); ++i) {
			if(children.get(i).getFullName().equals(childFullName)) {
				children.remove(i);
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * @purpose remove family relationship around removed children
	 * */
	public void removeChildRelationship(FamilyMember child) {
		String childName = child.getFullName();
		//remove child relationship from self
		this.removeChild(childName);
		//if I have a spouse, remove child from the spouse children
		FamilyMember spouse = this.getSpouse();
		if(spouse!=null) {
			spouse.removeChild(childName);
		}
				
	}
//=================================================================================================
//HELPER METHOD
//=================================================================================================	
	
	/**
	 * @param a child family member
	 * @return true if the child already exist in the children list
	 * */
	private boolean isDuplicateChild(FamilyMember newChild) {
		String newChildName = newChild.getFirstName();
		
		for(int i=0; i<children.size(); ++i) {
			if(children.get(i).getFirstName().equalsIgnoreCase(newChildName)) {
				return true;
			}
		}
		return false;
	}
	
}//end of FamilyMember.java
