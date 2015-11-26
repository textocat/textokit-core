/**
 *
 */
package org.nlplab.brat.ann;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.nlplab.brat.configuration.BratType;

/**
 * @author Rinat Gareev
 */
public class BratStructureAnnotation<BT extends BratType> extends BratAnnotation<BT> {

    private Multimap<String, BratAnnotation<?>> roleAnnotations;

    public BratStructureAnnotation(BT type,
                                   Multimap<String, ? extends BratAnnotation<?>> roleAnnotations) {
        super(type);
        setRoleAnnotations(roleAnnotations);
    }

    public Multimap<String, BratAnnotation<?>> getRoleAnnotations() {
        return roleAnnotations;
    }

    protected void setRoleAnnotations(Multimap<String, ? extends BratAnnotation<?>> roleAnnotations) {
        this.roleAnnotations = ImmutableMultimap.copyOf(roleAnnotations);
    }
}