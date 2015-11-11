/**
 *
 */
package ru.kfu.itis.cll.uima.cas;

import com.google.common.base.Objects;
import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.cas.FeatureStructure;

/**
 * @author Rinat Gareev
 */
class IdentityConstraint implements FSMatchConstraint {

    private static final long serialVersionUID = -2827846978417612056L;
    private FeatureStructure fs;

    private IdentityConstraint(FeatureStructure fs) {
        this.fs = fs;
    }

    public static IdentityConstraint of(FeatureStructure fs) {
        return new IdentityConstraint(fs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(FeatureStructure fs) {
        return Objects.equal(this.fs, fs);
    }

}