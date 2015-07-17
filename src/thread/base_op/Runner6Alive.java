package thread.base_op;

public class Runner6Alive  extends Thread {
	public void run() {
		System.out.println("run:\t"+Thread.currentThread().isAlive());
		for(int i=0;i<1000;i++) {
			System.out.println("SubThread: " + i);
		}
	}
}
