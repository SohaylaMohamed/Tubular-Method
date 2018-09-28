package eg.edu.alexu.csd.datastructure.linkedList.cs18_32;

import eg.edu.alexu.csd.datastructure.linkedList.ILinkedList;

/**.
 * @author Sohayla Mohamed
 *
 */
public class DoublyLinkedList implements ILinkedList {

	Node head = new Node(null, null);
	Node tail = new Node(null, head);
	int sizeS;

	/**.
	 * Inserts a specified element at the specified sposition in the list.
	 */
	public void add(final int index, final Object element) {
		if (index < 0 || index > sizeS()) {
			throw null;
		}
		Node newNode = new Node(null, null);
		newNode.setValue(element);

		Node temp = head;

		if (index == 0) {
			if (head.getNext() == null && tail.getPrev() == head) {
				newNode.setNext(tail);
				newNode.setPrev(head);
				head.setNext(newNode);
				tail.setPrev(newNode);
				tail.setNext(null);

			} else {
				head.getNext().setPrev(newNode);
				newNode.setNext(head.getNext());
				newNode.setPrev(head);
				head.setNext(newNode);

			}
		} else {
			for (int i = -1; i < index - 1; i++) {
				temp = temp.getNext();
			}
			if (temp.getNext() == null) {
				tail.setPrev(newNode);
				temp.setNext(newNode);
				newNode.setPrev(temp);
				newNode.setNext(tail);
			} else {
				temp.getNext().setPrev(newNode);
				newNode.setNext(temp.getNext());
				newNode.setPrev(temp);
				temp.setNext(newNode);
			}
		}
		sizeS++;

	}

	/**.
	 * Prints the list
	 */

	public void print() {
		Node i = head;
		while (i.getNext() != tail && i.getNext() != null) {
			System.out.println(i.getNext().getValue());
			i = i.getNext();
		}
	}

	/**.
	 * Inserts the specified element at the end of the list.
	 */
	public void add(final Object element) {
		if (element == null) {
			throw null;
		}
		Node newNode = new Node(null, null);
		newNode.setValue(element);
		tail.getPrev().setNext(newNode);
		newNode.setPrev(tail.getPrev());

		tail.setPrev(newNode);
		newNode.setNext(tail);
		sizeS++;

	}

	/**.
	 * Returns the element at the specified position in this list.
	 * 
	 */
	public Object get(final int index) {
		if (index < 0 || index >= sizeS()) {
			throw new RuntimeException();
		}
		Node temp = head;
		for (int i = 0; i < index + 1; i++) {
			temp = temp.getNext();
		}
		return temp.getValue();

	}

	/**.
	 * Replaces the element at the specified position in this list with the
	 * specified element.
	 */
	public void set(final int index, final Object element) {
		if (index < 0 || index >= size()) {
			throw new RuntimeException();
		}
		if (element == null || isEmpty()) {
			throw new RuntimeException();

		}
		Node temp = head;
		for (int i = 0; i <= index; i++) {
			temp = temp.getNext();
		}
		temp.setValue(element);

	}

	/**.
	 * Removes all of the elements from this list.
	 */
	public void clear() {
		tail.setPrev(head);
		tail.setNext(null);
		head.setNext(null);
		head.setPrev(null);
		sizeS = 0;

		return;
	}

	/**.
	 * Returns true if this list contains no elements.
	 */
	public boolean isEmpty() {

		if (sizeS() == 0 || (head.getNext() == null && tail.getPrev() == head) || sizeS == 0) {
			return true;
		}

		return false;

	}

	/**.
	 * Removes the element at the specified position in this list.
	 */
	public void remove(final int index) {
		if (index < 0 || index >= sizeS()) {
			throw new RuntimeException();
		}
		Node temp = head;
		for (int i = 0; i < index + 1; i++) {
			temp = temp.getNext();
		}
		temp.getNext().setPrev(temp.getPrev());
		temp.getPrev().setNext(temp.getNext());
		temp.setNext(null);
		temp.setPrev(null);
		sizeS--;

	}

	/**.
	 * Returns the number of elements in this list.
	 */
	public int size() {

		if (head.getNext() == null && tail.getPrev() == head) {
			return 0;
		}
		Node temp = head;
		int count = 0;
		while (temp.getNext() != tail) {
			count++;
			temp = temp.getNext();
		}
		return count;

	}

	/**.
	 * Returns a view of the portion of this list between the specified
	 * fromIndex and toIndex, inclusively.
	 */
	public ILinkedList sublist(final int fromIndex, final int toIndex) {
		if (fromIndex < 0 || fromIndex >= size() || toIndex < 0 || toIndex >= size() || isEmpty()
				|| toIndex < fromIndex) {
			throw new RuntimeException();
		}
		Node temp = head;

		int i;
		for (i = 0; i <= fromIndex && temp.getNext() != null; i++) {
			temp = temp.getNext();

		}

		DoublyLinkedList subList = new DoublyLinkedList();
		Node n = new Node(null, null);
		n.setValue(temp.getValue());
		subList.add(0, n.getValue());

		for (int j = i; j <= toIndex && temp.getNext() != null; j++) {
			n.setValue(temp.getNext().getValue());
			subList.add(n.getValue());
			temp = temp.getNext();

		}

		ILinkedList list;
		list = subList;
		return list;

	}

	/**.
	 * Returns true if this list contains an element with the same value as the
	 * specified element.
	 */
	public boolean contains(final Object o) {
		if (head.getNext() == null && tail.getPrev() == head || isEmpty()) {
			throw new RuntimeException();
		}
		if (o == null) {
			throw new RuntimeException();
		}

		Node temp = head.getNext();

		while (temp != tail) {
			if (temp.getValue().equals(o)) {
				return true;
			}
			temp = temp.getNext();

		}

		return false;
	}

	/**.
	 * Returns the size of the list
	 */
	public int sizeS() {

		return sizeS;
	}

}
