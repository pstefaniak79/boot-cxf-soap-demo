package com.blog.demo.config;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.ws.addressing.soap.MAPCodec;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import java.util.*;

public class UUIDHeaderInterceptor extends AbstractPhaseInterceptor {


    private static final String REQUEST_ID_ATTRIBUTE_NAME = "pstefaniak";

    public UUIDHeaderInterceptor() {
        super(Phase.PRE_PROTOCOL);

    }

    @Override
    public void handleMessage(Message message) throws Fault {

        Map<String, Map<String, List<String>>> headersP = new HashMap<>();

        Map<String, List<String>> inHeaders = new HashMap<>();
        inHeaders.put("set-cookie", Arrays.asList(new String[]{"pass123"}));
        message.put(Message.PROTOCOL_HEADERS, inHeaders);

        List<Header> headersList = new ArrayList<Header>();

        Header testSoapHeader1 = null;
        Header testSoapHeader2 = null;
        try {
            testSoapHeader1 = new Header(new QName("uri:singz.ws.sample", "soapheader1"), "SOAP Header Message 1", new JAXBDataBinding(String.class));
            testSoapHeader2 = new Header(new QName("uri:singz.ws.sample", "soapheader2"), "SOAP Header Message 2", new JAXBDataBinding(String.class));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        headersList.add(testSoapHeader1);
        headersList.add(testSoapHeader2);

        message.put(Header.HEADER_LIST, headersList);


    }

    @Override
    public void handleFault(Message message) {
        handleMessage(message);
    }
}

