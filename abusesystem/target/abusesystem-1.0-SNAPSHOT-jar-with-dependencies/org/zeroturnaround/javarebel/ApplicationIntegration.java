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

/**
 * <p>Provides hooks for integrating JavaRebel with application containers.
 * To use it acquire an instance from {@link ApplicationIntegrationFactory}</p>
 * 
 * @author Rein Raudjärv
 * 
 * @see ApplicationIntegrationFactory
 * @see Integration
 * @see ServletIntegration
 * @see Reloader
 * @see Logger
 * @see Plugin
 */
public interface ApplicationIntegration {
  
  /**
   * Registers a JAR, WAR or EAR application with JavaRebel.
   * <p>
   * This is necessary to provide direct access to deployed application
   * which is impossible through the {@link ClassLoader} API.
   * </p>
   * <p>
   * This method is currently only used for Live Rebel.
   * </p>
   * <p>
   * The provided application is searched for a <code>rebel.xml</code> file.
   * If the configuration file contains &lt;name&gt; and &lt;version&gt; elements
   * all resources will be copied to a temporary directory
   * which will be served instead.
   * 
   * This enables to replace any resource in case of
   * an application version switch.
   * </p>
   * <p>
   * The EAR application should be registered before its modules.
   * </p>
   * <p>
   * If the EAR contains a <code>rebel.xml</code> in its root directory
   * the EAR is treated as a single application.
   * Otherwise each module will be handled separate.
   * 
   * @param application file or directory of the deployed JAR, WAR or EAR application.
   */
  public void registerApplication(File application);

}
