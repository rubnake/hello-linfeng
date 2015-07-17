package thread.base_op;

public class TestJoin {
	
	
	public static void main(String[] args) {
		TestThread tt = new TestThread("linfeng");
		tt.start();
		
		
		try {
			//Thread.sleep(10000);
			tt.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//tt.interrupt();
		for (int i=0;i<100;i++){
			System.out.println("I am main thread finish!");
		}
		
	}
}
