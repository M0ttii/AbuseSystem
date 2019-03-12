package org.zeroturnaround.javarebel.integration.support;

import org.zeroturnaround.javarebel.ClassResourceSource;
import org.zeroturnaround.javarebel.Resource;
import org.zeroturnaround.javarebel.integration.util.ResourceUtil;


/**
 * Base class-to-resource resolution strategy.
 * 
 * <p>This provides the default implementation for finding class resources
 * leaving general resource finding methods still undefined.</p>   

 * @author Rein Raudjärv
 * 
 * @see ClassResourceSource
 * @see ResourceUtil#getClassResource(org.zeroturnaround.javarebel.ResourceSource, String)
 */
public abstract class BaseClassResourceSource implements ClassResourceSource {

  /**
   * Finds the class resource with the given name.
   * 
   * <p>The implementation of
   * {@link ResourceUtil#getClassResource(org.zeroturnaround.javarebel.ResourceSource, String)}
   * is used here.
   * 
   * @param className the name of the class.
   * 
   * @return found class resource or <code>null</code> if nothing found.
   *
   * @see ResourceUtil#getClassResource(org.zeroturnaround.javarebel.ResourceSource, String)
   */
  public Resource getClassResource(String className) {
    return ResourceUtil.getClassResource(this, className);
  }

}
