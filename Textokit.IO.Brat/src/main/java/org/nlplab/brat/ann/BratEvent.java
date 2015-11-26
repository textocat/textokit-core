
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

import com.google.common.collect.Multimap;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nlplab.brat.configuration.BratEventType;

/**
 * @author Rinat Gareev
 */
public class BratEvent extends BratStructureAnnotation<BratEventType> {

    private BratEventTrigger trigger;

    public BratEvent(BratEventType type, BratEventTrigger trigger,
                     Multimap<String, ? extends BratAnnotation<?>> roleAnnotations) {
        super(type, roleAnnotations);
        this.trigger = trigger;
    }

    public BratEventTrigger getTrigger() {
        return trigger;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", getId()).append("type", getType())
                .append("trigger", trigger).append("roleAnnotation", getRoleAnnotations())
                .toString();
    }
}