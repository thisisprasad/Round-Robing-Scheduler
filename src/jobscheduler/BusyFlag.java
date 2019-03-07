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
import supportive.*;
public class BusyFlag{
	protected Thread busyFlag = null;	//	This is our lock object which tells us that currently which threads holds the lock. A "mutex" lock maybe would do.
	protected int busyCount = 0;
	
	public synchronized void getBusyFlag(){
		while(tryGetBusyFlag() == false){
			try{
				wait();	//	here we don't want to waste CPU cycles by awakening periodically to check whether the lock has been released or not.
			}catch(Exception e){ Resources.show("line 21, BusyFlag class"); }
		}
	}
	
	public synchronized boolean tryGetBusyFlag(){
		if(busyFlag == null){	//	Acquire the lock
			busyFlag = Thread.currentThread();
			busyCount = 1;
			return true;
		}
		if(busyFlag == Thread.currentThread()){	//	lock already acquired
			busyCount++;
			return true;
		}
		return false;
	}
	
//	Method to release the  lock
	public synchronized void freeBusyFlag(){
		if(getBusyFlagOwner() == Thread.currentThread()){
			busyCount--;
			if(busyCount == 0){
				busyFlag = null;
				notify();	//	signal to the waitting thread
			}
		}
	}
	
//	the lock is  protected(for inheritance purpose), so we'll access it from a public method
	public synchronized Thread getBusyFlagOwner(){
		return busyFlag;
	}
}
