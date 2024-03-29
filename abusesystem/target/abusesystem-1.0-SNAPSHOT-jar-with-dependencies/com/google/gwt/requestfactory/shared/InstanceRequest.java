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
package com.google.gwt.requestfactory.shared;

/**
 * Used to call instance methods. Note that this does not extend {@link Request}
 * &mdash; rather it vends one. There is no way to get an instance method's
 * {@link Request#fire} without assigning an instance by calling {@link #using}.
 * 
 * @param <P> the instance type of EntityProxy
 * @param <T> the type eventually returned by the method invocation
 */
public interface InstanceRequest<P extends EntityProxy, T> {
  /**
   * Provide the instance on which the request will be invoked.
   */
  Request<T> using(P instanceObject);
}
