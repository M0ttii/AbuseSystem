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

/**
 * <p>Provides control and notification over/on class loading and reloading. 
 * To use acquire an instance from the {@link ReloaderFactory}.</p>
 * 
 * <pre>
 * ReloaderFactory.getInstance()
 *     .addClassReloadListener(new ClassEventListener() {
 *   public void onClassEvent(int eventType, Class klass) {
 *     System.out.println(
 *       "Class '" + klass.getName() + "' was reloaded!");
 *     }
 *   });
 * </pre>
 * 
 * @author Jevgeni Kabanov (ekabanov@zeroturnaround.com)
 * @see ReloaderFactory
 * @see Integration
 * @see Configuration
 * @see Logger
 * @see Plugin
 */
public interface Reloader {
  /**
   * Returns whether reloading is enabled generally.
   * @return Whether reloading is enabled generally.
   */
  boolean isReloadEnabled();
  
  /**
   * Returns whether this particular class is managed by JavaRebel and will be reloaded when it's updated.
   * @param klass Any class.
   * @return Whether this particular class is managed by JavaRebel and will be reloaded when it's updated.
   */
  boolean isReloadableClass(Class klass);
  
  /**
   * <p>Causes a class to be reloaded if (and only if) the 
   * underlying ".class" file has been updated. Returns
   * <b>true</b> if reloaded, <b>false</b> otherwise.</p>
   * 
   * <p>Note, that as JavaRebel is lazy, such checks may be necessary
   * if you want to be sure that your metadata is up-to-date. 
   * The {@link ClassEventListener} will only fire after JavaRebel 
   * has reloaded the class, which by default occurs on method 
   * calls to the class in question (and some reflection methods).</p>
   * 
   * @param klass The class to check.
   * @return <b>true</b> if reloaded, <b>false</b> otherwise.
   * 
   * @see ClassEventListener
   */
  boolean checkAndReload(Class klass);  
    
  /**
   * 
   */
  void forceCheck(); 
  
  /**
   * Register a {@link ClassEventListener} to receive notifications when managed classes are loaded.
   */
  void addClassLoadListener(ClassEventListener listener);
  
  /**
   * Deregister a {@link ClassEventListener}.
   */

  void removeClassLoadListener(ClassEventListener listener);

  /**
   * Register a {@link ClassEventListener} to receive notifications when managed classes are reloaded.
   */
  void addClassReloadListener(ClassEventListener listener);
  
  /**
   * Deregister a {@link ClassEventListener}.
   */

  void removeClassReloadListener(ClassEventListener listener);
  
  /**
   * 
   * @param klass
   */
  void reinitClass(Class klass);
  
  /**
   * 
   * @param className
   */
  void reinitOnReload(String className);
}
