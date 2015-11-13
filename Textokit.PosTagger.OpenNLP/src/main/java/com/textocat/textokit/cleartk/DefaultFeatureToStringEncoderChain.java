/**
 *
 */
package com.textocat.textokit.cleartk;

import org.cleartk.ml.encoder.features.FeatureEncoderChain;

/**
 * @author Rinat Gareev
 */
public class DefaultFeatureToStringEncoderChain extends FeatureEncoderChain<String> {

    private static final long serialVersionUID = 5915214180458567772L;

    public DefaultFeatureToStringEncoderChain() {
        addEncoder(new Boolean2StringFeatureEncoder());
        addEncoder(new String2StringFeatureEncoder());
    }

}
