package thread.base_op;

import java.util.Date;

import util.UtilTools;



public class TestRunable implements Runnable{
	private boolean flag = true;
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public void run() {
		while(flag){
			System.out.println("==date:\t"+UtilTools.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss")+"!");
			
		}
	}
	
	
}
