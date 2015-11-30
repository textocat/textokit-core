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

package com.textocat.textokit.ruta.seeder;

import com.textocat.textokit.tokenizer.fstype.TokenBase;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.ruta.seed.RutaAnnotationSeeder;

/**
 * @author Rinat Gareev
 */
public class TextokitTokenizerSeeder implements RutaAnnotationSeeder {
    @Override
    public Type seed(String text, CAS cas) {
        String tokenBaseTypeName = TokenBase.class.getName();
        Type tokenBaseType = cas.getTypeSystem().getType(tokenBaseTypeName);
        if (tokenBaseType == null) {
            throw new IllegalStateException(String.format(
                    "Unknown type : %s", tokenBaseTypeName));
        }
        return tokenBaseType;
    }
}
