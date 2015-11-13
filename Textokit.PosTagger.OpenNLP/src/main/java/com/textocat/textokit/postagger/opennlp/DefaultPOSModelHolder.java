/*
 *    Copyright 2015 Textocat
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
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
