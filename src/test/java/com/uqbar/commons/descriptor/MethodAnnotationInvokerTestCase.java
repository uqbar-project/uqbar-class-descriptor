package com.uqbar.commons.descriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.uqbar.commons.descriptor.visitors.Message;


/**
 * 	@AnnotationTest1 
 * public void method1(@AnnotationTest1 @AnnotationTest2 boolean param1) 
 *
 *	@AnnotationTest1 @AnnotationTest2
 *	public Integer method2(@AnnotationTest1 @AnnotationTest2 int param1) 
 *
 *	@AnnotationTest2 
 *	public Integer method3() 
 *
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 *
 */
public class MethodAnnotationInvokerTestCase extends AbstractClassDescriptorTest {

	private boolean methodAnnotationMethod2Annotation1;
	private boolean methodAnnotation;
	private int methodAnnotationMethod2Counter = 0;
	private int methodAnnotation2Counter = 0;
	
	
	//metodo default
	public void methodAnnotation(Class type, Method method, Annotation annotation) {
		assertEquals("Solo no se sobreescribio ningun metodo para el method1", "method1", method.getName());
		assertFalse("ya se habia pasado por el metodo methodAnnotation", this.methodAnnotation);
		this.methodAnnotation = true;
		
	}
	
	//sobreescritura por nombre y annotation
	@Message(name ="method2", parameters =  {int.class})
	public void methodAnnotationMethod2Annotation1(Class type, Method method, AnnotationTest1 annotation) {
		assertEquals("Se indico que debe ser para el method method2", "method2", method.getName());
		assertFalse("ya se habia pasado por el metodo methodAnnotationMethod2", this.methodAnnotationMethod2Annotation1);
		this.methodAnnotationMethod2Annotation1 = true;
	}
	
	//sobreescritura por nombre
	@Message(name ="method2", parameters =  {int.class})
	public void methodAnnotationMethod2(Class type, Method method, Annotation annotation) {
		assertEquals("Se indico que debe ser para el method method2", "method2", method.getName());
		this.methodAnnotationMethod2Counter ++;
	}
	
	//sobreescritura por annotation
	public void methodAnnotation2(Class type, Method method, AnnotationTest2 annotation) {
		this.methodAnnotation2Counter  ++;
	}
	
	
	@Override
	protected void assertions() {
		assertTrue("Fallo la sobreescritura para method2 y AnnotationTest1. No se paso por el metodo methodAnnotationMethod2", this.methodAnnotationMethod2Annotation1);
		assertTrue("Fallo la llamada al metodo default, se deberia haber llamado para el method1", this.methodAnnotation);
		assertEquals("Fallo la sobreescritura por nombre para el method 2, metodo methodAnnotationMethod2", 2, this.methodAnnotationMethod2Counter);
		assertEquals("Fallo la sobreescritura por Annotation para la annotation 2, metodo methodAnnotation2", 2, this.methodAnnotation2Counter);
	}

}
