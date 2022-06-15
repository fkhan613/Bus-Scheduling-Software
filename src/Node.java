/***************************************
 * File: Node.java  
 * Date Created: 2022 / 05 / 30 
 * Author: Farhan, Nathan	   
 * Program Description:		   
 * This class holds data of different objects -
 * Ex. Buses, routes, passengers etc
 ***************************************/

class Node<T> {
	// instance variables
	private T data;
	private Node<T> next;
	private int id; // corresponds to the bus id, passenger id or route num

	// constructor
	public Node(T d, int i) { this(d, null, i); }
	public Node(T d, Node<T> n, int i) { this.data = d; this.next = n; this.id = i; }

	// accessor
	public T getData() { return this.data; }
	public Node<T> getNext() { return this.next; }
	public int getID() { return this.id; }

	// mutators
	public void setData(T v) { this.data = v; }
	public void setNext(Node<T> v) { this.next = v; }
	public void setID(int v) { this.id = v; }
}