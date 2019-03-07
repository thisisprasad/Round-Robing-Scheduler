/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reminder;

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
import javafx.application.Platform;
import supportive.Resources;    //  User-defined package

public class Reminder implements Runnable, Serializable{
	SwingSound ss = new SwingSound();
	public Thread t;
	Resources r = new Resources();
	boolean flag = false;	//	This variable is used to suspend and resume the threads
	ReminderQueue rlist;	//	I need a seperate list in the Reminder thread because, real-time updates are necessary to be accessed by the Reminder thread.
	int hr, min, sec;
	LocalDate date;
	InputReminder a[];
	
	public synchronized void showList(InputReminder a[]){
		for(int i = 0; i < a.length; i++) Resources.show(a[i].title + " " + a[i].adate + ":" + a[i].ahour + ":" + a[i].amin + ":" + a[i].asec);
	}
	public synchronized void suspendThread(){
		flag = true;	//	Thread flag is set to true and hence its execution is suspended.
		Resources.show("flag = true, suspend");
	}
	public synchronized void resumeThread(){
		flag = false;	//	Thread resumes its execution
		Resources.show("flag = false, resume");
		File f = new File(Resources.REMFILE);
		try(InputStream fis = new FileInputStream(f)){
			ObjectInputStream ois = new ObjectInputStream(fis);
			rlist = (ReminderQueue)ois.readObject();
			a = new InputReminder[rlist.queue.size()];
			a = rlist.queue.toArray(a);
			showList(a);
		}
		catch(FileNotFoundException fnfe){
			Resources.showerr(fnfe);
		}
		catch(IOException ioe){
			Resources.showerr(ioe);
		}
		catch(ClassNotFoundException cnfe){
			Resources.showerr(cnfe);
		}
		catch(Exception e){
			Resources.showerr(e);
		}
		notify();
	}
	
	static void addList(File f){	//	maybe Will overload this function in future
		ReminderQueue list = new ReminderQueue();	//	An empty  reminder list is created
		try(OutputStream fos = new FileOutputStream(f)){
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(list);
//				Resources.guierr("An empty list is added into the file");
		}catch(IOException ioe){
			Resources.show("IOException in addList");
			Resources.showerr(ioe);
		}
	}
	public Reminder(String name){
		t = new Thread(this, name);
		//	t.setPriority(4);
		flag = false;
		r.show("Reminder thread started");
		File remfile = new File(Resources.REMFILE);
		if(!remfile.exists()){	//	Need to create a file and store an empty vector into that file
			//	Resources.guierr("File does not exist");
			try{
				remfile.createNewFile();
				addList(remfile);
				//	Resources.getch();
			}catch(IOException ioe){
				Resources.show("line 107, IOException caught in creating a new file in reminder construcctor");
			}
		}
		else Resources.show("File exists");
	}
	
//	
//	@Override
//	public void run(){
//		Resources.show("Inside reminder's run method");
//		try{
//			//	r.show("Inside run method. Features are yet to be implemented. Hit enter, I'll proceed");
//			//	r.getch();
//		//	Entry point of the thread
//			//	Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
//			File f = new File(Resources.REMFILE);
//			LocalDateTime t;
//			long cnt = 0;
//			try(InputStream fis1 = new FileInputStream(f)){
//				ObjectInputStream ois1 = new ObjectInputStream(fis1);
//				rlist = (ReminderQueue)ois1.readObject();
//				a = new InputReminder[rlist.queue.size()];
//				rlist.queue.toArray(a);
//				while(true){
//				//	The checking code will come here
//			/*
//				Now will have to insert a syncronized block to access the reminderFile
//			*/
//					synchronized(f){
//						try(InputStream fis = new FileInputStream(f)){
//							ObjectInputStream ois = new ObjectInputStream(fis);
//							rlist = (ReminderQueue)ois.readObject();
//							a = new InputReminder[rlist.queue.size()];
//							rlist.queue.toArray(a);
//						}
//					}
//					//	showList();
//					//	Resources.show(Thread.currentThread().getName() + ": Thread running " + cnt); 
//					cnt++;
//					if(a.length > 0){
//						t = LocalDateTime.now();
//						date = t.toLocalDate();
//						hr = t.getHour(); min = t.getMinute(); sec = t.getSecond();
//						//	Resources.show("Outside comparison");
//						if((a[0].adate.equals(date) || a[0].adate.compareTo(date)<=0) && (a[0].ahour<=hr && a[0].amin<=min && a[0].asec<=sec)){
//							Resources.show("It should pop up");
//						//	Now remove the first element from the queue. We update the queue. Not a making a function call overe here
//							rlist.queue.remove(0);	//	Write this queue into that file
//							a = new InputReminder[rlist.queue.size()];
//							rlist.queue.toArray(a);
//							showList();
//							synchronized(f){
//								try(OutputStream fos = new FileOutputStream(f)){
//									ObjectOutputStream oos = new ObjectOutputStream(fos);
//									oos.writeObject(rlist);
//									Resources.show("Andar hoon main");
//									Resources.show("Hit enter now");
//									Resources.getch();
//								}catch(Exception e){
//									Resources.show("line 164, Exception ka chakkar hain");
//									Resources.showerr(e);
//								}
//							//	if(a.length == 0) break;
//							}
//						}
//						synchronized(this){
//							while(this.flag){
//								wait();
//							}//	Resources.show("Out of while");
//						}
//					//	Resources.show("Out sync block");
//					}
//				}
//			}
//			catch(ClassNotFoundException cnfe){
//				Resources.show("Exception caught at line 180");
//				Resources.showerr(cnfe);
//			}
//			catch(InterruptedException ie){
//				Resources.show("linr 183, InterruptedException iis cought for the sync block of run");
//				Resources.showerr(ie);
//			}
//			r.show("Bye!!!");
//		}catch(IOException ioe){
//			r.show("line 188, IOException caught in reminder's run methodd");
//			r.showerr(ioe);
//		}
//	}
	
	public void showList(){
		if(a.length > 0)
			for(int i = 0; i < a.length; i++)
				Resources.show(a[i].title + "," + a[i].adate + " Time: " + a[i].ahour+":"+a[i].amin+":"+a[i].asec);
		else Resources.show("No reminders in the list");
	}
	
	
	@Override
	public void run(){
		Resources.show("Inside reminder's run method");
		try{
			//	r.show("Inside run method. Features are yet to be implemented. Hit enter, I'll proceed");
			//	r.getch();
		//	Entry point of the thread
			//	Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			File f = new File(Resources.REMFILE);
			LocalDateTime t;
			long cnt = 0;
			
			/*
			try(InputStream fis1 = new FileInputStream(f)){
				ObjectInputStream ois1 = new ObjectInputStream(fis1);
				rlist = (ReminderQueue)ois1.readObject();
				a = new InputReminder[rlist.queue.size()];
				rlist.queue.toArray(a);
			*/
				while(true){
					boolean flag = false;
				//	The checking code will come here
			/*
				Now will have to insert a syncronized block to access the reminderFile
			*/
				synchronized(f){
					try(InputStream fis = new FileInputStream(f)){
						ObjectInputStream ois = new ObjectInputStream(fis);
						rlist = (ReminderQueue)ois.readObject();
						a = new InputReminder[rlist.queue.size()];
						rlist.queue.toArray(a);

						//	showList();
						//	Resources.show(Thread.currentThread().getName() + ": Thread running " + cnt); 
						cnt++;
						if(a.length > 0){
							t = LocalDateTime.now();
							date = t.toLocalDate();
							hr = t.getHour(); min = t.getMinute(); sec = t.getSecond();
							//	Resources.show("Outside comparison");
//							if((a[0].adate.equals(date) || a[0].adate.compareTo(date)<=0) && (a[0].ahour<=hr && a[0].amin<=min && a[0].asec<=sec)){
//								Resources.show("It should pop up");
//								Resources.show("Hit Enter to stop");
//							//	Now start the Audio of your alarm
//								//	new Inp	//	utReminder().input(this);	//	Can't do this....
//								//	new Sound().playSound();	//	getch() andar hai
//								SwingSound ss = new SwingSound();
//								ss.setVisible(true); ss.playSound(a[0]);
//							//	Now remove the first element from the queue. We update the queue. Not a making a function call overe here
//								rlist.queue.remove(0);	//	Write this queue into that file
//								a = new InputReminder[rlist.queue.size()];
//								rlist.queue.toArray(a);
//								showList();
//								
//								try(OutputStream fos = new FileOutputStream(f)){
//									ObjectOutputStream oos = new ObjectOutputStream(fos);
//									oos.writeObject(rlist);
//									Resources.show("Time match ho gaya!!!");
//									//	Resources.getch();
//								}catch(Exception e){
//									Resources.show("line 164, Exception ka chakkar hain");
//									Resources.showerr(e);
//								}
//								//	if(a.length == 0) break;
//							}
							if(a[0].adate.compareTo(date) < 0){
								ss.setVisible(true); ss.playSound(a[0]); flag = true;
							}
							else if(a[0].adate.equals(date)){
								if(a[0].ahour < hr){
									ss.setVisible(true); ss.playSound(a[0]); flag = true;
								}
								else if(a[0].ahour == hr){
									if(a[0].amin < min){
										ss.setVisible(true); ss.playSound(a[0]); flag = true;
									}
									else if(a[0].amin == min){
										if(a[0].asec <= sec){
											ss.setVisible(true); ss.playSound(a[0]); flag = true;
										}
									}
								}
							}
							if(flag){
								Resources.show("Time match ho gaya");
								rlist.queue.remove(0);	//	Write this queue into that file
								a = new InputReminder[rlist.queue.size()];
								rlist.queue.toArray(a);
								showList();
								try(OutputStream fos = new FileOutputStream(f)){
									ObjectOutputStream oos = new ObjectOutputStream(fos);
									oos.writeObject(rlist);
								}catch(Exception e){
									Resources.show("line 296, Exception catch kiya");
									Resources.showerr(e);
								}
							}
							synchronized(this){
								while(this.flag){
									wait();
								}//	Resources.show("Out of while");
							}
						//	Resources.show("Out sync block");
						}
					}
					catch(IOException ioe){
						Resources.show("line 270, IOException caught");
						Resources.showerr(ioe);
					}
					catch(ClassNotFoundException cnfe){
					Resources.show("Exception caught at line 180");
					Resources.showerr(cnfe);
					}
					catch(InterruptedException ie){
						Resources.show("linr 183, InterruptedException iis cought for the sync block of run");
						Resources.showerr(ie);
					}
				}
			/*
				catch(ClassNotFoundException cnfe){
					Resources.show("Exception caught at line 180");
					Resources.showerr(cnfe);
				}
				catch(InterruptedException ie){
					Resources.show("linr 183, InterruptedException iis cought for the sync block of run");
					Resources.showerr(ie);
				}
				r.show("Bye!!!");
			*/
				}
			/*
			}
			
			catch(IOException ioe){
				Resources.show("line 297, IOException caught");
				Resources.showerr(ioe);
			}
			catch(ClassNotFoundException cnfe){
				Resources.show("Exception caught at line 180");
				Resources.showerr(cnfe);
			}
			//	catch(InterruptedException ie){
				//	Resources.show("linr 183, InterruptedException iis cought for the sync block of run");
				//	Resources.showerr(ie);
			//	}
			*/
		}
		catch(Exception e){
			Resources.show("line 310, Exception");
			Resources.showerr(e);
		}
		r.show("Bye!!!");
	}
}

