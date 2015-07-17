package pattern.struct.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MainClass {
	
	public static Subject getRealSubject(){
		return new RealSubject();
	}

	public static void main(String[] args) {
		Subject proxySubject = new ProxySubject();
		proxySubject.sailBook();

//		// jdk proxy
		final Subject realSubject = getRealSubject();
		Subject jdkProxySubject = (Subject) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				realSubject.getClass().getInterfaces(),
				new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				System.out.println("打折");

				Object result = method.invoke(realSubject, args);
				System.out.println("送代金卷");
				return result;
			}
		});

		jdkProxySubject.sailBook();
		
//	    我们要代理的真实对象
//        Subject realSubject = new RealSubject();
//
//        //    我们要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
//        InvocationHandler handler = new DynamicProxy(realSubject);
//        
//        Subject subject = (Subject)Proxy.newProxyInstance(handler.getClass().getClassLoader(), realSubject
//                .getClass().getInterfaces(), handler);
//        
//        System.out.println(subject.getClass().getName());
//        subject.sailBook();
        

	}
}
