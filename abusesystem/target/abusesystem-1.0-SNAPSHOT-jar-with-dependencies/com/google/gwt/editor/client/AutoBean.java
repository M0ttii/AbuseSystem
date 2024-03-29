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
package com.google.gwt.editor.client;

/**
 * A controller for an implementation of a bean interface.
 * 
 * @param <T> the type of interface that will be wrapped.
 */
public interface AutoBean<T> {
  /**
   * Accept an AutoBeanVisitor.
   *
   * @param visitor an {@link AutoBeanVisitor}
   */
  void accept(AutoBeanVisitor visitor);

  /**
   * Returns a proxy implementation of the <code>T</code> interface which will
   * delegate to the underlying wrapped object, if any.
   *
   * @return a proxy that delegates to the wrapped object
   */
  T as();

  /**
   * Creates a copy of the AutoBean.
   * <p>
   * If the AutoBean has tags, the tags will be copied into the cloned AutoBean.
   * If any of the tag values are AutoBeans, they will not be cloned, regardless
   * of the value of <code>deep</code>.
   * 
   * @param deep indicates if all referenced AutoBeans should be cloned
   * @return a copy of this {@link AutoBean}
   * @throws IllegalStateException if the AutoBean is a wrapper type
   */
  AutoBean<T> clone(boolean deep);

  /**
   * Retrieve a tag value that was previously provided to
   * {@link #setTag(String, Object)}.
   * 
   * @param tagName the tag name
   * @return the tag value
   * @see #setTag(String, Object)
   */
  <Q> Q getTag(String tagName);

  /**
   * Returns the value most recently passed to {@link #setFrozen}, or {@code
   * false} if it has never been called.
   * 
   * @return {@code true} if this instance is frozen
   */
  boolean isFrozen();

  /**
   * Returns {@code true} if the AutoBean was provided with an external
   * object.
   *
   * @return {@code true} if this instance is a wrapper
   */
  boolean isWrapper();

  /**
   * Disallows any method calls other than getters. All setter and call
   * operations will throw an {@link UnsupportedOperationException}.
   * 
   * @param frozen if {@code true}, freeze this instance
   */
  void setFrozen(boolean frozen);

  /**
   * A tag is an arbitrary piece of external metadata to be associated with the
   * wrapped value.
   *
   * @param tagName the tag name
   * @param value the wrapped value
   * @see #getTag(String)
   */
  void setTag(String tagName, Object value);

  /**
   * If the AutoBean wraps an object, return the underlying object.
   * The AutoBean will no longer function once unwrapped.
   *
   * @return the previously-wrapped object
   * @throws IllegalStateException if the AutoBean is not a wrapper
   */
  T unwrap();
}
