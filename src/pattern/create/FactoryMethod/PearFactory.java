package pattern.create.FactoryMethod;
public class PearFactory implements FruitFactory {

	public Fruit getFruit() {
		return new Pear();
	}
}
