package asm.javaassist;

import java.lang.reflect.Method;  
import java.lang.reflect.Modifier;  
  
import javassist.ClassPool;  
import javassist.CtClass;  
import javassist.CtConstructor;  
import javassist.CtField;  
import javassist.CtField.Initializer;  
import javassist.CtMethod;  
import javassist.CtNewMethod;  
  
/* target
     public class JavassistClass{  
        private String name="default";  
        public JavassistClass(){  
            name="me";  
        }
        
        public String getName() {  
            return name;  
        }  
        public void setName(String name) {  
            this.name = name;  
        }  
        public void execute(){  
            System.out.println(name);  
            System.out.println("execute ok");  
        }  
    }  
 */
/*
 * see http://yonglin4605.iteye.com/blog/1396494
 * from http://blog.csdn.net/sadfishsc/article/details/9999169
 */
public class JavassistLearn{  
      
      
    public static void main(String[] args) throws Exception{  
        ClassPool cp=ClassPool.getDefault();  
        CtClass ctClass=cp.makeClass("asm.javaassist.JavassistClass");
        CtClass.debugDump="D:/javaassist";
          
        StringBuffer body=null;  
        //����  1����������  2����������  3��������CtClass  
        CtField ctField=new CtField(cp.get("java.lang.String"), "name", ctClass);  
        ctField.setModifiers(Modifier.PRIVATE);  
        //����name���Ե�get set����  
        ctClass.addMethod(CtNewMethod.setter("setName", ctField));  
        ctClass.addMethod(CtNewMethod.getter("getName", ctField));  
        ctClass.addField(ctField, Initializer.constant("default"));  
          
        //����  1����������   2��������CtClass  
        CtConstructor ctConstructor=new CtConstructor(new CtClass[]{}, ctClass);  
        body=new StringBuffer();  
        body.append("{\n name=\"me\";\n}");  
        ctConstructor.setBody(body.toString());  
        ctClass.addConstructor(ctConstructor);  
          
        //������  1����������  2����������  3�������������  4��������CtClass  
        CtMethod ctMethod=new CtMethod(CtClass.voidType,"execute",new CtClass[]{},ctClass);  
        ctMethod.setModifiers(Modifier.PUBLIC);  
        body=new StringBuffer();  
        body.append("{\n System.out.println(name);");  
        body.append("\n System.out.println(\"execute ok\");");  
        body.append("\n return ;");  
        body.append("\n}");  
        ctMethod.setBody(body.toString());  
        ctClass.addMethod(ctMethod);  
        Class<?> c=ctClass.toClass();  
        Object o=c.newInstance();  
        Method method=o.getClass().getMethod("execute", new Class[]{});  
        //�����ֽ����������execute����  
        method.invoke(o, new Object[]{});  
    }  
  
}  