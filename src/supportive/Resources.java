/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supportive;

import java.io.IOException;
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
import supportive.Resources;    //  User-defined package
/**
 *
 * @author Prasad-pc
 */
public class Resources {
	public static final String DIR = "F:\\Prasad\\Projects\\Java Side Project\\";		//	This the director where the files will be stored
	public static final String REMFILE = DIR + "reminderQueue.rmi";
	public static final String sherlock = DIR + "sherlock.mp3";	//	Alarm audio mp3 url
	public static final String sher8 = DIR + "8bitsherlock.mp3";
	public static final String serverPath = "F:\\Prasad\\Projects\\WT Project\\ServerFiles\\";	//	This is the location where the files of the server are created.
	public static final int maxn = 105;
	public static void getch() throws IOException
	{
		char ch = (char)System.in.read();
	}
	
//	Function to display the string on screen. Used for debugging purpose
	public static void show(Object o){
		System.out.println(o);
	}
	
	public static void show(String str)
	{
		System.out.println(str);
	}
	
	public static void show(int i)
	{
		System.out.println(i);
	}
//	For displaying errors
	public static void showerr(Exception e)
	{
		System.err.println(e);
	}
	public static void guierr(String s, int error){
		Stage stage = new Stage();
		stage.setTitle("#error");
		Label label = new Label(s);
		Button ok = new Button("Ok");
		VBox root = new VBox();
		Scene scene = new Scene(root, 100, 70);
		root.getChildren().addAll(label, ok);
		stage.setScene(scene);
		stage.show();
		
		ok.setOnAction(e -> stage.close());		//	Close the error winndow
	}
	public static void guierr(String s){
		Stage stage = new Stage();
		stage.setTitle("#error");
		Label label = new Label(s);
		Button ok = new Button("Ok");
		VBox root = new VBox();
		Scene scene = new Scene(root, 100, 70);
		root.getChildren().addAll(label, ok);
		stage.setScene(scene);
		stage.show();
		
		ok.setOnAction(e -> stage.close());		//	Close the error winndow
	}
}

//	This class is used in non-static method blocks and to reduce the typing for debugging
class R{	//	Make it public for import purpose
	public static final String DIR = "F:\\Prasad\\Projects\\Java Side Project\\";		//	This the director where the files will be stored
	public static final String REMFILE = DIR + "reminderQueue" + ".rmi";
	public static final String sherlock = "F:\\Prasad\\Projects\\Java Side Project\\sherlock.mp3";	//	Alarm audio mp3 url
	public static final String sher8 = "F:\\Prasad\\Projects\\Java Side Project\\8bitsherlock.mp3";
	public static final int maxn = 105;
	
	public void getch(){//	Exception is handled within the metthod
		try{
			char ch = (char)System.in.read();
		}catch(IOException ioe){
			System.out.println("Exception in getch() in class R(supportive)");
		}
	}
	public static void show(String str){
		System.out.println(str);
	}
	public void show(int i){
		System.out.println(i);
	}
	public static void showerr(Exception e){
		System.err.println(e);
	}
	public void guierr(String s, int error){
		Stage stage = new Stage();
		stage.setTitle("#error");
		Label label = new Label(s);
		Button ok = new Button("Ok");
		VBox root = new VBox();
		Scene scene = new Scene(root, 100, 70);
		root.getChildren().addAll(label, ok);
		stage.setScene(scene);
		stage.show();
		
		ok.setOnAction(e -> stage.close());		//	Close the error winndow
	}
	public void guierr(String s){
		Stage stage = new Stage();
		stage.setTitle("#error");
		Label label = new Label(s);
		Button ok = new Button("Ok");
		VBox root = new VBox();
		Scene scene = new Scene(root, 100, 70);
		root.getChildren().addAll(label, ok);
		stage.setScene(scene);
		stage.show();
		
		ok.setOnAction(e -> stage.close());		//	Close the error winndow
	}
}