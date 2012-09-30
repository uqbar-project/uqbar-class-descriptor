package com.uqbar.commons.descriptor.visitors;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Usada por un ClassVisitor para indicarle al ClassDescriptor que un metodo no debe ser interpretado como un
 * listener de eventos.
 * 
 * @see ClassVisitor
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Ignore {

}
