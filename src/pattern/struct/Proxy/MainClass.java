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
				System.out.println("����");

				Object result = method.invoke(realSubject, args);
				System.out.println("�ʹ����");
				return result;
			}
		});

		jdkProxySubject.sailBook();
		
//	    ����Ҫ�������ʵ����
//        Subject realSubject = new RealSubject();
//
//        //    ����Ҫ�����ĸ���ʵ���󣬾ͽ��ö��󴫽�ȥ�������ͨ������ʵ�����������䷽����
//        InvocationHandler handler = new DynamicProxy(realSubject);
//        
//        Subject subject = (Subject)Proxy.newProxyInstance(handler.getClass().getClassLoader(), realSubject
//                .getClass().getInterfaces(), handler);
//        
//        System.out.println(subject.getClass().getName());
//        subject.sailBook();
        

	}
}
