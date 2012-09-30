package com.uqbar.commons.descriptor;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 
 * @author lgassman
 */
public class ClassDescriptorAllTest {

	public static Test suite() {
		TestSuite suite = new TestSuite("Uqbar-commons-classDescriptor Tests");
		suite.addTestSuite(FieldInvokerTestCase.class);		
		suite.addTestSuite(FieldAnnotationInvokerTestCase.class);		
		suite.addTestSuite(ClassAnnotationInvokerTestCase.class);		
		suite.addTestSuite(MethodAnnotationInvokerTestCase.class);		
		suite.addTestSuite(MethodInvokerTestCase.class);		
		suite.addTestSuite(MethodParameterAnnotationInvokerTestCase.class);		
		suite.addTestSuite(ConstructorInvokerTestCase.class);		
		suite.addTestSuite(ConstructorAnnotationInvokerTestCase.class);		
		suite.addTestSuite(ConstructorParameterAnnotationInvokerTestCase.class);	
		suite.addTestSuite(TypeInvokerTestCase.class);
		suite.addTestSuite(NonDefaultAnnotationValueInvokerTestCase.class);
		suite.addTestSuite(MethodAnnotationValueInvokerTestCase.class);
		suite.addTestSuite(SpecificAnnotationValueInvokerTestCase.class);
		suite.addTestSuite(TemplateMethodsTestCase.class);
		return suite;
	}


}
