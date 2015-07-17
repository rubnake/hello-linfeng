package coreJava.immutable;

public class Test {
	
	public static void main2(String[] args) {
		// java中不可变类 Integer String ...
		Integer a=100;
		Integer b=100;
		Integer c=1000;
		Integer d=1000;
		System.out.println(a==b);
		System.out.println(c==d);
		
		
		System.out.println(0L == 0);
		System.out.println(((Long)0L).equals(0));
	}
	public static void main(String[] args) {
		Integer a=Integer.valueOf(100);
		Integer b=Integer.valueOf(100);
		Integer c=Integer.valueOf(100);
		Integer d=Integer.valueOf(1000);
		Integer aa=100;
		System.out.println(a==b);
		System.out.println(c==d);
		System.out.println(a==aa);
	}
	

	
}
