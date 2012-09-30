package com.uqbar.commons.descriptor;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import com.uqbar.commons.descriptor.visitors.Types;

/**
 * 	public ClassForDescription()
 *	public ClassForDescription(boolean param1)
 *	public ClassForDescription(boolean param1, boolean param2)
 *
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 *
 */
public class ConstructorInvokerTestCase extends AbstractClassDescriptorTest {

	private int defaultMethodCounter;
	private boolean overridenMethod;
	
	public void constructor(Class type, Constructor constructor) {
		this.defaultMethodCounter++;
	}
	
	@Types({boolean.class})
	public void specificConstructor(Class type, Constructor constructor) {
		assertTrue("Este metodo fue sobreescrito para el constructor con boolean", Arrays.equals(new Class[]{boolean.class}, constructor.getParameterTypes()));
		assertFalse("Se esperaba que pase solo una vez para el constructor con boolean", this.overridenMethod);
		this.overridenMethod = true;		
	}
	
	@Override
	protected void assertions() {
		assertEquals("Fallo la llamada al metodo default", 2, this.defaultMethodCounter);
		assertTrue("Fallo la llamada al metodo sobrescrito", this.overridenMethod);		
	}

}
