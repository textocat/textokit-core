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

import opennlp.model.AbstractModel;
import opennlp.tools.util.BaseToolFactory;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.model.BaseModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Rinat Gareev
 */
public class POSModel extends BaseModel {

    private static final String COMPONENT_NAME = "UIMA.Ext.PoSTagger.ME";

    public static final String POS_MODEL_ENTRY_NAME = "pos.model";
    public static final String MORPH_DICT_ENTRY_NAME = "morph.dict";

    public POSModel(String languageCode, AbstractModel posModel,
                    Map<String, String> manifestInfoEntries, POSTaggerFactory posFactory) {

        super(COMPONENT_NAME, languageCode, manifestInfoEntries, posFactory);

        if (posModel == null)
            throw new IllegalArgumentException("The maxentPosModel param must not be null!");

        artifactMap.put(POS_MODEL_ENTRY_NAME, posModel);
        checkArtifactMap();
    }

    public POSModel(InputStream in) throws IOException,
            InvalidFormatException {
        super(COMPONENT_NAME, in);
    }

    @Override
    protected Class<? extends BaseToolFactory> getDefaultFactory() {
        return POSTaggerFactory.class;
    }

    @Override
    protected void validateArtifactMap() throws InvalidFormatException {
        super.validateArtifactMap();

        if (!(artifactMap.get(POS_MODEL_ENTRY_NAME) instanceof AbstractModel)) {
            throw new InvalidFormatException("POS model is incomplete!");
        }
    }

    public AbstractModel getPosModel() {
        return (AbstractModel) artifactMap.get(POS_MODEL_ENTRY_NAME);
    }

    public POSTaggerFactory getFactory() {
        return (POSTaggerFactory) this.toolFactory;
    }
}
