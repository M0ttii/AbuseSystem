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
 * A callback that will be notified when a class event occurs. 
 * 
 * @since 1.2
 * 
 * @author Jevgeni Kabanov (ekabanov@zeroturnaround.com)
 */
public interface ClassEventListener { 
  public static final int PRIORITY_DEFAULT = 0;
  public static final int PRIORITY_WEB = -1000000;
  public static final int PRIORITY_CORE = 1000000;
  
  /**
   * Event that occurs right after a class has been loaded.
   */
  public static final int EVENT_LOADED = 0;
  /**
   * Event that occurs right after a class has been reloaded.
   */
  public static final int EVENT_RELOADED = 1;
  
  /**
   * Called when a class event occurs. 
   * 
   * @see #EVENT_LOADED
   * @see #EVENT_RELOADED
   */
  void onClassEvent(int eventType, Class klass);
  
  /**
   * Returns the priority of the listener that defines the order of listener invocation.
   */
  int priority();
}