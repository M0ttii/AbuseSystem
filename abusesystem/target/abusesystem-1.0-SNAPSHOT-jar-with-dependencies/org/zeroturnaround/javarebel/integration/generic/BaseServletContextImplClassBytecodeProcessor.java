package org.zeroturnaround.javarebel.integration.generic;

import org.zeroturnaround.bundled.javassist.ClassPool;
import org.zeroturnaround.bundled.javassist.CtClass;
import org.zeroturnaround.bundled.javassist.CtMethod;
import org.zeroturnaround.javarebel.RebelServletContext;
import org.zeroturnaround.javarebel.ServletIntegration;
import org.zeroturnaround.javarebel.integration.support.JavassistClassBytecodeProcessor;

/**
 * Bytecode processor for delegating Servlet context's resource related methods.
 * 
 * <p>The servlet context instance must be first registered with JavaRebel.
 * Otherwise the resource providing is unaffected.</p>
 * 
 * @author Rein Raudjärv
 * 
 * @see ServletIntegration
 * @see RebelServletContext
 * @see ServletContextImplClassBytecodeProcessor
 */
public class BaseServletContextImplClassBytecodeProcessor extends JavassistClassBytecodeProcessor {

  public void process(ClassPool cp, ClassLoader cl, CtClass ctClass) throws Exception {
    CtMethod m = ctClass.getMethod("getResource", "(Ljava/lang/String;)Ljava/net/URL;");
    m.insertBefore(
        "{" + 
        "  if ($0 instanceof " + RebelServletContext.class.getName() + ") {" +
        "    org.zeroturnaround.javarebel.ServletIntegration integration = org.zeroturnaround.javarebel.ServletIntegrationFactory.getInstance();" +
        "    org.zeroturnaround.javarebel.RebelServletContext servletContext = (" + RebelServletContext.class.getName() + ") $0;" +
        "    if (integration.isResourceReplaced(servletContext, $1))" +
        "      return integration.getResource(servletContext, $1);" + 
        "  }" +
        "}");
    
    m = ctClass.getMethod("getResourceAsStream", "(Ljava/lang/String;)Ljava/io/InputStream;");
    m.insertBefore(
        "{" + 
        "  if ($0 instanceof " + RebelServletContext.class.getName() + ") {" +
        "    org.zeroturnaround.javarebel.ServletIntegration integration = org.zeroturnaround.javarebel.ServletIntegrationFactory.getInstance();" +
        "    org.zeroturnaround.javarebel.RebelServletContext servletContext = (" + RebelServletContext.class.getName() + ") $0;" +
        "    if (integration.isResourceReplaced(servletContext, $1))" +
        "      return integration.getResourceAsStream(servletContext, $1);" + 
        "  }" +
        "}");
    
    m = ctClass.getMethod("getResourcePaths", "(Ljava/lang/String;)Ljava/util/Set;");
    m.insertBefore(
        "{" + 
        "  if ($0 instanceof " + RebelServletContext.class.getName() + ") {" +
        "    org.zeroturnaround.javarebel.ServletIntegration integration = org.zeroturnaround.javarebel.ServletIntegrationFactory.getInstance();" +
        "    org.zeroturnaround.javarebel.RebelServletContext servletContext = (" + RebelServletContext.class.getName() + ") $0;" +
        "    if (integration.isResourceReplaced(servletContext, $1))" +
        "      return integration.getResourcePaths(servletContext, $1);" + 
        "  }" +
        "}");
    
    m = ctClass.getMethod("getRealPath", "(Ljava/lang/String;)Ljava/lang/String;");
    m.insertBefore(
        "{" + 
        "  if ($0 instanceof " + RebelServletContext.class.getName() + ") {" +
        "    org.zeroturnaround.javarebel.ServletIntegration integration = org.zeroturnaround.javarebel.ServletIntegrationFactory.getInstance();" +
        "    org.zeroturnaround.javarebel.RebelServletContext servletContext = (" + RebelServletContext.class.getName() + ") $0;" +
        "    if (integration.isResourceReplaced(servletContext, $1))" +
        "      return integration.getRealPath(servletContext, $1);" + 
        "  }" +
        "}");
  }
}
