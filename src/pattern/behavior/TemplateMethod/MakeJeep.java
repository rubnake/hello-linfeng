package pattern.behavior.TemplateMethod;

public class MakeJeep extends MakeCar {

	public void makeBody() {
		System.out.println("jeep:��װ����");
	}

	public void makeHead() {
		System.out.println("jeep:��װ��ͷ");
	}

	public void makeTail() {
		System.out.println("jeep:��װ��β");
	}

}
