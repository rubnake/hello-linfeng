package pattern.behavior.Command;

public class MainClass {
	public static void main(String[] args) {
		Peddler peddler = new Peddler();
//		peddler.sailApple();
//		peddler.sailBanana();
		
		Command appleCommand = new AppleCommand(peddler);
		Command bananaCommand = new BananaCommand(peddler);
//		appleCommand.sail();
//		bananaCommand.sail();
		Waiter waiter = new Waiter();
		
		//�¶���
		waiter.setOrder(appleCommand);
		waiter.setOrder(bananaCommand);
		
		//�Ƴ�����ĳ��
		waiter.removeOrder(appleCommand);
		
		waiter.sail();
	}
}
