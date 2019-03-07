/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Prasad-pc
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
import personalassistant.*;

public class NewProcess{
	public static void main(String args[]){
		try{
			String cmd = "C:\\Users\\pranav-pc\\Documents\\Visual Studio 2015\\Projects\\GUI\\GUI\\bin\\Debug\\GUI.exe";
			//	String cmd = "F:\\Prasad\\Projects\\Banking\\main.exe";
			
			Runtime run = Runtime.getRuntime();
			Process p = run.exec(cmd);
		}catch(IOException ioe){
			Resources.show("IOException caught");
		}
	}
}
