package thread.deadlock;

public class TestSync implements Runnable {
	  Timer timer = new Timer();
	  
	  public void run(){
	    timer.add(Thread.currentThread().getName());
	  }
	  
	  
	  public static void main(String[] args) {
		    TestSync test = new TestSync();
		    Thread t1 = new Thread(test);
		    Thread t2 = new Thread(test);
		    t1.setName("t1"); 
		    t2.setName("t2");
		    t1.start(); 
		    t2.start();
		  }
	}