/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;
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
import scheduler.*;
/**
 *
 * @author Prasad-pc
 */
public class CPUScheduler extends Thread{
	private Thread current;	//	Holds the currently executing thread if it had to...
	private static boolean flag = false;	//	A static variable to ensure that only one instace of the Scheduler is created at run-time
	private CircularList threadQ;	//	Circular linkedlist of threads
	private int timeslice;	//	The time after which the Scheduler wakes up and changes the currently executing thread
	public Thread t;
	
	private synchronized static boolean isSet(){
		if(flag) return true;
		flag = true;
		return false;
	}
	
	public CPUScheduler(int t){	//	constructor
		if(isSet())
			throw new SecurityException("Already Initialized");
		threadQ = new CircularList();
		timeslice = t;
		setDaemon(true);	//	Scheduler is a daemon thread
	}
	
	public synchronized void addThread(Thread t){
		t.setPriority(2);
		threadQ.insert(t);
	}
	
	public void removeThread(Thread t){
		try{
			t.setPriority(5);
		}catch(Exception e){Resources.show("Exception in removeThread() of the CPUScheduler"); Resources.showerr(e);}
		
		threadQ.delete(t);
		/**
			If scheduler is sleeping and the currently running thread tries to exit
			If after waking up the scheduler thread will try to make priorrity of current thread to 5 which is in exxiting state and an exception will be
			thrown.
		*/
		synchronized(this){	
			if(current == t)	//	There was only one element in the CircularList
				current = null;
		}
	}
	
	@Override
	public void run(){
		setPriority(8);
		while(true){
			//	Resources.show("Wake up schedler");
			current = (Thread) threadQ.getNext();
			if(current == null) return;
			
			try{ current.setPriority(4); }
			catch(Exception e){ removeThread(current); }
			
			try{ 
				Thread.sleep(timeslice); 
			//	Now the current thread executes.
				//	showThreadList();
				//	try{ Resources.getch();}
				//	catch(Exception e){ Resources.showerr(e); }
			}
			catch(InterruptedException ie){ Resources.show("InterruptedExceptin caught in run() method of scheduler"); Resources.showerr(ie); }
			
		/*
			If we call removeThread() and  current thread is removed then current thread goes into exiting state and changing its state/priority will be
			error
			So we need to synchronize the access to this thread
			removeThread must inform the scheduler that the current thread has been removed.
		*/
			synchronized(this){
				if(current != null){
					try{ current.setPriority(2); }
					catch(Exception e){ removeThread(current); }
				}
			}
		}
	}
	
	void showThreadList(){
		CircularListNode node = new CircularListNode(), q = new CircularListNode();
		node = threadQ.current; q = threadQ.current;
		Resources.show(node.toString());
		node = node.next;
		if(node == q){
			Resources.show("There is onlly one element in the list");
			return;
		}
		while(node != q){
			Resources.show(node.o.getClass().getName());
			node = node.next; 
		}
	}
}
