package pattern.struct.Proxy;
public class ProxySubject implements Subject{
	private RealSubject realSubject;

	public void sailBook() {
		dazhe();
		if(realSubject == null) {
			realSubject = new RealSubject();
		}
		realSubject.sailBook();
		give();
	}
	
	public void dazhe() {
		System.out.println("����");
	}
	
	public void give() {
		System.out.println("���ʹ���ȯ");
	}
}
