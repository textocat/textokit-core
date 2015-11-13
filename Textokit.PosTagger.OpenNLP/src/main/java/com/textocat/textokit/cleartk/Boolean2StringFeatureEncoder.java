/**
 *
 */
package com.textocat.textokit.cleartk;

import com.google.common.collect.ImmutableList;
import org.cleartk.ml.Feature;
import org.cleartk.ml.encoder.CleartkEncoderException;
import org.cleartk.ml.encoder.features.FeatureEncoder;

import java.util.List;

/**
 * @author Rinat Gareev
 */
public class Boolean2StringFeatureEncoder implements FeatureEncoder<String> {

    private static final long serialVersionUID = -2489930447901347937L;

    @Override
    public List<String> encode(Feature feature) throws CleartkEncoderException {
        if ((Boolean) feature.getValue()) {
            return ImmutableList.of(feature.getName());
        } else {
            return ImmutableList.of();
        }
    }

    @Override
    public boolean encodes(Feature feature) {
        return feature.getValue() instanceof Boolean;
    }

}
