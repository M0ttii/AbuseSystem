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

import java.io.File;
import org.zeroturnaround.javarebel.support.PackageClassFilter;

/**
 * <p>This class manages JavaRebel configuration.</p>
 * 
 * <p>To manage the classes that will be instrumented use the filter methods 
 * {@link #addExcludeManagedFilter(ClassFilter)} and {@link #addIncludeManagedFilter(ClassFilter)}. 
 * If you need to override the default behavior (e.g. you don't need class files to be managed)
 * use {@link #clearManagedFilters()} to disable the default filters. If you want to filter 
 * classes by packages you can use {@link PackageClassFilter}.<p>
 * 
 * <p>The filter methods work as follows:</p
 * 
 * <ul>
 *   <li>If at least one exclude filter matches a class it will not be instrumented.</li>
 *   <li>If none of the exclude filters match a class and at least one of the include filters matches a class it will be instrumented.</li>
 * </ul>
 * 
 * <p>For example, if you want all classes in a particular JAR to be instrumented you 
 * can use a following filter:</p>
 * 
 * <pre>
 *   ConfigurationFactory.getInstance()
 *     .addIncludeManagedFilter(new ClassFilter() {
 *     public boolean matches(ClassLoader cl, String className,
 *         Resource classResource) {        
 *       return classResource.toURL().getPath().contains("mylibrary.jar");
 *     }
 *   });
 * </pre>
 * 
 * <p>To manage monitored directories use the method {@link #addMonitoredDir(File)}</p>
 * 
 * @since 1.2
 * 
 * @author Jevgeni Kabanov (ekabanov@zeroturnaround.com)
 * 
 * @see ConfigurationFactory
 * @see Integration
 * @see Reloader
 * @see Logger
 * @see Plugin
 */
public interface Configuration {
  /**
   * This system property should be set to a comma-separated list of Java packages. 
   * JavaRebel will then only manage classes in those packages and their subpackages.
   * This is mainly used to restrict instrumenting to only a limited subset of classes, 
   * e.g. to improve performance.<br/>
   * For example <code>-Drebel.packages=com.mycompany.mypackage,com.thirdparty.theirpackage</code>.
   */
  public static final String SYSPROP_MANAGED_PACKAGES = "rebel.packages";
  
  /**
   * This system property should be set to a comma-separated list of Java packages. 
   * JavaRebel will not manage classes in those packages and their subpackages.
   * This is mainly used to exclude classes that produce errors with JavaRebel from
   * being instrumented. <br/>
   * For example <code>-Drebel.packages_exclude=com.mycompany.mypackage,com.thirdparty.theirpackage</code>. 
   */
  public static final String SYSPROP_MANAGED_PACKAGES_EXCLUDE = "rebel.packages_exclude";
  
  /**
   * This system property should be set to a comma-separated list of Java packages. 
   * JavaRebel will also manage classes in those packages and their subpackages.
   * This is mainly used to enable JAR file reloading. <br/>
   * For example <code>-Drebel.packages_include=com.mycompany.mypackage,com.thirdparty.theirpackage</code>.
   */
  public static final String SYSPROP_MANAGED_PACKAGES_INCLUDE = "rebel.packages_include";
  
  /**
   * This system property should be set to a comma-separated list of directories. 
   * Those directories should have the same layout as if they were in the classpath.
   * The classes in those directories will be monitored for class changes.
   * This is mainly meant to use with unexploded development by setting this 
   * property to the IDE class output directories.
   * <br/>
   * For example <code>-Drebel.dirs=c:/workspace/myproject/bin,c:/workspace/mylibrary/bin</code>.
   * 
   * @deprecated
   */
  public static final String SYSPROP_MONITORED_DIRS = "rebel.dirs";
  
  /**
   * Clears all currently used filters including the default ones. 
   * Use it if you need to override the default behavior (e.g. you don't need class files to be managed).
   */
  void clearManagedFilters();
  
  /**
   * Adds an include filter for managed classes. 
   * If none of the exclude filters match a class and at least one 
   * of the include filters matches a class it will be instrumented by JavaRebel.
   */
  void addIncludeManagedFilter(ClassFilter classFilter);
  
  /**
   * Adds an exclude filter for managed classes. 
   * If at least one exclude filter matches a class it will not be instrumented by JavaRebel.
   */
  void addExcludeManagedFilter(ClassFilter classFilter);
  
  /**
   * Returns whether the filters will allow the given class to be managed.  
   */
  boolean isManagedClass(ClassLoader cl, String classname, Resource cr);
  
  /**
   * Returns a JavaRebel Resource exactly as JavaRebel will load it. 
   * E.g. if the resource is in the monitored directory it will return that. 
   * Note that this already includes managed filtering, that is if
   * {@link #isManagedClass(ClassLoader, String, Resource)} returns false
   * then this method will return null.
   */
  public Resource getClassResource(ClassLoader cl, String classname);
  
  /**
   * Adds a monitored directory. 
   * These directories should have the same layout as if they were in the classpath.
   * The classes in those directories will be monitored for class changes.
   * This is mainly meant to use with unexploded development by setting this 
   * property to the IDE class output directories.
   * 
   * @deprecated
   */
  void addMonitoredDir(File dir);
  
  /**
   * Returns all currently monitored directories.
   * 
   * @deprecated
   */
  File[] getMonitoredDirs();
}
