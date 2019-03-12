package org.zeroturnaround.javarebel.integration.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.zeroturnaround.javarebel.ClassResourceSource;
import org.zeroturnaround.javarebel.LoggerFactory;
import org.zeroturnaround.javarebel.Resource;
import org.zeroturnaround.javarebel.ResourceSource;
import org.zeroturnaround.javarebel.integration.support.BaseClassResourceSource;
import org.zeroturnaround.javarebel.support.FileResource;
import org.zeroturnaround.javarebel.support.URLResource;

/**
 * Helper methods for finding resources.
 * 
 * @author Rein Raudjärv
 * 
 * @see Resource
 * @see ResourceSource
 * @see ClassLoader
 * @see URLClassLoader
 * @see URL
 */
public class ResourceUtil {

  private static final Method FIND_RESOURCE_METHOD  = getClassLoaderMethod("findResource", new Class[] {String.class});
  private static final Method FIND_RESOURCES_METHOD = getClassLoaderMethod("findResources", new Class[] {String.class});
  
  /**
   * Returns a <code>Method</code> object that reflects the specified
   * declared method of the {@link ClassLoader}.
   *  
   * @param methodName the name of the method.
   * @param paramTypes the parameter array.
   * @return the <code>Method</code> object for the method of the {@link ClassLoader}
   * matching the specified name and parameters.
   */
  private static Method getClassLoaderMethod(String methodName, Class[] paramTypes) {
    Method result = null;
    try {
      result = ClassLoader.class.getDeclaredMethod(methodName, paramTypes);
      result.setAccessible(true);
    } 
    catch (NoSuchMethodException e) {
      LoggerFactory.getInstance().errorEcho(e);
    }
    return result;
  }
  
  /**
   * Invokes the <code>findResource</code> method of the given class loader.
   * 
   * <p>In case of {@link URLClassLoader} the underlying public method is invoked directly.
   * Otherwise the protected method of the {@link ClassLoader} is invoked through the
   * reflection.</p>
   * 
   * @param classloader the class loader object.
   * @param name the name of the resource
   * (parameter passed to the underlying <code>findResource</code> method)
   * @return a <code>URL</code> for the resource, or <code>null</code>
   * if the resource could not be found.
   * 
   * @see URLClassLoader#findResource(String)
   */
  public static URL findResource(ClassLoader classloader, String name) {
    URL url;
    if (classloader instanceof URLClassLoader)
      url = ((URLClassLoader) classloader).findResource(name);
    else {
      try {
        url = (URL) FIND_RESOURCE_METHOD.invoke(classloader, new Object[] {name});
      } catch (IllegalArgumentException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      } catch (InvocationTargetException e) {
        throw new RuntimeException(e.getTargetException());
      }
    }
    return url;
  }
  
  /**
   * Invokes the <code>findResources</code> method of the given class loader.
   * 
   * <p>In case of {@link URLClassLoader} the underlying public method is invoked directly.
   * Otherwise the protected method of the {@link ClassLoader} is invoked through the
   * reflection.</p>
   * 
   * <p>The underlying <code>Enumeration</code> is converted to an array of <code>URL</code>s.
   * 
   * @param classloader the class loader object.
   * @param name the resource name.
   * (parameter passed to the underlying <code>findResources</code> method)
   * @return <code>URL<code>s for the resources, or <code>null</code>
   * if no resource could be found.
   * 
   * @see URLClassLoader#findResources(String)
   * @see #findResources(URLClassLoader, String)
   * @see #findResource(ClassLoader, String)
   * @see #toURLs(Enumeration)
   */
  public static URL[] findResources(ClassLoader classloader, String name) {
    Enumeration en;
    if (classloader instanceof URLClassLoader)
      try {
        en = ((URLClassLoader) classloader).findResources(name);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    else {
      try {
        en = (Enumeration) FIND_RESOURCES_METHOD.invoke(classloader, new Object[] {name});
      } catch (IllegalArgumentException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      } catch (InvocationTargetException e) {
        throw new RuntimeException(e.getTargetException());
      }
    }
    
    if (en == null || !en.hasMoreElements())
      return null;
    
    return toURLs(en);
  }
  
  /**
   * Invokes the <code>findResources</code> method of the given URL class loader.
   * 
   * <p>The underlying public method is invoked directly and
   * the <code>Enumeration</code> is converted to an array of <code>URL</code>s.
   * 
   * @param classloader the class loader object.
   * @param name the resource name.
   * (parameter passed to the underlying <code>findResources</code> method)
   * @return <code>URL<code>s for the resources, or <code>null</code>
   * if no resource could be found.
   * 
   * @see URLClassLoader#findResources(String)
   * @see #findResources(ClassLoader, String)
   * @see #findResource(ClassLoader, String)
   * @see #toURLs(Enumeration)
   */
  public static URL[] findResources(URLClassLoader classloader, String name) {
    Enumeration en;
    try {
      en = classloader.findResources(name);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    
    if (en == null || !en.hasMoreElements())
      return null;
    
    return toURLs(en);
  }
  
  /**
   * Converts the <code>Enumeration</code> to an array of <code>URL</code>s. 
   * 
   * @param en an <code>Enumeration</code> of <code>URL</code>s (not <code>null</code>).
   * @return an array of <code>URL</code>s.
   * 
   * @see #toEnumeration(URL[])
   */
  public static URL[] toURLs(Enumeration en) {
    List urls = new ArrayList();
    while (en.hasMoreElements())
      urls.add((URL) en.nextElement());
    
    return (URL[]) urls.toArray(new URL[urls.size()]);
  }
  
  /**
   * Converts the array of <code>URL</code>s to an <code>Enumeration</code>.
   * 
   * @param urls an array of <code>URL</code>s (not <code>null</code>).
   * @return an <code>Enumeration</code> of <code>URL</code>s
   * 
   * @see #toURLs(Enumeration)
   */
  public static Enumeration toEnumeration(URL[] urls) {
	  return new Vector(Arrays.asList(urls)).elements();
  }
  
  /**
   * Finds a class resource using a resource finder.
   * 
   * <p>This is used in {@link BaseClassResourceSource}
   * which is the base implementation of {@link ClassResourceSource}.</p>
   * 
   * <p>The class name is converted to a resource name
   * by replacing all dots with slashes
   * and adding <code>.class</code> to the end.</p>
   * 
   * <p>As most containers do not like when JavaRebel instruments their code
   * <code>null</code> is returned for those classes.</p> 
   * 
   * @param source the resource finder.
   * @param className the name of the class.
   * @return {@link Resource} that corresponds to the given class.
   * 
   * @see ResourceSource
   * @see ClassResourceSource
   * @see BaseClassResourceSource
   */
  public static Resource getClassResource(ResourceSource source, String className) {
    // Most containers don't like when JavaRebel instruments their code
    if (className.startsWith("weblogic.")
        || className.startsWith("com.evermind.")
        || className.startsWith("oracle.")
        || className.startsWith("com.bea.")
        || className.startsWith("org.eclipse.osgi.")
        || className.startsWith("com.ibm.websphere")
        || className.startsWith("org.apache.jsp."))
      return null;
    
    return source.getLocalResource(className.replace('.', '/') + ".class");
  }

  /**
   * Creates a one-element array of <code>Resource</code>s.
   * 
   * @param resource the <code>Resource</code> element.
   * @return a one-element array with the given element
   * or <code>null</code> if the element was <code>null</code>.
   */
  public static Resource[] asArray(Resource resource) {
    if (resource == null)
      return null;
    return new Resource[] { resource };
  }
  
  /**
   * Finds the resource with the given name using one plus many
   * <code>ResourceSource</code>s.
   * 
   * <p>First {@link Resource} found will be returned.
   * If none of the <code>ResourceSource</code>s could find anything
   * <code>null</code> will be returned.
   * 
   * <p>E.g. this can be used to find resources from <code>WEB-INF/classes</code>
   * plus the libraries in <code>WEB-INF/lib</code> directory.
   * 
   * @param aSource the first resource finder.
   * @param otherSources the rest of the resource finders. 
   * @param name the name of the resource.
   * @return found resource or <code>null</code> if nothing found.
   * 
   * @see #getResources(ResourceSource, Collection, String)
   * @see ResourceSource#getLocalResource(String)
   */
  public static Resource getResource(ResourceSource aSource, Collection otherSources, String name) {
    if (aSource != null) {
      Resource result = aSource.getLocalResource(name);
      if (result != null)
        return result;
    }
    
    if (otherSources != null) {
      for (Iterator it = otherSources.iterator(); it.hasNext();) {
        ResourceSource rs = (ResourceSource) it.next();
        Resource result = rs.getLocalResource(name);
        if (result != null)
          return result;
      }
    }
    
    return null;
  }
    
  /**
   * Finds all resource with the given name using one plus many
   * <code>ResourceSource</code>s.
   * 
   * <p>All {@link Resource}s will be returned as an array.
   * If none of the <code>ResourceSource</code>s could find anything
   * <code>null</code> will be returned.
   * 
   * <p>E.g. this can be used to find resources from <code>WEB-INF/classes</code>
   * plus the libraries in <code>WEB-INF/lib</code> directory.
   * 
   * @param aSource the first resource finder.
   * @param otherSources the rest of the resource finders. 
   * @param name the name of the resource.
   * @return found resources or <code>null</code> if nothing found.
   * 
   * @see #getResource(ResourceSource, Collection, String)
   * @see ResourceSource#getLocalResources(String)
   */
  public static Resource[] getResources(ResourceSource aSource, Collection otherSources, String name) {
    List result = new ArrayList();
    
    if (aSource != null) {
      Resource[] resources = aSource.getLocalResources(name);
      if (resources != null)
        result.addAll(Arrays.asList(resources));
    }
    
    if (otherSources != null) {
      for (Iterator it = otherSources.iterator(); it.hasNext();) {
        Resource[] resources = ((ResourceSource) it.next()).getLocalResources(name);
        if (resources != null)
          result.addAll(Arrays.asList(resources));
      }
    }
    
    if (result.isEmpty())
      return null;
    
    return (Resource[]) result.toArray(new Resource[result.size()]);
  }

  /**
   * Creates a new <code>Resource</code> object based on the given <code>URL</code>.
   * 
   * <p>If a <code>file</code> or <code>vfsfile</code> protocol is used
   * a {@link FileResource} will be created.
   * Otherwise a new {@link URLResource} instance will be returned.
   * 
   * @param url the resource <code>URL</code> (not <code>null</code>).
   * @return new <code>Resource</code> object based on the given <code>URL</code>.
   */
  public static Resource asResource(URL url) {
    if (url.getProtocol().equals("file") || url.getProtocol().equals("vfsfile") ) {
      return new FileResource(url);
    }
    else {
      return new URLResource(url);
    }
  }

  public static InputStream asInputStream(URL url) {
    if (url == null) return null;
    
    try {
      return url.openStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
