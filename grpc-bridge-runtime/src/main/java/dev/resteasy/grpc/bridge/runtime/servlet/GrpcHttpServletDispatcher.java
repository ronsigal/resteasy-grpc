/*
 * JBoss, Home of Professional Open Source.
 *
 * Copyright 2023 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.resteasy.grpc.bridge.runtime.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;

@WebServlet(asyncSupported = true, value = "/RESTEASY_GrpcHttpServletDispatcher")
public class GrpcHttpServletDispatcher extends HttpServlet30Dispatcher {

    private static final long serialVersionUID = -7323100224345687064L;
    private static final Map<String, Servlet> servletMap = new HashMap<String, Servlet>();
    private static final Map<Servlet, ServletContext> servletContextMap = new HashMap<Servlet, ServletContext>();
    private static final Map<String, HttpServletRequest> requests = new HashMap<String, HttpServletRequest>();
    private static final Map<String, HttpServletResponse> responses = new HashMap<String, HttpServletResponse>();
    private String name;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        name = servletConfig.getServletName();
        addServlet(name, this, servletConfig.getServletContext());
        System.out.println(this + ": servlet: " + name);
        System.out.println("context: " + servletConfig.getServletContext().getContextPath());
    }

    @Override
    public void destroy() {
        super.destroy();
        removeServlet(name);
    }

    @Override
    public void service(String httpMethod, HttpServletRequest request, HttpServletResponse response) throws IOException {
        new Exception("GrpcHttpServletDispatcher").printStackTrace();
        if (requests.get(name) == null) {
            requests.put(name, request);
        }
        if (responses.get(name) == null) {
            responses.put(name, response);
        }
        System.out.println("GrpcHttpServletDispatcher request: " + request);
        System.out.println("GrpcHttpServletDispatcher name: " + name);
        //        boolean secure = request.isSecure();
        //        System.out.println("secure: " + secure);
        //        System.out.println("authType: " + request.getAuthType());
        super.service(httpMethod, request, response);
    }

    public static void addServlet(String name, Servlet servlet, ServletContext servletContext) {
        System.out.println("adding servlet: " + name + ": " + servlet);
        servletMap.put(name, servlet);
        servletContextMap.put(servlet, servletContext);
    }

    public static void removeServlet(String name) {
        servletContextMap.remove(servletMap.get(name));
        servletMap.remove(name);
    }

    public static Servlet getServlet(String name) {
        System.out.println("getServlet(): " + name + ": " + servletMap.get(name));
        return servletMap.get(name);
    }

    public static ServletContext getServletContext(String servletName) {
        Servlet servlet = servletMap.get(servletName);
        if (servlet == null) {
            return null;
        }
        return servletContextMap.get(servlet);
    }

    public static ServletContext getServletContext(Servlet servlet) {
        return servletContextMap.get(servlet);
    }

    public static HttpServletRequest getHttpServletRequest(String name) {
        return requests.get(name);
    }

    public static HttpServletResponse getHttpServletResponse(String name) {
        return responses.get(name);
    }
}
