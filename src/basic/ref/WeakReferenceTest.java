package basic.ref;

import java.lang.ref.WeakReference;

import entity.Person;
/*
 * WeakReference�������ã����б���Ķ���ʵ�����Ա�GC���յ���
 * 
 * �����ͨ��������ĳ������������ã����ֲ����Ÿö���GC���գ�ͨ������Debug��
 * �ڴ���ӹ��ߵȳ����С���Ϊ�������һ��Ҫ��Ҫ�۲쵽�����ֲ���Ӱ��ö���������GC���̡�
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