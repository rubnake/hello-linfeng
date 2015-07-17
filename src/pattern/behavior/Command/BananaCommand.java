package pattern.behavior.Command;

public class BananaCommand extends Command{

	public BananaCommand(Peddler peddler) {
		super(peddler);
	}

	public void sail() {
		this.getPeddler().sailBanana();
	}

}
