package pattern.behavior.Strategy;

public class MainClass {
	public static void main(String[] args) {
		double num = 200;
		Context context = new Context(new StrategyB());
		double newNum = context.cost(num);
		System.out.println("ʵ�ʸ���" + newNum + "Ԫ");
	}
}
