/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.requestfactory.shared.impl;

/**
 * Defines a property of an {@link EntityProxy}.
 *
 * @param <V> the type of the property's value
 */
public class Property<V> {
  private final String name;
  private final Class<V> type;
  private final String displayName;

  /**
   * @param name the property's name and displayName
   * @param type the class of the property's value
   */
  public Property(String name, Class<V> type) {
    this(name, name, type);
  }

  /**
   * @param name the property's name
   * @param displayName the property's user visible name
   * @param type the class of the property's value
   */
  public Property(String name, String displayName, Class<V> type) {
    this.name = name;
    this.displayName = displayName;
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Property<?>)) {
      return false;
    }
    Property<?> property = (Property<?>) o;
    if (name != null ? !name.equals(property.name) : property.name != null) {
      return false;
    }
    if (type != null ? !type.equals(property.type) : property.type != null) {
      return false;
    }
    return true;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getName() {
    return name;
  }

  public Class<V> getType() {
    return type;
  }

  @Override
  public int hashCode() {
    int nameHash = name == null ? 0 : name.hashCode();
    int typeHash = type == null ? 0 : type.hashCode();
    return (nameHash * 31) ^ typeHash;
  }
}
