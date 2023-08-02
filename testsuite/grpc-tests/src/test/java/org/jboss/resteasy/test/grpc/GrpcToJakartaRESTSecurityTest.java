package org.jboss.resteasy.test.grpc;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

import org.jboss.arquillian.container.test.api.ContainerController;
import org.jboss.arquillian.container.test.api.Deployer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import dev.resteasy.grpc.example.CC1ServiceGrpc;
import dev.resteasy.grpc.example.CC1ServiceGrpc.CC1ServiceBlockingStub;
import dev.resteasy.grpc.example.CC1_proto;
import dev.resteasy.grpc.example.CC1_proto.GeneralEntityMessage;
import dev.resteasy.grpc.example.CC1_proto.GeneralReturnMessage;
import dev.resteasy.grpc.example.ExampleApp;
import dev.resteasy.grpc.example.sub.CC8;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

/**
 * @tpSubChapter gRPC bridge plus WildFly grpc subsystem
 * @tpChapter grpc-tests tests
 * @tpSince RESTEasy 1.0.0
 */
@RunWith(Arquillian.class)
@RunAsClient
//@ServerSetup(GrpcToJakartaRESTOneWaySslTest.OneWaySslConfiguration.class)
public class GrpcToJakartaRESTSecurityTest {
    //   @Deployment(name = WAR_WITH_PROVIDERS_FILE, managed = false, testable = false)
    //   @TargetsContainer(GZIP_CONTAINER_QUALIFIER)
    //    @Deployment(name = "security", managed = false)
    //    @Deployment
    @Deployment(name = "security", managed = false)
    @TargetsContainer("security")
    public static Archive<?> deploy() throws Exception {
        final var resolver = Maven.resolver()
                .loadPomFromFile("pom.xml");
        return ShrinkWrap.create(WebArchive.class, GrpcToJakartaRESTSecurityTest.class.getSimpleName() + ".war")
                .addPackage(GrpcToJakartaRESTSecurityTest.class.getPackage())
                .addPackage(ExampleApp.class.getPackage())
                .addPackage(CC8.class.getPackage())
                .addAsLibrary(resolver.resolve("dev.resteasy.grpc:grpc-bridge-runtime")
                        .withoutTransitivity()
                        .asSingleFile())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("web.xml");
    }

    static void accessServletContexts() throws InterruptedException {
        System.out.println("context: calling");

        try (

                Client client = ClientBuilder.newClient();
                var response = client.target(
                        "http://localhost:9080/GrpcToJakartaRESTSecurityTest/grpcToJakartaRest/grpcserver/context")
                        //                        "http://localhost:8080/GrpcToJakartaRESTSecurityTest/grpcserver/context")
                        .request()
                        .get()) {
            //            Thread.sleep(11111111);
            System.out.println("context: called");

            final var message = response.getStatus() + ": " + response.readEntity(String.class);
            Assert.assertEquals(message, 200, response.getStatus());
            System.out.println("context: success");
        }
    }

    private static ManagedChannel channelPlaintext;

    private static CC1ServiceGrpc.CC1ServiceBlockingStub blockingStubPlaintext;

    private static CC1ServiceGrpc.CC1ServiceStub asyncStubPlaintext;

    private static CC1ServiceGrpc.CC1ServiceFutureStub futureStubPlaintext;

    @ArquillianResource
    protected static ContainerController containerController;

    @ArquillianResource
    protected static Deployer deployer;

    @BeforeClass
    public static void beforeClass() throws Exception {
        //        Thread.currentThread().sleep(1111111);
        //        System.out.println("containerController: " + containerController);
        //        System.out.println("deployer: " + deployer);
        //        System.out.println(containerController.isStarted("security"));
        //        if (!containerController.isStarted("security")) {
        //            containerController.start("security");
        //        }
        //
        //        deployer.deploy("security");
        //        System.out.println("calling accessServletContexts()");

        //        accessServletContexts();
        System.out.println("back from accessServletContexts()");
        //        Thread.currentThread().sleep(1111111);
        channelPlaintext = ManagedChannelBuilder.forTarget("localhost:10555").usePlaintext().build();

        blockingStubPlaintext = CC1ServiceGrpc.newBlockingStub(channelPlaintext);
        asyncStubPlaintext = CC1ServiceGrpc.newStub(channelPlaintext);
        futureStubPlaintext = CC1ServiceGrpc.newFutureStub(channelPlaintext);
    }

    @AfterClass
    public static void afterClass() throws InterruptedException {
        channelPlaintext.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }

    //    @Before
    //    public void startContainer() throws Exception {
    //       if (!containerController.isStarted(SSL_CONTAINER_QUALIFIER)) {
    //          containerController.start(SSL_CONTAINER_QUALIFIER);
    //          secureServer(SERVER_KEYSTORE_PATH, SSL_CONTAINER_PORT_OFFSET);
    //          deployer.deploy(DEPLOYMENT_NAME);
    //       }
    //    }

    @Before
    public void startContainerSecurity() throws InterruptedException {
        System.out.println("entering startContainerSecurity()");
        System.out.println(containerController.isStarted("security"));
        System.out.println("containerController: " + containerController);
        System.out.println("deployer: " + deployer);
        if (!containerController.isStarted("security")) {
            containerController.start("security");
        }
        System.out.println(containerController.isStarted("security"));

        deployer.deploy("security");

        System.out.println("deployed");
        accessServletContexts();
        System.out.println("back from accessServletContexts()");
        //        Thread.currentThread().sleep(1111111);
        channelPlaintext = ManagedChannelBuilder.forTarget("localhost:9555").usePlaintext().build();

        blockingStubPlaintext = CC1ServiceGrpc.newBlockingStub(channelPlaintext);
        asyncStubPlaintext = CC1ServiceGrpc.newStub(channelPlaintext);
        futureStubPlaintext = CC1ServiceGrpc.newFutureStub(channelPlaintext);
    }

    /****************************************************************************************/
    /****************************************************************************************/
    //    @Test
    public void testIsSecure(CC1ServiceBlockingStub stub) throws Exception {
        CC1_proto.GeneralEntityMessage.Builder builder = CC1_proto.GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/isSecure").build();
        GeneralReturnMessage response;
        try {
            response = stub.isSecure(gem);
            CC1_proto.gBoolean expected = dev.resteasy.grpc.example.CC1_proto.gBoolean.newBuilder()
                    .setValue(false)
                    .build();
            System.out.println("isSecure(): " + response.getGBooleanField());
            Assert.assertEquals(expected, response.getGBooleanField());
            System.out.println("context: success");
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }

    @Test
    @TargetsContainer("security")
    public void testIdentity() throws Exception {
        System.out.println("entering testIdentity()");
        //        Thread.currentThread().sleep(1111111);

        CC1_proto.GeneralEntityMessage.Builder builder = CC1_proto.GeneralEntityMessage.newBuilder();
        GeneralEntityMessage gem = builder.setURL("http://localhost:8080" + "/p/identity").build();
        GeneralReturnMessage response;
        try {
            response = blockingStubPlaintext.identity(gem);
            System.out.println("identity(): " + response.getGStringField());
            Assert.assertEquals("OK", response.getGStringField().getValue());
        } catch (StatusRuntimeException e) {

            try (StringWriter writer = new StringWriter()) {
                e.printStackTrace(new PrintWriter(writer));
                Assert.fail(writer.toString());
            }
        }
    }
}
