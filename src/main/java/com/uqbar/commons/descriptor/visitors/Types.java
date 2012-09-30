package com.uqbar.commons.descriptor.visitors;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Usado para que un ClassVisitor indique que un metodo es usado para un Constructor en particular
 * 
 * @see ClassVisitor
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Types {
	Class[] value();
}
