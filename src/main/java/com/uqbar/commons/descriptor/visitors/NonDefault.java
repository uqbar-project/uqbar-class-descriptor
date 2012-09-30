package com.uqbar.commons.descriptor.visitors;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Usada para indicar que un metodo debe ser invocado unicamente si el valor de la annotation no es default.
 * @author lgassman
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NonDefault {

}
