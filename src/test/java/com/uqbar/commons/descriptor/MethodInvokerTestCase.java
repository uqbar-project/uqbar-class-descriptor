package com.uqbar.commons.descriptor;

import java.lang.reflect.Method;

import com.uqbar.commons.descriptor.visitors.Message;

/**
 * public void method1(boolean param1) 
 * public Integer method2(int param1) 
 * public Integer method3() 
 * 
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 * 
 */
public class MethodInvokerTestCase extends AbstractClassDescriptorTest {

	private boolean checkMethod3 = false;
	private int methodCounter = 0;

	public void method(Class type, Method method) {
		assertFalse("El method3 no debe ser tratado por el metodo default", method.getName().equals("method3"));
		this.methodCounter++;
	}

	@Message(name = "method3")
	public void methodSpecific(Class clazz, Method method) {
		assertEquals("Se indico especificamente que este metodo es para el method3", "method3", method.getName());
		assertFalse("Se paso mas de una vez al decribir el method3", this.checkMethod3);
		this.checkMethod3 = true;
	}

	@Override
	protected void assertions() {
		assertTrue("Fallo la sobreescritura del method3", this.checkMethod3);
		// el 11 se debe a que tambien tiene en cuenta los metodos de la superclase, pues este visitor es para
		// la interface publica
		assertEquals("No se pasó la cantidad de veces esperadas por el metodo default", 11, this.methodCounter);
	}

}
