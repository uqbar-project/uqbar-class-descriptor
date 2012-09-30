package com.uqbar.commons.descriptor.invokers;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import sun.reflect.annotation.AnnotationType;

import com.uqbar.commons.descriptor.visitors.Attribute;
import com.uqbar.commons.descriptor.visitors.Message;
import com.uqbar.commons.descriptor.visitors.Type;
import com.uqbar.commons.descriptor.visitors.Types;

/**
 * Relaciona las annotations usadas para sobreescribir un metodo, con un Member de reflection
 * 
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leonardo Gassman</a>
 * 
 */
public class ClassDescriptorAnnotationUtils {

	/**
	 * @param method
	 * @param message message or null, for not check isAnnotationPresent
	 * @return Si el metodo responde al mensaje.
	 */
	public static boolean isForThis(Method method, Message message) {
		return message != null && message.name().equals(method.getName())
			&& Arrays.equals(method.getParameterTypes(), message.parameters());
	}

	/**
	 * @param method
	 * @param message message or null, for not check isAnnotationPresent
	 * @return Si el Constructor es para estos tipos.
	 */
	public static boolean isForThis(Constructor constructor, Types types) {
		return types != null && Arrays.equals(constructor.getParameterTypes(), types.value());
	}

	/**
	 * @param clazz
	 * @param type
	 * @return Si la clase es de tipo
	 */
	public static boolean isForThis(Class clazz, Type type) {
		return type != null && clazz.equals(type.value());
	}

	/**
	 * @param field
	 * @param attribute
	 * @return Si el field corresponde a ese atributo
	 */
	public static boolean isForThis(Field field, Attribute attribute) {
		return attribute != null && field.getName().equals(attribute.value());
	}

	/**
	 * @param field
	 * @param attribute
	 * @return Si el field corresponde a ese atributo
	 */
	public static boolean isForThis(Annotation annotation, String attributeName, AnnotationAttribute annotationAttribute) {
		final Class annotationAttributeType = AnnotationType.getInstance(annotation.annotationType()).memberTypes().get(attributeName);
		return annotationAttribute != null && annotationAttribute.name().equals(attributeName) && annotationAttributeType!= null && annotationAttributeType.equals(annotationAttribute.type());
	}

	/**
	 * Indica si este metodo se definio para un AnnotatedElement que no corresponde al que se le paso por parametro.
	 */ 
	public static boolean isOverrideForOther(Method method, AnnotatedElement annotatedElement) {

		//REVISARME
		//no estoy seguro si quiero ser tan defensivo, pero creo que si permito que se anote con dos annotation, puedo tener problemas
		switch (annotationCount(method)) {
			case 0 : return false;
			case 1 : {
				if (annotatedElement.getClass().equals(Field.class) && method.isAnnotationPresent(Attribute.class)) {
					return !isForThis((Field) annotatedElement, method.getAnnotation(Attribute.class));
				}
				else if (annotatedElement.getClass().equals(Method.class) && method.isAnnotationPresent(Message.class)) {
					return !isForThis((Method) annotatedElement, method.getAnnotation(Message.class));
				}
				else if (annotatedElement.getClass().equals(Constructor.class) && method.isAnnotationPresent(Types.class)) {
					return !isForThis((Constructor) annotatedElement, method.getAnnotation(Types.class));
				}
				else if (annotatedElement.getClass().equals(Class.class) && method.isAnnotationPresent(Type.class)) {
					return !isForThis((Class) annotatedElement, method.getAnnotation(Type.class));
				}
				//si llego aca, el annotated element no corresponde con lo que se esta anotando, asi que seguro esta sobrescrito para otro
				return true;
			}
			default : {
				throw new UnsupportedOperationException(
					"No se puede determinar la annotation correspondiente al annotatedElement " + annotatedElement);
				
			}
		} 
		
	}

	/**
	 * @param method
	 * @return la cantidad de annotations especificas de classDescriptor que tiene el metodo
	 */
	private static int annotationCount(Method method) {
		return 
			sum(method, Attribute.class) + 
			sum(method, Message.class) +
			sum(method, Type.class) +
			sum(method, Types.class);
	}

	/**
	 * @param method
	 * @param annotationClass
	 * @return method.isAnnotationPresent(annotationClass) ? 1 : 0
	 */
	private static int sum(Method method, Class<? extends Annotation> annotationClass) {
		return method.isAnnotationPresent(annotationClass) ? 1 : 0;
	}
	
}
