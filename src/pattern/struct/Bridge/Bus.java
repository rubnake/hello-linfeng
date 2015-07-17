package pattern.struct.Bridge;

public class Bus extends Car{

	public Bus(Engine engine) {
		super(engine);
	}

	public void installEngine() {
		System.out.print("Bus£º");
		this.getEngine().installEngine();
	}
}
