package algorithm.greedy.demo.entity;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class TestFunRecord {
	
	static FunRecord one = new FunRecord();
	
	/*
	 * 前置处理：
	 *  0 出现 if for while 这个时候，必有第一层( 
	 *   此行替换为 if (object){
	 *          object = 原第一层括号中的内容
	 *  0 switch(表达式){  //todo
	 *  	case 常量表达式1:语句1;
	 *      default value;
	 *  	}
	 *  0 出现?  int a=(sv.test(B)>1?C:D);  sv.test(B)>1?C:D; return sv.test(B)>1?C:D;
	 *   出现return xxx;
	 *     returnObject = xxx;
	 *     return object;
	 *   C = line.substrt(pos(?),pos(:);//暂时不处理
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
		
		//假设一行中只会出现一个？调用
		if (line.contains("?")){
			int pos = line.indexOf("?");
			//todo
		}
		
		return result;
	}
	
	/*
	 * 从带有表达式的一行获取提取函数
	 * 
	 * 分割方法：
	 * 前置处理：
	 *  0 出现 if for while case 这个时候，必有第一层( 
	 *   此行替换为 if (object){
	 *          object = 原第一层括号中的内容
	 *  0 出现?  int a=(sv.test(B)>1?C:D);  sv.test(B)>1?C:D; return sv.test(B)>1?C:D;
	 *   出现return xxx;
	 *     object = xxx;
	 *     return object;
	 *   value1 = line.substrt(pos(?),pos(:);//暂时不处理
	 *            
	 *	1 如果出现！，则对！后边为函数的进行分割 右边的表达式，获取方式Pos(!)+1开始到分隔符，如果遇到（，则获取()整个内容
	 *	  object object = 右边的表达式
	 *	  如果表达式包含（，则进行替换
	 *	    进行替换原行= if ( (!object==testB(value)&&flag)&&(3*sv.testC(value)+test(B)) ){
	 *	2 如果出现==，&&，||
	 *	  对于==的右边,获取方式Pos(==)+1开始到分隔符，如果遇到（，则获取()整个内容
	 *	  作于==的左边,获取Pos（==）-1到分割符，如果遇到），则获取（）整个内容+获取（到分割符的内容
	 *	3 如果出现+，-，*，/，&，~，|,>>,<<
	 *	  对于+的右边,获取方式Pos(+)+1开始到分隔符，如果遇到（，则获取()整个内容
	 *	  对于+的左边,获取方式Pos(+)-11开始到分隔符，如果遇到），则获取（）整个内容+获取（到分割符的内容
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
				while (line.charAt(i+1)==' '){ //遇到空格往前推进
					i++;
				}
				if (line.charAt(i+1)!='('){  //此时!后面为函数调用
					for (int k=i+1;k<line.length();k++){
						if (one.isStopFlag(line.charAt(k))||line.charAt(k)==')'){
							break;
						}
						if (line.charAt(k)=='('){
							  pos = _getEndPos(k,line);
							  res.add(line.substring(i+1, pos+1));
							  i = pos;//截支，往前推进
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
				int front = i;//记录当前匹配的pos，在往前查找函数时候使用
				int pos = -1;
				while (line.charAt(i+2)==' '){ //遇到空格往前推进
					i++;
				}
				if (line.charAt(i+2)!='('&&(!one.isStopFlag(line.charAt(i+2)))){  //此时==后面为函数调用
					for (int k=i+2;k<line.length();k++){
						if (one.isStopFlag(line.charAt(k))||line.charAt(k)==')'){
							break;
						}
						if (line.charAt(k)=='('){
							  pos = _getEndPos(k,line);
							  res.add(line.substring(i+2, pos+1));
							  i = pos;//截支，往前推进
							  isFindFunc  = true;
							  //line = line.replace(line.substring(i, pos), "object"+res.size());
							  break;
						}
					}
				}
				//处理== 左边的函数替换
				int funBegPos = -1;
				while (line.charAt(front-1)==' '){ //遇到空格往前推进
					i--;
				}
				if (line.charAt(front-1)==')'){//此时==前面为函数调用
					int begPos = _getBegPos(front-1, line);
					for (int k=begPos-1;k>-1;k--){
						if (one.isStopFlag(line.charAt(k))||line.charAt(k)=='('){
							funBegPos =  k;
							break;
						}
					}
					
					if (StringUtils.isNotBlank(line.substring(funBegPos+1, begPos))){//此时后找到调用的函数
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
	 * 从带有表达式的一行获取提取函数
	 * 
	 * 1 关键字if while for开头的  一般结尾为{
	 *   if (test(A)){
	 *   if ( (!testA(value)==sv.testB(value)) ) {
	 * 
	 * 2 sv.test(value1,sv2.testB(value2)+3);
	 */
	public static void getFuncByExpressionLine(String line,Deque<String> result){
		
	}
	
	/*
	 * 将带有表达式的行退化成”普通行“，即一行不会出现2个运算符
	 * 
	 * 表达式符  ==,&&,||,!,+,-,^,/,<<,>>,~,&,| 
	 * 还有特殊的表达式 int b=(a==0?b:c);  //todo 此表达式暂时不处理
	 * 
	 * 使用此方法之前，必须保证line是包含表达式的
	 * 在将一行退化成simpleJavaLine中使用
	 * 
	 * 算法：
	 * 从最后一个)往前匹配(,进行分段替换
	 * 
	 * 只会出现这种形式~~~~
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
	 * ()表达式处理
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
	 * 获取匹配)的( 的位置
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
	 * 获取匹配(的) 的位置
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
