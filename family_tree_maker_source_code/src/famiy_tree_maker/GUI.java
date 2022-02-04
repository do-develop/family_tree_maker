package assignment2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * @author Doyoung Oh 34061746
 * @version 0.3
 * @since 17/06/2021
 * @filename GUI.java
 * */

/**
 * @purpose for better maintenance and readability of possible relationship of this tree maker
 * */
enum Relationship{
	FATHER,
	MOTHER,
	SPOUSE,
	CHILD
}


public class GUI extends Application 
{

	//VARIABLES
	String familyName = "";
    FamilyTree familyTree;
    boolean isViewMode = false;
    private static Logger logger = Logger.getLogger(GUI.class.getName());
    private static FileHandler logfile; 
	Stage window;
	
    //MAIN WINDOW
    BorderPane root = new BorderPane();    
    
    //MENU PANES
    VBox topContent = new VBox();
    VBox leftContent = new VBox();
    VBox centerContent = new VBox();
    GridPane rightContent = new GridPane();
    VBox bottomContent = new VBox();
    
    //TOP
    Button view = new Button("View Mode");
	Button edit = new Button("Edit Mode");
	
	//LEFT
	HBox memberButtons = new HBox();
	Button newTree = new Button("New FamilyTree");		
	Button loadTree = new Button("Load FamilyTree");
	Button saveTree = new Button("Save FamilyTree");
	ListView<String> membersList = new ListView<String>();
	ObservableList<String> memberData = FXCollections.observableArrayList();
    
	//CENTER
	ListView<String> treeList = new ListView<String>();
	ObservableList<String> treeData = FXCollections.observableArrayList();
	
	//RIGHT
    TextField fNameTxt = new TextField();
    TextField sBNameTxt = new TextField();
    TextField sMNameTxt = new TextField();
    ComboBox<String> genderBox = new ComboBox<>();
    TextField addressSNumTxt = new TextField();  
    TextField addressSNameTxt = new TextField();  
    TextField addressSubTxt = new TextField(); 
    TextField addressPcodeTxt = new TextField();  
    TextField descriptionTxt = new TextField();
    TextField motherTxt = new TextField(); 
    TextField fatherTxt = new TextField();
    TextField spouseTxt = new TextField(); 
    TextArea childrenTxt = new TextArea();
    
    Button addFather = new Button("Add");	Button deleteFather = new Button("Delete");
    Button addMother = new Button("Add");	Button deleteMother = new Button("Delete");
    Button addSpouse = new Button("Add");	Button deleteSpouse = new Button("Delete");
    Button addChild = new Button("Add");	Button deleteChild = new Button("Delete");
	Button update = new Button("Update");

	
	//ADD MEMBER (AM) WINDOW   
	Label AMTitle = new Label();
    TextField AMfNameTxt = new TextField();
    TextField AMsBNameTxt = new TextField();
    TextField AMsMNameTxt = new TextField();
    ComboBox<String> AMgenderBox = new ComboBox<>();
    TextField AMaddressTxt = new TextField();   
    TextField AMaddressSNumTxt = new TextField();  
    TextField AMaddressSNameTxt = new TextField();       
    TextField AMaddressSubTxt = new TextField(); 
    TextField AMaddressPcodeTxt = new TextField();        
    TextField AMdescriptionTxt = new TextField();
    
    Button saveMember = new Button("Save");
    Button cancelMember = new Button("Cancel");
    Stage addNewMember = new Stage();

	//DELETE CHILD MEMBER
    Button deleteChildSelectButton;
	ChoiceBox<String> childChoice;
	Stage deleteChildStage = new Stage();
	
	/**
	 * @purpose to create all UI components needed for start() method
	 * @param the primary stage for this application
	 * */
    private void createUI(Stage topView) {
    	
    	//LOG FILE
        try {
			logfile = new FileHandler("log/FTM_log.txt");
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
        logfile.setFormatter(new SimpleFormatter());
 	   	logger.addHandler(logfile);
 	   	
 	   	//STAGE
    	window = topView;
		window.setTitle("Family Tree Maker");
		
		//TOP
		try {
			FileInputStream input = new FileInputStream("asset/tree_icon.png");
			Image treeIcon = new Image(input);
	        ImageView treeImage = new ImageView(treeIcon);
			Label title = new Label("Family Tree Maker", treeImage);
			title.setId("bold-title-label");
			
			HBox modes = new HBox();
			modes.setSpacing(5);
			modes.setPadding(new Insets(10, 10, 10, 10));
			modes.getChildren().addAll(view, edit);
			modes.setAlignment(Pos.CENTER);
			topContent.getChildren().addAll(title, modes);
			topContent.setAlignment(Pos.CENTER);
			
		} catch (FileNotFoundException e) {
			logger.log(Level.WARNING, "TREE ICON IMAGE FILE NOT FOUND: " + e);
			e.printStackTrace();
		}
		
		/**
		 * @purpose switch view mode, upon button click event
		 * */
		view.setOnAction(e -> {
			switchMode();
		});
		
		/**
		 * @purpose switch edit mode, upon button click event
		 * */
		edit.setOnAction(e -> {
			switchMode();
		});
	
		//BOTTOM
		bottomContent.setPadding(new Insets(10, 10, 10, 10));
		Button exit = new Button("Exit Family Tree Maker");
		bottomContent.getChildren().addAll(exit);
		bottomContent.setAlignment(Pos.CENTER);
	
		
		/**
		 * @purpose exit application, upon button click event
		 * */
		exit.setOnAction(e -> {
			System.exit(0);
		});
		
		
		//LEFT
		memberButtons.setSpacing(5);
		leftContent.setPadding(new Insets(10, 10, 10, 10));
		Label members = new Label("Family Members:");
		members.setId("bold-label");
		membersList.setItems(memberData);
		membersList.setPrefWidth(323);
		ScrollPane memberScroll = new ScrollPane(membersList);
		newTree.setId("tree-buttons");
		loadTree.setId("tree-buttons");
		saveTree.setId("tree-buttons");
		memberButtons.getChildren().addAll(newTree, loadTree, saveTree);
		leftContent.getChildren().addAll(members, memberScroll, memberButtons);
		
		/**
		 * @purpose open add member window, upon button click event
		 * */
		newTree.setOnAction(e -> {
			openAddMember("Family");
		});
		
		/**
		 * @purpose open save member window, upon button click event
		 * */
		saveTree.setOnAction(e ->{
			saveTree();
		});
		
		/**
		 * @purpose open load member window, upon button click event
		 * */
		loadTree.setOnAction(e -> {
			loadTree();
        });
		
		/**
		 * @purpose show detail of the selected member, upon a member from the membersList is selected
		 * */
		membersList.setOnMouseClicked(e -> {
			showTree(membersList.getSelectionModel().getSelectedIndex());
			updateDetailsPanel(familyTree.getFamilyMember(membersList.getSelectionModel().getSelectedIndex()));
		});
		
		//CENTER
		Label treeLabel = new Label("Family Tree:");
		treeLabel.setId("bold-label");
		treeList.setItems(treeData);
		treeList.setPrefWidth(330);
		////make the tree list unclickable
		treeList.setMouseTransparent( true );
		treeList.setFocusTraversable( false );
		ScrollPane treeScroll = new ScrollPane(treeList);
		centerContent.setPadding(new Insets(10, 10, 10, 10));
		centerContent.getChildren().addAll(treeLabel, treeScroll);

		//RIGHT
		Label detailLabel = new Label("Member Details:");
		Label fName = new Label("First Name: ");
		Label bLName = new Label("Surname(Before Marriage):");
		Label aLName = new Label("Surname(After Marriage):");
		Label gender = new Label("Gender: ");
		Label addressSNum = new Label("Street Number: ");  
	    Label addressSName = new Label("Street Name: ");      
	    Label addressSub = new Label("Suburb: "); 
	    Label addressPcode = new Label("Postcode: ");
	    Label description = new Label("Description: ");
	    Label addMember = new Label("Add Family Member: "); 
	    Label mother = new Label("Mother: ");
	    Label father = new Label("Father: ");
	    Label spouse = new Label("Spouse: ");
	    Label children = new Label("Children: ");
		rightContent.setPadding(new Insets(10, 10, 10, 10)); 
		rightContent.setVgap(1);
		detailLabel.setId("bold-label");
		addMember.setId("bold-label");
		rightContent.add(detailLabel, 0, 0); //first column, first row
		rightContent.add(fName, 0, 1);
		rightContent.add(bLName, 0, 2);
		rightContent.add(aLName, 0, 3);
		rightContent.add(gender, 0, 4);
		rightContent.add(addressSNum, 0, 5);    
	    rightContent.add(addressSName, 0, 6); 
	    rightContent.add(addressSub, 0, 7);
	    rightContent.add(addressPcode, 0, 8);
	    rightContent.add(description, 0, 9);
	    rightContent.add(addMember, 0, 11);
	    rightContent.add(father, 0, 12);
	    rightContent.add(mother, 0, 13);
	    rightContent.add(spouse, 0, 14);
	    rightContent.add(children, 0, 15);
	    
	    rightContent.add(fNameTxt, 1, 1);	fNameTxt.setId("text-input-field");
	    rightContent.add(sBNameTxt, 1, 2);	sBNameTxt.setId("text-input-field");
	    rightContent.add(sMNameTxt, 1, 3);	sMNameTxt.setId("text-input-field");
	    genderBox.getItems().addAll("MALE", "FEMALE");
	    rightContent.add(genderBox, 1, 4); 
	    rightContent.add(addressSNumTxt, 1, 5);		addressSNumTxt.setId("text-input-field");
	    rightContent.add(addressSNameTxt, 1, 6);	addressSNameTxt.setId("text-input-field");
	    rightContent.add(addressSubTxt, 1, 7);		addressSubTxt.setId("text-input-field");
	    rightContent.add(addressPcodeTxt, 1, 8);	addressPcodeTxt.setId("text-input-field");
	    rightContent.add(descriptionTxt, 1, 9);		descriptionTxt.setId("text-input-field");
	    rightContent.add(fatherTxt, 1, 12);			fatherTxt.setId("text-input-field");
	    rightContent.add(motherTxt, 1, 13);			motherTxt.setId("text-input-field");
	    rightContent.add(spouseTxt, 1, 14); 		spouseTxt.setId("text-input-field");
	    ScrollPane childrenScroll = new ScrollPane(childrenTxt);
	    rightContent.add(childrenScroll, 1, 15); 	childrenScroll.setId("text-input-field");
	    
	    motherTxt.setEditable(false);
	    fatherTxt.setEditable(false);
	    spouseTxt.setEditable(false);
	    childrenTxt.setEditable(false);
	    ////right buttons
	    rightContent.add(addFather, 2, 12);		addFather.setId("right-buttons");
	    rightContent.add(addMother, 2, 13);		addMother.setId("right-buttons");
	    rightContent.add(addSpouse, 2, 14);		addSpouse.setId("right-buttons");
	    rightContent.add(addChild, 2, 15);		addChild.setId("right-buttons");
	    rightContent.add(deleteFather, 3, 12);	deleteFather.setId("right-buttons");
	    rightContent.add(deleteMother, 3, 13);	deleteMother.setId("right-buttons");
	    rightContent.add(deleteSpouse, 3, 14);	deleteSpouse.setId("right-buttons");
	    rightContent.add(deleteChild, 3, 15);	deleteChild.setId("right-buttons");
	    rightContent.add(update, 2, 10);			update.setId("right-buttons");
	    
	    /**
		 * @purpose open add father member window, upon button click event
		 * */
	    addFather.setOnAction(e -> {
	    	openAddMember("Father");
	    });
	    
	    /**
		 * @purpose open add mother member window, upon button click event
		 * */
	    addMother.setOnAction(e -> {
	    	openAddMember("Mother");
	    });
	    
	    /**
		 * @purpose open add spouse member window, upon button click event
		 * */
	    addSpouse.setOnAction(e -> {
	    	openAddMember("Spouse");
	    });
	    
	    /**
		 * @purpose open add child member window, upon button click event
		 * */
	    addChild.setOnAction(e -> {
	    	openAddMember("Child");
	    });
	    
	    /**
		 * @purpose delete a member, father
		 * */
	    deleteFather.setOnAction(e -> {
	    	deleteMember(Relationship.FATHER);
	    });
	    
	    /**
		 * @purpose delete a member, mother
		 * */
	    deleteMother.setOnAction(e -> {
	    	deleteMember(Relationship.MOTHER);
	    });
	    
	    /**
		 * @purpose delete a member, spouse
		 * */
	    deleteSpouse.setOnAction(e -> {
	    	deleteMember(Relationship.SPOUSE);
	    });
	    
	    /**
		 * @purpose delete a member, child
		 * */
	    deleteChild.setOnAction(e -> {
	    	deleteMember(Relationship.CHILD);
	    });
	    
	    /**
		 * @purpose update member data, upon button click event
		 * */
	    update.setOnAction(e -> {
	    	updateMember(familyTree.getFamilyMember(membersList.getSelectionModel().getSelectedIndex()));
	    });
	    
	    //ADD MEMBER WINDOW
	    GridPane AMLayout = new GridPane();
		AMLayout.setPadding(new Insets(10, 10, 10, 10)); 
		Label AMdetailLabel = new Label("Member Details:");
		Label AMaddress = new Label("Address Details"); 
		Label AMFName = new Label("First Name: ");
	    Label AMBLastname = new Label("Surname  (before marriage): ");
	    Label AMALastname = new Label("Surname (after marriage): ");
	    Label AMgender = new Label("Gender: ");
	    Label AMaddressSNum = new Label("Street Number: ");  
	    Label AMaddressSName = new Label("Street Name: ");       
	    Label AMaddressSub = new Label("Suburb: "); 
	    Label AMaddressPcode = new Label("Postcode: ");  
	    Label AMdescription = new Label("Description: ");
		AMLayout.add(AMdetailLabel, 0, 0); //first column, first row
		AMLayout.add(AMFName, 0, 1);
		AMLayout.add(AMBLastname, 0, 2);
		AMLayout.add(AMALastname, 0, 3);
		AMLayout.add(AMgender, 0, 4);
		AMLayout.add(AMaddress, 0, 5);   
		AMLayout.add(AMaddressSNum, 0, 6);     
	    AMLayout.add(AMaddressSName, 0, 7); 
	    AMLayout.add(AMaddressSub, 0, 8);
	    AMLayout.add(AMaddressPcode, 0, 9);
	    AMLayout.add(AMdescription, 0, 10);
		
	    AMLayout.add(AMfNameTxt, 1, 1);
	    AMLayout.add(AMsBNameTxt, 1, 2);
	    AMLayout.add(AMsMNameTxt, 1, 3);
	    AMgenderBox.getItems().addAll("Female", "Male");
	    AMLayout.add(AMgenderBox, 1, 4);  
	    AMLayout.add(AMaddressSNumTxt, 1, 6); 
	    AMLayout.add(AMaddressSNameTxt, 1, 7);
	    AMLayout.add(AMaddressSubTxt, 1, 8);
	    AMLayout.add(AMaddressPcodeTxt, 1, 9);
	    AMLayout.add(AMdescriptionTxt, 1, 10);

	    AMLayout.add(saveMember, 2, 15);
	    AMLayout.add(cancelMember, 3, 15);
	    
	    Scene addNewMemberScene = new Scene(AMLayout);
	    addNewMember.setTitle("Add New Member");
	    addNewMember.setScene(addNewMemberScene);
	    
	    cancelMember.setOnAction(e ->{
	    	closeAddMember();
	    });

	    //Add content to the main pane
		root.setTop(topContent);
		root.setLeft(leftContent);
		root.setCenter(centerContent);
		root.setRight(rightContent);
		root.setBottom(bottomContent);
		
		//build scene
		Scene uiContainer = new Scene(root, 1300, 640);
		uiContainer.getStylesheets().add("FamilyTreeMakerStyle.css");//add style
		topView.setScene(uiContainer);
		
        //initial mode is set to view mode
        switchMode();

    }
    
    /**
     * @purpose main entry point for this javafx application
     * @exception file not found exception
     * @param the top level javafx container 
     * */
	@Override
	public void start(Stage primaryStage) {

		createUI(primaryStage);
		createDummyData();
		primaryStage.show();
 	   	
	}//end of start()

//===================================================================================================
// GUI METHODS
//===================================================================================================
	
	/**
	 * @purpose open the add member window
	 * @param type of the member
	 * */
	private void openAddMember(String type)
    {        
		addNewMember.setTitle("Add New " + type);
		AMTitle.setText("Add New " + type);
		saveMember.setOnAction(e -> {
        	saveAddMember(type);
        });
        
		addNewMember.showAndWait();
    }
	
	/**
	 * @purpose close the add member window
	 * */
	private void closeAddMember() {
		clearAMField(); 
		addNewMember.close();
	}
	
	/**
	 * @purpose check if any details field is empty
	 * @return true only if all fields are filled
	 * */
	private boolean checkEmptyDetailsField() {
		if(fNameTxt.getText().trim().isEmpty()){return false;}
        if(sBNameTxt.getText().trim().isEmpty()){return false;}
        if(addressSNumTxt.getText().trim().isEmpty()){return false;}
        if(addressSNameTxt.getText().trim().isEmpty()){return false;}
        if(addressSubTxt.getText().trim().isEmpty()){return false;}
        if(addressPcodeTxt.getText().trim().isEmpty()){return false;}
        return !descriptionTxt.getText().trim().isEmpty();
	}
	
	/**
	 * @return true if all details field our filled out correctly
	 * */
	private boolean checkValidDetailsField() {
		boolean isValid = false;
		if(isValidName(fNameTxt.getText()) && isValidName(sBNameTxt.getText()) &&
			isValidName(addressSNameTxt.getText()) && isValidName(addressSubTxt.getText())&&
			isValidNumber(addressSNumTxt.getText()) && isValidNumber(addressPcodeTxt.getText())) {
			isValid = true;
		}
		return isValid;		
	}

	
	/**
	 * @purpose check if any add member field is empty
	 * @return true only if all fields are filled
	 * */
	private boolean checkEmptyAMField() {
		if(AMfNameTxt.getText().trim().isEmpty()){return false;}
        if(AMsBNameTxt.getText().trim().isEmpty()){return false;}
        if(AMaddressSNumTxt.getText().trim().isEmpty()){return false;}
        if(AMaddressSNameTxt.getText().trim().isEmpty()){return false;}
        if(AMaddressSubTxt.getText().trim().isEmpty()){return false;}
        if(AMaddressPcodeTxt.getText().trim().isEmpty()){return false;}
        return !AMdescriptionTxt.getText().trim().isEmpty();
	}
	
	/**
	 * @purpose check if add member field is filled out correctly
	 * @return true only if all fields are filled correctly
	 * */
	private boolean checkValidAMField() {
		boolean isValid = false;
		if(isValidName(AMfNameTxt.getText()) && isValidName(AMsBNameTxt.getText()) &&
			isValidName(AMaddressSNameTxt.getText()) && isValidName(AMaddressSubTxt.getText())&&
			isValidNumber(AMaddressSNumTxt.getText()) && isValidNumber(AMaddressPcodeTxt.getText())) {
			isValid = true;
		}
		return isValid;		
	}
	
	
	/**
	 * @purpose clears all add member field
	 * */
	private void clearAMField() {
		AMfNameTxt.setText("");
		AMsBNameTxt.setText("");
		AMsMNameTxt.setText("");
		AMgenderBox.valueProperty().set(null);
		AMaddressTxt.setText("");  
		AMaddressSNumTxt.setText("");
		AMaddressSNameTxt.setText("");
		AMaddressSubTxt.setText(""); 
		AMaddressPcodeTxt.setText(""); 
		AMdescriptionTxt.setText("");
	}
	
	/**
	 * @purpose clears all details field
	 * */
	private void clearDetailField() {
		fNameTxt.setText("");
		sBNameTxt.setText("");
		sMNameTxt.setText("");
		genderBox.valueProperty().set(null);
		addressSNumTxt.setText("");
		addressSNameTxt.setText("");
		addressSubTxt.setText(""); 
		addressPcodeTxt.setText(""); 
		descriptionTxt.setText("");
		motherTxt.setText("");
		fatherTxt.setText("");
		spouseTxt.setText("");
		childrenTxt.setText("");
	}
	
	/**
	 * @purpose allow or disallow altering details of a member
	 * @param on or off status
	 * */
	private void allowDisallowAlter(boolean onoff) {
		fNameTxt.setEditable(onoff);
		sBNameTxt.setEditable(onoff);
        sMNameTxt.setEditable(onoff);
        genderBox.setDisable(!onoff);
        addressSNumTxt.setEditable(onoff); 
        addressSNameTxt.setEditable(onoff); 
        addressSubTxt.setEditable(onoff); 
        addressPcodeTxt.setEditable(onoff); 
        descriptionTxt.setEditable(onoff); 

        addChild.setDisable(!onoff);
        addSpouse.setDisable(!onoff);
        addMother.setDisable(!onoff);
        addFather.setDisable(!onoff);
        deleteChild.setDisable(!onoff);
        deleteSpouse.setDisable(!onoff);
        deleteMother.setDisable(!onoff);
        deleteFather.setDisable(!onoff);
        update.setDisable(!onoff);
	}

	/**
	 * @purpose display basic details in details panel on the right
	 * @param selected member from the membersList on the left
	 * */
	private void setBasicDetails(FamilyMember member) {
		fNameTxt.setText(member.getFirstName());
		sBNameTxt.setText(member.getBirthLastName());
		sMNameTxt.setText(member.getMarryLastName());
		
		if(member.getGender().equals("MALE")) {
			genderBox.getSelectionModel().select("MALE");
		}
		else {
			genderBox.getSelectionModel().select("FEMALE");
		}
		
		addressSNumTxt.setText(member.getAddress().getStreetNumber()); 
		addressSNameTxt.setText(member.getAddress().getStreetName()); 
		addressSubTxt.setText(member.getAddress().getSuburb()); 
		addressPcodeTxt.setText(member.getAddress().getPostcode());
		descriptionTxt.setText(member.getDescription());
	}
	
	/**
	 * @purpose set and display parent details in details panel on the right
	 * @param selected member from the membersList on the left
	 * */
	private void setParentDetails(FamilyMember member) {
		if(member.getFather()!=null) {
        	fatherTxt.setText(member.getFather().getFullName());
        }else {
        	fatherTxt.setText("");
        }
        if(member.getMother()!=null) {
        	motherTxt.setText(member.getMother().getFullName());
        }else {
        	motherTxt.setText("");
        }
		//if the member has mother and father, mother and father refer each other as spouse
    	//the parents refer myself as a child
        member.setParentRelationship();
	}
	
	/**
	 * @purpose set and display spouse details in details panel on the right
	 * @param selected member from the membersList on the left
	 * */
	private void setSpouseDetails(FamilyMember member) {
		//set spouse and refer each other as spouse
        if(member.getSpouse()!=null) {
        	String spouseName = member.getSpouse().getFullName();
        	spouseTxt.setText(spouseName);
        	member.setSpouseRelationship();
        }else {
        	spouseTxt.setText("");
        }
	}
	
	/**
	 * @purpose set and display a child details in details panel on the right
	 * @param selected member from the membersList on the left
	 * */
	private void setChildDetails(FamilyMember member) {
		//set child
        String childrenName="";
        
        member.setChildrenRelationship();
        for(int i=0; i<member.getChildren().size(); ++i) {
        	FamilyMember child = member.getChildren().get(i);
        	childrenName += (child.getFullName() + "\n");
        }
        childrenTxt.setText(childrenName);
	}
	
	/**
	 * @purpose update details panel according to the selected family member from the list
	 * @param selected member from the member list on the left
	 * */
	private void updateDetailsPanel(FamilyMember member) {
		clearDetailField();
		setBasicDetails(member);
		setParentDetails(member);
    	setSpouseDetails(member);
    	setChildDetails(member);       
	}
	
	/**
	 * @purpose save adding a new member and update family list accordingly
	 * @param type of family relationship
	 * */
	private void saveAddMember(String type) {
		if(checkEmptyAMField() && checkValidAMField()) {
			
			FamilyMember newMember = new FamilyMember(AMfNameTxt.getText(),
										AMsBNameTxt.getText(),
										AMsMNameTxt.getText(),
										AMgenderBox.getValue(),
										new Address(AMaddressSNumTxt.getText(),
													AMaddressSNameTxt.getText(),
													AMaddressSubTxt.getText(),
													AMaddressPcodeTxt.getText()),
										AMdescriptionTxt.getText());
			
			if(type.equals("Family")) {
				//reset to clean slate before loading a new family
                familyTree.removeAllMembers();
                if(!memberData.isEmpty())
                	memberData.removeAll(memberData);
                
                //now add the base member in the new family tree
				familyTree = new FamilyTree(newMember);
				view.setDisable(true);
				
				
			}else {
				familyTree.addFamilyMember(newMember, type, membersList.getSelectionModel().getSelectedIndex());
			}
			closeAddMember(); //close the Add Member window
			clearAMField(); //clean Add Member fields
			updateFamilyList(); //update left pane

		}else {
			Alert fillInAlert  = new Alert(AlertType.WARNING, "Please fill in all field correctly", ButtonType.CLOSE);
			fillInAlert.show();			
		}
	}
	
	/**
	 * @purpose update member details in the family list and the details panel
	 * @param a family member
	 * */
	private void updateMember(FamilyMember member) {
		
		if(checkEmptyDetailsField() && checkValidDetailsField()) {
			//set member data
			member.setFirstName(fNameTxt.getText());
			member.setBirthLastName(sBNameTxt.getText());
			member.setMarryLastName(sMNameTxt.getText());
			member.setGender(genderBox.getSelectionModel().getSelectedItem().toString());
			member.setAddress(new Address(addressSNumTxt.getText(),
										addressSNameTxt.getText(),
										addressSubTxt.getText(),
										addressPcodeTxt.getText() ));
			member.setDescription(descriptionTxt.getText());
			//update family list
			updateFamilyList();
			//update details panel
			updateDetailsPanel(familyTree.getFamilyMember(membersList.getSelectionModel().getSelectedIndex()));
			
			Alert updateSuccess  = new Alert(AlertType.INFORMATION, "Successfully updated!", ButtonType.CLOSE);
			updateSuccess.show();
		
		}else {
			Alert emptyField  = new Alert(AlertType.INFORMATION, "Please fill in all field correctly", ButtonType.CLOSE);
			emptyField.show();
		}
	}
	
	/**
	 * @purpose switch to view or edit mode
	 * */
	private void switchMode() {

		if(isViewMode) {
			if(!memberData.isEmpty()) {//switch to edit mode
				isViewMode = false;
				view.setDisable(false);
				edit.setDisable(true);

				newTree.setDisable(true);
				loadTree.setDisable(true);

				allowDisallowAlter(!isViewMode);
			}else {
				Alert noFamilyLoaded  = new Alert(AlertType.INFORMATION, "No Family Tree Loaded", ButtonType.CLOSE);
				noFamilyLoaded.show();
			}
		}else {//switch to view mode
			isViewMode = true;
			view.setDisable(true);
			edit.setDisable(false);

			newTree.setDisable(false);
			loadTree.setDisable(false);

			allowDisallowAlter(!isViewMode);
		}
	}
	
	/**
	 * @purpose save family tree to a file
	 * */
	private void saveTree() {
		if(familyTree != null) {

	        TextInputDialog dialog = new TextInputDialog();
	        dialog.setHeaderText("Save your family tree");
	        dialog.setTitle("New Family Name");
	        dialog.setContentText("Family Name: ");
	        String filename="";
	        Optional<String> result = dialog.showAndWait();
	        if(result.isPresent())
	        {
	        	filename = result.get();
	        }
	        
	        FileOutputStream fos;
	        ObjectOutputStream oos;
	        if(!filename.trim().equals("")) {
	        	try {
	        		familyName = filename;
	        		File ffile = new File("family");
	        		if(ffile.exists()) {
	        			if(ffile.mkdir()){ 
	        				logger.log(Level.INFO, "File directory created");
	        			} else{
	        				logger.log(Level.INFO, "File directory already exists");
	        			}
	        			fos = new FileOutputStream("family/"+filename+".ftree");
	        			oos = new ObjectOutputStream(fos);
	        			oos.writeObject(familyTree);
	        			
	        			fos.close();
	        			infoAlert("Family tree is saved");
	        		}
	        	}
	        	catch(FileNotFoundException ex)
                {
	        		errorAlert("File not found!", ex);
                }
	        	catch(IOException ex)
                {   
                	errorAlert("IOException occured!", ex);
                }
	        }
	        else {
	        	warningAlert("Type a family name");
	        }
		} else {
			warningAlert("No family to save!");
		}
	}
	
	/**
	 * @purpose load the family tree object from a '.ftree' file
	 * */
	private void loadTree() {
		String path;
		FileInputStream fis;
        ObjectInputStream ois;
        
        FileChooser fchooser = new FileChooser();
        fchooser.setInitialDirectory(new File(System.getProperty("user.dir")+"/family"));
        ExtensionFilter treeFilter= new ExtensionFilter("Family Tree Files","*.ftree");
        fchooser.getExtensionFilters().add(treeFilter);
        File chosenFile = fchooser.showOpenDialog(window);
        if(chosenFile != null) {
        	path = chosenFile.getPath();
        	
        	try {
        		
        		familyName = chosenFile.getName().replace(".ftree","");
                fis = new FileInputStream(path);
                ois = new ObjectInputStream(fis);
                
                //reset to clean slate before loading a new family
                familyTree.removeAllMembers();
                if(!memberData.isEmpty())
                	memberData.removeAll(memberData);
                
                familyTree = (FamilyTree)ois.readObject();
                ois.close();

                updateFamilyList();
                edit.setDisable(false);
                switchMode(); //edit mode
                switchMode(); //view mode (set view mode as a default mode)
        	}
        	catch(FileNotFoundException ex)
            {
            	errorAlert("The file not found!", ex);
            }
            catch(IOException ex)
            {           
            	errorAlert("Problem in loading family!", ex);
            } 
            catch (ClassNotFoundException ex) 
            {
            	errorAlert("Class not found!", ex);
            }
        }
	}
	
	/**
	 * @purpose display the tree in a simple list form
	 * @param index position of the family tree
	 * */
	private void showTree(int index) {
		treeData.clear();
		
		if(familyTree == null)
			return;
		
		if(familyTree.getFamilySize()>0) {
		FamilyMember base = familyTree.getFamilyMember(index);
		
			if(base.getMother() != null) {
				treeData.add("Mother: " + base.getMother().getFullName());
			}
			if(base.getFather() != null) {
				treeData.add("Father: " + base.getFather().getFullName());
			}
			treeData.add("	Self: " + base.getFullName());
			if(base.getSpouse() != null) {
				treeData.add("	Spouse: " + base.getSpouse().getFullName());
			}
			if(base.getChildren().size()>0) {
				for(int i=0; i<base.getChildren().size(); ++i) {
					FamilyMember child = base.getChildren().get(i);
					treeData.add("		Child " + (i+1) +": " + child.getFullName());
					
					if(child.getChildren().size()>0) {
						for(int j=0; j<child.getChildren().size(); ++j) {
							treeData.add("			Grandchild "+(j+1) +": " + child.getChildren().get(j).getFullName());
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * @purpose update the list of family members in the left pane
	 * */
	private void updateFamilyList() {
		if(familyTree.getFamilySize()>0) {
			int selectedIdx = membersList.getSelectionModel().getSelectedIndex();
			
			for(int i=0; i<familyTree.getFamilySize(); ++i) {			
				//check duplicate
				if(!isDuplicateEntry(i)) {
					if(memberData.size()<=i) {
						memberData.add(familyTree.getFamilyMember(i).getFullName());
	                }
	                else {
	                	memberData.set(i, familyTree.getFamilyMember(i).getFullName());       
	                }
				}
			}//end of for loop

            if(selectedIdx<0) {
            	selectedIdx=0;   
            }
            membersList.getSelectionModel().selectIndices(selectedIdx);
            showTree(selectedIdx);
            updateDetailsPanel(familyTree.getFamilyMember(selectedIdx));
			
		}
	}
	
	
	/**
	 * @param the relationship type of the member to be removed
	 * @return true if successfully deleted the member of the type
	 * */
	private boolean deleteMember(Relationship type) {
		
		FamilyMember self = familyTree.getFamilyMember(membersList.getSelectionModel().getSelectedIndex());
		
		if(type==Relationship.FATHER) {
			FamilyMember father = self.getFather();
			if(father != null) {
				deleteMemberRecord(father.getFullName());
		    	self.removeParent(father);
	    	}
	    	else {
	    		warningAlert("Father data empty!");
	    		return false;
	    	}
		}
		
		if(type==Relationship.MOTHER) {
			FamilyMember mother = self.getMother();
			if(mother != null) {
				deleteMemberRecord(mother.getFullName());
				self.removeParent(mother);
	    	}
	    	else {
	    		warningAlert("Mother data empty!");
	    		return false;
	    	}
		}
		
		if(type==Relationship.SPOUSE) {
			FamilyMember spouse = self.getSpouse();
			if(spouse != null) {
				deleteMemberRecord(spouse.getFullName());
		    	self.removeSpouse(spouse);
	    	}
	    	else {
	    		warningAlert("Spouse data empty!");
	    		return false;
	    	}
		}
		
		if(type==Relationship.CHILD) {
			ArrayList <FamilyMember> children = self.getChildren();
			if(children != null) {
				deleteChildWindow(self);
	    	}
	    	else {
	    		warningAlert("Child data empty!");
	    		return false;
	    	}
		}
    	
		//reflect changes to members list on the left pane
		membersList.setItems(memberData);
		//reflect changes to details panel
		updateDetailsPanel(self);
		//reflect changes to tree display area
    	showTree(membersList.getSelectionModel().getSelectedIndex());
		return true;
	}
	
	/**
	 * @purpose a separate window will appear to get user's selection on whom to delete
	 * @param a self, the children of this member will be referred for deletion
	 * */
	private void deleteChildWindow(FamilyMember self) {
		
		GridPane deleteChildLayout = new GridPane();
		deleteChildLayout.setPadding(new Insets(10, 10, 10, 10)); 
		Label deleteChildLabel = new Label("Select Member to delete: ");
		deleteChildSelectButton  = new Button("Select");
		//drop down list
		childChoice = new ChoiceBox<>();
		ArrayList<FamilyMember> children = self.getChildren();
		for(FamilyMember child : children) {
			childChoice.getItems().add(child.getFullName());
		}
				
		deleteChildSelectButton.setOnAction(e-> deleteSelectedChild(self, childChoice.getValue()));
		
		deleteChildLayout.add(deleteChildLabel, 0, 0);
		deleteChildLayout.add(childChoice, 0, 1);
		deleteChildLayout.add(deleteChildSelectButton, 1, 1);
		Scene deleteChildScene = new Scene(deleteChildLayout);
	    
	    deleteChildStage.setScene(deleteChildScene);
		deleteChildStage.showAndWait();
	}
	
	
//===================================================================================================
// HELPER METHODS
//===================================================================================================
	
	/**
	 * @purpose check if the family member is being entered again
	 * @return true if it is a duplicate entry
	 * */
	private boolean isDuplicateEntry(int index) {
		for(int j=0; j<memberData.size(); ++j) {
			if(memberData.get(j).equalsIgnoreCase(familyTree.getFamilyMember(index).getFullName())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @purpose remove the member by name from member data
	 * @return true if the member is successfully removed
	 * */
	private boolean deleteMemberDataByName(String name) {
		for(int j=0; j<memberData.size(); ++j) {
			if(memberData.get(j).equalsIgnoreCase(name)) {
				memberData.remove(j);
				return true;
			}
		}
		return false;
	}

	/**
	 * @purpose it alerts user that the error has occurred and logs the event
	 * @param a simple error description and an exception
	 * */
	private void errorAlert(String errorDescription, Exception ex) {
		Alert errorAlert  = new Alert(AlertType.ERROR, errorDescription, ButtonType.CLOSE);
		errorAlert.show();
    	logger.log(Level.SEVERE, "Error detail: " + ex);
	}
	
	/**
	 * @purpose a alert window warns a user
	 * @param a simple warning description
	 * */
	private void warningAlert(String warningDescription) {
		Alert warningAlert  = new Alert(AlertType.WARNING, warningDescription, ButtonType.CLOSE);
		warningAlert.show();
	}
	
	/**
	 * @purpose a alert window informs a user
	 * @param a simple information description
	 * */
	private void infoAlert(String infoDescription) {
		Alert infoAlert  = new Alert(AlertType.INFORMATION, infoDescription, ButtonType.CLOSE);
		infoAlert.show();
	}
	
	/**
	 * @purpose deletes member's record in member data list and family tree list
	 * @param member's full name
	 * */
	private void deleteMemberRecord(String memberFullName) {
		deleteMemberDataByName(memberFullName);
		familyTree.removeFamilyMemberByName(memberFullName);
	}
	
	/**
	 * @purpose the selected child from the choice box will be deleted
	 * @param a self, a choice box to select a child's name
	 * */
	private void deleteSelectedChild(FamilyMember self, String childName) {
	
		FamilyMember selectedChild = familyTree.getFamilyMemberByName(childName);
		
		//if the child has children do not delete the child
		ArrayList<FamilyMember> grandChildren = selectedChild.getChildren();
		if(grandChildren.size()!=0) {
			Alert typeNameAlert  = new Alert(AlertType.WARNING, "You can't delete a child who has children", ButtonType.CLOSE);
        	typeNameAlert.show();
			return;
		}

		//remove GUI components before actual data removal
		FamilyMember selectedChildSpouse = selectedChild.getSpouse();
		if(selectedChildSpouse!=null) {
			deleteMemberRecord(selectedChildSpouse.getFullName());
		}
		
		deleteMemberRecord(childName);
		self.removeChildRelationship(selectedChild);

		deleteChildStage.close();
	}
	
	/**
	 * @param a name
	 * @return true if the user input name is in a valid format
	 * */
	private boolean isValidName(String name) {
		boolean isValid = name.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");
		return isValid;
	}
	
	/**
	 * @param a number
	 * @return true if the user input number is in a valid format
	 * */
	private boolean isValidNumber(String number) {
		try {
	        int num = Integer.parseInt(number);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
		return true;
	}
	
	/**
	 * @purpose it creates dummy data
	 * */
	private void createDummyData() {
		
		FamilyMember mother = new FamilyMember("Mona", "Olsen", "Simpson", "FEMALE",
										new Address("2", "Unknown Street", "Unknown Town", "11123"),
										"I am the mother of Homer Simpson!");	
		FamilyMember father = new FamilyMember("Abraham II", "Simpson", "Simpson", "MALE",
										new Address("2", "Unknown Street", "Unknown Town", "11123"),
										"I am the father of Homer Simpson!");		
		FamilyMember self = new FamilyMember("Homer", "Simpson", "Simpson", "MALE",
										new Address("2", "Home Street", "Springfield", "12333"),
										"I am the father of Bart Simpson!");		
		FamilyMember spouse = new FamilyMember("Margie", "Bouvier", "Simpson", "FEMALE",
										new Address("2", "Home Street", "Springfield", "12333"),
										"I have a beautiful voice");		
		FamilyMember son = new FamilyMember("Bart", "Simpson", "", "MALE",
										new Address("2", "Home Street", "Springfield", "12333"),
										"Aye Caramba!");
		FamilyMember daughter1 = new FamilyMember("Lisa", "Simpson", "", "FEMALE",
										new Address("2", "Home Street", "Springfield", "12333"),
										"I am smart!");
		FamilyMember daughter2 = new FamilyMember("Maggie", "Simpson", "", "FEMALE",
										new Address("2", "Home Street", "Springfield", "12333"),
										"The youngest family member");
								
		familyTree = new FamilyTree(self);
		familyTree.addFamilyMember(spouse, "Spouse", 0);
		familyTree.addFamilyMember(mother, "Mother", 0);
		familyTree.addFamilyMember(father, "Father", 0);
		familyTree.addFamilyMember(son, "Child", 0);
		familyTree.addFamilyMember(daughter1, "Child", 0);
		familyTree.addFamilyMember(daughter2, "Child", 0);
		updateFamilyList();
		
	}
	
}//end of class