/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personalassistant;
import java.lang.reflect.InvocationTargetException;
import supportive.Resources;
import supportive.Resources.*;
import reminder.*;
////////////////////////////////////////////////////


import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.*;
import javafx.scene.text.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.event.*;
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
import java.lang.*;
import java.time.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.*;
import java.time.format.*;
import java.time.format.*;
import java.time.chrono.*;
import java.util.*;
import java.nio.file.*;
import java.nio.channels.*;
import java.nio.*;
import supportive.Resources;    //  User-defined package
import circularlinkedlist.*;
import reminder.*;
import scheduler.*;
/**
 *
 * @author Prasad-pc
 */
public class Portal extends Application implements Runnable{
	public Thread t;
	Reminder rem;
	public Portal(Reminder rem){
		this.rem = rem;
		t = new Thread(this, "portal");
		Resources.show("Portal thread created");
	}
	public Portal(){}
	
	@Override
	public void start(Stage stage){
		Resources.show("start from Portal");
		stage.setTitle("Personal Assistant");	//	Title of the application
		//	Reminder rem = new Reminder("In-Built");
		VBox root = new VBox();
		Text header = new Text("Your Personal Assistant!!!");
		Button infoButton = new Button("Add acquaintance");	//	Button to add/modify contact
		Button todoButton = new Button("TODO-List");	//	Takes you to the TODO-List window
		Button reminderButton = new Button("Set-Reminder(stops thread)");
		Button inputrembtn = new Button("Set Reminder(Initiates thread");
		Button quit = new Button("Exit Portal");	//	Button to quit the application
	//	infoButton event Listener
		//	notifyAll();
		infoButton.setOnAction(e -> {
		//	call the Directory class' putdetails function
			Directory directory = new Directory();
			directory.putDetails();	//	method of directory class that accepts the info
		});
	//	inputrembtn even listener
		inputrembtn.setOnAction(e -> {
			Reminder ob = new Reminder("Initiated");	//	A new thread will be started
		});
	//	reminderButton event Listener. This thread must start as soon as the application begins. This feature will be implemented afterwards
		reminderButton.setOnAction(e -> {	//	Here we suspend the currently executing thread, perform required operations and resume
			/*
			try{
				rem.wait(1000);
			}catch(InterruptedException ie){Resources.show("InterruptedException boy!!");}
			catch(Exception ee){Resources.show("Iske bahar kuch nahi jaa sakta");}
			*/
			//	rem.suspendThread();
			//	Resources.show("Hit enter and then I'll resume");
			InputReminder ir = new InputReminder();
			ir.input(rem);
			//	notifyAll();
		});
	//	quit button event listener
		quit.setOnAction(e -> System.exit(0));
		
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		root.setSpacing(5);
		root.getChildren().addAll(header, infoButton, todoButton, reminderButton, inputrembtn, quit);
		stage.show();	//	Makes the GUI visible
	}
	
	@Override
	public void run(){
		Resources.show("Yeh chalu ho gaya");
		Application.launch("hello");
		
		Resources.show("Portal thread exiting");
	}
}
