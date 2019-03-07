/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscheduler;

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

public class ThreadPool{
	class ThreadPoolRequest{
		Runnable target;	//	Don't create an object of the thread
		Object lock;	//	lock
		
		ThreadPoolRequest(Runnable t, Object l){
			target = t; lock = l;
		}
	}
	
//	This is the class which actually executes the thread
//	Thread inside a pool which is  executed by the pool
	class ThreadPoolThread extends Thread{
		ThreadPool parent;
		volatile boolean shouldRun = true;
		
		ThreadPoolThread (ThreadPool parent, int i){
			super("ThreadPoolThread " + i);	//	To its parent class i.e Thread class
			this.parent = parent;
		}
		
		@Override
		public void run(){
			ThreadPoolRequest obj = null;
			while(shouldRun){
				try{
					parent.cvFlag.getBusyFlag();	//	Acquire the lock
					while(obj==null && shouldRun){
						try{
							obj = (ThreadPoolRequest)parent.objects.elementAt(0);	//	Take the first thread from the poolThread and execute it
							parent.objects.removeElementAt(0);
						}catch(ArrayIndexOutOfBoundsException aioobe){
							obj = null;
						}catch(ClassCastException cce){
							System.err.println("Unexpected data");
							obj = null;
						}
						if(obj == null){
							try{
								parent.cvAvailable.cvWait();
							}catch(InterruptedException ie){
								return;
							}
						}
					}
				}finally{
					parent.cvFlag.freeBusyFlag();	//	Release the lcok
				}
				if(!shouldRun) return;
				obj.target.run();
				try{
					cvFlag.getBusyFlag();	//	Acquire the lock(mutex)
					nObjects--;
					if(nObjects == 0)
						cvEmpty.cvSignal();	//	cvEmpty waits for end condition
				}finally{
					parent.cvFlag.freeBusyFlag();	//	Release the lock
				}
				if(obj.lock != null){
					synchronized(obj.lock){
						obj.lock.notify();
					}
				}
				obj = null;
			}
		}
	}
	
//	Instance variables of ThreadPool class
	Vector objects;	// Stores the threads to be executed
	int nObjects = 0;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	CondVar cvAvailable, cvEmpty;
	BusyFlag cvFlag;
//	We have two condition  variables and one mutex lock.
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	ThreadPoolThread poolThreads[];	//	All the threads which is
	boolean terminated = false;
	
	public ThreadPool(int n){
		cvFlag = new BusyFlag();	//	Mutex lock is created
		cvAvailable = new CondVar(cvFlag);
		cvEmpty = new  CondVar(cvFlag);
		objects = new Vector();
		poolThreads = new ThreadPoolThread[n];
		
		for(int i = 0; i < n; i++){
			poolThreads[i] = new ThreadPoolThread(this, i);
			poolThreads[i].start();
		}
	}
	
	private void add(Runnable target, Object lock){	//	Can't access this method outside the class' scoppe
		try{
			cvFlag.getBusyFlag();
			if(terminated)	//	Thread has been shut down. So, we will not add any new threads
				throw new IllegalStateException("Thread pool has shut down");
			
			objects.addElement(new ThreadPoolRequest(target, lock));
			nObjects++;
			cvAvailable.cvSignal();
		}finally{
			cvFlag.freeBusyFlag();
		}
	}
	
	public void addRequest(Runnable target){
		add(target, null);
	}
	
	public void addRequestAndWait(Runnable target) throws InterruptedException{
		Object lock = new Object();
		synchronized(lock){
			add(target, lock);
			lock.wait();
		}
	}
	
	public void waitForAll(boolean terminate) throws InterruptedException{
		try{
			cvFlag.getBusyFlag();
			while(nObjects != 0) cvEmpty.cvWait();
			if(terminate){
				for(int i = 0; i < poolThreads.length; i++){
					poolThreads[i].shouldRun = false;
				}
				cvAvailable.cvBroadcast();
				terminated = true;
			}
		}finally{
			cvFlag.freeBusyFlag();
		}
	}
	
	public void waitForAll() throws InterruptedException{
		waitForAll(false);
	}
}
