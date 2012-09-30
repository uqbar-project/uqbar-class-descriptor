package com.uqbar.commons.descriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import com.uqbar.commons.descriptor.invokers.AnnotationAttribute;

public class SpecificAnnotationValueInvokerTestCase extends AbstractClassDescriptorTest {

	private int counter;


	@AnnotationAttribute(name = "value1", type=String.class)
	public void specificAnnotationValue(Class clazz, AnnotatedElement element, Annotation annotation, String name, Object value, boolean isDefault) {
		assertEquals("value1", name);
		this.counter++;
	}

	@Override
	protected void assertions() {
		assertEquals("Se esperaba 14 entre valores de las annotationTest1 y annotationTest2", 14 ,this.counter);
	}

}
