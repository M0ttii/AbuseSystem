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
package com.google.gwt.resources.rebind.context;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.SelectionProperty;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JGenericType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.dev.generator.NameFactory;
import com.google.gwt.dev.util.Util;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.ResourcePrototype;
import com.google.gwt.resources.ext.ClientBundleFields;
import com.google.gwt.resources.ext.ClientBundleRequirements;
import com.google.gwt.resources.ext.ResourceContext;
import com.google.gwt.resources.ext.ResourceGenerator;
import com.google.gwt.resources.ext.ResourceGeneratorType;
import com.google.gwt.resources.rg.BundleResourceGenerator;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.user.rebind.StringSourceWriter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The base class for creating new ClientBundle implementations.
 * <p>
 * The general structure of the generated class is as follows:
 * 
 * <pre>
 * public ResourceType resource() {
 *   return resource;
 * }
 * private void _init0() {
 *   resource = new Resource();
 * }
 * // Other ResourceGenerator-defined fields
 * private static ResourceType resource;
 * private static HashMap&lt;String, ResourcePrototype&gt; resourceMap;
 * static {
 *   new ClientBundle()._init0();
 * }
 * public ResourcePrototype[] getResources() {
 *   return new ResourcePrototype[] { resource() };
 * }
 * public ResourcePrototype getResource(String name) {
 *   if (GWT.isScript()) {
 *     return getResourceNative(name);
 *   } else {
 *     if (resourceMap == null) {
 *       resourceMap = new HashMap<String, ResourcePrototype>();
 *       resourceMap.put("resource", resource());
 *     }
 *     return resourceMap.get(name);
 *   }
 * }
 * private native ResourcePrototype getResourceNative(String name) /-{
 *   switch (name) {
 *     case 'resource': return this.@...::resource()();
 *   }
 *   return null;
 * }-/
 * </pre>
 * The instantiation of the individual ResourcePrototypes is done in the content
 * of an instance of the ClientBundle type so that resources can refer to one
 * another by simply emitting a call to <code>resource()</code>.
 */
public abstract class AbstractClientBundleGenerator extends Generator {

  /**
   * An implementation of ClientBundleFields.
   */
  protected static class FieldsImpl implements ClientBundleFields {
    private final NameFactory factory = new NameFactory();
    /**
     * It is necessary to maintain order in case one field refers to another.
     */
    private final Map<String, String> fieldsToDeclarations = new LinkedHashMap<String, String>();
    private final Map<String, String> fieldsToInitializers = new HashMap<String, String>();

    public String define(JType type, String name) {
      return define(type, name, null, true, false);
    }

    public String define(JType type, String name, String initializer,
        boolean isStatic, boolean isFinal) {

      assert Util.isValidJavaIdent(name) : name
          + " is not a valid Java identifier";

      String ident = factory.createName(name);

      StringBuilder sb = new StringBuilder();
      sb.append("private ");

      if (isStatic) {
        sb.append("static ");
      }

      if (isFinal) {
        sb.append("final ");
      }

      sb.append(type.getParameterizedQualifiedSourceName());
      sb.append(" ");
      sb.append(ident);

      fieldsToDeclarations.put(ident, sb.toString());

      if (initializer != null) {
        fieldsToInitializers.put(ident, initializer);
      }

      return ident;
    }

    /**
     * This can be called to reset the initializer expression on an
     * already-defined field.
     * 
     * @param ident an identifier previously returned by {@link #define}
     * @param initializer a Java expression that will be used to initialize the
     *          field
     */
    public void setInitializer(String ident, String initializer) {
      assert fieldsToDeclarations.containsKey(ident) : ident + " not defined";
      fieldsToInitializers.put(ident, initializer);
    }

    private String getCode() {
      StringBuilder sb = new StringBuilder();
      for (Map.Entry<String, String> entry : fieldsToDeclarations.entrySet()) {
        String ident = entry.getKey();
        sb.append(entry.getValue());

        String initializer = fieldsToInitializers.get(ident);
        if (initializer != null) {
          sb.append(" = ").append(initializer);
        }
        sb.append(";\n");
      }
      return sb.toString();
    }
  }

  private static class RequirementsImpl implements ClientBundleRequirements {
    private final Set<String> axes = new HashSet<String>();
    private final PropertyOracle oracle;

    public RequirementsImpl(PropertyOracle oracle) {
      this.oracle = oracle;
    }

    public void addPermutationAxis(String propertyName)
        throws BadPropertyValueException {
      // Ensure the property exists and add a permutation axis if the
      // property is a deferred binding property.
      try {
        oracle.getSelectionProperty(TreeLogger.NULL, propertyName).getCurrentValue();
        axes.add(propertyName);
      } catch (BadPropertyValueException e) {
        oracle.getConfigurationProperty(propertyName).getValues();
      }
    }
  }

  @Override
  public final String generate(TreeLogger logger,
      GeneratorContext generatorContext, String typeName)
      throws UnableToCompleteException {

    // The TypeOracle knows about all types in the type system
    TypeOracle typeOracle = generatorContext.getTypeOracle();

    // Get a reference to the type that the generator should implement
    JClassType sourceType = typeOracle.findType(typeName);

    // Ensure that the requested type exists
    if (sourceType == null) {
      logger.log(TreeLogger.ERROR, "Could not find requested typeName");
      throw new UnableToCompleteException();
    } else if (sourceType.isInterface() == null) {
      // The incoming type wasn't a plain interface, we don't support
      // abstract base classes
      logger.log(TreeLogger.ERROR, sourceType.getQualifiedSourceName()
          + " is not an interface.", null);
      throw new UnableToCompleteException();
    }

    /*
     * This associates the methods to implement with the ResourceGenerator class
     * that will generate the implementations of those methods.
     */
    Map<Class<? extends ResourceGenerator>, List<JMethod>> taskList = createTaskList(
        logger, typeOracle, sourceType);

    /*
     * Additional objects that hold state during the generation process.
     */
    AbstractResourceContext resourceContext = createResourceContext(logger,
        generatorContext, sourceType);
    FieldsImpl fields = new FieldsImpl();
    RequirementsImpl requirements = new RequirementsImpl(
        generatorContext.getPropertyOracle());
    doAddFieldsAndRequirements(logger, generatorContext, fields, requirements);

    /*
     * Initialize the ResourceGenerators and prepare them for subsequent code
     * generation.
     */
    Map<ResourceGenerator, List<JMethod>> generators = initAndPrepare(logger,
        taskList, resourceContext, requirements);

    /*
     * Now that the ResourceGenerators have been initialized and prepared, we
     * can compute the actual name of the implementation class in order to
     * ensure that we use a distinct name between permutations.
     */
    String generatedSimpleSourceName = generateSimpleSourceName(logger,
        resourceContext, requirements);
    String packageName = sourceType.getPackage().getName();
    String createdClassName = packageName + "." + generatedSimpleSourceName;

    PrintWriter out = generatorContext.tryCreate(logger, packageName,
        generatedSimpleSourceName);

    // If an implementation already exists, we don't need to do any work
    if (out != null) {
      // There is actual work to do
      doCreateBundleForPermutation(logger, generatorContext, fields,
          generatedSimpleSourceName);
      // Begin writing the generated source.
      ClassSourceFileComposerFactory f = new ClassSourceFileComposerFactory(
          packageName, generatedSimpleSourceName);

      // The generated class needs to be able to determine the module base URL
      f.addImport(GWT.class.getName());

      // Used by the map methods
      f.addImport(ResourcePrototype.class.getName());

      // The whole point of this exercise
      f.addImplementedInterface(sourceType.getQualifiedSourceName());

      // All source gets written through this Writer
      SourceWriter sw = f.createSourceWriter(generatorContext, out);

      // Set the now-calculated simple source name
      resourceContext.setSimpleSourceName(generatedSimpleSourceName);

      JParameterizedType hashMapStringResource = getHashMapStringResource(typeOracle);
      String resourceMapField = fields.define(hashMapStringResource, "resourceMap");

      // Write the generated code to disk
      createFieldsAndAssignments(logger, sw, generators, resourceContext,
          fields);

      // Print the accumulated field definitions
      sw.println(fields.getCode());

      // Print the static initializer after the fields
      sw.println("static {");
      sw.indentln("new " + resourceContext.getImplementationSimpleSourceName()
          + "()._init0();");
      sw.println("}");

      /*
       * The map-accessor methods use JSNI and need a fully-qualified class
       * name, but should not include any sub-bundles.
       */
      taskList.remove(BundleResourceGenerator.class);
      writeMapMethods(sw, taskList, hashMapStringResource, resourceMapField);

      sw.commit(logger);
    }

    finish(logger, resourceContext, generators.keySet());
    doFinish(logger);

    // Return the name of the concrete class
    return createdClassName;
  }

  /**
   * Create the ResourceContext object that will be used by
   * {@link ResourceGenerator} subclasses. This is the primary way to implement
   * custom logic in the resource generation pass.
   */
  protected abstract AbstractResourceContext createResourceContext(
      TreeLogger logger, GeneratorContext context, JClassType resourceBundleType)
      throws UnableToCompleteException;

  /**
   * Provides a hook for subtypes to add additional fields or requirements to
   * the bundle.
   * 
   * @param logger a TreeLogger
   * @param context the GeneratorContext
   * @param fields ClentBundle fields
   * @param requirements ClientBundleRequirements
   * 
   * @throws UnableToCompleteException if an error occurs.
   */
  protected void doAddFieldsAndRequirements(TreeLogger logger,
      GeneratorContext context, FieldsImpl fields,
      ClientBundleRequirements requirements) throws UnableToCompleteException {
  }

  /**
   * This method is called after the ClientBundleRequirements have been
   * evaluated and a new ClientBundle implementation is being created.
   * 
   * @param logger a TreeLogger
   * @param generatorContext the GeneratoContext
   * @param fields ClientBundle fields
   * @param generatedSimpleSourceName a String
   * 
   * @throws UnableToCompleteException if an error occurs.
   */
  protected void doCreateBundleForPermutation(TreeLogger logger,
      GeneratorContext generatorContext, FieldsImpl fields,
      String generatedSimpleSourceName) throws UnableToCompleteException {
  }

  /**
   * Provides a hook for finalizing generated resources.
   * 
   * @param logger a TreeLogger
   * 
   * @throws UnableToCompleteException if an error occurs.
   */
  protected void doFinish(TreeLogger logger) throws UnableToCompleteException {
  }

  /**
   * Create fields and assignments for a single ResourceGenerator.
   */
  private boolean createFieldsAndAssignments(TreeLogger logger,
      AbstractResourceContext resourceContext, ResourceGenerator rg,
      List<JMethod> generatorMethods, SourceWriter sw, SourceWriter init,
      ClientBundleFields fields) {

    // Defer failure until this phase has ended
    boolean fail = false;

    resourceContext.setCurrentResourceGenerator(rg);

    // Write all field values
    try {
      rg.createFields(logger.branch(TreeLogger.DEBUG, "Creating fields"),
          resourceContext, fields);
    } catch (UnableToCompleteException e) {
      return false;
    }

    // Create the instance variables in the IRB subclass by calling
    // writeAssignment() on the ResourceGenerator
    for (JMethod m : generatorMethods) {
      String rhs;

      try {
        rhs = rg.createAssignment(logger.branch(TreeLogger.DEBUG,
            "Creating assignment for " + m.getName() + "()"), resourceContext,
            m);
      } catch (UnableToCompleteException e) {
        fail = true;
        continue;
      }

      // Define a field that will hold the ResourcePrototype
      String ident = fields.define(m.getReturnType().isClassOrInterface(),
          m.getName(), null, true, false);

      // Strip off all but the access modifiers
      sw.print(m.getReadableDeclaration(false, true, true, true, true));
      sw.println(" {");
      sw.indentln("return " + ident + ";");
      sw.println("}");

      // Record the initialization statement for the one-shot init() method
      init.println(ident + " = " + rhs + ";");
    }

    if (fail) {
      return false;
    }

    return true;
  }

  /**
   * Create fields and assignments for multiple ResourceGenerators.
   */
  private void createFieldsAndAssignments(TreeLogger logger, SourceWriter sw,
      Map<ResourceGenerator, List<JMethod>> generators,
      AbstractResourceContext resourceContext, ClientBundleFields fields)
      throws UnableToCompleteException {
    // Try to provide as many errors as possible before failing.
    boolean success = true;

    SourceWriter init = new StringSourceWriter();
    init.println("private void _init0() {");
    init.indent();

    // Run the ResourceGenerators to generate implementations of the methods
    for (Map.Entry<ResourceGenerator, List<JMethod>> entry : generators.entrySet()) {
      success &= createFieldsAndAssignments(logger, resourceContext,
          entry.getKey(), entry.getValue(), sw, init, fields);
    }

    init.outdent();
    init.println("}");

    sw.println(init.toString());

    if (!success) {
      throw new UnableToCompleteException();
    }
  }

  /**
   * Given a ClientBundle subtype, compute which ResourceGenerators should
   * implement which methods. The data returned from this method should be
   * stable across identical modules.
   */
  private Map<Class<? extends ResourceGenerator>, List<JMethod>> createTaskList(
      TreeLogger logger, TypeOracle typeOracle, JClassType sourceType)
      throws UnableToCompleteException {

    Map<Class<? extends ResourceGenerator>, List<JMethod>> toReturn = new LinkedHashMap<Class<? extends ResourceGenerator>, List<JMethod>>();

    JClassType bundleType = typeOracle.findType(ClientBundle.class.getName());
    assert bundleType != null;

    JClassType bundleWithLookupType = typeOracle.findType(ClientBundleWithLookup.class.getName());
    assert bundleWithLookupType != null;

    JClassType resourcePrototypeType = typeOracle.findType(ResourcePrototype.class.getName());
    assert resourcePrototypeType != null;

    // Accumulate as many errors as possible before failing
    boolean throwException = false;

    // Using overridable methods allows composition of interface types
    for (JMethod m : sourceType.getOverridableMethods()) {
      JClassType returnType = m.getReturnType().isClassOrInterface();

      if (m.getEnclosingType().equals(bundleType)
          || m.getEnclosingType().equals(bundleWithLookupType)) {
        // Methods that we must generate, but that are not resources
        continue;

      } else if (!m.isAbstract()) {
        // Covers the case of an abstract class base type
        continue;

      } else if (returnType == null
          || !(returnType.isAssignableTo(resourcePrototypeType) || returnType.isAssignableTo(bundleType))) {
        // Primitives and random other abstract methods
        logger.log(TreeLogger.ERROR, "Unable to implement " + m.getName()
            + " because it does not derive from "
            + resourcePrototypeType.getQualifiedSourceName() + " or "
            + bundleType.getQualifiedSourceName());
        throwException = true;
        continue;
      }

      try {
        Class<? extends ResourceGenerator> clazz = findResourceGenerator(
            logger, m);
        List<JMethod> generatorMethods;
        if (toReturn.containsKey(clazz)) {
          generatorMethods = toReturn.get(clazz);
        } else {
          generatorMethods = new ArrayList<JMethod>();
          toReturn.put(clazz, generatorMethods);
        }

        generatorMethods.add(m);
      } catch (UnableToCompleteException e) {
        throwException = true;
      }
    }

    if (throwException) {
      throw new UnableToCompleteException();
    }

    return toReturn;
  }

  /**
   * Given a JMethod, find the a ResourceGenerator class that will be able to
   * provide an implementation of the method.
   */
  private Class<? extends ResourceGenerator> findResourceGenerator(
      TreeLogger logger, JMethod method) throws UnableToCompleteException {
    JClassType resourceType = method.getReturnType().isClassOrInterface();
    assert resourceType != null;

    ResourceGeneratorType annotation = resourceType.findAnnotationInTypeHierarchy(ResourceGeneratorType.class);
    if (annotation == null) {
      logger.log(TreeLogger.ERROR, "No @"
          + ResourceGeneratorType.class.getName() + " was specifed for type "
          + resourceType.getQualifiedSourceName() + " or its supertypes");
      throw new UnableToCompleteException();
    }

    return annotation.value();
  }

  /**
   * Call finish() on several ResourceGenerators.
   */
  private void finish(TreeLogger logger, AbstractResourceContext context,
      Collection<ResourceGenerator> generators)
      throws UnableToCompleteException {
    boolean fail = false;
    // Finalize the ResourceGenerator
    for (ResourceGenerator rg : generators) {
      context.setCurrentResourceGenerator(rg);
      try {
        rg.finish(
            logger.branch(TreeLogger.DEBUG, "Finishing ResourceGenerator"),
            context);
      } catch (UnableToCompleteException e) {
        fail = true;
      }
    }
    if (fail) {
      throw new UnableToCompleteException();
    }
  }

  /**
   * Given a user-defined type name, determine the type name for the generated
   * class based on accumulated requirements.
   * 
   * @throws UnableToCompleteException if an error occurs.
   */
  private String generateSimpleSourceName(TreeLogger logger,
      ResourceContext context, RequirementsImpl requirements) {
    StringBuilder toReturn = new StringBuilder(
        context.getClientBundleType().getName().replaceAll("[.$]", "_"));
    Set<String> permutationAxes = new HashSet<String>(requirements.axes);
    permutationAxes.add("locale");

    try {
      PropertyOracle oracle = context.getGeneratorContext().getPropertyOracle();
      for (String property : permutationAxes) {
        SelectionProperty prop = oracle.getSelectionProperty(logger, property);
        String value = prop.getCurrentValue();
        toReturn.append("_" + value);
      }
    } catch (BadPropertyValueException e) {
    }

    toReturn.append("_" + getClass().getSimpleName());

    return toReturn.toString();
  }

  /**
   * Returns HashMap&lt;String, ResourcePrototype&gt;.
   */
  private JParameterizedType getHashMapStringResource(TypeOracle typeOracle) {
    JGenericType hashMap = (JGenericType) typeOracle.findType(HashMap.class.getName());
    assert hashMap != null;
    JClassType string = typeOracle.findType(String.class.getName());
    assert string != null;
    JClassType resourcePrototype = typeOracle.findType(ResourcePrototype.class.getName());
    assert resourcePrototype != null;
    JParameterizedType mapStringRes = typeOracle.getParameterizedType(hashMap,
        new JClassType[]{string, resourcePrototype});
    return mapStringRes;
  }

  private boolean initAndPrepare(TreeLogger logger,
      AbstractResourceContext resourceContext, ResourceGenerator rg,
      List<JMethod> generatorMethods, ClientBundleRequirements requirements) {
    try {
      resourceContext.setCurrentResourceGenerator(rg);
      rg.init(
          logger.branch(TreeLogger.DEBUG, "Initializing ResourceGenerator"),
          resourceContext);
    } catch (UnableToCompleteException e) {
      return false;
    }

    boolean fail = false;

    // Prepare the ResourceGenerator by telling it all methods that it is
    // expected to produce.
    for (JMethod m : generatorMethods) {
      try {
        rg.prepare(logger.branch(TreeLogger.DEBUG, "Preparing method "
            + m.getName()), resourceContext, requirements, m);
      } catch (UnableToCompleteException e) {
        fail = true;
      }
    }

    return !fail;
  }

  private Map<ResourceGenerator, List<JMethod>> initAndPrepare(
      TreeLogger logger,
      Map<Class<? extends ResourceGenerator>, List<JMethod>> taskList,
      AbstractResourceContext resourceContext,
      ClientBundleRequirements requirements) throws UnableToCompleteException {
    // Try to provide as many errors as possible before failing.
    boolean success = true;
    Map<ResourceGenerator, List<JMethod>> toReturn = new LinkedHashMap<ResourceGenerator, List<JMethod>>();

    // Run the ResourceGenerators to generate implementations of the methods
    for (Map.Entry<Class<? extends ResourceGenerator>, List<JMethod>> entry : taskList.entrySet()) {

      ResourceGenerator rg = instantiateResourceGenerator(logger,
          entry.getKey());
      toReturn.put(rg, entry.getValue());

      success &= initAndPrepare(logger, resourceContext, rg, entry.getValue(),
          requirements);
    }

    if (!success) {
      throw new UnableToCompleteException();
    }

    return toReturn;
  }

  /**
   * Utility method to construct a ResourceGenerator that logs errors.
   */
  private <T extends ResourceGenerator> T instantiateResourceGenerator(
      TreeLogger logger, Class<T> generatorClass)
      throws UnableToCompleteException {
    try {
      return generatorClass.newInstance();
    } catch (InstantiationException e) {
      logger.log(TreeLogger.ERROR, "Unable to initialize ResourceGenerator", e);
    } catch (IllegalAccessException e) {
      logger.log(TreeLogger.ERROR, "Unable to instantiate ResourceGenerator. "
          + "Does it have a public default constructor?", e);
    }
    throw new UnableToCompleteException();
  }

  /**
   * Emits getResources() and getResourceMap() implementations.
   * 
   * @param sw the output writer
   * @param taskList the list of methods to map by name
   * @param resourceMapField field containing the Java String to Resource map
   */
  private void writeMapMethods(SourceWriter sw,
      Map<Class<? extends ResourceGenerator>, List<JMethod>> taskList,
      JParameterizedType resourceMapType, String resourceMapField) {

    // Complete the IRB contract
    sw.println("public ResourcePrototype[] getResources() {");
    sw.indent();
    sw.println("return new ResourcePrototype[] {");
    sw.indent();
    for (List<JMethod> methods : taskList.values()) {
      for (JMethod m : methods) {
        sw.println(m.getName() + "(), ");
      }
    }
    sw.outdent();
    sw.println("};");
    sw.outdent();
    sw.println("}");

    // Map implementation for dev mode.
    sw.println("public ResourcePrototype getResource(String name) {");
    sw.indent();
    sw.println("if (GWT.isScript()) {");
    sw.indent();
    sw.println("return getResourceNative(name);");
    sw.outdent();
    sw.println("} else {");
    sw.indent();
    sw.println("if (" + resourceMapField + " == null) {");
    sw.indent();
    sw.println(resourceMapField + " = new "
        + resourceMapType.getParameterizedQualifiedSourceName() + "();");
    for (List<JMethod> list : taskList.values()) {
      for (JMethod m : list) {
        sw.println(resourceMapField + ".put(\"" + m.getName() + "\", "
            + m.getName() + "());");
      }
    }
    sw.outdent();
    sw.println("}");
    sw.println("return resourceMap.get(name);");
    sw.outdent();
    sw.println("}");
    sw.outdent();
    sw.println("}");

    // Use a switch statement as a fast map for script mode.
    sw.println("private native ResourcePrototype "
        + "getResourceNative(String name) /*-{");
    sw.indent();
    sw.println("switch (name) {");
    sw.indent();
    for (List<JMethod> list : taskList.values()) {
      for (JMethod m : list) {
        sw.println("case '" + m.getName() + "': return this."
            + m.getJsniSignature() + "();");
      }
    }
    sw.outdent();
    sw.println("}");
    sw.println("return null;");
    sw.outdent();
    sw.println("}-*/;");
  }
}
