package thread.base_op;


public class TestYield {
/*
 * yield()
　　yield() 方法与sleep() 方法相似，只是它不能由用户指定线程暂停多长时间。按照SUN的说法：
sleep方法可以使低优先级的线程得到执行的机会，当然也可以让同优先级和高优先级的线程有执行的机会。而yield()
方法只能使同优先级的线程有执行的机会。
 */
	public class ThreadTest implements Runnable {
		public void run() {
			for (int k = 0; k < 10; k++) {
				if (k == 5 && Thread.currentThread().getName().equals("t1")) {
					Thread.yield();
				}
				System.out
						.println(Thread.currentThread().getName() + " : " + k);
			}
		}

	}

	public static void main(String[] args) {
		Runnable r = new TestYield().new ThreadTest();
		Thread t1 = new Thread(r, "t1");
		Thread t2 = new Thread(r, "t2");
		t1.setPriority(Thread.MAX_PRIORITY);
//		t2.setPriority(Thread.MIN_PRIORITY);
		t1.start();
		t2.start();
	}

}

