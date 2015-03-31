package com.uqbar.commons.descriptor.visitors;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Esta interface tiene todos los metodos por default a los cuales llama un ClassDescriptor
 * Esta interface solo es usada como documentacion, o si se quiere crear un Visitor que responda a todos los
 * mensajes por default, pues el ClassVisitor llama a los metodos por default por reflection. Si un visitor
 * no implementa alguno de estos metodos, simplemente esos eventos seran ignorados.
 * 
 * 
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a> 
 *
 */
public interface ClassVisitor {

	/**
	 * Metodo invocado al comenzar una descripcion
	 * @param clazz
	 */
	void descriptionStart(Class clazz);
	
	/**
	 * Metodo invocado al finalizar una descripcion
	 * @param clazz
	 */
	void descriptionFinish(Class clazz);
	
	/**
	 * Informa que la clase que se esta visitando, tiene una annotation.
	 * Se puede escribir un metodo para una annotation particular que reciba 
	 * dos paramatros, el primero del tipo Class y el segundo del tipo particular de la 
	 * annotation. Entonces en vez de llamarse a este metodo el ClassDescriptor llama al particular
	 * si se quiere escribir un metodo con una firma similar para un proposito distinto, entonces
	 * debe marcar el metodo como la annotation {@link Ignore}
	 * 
	 * @param clazz 
	 * 
	 * @param annotation
	 */
	void classAnnotation(Class clazz, Annotation annotation);

	/**
	 * Indica que la clase tiene un field. Este metodo puede ser sobreescrito
	 * para un field en particular si se escribe un metodo que reciba un parametro de 
	 * tipo Class y uno de tipo Field, y se lo anota con la annotation Attribute("nombreAtributo") 
	 * 
	 * @param clazz
	 * @param field
	 */
	void field(Class clazz, Field field);

	/**
	 * Indica que un atributo de una clase tiene una annotation
	 * Este metodo puede ser sobrescrito para una annotation en particular, si se genera un metodo con
	 * parametros(Class, Field, SpecificAnnotation)
	 * o para una annotation particular de un atributo particular, si a un metodo con parametros
	 * parametros(Class, Field, SpecificAnnotation) se lo anota con Attribute("attributeName")
	 * tambien se lo puede sobreescribir para un field particular, cualquier annotation, con un metodo
	 * con los mismos parametros y el annotation Attribute("attributeName")
	 * 
	 * @param type
	 * @param field
	 * @param annotation
	 */
	void fieldAnnotation(Class type, Field field, Annotation annotation);

	
	/**
	 * Indica que la clase tiene un metodo
	 * @param type
	 * @param method
	 */
	void method(Class type, Method method);
	
	/**
	 * Indica que el metodo de la clase tiene una annotation.
	 * Las formas de sobreescribir este metodo son:
	 * 
	 * - escribiendo un metodo con la misma firma pero con una annotation en particular.
	 * - anotando el metodo con Message()
	 * - ambas combinaciones.
	 * 
	 * @param type
	 * @param method
	 * @param annotation
	 */
	void methodAnnotation(Class type, Method method, Annotation annotation);

	/**
	 * Indica que el metodo de la clase tiene una annotation en un parametro.
	 * Las formas de sobreescribir este metodo son:
	 * 
	 * - por annotation escribiendo un metodo con la misma firma pero con una annotation en particular.
	 * - por metodo anotando el metodo con Message()
	 * - ambas combinaciones.
	 * 
	 * Por el momento no se puede sobreescribir para un parametro particular, porque las combinaciones crecen mucho 
	 * y no parece ser util, pues lo logico es procesar por igual todos los parametros de un metodo.
	 * 
	 * @param type
	 * @param method
	 * @param index indice del parametro
	 * @param annotation
	 */
	void parameterAnnotation(Class type, Method method, int index, Annotation annotation);

	/**
	 * Indica que el constructor de la clase tiene una annotation en un parametro.
	 * Las formas de sobreescribir este metodo son:
	 * 
	 * - por annotation escribiendo un metodo con la misma firma pero con una annotation en particular.
	 * - por metodo anotando el metodo con Types()
	 * - ambas combinaciones.
	 * 
	 * Por el momento no se puede sobreescribir para un parametro particular, porque las combinaciones crecen mucho 
	 * y no parece ser util, pues lo logico es procesar por igual todos los parametros de un metodo.
	 * 
	 * @param type
	 * @param constructor
	 * @param index indice del parametro
	 * @param annotation
	 */
	void parameterAnnotation(Class type, Constructor constructor, int index, Annotation annotation);

	/**
	 * Indica que la clase tiene ese constructor.
	 * Este metodo se puede sobreescribir escribiendo un metodo que reciba los mismos parametros
	 * y anotandolo con la annotation Types
	 * 
	 * @param type
	 * @param constructor
	 */
	void constructor(Class type, Constructor constructor);

	/**
	 * Indica que la clase en dicho constructor, tiene esa annotation.
	 * 
	 * - por annotation escribiendo un metodo con la misma firma pero con una annotation en particular.
	 * - por constructor anotando el metodo con Types()
	 * - ambas combinaciones.
	 * 
	 * @param type
	 * @param element
	 */
	void constructorAnnotation(Class type, Constructor element, Annotation annotation);
	
	/**
	 * Indica que la clase de la cual extiende. Este metodo Se puede sobreescribir 
	 * con la annotation Type
	 * 
	 * @param clazz
	 * @param superClass
	 */
	void superClass(Class clazz, Class superClass);

	/**
	 * Indica que la clase contiene tal tipo.
	 * El ClassDescriptor por default usa solo las interfaces que implementa
	 * 
	 * Este metodo se puede sobreescribir con la annotation Type
	 * 
	 * @param type
	 * @param superType
	 */
	void type(Class type, Class superType);
	
	
	/**
	 * avisa el valor de un campo de una annotation.
	 * Se puede sobreescribirse de varias formas:
	 * 
	 * - Usando el tipo especifico de la annotation
	 * - Usando el tipo especifico del annotatedElement
	 * - Usando las annotation {@link Type}, {@link Types}, {@link Message} y {@link Attribute}
	 * 
	 * @param clazz
	 * @param element
	 * @param annotation
	 * @param value
	 */
	void annotationValue(Class clazz, AnnotatedElement element, Annotation annotation, String name, Object value, boolean isDefault);
}
