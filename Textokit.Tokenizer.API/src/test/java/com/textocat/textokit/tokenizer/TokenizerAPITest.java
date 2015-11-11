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
package com.textocat.textokit.tokenizer;

import com.google.common.collect.Sets;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Rinat Gareev
 */
public class TokenizerAPITest {

    @Test
    public void checkTypeSystemExistence() throws ResourceInitializationException {
        TypeSystemDescription tsd = TypeSystemDescriptionFactory.createTypeSystemDescription(
                TokenizerAPI.TYPESYSTEM_TOKENIZER);
        CAS cas = CasCreationUtils.createCas(tsd, null, null);
        Iterator<Type> typeIterator = cas.getTypeSystem().getTypeIterator();
        Set<String> shortNames = Sets.newHashSet();
        while (typeIterator.hasNext()) {
            shortNames.add(typeIterator.next().getShortName());
        }
        Assert.assertTrue("No Token type", shortNames.contains("Token"));
        Assert.assertTrue("No W type", shortNames.contains("W"));
    }

}
