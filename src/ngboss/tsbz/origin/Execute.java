package ngboss.tsbz.origin;

import java.util.HashMap;
import java.util.Map;

public abstract class Execute {
	Map context ;//�����ġ��ݲ�ʹ�õ�
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
