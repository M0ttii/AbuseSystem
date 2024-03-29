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
package com.google.gwt.user.client.rpc.core.java.util;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Dummy class for nesting the custom serializer.
 */
public final class Collections {

  /**
   * Custom field serializer for {@link java.util.Collections$EmptyList}.
   */
  public static final class EmptyList_CustomFieldSerializer {

    public static Class<?> concreteType() {
      return java.util.Collections.emptyList().getClass();
    }

    @SuppressWarnings({"unused", "unchecked"})
    public static void deserialize(SerializationStreamReader streamReader,
        List instance) throws SerializationException {
      // Handled in instantiate.
    }

    @SuppressWarnings({"unused", "unchecked"})
    public static List instantiate(SerializationStreamReader streamReader)
        throws SerializationException {
      return java.util.Collections.emptyList();
    }

    @SuppressWarnings({"unused", "unchecked"})
    public static void serialize(SerializationStreamWriter streamWriter,
        List instance) throws SerializationException {
      // Nothing to serialize -- instantiate always returns the same thing
    }
  }

  /**
   * Custom field serializer for {@link java.util.Collections$EmptyMap}.
   */
  public static final class EmptyMap_CustomFieldSerializer {

    public static Class<?> concreteType() {
      return java.util.Collections.emptyMap().getClass();
    }

    @SuppressWarnings({"unused", "unchecked"})
    public static void deserialize(SerializationStreamReader streamReader,
        Map instance) throws SerializationException {
      // Handled in instantiate.
    }

    @SuppressWarnings({"unused", "unchecked"})
    public static Map instantiate(SerializationStreamReader streamReader)
        throws SerializationException {
      return java.util.Collections.emptyMap();
    }

    @SuppressWarnings({"unused", "unchecked"})
    public static void serialize(SerializationStreamWriter streamWriter,
        Map instance) throws SerializationException {
      // Nothing to serialize -- instantiate always returns the same thing
    }
  }

  /**
   * Custom field serializer for {@link java.util.Collections$EmptySet}.
   */
  public static final class EmptySet_CustomFieldSerializer {

    public static Class<?> concreteType() {
      return java.util.Collections.emptySet().getClass();
    }

    @SuppressWarnings({"unused", "unchecked"})
    public static void deserialize(SerializationStreamReader streamReader,
        Set instance) throws SerializationException {
      // Handled in instantiate.
    }

    @SuppressWarnings({"unused", "unchecked"})
    public static Set instantiate(SerializationStreamReader streamReader)
        throws SerializationException {
      return java.util.Collections.emptySet();
    }

    @SuppressWarnings({"unused", "unchecked"})
    public static void serialize(SerializationStreamWriter streamWriter,
        Set instance) throws SerializationException {
      // Nothing to serialize -- instantiate always returns the same thing
    }
  }

  /**
   * Custom field serializer for {@link java.util.Collections$SingletonList}.
   */
  public static final class SingletonList_CustomFieldSerializer {

    public static Class<?> concreteType() {
      return java.util.Collections.singletonList(null).getClass();
    }

    @SuppressWarnings({"unused", "unchecked"})
    public static void deserialize(SerializationStreamReader streamReader,
        List instance) throws SerializationException {
    }

    @SuppressWarnings("unchecked")
    public static List instantiate(SerializationStreamReader streamReader)
        throws SerializationException {
      return java.util.Collections.singletonList(streamReader.readObject());
    }

    @SuppressWarnings("unchecked")
    public static void serialize(SerializationStreamWriter streamWriter,
        List instance) throws SerializationException {
      streamWriter.writeObject(instance.get(0));
    }
  }
}
