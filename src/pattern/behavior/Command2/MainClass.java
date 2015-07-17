package pattern.behavior.Command2;

import java.util.ArrayList;
import java.util.Iterator;

interface Command {
	void execute();
}

class Hello implements Command {
	public void execute() {
		System.out.print("Hello ");
	}
}

class World implements Command {
	public void execute() {
		System.out.print("World! ");

	}
}

class IAm implements Command {
	public void execute() {
		System.out.print("I'm the command pattern!");
	}
}

// A Command object that holds commands:
class Macro {
	private ArrayList commands = new ArrayList();

	public void add(Command c) {
		commands.add(c);
	}

	public void run() {
		Iterator it = commands.iterator();
		while (it.hasNext())
			((Command) it.next()).execute();
	}
}

public class MainClass {

	public static void main(String[] args) {
		Macro macro = new Macro();
		macro.add(new Hello());
		macro.add(new World());
		macro.add(new IAm());
		
		macro.run();
	}

}
