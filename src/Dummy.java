/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Prasad-pc
 */
public class Dummy implements Runnable{
	Thread t;
	public Dummy(){
		t = new Thread(this, "Dummy");
		//	t.start();
	}
	
	@Override
	public void run(){
		System.out.println("Living in harmony");
	}
}
