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
package com.google.gwt.requestfactory.ui.client;

import com.google.gwt.text.shared.AbstractRenderer;

/**
 * Renders a proxy object, and reports the properties it requires to do that
 * rendering.
 * 
 * @param <R> the type to render
 */
public abstract class ProxyRenderer<R> extends
    AbstractRenderer<R> {

  private final String[] paths;

  public ProxyRenderer(String[] strings) {
    this.paths = strings;
  }

  /**
   * The properties required by this renderer.
   */
  public String[] getPaths() {
    return paths;
  }
}
