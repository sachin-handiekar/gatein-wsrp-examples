package com.sachinhandiekar.examples.gatein.wsrp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Sachin Handiekar
 */
public class DOMUtils {
    static Element createElement(String namespaceURI, String name) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        try {
            final DocumentBuilder builder = builderFactory.newDocumentBuilder();
            final Document document = builder.newDocument();
            return document.createElementNS(namespaceURI, name);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Couldn't get a DocumentBuilder", e);
        }
    }


    static Element createElement(String name) {

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        try {
            final DocumentBuilder builder = builderFactory.newDocumentBuilder();
            final Document document = builder.newDocument();

            return document.createElement(name);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Couldn't get a DocumentBuilder", e);
        }
    }

    static Node addChild(Node parent, String namespaceURI, String childName) {
        final Element child = parent.getOwnerDocument().createElementNS(namespaceURI, childName);
        return parent.appendChild(child);
    }

    static Node addChild(Node parent, String childName) {
        final Element child = parent.getOwnerDocument().createElement(childName);
        return parent.appendChild(child);
    }
}