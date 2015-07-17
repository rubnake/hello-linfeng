package ngboss.tsbz.origin;

import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;


import ngboss.tsbz.util.GlobalObject;
import ngboss.tsbz.util.UserInfo;

public class DealEngineManeger {
	
	static Map allDealApps = new ConcurrentHashMap();
	public DealEngineManeger(){
		
		new Timer().schedule(new CleanTask(), 30, 5*60*1000);
	}
	private class CleanTask extends TimerTask{

		@Override
		public void run() {
			Iterator keyIter = allDealApps.keySet().iterator();
			while (keyIter.hasNext()) {
				String key = (String) keyIter.next();
				DealEngine oneDeal = (DealEngine) allDealApps.get(key);
				if (oneDeal.getState().equals("FINISH")) {
					allDealApps.remove(key);

				}
			}

		}
		
	}
	
	/**
	 * 添加用户
	 */
	public static final void add(String key ,DealEngine one)
	{
		allDealApps.put(key,one);
	}
	
	/**
	 * 删除
	 */
	public static final DealEngine removeUserInfo(String key)
	{
		return (DealEngine)allDealApps.remove(key);
	}
	
	
	
	/**
	 * 取
	 * @param hexKey
	 * @return
	 */
	public static final DealEngine getDealEngine(String key)
	{
		DealEngine one = (DealEngine)allDealApps.get(key);
		return one;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		DealEngine todo = new DealEngine("1234");
		UserInfo user = GlobalObject.getUserInfo();
		long doneCode = GlobalObject.getDoneCode();
		todo.service();
		
		DealEngineManeger.add(user.name+"_"+doneCode, todo);
		
	}
}
