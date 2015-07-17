package web.sync.collection;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * 对一个用户，单个策划的预判处理子线程
 * 此场景不涉及同步
 * app由于数据库连接数的限制，需要将其提升到web层进行处理。
 * 林峰 2013-12-11 18:23:52
 */
public class PromInfoWork implements Runnable{
	
	long offerId;
	boolean canDo = false; //操作员是否可受理，默认为不可受理
	
	boolean finish = false;

	public PromInfoWork(long offerId) {
		this.offerId = offerId;
	}
	

	public long getOfferId() {
		return offerId;
	}




	public boolean isCanDo() {
		return canDo;
	}


	@Override
	public void run() {
		
		long startTime = new Date().getTime();
		System.out.println("ThreadId:\t"+Thread.currentThread().getId()+"\t 开始预判：offerId\t" + this.offerId);
		try {
			deal();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			finish = true;
			long endTime = new Date().getTime();
			System.out.println("ThreadId:\t"+Thread.currentThread().getId()+"\t 结束预判：offerId\t" + this.offerId+"\t花费时间：\t"+(endTime-startTime)/1000+"\t预判结果:\t"+this.finish);
		}
	}

	public void deal() throws Exception {
		//ServiceFactory.getCrossCenterService(interfaceClass)
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ThreadId:\t"+Thread.currentThread().getId()+ "\t 预判：offerId\t" + this.offerId+"\t 不能办理\t"+e.getMessage());
			this.canDo = false;
		}
		
	}
	
	//必须要有这个方法
	public boolean getFlag(){
		return this.finish;
	}
	
}
