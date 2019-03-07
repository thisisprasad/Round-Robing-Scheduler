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

//	The job scheduler itself is one thread that will run the ThreadPool threads
public class JobScheduler implements Runnable{
	final public static int ONCE = 1;
	final public static int FOREVER = -1;
	final public static long HOURLY = (long) 60*60*1000;	//	Convert milliseconds to seconds
	final public static long DAILY = 24*HOURLY;
	final public static long WEEKLY = 7*DAILY;
	final public static long MONTHLY = -1;
	final public static long YEARLY = -2;
	
	private class JobNode{
		public Runnable job;	//	Job to be executed
		public Date executeAt;
		public long interval;
		public int count;
	}
	
	private ThreadPool tp;
	private DaemonLock dlock = new DaemonLock();
	private Vector jobs = new Vector(100);
	
	public JobScheduler(int n){
		if(n > 0) tp = new ThreadPool(n);
		else tp = null;
		Thread js = new Thread(this);
		js.setDaemon(true);
		js.start();
	}
	
	private synchronized void addJob(JobNode job){
		dlock.acquire();	//	Acquire the lock
		jobs.addElement(job);
		notify();	//	Is the lock released
	}
	
	private synchronized void deleteJob(Runnable job){
		for(int i = 0; i < jobs.size(); i++){
			if(((JobNode) jobs.elementAt(i)).job == job){
				jobs.removeElementAt(i);
				dlock.release();
				break;
			}
		}
	}
	
	private JobNode updateJobNode(JobNode jn){
		Calendar cal = Calendar.getInstance();
		cal.setTime(jn.executeAt);
		
		if(jn.interval == MONTHLY){
			cal.add(Calendar.MONTH, 1);
			jn.executeAt = cal.getTime();
		}
		else if(jn.interval == YEARLY){
			cal.add(Calendar.YEAR, 1);
			jn.executeAt = cal.getTime();
		}
		else{
			jn.executeAt = new Date(jn.executeAt.getTime() + jn.interval);
		}
		
		if(jn.count == FOREVER) jn.count = FOREVER;
		else jn.count = jn.count-1;
		return (jn.count != 0) ? jn : null;
	}
	
	private synchronized long runJobs(){
		long mindiff = Long.MAX_VALUE;
		long now = System.currentTimeMillis();
		
		for(int i = 0; i < jobs.size(); i++){
			JobNode jn = (JobNode) jobs.elementAt(i);
			if(jn.executeAt.getTime() <= now){
				if(tp != null) tp.addRequest(jn.job);

				else{
					Thread jt = new Thread(jn.job);
					jt.setDaemon(false);
					jt.start();
				}

				if(updateJobNode(jn) == null){
					jobs.removeElementAt(i);
					dlock.release();
				}
			}
			else{
				long diff = jn.executeAt.getTime() - now;
				mindiff = Math.min(diff, mindiff);
				i++;
			}
		}
		return mindiff;
	}
}
