package algorithm.greedy.demo.entity;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class TestFunRecord {
	
	static FunRecord one = new FunRecord();
	
	/*
	 * ǰ�ô���
	 *  0 ���� if for while ���ʱ�򣬱��е�һ��( 
	 *   �����滻Ϊ if (object){
	 *          object = ԭ��һ�������е�����
	 *  0 switch(���ʽ){  //todo
	 *  	case �������ʽ1:���1;
	 *      default value;
	 *  	}
	 *  0 ����?  int a=(sv.test(B)>1?C:D);  sv.test(B)>1?C:D; return sv.test(B)>1?C:D;
	 *   ����return xxx;
	 *     returnObject = xxx;
	 *     return object;
	 *   C = line.substrt(pos(?),pos(:);//��ʱ������
	 *   D = line.substrt(pos(:),pos(:)||pos()));
	 *   condtion = line.substrt(pos(=)||pos((),pos(?));
	 *   
	 *   if (condtion){
	 *   	object = C;
	 *   }else{
	 *   	object = D;
	 *   }
	 *   int a = (object);
	 */
	public static List<String> preDeal(String line){
		if (StringUtils.isBlank(line)){
			return new ArrayList<String>();
		}
		line = line.trim();
		List<String> result = new ArrayList<String>();
		
		if (line.startsWith("if")||line.startsWith("for")||
				line.startsWith("while")){
			int begPos = 0;
			int endPos = 0;
			String inner  = null;
			if (line.contains("(")){
				begPos = line.indexOf("(");
				endPos = _getBegPos(begPos, line);
				inner = line.substring(begPos+1, endPos);
				result.add(inner);
			}
			if (inner!=null){
				line  = line.replace(inner, "object");
				result.add("object = "+inner+";");
			}
			result.add(line);
		}
		
		if (line.startsWith("return ")){
			line.replace("return", "returnObject=");
			result.add(line);
			result.add("return returnObject;");
		}
		
		//����һ����ֻ�����һ��������
		if (line.contains("?")){
			int pos = line.indexOf("?");
			//todo
		}
		
		return result;
	}
	
	/*
	 * �Ӵ��б��ʽ��һ�л�ȡ��ȡ����
	 * 
	 * �ָ����
	 * ǰ�ô���
	 *  0 ���� if for while case ���ʱ�򣬱��е�һ��( 
	 *   �����滻Ϊ if (object){
	 *          object = ԭ��һ�������е�����
	 *  0 ����?  int a=(sv.test(B)>1?C:D);  sv.test(B)>1?C:D; return sv.test(B)>1?C:D;
	 *   ����return xxx;
	 *     object = xxx;
	 *     return object;
	 *   value1 = line.substrt(pos(?),pos(:);//��ʱ������
	 *            
	 *	1 ������֣�����ԣ����Ϊ�����Ľ��зָ� �ұߵı��ʽ����ȡ��ʽPos(!)+1��ʼ���ָ�������������������ȡ()��������
	 *	  object object = �ұߵı��ʽ
	 *	  ������ʽ��������������滻
	 *	    �����滻ԭ��= if ( (!object==testB(value)&&flag)&&(3*sv.testC(value)+test(B)) ){
	 *	2 �������==��&&��||
	 *	  ����==���ұ�,��ȡ��ʽPos(==)+1��ʼ���ָ�������������������ȡ()��������
	 *	  ����==�����,��ȡPos��==��-1���ָ������������������ȡ������������+��ȡ�����ָ��������
	 *	3 �������+��-��*��/��&��~��|,>>,<<
	 *	  ����+���ұ�,��ȡ��ʽPos(+)+1��ʼ���ָ�������������������ȡ()��������
	 *	  ����+�����,��ȡ��ʽPos(+)-11��ʼ���ָ�������������������ȡ������������+��ȡ�����ָ��������
	 * 
	 *  @date 2015-5-14 21:01:04
	 *  
	 *  sv.sayHello(name,testA(value)+(testB(value)-sv.testC(value)) );
	 *  if ( (!testA(value)==testB(value)&&flag)&&sv.testC(value||test(B)) ){
	 *  if (!test(B)){
	 */
	//@Deprecated
	public static void spiltExpressionLine(String line){
		if (!one.hasExpression(line)){
			return ;
		}
		char[] temp = line.toCharArray();
		List<String> res = new ArrayList<String>();
		for (int i=0;i<line.length();i++){
			boolean isFindFunc = false;
			if (line.charAt(i)=='!'){
				int pos = -1;
				while (line.charAt(i+1)==' '){ //�����ո���ǰ�ƽ�
					i++;
				}
				if (line.charAt(i+1)!='('){  //��ʱ!����Ϊ��������
					for (int k=i+1;k<line.length();k++){
						if (one.isStopFlag(line.charAt(k))||line.charAt(k)==')'){
							break;
						}
						if (line.charAt(k)=='('){
							  pos = _getEndPos(k,line);
							  res.add(line.substring(i+1, pos+1));
							  i = pos;//��֧����ǰ�ƽ�
							  isFindFunc  = true;
							  //line = line.replace(line.substring(i, pos), "object"+res.size());
							  break;
						}
						
					}
				}
			}
			if (isFindFunc){
				continue;
			}
			
			if ((line.charAt(i)=='='&&line.charAt(i+1)=='=')||
					(line.charAt(i)=='|'&&line.charAt(i+1)=='|') ||
					(line.charAt(i)=='&'&&line.charAt(i+1)=='&')){
				int front = i;//��¼��ǰƥ���pos������ǰ���Һ���ʱ��ʹ��
				int pos = -1;
				while (line.charAt(i+2)==' '){ //�����ո���ǰ�ƽ�
					i++;
				}
				if (line.charAt(i+2)!='('&&(!one.isStopFlag(line.charAt(i+2)))){  //��ʱ==����Ϊ��������
					for (int k=i+2;k<line.length();k++){
						if (one.isStopFlag(line.charAt(k))||line.charAt(k)==')'){
							break;
						}
						if (line.charAt(k)=='('){
							  pos = _getEndPos(k,line);
							  res.add(line.substring(i+2, pos+1));
							  i = pos;//��֧����ǰ�ƽ�
							  isFindFunc  = true;
							  //line = line.replace(line.substring(i, pos), "object"+res.size());
							  break;
						}
					}
				}
				//����== ��ߵĺ����滻
				int funBegPos = -1;
				while (line.charAt(front-1)==' '){ //�����ո���ǰ�ƽ�
					i--;
				}
				if (line.charAt(front-1)==')'){//��ʱ==ǰ��Ϊ��������
					int begPos = _getBegPos(front-1, line);
					for (int k=begPos-1;k>-1;k--){
						if (one.isStopFlag(line.charAt(k))||line.charAt(k)=='('){
							funBegPos =  k;
							break;
						}
					}
					
					if (StringUtils.isNotBlank(line.substring(funBegPos+1, begPos))){//��ʱ���ҵ����õĺ���
						res.add(line.substring(funBegPos+1, front));
					}
					
				}
				
			}
			
			
			if (isFindFunc){
				continue;
			}
			
			
		}
		//print
		System.out.println(res);
		
		
	}
	
	
	/*
	 * �Ӵ��б��ʽ��һ�л�ȡ��ȡ����
	 * 
	 * 1 �ؼ���if while for��ͷ��  һ���βΪ{
	 *   if (test(A)){
	 *   if ( (!testA(value)==sv.testB(value)) ) {
	 * 
	 * 2 sv.test(value1,sv2.testB(value2)+3);
	 */
	public static void getFuncByExpressionLine(String line,Deque<String> result){
		
	}
	
	/*
	 * �����б��ʽ�����˻��ɡ���ͨ�С�����һ�в������2�������
	 * 
	 * ���ʽ��  ==,&&,||,!,+,-,^,/,<<,>>,~,&,| 
	 * ��������ı��ʽ int b=(a==0?b:c);  //todo �˱��ʽ��ʱ������
	 * 
	 * ʹ�ô˷���֮ǰ�����뱣֤line�ǰ������ʽ��
	 * �ڽ�һ���˻���simpleJavaLine��ʹ��
	 * 
	 * �㷨��
	 * �����һ��)��ǰƥ��(,���зֶ��滻
	 * 
	 * ֻ�����������ʽ~~~~
	 * if ( ((!A==B)&&flag)&&(3*C+D) ){
	 * result:
	 * E1=(!A==B)
	 * E2=E1&&flag
	 * E3=3*C+D;
	 * E4=(E2&&E3);
	 * if (E4){
	 * 
	 * @param line
	 */
	public static void getLinesByExpressionLine(String line){
		
	}
	
	/*
	 * ()���ʽ����
	 * 
	 * 1
	 * 
	 * 2 
	 */
	public static void getLineByExpressionLine(String line,Deque<String> result){
		
		String todeal = line;
		List<String> queue = new LinkedList<String>();
		while (todeal.contains(")")){
			int endPos = todeal.lastIndexOf(")");
			int begPos = _getBegPos(endPos,todeal);
			String temp = todeal.substring(begPos, endPos+1);
			String _temp = temp.substring(1, temp.length()-1);
			
			queue.add(_temp);
			result.push(_temp);
			todeal = todeal.substring(0, begPos);
			
			
		}
		if (line.contains(")")){
			for (int j=0;j<queue.size();j++){
				getLineByExpressionLine(queue.get(j),result);
			}
		}
		
		
		return ;
	}
	
	/*
	 * ��ȡƥ��)��( ��λ��
	 * 
	 */
	public static int _getBegPos(int endPos,String line){
//		int endPos = line.indexOf(")");
		int begPos = 0;
		if (endPos>-1){
			int lMatch=0;
			for (int i= endPos-1;i>0;i--){
				if (line.charAt(i)==')'){
					lMatch++;
				}
				if (line.charAt(i)=='('){
					if (lMatch==0){
						begPos = i;
						break;
					}else{
						lMatch--;
					}
				}
			}
		}
		return begPos;
	}
	
	/*
	 * ��ȡƥ��(��) ��λ��
	 * 
	 */
	public static int _getEndPos(int begPos,String line){
		int endPos = begPos;
//		int begPos = line.indexOf("(");
		if (begPos>-1){
			int lMatch=0;
			for (int i= begPos+1;i<line.length();i++){
				if (line.charAt(i)=='('){
					lMatch++;
				}
				if (line.charAt(i)==')'){
					if (lMatch==0){
						endPos = i;
						break;
					}else{
						lMatch--;
					}
				}
			}
		}
		return endPos;
	}
	

	public static void main(String[] args) {
		FunRecord test = new FunRecord();
		TestFunRecord work =  new TestFunRecord();
		
		
//		String line = "String res = (String[])Service.get2().get(test(B)).sayHello((String[])testC(value),id);";
//		
//	    String line1 = "int a = (String)(age+1);";
//
//	    String line2 ="  return (String[])result.toArray(new String[0]);";
//	    String line4 ="String res = sv.sayHello((String[])testC(value),id);";
//	    System.out.println(test.dealTypeChangeInogore(line));
//		System.out.println(test.dealTypeChangeInogore(line1));
//		System.out.println(test.dealTypeChangeInogore(line2));
//		System.out.println(test.dealTypeChangeInogore(line4));
		
//		Deque<String> result =  new LinkedList<String>();
//		String line = "if ( ((!A==B)&&flag)&&(3*C+D)||F  ){";
//		TestFunRecord.getLineByExpressionLine(line,result);
//		System.out.println(result);
		
//		String line = "(!testA(value)==testB(value)&&flag)&&sv.testC(value||test(B))";
		String line = "(testA(value)==testB(value))";
		System.out.println(line);
		work.spiltExpressionLine(line);
		
	}
}
