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

import org.zeroturnaround.javarebel.support.FileResource;
import org.zeroturnaround.javarebel.support.URLResource;


/**
 * A class-to-resource resolution strategy. JavaRebel will use the resource
 * to monitor {@link Resource#lastModified()} for updates.
 * 
 * @author Jevgeni Kabanov (ekabanov@zeroturnaround.com)
 */
public interface ClassResourceSource extends ResourceSource {
  /**
   * Should return the {@link Resource} that corresponds to the resource
   * that the given class is loaded from.
   * 
   * @see FileResource
   * @see URLResource
   */
  public Resource getClassResource(String className);
}
