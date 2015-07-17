package web.sync.collection;

import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import util.UtilTools;



public class ClmAsynContainer {
	private static ClmAsynContainer  instance = null;
	//线程池   
    private ExecutorService executorService;  
	
	private  ClmAsynContainer(){
		this.executorService = new ThreadPoolExecutor(1, 3,
                10L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>()); //超过4则不处理了
	}
	public static ClmAsynContainer getInstance(){
		if (instance == null){
			synchronized (ClmAsynContainer.class) {
				if (instance == null){
					instance = new ClmAsynContainer();
				}
				
			}
		}
		return instance;
	}
	
	public void dealWork(Vector<PromInfoWork> todo) {
		for (int i=0;i<todo.size();i++){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				this.executorService.execute(todo.get(i));
			} catch (Exception e) {
				e.printStackTrace();
				return ;
			}
			
		}
		
		//开一个监视线程判断是否完成。
		FinishMonitor finishMonitor = new FinishMonitor();
		finishMonitor.setFutureContext(todo);
		
		Thread monitor = new Thread(finishMonitor);
		monitor.start();
		
		try {
			monitor.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(" dealWork finish!");
//		this.executorService.shutdown();
		
	}
	
	
	private class FinishMonitor implements Runnable {   
		boolean flag = true;
        private Vector<PromInfoWork> context;   
  
        public void setFutureContext(Vector<PromInfoWork> context) {   
            this.context = context;   
        }   
  
        @Override  
        public void run() {   
        	
            try {
            	System.out.println("start to output result:\t"
						+ UtilTools.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
				while (flag) {
					if (ClmAsynContainer.getFinishWork(this.context) == this.context
							.size()) {
						flag = false;
					}
					//            	int count = 0;
					//            	for (int i=0;i<this.context.size();i++){
					//            		PromInfoWork one = this.context.get(i);
					//            		if (one.getFlag()){
					//            			count++;
					//            		}
					//            	}
					//            	if (count==this.context.size()){
					//            		flag = false;
					//            	}

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println();
				System.out.println("finish to output result:\t"
						+ UtilTools.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
			} catch (Exception e) {
				e.printStackTrace();
				
			}  
        }
        
       
    }
	
	protected static int getFinishWork(List<PromInfoWork> todo){
		int count = 0;
    	for (int i=0;i<todo.size();i++){
    		PromInfoWork one = todo.get(i);
    		if (one.getFlag()){
    			count++;
    		}
    	}
    	return count;
		
	}
}