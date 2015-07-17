package algorithm.greedy.demo.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.BorderUIResource.BevelBorderUIResource;

import org.apache.commons.lang.StringUtils;

import algorithm.greedy.demo.factory.ServiceManager;
import algorithm.greedy.demo.interfaces.IHelloSV;

public class FunRecord {
	
	private String packageName;
	private String className;//存放的是完整的类名
	private List<String> importClasses = new ArrayList<String>();//{"java.util.ArrayList",...}
	private Map<String,String> importClassesContext = new HashMap<String,String>();//key=ArrayList,value=java.util.ArrayList
	
	private List<String> supperClasses = new ArrayList<String>();//只保存当前函数所在java源文件内容显示的继承的类或接口
	private String funName;
	private List<String> funcInArgs = new ArrayList<String>();//这里存放的是原始入参{"String name","int[] array"}
	private String funcInArgsOrgin ;//="String name,int[] array"
	private String funcOutArg;
	
	private boolean inArgsInit = false;//在调用setFuncInArgsOrgin 之后，inArgsInit = true
	private boolean outArgInit = false;//在调用 setOutArg 之后，outArgInit = true
	
	private String funcSrc;//函数的方法体源代码
	
	private String funcSign; //函数的唯一标示 其值为className.funName(int,long,Object[]....)
	
	private List<FunRecord> innerFunctions = new ArrayList<FunRecord>();//函数的内部调用，函数内部调用调用了其他函数，故这里只处理此函数的签名
	private Map<String,String> funcVarsContenxt = new HashMap<String,String>();// (k,v)=(id,String)
	
	private static String[] stopFlags = new String[]{"=","==","&&","||","!","+","-","^","/","<<",">>","~","&","|",","};
	
	private static String[] expressionFlags = new String[]{"!","==","&&","||","+","-","^","/","<<",">>","~","&","|"};
	private static String[] caseCondtion = new String[]{"if","case","while","for"};
	
	public static boolean isCaseCondtion(String line){
		boolean flag = false;
		for (int i=0;i<caseCondtion.length;i++){
			if (line.contains(caseCondtion[i])){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public static boolean isStopFlag(String aChar){
		boolean flag = false;
		for (int i=0;i<stopFlags.length;i++){
			if (stopFlags[i].equals(aChar)){
				flag = true;
				break;
			}
		}
		return flag;
	}
	public static boolean isStopFlag(char aChar){
		return isStopFlag(new String(new char[]{aChar}));
	}
	
	/*
	 * 
	 */
	@Deprecated
	public static boolean isInnerFuctionLine_no(String line){
		boolean flag = false;
		
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
			
			flag = true;
			
		}
		//判断条件2 未出现=，但是出现了()  那么()内部可能有一个函数，()前置如果非空，则肯定是一个函数名称
		if (!line.contains("=")&&line.contains("(")){
			//test(1);
			// if (test(1)){   if (testA(testB(c))){  if(4==funcA(a)){ && 分支处理 2
			flag = true;
		}
		
		//判断条件3  
		//特例 1 if(4==funcA(a)){ 
		//todo 特例 2  int a=(funcA(x)==c?1:0);
		//todo 特例 3  for (int a=(funcA(x); a<10; a++){
		if(line.contains("==")&&line.contains("(")&&(line.contains("if")||line.contains("while"))){ //特例 1
			String[] temp = line.split("==");
			for (int i=0;i<temp.length;i++){
//				if (isInnerFuctionLine(temp[i])){
//					flag = true;
//					break;
//				}
			}
		}
		
		return flag;
	}
	
	
	/*
	 * 判断一行是否含有表达式
	 * 表达式符  ==,&&,||,!,+,-,^,/,<<,>>,~,&,| 
	 * 
	 * 在将一行退化成simpleJavaLine中使用
	 */
	public static boolean hasExpression(String line){
		boolean flag = false;
		for (int i=0;i<expressionFlags.length;i++){
			if (line.contains(expressionFlags[i])){
				flag = true;
				break;
			}
		}
		return flag;
	}
	/*
	 * 判断是否为遇到了express符
	 */
	public static boolean isExpresssionFlag(char aChar){
		
		char[] expressionFlags = new char[]{'!','=','&','|','+','-','^','/','<','>','~'};
		boolean flag = false;
		for (int i=0;i<expressionFlags.length;i++){
			if (expressionFlags[i]==aChar){
				flag = true;
				break;
			}
		}
		return flag;
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
	 * if ( ((!A==B)&&flag)&&(3*C+D) ){
	 * result:
	 * E0=(!A)
	 * E1=(E0==B)
	 * E2=(E&&flag);
	 * E30=(3*C);
	 * E3=(E30+D);
	 * E4=(E2&&E3);
	 * 
	 * @param line
	 */
	public static String[] getLineByExpressionLine(String line){
		List<String> result =  new ArrayList<String>();
		
		
		
		
		return result.toArray(new String[0]);
	}
	
	/*
	 * 处理强制类型转换 忽略掉  int a = (String)(age); 退化为 int a = (age);
	 * 算法       对于一行中(xxx)中的内容,其左括号到终止符或行首的位置之间的字符串为空
     *      而且(xxx)内容不包含终止符及( ,且其内容在上下文变量是未找到{这个先收敛为首字符是大写，及为类名}
     *     
     *     String res = (String[])Service.get2().get(test(B)).sayHello((String[])testC(value),id);
     *     int a = (String)(age+1); //约定 变量和函数名称首字符都不是大写，类名首字母是大写的
     *     return (String[])result.toArray(new String[0]);
	 */
	public static String dealTypeChangeInogore(String line){
		if (isSimleJavaLine(line)){
			return line;
		}
		String help = line.trim();
		char[] todeal = help.toCharArray();
		
		//找到所有(
		for (int i=0;i<help.length()&&todeal[i]!='#';i++){
			if (help.charAt(i)=='('){
				int endPos = i;
				int begPos = -1;
				for (int j=endPos-1;j>0;j--){
					if(help.charAt(j)=='('||isStopFlag(help.charAt(j))){
						begPos = j;
						break;
					}
				}
				String subPrefix= help.substring(begPos+1, endPos);
				if (StringUtils.isBlank(subPrefix)||"return".equals(subPrefix.trim())){
					int _pos = i+1;
					for (int k=i+1;k<help.length();k++){
						if (help.charAt(k)=='('||isStopFlag(help.charAt(k))){
							break;
						}
						if (help.charAt(k)==')'){
							String innerText = help.substring(_pos,k);
							if (StringUtils.isNotBlank(innerText)){
								if ((innerText.charAt(0)>='A')&&(innerText.charAt(0)<='Z')){
									//i-k =''
									for (int k2=i;k2<=k;k2++){
										todeal[k2]='#';
									}
								}
							}
						}
					}
				}
			}
		}
		StringBuilder result = new StringBuilder();
		for (int k2=0;k2<todeal.length;k2++){
			if (todeal[k2]!='#'){
				result.append(todeal[k2]);
			}
		}
		
		return result.toString();
	}
	
	/*
	 * 将复杂的一行java代码转换为几行简单java代码：
	 * 
	 * 简单java代码的定义：
	 *  1 以;或者{结尾 
	 *  2 一行中只出现一个(),
	 *  ps。 特例函数类型转换的多余的()内容将被抛弃
	 * 
	 *  demo1 : 
		if ( (testA(value)==testB(value)&&flag)&&testC(value)||(testD(value))&&flag||(4==type) ){
		demo2:
			this.getSV().dowork(new String("123"),id,sv.getSV2(value));
			temp_1 = ${temp_2}.dowork(new String("123"),id,sv.getSV2(value)); 
			   temp_1 = .dowork(temp_1_2,id,temp_1_1); //此行为当前行最终表达式。需要一个队列保存解析的顺序，将最终表达式插入到队首
			   temp_1_1 = sv.getSV2(value);
			   temp_1_2 = new String("123");
			temp_2 = ${temp_3}.getSV2();
			temp_3 = ${temp_4}.getSV();
			temp_4 = this;
		--生产的simpleJavaLine 为逆序，可借助一个整体的栈
		demo3:
		  if (this.getSV2().dowork(test(name))==Util.getSV3().test(value)) {
		demo:4
		   int a=(a+4)*(a+5) - sv.test(a); 
		demo5：或者称干扰 (String[])result.toArray(new String[0]);
		demo6：这样形式是不处理的。类似System.out.println();  这样静态类的静态类的方法调用,此行不做任何处理。只打印此行,
		
	 * 简单分析 ： 1 由于函数调用是从左往右的，于是退化的方式是从右到左， 2 如果遇见()内部再出现函数调用的，此()内部的函数调用可单独提取出来。
	 * simpleJavaLine算法描述： 
	 *   类强制转换。忽略此()中的内容。 
	 * 第一步：划分 
	 *  从最后一个）往前找与之匹配的（，找到之后，再往前匹配，如果到首行或者遇到终止符==,&&,||,!,+,-,^,/,<<,>>,~,&,|,. 则终止
	 *  否则
	 *  如果终止符是. 则往前查找，如果遇到)则停止 否则  	查找终止符,如果终止符是.则停止，直到首行的pos，那么(pos,.Pos)={this,Util}为当前函数调用链的根类 处理.调用的替换
	 * 	否则，处理正常()的替换 替换的同时，需要把位置也记录下来，然后对当前行进行重新组装。
	 *  这个顺序保存到一个临时队列上。然后对此访问此队列，将其值都放入栈中（最终的结果）
	 * 第二步： 递归处理第一步得到的划分行
	 * 最后一步：按照java运行时候的顺序（从左到右，如果遇到同级()，则同时计算()内容，所有运算都执行），组装得到所有的simpleJavaLine
	 * 
	 * 2 数据结构设计 Stack+Queue 
	 * 划分的结果使用队列来保存，然后对划分的结果入栈，最终栈的访问顺序即可simpleJavaLine的顺序。
	 */
	
	public String[] getSimpleJavaLines(String line){
		List<String> result = new ArrayList<String>();
		result.toArray(new String[0]);
		return (String[])result.toArray(new String[0]);
	}
	
	/*  前置条件 为 从java源代码"简单"一行
	 *  其判断依据：  一行里面最多只有一对()
	 *  
	 */
    public static boolean isSimleJavaLine(String line){
    	boolean flag = false;
    	if (line.contains("(")){
    		if (!line.substring(line.indexOf("(")+1).contains("(")){
    			flag = true;
    		}else{
    			flag = false;
    		}
    	}else{
    		flag = true;
    	}
    	return flag;
    }
	
	/*  前置条件 为 从java源代码"简单"一行
	 *  其判断依据：  一行里面最多只有一对()
	 *  
	 * ，且包含 函数 判断条件
	 *  1  只出现一个。 及一个 (       String res = sv.sayHello(name);
	 *  2 未出现. 出现一个( 且(前面是有标示符    int a = test(b);  int a = (a+1)*3；
	 *  
	 */
    public static boolean _isFuncLineBySimleJavaLine(String line){
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
    
	/*
	 *  从java源代码"简单"一行获取函数定义
	 *  此行包含. 和 ()  demo:
	 *  1 直接调用
	 *  String res = sv.sayHello(name);
	 *  sv.sayHello(name);
	 *  int a=(sv.funcA(x)==c?1:0);
	 *  int res = sv.sayHello(new Long(new Long(1)));
	 *  HelpUtil.sayHello(name);//静态调用
	 *  
	 *  2 链式调用  先将代码退化成每行只有一个函数调用。
	 *  String res = this.getxxxxSV().sayHello(name);
	 *  String res = sv.testA(sv2.testB(value));             
	 *  String res = HelpUtil.testA(sv2.testB(value)).testC(value);  这种情况下，只计算xx。testC
	 *  
	 *  @param line
	 *  @return FunNode
	 *  
	 *  算法描述：
	 *  1 函数的endPos : 从最后一个)往前匹配(,如果匹配到，则认为(前面就是函数名称的结尾
	 *  2 函数的begPos ：  从 endPos 往前匹配第一个.
	 *  
	 *  函数前缀begPos从前匹配，第一个(,或=，或到首行
	 *  
	 *  @author linfeng
	 */
	@Deprecated
	private FunRecord _getInnerFunctionsByLine1_no(String line,FunRecord currentNode){
		
		
		FunRecord inneNode = new FunRecord();
		
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
		int begPos = -1;
		int begMatch = 0;
		for (int j=pos-1;j>0;j--){
			if (line.charAt(j)=='='){
				begPos = j;
				funcHasReturnValue = true;
				break;
			}
			if (line.charAt(j)=='('){
				if (begMatch==0){
					begPos = j;
					break;
				}else{
					begMatch--;
				}
			}
			if (line.charAt(j)==')'){
				begMatch++;
			}
		}
		
		//funcName还需要处理链式调用关系和上下文的获取,最终的函数签名为OutArg package.class.menthod(inArgs)
		//链式函数调用，只处理最后一级的函数，这里假设CRM代码不会出现Fluent风格的函数调用
		
		funcName = line.substring(begPos+1,endPos).trim().replace(" ", "");
		System.out.println("funcName todeal \t"+funcName);
		//直接调用 sv.sayHello FactoryUtil.sayHello
		String prefix = funcName.substring(0,funcName.lastIndexOf("."));
		String innerClassFullName = null;
		 if (!prefix.contains("(")){
			 String innerClassName = null;
			 if(prefix.charAt(0)>='A'&&prefix.charAt(0)<='Z'){ //首字母大写的认为是类名
				 innerClassName = prefix;
			 }else {//小写的类信息需要从上下文中获取
				 if(prefix.equals("this")){
					 innerClassName = currentNode.getClassName();
				 }else{
					 innerClassName  = currentNode.getVarsDefineByContenxt(prefix);
				 }
				 
			 } 
			 innerClassFullName = currentNode.getImportClassByKey(innerClassName);
		 }else{ 
	     //todo 出现链式调用   this.getxxxxSV().sayHello  getxxxxSV().sayHello ((IHelloSV)Factory.getxxxxSV()).sayHello
			 
		 }
		 inneNode.setClassName(innerClassFullName);
		 inneNode.setFunName(funcName.substring(funcName.lastIndexOf(".")+1));
		 
		 System.out.println("innerClassFullName\t"+innerClassFullName);
		 System.out.println("innerFuncName\t"+inneNode.getFunName());
		
		//deal Args
		funcInAgrs = line.substring(endPos+1,endPos+line.substring(endPos).indexOf(")"));
		//sv.sayHello(1,name,new Long(1));
		String[] funcInArgsTemp = funcInAgrs.split(",");
		StringBuilder inneFuncInArgsOrgin = new StringBuilder();
		for (int k1=0;k1<funcInArgsTemp.length;k1++){
			if (funcInArgsTemp[k1].startsWith("new")){
				int funcInArgPos = 3;
				for (int i=3;i<funcInAgrs.length();i++){
					if (funcInAgrs.charAt(i)=='('){
						funcInArgPos = i;
						break;
					}
				}
				inneFuncInArgsOrgin.append(funcInAgrs.substring(3, funcInArgPos).trim()).append(" object");
			}else{
				inneFuncInArgsOrgin.append(currentNode.getVarsDefineByContenxt(funcInArgsTemp[k1]))
				.append(" ").append(funcInArgsTemp[k1]);
			}
			if (k1<funcInArgsTemp.length-1){
				inneFuncInArgsOrgin.append(",");
			}
		}
		System.out.println("inneFuncInArgsOrgin\t"+inneFuncInArgsOrgin.toString());
		inneNode.setFuncInArgsOrgin(inneFuncInArgsOrgin.toString()); //函数入参id,String
		
		
		if (funcHasReturnValue){
			funcOutArg = line.substring(0, begPos);
			inneNode.setFuncOutArg(currentNode.getVarsDefineByContenxt(funcOutArg));
		}
		System.out.println("inneFuncOutArgsOrgin\t"+inneNode.getFuncOutArg());
		
		return inneNode;
	}
	/*
	 * 从行获取函数的定义
	 * 
	 * 不考虑分支处理
	 * 
	 * @author linfeng 2015-5-11 17:06:46
	 */
	@Deprecated
	public FunRecord getInnerFunctionsByLine_no(String line,FunRecord currentNode){
		System.out.println("###########begin getInnerFunctionsByLine#############");
		FunRecord inneNode = new FunRecord();
		
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
//				inneNode = _getInnerFunctionsByLine1(line,currentNode);
			}
			//if (sv.sayHello(name)==name){   
			//if(4==sv.funcA(a)){ 
			//if (sv.funcA(a)){ 
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
		
		
		//
		currentNode.addInnerFunc(inneNode);
		
		System.out.println("###########end getInnerFunctionsByLine");
		return inneNode;
	}
	
	/*  @param funcSrc
	 *  先对{,},;进行换行操作
	 *  demo:	public int work(String id,String name){
	 *  IHelloSV sv = (IHelloSV)ServiceManager.getService(id);
	 *  String res = sv.sayHello(name);
	 *  System.out.println(res);
	 *  sv.doExe(sv);
	 *  if (true){
	 *  test(1);
	 *  }
	 *  sv.sayBye(name);
	 *  JavascHelp.TestClassGet(null);
	 *  return 1;
	 *  }
	 *  
	 *  @return List<FunRecord>
	 *  
	 *  @author linfeng  2015-5-13 11:18:56
	 *  
	 */
	public  List<FunRecord> getSubFuntions(){
		String todeal = this.funcSrc;
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
			System.out.println("line \t"+i);
			String line = lines.get(i).trim();
			//将每行退化成 java源代码"简单"一行  其判断条件  一行里面最多只有一对()
			String[] simpleJavaLines = getSimpleJavaLines(line);
			for (int j=0;j<simpleJavaLines.length;j++){
				//第一步： 函数，变量定义的上下文
				setFunVarsContext(simpleJavaLines[j],this);
				// 第二步： 处理得到内部子函数
				if (_isFuncLineBySimleJavaLine(simpleJavaLines[j])){
					//todo
					FunRecord innerFunc  = getInnerFunRecordBySimleJavaLine(simpleJavaLines[j],this);
					this.addInnerFunc(innerFunc);
				}
			}
		}
		
		return this.getInnerFunctions();
	}
	
	/*
	 *  从simpleJavaLine活动函数的名称，入参
	 *  再根据上下文currentNode，分析得到函数的完整定义
	 *  
	 *  @param simpleJavaLine 
	 *  @param currentNode
	 *  @return FunRecord 
	 *  
	 *  @author linfeng 2015-5-13 11:17:56
	 */
	private FunRecord getInnerFunRecordBySimleJavaLine(String simpleJavaLine,FunRecord currentNode){
		//todo
		FunRecord innerFunc = new FunRecord();
		
		return innerFunc;
	}
	
	/* 
	 *  @param simpleJavaLine  
	 *  条件：不包含==，只包含一个()。 特例如何出现强制类型转换，可包含多个()
	 * 
	 *  得到变量的上下文
	 *  int a = 0;
	 *  IHello sv = (IHelloSV)ServiceMannager.getService(id); 
	 *  
	 *  for (int a=0;a<10;a++)
	 *  for (String a :Temp[])
	 */
	private FunRecord setFunVarsContext(String simpleJavaLine,FunRecord currentNode){
		String tempVars = "";
		if (!simpleJavaLine.contains("for")){
			int defineContextPos=0;
			for (int i1=0;i1<simpleJavaLine.length()-2;i1++){
				if (simpleJavaLine.charAt(i1)=='='){
					defineContextPos = i1;
					break;
				}
			}
			tempVars = simpleJavaLine.substring(0, defineContextPos);
			
		}else
		//情况2  处理在for中定义的变量 for (int a=1;...) for (String a :Temp[])
		{
			int defineContextPos=0;
			for (int i1=0;i1<simpleJavaLine.length()-2;i1++){
				if (simpleJavaLine.charAt(i1)=='='){
					defineContextPos = i1;
					break;
				}
			}
			if (simpleJavaLine.indexOf(":")>0&&(defineContextPos==0)){
				defineContextPos = simpleJavaLine.indexOf(":");
			}
			tempVars = simpleJavaLine.substring(simpleJavaLine.indexOf("(")+1, defineContextPos);
		}
		
		String[] temp = tempVars.split(" ");
		for (int i2=1;i2<temp.length&&temp.length>1;i2++){
			if (StringUtils.isNotBlank(temp[i2])){
				currentNode.setVarsDefineByContenxt(temp[i2],this.getImportClassByKey(temp[0]));
				break;
			}
		}
		
		return currentNode;
		
	}
	
	
	public String funcSign() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.funcOutArg).append(" ");
		
		sb.append(this.className).append(".")
		.append(this.funName).append("(");
		for (int i=0;i<funcInArgs.size();i++){
			String[] temp = funcInArgs.get(i).trim().split(" ");
			//数组的写法可以int[] array 或 int array[]
			if (funcInArgs.get(i).contains("[")&&!temp[0].contains("[")){
				sb.append(temp[0]).append("[]");
			}else{
				sb.append(temp[0]);
			}
			if (i!=funcInArgs.size()-1){
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	public String toString(){
		return funcSign();
	}
	
	/*
	 * @param funcInArgsOrgin  "String name,int[] array"
	 * 
	 */
	public void setFuncInArgsOrgin(String funcInArgsOrgin) {
		if (funcInArgsOrgin==null||inArgsInit){
			return;
		}
		this.funcInArgsOrgin = funcInArgsOrgin;
		String[] temp = funcInArgsOrgin.trim().split(",");
		if (this.funcInArgs.size()>0){
			this.funcInArgs.clear();
		}
		for (int i=0;i<temp.length;i++){
			this.funcInArgs.add(temp[i]);
			String[] temp2 = temp[i].split(" ");
			for (int k=1;k<temp2.length;k++){
				if (StringUtils.isNotBlank(temp2[k])){
					this.funcVarsContenxt.put(temp2[k], temp2[0]);
				}
			}
		}
		inArgsInit = true;
	}
	
	public void addInnerFunc(FunRecord child){
		this.innerFunctions.add(child);
	}
	public void addImports(String importClass){
		this.importClasses.add(importClass);
		String key = importClass.substring(importClass.lastIndexOf(".")+1);
		this.importClassesContext.put(key, importClass);
	}
	public String getImportClassByKey(String name){
		return this.importClassesContext.get(name)==null?name:this.importClassesContext.get(name);
	}
	public void addSupperClasses(String supperClass){
		this.supperClasses.add(supperClass);
	}
	
	//............................

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<String> getImportClasses() {
		return importClasses;
	}

	public void setImportClasses(List<String> importClasses) {
		this.importClasses = importClasses;
	}

	public String getFunName() {
		return funName;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}

    
	public List<String> getSupperClasses() {
		return supperClasses;
	}
	public void setSupperClasses(List<String> supperClasses) {
		this.supperClasses = supperClasses;
	}
	public List<String> getFuncInArgs() {
		return funcInArgs;
	}
	public void setFuncInArgs(List<String> funcInArgs) {
		this.funcInArgs = funcInArgs;
	}
	public String getFuncSrc() {
		return funcSrc;
	}

	public void setFuncSrc(String funcSrc) {
		this.funcSrc = funcSrc;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	

	public String getFuncInArgsOrgin() {
		return funcInArgsOrgin;
	}


	public String getFuncOutArg() {
		return funcOutArg;
	}

	public void setFuncOutArg(String funcOutArg) {
		this.funcOutArg = funcOutArg;
	}

	public Map<String, String> getImportClassesContext() {
		return importClassesContext;
	}

	public List<FunRecord> getInnerFunctions() {
		return innerFunctions;
	}

	public String getVarsDefineByContenxt(String key) {
		return this.funcVarsContenxt.get(key)==null?key:this.funcVarsContenxt.get(key);
	}
	public void setVarsDefineByContenxt(String varInstance,String varObject){
		this.funcVarsContenxt.put(varInstance, varObject);
	}
	

}
