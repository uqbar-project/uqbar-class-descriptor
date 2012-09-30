package com.uqbar.commons.descriptor.invokers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import com.uqbar.commons.descriptor.visitors.Types;

/**
 * Invoca a los metodos que procesan una Annotation de un Constructor
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leonardo Gassman</a>
 *
 */
public class ConstructorAnnotationInvoker extends AbstractAnnotationInvoker<Constructor, Types> {

	/**
	 * @param type 
	 * @param visitor
	 * @param element
	 * @param annotation
	 * @see AbstractAnnotationInvoker#AbstractAnnotationInvoker(Class, Object, java.lang.reflect.AnnotatedElement, Annotation, String, Class[])
	 */
	public ConstructorAnnotationInvoker(Class type, Object visitor, Constructor element, Annotation annotation) {
		super(type, visitor, element, annotation, "constructorAnnotation", new Class[]{Class.class, Constructor.class, Annotation.class});
	}

	/**
	 * @see AbstractAnnotationInvoker#isForThisElement(Method)
	 */
	@Override
	protected boolean isForThisElement(Method method) {
		return ClassDescriptorAnnotationUtils.isForThis(this.getElement(), method.getAnnotation(Types.class));
	}

	/**
	 * @return {@link Types}
	 * @see AbstractAnnotationInvoker#getElementAnnotationType()
	 */
	@Override
	public Class<Types> getElementAnnotationType() {
		return Types.class;
	}


}
