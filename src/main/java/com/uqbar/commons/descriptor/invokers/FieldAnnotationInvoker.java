package com.uqbar.commons.descriptor.invokers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.uqbar.commons.descriptor.visitors.Attribute;

/**
 * Procesa la annotation de un field
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leonardo Gassman</a>
 *
 */
public class FieldAnnotationInvoker extends AbstractAnnotationInvoker<Field, Attribute> {


	/**
	 * @param clazz
	 * @param visitor
	 * @param field
	 * @param annotation
	 * @see AbstractAnnotationInvoker#AbstractAnnotationInvoker(Class, Object, java.lang.reflect.AnnotatedElement, Annotation, String, Class[])
	 */
	public FieldAnnotationInvoker(Class clazz, Object visitor, Field field, Annotation annotation) {
		super(clazz, visitor, field, annotation, "fieldAnnotation", new Class[]{Class.class, Field.class, Annotation.class});
	}


	/**
	 * @see AbstractAnnotationInvoker#isForThisElement(Method)
	 */
	@Override
	protected boolean isForThisElement(Method method) {
		return ClassDescriptorAnnotationUtils.isForThis(this.getElement(), method.getAnnotation(Attribute.class));
	}

	/**
	 * @return {@link Attribute}
	 * @see AbstractAnnotationInvoker#getElementAnnotationType()
	 */
	@Override
	public Class<Attribute> getElementAnnotationType() {
		return Attribute.class;
	}

}
