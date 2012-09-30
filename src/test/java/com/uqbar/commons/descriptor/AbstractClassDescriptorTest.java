package com.uqbar.commons.descriptor;

import junit.framework.TestCase;

/**
 * 
 * @author <a href=mailto:lgassman@andinasoftware.com.ar>Leo Gassman</a>
 *
 */
public abstract class AbstractClassDescriptorTest extends TestCase {

	
	public void testDescriptor() {
		new ClassDescriptor().describe(ClassForDescription.class, this);
		this.assertions();
	}

	protected abstract void assertions();

}
