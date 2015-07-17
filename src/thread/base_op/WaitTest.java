package thread.base_op;
/*
 * t1线程最先执行。由于初始状态下shareVar为0，t1将使shareVar连续加1，当shareVar的值为5时，t1调用wait() 方法，
t1将处于休息状态，同时释放锁标志。这时t2得到了锁标志开始执行，shareVar的值已经变为5，所以t2直接输出shareVar的值，
然后再调用notify() 方法唤醒t1。t1接着上次休息前的进度继续执行，把shareVar的值一直加到10，由于此刻shareVar的值不为0，
所以t1将输出此刻shareVar的值，然后再调用notify() 方法，由于此刻已经没有等待锁标志的线程，所以此调用语句不起任何作用。
　　这个程序简单的示范了wait(), notify() 的用法，读者还需要在实践中继续摸索。
 */
public class WaitTest implements Runnable {
	public static int shareVar = 0;

	public synchronized void run() {
		if (shareVar == 0) {
			for (int i = 0; i < 10; i++) {
				shareVar++;
				if (shareVar == 5) {
					try {
						this.wait();
					} catch (Exception e) {
					}
				}
			}
		}
		System.out.println(Thread.currentThread().getName()+"   linfneg");
		if (shareVar != 0) {
			System.out.print(Thread.currentThread().getName());
			System.out.println(" shareVar = " + shareVar);
			this.notify();
		}
	}

	public static void main(String[] args) {
		Runnable r = new WaitTest();
		Thread t1 = new Thread(r, "t1");
		Thread t2 = new Thread(r, "t2");
		t1.start();
		t2.start();
	}
}
