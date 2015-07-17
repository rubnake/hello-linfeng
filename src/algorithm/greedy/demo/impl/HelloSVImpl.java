package algorithm.greedy.demo.impl;

import algorithm.greedy.demo.interfaces.IHelloSV;

public class HelloSVImpl implements IHelloSV {

	public HelloSVImpl() {
	}

	@Override
	public String sayHello(String name) {
		return "hello\t"+name;
	}

	@Override
	public void doExe(IHelloSV deal) {
		String res = deal.sayHello("linfeng");
		System.out.println(res);
	}

	@Override
	public void sayBye(String name) {
		System.out.println("godbye\t"+name);
		
	}

}
