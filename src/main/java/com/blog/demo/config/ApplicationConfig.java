package com.blog.demo.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.common.ConfigurationConstants;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.blog.demo.service.AccountServiceEndpoint;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class ApplicationConfig {

    @Bean
    public ServletRegistrationBean<CXFServlet> dispatcherServlet() {
        return new ServletRegistrationBean<CXFServlet>(new CXFServlet(), "/soap-api/*");
    }

    @Bean
    @Primary
    public DispatcherServletPath dispatcherServletPathProvider() {
        return () -> "";
    }

    @Bean
    public WSS4JInInterceptor wss4JInInterceptor() {
        Map inProps = new HashMap<>();
        inProps.put(ConfigurationConstants.ACTION, ConfigurationConstants.USERNAME_TOKEN);
        inProps.put(ConfigurationConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
        inProps.put(ConfigurationConstants.PW_CALLBACK_CLASS, SimplePasswordCallback.class.getName());
        inProps.put(WSHandlerConstants.USER, "cxf");
        WSS4JInInterceptor wss4JInInterceptor = new WSS4JInInterceptor(inProps);
        return wss4JInInterceptor;
    }


    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus(LoggingFeature loggingFeature) {
        SpringBus cxfBus = new SpringBus();
        cxfBus.getFeatures().add(loggingFeature);
        cxfBus.getOutInterceptors().add(addSOAPHeaderOutInterceptor());
        cxfBus.getInInterceptors().add(wss4JInInterceptor());
//        KeyStoreFactoryBean keyStoreFactoryBean = new KeyStoreFactoryBean();
//        keyStoreFactoryBean.setLocation("d:/1/SOAPKeyStore.jks ");
//        keyStoreFactoryBean.setPassword("Dd7)3)4))");
//        try {
//            KeyStore keyStore = keyStoreFactoryBean.createKeyStore();
//
//
//
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        }
//        QName cookie = new QName("AbstractPhaseInterceptorookie", "Set-Cookie");
//
//
//
//        SOAPMessage sm = message.getContent(SOAPMessage.class);
//
//        try {
//            SOAPFactory sf = SOAPFactory.newInstance();
//            Name twoTermName = sf.createName("TwoTerms", "samp", "http://www.example.org");
//
//            message.getHeaders().add(new Header(cookie,"abc"))    ;
//        } catch (SOAPException e) {
//            throw new Fault(e);
//        }


        return cxfBus;
    }

    @Bean
    public UUIDHeaderInterceptor addSOAPHeaderOutInterceptor() {
        return new UUIDHeaderInterceptor();
    }

    @Bean
    public LoggingFeature loggingFeature() {
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        return loggingFeature;
    }


    @Bean
    public Endpoint endpoint(Bus bus, AccountServiceEndpoint accountServiceEndpoint) {
        EndpointImpl endpoint = new EndpointImpl(bus, accountServiceEndpoint);

        endpoint.publish("/service/accounts");
        return endpoint;
    }


}