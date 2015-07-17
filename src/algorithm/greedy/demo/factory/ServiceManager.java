package algorithm.greedy.demo.factory;

import java.util.HashMap;
import java.util.Map;

import algorithm.greedy.demo.impl.HelloSVImpl;

public class ServiceManager {
	
	private static Map map = new HashMap();
	
	private ServiceManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static Object getService(String name){
		Object result = new HelloSVImpl();
		if (map.containsKey(name)){
			result = map.get(name);
		}
		return result;
	}

}
