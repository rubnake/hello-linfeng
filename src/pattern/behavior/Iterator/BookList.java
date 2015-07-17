package pattern.behavior.Iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class BookList {
	private List<Book> bookList;
	private int index;
	private Iterator iterator;
	
	public BookList() {
		bookList = new ArrayList<Book>();
	}
	
	//����鼮
	public void addBook(Book book) {
		bookList.add(book);
	}
	
	//ɾ���鼮
	public void deleteBook(Book book) {
		int bookIndex = bookList.indexOf(book);
		bookList.remove(bookIndex);
	}
	
//	//�ж��Ƿ�����һ����
//	public boolean hasNext() {
//		if(index >= bookList.size()) {
//			return false;
//		}
//		return true;
//	}
//	
//	//�����һ����
//	public Book getNext() {
//		return bookList.get(index++);
//	}
	
//	public List<Book> getBookList() {
//		return bookList;
//	}
	
	public Iterator Iterator() {
		return new Itr();
	}
	
	private class Itr implements Iterator{

		public boolean hasNext() {
			if(index >= bookList.size()) {
				return false;
			}
			return true;
		}

		public Object next() {
			return bookList.get(index++);
		}

		public void remove() {
			
		}
		
	}
	
}
