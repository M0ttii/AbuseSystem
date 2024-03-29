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
package com.google.gwt.uibinder.elementparsers;

import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.uibinder.rebind.MortalLogger;
import com.google.gwt.uibinder.rebind.XMLElement;

/**
 * Interprets generic message tags like:
 * <b>&lt;ui:text from="{myMsg.message}" /&gt;</b>. It's called in both hasText
 * and hasHTML context.
 */
public class UiTextInterpreter implements XMLElement.Interpreter<String> {

  private static final String BINDER_URI = "urn:ui:com.google.gwt.uibinder";
  private static final String LOCAL_NAME = "text";

  private final MortalLogger logger;

  public UiTextInterpreter(MortalLogger logger) {
    this.logger = logger;
  }

  public String interpretElement(XMLElement elem)
      throws UnableToCompleteException {
   // Must be in the format: <ui:string from="{myMsg.message}" />
   if (BINDER_URI.equals(elem.getNamespaceUri())
        && LOCAL_NAME.equals(elem.getLocalName())) {
      String fieldRef = elem.consumeStringAttribute("from");
      if (fieldRef == null) {
        logger.die(elem, "Attribute 'from' not found.");
      }
      return "\" + " + fieldRef + " + \"";
    }
    return null;
  }
}
