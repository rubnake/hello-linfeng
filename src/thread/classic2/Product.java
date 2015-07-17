package thread.classic2;

import java.util.Stack;

import thread.classic.WoTou;

public class Product implements Runnable{
	Stack ss = null;
	
	Product (Stack a){
		this.ss = a;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			WoTou wt = new WoTou(i);
			ss.push(wt);
			
			System.out.println("Éú²úÁË£º" + wt);
			try {
				Thread.sleep((int) (Math.random() * 200));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
