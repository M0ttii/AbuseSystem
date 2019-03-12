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

import java.net.URL;
import java.util.Enumeration;

/**
 * <p>Provides hooks for integrating JavaRebel with custom containers, frameworks and classloaders.
 * To use it acquire an instance from {@link IntegrationFactory}</p>
 * 
 * <p>A typical (classloader) integration will look like this:</p>
 * 
 * <pre>
 * class MyClassLoader extends URLClassLoader {
 *   public MyClassLoader() {
 *     ...
 *     IntegrationFactory.getInstance().registerClassLoader(
 *      this,
 *      new FindResourceClassResourceSource(this)));
 *   }
 *   ...
 *   public Class findClass(String classname) {
 *     synchronized(classloader) {
 *       Class result =
 *         classloader.findLoadedClass(classname);
 *       if (result != null)
 *         return result;
 *         
 *       result = 
 *         IntegrationFactory.getInstance()
 *           .findReloadableClass(this, classname);
 *       if (result != null)
 *         return result;
 *     }
 *      ...
 *   }   
 * }
 * </pre>
 * 
 * <p>You can use the {@link #addIntegrationProcessor(String, ClassBytecodeProcessor)} and 
 * {@link #addIntegrationProcessor(String[], ClassBytecodeProcessor)}
 * and {@link #makeTransparent(String)} instrument specific classes to enhance their behaviour
 * with JavaRebel.</p>
 * 
 * <p>You can use {@link #addManagedPreProcessor(ClassBytecodeProcessor)} to register a preprocessor for 
 * all classes managed by JavaRebel e.g. to enable container-specific AOP</p>
 * 
 * @author Jevgeni Kabanov (ekabanov@zeroturnaround.com)
 * 
 * @see IntegrationFactory
 * @see ServletIntegration
 * @see Configuration
 * @see Reloader
 * @see Logger
 * @see Plugin
 */
public interface Integration {
  /**
   * Registers a <code>ClassLoader</code> with JavaRebel associating with it
   * the class-to-file resolution strategy provided by {@link ClassFileSource}. It
   * is necessary to do this before any calls to {@link #findReloadableClass(ClassLoader, String)},
   * the best place is usually the constructor.
   * 
   * @param cl The custom class loader being integrated.
   * @param cfs The class-to-file resolution strategy source. 
   * 
   * @see ClassResourceSource
   */
  public void registerClassLoader(ClassLoader cl, ClassResourceSource cfs);
  
  public void reinitializeClassLoader(ClassLoader cl);
    
  /**
   * Resolves classes managed by JavaRebel. These and only these classes
   * will be reloadable.
   * 
   * Returns <b>null</b> if class couldn't be resolved
   * and the non-JavaRebel class loading should be used as fail-over.
   * 
   * Throws {@link ClassNotFoundException} if class does not exist
   * and the non-JavaRebel class loading should be skipped.
   * 
   * @param cl The custom class loader being integrated.
   * @param classname The public class name.
   * @return Resolved class or <b>null</b>.
   *
   * @throws ClassNotFoundException if class does not exist.
   */
  public Class findReloadableClass(ClassLoader cl, String className) throws ClassNotFoundException;
  
  /**
   * Returns <code>true</code> if the given resource is managed by JavaRebel. 
   * <p>
   * In that case resources can be resolved using the corresponding methods of the <code>Integration</code>.
   * If any of these methods returns a <code>null</code> it must be returned by the <code>ClassLoader</code> as well.
   * 
   * @param cl The custom class loader being integrated.
   * @param name The public resource name.
   * @return <code>true</code> if the given resource is managed by JavaRebel.
   */
  public boolean isResourceReplaced(ClassLoader cl, String name);
  
  /**
   * Resolves resources managed by JavaRebel.
   * 
   * @param cl The custom class loader being integrated.
   * @param name The public resource name.
   * @return Resolved resource URL or <b>null</b>.
   */
  public URL findResource(ClassLoader cl, String name);
  
  /**
   * Resolves resources managed by JavaRebel.
   * 
   * @param cl The custom class loader being integrated.
   * @param name The public resource name.
   * @return Resolved resource URLs or <b>null</b>.
   */
  public Enumeration findResources(ClassLoader cl, String name);
  
  /**
   * Allows the given class to see the classes instrumented with JavaRebel via reflection
   * without hiding any artifacts. Needed e.g. for creating dynamic proxies and so on. 
   */
  public void makeTransparent(String className);  
  
  /**
   * Adds a bytecode processor for the given class in the given classloader. It will be run before that class is loaded 
   * and is mainly use to enable integration with a specific framework or classloader. Allows to additionally
   * restrict the bytecode processing to the given classloader.
   */
  public void addIntegrationProcessor(ClassLoader cl, String className, ClassBytecodeProcessor processor);
  
  /**
   * Adds a bytecode processor for the given class. It will be run before that class is loaded 
   * and is mainly use to enable integration with a specific framework or classloader.
   */
  public void addIntegrationProcessor(String className, ClassBytecodeProcessor processor);
  
  /**
   * Adds a bytecode processor for all classes. It will be run before that class is loaded 
   * and is mainly used for integration with preprocessors like AspectJ.
   * <p>
   * If <code>managedOnly</code> is <code>true</code> the processor will only be run for the 
   * classes managed by JavaRebel. This is useful to enable processing usually done in the 
   * class loader <code>findClass()</code> method, e.g. the JBoss AOP.
   */
  public void addIntegrationProcessor(ClassBytecodeProcessor processor, boolean managedOnly);
  
  /**
   * Adds a bytecode processor for the set of given classes. It will be run before those classes are loaded 
   * and is mainly use to enable integration with a specific framework or classloader.
   * 
   * @see #addIntegrationProcessor(String, ClassBytecodeProcessor)
   */
  public void addIntegrationProcessor(String[] classNames, ClassBytecodeProcessor processor);
  
  /**
   * Adds a bytecode processor for the set of given classes in the given classloader. It will be run before those classes are loaded 
   * and is mainly use to enable integration with a specific framework or classloader. Allows to additionally
   * restrict the bytecode processing to the given classloader.
   * 
   * @see #addIntegrationProcessor(ClassLoader, String, ClassBytecodeProcessor)
   */
  public void addIntegrationProcessor(ClassLoader cl, String[] classNames, ClassBytecodeProcessor processor);
}