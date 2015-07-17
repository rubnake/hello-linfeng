package ngboss.tsbz.util;

import java.sql.Connection;

public class GlobalObject {
	static long count=300l;
	
	public static Connection getConnection(String name){ return null;};
	
	public static UserInfo getUserInfo(){return new UserInfo("linfeng");}
	
	public static long getDoneCode(){
		synchronized (GlobalObject.class) {
			count++;
		}
		return count;
	}
}
