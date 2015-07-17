package pattern.create.simpleFactory;

public class MainClass {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
//		//ʵ����һ��Apple
//		Apple apple = new Apple();
//		//ʵ����һ��Banana
//		Banana banana = new Banana();
//		
//		apple.get();
//		banana.get();
		
//		//ʵ����һ��Apple,�õ��˶�̬
//		Fruit apple = new Apple();
//		Fruit banana = new Banana();
//		apple.get();
//		banana.get();
		
//		//ʵ����һ��Apple
//		Fruit apple = FruitFactory.getApple();
//		Fruit banana = FruitFactory.getBanana();
//		apple.get();
//		banana.get();
		
		Fruit apple = FruitFactory.getFruit("pattern.create.model.Apple");
		Fruit banana = FruitFactory.getFruit("pattern.create.model.Banana");
		apple.get();
		banana.get();
		
	}
}
