package pattern.create.Builder;

public class HouseDirector {	
	
	public void makeHouse(HouseBuilder builder) {
		builder.makeFloor();
		builder.makeWall();
		builder.makeHousetop();
	}
	
}
