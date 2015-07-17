package basic.ref;

import java.lang.ref.SoftReference;

import entity.Person;
/*
 * ������������á����һ��������������ã��ڴ�ռ��㹻���� ���������Ͳ����������
 * ����ڴ�ռ䲻���ˣ��ͻ������Щ������ڴ档ֻҪ����������û�л��������ö���Ϳ��Ա�����ʹ�á�
 * 
 * �����ÿ�����ʵ���ڴ����еĸ� �ٻ��档ʹ���������ܷ�ֹ�ڴ�й¶����ǿ����Ľ�׳�ԡ�   
 * SoftReference���ص�������һ��ʵ�������һ��Java����������ã� �������õĴ��ڲ����������ռ��̶߳Ը�Java����Ļ��ա�
 * Ҳ����˵��һ��SoftReference�����˶�һ��Java����������ú��������̶߳� ���Java�������ǰ��
 * SoftReference�����ṩ��get()��������Java�����ǿ���á����⣬һ�������̻߳��ո�Java����֮ ��get()����������null
 */
public class SoftReferenceTest {
	 /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        Person a = new Person();  
        a.setName("Hello, reference");  
        SoftReference<Person> sr = new SoftReference<Person>(a);  
        a = null; 
        int i = 0;  
        while (sr.get() != null) {  
            System.out.println(String.format("Get str from object of SoftReference: %s, count: %d", sr.get().getName(), ++i));  
            if (i % 10 == 0) {  
                System.gc();  
                System.out.println("System.gc() was invoked!");  
            }  
            try {  
                Thread.sleep(500);  
            } catch (InterruptedException e) {  
  
            }  
        }  
        System.out.println("object Person was cleared by JVM!");  
    }  
}
