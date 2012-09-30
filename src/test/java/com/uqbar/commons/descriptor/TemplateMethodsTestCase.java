package com.uqbar.commons.descriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.uqbar.commons.descriptor.visitors.ClassVisitor;

public class TemplateMethodsTestCase extends AbstractClassDescriptorTest implements ClassVisitor{

	private boolean init = false;
	private boolean end = false;

	public void annotationValue(Class clazz, AnnotatedElement element, Annotation annotation, String name,
			Object value, boolean isDefault) {
		this.checkInProcess();
	}

	private void checkInProcess() {
		assertTrue("se llamo a un metodo antes del descriptionStart", this.init);
		assertFalse("se llamo a un metodo despues del descriptionFinish", this.end);	
	}

	public void classAnnotation(Class clazz, Annotation annotation) {
		this.checkInProcess();
	}

	public void constructor(Class type, Constructor constructor) {
		this.checkInProcess();
	}

	public void constructorAnnotation(Class type, Constructor element, Annotation annotation) {
		this.checkInProcess();
	}

	public void descriptionFinish(Class clazz) {
		this.checkInProcess();
		this.end = true;
	}

	public void descriptionStart(Class clazz) {
		assertFalse("Se llamo al start despues de iniciado", this.init);
		this.init = true;
		this.checkInProcess();
	}

	public void field(Class clazz, Field field) {
		this.checkInProcess();
	}

	public void fieldAnnotation(Class type, Field field, Annotation annotation) {
		this.checkInProcess();
	}

	public void method(Class type, Method method) {
		this.checkInProcess();
	}

	public void methodAnnotation(Class type, Method method, Annotation annotation) {
		this.checkInProcess();
	}

	public void parameterAnnotation(Class type, Method method, int index, Annotation annotation) {
		this.checkInProcess();
	}

	public void parameterAnnotation(Class type, Constructor constructor, int index, Annotation annotation) {
		this.checkInProcess();
	}

	public void superClass(Class clazz, Class superClass) {
		this.checkInProcess();
	}

	public void type(Class type, Class superType) {
		this.checkInProcess();
	}

	@Override
	protected void assertions() {
		assertTrue("nunca se finalizo el proceso", this.end);
	}
}
