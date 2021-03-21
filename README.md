# Spring Boot Apache CXF SOAP Service

[![Build Status](https://travis-ci.org/briansjavablog/boot-cxf-soap-demo.svg?branch=master)](https://travis-ci.org/briansjavablog/boot-cxf-soap-demo)


Spring Boot demo app uses Apache CXF to expose a simple SOAP endpoint.

Pull the source from github with git clone https://github.com/briansjavablog/boot-cxf-soap-demo.git. 
To run the integration test run mvn test. To run as executable JAR run java -jar target/boot-cxf-demo.jar




<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:acc="http://com/blog/demo/webservices/accountservice">
   <soapenv:Header>
      <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" soapenv:mustUnderstand="1">
         <wsse:UsernameToken wsu:Id="UsernameToken-1">
            <wsse:Username>cfx</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">wspassword</wsse:Password>
            <wsse:Nonce EncodingType="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary">8fh3c+hrfP7Xh4qE7UYeHQ==</wsse:Nonce>
            <wsu:Created>2021-03-19T12:40:00.777Z</wsu:Created>
         </wsse:UsernameToken>
      </wsse:Security>
   </soapenv:Header>
   <soapenv:Body>
      <acc:AccountDetailsRequest>
         <acc:accountNumber>1</acc:accountNumber>
      </acc:AccountDetailsRequest>
   </soapenv:Body>
</soapenv:Envelope>




Endpoint endpoint = client.getEndpoint();
Map<String, Object> outProps = new HashMap<String, Object>();
outProps.put("SecurityToken", MY-TOKEN);
endpoint.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));


private static final String XMLNS_WSU = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
private static final String XSD_WSSE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";

final List<Header> headers = new ArrayList<Header>();
final SOAPFactory sf = SOAPFactory.newInstance();
final SOAPElement securityElement = sf.createElement("Security", "wsse", XSD_WSSE);
final SOAPElement authElement = sf.createElement("BinarySecurityToken", "wsse", XSD_WSSE);
authElement.setAttribute("ValueType", "WASP");
authElement.setAttribute("EncodingType", "wsse:Base64Binary");
authElement.setAttribute("wsu:Id", "SecurityToken");
authElement.addAttribute(new QName("xmlns:wsu"), XMLNS_WSU);
authElement.addTextNode(StringUtils.replace(SessionToken.getEncodedSessionToken(), "\n", ""));
securityElement.addChildElement(authElement);
final SoapHeader securityHeader = new SoapHeader(
        new QName(null, "Security"), securityElement);
headers.add(securityHeader);
((BindingProvider) interactiveService).getRequestContext().put(Header.HEADER_LIST, headers);