package pool;

public class TestObjectPool {
	
	
	public static void main(String[] args) {
		final ObjectPoolPer pool = new ObjectPoolPer();
		
		for (int i=0;i<10;i++){
			Thread one = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Element e = pool.getObject();
					
					System.out.println(Thread.currentThread().getName()+" is deal object :\t"+e.getId());
					
					pool.returnObject(e);
					
				}
			});
			one.start();
			Thread.yield();
		}
		
		
	}
	
	
}
