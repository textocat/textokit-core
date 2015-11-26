
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

package org.nlplab.brat.ann;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nlplab.brat.configuration.BratType;

/**
 * @author Rinat Gareev
 */
public abstract class BratTextBoundAnnotation<T extends BratType> extends BratAnnotation<T> {

    private int begin;
    private int end;
    private String spannedText;

    public BratTextBoundAnnotation(T type, int begin, int end, String spannedText) {
        super(type);
        this.begin = begin;
        this.end = end;
        this.spannedText = spannedText;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public String getSpannedText() {
        return spannedText;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", getId()).append("type", getType())
                .append("begin", getBegin()).append("end", getEnd())
                .append("spannedText", getSpannedText()).toString();
    }
}