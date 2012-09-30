package com.uqbar.commons.descriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Arrays;

import com.uqbar.commons.descriptor.visitors.Types;

/**
 * @AnnotationTest1 public ClassForDescription() 
 * 
 * @AnnotationTest2
 * @AnnotationTest1 
 * public ClassForDescription(boolean param1) 
 * 
 * @AnnotationTest2 
 * public ClassForDescription(boolean param1, boolean param2) 
 * 
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 */
public class ConstructorAnnotationInvokerTestCase extends AbstractClassDescriptorTest {

	private boolean constructorAnnotationConstructor2Annotation1;
	private boolean constructorAnnotation;
	private int constructorAnnotationConstructor2Counter = 0;
	private int constructorAnnotation2Counter = 0;

	// metodo default
	public void constructorAnnotation(Class type, Constructor constructor, Annotation annotation) {
		assertTrue("S'olo no se sobreescribio ningun metodo para el constructor vacio", Arrays.equals(new Class[]{}, constructor.getParameterTypes()));
		assertFalse("ya se habia pasado por el metodo constructorAnnotation", this.constructorAnnotation);
		this.constructorAnnotation = true;
	}

	// sobreescritura por nombre y annotation
	@Types( { boolean.class })
	public void constructorAnnotationConstructor2Annotation1(Class type, Constructor constructor,
			AnnotationTest1 annotation) {
		assertTrue("Se indico que debe ser para el constructor con boolean", Arrays.equals(
			new Class[] { boolean.class }, constructor.getParameterTypes()));
		assertFalse("ya se habia pasado por el metodo constructorAnnotationConstructor2",
			this.constructorAnnotationConstructor2Annotation1);
		this.constructorAnnotationConstructor2Annotation1 = true;
	}

	// sobreescritura por nombre
	@Types( { boolean.class })
	public void constructorAnnotationConstructor2(Class type, Constructor constructor, Annotation annotation) {
		assertTrue("Se indico que debe ser para el constructor con boolean", Arrays.equals(
			new Class[] { boolean.class }, constructor.getParameterTypes()));
		this.constructorAnnotationConstructor2Counter++;
	}

	// sobreescritura por annotation
	public void constructorAnnotation2(Class type, Constructor constructor, AnnotationTest2 annotation) {
		this.constructorAnnotation2Counter++;
	}

	@Override
	protected void assertions() {
		assertTrue(
			"Fallo la sobreescritura para constructor con boolean y AnnotationTest1. No se paso por el metodo constructorAnnotationConstructor2",
			this.constructorAnnotationConstructor2Annotation1);
		assertTrue("Fallo la llamada al metodo default, se deberia haber llamado para el constructor vacio",
			this.constructorAnnotation);
		assertEquals(
			"Fallo la sobreescritura por nombre para el constructor con boolean, metodo constructorAnnotationConstructor2", 2,
			this.constructorAnnotationConstructor2Counter);
		assertEquals("Fallo la sobreescritura por Annotation para la annotation 2, metodo constructorAnnotation2", 2,
			this.constructorAnnotation2Counter);
	}

}
