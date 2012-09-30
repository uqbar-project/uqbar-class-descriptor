package com.uqbar.commons.descriptor.invokers;

/**
 * Un invoker tiene la responsabilidad de detectar cuales son los metodos del visitor que debe llamar para un elemento particular de la clase.
 * En caso que no haya un metodo para un caso especifico, sabe detectar si el visitor tiene un metdodo default.
 * El invoker llama a los metodos espicificos que detecto o al default si existiese.
 * 
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 */
public interface Invoker {

	/**
	 * llama a los metodos especificos del visitor segun el elemento que se esta visitando, o al metodo default.
	 */
	public void invoke();

}
