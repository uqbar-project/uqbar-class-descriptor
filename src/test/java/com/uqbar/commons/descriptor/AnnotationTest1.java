package com.uqbar.commons.descriptor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationTest1 {
	
	public String value1() default "default";
	public int value2() default 0;
	
}
