package dev.resteasy.grpc.bridge.generator.protobuf;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jboss.logging.Logger;
import org.jboss.resteasy.plugins.providers.sse.OutboundSseEventImpl;

import dev.resteasy.grpc.bridge.runtime.servlet.HttpServletResponseImpl;

/**
 * Creates an implementation of MessageBodyReader&lt;Object&gt; and MessageBodyWriter&lt;Object&gt;
 * which uses &lt;prefix&gt;JavabufTranslator, generated by {@code JavabufTranslatorGenerator},
 * to translate back and forth between Java classes and their corresponding javabuf classes
 */
public class ReaderWriterGenerator {

    private static Logger logger = Logger.getLogger(ReaderWriterGenerator.class);
    private static String LS = System.lineSeparator();
    private static Map<String, String> primitives = new HashMap<String, String>();
    private static Set<String> internalClasses = new HashSet<String>();
    private static boolean hasSSE;
    private static Map<String, String> ENTITY_TRANSLATORS = new HashMap<String, String>();
    private static final Map<String, String> genericTypes = new HashMap<String, String>();

    private static String READER_WRITER_MAPS;
    private static String ENTITY_MAP_SETUP;
    private static String JAVA_TO_JAVABUF_NAME;
    private static String GET_PARSER;

    static {
        primitives.put("gBoolean", "boolean");
        primitives.put("gByte", "byte");
        primitives.put("gCharacter", "char");
        primitives.put("gDouble", "double");
        primitives.put("gEmpty", "ignore");
        primitives.put("gFloat", "float");
        primitives.put("gInteger", "int");
        primitives.put("gLong", "long");
        primitives.put("gShort", "short");
        primitives.put("gString", "String");
    }

    static {
        internalClasses.add("FormMap");
        internalClasses.add("FormValues");
        internalClasses.add("GeneralEntityMessage");
        internalClasses.add("GeneralReturnMessage");
        internalClasses.add("gCookie");
        internalClasses.add("gEmpty");
        internalClasses.add("gHeader");
        internalClasses.add("gNewCookie");
        internalClasses.add("ServletInfo");
    }

    static {
        ENTITY_TRANSLATORS.put("boolean",
                "dev_resteasy_grpc_arrays___Boolean___Array.class.getMethod(\"parseFrom\", InputStream.class)");
        ENTITY_TRANSLATORS.put("boolean[]",
                "dev_resteasy_grpc_arrays___Boolean___Array_ToJavabuf()");
        ENTITY_TRANSLATORS.put("Boolean[]",
                "new dev_resteasy_grpc_arrays___Boolean___WArray_ToJavabuf()");
        ENTITY_TRANSLATORS.put("byte[]",
                "new dev_resteasy_grpc_arrays___Byte___Array_ToJavabuf()");
        ENTITY_TRANSLATORS.put("Byte[]",
                "new dev_resteasy_grpc_arrays___Byte___WArray_ToJavabuf()");
        ENTITY_TRANSLATORS.put("short[]",
                "new dev_resteasy_grpc_arrays___Short___Array_ToJavabuf()");
        ENTITY_TRANSLATORS.put("Short[]",
                "new dev_resteasy_grpc_arrays___Short___WArray_ToJavabuf()");
        ENTITY_TRANSLATORS.put("int[]",
                "new dev_resteasy_grpc_arrays___Integer___Array_ToJavabuf()");
        ENTITY_TRANSLATORS.put("Integer[]",
                "new dev_resteasy_grpc_arrays___Integer___WArray_ToJavabuf()");
        ENTITY_TRANSLATORS.put("long[]",
                "new dev_resteasy_grpc_arrays___Long___Array_ToJavabuf()");
        ENTITY_TRANSLATORS.put("Long[]",
                "new dev_resteasy_grpc_arrays___Long___WArray_ToJavabuf()");
        ENTITY_TRANSLATORS.put("float[]",
                "new dev_resteasy_grpc_arrays___Float___Array_ToJavabuf()");
        ENTITY_TRANSLATORS.put("Float[]",
                "new dev_resteasy_grpc_arrays___Float___WArray_ToJavabuf()");
        ENTITY_TRANSLATORS.put("double[]",
                "new dev_resteasy_grpc_arrays___Double___Array_ToJavabuf()");
        ENTITY_TRANSLATORS.put("Double[]",
                "new dev_resteasy_grpc_arrays___Double___WArray_ToJavabuf()");
        ENTITY_TRANSLATORS.put("char[]",
                "new dev_resteasy_grpc_arrays___Character___Array_ToJavabuf()");
        ENTITY_TRANSLATORS.put("Character[]",
                "new dev_resteasy_grpc_arrays___Character___WArray_ToJavabuf()");
        ENTITY_TRANSLATORS.put("String[]",
                "new dev_resteasy_grpc_arrays___String___WArray_ToJavabuf()");
        ENTITY_TRANSLATORS.put("dev_resteasy_grpc_arrays___ArrayHolder",
                "new  dev_resteasy_grpc_arrays___ArrayHolder_ToJavabuf()");
        ENTITY_TRANSLATORS.put("dev_resteasy_grpc_arrays___ArrayHolder[]",
                "new  dev_resteasy_grpc_arrays___ArrayHolder___WArray_ToJavabuf()");
    }

    static {
        READER_WRITER_MAPS = "   static {" + LS +
                "      PRIMITIVE_MAP.put(\"boolean\", \"%1$s$gBoolean\");" + LS +
                "      PRIMITIVE_MAP.put(\"byte\",    \"%1$s$gByte\");" + LS +
                "      PRIMITIVE_MAP.put(\"short\",   \"%1$s$gShort\");" + LS +
                "      PRIMITIVE_MAP.put(\"int\",     \"%1$s$gInteger\");" + LS +
                "      PRIMITIVE_MAP.put(\"long\",    \"%1$s$gLong\");" + LS +
                "      PRIMITIVE_MAP.put(\"float\",   \"%1$s$gFloat\");" + LS +
                "      PRIMITIVE_MAP.put(\"double\",  \"%1$s$gDouble\");" + LS +
                "      PRIMITIVE_MAP.put(\"char\",    \"%1$s$gCharacter\");" + LS +
                "      PRIMITIVE_WRAPPER_MAP.put(\"java.lang.Boolean\",   \"%1$s$gBoolean\");" + LS +
                "      PRIMITIVE_WRAPPER_MAP.put(\"java.lang.Byte\",      \"%1$s$gByte\");" + LS +
                "      PRIMITIVE_WRAPPER_MAP.put(\"java.lang.Short\",     \"%1$s$gShort\");" + LS +
                "      PRIMITIVE_WRAPPER_MAP.put(\"java.lang.Integer\",   \"%1$s$gInteger\");" + LS +
                "      PRIMITIVE_WRAPPER_MAP.put(\"java.lang.Long\",      \"%1$s$gLong\");" + LS +
                "      PRIMITIVE_WRAPPER_MAP.put(\"java.lang.Float\",     \"%1$s$gFloat\");" + LS +
                "      PRIMITIVE_WRAPPER_MAP.put(\"java.lang.Double\",    \"%1$s$gDouble\");" + LS +
                "      PRIMITIVE_WRAPPER_MAP.put(\"java.lang.Character\", \"%1$s$gCharacter\");" + LS +
                "      PRIMITIVE_WRAPPER_MAP.put(\"java.lang.String\",    \"%1$s$gString\");" + LS +
                "" + LS +
                "      PRIMITIVE_ARRAY_MAP.put(\"boolean[]\", \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___Boolean___Array\");"
                + LS
                +
                "      PRIMITIVE_ARRAY_MAP.put(\"byte[]\",    \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___Byte___Array\");"
                + LS +
                "      PRIMITIVE_ARRAY_MAP.put(\"short[]\",   \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___Short___Array\");"
                + LS +
                "      PRIMITIVE_ARRAY_MAP.put(\"int[]\",     \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___Integer___Array\");"
                + LS
                +
                "      PRIMITIVE_ARRAY_MAP.put(\"long[]\",    \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___Long___Array\");"
                + LS +
                "      PRIMITIVE_ARRAY_MAP.put(\"float[]\",   \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___Float___Array\");"
                + LS +
                "      PRIMITIVE_ARRAY_MAP.put(\"double[]\",  \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___Double___Array\");"
                + LS
                +
                "      PRIMITIVE_ARRAY_MAP.put(\"char[]\",    \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___Character___Array\");"
                + LS
                +
                "      PRIMITIVE_ARRAY_MAP.put(\"java.lang.Boolean[]\",   \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___Boolean___WArray\");"
                + LS +
                "      PRIMITIVE_ARRAY_MAP.put(\"java.lang.Byte[]\",      \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___Byte___WArray\");"
                + LS +
                "      PRIMITIVE_ARRAY_MAP.put(\"java.lang.Short[]\",     \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___Short___WArray\");"
                + LS +
                "      PRIMITIVE_ARRAY_MAP.put(\"java.lang.Integer[]\",   \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___Integer___WArray\");"
                + LS +
                "      PRIMITIVE_ARRAY_MAP.put(\"java.lang.Long[]\",      \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___Long___WArray\");"
                + LS +
                "      PRIMITIVE_ARRAY_MAP.put(\"java.lang.Float[]\",     \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___Float___WArray\");"
                + LS +
                "      PRIMITIVE_ARRAY_MAP.put(\"java.lang.Double[]\",    \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___Double___WArray\");"
                + LS +
                "      PRIMITIVE_ARRAY_MAP.put(\"java.lang.Character[]\", \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___Character___WArray\");"
                + LS +
                "      PRIMITIVE_ARRAY_MAP.put(\"java.lang.String[]\",    \"dev.resteasy.grpc.arrays.Array_proto$dev_resteasy_grpc_arrays___String___WArray\");"
                + LS +
                "   }" + LS + LS;

        ENTITY_MAP_SETUP = "   static {%n"
                + "Path file = Paths.get(\"%1$s\", \"target\", \"entityTypes\");%n"
                + "        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {%n"
                + "            String line = reader.readLine();%n"
                + "            while (line != null) {%n"
                + "                int n = line.indexOf(\" \");%n"
                + "                String l1 = line.substring(0, n);%n"
                + "                String l2 = line.substring(n + 1);%n"
                + "                ENTITY_MAP.put(l1, getParser(l2));%n"
                + "                line = reader.readLine();%n"
                + "            }%n"
                + "        } catch (Exception e) {%n"
                + "            throw new RuntimeException(e);%n"
                + "        }%n"
                + "   }%n%n";

        GET_PARSER = "    private static Method getParser(String classname) throws Exception {%n"
                + "       return Class.forName(classname).getDeclaredMethod(\"parseFrom\", InputStream.class);%n"
                + "    }%n%n";

        JAVA_TO_JAVABUF_NAME = ""
                + "    private static String javaToJavabufName(String javaName) {%n"
                + "        try {%n"
                + "            if (PRIMITIVE_MAP.containsKey(javaName)) {%n"
                + "                return PRIMITIVE_MAP.get(javaName);%n"
                + "            }%n"
                + "            if (PRIMITIVE_WRAPPER_MAP.containsKey(javaName)) {%n"
                + "                return PRIMITIVE_WRAPPER_MAP.get(javaName);%n"
                + "            }%n"
                + "            if (javaName.endsWith(\"[][]\")) {%n"
                + "                return \"%1$s_proto$dev_resteasy_grpc_arrays___ArrayHolder___WArray\";%n"
                + "            }%n"
                + "            if (javaName.endsWith(\"[]\")) {%n"
                + "                if (PRIMITIVE_ARRAY_MAP.containsKey(javaName)) {%n"
                + "                    return PRIMITIVE_ARRAY_MAP.get(javaName);%n"
                + "                }%n"
                + "                String componentType = javaName.substring(0, javaName.length() - 2);%n"
                + "                if (PRIMITIVE_MAP.containsKey(componentType)) {%n"
                + "                    return componentType + \"___WArray\";%n"
                + "                }%n"
                + "                if (PRIMITIVE_WRAPPER_MAP.containsKey(componentType)) {%n"
                + "                    return componentType + \"___WArray\";%n"
                + "                }%n"
                + "                return javaToJavabufName(componentType) + \"___WArray\";%n"
                + "            }%n"
                + "            Class<?> clazz = Class.forName(javaName);%n"
                + "            if (clazz.getEnclosingClass() != null) {%n"
                + "                String pkg = clazz.getEnclosingClass().getPackageName().replaceAll(\".\", \"_\");%n"
                + "                String esn = clazz.getEnclosingClass().getSimpleName();%n"
                + "                String sn = clazz.getSimpleName();%n"
                + "                return \"%1$s_proto$\" + pkg + \"_\" + esn + \"___\" + sn;%n"
                + "            }%n"
                + "            String pkg = clazz.getPackageName().replace(\".\", \"_\");%n"
                + "            String sn = clazz.getSimpleName();%n"
                + "            return \"%1$s_proto$\" + pkg + \"___\" + sn;%n"
                + "        } catch (ClassNotFoundException e) {%n"
                + "            throw new RuntimeException(e);%n"
                + "        }%n"
                + "    }%n%n";
    }

    public static void main(String[] args) {
        if (args == null || args.length != 5) {
            logger.info("need five args:");
            logger.info("  arg[0]: root directory");
            logger.info("  arg[1]: javabuf wrapper class name");
            logger.info("  arg[2]: .proto file prefix");
            logger.info("  arg[3]: project base directory");
            logger.info("  arg[4): .proto file");
            return;
        }
        try {
            getListsAndSets(args[4]);
            String readerWriterClass = args[2] + "_MessageBodyReaderWriter";
            Class<?>[] wrappedClasses = JavabufTranslatorGenerator.getWrappedClasses(args);
            StringBuilder sbHeader = new StringBuilder();
            StringBuilder sbBody = new StringBuilder();
            classHeader(args, readerWriterClass, wrappedClasses, sbHeader);
            classBody(args, wrappedClasses, sbBody);
            getEntityTypeTable(args[3], sbBody);
            finishClass(sbBody);
            writeClass(wrappedClasses[0], args, sbHeader, sbBody);
        } catch (Exception e) {
            logger.error(e);

        }
    }

    private static void classHeader(String[] args, String readerWriterClass, Class<?>[] wrappedClasses, StringBuilder sb) {
        sb.append("package ").append(wrappedClasses[0].getPackage().getName()).append(";" + LS + LS);
        imports(wrappedClasses, args[2], sb);
    }

    private static void imports(Class<?>[] wrappedClasses, String rootClass, StringBuilder sb) {
        sb.append("import static dev.resteasy.grpc.bridge.runtime.Constants.ANY;" + LS)
                .append("import java.io.BufferedReader;" + LS)
                .append("import java.io.ByteArrayOutputStream;" + LS)
                .append("import java.io.File;" + LS)
                .append("import java.io.IOException;" + LS)
                .append("import java.io.InputStream;" + LS)
                .append("import java.io.OutputStream;" + LS)
                .append("import java.lang.annotation.Annotation;" + LS)
                .append("import java.lang.reflect.Method;" + LS)
                .append("import java.lang.reflect.Type;" + LS)
                .append("import java.nio.charset.StandardCharsets;" + LS)
                .append("import java.nio.file.Files;" + LS)
                .append("import java.nio.file.Paths;" + LS)
                .append("import java.nio.file.Path;" + LS)
                .append("import java.util.Map;" + LS)
                .append("import java.util.HashMap;" + LS)
                .append("import jakarta.annotation.Priority;" + LS)
                .append("import jakarta.ws.rs.Consumes;" + LS)
                .append("import jakarta.ws.rs.core.GenericType;" + LS)
                .append("import jakarta.ws.rs.Produces;" + LS)
                .append("import jakarta.ws.rs.WebApplicationException;" + LS)
                .append("import jakarta.ws.rs.core.MediaType;" + LS)
                .append("import jakarta.ws.rs.core.MultivaluedMap;" + LS)
                .append("import jakarta.ws.rs.ext.MessageBodyReader;" + LS)
                .append("import jakarta.ws.rs.ext.MessageBodyWriter;" + LS)
                .append("import jakarta.ws.rs.ext.Provider;" + LS)
                .append("import com.google.protobuf.GeneratedMessageV3;" + LS)
                .append("import com.google.protobuf.Any;" + LS)
                .append("import com.google.protobuf.Message;" + LS)
                .append("import com.google.protobuf.CodedInputStream;" + LS)
                .append("import com.google.protobuf.CodedOutputStream;" + LS)
                .append("import ").append("jakarta.servlet.http.HttpServletResponse;" + LS)
                .append("import ").append("dev.resteasy.grpc.bridge.runtime.servlet.AsyncMockServletOutputStream;" + LS)
                .append("import ").append("dev.resteasy.grpc.bridge.runtime.Utility;" + LS)
                .append("import ").append("dev.resteasy.grpc.arrays.Array_proto;" + LS)
                .append("import ").append("dev.resteasy.grpc.bridge.runtime.protobuf.JavabufTranslator;" + LS)
                .append("import dev.resteasy.grpc.example." + rootClass + "_proto.GeneralEntityMessage;" + LS)
                .append("import dev.resteasy.grpc.example." + rootClass + "_proto.GeneralReturnMessage;" + LS)
                .append("import ").append(OutboundSseEventImpl.class.getCanonicalName()).append(";" + LS)
                .append("import ").append(HttpServletResponseImpl.class.getCanonicalName()).append(";" + LS)
                .append("import org.jboss.resteasy.core.ResteasyContext;" + LS);
        for (Class<?> wrappedClass : wrappedClasses) {
            if (wrappedClass.isInterface()
                    || internalClasses.contains(wrappedClass.getSimpleName())
                    || wrappedClass.getSimpleName().endsWith("_wrapper")) {
                continue;
            }
            if ("SseEvent".equals(originalSimpleName(wrappedClass.getSimpleName()))) {
                hasSSE = true;
            }
            if (primitives.containsKey(wrappedClass.getSimpleName())) {
                sb.append("import ").append(wrappedClass.getName().replace("$", ".")).append(";" + LS);
            } else if ("GeneralEntityMessage".equals(wrappedClass.getSimpleName())
                    || "GeneralReturnMessage".equals(wrappedClass.getSimpleName())
                    || "ServletInfo".equals(wrappedClass.getSimpleName())
                    || "gNewCookie".equals(wrappedClass.getSimpleName())
                    || "gCookie".equals(wrappedClass.getSimpleName())
                    || "gHeader".equals(wrappedClass.getSimpleName())
                    || "FormMap".equals(wrappedClass.getSimpleName())
                    || "FormValues".equals(wrappedClass.getSimpleName())
                    || "dev_resteasy_grpc_arrays___ArrayHolder".equals(wrappedClass.getSimpleName())
                    || "dev_resteasy_grpc_arrays___ArrayHolder___WArray".equals(wrappedClass.getSimpleName())
                    || wrappedClass.getSimpleName().endsWith("_Array")
                    || wrappedClass.getSimpleName().endsWith("_WArray")) {
                sb.append("import ").append(wrappedClass.getName().replace("$", ".")).append(";" + LS);
            } else if (wrappedClass.getName().contains("_HIDDEN_")
                    || "dev_resteasy_grpc_arrays___NONE".equals(wrappedClass.getSimpleName())) {
                sb.append("import ").append(wrappedClass.getName().replace("$", ".")).append(";" + LS);
            } else if (genericTypes.containsKey(originalClassName(wrappedClass.getName()))) {
                sb.append("import ").append(wrappedClass.getName().replace("$", ".")).append(";" + LS);
            } else {
                sb.append("import ").append(wrappedClass.getName().replace("$", ".")).append(";" + LS);
                sb.append("import ").append(originalClassName(wrappedClass.getName())).append(";" + LS);
            }
        }
        sb.append("" + LS + LS);
    }

    private static void classBody(String[] args, Class<?>[] wrappedClasses, StringBuilder sb) {
        sb.append("@Provider" + LS)
                .append("@Consumes({\"application/grpc-jaxrs;grpc-jaxrs=true\",\"application/grpc-part\"})" + LS)
                .append("@Produces(\"*/*;grpc-jaxrs=true\")" + LS)
                .append("@Priority(Integer.MIN_VALUE)" + LS)
                .append("@SuppressWarnings(\"rawtypes\")" + LS)
                .append("public class ")
                .append(args[2])
                .append("MessageBodyReaderWriter implements MessageBodyReader<Object>, MessageBodyWriter<Object> {" + LS + LS)
                .append("   private static JavabufTranslator translator = new " + args[2] + "JavabufTranslator();" + LS)
                .append("   private static Map<String, Method> ENTITY_MAP = new HashMap<String, Method>();" + LS)
                .append("   private static Map<String, String> PRIMITIVE_ARRAY_MAP = new HashMap<String, String>();" + LS)
                .append("   private static Map<String, String> PRIMITIVE_MAP = new HashMap<String, String>();" + LS)
                .append("   private static Map<String, String> PRIMITIVE_WRAPPER_MAP = new HashMap<String, String>();" + LS
                        + LS)
                .append(String.format(READER_WRITER_MAPS, args[1] + "_proto"))
                .append(String.format(ENTITY_MAP_SETUP, args[3]).replace("\\", "\\\\"))
                .append("   @Override" + LS)
                .append("   public boolean isReadable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {"
                        + LS)
                .append("      if (type.isInterface()) {" + LS)
                .append("         return true;" + LS)
                .append("      } else if (type.isArray() && type.isArray()) {" + LS)
                .append("         return true;" + LS)
                .append("      } else {" + LS)
                .append("         return ")
                .append("translator.handlesFromJavabuf(type);" + LS)
                .append("      }" + LS)
                .append("   }" + LS + LS)
                .append("   @SuppressWarnings(\"unchecked\")" + LS)
                .append("   @Override" + LS)
                .append("   public Object readFrom(Class type, Type genericType, Annotation[] annotations, MediaType mediaType,"
                        + LS)
                .append("        MultivaluedMap httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {"
                        + LS)
                .append("      try {" + LS)
                .append("         Type oType = Utility.objectify(genericType);" + LS)
                .append("         String gt = oType.getTypeName().replace(\"class \", \"\").replace(\"interface \", \"\");"
                        + LS)
                .append("         if (type.isInterface() && !ENTITY_MAP.containsKey(gt)) {" + LS)
                .append("            Any any =  Any.parseFrom(CodedInputStream.newInstance(entityStream));" + LS)
                .append("            Class clazz = Utility.extractTypeFromAny(any, getClass().getClassLoader(), \"")
                .append(args[2]).append("_proto\");" + LS)
                .append("            Message m = any.unpack(clazz);" + LS)
                .append("            return ")
                .append("translator.translateFromJavabuf(m);" + LS)
                .append("         } else if (\"application/grpc-part\".equals(mediaType.toString())) {" + LS)
                .append("            return new String(entityStream.readAllBytes());" + LS)
                .append("         } else if (httpHeaders.getFirst(ANY) != null) {" + LS)
                .append("            Any any =  Any.parseFrom(CodedInputStream.newInstance(entityStream));" + LS)
                .append("            Message m = any.unpack(")
                .append("translator.translateToJavabufClass(type));" + LS)
                .append("            return ")
                .append("translator.translateFromJavabuf(m);" + LS)
                .append("         } else if (ENTITY_MAP.containsKey(gt)) {" + LS)
                .append("            GeneratedMessageV3 message = (GeneratedMessageV3) ENTITY_MAP.get(gt).invoke(null, entityStream);"
                        + LS)
                .append("            return translator.translateFromJavabuf(message);" + LS)
                .append("         } else {" + LS)
                .append("            GeneratedMessageV3 message = (GeneratedMessageV3) ENTITY_MAP.get(type.getName()).invoke(null, entityStream);"
                        + LS)
                .append("            return translator.translateFromJavabuf(message);" + LS)
                .append("         }" + LS)
                .append("      } catch (Exception e) {" + LS)
                .append("         throw new RuntimeException(e);" + LS)
                .append("      }" + LS)
                .append("   }" + LS + LS)
                .append("   @Override" + LS)
                .append("   public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {"
                        + LS)
                .append("      if (type.isArray() && type.isArray()) {" + LS)
                .append("         return true;" + LS)
                .append("      } else {" + LS)
                .append("         return translator.handlesToJavabuf(type);" + LS)
                .append("      }" + LS)
                .append("   }" + LS + LS)
                .append("   @Override" + LS)
                .append("   public void writeTo(Object t, Class type, Type genericType, Annotation[] annotations, MediaType mediaType,"
                        + LS)
                .append("      MultivaluedMap httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {"
                        + LS);
        if (hasSSE) {
            sb.append("      if (t instanceof OutboundSseEventImpl) {" + LS)
                    .append("         t = convertSseEvent((OutboundSseEventImpl) t);" + LS)
                    .append("      }" + LS);
        }
        sb.append("      Message message = null;" + LS)
                .append("      if (genericType != null) {" + LS)
                .append("         GenericType gt =  new GenericType(genericType);" + LS)
                .append("         if (gt.getRawType().isInterface()) {" + LS)
                .append("            message = translator.translateToJavabuf(t);" + LS)
                .append("         } else {" + LS)
                .append("            message = translator.translateToJavabuf(t, gt);" + LS)
                .append("         }" + LS)
                .append("      } else {" + LS)
                .append("         message = translator.translateToJavabuf(t);" + LS)
                .append("      }" + LS)
                .append("      HttpServletResponse servletResponse = ResteasyContext.getContextData(HttpServletResponse.class);"
                        + LS)
                .append("      if ((servletResponse != null && servletResponse.getHeader(ANY) != null)" + LS)
                .append("            || (genericType != null && new GenericType(genericType).getRawType().isInterface())) {"
                        + LS)
                .append("         if (servletResponse instanceof HttpServletResponseImpl) {" + LS)
                .append("            ((HttpServletResponseImpl) servletResponse).removeHeader(ANY);" + LS)
                .append("         }" + LS)
                .append("         CodedOutputStream cos = CodedOutputStream.newInstance(entityStream);" + LS)
                .append("         Any.pack(message).writeTo(cos);" + LS)
                .append("         cos.flush();" + LS)
                .append("         if (servletResponse.getOutputStream() instanceof AsyncMockServletOutputStream) {" + LS)
                .append("            AsyncMockServletOutputStream amsos = (AsyncMockServletOutputStream) servletResponse.getOutputStream();"
                        + LS)
                .append("            amsos.release();" + LS)
                .append("         }" + LS)
                .append("         return;" + LS)
                .append("      }" + LS)
                .append("      if (servletResponse.getOutputStream() instanceof AsyncMockServletOutputStream) {" + LS)
                .append("         AsyncMockServletOutputStream amsos = (AsyncMockServletOutputStream) servletResponse.getOutputStream();"
                        + LS)
                .append("         ByteArrayOutputStream baos = new ByteArrayOutputStream();" + LS)
                .append("         message.writeTo(baos);" + LS)
                .append("         amsos.release(baos);" + LS)
                .append("      } else {" + LS)
                .append("         CodedOutputStream cos = CodedOutputStream.newInstance(entityStream);" + LS)
                .append("         message.writeTo(cos);" + LS)
                .append("         cos.flush();" + LS)
                .append("      }" + LS)
                .append("   }" + LS + LS) // Turn into switch
                .append(String.format(GET_PARSER))
                .append(String.format(JAVA_TO_JAVABUF_NAME, args[1]));
        if (hasSSE) {
            sb.append("   private SseEvent convertSseEvent(OutboundSseEventImpl osei) throws IOException {" + LS)
                    .append("      SseEvent sseEvent = new SseEvent();" + LS)
                    .append("      sseEvent.setComment(osei.getComment());" + LS)
                    .append("      sseEvent.setData(convertData(osei));" + LS)
                    .append("      sseEvent.setId(osei.getId());" + LS)
                    .append("      sseEvent.setName(osei.getName());" + LS)
                    .append("      sseEvent.setReconnectDelay(osei.getReconnectDelay());" + LS)
                    .append("      return sseEvent;" + LS)
                    .append("   }" + LS + LS);
            sb.append("   private Any convertData(OutboundSseEventImpl osei) throws IOException {" + LS)
                    .append("      Message message = translator.translateToJavabuf(osei.getData());" + LS)
                    .append("      return Any.pack(message);" + LS)
                    .append("   }" + LS + LS);
        }
    }

    private static void finishClass(StringBuilder sb) {
        sb.append("}" + LS);
    }

    private static void writeClass(Class<?> wrapperClass, String[] args, StringBuilder sbHeader, StringBuilder sbBody)
            throws IOException {
        Path path = Files.createDirectories(Path.of(args[0], wrapperClass.getPackageName().replace(".", "/")));
        path = path.resolve(args[2] + "MessageBodyReaderWriter.java");
        if (path.toFile().exists()) {
            return;
        }
        Files.writeString(path, sbHeader.toString(), StandardCharsets.UTF_8);
        Files.writeString(path, sbBody.toString(), StandardCharsets.UTF_8, CREATE, APPEND, WRITE);
    }

    private static String javabufToJavaClass(String classname) {
        int i = classname.indexOf("___");
        if (i >= 0) {
            String simpleName = classname.substring(i + 3);
            if (primitives.containsKey(simpleName) && !"gEmpty".equals(simpleName)) {
                return "java.lang." + simpleName.substring(1);
            }
            return simpleName;
        } else {
            i = classname.indexOf("_INNER_");
            if (i >= 0) {
                return classname.substring(i + "_INNER_".length());
            } else {
                i = classname.indexOf("_HIDDEN_");
                if (i >= 0) {
                    return classname.substring(i + "_HIDDEN_".length());
                } else if (primitives.containsKey(classname) && !"gEmpty".equals(classname)) {
                    return "java.lang." + classname.substring(1);
                }
                return classname;
            }
        }
    }

    private static String originalClassName(String s) {
        int i = s.indexOf("$");
        int j = s.lastIndexOf("___");
        j = j < 0 ? s.indexOf("_INNER_") : j;
        j = j < 0 ? s.indexOf("_HIDDEN_") : j;
        j = j < 0 ? s.length() : j;
        String pkg = s.substring(i + 1, j).replace('_', '.');
        return pkg + "." + originalSimpleName(s);
    }

    private static String originalSimpleName(String s) {
        int i = s.lastIndexOf("___");
        if (i >= 0) {
            return s.substring(i + "___".length());
        }
        i = s.indexOf("_INNER_");
        if (i >= 0) {
            return s.substring(i + "_INNER_".length());
        }
        i = s.indexOf("_HIDDEN_");
        if (i >= 0) {
            return s.substring(i + "_HIDDEN_".length());
        }
        return s;
    }

    public static String getJavabufClassname(String classname) {
        String javabufClassname = classname.replace('.', '_');
        int pos = javabufClassname.lastIndexOf('_');
        javabufClassname = javabufClassname.substring(0, pos) + "__" + javabufClassname.substring(pos);
        if (javabufClassname.startsWith("[")) {
            javabufClassname = javabufClassname.substring(2, javabufClassname.length() - 1) + "___WArray";
        }
        return javabufClassname;
    }

    private static void getEntityTypeTable(String path, StringBuilder sb) throws IOException {
        final var file = Path.of(path + "/target/entityTypes");
        if (Files.notExists(file)) {
            throw new RuntimeException(path + "/target/entityTypes not found");
        }
    }

    private String javaToJavabufName(String javaName) {
        try {
            Class<?> clazz = Class.forName(javaName);
            if (clazz.getEnclosingClass() != null) {
                String pkg = clazz.getEnclosingClass().getPackageName().replaceAll(".", "_");
                String esn = clazz.getEnclosingClass().getSimpleName();
                String sn = clazz.getSimpleName();
                return pkg + "_" + esn + "___" + sn;
            }
            String pkg = clazz.getEnclosingClass().getPackageName().replaceAll(".", "_");
            String sn = clazz.getSimpleName();
            return pkg + "___" + sn;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static void getListsAndSets(String path) {
        Path file = Paths.get(path);
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains("//////////  synthetic names: //////////")) {
                    break;
                }
            }
            while ((line = reader.readLine()) != null) {
                String syntheticName = line.substring(3, line.indexOf("->"));
                String originalName = line.substring(line.indexOf("->") + 2);
                genericTypes.put(syntheticName, originalName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
