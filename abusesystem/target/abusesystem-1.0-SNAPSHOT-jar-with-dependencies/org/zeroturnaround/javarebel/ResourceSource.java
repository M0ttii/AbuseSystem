package org.zeroturnaround.javarebel;


/**
 * A resource finder.
 * <p>
 * There can exist several resources with the same name.
 * 
 * @author Rein Raudjärv
 * 
 * @see ClassResourceSource
 */
public interface ResourceSource {
  
  /**
   * Finds the resource with the given name.
   * 
   * @param name The name of a resource is a '/'-separated path name that identifies the resource.
   * 
   * @return found resource or <code>null</code> if nothing found.
   */
  public Resource getLocalResource(String name);
  
  /**
   * Finds all the resources with the given name.
   * 
   * @param name The name of a resource is a '/'-separated path name that identifies the resource.
   *  
   * @return found resources or <code>null</code> if nothing found.
   */
  public Resource[] getLocalResources(String name);
  
}
