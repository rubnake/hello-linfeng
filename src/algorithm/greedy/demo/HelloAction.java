package algorithm.greedy.demo;

import algorithm.greedy.JavascHelp;
import algorithm.greedy.demo.factory.ServiceManager;
import algorithm.greedy.demo.interfaces.IHelloSV;

public class HelloAction {
	int f;

	public HelloAction() {
		// TODO Auto-generated constructor stub
	}

	public IHelloSV getSV() {
		return (IHelloSV) ServiceManager.getService("IHelloSV");
	}

	public int work(String id, String name) {
		IHelloSV sv = getSV();
		String res = getSV().sayHello(name);
		System.out.println(res);

		// test
		sv.doExe(sv);
		if (isTrue()) {// test2222
			test(1);
		}
		sv.sayBye(name);
		/*
		 * dsdsadsa
		 */
		JavascHelp.TestClassGet(null);

		return 1;
	}

	public void checkAndSetF(int f) {
		if (f >= 0) {
			this.f = f;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public boolean isTrue(){
		return true;
	}

	private void test(int a) {
		System.out.println(a);
	}

}
