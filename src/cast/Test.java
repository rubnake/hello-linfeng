package cast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
	
	public static void main(String[] args) throws Exception {
		
//		String name="аж╥Е22";
//		System.out.println(name);
//		String utf8_name = new String(name.getBytes(),"UTF-8");
//		String iso_name = new String(name.getBytes(),"ISO-8859-1");
//		System.out.println(utf8_name);
//		System.out.println(iso_name);
		Person a = new Person();
		a.setId(2L);
		System.out.println(a.getId());
	}
}
