package util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * 列表集合线程安全帮助类
 * 
 * @author llj
 *
 */
public class ListHelper<E> {
	
	LinkedList<E> list;
	
	public ListHelper() {
//		list = (LinkedList<E>) Collections.synchronizedList(new LinkedList<>());
		list = new LinkedList<>();
	}

	public E getFirst() {
		synchronized (list) {
			return list.getFirst();
		}
	}
	
	public E getLast() {
		synchronized (list) {
			return list.getLast();
		}
	}
	
	public E get(int index) {
		synchronized (list) {
			E e = list.get(index);
			return e;
		}
	}

	public void add(E e) {
		synchronized (list) {
			list.add(e);
		}
	}

	public void add(int indext, E e) {
		synchronized (list) {
			list.add(indext, e);
		}
	}

	public void addAll(Collection<E> arg0) {
		synchronized (list) {
			list.addAll(arg0);
		}
	}

	public void addAll(int index, Collection<E> arg0) {
		synchronized (list) {
			list.addAll(index, arg0);
		}
	}

	public void remove(int index) {
		synchronized (list) {
			list.remove(index);
		}
	}

	public void remove(E e) {
		synchronized (list) {
			list.remove(e);
		}
	}
	
	public void removeFirst() {
		synchronized (list) {
			list.removeFirst();
		}
	}
	
	public void removeLast() {
		synchronized (list) {
			list.removeLast();
		}
	}

	public void clear() {
		synchronized (list) {
			list.clear();
		}
	}
	
	public boolean isEmpty() {
		synchronized (list) {
			return list.isEmpty();
		}
	}
	
	public int size() {
		synchronized (list) {
			return list.size();
		}
	}

}
