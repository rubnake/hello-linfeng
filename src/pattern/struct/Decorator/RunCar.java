package pattern.struct.Decorator;

public class RunCar implements Car {

	public void run() {
		System.out.println("������");
	}

	public void show() {
		this.run();
	}

}
