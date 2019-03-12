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
 * A factory for {@link Configuration} that resolves a suitable implementation. 
 * Use {@link #getInstance()} method to get the {@link Configuration} instance.
 * 
 * @author Jevgeni Kabanov (ekabanov@zeroturnaround.com)
 */
public class ConfigurationFactory {
  private static Configuration INSTANCE;
  
  static {
    try {
      Class configurationClass = Class.forName("com.zeroturnaround.javarebel.SDKConfigurationImpl"); 
      INSTANCE = (Configuration) configurationClass.newInstance();
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
      INSTANCE = new Configuration() {
        public void addExcludeManagedFilter(ClassFilter classFilter) { 
        }

        public void addIncludeManagedFilter(ClassFilter classFilter) {
        }

        public void addMonitoredDir(File dir) {
        }

        public void clearManagedFilters() {
        }

        public boolean isManagedClass(ClassLoader cl, String classname,
            Resource cr) {
          return false;
        }

        public File[] getMonitoredDirs() {
          return null;
        }

        public Resource getClassResource(ClassLoader cl, String classname) {
          // TODO Auto-generated method stub
          return null;
        }
      };
    }
  }
  
  /**
   * Returns a suitable {@link Configuration} implementation.
   * @return a suitable {@link Configuration} implementation.
   */
  public static Configuration getInstance() {
    return INSTANCE;
  }
}
