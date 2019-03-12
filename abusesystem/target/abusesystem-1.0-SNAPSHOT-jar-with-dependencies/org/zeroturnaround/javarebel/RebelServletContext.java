package org.zeroturnaround.javarebel;

import java.io.InputStream;
import java.net.URL;
import java.util.Set;

/**
 * Servlet context interface visible to JavaRebel agent.
 * <p>
 * <code>RebelServletContext</code> consists of the resource providing methods
 * defined in <code>javax.servlet.ServletContext</code>.
 * </p>
 * <p> 
 * Separate interface is required as the <code>javax.servlet.ServletContext</code>
 * interface is not loaded by the boot class loader.
 * <p>
 * All Servlet context instances must implement this interface
 * in order to be integrated with JavaRebel.
 * </p>
 * <p>
 * In practice a bytecode processor makes <code>javax.servlet.ServletContext</code>
 * to extend the given interface. So no particular Servlet context implementation
 * has to implement it explicitly.
 * </p>
 * 
 * @author Rein Raudjärv
 * 
 * @see ServletIntegration
 */
public interface RebelServletContext {

  /**
   * Returns a directory-like listing of all the paths to resources within the web
   * application whose longest sub-path matches the supplied path argument. Paths
   * indicating subdirectory paths end with a '/'. The returned paths are all relative
   * to the root of the web application and have a leading '/'. For example, for a web
   * application containing
   * 
   * /welcome.html
   * /catalog/index.html
   * /catalog/products.html
   * /catalog/offers/books.html
   * /catalog/offers/music.html
   * /customer/login.jsp
   * /WEB-INF/web.xml
   * /WEB-INF/classes/com.acme.OrderServlet.class,
   *
   * getResourcePaths("/") returns {"/welcome.html", "/catalog/", "/customer/", "/WEB-INF/"}
   * getResourcePaths("/catalog/") returns {"/catalog/index.html", "/catalog/products.html", "/catalog/offers/"}.
   * 
   * @param path partial path used to match the resources, which must start with a /
   * 
   * @return a Set containing the directory listing, or null if there are no resources
   * in the web application whose path begins with the supplied path.
   */
  public Set getResourcePaths(java.lang.String path);
  
  /**
   * Returns a URL to the resource that is mapped to a specified path. The path must begin
   * with a "/" and is interpreted as relative to the current context root. 
   * 
   * This method allows the servlet container to make a resource available to servlets from
   * any source. Resources can be located on a local or remote file system, in a database,
   * or in a .war file. 
   * 
   * The servlet container must implement the URL handlers and URLConnection objects that
   * are necessary to access the resource. 
   * 
   * This method returns null  if no resource is mapped to the pathname. 
   * 
   * Some containers may allow writing to the URL returned by this method using the methods
   * of the URL class. 
   * 
   * The resource content is returned directly, so be aware that requesting a .jsp page
   * returns the JSP source code. Use a RequestDispatcher instead to include results of an
   * execution. 
   * 
   * This method has a different purpose than java.lang.Class.getResource, which looks up
   * resources based on a class loader. This method does not use class loaders.
   * 
   * @param path a String specifying the path to the resource
   * 
   * @return the resource located at the named path, or null if there is no resource at that
   * path
   * 
   * @throws java.net.MalformedURLException if the pathname is not given in the correct form
   */
  public URL getResource(java.lang.String path) throws java.net.MalformedURLException;
  
  /**
   * Returns the resource located at the named path as an InputStream object. 
   * 
   * The data in the InputStream can be of any type or length. The path must be specified
   * according to the rules given in getResource. This method returns null if no resource exists
   * at the specified path. 
   * 
   * Meta-information such as content length and content type that is available via getResource
   * method is lost when using this method. 
   * 
   * The servlet container must implement the URL handlers and URLConnection objects necessary
   * to access the resource. 
   * 
   * This method is different from java.lang.Class.getResourceAsStream, which uses a class loader.
   * This method allows servlet containers to make a resource available to a servlet from any
   * location, without using a class loader.
   * 
   * @param name a String specifying the path to the resource
   * 
   * @return the InputStream returned to the servlet, or null if no resource exists at the specified path
   */
  public InputStream getResourceAsStream(String name);
  
  /**
   * Returns a String containing the real path for a given virtual path. For example, the path
   * "/index.html" returns the absolute file path on the server's filesystem would be served by
   * a request for "http://host/contextPath/index.html", where contextPath is the context path
   * of this ServletContext.. 
   * 
   * The real path returned will be in a form appropriate to the computer and operating system
   * on which the servlet container is running, including the proper path separators. This method
   * returns null  if the servlet container cannot translate the virtual path to a real path for
   * any reason (such as when the content is being made available from a .war archive).
   * 
   * @param path a String specifying a virtual path
   * @return a String specifying the real path, or null if the translation cannot be performed
   */
  public String getRealPath(java.lang.String path);
  
}
