package com.uqbar.commons.descriptor.invokers;

import java.lang.reflect.Method;

import com.uqbar.commons.descriptor.visitors.Message;

/**
 * Procesa un Metodo de una clase
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leonardo Gassman</a>
 *
 */
public class MethodInvoker extends AbstractInvoker {

	private Method method;

	/**
	 * @param clazz
	 * @param visitor
	 * @param method Metodo a procesar
	 * @see AbstractInvoker#AbstractInvoker(Class, Object)
	 */
	public MethodInvoker(Class clazz, Object visitor, Method method) {
		super(clazz, visitor, "method", new Class[]{Class.class, Method.class});
		this.method = method;
	}

	/**
	 * @see AbstractInvoker#createParameters(Method)
	 */
	@Override
	protected Object[] createParameters(Method method) {
		return new Object[]{this.getType(), this.method};
	}
	

	/**
	 * @see AbstractInvoker#mustExecute(Method, Class[])
	 */
	@Override
	protected boolean mustExecute(Method method, Class<?>[] parameterTypes) {
		Message message = method.getAnnotation(Message.class);
		return parameterTypes.length == 2 && 
		parameterTypes[0].equals(Class.class) && 
		parameterTypes[1].equals(Method.class) && 
		ClassDescriptorAnnotationUtils.isForThis(this.method, message);
	}

}
