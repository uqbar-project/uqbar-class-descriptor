package com.uqbar.commons.descriptor.visitors;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Usada para indicar en un ClassVisitor que un Metodo es para procesar un metodo especifico
 * 
 * @see ClassVisitor
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Message {
	String name();

	Class[] parameters() default {};
}
