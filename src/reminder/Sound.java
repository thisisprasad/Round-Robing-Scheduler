/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reminder;

//import javafx.application.Application;
//import javafx.application.Application;
//import javafx.stage.*;
//import javafx.scene.text.*;
//import javafx.scene.*;
//import javafx.scene.layout.*;
//import javafx.scene.*;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import javafx.scene.control.*;
//import javafx.scene.media.*;
//import javafx.scene.media.MediaPlayer.Status;
//	import javafx.event.*;
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
//	import javafx.application.Platform;
import java.net.URL;
import javax.sound.sampled.*;
import reminder.*;
import scheduler.*;
import personalassistant.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Prasad-pc
 */
public class Sound extends KeyAdapter{
	InputReminder ir;
	Clip clip;
	public void playSound(){
		//	this.ir = ir;
		JFrame frame = new JFrame("Alarm frame");
		frame.setSize(300, 175);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton btn = new JButton("Ok");
		frame.add(btn);
		frame.setVisible(true);
		AudioInputStream ais;
		String path = Resources.DIR + "sherlock.wav";
		try{
			ais = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
			clip = AudioSystem.getClip();
			
			//	Open audioInputStream
			clip.open(ais);
			
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
			//	Resources.getch();
			//	clip.stop();
			//	clip.close();
			btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				//	System.exit();
				clip.stop(); clip.close();
				frame.dispose();
				//	System.exit(0);
				//	return; 
			}
		});
			
		}catch(Exception e){
			Resources.show("line 70");
			Resources.showerr(e);
		}
		//	Application.launch("Why");
	}
	public void keyPressed(KeyEvent ke){
		clip.stop();; clip.close();
		return;	//	Lekin kaha jayega
	}
	
//	@Override
//	public void start(Stage stage){
//		Sound o = new Sound();
//		o.ir = new InputReminder();
//		o.ir.adate = LocalDate.now();
//		o.ir.ahour = o.ir.amin = o.ir.asec = 11;
//		o.ir.title = "Playing Audio";
//		o.ir.cmt = "Currently in the production state";
//		stage.setTitle(o.ir.title);
//		String path = Resources.sherlock;
//		URL mediaURL = getClass().getClassLoader().getResource(path);
//		String mediaStringURL = mediaURL.toExternalForm();
//		Media clip = new Media(mediaStringURL);
//		MediaPlayer player = new MediaPlayer(clip);
//		Label datelbl = new Label(o.ir.adate.toString());
//		Label timelbl = new Label(o.ir.ahour+":"+o.ir.amin+":"+o.ir.asec);
//		Label cmtlbl = new Label(o.ir.cmt);
//		Button dismissbtn = new Button("Dismiss");
//		VBox root = new VBox();
//		Scene scene = new Scene(root, 300, 500);
//		root.getChildren().addAll(datelbl, new Label(":- "), timelbl);
//		root.getChildren().add(cmtlbl);
//		root.getChildren().add(dismissbtn);
//		stage.setScene(scene);
//		stage.show();
/////////////////////////////////////////////////////////////////////////////////////
//		player.setAutoPlay(true);	//	Automatically begins playing the song
/////////////////////////////////////////////////////////////////////////////////////
//		dismissbtn.setOnAction(e -> {
//			if(player.getStatus() == MediaPlayer.Status.PLAYING){
//				player.stop();
//				Platform.exit();
//			}
//			else{
//				player.play();
//			}
//		});
//	}
	
//	public void alarm(InputReminder ir){
//		Stage stage = new Stage();
//		Sound o = new Sound();
//		o.ir = ir;
//		stage.setTitle(o.ir.title);
//		String path = Resources.sherlock;
//		URL mediaURL = getClass().getClassLoader().getResource(path);
//		String mediaStringURL = mediaURL.toExternalForm();
//		Media clip = new Media(mediaStringURL);
//		MediaPlayer player = new MediaPlayer(clip);
//		Label datelbl = new Label(o.ir.adate.toString());
//		Label timelbl = new Label(o.ir.ahour+":"+o.ir.amin+":"+o.ir.asec);
//		Label cmtlbl = new Label(o.ir.cmt);
//		Button dismissbtn = new Button("Dismiss");
//		VBox root = new VBox();
//		Scene scene = new Scene(root, 300, 500);
//		root.getChildren().addAll(datelbl, new Label(":- "), timelbl);
//		root.getChildren().add(cmtlbl);
//		root.getChildren().add(dismissbtn);
//		stage.setScene(scene);
//		stage.show();
/////////////////////////////////////////////////////////////////////////////////////
//		player.setAutoPlay(true);	//	Automatically begins playing the song
/////////////////////////////////////////////////////////////////////////////////////
//		dismissbtn.setOnAction(e -> {
//			if(player.getStatus() == MediaPlayer.Status.PLAYING){
//				player.stop();
//				Platform.exit();
//			}
//			else{
//				player.play();
//			}
//		});
//	}
	
	public static void main(String args[]){
//		Sound o = new Sound();
//		o.ir = new InputReminder();
//		o.ir.adate = LocalDate.now();
//		o.ir.ahour = o.ir.amin = o.ir.asec = 11;
//		o.ir.title = "Playing Audio";
//		o.ir.cmt = "Currently in the production state";
//		Application.launch(args);
		new Sound().playSound();
	}
}
