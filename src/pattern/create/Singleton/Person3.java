package pattern.create.Singleton;
public class Person3 {
	private String name;
	private static Person3 person;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//���캯��˽�л�
	private Person3() {
	}
	
	//�ṩһ��ȫ�ֵľ�̬������ʹ��ͬ������
	public static synchronized Person3 getPerson() {
		if(person == null) {
			person = new Person3();
		}
		return person;
	}
}
