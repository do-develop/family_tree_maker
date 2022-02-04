package assignment2;

public class UnitTest {

	public static void main(String[] args) {
		//Address
		System.out.println("<Unit Test Address>");
		Address address = new Address("52", "Bada", "Happy Town", "34521");
		
		System.out.println("\nAfter Constructor:");
		System.out.println("Street name: "+address.getStreetName());
		System.out.println("Street number: "+address.getStreetNumber());
		System.out.println("Suburb: "+address.getSuburb());
		System.out.println("Postcode: "+address.getPostcode());
		
		address.setStreetName("Namu");
		address.setStreetNumber("77");
		address.setSuburb("Smile Town");
		address.setPostcode("12345");
		
		System.out.println("\nAfter Setters:");
		System.out.println("Street name: "+address.getStreetName());
		System.out.println("Street number: "+address.getStreetNumber());
		System.out.println("Suburb: "+address.getSuburb());
		System.out.println("Postcode: "+address.getPostcode());
		
		//FamilyMember
		System.out.println("\n<Unit Test FamilyMember>");
		FamilyMember member = new FamilyMember("Star", "Flower", 
												"Butterfly", "FEMALE",
												address, "I'm a magical princess from another dimension!");
		
		System.out.println("\nAfter constructor:");
		System.out.println("First name: "+member.getFirstName());
		System.out.println("Birth last name: "+member.getBirthLastName());
		System.out.println("Marriage last name: "+member.getMarryLastName());
		System.out.println("Gender: "+member.getGender());
		System.out.println("Address surburb: "+member.getAddress().getSuburb());
		System.out.println("Description: "+member.getDescription());
		
		Address newAddress = new Address("11", "Yolo", "Magic Town", "10101");
		member.setAddress(newAddress);
		member.setFirstName("Marco");
		member.setBirthLastName("Diaz");
		member.setMarryLastName("Diaz");
		member.setGender("MALE");
		member.setDescription("I am happy!");		
		
		System.out.println("\nAfter Setters:");
		System.out.println("First name: "+member.getFirstName());
		System.out.println("Birth last name: "+member.getBirthLastName());
		System.out.println("Marriage last name: "+member.getMarryLastName());
		System.out.println("Gender: "+member.getGender());
		System.out.println("Address surburb: "+member.getAddress().getSuburb());
		System.out.println("Description: "+member.getDescription());
		
		FamilyMember spouse = new FamilyMember("Star", "Flower", 
				"Butterfly", "FEMALE",
				address, "I'm a magical princess from another dimension!");
		FamilyMember father = new FamilyMember("John", "Diaz", 
				"Diaz", "MALE",
				address, "I am the father of Marco");
		FamilyMember mother = new FamilyMember("Selena", "Gomez", 
				"Diaz", "FEMALE",
				address, "I am the mother of Marco");
		
		member.setSpouse(spouse);
		member.setMother(mother);
		member.setFather(father);
		
		System.out.println("\nSpouse Fullname: "+member.getSpouse().getFullName());
		System.out.println("Father Fullname: "+member.getFather().getFullName());
		System.out.println("Mother Fullname: "+member.getMother().getFullName());
		
		System.out.println("\nTest isEqualMember(FamilyMember)");
		System.out.println("Member is eqaul member of Member: " + member.isEqualMember(member));
		System.out.println("Spouse is eqaul member of Member: " +member.isEqualMember(spouse));
		
		System.out.println("\nTest isEqualMember(FamilyMember)");
		System.out.println("Member is eqaul member of Member: " + member.isEqualMember(member));
		System.out.println("Spouse is eqaul member of Member: " +member.isEqualMember(spouse));
		
		System.out.println("\nTest removeParent(FamilyMember)");
		System.out.println("Remove mother relationship: " + member.removeParent(spouse));
		System.out.println("Remove mother relationship: " + member.removeParent(mother));
		
		
		System.out.println("\nTest removeSpouse(FamilyMember)");
		System.out.println("Remove mother relationship: " + member.removeSpouse(mother));
		System.out.println("Remove mother relationship: " + member.removeSpouse(spouse));
		
		System.out.println("\nTest addChild(FamilyMember)");
		member.addChild(spouse);
		System.out.println("Add child relationship: " + member.getChildren().get(0).getFullName());
		
		System.out.println("\nTest removeChild(String fullname)");
		System.out.println("Remove child relationship: " + member.removeChild(member.getFullName()));
		System.out.println("Remove child relationship: " + member.removeChild(member.getChildren().get(0).getFullName()));
		
		
		//FamilyTree
		System.out.println("\n<Unit Test FamilyTree>");
		
		System.out.println("\nAfter constructor:");
		FamilyTree familyTree = new FamilyTree(member);
		System.out.println("Base member: " + familyTree.getBaseMember().getFullName());
		System.out.println("Family size: " + familyTree.getFamilySize());
		
		familyTree.addFamilyMember(mother, "MOTHER", 0);
		familyTree.addFamilyMember(father, "FATHER", 0);
		System.out.println("Base member: " + familyTree.getBaseMember().getFullName());
		System.out.println("Family size: " + familyTree.getFamilySize());
		
		System.out.println("\nTest getFamilyMember(int index)");
		System.out.println("Get family member by index position: " + familyTree.getFamilyMember(2).getFullName());
		
		System.out.println("\nTest getFamilyMemberByName(String fullname)");
		System.out.println("Mother's birth last name: " + familyTree.getFamilyMemberByName("Selena Diaz").getBirthLastName());
		
		System.out.println("\nTest removeFamilyMemberByName(String fullname)");
		familyTree.removeFamilyMemberByName("John Diaz");
		System.out.println("Family size: " + familyTree.getFamilySize());
	}
	

}
