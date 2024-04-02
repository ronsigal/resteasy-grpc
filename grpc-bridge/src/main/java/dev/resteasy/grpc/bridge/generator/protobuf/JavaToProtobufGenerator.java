package dev.resteasy.grpc.bridge.generator.protobuf;

import static dev.resteasy.grpc.bridge.runtime.Constants.ANY;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.github.javaparser.ParseResult;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedFieldDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedTypeDeclaration;
import com.github.javaparser.resolution.types.ResolvedArrayType;
import com.github.javaparser.resolution.types.ResolvedReferenceType;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserClassDeclaration;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.model.typesystem.ReferenceTypeImpl;
import com.github.javaparser.symbolsolver.reflectionmodel.ReflectionClassDeclaration;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.utils.Log;
import com.github.javaparser.utils.SourceRoot;

import dev.resteasy.grpc.bridge.runtime.servlet.HttpServletRequestImpl;

/**
 * Traverses a set of Jakarta REST resources and creates a protobuf representation.
 * <p/>
 * <ol>
 * <li>Find all Jakarta REST resource methods and resource locators and create an rpc entry for each</li>
 * <li>Find the transitive closure of the classes mentioned in the resource methods and locators
 * and create a message entry for each.</li>
 * </ol>
 * <p/>
 * </pre>
 * For example,
 * <p/>
 *
 * <pre>
 * public class CC1 {
 *
 *     &#064;Path("m1")
 *     &#064;GET
 *     String m1(CC2 cc2) {
 *         return "x";
 *     }
 *
 *     String m2(String s) {
 *         return "x";
 *     }
 *
 *     &#064;Path("m3")
 *     &#064;GET
 *     String m3(CC4 cc4) {
 *         return "x";
 *     }
 * }
 * </pre>
 *
 * together with the class definitions
 * <p/>
 *
 * <pre>
 * package io.grpc.examples;
 *
 * public class CC2 extends CC3 {
 *    int j;
 *
 *    public CC2(String s, int j) {
 *       super(s);
 *       this.j = j;
 *    }
 *
 *    public CC2() {}
 * }
 *
 * ========================
 * package io.grpc.examples;
 *
 * public class CC3 {
 *    String s;
 *
 *    public CC3(String s) {
 *       this.s = s;
 *    }
 *
 *    public CC3() {}
 * }
 *
 * ========================
 * package io.grpc.examples;
 *
 * public class CC4 {
 *    private String s;
 *    private CC5 cc5;
 *
 *    public CC4(String s, CC5 cc5) {
 *       this.s = s;
 *       this.cc5 = cc5;
 *    }
 *
 *    public CC4() {}
 * }
 *
 * ========================
 * package io.grpc.examples;
 *
 * public class CC5 {
 *    int k;
 *
 *    public CC5(int k) {
 *       this.k = k;
 *    }
 *
 *    public CC5() {}
 * }
 * </pre>
 *
 * is translated to CC1.proto:
 * <p/>
 *
 * <pre>
 * syntax = "proto3";
 * package io.grpc.examples;
 * option java_package = "io.grpc.examples";
 * option java_outer_classname = "CC1_proto";
 *
 * service CC1Service {
 *    rpc m1 (GeneralEntityMessage) returns (GeneralReturnMessage);
 *    rpc m3 (GeneralEntityMessage) returns (GeneralReturnMessage);
 * }
 *
 * message io_grpc_examples___CC2 {
 *    int32 j = 1;
 *    io_grpc_examples___CC3 cC3___super = 2;
 * }
 *
 * message io_grpc_examples___CC4 {
 *    string s = 1;
 *    io_grpc_examples___CC5 cc5 = 2;
 * }
 *
 * message io_grpc_examples___CC3 {
 *    string s = 1;
 * }
 *
 * message io_grpc_examples___CC5 {
 *    int32 k = 1;
 * }
 *
 * ...
 *
 * message GeneralEntityMessage {
 *    ServletInfo servletInfo = 1;
 *    string URL = 2;
 *    map&lt;string, gHeader&gt; headers = 3;
 *    repeated gCookie cookies = 4;
 *    string httpMethod = 5;
 *    oneof messageType {
 *       io_grpc_examples___CC4 io_grpc_examples___CC4_field = 6;
 *       io_grpc_examples___CC2 io_grpc_examples___CC2_field = 7;
 *       FormMap form_field = 8;
 *    }
 * }
 *
 * message GeneralReturnMessage {
 *    map<string, gHeader> headers = 1;
 *    repeated gNewCookie cookies = 2;
 *    gInteger status = 3;
 *    oneof messageType {
 *       gString gString_field = 4;
 *    }
 * }
 * </pre>
 * <p/>
 * <b>Notes.</b>
 * <ol>
 * <li>{@code CC1.m2()} is not a resource method, so it does not appear in CC1.proto.
 * <li>Protobuf syntax does not support inheritance, so {@code JavaToProtobufGenerator}
 * treats a superclass as a special field. For example, {@code CC2} is a subclass of {@code CC3},
 * so each instance of {@code CC2} has a field named {@code cC3___super} of {@code type io_grpc_examples___CC3}.
 * <li>{@code GeneralEntityMessage} and {@code GeneralReturnMessage} are general purpose classes for conveying
 * entity parameters to the server and responses back to the client. They are defined to hold all possible entity
 * and return types plus a variety of additional fields. For more information, see the User Guide.
 * </ol>
 */
public class JavaToProtobufGenerator {

    private static final Logger logger = Logger.getLogger(JavaToProtobufGenerator.class);
    private static final String LS = System.lineSeparator();

    private static Map<String, String> TYPE_MAP = new HashMap<String, String>();
    private static Map<String, String> PRIMITIVE_WRAPPER_TYPES_FIELD = new HashMap<String, String>();
    private static Map<String, String> PRIMITIVE_WRAPPER_TYPES_IO = new HashMap<String, String>();
    private static Map<String, String> PRIMITIVE_WRAPPER_DEFINITIONS = new HashMap<String, String>();
    private static Set<String> ANNOTATIONS = new HashSet<String>();
    private static Set<String> HTTP_VERBS = new HashSet<String>();
    private static String prefix;
    private static boolean needEmpty = false;

    private static Set<ResolvedReferenceTypeDeclaration> resolvedTypes = ConcurrentHashMap.newKeySet();
    private static List<String> interfaces = new CopyOnWriteArrayList<String>();
    private static Set<String> entityMessageTypes = new HashSet<String>();
    private static Set<String> returnMessageTypes = new HashSet<String>();
    private static Set<String> jars;
    private static Set<String> additionalClasses;// = new CopyOnWriteArraySet<String>();
    private static Set<String> visited = new HashSet<String>();
    private static JavaSymbolSolver symbolSolver;
    private static ClassVisitor classVisitor = new ClassVisitor();
    private static JakartaRESTResourceVisitor jakartaRESTResourceVisitor = new JakartaRESTResourceVisitor();
    private static boolean started = false;
    private static int counter = 1;
    private static boolean isSSE;
    private static String SSE_EVENT_CLASSNAME = "dev_resteasy_grpc_bridge_runtime_sse___SseEvent";
    private static StringBuilder wrapperBuilder = new StringBuilder();
    private static Set<String> repeatedTypes = new HashSet<String>();
    private static Map<String, String> REPEAT_MAP = new HashMap<String, String>();

    static {
        TYPE_MAP.put("boolean", "bool");
        TYPE_MAP.put("byte", "int32");
        TYPE_MAP.put("short", "int32");
        TYPE_MAP.put("int", "int32");
        TYPE_MAP.put("long", "int64");
        TYPE_MAP.put("float", "float");
        TYPE_MAP.put("double", "double");
        TYPE_MAP.put("boolean", "bool");
        TYPE_MAP.put("char", "string");
        TYPE_MAP.put("String", "string");
        TYPE_MAP.put("java.lang.String", "string");

        REPEAT_MAP.put("bool", "gBoolean");
        REPEAT_MAP.put("int32", "gInteger");
        REPEAT_MAP.put("int64", "gInteger");
        REPEAT_MAP.put("float", "gFloat");
        REPEAT_MAP.put("double", "gDouble");
        REPEAT_MAP.put("string", "gString");
        REPEAT_MAP.put("byte", "gByte");

        PRIMITIVE_WRAPPER_TYPES_IO.put("boolean", "gBoolean");
        PRIMITIVE_WRAPPER_TYPES_IO.put("byte", "gByte");
        PRIMITIVE_WRAPPER_TYPES_IO.put("short", "gShort");
        PRIMITIVE_WRAPPER_TYPES_IO.put("int", "gInteger");
        PRIMITIVE_WRAPPER_TYPES_IO.put("long", "gLong");
        PRIMITIVE_WRAPPER_TYPES_IO.put("float", "gFloat");
        PRIMITIVE_WRAPPER_TYPES_IO.put("double", "gDouble");
        PRIMITIVE_WRAPPER_TYPES_IO.put("boolean", "gBoolean");
        PRIMITIVE_WRAPPER_TYPES_IO.put("char", "gCharacter");
        PRIMITIVE_WRAPPER_TYPES_IO.put("string", "gString");
        PRIMITIVE_WRAPPER_TYPES_IO.put("Boolean", "gBoolean");
        PRIMITIVE_WRAPPER_TYPES_IO.put("Byte", "gByte");
        PRIMITIVE_WRAPPER_TYPES_IO.put("Short", "gShort");
        PRIMITIVE_WRAPPER_TYPES_IO.put("Integer", "gInteger");
        PRIMITIVE_WRAPPER_TYPES_IO.put("Long", "gLong");
        PRIMITIVE_WRAPPER_TYPES_IO.put("Float", "gFloat");
        PRIMITIVE_WRAPPER_TYPES_IO.put("Double", "gDouble");
        PRIMITIVE_WRAPPER_TYPES_IO.put("Boolean", "gBoolean");
        PRIMITIVE_WRAPPER_TYPES_IO.put("Character", "gCharacter");
        PRIMITIVE_WRAPPER_TYPES_IO.put("String", "gString");
        PRIMITIVE_WRAPPER_TYPES_IO.put("java.lang.String", "gString");
        PRIMITIVE_WRAPPER_TYPES_IO.put("java.lang.Byte", "gByte");
        PRIMITIVE_WRAPPER_TYPES_IO.put("java.lang.Short", "gShort");
        PRIMITIVE_WRAPPER_TYPES_IO.put("java.lang.Integer", "gInteger");
        PRIMITIVE_WRAPPER_TYPES_IO.put("java.lang.Long", "gLong");
        PRIMITIVE_WRAPPER_TYPES_IO.put("java.lang.Float", "gFloat");
        PRIMITIVE_WRAPPER_TYPES_IO.put("java.lang.Double", "gDouble");
        PRIMITIVE_WRAPPER_TYPES_IO.put("java.lang.Boolean", "gBoolean");
        PRIMITIVE_WRAPPER_TYPES_IO.put("java.lang.Character", "gCharacter");
        PRIMITIVE_WRAPPER_TYPES_IO.put("java.lang.String", "gString");

        PRIMITIVE_WRAPPER_TYPES_FIELD.put("Boolean", "bool");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("Byte", "int32");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("Short", "int32");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("Integer", "int32");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("Long", "int64");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("Float", "float");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("Double", "double");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("Character", "int32");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("String", "string");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("java.lang.Boolean", "bool");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("java.lang.Byte", "int32");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("java.lang.Short", "int32");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("java.lang.Integer", "int32");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("java.lang.Long", "int64");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("java.lang.Float", "float");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("java.lang.Double", "double");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("java.lang.Character", "int32");
        PRIMITIVE_WRAPPER_TYPES_FIELD.put("java.lang.String", "string");

        PRIMITIVE_WRAPPER_DEFINITIONS.put("Boolean", "message gBoolean   {bool   value = $V$;}");
        PRIMITIVE_WRAPPER_DEFINITIONS.put("Byte", "message gByte      {int32  value = $V$;}");
        PRIMITIVE_WRAPPER_DEFINITIONS.put("Short", "message gShort     {int32  value = $V$;}");
        PRIMITIVE_WRAPPER_DEFINITIONS.put("Integer", "message gInteger   {int32  value = $V$;}");
        PRIMITIVE_WRAPPER_DEFINITIONS.put("Long", "message gLong      {int64  value = $V$;}");
        PRIMITIVE_WRAPPER_DEFINITIONS.put("Float", "message gFloat     {float  value = $V$;}");
        PRIMITIVE_WRAPPER_DEFINITIONS.put("Double", "message gDouble    {double value = $V$;}");
        PRIMITIVE_WRAPPER_DEFINITIONS.put("Character", "message gCharacter {string value = $V$;}");
        PRIMITIVE_WRAPPER_DEFINITIONS.put("String", "message gString    {string value = $V$;}");

        ANNOTATIONS.add("Context");
        ANNOTATIONS.add("CookieParam");
        ANNOTATIONS.add("HeaderParam");
        ANNOTATIONS.add("MatrixParam");
        ANNOTATIONS.add("PathParam");
        ANNOTATIONS.add("QueryParam");

        HTTP_VERBS.add("DELETE");
        HTTP_VERBS.add("HEAD");
        HTTP_VERBS.add("GET");
        HTTP_VERBS.add("OPTIONS");
        HTTP_VERBS.add("PATCH");
        HTTP_VERBS.add("POST");
        HTTP_VERBS.add("PUT");
    }

    public static void main(String[] args) throws IOException {
        if (args == null || (args.length < 4)) {
            logger.info("need four args");
            logger.info("  arg[0]: root directory");
            logger.info("  arg[1]: package to be used in .proto file");
            logger.info("  arg[2]: java package to be used in .proto file");
            logger.info("  arg[3]: java outer classname to be generated from .proto file");
            logger.info("  -Djars: comma separated of jars [optional]");
            logger.info("  -Dclasses: comma separated of addition classes [optional]");
            return;
        }
        prefix = args[3];
        String s = System.getProperty("jars", "default");
        jars = "default".equals(s) || "".equals(s)
                ? new CopyOnWriteArraySet<String>()
                : new CopyOnWriteArraySet<String>(Arrays.asList(s.split(",")));
        s = System.getProperty("classes", "default");
        additionalClasses = "default".equals(s) || "".equals(s)
                ? new CopyOnWriteArraySet<String>()
                : new CopyOnWriteArraySet<String>(Arrays.asList(s.split(",")));
        StringBuilder sb = new StringBuilder();
        protobufHeader(args, sb);
        new JavaToProtobufGenerator().processClasses(args, sb);
        int i = 0;
        while (!resolvedTypes.isEmpty() && i++ < 100) {
            for (ResolvedReferenceTypeDeclaration rrtd : resolvedTypes) {
                classVisitor.visit(rrtd, sb);
            }
        }
        finishProto(sb);
        writeProtoFile(args, sb);
        createProtobufDirectory(args);
    }

    private static void protobufHeader(String[] args, StringBuilder sb) {
        sb.append("syntax = \"proto3\";" + LS);
        sb.append("package " + args[1].replace('-', '.') + ";" + LS);
        sb.append("import \"google/protobuf/any.proto\";" + LS);
        sb.append("import \"google/protobuf/timestamp.proto\";" + LS);
        sb.append("import \"dev/resteasy/grpc/arrays/arrays.proto\";" + LS);
        sb.append("option java_package = \"" + args[2] + "\";" + LS);
        sb.append("option java_outer_classname = \"" + args[3] + "_proto\";" + LS);
    }

    /**
     * Visit all Jakarta REST resource classes discovered in project's src/main/java
     */
    private void processClasses(String[] args, StringBuilder sb) throws IOException {
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());

        // SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
        Path path = Path.of(args[0], "/src/main/java/");
        SourceRoot sourceRoot = new SourceRoot(path);
        TypeSolver reflectionTypeSolver = new ReflectionTypeSolver();
        TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(path);
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(reflectionTypeSolver);
        combinedTypeSolver.add(javaParserTypeSolver);
        for (Iterator<String> it = jars.iterator(); it.hasNext();) {
            String s = it.next();
            combinedTypeSolver.add(new JarTypeSolver(s));
        }
        symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
        sourceRoot.getParserConfiguration().setSymbolResolver(symbolSolver);
        List<ParseResult<CompilationUnit>> list = sourceRoot.tryToParse();
        for (ParseResult<CompilationUnit> p : list) {
            jakartaRESTResourceVisitor.visit(p.getResult().get(), sb);
        }
        if (started) {
            sb.append("}" + LS);
        }
        processAdditionalClasses(symbolSolver, sb);
    }

    /*********************************************************************/
    /*********************** primary methods *****************************/
    /*********************************************************************/

    private static void processAdditionalClasses(JavaSymbolSolver symbolSolver, StringBuilder sb) throws FileNotFoundException {
        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);
        for (String filename : additionalClasses) {
            int n = filename.indexOf(":");
            if (n < 0) {
                throw new RuntimeException("bad syntax: " + filename);
            }
            String dir = filename.substring(0, n).trim();
            filename = dir + "/" + filename.substring(n + 1).replace(".", "/") + ".java";
            CompilationUnit cu = StaticJavaParser.parse(new File(filename));
            ClassVisitor classVisitor = new ClassVisitor();
            classVisitor.visit(cu, sb);
        }
        if (isSSE) {
            sb.append(LS)
                    .append("message dev_resteasy_grpc_bridge_runtime_sse___SseEvent {" + LS)
                    .append("  string comment = ").append(counter++).append(";").append(LS)
                    .append("  string id = ").append(counter++).append(";").append(LS)
                    .append("  string name = ").append(counter++).append(";").append(LS)
                    .append("  google.protobuf.Any data = ").append(counter++).append(";").append(LS)
                    .append("  int64 reconnectDelay = ").append(counter++).append(";").append(LS)
                    .append("}").append(LS);
        }
    }

    private static void finishProto(StringBuilder sb) {
        if (needEmpty) {
            sb.append(LS + "message gEmpty {}");
            entityMessageTypes.add("gEmpty");
            returnMessageTypes.add("gEmpty");
        }
        for (String wrapper : PRIMITIVE_WRAPPER_DEFINITIONS.values()) {
            counter = 1;
            sb.append(LS).append(wrapper.replace("$V$", String.valueOf(counter++)));
        }
        createGeneralEntityMessageType(sb);
        createGeneralReturnMessageType(sb);
    }

    private static void createGeneralEntityMessageType(StringBuilder sb) {
        counter = 1;
        sb.append(LS + LS + "message gHeader {" + LS).append("   repeated string values = ").append(counter++)
                .append(";" + LS + "}");
        counter = 1;
        sb.append(LS + LS + "message gCookie {" + LS)
                .append("   string name = ").append(counter++).append(";" + LS)
                .append("   string value = ").append(counter++).append(";" + LS)
                .append("   int32  version = ").append(counter++).append(";" + LS)
                .append("   string path = ").append(counter++).append(";" + LS)
                .append("   string domain = ").append(counter++).append(";" + LS)
                .append("}");
        counter = 1;
        sb.append(LS + LS + "message gNewCookie {" + LS)
                .append("   string name = ").append(counter++).append(";" + LS)
                .append("   string value = ").append(counter++).append(";" + LS)
                .append("   int32  version = ").append(counter++).append(";" + LS)
                .append("   string path = ").append(counter++).append(";" + LS)
                .append("   string domain = ").append(counter++).append(";" + LS)
                .append("   string comment = ").append(counter++).append(";" + LS)
                .append("   int32 maxAge = ").append(counter++).append(";" + LS)
                .append("   google.protobuf.Timestamp expiry = ").append(counter++).append(";" + LS)
                .append("   bool secure = ").append(counter++).append(";" + LS)
                .append("   bool httpOnly = ").append(counter++).append(";" + LS + LS)
                .append("   enum SameSite {" + LS)
                .append("      NONE   = 0;" + LS)
                .append("      LAX    = 1;" + LS)
                .append("      STRICT = 2;" + LS)
                .append("   }" + LS + LS)
                .append("   SameSite sameSite = ").append(counter++).append(";" + LS)
                .append("}");
        counter = 1;
        sb.append(LS + LS + "message ServletInfo {" + LS)
                .append("   string characterEncoding = ").append(counter++).append(";" + LS)
                .append("   string clientAddress = ").append(counter++).append(";" + LS)
                .append("   string clientHost = ").append(counter++).append(";" + LS)
                .append("   int32  clientPort = ").append(counter++).append(";" + LS)
                .append("}");
        counter = 1;
        sb.append(LS + LS + "message FormValues {" + LS)
                .append("   repeated string formValues_field = ").append(counter++).append(";" + LS)
                .append("}");
        counter = 1;
        sb.append(LS + LS + "message FormMap {" + LS)
                .append("   map<string, FormValues> formMap_field = ").append(counter++).append(";" + LS)
                .append("}");
        counter = 1;
        sb.append(LS + LS + "message GeneralEntityMessage {" + LS)
                .append("   ServletInfo servletInfo = ").append(counter++).append(";" + LS)
                .append("   string URL = ").append(counter++).append(";" + LS)
                .append("   map<string, gHeader> headers = ").append(counter++).append(";" + LS)
                .append("   repeated gCookie cookies = ").append(counter++).append(";" + LS)
                .append("   string httpMethod = ").append(counter++).append(";" + LS)
                .append("   oneof messageType {" + LS);
        for (String messageType : entityMessageTypes) {
            if (ANY.equals(messageType)) {
                continue;
            }
            sb.append("      ")
                    .append(messageType)
                    .append(" ")
                    .append(namify(messageType)).append("_field")
                    .append(" = ")
                    .append(counter++)
                    .append(";" + LS);
        }
        sb.append("      FormMap form_field = ").append(counter++).append(";" + LS);
        sb.append("   }" + LS + "}" + LS);
    }

    private static void createGeneralReturnMessageType(StringBuilder sb) {
        counter = 1;
        sb.append(LS + "message GeneralReturnMessage {" + LS)
                .append("   map<string, gHeader> headers = ").append(counter++).append(";" + LS)
                .append("   repeated gNewCookie cookies = ").append(counter++).append(";" + LS)
                .append("   gInteger status = ").append(counter++).append(";" + LS)
                .append("   oneof messageType {" + LS);
        for (String messageType : returnMessageTypes) {
            if (ANY.equals(messageType)) {
                continue;
            }
            sb.append("      ")
                    .append(messageType)
                    .append(" ")
                    .append(namify(messageType)).append("_field")
                    .append(" = ")
                    .append(counter++)
                    .append(";" + LS);
        }
        sb.append("   }" + LS + "}" + LS);
    }

    private static void writeProtoFile(String[] args, StringBuilder sb) throws IOException {
        Path path = Files.createDirectories(Path.of(args[0], "src", "main", "proto"));
        if (path.resolve(args[3] + ".proto").toFile().exists()) {
            //            return;
        }
        counter = 0;
        wrapperBuilder.append("message ELEMENT_WRAPPER {" + LS)
                .append("   int64 position = " + ++counter + ";" + LS)
                .append("   oneof messageType {" + LS);
        for (String type : repeatedTypes) {
            String fieldName = type.contains(".") ? namify(type) : type;
            wrapperBuilder
                    .append("      ")
                    .append(type)
                    .append(" ")
                    .append(fieldName)
                    .append("_field = ")
                    .append(++counter)
                    .append(";" + LS);
        }
        wrapperBuilder.append("   }" + LS).append("}" + LS);
        Files.writeString(path.resolve(args[3] + ".proto"), sb.toString() + wrapperBuilder.toString(), StandardCharsets.UTF_8);
        Path path2 = Path.of("/tmp/CC1.proto");
        Files.writeString(path2, sb.toString(), StandardCharsets.UTF_8);
    }

    private static void createProtobufDirectory(String[] args) {
        String path = args[0] + "/target/generatedSources";
        for (String s : args[1].split("\\.")) {
            path += "/" + s;
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
    }

    /****************************************************************************/
    /*********************************
     * classes **********************************
     * /
     ****************************************************************************/

    /**
     * Visits each class in the transitive closure of all classes referenced in the
     * signatures of resource methods. Creates a service with an rpc declaration for
     * each resource method or locator.
     */
    static class JakartaRESTResourceVisitor extends VoidVisitorAdapter<StringBuilder> {

        public void visit(final ClassOrInterfaceDeclaration subClass, StringBuilder sb) {
            // Don't process gRPC server
            if (subClass.getFullyQualifiedName().orElse("").startsWith("grpc.server")) {
                return;
            }
            Optional<AnnotationExpr> opt = subClass.getAnnotationByName("Path");
            SingleMemberAnnotationExpr annotationExpr = opt.isPresent() ? (SingleMemberAnnotationExpr) opt.get() : null;
            String classPath = "";
            if (annotationExpr != null) {
                classPath = annotationExpr.getMemberValue().toString();
                classPath = classPath.substring(1, classPath.length() - 1);
            }
            for (BodyDeclaration<?> bd : subClass.getMembers()) {
                if (bd instanceof MethodDeclaration) {
                    MethodDeclaration md = (MethodDeclaration) bd;
                    if (!isResourceOrLocatorMethod(md)) {
                        continue;
                    }
                    String methodPath = "";
                    opt = md.getAnnotationByName("Path");
                    annotationExpr = opt.isPresent() ? (SingleMemberAnnotationExpr) opt.get() : null;
                    if (annotationExpr != null) {
                        methodPath = annotationExpr.getMemberValue().toString();
                        methodPath = methodPath.substring(1, methodPath.length() - 1);
                    }
                    String httpMethod = getHttpMethod(md);
                    // Add service with a method for each resource method in class.
                    if (!started) {
                        sb.append(LS + "service ")
                                .append(prefix)
                                .append("Service {" + LS);
                        started = true;
                    }
                    String entityType = getEntityParameter(md, httpMethod);
                    String returnType = getReturnType(md, httpMethod);
                    String syncType = isSuspended(md) ? "suspended"
                            : (isCompletionStage(md) ? "completionStage" : (isSSE(md) ? "sse" : "sync"));
                    isSuspended(md);
                    sb.append("// ");
                    if (!("".equals(classPath))) {
                        sb.append(classPath).append("/");
                    }
                    sb.append(methodPath).append(" ")
                            .append(entityType).append(" ")
                            .append(returnType).append(" ")
                            .append(httpMethod).append(" ")
                            .append(syncType).append("" + LS);
                    entityMessageTypes.add(entityType);
                    returnMessageTypes.add(returnType);
                    sb.append("  rpc ")
                            .append(md.getNameAsString())
                            .append(" (")
                            .append("GeneralEntityMessage")
                            .append(") returns (")
                            .append("sse".equals(syncType) ? "stream " : "")
                            .append("sse".equals(syncType) ? SSE_EVENT_CLASSNAME : "GeneralReturnMessage")
                            .append(");" + LS);

                    // Add each parameter and return type to resolvedTypes for further processing.
                    for (Parameter p : md.getParameters()) {
                        if (!isEntity(p)) {
                            continue;
                        }
                        if (p.getType().resolve().isPrimitive()) {
                            continue;
                        }
                        if (p.getType().isArrayType()) {
                            continue;
                        }
                        ReferenceTypeImpl rt = (ReferenceTypeImpl) p.getType().resolve();
                        ResolvedReferenceTypeDeclaration rrtd = rt.getTypeDeclaration().get();
                        String type = rt.asReferenceType().getQualifiedName();
                        if (!visited.contains(type)) {
                            resolvedTypes.add(rrtd);
                            if (rrtd.isInterface()) {
                                interfaces.add(rrtd.getQualifiedName());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Visit all classes discovered by JakartaRESTResourceVisitor in the process of visiting all Jakarta REST resources
     */
    static class ClassVisitor extends VoidVisitorAdapter<StringBuilder> {

        public void visit(ClassOrInterfaceDeclaration clazz, StringBuilder sb) {
            visit(clazz.resolve(), sb);
        }

        /**
         * For each class, create a message type with a field for each variable in the class.
         */
        public void visit(ResolvedReferenceTypeDeclaration clazz, StringBuilder sb) {
            resolvedTypes.remove(clazz);
            String fqn = clazz.getQualifiedName();
            if (visited.contains(fqn)) {
                return;
            }
            counter = 1;
            doVisit(clazz, sb, true);
            visited.add(clazz.getQualifiedName());
        }

        private void doVisit(ResolvedReferenceTypeDeclaration clazz, StringBuilder sb, boolean start) {
            Set<String> fieldNames = new HashSet<String>();
            if (clazz.isInterface()) {
                return;
            }
            if (PRIMITIVE_WRAPPER_TYPES_FIELD.containsKey(clazz.getClassName())) {
                return;
            }
            if (Response.class.getName().equals(clazz.getQualifiedName())) {
                return;
            }
            String fqn = clazz.getQualifiedName();
            if (start) {
                sb.append(LS + "message ").append(fqnifyClass(fqn, isInnerClass(clazz))).append(" {" + LS);
            }
            ResolvedReferenceTypeDeclaration superClass = getSuperClass(clazz);
            if (superClass != null) {
                doVisit(superClass, sb, false);
            }
            for (ResolvedReferenceTypeDeclaration r : clazz.internalTypes()) {
                if (!visited.contains(r.getQualifiedName())) {
                    resolvedTypes.add(r);
                }
            }
            // Begin protobuf message definition.
            // Scan all variables in class.
            for (ResolvedFieldDeclaration rfd : clazz.getDeclaredFields()) {
                String type = null;
                if ("$assertionsDisabled".equals(rfd.getName())) {
                    continue;
                }
                if (rfd.getName() != null && rfd.getName().startsWith("this$")) {
                    continue;
                }
                if (clazz.getName().contains("safe")) {
                    continue;
                }
                if (TYPE_MAP.containsKey(rfd.getType().describe())) {
                    type = TYPE_MAP.get(rfd.getType().describe());
                } else if (PRIMITIVE_WRAPPER_TYPES_FIELD.containsKey(rfd.getType().describe())) {
                    type = PRIMITIVE_WRAPPER_TYPES_FIELD.get(rfd.getType().describe());
                } else if (rfd.getType() instanceof ResolvedArrayType) {
                    ResolvedArrayType rat = (ResolvedArrayType) rfd.getType();
                    ResolvedType ct = rat.getComponentType();
                    if ("java.lang.Object".equals(ct.describe())) {
                        type = "repeated " + wrapRepeated("google.protobuf.Any");
                    } else if ("byte".equals(ct.describe())) {
                        type = "bytes";
                    } else if ("char".equals(ct.describe()) || "java.lang.Character".equals(ct.describe())) {
                        type = "string";
                    } else if (ct.isPrimitive()) {
                        type = "repeated " + wrapRepeated(ct.describe());
                    } else if (ct instanceof ResolvedArrayType) {
                        type = "dev.resteasy.grpc.arrays.dev_resteasy_grpc_arrays___ArrayHolder";
                        ResolvedType bat = getBasicArrayType((ResolvedArrayType) ct);
                        if (bat.isReference()) {
                            ResolvedReferenceTypeDeclaration rrtd = (ResolvedReferenceTypeDeclaration) bat.asReferenceType()
                                    .getTypeDeclaration().get();
                            fqn = rrtd.getPackageName() + "." + rrtd.getClassName();
                            if (!visited.contains(fqn)) {
                                resolvedTypes.add(rrtd.asReferenceType());
                                if (rrtd.isInterface()) {
                                    interfaces.add(rrtd.getQualifiedName());
                                }
                            }
                        }
                    } else {
                        fqn = removeTypeVariables(ct.describe());
                        if (!ct.isReferenceType()) {
                            continue;
                        }
                        if (PRIMITIVE_WRAPPER_TYPES_FIELD.containsKey(fqn)) {
                            type = "repeated " + wrapRepeated(fqn);
                        } else if (!visited.contains(fqn)) {
                            resolvedTypes.add(ct.asReferenceType().getTypeDeclaration().get());
                            if (ct.asReferenceType().getTypeDeclaration().get().isInterface()) {
                                interfaces.add(ct.asReferenceType().getTypeDeclaration().get().getQualifiedName());
                            }
                            type = "repeated " + wrapRepeated(fqnifyClass(fqn, isInnerClass(ct.asReferenceType()
                                    .getTypeDeclaration()
                                    .get())));
                        }

                    }
                } else { // Defined type
                    if (rfd.getType().isReferenceType()) {
                        ResolvedReferenceTypeDeclaration rrtd = (ResolvedReferenceTypeDeclaration) rfd.getType()
                                .asReferenceType().getTypeDeclaration().get();
                        String fqn2 = rrtd.getPackageName() + "." + rrtd.getClassName();
                        if (rrtd.isInterface() || isAbstract(rrtd)) {
                            interfaces.add(rrtd.getQualifiedName());
                            type = "google.protobuf.Any";
                        } else {
                            type = fqnifyClass(fqn2, isInnerClass(rrtd));
                            resolvedTypes.add(rrtd);
                        }
                    } else if (rfd.getType().isTypeVariable()) {
                        type = "google.protobuf.Any";
                    }
                }
                String fieldName = getFieldName(fieldNames, rfd.getName());
                if (type != null) {
                    sb.append("  ")
                            .append(type)
                            .append(" ")
                            .append(fieldName)
                            .append(" = ")
                            .append(counter++)
                            .append(";" + LS);
                }
            }
            if (start) {
                sb.append("}" + LS);
            }
        }
    }

    /****************************************************************************/
    /******************************
     * utility methods *****************************
     * /
     *****************************************************************************/
    private static String getEntityParameter(MethodDeclaration md, String httpMethod) {
        if (HttpServletRequestImpl.LOCATOR.equals(httpMethod)) {
            return "google.protobuf.Any";
        }
        for (Parameter p : md.getParameters()) {
            if (isEntity(p)) {
                String rawType = p.getTypeAsString();
                if (PRIMITIVE_WRAPPER_TYPES_IO.containsKey(rawType)) {
                    return PRIMITIVE_WRAPPER_TYPES_IO.get(rawType);
                }
                if (TYPE_MAP.containsKey(rawType)) {
                    return TYPE_MAP.get(rawType);
                }
                // array?
                ResolvedType rt = p.getType().resolve();
                if (isInterface(rt)) {
                    return "google.protobuf.Any";
                }
                if (rt.isArray()) {
                    return "dev.resteasy.grpc.arrays.dev_resteasy_grpc_arrays___ArrayHolder";
                }
                ResolvedReferenceTypeDeclaration r = rt.asReferenceType().getTypeDeclaration().orElse(null);
                if (r != null) {
                    resolvedTypes.add(r);
                }
                if (rt.asReferenceType().getTypeDeclaration().get().isInterface()) {
                    interfaces.add(rt.asReferenceType().getTypeDeclaration().get().getQualifiedName());
                }
                String type = rt.describe();
                return fqnifyClass(type, isInnerClass(rt.asReferenceType().getTypeDeclaration().get()));
            }
        }
        needEmpty = true;
        return "gEmpty";
    }

    private static boolean isEntity(Parameter p) {
        for (AnnotationExpr ae : p.getAnnotations()) {
            if (ANNOTATIONS.contains(ae.getNameAsString())) {
                return false;
            }
        }
        String name = p.getTypeAsString();
        if (AsyncResponse.class.getName().equals(name) || AsyncResponse.class.getSimpleName().equals(name)) {
            return false;
        }
        return true;
    }

    private static String getReturnType(MethodDeclaration md, String httpMethod) {
        if (isSuspended(md) || HttpServletRequestImpl.LOCATOR.equals(httpMethod)) {
            return "google.protobuf.Any";
        }
        if (isSSE(md)) {
            return SSE_EVENT_CLASSNAME;
        }
        for (Node node : md.getChildNodes()) {
            if (node instanceof Type) {
                if (node instanceof VoidType) {
                    return "google.protobuf.Any"; // ??
                }
                String rawType = ((Type) node).asString();
                int open = rawType.indexOf("<");
                int close = rawType.indexOf(">");
                if (open >= 0 && close > open) {
                    String type = rawType.substring(0, open);
                    String parameterType = rawType.substring(open + 1, close);
                    if (CompletionStage.class.getCanonicalName().contentEquals(type)
                            || CompletionStage.class.getSimpleName().contentEquals(type)) {
                        rawType = parameterType;
                    } else {
                        rawType = type;
                    }
                }
                if (PRIMITIVE_WRAPPER_TYPES_IO.containsKey(rawType)) {
                    return PRIMITIVE_WRAPPER_TYPES_IO.get(rawType);
                }
                if (TYPE_MAP.containsKey(rawType)) {
                    return TYPE_MAP.get(rawType);
                }
                if ("jakarta.ws.rs.core.Response".equals(rawType) || "Response".equals(rawType)) {
                    return "google.protobuf.Any";
                }
                // array?
                ResolvedType rt = ((Type) node).resolve();
                if (isInterface(rt)) {
                    return "google.protobuf.Any";
                }
                if (rt.isArray()) {
                    return "dev.resteasy.grpc.arrays.dev_resteasy_grpc_arrays___ArrayHolder";
                }
                resolvedTypes.add(rt.asReferenceType().getTypeDeclaration().get());
                if (rt.asReferenceType().getTypeDeclaration().get().isInterface()) {
                    interfaces.add(rt.asReferenceType().getTypeDeclaration().get().getQualifiedName());
                }
                String type = ((Type) node).resolve().describe();
                return fqnifyClass(type, isInnerClass(rt.asReferenceType().getTypeDeclaration().get()));
            }
        }
        needEmpty = true;
        return "gEmpty";
    }

    private static boolean isSuspended(MethodDeclaration md) {
        for (Parameter p : md.getParameters()) {
            for (AnnotationExpr ae : p.getAnnotations()) {
                if ("Suspended".equals(ae.getNameAsString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isCompletionStage(MethodDeclaration md) {
        for (Node node : md.getChildNodes()) {
            if (node instanceof Type) {
                String rawType = ((Type) node).asString();
                int open = rawType.indexOf("<");
                int close = rawType.indexOf(">");
                if (open >= 0 && close > open) {
                    String type = rawType.substring(0, open);
                    if (CompletionStage.class.getCanonicalName().contentEquals(type)
                            || CompletionStage.class.getSimpleName().contentEquals(type)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isSSE(MethodDeclaration md) {
        Optional<AnnotationExpr> opt = md.getAnnotationByName("Produces");
        if (opt.isEmpty()) {
            return false;
        }
        AnnotationExpr ae = opt.get();
        List<StringLiteralExpr> list1 = ae.findAll(StringLiteralExpr.class);
        for (Iterator<StringLiteralExpr> it = list1.iterator(); it.hasNext();) {
            StringLiteralExpr sle = it.next();
            if (MediaType.SERVER_SENT_EVENTS.equals(sle.getValue())) {
                isSSE = true;
                return true;
            }
        }
        List<FieldAccessExpr> list2 = ae.findAll(FieldAccessExpr.class);
        for (Iterator<FieldAccessExpr> it = list2.iterator(); it.hasNext();) {
            FieldAccessExpr fae = it.next();
            List<Node> list3 = fae.getChildNodes();
            if (list3.size() >= 2 && list3.get(0) instanceof NameExpr && list3.get(1) instanceof SimpleName) {
                NameExpr ne = (NameExpr) list3.get(0);
                SimpleName sn = (SimpleName) list3.get(1);
                if ("MediaType".equals(ne.getName().asString()) && "SERVER_SENT_EVENTS".equals(sn.asString())) {
                    isSSE = true;
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isResourceOrLocatorMethod(MethodDeclaration md) {
        for (AnnotationExpr ae : md.getAnnotations()) {
            if (HTTP_VERBS.contains(ae.getNameAsString().toUpperCase()) || "Path".equals(ae.getNameAsString())) {
                return true;
            }
        }
        return false;
    }

    private static String removeTypeVariables(String classType) {
        int left = classType.indexOf('<');
        if (left < 0) {
            return classType;
        }
        return classType.substring(0, left);
    }

    private static String isInnerClass(ResolvedReferenceTypeDeclaration clazz) {
        try {
            Optional<?> opt = clazz.containerType();
            if (opt.isEmpty()) {
                return "___";
            }
            ResolvedTypeDeclaration rtd = clazz.containerType().get();
            if (!rtd.isClass()) {
                return "___";
            }
            if (AccessSpecifier.PUBLIC.equals(clazz.asClass().accessSpecifier())) {
                return "_INNER_";
            }
            return "_HIDDEN_";
        } catch (Exception e) {
            return "___";
        }
    }

    private static String fqnifyClass(String s, String separator) {
        int l = s.lastIndexOf(".");
        String sPackage = s.substring(0, l).replace(".", "_");
        String className = s.substring(l + 1);
        return sPackage + separator + className;
    }

    private static String namify(String s) {
        return s.replace(".", "_");
    }

    private static String getHttpMethod(MethodDeclaration md) {
        if (!md.getAnnotationByName("DELETE").isEmpty()) {
            return "DELETE";
        }
        if (!md.getAnnotationByName("GET").isEmpty()) {
            return "GET";
        }
        if (!md.getAnnotationByName("HEAD").isEmpty()) {
            return "HEAD";
        }
        if (!md.getAnnotationByName("OPTIONS").isEmpty()) {
            return "OPTIONS";
        }
        if (!md.getAnnotationByName("PATCH").isEmpty()) {
            return "PATCH";
        }
        if (!md.getAnnotationByName("POST").isEmpty()) {
            return "POST";
        }
        if (!md.getAnnotationByName("PUT").isEmpty()) {
            return "PUT";
        }
        return HttpServletRequestImpl.LOCATOR;
    }

    private static boolean isInterface(ResolvedType rt) {
        if (rt instanceof ResolvedReferenceType) {
            Optional<ResolvedReferenceTypeDeclaration> opt = ((ResolvedReferenceType) rt).getTypeDeclaration();
            if (opt.isPresent()) {
                ResolvedReferenceTypeDeclaration rrtd = opt.get();
                if (rrtd.isInterface()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static ResolvedType getBasicArrayType(ResolvedArrayType rat) {
        if (rat.getComponentType().isArray()) {
            return getBasicArrayType((ResolvedArrayType) rat.getComponentType());
        } else {
            return rat.getComponentType();
        }
    }

    private static String getFieldName(Set<String> fieldNames, String proposedName) {
        String name = proposedName;
        int counter = 1;
        while (fieldNames.contains(name.toLowerCase())) {
            name = proposedName + "___" + counter++;
        }
        fieldNames.add(name.toLowerCase());
        return name;
    }

    private static ResolvedReferenceTypeDeclaration getSuperClass(ResolvedReferenceTypeDeclaration clazz) {
        for (ResolvedReferenceType rrt : clazz.getAncestors()) {
            ResolvedReferenceTypeDeclaration rrtd = rrt.getTypeDeclaration().orElse(null);
            if (rrtd != null) {
                if (rrtd.isClass()) {
                    return rrtd;
                }
            }
        }
        return null;
    }

    private static boolean isAbstract(ResolvedReferenceTypeDeclaration rrtd) {
        if (rrtd instanceof ReflectionClassDeclaration) {
            ReflectionClassDeclaration rcd = (ReflectionClassDeclaration) rrtd;
            try {
                Field field = rcd.getClass().getDeclaredField("clazz");
                field.setAccessible(true);
                Class<?> clazz = (Class<?>) field.get(rcd);
                return Modifier.isAbstract(clazz.getModifiers());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (rrtd instanceof JavaParserClassDeclaration) {
            JavaParserClassDeclaration jpcd = (JavaParserClassDeclaration) rrtd;
            String s = jpcd.getWrappedNode().toString();
            int n = s.indexOf(rrtd.getClassName());
            if (n < 0) {
                String classname = rrtd.getClassName();
                int i = classname.lastIndexOf(".");
                classname = classname.substring(i + 1);
                n = s.indexOf(classname);
            }
            return s.substring(0, n).contains("abstract");
        }
        return true;
    }

    private static String wrapRepeated(String type) {
        if (PRIMITIVE_WRAPPER_TYPES_IO.containsKey(type)) {
            type = PRIMITIVE_WRAPPER_TYPES_IO.get(type);
        }
        repeatedTypes.add(type);
        return "ELEMENT_WRAPPER";
    }
}
