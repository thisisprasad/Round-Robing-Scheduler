/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscheduler;

import supportive.Resources;

/**
 *
 * @author Prasad-pc
 */

//	This is very useful for daemon threads to run the critcal sections of thr code.
//	A lock is user thread which is held by a daemon thread which prevents JVM from exiting when critical section of the codes are run by the daemon thread
public class DaemonLock implements Runnable{
	private int lockCount = 0;	//	Inittialized ti zeor
	
	public synchronized void acquire(){
		if(lockCount++ == 0){
			Thread t = new Thread(this);
			t.setDaemon(false);	//	This thread will be started by a daemon thread
			t.start();
		}
	}
	
//	Release this DaeemonLock when the daemon thread has completed its execution
	public synchronized void release(){
		if(--lockCount == 0){
			notify();
		}
	}
	
	public void run(){
		while(lockCount != 0){
			try{
				wait();
			}catch(InterruptedException ie){
				Resources.show("Exception caught at line 40");
				Resources.showerr(ie);
			}
		}
	}
}
