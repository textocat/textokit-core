
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

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nlplab.brat.configuration.BratNoteType;

/**
 * @author Rinat Gareev
 */
public class BratNoteAnnotation extends BratAnnotation<BratNoteType> {

    private BratAnnotation<?> targetAnnotation;
    private String content;

    public BratNoteAnnotation(BratNoteType type, BratAnnotation<?> targetAnnotation, String content) {
        super(type);
        this.targetAnnotation = targetAnnotation;
        this.content = content;
    }

    public BratAnnotation<?> getTargetAnnotation() {
        return targetAnnotation;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", getId()).append("type", getType())
                .append("targetAnnotation", targetAnnotation).append("content", content)
                .toString();
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BratNoteAnnotation)) {
            return false;
        }
        BratNoteAnnotation that = (BratNoteAnnotation) obj;
        return Objects.equal(this.getId(), that.getId());
    }
}