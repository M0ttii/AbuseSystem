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
package com.google.gwt.uibinder.attributeparsers;

import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.uibinder.rebind.MortalLogger;
import com.google.gwt.uibinder.rebind.XMLAttribute;
import com.google.gwt.uibinder.rebind.XMLElement;
import com.google.gwt.uibinder.rebind.model.OwnerClass;
import com.google.gwt.uibinder.rebind.model.OwnerField;
import com.google.gwt.uibinder.rebind.model.OwnerFieldClass;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manages BundleAttributeParsers, which provide the deprecated
 * res:style{style.pretty} feature.
 */
@Deprecated
public class BundleAttributeParsers {
  private static final String BUNDLE_URI_SCHEME = "urn:with:";

  private final TypeOracle oracle;
  private final MortalLogger logger;
  private final OwnerClass ownerClass;
  private final String templatePath;
  private final JClassType uiOwnerType;

  /**
   * Map of bundle parsers, keyed by bundle class name.
   */
  private final Map<String, BundleAttributeParser> parsers = new LinkedHashMap<String, BundleAttributeParser>();

  public BundleAttributeParsers(TypeOracle oracle, MortalLogger logger,
      OwnerClass ownerClass, String templatePath, JClassType uiOwnerType) {
    this.oracle = oracle;
    this.logger = logger;
    this.ownerClass = ownerClass;
    this.templatePath = templatePath;
    this.uiOwnerType = uiOwnerType;
  }

  public BundleAttributeParser get(OwnerFieldClass type) {
    return parsers.get(type.getRawType().getQualifiedSourceName());
  }

  public BundleAttributeParser get(XMLAttribute attribute)
      throws UnableToCompleteException {
    String attributePrefixUri = attribute.getNamespaceUri();

    if ((attributePrefixUri == null)
        || !attributePrefixUri.startsWith(BUNDLE_URI_SCHEME)) {
      return null;
    }

    String bundleClassName = attributePrefixUri.substring(BUNDLE_URI_SCHEME.length());
    BundleAttributeParser parser = parsers.get(bundleClassName);
    if (parser == null) {
      JClassType bundleClassType = getOracle().findType(bundleClassName);
      if (bundleClassType == null) {
        die(attribute.getElement(), "No such resource class: "
            + bundleClassName);
      }
      parser = createBundleParser(bundleClassType, attribute);
      parsers.put(bundleClassName, parser);
    }

    return parser;
  }

  public Map<String, BundleAttributeParser> getMap() {
    return Collections.unmodifiableMap(parsers);
  }

  /**
   * Creates a parser for the given bundle class. This method will die soon.
   */
  private BundleAttributeParser createBundleParser(JClassType bundleClass,
      XMLAttribute attribute) throws UnableToCompleteException {

    final String templateResourceName = attribute.getName().split(":")[0];
    warn(attribute.getElement(), "\"%s\" is deprecated by "
        + "<ui:with field='%s' type='%s.%s' />", BUNDLE_URI_SCHEME,
        templateResourceName, bundleClass.getPackage().getName(),
        bundleClass.getName());

    // Try to find any bundle instance created with UiField.
    OwnerField field = getOwnerClass().getUiFieldForType(bundleClass);
    if (field != null) {
      if (!templateResourceName.equals(field.getName())) {
        die(attribute.getElement(),
            "Template %s has no \"xmlns:%s='urn:with:%s'\" for %s.%s#%s",
            templatePath, field.getName(),
            bundleClass.getQualifiedSourceName(),
            uiOwnerType.getPackage().getName(), uiOwnerType.getName(),
            field.getName());
      }

      if (field.isProvided()) {
        return new BundleAttributeParser(bundleClass, "owner."
            + field.getName(), false);
      }
    }

    // Try to find any bundle instance created with @UiFactory.
    JMethod method = getOwnerClass().getUiFactoryMethod(bundleClass);
    if (method != null) {
      return new BundleAttributeParser(bundleClass, "owner." + method.getName()
          + "()", false);
    }

    return new BundleAttributeParser(bundleClass, "my"
        + bundleClass.getName().replace('.', '_') + "Instance", true);
  }

  private void die(XMLElement elem, String string, Object... params)
      throws UnableToCompleteException {
    logger.die(elem, string, params);
  }

  private TypeOracle getOracle() {
    return oracle;
  }

  private OwnerClass getOwnerClass() {
    return ownerClass;
  }

  private void warn(XMLElement elem, String string, Object... params) {
    logger.warn(elem, string, params);
  }
}
