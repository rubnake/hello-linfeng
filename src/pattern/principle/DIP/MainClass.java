package pattern.principle.DIP;

/*
 * 依赖倒转(Dependence Inversion Principle )：
    1.抽象不应该依赖于细节，细节应该依赖于抽
象。
    2.高层模块不依赖底层模块，两者都依赖抽象
    
    1.工厂方法模式
2.模板方法模式
3.迭代子模式 

 */
public class MainClass {
	public static void main(String[] args) {
		Computer computer = new Computer();
		computer.setMainBoard(new HuaSuoMainBoard());
		computer.setMemory(new JinShiDunMemory());
		computer.setHarddisk(new XiJieHardDisk());
		
		computer.display();
		
		System.out.println("-------------");
		
		computer.setMainBoard(new WeiXingMainBoard());
		computer.display();
	}
}
