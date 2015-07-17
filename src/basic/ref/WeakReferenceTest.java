package basic.ref;

import java.lang.ref.WeakReference;

import entity.Person;
/*
 * WeakReference是弱引用，其中保存的对象实例可以被GC回收掉。
 * 
 * 这个类通常用于在某处保存对象引用，而又不干扰该对象被GC回收，通常用于Debug、
 * 内存监视工具等程序中。因为这类程序一般要求即要观察到对象，又不能影响该对象正常的GC过程。
 */
public class WeakReferenceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Person a = new Person();
		a.setName("Hello, reference");
		WeakReference<Person> weak = new WeakReference<Person>(a);
		a = null;
		int i = 0;
		while (weak.get() != null) {
			System.out.println(String.format(
					"Get str from object of WeakReference: %s, count: %d",
					weak.get().getName(), ++i));
			if (i % 10 == 0) {
				System.gc();
				System.out.println("System.gc() was invoked!");
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

			}
		}
		System.out.println("object a was cleared by JVM!");
	}

}