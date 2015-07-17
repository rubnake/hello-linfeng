package pattern.create.Builder;
public class GongyuBuilder implements HouseBuilder{
	House house = new House();
	
	public House getHouse() {
		return house;
	}

	public void makeFloor() {
		house.setFloor("��Ԣ-->�ذ�");
	}

	public void makeHousetop() {
		house.setHousetop("��Ԣ-->����");
	}

	public void makeWall() {
		house.setWall("��Ԣ-->ǽ");
	}
	
}
