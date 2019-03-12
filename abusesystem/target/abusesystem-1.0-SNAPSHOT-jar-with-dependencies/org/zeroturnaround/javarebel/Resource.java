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
import org.zeroturnaround.javarebel.support.FileResource;
import org.zeroturnaround.javarebel.support.URLResource;

/**
 * Represents an abstract resource that can be monitored for changes 
 * and is tied to a particular URL.
 * 
 * @author Jevgeni Kabanov (ekabanov@zeroturnaround.com)
 * 
 * @see ResourceSource
 * @see ClassResourceSource
 * @see FileResource
 * @see URLResource
 */
public interface Resource {  
  /**
   * Returns the binary representation of the class file.
   */
  byte[] getBytes();
  
  /**
   * Returns a millisecond representation of last modified time comparable with {@link System#currentTimeMillis()}.
   */
  long lastModified();
  
  /**
   * Returns the URL that class is loaded from.
   */
  URL toURL();
  
}
