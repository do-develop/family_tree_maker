package assignment2;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author Doyoung Oh 34061746
 * @version 0.3
 * @since 17/06/2021
 * @filename FamilyTree.java
 * */


/**
 * @purpose to limit relationship input and to ensure type safety
 * */
public class FamilyTree implements Serializable {

	//default serial UID for serializable class
	private static final long serialVersionUID = 1L;
	private FamilyMember startingPerson;
	private ArrayList<FamilyMember> family = new ArrayList<>();
	
	/**
	 * @category constructor
	 * */
	public FamilyTree(FamilyMember startingPerson){
		setStartPerson(startingPerson);
	}
	
//=================================================================================================
//CLASS METHODS
//=================================================================================================
	
	/**
	 * @purpose set a base person for the family tree
	 * @param a family member
	 * */
	public void setStartPerson(FamilyMember startingPerson) {
		this.startingPerson = startingPerson;

		if(family.size()>0) {
			family.set(0, startingPerson);
		}else {
			family.add(startingPerson);
		}

	}
	
	/**
	 * @purpose add a new family member
	 * @param a new member, relationship in regards to the base member, tree index of the base member
	 * @return true if member is successfully added or false
	 * */
	public boolean addFamilyMember(FamilyMember newMember, String relationship, int index) {
		
		if(Relationship.FATHER.toString().equalsIgnoreCase(relationship)) {
			family.get(index).setFather(newMember);
		}else if(Relationship.MOTHER.toString().equalsIgnoreCase(relationship)) {
			family.get(index).setMother(newMember);
		}else if(Relationship.SPOUSE.toString().equalsIgnoreCase(relationship)) {
			family.get(index).setSpouse(newMember);
		}else if(Relationship.CHILD.toString().equalsIgnoreCase(relationship)) {
			family.get(index).addChild(newMember);
		}else {
			return false;
		}
		family.add(newMember);
		return true;
	}
	
	/**
	 * @return the base member of the family tree
	 * */
	public FamilyMember getBaseMember() {
		return startingPerson;
	}
	
	/**
	 * @return size of the family tree
	 * */
	public int getFamilySize(){
		return family.size();
	}
	
	/**
	 * @param the position of the family tree
	 * @return a family member from the position in the family tree
	 * */
	public FamilyMember getFamilyMember(int position){
		if(position < family.size())
			return family.get(position);
		return null;
	}
	
	/**
	 * @param the name of the family member
	 * @return a family member that matches the name from the family tree
	 * */
	public FamilyMember getFamilyMemberByName(String name){
		for(FamilyMember member : family) {
			if(member.getFullName().equalsIgnoreCase(name))
			{
				return member;
			}
		}
		return null;
	}
	
	/**
	 * @purpose to make the family list empty
	 * */
	public void removeAllMembers() {
		if( (family != null) && (!family.isEmpty()) )
			family.removeAll(family);
	}
	
	/**
	 * @param the first name of the family member to be removed
	 * @return true if the removal is completed
	 * */
	public boolean removeFamilyMemberByName(String name) {
		for(int i=0; i<family.size(); ++i) {
			if(family.get(i).getFullName().equalsIgnoreCase(name))
			{
				family.remove(i);
				return true;
			}
		}
		return false;
	}
	
}
