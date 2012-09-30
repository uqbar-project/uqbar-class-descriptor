package com.uqbar.commons.descriptor.invokers;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * Clase base para invocar los metodos que procesan las annotation de los parametros de un metodo o un
 * constructor.
 * 
 * @param <T> 
 * @param <A>
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leonardo Gassman</a>
 */
public abstract class AbstractParameterAnnotationInvoker<T extends AnnotatedElement, A extends Annotation> extends
		AbstractAnnotationInvoker<T, A> {

	private int index;

	/**
	 * Constructor para ser usado por las subclases
	 * 
	 * @param type Clase que se esta describiendo
	 * @param visitor Visitor que recibe las invocaciones
	 * @param element El metodo o el Constructor que tiene la annotation
	 * @param index indice del parametro al que corresponde la annotation
	 * @param annotation Annotation que se procesa
	 * @param defaultMethodName nombre del metodo a llamar si es que no hay un metodo especifico
	 * @param defaultMeThodParamTypes parametros del metodo a llamar si es que no hay un metodo especifico
	 */
	public AbstractParameterAnnotationInvoker(Class type, Object visitor, T element, int index, Annotation annotation,
			String defaultMethodName, Class[] defaultMeThodParamTypes) {
		super(type, visitor, element, annotation, defaultMethodName, defaultMeThodParamTypes);
		this.index = index;
	}

	/**
	 * @see AbstractAnnotationInvoker#getParamsLength()
	 */
	@Override
	protected int getParamsLength() {
		return 4;
	}

	/**
	 * @see AbstractAnnotationInvoker#getAnnotationIndex()
	 */

	@Override
	protected int getAnnotationIndex() {
		return 3;
	}

	/**
	 * @see AbstractInvoker#mustExecute(Method, Class[])
	 */
	@Override
	protected boolean mustExecute(Method method, Class<?>[] parameterTypes) {
		return super.mustExecute(method, parameterTypes) && int.class.isAssignableFrom(parameterTypes[2]);
	}

	/**
	 * @see AbstractInvoker#createParameters(Method)
	 */
	@Override
	protected Object[] createParameters(Method method) {
		return new Object[] { this.getType(), this.getElement(), this.getIndex(), this.getAnnotation() };
	}

	/**
	 * @return indice que identifica al parametro
	 */
	protected int getIndex() {
		return this.index;
	}
}
