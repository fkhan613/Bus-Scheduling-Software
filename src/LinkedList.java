/***************************************
 * File: LinkedList.java  
 * Date Created: 2022 / 05 / 30 
 * Author: Farhan, Nathan	   
 * Program Description:		   
 * This class is a generic linked list to hold info about -
 * different classes and objects
 ***************************************/

class LinkedList<T> {
	// instance variables
	private Node<T> head;
	private Node<T> end;

	// constructors
    public LinkedList() { this.head = this.end = null; }

	// accessors
	public Node<T> getHead() { return this.head; }
	public Node<T> getEnd() { return this.end; }

	// mutators
	public void setHead(Node<T> v) { this.head = v; }
	public void setEnd(Node<T> v) { this.end = v; }

	// check if list is empty
	public boolean isEmpty() { return getHead() == null; }

	// output
	public void print() {
		if(isEmpty()) {
			System.out.println("List is empty.");
			return;
		}

		System.out.println();

		// set current node
		Node<T> currentNode = this.getHead();

		while(currentNode != null) {
			System.out.println(currentNode.getData());
			currentNode = currentNode.getNext();
		}
	}

	// add element to front
	public void insertInFront(T d, int id) {
		// check if empty
		if(isEmpty()) {
			this.head = this.end = new Node<T>(d, id);
			return;
		}
		else { // if not empty, then replace head
			setHead(new Node<T>(d, getHead(), id));
		}
	}

	// add to end
	public void queue(T d, int id) {
		// check if list is empty
		if(isEmpty()) {
			insertInFront(d, id);
			return;
		}
		
		// set the end's next node to equal a new node
		getEnd().setNext(new Node<T>(d, id));
		setEnd(getEnd().getNext());
	}
		

	// remove from front
	public T dequeue() {
		if(isEmpty()) {
			return null;
		}

		T retVal = getHead().getData();
        
	    if(getHead() == getEnd()){ // there was only one item in the list
	      this.head = this.end = null;
	    } else {
	      setHead(getHead().getNext());
	    }
			
	    return retVal;
	}

	public void remove(int val) {
	    if(head == null) return;

        while(head!=null && head.getID() == val) head = head.getNext();
		
	    Node<T> temp = head, prev = null;
	        
	   	while(temp!=null) {
					
	    	if(temp.getID() == val) {
				prev.setNext(temp.getNext());

				if(end == temp) {
					setEnd(prev);
				}
			}
	   		else {
				prev = temp;
			}
			
	      	temp = temp.getNext();
    	}
    }

	public int count() {
		int count = 0;

		Node<T> currentNode = getHead();

		while(currentNode != null) {
			count++;
			currentNode = currentNode.getNext();
		}

		return count;
	}

	public int getHighest() {
		int highest = 0;

		Node<T> currentNode = getHead();

		while(currentNode != null) {
			if(currentNode.getID() > highest) {
				highest = currentNode.getID();
			}

			currentNode = currentNode.getNext();
		}

		return highest;
	}
}