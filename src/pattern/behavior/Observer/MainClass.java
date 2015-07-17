package pattern.behavior.Observer;
/*
 *  It is often used for the specific case of changes based 
 *  on other object’s change of state, 
 *  but is also the basis of event management. 
 *  Anytime you want to decouple the source of the call 
 *  from the called code in a completely dynamic way.
 *  
 *  The observer pattern solves a fairly common problem: 
 *  What if a group of objects needs to update themselves when some object changes state
 *  
 *  
 */
public class MainClass {
	public static void main(String[] args) {
		BlogUser user = new BlogUser();
		user.addObserver(new MyObServer());
		user.publishBlog("哈哈，博客上线了", "大家多来访问");
	}
}
