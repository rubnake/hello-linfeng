package pattern.behavior.TemplateMethod;

public class MakeBus extends MakeCar {

	public void makeBody() {
		System.out.println("bus:��װ����");
	}

	public void makeHead() {
		System.out.println("bus:��װ��ͷ");
	}

	public void makeTail() {
		System.out.println("bus:��װ��β");
	}
}
