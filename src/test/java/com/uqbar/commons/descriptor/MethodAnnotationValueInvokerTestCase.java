package com.uqbar.commons.descriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.uqbar.commons.descriptor.visitors.Message;

public class MethodAnnotationValueInvokerTestCase extends AbstractClassDescriptorTest {

	private boolean checkAnnotation1value1;
	private boolean checkAnnotation1value2;
	private boolean checkAnnotation2value1;
	private int counter;

	@Message(name = "method2", parameters = {int.class})
	public void annotationValue(Class clazz, Method element, Annotation annotation, String name, Object value, boolean isDefault) throws SecurityException, NoSuchMethodException {
		assertEquals(element, ClassForDescription.class.getMethod("method2", int.class));
		this.checkAnnotation1value1 |= name.equals("value1") && value.equals("default") && isDefault && annotation.annotationType().equals(AnnotationTest1.class);
		this.checkAnnotation1value2 |= name.equals("value2") && value.equals(2) && !isDefault && annotation.annotationType().equals(AnnotationTest1.class);
		this.checkAnnotation2value1 |= name.equals("value1") && value.equals("default") && isDefault && annotation.annotationType().equals(AnnotationTest2.class);
		this.counter++;
	}

	
	
	@Override
	protected void assertions() {
		assertEquals("se esperaban tres valores de annotations para el method2", 3, this.counter);
		assertTrue("fallo la validacion del valor1 annotation1",this.checkAnnotation1value1);
		assertTrue("fallo la validacion del valor2 annotation1",this.checkAnnotation1value2);
		assertTrue("fallo la validacion del valor1 annotation2",this.checkAnnotation2value1);
	}

}
