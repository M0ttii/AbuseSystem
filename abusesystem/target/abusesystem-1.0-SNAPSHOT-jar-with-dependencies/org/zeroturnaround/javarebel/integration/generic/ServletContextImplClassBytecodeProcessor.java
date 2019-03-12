package org.zeroturnaround.javarebel.integration.generic;

import org.zeroturnaround.bundled.javassist.ClassPool;
import org.zeroturnaround.bundled.javassist.CtClass;
import org.zeroturnaround.bundled.javassist.CtConstructor;
import org.zeroturnaround.javarebel.RebelServletContext;
import org.zeroturnaround.javarebel.ServletIntegration;

/**
 * Bytecode processor for the default Servlet context integration.
 * 
 * <p>
 * Transforms any <code>javax.servlet.ServletContext</code> implementation
 * by registering servlet context instance (in the end of any constructor)
 * and delegating all resource providing methods to JavaRebel.
 * </p>
 * <p>
 * This can be used with servlet context implementations which are ready to provide resources
 * already after the constructor call (no additional initialization method will be called afterwards).
 * 
 * @author Rein Raudjärv
 * 
 * @see ServletIntegration
 * @see RebelServletContext 
 * @see BaseServletContextImplClassBytecodeProcessor
 */
public class ServletContextImplClassBytecodeProcessor extends BaseServletContextImplClassBytecodeProcessor {

  public void process(ClassPool cp, ClassLoader cl, CtClass ctClass) throws Exception {
    CtConstructor[] constructors = ctClass.getConstructors();
    for (int i = 0; i < constructors.length; i++) {
      CtConstructor constructor = constructors[i];
      constructor.insertAfter(
          "org.zeroturnaround.javarebel.ServletIntegrationFactory.getInstance()" +
          "  .registerServletContext((" + RebelServletContext.class.getName() + ") $0);");
    }

    super.process(cp, cl, ctClass);
  }
}
