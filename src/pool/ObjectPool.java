package pool;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
 * ָ�����pool�ĳش�СΪ count
 * ���pool�޿��ã���wait
 * 
 * ���ò�����ܺܲ�~~
 * 
 * ������Apache common pool ������get���⡣�����ڷ��ص�ʱ��ֻ����maxidl�Ŀ��ж���أ���������ֱ��delete��
 */

public final class ObjectPool implements Pool{
	private List<Element>  valiable = new LinkedList<Element>();
	private Map<Element,String> all = new HashMap<Element,String>();
	private int count;
	private int valiNum;
	
	public ObjectPool(){
		for (int i=0;i<3;i++){
			Element e = new Element();
			e.setId(i);
			e.setObj(new Object());
			valiable.add(e);
			all.put(e, "Prepared");
		}
		count = 3;
		valiNum = 3;
	}
	
	public ObjectPool(int count){
		for (int i=0;i<count;i++){
			Element e = new Element();
			e.setId(i);
			e.setObj(new Object());
			valiable.add(e);
			all.put(e, "Prepared");
		}
		this.count = count;
		valiNum = count;
	}
	
	public ObjectPool(int count, List<Element> list) {
		if (list.size() > 0 && list.size() == count) {
			this.count = count;
			this.valiNum = count;
			this.valiable = list;
			for (Element e : list) {
				all.put(e, "Prepared");
			}
		}

	}
	
	public synchronized int getValiableSize(){
		return this.valiNum;
	}
	
	public synchronized Element getObject() {
		sleep(500);
		
		Element one = null;
		while (valiNum < 1) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		one = valiable.remove(0);
		all.put(one, "Used");
		valiNum--;
		return one;
	}
	
	public synchronized void returnObject(Element e){
		sleep(250);
		valiNum++;
		if (valiNum>count){
			return ;
		}
		valiable.add(e);
		all.put(e, "Prepared");
		notifyAll();
	}
	
	
	private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
        }
    }
}
