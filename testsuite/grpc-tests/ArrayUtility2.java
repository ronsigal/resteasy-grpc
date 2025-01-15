package org.jboss.resteasy.test.grpc;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.protobuf.ByteString;

import dev.resteasy.grpc.arrays.Array_proto;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Any___wrapper;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Boolean___Array;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Boolean___WArray;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Boolean___wrapper;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Byte___Array;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Byte___WArray;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Byte___wrapper;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Character___wrapper;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Double___Array;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Double___wrapper;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Float___Array;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Float___wrapper;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Integer___Array;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Integer___WArray;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Integer___wrapper;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Long___Array;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Long___WArray;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Long___wrapper;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___NONE;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Short___Array;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Short___WArray;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___Short___wrapper;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___String___wrapper;
import dev.resteasy.grpc.bridge.runtime.protobuf.JavabufTranslator;
import dev.resteasy.grpc.example.CC1JavabufTranslator.dev_resteasy_grpc_arrays___Boolean___Array_ToJavabuf;
import dev.resteasy.grpc.example.CC1JavabufTranslator.dev_resteasy_grpc_arrays___Boolean___WArray_ToJavabuf;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_arrays___ArrayHolder;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_arrays___ArrayHolder___WArray;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_arrays___ArrayHolder___wrapper;

public class ArrayUtility2 {

    private static Map<String, String> PRIMITIVE_WRAPPER_TYPES = new HashMap<String, String>();
    private static Map<String, String> PRIMITIVE_TYPES = new HashMap<String, String>();
    private dev_resteasy_grpc_arrays___ArrayHolder.Builder ahb = dev_resteasy_grpc_arrays___ArrayHolder.newBuilder();

    private static dev_resteasy_grpc_arrays___NONE.Builder nBuilder = dev_resteasy_grpc_arrays___NONE.newBuilder();

    private static dev_resteasy_grpc_arrays___Boolean___wrapper NULL_BOOLEAN = dev_resteasy_grpc_arrays___Boolean___wrapper
            .newBuilder()
            .setNoneField(nBuilder).build();
    private static dev_resteasy_grpc_arrays___Byte___wrapper NULL_BYTE = dev_resteasy_grpc_arrays___Byte___wrapper.newBuilder()
            .setNoneField(nBuilder).build();
    private static dev_resteasy_grpc_arrays___Short___wrapper NULL_SHORT = dev_resteasy_grpc_arrays___Short___wrapper
            .newBuilder()
            .setNoneField(nBuilder).build();
    private static dev_resteasy_grpc_arrays___Integer___wrapper NULL_INTEGER = dev_resteasy_grpc_arrays___Integer___wrapper
            .newBuilder()
            .setNoneField(nBuilder).build();
    private static dev_resteasy_grpc_arrays___Long___wrapper NULL_LONG = dev_resteasy_grpc_arrays___Long___wrapper.newBuilder()
            .setNoneField(nBuilder).build();
    private static dev_resteasy_grpc_arrays___Float___wrapper NULL_FLOAT = dev_resteasy_grpc_arrays___Float___wrapper
            .newBuilder()
            .setNoneField(nBuilder).build();
    private static dev_resteasy_grpc_arrays___Double___wrapper NULL_DOUBLE = dev_resteasy_grpc_arrays___Double___wrapper
            .newBuilder()
            .setNoneField(nBuilder).build();
    private static dev_resteasy_grpc_arrays___Character___wrapper NULL_CHARACTER = dev_resteasy_grpc_arrays___Character___wrapper
            .newBuilder()
            .setNoneField(nBuilder).build();
    private static dev_resteasy_grpc_arrays___String___wrapper NULL_STRING = dev_resteasy_grpc_arrays___String___wrapper
            .newBuilder()
            .setNoneField(nBuilder).build();
    private static dev_resteasy_grpc_arrays___Any___wrapper NULL_ANY = dev_resteasy_grpc_arrays___Any___wrapper.newBuilder()
            .setNoneField(nBuilder)
            .build();
    private static dev_resteasy_grpc_arrays___ArrayHolder NULL_ARRAY_HOLDER = dev_resteasy_grpc_arrays___ArrayHolder
            .newBuilder().setDevResteasyGrpcArraysNONEField(nBuilder).build();

    private static dev_resteasy_grpc_arrays___Short___wrapper.Builder sBuilder = dev_resteasy_grpc_arrays___Short___wrapper
            .newBuilder();
    private static dev_resteasy_grpc_arrays___Short___Array.Builder saBuilder = dev_resteasy_grpc_arrays___Short___Array
            .newBuilder();

    private static dev_resteasy_grpc_arrays___Integer___wrapper.Builder iBuilder = dev_resteasy_grpc_arrays___Integer___wrapper
            .newBuilder();
    private static dev_resteasy_grpc_arrays___Integer___Array.Builder iaBuilder = dev_resteasy_grpc_arrays___Integer___Array
            .newBuilder();

    private static dev_resteasy_grpc_arrays___Long___wrapper.Builder lBuilder = dev_resteasy_grpc_arrays___Long___wrapper
            .newBuilder();
    private static dev_resteasy_grpc_arrays___Long___Array.Builder laBuilder = dev_resteasy_grpc_arrays___Long___Array
            .newBuilder();

    private static dev_resteasy_grpc_arrays___Float___wrapper.Builder fBuilder = dev_resteasy_grpc_arrays___Float___wrapper
            .newBuilder();
    private static dev_resteasy_grpc_arrays___Float___Array.Builder faBuilder = dev_resteasy_grpc_arrays___Float___Array
            .newBuilder();

    private static dev_resteasy_grpc_arrays___Double___wrapper.Builder dBuilder = dev_resteasy_grpc_arrays___Double___wrapper
            .newBuilder();
    private static dev_resteasy_grpc_arrays___Double___Array.Builder daBuilder = dev_resteasy_grpc_arrays___Double___Array
            .newBuilder();

    static {
        PRIMITIVE_WRAPPER_TYPES.put("boolean", "java.lang.Boolean");
        PRIMITIVE_WRAPPER_TYPES.put("byte", "java.lang.Byte");
        PRIMITIVE_WRAPPER_TYPES.put("short", "java.lang.Short");
        PRIMITIVE_WRAPPER_TYPES.put("int", "java.lang.Integer");
        PRIMITIVE_WRAPPER_TYPES.put("long", "java.lang.Long");
        PRIMITIVE_WRAPPER_TYPES.put("float", "java.lang.Float");
        PRIMITIVE_WRAPPER_TYPES.put("double", "java.lang.Double");
        PRIMITIVE_WRAPPER_TYPES.put("char", "java.lang.Character");

        PRIMITIVE_TYPES.put("Z", "boolean");
        PRIMITIVE_TYPES.put("B", "byte");
        PRIMITIVE_TYPES.put("S", "short");
        PRIMITIVE_TYPES.put("I", "int");
        PRIMITIVE_TYPES.put("J", "long");
        PRIMITIVE_TYPES.put("F", "float");
        PRIMITIVE_TYPES.put("D", "double");
        PRIMITIVE_TYPES.put("C", "char");
    }

    public static Object getArray(dev_resteasy_grpc_arrays___ArrayHolder ah) throws Exception {
        return getArray(null, ah);
    }

    public static Object getArray(JavabufTranslator translator, dev_resteasy_grpc_arrays___ArrayHolder ah) throws Exception {

        switch (ah.getComponentClass()) {

            case "boolean": {
                dev_resteasy_grpc_arrays___Boolean___Array ba = ah.getDevResteasyGrpcArraysBooleanArrayField();
                boolean[] bs = new boolean[ba.getBooleanFieldCount()];
                for (int i = 0; i < bs.length; i++) {
                    bs[i] = ba.getBooleanField(i);
                }
                return bs;
            }

            case "java.lang.Boolean": {
                List<dev_resteasy_grpc_arrays___Boolean___wrapper> list = ah.getDevResteasyGrpcArraysBooleanWArrayField()
                        .getBooleanFieldList();
                Boolean[] Bs = new Boolean[list.size()];
                for (int i = 0; i < Bs.length; i++) {
                    if (!list.get(i).hasNoneField()) {
                        Bs[i] = list.get(i).getBooleanField();
                    }
                }
                return Bs;
            }

            case "byte": {
                return byteStringToByteArray(ah.getDevResteasyGrpcArraysByteArrayField().getByteField());
            }

            case "java.lang.Byte": {
                List<Array_proto.dev_resteasy_grpc_arrays___Byte___wrapper> list = ah.getDevResteasyGrpcArraysByteWArrayField()
                        .getByteFieldList();
                Byte[] Bs = new Byte[list.size()];
                for (int i = 0; i < Bs.length; i++) {
                    if (list.get(i).hasByteField()) {
                        Bs[i] = list.get(i).getByteField().byteAt(0);
                    }
                }
                return Bs;
            }

            case "short": {
                List<Integer> list = ah.getDevResteasyGrpcArraysShortArrayField().getShortFieldList();
                short[] ss = new short[list.size()];
                for (int i = 0; i < ss.length; i++) {
                    ss[i] = list.get(i).shortValue();
                }
                return ss;
            }

            case "java.lang.Short": {
                List<Array_proto.dev_resteasy_grpc_arrays___Short___wrapper> list = ah
                        .getDevResteasyGrpcArraysShortWArrayField().getShortFieldList();
                Short[] ss = new Short[list.size()];
                for (int i = 0; i < ss.length; i++) {
                    if (list.get(i).hasShortField()) {
                        ss[i] = (short) list.get(i).getShortField();
                    }
                }
                return ss;
            }

            case "int": {
                List<Integer> list = ah.getDevResteasyGrpcArraysIntegerArrayField().getIntFieldList();
                int[] is = new int[list.size()];
                for (int i = 0; i < is.length; i++) {
                    is[i] = list.get(i);
                }
                return is;
            }

            case "java.lang.Integer": {
                List<dev_resteasy_grpc_arrays___Integer___wrapper> list = ah.getDevResteasyGrpcArraysIntegerWArrayField()
                        .getIntegerFieldList();
                Integer[] is = new Integer[list.size()];
                for (int i = 0; i < is.length; i++) {
                    if (list.get(i).hasIntegerField()) {
                        is[i] = list.get(i).getIntegerField();
                    }
                }
                return is;
            }

            case "long": {
                List<Long> list = ah.getDevResteasyGrpcArraysLongArrayField().getLongFieldList();
                long[] ls = new long[list.size()];
                for (int i = 0; i < ls.length; i++) {
                    ls[i] = list.get(i);
                }
                return ls;
            }

            case "java.lang.Long": {
                List<dev_resteasy_grpc_arrays___Long___wrapper> list = ah.getDevResteasyGrpcArraysLongWArrayField()
                        .getLongFieldList();
                Long[] ls = new Long[list.size()];
                for (int i = 0; i < ls.length; i++) {
                    if (list.get(i).hasLongField()) {
                        ls[i] = list.get(i).getLongField();
                    }
                }
                return ls;
            }

            //            case "float": {
            //                List<Float> list = ah.getFloatArrayField().getFloatFieldList();
            //                float[] fs = new float[list.size()];
            //                for (int i = 0; i < fs.length; i++) {
            //                    fs[i] = list.get(i);
            //                }
            //                return fs;
            //            }
            //
            //            case "java.lang.Float": {
            //                List<Array_proto.dev_resteasy_grpc_arrays___Float> list = ah.getFloatWArrayField().getFloatFieldList();
            //                Float[] fs = new Float[list.size()];
            //                for (int i = 0; i < fs.length; i++) {
            //                    if (list.get(i).hasFloatField()) {
            //                        fs[i] = list.get(i).getFloatField();
            //                    }
            //                }
            //                return fs;
            //            }
            //
            //            case "double": {
            //                List<Double> list = ah.getDoubleArrayField().getDoubleFieldList();
            //                double[] ds = new double[list.size()];
            //                for (int i = 0; i < ds.length; i++) {
            //                    ds[i] = list.get(i);
            //                }
            //                return ds;
            //            }
            //
            //            case "java.lang.Double": {
            //                List<Array_proto.dev_resteasy_grpc_arrays___Double> list = ah.getDoubleWArrayField().getDoubleFieldList();
            //                Double[] ds = new Double[list.size()];
            //                for (int i = 0; i < ds.length; i++) {
            //                    if (list.get(i).hasDoubleField()) {
            //                        ds[i] = list.get(i).getDoubleField();
            //                    }
            //                }
            //                return ds;
            //            }
            //
            //            case "char": {
            //                List<String> list = ah.getCharArrayField().getCharFieldList();
            //                char[] cs = new char[list.size()];
            //                for (int i = 0; i < cs.length; i++) {
            //                    cs[i] = list.get(i).charAt(0);
            //                }
            //                return cs;
            //            }
            //
            //            case "java.lang.Character": {
            //                List<Array_proto.dev_resteasy_grpc_arrays___Character> list = ah.getCharacterWArrayField()
            //                        .getCharacterFieldList();
            //                Character[] cs = new Character[list.size()];
            //                for (int i = 0; i < cs.length; i++) {
            //                    if (list.get(i).hasCharField()) {
            //                        cs[i] = list.get(i).getCharField().charAt(0);
            //                    }
            //                }
            //                return cs;
            //            }
            //
            //            case "java.lang.String": {
            //                List<Array_proto.dev_resteasy_grpc_arrays___String> list = ah.getStringWArrayField().getStringFieldList();
            //                ;
            //                String[] ss = new String[list.size()];
            //                for (int i = 0; i < ss.length; i++) {
            //                    if (list.get(i).hasStringField()) {
            //                        ss[i] = list.get(i).getStringField();
            //                    }
            //                }
            //                return ss;
            //            }
            //
            //            case "com.google.protobuf.Any": {
            //                List<dev_resteasy_grpc_arrays___Any> list = ah.getAnyArrayField().getAnyFieldList();
            //                Any[] as = new Any[list.size()];
            //                for (int i = 0; i < as.length; i++) {
            //                    if (list.get(i).hasAnyField()) {
            //                        as[i] = (Any) list.get(i).getAnyField();
            //                    }
            //                }
            //                return as;
            //            }

            //            case "java.lang.Object": {
            //                dev_resteasy_grpc_arrays___anyArray aa = ah.getAnyArrayField();
            //                Object[] os = new Object[aa.getAnyFieldCount()];
            //                if (os.length == 0) {
            //                    return os;
            //                }
            //                @SuppressWarnings("rawtypes")
            //                Class clazz = Utility.extractClassFromAny(aa.getAnyField(0), translator);
            //                for (int i = 0; i < os.length; i++) {
            //                    @SuppressWarnings("unchecked")
            //                    Message m = aa.getAnyField(i).unpack(clazz);
            //                    os[i] = translator.translateFromJavabuf(m);
            //                }
            //                return os;
            //            }
            //
            default: {
                if (isArray(ah.getComponentClass())) {
                    if (ah.getDevResteasyGrpcArraysArrayHolderWArrayField()
                            .getDevResteasyGrpcArraysArrayHolderFieldCount() == 0) {
                        return getNestedEmptyArray(ah.getComponentClass());
                    }
                    Object o = getArray(translator, ah.getDevResteasyGrpcArraysArrayHolderWArrayField()
                            .getDevResteasyGrpcArraysArrayHolderField(0).getDevResteasyGrpcArraysArrayHolderField());
                    Object[] os = (Object[]) Array.newInstance(o.getClass(),
                            ah.getDevResteasyGrpcArraysArrayHolderWArrayField()
                                    .getDevResteasyGrpcArraysArrayHolderFieldCount());
                    os[0] = o;
                    for (int i = 1; i < ah.getDevResteasyGrpcArraysArrayHolderWArrayField()
                            .getDevResteasyGrpcArraysArrayHolderFieldCount(); i++) {
                        os[i] = getArray(translator, ah.getDevResteasyGrpcArraysArrayHolderWArrayField()
                                .getDevResteasyGrpcArraysArrayHolderField(i).getDevResteasyGrpcArraysArrayHolderField());
                    }
                    return os;
                }
                //               dev_resteasy_grpc_arrays___anyArray aa = ah.getAnyArrayField();
                //               String className = ah.getComponentClass();
                //               if ("".equals(className) || className == null) {
                //                  return null;
                //               }
                //               if ("java.lang.Object".equals(className)) {
                //                  Object[] os = new Object[aa.getAnyFieldCount()];
                //                  if (os.length == 0) {
                //                     return os;
                //                  }
                //                  @SuppressWarnings("rawtypes")
                //                  Class clazz = Utility.extractClassFromAny(aa.getAnyField(0), translator);
                //                  for (int i = 0; i < os.length; i++) {
                //                     @SuppressWarnings("unchecked")
                //                     Message m = aa.getAnyField(i).unpack(clazz);
                //                     os[i] = translator.translateFromJavabuf(m);
                //                  }
                //                  return os;
                //               }
                //               Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
                //               Object array = createArray(className, aa.getAnyFieldCount());
                //               List<Any> list = aa.getAnyFieldList();
                //               for (int i = 0; i < list.size(); i++) {
                //                  @SuppressWarnings("unchecked")
                //                  Message element = aa.getAnyField(i).unpack(translator.translateToJavabufClass(clazz));
                //                  Array.set(array, i, translator.translateFromJavabuf(element));
                //               }
                //               return array;
                return null;
            }
        }
    }

    public static dev_resteasy_grpc_arrays___ArrayHolder getHolder(Object o) {
        return getHolder(null, o);
    }

    public static dev_resteasy_grpc_arrays___ArrayHolder getHolder(JavabufTranslator translator, Object o) {
        if (!o.getClass().isArray()) {
            throw new RuntimeException("Expecting array");
        }
        dev_resteasy_grpc_arrays___ArrayHolder.Builder ahBuilder = dev_resteasy_grpc_arrays___ArrayHolder.newBuilder();
        String componentClassName = o.getClass().getComponentType().getName();

        switch (componentClassName) {

            case "boolean": {
                dev_resteasy_grpc_arrays___Boolean___Array_ToJavabuf t = new dev_resteasy_grpc_arrays___Boolean___Array_ToJavabuf();
                dev_resteasy_grpc_arrays___Boolean___Array array = (dev_resteasy_grpc_arrays___Boolean___Array) t
                        .assignToJavabuf(o);
                return ahBuilder.setComponentClass("boolean").setDevResteasyGrpcArraysBooleanArrayField(array).build();
            }

            case "java.lang.Boolean": {
                dev_resteasy_grpc_arrays___Boolean___WArray_ToJavabuf t = new dev_resteasy_grpc_arrays___Boolean___WArray_ToJavabuf();
                dev_resteasy_grpc_arrays___Boolean___WArray array = (dev_resteasy_grpc_arrays___Boolean___WArray) t
                        .assignToJavabuf(o);
                return ahBuilder.setComponentClass("java.lang.Boolean").setDevResteasyGrpcArraysBooleanWArrayField(array)
                        .build();
            }

            case "byte": {
                byte[] bs = (byte[]) o;
                dev_resteasy_grpc_arrays___Byte___Array.Builder builder = dev_resteasy_grpc_arrays___Byte___Array.newBuilder();
                builder.setByteField(ByteString.copyFrom(bs));
                return ahBuilder.setDevResteasyGrpcArraysByteArrayField(builder).setComponentClass("byte").build();
            }

            case "java.lang.Byte": {
                Byte[] Bs = (Byte[]) o;
                dev_resteasy_grpc_arrays___Byte___wrapper.Builder baBuilder = dev_resteasy_grpc_arrays___Byte___wrapper
                        .newBuilder();
                dev_resteasy_grpc_arrays___Byte___WArray.Builder bwaBuilder = dev_resteasy_grpc_arrays___Byte___WArray
                        .newBuilder();
                for (int i = 0; i < Bs.length; i++) {
                    if (Bs[i] == null) {
                        bwaBuilder.addByteField(NULL_BYTE);
                    } else {
                        byte[] bs = new byte[] { Bs[i].byteValue() };
                        bwaBuilder.addByteField(baBuilder.clear().setByteField(ByteString.copyFrom(bs)));
                    }
                }
                return ahBuilder.setDevResteasyGrpcArraysByteWArrayField(bwaBuilder).setComponentClass("java.lang.Byte")
                        .build();
            }

            case "short": {
                short[] ss = (short[]) o;
                dev_resteasy_grpc_arrays___Short___Array.Builder sBuilder = dev_resteasy_grpc_arrays___Short___Array
                        .newBuilder();
                for (int i = 0; i < ss.length; i++) {
                    sBuilder.addShortField(ss[i]);
                }
                return ahBuilder.setDevResteasyGrpcArraysShortArrayField(sBuilder).setComponentClass("short").build();
            }

            case "java.lang.Short": {
                Short[] Ss = (Short[]) o;
                dev_resteasy_grpc_arrays___Short___wrapper.Builder sBuilder = dev_resteasy_grpc_arrays___Short___wrapper
                        .newBuilder();
                dev_resteasy_grpc_arrays___Short___WArray.Builder swaBuilder = dev_resteasy_grpc_arrays___Short___WArray
                        .newBuilder();
                for (int i = 0; i < Ss.length; i++) {
                    if (Ss[i] == null) {
                        swaBuilder.addShortField(NULL_SHORT).build();
                    } else {
                        swaBuilder.addShortField(sBuilder.setShortField(Ss[i]));
                    }
                }
                return ahBuilder.setDevResteasyGrpcArraysShortWArrayField(swaBuilder).setComponentClass("java.lang.Short")
                        .build();
            }

            case "int": {
                int[] is = (int[]) o;
                dev_resteasy_grpc_arrays___Integer___Array.Builder iBuilder = dev_resteasy_grpc_arrays___Integer___Array
                        .newBuilder();
                for (int i = 0; i < is.length; i++) {
                    iBuilder.addIntField(is[i]);
                }
                return ahBuilder.setDevResteasyGrpcArraysIntegerArrayField(iBuilder).setComponentClass("int").build();
            }

            case "java.lang.Integer": {
                Integer[] is = (Integer[]) o;
                dev_resteasy_grpc_arrays___Integer___wrapper.Builder iBuilder = dev_resteasy_grpc_arrays___Integer___wrapper
                        .newBuilder();
                dev_resteasy_grpc_arrays___Integer___WArray.Builder iwaBuilder = dev_resteasy_grpc_arrays___Integer___WArray
                        .newBuilder();
                for (int i = 0; i < is.length; i++) {
                    if (is[i] == null) {
                        iwaBuilder.addIntegerField(NULL_INTEGER).build();
                    } else {
                        iwaBuilder.addIntegerField(iBuilder.setIntegerField(is[i]));
                    }
                }
                return ahBuilder.setDevResteasyGrpcArraysIntegerWArrayField(iwaBuilder).setComponentClass("java.lang.Integer")
                        .build();
            }

            case "long": {
                long[] ls = (long[]) o;
                dev_resteasy_grpc_arrays___Long___Array.Builder lBuilder = dev_resteasy_grpc_arrays___Long___Array.newBuilder();
                for (int i = 0; i < ls.length; i++) {
                    lBuilder.addLongField(ls[i]);
                }
                return ahBuilder.setDevResteasyGrpcArraysLongArrayField(lBuilder).setComponentClass("long").build();
            }

            case "java.lang.Long": {
                Long[] ls = (Long[]) o;
                dev_resteasy_grpc_arrays___Long___wrapper.Builder lBuilder = dev_resteasy_grpc_arrays___Long___wrapper
                        .newBuilder();
                dev_resteasy_grpc_arrays___Long___WArray.Builder lwaBuilder = dev_resteasy_grpc_arrays___Long___WArray
                        .newBuilder();
                for (int i = 0; i < ls.length; i++) {
                    if (ls[i] == null) {
                        lwaBuilder.addLongField(NULL_LONG);
                    } else {
                        lwaBuilder.addLongField(lBuilder.setLongField(ls[i]));
                    }
                }
                return ahBuilder.setDevResteasyGrpcArraysLongWArrayField(lwaBuilder).setComponentClass("java.lang.Long")
                        .build();
            }

            //            case "float": {
            //                float[] fs = (float[]) o;
            //                dev_resteasy_grpc_arrays___FloatArray.Builder builder = dev_resteasy_grpc_arrays___FloatArray.newBuilder();
            //                for (int i = 0; i < fs.length; i++) {
            //                    builder.addFloatField(fs[i]);
            //                }
            //                return ahBuilder.setFloatArrayField(builder).setComponentClass("float").build();
            //            }
            //
            //            case "java.lang.Float": {
            //                Float[] fs = (Float[]) o;
            //                dev_resteasy_grpc_arrays___FloatArray.Builder builder = dev_resteasy_grpc_arrays___FloatArray.newBuilder();
            //                for (int i = 0; i < fs.length; i++) {
            //                    builder.addFloatField(fs[i]);
            //                }
            //                return ahBuilder.setFloatArrayField(builder).setComponentClass("java.lang.Float").build();
            //            }
            //
            //            case "double": {
            //                double[] ds = (double[]) o;
            //                dev_resteasy_grpc_arrays___DoubleArray.Builder builder = dev_resteasy_grpc_arrays___DoubleArray.newBuilder();
            //                for (int i = 0; i < ds.length; i++) {
            //                    builder.addDoubleField(ds[i]);
            //                }
            //                return ahBuilder.setDoubleArrayField(builder).setComponentClass("double").build();
            //            }
            //
            //            case "java.lang.Double": {
            //                Double[] fs = (Double[]) o;
            //                dev_resteasy_grpc_arrays___DoubleArray.Builder builder = dev_resteasy_grpc_arrays___DoubleArray.newBuilder();
            //                for (int i = 0; i < fs.length; i++) {
            //                    builder.addDoubleField(fs[i]);
            //                }
            //                return ahBuilder.setDoubleArrayField(builder).setComponentClass("java.lang.Double").build();
            //            }
            //
            //            case "char": {
            //                char[] cs = (char[]) o;
            //                dev_resteasy_grpc_arrays___CharArray.Builder builder = dev_resteasy_grpc_arrays___CharArray.newBuilder();
            //                for (int i = 0; i < cs.length; i++) {
            //                    builder.addCharField(String.valueOf(cs[i]));
            //                }
            //                return ahBuilder.setCharArrayField(builder).setComponentClass("char").build();
            //            }
            //
            //            case "java.lang.Character": {
            //                Character[] cs = (Character[]) o;
            //                dev_resteasy_grpc_arrays___CharArray.Builder builder = dev_resteasy_grpc_arrays___CharArray.newBuilder();
            //                for (int i = 0; i < cs.length; i++) {
            //                    builder.addCharField(String.valueOf(cs[i]));
            //                }
            //                return ahBuilder.setCharArrayField(builder).setComponentClass("java.lang.Character").build();
            //            }
            //
            //            case "java.lang.String": {
            //                String[] ss = (String[]) o;
            //                dev_resteasy_grpc_arrays___StringArray.Builder builder = dev_resteasy_grpc_arrays___StringArray.newBuilder();
            //                for (int i = 0; i < ss.length; i++) {
            //                    builder.addStringField(ss[i]);
            //                }
            //                return ahBuilder.setStringArrayField(builder).setComponentClass("java.lang.String").build();
            //            }
            //
            //            case "com.google.protobuf.Any": {
            //                Any[] as = (Any[]) o;
            //                dev_resteasy_grpc_arrays___anyArray.Builder builder = dev_resteasy_grpc_arrays___anyArray.newBuilder();
            //                for (int i = 0; i < as.length; i++) {
            //                    builder.addAnyField(as[i]);
            //                }
            //                return ahBuilder.setAnyArrayField(builder).setComponentClass("com.google.protobuf.Any").build();
            //            }
            //
            default: {
                Object[] objects = (Object[]) o;
                if (objects.getClass().getComponentType().isArray()) {
                    dev_resteasy_grpc_arrays___ArrayHolder___WArray.Builder ahaBuilder = dev_resteasy_grpc_arrays___ArrayHolder___WArray
                            .newBuilder();
                    dev_resteasy_grpc_arrays___ArrayHolder___wrapper.Builder ahwBuilder = dev_resteasy_grpc_arrays___ArrayHolder___wrapper
                            .newBuilder();
                    for (int i = 0; i < objects.length; i++) {
                        if (objects[i] == null) {
                            ahwBuilder.clear().setDevResteasyGrpcArraysArrayHolderField(NULL_ARRAY_HOLDER);
                        } else {
                            ahwBuilder.clear()
                                    .setDevResteasyGrpcArraysArrayHolderField(ArrayUtility2.getHolder(translator, objects[i]));
                        }
                        ahaBuilder.addDevResteasyGrpcArraysArrayHolderField(ahwBuilder);
                        //                     ahaBuilder.setDevResteasyGrpcArraysArrayHolderWrapperField(i, ahwBuilder);
                    }
                    ahBuilder.setDevResteasyGrpcArraysArrayHolderWArrayField(ahaBuilder)
                            .setComponentClass(objects.getClass().getComponentType().getName());
                    return ahBuilder.build();
                }
                return null; // build Any here
            }
        }
    }

    public static void assignArray(Object to, Object from) {
        if (Array.getLength(from) != Array.getLength(to)) {
            throw new RuntimeException("array lengths differ");
        }
        if (to.getClass().equals(from.getClass()) || componentTypeWraps(to, from)) {
            for (int i = 0; i < Array.getLength(from); i++) {
                Array.set(to, i, Array.get(from, i));
            }
        } else {
            for (int i = 0; i < Array.getLength(to); i++) {
                assignArray(Array.get(to, i), Array.get(from, i));
            }
        }
    }

    public static String getComponentClass(dev_resteasy_grpc_arrays___ArrayHolder holder) {
        if (holder.hasDevResteasyGrpcArraysArrayHolderWArrayField()) {
            dev_resteasy_grpc_arrays___ArrayHolder___WArray holderArray = holder
                    .getDevResteasyGrpcArraysArrayHolderWArrayField();
            if (holderArray.getDevResteasyGrpcArraysArrayHolderFieldCount() == 0) {
                return "";
            }
            return getComponentClass(
                    holderArray.getDevResteasyGrpcArraysArrayHolderField(0).getDevResteasyGrpcArraysArrayHolderField());
        }
        return holder.getComponentClass();
    }

    public static Class<?> getComponentClass(Class<?> clazz) {
        if (clazz.getComponentType().isArray()) {
            return getComponentClass(clazz.getComponentType());
        }
        return clazz.getComponentType();
    }

    private static boolean componentTypeWraps(Object from, Object to) {
        String fromComponentType = from.getClass().getComponentType().toString();
        String toComponentType = to.getClass().getComponentType().toString();
        if (fromComponentType == null || toComponentType == null) {
            return false;
        }
        return PRIMITIVE_WRAPPER_TYPES.get(fromComponentType).equals(toComponentType) ||
                PRIMITIVE_WRAPPER_TYPES.get(toComponentType).equals(fromComponentType);
    }

    public static void assignArray(Field field, Object target, Object obj)
            throws IllegalArgumentException, IllegalAccessException {
        if (field.getType().equals(obj.getClass())) {
            field.set(target, obj);
        } else {
            for (int i = 0; i < Array.getLength(obj); i++) {
                Array.set(target, i, obj);
            }
        }
    }

    private static Object createArray(String componentType, int length) throws Exception {
        if (PRIMITIVE_WRAPPER_TYPES.containsKey(componentType)) {
            switch (componentType) {
                case "boolean":
                    return new boolean[length];

                case "byte":
                    return new byte[length];

                case "short":
                    return new short[length];

                case "int":
                    return new int[length];

                case "long":
                    return new long[length];

                case "float":
                    return new float[length];

                case "double":
                    return new double[length];

                case "char":
                    return new char[length];
            }
        }
        return Array.newInstance(Class.forName(trimClassName(componentType)), length);
    }

    private static boolean isArray(String className) {
        return className.startsWith("[");
    }

    private static Object getNestedEmptyArray(String componentClassName) throws Exception {
        if (isArray(componentClassName)) {
            Object o = getNestedEmptyArray(componentClassName.substring(1));
            return Array.newInstance(o.getClass(), 0);
        }
        if (PRIMITIVE_TYPES.containsKey(componentClassName)) {
            return createArray(PRIMITIVE_TYPES.get(componentClassName), 0);
        }
        return createArray(componentClassName, 0);
    }

    private static String trimClassName(String className) {
        if (className.startsWith("L")) {
            return className.substring(1, className.length() - 1);
        }
        return className;
    }

    private static byte[] byteStringToByteArray(ByteString bs) {
        byte[] bytes = new byte[bs.size()];
        for (int i = 0; i < bs.size(); i++) {
            bytes[i] = bs.byteAt(i);
        }
        return bytes;
    }
}