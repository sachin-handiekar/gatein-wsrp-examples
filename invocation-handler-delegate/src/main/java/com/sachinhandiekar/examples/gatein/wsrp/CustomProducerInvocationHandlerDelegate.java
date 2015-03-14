package com.sachinhandiekar.examples.gatein.wsrp;

import org.gatein.pc.api.invocation.PortletInvocation;
import org.gatein.pc.api.invocation.RenderInvocation;
import org.gatein.pc.api.invocation.response.PortletInvocationResponse;
import org.gatein.wsrp.api.extensions.ExtensionAccess;
import org.gatein.wsrp.api.extensions.InvocationHandlerDelegate;
import org.gatein.wsrp.api.extensions.UnmarshalledExtension;
import org.oasis.wsrp.v2.MarkupParams;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Sachin Handiekar
 */
public class CustomProducerInvocationHandlerDelegate extends InvocationHandlerDelegate {

    private static final Logger logger = Logger.getLogger(CustomProducerInvocationHandlerDelegate.class.getName());

    @Override
    public void processInvocation(PortletInvocation invocation) {
        if (invocation instanceof RenderInvocation) {

            final List<UnmarshalledExtension> requestExtensions = ExtensionAccess.getProducerExtensionAccessor().getRequestExtensionsFor(MarkupParams.class);

            if (!requestExtensions.isEmpty()) {

                final UnmarshalledExtension unmarshalledExtension = requestExtensions.get(0);

                if (unmarshalledExtension.isElement()) {

                    final Element element = (Element) unmarshalledExtension.getValue();

                    NodeList nodeList = element.getChildNodes();

                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Node node = nodeList.item(i);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {

                            String nodeKey = node.getNodeName();
                            String nodeValue = node.getTextContent();

                            logger.info("Node Key : " + nodeKey);
                            logger.info("Node  Value: " + nodeValue);

                            // Get the HttpServletRequest
                            HttpServletRequest httpServletRequest = invocation.getRequest();

                            // Set the attributes here
                            httpServletRequest.setAttribute(nodeKey, nodeValue);

                        }
                    }
                }
            }
        }
    }

    @Override
    public void processInvocationResponse(PortletInvocationResponse response, PortletInvocation invocation) {
        // Do nothing
    }
}