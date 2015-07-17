package algorithm.greedy.demo.context;

import java.util.HashMap;
import java.util.Map;

import algorithm.greedy.demo.interfaces.IHelloSV;

public class DemoContext {
	private Map context = new HashMap();
	
	public DemoContext() {
		// TODO Auto-generated constructor stub
	}
	
	public int doExe(IHelloSV deal){
		String name = context.get("name")==null?"linfeng":(String)context.get("name");
		deal.sayHello(name);
		return 1;
	}

}
