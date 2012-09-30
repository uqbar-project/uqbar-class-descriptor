package com.uqbar.commons.descriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Arrays;

import com.uqbar.commons.descriptor.visitors.Types;

/**
 * 
 *	public ClassForDescription()
 * 
 *	public ClassForDescription(@AnnotationTest1 boolean param1) 
 *	public ClassForDescription(@AnnotationTest2 boolean param1, @AnnotationTest2 boolean param2) 
 * 
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 */
public class ConstructorParameterAnnotationInvokerTestCase extends AbstractClassDescriptorTest{

	private boolean defaultMethod;
	private int annotation2Counter;
	private int constructor2Counter;
	private int annotation2constructor2Counter;
	
	//metodo default
	public void parameterAnnotation(Class type, Constructor constructor, int index, Annotation annotation) {
		assertEquals("Solo el constructor con la AnnotationTest1 no fue sobrescrito ", AnnotationTest1.class, annotation.annotationType());
		assertFalse("paso mas de una vez por el metodo default", this.defaultMethod);
		this.defaultMethod = true;
	}
	
	//sobrescribir por constructor
	@Types({boolean.class, boolean.class})
	public void parameterAnnotationByConstructor(Class type, Constructor constructor, int index, Annotation annotation) {
		assertTrue("el constructor con dos booleans fue sobrescrito en este metododo",  Arrays.equals(new Class[]{boolean.class, boolean.class}, constructor.getParameterTypes()));
		this.constructor2Counter ++;
	}
		
	//sobrescribir por Annotation
	public void parameterAnnotationByAnnotation(Class type, Constructor constructor, int index, AnnotationTest2 annotation) {
		assertTrue("el constructor con dos booleans fue sobrescrito en este metododo",  Arrays.equals(new Class[]{boolean.class, boolean.class}, constructor.getParameterTypes()));
		this.annotation2Counter ++;
	}
	
	//sobrescribir por constructor y annotation
	@Types({boolean.class, boolean.class})
	public void parameterAnnotationByConstructorAndAnnotation(Class type, Constructor constructor, int index, AnnotationTest2 annotation) {
		assertTrue("el constructor con dos booleans fue sobrescrito en este metododo",  Arrays.equals(new Class[]{boolean.class, boolean.class}, constructor.getParameterTypes()));
		this.annotation2constructor2Counter ++;
	}
	
	
	@Override
	protected void assertions() {
		assertTrue("fallo la llamada al metodo default para la annotation AnnotationTest1", this.defaultMethod);
		assertEquals("fallo el metodo especifico por annotation", 2, this.annotation2Counter);
		assertEquals("fallo el metodo especifico por annotation y constructor", 2, this.annotation2constructor2Counter);
		assertEquals("fallo el metodo especifico por constructor", 2, this.constructor2Counter);
	
	} 

}
