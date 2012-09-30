package com.uqbar.commons.descriptor.visitors;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Clase base para escribir ClassVisitors Implementa todos los metodos por default para que no hagan nada
 * 
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 */
public class AbstractClassVisitor implements ClassVisitor {

	/**
	 * @see ClassVisitor#classAnnotation(Class, Annotation)
	 */
	public void classAnnotation(Class clazz, Annotation annotation) {
	}

	/**
	 * @see ClassVisitor#field(Class, Field)
	 */
	public void field(Class clazz, Field field) {
	}

	/**
	 * @see ClassVisitor#fieldAnnotation(Class, Field, Annotation)
	 */
	public void fieldAnnotation(Class type, Field field, Annotation annotation) {
	}

	/**
	 * @see ClassVisitor#method(Class, Method)
	 */
	public void method(Class type, Method method) {
	}

	/**
	 * @see ClassVisitor#methodAnnotation(Class, Method, Annotation)
	 */
	public void methodAnnotation(Class type, Method method, Annotation annotation) {
	}

	/**
	 * @see ClassVisitor#parameterAnnotation(Class, Method, int, Annotation)
	 */
	public void parameterAnnotation(Class type, Method method, int index, Annotation annotation) {
	}

	/**
	 * @see ClassVisitor#constructor(Class, Constructor)
	 */
	public void constructor(Class type, Constructor constructor) {
	}

	/**
	 * @see ClassVisitor#constructorAnnotation(Class, Constructor, Annotation)
	 */
	public void constructorAnnotation(Class type, Constructor element, Annotation annotation) {
	}

	/**
	 * @see ClassVisitor#parameterAnnotation(Class, Constructor, int, Annotation)
	 */
	public void parameterAnnotation(Class type, Constructor constructor, int index, Annotation annotation) {
	}

	/**
	 * @see ClassVisitor#type(Class, Class)
	 */
	public void type(Class type, Class superType) {
	}

	/**
	 * @see ClassVisitor#superClass(Class, Class)
	 */
	public void superClass(Class clazz, Class superClass) {
	}

	public void annotationValue(Class clazz, AnnotatedElement element, Annotation annotation, String name,
			Object value, boolean isDefault) {
	}

	public void descriptionFinish(Class clazz) {
	}

	public void descriptionStart(Class clazz) {
	}

}
