package org.zeroturnaround.javarebel.integration.generic;

import java.net.URL;
import org.zeroturnaround.javarebel.Resource;
import org.zeroturnaround.javarebel.integration.support.BaseClassResourceSource;
import org.zeroturnaround.javarebel.integration.util.ResourceUtil;

/**
 * Default class-to-resource resolution strategy.
 * 
 * <p>The <code>findResource</code> and <code>findResources</code> methods
 * of the given class loader are used.
 * 
 * @see ClassLoader
 * @see ResourceUtil#findResource(ClassLoader, String)
 * @see ResourceUtil#findResources(ClassLoader, String)
 */
public class FindResourceClassResourceSource extends BaseClassResourceSource {  
  private ClassLoader classloader;
  
  public FindResourceClassResourceSource(ClassLoader classloader) {
    this.classloader = classloader;
  }
  
  public Resource getLocalResource(String name) {
    URL url = ResourceUtil.findResource(classloader, name);
    
    if (url == null)
      return null;
    
    return ResourceUtil.asResource(url);
  }

  public Resource[] getLocalResources(String name) {
    URL[] urls = ResourceUtil.findResources(classloader, name);
    
    if (urls == null)
      return null;
    
    Resource[] result = new Resource[urls.length];
    for (int i = 0; i < urls.length; i++) {
      result[i] = ResourceUtil.asResource(urls[i]);
    }
    return result;
  }
}