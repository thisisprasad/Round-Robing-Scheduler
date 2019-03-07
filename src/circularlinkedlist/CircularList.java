/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package circularlinkedlist;
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
public class CircularList{	//	CircularLinkedList
	public CircularListNode current;
	
	public synchronized void insert(Object o){
		CircularListNode node = new CircularListNode();
		node.o = o;
		if(current == null){
			node.next = node.prev = node;
			current = node;
		}
		else{
			node.next = current;
			node.prev = current.prev;
			current.prev.next = node;
			current.prev = node;
		}
	}
	
	public synchronized void delete(Object o){
		CircularListNode p = find(o);
		CircularListNode next = p.next;
		CircularListNode prev = p.prev;
		
		if(p == p.next){	//	There is only one element in the list
			current = null;
			return;
		}
		prev.next = next;
		next.prev = prev;
		if(current == p) current = next;
	}
	
	private CircularListNode find(Object o){
		CircularListNode p = current;
		if(p == null){
			throw new IllegalArgumentException("line 74, find");
		}
		
		do{
			if(p.o == o) return p;
			p = p.next;
		}while(p != current);
		throw new IllegalArgumentException("line 81, find");
	}
	
	public synchronized Object locate(Object o){
		CircularListNode ptr = current;
		do{
			if(ptr.o.equals(o)) return ptr.o;
			ptr = ptr.next;
		}while(ptr != current);
		throw new IllegalArgumentException("line 90, loocate");
	}
	
	public synchronized Object getNext(){
		if(current == null) return null;
		current = current.next;
		return current.o;
	}
}
