package pattern.principle.ocp;

/*
 * 负责存款业务的业务员
 */

public class SavingBankWorker implements BankWorker {

	public void operation() {
		System.out.println("进行存款操作");
	}
	
}
