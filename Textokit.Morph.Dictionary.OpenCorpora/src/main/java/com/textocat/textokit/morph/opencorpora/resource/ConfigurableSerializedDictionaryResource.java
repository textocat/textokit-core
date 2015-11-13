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
import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionaryHolder;
import org.apache.uima.fit.component.initialize.ConfigurationParameterInitializer;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

/**
 * @author Rinat Gareev
 */
public class ConfigurableSerializedDictionaryResource implements MorphDictionaryHolder,
        GramModelHolder, SharedResourceObject {

    public static final String PARAM_PREDICTOR_CLASS_NAME = "predictorClassName";
    private static final Logger log = LoggerFactory
            .getLogger(ConfigurableSerializedDictionaryResource.class);

    // config fields
    @ConfigurationParameter(name = PARAM_PREDICTOR_CLASS_NAME, mandatory = false)
    private String wfPredictorClassName;
    // state fields
    private MorphDictionary dict;

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(DataResource dr) throws ResourceInitializationException {
        ConfigurationParameterInitializer.initialize(this, dr);
        try {
            dict = DictionaryDeserializer.from(dr.getInputStream(), String.valueOf(dr.getUrl()));
            if (wfPredictorClassName != null) {
                @SuppressWarnings("unchecked")
                Class<? extends WordformPredictor> wfPredictorClass = (Class<? extends WordformPredictor>)
                        Class.forName(wfPredictorClassName);
                WordformPredictor wfPredictor = makePredictor(wfPredictorClass);
                ((MorphDictionaryImpl) dict).setWfPredictor(wfPredictor);
                log.info("{} was set in deserialized MorphDictionary",
                        wfPredictor.getClass().getSimpleName());
            } else {
                log.info("A wordform predictor has not been set in deserialized MorphDictionary");
            }
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public MorphDictionary getDictionary() {
        return dict;
    }

    @Override
    public GramModel getGramModel() {
        return dict.getGramModel();
    }

    private WordformPredictor makePredictor(Class<? extends WordformPredictor> predictorClass)
            throws Exception {
        Constructor<? extends WordformPredictor> constr = null;
        // check constructor with the single parameter typed by MorphDictionary
        try {
            constr = predictorClass.getConstructor(MorphDictionary.class);
        } catch (NoSuchMethodException e) {
        }
        if (constr != null) {
            return constr.newInstance(dict);
        } else {
            constr = predictorClass.getConstructor();
            if (constr != null) {
                return constr.newInstance();
            }
        }
        throw new IllegalStateException(String.format("%s does not have appropriate constructors",
                predictorClass.getName()));
    }
}