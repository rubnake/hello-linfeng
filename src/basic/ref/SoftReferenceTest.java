package basic.ref;

import java.lang.ref.SoftReference;

import entity.Person;
/*
 * 即对象的软引用。如果一个对象具有软引用，内存空间足够，垃 圾回收器就不会回收它；
 * 如果内存空间不足了，就会回收这些对象的内存。只要垃圾回收器没有回收它，该对象就可以被程序使用。
 * 
 * 软引用可用来实现内存敏感的高 速缓存。使用软引用能防止内存泄露，增强程序的健壮性。   
 * SoftReference的特点是它的一个实例保存对一个Java对象的软引用， 该软引用的存在不妨碍垃圾收集线程对该Java对象的回收。
 * 也就是说，一旦SoftReference保存了对一个Java对象的软引用后，在垃圾线程对 这个Java对象回收前，
 * SoftReference类所提供的get()方法返回Java对象的强引用。另外，一旦垃圾线程回收该Java对象之 后，get()方法将返回null
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
