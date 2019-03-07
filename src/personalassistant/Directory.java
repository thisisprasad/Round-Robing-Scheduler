/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personalassistant;

/**
 *
 * @author user
 */

import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.text.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.event.*;
import java.io.*;
import supportive.Resources;

//  This class contain all the information needed to store of a person
class DOB
{
    int day;
    int month;
    int year;
}
        
public class Directory extends Resources implements Serializable
{
    enum gender
    {
        female, male
    }
    
    class SocialMediaLink
    {
        String facebook;
        String instagram;
        String snapchat;
        String whatsapp;    //  This is the whatsapp number
    }
    
    String dirName; //  Name of the directory
    String name;
    String email;
    DOB dob;
    String mobile;
    String phone;
    gender gen;
    int age;
    String designation;
    String salary;
    String company;
    boolean occupation; //  if true then own business else has a job
    float acquaintanceLevel; //  It can be changed manually. Based on information you have provided it will be precalculated
    String address;
    String state;
    String city;
    String town;
    String from;
    String relation;   
    
    SocialMediaLink socialNetworking;
    String interests;
    
    String comments;    //  My personal views on this person
	
    
    void putDetails(){
	/*
        This function will input the details of the person using a GUI.
        Write them to a file. The nodes are local to the function. 
    */
     // GUI code here. Create an independent window(scene) here
		Stage stage = new Stage();
		stage.setTitle("Info");
		//	VBox root = new VBox();	//	root node
		GridPane root = new GridPane();
	//	Creating the required labels, textFields and buttons
		Label nameLbl = new Label("Full name:");								TextField nameFld = new TextField();
		Label DOBLbl = new Label("DOB:");										DatePicker birthDate = new DatePicker();	//	Selecting the birthdate of the person
		Label emailLbl = new Label("Email:");									TextField emailFld = new TextField();
		Label mobileLbl = new Label("Mobile:");									TextField mobileFld = new TextField();
		Label genderLbl = new Label("Gender:");									TextField genderFld = new TextField("This control will changed to lsitview soon");
		Button finishButton = new Button("Finish");
		Button advancedButton = new Button("Advanced>>>");
	/////////////////////////////////////////////////////////////////////////////
	//	Event handlers of the buttons
		finishButton.setOnAction(e ->{
			//	writeDetailsInFile();	//	the entered details are wwritten into a file.	
			//	Will have to pass each and every node ti the funcction
		//	Writing contents into a file
			this.name = nameFld.getText();
		//	DOB code is not written yet
			this.email = emailFld.getText();
			this.mobile = mobileFld.getText();
		//	Gender has not been asssigned yet.
		/*
			Now create a file and wwrite this object into it. File name is the name of the person_gender_dob.txt
			the object is to be written
		*/
			File f = new File(Resources.DIR + name + ".dude");	//	File extension are kept as ".dude"
		//	Now the file is created, we use stream to open the file and write onbject into it. Using the try-with-resources block
			try(OutputStream fos = new FileOutputStream(f)){
				ObjectOutputStream oos = new ObjectOutputStream(fos);	//	Now the file is connected to a stream
				oos.writeObject(this);
				guierr("info successfully saved");
			}catch(IOException ioe){
				show("Exception caught"); showerr(ioe);
			}
	
		});
		advancedButton.setOnAction(e -> {
			Resources r = new Resources();
			r.guierr("To be implemented");
		});
		
		Scene scene = new Scene(root, 1000, 700);
		stage.setScene(scene);
		int row = 0;
		root.addRow(row, nameLbl, nameFld); row++;
		root.addRow(row, DOBLbl, birthDate); row++;
		root.addRow(row, emailLbl, emailFld); row++;
		root.addRow(row, mobileLbl, mobileFld); row++;
		root.addRow(row, genderLbl, genderFld); row++;
		root.addRow(row, finishButton, advancedButton);
		stage.show();
    }
    
    void getDetails(){
	/*
        This function displays the information of a person in user-friendly manner.
        Redirects to editing options.   
    */
		
	}
    
    //  Algorithm for acquaintanceLevel factor.
    void acquaintanceLavelAlgorithm()
    {
        
    }
    
}