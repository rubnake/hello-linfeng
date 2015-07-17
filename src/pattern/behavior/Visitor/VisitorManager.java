package pattern.behavior.Visitor;

public class VisitorManager implements Visitor {

	public void visit(Park park) {
		System.out.println("����Ա������" + park.getName() + "�������");
	}

	public void visit(ParkA parkA) {
		System.out.println("����Ա������԰"+ parkA.getName() +"�����������");
	}

	public void visit(ParkB parkB) {
		System.out.println("����Ա������԰"+ parkB.getName() +"�ֲ��������");
	}

}
