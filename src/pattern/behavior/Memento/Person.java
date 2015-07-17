package pattern.behavior.Memento;

public class Person {
	//����
	private String name;
	//�Ա�
	private String sex;
	//����
	private int age;
	
	public Person() {
		
	}
	
	public Person(String name, String sex, int age) {
		this.name = name;
		this.sex = sex;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public void display() {
		System.out.println("name:" + name + ",sex:" + sex + ",age:" + age);
	}
	
	//����һ������
	public Memento createMemento() {
		return new Memento(name,sex,age);
	}
	
	//�ָ����ݣ���ԭ
	public void setMemento(Memento memento) {
		this.name = memento.getName();
		this.sex = memento.getSex();
		this.age =  memento.getAge();
	}
	
}
