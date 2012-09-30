package com.uqbar.commons.descriptor.invokers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.uqbar.commons.descriptor.visitors.Attribute;

/**
 * Procesa un field de una clase
 * 
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leonardo Gassman</a>
 * 
 */
public class FieldInvoker extends AbstractInvoker {

	private Field field;

	/**
	 * @param clazz
	 * @param visitor
	 * @param field field procesado
	 * @see AbstractInvoker
	 */
	public FieldInvoker(Class clazz, Object visitor, Field field) {
		super(clazz, visitor, "field", new Class[] { Class.class, Field.class });
		this.field = field;
	}

	/**
	 * @see AbstractInvoker#mustExecute(Method, Class[])
	 */
	@Override
	protected boolean mustExecute(Method method, Class<?>[] parameterTypes) {
		return parameterTypes.length == 2 && parameterTypes[0].equals(Class.class)
			&& parameterTypes[1].equals(Field.class)
			&& ClassDescriptorAnnotationUtils.isForThis(this.field, method.getAnnotation(Attribute.class));
	}

	/**
	 * @see AbstractInvoker#createParameters(Method)
	 */
	@Override
	protected Object[] createParameters(Method method) {
		return new Object[] { this.getType(), this.field };
	}

}
