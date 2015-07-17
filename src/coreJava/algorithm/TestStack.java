package coreJava.algorithm;

import java.util.Iterator;
import java.util.Stack;

public class TestStack {
	
	public static void main(String[] args) {
		String origin = "ABCDEFG";
		Stack stack = new Stack();
		for (int i=0;i<origin.length();i++){
			stack.push(origin.charAt(i));
		}
		Iterator it = stack.iterator();
		while (it.hasNext()){
			System.out.println((Character)it.next());
		}
		Character one = null;
		while (!stack.isEmpty()&&(one=(Character)stack.pop())!=null){
			System.out.print(one);
		}
		System.out.println("----------------------------------");
		char[] nstr = origin.toCharArray();
		print(nstr);
		reverse(nstr);
		print(nstr);
		System.out.println("----------------------------------");
		StringBuilder sb = new StringBuilder(origin);
		sb.reverse();
		System.out.println(sb.toString());
	}
	
	public static void reverse(char[] aStr){
		int n = aStr.length-1;
		for (int i=0;i<(n>>1);i++){
			char temp  = aStr[i];
			aStr[i] = aStr[n-i];
			aStr[n-i] = temp;
		}
	}
	public static void print(char[] aStr){
		for (int i=0;i<aStr.length;i++){
			System.out.print(aStr[i]);
		}
		System.out.println();
	}
}
