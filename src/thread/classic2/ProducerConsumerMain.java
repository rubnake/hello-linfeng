package thread.classic2;

import java.util.Stack;


public class  ProducerConsumerMain {
	public static void main(String[] args) {
		Stack ss = new Stack();
		
		Product p = new Product(ss);
		Consumer c = new Consumer(ss);
		new Thread(p).start();
		new Thread(p).start();
		new Thread(p).start();
		new Thread(c).start();
	}
}
