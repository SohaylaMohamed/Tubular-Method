package eg.edu.alexu.csd.datastructure.linkedList.cs18_32;

/**.
 * @author Sohayla Mohamed
 *
 */
public class Node {

	public Object value;
	public Node next = null;
	public Node prev = null;

	/**.
	 * A constructor for the node class
	 */
	public Node(final Node next, final Node prev) {

		this.next = next;
		this.prev = prev;
	}

	/**.
	 * gets the value in a node
	 * 
	 * @return value
	 */
	public Object getValue() {
		return value;
	}

	/**.
	 * sets a new value in the node
	 * 
	 * @param value
	 *            the new value
	 */
	public void setValue(final Object value) {
		this.value = value;
	}

	/**.
	 * gets the next node
	 * 
	 * @return the next node
	 */
	public Node getNext() {
		return next;
	}

	/**.
	 * sets a new next to a node
	 * 
	 * @param next
	 *            the new node
	 */
	public void setNext(final Node next) {
		this.next = next;
	}

	/**.
	 * gets the prev node of a node
	 * 
	 * @return the previous node
	 */
	public Node getPrev() {
		return prev;
	}

	/**.
	 * sets a new previous to a node
	 * 
	 * @param prev
	 *            the new node
	 */
	public void setPrev(final Node prev) {
		this.prev = prev;
	}

}
