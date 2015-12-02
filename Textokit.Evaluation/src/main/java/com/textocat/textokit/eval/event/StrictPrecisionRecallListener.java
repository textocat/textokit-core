package com.textocat.textokit.eval.event;

import com.textocat.textokit.eval.measure.RecognitionMeasures;
import org.apache.uima.cas.text.AnnotationFS;

import javax.annotation.PostConstruct;

/**
 * @author Rinat Gareev
 */
public class StrictPrecisionRecallListener extends TypedPrintingEvaluationListener {

    // state fields
    private RecognitionMeasures measures = new RecognitionMeasures();

    @Override
    @PostConstruct
    protected void init() throws Exception {
        super.init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMissing(AnnotationFS goldAnno) {
        if (!checkType(goldAnno)) {
            return;
        }
        measures.incrementMissing(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onExactMatch(AnnotationFS goldAnno, AnnotationFS sysAnno) {
        if (!checkType(goldAnno)) {
            return;
        }
        measures.incrementMatching(1);
    }

    @Override
    public void onPartialMatch(AnnotationFS goldAnno, AnnotationFS sysAnno) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSpurious(AnnotationFS sysAnno) {
        if (!checkType(sysAnno)) {
            return;
        }
        measures.incrementSpurious(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvaluationComplete() {
        String report = MeasuresReportUtils.getReportString(targetType, measures);
        printer.println(report);
        clean();
    }

    public RecognitionMeasures getMeasures() {
        return measures;
    }

    private boolean checkType(AnnotationFS anno) {
        if (targetType == null) {
            return true;
        }
        return ts.subsumes(targetType, anno.getType());
    }
}