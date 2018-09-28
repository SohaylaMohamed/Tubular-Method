package eg.edu.alexu.csd.datastructure.linkedList.cs18_32;

import eg.edu.alexu.csd.datastructure.linkedList.ILinkedList;

/**
 * .
 * 
 * @author Sohayla Mohamed
 *
 */
public class SinglyLinkedList implements ILinkedList {

	NodeS head;
	NodeS tail;
	int size;

	/**
	 * . Inserts a specified element at the specified sposition in the list.
	 */
	public void add(final int index, final Object element) {
		if (element == null) {
			throw new RuntimeException();
		}
		NodeS newNode = new NodeS();
		newNode.setValue(element);
		if (head == null) {
			head = newNode;
			newNode.setNext(null);
			return;
		}
		int size = size();
		if (index > size || index < 0) {
			throw new RuntimeException();
		}
		NodeS temp1 = null;
		NodeS temp2 = head;
		int i = 0;
		if (index == 0) {
			temp1 = head;
			head = newNode;
			newNode.setNext(temp1);
			return;
		}
		while (i != index) {
			temp1 = temp2;
			temp2 = temp2.getNext();
			i++;
		}
		temp1.setNext(newNode);
		newNode.setNext(temp2);
		return;
	}

	public void addatTail(Object o) {
		NodeS temp = new NodeS();
		temp.setValue(o);
		if (isEmpty()) {
			head = temp;
			tail = temp;
			temp.setNext(null);
			size++;

		} else {
			NodeS temp2 = tail;
			temp2.setNext(temp);
			temp.setNext(null);
			tail = temp;
			size++;
		}
	}

	public Object removeHead() {
		if (head == null) {
			throw new RuntimeException();
		} else {
			Object o = head.getValue();
			head = head.getNext();
			return o;
		}
	}
	

	/**
	 * . Inserts the specified element at the end of the list.
	 */
	public void add(final Object element) {
		if (element == null) {
			throw new RuntimeException();
		}
		NodeS newNode = new NodeS();
		newNode.setValue(element);
		if (head == null) {
			head = newNode;
			newNode.setNext(null);
			return;
		}
		NodeS temp = head;
		while (temp.getNext() != null) {
			temp = temp.getNext();
		}
		temp.setNext(newNode);
		newNode.setNext(null);
		return;
	}

	/**
	 * . Returns the element at the specified position in this list.
	 * 
	 */
	public Object get(final int index) {
		if (head == null) {
			throw new RuntimeException();
		}
		int size = size();
		if (index >= size || index < 0) {
			throw new RuntimeException();
		}
		NodeS temp = head;
		int counter = 0;
		while (counter != index) {
			temp = temp.getNext();
			counter++;
		}
		return temp.getValue();
	}

	/**
	 * . Replaces the element at the specified position in this list with the
	 * specified element.
	 */
	public void set(final int index, final Object element) {
		if (head == null) {
			throw new RuntimeException();
		}
		int size = size();
		if (index >= size || index < 0) {
			throw new RuntimeException();
		}
		if (element == null) {
			throw new RuntimeException();
		}
		NodeS node = new NodeS();
		node.setValue(element);
		NodeS temp2 = head;
		int counter = 0;
		while (counter != index) {
			temp2 = temp2.getNext();
			counter++;
		}
		temp2.setValue(element);
		return;
	}

	/**
	 * . Removes all of the elements from this list.
	 */
	public void clear() {
		head = null;
		return;
	}

	/**
	 * . Returns true if this list contains no elements.
	 */
	public boolean isEmpty() {
		if (head == null) {
			return true;
		}
		return false;
	}

	/**
	 * . Removes the element at the specified position in this list.
	 */
	public void remove(final int index) {
		if (head == null) {
			throw new RuntimeException();
		}
		int size = size();
		if (index >= size || index < 0) {
			throw new RuntimeException();
		}
		NodeS temp1 = null;
		NodeS temp2 = head;
		int i = 0;
		if (index == 0) {
			head = head.getNext();
			return;
		}
		while (i != index) {
			temp1 = temp2;
			temp2 = temp2.getNext();
			i++;
		}
		temp1.setNext(temp2.getNext());
		temp2.setNext(null);
		return;
	}

	/**
	 * . Returns the number of elements in this list.
	 */
	public int size() {
		if (head == null) {
			return 0;
		}
		NodeS temp = head;
		int counter = 1;
		while (temp.getNext() != null) {
			counter++;
			temp = temp.getNext();
		}
		return counter;
	}

	/**
	 * . Returns a view of the portion of this list between the specified
	 * fromIndex and toIndex, inclusively.
	 */
	public ILinkedList sublist(final int fromIndex, final int toIndex) {
		if (fromIndex < 0 || fromIndex >= size()) {
			throw new RuntimeException();
		}
		if (toIndex < 0 || toIndex >= size()) {
			throw new RuntimeException();
		}
		if (fromIndex > toIndex) {
			throw new RuntimeException();
		}
		if (head == null) {
			throw new RuntimeException();
		}
		int counter = 0;
		NodeS temp = head;
		while (counter != fromIndex) {
			temp = temp.getNext();
			counter++;
		}
		NodeS firstNode = new NodeS();
		firstNode.setValue(temp.getValue());
		SinglyLinkedList list = new SinglyLinkedList();
		list.head = firstNode;
		while (counter < toIndex) {
			temp = temp.getNext();
			NodeS nodes = new NodeS();
			nodes.setValue(temp.getValue());
			firstNode.setNext(nodes);
			firstNode = firstNode.getNext();
			counter++;
		}
		firstNode.setNext(null);
		ILinkedList list2;
		list2 = list;
		return list2;
	}

	/**
	 * . Returns true if this list contains an element with the same value as
	 * the specified element.
	 */
	public boolean contains(final Object o) {
		if (head == null) {
			throw new RuntimeException();
		}
		if (o == null) {
			throw new RuntimeException();
		}
		NodeS temp = head;
		while (temp.getNext() != null) {
			if (temp.getValue().equals(o)) {
				return true;
			}
			temp = temp.getNext();
		}
		if (temp.getValue().equals(o)) {
			return true;
		}
		return false;
	}

	/**
	 * . prints the list
	 */
	public void print() {
		if (head == null) {
			System.out.println("list is empty");
			return;
		}
		NodeS temp = new NodeS();
		temp = head;
		while (temp.getNext() != null) {
			System.out.println(temp.getValue());
			temp = temp.getNext();
		}
		System.out.println(temp.getValue());
		return;
	}

}