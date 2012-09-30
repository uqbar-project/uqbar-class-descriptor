package com.uqbar.commons.descriptor.invokers;

import java.lang.reflect.Method;

import com.uqbar.commons.descriptor.visitors.Type;

/**
 * Invoca al metodo correspondiente a un supertipo
 * 
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leonardo Gassman</a>
 */
public class TypeInvoker extends AbstractInvoker {

	private Class superType;

	/**
	 * @param type
	 * @param visitor
	 * @param superType
	 * @param asSuperClass si se debe invocar al metodo declarado para la superclase o al de todos los tipos
	 * @see AbstractInvoker#AbstractInvoker(Class, Object, String, Class[])
	 */
	public TypeInvoker(Class type, Object visitor, Class superType, boolean asSuperClass) {
		super(type, visitor, asSuperClass ? "superClass" : "type", new Class[]{Class.class, Class.class});
		this.superType = superType;
	}

	/**
	 * @see AbstractInvoker#createParameters(Method)
	 */
	@Override
	protected Object[] createParameters(Method method) {
		return new Object[] {this.getType(), this.superType};
	}


	/**
	 * @see AbstractInvoker#mustExecute(Method, Class[])
	 */
	@Override
	protected boolean mustExecute(Method method, Class<?>[] parameterTypes) {
		return ClassDescriptorAnnotationUtils.isForThis(this.superType, method.getAnnotation(Type.class));
	}

}
