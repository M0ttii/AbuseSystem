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
 * Provides simple logging with {@link #echo(String)}-style methods outputting both to console 
 * and to JavaRebel.log and {@link #log(String)}-style methods outputting only to log.
 * To use it acquire an instance from {@link LoggerFactory}
 * 
 * @since 1.2
 * 
 * @author Jevgeni Kabanov (ekabanov@zeroturnaround.com)
 * @see LoggerFactory
 * @see Integration
 * @see Configuration
 * @see Reloader
 * @see Plugin
 */
public interface Logger {
  /**
   * Enables debug logging to the JavaRebel.log file. 
   * Use when you are having some problems with the JavaRebel and you need to get more context.
   */
  public static final String SYSPROP_LOG = "rebel.log";
  
  /**
   * Enables additional logging of the performance statistics to the JavaRebel.log file.
   */
  public static final String SYSPROP_LOG_PERF = "rebel.log.perf";
  
  /**
   * Returns <code>true</code> if logging to JavaRebel.log is enabled.
   * 
   * @return <code>true</code> if logging to JavaRebel.log is enabled.
   */
  boolean isEnabled();
  
  /**
   * Logs a message to JavaRebel.log if enabled.
   */
  void log(String message);
  
  /**
   * Logs a message to JavaRebel.log if enabled and prints it to console.
   */
  void echo(String message);
  
  /**
   * Adds a newline to JavaRebel.log if enabled and prints it to console.
   */
  void echo();
  
  /**
   * Dumps stacktrace to to JavaRebel.log if enabled.
   */
  void error(Throwable e);
  
  /**
   * Dumps stacktrace to to JavaRebel.log if enabled  and prints it to console.
   */
  void errorEcho(Throwable e);
  
  /**
   * Returns a StopWatch used to log time taken by something. 
   * CAN RETURN NULL!!! 
   */
  StopWatch createStopWatch(String category);
}