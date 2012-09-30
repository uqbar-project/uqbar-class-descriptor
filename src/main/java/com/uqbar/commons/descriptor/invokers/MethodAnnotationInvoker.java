package com.uqbar.commons.descriptor.invokers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.uqbar.commons.descriptor.visitors.Message;

/**
 * Procesa una Annotation de un metodo
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leonardo Gassman</a>
 *
 */
public class MethodAnnotationInvoker extends AbstractAnnotationInvoker<Method, Message> {

	/**
	 * 
	 * @param type
	 * @param visitor
	 * @param method
	 * @param annotation
	 * @see AbstractAnnotationInvoker#AbstractAnnotationInvoker(Class, Object, java.lang.reflect.AnnotatedElement, Annotation, String, Class[])
	 */
	public MethodAnnotationInvoker(Class type, Object visitor, Method method, Annotation annotation) {
		super(type, visitor, method, annotation, "methodAnnotation", new Class[]{Class.class, Method.class, Annotation.class});
	}

	/**
	 * @see AbstractAnnotationInvoker#isForThisElement(Method)
	 */
	@Override
	protected boolean isForThisElement(Method method) {
		return ClassDescriptorAnnotationUtils.isForThis(this.getElement(), method.getAnnotation(Message.class));
	}


	/**
	 * see AbstractAnnotationInvoker#getElement()
	 */
	@Override
	public Class<Message> getElementAnnotationType() {
		return Message.class;
	}

}
