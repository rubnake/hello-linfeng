package pattern.principle.DIP;

/*
 * ������ת(Dependence Inversion Principle )��
    1.����Ӧ��������ϸ�ڣ�ϸ��Ӧ�������ڳ�
��
    2.�߲�ģ�鲻�����ײ�ģ�飬���߶���������
    
    1.��������ģʽ
2.ģ�巽��ģʽ
3.������ģʽ 

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
