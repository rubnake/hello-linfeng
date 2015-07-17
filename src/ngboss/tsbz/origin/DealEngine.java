package ngboss.tsbz.origin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DealEngine {
	String defineId;
	List<DealUnit> dealList = new ArrayList<DealUnit>();
	Map context = new HashMap();//�����ġ��ݲ�ʹ�õ�
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
	
	//���״̬����ȡִ�еĲ���
	public String getState(){
		if (dealCount==size){
			//֪ͨȫ�ֱ�������δ�����������
			return "FINISH";
		}
		return "����:\t"+dealCount*100/size+"%";
	}
}
