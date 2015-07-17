package pattern.behavior.ChainOfR;

public abstract class CarHandler {
	protected CarHandler carHandler;
	
	public CarHandler setNextHandler(CarHandler carHandler) {
		this.carHandler = carHandler;
		return this.carHandler;
	}
	
	public abstract void HandlerCar(); 
}
