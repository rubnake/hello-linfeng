package pattern.behavior.Visitor;
/*
 * ��๤A,����parkA���������
 */
public class VisitorA implements Visitor {

	public void visit(Park park) {

	}

	public void visit(ParkA parkA) {
		System.out.println("��๤A:��ɹ�԰" + parkA.getName()+ "������");
	}

	public void visit(ParkB parkB) {

	}

}
