package pattern.principle.LSP;

/*
 * 里氏代换原则(Liskov Substitution Principle)：
    一个软件实体如果使用的是一个父类的话，那
么一定适用于其子类，而且它察觉不出父类和子
类对象的区别。也就是说，在软件里面，把父类
替换成它的子类，程序的行为没有变化。

定义定义的时候，尽量使用父类。
 */
public class MainClass {
	public static void main(String[] args) {
		fly(new Laoying());
	}
	
	public static void fly(Bird bird) {
		bird.fly();
	}
}
