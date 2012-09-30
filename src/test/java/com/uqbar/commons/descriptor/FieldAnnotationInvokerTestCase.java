package com.uqbar.commons.descriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.uqbar.commons.descriptor.visitors.Attribute;

/**
 * 	@AnnotationTest1 
 *	private boolean attribute1;
 *	@AnnotationTest2 @AnnotationTest1
 *	private boolean attribute2;
 *	@AnnotationTest2 
 *	private boolean attribute3;
 *
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 *
 */
public class FieldAnnotationInvokerTestCase extends AbstractClassDescriptorTest {

	private boolean fieldAnnotationAttribute2Annotation1;
	private boolean fieldAnnotation;
	private int fieldAnnotationAttribute2Counter = 0;
	private int fieldAnnotation2Counter = 0;
	
	
	//metodo default
	public void fieldAnnotation(Class type, Field field, Annotation annotation) {
		assertEquals("Solo no se sobreescribio ningun metodo para el attribute1", "attribute1", field.getName());
		assertFalse("ya se habia pasado por el metodo fieldAnnotation", this.fieldAnnotation);
		this.fieldAnnotation = true;
		
	}
	
	//sobreescritura por nombre y annotation
	@Attribute("attribute2")
	public void fieldAnnotationAttribute2Annotation1(Class type, Field field, AnnotationTest1 annotation) {
		assertEquals("Se indico que debe ser para el field attribute2", "attribute2", field.getName());
		assertFalse("ya se habia pasado por el metodo fieldAnnotationAttribute2", this.fieldAnnotationAttribute2Annotation1);
		this.fieldAnnotationAttribute2Annotation1 = true;
	}
	
	//sobreescritura por nombre
	@Attribute("attribute2")
	public void fieldAnnotationAttribute2(Class type, Field field, Annotation annotation) {
		assertEquals("Se indico que debe ser para el field attribute2", "attribute2", field.getName());
		this.fieldAnnotationAttribute2Counter ++;
	}
	
	//sobreescritura por annotation
	public void fieldAnnotation2(Class type, Field field, AnnotationTest2 annotation) {
		this.fieldAnnotation2Counter  ++;
	}
	
	
	@Override
	protected void assertions() {
		assertTrue("Fallo la sobreescritura para attribute2 y AnnotationTest1. No se paso por el metodo fieldAnnotationAttribute2", this.fieldAnnotationAttribute2Annotation1);
		assertTrue("Fallo la llamada al metodo default, se deberia haber llamado para el attribute1", this.fieldAnnotation);
		assertEquals("Fallo la sobreescritura por nombre para el attribute 2, metodo fieldAnnotationAttribute2", 2, this.fieldAnnotationAttribute2Counter);
		assertEquals("Fallo la sobreescritura por Annotation para la annotation 2, metodo fieldAnnotation2", 2, this.fieldAnnotation2Counter);
	}

}
