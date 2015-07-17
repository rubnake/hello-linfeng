package ngboss.tsbz.idea1;

import java.sql.Connection;
import java.util.Map;

import ngboss.tsbz.util.GlobalObject;
import ngboss.tsbz.util.SqlExecute;

public class DealUnit {
	public String dealSet;
	public String dealSetType;
	public String db;
	public Map dealSetParam;
	
	public String result;
	
	public void init(){
		
	}
	
	public void execute() throws Exception{
		
		if (dealSetType.equals("SimpleSql")){
			Connection conn = GlobalObject.getConnection(db);
			SqlExecute.executeSQL(conn, this.dealSet, this.dealSetParam);
			result = "SUCCESS";
		}else if (dealSetType.equals("procedure")){
			Connection conn = GlobalObject.getConnection(db);
			SqlExecute.executeSQL(conn, this.dealSet, this.dealSetParam);
			result = "SUCCESS";
		}
		
	}
	
	
}
