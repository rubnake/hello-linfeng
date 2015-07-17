package pattern.behavior.ChainOfR;

public class MainClass {
	public static void main(String[] args) {
		CarHandler headH = new CarHeadHandler();
		CarHandler bodyH = new CarBodyHandler();
		CarHandler tailH = new CarTailHandler();

		//��װ˳��Ԥ���趨��,˳���ǳ�ͷ-->����-->��β
		headH.setNextHandler(bodyH);
		bodyH.setNextHandler(tailH);
		
		//����ְ��������ͷ����ɲ���
		headH.HandlerCar();
		
		System.out.println("---------------");
		
		//˳��ı䣬ϣ���ǳ���-->��ͷ--��β
		bodyH.setNextHandler(headH);
		headH.setNextHandler(tailH);
		bodyH.HandlerCar();
		
		System.out.println("---------------");
		//������
		bodyH.setNextHandler(headH).setNextHandler(tailH);
		bodyH.HandlerCar();
		
		
	}
}
