package dev.resteasy.grpc.example;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.Internal.IntList;
import com.google.protobuf.Message;
import dev.resteasy.grpc.arrays.ArrayUtility;
import dev.resteasy.grpc.bridge.runtime.Utility;
import dev.resteasy.grpc.bridge.runtime.protobuf.AssignFromJavabuf;
import dev.resteasy.grpc.bridge.runtime.protobuf.AssignToJavabuf;
import dev.resteasy.grpc.bridge.runtime.protobuf.JavabufTranslator;
import dev.resteasy.grpc.bridge.runtime.protobuf.ReturnJavaClass;
import dev.resteasy.grpc.bridge.runtime.protobuf.TranslateFromJavabuf;
import dev.resteasy.grpc.bridge.runtime.protobuf.TranslateToJavabuf;
import dev.resteasy.grpc.arrays.Array_proto;
//XXXXXXXX HERE XXXXXXXXX
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_arrays___ArrayHolder;
import dev.resteasy.grpc.example.CC1_proto.gShort;
import dev.resteasy.grpc.example.CC1_proto.gDouble;
import dev.resteasy.grpc.example.CC1_proto.gBoolean;
import dev.resteasy.grpc.example.CC1_proto.gString;
import dev.resteasy.grpc.example.CC1_proto.gLong;
import dev.resteasy.grpc.example.CC1_proto.gByte;
import dev.resteasy.grpc.example.CC1_proto.gCharacter;
import dev.resteasy.grpc.example.CC1_proto.gFloat;
import dev.resteasy.grpc.example.CC1_proto.gInteger;
import dev.resteasy.grpc.example.CC1_proto.java_util_LinkedHashMap_HIDDEN_Entry;
import java.util.AbstractMap.SimpleEntry;
import dev.resteasy.grpc.example.CC1_proto.java_util_AbstractMap_INNER_SimpleEntry;
import dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_TreeNode;
import dev.resteasy.grpc.example.CC1_proto.java_util_ArrayList_HIDDEN_ListItr;
import java.lang.Object;
import dev.resteasy.grpc.example.CC1_proto.java_lang___Object;
import dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_KeyIterator;
import dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_Values;
import dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_UnsafeHolder;
import dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_EntrySet;
import dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_ValueSpliterator;
import dev.resteasy.grpc.example.CC6;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC6;
import java.util.HashSet;
import dev.resteasy.grpc.example.CC1_proto.java_util___HashSet;
import dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_ValueIterator;
import java.util.AbstractMap.SimpleImmutableEntry;
import dev.resteasy.grpc.example.CC1_proto.java_util_AbstractMap_INNER_SimpleImmutableEntry;
import dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_KeySet;
import dev.resteasy.grpc.example.CC1_proto.java_util_ArrayList_HIDDEN_ArrayListSpliterator;
import dev.resteasy.grpc.example.CC1_proto.java_util_ArrayList_HIDDEN_SubList;
import dev.resteasy.grpc.example.CC1_proto.java_util_AbstractList_HIDDEN_ListItr;
import dev.resteasy.grpc.example.CC1_proto.java_util_ArrayList_HIDDEN_Itr;
import dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_EntryIterator;
import dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_EntrySpliterator;
import dev.resteasy.grpc.example.CC3.CC3_Sub;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example_CC3_INNER_CC3_Sub;
import dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_HashIterator;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example_ArrayStuff_HIDDEN_Stuff;
import dev.resteasy.grpc.example.CC1_proto.java_util_AbstractList_HIDDEN_RandomAccessSpliterator;
import dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_KeySpliterator;
import dev.resteasy.grpc.example.CC1_proto.java_util_AbstractList_HIDDEN_RandomAccessSubList;
import dev.resteasy.grpc.example.CC4;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC4;
import dev.resteasy.grpc.example.CC9;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC9;
import dev.resteasy.grpc.example.CC1_proto.java_util_AbstractList_HIDDEN_SubList;
import dev.resteasy.grpc.example.CC7;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC7;
import dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_HashMapSpliterator;
import dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_Node;
import dev.resteasy.grpc.example.CC1_proto.java_util_AbstractList_HIDDEN_Itr;
import java.util.HashMap;
import dev.resteasy.grpc.example.CC1_proto.java_util___HashMap;
import java.util.ArrayList;
import dev.resteasy.grpc.example.CC1_proto.java_util___ArrayList;
import dev.resteasy.grpc.example.ArrayStuff;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___ArrayStuff;
import dev.resteasy.grpc.example.IntfImpl;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___IntfImpl;
import dev.resteasy.grpc.example.CC1.InnerClass;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example_CC1_INNER_InnerClass;
import dev.resteasy.grpc.example.CC2;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC2;
import dev.resteasy.grpc.bridge.runtime.sse.SseEvent;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent;
import dev.resteasy.grpc.example.CC5;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC5;
import dev.resteasy.grpc.example.CC3;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC3;

public class CC1JavabufTranslator implements JavabufTranslator {
   private static JavabufTranslator INSTANCE = new CC1JavabufTranslator();
   private static Map<String, TranslateToJavabuf> toJavabufMap = new HashMap<String, TranslateToJavabuf>();
   private static Map<String, TranslateFromJavabuf> fromJavabufMap = new HashMap<String, TranslateFromJavabuf>();
   private static JavabufTranslator translator = new dev.resteasy.grpc.example.CC1JavabufTranslator();
   private static dev_resteasy_grpc_arrays___ArrayHolder_ToJavabuf arrayHolderToJavabuf = new dev_resteasy_grpc_arrays___ArrayHolder_ToJavabuf();
   private static Set<Class<?>> WRAPPER_TYPES = new HashSet<Class<?>>();
   private static Set<String> ARRAY_WRAPPER_TYPES = new HashSet<String>();

   @SuppressWarnings("rawtypes")
   private static Map<String, Class> toJavabufClassMap = new HashMap<String, Class>();
   private static Map<String, Class<?>> fromJavabufClassMap = new HashMap<String, Class<?>>();
   private static Set<String> hiddenClasses = new HashSet<String>();
       private static Map<String, Constructor<?>> constructors = new HashMap<String, Constructor<?>>();
   static {

      toJavabufMap.put("java.lang.Short" , new gShort_ToJavabuf());
      fromJavabufMap.put("gShort", new gShort_FromJavabuf());
      toJavabufClassMap.put("java.lang.Short", gShort.class);
      fromJavabufClassMap.put(gShort.class.getName(), short.class);

      toJavabufMap.put("java.lang.Double" , new gDouble_ToJavabuf());
      fromJavabufMap.put("gDouble", new gDouble_FromJavabuf());
      toJavabufClassMap.put("java.lang.Double", gDouble.class);
      fromJavabufClassMap.put(gDouble.class.getName(), double.class);

      toJavabufMap.put("java.lang.Boolean" , new gBoolean_ToJavabuf());
      fromJavabufMap.put("gBoolean", new gBoolean_FromJavabuf());
      toJavabufClassMap.put("java.lang.Boolean", gBoolean.class);
      fromJavabufClassMap.put(gBoolean.class.getName(), boolean.class);

      toJavabufMap.put("java.lang.String" , new gString_ToJavabuf());
      fromJavabufMap.put("gString", new gString_FromJavabuf());
      toJavabufClassMap.put("java.lang.String", gString.class);
      fromJavabufClassMap.put(gString.class.getName(), java.lang.String.class);

      toJavabufMap.put("java.lang.Long" , new gLong_ToJavabuf());
      fromJavabufMap.put("gLong", new gLong_FromJavabuf());
      toJavabufClassMap.put("java.lang.Long", gLong.class);
      fromJavabufClassMap.put(gLong.class.getName(), long.class);

      toJavabufMap.put("java.lang.Byte" , new gByte_ToJavabuf());
      fromJavabufMap.put("gByte", new gByte_FromJavabuf());
      toJavabufClassMap.put("java.lang.Byte", gByte.class);
      fromJavabufClassMap.put(gByte.class.getName(), byte.class);

      toJavabufMap.put("java.lang.Character" , new gCharacter_ToJavabuf());
      fromJavabufMap.put("gCharacter", new gCharacter_FromJavabuf());
      toJavabufClassMap.put("java.lang.Character", gCharacter.class);
      fromJavabufClassMap.put(gCharacter.class.getName(), char.class);

      toJavabufMap.put("java.lang.Float" , new gFloat_ToJavabuf());
      fromJavabufMap.put("gFloat", new gFloat_FromJavabuf());
      toJavabufClassMap.put("java.lang.Float", gFloat.class);
      fromJavabufClassMap.put(gFloat.class.getName(), float.class);

      toJavabufMap.put("java.lang.Integer" , new gInteger_ToJavabuf());
      fromJavabufMap.put("gInteger", new gInteger_FromJavabuf());
      toJavabufClassMap.put("java.lang.Integer", gInteger.class);
      fromJavabufClassMap.put(gInteger.class.getName(), int.class);

      toJavabufMap.put("java.util.LinkedHashMap$Entry" , new java_util_LinkedHashMap_HIDDEN_Entry_ToJavabuf());
      fromJavabufMap.put("java_util_LinkedHashMap_HIDDEN_Entry", new java_util_LinkedHashMap_HIDDEN_Entry_FromJavabuf());
      toJavabufClassMap.put("java.util.LinkedHashMap$Entry", java_util_LinkedHashMap_HIDDEN_Entry.class);
      fromJavabufClassMap.put(java_util_LinkedHashMap_HIDDEN_Entry.class.getName(), getReturnNonPublicJavaClass("java.util.LinkedHashMap$Entry").getJavaClass());

      toJavabufMap.put("java.util.AbstractMap$SimpleEntry" , new java_util_AbstractMap_INNER_SimpleEntry_ToJavabuf());
      fromJavabufMap.put("java_util_AbstractMap_INNER_SimpleEntry", new java_util_AbstractMap_INNER_SimpleEntry_FromJavabuf());
      toJavabufClassMap.put("java.util.AbstractMap$SimpleEntry", java_util_AbstractMap_INNER_SimpleEntry.class);
      fromJavabufClassMap.put(java_util_AbstractMap_INNER_SimpleEntry.class.getName(), java.util.AbstractMap.SimpleEntry.class);

      toJavabufMap.put("java.util.HashMap$TreeNode" , new java_util_HashMap_HIDDEN_TreeNode_ToJavabuf());
      fromJavabufMap.put("java_util_HashMap_HIDDEN_TreeNode", new java_util_HashMap_HIDDEN_TreeNode_FromJavabuf());
      toJavabufClassMap.put("java.util.HashMap$TreeNode", java_util_HashMap_HIDDEN_TreeNode.class);
      fromJavabufClassMap.put(java_util_HashMap_HIDDEN_TreeNode.class.getName(), getReturnNonPublicJavaClass("java.util.HashMap$TreeNode").getJavaClass());

      toJavabufMap.put("java.util.ArrayList$ListItr" , new java_util_ArrayList_HIDDEN_ListItr_ToJavabuf());
      fromJavabufMap.put("java_util_ArrayList_HIDDEN_ListItr", new java_util_ArrayList_HIDDEN_ListItr_FromJavabuf());
      toJavabufClassMap.put("java.util.ArrayList$ListItr", java_util_ArrayList_HIDDEN_ListItr.class);
      fromJavabufClassMap.put(java_util_ArrayList_HIDDEN_ListItr.class.getName(), getReturnNonPublicJavaClass("java.util.ArrayList$ListItr").getJavaClass());

      toJavabufMap.put("java.lang.Object" , new java_lang___Object_ToJavabuf());
      fromJavabufMap.put("java_lang___Object", new java_lang___Object_FromJavabuf());
      toJavabufClassMap.put("java.lang.Object", java_lang___Object.class);
      fromJavabufClassMap.put(java_lang___Object.class.getName(), java.lang.Object.class);

      toJavabufMap.put("java.util.HashMap$KeyIterator" , new java_util_HashMap_HIDDEN_KeyIterator_ToJavabuf());
      fromJavabufMap.put("java_util_HashMap_HIDDEN_KeyIterator", new java_util_HashMap_HIDDEN_KeyIterator_FromJavabuf());
      toJavabufClassMap.put("java.util.HashMap$KeyIterator", java_util_HashMap_HIDDEN_KeyIterator.class);
      fromJavabufClassMap.put(java_util_HashMap_HIDDEN_KeyIterator.class.getName(), getReturnNonPublicJavaClass("java.util.HashMap$KeyIterator").getJavaClass());

      toJavabufMap.put("java.util.HashMap$Values" , new java_util_HashMap_HIDDEN_Values_ToJavabuf());
      fromJavabufMap.put("java_util_HashMap_HIDDEN_Values", new java_util_HashMap_HIDDEN_Values_FromJavabuf());
      toJavabufClassMap.put("java.util.HashMap$Values", java_util_HashMap_HIDDEN_Values.class);
      fromJavabufClassMap.put(java_util_HashMap_HIDDEN_Values.class.getName(), getReturnNonPublicJavaClass("java.util.HashMap$Values").getJavaClass());

      toJavabufMap.put("java.util.HashMap$UnsafeHolder" , new java_util_HashMap_HIDDEN_UnsafeHolder_ToJavabuf());
      fromJavabufMap.put("java_util_HashMap_HIDDEN_UnsafeHolder", new java_util_HashMap_HIDDEN_UnsafeHolder_FromJavabuf());
      toJavabufClassMap.put("java.util.HashMap$UnsafeHolder", java_util_HashMap_HIDDEN_UnsafeHolder.class);
      fromJavabufClassMap.put(java_util_HashMap_HIDDEN_UnsafeHolder.class.getName(), getReturnNonPublicJavaClass("java.util.HashMap$UnsafeHolder").getJavaClass());

      toJavabufMap.put("java.util.HashMap$EntrySet" , new java_util_HashMap_HIDDEN_EntrySet_ToJavabuf());
      fromJavabufMap.put("java_util_HashMap_HIDDEN_EntrySet", new java_util_HashMap_HIDDEN_EntrySet_FromJavabuf());
      toJavabufClassMap.put("java.util.HashMap$EntrySet", java_util_HashMap_HIDDEN_EntrySet.class);
      fromJavabufClassMap.put(java_util_HashMap_HIDDEN_EntrySet.class.getName(), getReturnNonPublicJavaClass("java.util.HashMap$EntrySet").getJavaClass());

      toJavabufMap.put("java.util.HashMap$ValueSpliterator" , new java_util_HashMap_HIDDEN_ValueSpliterator_ToJavabuf());
      fromJavabufMap.put("java_util_HashMap_HIDDEN_ValueSpliterator", new java_util_HashMap_HIDDEN_ValueSpliterator_FromJavabuf());
      toJavabufClassMap.put("java.util.HashMap$ValueSpliterator", java_util_HashMap_HIDDEN_ValueSpliterator.class);
      fromJavabufClassMap.put(java_util_HashMap_HIDDEN_ValueSpliterator.class.getName(), getReturnNonPublicJavaClass("java.util.HashMap$ValueSpliterator").getJavaClass());

      toJavabufMap.put("dev.resteasy.grpc.example.CC6" , new dev_resteasy_grpc_example___CC6_ToJavabuf());
      fromJavabufMap.put("dev_resteasy_grpc_example___CC6", new dev_resteasy_grpc_example___CC6_FromJavabuf());
      toJavabufClassMap.put("dev.resteasy.grpc.example.CC6", dev_resteasy_grpc_example___CC6.class);
      fromJavabufClassMap.put(dev_resteasy_grpc_example___CC6.class.getName(), dev.resteasy.grpc.example.CC6.class);

      toJavabufMap.put("java.util.HashSet" , new java_util___HashSet_ToJavabuf());
      fromJavabufMap.put("java_util___HashSet", new java_util___HashSet_FromJavabuf());
      toJavabufClassMap.put("java.util.HashSet", java_util___HashSet.class);
      fromJavabufClassMap.put(java_util___HashSet.class.getName(), java.util.HashSet.class);

      toJavabufMap.put("java.util.HashMap$ValueIterator" , new java_util_HashMap_HIDDEN_ValueIterator_ToJavabuf());
      fromJavabufMap.put("java_util_HashMap_HIDDEN_ValueIterator", new java_util_HashMap_HIDDEN_ValueIterator_FromJavabuf());
      toJavabufClassMap.put("java.util.HashMap$ValueIterator", java_util_HashMap_HIDDEN_ValueIterator.class);
      fromJavabufClassMap.put(java_util_HashMap_HIDDEN_ValueIterator.class.getName(), getReturnNonPublicJavaClass("java.util.HashMap$ValueIterator").getJavaClass());

      toJavabufMap.put("java.util.AbstractMap$SimpleImmutableEntry" , new java_util_AbstractMap_INNER_SimpleImmutableEntry_ToJavabuf());
      fromJavabufMap.put("java_util_AbstractMap_INNER_SimpleImmutableEntry", new java_util_AbstractMap_INNER_SimpleImmutableEntry_FromJavabuf());
      toJavabufClassMap.put("java.util.AbstractMap$SimpleImmutableEntry", java_util_AbstractMap_INNER_SimpleImmutableEntry.class);
      fromJavabufClassMap.put(java_util_AbstractMap_INNER_SimpleImmutableEntry.class.getName(), java.util.AbstractMap.SimpleImmutableEntry.class);

      toJavabufMap.put("java.util.HashMap$KeySet" , new java_util_HashMap_HIDDEN_KeySet_ToJavabuf());
      fromJavabufMap.put("java_util_HashMap_HIDDEN_KeySet", new java_util_HashMap_HIDDEN_KeySet_FromJavabuf());
      toJavabufClassMap.put("java.util.HashMap$KeySet", java_util_HashMap_HIDDEN_KeySet.class);
      fromJavabufClassMap.put(java_util_HashMap_HIDDEN_KeySet.class.getName(), getReturnNonPublicJavaClass("java.util.HashMap$KeySet").getJavaClass());

      toJavabufMap.put("java.util.ArrayList$ArrayListSpliterator" , new java_util_ArrayList_HIDDEN_ArrayListSpliterator_ToJavabuf());
      fromJavabufMap.put("java_util_ArrayList_HIDDEN_ArrayListSpliterator", new java_util_ArrayList_HIDDEN_ArrayListSpliterator_FromJavabuf());
      toJavabufClassMap.put("java.util.ArrayList$ArrayListSpliterator", java_util_ArrayList_HIDDEN_ArrayListSpliterator.class);
      fromJavabufClassMap.put(java_util_ArrayList_HIDDEN_ArrayListSpliterator.class.getName(), getReturnNonPublicJavaClass("java.util.ArrayList$ArrayListSpliterator").getJavaClass());

      toJavabufMap.put("java.util.ArrayList$SubList" , new java_util_ArrayList_HIDDEN_SubList_ToJavabuf());
      fromJavabufMap.put("java_util_ArrayList_HIDDEN_SubList", new java_util_ArrayList_HIDDEN_SubList_FromJavabuf());
      toJavabufClassMap.put("java.util.ArrayList$SubList", java_util_ArrayList_HIDDEN_SubList.class);
      fromJavabufClassMap.put(java_util_ArrayList_HIDDEN_SubList.class.getName(), getReturnNonPublicJavaClass("java.util.ArrayList$SubList").getJavaClass());

      toJavabufMap.put("java.util.AbstractList$ListItr" , new java_util_AbstractList_HIDDEN_ListItr_ToJavabuf());
      fromJavabufMap.put("java_util_AbstractList_HIDDEN_ListItr", new java_util_AbstractList_HIDDEN_ListItr_FromJavabuf());
      toJavabufClassMap.put("java.util.AbstractList$ListItr", java_util_AbstractList_HIDDEN_ListItr.class);
      fromJavabufClassMap.put(java_util_AbstractList_HIDDEN_ListItr.class.getName(), getReturnNonPublicJavaClass("java.util.AbstractList$ListItr").getJavaClass());

      toJavabufMap.put("java.util.ArrayList$Itr" , new java_util_ArrayList_HIDDEN_Itr_ToJavabuf());
      fromJavabufMap.put("java_util_ArrayList_HIDDEN_Itr", new java_util_ArrayList_HIDDEN_Itr_FromJavabuf());
      toJavabufClassMap.put("java.util.ArrayList$Itr", java_util_ArrayList_HIDDEN_Itr.class);
      fromJavabufClassMap.put(java_util_ArrayList_HIDDEN_Itr.class.getName(), getReturnNonPublicJavaClass("java.util.ArrayList$Itr").getJavaClass());

      toJavabufMap.put("java.util.HashMap$EntryIterator" , new java_util_HashMap_HIDDEN_EntryIterator_ToJavabuf());
      fromJavabufMap.put("java_util_HashMap_HIDDEN_EntryIterator", new java_util_HashMap_HIDDEN_EntryIterator_FromJavabuf());
      toJavabufClassMap.put("java.util.HashMap$EntryIterator", java_util_HashMap_HIDDEN_EntryIterator.class);
      fromJavabufClassMap.put(java_util_HashMap_HIDDEN_EntryIterator.class.getName(), getReturnNonPublicJavaClass("java.util.HashMap$EntryIterator").getJavaClass());

      toJavabufMap.put("java.util.HashMap$EntrySpliterator" , new java_util_HashMap_HIDDEN_EntrySpliterator_ToJavabuf());
      fromJavabufMap.put("java_util_HashMap_HIDDEN_EntrySpliterator", new java_util_HashMap_HIDDEN_EntrySpliterator_FromJavabuf());
      toJavabufClassMap.put("java.util.HashMap$EntrySpliterator", java_util_HashMap_HIDDEN_EntrySpliterator.class);
      fromJavabufClassMap.put(java_util_HashMap_HIDDEN_EntrySpliterator.class.getName(), getReturnNonPublicJavaClass("java.util.HashMap$EntrySpliterator").getJavaClass());

      toJavabufMap.put("dev.resteasy.grpc.example.CC3$CC3_Sub" , new dev_resteasy_grpc_example_CC3_INNER_CC3_Sub_ToJavabuf());
      fromJavabufMap.put("dev_resteasy_grpc_example_CC3_INNER_CC3_Sub", new dev_resteasy_grpc_example_CC3_INNER_CC3_Sub_FromJavabuf());
      toJavabufClassMap.put("dev.resteasy.grpc.example.CC3$CC3_Sub", dev_resteasy_grpc_example_CC3_INNER_CC3_Sub.class);
      fromJavabufClassMap.put(dev_resteasy_grpc_example_CC3_INNER_CC3_Sub.class.getName(), dev.resteasy.grpc.example.CC3.CC3_Sub.class);

      toJavabufMap.put("java.util.HashMap$HashIterator" , new java_util_HashMap_HIDDEN_HashIterator_ToJavabuf());
      toJavabufClassMap.put("java.util.HashMap$HashIterator", java_util_HashMap_HIDDEN_HashIterator.class);
      fromJavabufClassMap.put(java_util_HashMap_HIDDEN_HashIterator.class.getName(), getReturnNonPublicJavaClass("java.util.HashMap$HashIterator").getJavaClass());

      toJavabufMap.put("dev.resteasy.grpc.example.ArrayStuff$Stuff" , new dev_resteasy_grpc_example_ArrayStuff_HIDDEN_Stuff_ToJavabuf());
      fromJavabufMap.put("dev_resteasy_grpc_example_ArrayStuff_HIDDEN_Stuff", new dev_resteasy_grpc_example_ArrayStuff_HIDDEN_Stuff_FromJavabuf());
      toJavabufClassMap.put("dev.resteasy.grpc.example.ArrayStuff$Stuff", dev_resteasy_grpc_example_ArrayStuff_HIDDEN_Stuff.class);
      fromJavabufClassMap.put(dev_resteasy_grpc_example_ArrayStuff_HIDDEN_Stuff.class.getName(), getReturnNonPublicJavaClass("dev.resteasy.grpc.example.ArrayStuff$Stuff").getJavaClass());

      toJavabufMap.put("java.util.AbstractList$RandomAccessSpliterator" , new java_util_AbstractList_HIDDEN_RandomAccessSpliterator_ToJavabuf());
      fromJavabufMap.put("java_util_AbstractList_HIDDEN_RandomAccessSpliterator", new java_util_AbstractList_HIDDEN_RandomAccessSpliterator_FromJavabuf());
      toJavabufClassMap.put("java.util.AbstractList$RandomAccessSpliterator", java_util_AbstractList_HIDDEN_RandomAccessSpliterator.class);
      fromJavabufClassMap.put(java_util_AbstractList_HIDDEN_RandomAccessSpliterator.class.getName(), getReturnNonPublicJavaClass("java.util.AbstractList$RandomAccessSpliterator").getJavaClass());

      toJavabufMap.put("java.util.HashMap$KeySpliterator" , new java_util_HashMap_HIDDEN_KeySpliterator_ToJavabuf());
      fromJavabufMap.put("java_util_HashMap_HIDDEN_KeySpliterator", new java_util_HashMap_HIDDEN_KeySpliterator_FromJavabuf());
      toJavabufClassMap.put("java.util.HashMap$KeySpliterator", java_util_HashMap_HIDDEN_KeySpliterator.class);
      fromJavabufClassMap.put(java_util_HashMap_HIDDEN_KeySpliterator.class.getName(), getReturnNonPublicJavaClass("java.util.HashMap$KeySpliterator").getJavaClass());

      toJavabufMap.put("java.util.AbstractList$RandomAccessSubList" , new java_util_AbstractList_HIDDEN_RandomAccessSubList_ToJavabuf());
      fromJavabufMap.put("java_util_AbstractList_HIDDEN_RandomAccessSubList", new java_util_AbstractList_HIDDEN_RandomAccessSubList_FromJavabuf());
      toJavabufClassMap.put("java.util.AbstractList$RandomAccessSubList", java_util_AbstractList_HIDDEN_RandomAccessSubList.class);
      fromJavabufClassMap.put(java_util_AbstractList_HIDDEN_RandomAccessSubList.class.getName(), getReturnNonPublicJavaClass("java.util.AbstractList$RandomAccessSubList").getJavaClass());

      toJavabufMap.put("dev.resteasy.grpc.example.CC4" , new dev_resteasy_grpc_example___CC4_ToJavabuf());
      fromJavabufMap.put("dev_resteasy_grpc_example___CC4", new dev_resteasy_grpc_example___CC4_FromJavabuf());
      toJavabufClassMap.put("dev.resteasy.grpc.example.CC4", dev_resteasy_grpc_example___CC4.class);
      fromJavabufClassMap.put(dev_resteasy_grpc_example___CC4.class.getName(), dev.resteasy.grpc.example.CC4.class);

      toJavabufMap.put("dev.resteasy.grpc.example.CC9" , new dev_resteasy_grpc_example___CC9_ToJavabuf());
      fromJavabufMap.put("dev_resteasy_grpc_example___CC9", new dev_resteasy_grpc_example___CC9_FromJavabuf());
      toJavabufClassMap.put("dev.resteasy.grpc.example.CC9", dev_resteasy_grpc_example___CC9.class);
      fromJavabufClassMap.put(dev_resteasy_grpc_example___CC9.class.getName(), dev.resteasy.grpc.example.CC9.class);

      toJavabufMap.put("java.util.AbstractList$SubList" , new java_util_AbstractList_HIDDEN_SubList_ToJavabuf());
      fromJavabufMap.put("java_util_AbstractList_HIDDEN_SubList", new java_util_AbstractList_HIDDEN_SubList_FromJavabuf());
      toJavabufClassMap.put("java.util.AbstractList$SubList", java_util_AbstractList_HIDDEN_SubList.class);
      fromJavabufClassMap.put(java_util_AbstractList_HIDDEN_SubList.class.getName(), getReturnNonPublicJavaClass("java.util.AbstractList$SubList").getJavaClass());

      toJavabufMap.put("dev.resteasy.grpc.example.CC7" , new dev_resteasy_grpc_example___CC7_ToJavabuf());
      fromJavabufMap.put("dev_resteasy_grpc_example___CC7", new dev_resteasy_grpc_example___CC7_FromJavabuf());
      toJavabufClassMap.put("dev.resteasy.grpc.example.CC7", dev_resteasy_grpc_example___CC7.class);
      fromJavabufClassMap.put(dev_resteasy_grpc_example___CC7.class.getName(), dev.resteasy.grpc.example.CC7.class);

      toJavabufMap.put("java.util.HashMap$HashMapSpliterator" , new java_util_HashMap_HIDDEN_HashMapSpliterator_ToJavabuf());
      fromJavabufMap.put("java_util_HashMap_HIDDEN_HashMapSpliterator", new java_util_HashMap_HIDDEN_HashMapSpliterator_FromJavabuf());
      toJavabufClassMap.put("java.util.HashMap$HashMapSpliterator", java_util_HashMap_HIDDEN_HashMapSpliterator.class);
      fromJavabufClassMap.put(java_util_HashMap_HIDDEN_HashMapSpliterator.class.getName(), getReturnNonPublicJavaClass("java.util.HashMap$HashMapSpliterator").getJavaClass());

      toJavabufMap.put("java.util.HashMap$Node" , new java_util_HashMap_HIDDEN_Node_ToJavabuf());
      fromJavabufMap.put("java_util_HashMap_HIDDEN_Node", new java_util_HashMap_HIDDEN_Node_FromJavabuf());
      toJavabufClassMap.put("java.util.HashMap$Node", java_util_HashMap_HIDDEN_Node.class);
      fromJavabufClassMap.put(java_util_HashMap_HIDDEN_Node.class.getName(), getReturnNonPublicJavaClass("java.util.HashMap$Node").getJavaClass());

      toJavabufMap.put("java.util.AbstractList$Itr" , new java_util_AbstractList_HIDDEN_Itr_ToJavabuf());
      fromJavabufMap.put("java_util_AbstractList_HIDDEN_Itr", new java_util_AbstractList_HIDDEN_Itr_FromJavabuf());
      toJavabufClassMap.put("java.util.AbstractList$Itr", java_util_AbstractList_HIDDEN_Itr.class);
      fromJavabufClassMap.put(java_util_AbstractList_HIDDEN_Itr.class.getName(), getReturnNonPublicJavaClass("java.util.AbstractList$Itr").getJavaClass());

      toJavabufMap.put("java.util.HashMap" , new java_util___HashMap_ToJavabuf());
      fromJavabufMap.put("java_util___HashMap", new java_util___HashMap_FromJavabuf());
      toJavabufClassMap.put("java.util.HashMap", java_util___HashMap.class);
      fromJavabufClassMap.put(java_util___HashMap.class.getName(), java.util.HashMap.class);

      toJavabufMap.put("java.util.ArrayList" , new java_util___ArrayList_ToJavabuf());
      fromJavabufMap.put("java_util___ArrayList", new java_util___ArrayList_FromJavabuf());
      toJavabufClassMap.put("java.util.ArrayList", java_util___ArrayList.class);
      fromJavabufClassMap.put(java_util___ArrayList.class.getName(), java.util.ArrayList.class);

      toJavabufMap.put("dev.resteasy.grpc.example.ArrayStuff" , new dev_resteasy_grpc_example___ArrayStuff_ToJavabuf());
      fromJavabufMap.put("dev_resteasy_grpc_example___ArrayStuff", new dev_resteasy_grpc_example___ArrayStuff_FromJavabuf());
      toJavabufClassMap.put("dev.resteasy.grpc.example.ArrayStuff", dev_resteasy_grpc_example___ArrayStuff.class);
      fromJavabufClassMap.put(dev_resteasy_grpc_example___ArrayStuff.class.getName(), dev.resteasy.grpc.example.ArrayStuff.class);

      toJavabufMap.put("dev.resteasy.grpc.example.IntfImpl" , new dev_resteasy_grpc_example___IntfImpl_ToJavabuf());
      fromJavabufMap.put("dev_resteasy_grpc_example___IntfImpl", new dev_resteasy_grpc_example___IntfImpl_FromJavabuf());
      toJavabufClassMap.put("dev.resteasy.grpc.example.IntfImpl", dev_resteasy_grpc_example___IntfImpl.class);
      fromJavabufClassMap.put(dev_resteasy_grpc_example___IntfImpl.class.getName(), dev.resteasy.grpc.example.IntfImpl.class);

      toJavabufMap.put("dev.resteasy.grpc.example.CC1$InnerClass" , new dev_resteasy_grpc_example_CC1_INNER_InnerClass_ToJavabuf());
      fromJavabufMap.put("dev_resteasy_grpc_example_CC1_INNER_InnerClass", new dev_resteasy_grpc_example_CC1_INNER_InnerClass_FromJavabuf());
      toJavabufClassMap.put("dev.resteasy.grpc.example.CC1$InnerClass", dev_resteasy_grpc_example_CC1_INNER_InnerClass.class);
      fromJavabufClassMap.put(dev_resteasy_grpc_example_CC1_INNER_InnerClass.class.getName(), dev.resteasy.grpc.example.CC1.InnerClass.class);

      toJavabufMap.put("dev.resteasy.grpc.example.CC2" , new dev_resteasy_grpc_example___CC2_ToJavabuf());
      fromJavabufMap.put("dev_resteasy_grpc_example___CC2", new dev_resteasy_grpc_example___CC2_FromJavabuf());
      toJavabufClassMap.put("dev.resteasy.grpc.example.CC2", dev_resteasy_grpc_example___CC2.class);
      fromJavabufClassMap.put(dev_resteasy_grpc_example___CC2.class.getName(), dev.resteasy.grpc.example.CC2.class);

      toJavabufMap.put("dev.resteasy.grpc.bridge.runtime.sse.SseEvent" , new dev_resteasy_grpc_bridge_runtime_sse___SseEvent_ToJavabuf());
      fromJavabufMap.put("dev_resteasy_grpc_bridge_runtime_sse___SseEvent", new dev_resteasy_grpc_bridge_runtime_sse___SseEvent_FromJavabuf());
      toJavabufClassMap.put("dev.resteasy.grpc.bridge.runtime.sse.SseEvent", dev_resteasy_grpc_bridge_runtime_sse___SseEvent.class);
      fromJavabufClassMap.put(dev_resteasy_grpc_bridge_runtime_sse___SseEvent.class.getName(), dev.resteasy.grpc.bridge.runtime.sse.SseEvent.class);

      toJavabufMap.put("dev.resteasy.grpc.example.CC5" , new dev_resteasy_grpc_example___CC5_ToJavabuf());
      fromJavabufMap.put("dev_resteasy_grpc_example___CC5", new dev_resteasy_grpc_example___CC5_FromJavabuf());
      toJavabufClassMap.put("dev.resteasy.grpc.example.CC5", dev_resteasy_grpc_example___CC5.class);
      fromJavabufClassMap.put(dev_resteasy_grpc_example___CC5.class.getName(), dev.resteasy.grpc.example.CC5.class);

      toJavabufMap.put("dev.resteasy.grpc.example.CC3" , new dev_resteasy_grpc_example___CC3_ToJavabuf());
      fromJavabufMap.put("dev_resteasy_grpc_example___CC3", new dev_resteasy_grpc_example___CC3_FromJavabuf());
      toJavabufClassMap.put("dev.resteasy.grpc.example.CC3", dev_resteasy_grpc_example___CC3.class);
      fromJavabufClassMap.put(dev_resteasy_grpc_example___CC3.class.getName(), dev.resteasy.grpc.example.CC3.class);


      WRAPPER_TYPES.add(java.lang.Boolean.class);
      WRAPPER_TYPES.add(java.lang.Byte.class);
      WRAPPER_TYPES.add(java.lang.Short.class);
      WRAPPER_TYPES.add(java.lang.Integer.class);
      WRAPPER_TYPES.add(java.lang.Long.class);
      WRAPPER_TYPES.add(java.lang.Float.class);
      WRAPPER_TYPES.add(java.lang.Double.class);
      WRAPPER_TYPES.add(java.lang.Character.class);
      WRAPPER_TYPES.add(java.lang.String.class);
   }

   public boolean handlesToJavabuf(Class<?> clazz) {
      return clazz.isPrimitive() || toJavabufMap.containsKey(clazz.getName());
   }

   public boolean handlesFromJavabuf(Class<?> clazz) {
      return clazz.isPrimitive() || toJavabufMap.containsKey(clazz.getName());
   }

   public Message translateToJavabuf(Object o) {
      if (o.getClass().isArray()) {
         return ArrayUtility.getHolder(this, o);
      }
      TranslateToJavabuf ttj = toJavabufMap.get(o.getClass().getName());
      if (ttj == null) {
         throw new RuntimeException(o.getClass() + " is not recognized");
      }
      ttj.clear();
      return ttj.assignToJavabuf(o);
   }

   @SuppressWarnings("rawtypes")
   public Class translateToJavabufClass(Class<?> clazz) {
      return toJavabufClassMap.get(clazz.getName());
   }

   @SuppressWarnings("rawtypes")
   public Class translateToJavabufClass(String classname) {
      return toJavabufClassMap.get(classname);
   }

   @SuppressWarnings("rawtypes")
   @Override
   public Class translatefromJavabufClass(String classname) {
      return fromJavabufClassMap.get(classname);
   }

   @Override
   public String getOuterClassname() {
      return "dev.resteasy.grpc.example.CC1_proto";
   }

   public Object translateFromJavabuf(Message message) {
      if (CC1_proto.dev_resteasy_grpc_arrays___ArrayHolder.class.equals(message.getClass())) {
         try {
            return ArrayUtility.getArray(this, (dev_resteasy_grpc_arrays___ArrayHolder) message);
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
      }
      String s = null;
      try {
         s = message.getDescriptorForType().getFullName();
         s = s.substring(s.lastIndexOf(".") + 1);
         TranslateFromJavabuf tfj = fromJavabufMap.get(s);
         if (tfj == null) {
            throw new RuntimeException(message.getClass() + " is not recognized");
         }
         return tfj.assignFromJavabuf(message);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   private static AssignToJavabuf toJavabuf(Class<?> clazz, FieldDescriptor fd) {
      try {
         AssignToJavabuf assignToJavabuf = (obj, messageBuilder) -> {
            try {
               if (obj == null) {
                  return;
               }
                  final Field field = Utility.getField(clazz, fd.getName());
                  field.setAccessible(true);
                  if (field.getType().isArray()) {
                        Object array = field.get(obj);
                        if (array == null) {
                           return;
                        }
                        Class<?> componentType = array.getClass().getComponentType();
                        if (componentType.isArray()) {
                           messageBuilder.setField(fd, arrayHolderToJavabuf.assignToJavabuf(field.get(obj)));
                        } else if (componentType.isPrimitive() || WRAPPER_TYPES.contains(componentType)) {
                           if (Character.class.equals(componentType) || char.class.equals(componentType)) {
                              messageBuilder.setField(fd, charsToString(array));
                           } else if (byte.class.equals(componentType)) {
                              messageBuilder.setField(fd, ByteString.copyFrom((byte[])array));
                           } else if (Byte.class.equals(componentType)) {
                              for (int i = 0; i < Array.getLength(array); i++) {
                                 messageBuilder.addRepeatedField(fd, Array.get(array, i));
                              }
                           } else {
                              for (int i = 0; i < Array.getLength(array); i++) {
                                 messageBuilder.addRepeatedField(fd, Array.get(array, i));
                              }
                           }
                        } else if (Object.class.equals(componentType)) {
System.out.println(Array.getLength(array));
                           for (int i = 0; i < Array.getLength(array); i++) {
                              if (Array.get(array, i) != null) {
                                 messageBuilder.addRepeatedField(fd, Any.pack(translator.translateToJavabuf(Array.get(array, i))));
                              }
                           }
                        } else {
                           messageBuilder.setField(fd, ArrayUtility.getHolder(INSTANCE, obj));
//                           messageBuilder.addRepeatedField(fd, ELEMENT_WRAPPER.newBuilder().setPosition(Array.getLength(array)).build());
//                           for (int i = 0; i < Array.getLength(array); i++) {
//                              if (Array.get(array, i) != null) {
//                                 messageBuilder.addRepeatedField(fd, wrapArrayElement(translator.translateToJavabuf(Array.get(array, i)), i));
//                              }
//                           }
                           
                        }
                    }
                  else if (".google.protobuf.Any".equals(fd.toProto().getTypeName())) {
                     if (field.get(obj) != null) {
                        Class<?> c = field.get(obj).getClass();
                        if (Any.class.equals(c)) {
                           messageBuilder.setField(fd, field.get(obj));
                        } else {
                           Message message = toJavabufMap.get(c.getName()).assignToJavabuf(field.get(obj));
                           messageBuilder.setField(fd, Any.pack(message));
                        }
                     }
                  } else if (!String.class.equals(field.getType()) && toJavabufMap.keySet().contains(field.getType().getName())) {
                        Message message = toJavabufMap.get(field.getType().getName()).assignToJavabuf(field.get(obj));
                        if (message != null) {
                           messageBuilder.setField(fd, message);
                        }
                    } else if (field.get(obj) != null) {
                        if (Byte.class.equals(field.getType()) || byte.class.equals(field.getType())) {
                           Byte b = field.getByte(obj);
                           messageBuilder.setField(fd, b.intValue());
                        } else if (Short.class.equals(field.getType()) || short.class.equals(field.getType())) {
                           Short s = field.getShort(obj);
                           messageBuilder.setField(fd, s.intValue());
                        } else if (Character.class.equals(field.getType()) || char.class.equals(field.getType())) {
                           Character c = field.getChar(obj);
                           messageBuilder.setField(fd, String.valueOf(c));
                        } else if (Descriptors.FieldDescriptor.JavaType.BYTE_STRING.equals(fd.getJavaType())) {
                           ByteString bs = ByteString.copyFrom((byte[]) field.get(obj));
                           messageBuilder.setField(fd, bs);
                        } else {
                           messageBuilder.setField(fd, field.get(obj));
                        }
                     }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
         };
         return assignToJavabuf;
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   private static AssignFromJavabuf fromJavabuf(Class<?> javaClass, FieldDescriptor fd) {
      try {
         AssignFromJavabuf assignFromJavabuf = (message, object) -> {
            try {
                  final Field field = Utility.getField(javaClass, javabufClassToJavaClass(fd.getName()));
                  field.setAccessible(true);
                  if (field.getType().isArray()) {
                     String typeName = fd.toProto().getTypeName();
                     if (".google.protobuf.Any".equals(typeName)) {
                        if (message.getRepeatedFieldCount(fd) > 0) {
                           Class clazz = Utility.extractClassFromAny((Any) message.getRepeatedField(fd, 0), INSTANCE);
                           Class javaClazz = INSTANCE.translatefromJavabufClass(clazz.getName());
                           Object array = Array.newInstance(javaClazz, message.getRepeatedFieldCount(fd));
                           for (int i = 0; i < message.getRepeatedFieldCount(fd); i++) {
                              Any any = (Any) message.getRepeatedField(fd, i);
                              Object o = INSTANCE.translateFromJavabuf(any.unpack(clazz));
                              Array.set(array, i, o);
                           }
                           Utility.setField(field, object, array, INSTANCE);
                        }
                     } else if ("dev_resteasy_grpc_arrays___ArrayHolder".equals(typeName.substring(typeName.lastIndexOf(".") + 1))) {
                        dev_resteasy_grpc_arrays___ArrayHolder ah = (dev_resteasy_grpc_arrays___ArrayHolder) message.getField(fd);
                        Utility.setField(field, object, ArrayUtility.getArray(translator, ah), INSTANCE);
                     } else if (Descriptors.FieldDescriptor.JavaType.STRING.equals(fd.getJavaType())) {
                        if (char.class.equals(field.getType().getComponentType())) {
                           char[] cs = stringToChars((String) message.getField(fd));
                           Utility.setField(field, object, cs, INSTANCE);
                        } else {
                            Character[] cs = stringToCharacters((String) message.getField(fd));
                            Utility.setField(field, object, cs, INSTANCE);
                        }
                     } else if (byte.class.equals(field.getType().getComponentType())) {
                        ByteString bs = (ByteString) message.getField(fd);
                        Utility.setField(field, object, bs.toByteArray(), INSTANCE);
                      } else if (Byte.class.equals(field.getType().getComponentType())) {
                         IntList il = (IntList) message.getField(fd);
                         Byte[] bs = new Byte[il.size()];
                         for (int i = 0; i < il.size(); i++) {
                            bs[i] = il.get(i).byteValue();
                         }
                         Utility.setField(field, object, bs, INSTANCE);
                     } else if (message.getField(fd) instanceof List) {
                        List list = (List) message.getField(fd);
                        if (list.size() == 0) {
                           return;
                        }
                        if (message.getField(fd).getClass().getPackage().getName().startsWith("com.google.protobuf")) {
//                           if (field.get(object) == null) {
//                              Utility.setField(field, object, getArray(field, list.size()), INSTANCE);
//                           }
                           if ((short.class.equals(field.getType().getComponentType())
                             || Short.class.equals(field.getType().getComponentType()))
                                && list.get(0) instanceof Integer) {
                              for (int i = 0; i < list.size(); i++) {
                                 Array.set(field.get(object), i, ((Integer) list.get(i)).shortValue());
                               }
                           } else {
                              for (int i = 0; i < list.size(); i++) {
                                 Array.set(field.get(object), i, list.get(i));
                              }
                           }
                        } 
//                        else {
//                           if (fd.getMessageType().getName().endsWith("ELEMENT_WRAPPER")) {
//                              if (field.get(object) == null) {
//                                  int size = (int) ((ELEMENT_WRAPPER) list.get(0)).getPosition();
//                                  Utility.setField(field, object, Array.newInstance(field.getType().getComponentType(), size), INSTANCE);
//                              }
//                              for (int i = 1; i < list.size(); i++) {
//                                 int position = (int) ((ELEMENT_WRAPPER) list.get(i)).getPosition();
//                                 Message element = unwrapArrayElement((ELEMENT_WRAPPER) list.get(i));
//                                 Array.set(field.get(object), position, translator.translateFromJavabuf(element));
//                              }
//                            } else for (int i = 0; i < list.size(); i++) {
//                              Array.set(field.get(object), i, translator.translateFromJavabuf((Message) list.get(i)));
//                           }
//                        }
                     }
                  } else if (Descriptors.FieldDescriptor.Type.MESSAGE.equals(fd.getType())
                     && fromJavabufMap.keySet().contains(fd.getMessageType().getName())) {
                     if (message.hasField(fd)) {
                        Message submessage = (Message) message.getField(fd);
                        Object obj = fromJavabufMap.get(fd.getMessageType().getName()).assignFromJavabuf(submessage);
                        Utility.setField(field, object, obj, INSTANCE);
                     }
                  } else if (JavaType.MESSAGE.equals(fd.getJavaType()) && dev_resteasy_grpc_arrays___ArrayHolder.class.getSimpleName().equals(fd.getMessageType().getName())) {
                     dev_resteasy_grpc_arrays___ArrayHolder submessage = (dev_resteasy_grpc_arrays___ArrayHolder) message.getField(fd);
                     Object o = ArrayUtility.getArray(translator, submessage);
                  } else {
                     Object ooo = message.getField(fd);
                     if (Integer.class.equals(ooo.getClass()) && (Byte.class.equals(field.getType()) || byte.class.equals(field.getType()))) {
                        Utility.setField(field, object, ((Integer) ooo).byteValue(), INSTANCE);
                     } else if (Integer.class.equals(ooo.getClass()) && (Short.class.equals(field.getType()) || short.class.equals(field.getType()))) {
                        Utility.setField(field, object, ((Integer) ooo).shortValue(), INSTANCE);
                     } else if (Integer.class.equals(ooo.getClass()) && (Character.class.equals(field.getType()) || char.class.equals(field.getType()))) {
                        int i = ((Integer)ooo).intValue();
                        Utility.setField(field, object, Character.toChars(i)[0], INSTANCE);
                     } else if (ooo instanceof ByteString) {
                        Utility.setField(field, object, ((ByteString) ooo).newInput().readAllBytes(), INSTANCE);
                     } else {
                        Utility.setField(field, object, ooo, INSTANCE);
                  }
               }
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         };
         return assignFromJavabuf;
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   private static String javaClassToJavabufClass(String javaClassName) {
      String javabufClassName = javaClassName.replace(".", "_");
      int i = javabufClassName.lastIndexOf("_");
      javabufClassName = javabufClassName.substring(0, i) + "___" + javabufClassName.substring(i + 1);
      return javabufClassName;
   }

   private static String javabufClassToJavaClass(String fieldName) {
      int pos = fieldName.lastIndexOf("___");
      if (pos >= 0) {
         return fieldName.substring(0, pos);
      }
      return fieldName;
   }

   private static FieldDescriptor getSuperField(Message message) {
      Map<FieldDescriptor, Object> map = message.getAllFields();
      for (FieldDescriptor fd : map.keySet()) {
         if (fd.getName().endsWith("___super")) {
            return fd;
         }
      }
      return null;
   }

   private static boolean isSuperClass(String fieldName) {
      return fieldName.endsWith("___super");
   }

   private static String charsToString(Object o) {
      StringBuilder sb = new StringBuilder();
      if (char.class.equals(o.getClass().getComponentType())) {
         char[] array = (char[]) o;
         for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
         }
      } else {
           Character[] array = (Character[]) o;
           for (int i = 0; i < array.length; i++) {
              sb.append(array[i]);
            }
      }
      return sb.toString();
   }

   private static char[] stringToChars(String s) {
      char[] cs = new char[s.length()];
      for (int i = 0; i < s.length(); i++) {
         cs[i] = s.charAt(i);
      }
      return cs;
   }

   private static Character[] stringToCharacters(String s) {
      Character[] cs = new Character[s.length()];
      for (int i = 0; i < s.length(); i++) {
         cs[i] = s.charAt(i);
      }
      return cs;
   }

   private static ReturnJavaClass getReturnNonPublicJavaClass(final String classname) {
      return new ReturnJavaClass() {
         public Class<?> getJavaClass() {
            try {
               return Class.forName(classname);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      };
   }

   private static Constructor<?> getConstructor(String classname) throws Exception {
      if (constructors.containsKey(classname)) {
         return constructors.get(classname);
      }
      Constructor<?>[] conss = Class.forName(classname).getDeclaredConstructors();
      if (conss.length == 0) {
         return null;
      }
      Constructor<?> cons = conss[0];
      for (int i = 1; i < conss.length; i++) {
         if (conss[i].getParameterCount() < cons.getParameterCount()) {
            cons = conss[i];
         }
      }
      cons.setAccessible(true);
      constructors.put(classname, cons);
      return cons;
   }

   static String squashName(String name) {
      StringBuilder sb = new StringBuilder();
      int pos = name.indexOf('_');
      while (pos > 0) {
         sb.append(name.substring(0, pos));
         name = name.substring(pos + 1);
         pos = name.indexOf('_');
      }
      sb.append(name);
      return sb.toString().toLowerCase();
   }

   static class dev_resteasy_grpc_arrays___ArrayHolder_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_arrays___ArrayHolder.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
//         for (FieldDescriptor f : descriptor.getFields()) {
//            String name = f.getName();
//            if (descriptor.findFieldByName(name) == null) {
//               continue;
//            }
//            assignList.add(toJavabuf(dev.resteasy.grpc.arrays.ArrayHolder.class, descriptor.findFieldByName(name)));
//         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
//         dev_resteasy_grpc_arrays___ArrayHolder.Builder builder = dev_resteasy_grpc_arrays___ArrayHolder.newBuilder();
//         for (AssignToJavabuf assignTo : assignList) {
//            try {
//               assignTo.assign(c1, builder);
//            } catch (Exception e) {
//               throw new RuntimeException(e);
//            }
//         }
//         return builder.build();
         return ArrayUtility.getHolder(c1);
      }

      public void clear() {
      }
   }

   static class gShort_ToJavabuf implements TranslateToJavabuf {

      public Message assignToJavabuf(Object x) {
         if (x == null) {
            return null;
         }
         Short p = (Short) x;
         dev.resteasy.grpc.example.CC1_proto.gShort.Builder builder = dev.resteasy.grpc.example.CC1_proto.gShort.newBuilder();
         return builder.setValue(p.shortValue()).build();
      }

      public void clear() {
         //
      }
   }

   static class gShort_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.gShort.getDescriptor();
      public Short assignFromJavabuf(Message message) {
         FieldDescriptor fd = descriptor.getFields().get(0);
         return ((Integer) message.getField(fd)).shortValue();
      }

      public void assignExistingFromJavabuf(Message message, Object obj) { }
   }

   static class gDouble_ToJavabuf implements TranslateToJavabuf {

      public Message assignToJavabuf(Object x) {
         if (x == null) {
            return null;
         }
         Double p = (Double) x;
         dev.resteasy.grpc.example.CC1_proto.gDouble.Builder builder = dev.resteasy.grpc.example.CC1_proto.gDouble.newBuilder();
         return builder.setValue(p.doubleValue()).build();
      }

      public void clear() {
         //
      }
   }

   static class gDouble_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.gDouble.getDescriptor();
      public Double assignFromJavabuf(Message message) {
         FieldDescriptor fd = descriptor.getFields().get(0);
         return (Double) message.getField(fd);
      }

      public void assignExistingFromJavabuf(Message message, Object obj) { }
   }

   static class gBoolean_ToJavabuf implements TranslateToJavabuf {

      public Message assignToJavabuf(Object x) {
         if (x == null) {
            return null;
         }
         Boolean p = (Boolean) x;
         dev.resteasy.grpc.example.CC1_proto.gBoolean.Builder builder = dev.resteasy.grpc.example.CC1_proto.gBoolean.newBuilder();
         return builder.setValue(p.booleanValue()).build();
      }

      public void clear() {
         //
      }
   }

   static class gBoolean_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.gBoolean.getDescriptor();
      public Boolean assignFromJavabuf(Message message) {
         FieldDescriptor fd = descriptor.getFields().get(0);
         return (Boolean) message.getField(fd);
      }

      public void assignExistingFromJavabuf(Message message, Object obj) { }
   }

   static class gString_ToJavabuf implements TranslateToJavabuf {

      public Message assignToJavabuf(Object x) {
         if (x == null) {
            return null;
         }
         String p = (String) x;
         dev.resteasy.grpc.example.CC1_proto.gString.Builder builder = dev.resteasy.grpc.example.CC1_proto.gString.newBuilder();
         return builder.setValue(p).build();
      }

      public void clear() {
         //
      }
   }

   static class gString_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.gString.getDescriptor();
      public String assignFromJavabuf(Message message) {
         FieldDescriptor fd = descriptor.getFields().get(0);
         return (String) message.getField(fd);
      }

      public void assignExistingFromJavabuf(Message message, Object obj) { }
   }

   static class gLong_ToJavabuf implements TranslateToJavabuf {

      public Message assignToJavabuf(Object x) {
         if (x == null) {
            return null;
         }
         Long p = (Long) x;
         dev.resteasy.grpc.example.CC1_proto.gLong.Builder builder = dev.resteasy.grpc.example.CC1_proto.gLong.newBuilder();
         return builder.setValue(p.longValue()).build();
      }

      public void clear() {
         //
      }
   }

   static class gLong_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.gLong.getDescriptor();
      public Long assignFromJavabuf(Message message) {
         FieldDescriptor fd = descriptor.getFields().get(0);
         return (Long) message.getField(fd);
      }

      public void assignExistingFromJavabuf(Message message, Object obj) { }
   }

   static class gByte_ToJavabuf implements TranslateToJavabuf {

      public Message assignToJavabuf(Object x) {
         if (x == null) {
            return null;
         }
         Byte p = (Byte) x;
         dev.resteasy.grpc.example.CC1_proto.gByte.Builder builder = dev.resteasy.grpc.example.CC1_proto.gByte.newBuilder();
         return builder.setValue(p.byteValue()).build();
      }

      public void clear() {
         //
      }
   }

   static class gByte_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.gByte.getDescriptor();
      public Byte assignFromJavabuf(Message message) {
         FieldDescriptor fd = descriptor.getFields().get(0);
         return ((Integer) message.getField(fd)).byteValue();
      }

      public void assignExistingFromJavabuf(Message message, Object obj) { }
   }

   static class gCharacter_ToJavabuf implements TranslateToJavabuf {

      public Message assignToJavabuf(Object x) {
         if (x == null) {
            return null;
         }
         Character p = (Character) x;
         dev.resteasy.grpc.example.CC1_proto.gCharacter.Builder builder = dev.resteasy.grpc.example.CC1_proto.gCharacter.newBuilder();
         return builder.setValue(p.toString()).build();
      }

      public void clear() {
         //
      }
   }

   static class gCharacter_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.gCharacter.getDescriptor();
      public Character assignFromJavabuf(Message message) {
         FieldDescriptor fd = descriptor.getFields().get(0);
         return ((String) message.getField(fd)).charAt(0);
      }

      public void assignExistingFromJavabuf(Message message, Object obj) { }
   }

   static class gFloat_ToJavabuf implements TranslateToJavabuf {

      public Message assignToJavabuf(Object x) {
         if (x == null) {
            return null;
         }
         Float p = (Float) x;
         dev.resteasy.grpc.example.CC1_proto.gFloat.Builder builder = dev.resteasy.grpc.example.CC1_proto.gFloat.newBuilder();
         return builder.setValue(p.floatValue()).build();
      }

      public void clear() {
         //
      }
   }

   static class gFloat_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.gFloat.getDescriptor();
      public Float assignFromJavabuf(Message message) {
         FieldDescriptor fd = descriptor.getFields().get(0);
         return (Float) message.getField(fd);
      }

      public void assignExistingFromJavabuf(Message message, Object obj) { }
   }

   static class gInteger_ToJavabuf implements TranslateToJavabuf {

      public Message assignToJavabuf(Object x) {
         if (x == null) {
            return null;
         }
         Integer p = (Integer) x;
         dev.resteasy.grpc.example.CC1_proto.gInteger.Builder builder = dev.resteasy.grpc.example.CC1_proto.gInteger.newBuilder();
         return builder.setValue(p.intValue()).build();
      }

      public void clear() {
         //
      }
   }

   static class gInteger_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.gInteger.getDescriptor();
      public Integer assignFromJavabuf(Message message) {
         FieldDescriptor fd = descriptor.getFields().get(0);
         return (Integer) message.getField(fd);
      }

      public void assignExistingFromJavabuf(Message message, Object obj) { }
   }

   static class java_util_LinkedHashMap_HIDDEN_Entry_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_LinkedHashMap_HIDDEN_Entry.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.LinkedHashMap$Entry").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_LinkedHashMap_HIDDEN_Entry.Builder builder = java_util_LinkedHashMap_HIDDEN_Entry.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_LinkedHashMap_HIDDEN_Entry_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_LinkedHashMap_HIDDEN_Entry.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.LinkedHashMap$Entry").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.LinkedHashMap$Entry").newInstance(0, null, null, null);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_AbstractMap_INNER_SimpleEntry_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_AbstractMap_INNER_SimpleEntry.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(java.util.AbstractMap.SimpleEntry.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_AbstractMap_INNER_SimpleEntry.Builder builder = java_util_AbstractMap_INNER_SimpleEntry.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_AbstractMap_INNER_SimpleEntry_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_AbstractMap_INNER_SimpleEntry.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(java.util.AbstractMap.SimpleEntry.class, descriptor.findFieldByName(name)));
         }
      }

      public SimpleEntry assignFromJavabuf(Message message) {
         SimpleEntry obj = new SimpleEntry(null);
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_HashMap_HIDDEN_TreeNode_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_TreeNode.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$TreeNode").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_HashMap_HIDDEN_TreeNode.Builder builder = java_util_HashMap_HIDDEN_TreeNode.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_HashMap_HIDDEN_TreeNode_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_TreeNode.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$TreeNode").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.HashMap$TreeNode").newInstance(0, null, null, null);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_ArrayList_HIDDEN_ListItr_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_ArrayList_HIDDEN_ListItr.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.ArrayList$ListItr").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_ArrayList_HIDDEN_ListItr.Builder builder = java_util_ArrayList_HIDDEN_ListItr.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_ArrayList_HIDDEN_ListItr_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_ArrayList_HIDDEN_ListItr.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.ArrayList$ListItr").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.ArrayList$ListItr").newInstance(null, 0);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_lang___Object_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_lang___Object.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(java.lang.Object.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_lang___Object.Builder builder = java_lang___Object.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_lang___Object_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_lang___Object.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(java.lang.Object.class, descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         Object obj = new Object();
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_HashMap_HIDDEN_KeyIterator_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_KeyIterator.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$KeyIterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_HashMap_HIDDEN_KeyIterator.Builder builder = java_util_HashMap_HIDDEN_KeyIterator.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_HashMap_HIDDEN_KeyIterator_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_KeyIterator.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$KeyIterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.HashMap$KeyIterator").newInstance(null);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_HashMap_HIDDEN_Values_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_Values.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$Values").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_HashMap_HIDDEN_Values.Builder builder = java_util_HashMap_HIDDEN_Values.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_HashMap_HIDDEN_Values_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_Values.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$Values").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.HashMap$Values").newInstance(null);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_HashMap_HIDDEN_UnsafeHolder_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_UnsafeHolder.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$UnsafeHolder").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_HashMap_HIDDEN_UnsafeHolder.Builder builder = java_util_HashMap_HIDDEN_UnsafeHolder.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_HashMap_HIDDEN_UnsafeHolder_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_UnsafeHolder.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$UnsafeHolder").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.HashMap$UnsafeHolder").newInstance();
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_HashMap_HIDDEN_EntrySet_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_EntrySet.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$EntrySet").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_HashMap_HIDDEN_EntrySet.Builder builder = java_util_HashMap_HIDDEN_EntrySet.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_HashMap_HIDDEN_EntrySet_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_EntrySet.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$EntrySet").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.HashMap$EntrySet").newInstance(null);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_HashMap_HIDDEN_ValueSpliterator_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_ValueSpliterator.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$ValueSpliterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_HashMap_HIDDEN_ValueSpliterator.Builder builder = java_util_HashMap_HIDDEN_ValueSpliterator.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_HashMap_HIDDEN_ValueSpliterator_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_ValueSpliterator.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$ValueSpliterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.HashMap$ValueSpliterator").newInstance(null, 0, 0, 0, 0);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class dev_resteasy_grpc_example___CC6_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC6.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(dev.resteasy.grpc.example.CC6.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         dev_resteasy_grpc_example___CC6.Builder builder = dev_resteasy_grpc_example___CC6.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class dev_resteasy_grpc_example___CC6_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC6.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(dev.resteasy.grpc.example.CC6.class, descriptor.findFieldByName(name)));
         }
      }

      public CC6 assignFromJavabuf(Message message) {
         CC6 obj = new CC6(0, null);
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util___HashSet_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util___HashSet.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(java.util.HashSet.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util___HashSet.Builder builder = java_util___HashSet.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util___HashSet_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util___HashSet.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(java.util.HashSet.class, descriptor.findFieldByName(name)));
         }
      }

      public HashSet assignFromJavabuf(Message message) {
         HashSet obj = new HashSet();
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_HashMap_HIDDEN_ValueIterator_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_ValueIterator.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$ValueIterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_HashMap_HIDDEN_ValueIterator.Builder builder = java_util_HashMap_HIDDEN_ValueIterator.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_HashMap_HIDDEN_ValueIterator_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_ValueIterator.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$ValueIterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.HashMap$ValueIterator").newInstance(null);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_AbstractMap_INNER_SimpleImmutableEntry_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_AbstractMap_INNER_SimpleImmutableEntry.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(java.util.AbstractMap.SimpleImmutableEntry.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_AbstractMap_INNER_SimpleImmutableEntry.Builder builder = java_util_AbstractMap_INNER_SimpleImmutableEntry.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_AbstractMap_INNER_SimpleImmutableEntry_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_AbstractMap_INNER_SimpleImmutableEntry.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(java.util.AbstractMap.SimpleImmutableEntry.class, descriptor.findFieldByName(name)));
         }
      }

      public SimpleImmutableEntry assignFromJavabuf(Message message) {
         SimpleImmutableEntry obj = new SimpleImmutableEntry(null);
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_HashMap_HIDDEN_KeySet_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_KeySet.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$KeySet").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_HashMap_HIDDEN_KeySet.Builder builder = java_util_HashMap_HIDDEN_KeySet.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_HashMap_HIDDEN_KeySet_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_KeySet.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$KeySet").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.HashMap$KeySet").newInstance(null);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_ArrayList_HIDDEN_ArrayListSpliterator_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_ArrayList_HIDDEN_ArrayListSpliterator.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.ArrayList$ArrayListSpliterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_ArrayList_HIDDEN_ArrayListSpliterator.Builder builder = java_util_ArrayList_HIDDEN_ArrayListSpliterator.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_ArrayList_HIDDEN_ArrayListSpliterator_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_ArrayList_HIDDEN_ArrayListSpliterator.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.ArrayList$ArrayListSpliterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.ArrayList$ArrayListSpliterator").newInstance(null, 0, 0, 0);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_ArrayList_HIDDEN_SubList_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_ArrayList_HIDDEN_SubList.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.ArrayList$SubList").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_ArrayList_HIDDEN_SubList.Builder builder = java_util_ArrayList_HIDDEN_SubList.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_ArrayList_HIDDEN_SubList_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_ArrayList_HIDDEN_SubList.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.ArrayList$SubList").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.ArrayList$SubList").newInstance(null, 0, 0);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_AbstractList_HIDDEN_ListItr_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_AbstractList_HIDDEN_ListItr.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.AbstractList$ListItr").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_AbstractList_HIDDEN_ListItr.Builder builder = java_util_AbstractList_HIDDEN_ListItr.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_AbstractList_HIDDEN_ListItr_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_AbstractList_HIDDEN_ListItr.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.AbstractList$ListItr").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.AbstractList$ListItr").newInstance(null, 0);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_ArrayList_HIDDEN_Itr_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_ArrayList_HIDDEN_Itr.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.ArrayList$Itr").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_ArrayList_HIDDEN_Itr.Builder builder = java_util_ArrayList_HIDDEN_Itr.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_ArrayList_HIDDEN_Itr_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_ArrayList_HIDDEN_Itr.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.ArrayList$Itr").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.ArrayList$Itr").newInstance(null);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_HashMap_HIDDEN_EntryIterator_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_EntryIterator.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$EntryIterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_HashMap_HIDDEN_EntryIterator.Builder builder = java_util_HashMap_HIDDEN_EntryIterator.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_HashMap_HIDDEN_EntryIterator_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_EntryIterator.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$EntryIterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.HashMap$EntryIterator").newInstance(null);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_HashMap_HIDDEN_EntrySpliterator_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_EntrySpliterator.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$EntrySpliterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_HashMap_HIDDEN_EntrySpliterator.Builder builder = java_util_HashMap_HIDDEN_EntrySpliterator.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_HashMap_HIDDEN_EntrySpliterator_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_EntrySpliterator.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$EntrySpliterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.HashMap$EntrySpliterator").newInstance(null, 0, 0, 0, 0);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class dev_resteasy_grpc_example_CC3_INNER_CC3_Sub_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example_CC3_INNER_CC3_Sub.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(dev.resteasy.grpc.example.CC3.CC3_Sub.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         dev_resteasy_grpc_example_CC3_INNER_CC3_Sub.Builder builder = dev_resteasy_grpc_example_CC3_INNER_CC3_Sub.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class dev_resteasy_grpc_example_CC3_INNER_CC3_Sub_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example_CC3_INNER_CC3_Sub.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(dev.resteasy.grpc.example.CC3.CC3_Sub.class, descriptor.findFieldByName(name)));
         }
      }

      public CC3_Sub assignFromJavabuf(Message message) {
         CC3_Sub obj = new CC3_Sub();
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_HashMap_HIDDEN_HashIterator_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_HashIterator.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$HashIterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_HashMap_HIDDEN_HashIterator.Builder builder = java_util_HashMap_HIDDEN_HashIterator.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class dev_resteasy_grpc_example_ArrayStuff_HIDDEN_Stuff_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example_ArrayStuff_HIDDEN_Stuff.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("dev.resteasy.grpc.example.ArrayStuff$Stuff").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         dev_resteasy_grpc_example_ArrayStuff_HIDDEN_Stuff.Builder builder = dev_resteasy_grpc_example_ArrayStuff_HIDDEN_Stuff.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class dev_resteasy_grpc_example_ArrayStuff_HIDDEN_Stuff_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example_ArrayStuff_HIDDEN_Stuff.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("dev.resteasy.grpc.example.ArrayStuff$Stuff").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("dev.resteasy.grpc.example.ArrayStuff$Stuff").newInstance();
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_AbstractList_HIDDEN_RandomAccessSpliterator_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_AbstractList_HIDDEN_RandomAccessSpliterator.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.AbstractList$RandomAccessSpliterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_AbstractList_HIDDEN_RandomAccessSpliterator.Builder builder = java_util_AbstractList_HIDDEN_RandomAccessSpliterator.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_AbstractList_HIDDEN_RandomAccessSpliterator_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_AbstractList_HIDDEN_RandomAccessSpliterator.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.AbstractList$RandomAccessSpliterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.AbstractList$RandomAccessSpliterator").newInstance(null);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_HashMap_HIDDEN_KeySpliterator_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_KeySpliterator.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$KeySpliterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_HashMap_HIDDEN_KeySpliterator.Builder builder = java_util_HashMap_HIDDEN_KeySpliterator.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_HashMap_HIDDEN_KeySpliterator_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_KeySpliterator.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$KeySpliterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.HashMap$KeySpliterator").newInstance(null, 0, 0, 0, 0);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_AbstractList_HIDDEN_RandomAccessSubList_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_AbstractList_HIDDEN_RandomAccessSubList.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.AbstractList$RandomAccessSubList").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_AbstractList_HIDDEN_RandomAccessSubList.Builder builder = java_util_AbstractList_HIDDEN_RandomAccessSubList.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_AbstractList_HIDDEN_RandomAccessSubList_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_AbstractList_HIDDEN_RandomAccessSubList.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.AbstractList$RandomAccessSubList").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.AbstractList$RandomAccessSubList").newInstance(null, 0, 0);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class dev_resteasy_grpc_example___CC4_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC4.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(dev.resteasy.grpc.example.CC4.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         dev_resteasy_grpc_example___CC4.Builder builder = dev_resteasy_grpc_example___CC4.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class dev_resteasy_grpc_example___CC4_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC4.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(dev.resteasy.grpc.example.CC4.class, descriptor.findFieldByName(name)));
         }
      }

      public CC4 assignFromJavabuf(Message message) {
         CC4 obj = new CC4(null, null);
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class dev_resteasy_grpc_example___CC9_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC9.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(dev.resteasy.grpc.example.CC9.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         dev_resteasy_grpc_example___CC9.Builder builder = dev_resteasy_grpc_example___CC9.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class dev_resteasy_grpc_example___CC9_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC9.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(dev.resteasy.grpc.example.CC9.class, descriptor.findFieldByName(name)));
         }
      }

      public CC9 assignFromJavabuf(Message message) {
         CC9 obj = new CC9(false, (byte)0, (short)0, 0, 0L, 0.0f, 0.0d, '\u0000', null);
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_AbstractList_HIDDEN_SubList_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_AbstractList_HIDDEN_SubList.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.AbstractList$SubList").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_AbstractList_HIDDEN_SubList.Builder builder = java_util_AbstractList_HIDDEN_SubList.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_AbstractList_HIDDEN_SubList_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_AbstractList_HIDDEN_SubList.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.AbstractList$SubList").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.AbstractList$SubList").newInstance(null, 0, 0);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class dev_resteasy_grpc_example___CC7_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC7.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(dev.resteasy.grpc.example.CC7.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         dev_resteasy_grpc_example___CC7.Builder builder = dev_resteasy_grpc_example___CC7.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class dev_resteasy_grpc_example___CC7_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC7.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(dev.resteasy.grpc.example.CC7.class, descriptor.findFieldByName(name)));
         }
      }

      public CC7 assignFromJavabuf(Message message) {
         CC7 obj = new CC7();
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_HashMap_HIDDEN_HashMapSpliterator_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_HashMapSpliterator.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$HashMapSpliterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_HashMap_HIDDEN_HashMapSpliterator.Builder builder = java_util_HashMap_HIDDEN_HashMapSpliterator.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_HashMap_HIDDEN_HashMapSpliterator_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_HashMapSpliterator.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$HashMapSpliterator").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.HashMap$HashMapSpliterator").newInstance(null, 0, 0, 0, 0);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_HashMap_HIDDEN_Node_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_Node.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$Node").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_HashMap_HIDDEN_Node.Builder builder = java_util_HashMap_HIDDEN_Node.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_HashMap_HIDDEN_Node_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_HashMap_HIDDEN_Node.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.HashMap$Node").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.HashMap$Node").newInstance(0, null, null, null);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util_AbstractList_HIDDEN_Itr_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_AbstractList_HIDDEN_Itr.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(getReturnNonPublicJavaClass("java.util.AbstractList$Itr").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util_AbstractList_HIDDEN_Itr.Builder builder = java_util_AbstractList_HIDDEN_Itr.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util_AbstractList_HIDDEN_Itr_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util_AbstractList_HIDDEN_Itr.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(getReturnNonPublicJavaClass("java.util.AbstractList$Itr").getJavaClass(), descriptor.findFieldByName(name)));
         }
      }

      public Object assignFromJavabuf(Message message) {
         try {
            Object obj = getConstructor("java.util.AbstractList$Itr").newInstance(null);
            for (AssignFromJavabuf assignFrom : assignList) {
               assignFrom.assign(message, obj);
            }
            return obj;
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util___HashMap_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util___HashMap.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(java.util.HashMap.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util___HashMap.Builder builder = java_util___HashMap.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util___HashMap_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util___HashMap.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(java.util.HashMap.class, descriptor.findFieldByName(name)));
         }
      }

      public HashMap assignFromJavabuf(Message message) {
         HashMap obj = new HashMap();
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class java_util___ArrayList_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util___ArrayList.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(java.util.ArrayList.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         java_util___ArrayList.Builder builder = java_util___ArrayList.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class java_util___ArrayList_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.java_util___ArrayList.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(java.util.ArrayList.class, descriptor.findFieldByName(name)));
         }
      }

      public ArrayList assignFromJavabuf(Message message) {
         ArrayList obj = new ArrayList();
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class dev_resteasy_grpc_example___ArrayStuff_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___ArrayStuff.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(dev.resteasy.grpc.example.ArrayStuff.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         dev_resteasy_grpc_example___ArrayStuff.Builder builder = dev_resteasy_grpc_example___ArrayStuff.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class dev_resteasy_grpc_example___ArrayStuff_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___ArrayStuff.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(dev.resteasy.grpc.example.ArrayStuff.class, descriptor.findFieldByName(name)));
         }
      }

      public ArrayStuff assignFromJavabuf(Message message) {
         ArrayStuff obj = new ArrayStuff();
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class dev_resteasy_grpc_example___IntfImpl_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___IntfImpl.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(dev.resteasy.grpc.example.IntfImpl.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         dev_resteasy_grpc_example___IntfImpl.Builder builder = dev_resteasy_grpc_example___IntfImpl.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class dev_resteasy_grpc_example___IntfImpl_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___IntfImpl.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(dev.resteasy.grpc.example.IntfImpl.class, descriptor.findFieldByName(name)));
         }
      }

      public IntfImpl assignFromJavabuf(Message message) {
         IntfImpl obj = new IntfImpl();
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class dev_resteasy_grpc_example_CC1_INNER_InnerClass_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example_CC1_INNER_InnerClass.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(dev.resteasy.grpc.example.CC1.InnerClass.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         dev_resteasy_grpc_example_CC1_INNER_InnerClass.Builder builder = dev_resteasy_grpc_example_CC1_INNER_InnerClass.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class dev_resteasy_grpc_example_CC1_INNER_InnerClass_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example_CC1_INNER_InnerClass.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(dev.resteasy.grpc.example.CC1.InnerClass.class, descriptor.findFieldByName(name)));
         }
      }

      public InnerClass assignFromJavabuf(Message message) {
         InnerClass obj = new InnerClass();
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class dev_resteasy_grpc_example___CC2_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC2.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(dev.resteasy.grpc.example.CC2.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         dev_resteasy_grpc_example___CC2.Builder builder = dev_resteasy_grpc_example___CC2.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class dev_resteasy_grpc_example___CC2_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC2.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(dev.resteasy.grpc.example.CC2.class, descriptor.findFieldByName(name)));
         }
      }

      public CC2 assignFromJavabuf(Message message) {
         CC2 obj = new CC2(null, 0);
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class dev_resteasy_grpc_bridge_runtime_sse___SseEvent_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(dev.resteasy.grpc.bridge.runtime.sse.SseEvent.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         dev_resteasy_grpc_bridge_runtime_sse___SseEvent.Builder builder = dev_resteasy_grpc_bridge_runtime_sse___SseEvent.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class dev_resteasy_grpc_bridge_runtime_sse___SseEvent_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(dev.resteasy.grpc.bridge.runtime.sse.SseEvent.class, descriptor.findFieldByName(name)));
         }
      }

      public SseEvent assignFromJavabuf(Message message) {
         SseEvent obj = new SseEvent();
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class dev_resteasy_grpc_example___CC5_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC5.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(dev.resteasy.grpc.example.CC5.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         dev_resteasy_grpc_example___CC5.Builder builder = dev_resteasy_grpc_example___CC5.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class dev_resteasy_grpc_example___CC5_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC5.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(dev.resteasy.grpc.example.CC5.class, descriptor.findFieldByName(name)));
         }
      }

      public CC5 assignFromJavabuf(Message message) {
         CC5 obj = new CC5();
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

   static class dev_resteasy_grpc_example___CC3_ToJavabuf implements TranslateToJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC3.getDescriptor();
      private static List<AssignToJavabuf> assignList = new ArrayList<AssignToJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(toJavabuf(dev.resteasy.grpc.example.CC3.class, descriptor.findFieldByName(name)));
         }
      }

      public Message assignToJavabuf(Object c1) {
         if (c1 == null) {
            return null;
         }
         dev_resteasy_grpc_example___CC3.Builder builder = dev_resteasy_grpc_example___CC3.newBuilder();
         for (AssignToJavabuf assignTo : assignList) {
            try {
               assignTo.assign(c1, builder);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return builder.build();
      }

      public void clear() {
      }
   }

   static class dev_resteasy_grpc_example___CC3_FromJavabuf implements TranslateFromJavabuf {
      private static Descriptor descriptor = dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC3.getDescriptor();
      private static List<AssignFromJavabuf> assignList = new ArrayList<AssignFromJavabuf>();

      static {
         for (FieldDescriptor f : descriptor.getFields()) {
            String name = f.getName();
            if (descriptor.findFieldByName(name) == null) {
               continue;
            }
            assignList.add(fromJavabuf(dev.resteasy.grpc.example.CC3.class, descriptor.findFieldByName(name)));
         }
      }

      public CC3 assignFromJavabuf(Message message) {
         CC3 obj = new CC3();
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
         return obj;
      }

      public void assignExistingFromJavabuf(Message message, Object obj) {
         for (AssignFromJavabuf assignFrom : assignList) {
            try {
               assignFrom.assign(message, obj);
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

}