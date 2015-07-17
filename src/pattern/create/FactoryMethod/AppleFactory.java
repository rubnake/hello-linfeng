package pattern.create.FactoryMethod;
public class AppleFactory implements FruitFactory {

	public Fruit getFruit() {
		return new Apple();
	}

}
