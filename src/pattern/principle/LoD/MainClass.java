package pattern.principle.LoD;

/*
 * �����ط���(Law of Demeter )�ֽ�������֪ʶ
ԭ��Ҳ����˵��һ������Ӧ�����������󾡿�
���ٵ��˽⡣

��������಻�ر˴�ֱ��ͨ�ţ���ô��������
�Ͳ�Ӧ������ֱ�ӵ��໥���á��������һ����
��Ҫ������һ���ĳһ�������Ļ�������ͨ����
����ת��������á�


Ȼ����������֮��Ĺ�ϵ���Գ���Ϊһ������Ķ���������
1���ģʽ
2.�н���ģʽ

 */
public class MainClass {
	public static void main(String[] args) {
		SomeOne zhangsan = new SomeOne();
		zhangsan.setFriend(new Friend());
		zhangsan.setStranger(new StrangerA());
		zhangsan.play();
	}
}
