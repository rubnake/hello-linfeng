package web.sync.collection;

import java.util.Vector;

public class Test_block {

	public static void main(String[] args) {

		final Vector<PromInfoWork> todo = new Vector<PromInfoWork>();
		for (int i = 0; i < 2; i++) {
			PromInfoWork one = new PromInfoWork(i);
			todo.add(one);
		}

		for (int j = 0; j < 10; j++) {
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					ClmAsynContainer.getInstance().dealWork(todo);
				}
			}).start();
		}

	}
}
