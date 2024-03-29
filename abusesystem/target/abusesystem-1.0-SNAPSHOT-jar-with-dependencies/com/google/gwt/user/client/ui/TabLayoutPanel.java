/*
 * Copyright 2009 Google Inc.
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
package com.google.gwt.user.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.HasBeforeSelectionHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.safehtml.shared.SafeHtml;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A panel that represents a tabbed set of pages, each of which contains another
 * widget. Its child widgets are shown as the user selects the various tabs
 * associated with them. The tabs can contain arbitrary text, HTML, or widgets.
 *
 * <p>
 * This widget will <em>only</em> work in standards mode, which requires that
 * the HTML page in which it is run have an explicit &lt;!DOCTYPE&gt;
 * declaration.
 * </p>
 *
 * <h3>CSS Style Rules</h3>
 * <dl>
 * <dt>.gwt-TabLayoutPanel
 * <dd>the panel itself
 * <dt>.gwt-TabLayoutPanel .gwt-TabLayoutPanelTabs
 * <dd>the tab bar element
 * <dt>.gwt-TabLayoutPanel .gwt-TabLayoutPanelTab
 * <dd>an individual tab
 * <dt>.gwt-TabLayoutPanel .gwt-TabLayoutPanelTabInner
 * <dd>an element nested in each tab (useful for styling)
 * <dt>.gwt-TabLayoutPanel .gwt-TabLayoutPanelContent
 * <dd>applied to all child content widgets
 * </dl>
 *
 * <p>
 * <h3>Example</h3>
 * {@example com.google.gwt.examples.TabLayoutPanelExample}
 *
 * <h3>Use in UiBinder Templates</h3>
 * <p>
 * A TabLayoutPanel element in a {@link com.google.gwt.uibinder.client.UiBinder
 * UiBinder} template must have a <code>barHeight</code> attribute with a double
 * value, and may have a <code>barUnit</code> attribute with a
 * {@link com.google.gwt.dom.client.Style.Unit Style.Unit} value.
 * <code>barUnit</code> defaults to PX.
 * <p>
 * The children of a TabLayoutPanel element are laid out in &lt;g:tab>
 * elements. Each tab can have one widget child and one of two types of header
 * elements. A &lt;g:header> element can hold html, or a &lt;g:customHeader>
 * element can hold a widget. (Note that the tags of the header elements are
 * not capitalized. This is meant to signal that the head is not a runtime
 * object, and so cannot have a <code>ui:field</code> attribute.)
 * <p>
 * For example:<pre>
 * &lt;g:TabLayoutPanel barUnit='EM' barHeight='3'>
 *  &lt;g:tab>
 *    &lt;g:header size='7'>&lt;b>HTML&lt;/b> header&lt;/g:header>
 *    &lt;g:Label>able&lt;/g:Label>
 *  &lt;/g:tab>
 *  &lt;g:tab>
 *    &lt;g:customHeader size='7'>
 *      &lt;g:Label>Custom header&lt;/g:Label>
 *    &lt;/g:customHeader>
 *    &lt;g:Label>baker&lt;/g:Label>
 *  &lt;/g:tab>
 * &lt;/g:TabLayoutPanel>
 * </pre>
 */
public class TabLayoutPanel extends ResizeComposite implements HasWidgets,
    ProvidesResize, IndexedPanel.ForIsWidget,
    HasBeforeSelectionHandlers<Integer>, HasSelectionHandlers<Integer> {

  private static class Tab extends SimplePanel {
    private Element inner;

    public Tab(Widget child) {
      super(Document.get().createDivElement());
      getElement().appendChild(inner = Document.get().createDivElement());

      setWidget(child);
      setStyleName(TAB_STYLE);
      inner.setClassName(TAB_INNER_STYLE);

      // TODO: float:left may not be enough. If there are tabs of differing
      // heights, the shorter ones will top-align, rather than bottom-align,
      // which is what we would want. display:inline-block fixes this, but
      // needs lots of cross-browser hacks to work properly.
      getElement().getStyle().setFloat(Style.Float.LEFT);
    }

    public HandlerRegistration addClickHandler(ClickHandler handler) {
      return addDomHandler(handler, ClickEvent.getType());
    }

    public void setSelected(boolean selected) {
      if (selected) {
        addStyleDependentName("selected");
      } else {
        removeStyleDependentName("selected");
      }
    }

    @Override
    protected com.google.gwt.user.client.Element getContainerElement() {
      return inner.cast();
    }
  }
  private static final String CONTENT_STYLE = "gwt-TabLayoutPanelContent";
  private static final String TAB_STYLE = "gwt-TabLayoutPanelTab";

  private static final String TAB_INNER_STYLE = "gwt-TabLayoutPanelTabInner";

  private static final int BIG_ENOUGH_TO_NOT_WRAP = 16384;

  private final WidgetCollection children = new WidgetCollection(this);
  private final FlowPanel tabBar = new FlowPanel();
  private final ArrayList<Tab> tabs = new ArrayList<Tab>();
  private final double barHeight;
  private final Unit barUnit;
  private final LayoutPanel panel;
  private int selectedIndex = -1;

  /**
   * Creates an empty tab panel.
   *
   * @param barHeight the size of the tab bar
   * @param barUnit the unit in which the tab bar size is specified
   */
  public TabLayoutPanel(double barHeight, Unit barUnit) {
    this.barHeight = barHeight;
    this.barUnit = barUnit;

    panel = new LayoutPanel();
    initWidget(panel);

    panel.add(tabBar);
    panel.setWidgetLeftRight(tabBar, 0, Unit.PX, 0, Unit.PX);
    panel.setWidgetTopHeight(tabBar, 0, Unit.PX, barHeight, barUnit);
    panel.setWidgetVerticalPosition(tabBar, Alignment.END);

    // Make the tab bar extremely wide so that tabs themselves never wrap.
    // (Its layout container is overflow:hidden)
    tabBar.getElement().getStyle().setWidth(BIG_ENOUGH_TO_NOT_WRAP, Unit.PX);

    tabBar.setStyleName("gwt-TabLayoutPanelTabs");
    setStyleName("gwt-TabLayoutPanel");
  }

  /**
   * Convenience overload to allow {@link IsWidget} to be used directly.
   */
  public void add(IsWidget w) {
    add(asWidgetOrNull(w));
  }

  /**
   * Convenience overload to allow {@link IsWidget} to be used directly.
   */
  public void add(IsWidget w, IsWidget tab) {
    add(asWidgetOrNull(w), asWidgetOrNull(tab));
  }

  /**
   * Convenience overload to allow {@link IsWidget} to be used directly.
   */
  public void add(IsWidget w, String text) {
    add(asWidgetOrNull(w), text);
  }

  /**
   * Convenience overload to allow {@link IsWidget} to be used directly.
   */
  public void add(IsWidget w, String text, boolean asHtml) {
    add(asWidgetOrNull(w), text, asHtml);
  }

  public void add(Widget w) {
    insert(w, getWidgetCount());
  }

  /**
   * Adds a widget to the panel. If the Widget is already attached, it will be
   * moved to the right-most index.
   *
   * @param child the widget to be added
   * @param text the text to be shown on its tab
   */
  public void add(Widget child, String text) {
    insert(child, text, getWidgetCount());
  }

  /**
   * Adds a widget to the panel. If the Widget is already attached, it will be
   * moved to the right-most index.
   *
   * @param child the widget to be added
   * @param html the html to be shown on its tab
   */
  public void add(Widget child, SafeHtml html) {
    add(child, html.asString(), true);
  }

  /**
   * Adds a widget to the panel. If the Widget is already attached, it will be
   * moved to the right-most index.
   *
   * @param child the widget to be added
   * @param text the text to be shown on its tab
   * @param asHtml <code>true</code> to treat the specified text as HTML
   */
  public void add(Widget child, String text, boolean asHtml) {
    insert(child, text, asHtml, getWidgetCount());
  }

  /**
   * Adds a widget to the panel. If the Widget is already attached, it will be
   * moved to the right-most index.
   *
   * @param child the widget to be added
   * @param tab the widget to be placed in the associated tab
   */
  public void add(Widget child, Widget tab) {
    insert(child, tab, getWidgetCount());
  }

  public HandlerRegistration addBeforeSelectionHandler(
      BeforeSelectionHandler<Integer> handler) {
    return addHandler(handler, BeforeSelectionEvent.getType());
  }

  public HandlerRegistration addSelectionHandler(
      SelectionHandler<Integer> handler) {
    return addHandler(handler, SelectionEvent.getType());
  }

  public void clear() {
    Iterator<Widget> it = iterator();
    while (it.hasNext()) {
      it.next();
      it.remove();
    }
  }

  /**
   * Gets the index of the currently-selected tab.
   *
   * @return the selected index, or <code>-1</code> if none is selected.
   */
  public int getSelectedIndex() {
    return selectedIndex;
  }

  /**
   * Gets the widget in the tab at the given index.
   *
   * @param index the index of the tab to be retrieved
   * @return the tab's widget
   */
  public Widget getTabWidget(int index) {
    checkIndex(index);
    return tabs.get(index).getWidget();
  }

  /**
   * Convenience overload to allow {@link IsWidget} to be used directly.
   */
  public Widget getTabWidget(IsWidget child) {
    return getTabWidget(asWidgetOrNull(child));
  }

  /**
   * Gets the widget in the tab associated with the given child widget.
   *
   * @param child the child whose tab is to be retrieved
   * @return the tab's widget
   */
  public Widget getTabWidget(Widget child) {
    checkChild(child);
    return getTabWidget(getWidgetIndex(child));
  }

  /**
   * Returns the widget at the given index.
   */
  public Widget getWidget(int index) {
    checkIndex(index);
    return children.get(index);
  }

  /**
   * Returns the number of tabs and widgets.
   */
  public int getWidgetCount() {
    return children.size();
  }

  /**
   * Convenience overload to allow {@link IsWidget} to be used directly.
   */
  public int getWidgetIndex(IsWidget child) {
    return getWidgetIndex(asWidgetOrNull(child));
  }

  /**
   * Returns the index of the given child, or -1 if it is not a child.
   */
  public int getWidgetIndex(Widget child) {
    return children.indexOf(child);
  }

  /**
   * Convenience overload to allow {@link IsWidget} to be used directly.
   */
  public void insert(IsWidget child, int beforeIndex) {
    insert(asWidgetOrNull(child), beforeIndex);
  }

  /**
   * Convenience overload to allow {@link IsWidget} to be used directly.
   */
  public void insert(IsWidget child, IsWidget tab, int beforeIndex) {
    insert(asWidgetOrNull(child), asWidgetOrNull(tab), beforeIndex);
  }

  /**
   * Convenience overload to allow {@link IsWidget} to be used directly.
   */
  public void insert(IsWidget child, String text, boolean asHtml, int beforeIndex) {
    insert(asWidgetOrNull(child), text, asHtml, beforeIndex);
  }

  /**
   * Convenience overload to allow {@link IsWidget} to be used directly.
   */
  public void insert(IsWidget child, String text, int beforeIndex) {
    insert(asWidgetOrNull(child), text, beforeIndex);
  }

  /**
   * Inserts a widget into the panel. If the Widget is already attached, it will
   * be moved to the requested index.
   *
   * @param child the widget to be added
   * @param beforeIndex the index before which it will be inserted
   */
  public void insert(Widget child, int beforeIndex) {
    insert(child, "", beforeIndex);
  }

  /**
   * Inserts a widget into the panel. If the Widget is already attached, it will
   * be moved to the requested index.
   *
   * @param child the widget to be added
   * @param html the html to be shown on its tab
   * @param beforeIndex the index before which it will be inserted
   */
  public void insert(Widget child, SafeHtml html, int beforeIndex) {
    insert(child, html.asString(), true, beforeIndex);
  }

  /**
   * Inserts a widget into the panel. If the Widget is already attached, it will
   * be moved to the requested index.
   *
   * @param child the widget to be added
   * @param text the text to be shown on its tab
   * @param asHtml <code>true</code> to treat the specified text as HTML
   * @param beforeIndex the index before which it will be inserted
   */
  public void insert(Widget child, String text, boolean asHtml, int beforeIndex) {
    Widget contents;
    if (asHtml) {
      contents = new HTML(text);
    } else {
      contents = new Label(text);
    }
    insert(child, contents, beforeIndex);
  }

  /**
   * Inserts a widget into the panel. If the Widget is already attached, it will
   * be moved to the requested index.
   *
   * @param child the widget to be added
   * @param text the text to be shown on its tab
   * @param beforeIndex the index before which it will be inserted
   */
  public void insert(Widget child, String text, int beforeIndex) {
    insert(child, text, false, beforeIndex);
  }

  /**
   * Inserts a widget into the panel. If the Widget is already attached, it will
   * be moved to the requested index.
   *
   * @param child the widget to be added
   * @param tab the widget to be placed in the associated tab
   * @param beforeIndex the index before which it will be inserted
   */
  public void insert(Widget child, Widget tab, int beforeIndex) {
    insert(child, new Tab(tab), beforeIndex);
  }

  public Iterator<Widget> iterator() {
    return children.iterator();
  }

  public boolean remove(int index) {
    if ((index < 0) || (index >= getWidgetCount())) {
      return false;
    }

    Widget child = children.get(index);
    tabBar.remove(index);
    panel.remove(child);
    child.removeStyleName(CONTENT_STYLE);

    children.remove(index);
    tabs.remove(index);

    if (index == selectedIndex) {
      // If the selected tab is being removed, select the first tab (if there
      // is one).
      selectedIndex = -1;
      if (getWidgetCount() > 0) {
        selectTab(0);
      }
    } else if (index < selectedIndex) {
      // If the selectedIndex is greater than the one being removed, it needs
      // to be adjusted.
      --selectedIndex;
    }
    return true;
  }

  public boolean remove(Widget w) {
    int index = children.indexOf(w);
    if (index == -1) {
      return false;
    }

    return remove(index);
  }

  /**
   * Programmatically selects the specified tab and fires events.
   *
   * @param index the index of the tab to be selected
   */
  public void selectTab(int index) {
    selectTab(index, true);
  }

  /**
   * Programmatically selects the specified tab.
   *
   * @param index the index of the tab to be selected
   * @param fireEvents true to fire events, false not to
   */
  public void selectTab(int index, boolean fireEvents) {
    checkIndex(index);
    if (index == selectedIndex) {
      return;
    }

    // Fire the before selection event, giving the recipients a chance to
    // cancel the selection.
    if (fireEvents) {
      BeforeSelectionEvent<Integer> event = BeforeSelectionEvent.fire(this,
          index);
      if ((event != null) && event.isCanceled()) {
        return;
      }
    }

    // Update the tabs being selected and unselected.
    if (selectedIndex != -1) {
      Widget child = children.get(selectedIndex);
      panel.setWidgetVisible(child, false);
      tabs.get(selectedIndex).setSelected(false);
    }

    Widget child = children.get(index);
    panel.setWidgetVisible(child, true);
    tabs.get(index).setSelected(true);
    selectedIndex = index;

    // Fire the selection event.
    if (fireEvents) {
      SelectionEvent.fire(this, index);
    }
  }

  /**
   * Convenience overload to allow {@link IsWidget} to be used directly.
   */
  public void selectTab(IsWidget child) {
    selectTab(asWidgetOrNull(child));
  }

  /**
   * Convenience overload to allow {@link IsWidget} to be used directly.
   */
  public void selectTab(IsWidget child, boolean fireEvents) {
    selectTab(asWidgetOrNull(child), fireEvents);
  }

  /**
   * Programmatically selects the specified tab and fires events.
   *
   * @param child the child whose tab is to be selected
   */
  public void selectTab(Widget child) {
    selectTab(getWidgetIndex(child));
  }

  /**
   * Programmatically selects the specified tab.
   *
   * @param child the child whose tab is to be selected
   * @param fireEvents true to fire events, false not to
   */
  public void selectTab(Widget child, boolean fireEvents) {
    selectTab(getWidgetIndex(child), fireEvents);
  }

  /**
   * Sets a tab's HTML contents.
   *
   * Use care when setting an object's HTML; it is an easy way to expose
   * script-based security problems. Consider using
   * {@link #setTabHTML(int, SafeHtml)} or
   * {@link #setTabText(int, String)} whenever possible.
   *
   * @param index the index of the tab whose HTML is to be set
   * @param html the tab's new HTML contents
   */
  public void setTabHTML(int index, String html) {
    checkIndex(index);
    tabs.get(index).setWidget(new HTML(html));
  }

  /**
   * Sets a tab's HTML contents.
   *
   * @param index the index of the tab whose HTML is to be set
   * @param html the tab's new HTML contents
   */
  public void setTabHTML(int index, SafeHtml html) {
    setTabHTML(index, html.asString());
  }

  /**
   * Sets a tab's text contents.
   *
   * @param index the index of the tab whose text is to be set
   * @param text the object's new text
   */
  public void setTabText(int index, String text) {
    checkIndex(index);
    tabs.get(index).setWidget(new Label(text));
  }

  private void checkChild(Widget child) {
    assert children.contains(child);
  }

  private void checkIndex(int index) {
    assert (index >= 0) && (index < children.size()) : "Index out of bounds";
  }

  private void insert(final Widget child, Tab tab, int beforeIndex) {
    assert (beforeIndex >= 0) && (beforeIndex <= getWidgetCount()) : "beforeIndex out of bounds";

    // Check to see if the TabPanel already contains the Widget. If so,
    // remove it and see if we need to shift the position to the left.
    int idx = getWidgetIndex(child);
    if (idx != -1) {
      remove(child);
      if (idx < beforeIndex) {
        beforeIndex--;
      }
    }

    children.insert(child, beforeIndex);
    tabs.add(beforeIndex, tab);

    tabBar.insert(tab, beforeIndex);
    tab.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        selectTab(child);
      }
    });

    panel.insert(child, beforeIndex);
    layoutChild(child);

    if (selectedIndex == -1) {
      selectTab(0);
    } else if (selectedIndex >= beforeIndex) {
      // If we inserted before the currently selected tab, its index has just
      // increased.
      selectedIndex++;
    }
  }

  private void layoutChild(Widget child) {
    panel.setWidgetLeftRight(child, 0, Unit.PX, 0, Unit.PX);
    panel.setWidgetTopBottom(child, barHeight, barUnit, 0, Unit.PX);
    panel.setWidgetVisible(child, false);
    child.addStyleName(CONTENT_STYLE);
    child.setVisible(false);
  }
}
