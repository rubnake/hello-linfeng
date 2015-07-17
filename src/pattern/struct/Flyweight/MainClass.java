package pattern.struct.Flyweight;

/*
 * 顶一个全局（局部）的一个pools池。可结合单例一起使用
 */
public class MainClass {
	public static void main(String[] args) {
		TeacherFactory factory = new TeacherFactory();
		Teacher teacher1 = factory.getTeacher("0102034");
		Teacher teacher2 = factory.getTeacher("0102035");
		Teacher teacher3 = factory.getTeacher("0102034");
		Teacher teacher4 = factory.getTeacher("0102037");
		
		System.out.println(teacher1.getNumber());
		System.out.println(teacher2.getNumber());
		System.out.println(teacher3.getNumber());
		System.out.println(teacher4.getNumber());
		
		if(teacher1 == teacher3) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
}
