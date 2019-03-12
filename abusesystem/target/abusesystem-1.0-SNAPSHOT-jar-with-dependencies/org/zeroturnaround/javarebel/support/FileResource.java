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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import org.zeroturnaround.javarebel.Resource;
/**
 * A file system resource.
 * 
 * @author Jevgeni Kabanov (ekabanov@zeroturnaround.com)
 */
public class FileResource implements Resource {
  private URL url;
  private File file;
  
  public FileResource(URL url) {
    this.url = url;
    this.file = new File(URLDecoder.decode(url.getPath()));
  }

  public byte[] getBytes() {
    InputStream is;
    try {
      is = new FileInputStream(file);
    } 
    catch (FileNotFoundException e1) {
      throw new RuntimeException(e1);
    }
    
    try {      
      // Get the size of the file
      long length = file.length();

      if (length > Integer.MAX_VALUE) {
        // File is too large
      }

      // Create the byte array to hold the data
      byte[] bytes = new byte[(int)length];

      // Read in the bytes
      int offset = 0;
      int numRead = 0;
      while (offset < bytes.length
          && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
        offset += numRead;
      }

      // Ensure all the bytes have been read in
      if (offset < bytes.length) {
        throw new IOException("Could not completely read file "+file.getName());
      }

      // Close the input stream and return bytes
      return bytes;
    } 
    catch (IOException e) {
      throw new RuntimeException(e);
    }
    finally {
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  public long lastModified() {
    return file.lastModified();
  }
  
  public String toString() {
    return url.toString();
  }

  public URL toURL() {
    return url;
  }
}
