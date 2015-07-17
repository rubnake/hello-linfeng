package pattern.behavior.Command;

public class AppleCommand extends Command {

	public AppleCommand(Peddler peddler) {
		super(peddler);
	}

	public void sail() {
		this.getPeddler().sailApple();
	}
	
}
