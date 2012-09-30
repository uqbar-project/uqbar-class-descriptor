package com.uqbar.commons.descriptor.invokers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.uqbar.commons.descriptor.visitors.Message;

/**
 * Porcesa la annotation de un parametro de un metodo
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leonardo Gassman</a>
 *
 */
public class MethodParameterAnnotationInvoker extends AbstractParameterAnnotationInvoker<Method, Message> {

	/**
	 * @param type
	 * @param visitor
	 * @param element
	 * @param index
	 * @param annotation
	 * @see AbstractParameterAnnotationInvoker#AbstractParameterAnnotationInvoker(Class, Object, java.lang.reflect.AnnotatedElement, int, Annotation, String, Class[])
	 */
	public MethodParameterAnnotationInvoker(Class type, Object visitor, Method element, int index,
			Annotation annotation) {
		super(type, visitor, element, index, annotation, "parameterAnnotation", new Class[] { Class.class, Method.class, int.class, Annotation.class });
	}

	/**
	 * @see AbstractAnnotationInvoker#getElementAnnotationType()
	 */
	@Override
	public Class<Message> getElementAnnotationType() {
		return Message.class;
	}


	/**
	 * @see AbstractAnnotationInvoker#isForThisElement(Method)
	 */
	@Override
	protected boolean isForThisElement(Method method) {
		return ClassDescriptorAnnotationUtils.isForThis(this.getElement(), method.getAnnotation(this.getElementAnnotationType()));
	}

}
