package pattern.create.FactoryMethod;
public class BananaFactory implements FruitFactory {

	public Fruit getFruit() {
		return new Banana();
	}

}
