package org.zeroturnaround.javarebel.integration.support;

import java.io.InputStream;
import java.net.URL;
import org.zeroturnaround.bundled.javassist.ByteArrayClassPath;
import org.zeroturnaround.bundled.javassist.ClassPath;
import org.zeroturnaround.bundled.javassist.ClassPool;
import org.zeroturnaround.bundled.javassist.CtClass;
import org.zeroturnaround.bundled.javassist.LoaderClassPath;
import org.zeroturnaround.bundled.javassist.NotFoundException;
import org.zeroturnaround.javarebel.ClassBytecodeProcessor;
import org.zeroturnaround.javarebel.LoggerFactory;

/**
 * Javassist-based bytecode processor callback.
 * 
 * <p>Any sub class is required to implement the
 * {@link #process(ClassPool, ClassLoader, CtClass)} method.
 */
public abstract class JavassistClassBytecodeProcessor implements ClassBytecodeProcessor {
  
  public final byte[] process(ClassLoader cl, String classname, byte[] bytecode) {
    try {
      classname = classname.replace('/', '.');
      ClassPool cp = new ClassPool();
      //cp.appendClassPath(new RebelClassPath());
      cp.appendClassPath(new ByteArrayClassPath(classname, bytecode));
      cp.appendSystemPath();
      cp.appendClassPath(new LoaderClassPath(cl));
      CtClass cc = cp.get(classname);
      cc.defrost();
      LoggerFactory.getInstance().log("Found Javassist class " + cc.getURL() + " for " + classname);
      process(cp, cl, cc);
      return cc.toBytecode();
    } 
    catch (Throwable e) {
      LoggerFactory.getInstance().errorEcho(e);
      return bytecode;
    }
  }
  
  /**
   * Modifies the class to be loaded.
   *
   * @param cp the container of <code>CtClass</code> objects.
   * @param cl the class loader loading the given class.
   * @param ctClass the class representation.
   */
  public abstract void process(ClassPool cp, ClassLoader cl, CtClass ctClass) throws Exception;
  
  
  private static class RebelClassPath implements ClassPath {
    
    public URL find(String arg0) {
      LoggerFactory.getInstance().log("Searching for Javassist resource " + arg0);
      return null;
    }
    
    public InputStream openClassfile(String arg0) throws NotFoundException {
      LoggerFactory.getInstance().log("Searching for Javassist class " + arg0);
      throw new NotFoundException(arg0);
    }
    
    public void close() {
    }
  }
}
