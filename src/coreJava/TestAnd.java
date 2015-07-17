package coreJava;

public class TestAnd {
	public static void main(String[] args) {
		int a=1;
		boolean flag=false;
		
		if (flag&&check(a)){
			System.out.println("do it success!");
		}
		
		System.out.println("finish");
	}
	
	public static boolean check(int a){
		System.out.println("!!!!!!!!!!");
		return a>0;
	}
}
