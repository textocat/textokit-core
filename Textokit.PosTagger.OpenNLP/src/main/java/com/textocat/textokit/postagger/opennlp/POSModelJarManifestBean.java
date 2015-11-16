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

import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * @author Rinat Gareev
 */
public class POSModelJarManifestBean {

    // ME ~ Manifest Entry
    public static final String ME_LANGUAGE = "Textokit-OpenNLP-MaxEnt-PoS-Language";
    public static final String ME_VARIANT = "Textokit-OpenNLP-MaxEnt-PoS-Variant";

    public static POSModelJarManifestBean readFrom(Manifest m) {
        String lang = m.getMainAttributes().getValue(ME_LANGUAGE);
        String modelVariant = m.getMainAttributes().getValue(ME_VARIANT);
        return new POSModelJarManifestBean(lang, modelVariant);
    }

    private String languageCode;
    private String modelVariant;

    public POSModelJarManifestBean(String languageCode, String modelVariant) {
        this.languageCode = languageCode;
        this.modelVariant = modelVariant;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getModelVariant() {
        return modelVariant;
    }

    public Manifest toManifest() {
        Manifest res = new Manifest();
        Attributes attrs = res.getMainAttributes();
        attrs.putValue(Attributes.Name.MANIFEST_VERSION.toString(), "1.0");
        attrs.putValue(ME_LANGUAGE, languageCode);
        attrs.putValue(ME_VARIANT, modelVariant);
        return res;
    }
}
