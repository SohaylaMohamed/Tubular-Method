package eg.edu.alexu.csd.datastructure.queue.cs32;

import eg.edu.alexu.csd.datastructure.queue.IArrayBased;
import eg.edu.alexu.csd.datastructure.queue.IQueue;

public class QueueArray implements IQueue, IArrayBased {
	private int count;
	private int r;
	private int f;
	private Object[] Q;

	public QueueArray(int N) {
		this.Q = new Object[N];
		this.r = -1;
		this.f = -1;
		this.count = 0;
	}

	public void enqueue(Object item) {
		if ((r + 1) % Q.length == f) {
			throw null;
		} else if (isEmpty()) {
			r = 0;
			f = 0;
		} else {
			r = (r + 1) % Q.length;
		}
		Q[r] = item;
		count++;
	}

	public Object dequeue() {
		Object temp;
		if (isEmpty()) {
			throw null;
		} else if (f == r) {
			temp = Q[f];
			f = -1;
			r = -1;
			count--;
		} else {
			temp = Q[f];
			f = (f + 1) % Q.length;
			count--;
		}
		return temp;
	}

	public boolean isEmpty() {
		if (f == -1 && r == -1) {
			return true;
		}
		return false;
	}

	public int size() {
		return count;
	}

	public void print() {
		for (int i = f; i <= r; i++) {
			System.out.println(Q[i]);
		}
	}

}
