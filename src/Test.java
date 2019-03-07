/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Prasad-pc
 */
public class Test{
	public static void main(String args[]){
		Dummy d = new Dummy();
		//	d.t.start();
	}
}

class ubs{
	static void show(){
		System.out.println("void data--type");
	}
	
	static int showx(int i){
		System.out.println("int data-type");
		return i;
	}
	
	public static void main(String args[]){
		show();
		int i = show(0);
	}
}
