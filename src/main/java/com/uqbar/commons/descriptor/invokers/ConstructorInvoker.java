package com.uqbar.commons.descriptor.invokers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import com.uqbar.commons.descriptor.visitors.Types;

/**
 * Procesa un Constructor
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leonardo Gassman</a>
 *
 */
public class ConstructorInvoker extends AbstractInvoker {

	private Constructor constructor;

	/**
	 * @param type
	 * @param visitor
	 * @param constructor
	 * @see AbstractInvoker#AbstractInvoker(Class, Object, String, Class[])
	 */
	public ConstructorInvoker(Class type, Object visitor, Constructor constructor) {
		super(type, visitor, "constructor", new Class[]{Class.class, Constructor.class});
		this.constructor = constructor;
	}

	/**
	 * @see AbstractInvoker#createParameters(Method)
	 */
	@Override
	protected Object[] createParameters(Method method) {
		return new Object[] { this.getType(), this.constructor };
	}


	/**
	 * @see AbstractInvoker#mustExecute(Method, Class[])
	 */
	@Override
	protected boolean mustExecute(Method method, Class<?>[] parameterTypes) {
		return parameterTypes.length == 2 && parameterTypes[0].equals(Class.class)
				&& parameterTypes[1].equals(Constructor.class)
				&& ClassDescriptorAnnotationUtils.isForThis(this.constructor, method.getAnnotation(Types.class));

	}

}
