package pattern.behavior.Visitor;
/*
 * ��๤B������԰B���ֵ�����
 */
public class VisitorB implements Visitor {

	public void visit(Park park) {

	}

	public void visit(ParkA parkA) {

	}

	public void visit(ParkB parkB) {
		System.out.println("��๤B:��ɹ�԰" + parkB.getName()+"������");
	}

}
