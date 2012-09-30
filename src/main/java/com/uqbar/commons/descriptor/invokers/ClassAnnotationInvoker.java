package com.uqbar.commons.descriptor.invokers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.uqbar.commons.descriptor.visitors.Type;

/**
 * Procesa una Annotation de la Clase
 *
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leonardo Gassman</a>
 * 
 */
public class ClassAnnotationInvoker extends AbstractAnnotationInvoker<Class, Type> {

	/**
	 * @param type
	 * @param visitor
	 * @param annotation
	 * @see AbstractAnnotationInvoker#AbstractAnnotationInvoker(Class, Object, java.lang.reflect.AnnotatedElement, Annotation, String, Class[])
	 */
	public ClassAnnotationInvoker(Class type, Object visitor, Annotation annotation) {
		super(type, visitor, type, annotation, "classAnnotation", new Class[] { Class.class, Annotation.class });
	}

	/**
	 * @see AbstractAnnotationInvoker#isForThisElement(Method)
	 */
	@Override
	protected boolean isForThisElement(Method method) {
		return ClassDescriptorAnnotationUtils.isForThis(this.getElement(), method.getAnnotation(Type.class));
	}

	/**
	 * @see AbstractAnnotationInvoker#getElementAnnotationType()
	 */
	@Override
	public Class<Type> getElementAnnotationType() {
		return Type.class;
	}

}
