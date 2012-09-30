package com.uqbar.commons.descriptor.invokers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import com.uqbar.commons.descriptor.visitors.Types;

/**
 * Procesa la annotation de un parametro de un constructor
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leonardo Gassman</a>
 *
 */
public class ConstructorParameterAnnotationInvoker extends
		AbstractParameterAnnotationInvoker<Constructor, Types> {

	/**
	 * @param type
	 * @param visitor
	 * @param element
	 * @param index
	 * @param annotation
	 * @see AbstractParameterAnnotationInvoker#AbstractParameterAnnotationInvoker(Class, Object, java.lang.reflect.AnnotatedElement, int, Annotation, String, Class[])
	 */
	public ConstructorParameterAnnotationInvoker(Class type, Object visitor, Constructor element,
			int index, Annotation annotation) {
		super(type, visitor, element, index, annotation, "parameterAnnotation", new Class[] { Class.class,
				Constructor.class, int.class, Annotation.class });
	}

	/**
	 * @see AbstractAnnotationInvoker#isForThisElement(Method)
	 */
	@Override
	protected boolean isForThisElement(Method method) {
		return ClassDescriptorAnnotationUtils.isForThis(this.getElement(), method.getAnnotation(this.getElementAnnotationType()));
	}

	/**
	 * @return Types.class
	 * @see AbstractAnnotationInvoker#getElementAnnotationType()
	 */	
	@Override
	public Class<Types> getElementAnnotationType() {
		return Types.class;
	}

}
