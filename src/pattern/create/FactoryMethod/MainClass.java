package pattern.create.FactoryMethod;


public class MainClass {
	public static void main(String[] args) {
		//���AppleFactory
		FruitFactory ff = new AppleFactory();
		//ͨ��AppleFactory�����Appleʵ������
		Fruit apple = ff.getFruit();
		apple.get();
		
		//���BananaFactory
		FruitFactory ff2 = new BananaFactory();
		Fruit banana = ff2.getFruit();
		banana.get();
		
		//���PearFactory
		FruitFactory ff3 = new PearFactory();
		Fruit pear = ff3.getFruit();
		pear.get();
	}
}
