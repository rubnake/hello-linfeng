package algorithm.greedy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import algorithm.greedy.demo.entity.FunRecord;

public class JavascHelp {
	
	private static String basePath="D:/crm_workspace_ext/Simple2/src";
	private static String JAVA_PACKAGE="package";
	private static String JAVA_IMPORT="import";
	private static String JAVA_CLASS="class";
	private static String JAVA_EXTENDS="extends";
	private static String JAVA_IMPLEMENTS="implements";
	
	private static String JAVA_PUBLIUC="public";
	private static String JAVA_PRIVATE="private";
	private static String JAVA_PROTECTED="protected";
	
	private static String JAVA_STATIC="static";
	private static String JAVA_FINAL="final";
	private static String JAVA_SYNCHRONIZED="synchronized";
	
	//todo 解析私有变量，尤其是是有变量是一个接口\类定义
	
	/*
	 * 判断一行是否为类定义行
	 */
	 public static boolean isClassLine(String line) {
		 if(line==null){
			 return false;
		 }
         boolean flag = false;
         int pos=line.indexOf(JAVA_PUBLIUC);
         if (pos>-1&&line.endsWith("{")){
        	 String temp = line.substring(JAVA_PUBLIUC.length()+1).trim();
        	 if (temp!=null&&temp.startsWith(JAVA_CLASS)){
        		 flag=true;
        	 }
         }
         
         return flag;
     }
	 
	 /*
	  * 通用函数名和入参数判断是否是为同一个函数
	  * 参数入参是有顺序的
	  */
	 private static boolean isFuncLine(String line,String funcName,String[] inArgs) {
		 if(line==null){
			 return false;
		 }
         boolean flag = true;
         if (line.contains(funcName)&&line.endsWith("{")&&line.contains(")")){
        	 int ags_beg_pos = line.indexOf("(");
        	 int ags_end_pos = line.indexOf(")");
        	 String argsIn  = line.substring(ags_beg_pos+1, ags_end_pos);
        	 String[] argsInList = argsIn.split(",");
        	 if (argsInList.length==inArgs.length){
        		 for (int i=0;i<argsInList.length;i++){
        			 String temp = argsInList[i];
        			 //有可能出现object array[]这种数组的写法
        			 if (temp.contains("[")){
        				 temp = temp.trim().split(" ")[0]+"[]";
        			 }
        			 if (!temp.contains(inArgs[i])){
        				 flag = false;
        				 break;
        			 }
        		 }
        	 }else{
        		 flag = false;
        	 }
         }else{
        	 flag = false;
         }
         
         return flag;
     }
	 
	 /*
	  * 判断一行是否为注释行
	  * 
	  */
	 private static boolean isCommentsLine(String line){
		 if (line==null){
			 return true;
		 }
		 line = line.trim();
		 boolean flag = false;
		 if (line.startsWith("/")||line.startsWith("//")||line.startsWith("*")){
			 flag = true;
		 }
		 return flag;
	 }
	 
	 /*
	  * 判读这次是否为一个函数的定义
	  * 前提 java代码是符合一般格式化的，遇到{ 是必须换行的
	  * 
	  * simple 处理
	  */
	 private static boolean isFuncLine(String line) {
         boolean flag = false;
         if (line.contains("(")&&line.endsWith("{")
        		 &&!line.contains("=")
        		 &&!line.contains("*")
        		 &&!line.contains("//")
        		 &&!line.contains("/")){
        	 //进一步判断段，认为写代码符合excelipse默认格式化的，函数都是有访问修饰符的
        	 //demo public int work(String id,String name){
        	 String[] temp = line.replace(JAVA_STATIC, "")
        			 .replace(JAVA_FINAL, "")
        			 .replace(JAVA_SYNCHRONIZED, "").trim().split(" ");
        	 if (temp.length>1){
        		 if (temp[0].equals(JAVA_PROTECTED)||temp[0].equals(JAVA_PUBLIUC)
        				 ||temp[0].equals(JAVA_PRIVATE)){
        			 flag = true;
        		 }
        	 }
        	
         }
         
         return flag;
     }
	 
	 /*
	  * 源代码中函数的一行，将其函数的具体进行设置到FuncRecord中去
	  * 此函数必须在判断 isFuncLine=true的条件下使用
	  * 
	  * @param line  demo：  public int work(String id,String name){
	  *           demo：  private static int work(String id,String name){
	  *           demo：  private final static void work(String id,String name){
	  */
	 public static FunRecord setFuncDesc(String line,FunRecord one){
		if (isFuncLine(line)){
			//public int work(String id,String name) throws Exception{
			int agrs_beg_pos = line.indexOf("(");
			int agrs_end_pos = line.indexOf(")");
			//函数名称为（ 前的第一个空格到（所在的位置
			int fun_beg_pos = line.substring(0, agrs_beg_pos).lastIndexOf(" ");
			//函数返回值为（ 前的倒数第二个空格到第一个空格所在的位置
			int return_beg_pos = line.substring(0, fun_beg_pos).lastIndexOf(" ");
			
			String inArgs = line.substring(agrs_beg_pos+1, agrs_end_pos);
			String funcName = line.substring(fun_beg_pos+1, agrs_beg_pos);
			String outArgs = line.substring(return_beg_pos+1, fun_beg_pos);
			
			System.out.println("inArgs\t"+inArgs);
			System.out.println("funcName\t"+funcName);
			System.out.println("outArgs\t"+outArgs);
			
			one.setFunName(funcName);
			one.setFuncInArgsOrgin(inArgs);
			one.setFuncOutArg(outArgs);
			
		}
		 
		return one;
	 }
	 
	 /*
	  * 通用函数名和入参数个数判断是否是为同一个函数
	  * 
	  * simple 处理
	  */
	 private static boolean isFuncLineBySize(String line,String funcName,int inArgSize) {
         boolean flag = true;
         if (line.contains(funcName)&&line.contains("{")&&!line.contains(";")){
        	 int ags_beg_pos = line.indexOf("(");
        	 int ags_end_pos = line.indexOf(")");
        	 String argsIn  = line.substring(ags_beg_pos+1, ags_end_pos);
        	 String[] argsInList = argsIn.split(",");
        	 if (argsInList.length==inArgSize){
        	 }else{
        		 flag = false;
        	 }
         }else{
        	 flag = false;
         }
         
         return flag;
     }
	
	/*
	 * @param className algorithm.greedy.demo.HelloAction
	 * @return className.java
	 */
	public static String getFileName(String className){
		
		String[] temp = className.split("\\.");
		StringBuilder sb = new StringBuilder();
		sb.append(basePath);
		for (int i=0;i<temp.length;i++){
			sb.append("/").append(temp[i]);
		}
		sb.append(".java");
		System.out.println(sb.toString());
		
		return sb.toString();
	}
	
	public static void TestClassGet(String line) {
		if (line==null){
			return ;
		}
		int class_beg_pos = line.indexOf(JAVA_CLASS);
		int class_end_pos = 0;
		int supperClass_beg_pos = 0;
		int supperClass_end_pos = 0;
		if (line.contains(JAVA_EXTENDS)) {
			class_end_pos = line.indexOf(JAVA_EXTENDS);
			supperClass_beg_pos = class_end_pos;
		} else if (line.contains(JAVA_IMPLEMENTS)) {
			class_end_pos = line.indexOf(JAVA_IMPLEMENTS);
			supperClass_beg_pos = class_end_pos;
		} else {
			class_end_pos = line.indexOf("{");
			supperClass_end_pos = class_end_pos;
		}
		String className_fr = line.substring(class_beg_pos+JAVA_CLASS.length()+1, class_end_pos)
				.trim();
		// one.setClassName(packageName+"."+className_fr);

		System.out.println("setClassName\t" + "." + className_fr);

		if (supperClass_beg_pos > 0) {
			String subStr = line.substring(supperClass_beg_pos)
					.replace(JAVA_EXTENDS, "").replace(JAVA_IMPLEMENTS, "")
					.replace("{", "");
			String[] temp = subStr.split(" ");
			for (int i = 0; i < temp.length; i++) {
				if (temp[i] != null && !temp[i].equals(" ")&& !temp[i].equals("")) {
					// one.addSupperClasses(temp[i]);
					System.out.println(temp[i]);

				}
			}
		}
	}


	/*
	 * 从java源文件得到函数的具体信息
	 * 
	 * 代码是符合格式化的，函数定义的时候，单行包含{，必须进行换行操作
	 * 
	 * @param className
	 * @param funcName
	 * @param funcInAgrs {int,Long,List,Map,"Struct"}
	 * 
	 * 2015-5-9 16:56:37
	 */
	public static FunRecord getFuncRecord(String className,String funcName,String[] funcInAgrs) throws Exception{
		FunRecord one = new FunRecord();
		
		FileInputStream fis=new FileInputStream(getFileName(className));
        InputStreamReader isr=new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        //简写如下
        //BufferedReader br = new BufferedReader(new InputStreamReader(
        //        new FileInputStream("E:/phsftp/evdokey/evdokey_201103221556.txt"), "UTF-8"));
        String line="";
        String packageName = null;
        StringBuilder funcSrc = new StringBuilder();
        
        boolean isGetFuncSrc = false;
        Deque<String> stack=new LinkedList<String>();
        //using push to put a element
        //using pop to get a element
        
        boolean isGetConmments = false;
        while ((line=br.readLine())!=null) {
        	
        	//begin deal with comments
        	if (line.trim().startsWith("//")){
        		continue;
        	}
        	if (line.contains("/*")){
        		isGetConmments = true;
        		continue;
        	}
        	if (line.contains("*/")){
    			isGetConmments = false;
    			continue;
    		}
        	if (isGetConmments){
        		continue;
        	}
        	if (line.contains("{")){
        		for (int i=line.indexOf("{")+1;i<line.length()-1;i++){
        			if ((line.charAt(i)=='/')&&(line.charAt(i+1)=='/')){
        				line = line.substring(0, i);
        				break;
        			}
        		}
        	}
        	//end deal with comments
        	
        	if (line.contains(JAVA_PACKAGE)){
        		int package_beg_pos = line.indexOf(JAVA_PACKAGE);
        		int package_end_pos = line.indexOf(";");
        		packageName = line.substring(package_beg_pos+JAVA_PACKAGE.length()+1, package_end_pos).trim();
        		System.out.println("packageName\t"+packageName);
        		one.setPackageName(packageName);
        	}
        	if (line.contains(JAVA_IMPORT)){
        		int import_beg_pos = line.indexOf(JAVA_IMPORT);
        		int import_end_pos = line.indexOf(";");
        		String importClass = line.substring(import_beg_pos+JAVA_IMPORT.length()+1, import_end_pos).trim();
        		one.addImports(importClass);
        		System.out.println("importClass\t"+importClass);
        	}
        	
        	if (isClassLine(line)){
        		int class_beg_pos = line.indexOf(JAVA_CLASS);
        		int class_end_pos = 0;
        		int supperClass_beg_pos =0;
        		int supperClass_end_pos =0;
        		if (line.contains(JAVA_EXTENDS)){
        			class_end_pos = line.indexOf(JAVA_EXTENDS);
        			supperClass_beg_pos = class_end_pos+JAVA_EXTENDS.length()+1;
        		}else if(line.contains(JAVA_IMPLEMENTS)){
        			class_end_pos = line.indexOf(JAVA_IMPLEMENTS);
        			supperClass_beg_pos = class_end_pos+JAVA_IMPLEMENTS.length()+1;
        		}else{
        			class_end_pos = line.indexOf("{");
        			supperClass_end_pos = class_end_pos;
        		}
        		String className_fr = line.substring(class_beg_pos+JAVA_CLASS.length()+1, class_end_pos).trim();
        		one.setClassName(packageName+"."+className_fr);
        		
        		System.out.println("setClassName\t"+packageName+"."+className_fr);
        		
        		if (supperClass_beg_pos>0){
        			String subStr = line.substring(supperClass_beg_pos).replace(JAVA_EXTENDS, "")
        					.replace(JAVA_IMPLEMENTS, "").replace("{", "");
        			String[] temp = subStr.split(" ");
        			for (int i=0;i<temp.length;i++){
        				if (temp[i] != null && !temp[i].equals(" ")&& !temp[i].equals("")) {
        					one.addSupperClasses(temp[i]);
        				}
        			}
        		}
        		
        	}
        	
        	if (isFuncLine(line)&&isFuncLine(line,funcName,funcInAgrs)){
        		setFuncDesc(line, one);
        		stack.push("{");
        		funcSrc.append(line);
        		isGetFuncSrc = true;
        		continue;
        	}
        	
        	//begin get FuncSrc
        	if (stack.size()>0&&isGetFuncSrc){
//        		System.out.println(stack.size());
        		//.append("\r\n")
        		funcSrc.append(line);
        		
	        	for (int i=0;i<line.length()&&stack.size()>0;i++){
	        		if (line.charAt(i)=='{'){
	        			stack.push("{");
	        		}
	        		if (line.charAt(i)=='}'){
	        			stack.poll();
	        		}
	        	}
        	}
        	
        	if (stack.size()==0&&isGetFuncSrc){
        		isGetFuncSrc = false;
        		break;
        	}
        	
        	//end get FuncSrc
        	
        	
        }
        String funcSrcTemp = funcSrc.toString().replace("\t", "");
        while(funcSrcTemp.contains("  ")){
        	funcSrcTemp = funcSrcTemp.replace("  ", " ");
        }
        System.out.println("FuncSrc\t"+funcSrcTemp);
        one.setFuncSrc(funcSrcTemp);
        
        br.close();
        isr.close();
        fis.close();

		
		return one;
	}
	
	
	public static int testGetInnerFunc(String line){
		String funcName = "";
		String funcInAgrs = "";
		String funcOutArg = "";
		
		boolean funcHasReturnValue = false;
		int pos = line.lastIndexOf(".");
		int endPos = pos;
		for (int k=pos+1;k<line.length();k++){
			if (line.charAt(k)=='('){
				endPos = k;
				break;
			}
		}
		int begPos = 0;
		int begHelpCount = 0;
		for (int j=pos-1;j>0;j--){
			if (line.charAt(j)=='='){
				begPos = j;
				funcHasReturnValue = true;
				break;
			}
			if (line.charAt(j)=='('){
				if (begHelpCount==0){
					begPos = j;
					break;
				}else{
					begHelpCount--;
				}
			}
			if (line.charAt(j)==')'){
				begHelpCount++;
			}
		}
		
		funcName = line.substring(begPos+1,endPos).replace(" ", "");
		//funcName还需要处理链式调用关系和上下文的获取,最终的函数签名为OutArg package.class.menthod(inArgs)
		//链式函数调用，只处理最后一级的函数，这里假设CRM代码不会出现Fluent风格的函数调用
		funcInAgrs = line.substring(endPos+1,endPos+line.substring(endPos).indexOf(")"));
		if (funcHasReturnValue){
			funcOutArg = line.substring(0, begPos);
		}
		
		System.out.println("funcName\t"+funcName);
		System.out.println("funcInAgrs\t"+funcInAgrs);
		System.out.println("funcOutArg\t"+funcOutArg);
		
		return 1;
	}
	
	/* 
	 *  逗取函数内部的所有子函数
	 * 
	 *  todeal =
	 *  public int work(String id,String name){IHelloSV sv = (IHelloSV)ServiceManager.getService(id);String res = sv.sayHello(name);System.out.println(res);sv.doExe(sv);if (true){test(1);}sv.sayBye(name);JavascHelp.TestClassGet(null);return 1;}
	 *  
	 *   函数判断方法： 
	 *  1  出现逗号。且出现（）对 和；
	 *     加强处理： CmServiceFactory.getBusiMode(AbstractAccountQueryModelImpl.class)).queryCount(xmlComdition);
	 *  2 一行中未出现=，但是出现了()  那么()内部可能有一个函数，()前置如果非空，则肯定是一个函数名称
	 *     加强处理： if (test(FuncA.BuildB(b).BuildB(c)){
	 */
	public static List<FunRecord> getSubFuntions(String todeal){
		List<FunRecord> result = new ArrayList<FunRecord>();
		Map<String,String> funcSrcContenxt = new HashMap<String,String>();
		
//		读取到{,},;即认为换行操作
		int line_beg_pos = 0;
		List<String>  lines = new ArrayList();
		for (int i=0;i<todeal.length();i++){
			if (todeal.charAt(i)=='{'||todeal.charAt(i)=='}'||todeal.charAt(i)==';'){
				lines.add(todeal.substring(line_beg_pos, i+1));
				line_beg_pos = i+1;
			}
		}
		
		for (int i=0;i<lines.size();i++){
			System.out. println(lines.get(i));
		}
		//第一行为函数定义  最后一行为}
		for (int i=1;i<lines.size()-1;i++){
			String line = lines.get(i);
			//函数，变量定义的上下文
			int defineContextPos=0;
			for (int i1=0;i1<line.length()-2;i1++){
				if (line.charAt(i1)=='='&&line.charAt(i1+1)!='='){
					defineContextPos = i1;
					break;
				}
			}
			//这个时候，肯定为 String  a =; List<String> todeal = 
			String[] temp = line.trim().substring(0, defineContextPos).split(" ");
			for (int i2=1;i2<temp.length&&temp.length>1;i2++){
				if (StringUtils.isNotBlank(temp[i2])){
					funcSrcContenxt.put(temp[i2],temp[0]);
					break;
				}
			}

			String funcName = "";
			String funcInAgrs = "";
			String funcOutArg = "";
			/*判断条件1  出现逗号。且出现（）对 和； 
			 * 函数名称的endPos: 最后一个。往后查找，遇到( 的pos，
			 * 函数名称的beginPos : 最后一个。往前找，如果遇到=,行首，则终止，第一次(,则终止，如果先遇到)则继续往前查找，并可消除继续查找过程中遇到的(，主到“首次”遇到(
			 *               
			 */
			if (line.contains(".")&&line.contains("(")){
				//String res = sv.sayHello(name); & 接口(类）定义
				//String res = this.getxxxxSV().sayHello(name); & 接口(类）定义
				//sv.sayHello(name);
				//int a=(sv.funcA(x)==c?1:0);   //这里假设不出现==两边都出现有函数调用，如果有，后续特殊处理
				if (line.endsWith(";")){
					
					boolean funcHasReturnValue = false;
					int pos = line.lastIndexOf(".");
					int endPos = pos;
					for (int k=pos+1;k<line.length();k++){
						if (line.charAt(k)=='('){
							endPos = k;
							break;
						}
					}
					int begPos = 0;
					int begHelpCount = 0;
					for (int j=pos+1;j>0;j--){
						if (line.charAt(j)=='='){
							begPos = j;
							funcHasReturnValue = true;
							break;
						}
						if (line.charAt(j)=='('){
							if (begHelpCount==0){
								begPos = j;
								break;
							}else{
								begHelpCount--;
							}
						}
						if (line.charAt(j)==')'){
							begHelpCount++;
						}
					}
					
					funcName = line.substring(begPos+1,endPos).replace(" ", "");
					//funcName还需要处理链式调用关系和上下文的获取,最终的函数签名为OutArg package.class.menthod(inArgs)
					//链式函数调用，只处理最后一级的函数，这里假设CRM代码不会出现Fluent风格的函数调用
					funcInAgrs = line.substring(endPos+1,line.substring(endPos).indexOf(")"));
					if (funcHasReturnValue){
						funcOutArg = line.substring(0, begPos);
					}
					
				}
				//if (sv.sayHello(name)==name){   
				//if(4==sv.funcA(a)){ 
				else if (line.endsWith("{")){   // && 分支处理 1
					
				}
				
				
			}
			//判断条件2 未出现=，但是出现了()  那么()内部可能有一个函数，()前置如果非空，则肯定是一个函数名称
			if (!line.contains("=")&&line.contains("(")){
				//test(1);
				
				// if (test(1)){   if (testA(testB(c))){  if(4==funcA(a)){ && 分支处理 2
			}
			
			//判断条件3  
			//特例 1 if(4==funcA(a)){ 
			//特例 2  int a=(funcA(x)==c?1:0);
			if(line.contains("==")&&line.contains("(")&&(line.contains("if")||line.contains("while"))){
				
			}
			
			//分支处理 3 直接出现{  if(true){   if(4==type){
			if (line.contains("{")){
				
			}
			
			//处理类定义
		}
		
		return result;
	}
	
	
	/*  前置条件 为 从java源代码"简单"一行
	 *  其判断依据：  一行里面最多只有一对()
	 *  
	 * ，且包含 函数 判断条件
	 *  1  只出现一个。 及至少一个 (       String res = sv.sayHello(name);
	 *  2 未出现. 出现( 且(前面是有标示符    int a = test(b);  int a = (a+1)*3；
	 *  
	 *  
	 */
    public static boolean _isFuncLineInLocalFunSrc(String line){
    	boolean flag = false;
    	if (line.contains("(")){
    		if (line.contains(".")){
	    		if (!line.substring(line.indexOf(".")+1).contains(".")){
	    			flag = true;
	    		}
    		}else{
    			int endPos = line.indexOf("(");
    			int begPos = 0;
    			boolean isHaveAlpha = false;
    			for (int i=endPos-1;i>0;i--){
    				begPos  = i;
    				if ((line.charAt(i)!=' ')&&(line.charAt(i)!='=')){
    					isHaveAlpha = true;
    				}
    				if (line.charAt(i)=='='){
    					break;
    				}
    			}
    			if (((endPos-begPos) >1) && (isHaveAlpha) ){
    				flag = true;
    			}
    		}
    	}
    	return flag;
    }
	
	public static void main(String[] args) throws Exception {
//		System.out.println(JavascHelp.getFileName("algorithm.greedy.demo.HelloAction"));
		
		System.out.println(JavascHelp.getFuncRecord("algorithm.greedy.demo.HelloAction","work",new String[]{"String","String"}));
		
//		String line="public class HelloAction extends Reader implements AA{";
//		JavascHelp.TestClassGet(line);
//		System.out.println(JavascHelp.isClassLine(line));
		
		
//		String line="public int work(String id,String name) throws Exception{";
//		JavascHelp.setFuncDesc(line, null);
//		String line = "public int work(String id,String name){IHelloSV sv = (IHelloSV)ServiceManager.getService(id);String res = sv.sayHello(name);System.out.println(res);sv.doExe(sv);if (true){test(1);}sv.sayBye(name);JavascHelp.TestClassGet(null);return 1;}";
//		System.out.println(JavascHelp.getSubFuntions(line));
		
//		String line = "IHelloSV sv = (IHelloSV)ServiceManager.getService(id)";
//		System.out.println(JavascHelp.testGetInnerFunc(line));
		
//		FunRecord node = JavascHelp.getFuncRecord("algorithm.greedy.demo.HelloAction","work",new String[]{"String","String"});
//		System.out.println("##########################################################");
//		String line = "";
//		List<FunRecord> res = node.getSubFuntions();
//		System.out.println("FFFFFFFF");
		
		
//		String line = "String res = sv.sayHello(name);";
//		String line1 = "int a = test(b)";
//		String line2 = "int a = (a+1)*3";
//		String line3 = "test(b)";
//		String line4 = "(a+1)*3";
//		System.out.println(JavascHelp._isFuncLineInLocalFunSrc(line));
//		System.out.println(JavascHelp._isFuncLineInLocalFunSrc(line1));
//		System.out.println(JavascHelp._isFuncLineInLocalFunSrc(line2));
//		System.out.println(JavascHelp._isFuncLineInLocalFunSrc(line3));
//		System.out.println(JavascHelp._isFuncLineInLocalFunSrc(line4));
//		
	}

}
