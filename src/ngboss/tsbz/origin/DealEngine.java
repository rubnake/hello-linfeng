package ngboss.tsbz.origin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DealEngine {
	String defineId;
	List<DealUnit> dealList = new ArrayList<DealUnit>();
	Map context = new HashMap();//上下文。暂不使用到
	//
	int size ;
	String name;
	int dealCount=0;
	
	public DealEngine(String defineId){
		this.defineId = defineId;
	}
	public void init(){
		//defineId
		
		
	}
	
	public boolean checkParam(){
		return true;
	}
	
	public void service() throws Exception {
		for (DealUnit one:dealList){
			one.execute();
			
			if (one.result.equals("SUCCESS")){			
			     dealCount++;
			}
		}
	}
	
	//监控状态，获取执行的步骤
	public String getState(){
		if (dealCount==size){
			//通知全局变量这个次处理操作已完结
			return "FINISH";
		}
		return "进度:\t"+dealCount*100/size+"%";
	}
}
