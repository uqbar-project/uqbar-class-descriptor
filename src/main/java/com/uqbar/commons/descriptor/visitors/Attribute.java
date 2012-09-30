package com.uqbar.commons.descriptor.visitors;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indica que un metodo es especifico de un field
 * 
 * @see ClassVisitor
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute {
	String value();
}
