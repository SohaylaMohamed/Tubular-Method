package tubular;

import eg.edu.alexu.csd.datastructure.linkedList.cs18_32.DoublyLinkedList;
import eg.edu.alexu.csd.datastructure.linkedList.cs18_32.SinglyLinkedList;
import eg.edu.alexu.csd.datastructure.queue.cs32.QueueLinked;

public class Temp {
	
	SinglyLinkedList checkVisited = new SinglyLinkedList();
	SinglyLinkedList PI = new SinglyLinkedList();
	SinglyLinkedList PIcov = new SinglyLinkedList();
	public String canBeGrouped(String s1, String s2, int numOfVariables) {
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

	public void grouping(DoublyLinkedList groups,DoublyLinkedList covers,int numOfVariables) {

		QueueLinked last = new QueueLinked();
		QueueLinked lastCov = new QueueLinked();
		int size = groups.sizeS();
		
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
				if(!checkVisited.isEmpty()){
				for(int x=0; x<size1; x++){
					lastStage[x] =(boolean) checkVisited.get(x);
				}
				}
				checkVisited.clear();
				QueueLinked temp = new QueueLinked();
				QueueLinked cov1 = new QueueLinked();
				QueueLinked gs1 = new QueueLinked();
				QueueLinked gs2 = new QueueLinked();
				gs2 = (QueueLinked) covers.get(i + 1);

				gs1 = (QueueLinked) covers.get(i);
				QueueLinked cov2 = new QueueLinked();

				int visit;
				int num;

				for (int i1 = 0; i1 < size1; i1++) {
					String s1 = (String) Q1.dequeue();
					visit = 0;

					cov1 = (QueueLinked) gs1.dequeue();
					for (int i2 = 0; i2 < size2; i2++) {
						cov2 = (QueueLinked) gs2.getAtIndex(i2);

						String s2 = (String) Q2.dequeue();
						String s = canBeGrouped(s1, s2 , numOfVariables);
						if (s != s1) {
							visit = 1;
							checkVisited.add(true);
							lastGr[i2] = true;
							if (contains(temp, s)) {
								continue;
							} else {
								QueueLinked tmp = new QueueLinked();
								for(int c =0; c< cov1.size(); c++){
									tmp.enqueue(cov1.getAtIndex(c));
								}

								for (int m = 0; m < cov2.size(); m++) {
									num = (int) cov2.getAtIndex(m);
									
									if (!contains(cov1, num)) {
										
										tmp.enqueue(num);
										gs1.enqueue(tmp);
									}
								}
								temp.enqueue(s);

							}
							

						}
						checkVisited.add(false);
						

						Q2.enqueue(s2);
					}
					if (visit == 0 && !lastStage[i1] ) {
						PI.add(s1);
						lastStage[i1]=true;
						PIcov.add(cov1);
					}

				}
				if(i+1 == size -1){
				/**for (int l = 0; l < lastGr.length; l++) {
					if (!lastGr[l]) {
						QueueLinked tmp = new QueueLinked();
						String s2 = (String) Q2.getAtIndex(l);
						last.enqueue(s2);
						cov2 = (QueueLinked) gs2.getAtIndex(l);
						for (int m = 0; m < cov2.size(); m++) {
							num = (int) cov2.getAtIndex(m);
							tmp.enqueue(num);
						}
						lastCov.enqueue(tmp);

					}
				}*/
				for(int l = 0; l<lastGr.length; l++){
					if(!lastGr[l]){
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
		
		if(size > 1){
			groups.remove(groups.sizeS()-1);
			covers.remove(covers.sizeS()-1);
			int count =0;
			for(int n =0; n<size-1; n++){
				if(!groups.get(n).equals(0)){
				count++;
			}
			}
			if(count == 1){
				return;
			}else{
			if (!last.isEmpty()) {
				groups.add(last);
				covers.add(lastCov);
				
			}/*else{
				grouping();
			}*/
		    }
		}
	
	for (int i = 0; i < PI.size(); i++) {
			System.out.printf("%s \n", PI.get(i));
			System.out.printf(" covers \n");
			((QueueLinked)PIcov.get(i)).print();

		}
		
		for (int i = 0; i < groups.size(); i++) {
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

		}
		return;

	}
}
