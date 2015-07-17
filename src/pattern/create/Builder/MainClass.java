package pattern.create.Builder;


public class MainClass {

	public static void main(String[] args) {
//		//�ͻ�ֱ���췿��
//		House house = new House();
//		house.setFloor("�ذ�");
//		house.setWall("ǽ");
//		house.setHousetop("�ݶ�");
		
		
		//�ɹ��̶�����
		HouseBuilder builder = new GongyuBuilder();
		//���������
		HouseDirector director = new HouseDirector();
		director.makeHouse(builder);
		
		House house = builder.getHouse();
		System.out.println(house.getFloor());
		System.out.println(house.getWall());
		System.out.println(house.getHousetop());
	}

}
