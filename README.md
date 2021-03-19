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