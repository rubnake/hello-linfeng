package pattern.struct.Flyweight;

public class Teacher extends Person {
	private String number;
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Teacher(String name, int age, String sex,String number) {
		super(name, age, sex);
		this.number = number;
	}
	
	public Teacher() {
		super();
	}
	
}
