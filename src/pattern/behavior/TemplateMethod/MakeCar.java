package pattern.behavior.TemplateMethod;
/*
 * ��װ��
 */
public abstract class MakeCar {
	//��װ��ͷ
	public abstract void makeHead();
	
	//��װ����
	public abstract void makeBody();
	
	//��װ��β
	public abstract void makeTail();
	
	public void make() {
		this.makeHead();
		this.makeBody();
		this.makeTail();
	}
}
