package pattern.behavior.State;

public class LState extends State{

	public void doSomething(Person person) {
		if(person.getHour() == 12) {
			System.out.println("���з�");
		} else {
			person.setState(new SState());
			person.doSomething();
		}
	}

}
