package pattern.principle.LSP;

/*
 * ���ϴ���ԭ��(Liskov Substitution Principle)��
    һ�����ʵ�����ʹ�õ���һ������Ļ�����
ôһ�������������࣬��������������������
����������Ҳ����˵����������棬�Ѹ���
�滻���������࣬�������Ϊû�б仯��

���嶨���ʱ�򣬾���ʹ�ø��ࡣ
 */
public class MainClass {
	public static void main(String[] args) {
		fly(new Laoying());
	}
	
	public static void fly(Bird bird) {
		bird.fly();
	}
}
