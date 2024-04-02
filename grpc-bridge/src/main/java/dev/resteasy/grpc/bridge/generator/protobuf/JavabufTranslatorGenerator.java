package dev.resteasy.grpc.bridge.generator.protobuf;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;

import com.google.protobuf.DynamicMessage;
import com.google.protobuf.Message;

import dev.resteasy.grpc.arrays.ArrayUtility;
import dev.resteasy.grpc.arrays.Array_proto;
import dev.resteasy.grpc.bridge.runtime.Utility;
import dev.resteasy.grpc.bridge.runtime.protobuf.AssignFromJavabuf;
import dev.resteasy.grpc.bridge.runtime.protobuf.AssignToJavabuf;
import dev.resteasy.grpc.bridge.runtime.protobuf.JavabufTranslator;
import dev.resteasy.grpc.bridge.runtime.protobuf.ReturnJavaClass;
import dev.resteasy.grpc.bridge.runtime.protobuf.TranslateFromJavabuf;
import dev.resteasy.grpc.bridge.runtime.protobuf.TranslateToJavabuf;

/**
 * Generates a class, &lt;prefix&gt;JavabufTranslator, that can translate back and forth between a Java class and
 * its protobuf representation in Java (i.e., its javabuf version).
 * <p/>
 * For example,
 * <p/>
 * 1. Start with two classes:
 *
 * <pre>
 * package example.grpc;
 *
 * public class CC3 {
 *    String s;
 *    public CC3(String s) {
 *       this.s = s;
 *    }
 * }
 *
 * package example.grpc;
 *
 * public class CC2 extends CC3 {
 *    int j;
 *    public CC2(String s, int j) {
 *       super(s);
 *       this.j = j;
 *    }
 * }
 * </pre>
 *
 * 2. Generate an Example.proto file from the two classes:
 *
 * <pre>
 * syntax = "proto3";
 * package example.grpc;
 * option java_package = "example.grpc";
 * option java_outer_classname = "Example_proto";
 *
 * message example.grpc___CC3 {
 *   string s = 1;
 * }
 *
 * message example.grpc___CC2 {
 *   int32 j = 2;
 *   example.grpc___CC3 cC3___super = 3;
 * }
 * </pre>
 *
 * 3. Use the protobuf compiler to generate class {@code Example_proto} with the javabuf versions
 * {@code Example_proto.example.grpc___CC2} and {@code Example_proto.example.grpc___CC3} of
 * {@code CC2} and {@code CC3}, respectively.
 * <p/>
 *
 * Now, {@code JavabufTranslatorGenerator} will generate an {@code ExampleJavabufTranslator} class that
 * can be used as follows:
 *
 * <pre>
 * &#064;Test
 * public void test() {
 *
 *     // 1. Create a CC2.
 *     CC2 cc2 = new CC2("abc", 19);
 *
 *     // 2. Translate to javabuf form
 *     Message message = ExampleJavabufTranslator.translateToJavabuf(cc2);
 *
 *     // 3. Manually create a javabuf representation of the same CC2 and demonstrate they're the same.
 *     Example_proto.example.grpc___CC3 cc3Message = Example_proto.example.grpc___CC3.newBuilder().setS("abc").build();
 *     Example_proto.example.grpc___CC2 cc2Message = Example_proto.example.grpc___CC2.newBuilder().setJ(19)
 *             .setCC3Super(cc3Message).build();
 *     Assert.assertEquals(message, cc2Message);
 *
 *     // 4. A. Translate the javabuf object created in step 2 back to its original java form.
 *     //    B. Demonstrate it's the same as the java object created in step 1.
 *     CC2 cc2_new = (CC2) ExampleJavabufTranslator.translateFromJavabuf(message);
 *     Assert.assertEquals(cc2, cc2_new);
 * }
 * </pre>
 */
public class JavabufTranslatorGenerator {

    private static Logger logger = Logger.getLogger(JavabufTranslatorGenerator.class);
    private static final String LS = System.lineSeparator();

    public interface AssignTo {
        void assign(Object from, DynamicMessage.Builder builder);
    }

    public interface AssignFrom {
        void assign(Message message, Object object);
    }

    private static final Map<String, Class<?>> PRIMITIVE_WRAPPER_TYPES = new HashMap<String, Class<?>>();
    private static final Map<String, String> GET_METHODS = new HashMap<String, String>();
    private static final Map<Class<?>, String> PRIMITIVE_DEFAULTS = new HashMap<Class<?>, String>();

    static {
        PRIMITIVE_WRAPPER_TYPES.put("gByte", byte.class);
        PRIMITIVE_WRAPPER_TYPES.put("gShort", short.class);
        PRIMITIVE_WRAPPER_TYPES.put("gInteger", int.class);
        PRIMITIVE_WRAPPER_TYPES.put("gLong", long.class);
        PRIMITIVE_WRAPPER_TYPES.put("gFloat", float.class);
        PRIMITIVE_WRAPPER_TYPES.put("gDouble", double.class);
        PRIMITIVE_WRAPPER_TYPES.put("gBoolean", boolean.class);
        PRIMITIVE_WRAPPER_TYPES.put("gCharacter", char.class);
        PRIMITIVE_WRAPPER_TYPES.put("gString", String.class);

        GET_METHODS.put("Byte", ".byteValue()");
        GET_METHODS.put("Short", ".shortValue()");
        GET_METHODS.put("Integer", ".intValue()");
        GET_METHODS.put("Long", ".longValue()");
        GET_METHODS.put("Float", ".floatValue()");
        GET_METHODS.put("Double", ".doubleValue()");
        GET_METHODS.put("Boolean", ".booleanValue()");
        GET_METHODS.put("Character", ".toString()");
        GET_METHODS.put("String", "");

        PRIMITIVE_DEFAULTS.put(boolean.class, "false");
        PRIMITIVE_DEFAULTS.put(byte.class, "(byte)0");
        PRIMITIVE_DEFAULTS.put(short.class, "(short)0");
        PRIMITIVE_DEFAULTS.put(int.class, "0");
        PRIMITIVE_DEFAULTS.put(long.class, "0L");
        PRIMITIVE_DEFAULTS.put(float.class, "0.0f");
        PRIMITIVE_DEFAULTS.put(double.class, "0.0d");
        PRIMITIVE_DEFAULTS.put(char.class, "'\\u0000'");
    }

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            logger.info("need two args:");
            logger.info("  arg[0]: root directory");
            logger.info("  arg[1]: javabuf wrapper class name");
            return;
        }
        try {
            int index = args[1].lastIndexOf('.');
            String simpleName = index < 0 ? args[1] : args[1].substring(index + 1);
            String translatorClass = simpleName + "JavabufTranslator";
            Class<?>[] wrappedClasses = getWrappedClasses(args);
            StringBuilder sb = new StringBuilder();
            classHeader(args, translatorClass, wrappedClasses, sb);
            classBody(args, wrappedClasses, sb);
            finishClass(sb);
            writeTranslatorClass(args, translatorClass, sb);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    private static Class<?>[] getWrappedClasses(String[] args) throws ClassNotFoundException {
        List<Class<?>> wrapperClasses = getWrapperClasses(args);
        List<Class<?>> wrappedClasses = new ArrayList<Class<?>>();
        for (Class<?> clazz : wrapperClasses) {
            wrappedClasses.addAll(Arrays.asList(clazz.getClasses()));
        }
        return wrappedClasses.toArray(new Class<?>[wrappedClasses.size()]);
    }

    private static List<Class<?>> getWrapperClasses(String[] args) throws ClassNotFoundException {
        List<Class<?>> list = new ArrayList<Class<?>>();
        Class<?> wrapperClass = Class.forName(args[1] + "_proto", true, Thread.currentThread()
                .getContextClassLoader());
        list.add(wrapperClass);
        try {
            Class<?> arrayWrapperClass = Class.forName("dev.resteasy.grpc.arrays.Array_proto", true,
                    Thread.currentThread().getContextClassLoader());
            list.add(arrayWrapperClass);
            return list;
        } catch (Exception ignore) {
            // Array_proto class is not available: ignore
            return list;
        }
    }

    private static void classHeader(String[] args, String translatorClass, Class<?>[] wrappedClasses, StringBuilder sb) {
        sb.append("package ").append(wrappedClasses[0].getPackage().getName()).append(";" + LS + LS);
        imports(wrappedClasses, sb);
        sb.append("public class ")
                .append(translatorClass)
                .append(" implements JavabufTranslator {" + LS);
    }

    private static void imports(Class<?>[] wrappedClasses, StringBuilder sb) {
        sb.append("import java.lang.reflect.Array;" + LS)
                .append("import java.lang.reflect.Constructor;" + LS)
                .append("import java.lang.reflect.Field;" + LS)
                .append("import java.util.ArrayList;" + LS)
                .append("import java.util.HashMap;" + LS)
                .append("import java.util.HashSet;" + LS)
                .append("import java.util.List;" + LS)
                .append("import java.util.Map;" + LS)
                .append("import java.util.Set;" + LS)
                .append("import com.google.protobuf.Any;" + LS)
                .append("import com.google.protobuf.ByteString;" + LS)
                .append("import com.google.protobuf.Descriptors;" + LS)
                .append("import com.google.protobuf.Descriptors.Descriptor;" + LS)
                .append("import com.google.protobuf.Descriptors.FieldDescriptor;" + LS)
                .append("import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;" + LS)
                .append("import com.google.protobuf.Internal.IntList;" + LS)
                .append("import com.google.protobuf.Message;" + LS)
                .append("import ").append(ArrayUtility.class.getCanonicalName()).append(";" + LS)
                .append("import ").append(Utility.class.getCanonicalName()).append(";" + LS)
                .append("import ").append(AssignFromJavabuf.class.getCanonicalName()).append(";" + LS)
                .append("import ").append(AssignToJavabuf.class.getCanonicalName()).append(";" + LS)
                .append("import ").append(JavabufTranslator.class.getCanonicalName()).append(";" + LS)
                .append("import ").append(ReturnJavaClass.class.getCanonicalName()).append(";" + LS)
                .append("import ").append(TranslateFromJavabuf.class.getCanonicalName()).append(";" + LS)
                .append("import ").append(TranslateToJavabuf.class.getCanonicalName()).append(";" + LS)
                .append("import dev.resteasy.grpc.arrays.Array_proto;" + LS)
                .append("import " + Array_proto.dev_resteasy_grpc_arrays___ArrayHolder.class.getCanonicalName())
                .append(";" + LS);
        for (Class<?> clazz : wrappedClasses) {
            if (clazz.isInterface()) {
                continue;
            }
            if ("dev.resteasy.grpc.arrays".equals(clazz.getPackageName())) {
                continue;
            }
            String simpleName = clazz.getSimpleName();
            if ("gEmpty".equals(simpleName)) {
                continue;
            }
            if (PRIMITIVE_WRAPPER_TYPES.containsKey(simpleName)) {
                sb.append("import ").append(clazz.getName().replace("$", ".")).append(";" + LS);
            } else if ("GeneralEntityMessage".equals(simpleName)
                    || "GeneralReturnMessage".equals(simpleName)
                    || "ServletInfo".equals(simpleName)
                    || "gNewCookie".equals(simpleName)
                    || "gCookie".equals(simpleName)
                    || "gHeader".equals(simpleName)
                    || "FormMap".equals(simpleName)
                    || "FormValues".equals(simpleName)) {
                continue;
            } else if (clazz.getName().contains("_HIDDEN_") || clazz.getName().endsWith("ELEMENT_WRAPPER")) {
                sb.append("import ")
                        .append(clazz.getName().replace("$", "."))
                        .append(";" + LS);
            } else {
                sb.append("import ")
                        .append(originalCanonicalClassName(clazz.getName()))
                        .append(";" + LS);
                sb.append("import ")
                        .append(clazz.getName().replace("$", "."))
                        .append(";" + LS);
            }
        }
        sb.append("" + LS);
    }

    private static void classBody(String[] args, Class<?>[] wrappedClasses, StringBuilder sb) throws Exception {
        privateVariables(sb, args);
        staticInit(wrappedClasses, sb);
        publicMethods(sb, wrappedClasses[0]);
        privateMethods(sb, wrappedClasses, args);
        for (Class<?> clazz : wrappedClasses) {
            if (clazz.isInterface()) {
                continue;
            }
            String simpleName = clazz.getSimpleName();
            if ("GeneralEntityMessage".equals(simpleName) || "GeneralReturnMessage".equals(simpleName)) {
                continue;
            }
            createTranslator(args, clazz, sb);
        }
        writePrivateMethods(sb);
    }

    private static void staticInit(Class<?>[] classes, StringBuilder sb) throws ClassNotFoundException {
        sb.append("   static {" + LS);
        for (Class<?> clazz : classes) {
            if (clazz.isInterface()) {
                continue;
            }
            String simpleName = clazz.getSimpleName();
            if ("gEmpty".equals(simpleName)
                    || "GeneralEntityMessage".equals(simpleName)
                    || "GeneralReturnMessage".equals(simpleName)
                    || "ServletInfo".equals(simpleName)
                    || "gNewCookie".equals(simpleName)
                    || "gCookie".equals(simpleName)
                    || "gHeader".equals(simpleName)
                    || "FormMap".equals(simpleName)
                    || "FormValues".equals(simpleName)
                    || "ELEMENT_WRAPPER".equals(simpleName)) {
                continue;
            }
            if ("dev.resteasy.grpc.arrays".equals(clazz.getPackageName())) {
                continue;
            }

            boolean isAbstract = false;
            if (!PRIMITIVE_WRAPPER_TYPES.containsKey(simpleName)) {
                try {
                    String originalName = originalSimpleName(clazz.getName());
                    String className = javabufToJava(clazz.getName(), originalName, true);
                    //                    Class<?> c = Class.forName(originalClassName(simpleName), true,
                    Class<?> c = Class.forName(className, true,
                            Thread.currentThread().getContextClassLoader());
                    isAbstract = Modifier.isAbstract(c.getModifiers());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    continue;
                }
            }
            sb.append(LS + "      toJavabufMap.put(\"")
                    .append(originalClassName(simpleName) + "\" , new ")
                    .append(simpleName)
                    .append("_ToJavabuf());" + LS);

            if (!isAbstract) {
                sb.append("      fromJavabufMap.put(")
                        .append("\"" + simpleName + "\"")
                        .append(", new ")
                        .append(simpleName)
                        .append("_FromJavabuf());" + LS);
            }
            sb.append("      toJavabufClassMap.put(\"")
                    .append(originalClassName(simpleName) + "\", ")
                    .append(simpleName + ".class);" + LS);
            sb.append("      fromJavabufClassMap.put(")
                    .append(simpleName + ".class.getName(), ")
                    .append(getJavabufClassValue(simpleName, false, false) + ");" + LS);
        }
        sb.append(LS);
        for (Class<?> clazz2 : classes) {
            if (clazz2.getName().endsWith("___WRAPPER")) {
                sb.append("      ARRAY_WRAPPER_TYPES.add(\"")
                        .append(clazz2.getName())
                        .append("\");" + LS);
            }
        }
        sb.append(LS);
        sb.append("      WRAPPER_TYPES.add(java.lang.Boolean.class);" + LS);
        sb.append("      WRAPPER_TYPES.add(java.lang.Byte.class);" + LS);
        sb.append("      WRAPPER_TYPES.add(java.lang.Short.class);" + LS);
        sb.append("      WRAPPER_TYPES.add(java.lang.Integer.class);" + LS);
        sb.append("      WRAPPER_TYPES.add(java.lang.Long.class);" + LS);
        sb.append("      WRAPPER_TYPES.add(java.lang.Float.class);" + LS);
        sb.append("      WRAPPER_TYPES.add(java.lang.Double.class);" + LS);
        sb.append("      WRAPPER_TYPES.add(java.lang.Character.class);" + LS);
        sb.append("      WRAPPER_TYPES.add(java.lang.String.class);" + LS);
        sb.append("   }" + LS + LS);
    }

    private static void publicMethods(StringBuilder sb, Class<?> clazz) {
        sb.append("   public boolean handlesToJavabuf(Class<?> clazz) {" + LS)
                .append("      return clazz.isPrimitive() || toJavabufMap.containsKey(clazz.getName());" + LS)
                .append("   }" + LS + LS)
                .append("   public boolean handlesFromJavabuf(Class<?> clazz) {" + LS)
                .append("      return clazz.isPrimitive() || toJavabufMap.containsKey(clazz.getName());" + LS)
                .append("   }" + LS + LS)
                .append("   public Message translateToJavabuf(Object o) {" + LS)
                .append("      if (o.getClass().isArray()) {" + LS)
                .append("         return ArrayUtility.getHolder(this, o);" + LS)
                .append("      }" + LS)
                .append("      TranslateToJavabuf ttj = toJavabufMap.get(o.getClass().getName());" + LS)
                .append("      if (ttj == null) {" + LS)
                .append("         throw new RuntimeException(o.getClass() + \" is not recognized\");" + LS)
                .append("      }" + LS)
                .append("      ttj.clear();" + LS)
                .append("      return ttj.assignToJavabuf(o);" + LS)
                .append("   }" + LS + LS)
                .append("   @SuppressWarnings(\"rawtypes\")" + LS)
                .append("   public Class translateToJavabufClass(Class<?> clazz) {" + LS)
                .append("      return toJavabufClassMap.get(clazz.getName());" + LS)
                .append("   }" + LS + LS)
                .append("   @SuppressWarnings(\"rawtypes\")" + LS)
                .append("   public Class translateToJavabufClass(String classname) {" + LS)
                .append("      return toJavabufClassMap.get(classname);" + LS)
                .append("   }" + LS + LS)
                .append("   @SuppressWarnings(\"rawtypes\")" + LS)
                .append("   @Override" + LS)
                .append("   public Class translatefromJavabufClass(String classname) {" + LS)
                .append("      return fromJavabufClassMap.get(classname);" + LS)
                .append("   }" + LS + LS)
                .append("   @Override" + LS)
                .append("   public String getOuterClassname() {" + LS)
                .append("      return \"").append(clazz.getDeclaringClass().getName()).append("\";" + LS)
                .append("   }" + LS + LS)
                .append("   public Object translateFromJavabuf(Message message) {" + LS)
                .append("      if (Array_proto.dev_resteasy_grpc_arrays___ArrayHolder.class.equals(message.getClass())) {" + LS)
                .append("         try {" + LS)
                .append("            return ArrayUtility.getArray(this, (Array_proto.dev_resteasy_grpc_arrays___ArrayHolder) message);"
                        + LS)
                .append("         } catch (Exception e) {" + LS)
                .append("            throw new RuntimeException(e);" + LS)
                .append("         }" + LS)
                .append("      }" + LS)
                .append("      String s = null;" + LS)
                .append("      try {" + LS)
                .append("         s = message.getDescriptorForType().getFullName();" + LS)
                .append("         s = s.substring(s.lastIndexOf(\".\") + 1);" + LS)
                .append("         TranslateFromJavabuf tfj = fromJavabufMap.get(s);" + LS)
                .append("         if (tfj == null) {" + LS)
                .append("            throw new RuntimeException(message.getClass() + \" is not recognized\");" + LS)
                .append("         }" + LS)
                .append("         return tfj.assignFromJavabuf(message);" + LS)
                .append("      } catch (Exception e) {" + LS)
                .append("         throw new RuntimeException(e);" + LS)
                .append("      }" + LS)
                .append("   }" + LS + LS);
    }

    private static void createTranslator(String[] args, Class<?> clazz, StringBuilder sb) throws Exception {
        createTranslatorToJavabuf(args, clazz, sb);
        createTranslatorFromJavabuf(args, clazz, sb);
    }

    private static void privateVariables(StringBuilder sb, String[] args) {
        sb.append("   private static final JavabufTranslator INSTANCE = new CC1JavabufTranslator();" + LS);
        sb.append("   private static final String NULL = \"_NULL_\";" + LS);
        sb.append(
                "   private static Map<String, TranslateToJavabuf> toJavabufMap = new HashMap<String, TranslateToJavabuf>();"
                        + LS);
        sb.append(
                "   private static Map<String, TranslateFromJavabuf> fromJavabufMap = new HashMap<String, TranslateFromJavabuf>();"
                        + LS);
        sb.append("   private static JavabufTranslator translator = new ").append(args[1]).append("JavabufTranslator();" + LS);
        sb.append(
                "   private static dev_resteasy_grpc_arrays___ArrayHolder_ToJavabuf arrayHolderToJavabuf = new dev_resteasy_grpc_arrays___ArrayHolder_ToJavabuf();"
                        + LS);
        sb.append("   private static Set<Class<?>> WRAPPER_TYPES = new HashSet<Class<?>>();" + LS);
        sb.append("   private static Set<String> ARRAY_WRAPPER_TYPES = new HashSet<String>();" + LS + LS);
        sb.append("   @SuppressWarnings(\"rawtypes\")" + LS);
        sb.append("   private static Map<String, Class> toJavabufClassMap = new HashMap<String, Class>();" + LS);
        sb.append("   private static Map<String, Class<?>> fromJavabufClassMap = new HashMap<String, Class<?>>();"
                + LS);
        sb.append("   private static Set<String> hiddenClasses = new HashSet<String>();" + LS);
        sb.append(
                "       private static Map<String, Constructor<?>> constructors = new HashMap<String, Constructor<?>>();" + LS);
    }

    private static void privateMethods(StringBuilder sb, Class<?>[] classes, String[] args) {
        sb.append("   private static AssignToJavabuf toJavabuf(Class<?> clazz, FieldDescriptor fd) {" + LS)
                .append("      try {" + LS)
                .append("         AssignToJavabuf assignToJavabuf = (obj, messageBuilder) -> {" + LS)
                .append("            try {" + LS)
                .append("               if (obj == null) {" + LS)
                .append("                  return;" + LS)
                .append("               }" + LS)
                .append("                  final Field field = Utility.getField(clazz, javabufClassToJavaClass(fd.getName()));"
                        + LS)
                .append("                  field.setAccessible(true);" + LS)

                .append("                  if (field.getType().isArray()) {" + LS)
                .append("                        Object array = field.get(obj);" + LS)
                .append("                        if (array == null) {" + LS)
                .append("                           return;" + LS)
                .append("                        }" + LS)
                .append("                        Class<?> componentType = array.getClass().getComponentType();" + LS)
                .append("                        if (componentType.isArray()) {" + LS)
                .append("                           messageBuilder.setField(fd, arrayHolderToJavabuf.assignToJavabuf(field.get(obj)));"
                        + LS)
                .append("                        } else if (componentType.isPrimitive() || WRAPPER_TYPES.contains(componentType)) {"
                        + LS)
                .append("                           if (Character.class.equals(componentType) || char.class.equals(componentType)) {"
                        + LS)
                .append("                              messageBuilder.setField(fd, charsToString(array));" + LS)
                .append("                           } else if (byte.class.equals(componentType)) {"
                        + LS)
                .append("                              messageBuilder.setField(fd, ByteString.copyFrom((byte[])array));" + LS)
                .append("                           } else {" + LS)
                .append("                              messageBuilder.addRepeatedField(fd, ELEMENT_WRAPPER.newBuilder().setPosition(Array.getLength(array)).build());"
                        + LS)
                .append("                              for (int i = 0; i < Array.getLength(array); i++) {" + LS)
                .append("                                 messageBuilder.addRepeatedField(fd, wrapArrayElement(translator.translateToJavabuf(Array.get(array, i)), i));"
                        + LS)
                .append("                              }" + LS)
                .append("                           }" + LS)
                .append("                        } else if (Object.class.equals(componentType)) {" + LS)
                .append("                           messageBuilder.addRepeatedField(fd, ELEMENT_WRAPPER.newBuilder().setPosition(Array.getLength(array)).build());"
                        + LS)
                .append("                           for (int i = 0; i < Array.getLength(array); i++) {" + LS)
                .append("                              if (Array.get(array, i) != null) {" + LS)
                .append("                                 messageBuilder.addRepeatedField(fd, wrapArrayElement(Any.pack(translator.translateToJavabuf(Array.get(array, i))), i));"
                        + LS)
                .append("                              }" + LS)
                .append("                           }" + LS)
                .append("                        } else {" + LS)
                .append("                           messageBuilder.addRepeatedField(fd, ELEMENT_WRAPPER.newBuilder().setPosition(Array.getLength(array)).build());"
                        + LS)
                .append("                           for (int i = 0; i < Array.getLength(array); i++) {" + LS)
                .append("                              if (Array.get(array, i) != null) {" + LS)
                .append("                                 messageBuilder.addRepeatedField(fd, wrapArrayElement(translator.translateToJavabuf(Array.get(array, i)), i));"
                        + LS)
                .append("                              }" + LS)
                .append("                           }" + LS)
                .append("                        }" + LS)
                .append("                    }" + LS)
                .append("                  else if (\".google.protobuf.Any\".equals(fd.toProto().getTypeName())) {" + LS)
                .append("                     if (field.get(obj) != null) {" + LS)
                .append("                        Class<?> c = field.get(obj).getClass();" + LS)
                .append("                        if (Any.class.equals(c)) {" + LS)
                .append("                           messageBuilder.setField(fd, field.get(obj));" + LS)
                .append("                        } else {" + LS)
                .append("                           Message message = toJavabufMap.get(c.getName()).assignToJavabuf(field.get(obj));"
                        + LS)
                .append("                           messageBuilder.setField(fd, Any.pack(message));" + LS)
                .append("                        }" + LS)
                .append("                     }" + LS)

                .append("                  } else if (!String.class.equals(field.getType()) && toJavabufMap.keySet().contains(field.getType().getName())) {"
                        + LS)
                .append("                        Message message = toJavabufMap.get(field.getType().getName()).assignToJavabuf(field.get(obj));"
                        + LS)
                .append("                        if (message != null) {" + LS)
                .append("                           messageBuilder.setField(fd, message);" + LS)
                .append("                        }" + LS)
                .append("                    } else if (field.get(obj) != null) {" + LS)
                .append("                        if (Byte.class.equals(field.getType()) || byte.class.equals(field.getType())) {"
                        + LS)
                .append("                           Byte b = field.getByte(obj);" + LS)
                .append("                           messageBuilder.setField(fd, b.intValue());" + LS)
                .append("                        } else if (Short.class.equals(field.getType()) || short.class.equals(field.getType())) {"
                        + LS)
                .append("                           Short s = field.getShort(obj);" + LS)
                .append("                           messageBuilder.setField(fd, s.intValue());" + LS)
                .append("                        } else if (Character.class.equals(field.getType()) || char.class.equals(field.getType())) {"
                        + LS)
                .append("                           Character c = field.getChar(obj);" + LS)
                .append("                           messageBuilder.setField(fd, String.valueOf(c));" + LS)
                .append("                        } else if (Descriptors.FieldDescriptor.JavaType.BYTE_STRING.equals(fd.getJavaType())) {"
                        + LS)
                .append("                           ByteString bs = ByteString.copyFrom((byte[]) field.get(obj));" + LS)
                .append("                           messageBuilder.setField(fd, bs);" + LS)
                .append("                        } else {" + LS)
                .append("                           messageBuilder.setField(fd, field.get(obj));" + LS)
                .append("                        }" + LS)
                .append("                     } else {" + LS)
                .append("                        messageBuilder.setField(fd, NULL);" + LS)
                .append("                     }" + LS)
                .append("            } catch (Exception e) {" + LS)
                .append("                throw new RuntimeException(e);" + LS)
                .append("            }" + LS)
                .append("         };" + LS)
                .append("         return assignToJavabuf;" + LS)
                .append("      } catch (Exception e) {" + LS)
                .append("         throw new RuntimeException(e);" + LS)
                .append("      }" + LS)
                .append("   }" + LS + LS);
        sb.append("   private static AssignFromJavabuf fromJavabuf(Class<?> javaClass, FieldDescriptor fd) {" + LS)
                .append("      try {" + LS)
                .append("         AssignFromJavabuf assignFromJavabuf = (message, object) -> {" + LS)
                .append("            try {" + LS)
                .append("                  final Field field = Utility.getField(javaClass, javabufClassToJavaClass(fd.getName()));"
                        + LS)
                .append("                  field.setAccessible(true);" + LS)
                .append("                  if (field.getType().isArray()) {" + LS)
                .append("                     String typeName = fd.toProto().getTypeName();" + LS)
                .append("                     if (\".google.protobuf.Any\".equals(typeName)) {" + LS)
                .append("                        if (message.getRepeatedFieldCount(fd) > 0) {" + LS)
                .append("                           Class clazz = Utility.extractClassFromAny((Any) message.getRepeatedField(fd, 0), INSTANCE);"
                        + LS)
                .append("                           Class javaClazz = INSTANCE.translatefromJavabufClass(clazz.getName());"
                        + LS)
                .append("                           Object array = Array.newInstance(javaClazz, message.getRepeatedFieldCount(fd));"
                        + LS)
                .append("                           for (int i = 0; i < message.getRepeatedFieldCount(fd); i++) {" + LS)
                .append("                              Any any = (Any) message.getRepeatedField(fd, i);" + LS)
                .append("                              Object o = INSTANCE.translateFromJavabuf(any.unpack(clazz));" + LS)
                .append("                              Array.set(array, i, o);" + LS)
                .append("                           }" + LS)
                .append("                           Utility.setField(field, object, array, INSTANCE);" + LS)
                .append("                        }" + LS)
                .append("                     } else if (\"dev_resteasy_grpc_arrays___ArrayHolder\".equals(typeName.substring(typeName.lastIndexOf(\".\") + 1))) {"
                        + LS)
                .append("                        dev_resteasy_grpc_arrays___ArrayHolder ah = (dev_resteasy_grpc_arrays___ArrayHolder) message.getField(fd);"
                        + LS)
                .append("                        Utility.setField(field, object, ArrayUtility.getArray(translator, ah), INSTANCE);"
                        + LS)
                .append("                     } else if (Descriptors.FieldDescriptor.JavaType.STRING.equals(fd.getJavaType())) {"
                        + LS)
                .append("                        if (char.class.equals(field.getType().getComponentType())) {" + LS)
                .append("                           char[] cs = stringToChars((String) message.getField(fd));" + LS)
                .append("                           Utility.setField(field, object, cs, INSTANCE);" + LS)
                .append("                        } else {" + LS)
                .append("                            Character[] cs = stringToCharacters((String) message.getField(fd));" + LS)
                .append("                            Utility.setField(field, object, cs, INSTANCE);" + LS)
                .append("                        }" + LS)
                .append("                     } else if (byte.class.equals(field.getType().getComponentType())) {"
                        + LS)
                .append("                        ByteString bs = (ByteString) message.getField(fd);" + LS)
                .append("                        Utility.setField(field, object, bs.toByteArray(), INSTANCE);" + LS)
                .append("                      } " + LS)
                .append("                      else if (message.getField(fd) instanceof List) {" + LS)
                .append("                        List list = (List) message.getField(fd);" + LS)
                .append("                        if (list.size() == 0) {" + LS)
                .append("                           return;" + LS)
                .append("                        }" + LS)
                .append("                        if (message.getField(fd).getClass().getPackage().getName().startsWith(\"com.google.protobuf\")) {"
                        + LS)
                .append("                           if (field.get(object) == null) {" + LS)
                .append("                              Utility.setField(field, object, getArray(field, list.size()), INSTANCE);"
                        + LS)
                .append("                           }" + LS)
                .append("                           if ((short.class.equals(field.getType().getComponentType())" + LS)
                .append("                             || Short.class.equals(field.getType().getComponentType()))" + LS)
                .append("                                && list.get(0) instanceof Integer) {" + LS)
                .append("                              for (int i = 0; i < list.size(); i++) {" + LS)
                .append("                                 Array.set(field.get(object), i, ((Integer) list.get(i)).shortValue());"
                        + LS)
                .append("                               }" + LS)
                .append("                           } else {" + LS)
                .append("                              for (int i = 0; i < list.size(); i++) {" + LS)
                .append("                                 Array.set(field.get(object), i, list.get(i));" + LS)
                .append("                              }" + LS)
                .append("                           }" + LS)
                .append("                        } else {" + LS)
                .append("                           if (fd.getMessageType().getName().endsWith(\"ELEMENT_WRAPPER\")) {" + LS)
                .append("                              int size = (int) ((ELEMENT_WRAPPER) list.get(0)).getPosition();" + LS)
                .append("                              if (field.get(object) == null || Array.getLength(field.get(object)) != size) {"
                        + LS)
                .append("                                  Utility.setField(field, object, Array.newInstance(field.getType().getComponentType(), size), INSTANCE);"
                        + LS)
                .append("                              }" + LS)
                .append("                              for (int i = 1; i < list.size(); i++) {" + LS)
                .append("                                 int position = (int) ((ELEMENT_WRAPPER) list.get(i)).getPosition();"
                        + LS)
                .append("                                 Message element = unwrapArrayElement((ELEMENT_WRAPPER) list.get(i));"
                        + LS)
                .append("                                 if (\"google.protobuf.Any\".equals(element.getDescriptorForType().getFullName())) {"
                        + LS)
                .append("                                    element = Utility.extractFromAny((Any) element, INSTANCE);" + LS)
                .append("                                 }" + LS)
                .append("                                 Array.set(field.get(object), position, translator.translateFromJavabuf(element));"
                        + LS)
                .append("                              }" + LS)
                .append("                            } else for (int i = 0; i < list.size(); i++) {" + LS)
                .append("                              Array.set(field.get(object), i, translator.translateFromJavabuf((Message) list.get(i)));"
                        + LS)
                .append("                           }" + LS)
                .append("                        }" + LS)
                .append("                     }" + LS)
                .append("                  } else if (Descriptors.FieldDescriptor.Type.MESSAGE.equals(fd.getType())" + LS)
                .append("                     && fromJavabufMap.keySet().contains(fd.getMessageType().getName())) {" + LS)
                .append("                     if (message.hasField(fd)) {" + LS)
                .append("                        Message submessage = (Message) message.getField(fd);" + LS)
                .append("                        String s = submessage.getDescriptorForType().getName();" + LS)
                .append("                        Object outer = null;" + LS)
                .append("                        if (s.contains(\"_INNER_\") || s.contains(\"_HIDDEN_\")) {"
                        + LS)
                .append("                           outer = object;" + LS)
                .append("                        }" + LS)
                .append("                        Object obj = fromJavabufMap.get(fd.getMessageType().getName()).assignFromJavabuf(submessage, outer);"
                        + LS)
                .append("                        Utility.setField(field, object, obj, INSTANCE);" + LS)
                .append("                     }" + LS)
                .append("                  } else if (JavaType.MESSAGE.equals(fd.getJavaType()) && Array_proto.dev_resteasy_grpc_arrays___ArrayHolder.class.getSimpleName().equals(fd.getMessageType().getName())) {"
                        + LS)
                .append("                     dev_resteasy_grpc_arrays___ArrayHolder submessage = (dev_resteasy_grpc_arrays___ArrayHolder) message.getField(fd);"
                        + LS)
                .append("                     Object o = ArrayUtility.getArray(translator, submessage);" + LS)
                .append("                  } else {" + LS)
                .append("                     Object ooo = message.getField(fd);" + LS)
                .append("                     if (Integer.class.equals(ooo.getClass()) && (Byte.class.equals(field.getType()) || byte.class.equals(field.getType()))) {"
                        + LS)
                .append("                        Utility.setField(field, object, ((Integer) ooo).byteValue(), INSTANCE);" + LS)
                .append("                     } else if (Integer.class.equals(ooo.getClass()) && (Short.class.equals(field.getType()) || short.class.equals(field.getType()))) {"
                        + LS)
                .append("                        Utility.setField(field, object, ((Integer) ooo).shortValue(), INSTANCE);" + LS)
                .append("                     } else if (Integer.class.equals(ooo.getClass()) && (Character.class.equals(field.getType()) || char.class.equals(field.getType()))) {"
                        + LS)
                .append("                        int i = ((Integer)ooo).intValue();" + LS)
                .append("                        Utility.setField(field, object, Character.toChars(i)[0], INSTANCE);" + LS)
                .append("                     } else if (ooo instanceof ByteString) {" + LS)
                .append("                        Utility.setField(field, object, ((ByteString) ooo).newInput().readAllBytes(), INSTANCE);"
                        + LS)
                .append("                     } else if (NULL.equals(ooo)) {" + LS)
                .append("                        Utility.setField(field, object, null, translator);" + LS)
                .append("                     } else {" + LS)
                .append("                        Utility.setField(field, object, ooo, INSTANCE);" + LS)
                .append("                  }" + LS)
                .append("               }" + LS)
                .append("            } catch (Exception e) {" + LS)
                .append("               throw new RuntimeException(e);" + LS)
                .append("            }" + LS)
                .append("         };" + LS)
                .append("         return assignFromJavabuf;" + LS)
                .append("      } catch (Exception e) {" + LS)
                .append("         throw new RuntimeException(e);" + LS)
                .append("      }" + LS)
                .append("   }" + LS + LS);
        sb.append("   private static String javaClassToJavabufClass(String javaClassName) {" + LS)
                .append("      String javabufClassName = javaClassName.replace(\".\", \"_\");" + LS)
                .append("      int i = javabufClassName.lastIndexOf(\"_\");" + LS)
                .append("      javabufClassName = javabufClassName.substring(0, i) + \"___\" + javabufClassName.substring(i + 1);"
                        + LS)
                .append("      return javabufClassName;" + LS)
                .append("   }" + LS + LS);
        sb.append("   private static String javabufClassToJavaClass(String fieldName) {" + LS)
                .append("      int pos = fieldName.lastIndexOf(\"___\");" + LS)
                .append("      if (pos >= 0) {" + LS)
                .append("         return fieldName.substring(0, pos);" + LS)
                .append("      }" + LS)
                .append("      return fieldName;" + LS)
                .append("   }" + LS + LS);
        sb.append("   private static FieldDescriptor getSuperField(Message message) {" + LS)
                .append("      Map<FieldDescriptor, Object> map = message.getAllFields();" + LS)
                .append("      for (FieldDescriptor fd : map.keySet()) {" + LS)
                .append("         if (fd.getName().endsWith(\"___super\")) {" + LS)
                .append("            return fd;" + LS)
                .append("         }" + LS)
                .append("      }" + LS)
                .append("      return null;" + LS)
                .append("   }" + LS + LS);
        sb.append(
                "   private static boolean isSuperClass(String fieldName) {" + LS +
                        "      return fieldName.endsWith(\"___super\");" + LS +
                        "   }" + LS + LS);
        sb.append("   private static String charsToString(Object o) {" + LS)
                .append("      StringBuilder sb = new StringBuilder();" + LS)
                .append("      if (char.class.equals(o.getClass().getComponentType())) {" + LS)
                .append("         char[] array = (char[]) o;" + LS)
                .append("         for (int i = 0; i < array.length; i++) {" + LS)
                .append("            sb.append(array[i]);" + LS)
                .append("         }" + LS)
                .append("      } else {" + LS)
                .append("           Character[] array = (Character[]) o;" + LS)
                .append("           for (int i = 0; i < array.length; i++) {" + LS)
                .append("              sb.append(array[i]);" + LS)
                .append("            }" + LS)
                .append("      }" + LS)
                .append("      return sb.toString();" + LS)
                .append("   }" + LS + LS);
        sb.append("   private static char[] stringToChars(String s) {" + LS)
                .append("      char[] cs = new char[s.length()];" + LS)
                .append("      for (int i = 0; i < s.length(); i++) {" + LS)
                .append("         cs[i] = s.charAt(i);" + LS)
                .append("      }" + LS)
                .append("      return cs;" + LS)
                .append("   }" + LS + LS);
        sb.append("   private static Character[] stringToCharacters(String s) {" + LS)
                .append("      Character[] cs = new Character[s.length()];" + LS)
                .append("      for (int i = 0; i < s.length(); i++) {" + LS)
                .append("         cs[i] = s.charAt(i);" + LS)
                .append("      }" + LS)
                .append("      return cs;" + LS)
                .append("   }" + LS + LS);
        sb.append("   private static ReturnJavaClass getReturnNonPublicJavaClass(final String classname) {" + LS)
                .append("      return new ReturnJavaClass() {" + LS)
                .append("         public Class<?> getJavaClass() {" + LS)
                .append("            try {" + LS)
                .append("               return Class.forName(classname);" + LS)
                .append("            } catch (Exception e) {" + LS)
                .append("               throw new RuntimeException(e);" + LS)
                .append("            }" + LS)
                .append("         }" + LS)
                .append("      };" + LS)
                .append("   }" + LS + LS);
        sb.append("   private static Constructor<?> getConstructor(String classname) throws Exception {" + LS)
                .append("      if (constructors.containsKey(classname)) {" + LS)
                .append("         return constructors.get(classname);" + LS)
                .append("      }" + LS)
                .append("      Constructor<?>[] conss = Class.forName(classname).getDeclaredConstructors();" + LS)
                .append("      if (conss.length == 0) {" + LS)
                .append("         return null;" + LS)
                .append("      }" + LS)
                .append("      Constructor<?> cons = conss[0];" + LS)
                .append("      for (int i = 1; i < conss.length; i++) {" + LS)
                .append("         if (conss[i].getParameterCount() < cons.getParameterCount()) {" + LS)
                .append("            cons = conss[i];" + LS)
                .append("         }" + LS)
                .append("      }" + LS)
                .append("      cons.setAccessible(true);" + LS)
                .append("      constructors.put(classname, cons);" + LS)
                .append("      return cons;" + LS)
                .append("   }" + LS + LS);
        sb.append("   static String squashName(String name) {" + LS)
                .append("      StringBuilder sb = new StringBuilder();" + LS)
                .append("      String s = name.replaceAll(\"\\\\.\", \"\");" + LS)
                .append("      s = s.replaceAll(\"_\", \"\");" + LS)
                .append("      return s.toLowerCase();" + LS)
                .append("   }" + LS + LS);
        for (Class<?> clazz : classes) {
            if (clazz.getName().endsWith("ELEMENT_WRAPPER")) {
                Class<?> builderClazz = null;
                Class<?> enumClazz = null;
                for (Class<?> innerClazz : clazz.getDeclaredClasses()) {
                    if (innerClazz.getName().endsWith("$Builder")) {
                        builderClazz = innerClazz;
                    }
                    if (innerClazz.getName().endsWith("MessageTypeCase")) {
                        enumClazz = innerClazz;
                    }
                }
                StringBuilder sb2 = new StringBuilder();
                Object[] os = enumClazz.getEnumConstants();
                if (os.length > 0) {
                    sb.append("   static Message wrapArrayElement(Message m, int position) {" + LS)
                            .append("      ELEMENT_WRAPPER.Builder builder = ELEMENT_WRAPPER.newBuilder();" + LS)
                            .append("      int pos = m.getDescriptorForType().getFullName().lastIndexOf('.');" + LS)
                            .append("      String name = squashName(m.getDescriptorForType().getFullName().substring(pos + 1));"
                                    + LS)
                            .append("      switch(name) {" + LS);
                    sb2.append("   static Message unwrapArrayElement(ELEMENT_WRAPPER wrapper) {" + LS);
                    Method setMethod = null;
                    for (int i = 0; i < os.length; i++) {
                        String enumSquash = squashName(os[i].toString());
                        for (Method m : builderClazz.getDeclaredMethods()) {
                            if (m.getName().startsWith("set") && !m.getParameterTypes()[0].getName().endsWith("Builder")) {
                                String classname = m.getName().substring(3, m.getName().length() - 5);
                                if ("".equals(classname)) {
                                    continue;
                                }
                                classname = classname.substring(0, 1).toLowerCase() + classname.substring(1);
                                String s = m.getName().substring(3, m.getName().length() - 5).toLowerCase();
                                if (enumSquash.equals(s)) {
                                    if (enumSquash.equals("googleprotobufany")) {
                                        enumSquash = "any";
                                    }
                                    setMethod = m;
                                    sb.append("         case \"").append(enumSquash).append("\":" + LS)
                                            .append("            builder.").append(m.getName()).append("((")
                                            .append(m.getParameterTypes()[0].getCanonicalName())
                                            .append(") m);" + LS)
                                            .append("            builder.setPosition(position);" + LS)
                                            .append("            return builder.build();" + LS);
                                    String hasMethod = "has" + m.getName().substring(3);
                                    String getMethod = "get" + m.getName().substring(3);
                                    sb2.append("      if (wrapper.").append(hasMethod).append("()) {" + LS)
                                            .append("         return wrapper.").append(getMethod).append("();" + LS)
                                            .append("      }" + LS);
                                }
                            }
                        }
                    }
                    sb.append("      }" + LS)
                            .append("      return m;" + LS)
                            .append("   }" + LS + LS);
                    sb2.append("      throw new RuntimeException(\"unrecognized field: \" + wrapper);" + LS)
                            .append("   }" + LS + LS);
                    sb.append(sb2);
                }
            }
        }
    }

    static String squashName(String name) {
        StringBuilder sb = new StringBuilder();
        int pos = name.indexOf('_');
        while (pos > 0) {
            sb.append(name.substring(0, pos));
            name = name.substring(pos + 1);
            pos = name.indexOf('_');
        }
        return sb.toString().toLowerCase();
    }

    private static void createTranslatorToJavabuf(String[] args, Class<?> clazz, StringBuilder sb) throws Exception {
        if ("gEmpty".equals(clazz.getSimpleName())
                || "gCookie".equals(clazz.getSimpleName())
                || "gHeader".equals(clazz.getSimpleName())
                || "ServletInfo".equals(clazz.getSimpleName())
                || "gNewCookie".equals(clazz.getSimpleName())
                || "FormMap".equals(clazz.getSimpleName())
                || "FormValues".equals(clazz.getSimpleName())
                || "ELEMENT_WRAPPER".equals(clazz.getSimpleName())) {
            return;
        }
        if ("dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___ArrayHolder".equals(clazz.getName())) {
            createArrayHolderTranslatorToJavabuf(args, sb);
            return;
        }
        if ("dev.resteasy.grpc.arrays".equals(clazz.getPackage().getName())) {
            return;
        }
        sb.append("   static class ")
                .append(fqnify(clazz.getSimpleName())).append("_ToJavabuf implements TranslateToJavabuf {" + LS);
        if (PRIMITIVE_WRAPPER_TYPES.containsKey(clazz.getSimpleName())) {
            String simpleJavabufName = clazz.getSimpleName();
            String simpleJavaName = simpleJavabufName.substring(1);
            sb.append("" + LS)
                    .append("      public Message assignToJavabuf(Object x) {" + LS)
                    .append("         if (x == null) {" + LS)
                    .append("            return null;" + LS)
                    .append("         }" + LS)
                    .append("         ")
                    .append(simpleJavaName)
                    .append(" p = (")
                    .append(simpleJavaName)
                    .append(") x;" + LS)
                    .append("         ")
                    .append(clazz.getCanonicalName())
                    .append(".Builder builder = ")
                    .append(clazz.getCanonicalName())
                    .append(".newBuilder();" + LS)
                    .append("         return builder.setValue(p")
                    .append(GET_METHODS.get(simpleJavaName))
                    .append(").build();" + LS)
                    .append("      }" + LS + LS)
                    .append("      public void clear() {" + LS)
                    .append("         //" + LS)
                    .append("      }" + LS);
        } else {
            String classname = null;
            if (clazz.getEnclosingClass() == null || Modifier.isPublic(clazz.getModifiers())) {
                classname = originalSimpleName(clazz.getSimpleName());
            } else {
                Class<?> enclosingClass = clazz.getEnclosingClass();
                classname = "Utility.getHiddenClass(" + enclosingClass.getName() + ", " + clazz.getSimpleName() + ")";
            }
            sb.append("      private static Descriptor descriptor = ").append(clazz.getCanonicalName())
                    .append(".getDescriptor();" + LS)
                    .append("      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();" + LS
                            + LS)
                    .append("      static {" + LS)
                    .append("         for (FieldDescriptor f : descriptor.getFields()) {" + LS)
                    .append("            String name = f.getName();" + LS)
                    .append("            if (descriptor.findFieldByName(name) == null) {" + LS)
                    .append("               continue;" + LS)
                    .append("            }" + LS)
                    .append("            assignList.add(toJavabuf(")
                    .append(getJavabufClassValue(clazz.getSimpleName(), false, false));
            sb.append(", descriptor.findFieldByName(name)));" + LS)
                    .append("         }" + LS)
                    .append("      }" + LS + LS)
                    .append("      public Message assignToJavabuf(Object c1) {" + LS)
                    .append("         if (c1 == null) {" + LS)
                    .append("            return null;" + LS)
                    .append("         }" + LS)
                    .append("         ").append(fqnify(clazz.getSimpleName())).append(".Builder builder = ")
                    .append(fqnify(clazz.getSimpleName())).append(".newBuilder();" + LS)
                    .append("         for (AssignToJavabuf assignTo : assignList) {" + LS)
                    .append("            try {" + LS)
                    .append("               assignTo.assign(c1, builder);" + LS)
                    .append("            } catch (Exception e) {" + LS)
                    .append("               throw new RuntimeException(e);" + LS)
                    .append("            }" + LS)
                    .append("         }" + LS)
                    .append("         return builder.build();" + LS)
                    .append("      }" + LS + LS)
                    .append("      public void clear() {" + LS)
                    .append("      }" + LS);
        }
        sb.append("   }" + LS + LS);
    }

    private static void createArrayHolderTranslatorToJavabuf(String[] args, StringBuilder sb) {
        sb.append("//////// ArrayHolder to Javabuf  ////////" + LS + LS);
        sb.append("   static class dev_resteasy_grpc_arrays___ArrayHolder_ToJavabuf implements TranslateToJavabuf {" + LS + LS)
                .append("      public Message assignToJavabuf(Object obj) {" + LS)
                .append("         return ArrayUtility.getHolder(translator, obj);" + LS)
                .append("      }" + LS + LS)
                .append("      public void clear() {" + LS)
                .append("         //" + LS)
                .append("      }" + LS)
                .append("   }" + LS);
    }

    private static void createTranslatorFromJavabuf(String[] args, Class<?> clazz, StringBuilder sb)
            throws Exception {
        if (clazz.isInterface()) {
            return;
        }
        String originalName = originalSimpleName(clazz.getName());
        if ("dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___ArrayHolder".equals(clazz.getName())) {
            createArrayHolderTranslatorFromJavabuf(args, sb);
            return;
        }
        if ("dev.resteasy.grpc.arrays".equals(clazz.getPackage().getName())) {
            return;
        }
        if ("gEmpty".equals(originalName)) {
            return;
        }
        if ("AbstractMessage".equals(clazz.getSimpleName())
                || "gCookie".equals(clazz.getSimpleName())
                || "gNewCookie".equals(clazz.getSimpleName())
                || "gHeader".equals(clazz.getSimpleName())
                || "ServletInfo".equals(clazz.getSimpleName())
                || "FormMap".equals(clazz.getSimpleName())
                || "FormValues".equals(clazz.getSimpleName())
                || "ELEMENT_WRAPPER".equals(clazz.getSimpleName())) {
            return;
        }
        if (PRIMITIVE_WRAPPER_TYPES.containsKey(originalName)) {
            sb.append("   static class ")
                    .append(fqnify(clazz.getSimpleName())).append("_FromJavabuf implements TranslateFromJavabuf {" + LS)
                    .append("      private static Descriptor descriptor = ").append(clazz.getCanonicalName())
                    .append(".getDescriptor();" + LS);
            String javaName = originalName.substring(1);
            if ("gByte".equals(originalName)) {
                sb.append("      public ").append(javaName)
                        .append(" assignFromJavabuf(Message message) {" + LS)
                        .append("         return assignFromJavabuf(message, null);" + LS)
                        .append("      }" + LS + LS)
                        .append("      public ").append(javaName)
                        .append(" assignFromJavabuf(Message message, Object outer) {" + LS)
                        .append("         FieldDescriptor fd = descriptor.getFields().get(0);" + LS)
                        .append("         return ((Integer) message.getField(fd)).byteValue();" + LS)
                        .append("      }" + LS + LS)
                        .append("      public void assignExistingFromJavabuf(Message message, Object obj) { }" + LS);
            } else if ("gShort".equals(originalName)) {
                sb.append("      public ").append(javaName)
                        .append(" assignFromJavabuf(Message message) {" + LS)
                        .append("         return assignFromJavabuf(message, null);" + LS)
                        .append("      }" + LS + LS)
                        .append("      public ").append(javaName)
                        .append(" assignFromJavabuf(Message message, Object outer) {" + LS)
                        .append("         FieldDescriptor fd = descriptor.getFields().get(0);" + LS)
                        .append("         return ((Integer) message.getField(fd)).shortValue();" + LS)
                        .append("      }" + LS + LS)
                        .append("      public void assignExistingFromJavabuf(Message message, Object obj) { }" + LS);
            } else if ("gCharacter".equals(originalName)) {
                sb.append("      public ").append(javaName)
                        .append(" assignFromJavabuf(Message message) {" + LS)
                        .append("         return assignFromJavabuf(message, null);" + LS)
                        .append("      }" + LS + LS)
                        .append("      public ").append(javaName)
                        .append(" assignFromJavabuf(Message message, Object outer) {" + LS)
                        .append("         FieldDescriptor fd = descriptor.getFields().get(0);" + LS)
                        .append("         return ((String) message.getField(fd)).charAt(0);" + LS)
                        .append("      }" + LS + LS)
                        .append("      public void assignExistingFromJavabuf(Message message, Object obj) { }" + LS);
            } else {
                sb.append("      public ").append(javaName)
                        .append(" assignFromJavabuf(Message message) {" + LS)
                        .append("         return assignFromJavabuf(message, null);" + LS)
                        .append("      }" + LS + LS)
                        .append("      public ").append(javaName)
                        .append(" assignFromJavabuf(Message message, Object outer) {" + LS)
                        .append("         FieldDescriptor fd = descriptor.getFields().get(0);" + LS)
                        .append("         return (").append(javaName).append(") message.getField(fd);" + LS)
                        .append("      }" + LS + LS)
                        .append("      public void assignExistingFromJavabuf(Message message, Object obj) { }" + LS);
            }
        } else {
            Constructor<?> cons = findConstructor(clazz, originalName);
            if (cons == null) { // abstract class
                return;
            }
            sb.append("   static class ")
                    .append(fqnify(clazz.getSimpleName())).append("_FromJavabuf implements TranslateFromJavabuf {" + LS)
                    .append("      private static Descriptor descriptor = ").append(clazz.getCanonicalName())
                    .append(".getDescriptor();" + LS);
            sb.append("      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();" + LS + LS)
                    .append("      static {" + LS)
                    .append("         for (FieldDescriptor f : descriptor.getFields()) {" + LS)
                    .append("            String name = f.getName();" + LS)
                    .append("            if (descriptor.findFieldByName(name) == null) {" + LS)
                    .append("               continue;" + LS)
                    .append("            }" + LS)
                    .append("            assignList.add(fromJavabuf(")
                    .append(getJavabufClassValue(clazz.getSimpleName(), false, false))
                    .append(", descriptor.findFieldByName(name)));" + LS)
                    .append("         }" + LS)
                    .append("      }" + LS + LS);
            if (clazz.getName().contains("_HIDDEN_")) {
                sb.append("\n      public Object assignFromJavabuf(Message message) {" + LS)
                        .append("         return assignFromJavabuf(message, null);" + LS)
                        .append("      }" + LS);
                sb.append("\n      public Object assignFromJavabuf(Message message, Object outer) {" + LS)
                        .append("         try {" + LS)
                        .append("            Object obj = getConstructor(\"")
                        .append(originalInnerClassName(clazz.getSimpleName()))
                        .append("\").newInstance(");
                writeArguments(cons, sb, true);
                sb.append(");" + LS)
                        .append("            for (AssignFromJavabuf assignFrom : assignList) {" + LS)
                        .append("               assignFrom.assign(message, obj);" + LS)
                        .append("            }" + LS)
                        .append("            return obj;" + LS)
                        .append("            } catch (Exception e) {" + LS)
                        .append("               throw new RuntimeException(e);" + LS)
                        .append("            }" + LS);
            } else {
                Constructor<?> con = findConstructor(clazz, originalName);
                Class<?> enclosingClass = null;
                if (!Modifier.isStatic(con.getDeclaringClass().getModifiers())) {
                    enclosingClass = con.getDeclaringClass().getEnclosingClass();
                }
                if (con != null) {
                    sb.append("      public ").append(originalName)
                            .append(" assignFromJavabuf(Message message) {" + LS)
                            .append("         return assignFromJavabuf(message, null);" + LS)
                            .append("      }" + LS + LS);
                    sb.append("      public ").append(originalName)
                            .append(" assignFromJavabuf(Message message, Object outer) {" + LS);
                    writeConstructor(enclosingClass, con, originalName, sb, clazz.getName().contains("_INNER_"));
                    sb.append(";" + LS)
                            .append("         for (AssignFromJavabuf assignFrom : assignList) {" + LS)
                            .append("            try {" + LS)
                            .append("               assignFrom.assign(message, obj);" + LS)
                            .append("            } catch (Exception e) {" + LS)
                            .append("               throw new RuntimeException(e);" + LS)
                            .append("            }" + LS)
                            .append("         }" + LS)
                            .append("         return obj;" + LS);
                } else {
                    sb.append("      public ").append(originalName)
                            .append(" assignFromJavabuf(Message message) {" + LS)
                            .append("      return assignFromJavabuf(message, null);" + LS)
                            .append("   }" + LS + LS);
                    sb.append("      public ").append(originalName)
                            .append(" assignFromJavabuf(Message message, Object outer) {" + LS);
                    sb.append("         return null;" + LS);
                }
            }
            sb.append("      }" + LS + LS);
            sb.append("      public void assignExistingFromJavabuf(Message message, Object obj) {" + LS)
                    .append("         for (AssignFromJavabuf assignFrom : assignList) {" + LS)
                    .append("            try {" + LS)
                    .append("               assignFrom.assign(message, obj);" + LS)
                    .append("            } catch (Exception e) {" + LS)
                    .append("               throw new RuntimeException(e);" + LS)
                    .append("            }" + LS)
                    .append("         }" + LS)
                    .append("      }" + LS);
        }
        sb.append("   }" + LS + LS);
    }

    private static void createArrayHolderTranslatorFromJavabuf(String[] args, StringBuilder sb) {
        sb.append("//////// ArrayHolder from Javabuf" + LS + LS)
                .append("   static class dev_resteasy_grpc_arrays___ArrayHolder_FromJavabuf implements TranslateFromJavabuf {"
                        + LS)
                .append("      public Object assignFromJavabuf(Message message) {" + LS)
                .append("         return assignFromJavabuf(message, null);" + LS)
                .append("      }" + LS)
                .append("\n      public Object assignFromJavabuf(Message message, Object outer) {" + LS)
                .append("         try {" + LS)
                .append("            return ArrayUtility.getArray(translator, (dev_resteasy_grpc_arrays___ArrayHolder) message);"
                        + LS)
                .append("         } catch (Exception e) {" + LS)
                .append("            throw new RuntimeException(e);" + LS)
                .append("         }" + LS)
                .append("      }" + LS + LS)
                .append("        public void assignExistingFromJavabuf(Message message, Object obj) {" + LS)
                .append("      }" + LS)
                .append("   }" + LS + LS);
        sb.append("   static Object getArray(Field field, int length) {" + LS)
                .append("      Class<?> clazz = field.getType();" + LS)
                .append("      Class<?> componentClass = clazz.getComponentType();" + LS)
                .append("      return Array.newInstance(componentClass, length);" + LS)
                .append("   }" + LS + LS);
    }

    private static void writePrivateMethods(StringBuilder sb) {
        sb.append("////////// private methods //////////" + LS)
                .append("   static private Object getOuterObject(Object o) {" + LS)
                .append("      try {" + LS)
                .append("         Field field = o.getClass().getDeclaredField(\"this$0\");" + LS)
                .append("         if (field == null) {" + LS)
                .append("            return null;" + LS)
                .append("         }" + LS)
                .append("         field.setAccessible(true);" + LS)
                .append("         return field.get(o);" + LS)
                .append("      } catch (Exception e) {" + LS)
                .append("         return null;" + LS)
                .append("      }" + LS)
                .append("   }" + LS + LS);
    }

    private static void finishClass(StringBuilder sb) {
        sb.append("}" + LS);
    }

    private static void writeTranslatorClass(String[] args, String translatorClass, StringBuilder sb) throws IOException {
        String pkgPath = args[1].lastIndexOf(".") < 0 ? ""
                : args[1].substring(0, args[1].lastIndexOf(".")).replace(".", File.separator);
        Path path = Files.createDirectories(Path.of(args[0], pkgPath));
        if (path.resolve(translatorClass + ".java").toFile().exists()) {
            return;
        }
        Files.writeString(path.resolve(translatorClass + ".java"), sb.toString(), StandardCharsets.UTF_8);
    }

    private static String fqnify(String s) {
        return s.replace(".", "_");
    }

    private static String originalSimpleName(String s) {
        int i = s.lastIndexOf("___");
        if (i >= 0) {
            return s.substring(i + 3).replace('$', '.');
        }
        // inner class
        i = s.indexOf("_INNER_");
        if (i >= 0) {
            return s.substring(i + "_INNER_".length());
        }
        i = s.indexOf("_HIDDEN_");
        if (i >= 0) {
            return s.substring(i + "_HIDDEN_".length());
        }
        // primitive class
        i = s.lastIndexOf("$");
        if (i >= 0) {
            return s.substring(i + 1);
        }
        if (PRIMITIVE_WRAPPER_TYPES.containsKey(s)) {
            return s.substring(1);
        }
        return s;
    }

    private static String originalCanonicalClassName(String s) {
        if (PRIMITIVE_WRAPPER_TYPES.containsKey(s)) {
            return "java.lang." + s.substring(1);
        }
        int i = s.indexOf("$");
        if (i >= 0) {
            s = s.substring(i + 1);
        }
        int j = s.lastIndexOf("___");
        if (j < 0) {
            j = s.indexOf("_INNER_");
        }
        if (j < 0) {
            j = s.indexOf("_HIDDEN_");
        }
        if (j >= 0) {
            String pkg = s.substring(0, j).replace('_', '.');
            return pkg + "." + originalSimpleName(s);
        }
        return s;
    }

    private static String originalClassName(String s) {
        if (PRIMITIVE_WRAPPER_TYPES.containsKey(s)) {
            return "java.lang." + s.substring(1);
        }
        int j = s.lastIndexOf("___");
        if (j >= 0) {
            String pkg = s.substring(0, j).replace('_', '.');
            return pkg + "." + originalSimpleName(s);
        }
        if (j < 0) {
            j = s.indexOf("_INNER_");
        }
        if (j < 0) {
            j = s.indexOf("_HIDDEN_");
        }
        if (j >= 0) {
            String pkg = s.substring(0, j).replace('_', '.');
            return pkg + "$" + originalSimpleName(s);
        }
        throw new RuntimeException("don't recognize: " + s);
    }

    private static String originalInnerClassName(String s) {
        int i = s.indexOf("$");
        if (i >= 0) {
            s = s.substring(i + 1);
        }
        int j = s.indexOf("_INNER_");
        if (j < 0) {
            j = s.indexOf("_HIDDEN_");
        }
        String pkg = s.substring(0, j).replace('_', '.');
        return pkg + "$" + originalSimpleName(s);
    }

    private static Constructor<?> findConstructor(Class<?> clazz, String originalName)
            throws ClassNotFoundException {
        try {
            String className = javabufToJava(clazz.getName(), originalName, true);
            Class<?> originalClazz = Class.forName(className);
            if (Modifier.isAbstract(originalClazz.getModifiers())) {
                return null;
            }
            Constructor<?>[] cons = originalClazz.getDeclaredConstructors();
            if (cons.length == 0) {
                return null;
            }
            Constructor<?> con = cons[0];
            if (cons.length > 1) {
                for (int i = 1; i < cons.length; i++) {
                    if (cons[i].getParameterCount() < con.getParameterCount()) {
                        con = cons[i];
                    }
                }
            }
            return con;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeConstructor(Class<?> enclosingClass, Constructor<?> con, String originalName, StringBuilder sb,
            boolean isInner) {
        if (enclosingClass != null) {
            sb.append("         ")
                    .append(enclosingClass.getSimpleName())
                    .append(" cast = (")
                    .append(enclosingClass.getSimpleName())
                    .append(") outer;" + LS);
            sb.append("         ").append(originalName).append(" obj = cast.new ").append(originalName)
                    .append("(");
            writeArguments(con, sb, false);
            sb.append(")");
        } else {
            sb.append("         ").append(originalName).append(" obj = new ").append(originalName).append("(");
            writeArguments(con, sb, false);
            sb.append(")");
        }
    }

    private static void writeArguments(Constructor<?> con, StringBuilder sb, boolean hidden) {
        boolean first = true;
        Class<?> enclosingClass = con.getDeclaringClass().getEnclosingClass();
        Class<?> declaringClass = con.getDeclaringClass();
        int start = enclosingClass != null && !Modifier.isStatic(declaringClass.getModifiers()) ? 1 : 0;
        if (hidden && con.getParameterCount() == 1) {
            if (PRIMITIVE_DEFAULTS.containsKey(con.getParameterTypes()[0])) {
                sb.append(PRIMITIVE_DEFAULTS.get(con.getParameterTypes()[0]));
            } else {
                sb.append("new Object[] {null}");
            }
        } else {
            for (int i = start; i < con.getParameterCount(); i++) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                if (PRIMITIVE_DEFAULTS.containsKey(con.getParameterTypes()[i])) {
                    sb.append(PRIMITIVE_DEFAULTS.get(con.getParameterTypes()[i]));
                } else {
                    sb.append("null");
                }
            }
        }
    }

    private static String javabufToJava(String javabufName, String simpleName, boolean canonical) {
        String tmp = javabufName;
        String tmpPkg = "";
        int n = tmp.lastIndexOf("$");
        if (n >= 0) {
            tmp = tmp.substring(n + 1);
        }
        n = tmp.lastIndexOf("___");
        if (n >= 0) {
            tmpPkg = tmp.substring(0, n);
        } else {
            n = tmp.indexOf("_INNER_");
            if (n >= 0) {
                tmpPkg = tmp.substring(0, n);
                tmpPkg = tmpPkg.replace("_", ".");
                if (canonical) {
                    tmp = tmpPkg + "$" + tmp.substring(n + "_INNER_".length());
                } else {
                    tmp = tmpPkg + "." + tmp.substring(n + "_INNER_".length());
                }
                return tmp;
            }
            n = tmp.indexOf("_HIDDEN_");
            if (n >= 0) {
                tmpPkg = tmp.substring(0, n);
                tmp = tmpPkg + "$" + tmp.substring(n + "_HIDDEN_".length());
                tmp = tmp.replace("_", ".");
                return tmp;
            }
        }
        if (tmpPkg.length() > 0) {
            tmpPkg = tmpPkg.replace("_", ".");
            return tmpPkg + "." + simpleName;
        }
        return simpleName;
    }

    private static String getJavabufClassValue(String simpleName, boolean finesseNonHiddenClasses, boolean canonical) {
        if (PRIMITIVE_WRAPPER_TYPES.containsKey(simpleName)) {
            return PRIMITIVE_WRAPPER_TYPES.get(simpleName).getName() + ".class";
        }
        try {
            if (simpleName.contains("_HIDDEN_")) {
                String classname = originalInnerClassName(simpleName);
                try {
                    Class<?> clazz = Class.forName(classname);
                    return "getReturnNonPublicJavaClass(\"" + clazz.getName() + "\").getJavaClass()";
                } catch (Exception e) {
                    return "";
                }
            }
            if (finesseNonHiddenClasses) {
                return "getReturnJavaClass(" + originalCanonicalClassName(simpleName) + ".class)";
            }
            return javabufToJava(simpleName, originalSimpleName(simpleName), canonical) + ".class";
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }
}
