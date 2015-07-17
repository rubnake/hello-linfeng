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
	private String className;//��ŵ�������������
	private List<String> importClasses = new ArrayList<String>();//{"java.util.ArrayList",...}
	private Map<String,String> importClassesContext = new HashMap<String,String>();//key=ArrayList,value=java.util.ArrayList
	
	private List<String> supperClasses = new ArrayList<String>();//ֻ���浱ǰ��������javaԴ�ļ�������ʾ�ļ̳е����ӿ�
	private String funName;
	private List<String> funcInArgs = new ArrayList<String>();//�����ŵ���ԭʼ���{"String name","int[] array"}
	private String funcInArgsOrgin ;//="String name,int[] array"
	private String funcOutArg;
	
	private boolean inArgsInit = false;//�ڵ���setFuncInArgsOrgin ֮��inArgsInit = true
	private boolean outArgInit = false;//�ڵ��� setOutArg ֮��outArgInit = true
	
	private String funcSrc;//�����ķ�����Դ����
	
	private String funcSign; //������Ψһ��ʾ ��ֵΪclassName.funName(int,long,Object[]....)
	
	private List<FunRecord> innerFunctions = new ArrayList<FunRecord>();//�������ڲ����ã������ڲ����õ���������������������ֻ����˺�����ǩ��
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
		
		/*�ж�����1  ���ֶ��š��ҳ��֣����� �ͣ� 
		 * �������Ƶ�endPos: ���һ����������ң�����( ��pos��
		 * �������Ƶ�beginPos : ���һ������ǰ�ң��������=,���ף�����ֹ����һ��(,����ֹ�����������)�������ǰ���ң����������������ҹ�����������(���������״Ρ�����(
		 *               
		 */
		if (line.contains(".")&&line.contains("(")){
			//String res = sv.sayHello(name); & �ӿ�(�ࣩ����
			//String res = this.getxxxxSV().sayHello(name); & �ӿ�(�ࣩ����
			//sv.sayHello(name);
			//int a=(sv.funcA(x)==c?1:0);   //������費����==���߶������к������ã�����У��������⴦��
			
			flag = true;
			
		}
		//�ж�����2 δ����=�����ǳ�����()  ��ô()�ڲ�������һ��������()ǰ������ǿգ���϶���һ����������
		if (!line.contains("=")&&line.contains("(")){
			//test(1);
			// if (test(1)){   if (testA(testB(c))){  if(4==funcA(a)){ && ��֧���� 2
			flag = true;
		}
		
		//�ж�����3  
		//���� 1 if(4==funcA(a)){ 
		//todo ���� 2  int a=(funcA(x)==c?1:0);
		//todo ���� 3  for (int a=(funcA(x); a<10; a++){
		if(line.contains("==")&&line.contains("(")&&(line.contains("if")||line.contains("while"))){ //���� 1
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
	 * �ж�һ���Ƿ��б��ʽ
	 * ���ʽ��  ==,&&,||,!,+,-,^,/,<<,>>,~,&,| 
	 * 
	 * �ڽ�һ���˻���simpleJavaLine��ʹ��
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
	 * �ж��Ƿ�Ϊ������express��
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
	 * �����б��ʽ�����˻��ɡ���ͨ�С�����һ�в������2�������
	 * 
	 * ���ʽ��  ==,&&,||,!,+,-,^,/,<<,>>,~,&,| 
	 * ��������ı��ʽ int b=(a==0?b:c);  //todo �˱��ʽ��ʱ������
	 * 
	 * ʹ�ô˷���֮ǰ�����뱣֤line�ǰ������ʽ��
	 * �ڽ�һ���˻���simpleJavaLine��ʹ��
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
	 * ����ǿ������ת�� ���Ե�  int a = (String)(age); �˻�Ϊ int a = (age);
	 * �㷨       ����һ����(xxx)�е�����,�������ŵ���ֹ�������׵�λ��֮����ַ���Ϊ��
     *      ����(xxx)���ݲ�������ֹ����( ,���������������ı�����δ�ҵ�{���������Ϊ���ַ��Ǵ�д����Ϊ����}
     *     
     *     String res = (String[])Service.get2().get(test(B)).sayHello((String[])testC(value),id);
     *     int a = (String)(age+1); //Լ�� �����ͺ����������ַ������Ǵ�д����������ĸ�Ǵ�д��
     *     return (String[])result.toArray(new String[0]);
	 */
	public static String dealTypeChangeInogore(String line){
		if (isSimleJavaLine(line)){
			return line;
		}
		String help = line.trim();
		char[] todeal = help.toCharArray();
		
		//�ҵ�����(
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
	 * �����ӵ�һ��java����ת��Ϊ���м�java���룺
	 * 
	 * ��java����Ķ��壺
	 *  1 ��;����{��β 
	 *  2 һ����ֻ����һ��(),
	 *  ps�� ������������ת���Ķ����()���ݽ�������
	 * 
	 *  demo1 : 
		if ( (testA(value)==testB(value)&&flag)&&testC(value)||(testD(value))&&flag||(4==type) ){
		demo2:
			this.getSV().dowork(new String("123"),id,sv.getSV2(value));
			temp_1 = ${temp_2}.dowork(new String("123"),id,sv.getSV2(value)); 
			   temp_1 = .dowork(temp_1_2,id,temp_1_1); //����Ϊ��ǰ�����ձ��ʽ����Ҫһ�����б��������˳�򣬽����ձ��ʽ���뵽����
			   temp_1_1 = sv.getSV2(value);
			   temp_1_2 = new String("123");
			temp_2 = ${temp_3}.getSV2();
			temp_3 = ${temp_4}.getSV();
			temp_4 = this;
		--������simpleJavaLine Ϊ���򣬿ɽ���һ�������ջ
		demo3:
		  if (this.getSV2().dowork(test(name))==Util.getSV3().test(value)) {
		demo:4
		   int a=(a+4)*(a+5) - sv.test(a); 
		demo5�����߳Ƹ��� (String[])result.toArray(new String[0]);
		demo6��������ʽ�ǲ�����ġ�����System.out.println();  ������̬��ľ�̬��ķ�������,���в����κδ���ֻ��ӡ����,
		
	 * �򵥷��� �� 1 ���ں��������Ǵ������ҵģ������˻��ķ�ʽ�Ǵ��ҵ��� 2 �������()�ڲ��ٳ��ֺ������õģ���()�ڲ��ĺ������ÿɵ�����ȡ������
	 * simpleJavaLine�㷨������ 
	 *   ��ǿ��ת�������Դ�()�е����ݡ� 
	 * ��һ�������� 
	 *  �����һ������ǰ����֮ƥ��ģ����ҵ�֮������ǰƥ�䣬��������л���������ֹ��==,&&,||,!,+,-,^,/,<<,>>,~,&,|,. ����ֹ
	 *  ����
	 *  �����ֹ����. ����ǰ���ң��������)��ֹͣ ����  	������ֹ��,�����ֹ����.��ֹͣ��ֱ�����е�pos����ô(pos,.Pos)={this,Util}Ϊ��ǰ�����������ĸ��� ����.���õ��滻
	 * 	���򣬴�������()���滻 �滻��ͬʱ����Ҫ��λ��Ҳ��¼������Ȼ��Ե�ǰ�н���������װ��
	 *  ���˳�򱣴浽һ����ʱ�����ϡ�Ȼ��Դ˷��ʴ˶��У�����ֵ������ջ�У����յĽ����
	 * �ڶ����� �ݹ鴦���һ���õ��Ļ�����
	 * ���һ��������java����ʱ���˳�򣨴����ң��������ͬ��()����ͬʱ����()���ݣ��������㶼ִ�У�����װ�õ����е�simpleJavaLine
	 * 
	 * 2 ���ݽṹ��� Stack+Queue 
	 * ���ֵĽ��ʹ�ö��������棬Ȼ��Ի��ֵĽ����ջ������ջ�ķ���˳�򼴿�simpleJavaLine��˳��
	 */
	
	public String[] getSimpleJavaLines(String line){
		List<String> result = new ArrayList<String>();
		result.toArray(new String[0]);
		return (String[])result.toArray(new String[0]);
	}
	
	/*  ǰ������ Ϊ ��javaԴ����"��"һ��
	 *  ���ж����ݣ�  һ���������ֻ��һ��()
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
	
	/*  ǰ������ Ϊ ��javaԴ����"��"һ��
	 *  ���ж����ݣ�  һ���������ֻ��һ��()
	 *  
	 * ���Ұ��� ���� �ж�����
	 *  1  ֻ����һ���� ��һ�� (       String res = sv.sayHello(name);
	 *  2 δ����. ����һ��( ��(ǰ�����б�ʾ��    int a = test(b);  int a = (a+1)*3��
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
	 *  ��javaԴ����"��"һ�л�ȡ��������
	 *  ���а���. �� ()  demo:
	 *  1 ֱ�ӵ���
	 *  String res = sv.sayHello(name);
	 *  sv.sayHello(name);
	 *  int a=(sv.funcA(x)==c?1:0);
	 *  int res = sv.sayHello(new Long(new Long(1)));
	 *  HelpUtil.sayHello(name);//��̬����
	 *  
	 *  2 ��ʽ����  �Ƚ������˻���ÿ��ֻ��һ���������á�
	 *  String res = this.getxxxxSV().sayHello(name);
	 *  String res = sv.testA(sv2.testB(value));             
	 *  String res = HelpUtil.testA(sv2.testB(value)).testC(value);  ��������£�ֻ����xx��testC
	 *  
	 *  @param line
	 *  @return FunNode
	 *  
	 *  �㷨������
	 *  1 ������endPos : �����һ��)��ǰƥ��(,���ƥ�䵽������Ϊ(ǰ����Ǻ������ƵĽ�β
	 *  2 ������begPos ��  �� endPos ��ǰƥ���һ��.
	 *  
	 *  ����ǰ׺begPos��ǰƥ�䣬��һ��(,��=��������
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
		
		//funcName����Ҫ������ʽ���ù�ϵ�������ĵĻ�ȡ,���յĺ���ǩ��ΪOutArg package.class.menthod(inArgs)
		//��ʽ�������ã�ֻ�������һ���ĺ������������CRM���벻�����Fluent���ĺ�������
		
		funcName = line.substring(begPos+1,endPos).trim().replace(" ", "");
		System.out.println("funcName todeal \t"+funcName);
		//ֱ�ӵ��� sv.sayHello FactoryUtil.sayHello
		String prefix = funcName.substring(0,funcName.lastIndexOf("."));
		String innerClassFullName = null;
		 if (!prefix.contains("(")){
			 String innerClassName = null;
			 if(prefix.charAt(0)>='A'&&prefix.charAt(0)<='Z'){ //����ĸ��д����Ϊ������
				 innerClassName = prefix;
			 }else {//Сд������Ϣ��Ҫ���������л�ȡ
				 if(prefix.equals("this")){
					 innerClassName = currentNode.getClassName();
				 }else{
					 innerClassName  = currentNode.getVarsDefineByContenxt(prefix);
				 }
				 
			 } 
			 innerClassFullName = currentNode.getImportClassByKey(innerClassName);
		 }else{ 
	     //todo ������ʽ����   this.getxxxxSV().sayHello  getxxxxSV().sayHello ((IHelloSV)Factory.getxxxxSV()).sayHello
			 
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
		inneNode.setFuncInArgsOrgin(inneFuncInArgsOrgin.toString()); //�������id,String
		
		
		if (funcHasReturnValue){
			funcOutArg = line.substring(0, begPos);
			inneNode.setFuncOutArg(currentNode.getVarsDefineByContenxt(funcOutArg));
		}
		System.out.println("inneFuncOutArgsOrgin\t"+inneNode.getFuncOutArg());
		
		return inneNode;
	}
	/*
	 * ���л�ȡ�����Ķ���
	 * 
	 * �����Ƿ�֧����
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
		/*�ж�����1  ���ֶ��š��ҳ��֣����� �ͣ� 
		 * �������Ƶ�endPos: ���һ����������ң�����( ��pos��
		 * �������Ƶ�beginPos : ���һ������ǰ�ң��������=,���ף�����ֹ����һ��(,����ֹ�����������)�������ǰ���ң����������������ҹ�����������(���������״Ρ�����(
		 *               
		 */
		if (line.contains(".")&&line.contains("(")){
			//String res = sv.sayHello(name); & �ӿ�(�ࣩ����
			//String res = this.getxxxxSV().sayHello(name); & �ӿ�(�ࣩ����
			//sv.sayHello(name);
			//int a=(sv.funcA(x)==c?1:0);   //������費����==���߶������к������ã�����У��������⴦��
			if (line.endsWith(";")){
//				inneNode = _getInnerFunctionsByLine1(line,currentNode);
			}
			//if (sv.sayHello(name)==name){   
			//if(4==sv.funcA(a)){ 
			//if (sv.funcA(a)){ 
			else if (line.endsWith("{")){   // && ��֧���� 1
				
			}
			
			
		}
		//�ж�����2 δ����=�����ǳ�����()  ��ô()�ڲ�������һ��������()ǰ������ǿգ���϶���һ����������
		if (!line.contains("=")&&line.contains("(")){
			//test(1);
			
			// if (test(1)){   if (testA(testB(c))){  if(4==funcA(a)){ && ��֧���� 2
		}
		
		//�ж�����3  
		//���� 1 if(4==funcA(a)){ 
		//���� 2  int a=(funcA(x)==c?1:0);
		if(line.contains("==")&&line.contains("(")&&(line.contains("if")||line.contains("while"))){
			
		}
		
		//��֧���� 3 ֱ�ӳ���{  if(true){   if(4==type){
		if (line.contains("{")){
			
		}
		
		//�����ඨ��
		
		
		//
		currentNode.addInnerFunc(inneNode);
		
		System.out.println("###########end getInnerFunctionsByLine");
		return inneNode;
	}
	
	/*  @param funcSrc
	 *  �ȶ�{,},;���л��в���
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
//		��ȡ��{,},;����Ϊ���в���
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
		//��һ��Ϊ��������  ���һ��Ϊ}
		for (int i=1;i<lines.size()-1;i++){
			System.out.println("line \t"+i);
			String line = lines.get(i).trim();
			//��ÿ���˻��� javaԴ����"��"һ��  ���ж�����  һ���������ֻ��һ��()
			String[] simpleJavaLines = getSimpleJavaLines(line);
			for (int j=0;j<simpleJavaLines.length;j++){
				//��һ���� ���������������������
				setFunVarsContext(simpleJavaLines[j],this);
				// �ڶ����� ����õ��ڲ��Ӻ���
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
	 *  ��simpleJavaLine����������ƣ����
	 *  �ٸ���������currentNode�������õ���������������
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
	 *  ������������==��ֻ����һ��()�� ������γ���ǿ������ת�����ɰ������()
	 * 
	 *  �õ�������������
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
		//���2  ������for�ж���ı��� for (int a=1;...) for (String a :Temp[])
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
			//�����д������int[] array �� int array[]
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
