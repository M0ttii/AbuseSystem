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
 * A factory for {@link ApplicationIntegration} that resolves a suitable implementation. 
 * Use {@link #getInstance()} method to get the {@link ApplicationIntegration} instance.
 * 
 * @author Jevgeni Kabanov (ekabanov@zeroturnaround.com)
 */
public class ApplicationIntegrationFactory {
  private static ApplicationIntegration INSTANCE;
  
  static {
    try {
      Class integrationClass = Class.forName("com.zeroturnaround.javarebel.SDKApplicationIntegrationImpl"); 
      INSTANCE = (ApplicationIntegration) integrationClass.newInstance();
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
      INSTANCE = new ApplicationIntegration() {
        public void registerApplication(File ear) {
        }
      };
    }
  }
  /**
   * Returns a suitable {@link ApplicationIntegration} implementation.
   * @return a suitable {@link ApplicationIntegration} implementation.
   */
  public static ApplicationIntegration getInstance() {
    return INSTANCE;
  }
}
