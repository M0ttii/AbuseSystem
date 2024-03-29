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
package com.google.gwt.resources.rg;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.resources.client.DataResource.MimeType;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.DataResource.DoNotEmbed;
import com.google.gwt.resources.ext.AbstractResourceGenerator;
import com.google.gwt.resources.ext.ResourceContext;
import com.google.gwt.resources.ext.ResourceGeneratorUtil;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.user.rebind.StringSourceWriter;

import java.net.URL;

/**
 * Provides implementations of DataResource.
 */
public final class DataResourceGenerator extends AbstractResourceGenerator {
  @Override
  public String createAssignment(TreeLogger logger, ResourceContext context,
      JMethod method) throws UnableToCompleteException {

    URL[] resources = ResourceGeneratorUtil.findResources(logger, context,
        method);

    if (resources.length != 1) {
      logger.log(TreeLogger.ERROR, "Exactly one resource must be specified",
          null);
      throw new UnableToCompleteException();
    }

    // Determine if a MIME Type has been specified
    MimeType mimeTypeAnnotation = method.getAnnotation(MimeType.class);
    String mimeType = mimeTypeAnnotation != null
        ? mimeTypeAnnotation.value() : null;

    // Determine if resource should not be embedded
    DoNotEmbed doNotEmbed = method.getAnnotation(DoNotEmbed.class);
    boolean forceExternal = (doNotEmbed != null);

    URL resource = resources[0];
    String outputUrlExpression = context.deploy(
        resource, mimeType, forceExternal);

    SourceWriter sw = new StringSourceWriter();
    // Write the expression to create the subtype.
    sw.println("new " + DataResource.class.getName() + "() {");
    sw.indent();

    // Convenience when examining the generated code.
    sw.println("// " + resource.toExternalForm());

    sw.println("public String getUrl() {");
    sw.indent();
    sw.println("return " + outputUrlExpression + ";");
    sw.outdent();
    sw.println("}");

    sw.println("public String getName() {");
    sw.indent();
    sw.println("return \"" + method.getName() + "\";");
    sw.outdent();
    sw.println("}");

    sw.outdent();
    sw.println("}");

    return sw.toString();
  }
}
