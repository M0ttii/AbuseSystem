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
package com.google.gwt.cell.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

/**
 * A cell that renders a button and takes a delegate to perform actions on
 * mouseUp.
 *
 * @param <C> the type that this Cell represents
 */
public class ActionCell<C> extends AbstractCell<C> {

  /**
   * The delegate that will handle events from the cell.
   *
   * @param <T> the type that this delegate acts on
   */
  public static interface Delegate<T> {
    /**
     * Perform the desired action on the given object.
     *
     * @param object the object to be acted upon
     */
    void execute(T object);
  }

  private final SafeHtml html;
  private final Delegate<C> delegate;

  /**
   * Construct a new {@link ActionCell}.
   *
   * @param message the message to display on the button
   * @param delegate the delegate that will handle events
   */
  public ActionCell(SafeHtml message, Delegate<C> delegate) {
    super("click", "keydown");
    this.delegate = delegate;
    this.html = new SafeHtmlBuilder().appendHtmlConstant(
        "<button type=\"button\" tabindex=\"-1\">").append(message).appendHtmlConstant(
        "</button>").toSafeHtml();
  }

  /**
   * Construct a new {@link ActionCell} with a text String that does not contain
   * HTML markup.
   *
   * @param text the text to display on the button
   * @param delegate the delegate that will handle events
   */
  public ActionCell(String text, Delegate<C> delegate) {
    this(SafeHtmlUtils.fromString(text), delegate);
  }

  @Override
  public void onBrowserEvent(Element parent, C value, Object key,
      NativeEvent event, ValueUpdater<C> valueUpdater) {
    super.onBrowserEvent(parent, value, key, event, valueUpdater);
    if ("click".equals(event.getType())) {
      onEnterKeyDown(parent, value, key, event, valueUpdater);
    }
  }

  @Override
  public void render(C value, Object key, SafeHtmlBuilder sb) {
    sb.append(html);
  }

  @Override
  protected void onEnterKeyDown(Element parent, C value, Object key,
      NativeEvent event, ValueUpdater<C> valueUpdater) {
    delegate.execute(value);
  }
}
