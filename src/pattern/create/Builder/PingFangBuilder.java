package pattern.create.Builder;
/*
 * ƽ�����̶�
 */

public class PingFangBuilder implements HouseBuilder {
	House house = new House();
	
	public void makeFloor() {
		house.setFloor("ƽ��-->�ذ�");
	}

	public void makeHousetop() {
		house.setHousetop("ƽ��-->����");
	}

	public void makeWall() {
		house.setWall("ƽ��-->ǽ");
	}

	public House getHouse() {
		return house;
	}

}
