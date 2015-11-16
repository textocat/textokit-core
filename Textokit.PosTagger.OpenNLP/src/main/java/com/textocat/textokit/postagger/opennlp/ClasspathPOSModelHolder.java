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

import com.textocat.textokit.commons.util.ManifestUtils;
import com.textocat.textokit.resource.ClasspathResourceBase;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.jar.Manifest;

import static com.textocat.textokit.postagger.opennlp.POSModelJarManifestBean.ME_VARIANT;

/**
 * @author Rinat Gareev
 */
public class ClasspathPOSModelHolder extends ClasspathResourceBase implements OpenNLPModelHolder<POSModel> {

    public static String getClassPath(String langCode, String modelVariant) {
        return String.format("com/textocat/textokit/postagger/opennlp/model/%s-%s.zip",
                langCode, modelVariant);
    }

    private POSModel model;

    @Override
    public boolean initialize(ResourceSpecifier aSpecifier, Map<String, Object> aAdditionalParams) throws ResourceInitializationException {
        if (!super.initialize(aSpecifier, aAdditionalParams)) {
            return false;
        }
        try (InputStream is = resource.getInputStream()) {
            model = new POSModel(is);
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
        return true;
    }

    @Override
    protected String locateDefaultResourceClassPath() {
        List<Manifest> candMans = ManifestUtils.searchByAttributeKey(ME_VARIANT);
        if (candMans.isEmpty()) {
            throw new IllegalStateException("Can't find POSModel in classpath");
        }
        if (candMans.size() > 1) {
            throw new UnsupportedOperationException("There are several POSModels in classpath");
        }
        Manifest man = candMans.get(0);
        POSModelJarManifestBean manBean = POSModelJarManifestBean.readFrom(man);
        return getClassPath(manBean.getLanguageCode(), manBean.getModelVariant());
    }

    @Override
    public POSModel getModel() {
        return model;
    }
}
