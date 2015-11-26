
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

package com.textocat.textokit.io.brat.test;

import com.textocat.textokit.io.brat.BratNoteMapper;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;

import static com.textocat.textokit.commons.util.AnnotatorUtils.annotationTypeExist;
import static com.textocat.textokit.commons.util.AnnotatorUtils.featureExist;

/**
 * @author Rinat Gareev
 */
public class CityNoteMapper implements BratNoteMapper {

    private static final String CITY_TYPE_NAME = "issst.test.HL_City";

    private Feature latFeat;
    private Feature longFeat;

    @Override
    public void typeSystemInit(TypeSystem ts) throws AnalysisEngineProcessException {
        Type cityType = ts.getType(CITY_TYPE_NAME);
        annotationTypeExist(CITY_TYPE_NAME, cityType);
        longFeat = featureExist(cityType, "longitude");
        latFeat = featureExist(cityType, "latitude");
    }

    @Override
    public String makeNote(AnnotationFS uAnno) {
        int latitude = uAnno.getIntValue(latFeat);
        int longitude = uAnno.getIntValue(longFeat);
        if (latitude == 0 || longitude == 0) {
            return null;
        }
        return String.format("%s:%s", latitude, longitude);
    }

    @Override
    public void parseNote(AnnotationFS uAnno, String noteContent) {
        if (noteContent == null) {
            return;
        }
        String[] coords = noteContent.split(":");
        int latitude = Integer.valueOf(coords[0]);
        int longitude = Integer.valueOf(coords[1]);
        uAnno.setIntValue(latFeat, latitude);
        uAnno.setIntValue(longFeat, longitude);
    }
}