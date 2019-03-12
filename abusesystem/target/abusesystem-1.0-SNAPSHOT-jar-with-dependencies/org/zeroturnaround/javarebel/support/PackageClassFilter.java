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

package org.zeroturnaround.javarebel.support;

import org.zeroturnaround.javarebel.ClassFilter;
import org.zeroturnaround.javarebel.Resource;

/**
 * Filters the classes by matching them to a list of packages.
 * 
 * @author Jevgeni Kabanov (ekabanov@zeroturnaround.com)
 */
public class PackageClassFilter implements ClassFilter {
  private String[] packages;
  
  public PackageClassFilter(String[] packages) {
    this.packages = packages;
  }

  public boolean matches(ClassLoader cl, String className, Resource resource) {
    for (int i = 0; i < packages.length; i++) {
      if (className.startsWith(packages[i]))
        return true;      
    }

    return false;
  }
}
