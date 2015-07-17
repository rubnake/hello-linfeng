package thread.base_op;

import java.util.Date;

import util.UtilTools;


public class TestThread extends Thread {
	TestThread(String name){
		super(name);
	}
	
	@Override
	public void run(){
		
		for (int i=0;i<10;i++){
			System.out.println(UtilTools.dateFormat(new Date(),  "yyyy-MM-dd HH:mm:ss")+this.getName());
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return ;
			}
//			System.out.println(" linfeng :\t"+i);
		}
		
//		while (true){
//			System.out.println(UtilTools.dateFormat(new Date(),  "yyyy-MM-dd HH:mm:ss")+" finish linfeng :\t");
//			try {
//				sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return ;
//			}
//		}
	}
	
	public static void main(String[] args) {
		
		
		TestThread tt = new TestThread("11");
		tt.start();
		
		for (int i=0;i<10000;i++){
			System.out.println("main thread :\t"+i);
		}
	}
}
