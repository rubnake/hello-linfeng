package annotation;

public class AnnotationTest {
	
	@NewAnnotation("just a test")
	public static void main(String[] args) {
		System.out.println("finish!");
	}
	
	
	@NewAnnotation(value="Hello NUMEN.")
    public static void sayHello() {

        // do something

    }
}
