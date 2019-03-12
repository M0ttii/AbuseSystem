package org.zeroturnaround.javarebel.integration.generic;

import org.zeroturnaround.bundled.javassist.ClassPool;
import org.zeroturnaround.bundled.javassist.CtClass;
import org.zeroturnaround.bundled.javassist.CtConstructor;
import org.zeroturnaround.bundled.javassist.CtMethod;
import org.zeroturnaround.bundled.javassist.NotFoundException;
import org.zeroturnaround.javarebel.Integration;
import org.zeroturnaround.javarebel.integration.support.JavassistClassBytecodeProcessor;

/**
 * Bytecode processor for the default class loader integration.
 * 
 * <p>The given class loader is registered with JavaRebel in the end of any constructor.</p>
 * 
 * <p><code>findClass</code>, <code>findResource</code> and <code>findResources</code> methods
 * are delegated to JavaRebel.
 * 
 * @see Integration
 * @see FindResourceClassResourceSource
 */
public class FindClassClassBytecodeProcessor extends JavassistClassBytecodeProcessor {

  public void process(ClassPool cp, ClassLoader cl, CtClass ctClass) throws Exception {
    CtConstructor[] constructors = ctClass.getConstructors();
    for (int i = 0; i < constructors.length; i++) {
      CtConstructor constructor = constructors[i];
      constructor.insertAfter(
          "org.zeroturnaround.javarebel.IntegrationFactory.getInstance()" +
          "  .registerClassLoader($0, " +
          "    new org.zeroturnaround.javarebel.integration.generic.FindResourceClassResourceSource($0));");
    }

    CtMethod m = ctClass.getMethod("findClass", "(Ljava/lang/String;)Ljava/lang/Class;");
    m.insertBefore(
        "{ synchronized ($0) {" + 
        "    Class result =" + 
        "      findLoadedClass($1);" + 
        "    if (result != null)" + 
        "      return result;" + 
        "    result = " + 
        "      org.zeroturnaround.javarebel.IntegrationFactory.getInstance()" + 
        "        .findReloadableClass($0, $1);" + 
        "    if (result != null)" + 
        "      return result;" + 
        "}}");

    try {
      m = ctClass.getDeclaredMethod("findResource", cp.get(new String[] {String.class.getName()}));
      m.insertBefore(
          "{" + 
          "  org.zeroturnaround.javarebel.Integration integration = org.zeroturnaround.javarebel.IntegrationFactory.getInstance();" +
          "  if (integration.isResourceReplaced($0, $1))" +
          "    return integration.findResource($0, $1);" + 
          "}");
    }
    catch (NotFoundException e) {
      // skip
    }

    try {
      m = ctClass.getDeclaredMethod("findResources", cp.get(new String[] {String.class.getName()}));
      m.insertBefore(
          "{" + 
          "  org.zeroturnaround.javarebel.Integration integration = org.zeroturnaround.javarebel.IntegrationFactory.getInstance();" +
          "  if (integration.isResourceReplaced($0, $1))" +
          "    return integration.findResources($0, $1);" + 
          "}");
    }
    catch (NotFoundException e) {
      // skip
    }
  }
}
