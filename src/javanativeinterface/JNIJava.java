/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javanativeinterface;

/**
 *
 * @author Prasad-pc
 */
public class JNIJava {
	static{
		System.load("libJNI_CPP");
	}
	
//	Declaring native methods
	public native void printString(String name);
	
	public static void main(final String args[]){
		JNIJava jnijava = new JNIJava();
		jnijava.printString("This string is from c++ function");
		
	}
}
