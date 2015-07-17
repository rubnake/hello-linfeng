package thread.todo;

import java.util.ArrayList;
import java.util.List;

public class MainClm {
	
	public static void main(String[] args) {
		MainClm test = new MainClm();
		
		List<ClmProInfo> todo = test.getTodo();
		System.out.println("begin!");
		System.out.println(test.getFinishWork(todo));
		
		for (int i=0;i<todo.size();i++){
			Thread tt = new Thread(todo.get(i));
			tt.start();
		}
		
		//监视是否完成。
		FinishMonitor finishMonitor = test.new FinishMonitor();
		finishMonitor.setFutureContext(todo);
		
		Thread monitor = new Thread(finishMonitor);
		monitor.start();
		
		try {
			monitor.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("finish!");
		System.out.println(test.getFinishWork(todo));
		
	}
	
	public List<ClmProInfo> getTodo(){
		List<ClmProInfo> todo = new ArrayList<ClmProInfo>();
		
		for (int i = 0; i < 100; i++) {
			todo.add(new ClmProInfo(i, "13917365143"));
		}
		return todo;
	}
	
	public class FinishMonitor implements Runnable {   
		boolean flag = true;
        private List<ClmProInfo> context;   
  
        public void setFutureContext(List<ClmProInfo> context) {   
            this.context = context;   
        }   
  
        @Override  
        public void run() {   
        	
            System.out.println("start to output result:");   
            while (flag){
            	int count = 0;
            	for (int i=0;i<this.context.size();i++){
            		ClmProInfo one = this.context.get(i);
            		if (one.getFlage()){
            			count++;
            		}
            	}
            	if (count==this.context.size()){
            		flag = false;
            	}
            	
            	try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
  
            System.out.println("finish to output result.");   
        }
        
  
       
    }
	
	public int getFinishWork(List<ClmProInfo> todo){
		int count = 0;
    	for (int i=0;i<todo.size();i++){
    		ClmProInfo one = todo.get(i);
    		if (one.getFlage()){
    			count++;
    		}
    	}
    	return count;
		
	}
}
