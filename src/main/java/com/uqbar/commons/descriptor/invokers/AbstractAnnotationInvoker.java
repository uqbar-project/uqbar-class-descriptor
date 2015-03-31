package com.uqbar.commons.descriptor.invokers;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.sql.Types;
import java.text.AttributedCharacterIterator.Attribute;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import com.sun.org.apache.bcel.internal.generic.Type;
import com.uqbar.commons.descriptor.visitors.ClassVisitor;

/**
 * Clase base para realizar las invocaciones sonbre las annotations
 *  
 * @param <T> El tipo del elemento anotado
 * @param <A> El tipo de la annotation asociado al tipo del element
 * @see Type Message Types  Attribute
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leonardo Gassman</a>
 */
public abstract class AbstractAnnotationInvoker <T extends AnnotatedElement, A extends Annotation> extends AbstractInvoker {

	private T element;
	private Annotation annotation;
	
	/**
	 * @param type Clase a la cual pertence el elemento anotado
	 * @param visitor Visitor que recibe las notificaciones @see {@link ClassVisitor}
	 * @param element Elemento anotado
	 * @param annotation annotation a procesar
	 * @param defaultMethodName nombre del metodo a invocar si no se encuentra uno especifico
	 * @param defaultMethodParamsTypes parametros del metodo a invocar si no encuentra uno especifico
	 */
	public AbstractAnnotationInvoker(Class type, Object visitor, T element, Annotation annotation, String defaultMethodName, Class[] defaultMethodParamsTypes) {
		super(type, visitor, defaultMethodName, defaultMethodParamsTypes);
		this.element = element;
		this.annotation = annotation;
	}
	
	/**
	 * @return El tipo de annotation asociada a este AnnotedElement 
	 */
	public abstract Class<A> getElementAnnotationType();

	/**
	 * Construye el array con los paremtros que se utilizaran en la invocacion
	 * 
	 * @param method Metodo que se invocara
	 * @return un array con los parametros
	 */
	@Override
	protected Object[] createParameters(Method method) {
		//si es un objeto accesible, lo tiene que tener en la parte de la firma
		if(isInParameter()) {
			return new Object[]{this.getType(), this.element, this.annotation};
		}
		else {
			return new Object[]{this.getType(), this.annotation};
		}
	}

	private boolean isInParameter() {
		return this.element instanceof AccessibleObject;
	}


	/**
	 * se indico explicitamente que ese metodo es solo para ese element
	 * @param method metodo candidato a invocar
	 * @return return AnnotationUtils.isForThis(this.getElement(), method.getAnnotation(this.getAnnotationType()))
	 */
	protected abstract boolean isForThisElement(Method method);
	
	/**
	 * se indico explicitamente que ese metodo es para otro element
	 * @param method metodo candidato a invocar
	 * @return Si es para todos los otros elementos menos este.
	 */	
	protected boolean isForOtherElement(Method method) {
		return !this.isForAllElements(method) && !this.isForThisElement(method);
	}
	
	/**
	 * no se indico nada acerca del elemento del metodo
	 * @param method metodo candidato a invocar
	 * @return !method.isAnnotationPresent(this.getAnnotationType());
	 */	
	protected  boolean isForAllElements(Method method) {
		return !method.isAnnotationPresent(this.getElementAnnotationType());
	};
	
	
	/**
	 * decide si el metodo candidato debe ser ejecutado
	 * @param method el metodo candidato
	 * @param parameterTypes method.getParameterTypes (for use convenience)
	 */
	@Override
	protected boolean mustExecute(Method method, Class<?>[] parameterTypes) {
		return parameterTypes.length == getParamsLength()
				&& parameterTypes[0].equals(Class.class) 
		        && checkElement(parameterTypes[1])
		       && (this.overridenForSpecificAnnotation(method, parameterTypes) || 
		    	   this.overridenForSpecificElement(method, parameterTypes) ||
		    	   this.overridenForSpecificElementAndAnnotation(method, parameterTypes));
	}

	private boolean overridenForSpecificElementAndAnnotation(Method method, Class<?>[] parameterTypes) {
		return parameterTypes[getAnnotationIndex()].equals(this.getAnnotation().annotationType()) && this.isForThisElement(method);
	}

	private boolean overridenForSpecificElement(Method method, Class<?>[] parameterTypes) {
		return 	Annotation.class.equals(parameterTypes[getAnnotationIndex()]) 
				&& this.isForThisElement(method);
	}
	
	private boolean overridenForSpecificAnnotation(Method method, Class<?>[] parameterTypes) {
		return parameterTypes[getAnnotationIndex()].equals(this.annotation.annotationType()) && isForAllElements(method);
	}

	/**
	 * Controla que de venir un AnnotatedElement entre los parametros del metodo candidato, sea del tipo con el que se construyo
	 * el invoqker
	 * @param clazz tipo recibido en los parametros.
	 * @return true si es lo esperado
	 */
	protected boolean checkElement(Class<?> clazz) {
		if(isInParameter()) {
			return clazz.equals(this.element.getClass());
		}
		else {
			return true;
		}
	}

	/**
	 * @return cantidad de parametros esperados en el metodo
	 */
	protected int getParamsLength() {
		return isInParameter() ? 3 : 2;
	}
	
	/**
	 * @return indice de la annotation
	 */
	protected int getAnnotationIndex() {
		return isInParameter() ? 2 : 1;
	}

	/**
	 * @return 	El elemento anotado
	 */
	protected T getElement() {
		return this.element;
	}
	
	/**
	 * @return La annotation anotada en el element
	 */
	public Annotation getAnnotation() {
		return this.annotation;
	}
	
}
