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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;



/**
 * A factory for {@link ServletIntegration} that resolves a suitable implementation. 
 * Use {@link #getInstance()} method to get the {@link ServletIntegration} instance.
 * 
 * @author Jevgeni Kabanov (ekabanov@zeroturnaround.com)
 */
public class ServletIntegrationFactory {
  private static ServletIntegration INSTANCE;
  
  static {
    try {
      Class integrationClass = Class.forName("com.zeroturnaround.javarebel.SDKServletIntegrationImpl"); 
      INSTANCE = (ServletIntegration) integrationClass.newInstance();
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
      INSTANCE = new ServletIntegration() {

        public void registerServletContext(RebelServletContext servletContext) {
        }
        public void unregisterServletContext(RebelServletContext servletContext) {
        }
        public boolean hasReplacedResources(RebelServletContext sc) {
          return false;
        }
        public boolean isResourceReplaced(RebelServletContext sc, String path) {
          return false;
        }
        public URL getResource(RebelServletContext servletContext, String path) {
          return null;
        }
        public InputStream getResourceAsStream(RebelServletContext servletContext, String path) {
          return null;
        }
        public Set getResourcePaths(RebelServletContext servletContext, String path) {
          return null;
        }
        public String getRealPath(RebelServletContext servletContext, String path) {
          return null;
        }
        public String getTransparentRealPath(RebelServletContext sc, String path) {
          return null;
        }
        public URL getTransparentResource(RebelServletContext sc, String path) throws MalformedURLException {
          return null;
        }
        public InputStream getTransparentResourceAsStream( RebelServletContext sc, String path) {
          return null;
        }
        public Set getTransparentResourcePaths(RebelServletContext sc, String path) {
          return null;
        }

      };
    }
  }
  /**
   * Returns a suitable {@link ServletIntegration} implementation.
   * @return a suitable {@link ServletIntegration} implementation.
   */
  public static ServletIntegration getInstance() {
    return INSTANCE;
  }
}
