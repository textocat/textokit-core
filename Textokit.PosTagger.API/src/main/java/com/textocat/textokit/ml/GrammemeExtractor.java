/**
 *
 */
package com.textocat.textokit.ml;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.textocat.textokit.commons.cas.FSUtils;
import com.textocat.textokit.morph.dictionary.resource.GramModel;
import com.textocat.textokit.morph.fs.Word;
import com.textocat.textokit.morph.fs.Wordform;
import org.apache.uima.fit.util.FSCollectionFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.cleartk.ml.Feature;
import org.cleartk.ml.feature.extractor.CleartkExtractorException;
import org.cleartk.ml.feature.extractor.FeatureExtractor1;

import java.util.BitSet;
import java.util.Collection;
import java.util.List;

import static com.textocat.textokit.morph.dictionary.resource.MorphDictionaryUtils.toGramBits;

/**
 * @author Rinat Gareev
 */
public class GrammemeExtractor implements FeatureExtractor1 {

    public static final String FEATURE_NAME_PREFIX = "Gram";

    @SuppressWarnings("unused")
    private String gramCat;
    private GramModel gramModel;
    // TODO make filterBS immutable
    private BitSet filterBS;
    private String featureName;

    public GrammemeExtractor(GramModel gramModel, String gramCat) {
        this.gramModel = gramModel;
        this.gramCat = gramCat;
        filterBS = gramModel.getGrammemWithChildrenBits(gramCat, true);
        featureName = FEATURE_NAME_PREFIX + "_" + gramCat;
    }

    @Override
    public List<Feature> extract(JCas view, Annotation focusAnnotation)
            throws CleartkExtractorException {
        Word wordAnno = PUtils.getWordAnno(view, focusAnnotation);
        if (wordAnno == null || wordAnno.getWordforms() == null) {
            return ImmutableList.of();
        }
        Collection<Wordform> wfs = FSCollectionFactory.create(
                wordAnno.getWordforms(), Wordform.class);
        if (wfs.isEmpty()) {
            return ImmutableList.of();
        }
        Wordform wf = wfs.iterator().next();
        BitSet wfBits = toGramBits(gramModel, FSUtils.toList(wf.getGrammems()));
        wfBits.and(filterBS);
        List<Feature> result = Lists.newArrayList();
        for (int i = wfBits.nextSetBit(0); i >= 0; i = wfBits.nextSetBit(i + 1)) {
            result.add(new Feature(featureName, gramModel.getGrammem(i).getId()));
        }
        return result;
    }
}