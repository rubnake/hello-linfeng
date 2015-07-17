package pattern.behavior.Memento;

public class MainClass {
	public static void main(String[] args) {
		Person per = new Person("lifengxing","��",24);
		
//		Memento memento = per.createMemento();
		Caretaker caretaker = new Caretaker();
		caretaker.setMemento(per.createMemento());
		
		per.display();
		
		per.setName("beifeng");
		per.setSex("Ů");
		per.setAge(1);
		
		per.display();
		
		per.setMemento(caretaker.getMemento());
		per.display();
		
	}
}
