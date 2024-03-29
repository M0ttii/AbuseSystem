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
 */package com.google.gwt.user.cellview.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashSet;
import java.util.Set;

/**
 * Standard implementation used by most cell based widgets.
 */
class CellBasedWidgetImplStandard extends CellBasedWidgetImpl {

  /**
   * The method used to dispatch non-bubbling events.
   */
  private static JavaScriptObject dispatchNonBubblingEvent;

  /**
   * Dispatch an event through the normal GWT mechanism.
   */
  private static native void dispatchEvent(
      Event evt, Element elem, EventListener listener) /*-{
    @com.google.gwt.user.client.DOM::dispatchEvent(Lcom/google/gwt/user/client/Event;Lcom/google/gwt/user/client/Element;Lcom/google/gwt/user/client/EventListener;)(evt, elem, listener);
  }-*/;

  /**
   * Handle an event from a cell. Used by {@link #initEventSystem()}.
   *
   * @param event the event to handle.
   */
  @SuppressWarnings("unused")
  private static void handleNonBubblingEvent(Event event) {
    // Get the event target.
    EventTarget eventTarget = event.getEventTarget();
    if (!Element.is(eventTarget)) {
      return;
    }
    com.google.gwt.user.client.Element target = eventTarget.cast();

    // Get the event listener.
    EventListener listener = DOM.getEventListener(target);
    while (target != null && listener == null) {
      target = target.getParentElement().cast();
      listener = (target == null) ? null : DOM.getEventListener(target);
    }

    // Fire the event.
    if (listener != null) {
      dispatchEvent(event, target, listener);
    }
  }

  /**
   * The set of non bubbling event types.
   */
  private final Set<String> nonBubblingEvents;

  public CellBasedWidgetImplStandard() {
    // Initialize the set of non-bubbling events.
    nonBubblingEvents = new HashSet<String>();
    nonBubblingEvents.add("focus");
    nonBubblingEvents.add("blur");
    nonBubblingEvents.add("load");
    nonBubblingEvents.add("error");
  }

  @Override
  protected int sinkEvent(Widget widget, String typeName) {
    if (nonBubblingEvents.contains(typeName)) {
      // Initialize the event system.
      if (dispatchNonBubblingEvent == null) {
        initEventSystem();
      }

      // Sink the non-bubbling event.
      Element elem = widget.getElement();
      String attr = "__gwtCellBasedWidgetImplDispatching" + typeName;
      if (!"true".equals(elem.getAttribute(attr))) {
        elem.setAttribute(attr, "true");
        sinkEventImpl(elem, typeName);
      }
      return -1;
    } else {
      return super.sinkEvent(widget, typeName);
    }
  }

  /**
   * Initialize the event system.
   */
  private native void initEventSystem() /*-{
    @com.google.gwt.user.cellview.client.CellBasedWidgetImplStandard::dispatchNonBubblingEvent = $entry(function(evt) {
      @com.google.gwt.user.cellview.client.CellBasedWidgetImplStandard::handleNonBubblingEvent(Lcom/google/gwt/user/client/Event;)(evt);
    });
  }-*/;

  /**
   * Sink an event on the element.
   *
   * @param elem the element to sink the event on
   * @param typeName the name of the event to sink
   */
  private native void sinkEventImpl(Element elem, String typeName) /*-{
    elem.addEventListener(typeName, @com.google.gwt.user.cellview.client.CellBasedWidgetImplStandard::dispatchNonBubblingEvent, true);
  }-*/;
}
