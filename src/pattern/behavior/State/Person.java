package pattern.behavior.State;


public class Person {
	private int hour;
	private State state;

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}
	
	public Person() {
		state = new MState();
	}
	
	public void doSomething(){
//		if(hour == 7) {
//			state = new MState();
//			state.doSomething();
//		} else if(hour == 12) {
//			state = new LState();
//			state.doSomething();
//		} else if(hour == 18) {
//			state = new SState();
//			state.doSomething();
//		} else {
//			state = new NoState();
//			state.doSomething();
//		}
		state.doSomething(this);
		//��λ�������Է����Ժ���ִ�С�
		state = new MState();
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
