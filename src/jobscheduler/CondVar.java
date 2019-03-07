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
public class CondVar{
	private BusyFlag syncVar;	//	This is the  mutex lock, which is accessed in a synchronized block
	public CondVar(){
		this(new BusyFlag());
	}
	
	public CondVar(BusyFlag sv){
		syncVar = sv;
	}
	
	public void cvWait() throws InterruptedException{
		cvTimedWait(syncVar, 0);
	}
	
	public void  cvWait(BusyFlag sv) throws InterruptedException{
		cvTimedWait(sv, 0);
	}
	
	public void cvTimedWait(int millis) throws InterruptedException{
		cvTimedWait(syncVar, millis);
	}
	
	public void cvTimedWait(BusyFlag sv, int millis) throws InterruptedException{
		int i = 0;	//	To count how many locks are acquired or are needed to be released
		InterruptedException errex = null;
		
		synchronized(this){
		//	Must own the lock in order to use this meethod
			if(sv.getBusyFlagOwner() != Thread.currentThread()){
				throw new IllegalMonitorStateException("current thread is no the owner");
			}
			
			while(sv.getBusyFlagOwner() == Thread.currentThread()){
				i++;
				sv.freeBusyFlag();
			}
			try{
				if(millis == 0) wait();
				else wait(millis);
			}catch(InterruptedException iex){
				errex = iex;
			}
		}
		while(i > 0){
			sv.getBusyFlag(); i--;
		}
		if(errex != null) throw errex;
		return;
	}
	
	public void cvSignal(){
		cvSignal(syncVar);
	}
	
	public synchronized void cvSignal(BusyFlag sv){
	//	Must own the lockk of the monitor
		if(sv.getBusyFlagOwner() != Thread.currentThread()){
			throw new IllegalMonitorStateException("Current thread is not the owner");
		}
		notify();
	}
	
	public void cvBroadcast(){
		cvBroadcast(syncVar);
	}
	
	public synchronized void cvBroadcast(BusyFlag sv){
	//	must own the lock
		if(sv.getBusyFlagOwner() != Thread.currentThread()){
			throw new IllegalMonitorStateException("Current thread not owner");
		}
		notifyAll();
	}
}
