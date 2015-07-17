package pattern.behavior.Strategy;


public class StrategyB implements Strategy {

	public double cost(double num) {
		if(num >= 200) {
			return num - 50;
		}
		return num;
	}

}
