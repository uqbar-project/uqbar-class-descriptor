package com.uqbar.commons.descriptor;

import java.lang.reflect.Field;

import com.uqbar.commons.descriptor.visitors.Attribute;

/**
 * Declaracion en ClassDescpriptor: private boolean attribute1; private boolean attribute2; private boolean
 * attribute3;
 * 
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 * 
 */
public class FieldInvokerTestCase extends AbstractClassDescriptorTest {

	private boolean checkAttribute1 = false;
	private int fieldCounter = 0;

	public void field(Class clazz, Field field) {
		assertFalse("El attribute1 no debe ser tratado por el metodo default", field.getName().equals("attribute1"));
		this.fieldCounter++;
	}

	@Attribute("attribute1")
	public void fieldAttribute1(Class clazz, Field field) {
		assertEquals("Se indico especificamente que este metodo es para el attribute1", "attribute1", field.getName());
		assertFalse("Se paso mas de una vez al decrbiri el attribute1", this.checkAttribute1);
		this.checkAttribute1 = true;
	}

	@Override
	protected void assertions() {
		assertTrue("Nunca se paso por el metodo fieldAttribute1", this.checkAttribute1);
		assertEquals("No se pasó la cantidad de veces esperadas por el metodo field", 2, this.fieldCounter);
	}
}
