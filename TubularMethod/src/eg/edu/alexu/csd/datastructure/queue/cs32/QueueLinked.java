package eg.edu.alexu.csd.datastructure.queue.cs32;

import eg.edu.alexu.csd.datastructure.linkedList.cs18_32.SinglyLinkedList;
import eg.edu.alexu.csd.datastructure.queue.ILinkedBased;
import eg.edu.alexu.csd.datastructure.queue.IQueue;

public class QueueLinked implements IQueue, ILinkedBased {
	SinglyLinkedList Q = new SinglyLinkedList();
	int count;

	public void enqueue(Object item) {
		Q.addatTail(item);
		count++;

	}
	public void print(){
		Q.print();
	}

	@Override
	public Object dequeue() {
		Object o = Q.removeHead();
		count--;
		return o;
	}
	public Object getAtIndex(int i){
		Object o = Q.get(i);
		return o;
	}

	@Override
	public boolean isEmpty() {
		if (count == 0) {
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return count;
	}

}
