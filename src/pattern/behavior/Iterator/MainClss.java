package pattern.behavior.Iterator;
import java.util.Iterator;


public class MainClss {
	public static void main(String[] args) {
		BookList bookList = new BookList();
		
		Book book1 = new Book("010203","Java���˼��",90);
		Book book2 = new Book("010204","Java�����ŵ���ͨ",60);
		
		bookList.addBook(book1);
		bookList.addBook(book2);
		
//		while(bookList.hasNext()) {
//			Book book = bookList.getNext();
//			book.display();
//		}
		
//		List<Book> bookDateList = bookList.getBookList();
//		for(int i = 0; i < bookDateList.size(); i++) {
//			Book book = bookDateList.get(i);
//			book.display();
//		}
		
		Iterator iter = bookList.Iterator();
		while(iter.hasNext()) {
			Book book = (Book) iter.next();
			book.display();
		}
		
		
	}
}
