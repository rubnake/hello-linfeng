package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class JianpanInput {
	
	
	public void test1() throws Exception{
		System.out.println("Enter a char:");
		char i = (char)System.in.read();
		System.out.println("your char is "+ i);
	}
	
	public void test2() throws Exception{
		System.out.println("Enter a string:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String str = br.readLine();
		System.out.println("your String is "+ str);
	}
	
	public void test3() throws Exception{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a char:");
		String name = sc.nextLine();
		System.out.println("your char is "+ name);
		
		System.out.println("Enter  your age:");
		int age = sc.nextInt();
		System.out.println("your age is "+ age);
	}
	
	public static void main(String[] args) throws Exception {
		JianpanInput t = new JianpanInput();
//		t.test1();
		System.out.println("------------------");
		t.test2();
		System.out.println("------------------");
//		t.test3();
		
		
	}

}
