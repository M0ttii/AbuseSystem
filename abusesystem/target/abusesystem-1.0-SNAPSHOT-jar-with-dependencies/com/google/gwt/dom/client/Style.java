/*
 * Copyright 2008 Google Inc.
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
package com.google.gwt.dom.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Provides programmatic access to properties of the style object.
 * 
 * <p>
 * Note that if a property or value is not explicitly enumerated in this
 * class, you can still access it via {@link #getProperty(String)},
 * and {@link #setProperty(String, String)}.
 * </p>
 * 
 * @see Element#getStyle()
 */
public class Style extends JavaScriptObject {

  /**
   * Interface to be implemented by enumerated CSS values.
   */
  public interface HasCssName {

    /**
     * Gets the CSS name associated with this value.
     */
    String getCssName();
  }

  /**
   * CSS length units.
   */
  public enum Unit {
    PX {
      @Override
      public String getType() {
        return UNIT_PX;
      }
    }, PCT {
      @Override
      public String getType() {
        return UNIT_PCT;
      }
    }, EM {
      @Override
      public String getType() {
        return UNIT_EM;
      }
    }, EX {
      @Override
      public String getType() {
        return UNIT_EX;
      }
    }, PT {
      @Override
      public String getType() {
        return UNIT_PT;
      }
    }, PC {
      @Override
      public String getType() {
        return UNIT_PC;
      }
    }, IN {
      @Override
      public String getType() {
        return UNIT_IN;
      }
    }, CM {
      @Override
      public String getType() {
        return UNIT_CM;
      }
    }, MM {
      @Override
      public String getType() {
        return UNIT_MM;
      }
    };

    public abstract String getType();
  }

  /**
   * Enum for the border-style property.
   */
  public enum BorderStyle implements HasCssName {
    NONE {
      @Override
      public String getCssName() {
        return BORDER_STYLE_NONE;
      }
    },
    DOTTED {
      @Override
      public String getCssName() {
        return BORDER_STYLE_DOTTED;
      }
    },
    DASHED {
      @Override
      public String getCssName() {
        return BORDER_STYLE_DASHED;
      }
    },
    HIDDEN {
      @Override
      public String getCssName() {
        return BORDER_STYLE_HIDDEN;
      }
    },
    SOLID {
      @Override
      public String getCssName() {
        return BORDER_STYLE_SOLID;
      }
    };
    public abstract String getCssName();
  }

  /**
   * Enum for the cursor property.
   */
  public enum Cursor implements HasCssName {
    DEFAULT {
      @Override
      public String getCssName() {
        return CURSOR_DEFAULT;
      }
    },
    AUTO {
      @Override
      public String getCssName() {
        return CURSOR_AUTO;
      }
    },
    CROSSHAIR {
      @Override
      public String getCssName() {
        return CURSOR_CROSSHAIR;
      }
    },
    POINTER {
      @Override
      public String getCssName() {
        return CURSOR_POINTER;
      }
    },
    MOVE {
      @Override
      public String getCssName() {
        return CURSOR_MOVE;
      }
    },
    E_RESIZE {
      @Override
      public String getCssName() {
        return CURSOR_E_RESIZE;
      }
    },
    NE_RESIZE {
      @Override
      public String getCssName() {
        return CURSOR_NE_RESIZE;
      }
    },
    NW_RESIZE {
      @Override
      public String getCssName() {
        return CURSOR_NW_RESIZE;
      }
    },
    N_RESIZE {
      @Override
      public String getCssName() {
        return CURSOR_N_RESIZE;
      }
    },
    SE_RESIZE {
      @Override
      public String getCssName() {
        return CURSOR_SE_RESIZE;
      }
    },
    SW_RESIZE {
      @Override
      public String getCssName() {
        return CURSOR_SW_RESIZE;
      }
    },
    S_RESIZE {
      @Override
      public String getCssName() {
        return CURSOR_S_RESIZE;
      }
    },
    W_RESIZE {
      @Override
      public String getCssName() {
        return CURSOR_W_RESIZE;
      }
    },
    TEXT {
      @Override
      public String getCssName() {
        return CURSOR_TEXT;
      }
    },
    WAIT {
      @Override
      public String getCssName() {
        return CURSOR_WAIT;
      }
    },
    HELP {
      @Override
      public String getCssName() {
        return CURSOR_HELP;
      }
    },
    COL_RESIZE {
      @Override
      public String getCssName() {
        return CURSOR_COL_RESIZE;
      }
    },
    ROW_RESIZE {
      @Override
      public String getCssName() {
        return CURSOR_ROW_RESIZE;
      }
    };
    public abstract String getCssName();
  }

  /**
   * Enum for the display property.
   */
  public enum Display implements HasCssName {
    NONE {
      @Override
      public String getCssName() {
        return DISPLAY_NONE;
      }
    },
    BLOCK {
      @Override
      public String getCssName() {
        return DISPLAY_BLOCK;
      }
    },
    INLINE {
      @Override
      public String getCssName() {
        return DISPLAY_INLINE;
      }
    },
    INLINE_BLOCK {
      @Override
      public String getCssName() {
        return DISPLAY_INLINE_BLOCK;
      }
    };
    public abstract String getCssName();
  }

  /**
   * Enum for the float property.
   */
  public enum Float implements HasCssName {
    LEFT {
      @Override
      public String getCssName() {
        return FLOAT_LEFT;
      }
    },
    RIGHT {
      @Override
      public String getCssName() {
        return FLOAT_RIGHT;
      }
    },
    NONE {
      @Override
      public String getCssName() {
        return FLOAT_NONE;
      }
    };
    public abstract String getCssName();
  }

  /**
   * Enum for the font-style property.
   */
  public enum FontStyle implements HasCssName {
    NORMAL {
      @Override
      public String getCssName() {
        return FONT_STYLE_NORMAL;
      }
    },
    ITALIC {
      @Override
      public String getCssName() {
        return FONT_STYLE_ITALIC;
      }
    },
    OBLIQUE {
      @Override
      public String getCssName() {
        return FONT_STYLE_OBLIQUE;
      }
    };
    public abstract String getCssName();
  }

  /**
   * Enum for the font-weight property.
   */
  public enum FontWeight implements HasCssName {
    NORMAL {
      @Override
      public String getCssName() {
        return FONT_WEIGHT_NORMAL;
      }
    },
    BOLD {
      @Override
      public String getCssName() {
        return FONT_WEIGHT_BOLD;
      }
    },
    BOLDER {
      @Override
      public String getCssName() {
        return FONT_WEIGHT_BOLDER;
      }
    },
    LIGHTER {
      @Override
      public String getCssName() {
        return FONT_WEIGHT_LIGHTER;
      }
    };
    public abstract String getCssName();
  }

  /**
   * Enum for the list-style-type property.
   */
  public enum ListStyleType implements HasCssName {
    NONE {
      @Override
      public String getCssName() {
        return LIST_STYLE_TYPE_NONE;
      }
    },
    DISC {
      @Override
      public String getCssName() {
        return LIST_STYLE_TYPE_DISC;
      }
    },
    CIRCLE {
      @Override
      public String getCssName() {
        return LIST_STYLE_TYPE_CIRCLE;
      }
    },
    SQUARE {
      @Override
      public String getCssName() {
        return LIST_STYLE_TYPE_SQUARE;
      }
    },
    DECIMAL {
      @Override
      public String getCssName() {
        return LIST_STYLE_TYPE_DECIMAL;
      }
    },
    LOWER_ALPHA {
      @Override
      public String getCssName() {
        return LIST_STYLE_TYPE_LOWER_ALPHA;
      }
    },
    UPPER_ALPHA {
      @Override
      public String getCssName() {
        return LIST_STYLE_TYPE_UPPER_ALPHA;
      }
    },
    LOWER_ROMAN {
      @Override
      public String getCssName() {
        return LIST_STYLE_TYPE_LOWER_ROMAN;
      }
    },
    UPPER_ROMAN {
      @Override
      public String getCssName() {
        return LIST_STYLE_TYPE_UPPER_ROMAN;
      }
    };
    public abstract String getCssName();
  }

  /**
   * Enum for the overflow property.
   */
  public enum Overflow implements HasCssName {
    VISIBLE {
      @Override
      public String getCssName() {
        return OVERFLOW_VISIBLE;
      }
    },
    HIDDEN {
      @Override
      public String getCssName() {
        return OVERFLOW_HIDDEN;
      }
    },
    SCROLL {
      @Override
      public String getCssName() {
        return OVERFLOW_SCROLL;
      }
    },
    AUTO {
      @Override
      public String getCssName() {
        return OVERFLOW_AUTO;
      }
    };
    public abstract String getCssName();
  }

  /**
   * Enum for the display property.
   */
  public enum Position implements HasCssName {
    STATIC {
      @Override
      public String getCssName() {
        return POSITION_STATIC;
      }
    },
    RELATIVE {
      @Override
      public String getCssName() {
        return POSITION_RELATIVE;
      }
    },
    ABSOLUTE {
      @Override
      public String getCssName() {
        return POSITION_ABSOLUTE;
      }
    },
    FIXED {
      @Override
      public String getCssName() {
        return POSITION_FIXED;
      }
    };
    public abstract String getCssName();
  }

  /**
   * Enum for the text-decoration property.
   */
  public enum TextDecoration implements HasCssName {
    NONE {
      @Override
      public String getCssName() {
        return TEXT_DECORATION_NONE;
      }
    },
    UNDERLINE {
      @Override
      public String getCssName() {
        return TEXT_DECORATION_UNDERLINE;
      }
    },
    OVERLINE {
      @Override
      public String getCssName() {
        return TEXT_DECORATION_OVERLINE;
      }
    },
    LINE_THROUGH {
      @Override
      public String getCssName() {
        return TEXT_DECORATION_LINE_THROUGH;
      }
    };
    public abstract String getCssName();
  }

  /**
   * Enum for the vertical-align property.
   */
  public enum VerticalAlign implements HasCssName {
    BASELINE {
      @Override
      public String getCssName() {
        return VERTICAL_ALIGN_BASELINE;
      }
    },
    SUB {
      @Override
      public String getCssName() {
        return VERTICAL_ALIGN_SUB;
      }
    },
    SUPER {
      @Override
      public String getCssName() {
        return VERTICAL_ALIGN_SUPER;
      }
    },
    TOP {
      @Override
      public String getCssName() {
        return VERTICAL_ALIGN_TOP;
      }
    },
    TEXT_TOP {
      @Override
      public String getCssName() {
        return VERTICAL_ALIGN_TEXT_TOP;
      }
    },
    MIDDLE {
      @Override
      public String getCssName() {
        return VERTICAL_ALIGN_MIDDLE;
      }
    },
    BOTTOM {
      @Override
      public String getCssName() {
        return VERTICAL_ALIGN_BOTTOM;
      }
    },
    TEXT_BOTTOM {
      @Override
      public String getCssName() {
        return VERTICAL_ALIGN_TEXT_BOTTOM;
      }
    };
    public abstract String getCssName();
  }

  /**
   * Enum for the visibility property.
   */
  public enum Visibility implements HasCssName {
    VISIBLE {
      @Override
      public String getCssName() {
        return VISIBILITY_VISIBLE;
      }
    },
    HIDDEN {
      @Override
      public String getCssName() {
        return VISIBILITY_HIDDEN;
      }
    };
    public abstract String getCssName();
  }

  private static final String BORDER_STYLE_SOLID = "solid";
  private static final String BORDER_STYLE_DASHED = "dashed";
  private static final String BORDER_STYLE_DOTTED = "dotted";
  private static final String BORDER_STYLE_HIDDEN = "hidden";
  private static final String BORDER_STYLE_NONE = "none";

  private static final String CURSOR_ROW_RESIZE = "row-resize";
  private static final String CURSOR_COL_RESIZE = "col-resize";
  private static final String CURSOR_HELP = "help";
  private static final String CURSOR_WAIT = "wait";
  private static final String CURSOR_TEXT = "text";
  private static final String CURSOR_W_RESIZE = "w-resize";
  private static final String CURSOR_S_RESIZE = "s-resize";
  private static final String CURSOR_SW_RESIZE = "sw-resize";
  private static final String CURSOR_SE_RESIZE = "se-resize";
  private static final String CURSOR_N_RESIZE = "n-resize";
  private static final String CURSOR_NW_RESIZE = "nw-resize";
  private static final String CURSOR_NE_RESIZE = "ne-resize";
  private static final String CURSOR_E_RESIZE = "e-resize";
  private static final String CURSOR_MOVE = "move";
  private static final String CURSOR_POINTER = "pointer";
  private static final String CURSOR_CROSSHAIR = "crosshair";
  private static final String CURSOR_AUTO = "auto";
  private static final String CURSOR_DEFAULT = "default";

  private static final String DISPLAY_INLINE_BLOCK = "inline-block";
  private static final String DISPLAY_INLINE = "inline";
  private static final String DISPLAY_BLOCK = "block";
  private static final String DISPLAY_NONE = "none";

  private static final String FLOAT_LEFT = "left";
  private static final String FLOAT_RIGHT = "right";
  private static final String FLOAT_NONE = "none";

  private static final String FONT_STYLE_OBLIQUE = "oblique";
  private static final String FONT_STYLE_ITALIC = "italic";
  private static final String FONT_STYLE_NORMAL = "normal";

  private static final String FONT_WEIGHT_LIGHTER = "lighter";
  private static final String FONT_WEIGHT_BOLDER = "bolder";
  private static final String FONT_WEIGHT_BOLD = "bold";
  private static final String FONT_WEIGHT_NORMAL = "normal";

  private static final String LIST_STYLE_TYPE_UPPER_ROMAN = "upper-roman";
  private static final String LIST_STYLE_TYPE_LOWER_ROMAN = "lower-roman";
  private static final String LIST_STYLE_TYPE_UPPER_ALPHA = "upper-alpha";
  private static final String LIST_STYLE_TYPE_LOWER_ALPHA = "lower-alpha";
  private static final String LIST_STYLE_TYPE_DECIMAL = "decimal";
  private static final String LIST_STYLE_TYPE_SQUARE = "square";
  private static final String LIST_STYLE_TYPE_CIRCLE = "circle";
  private static final String LIST_STYLE_TYPE_DISC = "disc";
  private static final String LIST_STYLE_TYPE_NONE = "none";

  private static final String OVERFLOW_AUTO = "auto";
  private static final String OVERFLOW_SCROLL = "scroll";
  private static final String OVERFLOW_HIDDEN = "hidden";
  private static final String OVERFLOW_VISIBLE = "visible";

  private static final String POSITION_FIXED = "fixed";
  private static final String POSITION_ABSOLUTE = "absolute";
  private static final String POSITION_RELATIVE = "relative";
  private static final String POSITION_STATIC = "static";

  private static final String STYLE_Z_INDEX = "zIndex";
  private static final String STYLE_WIDTH = "width";
  private static final String STYLE_VISIBILITY = "visibility";
  private static final String STYLE_TOP = "top";
  private static final String STYLE_TEXT_DECORATION = "textDecoration";
  private static final String STYLE_RIGHT = "right";
  private static final String STYLE_POSITION = "position";
  private static final String STYLE_PADDING_TOP = "paddingTop";
  private static final String STYLE_PADDING_RIGHT = "paddingRight";
  private static final String STYLE_PADDING_LEFT = "paddingLeft";
  private static final String STYLE_PADDING_BOTTOM = "paddingBottom";
  private static final String STYLE_PADDING = "padding";
  private static final String STYLE_OVERFLOW = "overflow";
  private static final String STYLE_OPACITY = "opacity";
  private static final String STYLE_MARGIN_TOP = "marginTop";
  private static final String STYLE_MARGIN_RIGHT = "marginRight";
  private static final String STYLE_MARGIN_LEFT = "marginLeft";
  private static final String STYLE_MARGIN_BOTTOM = "marginBottom";
  private static final String STYLE_MARGIN = "margin";
  private static final String STYLE_LIST_STYLE_TYPE = "listStyleType";
  private static final String STYLE_LEFT = "left";
  private static final String STYLE_HEIGHT = "height";
  private static final String STYLE_FONT_WEIGHT = "fontWeight";
  private static final String STYLE_FONT_STYLE = "fontStyle";
  private static final String STYLE_FONT_SIZE = "fontSize";
  private static final String STYLE_DISPLAY = "display";
  private static final String STYLE_CURSOR = "cursor";
  private static final String STYLE_COLOR = "color";
  private static final String STYLE_BOTTOM = "bottom";
  private static final String STYLE_BORDER_WIDTH = "borderWidth";
  private static final String STYLE_BORDER_STYLE = "borderStyle";
  private static final String STYLE_BORDER_COLOR = "borderColor";
  private static final String STYLE_BACKGROUND_IMAGE = "backgroundImage";
  private static final String STYLE_BACKGROUND_COLOR = "backgroundColor";
  private static final String STYLE_VERTICAL_ALIGN = "verticalAlign";

  private static final String TEXT_DECORATION_LINE_THROUGH = "line-through";
  private static final String TEXT_DECORATION_OVERLINE = "overline";
  private static final String TEXT_DECORATION_UNDERLINE = "underline";
  private static final String TEXT_DECORATION_NONE = "none";

  private static final String UNIT_MM = "mm";
  private static final String UNIT_CM = "cm";
  private static final String UNIT_IN = "in";
  private static final String UNIT_PC = "pc";
  private static final String UNIT_PT = "pt";
  private static final String UNIT_EX = "ex";
  private static final String UNIT_EM = "em";
  private static final String UNIT_PCT = "%";
  private static final String UNIT_PX = "px";

  private static final String VERTICAL_ALIGN_BASELINE = "baseline";
  private static final String VERTICAL_ALIGN_SUB = "sub";
  private static final String VERTICAL_ALIGN_SUPER = "super";
  private static final String VERTICAL_ALIGN_TOP = "top";
  private static final String VERTICAL_ALIGN_TEXT_TOP = "text-top";
  private static final String VERTICAL_ALIGN_MIDDLE = "middle";
  private static final String VERTICAL_ALIGN_BOTTOM = "bottom";
  private static final String VERTICAL_ALIGN_TEXT_BOTTOM = "text-bottom";

  private static final String VISIBILITY_HIDDEN = "hidden";
  private static final String VISIBILITY_VISIBLE = "visible";

  protected Style() {
  }

  /**
   * Clear the background-color css property.
   */
  public final void clearBackgroundColor() {
     clearProperty(STYLE_BACKGROUND_COLOR);
   }

  /**
   * Clear the background-image css property.
   */
  public final void clearBackgroundImage() {
     clearProperty(STYLE_BACKGROUND_IMAGE);
   }

  /**
   * Clear the border-color css property.
   */
  public final void clearBorderColor() {
     clearProperty(STYLE_BORDER_COLOR);
   }

  /**
   * Clears the border-style CSS property.
   */
  public final void clearBorderStyle() {
    clearProperty(STYLE_BORDER_STYLE);
  }

  /**
   * Clear the border-width css property.
   */
  public final void clearBorderWidth() {
     clearProperty(STYLE_BORDER_WIDTH);
   }

  /**
   * Clear the bottom css property.
   */
  public final void clearBottom() {
     clearProperty(STYLE_BOTTOM);
   }

  /**
   * Clear the color css property.
   */
  public final void clearColor() {
     clearProperty(STYLE_COLOR);
   }

  /**
   * Clears the cursor CSS property.
   */
  public final void clearCursor() {
    clearProperty(STYLE_CURSOR);
  }

  /**
   * Clears the display CSS property.
   */
  public final void clearDisplay() {
    clearProperty(STYLE_DISPLAY);
  }

  /**
   * Clear the font-size css property.
   */
  public final void clearFloat() {
    clearProperty(DOMImpl.impl.cssFloatPropertyName());
  }

  /**
   * Clear the font-size css property.
   */
  public final void clearFontSize() {
    clearProperty(STYLE_FONT_SIZE);
  }

  /**
   * Clears the font-style CSS property.
   */
  public final void clearFontStyle() {
    clearProperty(STYLE_FONT_STYLE);
  }

  /**
   * Clears the font-weight CSS property.
   */
  public final void clearFontWeight() {
    clearProperty(STYLE_FONT_WEIGHT);
  }

  /**
   * Clear the height css property.
   */
  public final void clearHeight() {
     clearProperty(STYLE_HEIGHT);
   }

  /**
   * Clear the left css property.
   */
  public final void clearLeft() {
     clearProperty(STYLE_LEFT);
   }

  /**
   * Clears the list-style-type CSS property.
   */
  public final void clearListStyleType() {
    clearProperty(STYLE_LIST_STYLE_TYPE);
  }

  /**
   * Clear the margin css property.
   */
  public final void clearMargin() {
     clearProperty(STYLE_MARGIN);
   }

  /**
   * Clear the margin-bottom css property.
   */
  public final void clearMarginBottom() {
     clearProperty(STYLE_MARGIN_BOTTOM);
   }

  /**
   * Clear the margin-left css property.
   */
  public final void clearMarginLeft() {
     clearProperty(STYLE_MARGIN_LEFT);
   }

  /**
   * Clear the margin-right css property.
   */
  public final void clearMarginRight() {
     clearProperty(STYLE_MARGIN_RIGHT);
   }

  /**
   * Clear the margin-top css property.
   */
  public final void clearMarginTop() {
     clearProperty(STYLE_MARGIN_TOP);
   }

  /**
   * Clear the opacity css property.
   */
  public final void clearOpacity() {
    DOMImpl.impl.cssClearOpacity(this);
  }

  /**
   * Clears the overflow CSS property.
   */
  public final void clearOverflow() {
    clearProperty(STYLE_OVERFLOW);
  }

  /**
   * Clear the padding css property.
   */
  public final void clearPadding() {
     clearProperty(STYLE_PADDING);
   }

  /**
   * Clear the padding-bottom css property.
   */
  public final void clearPaddingBottom() {
     clearProperty(STYLE_PADDING_BOTTOM);
   }

  /**
   * Clear the padding-left css property.
   */
  public final void clearPaddingLeft() {
     clearProperty(STYLE_PADDING_LEFT);
   }

  /**
   * Clear the padding-right css property.
   */
  public final void clearPaddingRight() {
     clearProperty(STYLE_PADDING_RIGHT);
   }

  /**
   * Clear the padding-top css property.
   */
  public final void clearPaddingTop() {
     clearProperty(STYLE_PADDING_TOP);
   }

  /**
   * Clears the position CSS property.
   */
  public final void clearPosition() {
    clearProperty(STYLE_POSITION);
  }

  /**
   * Clears the value of a named property, causing it to revert to its default.
   */
  public final void clearProperty(String name) {
    setProperty(name, "");
  }

  /**
   * Clear the right css property.
   */
  public final void clearRight() {
     clearProperty(STYLE_RIGHT);
   }

  /**
   * Clears the text-decoration CSS property.
   */
  public final void clearTextDecoration() {
    clearProperty(STYLE_TEXT_DECORATION);
  }

  /**
   * Clear the top css property.
   */
  public final void clearTop() {
     clearProperty(STYLE_TOP);
   }

  /**
   * Clears the visibility CSS property.
   */
  public final void clearVisibility() {
    clearProperty(STYLE_VISIBILITY);
  }

  /**
   * Clear the width css property.
   */
  public final void clearWidth() {
     clearProperty(STYLE_WIDTH);
   }

  /**
   * Clear the z-index css property.
   */
  public final void clearZIndex() {
     clearProperty(STYLE_Z_INDEX);
   }

  /**
   * Get the background-color css property.
   */
  public final String getBackgroundColor() {
    return getProperty(STYLE_BACKGROUND_COLOR);
  }

  /**
   * Get the background-image css property.
   */
  public final String getBackgroundImage() {
    return getProperty(STYLE_BACKGROUND_IMAGE);
  }

  /**
   * Get the border-color css property.
   */
  public final String getBorderColor() {
    return getProperty(STYLE_BORDER_COLOR);
  }

  /**
   * Gets the border-style CSS property.
   */
  public final String getBorderStyle() {
    return getProperty(STYLE_BORDER_STYLE);
  }

  /**
   * Get the border-width css property.
   */
  public final String getBorderWidth() {
    return getProperty(STYLE_BORDER_WIDTH);
  }

  /**
   * Get the bottom css property.
   */
  public final String getBottom() {
    return getProperty(STYLE_BOTTOM);
  }

  /**
   * Get the color css property.
   */
  public final String getColor() {
    return getProperty(STYLE_COLOR);
  }

  /**
   * Gets the cursor CSS property.
   */
  public final String getCursor() {
    return getProperty(STYLE_CURSOR);
  }

  /**
   * Gets the display CSS property.
   */
  public final String getDisplay() {
    return getProperty(STYLE_DISPLAY);
  }

  /**
   * Get the font-size css property.
   */
  public final String getFontSize() {
    return getProperty(STYLE_FONT_SIZE);
  }

  /**
   * Gets the font-style CSS property.
   */
  public final String getFontStyle() {
    return getProperty(STYLE_FONT_STYLE);
  }

  /**
   * Gets the font-weight CSS property.
   */
  public final String getFontWeight() {
    return getProperty(STYLE_FONT_WEIGHT);
  }

  /**
   * Get the height css property.
   */
  public final String getHeight() {
    return getProperty(STYLE_HEIGHT);
  }

  /**
   * Get the left css property.
   */
  public final String getLeft() {
    return getProperty(STYLE_LEFT);
  }

  /**
   * Gets the list-style-type CSS property.
   */
  public final String getListStyleType() {
    return getProperty(STYLE_LIST_STYLE_TYPE);
  }

  /**
   * Get the margin css property.
   */
  public final String getMargin() {
    return getProperty(STYLE_MARGIN);
  }

  /**
   * Get the margin-bottom css property.
   */
  public final String getMarginBottom() {
    return getProperty(STYLE_MARGIN_BOTTOM);
  }

  /**
   * Get the margin-left css property.
   */
  public final String getMarginLeft() {
    return getProperty(STYLE_MARGIN_LEFT);
  }

  /**
   * Get the margin-right css property.
   */
  public final String getMarginRight() {
    return getProperty(STYLE_MARGIN_RIGHT);
  }

  /**
   * Get the margin-top css property.
   */
  public final String getMarginTop() {
    return getProperty(STYLE_MARGIN_TOP);
  }

  /**
   * Get the opacity css property.
   */
  public final String getOpacity() {
    return getProperty(STYLE_OPACITY);
  }

  /**
   * Gets the overflow CSS property.
   */
  public final String getOverflow() {
    return getProperty(STYLE_OVERFLOW);
  }

  /**
   * Get the padding css property.
   */
  public final String getPadding() {
    return getProperty(STYLE_PADDING);
  }

  /**
   * Get the padding-bottom css property.
   */
  public final String getPaddingBottom() {
    return getProperty(STYLE_PADDING_BOTTOM);
  }

  /**
   * Get the padding-left css property.
   */
  public final String getPaddingLeft() {
    return getProperty(STYLE_PADDING_LEFT);
  }

  /**
   * Get the padding-right css property.
   */
  public final String getPaddingRight() {
    return getProperty(STYLE_PADDING_RIGHT);
  }

  /**
   * Get the padding-top css property.
   */
  public final String getPaddingTop() {
    return getProperty(STYLE_PADDING_TOP);
  }

  /**
   * Gets the position CSS property.
   */
  public final String getPosition() {
    return getProperty(STYLE_POSITION);
  }

  /**
   * Gets the value of a named property.
   */
  public final String getProperty(String name) {
    assertCamelCase(name);
    return getPropertyImpl(name);
  }

  /**
   * Get the right css property.
   */
  public final String getRight() {
    return getProperty(STYLE_RIGHT);
  }

  /**
   * Gets the text-decoration CSS property.
   */
  public final String getTextDecoration() {
    return getProperty(STYLE_TEXT_DECORATION);
  }

  /**
   * Get the top css property.
   */
  public final String getTop() {
    return getProperty(STYLE_TOP);
  }

  /**
   * Gets the vertical-align CSS property.
   */
  public final String getVerticalAlign() {
    return getProperty(STYLE_VERTICAL_ALIGN);
  }

  /**
   * Gets the visibility CSS property.
   */
  public final String getVisibility() {
    return getProperty(STYLE_VISIBILITY);
  }

  /**
   * Get the width css property.
   */
  public final String getWidth() {
    return getProperty(STYLE_WIDTH);
  }

  /**
   * Get the z-index css property.
   */
  public final String getZIndex() {
    return getProperty(STYLE_Z_INDEX);
  }

  /**
   * Set the background-color css property.
   */
  public final void setBackgroundColor(String value) {
    setProperty(STYLE_BACKGROUND_COLOR, value);
  }

  /**
   * Set the background-image css property.
   */
  public final void setBackgroundImage(String value) {
    setProperty(STYLE_BACKGROUND_IMAGE, value);
  }

  /**
   * Set the border-color css property.
   */
  public final void setBorderColor(String value) {
    setProperty(STYLE_BORDER_COLOR, value);
  }

  /**
   * Sets the border-style CSS property.
   */
  public final void setBorderStyle(BorderStyle value) {
    setProperty(STYLE_BORDER_STYLE, value.getCssName());
  }

  /**
   * Set the border-width css property.
   */
  public final void setBorderWidth(double value, Unit unit) {
    setProperty(STYLE_BORDER_WIDTH, value, unit);
  }

  /**
   * Set the bottom css property.
   */
  public final void setBottom(double value, Unit unit) {
    setProperty(STYLE_BOTTOM, value, unit);
  }

  /**
   * Sets the color CSS property.
   */
  public final void setColor(String value) {
    setProperty(STYLE_COLOR, value);
  }

  /**
   * Sets the cursor CSS property.
   */
  public final void setCursor(Cursor value) {
    setProperty(STYLE_CURSOR, value.getCssName());
  }

  /**
   * Sets the display CSS property.
   */
  public final void setDisplay(Display value) {
    setProperty(STYLE_DISPLAY, value.getCssName());
  }

  /**
   * Set the float css property.
   */
  public final void setFloat(Float value) {
    setProperty(DOMImpl.impl.cssFloatPropertyName(), value.getCssName());
  }

  /**
   * Set the font-size css property.
   */
  public final void setFontSize(double value, Unit unit) {
    setProperty(STYLE_FONT_SIZE, value, unit);
  }

  /**
   * Sets the font-style CSS property.
   */
  public final void setFontStyle(FontStyle value) {
    setProperty(STYLE_FONT_STYLE, value.getCssName());
  }

  /**
   * Sets the font-weight CSS property.
   */
  public final void setFontWeight(FontWeight value) {
    setProperty(STYLE_FONT_WEIGHT, value.getCssName());
  }

  /**
   * Set the height css property.
   */
  public final void setHeight(double value, Unit unit) {
    setProperty(STYLE_HEIGHT, value, unit);
  }

  /**
   * Set the left css property.
   */
  public final void setLeft(double value, Unit unit) {
    setProperty(STYLE_LEFT, value, unit);
  }

  /**
   * Sets the list-style-type CSS property.
   */
  public final void setListStyleType(ListStyleType value) {
    setProperty(STYLE_LIST_STYLE_TYPE, value.getCssName());
  }

  /**
   * Set the margin css property.
   */
  public final void setMargin(double value, Unit unit) {
    setProperty(STYLE_MARGIN, value, unit);
  }

  /**
   * Set the margin-bottom css property.
   */
  public final void setMarginBottom(double value, Unit unit) {
    setProperty(STYLE_MARGIN_BOTTOM, value, unit);
  }

  /**
   * Set the margin-left css property.
   */
  public final void setMarginLeft(double value, Unit unit) {
    setProperty(STYLE_MARGIN_LEFT, value, unit);
  }

  /**
   * Set the margin-right css property.
   */
  public final void setMarginRight(double value, Unit unit) {
    setProperty(STYLE_MARGIN_RIGHT, value, unit);
  }

  /**
   * Set the margin-top css property.
   */
  public final void setMarginTop(double value, Unit unit) {
    setProperty(STYLE_MARGIN_TOP, value, unit);
  }

  /**
   * Set the opacity css property.
   */
  public final void setOpacity(double value) {
    DOMImpl.impl.cssSetOpacity(this, value);
  }

  /**
   * Sets the overflow CSS property.
   */
  public final void setOverflow(Overflow value) {
    setProperty(STYLE_OVERFLOW, value.getCssName());
  }

  /**
   * Set the padding css property.
   */
  public final void setPadding(double value, Unit unit) {
    setProperty(STYLE_PADDING, value, unit);
  }

  /**
   * Set the padding-bottom css property.
   */
  public final void setPaddingBottom(double value, Unit unit) {
    setProperty(STYLE_PADDING_BOTTOM, value, unit);
  }

  /**
   * Set the padding-left css property.
   */
  public final void setPaddingLeft(double value, Unit unit) {
    setProperty(STYLE_PADDING_LEFT, value, unit);
  }

  /**
   * Set the padding-right css property.
   */
  public final void setPaddingRight(double value, Unit unit) {
    setProperty(STYLE_PADDING_RIGHT, value, unit);
  }

  /**
   * Set the padding-top css property.
   */
  public final void setPaddingTop(double value, Unit unit) {
    setProperty(STYLE_PADDING_TOP, value, unit);
  }

  /**
   * Sets the position CSS property.
   */
  public final void setPosition(Position value) {
    setProperty(STYLE_POSITION, value.getCssName());
  }

  /**
   * Sets the value of a named property.
   */
  public final void setProperty(String name, String value) {
    assertCamelCase(name);
    setPropertyImpl(name, value);
  }

  /**
   * Sets the value of a named property in the specified units.
   */
  public final void setProperty(String name, double value, Unit unit) {
    assertCamelCase(name);
    setPropertyImpl(name, value + unit.getType());
  }

  /**
   * Sets the value of a named property, in pixels.
   * 
   * This is shorthand for <code>value + "px"</code>.
   */
  public final void setPropertyPx(String name, int value) {
    setProperty(name, value, Unit.PX);
  }

  /**
   * Set the right css property.
   */
  public final void setRight(double value, Unit unit) {
    setProperty(STYLE_RIGHT, value, unit);
  }

  /**
   * Sets the text-decoration CSS property.
   */
  public final void setTextDecoration(TextDecoration value) {
    setProperty(STYLE_TEXT_DECORATION, value.getCssName());
  }

  /**
   * Set the top css property.
   */
  public final void setTop(double value, Unit unit) {
    setProperty(STYLE_TOP, value, unit);
  }

  /**
   * Sets the vertical-align CSS property.
   */
  public final void setVerticalAlign(VerticalAlign value) {
    setProperty(STYLE_VERTICAL_ALIGN, value.getCssName());
  }

  /**
   * Sets the vertical-align CSS property.
   */
  public final void setVerticalAlign(double value, Unit unit) {
    setProperty(STYLE_VERTICAL_ALIGN, value, unit);
  }

  /**
   * Sets the visibility CSS property.
   */
  public final void setVisibility(Visibility value) {
    setProperty(STYLE_VISIBILITY, value.getCssName());
  }

  /**
   * Set the width css property.
   */
  public final void setWidth(double value, Unit unit) {
    setProperty(STYLE_WIDTH, value, unit);
  }

  /**
   * Set the z-index css property.
   */
  public final void setZIndex(int value) {
    setProperty(STYLE_Z_INDEX, value + "");
  }

  /**
   * Assert that the specified property does not contain a hyphen.
   * 
   * @param name the property name
   */
  private void assertCamelCase(String name) {
    assert !name.contains("-") : "The style name '" + name
        + "' should be in camelCase format";
  }

  /**
   * Gets the value of a named property.
   */
  private native String getPropertyImpl(String name) /*-{
    return this[name];
  }-*/;

  /**
   * Sets the value of a named property.
   */
  private native void setPropertyImpl(String name, String value) /*-{
    this[name] = value;
  }-*/;
}
