package pattern.create.simpleFactory;

public class FruitFactory {
//	/*
//	 * ���Apple���ʵ��
//	 */
//	public static  Fruit getApple() {
//		return new Apple();
//	}
//	
//	/*
//	 * ���Banana��ʵ��
//	 */
//	public static Fruit getBanana() {
//		return new Banana();
//	}
	/*
	 * get������������в�Ʒ����
	 */
	public static Fruit getFruit(String type) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
//		if(type.equalsIgnoreCase("apple")) {
//			return Apple.class.newInstance();
//			
//		} else if(type.equalsIgnoreCase("banana")) {
//			return Banana.class.newInstance();
//		} else {
//			System.out.println("�Ҳ�����Ӧ��ʵ������");
//			return null;
//		}
			Class fruit = Class.forName(type);
			return (Fruit) fruit.newInstance();
		
		
	}
}
