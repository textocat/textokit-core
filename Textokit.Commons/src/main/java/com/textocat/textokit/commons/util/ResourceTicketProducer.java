package com.textocat.textokit.commons.util;

/**
 * @author Rinat Gareev
 */
public interface ResourceTicketProducer {
    /**
     * Notify this resource about a new client.
     */
    ResourceTicket acquire();
}
