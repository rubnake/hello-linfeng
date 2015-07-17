package pattern.principle.ocp;
/*
 *    ���ŷ��ԭ��(Open-Closed Principle)��һ�����ʵ��
Ӧ������չ���ţ��������޸Ĺرա�
   �����һ��ģ��ʱ��Ӧ��ʹ�����ģ������ڲ�����
�ĵ�ǰ���±���չ��Ҳ����˵��Ӧ�������ڲ����޸�Դ
�����������޸����ģ�����Ϊ��
   ��Ƶ�Ŀ�ı������������ĸı������ϵͳ�����
�ȶ����Ӷ�ʹ��ϵͳ���Ժ����׵Ĵ�һ���汾��������
һ���汾��


���仯����ʱ��������
����Ҫ���Ĳ����޸Ĵ��룬���Ǿ����ܵĽ��仯�����
�����и��룬Ȼ�������չ���������ı仯���Գ���
���޸�Ӧ���Ǿ�����ͨ����Ӵ�����ʵ�֣�������ͨ��
�޸Ĵ�����ʵ�֡�

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
