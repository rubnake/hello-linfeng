package pattern.principle.ocp;
/*
 * 负责转账业务的业务员
 */
public class ZhuanZhangBankWorker implements BankWorker {

	public void operation() {
		System.out.println("进行转账操作");
	}

}
