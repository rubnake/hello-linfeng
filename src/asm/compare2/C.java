package asm.compare2;

public class C {

	public static long timer;

	public void m() throws InterruptedException {
		timer -= System.currentTimeMillis();
		Thread.sleep(100);
		timer += System.currentTimeMillis();
	}

}