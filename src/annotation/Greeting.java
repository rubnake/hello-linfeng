package annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.CONSTRUCTOR })
public @interface Greeting {
	
	public enum FontColor {RED, GREEN, BLUE};
	 
    String name();
 
    String content();
 
    FontColor fontColor() default FontColor.BLUE;

}
