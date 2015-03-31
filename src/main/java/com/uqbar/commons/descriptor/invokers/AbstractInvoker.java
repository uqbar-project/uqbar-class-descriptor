package com.uqbar.commons.descriptor.invokers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.uqbar.commons.descriptor.visitors.ClassVisitor;
import com.uqbar.commons.descriptor.visitors.Ignore;


/**
 * Clase base para genear los invoker
 * 
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leonardo Gassman</a>
 * @see Invoker
 */
public abstract class AbstractInvoker implements Invoker {

	private Class type;
	private Object visitor;
	private Class[] defaultMethodParamsTypes;
	private String defaultMethodName;
	
	/**
	 * Constructor for AbstractInvoker
	 * @param type La clase que se esta describiendo
	 * @param visitor El visitor que escucha las notificaciones del descriptor
	 * @param defaultMethodName nombre del metodo default que se invocara si no existe un metodo especifico
	 * @param defaultMethodParamsTypes parametros del metodo default
	 */
	public AbstractInvoker(Class type, Object visitor, String defaultMethodName, Class[] defaultMethodParamsTypes) {
		this.type = type;
		this.visitor = visitor;
		this.defaultMethodParamsTypes = defaultMethodParamsTypes;
		this.defaultMethodName = defaultMethodName;
	}
	
	/**
	 * Constructor para un Invoker que no tiene metodo default
	 * @param type
	 * @param visitor
	 */
	public AbstractInvoker(Class type, Object visitor) {
		this(type, visitor, null, null);
	}
	
	/**
	 * @see Invoker#invoke()
	 */
	public void invoke() {
			boolean informed = false;
			for(Method method : this.getMethods()) {
				final Class<?>[] parameterTypes = method.getParameterTypes();
				//si el metodo tiene un mensaje para recibir la annotation de clase
				if (!this.ignore(method) && this.mustExecute(method, parameterTypes)) {
					try {
						invoke(method);
						informed = true;
					}
					catch (Exception e) {
						handleException(e);
					}
				}
			}
			if(!informed) {
				invokeDefaultMethod();
			}		

	}

	
	/**
	 *invoca el metodo default. El metodo default es uno definido en la interface {@link ClassVisitor}
	 *@see ClassVisitor
	 */
	protected void invokeDefaultMethod() {
		Method defaultMethod = getDefaultMethod();
		if(defaultMethod != null) {
			try {
				invoke(defaultMethod);
			}
			catch(Exception e) {
				this.handleException(e);
			}
		}
	}

	/**
	 * Los metodos marcados con la annotation ignore deben ser descartados como candidatos
	 * No deberia ser necesario sobreescribir este metodo. El metodo preparado para ser sobreescrito es 
	 * mustExecute
	 * 
	 * @see mustExecute
	 * @param method metodo que podria ser ignorado
	 * @return true si el metodo sera ignorado antes de llamar al mustExecute
	 */
	protected boolean ignore(Method method) {
		return method.isAnnotationPresent(Ignore.class);
	}

	/**
	 * Determina si este metodo es un caso particular que debe ser invocado por el invoker
	 * 
	 * @param method  method to evaluate
	 * @param parameterTypes parameters of this method. Es para evitar que todas las subclases necesiten hacer method.getParameterTypes()
	 * @return if this method must execute
	 */
	protected abstract boolean mustExecute(Method method, Class<?>[] parameterTypes);
	
	/**
	 * Este metodo es llamado si ninguno de los methods candidatos fue ejecutado
	 * Generalmente debe ejecutar un metodo de la interface {@link ClassVisitor}.
	 * @see ClassVisitor
	 */
	protected Method getDefaultMethod() throws SecurityException {
		Method method = null;
		try {
			if(this.defaultMethodName != null) {
				method = this.getVisitor().getClass().getMethod(this.defaultMethodName, this.defaultMethodParamsTypes);
			}
		}
		catch (NoSuchMethodException e) {
			//Si no existe el metodo default, significa que no le interesa a este visitor recibir este mensaje
		}
		return method;
	}

	/**
	 * Genera los parametros para la invocacion por reflection
	 * @param method 
	 * @return parametros para la invocacion de los metodos
	 */
	protected abstract Object[] createParameters(Method method);

	/**
	 * este metodo es llamado si ocurrio una excepcion al invocar un metodo por reflection.
	 * Generalmente es un traductor de una exception de reflection, a una RuntimeException.
	 * Puede ser sobreescrito si se quiere aumentar el nivel de detalle del mensaje de error 
	 * 
	 * @param e La excepcion ocurrida en la ejecucion del metodo. 
	 * @throws RuntimeException
	 */
	protected void handleException(Exception e) throws RuntimeException {
		Throwable cause;
		cause = (e instanceof InvocationTargetException) ? 	e.getCause() : e;
		throw new RuntimeException("Problems ocurred with the visitor: " + this.getVisitor() + ", type " + this.getType(), cause);
	}


	/**
	 * Obtener todos los methods que posiblemente deban invocarse
	 * No deberia necesitarse ser sobreescrito.
	 * @return metodos candidatos a ser invocados
	 */
	protected Method[] getMethods() {
		return this.getVisitor().getClass().getMethods();
	}
	

	/**
	 * Realiza la invocacion al metodo, generalmente no deberia ser necesario que se sobreescriba
	 * @param method
	 * @throws Exception
	 */
	protected void invoke(Method method) throws Exception {
		method.invoke(this.getVisitor(), this.createParameters(method));
	}
	
	
	/**
	 * @return El type para el cual se esta evaluando
	 */
	protected Class getType() {
		return this.type;
	}
	
	/**
	 * @return El visitor que esta trabajando
	 */
	protected Object getVisitor() {
		return this.visitor;
	}
}
