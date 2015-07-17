package pattern.principle.ocp;
/*
 *    开放封闭原则(Open-Closed Principle)：一个软件实体
应当对扩展开放，对内则修改关闭。
   在设计一个模块时，应当使得这个模块可以在不被修
改的前提下被扩展。也就是说，应当可以在不必修改源
代码的情况下修改这个模块的行为。
   设计的目的便在于面对需求的改变而保持系统的相对
稳定，从而使得系统可以很容易的从一个版本升级到另
一个版本。


当变化到来时，我们首
先需要做的不是修改代码，而是尽可能的将变化抽象出
来进行隔离，然后进行扩展。面对需求的变化，对程序
的修改应该是尽可能通过添加代码来实现，而不是通过
修改代码来实现。

 */
public class MainClass {
	public static void main(String[] args) {
		BankWorker bankWorker = new SavingBankWorker();
		bankWorker.operation();
		
		BankWorker bankWorker2 = new DrawingBankWorker();
		bankWorker2.operation();
		
		BankWorker bankWorker3 = new ZhuanZhangBankWorker();
		bankWorker3.operation();
		
		BankWorker bankWorker4 = new JiJinBankWorker();
		bankWorker4.operation();
	}
}
