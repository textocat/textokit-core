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

package com.textocat.textokit.morph.opencorpora.resource;

import com.textocat.textokit.morph.dictionary.resource.GramModel;
import com.textocat.textokit.morph.dictionary.resource.GramModelHolder;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

/**
 * @author Rinat Gareev
 */
public class GramModelResource implements GramModelHolder, SharedResourceObject {

    private GramModel gramModel;

    @Override
    public void load(DataResource dr) throws ResourceInitializationException {
        try {
            gramModel = GramModelDeserializer
                    .from(dr.getInputStream(), String.valueOf(dr.getUrl()));
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public GramModel getGramModel() {
        return gramModel;
    }

}
