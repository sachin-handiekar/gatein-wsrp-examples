package com.sachinhandiekar.examples.gatein.wsrp;

import org.gatein.pc.api.invocation.PortletInvocation;
import org.gatein.pc.api.invocation.RenderInvocation;
import org.gatein.pc.api.invocation.response.PortletInvocationResponse;
import org.gatein.wsrp.api.extensions.ConsumerExtensionAccessor;
import org.gatein.wsrp.api.extensions.ExtensionAccess;
import org.gatein.wsrp.api.extensions.InvocationHandlerDelegate;
import org.oasis.wsrp.v2.MarkupParams;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.logging.Logger;

/**
 * @author Sachin Handiekar
 */
public class CustomConsumerInvocationHandlerDelegate extends InvocationHandlerDelegate {

    private static final Logger logger = Logger.getLogger(CustomConsumerInvocationHandlerDelegate.class.getName());
    public static final String CUSTOMER_ID = "CustomerId";
    public static final String ORDER_ID = "OrderId";
    public static final String EXTENSION = "Extension";

    @Override
    public void processInvocation(PortletInvocation invocation) {

        if (invocation instanceof RenderInvocation) {

            logger.info("CustomConsumerInvocationHandlerDelegate invoked!!!!");

            // retrieve the session id from the portlet invocation
            final String id = invocation.getRequest().getSession().getId();
            logger.info("Session id: " + id);


            // Create an element named 'Extension'
            final Element markupParamsExtension = DOMUtils.createElement(EXTENSION);

            String customerId = invocation.getRequest().getParameter(CUSTOMER_ID);

            if (customerId != null) {
                // Create a child-element named 'CustomerId'
                createChildElement(markupParamsExtension, CUSTOMER_ID, customerId);
            }


            String orderId = invocation.getRequest().getParameter(ORDER_ID);

            if (orderId != null) {
                // Create a child-element named 'OrderId'
                createChildElement(markupParamsExtension, ORDER_ID, orderId);
            }

            // retrieve the ConsumerExtensionAccessor
            final ConsumerExtensionAccessor consumerExtensionAccessor = ExtensionAccess.getConsumerExtensionAccessor();

            // attach the newly created extension to the MarkupParams element for this particular invocation
            consumerExtensionAccessor.addRequestExtension(MarkupParams.class, markupParamsExtension);
        }
    }


    private void createChildElement(Element parentElement, String childElementName, String childElementValue) {
        final Node dummyData = DOMUtils.addChild(parentElement, childElementName);
        dummyData.setTextContent(childElementValue);
    }


    @Override
    public void processInvocationResponse(PortletInvocationResponse response, PortletInvocation invocation) {
        // do nothing
    }


}