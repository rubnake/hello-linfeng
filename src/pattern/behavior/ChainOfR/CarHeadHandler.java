package pattern.behavior.ChainOfR;

public class CarHeadHandler extends CarHandler{

	public void HandlerCar() {
		System.out.println("��װ��ͷ");
		if(this.carHandler != null) {
			this.carHandler.HandlerCar();
		}
	}

}
