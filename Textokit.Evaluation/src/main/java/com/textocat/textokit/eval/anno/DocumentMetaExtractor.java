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

package com.textocat.textokit.eval.anno;

import com.textocat.textokit.commons.cas.AnnotationUtils;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * @author Rinat Gareev
 */
public class DocumentMetaExtractor {

    @Autowired
    private TypeSystem typeSystem;
    @Value("${document.meta.annotationType}")
    private String docUriAnnotationType;
    @Value("${document.meta.uriFeatureName}")
    private String docUriFeatureName;
    // derived
    private Type docMetaType;
    private Feature docUriFeature;

    @SuppressWarnings("unused")
    @PostConstruct
    private void init() {
        docMetaType = typeSystem.getType(docUriAnnotationType);
        if (docMetaType == null) {
            throw new IllegalStateException("Can't find annotation type '"
                    + docUriAnnotationType + "'");
        }
        docUriFeature = docMetaType.getFeatureByBaseName(docUriFeatureName);
        if (docUriFeature == null) {
            throw new IllegalStateException(String.format("No feature %s in type %s",
                    docUriFeatureName, docMetaType));
        }
    }

    public String getDocumentUri(CAS cas) {
        String uri = AnnotationUtils.getStringValue(cas, docMetaType, docUriFeature);
        if (uri == null) {
            throw new IllegalStateException("CAS doesn't have annotation of type " + docMetaType);
        }
        return uri;
    }

}