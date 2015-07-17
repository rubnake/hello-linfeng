package web.sync.collection;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * ��һ���û��������߻���Ԥ�д������߳�
 * �˳������漰ͬ��
 * app�������ݿ������������ƣ���Ҫ����������web����д���
 * �ַ� 2013-12-11 18:23:52
 */
public class PromInfoWork implements Runnable{
	
	long offerId;
	boolean canDo = false; //����Ա�Ƿ������Ĭ��Ϊ��������
	
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
		System.out.println("ThreadId:\t"+Thread.currentThread().getId()+"\t ��ʼԤ�У�offerId\t" + this.offerId);
		try {
			deal();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			finish = true;
			long endTime = new Date().getTime();
			System.out.println("ThreadId:\t"+Thread.currentThread().getId()+"\t ����Ԥ�У�offerId\t" + this.offerId+"\t����ʱ�䣺\t"+(endTime-startTime)/1000+"\tԤ�н��:\t"+this.finish);
		}
	}

	public void deal() throws Exception {
		//ServiceFactory.getCrossCenterService(interfaceClass)
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ThreadId:\t"+Thread.currentThread().getId()+ "\t Ԥ�У�offerId\t" + this.offerId+"\t ���ܰ���\t"+e.getMessage());
			this.canDo = false;
		}
		
	}
	
	//����Ҫ���������
	public boolean getFlag(){
		return this.finish;
	}
	
}
