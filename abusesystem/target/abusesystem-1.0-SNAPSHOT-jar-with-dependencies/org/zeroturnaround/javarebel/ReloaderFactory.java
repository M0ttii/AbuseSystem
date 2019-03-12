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
 * A factory for {@link Reloader} that resolves a suitable implementation. 
 * Use {@link #getInstance()} method to get the {@link Reloader} instance.
 * 
 * @author Jevgeni Kabanov (ekabanov@zeroturnaround.com)
 */
public class ReloaderFactory {
  private static Reloader INSTANCE;
  
  static {   
    try {
      Class reloaderClass = Class.forName("com.zeroturnaround.javarebel.SDKReloaderImpl");     
      INSTANCE = (Reloader) reloaderClass.newInstance();
    }
    catch (ClassNotFoundException e) {
    }
    catch (InstantiationException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    catch (IllegalAccessException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    
    //"Do nothing" implementation
    if (INSTANCE == null) {
      INSTANCE = new Reloader() {
        public boolean isReloadableClass(Class klass) {
          return false;
        }
        public boolean isReloadEnabled() {
          return false;
        }
        public void addClassReloadListener(ClassEventListener listener) {
        }
        
        public void removeClassReloadListener(ClassEventListener listener) {
        }
        
        public boolean checkAndReload(Class klass) {
          return false;
        }           
        
        public void addClassLoadListener(ClassEventListener listener) {
        }
        public void removeClassLoadListener(ClassEventListener listener) {
        }
        public void forceCheck() {
        }
        public void reinitClass(Class klass) {         
        }        
        public void reinitOnReload(String className) {         
        }
        public void addClassLoadListener(int priority,
            ClassEventListener listener) {          
        }
        public void addClassReloadListener(int priority,
            ClassEventListener listener) {
        }
      };
    }
  }
  
  /**
   * Returns a suitable {@link Reloader} implementation.
   * @return a suitable {@link Reloader} implementation.
   */
  public static Reloader getInstance() {
    return INSTANCE;
  }
}
