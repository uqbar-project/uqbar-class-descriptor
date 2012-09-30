package com.uqbar.commons.descriptor;

import java.lang.annotation.Annotation;

/**
 * @AnnotationTest1
 * @AnnotationTest2 public class ClassForDescription
 * 
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 * 
 */
public class ClassAnnotationInvokerTestCase extends AbstractClassDescriptorTest {

	private boolean annotation1;
	private boolean annotation2;

	public void classAnnotation(Class clazz, Annotation annotation) {
		assertEquals("solo la annotation1 no se le sobreescribio ningun metodo", AnnotationTest1.class, annotation
			.annotationType());
		assertFalse("Se paso mas de una vez por el metodo default", this.annotation1);
		this.annotation1 = true;
	}

	public void classAnnotation(Class clazz, AnnotationTest2 annotation) {
		assertFalse("Se paso mas de una vez por el metodo default", this.annotation2);
		this.annotation2 = true;
	}

	@Override
	protected void assertions() {
		assertTrue("fallo la llamada default, se debia llamar para la AnnotationTest1", this.annotation1);
		assertTrue("fallo la llamada al metodo sobreescrito, se debia llamar para la AnnotationTest2", this.annotation2);

	}

}
