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
 * Represents a JavaRebel plugin that can be used by a third-party 
 * to configure or integrate with JavaRebel. Will be called before the
 * first class is loaded in the host classloader.
 * 
 * There are two ways to register a JavaRebel plugin. One is by creating
 * a JAR file with a custom manifest entry {@link #MANIFEST_ATTRIBUTE_PLUGIN} 
 * and putting it in the classpath. The other is by setting a system property
 * {@link #SYSPROP_PLUGIN} to the plugin class.
 * 
 * @since 1.2
 * 
 * @author Jevgeni Kabanov (ekabanov@zeroturnaround.com)
 * 
 * @see Integration
 * @see Configuration
 * @see Reloader
 * @see Logger
 */
public interface Plugin {
  /**
   * This system property should point to a class name that implements
   * this interface. It can be used by applications to enable additional
   * JavaRebel configuration.
   * <br/>
   * For example <code>-Drebel.plugin=com.mycompany.mypackage.MyJavaRebelPlugin</code>.
   */
  public static final String SYSPROP_PLUGIN = "rebel.plugin";
  
  /**
   * This JAR MANIFEST.MF attribute should point to a class name that implements
   * this interface. It can be used by third-party libraries to enable additional
   * JavaRebel configuration or integration and also to create standalone JavaRebel
   * plugin JARs.
   */
  public static final String MANIFEST_ATTRIBUTE_PLUGIN = "JavaRebel-Plugin";
  
  /**
   * This system property should point to comma-separated list of 2.0 plugin jars that
   * implement the {@link #checkDependencies(ClassLoader, ClassResourceSource)} method.
   * <br/>
   * For example <code>-Drebel.plugins=/path/to/plugin1.jar,/path/to/plugin2.jar</code>.
   * 
   * 
   * @since 2.0
   */
  public static final String SYSPROP_PLUGINS = "rebel.plugins";
  
  /**
   * Called before the first class in the host classloader is loaded.
   */
  void preinit();
  
  /**
   * This method is necessary to embed the plugin into JavaRebel distribution, 
   * it is not necessary for the plugins in the classpath. It should check
   * that the plugin dependencies are present in the tested classloader. 
   * Please don't try to load classes as this will cause them to be loaded 
   * and your postprocessing won't take place. Instead use {@link ClassResourceSource#getClassResource(String)} 
   * to test for class presense, e.g. 
   * <code>return crs.getClassResourceSource("org.myframework.MyClass") != null;</code>. 
   * 
   * To test whether the implementation is correct add your plugin jar to <code>-Drebel.plugins</code>
   * and remove it from the classpath. If your plugin is found it will be initialized by JavaRebel
   * in correct classloader.
   * 
   * @since 2.0
   */
  boolean checkDependencies(ClassLoader cl, ClassResourceSource crs);
  
  /**
   * A Unix-like id, e.g. "spring_plugin". 
   * Mainly used for internal handling and creating the system property. 
   * Cannot be null for embedded plugins!
   * 
   * @since 2.0
   */
  String getId();
  
  /**
   * Name of the plugin, optionally with a version, e.g. "Spring Framework Plugin 1.0 M4".
   * Can be null if you don't want the plugin to be visible to the user.
   * 
   * @since 2.0
   */
  String getName();
  
  /**
   * Description of the plugin, a few lines about purpose and functionality of the plugin. 
   * Can be null if you don't want the plugin to be visible to the user.
   * 
   * @since 2.0
   */
  String getDescription();
  
  /**
   * The name of the person/entity that contributed the plugin. Please include contact e-mail if possible.
   * Can be null if you don't want the plugin to be visible to the user.
   * 
   * @since 2.0
   */
  String getAuthor();
  
  /**
   * The URL to the website with information about the plugin.
   * Can be null if you don't want the plugin to be visible to the user. 
   * 
   * @since 2.0
   */
  String getWebsite();
}
