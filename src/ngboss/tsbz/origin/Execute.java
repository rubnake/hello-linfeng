package ngboss.tsbz.origin;

import java.util.HashMap;
import java.util.Map;

public abstract class Execute {
	Map context ;//上下文。暂不使用到
	String result;
	
	public abstract void init() throws Exception;
	public abstract void execute() throws Exception;
	
	public void setContext(Map context){
		this.context = context;
	}
	
	public void setResult(String result){
		this.result = result;
	}
	public String getResult(){
		return this.result;
	}
}
