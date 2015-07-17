package annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationIntro {
	public static void main1(String[] args) throws Exception {
		Method[] methods;
		try {
			Class obj = Class.forName("annotation.AnnotationTest");
			methods = obj.getDeclaredMethods();
		
			System.out.println("1111111 size:"+methods.length);
			Annotation[] annotations;
	
			for (Method method : methods) {
				annotations = method.getAnnotations();
				for (Annotation annotation : annotations) {
					System.out.println(method.getName() + " : "
					+ annotation.annotationType().getName());
				}
			}
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, InstantiationException, ClassNotFoundException {
//		AnnotationTest obj = new AnnotationTest();
		Object obj = Class.forName("annotation.AnnotationTest").newInstance();
		Method method = obj.getClass().getMethod("sayHello");
//		method.invoke(obj);
		NewAnnotation view = method.getAnnotation(NewAnnotation.class);
		System.out.println(view.value());
	
	}
}
