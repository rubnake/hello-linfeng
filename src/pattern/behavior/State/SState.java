package pattern.behavior.State;


public class SState extends State {

	public void doSomething(Person person) {
		if(person.getHour() == 18) {
			System.out.println("≥‘ÕÌ∑π");
		} else {
			person.setState(new NoState());
			person.doSomething();
		}
	}

}
