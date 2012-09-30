package com.uqbar.commons.descriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map.Entry;

import sun.reflect.annotation.AnnotationType;

import com.uqbar.commons.descriptor.invokers.AnnotationValueInvoker;
import com.uqbar.commons.descriptor.invokers.ClassAnnotationInvoker;
import com.uqbar.commons.descriptor.invokers.ConstructorAnnotationInvoker;
import com.uqbar.commons.descriptor.invokers.ConstructorInvoker;
import com.uqbar.commons.descriptor.invokers.ConstructorParameterAnnotationInvoker;
import com.uqbar.commons.descriptor.invokers.FieldAnnotationInvoker;
import com.uqbar.commons.descriptor.invokers.FieldInvoker;
import com.uqbar.commons.descriptor.invokers.MethodAnnotationInvoker;
import com.uqbar.commons.descriptor.invokers.MethodInvoker;
import com.uqbar.commons.descriptor.invokers.MethodParameterAnnotationInvoker;
import com.uqbar.commons.descriptor.invokers.TypeInvoker;

/**
 * Describe una clase y le va contando al visitor como esta compuesta El visitor puede annotar sus metodos
 * para que se le realicen invocaciones particulares por cada annotation en particular, cada atributo en
 * particular o cada metodo.
 * 
 * Se puede generar subclases para sobreescribir el metodo describe, para cambiar el orden de invocacion de
 * los metodos, o la cantidad de cosas que se desean informar. Por ejemplo, una subclase podria no describir
 * las clases de la cual heredan
 * 
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 */
public class ClassDescriptor {

	/**
	 * El classDescriptor le cuenta al visitor como esta compuesta la clase Es el metodo publico.
	 * 
	 * @param clazz
	 * @param visitor
	 */
	public void describe(Class clazz, Object visitor) {
		this.descriptionStart(clazz, visitor);
		
		this.describeTypesComplete(clazz, visitor);

		for (Annotation annotation : getClassAnnotations(clazz)) {
			this.describeClassAnnotations(clazz, visitor, annotation);
		}

		for (Constructor constructor : getConstructors(clazz)) {
			this.describeConstructorComplete(clazz, visitor, constructor);
		}

		for (Field field : getFields(clazz)) {
			this.describeFieldComplete(clazz, visitor, field);
		}

		for (Method method : getMethods(clazz)) {
			this.describeMethodComplete(clazz, visitor, method);
		}
		
		this.descriptionFinish(clazz, visitor);
	}

	protected void descriptionStart(Class clazz, Object visitor) {
		this.invokeMethod(clazz, visitor, "descriptionStart");
	}

	protected void descriptionFinish(Class clazz, Object visitor) {
		this.invokeMethod(clazz, visitor, "descriptionFinish");	
	}

	private void invokeMethod(Class clazz, Object visitor, String methodName) {
		try {
			Method m = visitor.getClass().getMethod(methodName, Class.class);
			m.invoke(visitor, clazz);
		}
		catch (SecurityException e) {
			//is ok, no method for call;			
		}
		catch (NoSuchMethodException e) {
			//is ok, no method for call;
		}
		catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void describeTypesComplete(Class clazz, Object visitor) {
		this.describeSuperClass(clazz, visitor, clazz.getSuperclass());
		for (Class type : getTypes(clazz)) {
			this.describeType(clazz, visitor, type);
		}
	}

	// ************************************************
	// ** todos los metodos que terminan en Complete son
	// ** templates method que se pueden sobreescribir
	// ************************************************

	protected void describeConstructorComplete(Class clazz, Object visitor, Constructor constructor) {
		describeConstructor(clazz, visitor, constructor);
		for (Annotation annotation : getConstructorAnnotations(constructor)) {
			this.describeConstructorAnnotation(clazz, visitor, constructor, annotation);
		}
		int index = 0;
		for (Annotation[] param : constructor.getParameterAnnotations()) {
			for (Annotation annotation : param) {
				this.describeConstructorParameterAnnotation(clazz, visitor, constructor, index, annotation);
			}
			index++;
		}

	}

	protected void describeMethodComplete(Class clazz, Object visitor, Method method) {
		describeMethod(clazz, visitor, method);
		for (Annotation annotation : getMethodAnnotations(method)) {
			this.describeMethodAnnotation(clazz, visitor, method, annotation);
		}

		int index = 0;
		for (Annotation[] param : method.getParameterAnnotations()) {
			for (Annotation annotation : param) {
				this.describeMethodParameterAnnotation(clazz, visitor, method, index, annotation);
			}
			index++;
		}
	}

	protected void describeFieldComplete(Class clazz, Object visitor, Field field) {
		this.describeField(clazz, visitor, field);
		for (Annotation annotation : field.getAnnotations()) {
			this.describeFieldAnnotation(clazz, visitor, field, annotation);
		}

	}

	protected void describeAnnotatedValues(Class clazz, Object visitor, AnnotatedElement annotatedElement, Annotation annotation) {
		AnnotationType annotationType = AnnotationType.getInstance(annotation.annotationType());
		for(Entry<String, Method> entry : annotationType.members().entrySet()) {
			Object value;
			try {
				value = entry.getValue().invoke(annotation, new Object[]{});
			}
			catch (Exception e) {
				throw new RuntimeException("Problema al recupear el valor de una annotation", e);
			}
			Object defaultValue = annotationType.memberDefaults().get(entry.getKey());
			this.describeAnnotationValue(clazz, visitor, annotatedElement, annotation, entry.getKey(), value, defaultValue != null && defaultValue.equals(value));
		}
	}

	protected void describeAnnotationValue(Class clazz, Object visitor, AnnotatedElement annotatedElement, Annotation annotation,
			String key, Object value, boolean isDefault) {

		new AnnotationValueInvoker(clazz, visitor, annotatedElement, annotation, key, value, isDefault).invoke();
	}

	// *****************************************************
	// ** Metodos que van a buscar las member de Reflection
	// ** Sobreescribiendo estos metodos se puede cambiar
	// ** para buscar los declared member o los public member
	// *****************************************************

	protected Method[] getMethods(Class clazz) {
		return clazz.getMethods();
	}

	protected Field[] getFields(Class clazz) {
		return clazz.getDeclaredFields();
	}

	protected Constructor[] getConstructors(Class clazz) {
		return clazz.getConstructors();
	}

	protected Annotation[] getClassAnnotations(Class clazz) {
		return clazz.getAnnotations();
	}

	protected Annotation[] getConstructorAnnotations(Constructor constructor) {
		return constructor.getAnnotations();
	}

	protected Annotation[] getMethodAnnotations(Method method) {
		return method.getAnnotations();
	}

	protected Class[] getTypes(Class clazz) {
		return clazz.getInterfaces();
	}

	// **********************************************
	// ** Metodos que finalmente hacen las invocaciones
	// ** al visitor. Todos protected listos para
	// ** sobrescribir
	// **********************************************

	protected void describeConstructor(Class clazz, Object visitor, Constructor constructor) {
		new ConstructorInvoker(clazz, visitor, constructor).invoke();
	}

	protected void describeConstructorParameterAnnotation(Class clazz, Object visitor, Constructor constructor,
			int index, Annotation annotation) {
		new ConstructorParameterAnnotationInvoker(clazz, visitor, constructor, index, annotation).invoke();
	}

	protected void describeConstructorAnnotation(Class clazz, Object visitor, Constructor constructor,
			Annotation annotation) {
		new ConstructorAnnotationInvoker(clazz, visitor, constructor, annotation).invoke();
		this.describeAnnotatedValues(clazz, visitor, constructor, annotation);
	}

	private void describeMethod(Class clazz, Object visitor, Method method) {
		new MethodInvoker(clazz, visitor, method).invoke();
	}

	protected void describeMethodParameterAnnotation(Class clazz, Object visitor, Method method, int index,
			Annotation annotation) {
		new MethodParameterAnnotationInvoker(clazz, visitor, method, index, annotation).invoke();
	}

	protected void describeMethodAnnotation(Class clazz, Object visitor, Method method, Annotation annotation) {
		new MethodAnnotationInvoker(clazz, visitor, method, annotation).invoke();
		this.describeAnnotatedValues(clazz, visitor, method, annotation);
	}

	protected void describeFieldAnnotation(Class clazz, Object visitor, Field field, Annotation annotation) {
		new FieldAnnotationInvoker(clazz, visitor, field, annotation).invoke();
		this.describeAnnotatedValues(clazz, visitor, field, annotation);
	}

	protected void describeField(Class clazz, Object visitor, Field field) {
		new FieldInvoker(clazz, visitor, field).invoke();
	}

	protected void describeClassAnnotations(Class clazz, Object visitor, Annotation annotation) {
		new ClassAnnotationInvoker(clazz, visitor, annotation).invoke();
		this.describeAnnotatedValues(clazz, visitor, clazz, annotation);
	}

	private void describeType(Class clazz, Object visitor, Class type) {
		new TypeInvoker(clazz, visitor, type, false).invoke();
	}

	private void describeSuperClass(Class clazz, Object visitor, Class superclass) {
		new TypeInvoker(clazz, visitor, superclass, true).invoke();
	}
}
