package org.zeroturnaround.javarebel.integration.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Map implementation that is always empty.
 * <p>
 * Put operations are just ignored.
 * 
 * @author Jevgeni Kabanov
 */
public class NopMap implements Map {

  public void clear() {
  }

  public boolean containsKey(Object key) {
    return false;
  }

  public boolean containsValue(Object value) {
    return false;
  }

  public Set entrySet() {
    return Collections.EMPTY_SET;
  }

  public Object get(Object key) {
    return null;
  }

  public boolean isEmpty() {
    return true;
  }

  public Set keySet() {
    return Collections.EMPTY_SET;
  }

  public Object put(Object arg0, Object arg1) {
    return null;
  }

  public void putAll(Map arg0) {
  }

  public Object remove(Object key) {
    return null;
  }

  public int size() {
    return 0;
  }

  public Collection values() {
    return Collections.EMPTY_LIST;
  }

}
