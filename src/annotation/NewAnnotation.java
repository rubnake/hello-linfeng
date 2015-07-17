package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * 基本类型、String、Class、枚举类型
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NewAnnotation {
	String value();
}
