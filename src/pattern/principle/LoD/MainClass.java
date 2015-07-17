package pattern.principle.LoD;

/*
 * 迪米特法则(Law of Demeter )又叫做最少知识
原则，也就是说，一个对象应当对其他对象尽可
能少的了解。

如果两个类不必彼此直接通信，那么这两个类
就不应当发生直接的相互作用。如果其中一个类
需要调用另一类的某一个方法的话，可以通过第
三者转发这个调用。


然后对象与对象之间的关系可以抽象为一个额外的对象来处理
1外观模式
2.中介者模式

 */
public class MainClass {
	public static void main(String[] args) {
		SomeOne zhangsan = new SomeOne();
		zhangsan.setFriend(new Friend());
		zhangsan.setStranger(new StrangerA());
		zhangsan.play();
	}
}
