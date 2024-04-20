package org.jboss.resteasy.test.grpc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Any;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.Timestamp;

import dev.resteasy.grpc.arrays.ArrayUtility;
import dev.resteasy.grpc.arrays.Array_proto;
import dev.resteasy.grpc.arrays.Array_proto.dev_resteasy_grpc_arrays___ArrayHolder;
import dev.resteasy.grpc.bridge.runtime.Utility;
import dev.resteasy.grpc.bridge.runtime.protobuf.JavabufTranslator;
import dev.resteasy.grpc.example.ArrayStuff;
import dev.resteasy.grpc.example.CC1JavabufTranslator;
import dev.resteasy.grpc.example.CC1ServiceGrpc.CC1ServiceBlockingStub;
import dev.resteasy.grpc.example.CC1ServiceGrpc.CC1ServiceFutureStub;
import dev.resteasy.grpc.example.CC1ServiceGrpc.CC1ServiceStub;
import dev.resteasy.grpc.example.CC1_proto;
import dev.resteasy.grpc.example.CC1_proto.FormMap;
import dev.resteasy.grpc.example.CC1_proto.FormValues;
import dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage;
import dev.resteasy.grpc.example.CC1_proto.GeneralReturnMessage;
import dev.resteasy.grpc.example.CC1_proto.ServletInfo;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___ArrayStuff;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC2;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC3;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC4;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC5;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC7;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___CC9;
import dev.resteasy.grpc.example.CC1_proto.dev_resteasy_grpc_example___IntfImpl;
import dev.resteasy.grpc.example.CC1_proto.gCookie;
import dev.resteasy.grpc.example.CC1_proto.gHeader;
import dev.resteasy.grpc.example.CC1_proto.gInteger;
import dev.resteasy.grpc.example.CC1_proto.gNewCookie;
import dev.resteasy.grpc.example.CC1_proto.gString;
import dev.resteasy.grpc.example.CC1_proto.java_util___ArrayList;
import dev.resteasy.grpc.example.CC1_proto.java_util___HashMap;
import dev.resteasy.grpc.example.CC1_proto.java_util___HashSet;
import dev.resteasy.grpc.example.ExampleApp;
import dev.resteasy.grpc.example.sub.CC8;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

/**
 * @tpSubChapter gRPC bridge plus WildFly grpc subsystem
 * @tpChapter grpc-tests tests
 * @tpSince RESTEasy 1.0.0
 */
abstract class AbstractGrpcToJakartaRESTTest {

    private static JavabufTranslator translator;

    static {
        Class<?> clazz;
        try {
            clazz = Class.forName("dev.resteasy.grpc.example.CC1JavabufTranslator");
            translator = (JavabufTranslator) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Archive<?> doDeploy(final String deploymentName) throws Exception {
        final var resolver = Maven.resolver()
                .loadPomFromFile("pom.xml");
        Archive<?> ar = ShrinkWrap.create(WebArchive.class, deploymentName + ".war")
                .addPackage(AbstractGrpcToJakartaRESTTest.class.getPackage())
                .addPackage(ExampleApp.class.getPackage())
                .addPackage(CC8.class.getPackage())
                .addAsLibrary(resolver.resolve("dev.resteasy.grpc:grpc-bridge-runtime")
                        .withoutTransitivity()
                        .asSingleFile())
                .addClass(Array_proto.class)
                .addPackage(ArrayUtility.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("web.xml");
        ar.as(ZipExporter.class).exportTo(
                new File("/tmp/arrays.war"), true);
        return ar;
    }

    static void accessServletContexts() {
        try (
                Client client = ClientBuilder.newClient();
                var response = client.target("http://localhost:8080/grpc-test/grpcserver/context")
                        .request()
                        .get()) {
            final var message = response.getStatus() + ": " + response.readEntity(String.class);
            Assert.assertEquals(message, response.getStatus(), 200);
        }
    }

    /****************************************************************************************/
    /****************************************************************************************/
    void doBlockingTest(CC1ServiceBlockingStub stub) throws Exception {
        this.testBoolean(stub);
        this.testBooleanWithUnnecessaryURL(stub);
        this.testBooleanWrapper(stub);
        this.testByte(stub);
        this.testByteWrapper(stub);
        this.testChar(stub);
        this.testCharacter(stub);
        this.testCompletionStage(stub);
        this.testConstructor(stub);
        this.testConsumes(stub);
        this.testCookieParams(stub);
        this.testDouble(stub);
        this.testDoubleWrapper(stub);
        this.testFloat(stub);
        this.testFloatWrapper(stub);
        this.testHeaderParams(stub);
        this.testInheritance(stub);
        this.testInnerClass(stub);
        this.testInt(stub);
        this.testInteger(stub);
        this.testJaxrsResponse(stub);
        this.testLocatorGet(stub);
        this.testLocatorPost(stub);
        this.testLong(stub);
        this.testLongWrapper(stub);
        this.testMatrixParams(stub);
        this.testParamsList(stub);
        this.testParamsSet(stub);
        this.testParamsSortedSet(stub);
        this.testPathParams(stub);
        this.testProduces(stub);
        this.testQueryParams(stub);
        this.testReferenceField(stub);
        this.testResponse(stub);
        this.testServerCookies(stub);
        this.testServerHeaders(stub);
        this.testServletConfigServletName(stub);
        this.testServletContextInitParam(stub);
        this.testServletContextPath(stub);
        this.testServletInfo(stub);
        //this.testServletParams(stub);
        this.testServletPath(stub);
        this.testServletResponse(stub);
        this.testShort(stub);
        this.testShortWrapper(stub);
        this.testSSE(stub);
        this.testString(stub);
        this.testSuspend(stub);
        this.testCopy(stub);
        this.testInterfaceEntity(stub);
        this.testInterfaceReturn(stub);
        this.testArraysInts1(stub);
        this.testArraysInts1Translator(stub);
        this.testArraysInts2(stub);
        this.testArraysInts5(stub);
        this.testArrayStuff(stub);
        this.testArrayStuffArray1(stub);
        this.testArrayStuffArray2(stub);
        this.doCollectionTests(stub);
    }

    void doCollectionTests(CC1ServiceBlockingStub stub) throws Exception {
        this.testArrayList(stub);
        this.testHashsetInteger(stub);
        this.testHashSetArrayStuff(stub);
        this.testHashMapInteger(stub);
        this.testHashMapArrayStuff(stub);
    }

    void doAsyncTest(CC1ServiceStub asyncStub) throws Exception {
        testIntAsyncStub(asyncStub);
        testSseAsyncStub(asyncStub);
    }

    void doFutureTest(CC1ServiceFutureStub futureStub) throws Exception {
        testIntFutureStub(futureStub);
    }

    /****************************************************************************************/
    /****************************************************************************************/
    void testBoolean(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gBoolean n = dev.resteasy.grpc.example.CC1_proto.gBoolean.newBuilder()
                .setValue(false)
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setGBooleanField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getBoolean(gem);
            dev.resteasy.grpc.example.CC1_proto.gBoolean expected = dev.resteasy.grpc.example.CC1_proto.gBoolean.newBuilder()
                    .setValue(true).build();
            Assert.assertEquals(expected, response.getGBooleanField());
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testBooleanWithUnnecessaryURL(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gBoolean n = dev.resteasy.grpc.example.CC1_proto.gBoolean.newBuilder()
                .setValue(false)
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080/p/boolean").setGBooleanField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getBoolean(gem);
            dev.resteasy.grpc.example.CC1_proto.gBoolean expected = dev.resteasy.grpc.example.CC1_proto.gBoolean.newBuilder()
                    .setValue(true).build();
            Assert.assertEquals(expected, response.getGBooleanField());
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testBooleanWrapper(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gBoolean n = dev.resteasy.grpc.example.CC1_proto.gBoolean.newBuilder()
                .setValue(false)
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setGBooleanField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getBooleanWrapper(gem);
            dev.resteasy.grpc.example.CC1_proto.gBoolean expected = dev.resteasy.grpc.example.CC1_proto.gBoolean.newBuilder()
                    .setValue(true).build();
            Assert.assertEquals(expected, response.getGBooleanField());
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testByte(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gByte n = dev.resteasy.grpc.example.CC1_proto.gByte.newBuilder().setValue(3)
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setGByteField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getByte(gem);
            dev.resteasy.grpc.example.CC1_proto.gByte expected = dev.resteasy.grpc.example.CC1_proto.gByte.newBuilder()
                    .setValue(4)
                    .build();
            Assert.assertEquals(expected, response.getGByteField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testByteWrapper(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gByte n = dev.resteasy.grpc.example.CC1_proto.gByte.newBuilder().setValue(7)
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setGByteField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getByteWrapper(gem);
            dev.resteasy.grpc.example.CC1_proto.gByte expected = dev.resteasy.grpc.example.CC1_proto.gByte.newBuilder()
                    .setValue(8)
                    .build();
            Assert.assertEquals(expected, response.getGByteField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testShort(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gShort n = dev.resteasy.grpc.example.CC1_proto.gShort.newBuilder()
                .setValue(3)
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setGShortField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getShort(gem);
            dev.resteasy.grpc.example.CC1_proto.gShort expected = dev.resteasy.grpc.example.CC1_proto.gShort.newBuilder()
                    .setValue(4)
                    .build();
            Assert.assertEquals(expected, response.getGShortField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testShortWrapper(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gShort n = dev.resteasy.grpc.example.CC1_proto.gShort.newBuilder()
                .setValue(7)
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setGShortField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getShortWrapper(gem);
            dev.resteasy.grpc.example.CC1_proto.gShort expected = dev.resteasy.grpc.example.CC1_proto.gShort.newBuilder()
                    .setValue(8)
                    .build();
            Assert.assertEquals(expected, response.getGShortField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testInt(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gInteger n = dev.resteasy.grpc.example.CC1_proto.gInteger.newBuilder()
                .setValue(3)
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setGIntegerField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getInt(gem);
            dev.resteasy.grpc.example.CC1_proto.gInteger expected = dev.resteasy.grpc.example.CC1_proto.gInteger.newBuilder()
                    .setValue(4)
                    .build();
            Assert.assertEquals(expected, response.getGIntegerField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testInteger(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gInteger n = dev.resteasy.grpc.example.CC1_proto.gInteger.newBuilder()
                .setValue(3)
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setGIntegerField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getInteger(gem);
            dev.resteasy.grpc.example.CC1_proto.gInteger expected = dev.resteasy.grpc.example.CC1_proto.gInteger.newBuilder()
                    .setValue(4)
                    .build();
            Assert.assertEquals(expected, response.getGIntegerField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testLong(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gLong n = dev.resteasy.grpc.example.CC1_proto.gLong.newBuilder()
                .setValue(3L)
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/long").setGLongField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getLong(gem);
            dev.resteasy.grpc.example.CC1_proto.gLong expected = dev.resteasy.grpc.example.CC1_proto.gLong.newBuilder()
                    .setValue(4L)
                    .build();
            Assert.assertEquals(expected, response.getGLongField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testLongWrapper(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gLong n = dev.resteasy.grpc.example.CC1_proto.gLong.newBuilder()
                .setValue(3L)
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/Long").setGLongField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getLongWrapper(gem);
            dev.resteasy.grpc.example.CC1_proto.gLong expected = dev.resteasy.grpc.example.CC1_proto.gLong.newBuilder()
                    .setValue(4L)
                    .build();
            Assert.assertEquals(expected, response.getGLongField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testFloat(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gFloat n = dev.resteasy.grpc.example.CC1_proto.gFloat.newBuilder()
                .setValue(3.0f)
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/float").setGFloatField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getFloat(gem);
            dev.resteasy.grpc.example.CC1_proto.gFloat expected = dev.resteasy.grpc.example.CC1_proto.gFloat.newBuilder()
                    .setValue(4.0f)
                    .build();
            Assert.assertEquals(expected, response.getGFloatField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testFloatWrapper(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gFloat n = dev.resteasy.grpc.example.CC1_proto.gFloat.newBuilder()
                .setValue(3.0f)
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/Float").setGFloatField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getFloat(gem);
            dev.resteasy.grpc.example.CC1_proto.gFloat expected = dev.resteasy.grpc.example.CC1_proto.gFloat.newBuilder()
                    .setValue(4.0f)
                    .build();
            Assert.assertEquals(expected, response.getGFloatField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testDouble(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gDouble n = dev.resteasy.grpc.example.CC1_proto.gDouble.newBuilder()
                .setValue(3.0d)
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/double").setGDoubleField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getDouble(gem);
            dev.resteasy.grpc.example.CC1_proto.gDouble expected = dev.resteasy.grpc.example.CC1_proto.gDouble.newBuilder()
                    .setValue(4.0d)
                    .build();
            Assert.assertEquals(expected, response.getGDoubleField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testDoubleWrapper(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gDouble n = dev.resteasy.grpc.example.CC1_proto.gDouble.newBuilder()
                .setValue(3.0d)
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/Double").setGDoubleField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getDouble(gem);
            dev.resteasy.grpc.example.CC1_proto.gDouble expected = dev.resteasy.grpc.example.CC1_proto.gDouble.newBuilder()
                    .setValue(4.0d)
                    .build();
            Assert.assertEquals(expected, response.getGDoubleField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testChar(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gCharacter n = dev.resteasy.grpc.example.CC1_proto.gCharacter.newBuilder()
                .setValue("a")
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/char").setGCharacterField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getChar(gem);
            dev.resteasy.grpc.example.CC1_proto.gCharacter expected = dev.resteasy.grpc.example.CC1_proto.gCharacter
                    .newBuilder()
                    .setValue("A").build();
            Assert.assertEquals(expected, response.getGCharacterField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testCharacter(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gCharacter n = dev.resteasy.grpc.example.CC1_proto.gCharacter.newBuilder()
                .setValue("a")
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/Character")
                .setGCharacterField(n)
                .build();
        GeneralReturnMessage response;
        try {
            response = stub.getChar(gem);
            dev.resteasy.grpc.example.CC1_proto.gCharacter expected = dev.resteasy.grpc.example.CC1_proto.gCharacter
                    .newBuilder()
                    .setValue("A").build();
            Assert.assertEquals(expected, response.getGCharacterField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testString(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.gString n = dev.resteasy.grpc.example.CC1_proto.gString.newBuilder()
                .setValue("abc")
                .build();
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/string").setGStringField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.getString(gem);
            dev.resteasy.grpc.example.CC1_proto.gString expected = dev.resteasy.grpc.example.CC1_proto.gString.newBuilder()
                    .setValue("ABC").build();
            Assert.assertEquals(expected, response.getGStringField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testConstructor(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/constructor").build();
        GeneralReturnMessage response;
        try {
            response = stub.constructor(gem);
            dev_resteasy_grpc_example___CC3 cc3 = dev_resteasy_grpc_example___CC3.newBuilder().setS("eight").build();
            dev_resteasy_grpc_example___CC9.Builder cc9Builder = dev_resteasy_grpc_example___CC9.newBuilder();
            dev_resteasy_grpc_example___CC9 expected = cc9Builder.setBo(true)
                    .setBy((byte) 1)
                    .setS((short) 2)
                    .setI(3)
                    .setL(4L)
                    .setF(5.0f)
                    .setD(6.0d)
                    .setC("7")
                    .setCc3(cc3)
                    .build();
            Assert.assertEquals(expected, response.getDevResteasyGrpcExampleCC9Field());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testProduces(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080/p/produces").build();
        GeneralReturnMessage response;
        try {
            response = stub.produces(gem);
            dev.resteasy.grpc.example.CC1_proto.gString expected = dev.resteasy.grpc.example.CC1_proto.gString.newBuilder()
                    .setValue("produces").build();
            Assert.assertEquals(expected, response.getGStringField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testConsumes(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/consumes").build();
        GeneralReturnMessage response;
        try {
            response = stub.produces(gem);
            dev.resteasy.grpc.example.CC1_proto.gString expected = dev.resteasy.grpc.example.CC1_proto.gString.newBuilder()
                    .setValue("consumes").build();
            Assert.assertEquals(expected, response.getGStringField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testPathParams(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080/p/path/aa/param/bb").build();
        GeneralReturnMessage response;
        try {
            response = stub.pathParams(gem);
            dev.resteasy.grpc.example.CC1_proto.gString expected = dev.resteasy.grpc.example.CC1_proto.gString.newBuilder()
                    .setValue("xaaybbz").build();
            Assert.assertEquals(expected, response.getGStringField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testQueryParams(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080/p/query?q1=a&q2=b").build();
        GeneralReturnMessage response;
        try {
            response = stub.queryParams(gem);
            dev.resteasy.grpc.example.CC1_proto.gString expected = dev.resteasy.grpc.example.CC1_proto.gString.newBuilder()
                    .setValue("xaybz").build();
            Assert.assertEquals(expected, response.getGStringField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testMatrixParams(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080/p/matrix;m1=a;m2=b/more;m3=c").build();
        GeneralReturnMessage response;
        try {
            response = stub.matrixParams(gem);
            dev.resteasy.grpc.example.CC1_proto.gString expected = dev.resteasy.grpc.example.CC1_proto.gString.newBuilder()
                    .setValue("waxbycz").build();
            Assert.assertEquals(expected, response.getGStringField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    /**
     * Clarify treatment of cookies
     */
    void testCookieParams(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder messageBuilder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        messageBuilder.setURL("http://localhost:8080/p/cookieParams");
        gCookie.Builder cookieBuilder1 = gCookie.newBuilder();
        gCookie.Builder cookieBuilder2 = gCookie.newBuilder();
        gCookie cookie1 = cookieBuilder1.setName("c1").setValue("v1").setPath("a/b").setDomain("d1").build();
        gCookie cookie2 = cookieBuilder2.setName("c2").setValue("v2").build();
        messageBuilder.addCookies(cookie1).addCookies(cookie2);
        GeneralEntityMessage gem = messageBuilder.build();
        GeneralReturnMessage response;
        try {
            response = stub.cookieParams(gem);
            Assert.assertEquals("xc1=v1;d1,a/b,0yc2=v2;,,0z", response.getGStringField().getValue());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testHeaderParams(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder messageBuilder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        messageBuilder.setURL("http://localhost:8080" + "/p/headerParams");
        gHeader.Builder headerBuilder1 = gHeader.newBuilder();
        gHeader header1 = headerBuilder1.addValues("v1.1").addValues("v1.2").build();
        messageBuilder.putHeaders("h1", header1);
        gHeader.Builder headerBuilder2 = gHeader.newBuilder();
        gHeader header2 = headerBuilder2.addValues("v2").build();
        messageBuilder.putHeaders("h2", header2);
        GeneralEntityMessage gem = messageBuilder.build();
        GeneralReturnMessage response;
        try {
            response = stub.headerParams(gem);
            Assert.assertEquals("xv1.1yv2z", response.getGStringField().getValue());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testParamsList(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = GeneralEntityMessage.newBuilder();
        builder.putHeaders("h1", gHeader.newBuilder().addValues("hv1").addValues("hv2").build());
        GeneralEntityMessage gem = builder
                .setURL("http://localhost:8080" + "/p/params;m1=mv1;m1=mv2/pv1/list/pv2?q1=qv1&q1=qv2").build();
        GeneralReturnMessage response;
        try {
            response = stub.paramsList(gem);
            gString expected = gString.newBuilder().setValue("hv1hv2mv1mv2pv1pv2qv1qv2").build();
            Assert.assertEquals(expected, response.getGStringField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testParamsSet(CC1ServiceBlockingStub stub) throws Exception {
        GeneralEntityMessage.Builder builder = GeneralEntityMessage.newBuilder();
        builder.putHeaders("h1", gHeader.newBuilder().addValues("hv1").addValues("hv2").build());
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/params;m1=mv1;m1=mv2/pv1/set/pv2?q1=qv1&q1=qv2")
                .build();
        GeneralReturnMessage response;
        try {
            response = stub.paramsSet(gem);
            gString expected = gString.newBuilder().setValue("hv1hv2mv1mv2pv1pv2qv1qv2").build();
            Assert.assertEquals(expected, response.getGStringField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testParamsSortedSet(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        builder.putHeaders("h1", gHeader.newBuilder().addValues("hv1").addValues("hv2").build());
        GeneralEntityMessage gem = builder
                .setURL("http://localhost:8080" + "/p/params;m1=mv1;m1=mv2/pv1/sortedset/pv2?q1=qv1&q1=qv2").build();
        GeneralReturnMessage response;
        try {
            response = stub.paramsSortedSet(gem);
            dev.resteasy.grpc.example.CC1_proto.gString expected = dev.resteasy.grpc.example.CC1_proto.gString.newBuilder()
                    .setValue("hv1hv2mv1mv2pv1pv2qv1qv2").build();
            Assert.assertEquals(expected, response.getGStringField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testResponse(CC1ServiceBlockingStub stub) throws Exception {
        GeneralEntityMessage.Builder messageBuilder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = messageBuilder.build();
        try {
            GeneralReturnMessage response = stub.getResponse(gem);
            //                dev_resteasy_grpc_example___CC3 cc3 = dev_resteasy_grpc_example___CC3.newBuilder().setS("cc7").build();
            dev_resteasy_grpc_example___CC7 cc7 = dev_resteasy_grpc_example___CC7.newBuilder()
                    .setM(11)
                    .setS("cc7")
                    .build();
            Any any = response.getGoogleProtobufAnyField();
            //                        .setCC3Super(cc3)
            Assert.assertEquals(cc7, any.unpack(dev_resteasy_grpc_example___CC7.class));
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testSuspend(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder messageBuilder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        messageBuilder.setURL("http://localhost:8080/p/suspend");
        GeneralEntityMessage gem = messageBuilder.build();
        try {
            GeneralReturnMessage response = stub.suspend(gem);
            Any any = response.getGoogleProtobufAnyField();
            gString gS = any.unpack(gString.class);
            String s = gS.getValue();
            Assert.assertEquals("suspend", s);
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testCompletionStage(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder messageBuilder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        messageBuilder.setURL("http://localhost:8080/p/async/cs");
        GeneralEntityMessage gem = messageBuilder.build();
        try {
            GeneralReturnMessage response = stub.getResponseCompletionStage(gem);
            Assert.assertEquals("cs", response.getGStringField().getValue());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testServletContextPath(CC1ServiceBlockingStub stub) throws Exception {
        GeneralEntityMessage.Builder messageBuilder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = messageBuilder.build();
        GeneralReturnMessage response;
        try {
            response = stub.contextPath(gem);
            Assert.assertEquals("/grpc-test", response.getGStringField().getValue());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testServletContextInitParam(CC1ServiceBlockingStub stub) throws Exception {
        GeneralEntityMessage.Builder messageBuilder = GeneralEntityMessage.newBuilder();
        messageBuilder.setURL("http://localhost:8080/p/servletContext");
        GeneralEntityMessage gem = messageBuilder.build();
        GeneralReturnMessage response;
        try {
            response = stub.servletContext(gem);
            Assert.assertEquals("/grpcToJakartaRest", response.getGStringField().getValue());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testServletConfigServletName(CC1ServiceBlockingStub stub) throws Exception {
        GeneralEntityMessage.Builder messageBuilder = GeneralEntityMessage.newBuilder();
        messageBuilder.setURL("http://localhost:8080/p/servletConfig");
        GeneralEntityMessage gem = messageBuilder.build();
        GeneralReturnMessage response;
        try {
            response = stub.servletConfig(gem);
            Assert.assertEquals("GrpcServlet", response.getGStringField().getValue());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testSSE(CC1ServiceBlockingStub stub) throws Exception {
        CC1_proto.GeneralEntityMessage.Builder messageBuilder = CC1_proto.GeneralEntityMessage.newBuilder();
        messageBuilder.setURL("http://localhost:8080/p/sse");
        GeneralEntityMessage gem = messageBuilder.build();
        Iterator<CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent> response;
        try {
            response = stub.sse(gem);
        } catch (StatusRuntimeException e) {
            Assert.fail("fail");
            return;
        }
        ArrayList<CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent> list = new ArrayList<CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent>();
        while (response.hasNext()) {
            CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent sseEvent = response.next();
            list.add(sseEvent);
        }
        Assert.assertEquals(4, list.size());
        for (int k = 0; k < 3; k++) {
            CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent sseEvent = list.get(k);
            Assert.assertEquals("name" + (k + 1), sseEvent.getName());
            Any any = sseEvent.getData();
            gString gString = any.unpack(gString.class);
            Assert.assertEquals("event" + (k + 1), gString.getValue());
        }
        CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent sseEvent = list.get(3);
        Assert.assertEquals("name4", sseEvent.getName());
        Any any = sseEvent.getData();
        dev_resteasy_grpc_example___CC5 cc5 = any.unpack(dev_resteasy_grpc_example___CC5.class);
        Assert.assertEquals(dev_resteasy_grpc_example___CC5.newBuilder().setK(4).build(), cc5);
    }

    void testInheritance(CC1ServiceBlockingStub stub) throws Exception {
        dev_resteasy_grpc_example___CC2 cc2 = dev_resteasy_grpc_example___CC2.newBuilder()
                .setJ(17)
                .setS("thag")
                .build();
        GeneralEntityMessage.Builder messageBuilder = GeneralEntityMessage.newBuilder();
        messageBuilder.setURL("http://localhost:8080/p/inheritance").setDevResteasyGrpcExampleCC2Field(cc2);
        GeneralEntityMessage gem = messageBuilder.build();
        GeneralReturnMessage response;
        try {
            response = stub.inheritance(gem);
            cc2 = dev_resteasy_grpc_example___CC2.newBuilder().setJ(18).setS("xthagy").build();
            Assert.assertEquals(cc2, response.getDevResteasyGrpcExampleCC2Field());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testReferenceField(CC1ServiceBlockingStub stub) throws Exception {
        dev_resteasy_grpc_example___CC5 cc5 = dev_resteasy_grpc_example___CC5.newBuilder().setK(11).build();
        dev_resteasy_grpc_example___CC4 cc4 = dev_resteasy_grpc_example___CC4.newBuilder()
                .setS("grog")
                .setCc5(cc5)
                .build();
        GeneralEntityMessage.Builder messageBuilder = GeneralEntityMessage.newBuilder();
        messageBuilder.setURL("http://localhost:8080/p/reference").setDevResteasyGrpcExampleCC4Field(cc4);
        GeneralEntityMessage gem = messageBuilder.build();
        GeneralReturnMessage response;
        try {
            response = stub.referenceField(gem);
            cc5 = dev_resteasy_grpc_example___CC5.newBuilder().setK(12).build();
            cc4 = dev_resteasy_grpc_example___CC4.newBuilder().setS("xgrogy").setCc5(cc5).build();
            Assert.assertEquals(cc4, response.getDevResteasyGrpcExampleCC4Field());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testServletInfo(CC1ServiceBlockingStub stub) throws Exception {
        ServletInfo servletInfo = ServletInfo.newBuilder()
                .setCharacterEncoding("utf-16")
                .setClientAddress("1.2.3.4")
                .setClientHost("bluemonkey")
                .setClientPort(7777).build();
        gString gstring = gString.newBuilder().setValue("servletInfo").build();
        GeneralEntityMessage gem = GeneralEntityMessage.newBuilder()
                .setURL("http://localhost:8080/p/servletInfo")
                .setServletInfo(servletInfo)
                .setGStringField(gstring).build();
        try {
            GeneralReturnMessage response = stub.testServletInfo(gem);
            Assert.assertEquals("UTF-16|1.2.3.5|BLUEMONKEY|7778", response.getGStringField().getValue());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    /**
     * Clarify treatment of cookies
     */
    void testServerCookies(CC1ServiceBlockingStub stub) throws Exception {
        GeneralEntityMessage.Builder messageBuilder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = messageBuilder.build();
        GeneralReturnMessage response;
        try {
            response = stub.serverCookies(gem);
            List<gNewCookie> list = response.getCookiesList();
            Assert.assertEquals(2, list.size());
            gNewCookie c1 = gNewCookie.newBuilder()
                    .setDomain("d1")
                    .setMaxAge(-1)
                    .setName("n1")
                    .setPath("p1")
                    .setValue("v1")
                    .build();
            gNewCookie c2 = gNewCookie.newBuilder()
                    .setDomain("d2")
                    .setMaxAge(17)
                    .setName("n2")
                    .setPath("p2")
                    .setValue("v2")
                    .setHttpOnly(true)
                    .setSecure(true)
                    .build();
            if ("n1".equals(list.get(0).getName())) {
                Assert.assertEquals(c1, list.get(0));
                Assert.assertEquals(c2, list.get(1));
            } else {
                Assert.assertEquals(c1, list.get(1));
                Assert.assertEquals(c2, list.get(0));
            }
            Assert.assertEquals("cookies", response.getGStringField().getValue());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testServerHeaders(CC1ServiceBlockingStub stub) throws Exception {
        GeneralEntityMessage.Builder messageBuilder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = messageBuilder.build();
        GeneralReturnMessage response;
        try {
            response = stub.serverHeaders(gem);
            Map<String, CC1_proto.gHeader> headers = response.getHeadersMap();
            gHeader gh1 = gHeader.newBuilder().addValues("v1a").addValues("v1b").build();
            Assert.assertEquals(gh1, headers.get("h1"));
            gHeader gh2 = gHeader.newBuilder().addValues("v2").build();
            Assert.assertEquals(gh2, headers.get("h2"));
            Assert.assertEquals("headers", response.getGStringField().getValue());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testServletPath(CC1ServiceBlockingStub stub) throws Exception {
        String contextPath;
        {
            GeneralEntityMessage.Builder messageBuilder = GeneralEntityMessage.newBuilder();
            GeneralEntityMessage gem = messageBuilder.build();
            GeneralReturnMessage response;
            try {
                response = stub.servletPath(gem);
                String result = response.getGStringField().getValue();

                // get context path
                int i = result.indexOf('|');
                contextPath = result.substring(0, i);

                // servlet path
                int j = result.indexOf('|', i + 1);
                Assert.assertEquals("", result.substring(i + 1, j));

                // path
                i = j + 1;
                j = result.indexOf('|', i);
                Assert.assertEquals(result.substring(i, j), "/p/servletPath");

                // HttpServletRequest.getPathTranslated()
                Assert.assertTrue(result.substring(j + 1)
                        .contains(File.separator + "p" + File.separator + "servletPath"));
            } catch (StatusRuntimeException e) {
                Assert.fail("fail");
                return;
            }
        }
        {
            GeneralEntityMessage.Builder messageBuilder = GeneralEntityMessage.newBuilder();
            GeneralEntityMessage gem = messageBuilder
                    .setURL("http://localhost:8080" + contextPath + "/grpcToJakartaRest/p/servletPath").build();
            GeneralReturnMessage response;
            try {
                response = stub.servletPath(gem);
                String result = response.getGStringField().getValue();

                // context path
                int i = result.indexOf('|');
                Assert.assertEquals(contextPath, result.substring(0, i));

                // servlet path
                int j = result.indexOf('|', i + 1);
                Assert.assertEquals("/grpcToJakartaRest", result.substring(i + 1, j));

                // path
                i = j + 1;
                j = result.indexOf('|', i);
                Assert.assertEquals(result.substring(i, j), "/p/servletPath");

                // HttpServletRequest.getPathTranslated()
                Assert.assertTrue(result.substring(j + 1)
                        .contains(File.separator + "p" + File.separator + "servletPath"));
            } catch (StatusRuntimeException e) {
                Assert.fail("fail");
                return;
            }
        }
    }

    void testServletParams(CC1ServiceBlockingStub stub) throws Exception {
        Map<String, FormValues> formMap = new HashMap<String, FormValues>();
        FormValues.Builder formValuesBuilderP2 = FormValues.newBuilder();
        formValuesBuilderP2.addFormValuesField("f2a").addFormValuesField("f2b");
        formMap.put("p2", formValuesBuilderP2.build());

        FormValues.Builder formValuesBuilderP3 = FormValues.newBuilder();
        formValuesBuilderP3.addFormValuesField("f3a").addFormValuesField("f3b");
        formMap.put("p3", formValuesBuilderP3.build());

        FormMap.Builder formMapBuilder = FormMap.newBuilder();
        formMapBuilder.putAllFormMapField(formMap);
        GeneralEntityMessage.Builder messageBuilder = GeneralEntityMessage.newBuilder();
        messageBuilder.setFormField(formMapBuilder.build());

        messageBuilder.setURL("http://localhost:8080/p/servletParams?p1=q1&p2=q2");
        GeneralEntityMessage gem = messageBuilder.build();
        GeneralReturnMessage response;
        try {
            response = stub.servletParams(gem);
            String s = response.getGStringField().getValue();
            Assert.assertTrue(s.startsWith("q1|q2|f2a|f3a"));
            Assert.assertTrue(s.contains("p1->q1"));
            Assert.assertTrue(s.contains("p2->f2af2bq2"));
            Assert.assertTrue(s.contains("p3->f3af3b"));
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    /**
     * Clarify treatment of cookies
     */
    void testJaxrsResponse(CC1ServiceBlockingStub stub) throws Exception {
        GeneralEntityMessage.Builder messageBuilder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = messageBuilder.build();
        GeneralReturnMessage response;
        try {
            response = stub.jaxrsResponse(gem);
            Assert.assertEquals(2, response.getCookiesCount());
            gNewCookie expectedCookie1 = gNewCookie.newBuilder()
                    .setDomain("d1")
                    .setName("n1")
                    .setPath("p1")
                    .setValue("v1")
                    .setMaxAge(11)
                    .setExpiry(Timestamp.newBuilder().setSeconds(111))
                    .setHttpOnly(true)
                    .setVersion(1)
                    .build();
            gNewCookie expectedCookie2 = gNewCookie.newBuilder()
                    .setDomain("d2")
                    .setName("n2")
                    .setPath("p2")
                    .setValue("v2")
                    .setMaxAge(17)
                    .setExpiry(Timestamp.newBuilder().setSeconds(222))
                    .setSecure(true)
                    .setVersion(1)
                    .build();
            Assert.assertTrue(expectedCookie1.equals(response.getCookies(0)) && expectedCookie2.equals(response.getCookies(1))
                    || expectedCookie1.equals(response.getCookies(1)) && expectedCookie2.equals(response.getCookies(0)));
            Map<String, CC1_proto.gHeader> headers = response.getHeadersMap();
            Assert.assertEquals(1, headers.get("h1").getValuesCount());
            Assert.assertEquals("v1", headers.get("h1").getValues(0));
            Assert.assertEquals(222, response.getStatus().getValue());
            Assert.assertEquals(1, headers.get("Content-Type").getValuesCount());
            Assert.assertEquals("x/y", headers.get("Content-Type").getValues(0));
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testServletResponse(CC1ServiceBlockingStub stub) throws Exception {
        GeneralEntityMessage.Builder messageBuilder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = messageBuilder.build();
        GeneralReturnMessage response;
        try {
            response = stub.servletResponse(gem);
            Map<String, CC1_proto.gHeader> headers = response.getHeadersMap();

            Assert.assertEquals(1, headers.get("d1").getValuesCount());
            Assert.assertEquals(1, headers.get("h1").getValuesCount());
            Assert.assertEquals(1, headers.get("i1").getValuesCount());

            Assert.assertEquals(2, headers.get("d2").getValuesCount());
            Assert.assertEquals(2, headers.get("h2").getValuesCount());
            Assert.assertEquals(2, headers.get("i2").getValuesCount());

            Assert.assertEquals(1, headers.get("d3").getValuesCount());
            Assert.assertEquals(1, headers.get("h3").getValuesCount());
            Assert.assertEquals(1, headers.get("i3").getValuesCount());

            Assert.assertTrue(headers.get("d1").getValues(0).contains("02 Jan 1970"));
            Assert.assertEquals("v1", headers.get("h1").getValues(0));
            Assert.assertEquals("13", headers.get("i1").getValues(0));

            Assert.assertTrue(headers.get("d2").getValues(0).contains("03 Jan 1970"));
            Assert.assertTrue(headers.get("d2").getValues(1).contains("04 Jan 1970"));
            Assert.assertEquals("v2a", headers.get("h2").getValues(0));
            Assert.assertEquals("v2b", headers.get("h2").getValues(1));
            Assert.assertEquals("19", headers.get("i2").getValues(0));
            Assert.assertEquals("29", headers.get("i2").getValues(1));

            Assert.assertTrue(headers.get("d3").getValues(0).contains("06 Jan 1970"));
            Assert.assertEquals("v3b", headers.get("h3").getValues(0));
            Assert.assertEquals("41", headers.get("i3").getValues(0));

            Assert.assertEquals(1, response.getCookiesCount());
            gNewCookie expectedCookie = gNewCookie.newBuilder().setDomain("d1").setMaxAge(3).setName("n1").setPath("p1")
                    .setValue("v1").build();
            Assert.assertEquals(expectedCookie, response.getCookies(0));

            Assert.assertEquals(223, response.getStatus().getValue());
        } catch (StatusRuntimeException e) {
            Assert.fail("fail 2");
            return;
        }
    }

    void testInnerClass(CC1ServiceBlockingStub stub) throws Exception {
        GeneralEntityMessage.Builder messageBuilder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = messageBuilder.build();
        GeneralReturnMessage response;
        try {
            response = stub.inner(gem);
            CC1_proto.dev_resteasy_grpc_example_CC1_INNER_InnerClass.Builder builder = CC1_proto.dev_resteasy_grpc_example_CC1_INNER_InnerClass
                    .newBuilder();
            CC1_proto.dev_resteasy_grpc_example_CC1_INNER_InnerClass inner = builder.setI(3).setS("three").build();
            Assert.assertEquals(inner, response.getDevResteasyGrpcExampleCC1INNERInnerClassField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testLocatorGet(CC1ServiceBlockingStub stub) throws Exception {
        GeneralEntityMessage.Builder messageBuilder = GeneralEntityMessage.newBuilder();
        messageBuilder.setURL("/p/locator/get").setHttpMethod("GET");
        GeneralEntityMessage gem = messageBuilder.build();
        GeneralReturnMessage response;
        try {
            response = stub.locator(gem);
            Assert.assertEquals("/p/locator/get",
                    response.getGoogleProtobufAnyField().unpack(CC1_proto.gString.class).getValue());
        } catch (Exception e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testLocatorPost(CC1ServiceBlockingStub stub) throws Exception {
        GeneralEntityMessage.Builder messageBuilder = GeneralEntityMessage.newBuilder();
        messageBuilder.setURL("/p/locator/post/abc").setHttpMethod("POST");
        messageBuilder.setGoogleProtobufAnyField(Any.pack(gString.newBuilder().setValue("xyz").build()));
        GeneralEntityMessage gem = messageBuilder.build();
        GeneralReturnMessage response;
        try {
            response = stub.locator(gem);
            Assert.assertEquals("abc|xyz", response.getGoogleProtobufAnyField()
                    .unpack(CC1_proto.gString.class)
                    .getValue());
        } catch (Exception e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testCopy(CC1ServiceBlockingStub stub) throws Exception {
        CC1_proto.gString n = CC1_proto.gString.newBuilder().setValue("abc").build();
        CC1_proto.GeneralEntityMessage.Builder builder = CC1_proto.GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/copy").setGStringField(n).build();
        GeneralReturnMessage response;
        try {
            response = stub.copy(gem);
            CC1_proto.gString expected = dev.resteasy.grpc.example.CC1_proto.gString.newBuilder()
                    .setValue("xyz")
                    .build();
            Assert.assertEquals(expected, response.getGStringField());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testInterfaceEntity(CC1ServiceBlockingStub stub) throws Exception {
        CC1_proto.GeneralEntityMessage.Builder builder = CC1_proto.GeneralEntityMessage.newBuilder();
        CC1_proto.dev_resteasy_grpc_example___IntfImpl entity = CC1_proto.dev_resteasy_grpc_example___IntfImpl.newBuilder()
                .setS("abc").build();
        Any entityAny = Any.pack(entity);
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/interface/entity")
                .setGoogleProtobufAnyField(entityAny)
                .build();
        GeneralReturnMessage response;
        try {
            response = stub.intfEntity(gem);
            gString gs = response.getGStringField();
            Assert.assertEquals("abc", gs.getValue());
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testInterfaceReturn(CC1ServiceBlockingStub stub) throws Exception {
        CC1_proto.GeneralEntityMessage.Builder builder = CC1_proto.GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/interface/return").build();
        GeneralReturnMessage response;
        try {
            response = stub.intfReturn(gem);
            Any any = response.getGoogleProtobufAnyField();
            Class clazz = Utility.extractTypeFromAny(any, CC1_proto.class.getClassLoader(), "CC1_proto");
            dev_resteasy_grpc_example___IntfImpl impl = (dev_resteasy_grpc_example___IntfImpl) any.unpack(clazz);
            Assert.assertEquals("xyz", impl.getS());
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    static class GeneralReturnMessageHolder<T> {
        ArrayList<T> values = new ArrayList<T>();

        T getValue() {
            return values.get(0);
        }

        void setValue(T value) {
            values.add(value);
        }

        void addValue(T value) {
            values.add(value);
        }

        Iterator<T> iterator() {
            return values.iterator();
        }

        int size() {
            return values.size();
        }
    }

    void testIntAsyncStub(CC1ServiceStub asyncStub) throws Exception {
        gInteger n = gInteger.newBuilder().setValue(3).build();
        GeneralEntityMessage.Builder builder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = builder.setGIntegerField(n).build();
        CountDownLatch latch = new CountDownLatch(1);
        GeneralReturnMessageHolder<Integer> grmh = new GeneralReturnMessageHolder<Integer>();
        StreamObserver<GeneralReturnMessage> responseObserver = new StreamObserver<GeneralReturnMessage>() {
            @Override
            public void onNext(GeneralReturnMessage value) {
                grmh.setValue(value.getGIntegerField().getValue());
                latch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };
        try {
            asyncStub.getInt(gem, responseObserver);
            latch.await();
            Assert.assertEquals((Integer) 4, grmh.getValue());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testSseAsyncStub(CC1ServiceStub asyncStub) throws Exception {
        GeneralEntityMessage.Builder builder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = builder.build();
        CountDownLatch latch = new CountDownLatch(1);
        GeneralReturnMessageHolder<CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent> grmh = new GeneralReturnMessageHolder<CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent>();
        StreamObserver<CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent> responseObserver = new StreamObserver<CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent>() {

            @Override
            public void onNext(CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent value) {
                grmh.addValue(value);
            }

            @Override
            public void onError(Throwable t) {
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };
        try {
            asyncStub.sse(gem, responseObserver);
            latch.await();
            Assert.assertEquals(4, grmh.size());
            Iterator<CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent> it = grmh.iterator();
            for (int i = 0; i < 3; i++) {
                CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent sseEvent = it.next();
                Assert.assertEquals("name" + (i + 1), sseEvent.getName());
                byte[] bytes = sseEvent.getData().toByteArray();
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                Any any = Any.parseFrom(CodedInputStream.newInstance(bais));
                gString gString = any.unpack(gString.class);
                Assert.assertEquals("event" + (i + 1), gString.getValue());
            }
            CC1_proto.dev_resteasy_grpc_bridge_runtime_sse___SseEvent sseEvent = it.next();
            Assert.assertEquals("name4", sseEvent.getName());
            Any any = sseEvent.getData();
            dev_resteasy_grpc_example___CC5 cc5 = any.unpack(dev_resteasy_grpc_example___CC5.class);
            Assert.assertEquals(dev_resteasy_grpc_example___CC5.newBuilder().setK(4).build(), cc5);
        } catch (StatusRuntimeException e) {
            Assert.fail("fail");
            return;
        }
    }

    void testIntFutureStub(CC1ServiceFutureStub futureStub) throws Exception {
        gInteger n = gInteger.newBuilder().setValue(3).build();
        GeneralEntityMessage.Builder builder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = builder.setGIntegerField(n).build();
        try {
            ListenableFuture<GeneralReturnMessage> future = futureStub.getInt(gem);
            Assert.assertEquals(4, future.get().getGIntegerField().getValue());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    //////////////////////////////
    ///       array tests      ///
    //////////////////////////////
    void testArraysInts1(CC1ServiceBlockingStub stub) throws Exception {
        boolean b = translator.getUseSparseArrays();
        translator.setUseSparseArrays(false);
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        int[] ints = new int[] { 1, 2, 3 };
        dev_resteasy_grpc_arrays___ArrayHolder ah = ArrayUtility.getHolder(translator, ints);
        GeneralEntityMessage gem = builder.setDevResteasyGrpcArraysDevResteasyGrpcArraysArrayHolderField(ah).build();
        GeneralReturnMessage response;
        try {
            response = stub.arraysInt1(gem);
            dev_resteasy_grpc_arrays___ArrayHolder as = response
                    .getDevResteasyGrpcArraysDevResteasyGrpcArraysArrayHolderField();
            Object o = ArrayUtility.getArray(as);
            int[] expected = new int[] { 2, 3, 4 };
            Assert.assertArrayEquals(expected, (int[]) o);
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        } finally {
            translator.setUseSparseArrays(b);
        }
    }

    void testArraysInts1Translator(CC1ServiceBlockingStub stub) throws Exception {
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        int[] ints = new int[] { 1, 2, 3 };
        boolean b = translator.getUseSparseArrays();
        translator.setUseSparseArrays(false);
        dev_resteasy_grpc_arrays___ArrayHolder ah = (dev_resteasy_grpc_arrays___ArrayHolder) translator
                .translateToJavabuf(ints);
        GeneralEntityMessage gem = builder.setDevResteasyGrpcArraysDevResteasyGrpcArraysArrayHolderField(ah).build();
        GeneralReturnMessage response;
        try {
            response = stub.arraysInt1(gem);
            dev_resteasy_grpc_arrays___ArrayHolder as = response
                    .getDevResteasyGrpcArraysDevResteasyGrpcArraysArrayHolderField();
            Object o = translator.translateFromJavabuf(as);
            int[] expected = new int[] { 2, 3, 4 };
            Assert.assertArrayEquals(expected, (int[]) o);
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        } finally {
            translator.setUseSparseArrays(b);
        }
    }

    void testArraysInts2(CC1ServiceBlockingStub stub) throws Exception {
        boolean b = translator.getUseSparseArrays();
        translator.setUseSparseArrays(false);
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        int[][] intss = new int[][] { { 1, 2 }, { 3, 4 } };
        dev_resteasy_grpc_arrays___ArrayHolder ah = ArrayUtility.getHolder(translator, intss);
        GeneralEntityMessage gem = builder.setDevResteasyGrpcArraysDevResteasyGrpcArraysArrayHolderField(ah).build();
        GeneralReturnMessage response;
        try {
            response = stub.arraysInt2(gem);
            dev_resteasy_grpc_arrays___ArrayHolder as = response
                    .getDevResteasyGrpcArraysDevResteasyGrpcArraysArrayHolderField();
            Object o = translator.translateFromJavabuf(as);
            int[][] expected = new int[][] { { 2, 3 }, { 4, 5 } };
            Assert.assertArrayEquals(expected, (int[][]) o);
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        } finally {
            translator.setUseSparseArrays(b);
        }
    }

    void testArraysInts5(CC1ServiceBlockingStub stub) throws Exception {
        boolean b = translator.getUseSparseArrays();
        translator.setUseSparseArrays(false);
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        int[][][][][] intsssss = new int[][][][][] { { { { { 1, 2, 3 } } } }, { { { { 4, 5 } } } } };
        dev_resteasy_grpc_arrays___ArrayHolder ah = ArrayUtility.getHolder(translator, intsssss);
        GeneralEntityMessage gem = builder.setDevResteasyGrpcArraysDevResteasyGrpcArraysArrayHolderField(ah).build();
        GeneralReturnMessage response;
        try {
            response = stub.arraysInt5(gem);
            dev_resteasy_grpc_arrays___ArrayHolder as = response
                    .getDevResteasyGrpcArraysDevResteasyGrpcArraysArrayHolderField();
            Object o = translator.translateFromJavabuf(as);
            int[][][][][] expected = new int[][][][][] { { { { { 2, 3, 4 } } } }, { { { { 5, 6 } } } } };
            Assert.assertArrayEquals(expected, (int[][][][][]) o);
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        } finally {
            translator.setUseSparseArrays(b);
        }
    }

    void testArrayStuff(CC1ServiceBlockingStub stub) throws Exception {
        boolean b = translator.getUseSparseArrays();
        translator.setUseSparseArrays(false);
        dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage.Builder builder = dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage
                .newBuilder();
        ArrayStuff as = new ArrayStuff(false);
        dev_resteasy_grpc_example___ArrayStuff as1 = (dev_resteasy_grpc_example___ArrayStuff) translator
                .translateToJavabuf(as);
        GeneralEntityMessage gem = builder.setDevResteasyGrpcExampleArrayStuffField(as1).build();
        GeneralReturnMessage response;
        try {
            response = stub.arrayStuff(gem);
            dev_resteasy_grpc_example___ArrayStuff as2 = response.getDevResteasyGrpcExampleArrayStuffField();
            ArrayStuff expected = new ArrayStuff(true);
            Assert.assertEquals(expected, translator.translateFromJavabuf(as2));
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        } finally {
            translator.setUseSparseArrays(b);
        }
    }

    void testArrayStuffArray1(CC1ServiceBlockingStub stub) throws Exception {
        boolean b = translator.getUseSparseArrays();
        translator.setUseSparseArrays(false);
        CC1JavabufTranslator.INSTANCE.setUseSparseArrays(false);
        ArrayStuff[] ass = new ArrayStuff[] { new ArrayStuff(true), new ArrayStuff(false) };
        dev_resteasy_grpc_arrays___ArrayHolder holder = (dev_resteasy_grpc_arrays___ArrayHolder) translator
                .translateToJavabuf(ass);
        GeneralEntityMessage.Builder builder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = builder.setDevResteasyGrpcArraysDevResteasyGrpcArraysArrayHolderField(holder).build();
        GeneralReturnMessage response;
        try {
            response = stub.arrayStuffArray(gem);
            dev_resteasy_grpc_arrays___ArrayHolder holder2 = response
                    .getDevResteasyGrpcArraysDevResteasyGrpcArraysArrayHolderField();
            Assert.assertArrayEquals(ass, (ArrayStuff[]) translator.translateFromJavabuf(holder2));
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        } finally {
            translator.setUseSparseArrays(b);
        }
    }

    void testArrayStuffArray2(CC1ServiceBlockingStub stub) throws Exception {
        boolean b = translator.getUseSparseArrays();
        translator.setUseSparseArrays(false);
        CC1JavabufTranslator.INSTANCE.setUseSparseArrays(true);
        ArrayStuff[] ass = new ArrayStuff[] { new ArrayStuff(true), new ArrayStuff(false) };
        dev_resteasy_grpc_arrays___ArrayHolder holder = (dev_resteasy_grpc_arrays___ArrayHolder) translator
                .translateToJavabuf(ass);
        GeneralEntityMessage.Builder builder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = builder.setDevResteasyGrpcArraysDevResteasyGrpcArraysArrayHolderField(holder).build();
        GeneralReturnMessage response;
        try {
            response = stub.arrayStuffArray(gem);
            dev_resteasy_grpc_arrays___ArrayHolder holder2 = response
                    .getDevResteasyGrpcArraysDevResteasyGrpcArraysArrayHolderField();
            Assert.assertArrayEquals(ass, (ArrayStuff[]) translator.translateFromJavabuf(holder2));
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        } finally {
            translator.setUseSparseArrays(b);
        }
    }

    ////////////////////////////////
    ///     Collection tests     ///
    ////////////////////////////////
    void testArrayList(CC1ServiceBlockingStub stub) throws Exception {
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(Integer.valueOf(3));
        list.add(new ArrayStuff(false));
        list.add(Integer.valueOf(5));
        java_util___ArrayList jblist = (java_util___ArrayList) translator.translateToJavabuf(list);
        GeneralEntityMessage.Builder builder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = builder.setJavaUtilArrayListField(jblist).build();
        GeneralReturnMessage response;
        try {
            response = stub.listArray(gem);
            jblist = response.getJavaUtilArrayListField();
            Assert.assertEquals(list, (ArrayList) translator.translateFromJavabuf(jblist));
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testHashSetArrayStuff(CC1ServiceBlockingStub stub) throws Exception {
        HashSet<ArrayStuff> set = new HashSet<ArrayStuff>();
        set.add(new ArrayStuff(false));
        set.add(new ArrayStuff(true));
        java_util___HashSet jbset = (java_util___HashSet) translator.translateToJavabuf(set);
        GeneralEntityMessage.Builder builder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = builder.setJavaUtilHashSetField(jbset).build();
        GeneralReturnMessage response;
        try {
            response = stub.hashset(gem);
            jbset = response.getJavaUtilHashSetField();
            HashSet<ArrayStuff> set2 = (HashSet<ArrayStuff>) translator.translateFromJavabuf(jbset);
            Assert.assertEquals(2, set2.size());
            for (ArrayStuff as : set) {
                Assert.assertTrue(set2.contains(as));
            }
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testHashsetInteger(CC1ServiceBlockingStub stub) throws Exception {
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(Integer.valueOf(3));
        set.add(Integer.valueOf(5));
        java_util___HashSet jbset = (java_util___HashSet) translator.translateToJavabuf(set);
        GeneralEntityMessage.Builder builder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = builder.setJavaUtilHashSetField(jbset).build();
        GeneralReturnMessage response;
        try {
            response = stub.hashset(gem);
            jbset = response.getJavaUtilHashSetField();
            Assert.assertEquals(set, (HashSet) translator.translateFromJavabuf(jbset));
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testHashMapInteger(CC1ServiceBlockingStub stub) throws Exception {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        map.put(Integer.valueOf(3), "three");
        map.put(Integer.valueOf(5), "five");
        System.out.println("map: " + map);
        java_util___HashMap jbmap = (java_util___HashMap) translator.translateToJavabuf(map);
        GeneralEntityMessage.Builder builder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = builder.setJavaUtilHashMapField(jbmap).build();
        GeneralReturnMessage response;
        try {
            response = stub.hashmap(gem);
            jbmap = response.getJavaUtilHashMapField();
            Assert.assertEquals(map, (HashMap) translator.translateFromJavabuf(jbmap));
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    void testHashMapArrayStuff(CC1ServiceBlockingStub stub) throws Exception {
        HashMap<Integer, ArrayStuff> map = new HashMap<Integer, ArrayStuff>();
        map.put(Integer.valueOf(3), new ArrayStuff(false));
        map.put(Integer.valueOf(5), new ArrayStuff(true));
        java_util___HashMap jbmap = (java_util___HashMap) translator.translateToJavabuf(map);
        GeneralEntityMessage.Builder builder = GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = builder.setJavaUtilHashMapField(jbmap).build();
        GeneralReturnMessage response;
        try {
            response = stub.hashmap(gem);
            jbmap = response.getJavaUtilHashMapField();
            Assert.assertEquals(map, (HashMap) translator.translateFromJavabuf(jbmap));
        } catch (StatusRuntimeException e) {
            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    //////////////////////////////
    static String getJavaFieldName(String fieldName) {
        int pos = fieldName.lastIndexOf("___");
        if (pos >= 0) {
            return fieldName.substring(pos);
        }
        return fieldName;
    }
}
