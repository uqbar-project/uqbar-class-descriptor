package com.uqbar.commons.descriptor;

import java.io.Serializable;

/**
 * Clase para testear el ClassDescriptor
 * 
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 * 
 */
@SuppressWarnings("serial")
@AnnotationTest1
@AnnotationTest2
public class ClassForDescription implements Cloneable, Serializable {

	@AnnotationTest1
	private boolean attribute1;
	@AnnotationTest2(value1 = "yeah")
	@AnnotationTest1(value1 = "otra cosa",  value2 = 1)
	private boolean attribute2;
	@AnnotationTest2
	private boolean attribute3;

	@AnnotationTest1
	public ClassForDescription() {
	}

	@AnnotationTest2
	@AnnotationTest1
	public ClassForDescription(@AnnotationTest1
	boolean param1) {
	}

	@AnnotationTest2
	public ClassForDescription(@AnnotationTest2
	boolean param1, @AnnotationTest2
	boolean param2) {
	}

	@AnnotationTest1
	public void method1(@AnnotationTest1
	@AnnotationTest2
	boolean param1, @AnnotationTest2
	Integer param2) {

	}

	@AnnotationTest1(value2 = 2)
	@AnnotationTest2
	public Integer method2(@AnnotationTest1
	@AnnotationTest2
	@AnnotationTest3
	int param1) {
		return new Integer(param1);
	}

	@AnnotationTest2
	public Integer method3() {
		return this.method4(0);
	}

	// Esto no se tiene en cuenta para ClassVisitorTest porque es un visitor que solo trabaja contra la
	// interfaz publica
	private Integer method4(@AnnotationTest1
	@AnnotationTest2
	int param1) {
		// me molestaban los warnings
		if (this.attribute1 && this.attribute2 && this.attribute3) {
			return new Integer(param1);
		}
		return 0;
	}

}
