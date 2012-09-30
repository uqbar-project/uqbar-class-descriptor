package com.uqbar.commons.descriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.uqbar.commons.descriptor.visitors.Message;


/**
 *
 *	public void method1(@AnnotationTest1 @AnnotationTest2 boolean param1, @AnnotationTest2 Integer param2);
 *	public Integer method2(@AnnotationTest1 @AnnotationTest2 @AnnotationTest3 int param1);
 *	public Integer method3()
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 *
 */
public class MethodParameterAnnotationInvokerTestCase extends AbstractClassDescriptorTest {

	private int parameterAnnotationCounter = 0;
	private int method2Counter;
	private boolean annotationTest3;
	private boolean annotationTest2;
	
	//metodo por default
	public void parameterAnnotation(Class type, Method method, int index, Annotation annotation) {
		assertEquals("El unico metodo que no fue sobreescrito es el method1", method.getName(), "method1");
		this.parameterAnnotationCounter++;
	}

	//metodo sobrescrito para una annotation
	public void annotationTest3(Class type, Method method, int index, AnnotationTest3 annotation) {
		assertEquals("solo el method2 tiene una annotation de este tipo", "method2", method.getName());
		assertEquals("solo el primer parametro tiene esta annotation", 0, index);
		assertFalse("ya se habia procesado una AnnotationTest3", this.annotationTest3);
		this.annotationTest3 = true;
	}
	
	//metodo sobrescrito para un method
	@Message(name = "method2", parameters={int.class})
	public void method2(Class type, Method method, int index, Annotation annotation) {
		assertEquals("solo el method2 tiene habilitado este metodo", "method2", method.getName());		
		this.method2Counter++;
	}
	
	//metodo sobrescrito para un method y una annotation
	@Message(name = "method2", parameters={int.class})
	public void method2Annotation2(Class type, Method method, int index, AnnotationTest2 annotation) {
		assertEquals("solo el method2 tiene habilitado este metodo", "method2", method.getName());		
		assertFalse("ya se habia procesado una AnnotationTest2", this.annotationTest2);
		this.annotationTest2 = true;
	}
	
	
	
	@Override
	protected void assertions() {
		assertEquals("Fallo la invocacion para el metodo default", 3, this.parameterAnnotationCounter);
		assertTrue("Fallo la sobreescritura por Annotation, se debia invocar al metodo annotationTest3", this.annotationTest3);
		assertEquals("Fallo la sobreescritura por Method, se debia pasar 3 veces por el method2", 3, this.method2Counter);
		assertTrue("Fallo la sobreescritura por Method y Annotation, se debia invocar al metodo method2Annotation2", this.annotationTest2);	
	}
	
	
	
}
