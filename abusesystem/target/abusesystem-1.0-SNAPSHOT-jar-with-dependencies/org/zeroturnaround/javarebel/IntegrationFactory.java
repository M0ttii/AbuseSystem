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
 * A factory for {@link Integration} that resolves a suitable implementation. 
 * Use {@link #getInstance()} method to get the {@link Integration} instance.
 * 
 * @author Jevgeni Kabanov (ekabanov@zeroturnaround.com)
 */
public class IntegrationFactory {
  private static Integration INSTANCE;
  
  static {
    try {
      Class integrationClass = Class.forName("com.zeroturnaround.javarebel.SDKIntegrationImpl"); 
      INSTANCE = (Integration) integrationClass.newInstance();
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
      INSTANCE = new Integration() {
        public Class findReloadableClass(ClassLoader cl, String classname) {
          return null;
        }

        public void registerClassLoader(ClassLoader cl, ClassResourceSource cfs) {
        }
        
        public void reinitializeClassLoader(ClassLoader cl) {
        }        

        public void addIntegrationProcessor(String classname,
            ClassBytecodeProcessor processor) {

        }

        public void addManagedPreProcessor(ClassBytecodeProcessor processor) {

        }

        public void makeTransparent(String className) {
        }

        public void addIntegrationProcessor(String[] classNames,
            ClassBytecodeProcessor processor) {
        }

        public void addIntegrationProcessor(ClassLoader cl, String className,
            ClassBytecodeProcessor processor) {
        }

        public void addIntegrationProcessor(ClassLoader cl,
            String[] classNames, ClassBytecodeProcessor processor) {
        }

        public boolean isResourceReplaced(ClassLoader cl, String classname) {
          return false;
        }        

        public URL findResource(ClassLoader cl, String name) {
          return null;
        }

        public Enumeration findResources(ClassLoader cl, String name) {
          return null;
        }
        public void addIntegrationProcessor(ClassBytecodeProcessor processor,
            boolean managedOnly) {
        }
      };
    }
  }
  /**
   * Returns a suitable {@link Integration} implementation.
   * @return a suitable {@link Integration} implementation.
   */
  public static Integration getInstance() {
    return INSTANCE;
  }
}
