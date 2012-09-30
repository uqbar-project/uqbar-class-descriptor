package com.uqbar.commons.descriptor;

import java.io.Serializable;

import com.uqbar.commons.descriptor.visitors.Type;

/**
 * 
 * public class ClassForDescription implements Cloneable, Serializable
 *
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 */
public class TypeInvokerTestCase extends AbstractClassDescriptorTest {


	private boolean superClass;
	private boolean type;
	private boolean specific;




	public void superClass(Class clazz, Class superClass) {
		assertEquals(Object.class, superClass);
		assertFalse("Se paso mas de una vez por la superclase", this.superClass);
		this.superClass = true;
	}
	
	public void type(Class type, Class superType) {
		assertEquals(Serializable.class, superType);
		assertFalse("Se paso mas de una vez por el type", this.type);
		this.type = true;
	}
	
	
	@Type(Cloneable.class)
	public void typeSpecific(Class type, Class superType) {
		assertEquals(Cloneable.class, superType);
		assertFalse("Se paso mas de una vez por el metodo especifico para Cloneable", this.specific);
		this.specific = true;
	}
	
	
	@Override
	protected void assertions() {
		assertTrue("Fallo la llamada al superClass", this.superClass);
		assertTrue("Fallo la llamada al type", this.type);
		assertTrue("Fallo la llamada al metodo especifico", this.specific);
	}
	

}
