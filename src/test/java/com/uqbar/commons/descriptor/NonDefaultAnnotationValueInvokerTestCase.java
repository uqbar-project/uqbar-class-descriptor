package com.uqbar.commons.descriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

import com.uqbar.commons.descriptor.visitors.NonDefault;

public class NonDefaultAnnotationValueInvokerTestCase extends AbstractClassDescriptorTest {


	private int counterNonDefaultAttribute;
	private boolean checkNonDefaultValue1Attribute2Annotation1 = false;
	private boolean checkNonDefaultValue2Attribute2Annotation1 = false;
	private int counterOtherValues;
	

	@NonDefault
	public void valueNonDefault(Class type, Field field, AnnotationTest1 annotation, String name, Object value) {
		assertEquals("solo el atributo 2 tiene valores de annotation1 no defaults", "attribute2", field.getName());
		this.checkNonDefaultValue1Attribute2Annotation1 |= name.equals("value1") && value.equals("otra cosa");
		this.checkNonDefaultValue2Attribute2Annotation1 |= name.equals("value2") && value.equals(1);		
		this.counterNonDefaultAttribute ++;
	}
	
	public void annotationValue(Class clazz, AnnotatedElement element, Annotation annotation, String name, Object value, boolean isDefault){
		this.counterOtherValues++;
	}
	
	@Override
	protected void assertions() {
		assertEquals(2, this.counterNonDefaultAttribute);
		assertTrue("no se llamo al metodo valueNonDefault para value1=otra cosa", this.checkNonDefaultValue1Attribute2Annotation1);
		assertTrue("no se llamo al metodo valueNonDefault para value2=1", this.checkNonDefaultValue2Attribute2Annotation1);
		assertEquals(19, this.counterOtherValues);
		
	}

}
