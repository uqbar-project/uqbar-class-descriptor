package com.uqbar.commons.descriptor.invokers;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import sun.reflect.annotation.AnnotationType;

import com.uqbar.commons.descriptor.visitors.NonDefault;

/**
 * Invoca a los metodos que corresponden para el valor de una annotation de una annotation
 * @author lgassman
 */
public class AnnotationValueInvoker extends AbstractInvoker {

	private AnnotatedElement annotatedElement;
	private Annotation annotation;
	private String name;
	private Object value;
	private boolean isDefault;

	public AnnotationValueInvoker(Class type, Object visitor, AnnotatedElement field, Annotation annotation, String name, Object value, boolean isDefault) {
		super(type, visitor, "annotationValue", new Class[]{Class.class, AnnotatedElement.class, Annotation.class, String.class, Object.class, boolean.class});
		
		this.annotatedElement = field;
		this.annotation = annotation;
		this.name = name;
		this.value = value;
		this.isDefault = isDefault;
	}

	@Override
	protected Object[] createParameters(Method method) {
		//si solo se ejecuta para los que no son default, no tiene sentido el parametro isDefault
		if (!isForNonDefaultValue(method)) {
			return new Object[] {this.getType(), this.annotatedElement, this.annotation, this.name, this.value, this.isDefault};
		}
		else {
			return new Object[] {this.getType(), this.annotatedElement, this.annotation, this.name, this.value};
		}
	}

	private boolean isForNonDefaultValue(Method method) {
		return method.isAnnotationPresent(NonDefault.class);
	}

	@Override
	//ok, quizas tenga que poner reglas mas piolas y no tantos ifs
	protected boolean mustExecute(Method method, Class<?>[] parameterTypes) {
		//El default solo se ejecuta si no se encuentra otro metodo.
		if(method.equals(this.getDefaultMethod())) {
			return false;
		}
		//tiene que ser la misma cantidad de parametros que createParameters
		if(!(isForNonDefaultValue(method) && parameterTypes.length == 5 || parameterTypes.length == 6)) {
			return false;
		}
		
		//Chequea los tipos de los parametros
		if(!this.checkClass(parameterTypes[0])) {
			return false;
		}
		
		if(!this.checkAnnotatedElement(parameterTypes[1])) {
			return false;
		}
		
		if(!this.checkAnnotation(parameterTypes[2])) {
			return false;
		}
		
		if(!this.checkValueName(parameterTypes[3])) {
			return false;
		}
		
		if(!this.checkValue(parameterTypes[4])) {
			return false;
		}

		if(!this.isForNonDefaultValue(method) && !this.checkDefault(parameterTypes[5])) {
			return false;
		}
		
		//El annotatedElement es uno en particular
		if(ClassDescriptorAnnotationUtils.isOverrideForOther(method, this.annotatedElement)) {
			return false;
		}
		//Es un valor default que se quiere ignorar
		if(this.isDefault && isForNonDefaultValue(method)) {
			return false;
		}
		
		//El nombre del atributo es uno en particular
		AnnotationAttribute specificAttribute = method.getAnnotation(AnnotationAttribute.class);
		return specificAttribute == null || ClassDescriptorAnnotationUtils.isForThis(this.annotation, this.name, specificAttribute);
	}
	

	private boolean checkDefault(Class<?> class1) {
		return boolean.class.isAssignableFrom(class1);
	}

	private boolean checkValue(Class<?> class1) {
		AnnotationType type = AnnotationType.getInstance(this.annotation.annotationType());
		return class1.isAssignableFrom(type.memberTypes().get(this.name));
	}

	private boolean checkValueName(Class<?> class1) {
		return class1.equals(String.class);
	}

	private boolean checkAnnotation(Class<?> class1) {
		return class1.isAssignableFrom(this.annotation.annotationType());
	}

	private boolean checkAnnotatedElement(Class<?> class1) {
		return class1.isAssignableFrom(this.annotatedElement.getClass());
	}

	private boolean checkClass(Class<?> class1) {
		return class1.equals(Class.class);
	}
	
	

}
