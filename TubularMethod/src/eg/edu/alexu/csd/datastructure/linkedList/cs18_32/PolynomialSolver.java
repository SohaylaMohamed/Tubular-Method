package eg.edu.alexu.csd.datastructure.linkedList.cs18_32;

import eg.edu.alexu.csd.datastructure.linkedList.IPolynomialSolver;

/**.
 * @author Sohayla Mohamed
 *
 */
public class PolynomialSolver implements IPolynomialSolver {

/**
*
 */
  private DoublyLinkedList A = new DoublyLinkedList();
/**
*
*/
 private  DoublyLinkedList B = new DoublyLinkedList();
/**
*
*/
DoublyLinkedList C = new DoublyLinkedList();
	/**
* 
	 */
	DoublyLinkedList R = new DoublyLinkedList();

	/**.
	 * sort an array
	 * 
	 * @param anArray
	 *            unsorted array
	 * @param size
	 *            the number of rows in an array
	 * 
	 */
	void sorting(final int anArray[][], final int size) {
		int minIndex;
		int index;
		for (index = 0; index < size - 1; index++) {
			minIndex = getMinIndex(anArray, index, size - 1);
			swap(anArray, index, minIndex);
		}
	}

	/**.
	 * gets the min value's index
	 * 
	 * @param anArray
	 *            an array
	 * @param first
	 *            first index
	 * @param last
	 *            last index
	 * @return min index of an array
	 * 
	 */
	int getMinIndex(final int anArray[][], final int first, final int last) {
		int minIndex;
		int index;
		minIndex = first;
		for (index = first + 1; index <= last; index++) {
			if (anArray[index][1] < anArray[minIndex][1]) {
				minIndex = index;
			}
		}
		return minIndex;
	}

	/**.
	 * Swap two elements in an array
	 * 
	 * @param Arr
	 *            an array
	 * @param a
	 *            first Row index
	 * @param b
	 *            second row index
	 */
	void swap(final int Arr[][], final int a, final int b) {
		int temp1 = Arr[a][0];
		int temp2 = Arr[a][1];
		Arr[a][0] = Arr[b][0];
		Arr[a][1] = Arr[b][1];
		Arr[b][0] = temp1;
		Arr[b][1] = temp2;
	}

	/**.
	 * checks if a list is set or nor
	 * 
	 * @param poly
	 *            char which points to a list
	 *
	 * @return true if set ,false if not
	 * 
	 */
	public boolean checkSet(final char poly) {
		DoublyLinkedList list = new DoublyLinkedList();
		if (poly == 'A' || poly == 'a') {
			list = A;
		} else if (poly == 'B' || poly == 'b') {
			list = B;
		} else if (poly == 'C' || poly == 'c') {
			list = C;
		} else if (poly == 'R' || poly == 'r') {
			list = R;
		}
		if (list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**.
	 * Set polynomial terms (coefficients & exponents)
	 * 
	 * @param poly
	 *            name of the polynomial
	 * @param terms
	 *            array of [coefficients][exponents]
	 */
	public void setPolynomial(final char poly, final int[][] terms) {
		if (poly == 'A' || poly == 'a') {
			if (!A.isEmpty()) {
				A.clear();
			}

		} else {
			if (poly == 'B' || poly == 'b') {
				if (!B.isEmpty()) {
					B.clear();
				}

			} else {
				if (poly == 'C' || poly == 'c') {
					if (!C.isEmpty()) {
						C.clear();
					}

				}

			}
		}
		if (terms == null || terms.length == 0) {
			throw null;
		}
		for (int i = 0; i < terms.length - 1; i++) {
			if (terms[i][1] < terms[i + 1][1]) {
				throw new RuntimeException();
			}
		}
		int max = 0;
		for (int i = 0; i < terms.length; i++) {
			if (max < terms[i][1]) {
				max = terms[i][1];
			}
			if ((terms[i][1]) < 0 || max != terms[0][1]) {
				throw null;
			}
		}
		if (poly != 'A' && poly != 'B' && poly != 'C' && poly != 'a' && poly != 'b' && poly != 'c') {
			throw new RuntimeException();
		}

		int rows = terms.length;

		DoublyLinkedList list = new DoublyLinkedList();
		int index = 0;
		sorting(terms, rows);
		for (int i = 0; i < rows; i++) {
			int exp = terms[i][1];
			if (exp == index) {
				list.add(index, terms[i][0]);
				index++;
			} else {
				while (exp != index) {
					list.add(index, 0);
					index++;
				}
				list.add(index, terms[i][0]);

				index++;

			}
		}
		if (poly == 'A' || poly == 'a') {
			A = list;

		} else {
			if (poly == 'B' || poly == 'b') {
				B = list;

			} else {
				if (poly == 'C' || poly == 'c') {
					C = list;

				}

			}
		}

	}

	/**.
	 * Print the polynomial in human readable representation
	 * 
	 * @param poly
	 *            name of the polynomial
	 * @return polynomial in the form like 27x^2+x-1
	 */
	public String print(final char poly) {
		DoublyLinkedList p = new DoublyLinkedList();
		String polynomial = "", word = null;
		Node temp = null;
		if (poly == 'A' || poly == 'a') {
			temp = A.tail.getPrev();
			p = A;
		} else if (poly == 'B' || poly == 'b') {
			temp = B.tail.getPrev();
			p = B;
		} else if (poly == 'C' || poly == 'c') {
			temp = C.tail.getPrev();
			p = C;
		} else if (poly == 'R' || poly == 'r') {
			temp = R.tail.getPrev();
			p = R;
		} else {
			throw null;
		}
		if (p.isEmpty()) {
			return null;
		}
		int counter = p.size() - 1;
		if ((int) temp.getValue() != 0) {
			word = (int) temp.getValue() + "X^" + counter;
			polynomial = word + polynomial;
			counter--;
			temp = temp.getPrev();
		}
		while (temp != p.head) {
			if ((Integer) temp.getValue() != 0) {
				if (counter == 0) {
					if ((Integer) temp.getValue() < 0) {
						polynomial = polynomial + temp.getValue();
					} else {
						polynomial = polynomial + '+' + temp.getValue();
					}
				} else {
					if (counter == 1) {
						word = (Integer) temp.getValue() + "X^" + counter;
						if ((Integer) temp.getValue() < 0) {
							polynomial = polynomial + word;
						} else {
							polynomial = polynomial + "+" + word;
						}
					} else {
						word = (Integer) temp.getValue() + "X^" + counter;
						if ((Integer) temp.getValue() < 0) {
							polynomial = polynomial + word;
						} else {
							polynomial = polynomial + "+" + word;
						}
					}
				}
			}
			counter--;
			temp = temp.getPrev();
		}
		if (polynomial == "") {
			polynomial = "0^0";
		}
		if (polynomial.charAt(0) == '+') {
			polynomial = polynomial.substring(1);
		}
		return polynomial;
	}

	/**.
	 * Clear the polynomial
	 * 
	 * @param poly
	 *            name of the polynomial
	 */
	public void clearPolynomial(final char poly) {
		if (poly == 'A' || poly == 'a') {
			if (A.isEmpty()) {
				throw new RuntimeException();
			}
			A.clear();

		} else {
			if (poly == 'B' || poly == 'b') {
				if (B.isEmpty()) {
					throw new RuntimeException();
				}
				B.clear();

			} else {
				if (poly == 'C' || poly == 'c') {
					if (C.isEmpty()) {
						throw new RuntimeException();
					}
					C.clear();

				} else if (poly == 'R' || poly == 'r') {
					if (R.isEmpty()) {
						throw new RuntimeException();
					}
					R.clear();

				} else {
					throw null;
				}
			}
		}

	}

	/**.
	 * Evaluate the polynomial
	 * 
	 * @param poly
	 *            name of the polynomial
	 * @param the
	 *            polynomial constant value
	 * @return the value of the polynomial
	 */
	public float evaluatePolynomial(final char poly, final float value) {
		float result = 0, counter = 0;
		DoublyLinkedList p = new DoublyLinkedList();
		Node temp = null;
		if (poly == 'A' || poly == 'a') {
			temp = A.head.getNext();
			p = A;
		} else if (poly == 'B' || poly == 'b') {
			temp = B.head.getNext();
			p = B;
		} else if (poly == 'C' || poly == 'c') {
			temp = C.head.getNext();
			p = C;
		} else if (poly == 'R' || poly == 'r') {
			temp = R.head.getNext();
			p = R;
		} else {
			throw new RuntimeException();
		}
		if (temp == null) {
			throw new RuntimeException();
		}

		while (temp.getNext() != p.tail) {
			if ((Integer) temp.getValue() != 0) {
				result += (Integer) temp.getValue() * Math.pow(value, counter);
			}
			temp = temp.getNext();
			counter++;
		}
		result += (Integer) temp.getValue() * Math.pow(value, counter);
		return result;
	}

	/**.
	 * Add two polynomials
	 * 
	 * @param poly1
	 *            first polynomial
	 * @param poly2
	 *            second polynomial
	 * @return the result polynomial
	 */
	public int[][] add(final char poly1, final char poly2) {
		DoublyLinkedList p1 = new DoublyLinkedList();
		DoublyLinkedList p2 = new DoublyLinkedList();
		DoublyLinkedList sum = new DoublyLinkedList();
		Node temp1 = null, temp2 = null;

		int result, counter = 0;
		if (poly1 == 'A' || poly1 == 'a') {
			temp1 = A.head.getNext();
			p1 = A;
		} else if (poly1 == 'B' || poly1 == 'b') {
			temp1 = B.head.getNext();
			p1 = B;
		} else if (poly1 == 'C' || poly1 == 'c') {
			temp1 = C.head.getNext();
			p1 = C;
		} else if (poly1 == 'R' || poly1 == 'r') {
			p1 = R;
			temp1 = R.head.getNext();
		} else {
			throw new RuntimeException();
		}

		if (poly2 == 'A' || poly2 == 'a') {
			temp2 = A.head.getNext();
			p2 = A;
		} else if (poly2 == 'B' || poly2 == 'b') {
			temp2 = B.head.getNext();
			p2 = B;
		} else if (poly2 == 'C' || poly2 == 'c') {
			temp2 = C.head.getNext();
			p2 = C;
		} else if (poly2 == 'R' || poly2 == 'r') {
			p2 = R;
			temp2 = R.head.getNext();
		} else {
			throw new RuntimeException();
		}
		if (temp1 == null || temp2 == null || p1.isEmpty() || p2.isEmpty()) {
			throw null;
		}

		int size1 = p1.size(), size2 = p2.size();
		while (counter < size1 && counter < size2) {
			result = (Integer) temp1.getValue() + (Integer) temp2.getValue();
			sum.add(result);
			counter++;
			temp1 = temp1.getNext();
			temp2 = temp2.getNext();
		}
		if (counter < size1) {
			while (counter < size1) {
				sum.add(temp1.getValue());
				temp1 = temp1.getNext();
				counter++;
			}
		} else {
			while (counter < size2) {
				sum.add(temp2.getValue());
				temp2 = temp2.getNext();
				counter++;
			}
		}

		R = sum;

		int[][] temp = new int[sum.size()][2];
		int j = 0;
		for (int i = sum.size() - 1; i >= 0; i--) {
			if ((int) sum.get(i) != 0) {
				temp[j][0] = (int) sum.get(i);
				temp[j][1] = i;
				j++;
			}
		}

		int[][] re = new int[j][2];
		for (int i = 0; i < j; i++) {
			re[i][0] = temp[i][0];
			re[i][1] = temp[i][1];
		}
		return re;
	}

	/**.
	 * Subtract two polynomials
	 * 
	 * @param poly1
	 *            first polynomial
	 * @param poly2
	 *            second polynomial
	 * @return the result polynomial
	 */
	public int[][] subtract(final char poly1, final char poly2) {
		Node temp1 = null, temp2 = null;
		DoublyLinkedList o1 = new DoublyLinkedList();
		DoublyLinkedList o2 = new DoublyLinkedList();
		DoublyLinkedList o = new DoublyLinkedList();

		int result, counter = 0;
		if (poly1 == 'A' || poly1 == 'a') {
			temp1 = A.head.getNext();
			o1 = A;
		} else if (poly1 == 'B' || poly1 == 'b') {
			temp1 = B.head.getNext();
			o1 = B;
		} else if (poly1 == 'C' || poly1 == 'c') {
			temp1 = C.head.getNext();
			o1 = C;
		} else if (poly1 == 'R' || poly1 == 'r') {
			o1 = R;
			temp1 = R.head.getNext();
		} else {
			throw new RuntimeException();
		}

		if (poly2 == 'A' || poly2 == 'a') {
			temp2 = A.head.getNext();
			o2 = A;
		} else if (poly2 == 'B' || poly2 == 'b') {
			temp2 = B.head.getNext();
			o2 = B;
		} else if (poly2 == 'C' || poly2 == 'c') {
			temp2 = C.head.getNext();
			o2 = C;
		} else if (poly2 == 'R' || poly2 == 'r') {
			o2 = R;
			temp2 = R.head.getNext();
		} else {
			throw new RuntimeException();
		}
		if (temp1 == null || temp2 == null) {
			throw new RuntimeException();
		}
		if (o1 == o2) {
			R.clear();
			R.add(0);
			int arr[][] = new int[1][2];
			arr[0][0] = 0;
			arr[0][1] = 0;
			return arr;
		}
		int size1 = o1.size(), size2 = o2.size();
		while (counter < size1 && counter < size2) {
			result = (Integer) temp1.getValue() - (Integer) temp2.getValue();
			o.add(result);
			counter++;
			temp1 = temp1.getNext();
			temp2 = temp2.getNext();
		}
		if (counter < size1) {
			while (counter < size1) {
				o.add(temp1.getValue());
				temp1 = temp1.getNext();
				counter++;
			}
		} else {
			while (counter < size2) {
				o.add(-1 * (Integer) temp2.getValue());
				temp2 = temp2.getNext();
				counter++;
			}
		}
		R = o;

		int j = 0;
		int[][] arr = new int[o.size()][2];
		for (int i = o.size() - 1; i >= 0; i--) {
			if ((int) o.get(i) != 0) {
				arr[j][0] = (int) o.get(i);
				arr[j][1] = i;
				j++;
			}
		}

		int[][] sub = new int[j][2];
		for (int i = 0; i < j; i++) {
			sub[i][0] = arr[i][0];
			sub[i][1] = arr[i][1];
		}
		return sub;
	}

	/**.
	 * Multiply two polynomials
	 * 
	 * @param poly1
	 *            first polynomial
	 * @param poly2
	 *            second polynomial
	 * @return the result polynomial
	 */
	public int[][] multiply(final char poly1, final char poly2) {
		DoublyLinkedList p1 = new DoublyLinkedList();
		DoublyLinkedList p2 = new DoublyLinkedList();
		DoublyLinkedList mult = new DoublyLinkedList();

		Node temp1 = null, temp2 = null;

		if (poly1 == 'A' || poly1 == 'a') {
			p1 = A;
			temp1 = A.head.getNext();
		} else if (poly1 == 'B' || poly1 == 'b') {
			p1 = B;
			temp1 = B.head.getNext();
		} else if (poly1 == 'C' || poly1 == 'c') {
			p1 = C;
			temp1 = C.head.getNext();

		} else if (poly1 == 'R' || poly1 == 'r') {
			p1 = R;
			temp1 = R.head.getNext();
		} else {
			throw new RuntimeException();
		}

		if (poly2 == 'A' || poly2 == 'a') {
			p2 = A;
			temp2 = A.head.getNext();

		} else if (poly2 == 'B' || poly2 == 'b') {
			p2 = B;
			temp2 = B.head.getNext();

		} else if (poly2 == 'C' || poly2 == 'c') {
			p2 = C;
			temp2 = C.head.getNext();

		} else if (poly2 == 'R' || poly2 == 'r') {
			p2 = R;
			temp2 = R.head.getNext();
		} else {
			throw new RuntimeException();
		}

		if (temp1 == null || temp2 == null) {
			throw new RuntimeException();
		}

		int exp, co;
		int counter = 0;
		int size1 = p1.size();
		int size2 = p2.size();

		for (int i = 0; i < size1; i++) {
			counter = 0;
			temp2 = p2.head.getNext();
			for (int j = 0; j < size2; j++) {

				exp = i + j;
				co = (Integer) temp1.getValue() * (Integer) temp2.getValue();

				while (counter != exp) {
					if (counter < mult.size()) {
						counter++;
					} else {
						mult.add(0);
						counter++;
					}
				}
				if (counter < mult.size()) {
					mult.set(counter, co + (Integer) mult.get(counter));
					counter++;
				} else {
					mult.add(counter, co);
					counter++;
				}

				temp2 = temp2.getNext();

			}

			temp1 = temp1.getNext();

		}
		R = mult;

		int[][] temp = new int[mult.size()][2];
		int indexI = 0;
		for (int k = mult.size() - 1; k >= 0; k--) {
			if ((int) mult.get(k) != 0) {
				temp[indexI][0] = (int) mult.get(k);
				temp[indexI][1] = k;
				indexI++;
			}
		}

		int[][] re = new int[indexI][2];
		for (int k = 0; k < indexI; k++) {
			re[k][0] = temp[k][0];
			re[k][1] = temp[k][1];
		}

		return re;
	}

}
