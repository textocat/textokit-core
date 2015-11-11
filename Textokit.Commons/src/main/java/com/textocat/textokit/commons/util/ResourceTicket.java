package com.textocat.textokit.commons.util;

import java.io.Closeable;

/**
 * @author Rinat Gareev
 */
public interface ResourceTicket extends Closeable {
    @Override
    void close();
}
