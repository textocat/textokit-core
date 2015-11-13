/**
 *
 */
package com.textocat.textokit.postagger.opennlp;

import org.apache.commons.io.IOUtils;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Rinat Gareev
 */
public class DefaultPOSModelHolder implements OpenNLPModelHolder<POSModel>,
        SharedResourceObject {

    // state
    private POSModel model;

    @Override
    public void load(DataResource dr) throws ResourceInitializationException {
        if (model != null) {
            throw new IllegalStateException();
        }
        InputStream is = null;
        try {
            is = dr.getInputStream();
            model = new POSModel(is);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    @Override
    public POSModel getModel() {
        return model;
    }
}
