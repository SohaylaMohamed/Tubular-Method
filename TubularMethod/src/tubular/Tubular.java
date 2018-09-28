package tubular;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

import eg.edu.alexu.csd.datastructure.linkedList.cs18_32.DoublyLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.cs18_32.SinglyLinkedList;
import eg.edu.alexu.csd.datastructure.queue.cs32.QueueLinked;

public class Tubular {

	Scanner s = new Scanner(System.in);
	DoublyLinkedList minterms = new DoublyLinkedList();
	DoublyLinkedList dontCares = new DoublyLinkedList();
	DoublyLinkedList compination = new DoublyLinkedList();

	int numOfVariables = 0;
	int finish = 0;
	SinglyLinkedList checkVisited = new SinglyLinkedList();
	DoublyLinkedList covers = new DoublyLinkedList();
	DoublyLinkedList groups = new DoublyLinkedList();
	DoublyLinkedList PI = new DoublyLinkedList();
	DoublyLinkedList PIcov = new DoublyLinkedList();
	DoublyLinkedList essentialPI = new DoublyLinkedList();
	DoublyLinkedList outputs = new DoublyLinkedList();


	void sorting(final DoublyLinkedList minterms2) {
		int minIndex;
		int index;
		for (index = 0; index < minterms2.size() - 1; index++) {
			minIndex = getMinIndex(minterms2, index, minterms2.size() - 1);
			swap(minterms2, index, minIndex);
		}
	}

	/**
	 * . gets the min value's index
	 * 
	 * @param minterms2
	 *            an array
	 * @param first
	 *            first index
	 * @param last
	 *            last index
	 * @return min index of an array
	 * 
	 */
	int getMinIndex(final DoublyLinkedList minterms2, final int first, final int last) {
		int minIndex;
		int index;
		minIndex = first;
		for (index = first + 1; index <= last; index++) {
			if ((Integer) minterms2.get(index) < (Integer) minterms2.get(minIndex)) {
				minIndex = index;
			}
		}
		return minIndex;
	}

	/**
	 * . Swap two elements in an array
	 * 
	 * @param minterms2
	 *            an array
	 * @param a
	 *            first Row index
	 * @param b
	 *            second row index
	 */
	void swap(final DoublyLinkedList minterms2, final int a, final int b) {
		int temp1 = (Integer) minterms2.get(a);

		minterms2.set(a, minterms2.get(b));
		minterms2.set(b, temp1);

	}

	public int findGroup(String str) {
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '1') {
				count++;
			}
		}
		return count;
	}

	public String toBinary(int num, int bs) {
		String b = Integer.toBinaryString(num);
		if (b.length() < bs) {
			String temp = b;
			int i = 0;
			b = "";
			while (i < (bs - temp.length())) {
				b = b + '0';
				i++;
			}
			b = b + temp;
		}
		return b;

	}
	public void scanMinterms(){
		int m = 0;
		System.out.println("Enter The minterms , -1 to end");
		while (!s.hasNextInt()) s.next();
		m = s.nextInt();
		while (m >= 0) {
			compination.add(m);
			minterms.add(m);
			while (!s.hasNextInt()) s.next();

			m = s.nextInt();
			while (minterms.contains(m)) {
				while (!s.hasNextInt()) s.next();

				m = s.nextInt();
			}
		}
		if(minterms.isEmpty()){
			System.out.println("You didn't enter any minterms!");
			input();
			return;

		}
	}
	
	public void scanDontCares(){
		int m = 0;
		System.out.println("Enter The don't cares , -1 to end");
		while (!s.hasNextInt()) s.next();

		m = s.nextInt();
		while (minterms.contains(m)) {
			System.out.println("You've already entered this minterm");
			System.out.println("Enter The don't cares , -1 to end");
			while (!s.hasNextInt()) s.next();

			m = s.nextInt();

		}
		while (m >= 0) {

			dontCares.add(m);
			compination.add(m);
			while (!s.hasNextInt()) s.next();

			m = s.nextInt();
			while (dontCares.contains(m) || minterms.contains(m)) {
				System.out.println("You've already entered this minterm");
				System.out.println("Enter The don't cares , -1 to end");
				while (!s.hasNextInt()) s.next();
				m = s.nextInt();
			}
		}
		if(compination.sizeS()==1){
			outputs.add(toBinary((int)compination.get(0),numOfVariables));
			print();
			return;
		}
		 preparing();
	}
	
	public void preparing(){
		int element = -1;
		sorting(minterms);
		sorting(dontCares);
		sorting(compination);

		element = (Integer) compination.get(compination.sizeS() - 1);

		numOfVariables = (int) (Math.floor((Math.log((double) element) / Math.log(2)) + 1));

		int j = 0;

		for (int i = 0; i < compination.sizeS(); i++) {
			String temp = toBinary((Integer) compination.get(i), numOfVariables);
			int index = findGroup(temp);
			while (index > groups.sizeS()) {
				groups.add(j, 0);
				covers.add(j, 0);
				j++;

			}
			QueueLinked Q = new QueueLinked();
			QueueLinked gs = new QueueLinked();
			QueueLinked cov = new QueueLinked();
			if (groups.isEmpty() || index == groups.sizeS()) {
				Q.enqueue(temp);
				cov.enqueue((int) compination.get(i));
				gs.enqueue(cov);
				groups.add(index, Q);
				covers.add(index, gs);
				j++;
			} else if (!groups.get(index).equals(0)) {
				Q = (QueueLinked) groups.get(index);
				Q.enqueue(temp);
				gs = (QueueLinked) covers.get(index);

				cov.enqueue((int) compination.get(i));
				gs.enqueue(cov);

				groups.set(index, Q);
				covers.set(index, gs);

			} else {
				Q.enqueue(temp);
				groups.set(index, Q);
				cov.enqueue((int) compination.get(i));
				gs.enqueue(cov);
				covers.set(index, gs);

			}

		}
	/**	for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).equals(0)) {
				System.out.println(groups.get(i));
			} else {
				System.out.printf("group %d \n", i);

				QueueLinked Q = new QueueLinked();
				Q = (QueueLinked) groups.get(i);
				Q.print();
				QueueLinked gs = new QueueLinked();
				gs = (QueueLinked) covers.get(i);
				for (int k = 0; k < gs.size(); k++) {
					System.out.printf("element %d \n covers \n", k);
					QueueLinked cov = new QueueLinked();
					cov = (QueueLinked) gs.getAtIndex(k);
					cov.print();
				}
			}

		}*/
		grouping();
		minimization();
		
	}
	public void input() {

		
		System.out.println("Please note that only numeric inputs will be taken into consideration.");
		scanMinterms();
		scanDontCares();

	}

	public String canBeGrouped(String s1, String s2) {
		int count = 0;
		int diff = 0;
		for (int i = 0; i < numOfVariables; i++) {
			if (s1.charAt(i) != s2.charAt(i)) {
				count++;
				diff = i;
			}
		}

		if (count == 1) {
			StringBuilder s = new StringBuilder(s1);
			s.setCharAt(diff, '-');
			s1 = s.toString();

		}
		return s1;
	}

	public boolean contains(QueueLinked q, Object o) {
		for (int i = 0; i < q.size(); i++) {
			if (q.getAtIndex(i).equals(o)) {
				return true;
			}
		}
		return false;
	}

	public void grouping() {

		int size = groups.sizeS();
		int group = 0;
		for (int i = 0; i < size - 1; i++) {
			if (groups.get(i).equals(0) || groups.get(i + 1).equals(0)) {
				continue;
			} else {

				QueueLinked Q1 = (QueueLinked) groups.get(i);

				int size1 = Q1.size();

				QueueLinked Q2 = (QueueLinked) groups.get(i + 1);
				int size2 = Q2.size();

				boolean[] lastGr = new boolean[size2];
				boolean[] lastStage = new boolean[size1];
				if (group > 0) {
					for (int x = 0; x < size1; x++) {
						lastStage[x] = (boolean) checkVisited.get(x);
					}
				}
				checkVisited.clear();
				group++;
				QueueLinked temp = new QueueLinked();
				QueueLinked cov1 = new QueueLinked();
				QueueLinked gs1 = new QueueLinked();
				QueueLinked gs2 = new QueueLinked();
				QueueLinked cov2 = new QueueLinked();

				gs2 = (QueueLinked) covers.get(i + 1);
				gs1 = (QueueLinked) covers.get(i);

				int visit;
				int num;
				for (int x = 0; x < size2; x++) {
					checkVisited.add(false);
				}
				for (int i1 = 0; i1 < size1; i1++) {
					String s1 = (String) Q1.dequeue();
					visit = 0;

					cov1 = (QueueLinked) gs1.dequeue();
					for (int i2 = 0; i2 < size2; i2++) {
						cov2 = (QueueLinked) gs2.getAtIndex(i2);

						String s2 = (String) Q2.dequeue();
						String s = canBeGrouped(s1, s2);
						if (s != s1) {
							visit = 1;
							checkVisited.set(i2, true);
							lastGr[i2] = true;
							if (contains(temp, s)) {
								Q2.enqueue(s2);
								continue;
							} else {
								QueueLinked tmp = new QueueLinked();
								for (int c = 0; c < cov1.size(); c++) {
									tmp.enqueue(cov1.getAtIndex(c));
								}

								for (int m = 0; m < cov2.size(); m++) {
									num = (int) cov2.getAtIndex(m);

									if (!contains(tmp, num)) {

										tmp.enqueue(num);

									}
								}
								gs1.enqueue(tmp);
								temp.enqueue(s);

							}

						}

						Q2.enqueue(s2);
					}
					if (visit == 0 && !lastStage[i1]) {
						PI.add(s1);
						lastStage[i1] = true;
						PIcov.add(cov1);
					}

				}
				if (i + 1 == size - 1) {

					for (int l = 0; l < checkVisited.size(); l++) {
						if (!(boolean) checkVisited.get(l)) {
							PI.add(Q2.getAtIndex(l));
							QueueLinked tmp = new QueueLinked();

							cov2 = (QueueLinked) gs2.getAtIndex(l);
							for (int m = 0; m < cov2.size(); m++) {
								num = (int) cov2.getAtIndex(m);
								tmp.enqueue(num);
							}
							PIcov.add(tmp);
						}
					}
				}

				covers.set(i, gs1);
				groups.set(i, temp);

			}
		}
		int count = 0;
		for (int n = 0; n < size; n++) {
			if (!groups.get(n).equals(0)) {
				count++;
			}
		}
		if (count > 1) {
			groups.remove(groups.sizeS() - 1);
			covers.remove(covers.sizeS() - 1);
			count--;

		}
		if (count == 1) {
			return;
		} else {

			grouping();
		}

		return;

	}

	public boolean covers(QueueLinked cov1, QueueLinked cov2, boolean[] covered) {
		for (int i = 0; i < cov2.size(); i++) {
			int num = (int) cov2.getAtIndex(i);
			if (minterms.contains(num)) {
				int index = getIndex(num);
				if (!covered[index]) {
					if (!contains(cov1, num)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public int rowDominates(int row1, int row2, boolean[] taken, boolean[] covered) {
		QueueLinked cov1 = (QueueLinked) PIcov.get(row1);
		QueueLinked cov2 = (QueueLinked) PIcov.get(row2);
		int size1 = 0;
		int size2 = 0;
		for (int i = 0; i < cov1.size(); i++) {
			int num = (int) cov1.getAtIndex(i);
			if (minterms.contains(num)) {
				int index = getIndex(num);
				if (!covered[index]) {
					size1++;
				}
			}
		}
		for (int i = 0; i < cov2.size(); i++) {
			int num = (int) cov2.getAtIndex(i);
			if (minterms.contains(num)) {
				int index = getIndex(num);

				if (!covered[index]) {
					size2++;
				}
			}
		}
		if (size1 >= size2) {
			if (covers(cov1, cov2, covered)) {
				taken[row2] = true;
				return row1;
			}
		} else {
			if (covers(cov2, cov1, covered)) {
				taken[row1] = true;

				return row2;
			}
		}
		return row1;
	}

	public boolean coveredBy(QueueLinked p1, QueueLinked p2, boolean[] taken, DoublyLinkedList primeImplicants) {
		for (int i = 0; i < p2.size(); i++) {
			int index = (int) p2.getAtIndex(i);
			if (!taken[index]) {
				if (!contains(p1, index)) {
					return false;
				}
			}
		}
		return true;
	}

	public int colDominates(int col1, int col2, boolean[] taken, boolean[] covered, DoublyLinkedList primeImplicants) {
		QueueLinked p1 = (QueueLinked) primeImplicants.get(col1);
		QueueLinked p2 = (QueueLinked) primeImplicants.get(col2);
		int size1 = 0;
		int size2 = 0;
		for (int i = 0; i < p1.size(); i++) {
			int index = (int) p1.getAtIndex(i);
			if (!taken[index]) {
				size1++;
			}
		}
		for (int i = 0; i < p2.size(); i++) {

			int index = (int) p2.getAtIndex(i);
			if (!taken[index]) {
				size2++;
			}
		}
		if (size1 >= size2) {
			if (coveredBy(p1, p2, taken, primeImplicants)) {
				covered[col1] = true;
				return col2;
			}
		} else {
			if (coveredBy(p2, p1, taken, primeImplicants)) {
				covered[col2] = true;
				return col1;
			}
		}
		return col1;
	}

	public int getIndex(int num) {
		for (int i = 0; i < minterms.sizeS(); i++) {
			if (num == (Integer) minterms.get(i)) {
				return i;
			}
		}
		return -1;
	}

	public void minimization() {
		int nRow = PI.sizeS();
		int nCol = minterms.sizeS();
		boolean[] covered = new boolean[nCol];
		boolean[] taken = new boolean[nRow];

		boolean[][] table = new boolean[nRow][nCol];

		for (int p = 0; p < nRow; p++) {
			QueueLinked cov = new QueueLinked();
			cov = (QueueLinked) PIcov.get(p);
			for (int c = 0; c < cov.size(); c++) {
				int num = (Integer) cov.getAtIndex(c);
				if (!dontCares.isEmpty()) {
					if (!dontCares.contains(num)) {
						int index = getIndex(num);
						table[p][index] = true;
					}
				} else {
					int index = getIndex(num);
					table[p][index] = true;
				}
			}
		}
		int count = 0;
		for (int col = 0; col < nCol; col++) {
			DoublyLinkedList indexCovered = new DoublyLinkedList();
			for (int row = 0; row < nRow; row++) {
				if (table[row][col]) {
					count++;
					indexCovered.add(row);
				}
			}
			if (count == 1) {
				covered[col] = true;
				if(!essentialPI.isEmpty()){
				if(!essentialPI.contains(PI.get((int) indexCovered.get(0)))){
				essentialPI.add(PI.get((int) indexCovered.get(0)));
				}
				}else{
					essentialPI.add(PI.get((int) indexCovered.get(0)));

				}
				taken[(int) indexCovered.get(0)] = true;
				for (int r = 0; r < nCol; r++) {
					if (table[(int) indexCovered.get(0)][r]) {
						covered[r] = true;
					}
				}
			}
			indexCovered.clear();
			count = 0;
		}
		for (int p = 0; p < nRow; p++) {
			int min = 0;
			for (int c = 0; c < nCol; c++) {
				if (!covered[c]) {
					if (table[p][c]) {
						min++;
					}
				}
			}
			if (min == 0) {
				taken[p] = true;
			}
		}

		DoublyLinkedList primeImplicants = new DoublyLinkedList();

		for (int col = 0; col < nCol; col++) {
			QueueLinked tmp = new QueueLinked();

			for (int row = 0; row < nRow; row++) {
				if (table[row][col]) {
					tmp.enqueue(row);
				}
			}

			primeImplicants.add(tmp);
		}
		

		boolean stop = false;
		while (!stop) {
			int rowIndexDom;
			int first = 0;
			int colIndexDom;
			boolean[] coveredB = covered;
			boolean[] takenB = taken;
			if(!(taken.length ==0)){
			while (taken[first]) {
				first++;
				if (first >= taken.length) {
					break;
				}
			}
			for (int row = first; row < nRow; row++) {
				rowIndexDom = first;

				if (taken[row]) {
					continue;
				}
				for (int second = row + 1; second < nRow; second++) {
					if (taken[second]) {
						continue;
					} else {
						rowIndexDom = rowDominates(rowIndexDom, second, taken, covered);
					}

				}

			}
			}
			if(!(covered.length==0)){
			first = 0;
			while (covered[first]) {

				first++;
				if (first >= covered.length) {
					break;
				}
			}

			for (int col = first; col < nCol; col++) {
				colIndexDom = first;
				if (covered[col]) {
					continue;
				}
				for (int second = col + 1; second < nCol; second++) {
					if (covered[second]) {
						continue;
					} else {
						colIndexDom = colDominates(colIndexDom, second, taken, covered, primeImplicants);
					}

				}

			}

			if (covered == coveredB && taken == takenB) {
				stop = true;
			}
			}
		}
		for (int col = 0; col < nCol; col++) {
			DoublyLinkedList indexCovered = new DoublyLinkedList();
			if (!covered[col]) {
				for (int row = 0; row < nRow; row++) {
					if (!taken[row]) {
						if (table[row][col]) {
							count++;
							indexCovered.add(row);
						}
					}
				}
				if (count == 1) {
					covered[col] = true;
					if(!essentialPI.isEmpty()){
						if(!essentialPI.contains(PI.get((int) indexCovered.get(0)))){
						essentialPI.add(PI.get((int) indexCovered.get(0)));
						}
						}else{
							essentialPI.add(PI.get((int) indexCovered.get(0)));

						}

					taken[(int) indexCovered.get(0)] = true;
					for (int r = 0; r < nCol; r++) {
						if (table[(int) indexCovered.get(0)][r]) {
							covered[r] = true;
						}
					}
				}
			}
			indexCovered.clear();
			count = 0;
		}
		DoublyLinkedList petrick = new DoublyLinkedList();
		char[] rows = new char[nRow];
		char rep = 'A';
		for (int i = 0; i < nRow; i++) {
			rows[i] = rep;
			rep = (char) (rep + 1);
		}
		for (int col = 0; col < nCol; col++) {
			StringBuilder s = new StringBuilder();
			if (!covered[col]) {
				for (int row = 0; row < nRow; row++) {
					if (!taken[row]) {
						if (table[row][col]) {
							s.append(rows[row]);
							s.append('+');
						}
					}

				}
			}
			if (s.length() == 0) {
				continue;
			} else {
				String temp = s.toString();
				temp = temp.substring(0, temp.length() - 1);
				petrick.add(temp);
			}
		}

		for (int p = 0; p < petrick.sizeS() - 1; p++) {

			for (int x = p + 1; x < petrick.sizeS(); x++) {

				String str = checkCommon((String) petrick.get(p), (String) petrick.get(x));
				if (str == (String) petrick.get(p)) {
					continue;
				} else {

					petrick.set(p, str);
					petrick.remove(x);
				}

			}

		}

		while (petrick.sizeS() > 1) {
			for (int p = 0; p < petrick.sizeS() - 1; p++) {

				for (int x = p + 1; x < petrick.sizeS(); x++) {

					String str = distribution((String) petrick.get(p), (String) petrick.get(x));

					petrick.set(p, str);
					petrick.remove(x);

				}

			}
		}
		if (!petrick.isEmpty()) {
			String finalPetrick = (String) petrick.get(0);
			String[] str1 = finalPetrick.split("\\+");
			Arrays.sort(str1, (a, b) -> Integer.compare(a.length(), b.length()));
			DoublyLinkedList options = new DoublyLinkedList();
			finalPetrick = simplify(str1);
			str1 = finalPetrick.split("\\+");
			Arrays.sort(str1, (a, b) -> Integer.compare(a.length(), b.length()));
			int min = str1[0].length();
			for (int i = 0; i < str1.length; i++) {
				if (str1[i].length() > min) {
					break;
				} else {
					QueueLinked tmp = new QueueLinked();
					for (int j = 0; j < str1[i].length(); j++) {
						char temp = str1[i].charAt(j);
						int index = 0;
						for (int k = 0; k < rows.length; k++) {
							if (rows[k] == temp) {
								index = k;
								break;
							}
						}
						tmp.enqueue(PI.get(index));
					}
					options.add(tmp);
				}
			}
			for (int i = 0; i < options.sizeS(); i++) {
				QueueLinked tmp = new QueueLinked();
				tmp = (QueueLinked) options.get(i);
				tmp.print();
			}

			for (int i = 0; i < options.sizeS(); i++) {
				StringBuilder tmp = new StringBuilder();
				for (int k = 0; k < essentialPI.sizeS(); k++) {
					tmp.append(literals((String) essentialPI.get(k)));
					tmp.append('+');
				}
				QueueLinked temp = new QueueLinked();
				temp = (QueueLinked) options.get(i);
				for (int j = 0; j < temp.size(); j++) {

					tmp.append(literals((String) temp.getAtIndex(j)));
					tmp.append("+");

				}
				outputs.add(tmp.toString().substring(0, tmp.length() - 1));
			}
			
		} else {

			StringBuilder tmp = new StringBuilder();
			for (int k = 0; k < essentialPI.sizeS(); k++) {
				tmp.append(literals((String) essentialPI.get(k)));
				tmp.append('+');
			}
			if(!outputs.isEmpty()){
			outputs.add(tmp.toString().substring(0, tmp.length() - 1));
			}

			
		}
		print();

	}

	public String literals(String s) {
		char[] literal = new char[numOfVariables];
		StringBuilder ret = new StringBuilder();
		char rep = 'A';
		for (int i = 0; i < literal.length; i++) {
			literal[i] = rep;
			rep = (char) (rep + 1);
		}
		for (int j = 0; j < s.length(); j++) {
			if (s.charAt(j) == '-') {
				continue;
			} else {
				if (s.charAt(j) == '0') {
					ret.append(literal[j] + "'");
				} else {
					ret.append(literal[j]);
				}
			}
		}
		String str = ret.toString();
		return str;
	}

	public String simplify(String[] ordered) {
		StringBuilder temp = new StringBuilder();
		String s1;
		for (int i = 0; i < ordered.length - 1; i++) {

			for (int j = i + 1; j < ordered.length; j++) {
				int count = 0;

				for (int l = 0; l < ordered[i].length(); l++) {
					StringBuilder t = new StringBuilder();
					t.append(ordered[i].charAt(l));
					if (ordered[j].contains(t)) {
						count++;
					}

				}
				if (count == ordered[i].length()) {
					ordered[j] = "0";
				}
			}
		}
		for (int i = 0; i < ordered.length; i++) {
			if (ordered[i] == "0") {
				continue;
			} else {
				temp.append(ordered[i]);
				temp.append("+");
			}
		}
		s1 = temp.toString();
		s1 = s1.substring(0, s1.length() - 1);
		return s1;

	}

	public String distribution(String s1, String s2) {
		String[] str1 = s1.split("\\+");
		String[] str2 = s2.split("\\+");
		StringBuilder s = new StringBuilder();

		for (int i = 0; i < str1.length; i++) {
			for (int j = 0; j < str2.length; j++) {

				StringBuilder temp = new StringBuilder();
				temp.append(str1[i] + str2[j]);

				char[] chars = new char[temp.length()];
				chars = temp.toString().toCharArray();
				Arrays.sort(chars);
				for (int k = 0; k < chars.length; k++) {
					if (k + 1 == chars.length) {
						s.append(chars[k]);
						break;
					}
					if (chars[k] == chars[k + 1]) {
						s.append(chars[k]);
						k = k + 1;
						if (k >= chars.length) {
							break;
						}
					} else {
						s.append(chars[k]);
					}
				}

				s.append("+");

			}
		}
		s1 = s.toString();
		s1 = s1.substring(0, s1.length() - 1);
		return s1;
	}

	public String checkCommon(String s1, String s2) {
		String[] str1 = s1.split("\\+");
		String[] str2 = s2.split("\\+");
		for (int i = 0; i < str1.length; i++) {
			for (int j = 0; j < str2.length; j++) {
				if (str1[i].matches(str2[j])) {
					StringBuilder s = new StringBuilder();
					s.append(str1[i]);
					for (int k = 0; k < str1.length; k++) {
						if (k == i) {
							continue;
						} else {
							for (int l = 0; l < str2.length; l++) {
								if (l == j) {
									continue;
								} else {
									s.append("+");
									if (str1[k].matches(str2[l])) {
										s.append(str1[k]);
									} else {
										s.append(str1[k] + str2[l]);

									}
								}
							}
						}

					}
					i = s1.length();
					s1 = s.toString();
					break;
				}
			}
		}
		return s1;
	}
	public void print(){
		outputs.print();	
	}
	

}
