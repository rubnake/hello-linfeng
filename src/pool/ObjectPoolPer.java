package pool;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/*
 * ָ�����pool�ĳش�СΪ count
 * 
 * ��ǿ����,ʹ�����޴��LinkedBlockingDeque�����ֶ��󣬵��ǳ����Ǳ�����fixֵ
 * 
 * �ο�Apache common pool ������get���⡣�����ڷ��ص�ʱ��ֻ����maxidl�Ŀ��ж���أ���������ֱ��delete��
 */

public final class ObjectPoolPer implements Pool{
	private LinkedBlockingDeque<Element> valiable = new LinkedBlockingDeque<Element>();
	private ConcurrentHashMap<Element, String> all = new ConcurrentHashMap<Element, String>();
	private volatile AtomicInteger id = new AtomicInteger(0);
	//final ReentrantLock lock = new ReentrantLock();
	private int count;
	PooledObjectFactory _factory;

	public ObjectPoolPer() {
		for (int i = 0; i < 3; i++) {
			Element e = new Element();
			e.setId(id.incrementAndGet());
			e.setObj(new Object());
			valiable.add(e);
			all.put(e, "Prepared");
		}
		count = 3;
	}

	public ObjectPoolPer(int count,PooledObjectFactory _factory) {
		for (int i = 0; i < count; i++) {
			Element e = new Element();
			e.setId(id.incrementAndGet());
			e.setObj(new Object());
			valiable.add(e);
			all.put(e, "Prepared");
		}
		this.count = count;
		this._factory = _factory;
	}

	public int getValiableSize() {
		return valiable.size();
	}

	public Element getObject() {
		sleep(200);
		Element one = valiable.pollFirst();
		if (one==null){
			try {
				one = _factory.makeObject();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		all.put(one, "Used");
		return one;
	}

	public void returnObject(Element e) {
		sleep(100);
		if (valiable.size() > count) {
			try {
				_factory.destroyObject(e);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return; //valiable.removeFirst(); ֱ�Ӷ���
		}
		valiable.push(e);
		all.put(e, "Prepared");
	}

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
}
