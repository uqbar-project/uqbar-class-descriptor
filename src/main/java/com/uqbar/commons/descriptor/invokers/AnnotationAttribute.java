package com.uqbar.commons.descriptor.invokers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationAttribute {
	/** el nombre del atributo de la annotation */
	public String name();
	/** el tipo del atributo */
	public Class type();
}
