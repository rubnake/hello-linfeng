package coreJava;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ListTT {
	public static void main(String[] args) {
		Map a = new HashMap();
		for (int i=0;i<10;i++){
			a.put(i, "linfeng__"+i);
		}
		Iterator it = a.keySet().iterator();
		while (it.hasNext()){
			String value = (String)a.get(it.next());
			if (value.equals("linfeng__3")){
				it.remove();
			}
		}
		System.out.println("----------------------");
		Iterator it_1 = a.keySet().iterator();
		while (it_1.hasNext()){
			String value = (String)a.get(it_1.next());
			System.out.println(value);
		}
	}
}
