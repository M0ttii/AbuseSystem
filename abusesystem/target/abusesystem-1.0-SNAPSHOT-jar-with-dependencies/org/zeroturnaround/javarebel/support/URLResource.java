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

import java.net.JarURLConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.zeroturnaround.javarebel.LoggerFactory;
import org.zeroturnaround.javarebel.Resource;


/**
 * A URL based resource.
 * 
 * @author Jevgeni Kabanov (ekabanov@zeroturnaround.com)
 */
public class URLResource implements Resource{
  private URL url;

  public URLResource(URL url) {
    this.url = url;
  }

  public byte[] getBytes() {
    URLConnection con = null;
    InputStream in = null;
    try {
      con = url.openConnection();
      
      if (con instanceof JarURLConnection) {
        JarURLConnection jarCon = (JarURLConnection) con;
        in = jarCon.getJarFile().getInputStream(jarCon.getJarEntry());
      }
      else {
        con.setUseCaches(false);
        con.connect();
        in = con.getInputStream();
      }

      ByteArrayOutputStream out = new ByteArrayOutputStream();

      byte[] buffer = new byte[4096];
      int read;

      while ((read = in.read(buffer)) != -1) {
        out.write(buffer, 0, read);
      }

      return out.toByteArray();
    } catch (IOException e) {
      LoggerFactory.getInstance().error(e);
    }
    finally {
      try {
        if (con != null && in != null)
          in.close();
      } catch (IOException e) {
        LoggerFactory.getInstance().error(e);
      }
    }

    return null;
  }

  public long lastModified() {
    URLConnection con = null;

    try {
      con = url.openConnection();

      if (con instanceof JarURLConnection) {
        return new File(((JarURLConnection) con).getJarFile().getName()).lastModified();
      }
      else {
        con.setUseCaches(false);
        con.connect();
        return con.getLastModified();
      }
    } catch (IOException e) {
      LoggerFactory.getInstance().error(e);
    }
    finally {
      try {
        if (con != null)
          con.getInputStream().close();
      } catch (IOException e) {
        LoggerFactory.getInstance().error(e);
      }
    }

    return 0;
  }

  public String toString() {
    return url.toString();
  }

  public URL toURL() {
    return url;
  }
}
