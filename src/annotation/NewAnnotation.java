package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * �������͡�String��Class��ö������
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NewAnnotation {
	String value();
}
