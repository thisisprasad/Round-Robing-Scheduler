/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reminder;

/**
 *
 * @author Prasad Saraf
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
import java.time.format.*;
import java.time.format.*;
import java.time.chrono.*;
import java.util.*;
import java.nio.file.*;
import java.nio.channels.*;
import java.nio.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import supportive.Resources;    //  User-defined package

public class InputReminder implements Serializable{
	public String title;
	LocalDateTime alarm;	//	The date and time of the alarm is set in this instance
	public LocalDate adate;	//	stores the alarm date from the DatePicker.
	LocalTime atime;	//	stores the alarm time.
	public int ahour, amin, asec;
	public String cmt;	//	commnents of the user
	//	Resources r = new Resources();
	public static void main(String args[]){
		//	Application.launch(args);
		Resources.show("Starting from InputReminder");
	}
	
	public void copy(InputReminder a[], InputReminder temp[], int low, int up){
		for(int i = low; i <= up; i++)
			a[i] = temp[i];
	}
	public void merge(InputReminder a[], InputReminder temp[], int low1, int up1, int low2, int up2){
		int i = low1, j = low2, k = low1;
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		while(i<=up1 && j<=up2){
			if(a[i].adate.compareTo(a[j].adate) < 0)	//	i-th date is less than j-th date. Because we want to select the earliset date
				temp[k++] = a[i++];
			else if(a[i].adate.compareTo(a[j].adate) > 0)
				temp[k++] = a[j++];
			else{	//	Both the dates are equal and we need to compare the time
				if(a[i].ahour < a[j].ahour)
					temp[k++] = a[i++];
				else if(a[i].ahour > a[j].ahour)
					temp[k++] = a[j++];
				else{	//	hours are equal and compare the min and if they turn out to be equal then coompare the secs
					if(a[i].amin < a[j].amin)
						temp[k++] = a[i++];
					else if(a[i].amin > a[j].amin)
						temp[k++] = a[j++];
					else{	//	cmopare the seconds
						if(a[i].asec <= a[j].asec)
							temp[k++] = a[i++];
						else temp[k++] = a[j++];
					}
				}
			}
		}
	//	Assuming that the above comparisons take place in O(1) time and hence not affecting the asymptotic complexity of the algorithm
	
	while(i <= up1) temp[k++] = a[i++];
	while(j <= up2) temp[k++] = a[j++];
	}
	public void mergeSort(InputReminder a[], int low, int up){
		int mid;
		InputReminder temp[] = new InputReminder[Resources.maxn];
		if(low < up){
			mid = (low+up)/2;
			mergeSort(a, low, mid);
			mergeSort(a, mid+1, up);
			merge(a, temp, low, mid, mid+1, up);
			copy(a, temp, low, up);
		}
	}
	public void sortQueue(ReminderQueue list){
		InputReminder array[] = new InputReminder[list.queue.size()];	//	Array to perform the sorting
		array = list.queue.toArray(array);
		int n = array.length;
		mergeSort(array, 0, n-1);
		list.queue = new ArrayList<InputReminder>(Arrays.asList(array));
	}
	
	public void input(Reminder rem){	//	Accept the reminder details here and then run the reminder thread
		Stage stage = new Stage();
		stage.setTitle("Set Reminder Details");
		
		GridPane root = new GridPane();
	//////////////////////////////////////////////////////////////////////////////////////////////////
		Label titlelbl = new Label("Title: ");		TextField titlefld = new TextField(); titlefld.setPrefColumnCount(10);
		Label timelbl = new Label("Set Time(hr/min/sec): ");	TextField hrfld = new TextField(); TextField minfld = new TextField(); TextField secfld = new TextField();
																hrfld.setPrefColumnCount(3); minfld.setPrefColumnCount(3); secfld.setPrefColumnCount(3);
		Label datelbl = new Label("Set Date: ");	DatePicker reminderDate = new DatePicker();
		Label cmtlbl = new Label("Brief: ");		TextArea cmtarea = new TextArea(); 
		cmtarea.setPromptText("Brief comments any..."); cmtarea.setPrefRowCount(20); cmtarea.setWrapText(true);
	///////////////////////////////////////////////////////////////////////////////////////////////////
		Button setbtn = new Button("Set Reminder...");
		Button showbtn = new Button("Show List");
		Button closebtn = new Button("Close");
		
	//	Defining event handlers for button
		setbtn.setOnAction((ActionEvent e) -> {
			//	rem.suspendThread();	//	Now this will cause a nullpointer Exception
			
			//	Now write this object into the queue of alarm
			File f = new File(Resources.DIR+"reminderQueue"+".rmi");	//	File extension is of type ".rmi"
			synchronized(f){
			/*
				Now the whole code below should be inside a synchronized block.
				Beccause the access to the REMFILE must be handled carefully
			*/
				//	Resources.guierr("work in progress");
				title = titlefld.getText();
				//	String hr = hrfld.getText(), min = minfld.getText(), sec = secfld.getText();
				ahour = Integer.parseInt(hrfld.getText()); amin = Integer.parseInt(minfld.getText()); asec = Integer.parseInt(secfld.getText());
				adate = reminderDate.getValue();
				//	atime = LocalTime.parse(hr+":"+min+":"+sec);
				cmt = cmtarea.getText();

				//	if(!f.exists()){	//	user is setting the reminder for the first time. Create a hashmap and store it in the file
				//	Resources.guierr("File does not exist");
				ReminderQueue list = new ReminderQueue();
			//	Now read the queue from the reminderQueue.rmi file
				try(InputStream fis = new FileInputStream(f)){
					ObjectInputStream ois = new ObjectInputStream(fis);
					list = (ReminderQueue)ois.readObject();
					list.queue.add(this);	//	Now sort this queue according to date/time
					sortQueue(list);	//	After sorting the queue insert it again into the file.
				}
				catch(IOException ioe){
					Resources.show("IOException caught in fileInputStream of InputReminder");
					Resources.showerr(ioe);
				}
				catch(Exception ee){
					Resources.show("Exception in InputReminder fileInputStream");
					Resources.showerr(ee);
				}
			//	Now write this queue into file 'f'
				try(OutputStream fos = new FileOutputStream(f)){
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(list);
					Resources.guierr("Reminder saved successfully");
				}catch(IOException ioe){
					Resources.show("IOException caught while setting first reminder");
					Resources.showerr(ioe);
				}
				//	}
				//	try{Resources.getch();}catch(Exception ese){}
				//	rem.resumeThread();	//	Resume the thread
			}
		});
		showbtn.setOnAction(e -> {
			showList();
		});
		closebtn.setOnAction(e -> stage.close());
		
		Scene scene = new Scene(root, 500, 300);	

		stage.setScene(scene);
		int row = 0;
		root.addRow(row, titlelbl, titlefld); row++;
		root.addRow(row, timelbl, hrfld, minfld, secfld); row++;
		root.addRow(row, datelbl, reminderDate); row++;
		root.addRow(row, cmtlbl, cmtarea); row++;
		root.addRow(row, setbtn, closebtn); row++;
		root.addRow(row, showbtn); row++;
		stage.show();
	}
	
	public void showList(){
		ReminderQueue slist = new ReminderQueue();
		File f = new File(Resources.REMFILE);
		try(InputStream fis = new FileInputStream(f)){
			ObjectInputStream ois = new ObjectInputStream(fis);
			slist = (ReminderQueue)ois.readObject();
			InputReminder a[] = new InputReminder[slist.queue.size()];
			a = slist.queue.toArray(a);
			if(a.length == 0){
				Resources.show("There are no reminders set.");
				return;
			}
			for(int i = 0; i < a.length; i++){
				Resources.guierr(a[i].title + "," + a[i].adate + " Time: " + a[i].ahour+":"+a[i].amin+":"+a[i].asec);
				Resources.show(a[i].title + "," + a[i].adate + " Time: " + a[i].ahour+":"+a[i].amin+":"+a[i].asec);
			}
		}
		catch(FileNotFoundException fnfe){ Resources.show("FileNotFoundException found while showList()");}
		catch(IOException ioe){
			Resources.show("IOExceeption in showList");
		}
		catch(ClassNotFoundException cnfe){
			Resources.showerr(cnfe);
		}
		//	Resources.show("Done?");
		//	try {
		//		Resources.getch();
		//	} catch (IOException ex) {
		//		Logger.getLogger(InputReminder.class.getName()).log(Level.SEVERE, null, ex);
		//	}
	}
}
