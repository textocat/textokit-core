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

package com.textocat.textokit.eval.event;

import com.textocat.textokit.eval.measure.RecognitionMeasures;
import org.apache.commons.io.IOUtils;
import org.apache.uima.cas.Type;

import java.io.IOException;
import java.io.InputStream;

import static org.apache.commons.io.IOUtils.closeQuietly;

/**
 * @author Rinat Gareev
 */
class MeasuresReportUtils {

    static String getReportString(Type targetType, RecognitionMeasures m) {
        return String.format(REPORT_FMT,
                targetType == null ? "Overall" : targetType.getName(),
                m.getMatchedScore(),
                m.getMissedScore(),
                m.getSpuriousScore(),
                m.getPrecision() * 100,
                m.getRecall() * 100,
                m.getF1() * 100);
    }

    private static final String PATH_REPORT_FMT = "com/textocat/textokit/eval/event/strict-pr-listener-report.fmt";
    private static final String REPORT_FMT;

    static {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream fmtIS = cl.getResourceAsStream(PATH_REPORT_FMT);
        if (fmtIS == null) {
            throw new IllegalStateException("Can't load reporting format file");
        }
        try {
            REPORT_FMT = IOUtils.toString(fmtIS, "utf-8");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            closeQuietly(fmtIS);
        }
    }

    private MeasuresReportUtils() {
    }
}