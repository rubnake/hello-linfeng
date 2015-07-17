package thread.base_op;

import java.util.concurrent.locks.ReentrantLock;

public class LockReeTest implements Runnable{
	private ReentrantLock rl  = new ReentrantLock();
	private int b=0;
	
	public void run(){
		rl.lock();
		System.out.println("1111");
		b++;
		System.out.println("b is "+b);
		rl.unlock();
	}
	
	public int getB(){
		return this.b;
	}
	
	public static void main(String[] args) {
		LockReeTest a = new LockReeTest();
		Thread b = new Thread(a);
		Thread c = new Thread(a);
		b.start();
		c.start();
	}
	
}
