package thread.classic2;

import java.util.Stack;

import thread.classic.WoTou;

public class Consumer implements Runnable{

	Stack ss = null;
	
	Consumer (Stack a){
		this.ss = a;
	}
	
	public void run() {
		for (int i = 0; i < ss.size(); i++) {
			WoTou wt = (WoTou) ss.pop();
			System.out.println("ฯ๛ทัมห: " + wt);
			try {
				Thread.sleep((int) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
