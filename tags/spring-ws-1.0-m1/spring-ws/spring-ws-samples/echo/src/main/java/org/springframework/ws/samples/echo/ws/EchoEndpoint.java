/*
 * Copyright (c) 2006, Your Corporation. All Rights Reserved.
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.ws.samples.echo.ws;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.springframework.util.Assert;
import org.springframework.ws.endpoint.AbstractDomPayloadEndpoint;
import org.springframework.ws.samples.echo.service.EchoService;

/**
 * Simple echoing Web service endpoint. Uses a <code>EchoService</code> to create a response string.
 *
 * @author Ingo Siebert
 * @author Arjen Poutsma
 */
public class EchoEndpoint extends AbstractDomPayloadEndpoint {

    /**
     * Namespace of both request and response.
     */
    public static final String NAMESPACE_URI = "http://www.springframework.org/spring-ws/samples/echo";

    /**
     * The local name of the expected request.
     */
    public static final String ECHO_REQUEST_LOCAL_NAME = "echoRequest";

    /**
     * The local name of the created response.
     */
    public static final String ECHO_RESPONSE_LOCAL_NAME = "echoResponse";

    private EchoService echoService;

    /**
     * Sets the "business service" to delegate to.
     */
    public void setEchoService(EchoService echoService) {
        this.echoService = echoService;
    }

    /**
     * Reads the given <code>requestElement</code>, and sends a the response back.
     *
     * @param requestElement the contents of the SOAP message as DOM elements
     * @param document       a DOM document to be used for constructing <code>Node</code>s
     * @return the response element
     */
    protected Element invokeInternal(Element requestElement, Document document) throws Exception {
        Assert.isTrue(NAMESPACE_URI.equals(requestElement.getNamespaceURI()), "Invalid namespace");
        Assert.isTrue(ECHO_REQUEST_LOCAL_NAME.equals(requestElement.getLocalName()), "Invalid local name");
        String echoText = requestElement.getTextContent();

        String echo = echoService.echo(echoText);
        Element responseElement = document.createElementNS(NAMESPACE_URI, ECHO_RESPONSE_LOCAL_NAME);
        responseElement.setTextContent(echo);
        return responseElement;
    }
}