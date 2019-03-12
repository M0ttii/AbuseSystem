/**
 * Copyright 2007 ZeroTurnaround.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package org.zeroturnaround.javarebel;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

/**
 * <p>Provides hooks for integrating JavaRebel with custom Servlet containers.
 * To use it acquire an instance from {@link ServletIntegrationFactory}</p>
 * 
 * <p>A typical (Servlet context) integration will look like this:</p>
 * 
 * <pre>
 * class MyServletContext implements ServletContext, RebelServletContext {
 * 
 *   ServletIntegration integration;
 * 
 *   public MyServletContext() {
 *     ...
 *     integration = ServletIntegrationFactory.getInstance();
 *     integration.registerServletContext(this);
 *   }
 *   ...
 *   public String getRealPath(String path) {
 *     if (integration.isResourceReplaced(this, path))
 *       return integration.getRealPath(this, path);
 *     ...
 *   }
 *   
 *   public URL getResource(String path) throws MalformedURLException {
 *     if (integration.isResourceReplaced(this, path))
 *       return integration.getResource(this, path);
 *     ...
 *   }
 *   
 *   public InputStream getResourceAsStream(String path) {
 *     if (integration.isResourceReplaced(this, path))
 *       return integration.getResourceAsStream(this, path);
 *     ...
 *   }
 *   
 *   public Set getResourcePaths(String path) {
 *     if (integration.isResourceReplaced(this, path))
 *       return integration.getResourcePaths(this, path);
 *     ...
 *   }
 * }
 * </pre>
 * 
 * @author Rein Raudjärv
 * 
 * @see ServletIntegrationFactory
 * @see RebelServletContext
 * @see Integration
 * @see Reloader
 * @see Logger
 * @see Plugin
 */
public interface ServletIntegration {
  
  /**
   * Registers a <code>ServletContext</code> with JavaRebel.
   * It is necessary to do this before any calls to the rest of the methods of <code>ServletIntegration</code>,
   * the best place is usually the constructor.
   * The custom <code>ServletContext</code> must implement the <code>RebelServletContext</code>,
   * The latter can be easily added by the corresponding <code>ClassBytecodeProcessor</code>.
   * 
   * @param sc The custom Servlet context being integrated.
   */
  public void registerServletContext(RebelServletContext sc);
  
  /**
   * Unregisters a <code>ServletContext</code> from JavaRebel.
   * It is recommended to do this in case the corresponding <code>ServletContext</code> is destroyed
   * (e.g. the web application is undeployed).
   * 
   * @param sc The custom Servlet context being integrated.
   */
  public void unregisterServletContext(RebelServletContext sc);
  
  /**
   * Returns <code>true</code> if all resources of the given Servlet Context are managed by JavaRebel.
   * <p>
   * All Servlet Context resources are managed by JavaRebel if the Servlet Context has been registered
   * and a valid <code>rebel.xml</code> has been found which defines the new resource locations.
   * 
   * @param sc The custom Servlet context being integrated.
   * @return <code>true</code> if all resources of the given Servlet Context are managed by JavaRebel.
   */
  public boolean hasReplacedResources(RebelServletContext sc);
  
  /**
   * Returns <code>true</code> if the given resource is managed by JavaRebel. 
   * <p>
   * In that case resources can be resolved using the corresponding methods of the <code>ServletIntegration</code>.
   * If any of these methods returns a <code>null</code> it must be returned by the <code>ServletContext</code> as well.
   * 
   * @param sc The custom Servlet context being integrated.
   * @param path path of the resource.
   * @return <code>true</code> if the given resource is managed by JavaRebel.
   */
  public boolean isResourceReplaced(RebelServletContext sc, String path);
  
  /**
   * Returns the managed resource located at the named path as an InputStream object.
   * 
   * <p>A typical usage will look like this:</p>
   * 
   * <pre>
   * class MyServletContext implements ServletContext, RebelServletContext {
   *   ...
   *   public InputStream getResourceAsStream(String path) throws MalformedURLException {
   *     ServletIntegration integration = ServletIntegrationFactory.getInstance();
   *     if (integration.isResourceReplaced(this, path))
   *       return integration.getResourceAsStream(this, path);
   *     ...
   *   }
   *   ...
   * }
   * </pre>
   * 
   * <p>The Servlet context instance must be first registered with JavaRebel.</p>
   * 
   * @param sc The custom Servlet context being integrated.
   * 
   * @param path a String specifying the path to the resource
   * 
   * @return the resource located at the named path as an InputStream object,
   * or <code>null</code> if there is no resource at that path
   * 
   * @see #registerServletContext(RebelServletContext)
   */
  public InputStream getResourceAsStream(RebelServletContext sc, String path);
  
  /**
   * Returns a URL to the managed resource that is mapped to a specified path.
   * 
   * <p>A typical usage will look like this:</p>
   * 
   * <pre>
   * class MyServletContext implements ServletContext, RebelServletContext {
   *   ...
   *   public URL getResource(String path) throws MalformedURLException {
   *     ServletIntegration integration = ServletIntegrationFactory.getInstance();
   *     if (integration.isResourceReplaced(this, path))
   *       return integration.getResource(this, path);
   *     ...
   *   }
   *   ...
   * }
   * </pre>
   * 
   * <p>The Servlet context instance must be first registered with JavaRebel.</p>
   * 
   * @param sc The custom Servlet context being integrated.
   * 
   * @param path a String specifying the path to the resource
   * 
   * @return the resource located at the named path,
   * or <code>null</code> if there is no resource at that path
   * 
   * @throws java.net.MalformedURLException if the pathname is not given in the correct form
   * 
   * @see #registerServletContext(RebelServletContext)
   */
  public URL getResource(RebelServletContext sc, String path) throws MalformedURLException;
  
  /**
   * Returns a String containing the managed real path for a given virtual path.
   * 
   * <p>A typical usage will look like this:</p>
   * 
   * <pre>
   * class MyServletContext implements ServletContext, RebelServletContext {
   *   ...
   *   public String getRealPath(String path) throws MalformedURLException {
   *     ServletIntegration integration = ServletIntegrationFactory.getInstance();
   *     if (integration.isResourceReplaced(this, path))
   *       return integration.getRealPath(this, path);
   *     ...
   *   }
   *   ...
   * }
   * </pre>
   * 
   * <p>The Servlet context instance must be first registered with JavaRebel.</p>
   * 
   * @param sc The custom Servlet context being integrated.
   * 
   * @param path a String specifying the path to the resource
   * 
   * @return a String specifying the real path,
   * or <code>null</code> if the translation cannot be performed
   * 
   * @see #registerServletContext(RebelServletContext)
   */
  public String getRealPath(RebelServletContext sc, String path);
  
  /**
   * Returns a directory-like listing of all the paths to managed resources within the
   * web application whose longest sub-path matches the supplied path argument.
   * 
   * <pre>
   * class MyServletContext implements ServletContext, RebelServletContext {
   *   ...
   *   public Set getResourcePaths(String path) throws MalformedURLException {
   *     ServletIntegration integration = ServletIntegrationFactory.getInstance();
   *     if (integration.isResourceReplaced(this, path))
   *       return integration.getResourcePaths(this, path);
   *     ...
   *   }
   *   ...
   * }
   * </pre>
   * 
   * <p>The Servlet context instance must be first registered with JavaRebel.</p>
   * 
   * @param sc The custom Servlet context being integrated.
   * 
   * @param path a String specifying the path to the resource
   * 
   * @return a Set containing the directory listing,
   * or <code>null</code> if there are no resources in the web application
   * whose path begins with the supplied path.
   * 
   * @see #registerServletContext(RebelServletContext)
   */
  public Set getResourcePaths(RebelServletContext sc, String path);
  
  /**
   * Returns the original resource located at the named path as an InputStream object.
   * 
   * <p>
   * This is used to get the original result unaltered by JavaRebel.
   * </p>
   * <p>
   * The corresponding Servlet context method will be invoked such that
   * {@link #isResourceReplaced(RebelServletContext, String)} returns <code>false</code>.
   * </p> 
   * 
   * @param path a String specifying the path to the resource
   * 
   * @return the resource located at the named path as an InputStream object,
   * or <code>null</code> if there is no resource at that path
   */
  public InputStream getTransparentResourceAsStream(RebelServletContext sc, String path);
  
  /**
   * Returns a URL to the original resource that is mapped to a specified path.
   * 
   * <p>
   * This is used to get the original result unaltered by JavaRebel.
   * </p>
   * <p>
   * The corresponding Servlet context method will be invoked such that
   * {@link #isResourceReplaced(RebelServletContext, String)} returns <code>false</code>.
   * </p> 
   * 
   * @param sc The custom Servlet context being integrated.
   * 
   * @param path a String specifying the path to the resource
   * 
   * @return the resource located at the named path,
   * or <code>null</code> if there is no resource at that path
   * 
   * @throws java.net.MalformedURLException if the pathname is not given in the correct form
   */  
  public URL getTransparentResource(RebelServletContext sc, String path) throws MalformedURLException;
  
  /**
   * Returns a String containing the original real path for a given virtual path.
   * 
   * <p>
   * This is used to get the original result unaltered by JavaRebel.
   * </p>
   * <p>
   * The corresponding Servlet context method will be invoked such that
   * {@link #isResourceReplaced(RebelServletContext, String)} returns <code>false</code>.
   * </p>
   * 
   * @param sc The custom Servlet context being integrated.
   * 
   * @param path a String specifying the path to the resource
   * 
   * @return a String specifying the real path,
   * or <code>null</code> if the translation cannot be performed
   */
  public String getTransparentRealPath(RebelServletContext sc, String path);
  
  /**
   * Returns a directory-like listing of all the paths to resources within the
   * web application whose longest sub-path matches the supplied path argument.
   * 
   * <p>
   * This is used to get the original result unaltered by JavaRebel.
   * </p>
   * <p>
   * The corresponding Servlet context method will be invoked such that
   * {@link #isResourceReplaced(RebelServletContext, String)} returns <code>false</code>.
   * </p>
   * 
   * @param sc The custom Servlet context being integrated.
   * 
   * @param path a String specifying the path to the resource
   * 
   * @return a Set containing the directory listing, or <code>null</code> if there are no
   * resources in the web application whose path begins with the supplied path.
   */
  public Set getTransparentResourcePaths(RebelServletContext sc, String path);

}