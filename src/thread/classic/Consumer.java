package thread.classic;

class Consumer implements Runnable {
	SyncStack ss = null;

	Consumer(SyncStack ss) {
		this.ss = ss;
	}

	public void run() {
		for (int i = 0; i < 40; i++) {
			WoTou wt = ss.pop();
			System.out.println("ฯ๛ทัมห: " + wt);
			try {
				Thread.sleep((int) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}