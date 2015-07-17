package ngboss.tsbz.origin;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ngboss.tsbz.util.GlobalObject;
import ngboss.tsbz.util.SqlExecute;

public class SQLDealUnitWithIn extends Execute{
	public String dealSet;
	public String dealSetType;
	public String db;
	
	public Map dealSetParam;
	
	
	public void init(){
//		this.dealSet = "insert into comdeal_define(define_id,define_name) values (:define_id,:define_name)";
		this.dealSetType = "SimpleSql";
		this.db = "etl";
//		this.dealSetParam = new HashMap();
//		this.dealSetParam.put("define_id", "1_int_89");
//		this.dealSetParam.put("define_name", "2_string_linfeng");
		
	}
	public SQLDealUnitWithIn(String dealSet,Map dealSetParam){
		this.dealSet = dealSet;
		this.dealSetParam = dealSetParam;
	}
	
	public void execute() throws Exception{
		
		if (dealSetType.equals("SimpleSql")){
			Connection conn = GlobalObject.getConnection(db);
			SqlExecute.executeSQL(conn, this.dealSet, this.dealSetParam);
			int dealTotal=0;
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement(this.dealSet);
				//需要识别变量?
				Iterator it = this.dealSetParam.values().iterator();
				while (it.hasNext()){
					String value = (String)it.next();
					String[] values = value.split("_");
					setPrepareStatementParameter(ps,Integer.parseInt(values[0]),values[1],values[2]);
				}
				
				dealTotal=ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("通用处理：执行操作失败");
			}
			
			this.setResult("SUCCESS");
		}else if (dealSetType.equals("procedure")){
			Connection conn = GlobalObject.getConnection(db);
			SqlExecute.executeSQL(conn, this.dealSet, this.dealSetParam);
			this.setResult("SUCCESS");
		}
		
	}
	
	public static void setPrepareStatementParameter(PreparedStatement stmt, int index, String type, Object value)
	        throws SQLException
	    {
	        if(type.equalsIgnoreCase("String"))
	        {
	            String content = value.toString();
	            if(content.length() > 2000)
	                stmt.setCharacterStream(index, new StringReader(content), content.length());
	            else
	                stmt.setString(index, content);
	        } else
	        if(type.equalsIgnoreCase("Short"))
	            stmt.setShort(index, Short.parseShort(value.toString()));
	        else
	        if(type.equalsIgnoreCase("Integer"))
	            stmt.setInt(index, Integer.parseInt(value.toString()));
	        else
	        if(type.equalsIgnoreCase("Long"))
	            stmt.setLong(index, Long.parseLong(value.toString()));
	        else
	        if(type.equalsIgnoreCase("Double"))
	            stmt.setDouble(index, Double.parseDouble(value.toString()));
	        else
	        if(type.equalsIgnoreCase("Float"))
	            stmt.setFloat(index, Float.parseFloat(value.toString()));
	        else
	        if(type.equalsIgnoreCase("Byte"))
	            stmt.setByte(index, Byte.parseByte(value.toString()));
	        else
	        if(type.equalsIgnoreCase("Char"))
	            stmt.setString(index, value.toString());
	        else
	        if(type.equalsIgnoreCase("Boolean"))
	            stmt.setBoolean(index, Boolean.getBoolean(value.toString()));
	        else
	        if(type.equalsIgnoreCase("Date"))
	        {
	            if(value instanceof Date)
	                stmt.setDate(index, (Date)(Date)value);
	            else
	                stmt.setDate(index, Date.valueOf(value.toString()));
	        } else
	        if(type.equalsIgnoreCase("Time"))
	        {
	            if(value instanceof Time)
	                stmt.setTime(index, (Time)(Time)value);
	            else
	                stmt.setTime(index, Time.valueOf(value.toString()));
	        } else
	        if(type.equalsIgnoreCase("DateTime"))
	        {
	            if(value instanceof Timestamp)
	                stmt.setTimestamp(index, (Timestamp)(Timestamp)value);
	            else
	            if(value instanceof Date)
	                stmt.setTimestamp(index, new Timestamp(((Date)value).getTime()));
	            else
	                stmt.setTimestamp(index, Timestamp.valueOf(value.toString()));
	        } else
	        if(value instanceof Character)
	            stmt.setString(index, value.toString());
	        else
	            stmt.setObject(index, value);
	    }
	
	
}
