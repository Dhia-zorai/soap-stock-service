package com.tp.soap.security;

import jakarta.xml.soap.*;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import jakarta.xml.ws.soap.SOAPFaultException;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import java.util.Set;

public class SecurityServerHandler implements SOAPHandler<SOAPMessageContext> {

        @Override
        public boolean handleMessage(SOAPMessageContext context) {
            // Check if the message is coming IN from the client
            Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

            if (isOutbound != null && !isOutbound) {
                try {
                    SOAPMessage msg = context.getMessage();
                    SOAPHeader header = msg.getSOAPHeader();

                    // If there's no header at all, block it
                    if (header == null) {
                        throw new Exception("No SOAP Header found!");
                    }

                    // Extract Username and Password tags from the header
                    NodeList usernameNodes = header.getElementsByTagName("Username");
                    NodeList passwordNodes = header.getElementsByTagName("Password");

                    if (usernameNodes.getLength() == 0 || passwordNodes.getLength() == 0) {
                        throw new Exception("Missing Username or Password in Header!");
                    }

                    String username = usernameNodes.item(0).getTextContent();
                    String password = passwordNodes.item(0).getTextContent();

                    // Validate the credentials
                    if (!"admin".equals(username) || !"secret123".equals(password)) {
                        throw new Exception("Bad credentials! Access denied.");
                    }

                } catch (Exception e) {
                    // If authentication fails, throw a SOAP Fault response back to the client
                    try {
                        SOAPFactory factory = SOAPFactory.newInstance();
                        SOAPFault fault = factory.createFault(e.getMessage(), new QName("http://schemas.xmlsoap.org/soap/envelope/", "Client"));
                        throw new SOAPFaultException(fault);
                    } catch (SOAPException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            return true; // Let the message proceed if valid
        }

        @Override
        public boolean handleFault(SOAPMessageContext context) {
            return true;
        }

        @Override
        public void close(MessageContext context) {
        }

        @Override
        public Set<QName> getHeaders() {
            return null;
        }
    }

