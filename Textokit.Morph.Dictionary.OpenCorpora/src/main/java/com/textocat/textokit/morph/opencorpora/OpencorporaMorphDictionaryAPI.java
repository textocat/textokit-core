/**
 *
 */
package com.textocat.textokit.morph.opencorpora;

import com.textocat.textokit.commons.util.CachedResourceTuple;
import com.textocat.textokit.morph.dictionary.MorphDictionaryAPI;
import com.textocat.textokit.morph.dictionary.resource.GramModel;
import com.textocat.textokit.morph.dictionary.resource.MorphDictionary;
import com.textocat.textokit.morph.opencorpora.resource.*;
import org.apache.uima.UIMAFramework;
import org.apache.uima.fit.factory.ExternalResourceFactory;
import org.apache.uima.resource.ExternalResourceDescription;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Rinat Gareev
 */
public class OpencorporaMorphDictionaryAPI implements MorphDictionaryAPI {

    public static final String DEFAULT_SERIALIZED_DICT_RELATIVE_PATH = "dict.opcorpora.ser";
    public static final String DEFAULT_SERIALIZED_DICT_RELATIVE_URL =
            "file:" + DEFAULT_SERIALIZED_DICT_RELATIVE_PATH;

    @Override
    public ExternalResourceDescription getResourceDescriptionForCachedInstance() {
        return ExternalResourceFactory.createExternalResourceDescription(
                CachedSerializedDictionaryResource.class,
                DEFAULT_SERIALIZED_DICT_RELATIVE_URL);
    }

    @Override
    public ExternalResourceDescription getResourceDescriptionWithPredictorEnabled() {
        return ExternalResourceFactory.createExternalResourceDescription(
                ConfigurableSerializedDictionaryResource.class,
                DEFAULT_SERIALIZED_DICT_RELATIVE_URL,
                ConfigurableSerializedDictionaryResource.PARAM_PREDICTOR_CLASS_NAME,
                DummyWordformPredictor.class.getName());
    }

    @Override
    public ExternalResourceDescription getGramModelDescription() {
        return ExternalResourceFactory.createExternalResourceDescription(
                GramModelResource.class,
                DEFAULT_SERIALIZED_DICT_RELATIVE_URL);
    }

    @Override
    public CachedResourceTuple<MorphDictionary> getCachedInstance() throws Exception {
        URL serDictUrl = getSerializedDictionaryURL();
        CachedDictionaryDeserializer.GetDictionaryResult gdr = CachedDictionaryDeserializer.getInstance().getDictionary(
                serDictUrl, serDictUrl.openStream());
        return new CachedResourceTuple<MorphDictionary>(gdr.cacheKey, gdr.dictionary);
    }

    @Override
    public GramModel getGramModel() throws Exception {
        URL serDictUrl = getSerializedDictionaryURL();
        return GramModelDeserializer.from(serDictUrl.openStream(), serDictUrl.toString());
    }

    private URL getSerializedDictionaryURL() {
        URL serDictUrl;
        try {
            serDictUrl = UIMAFramework.newDefaultResourceManager()
                    .resolveRelativePath(DEFAULT_SERIALIZED_DICT_RELATIVE_PATH);
        } catch (MalformedURLException e) {
            // should never happen as the URL is hard-coded here
            throw new IllegalStateException(e);
        }
        if (serDictUrl == null) {
            throw new IllegalStateException(String.format("Can't find %s in UIMA datapath",
                    DEFAULT_SERIALIZED_DICT_RELATIVE_PATH));
        }
        return serDictUrl;
    }
}
