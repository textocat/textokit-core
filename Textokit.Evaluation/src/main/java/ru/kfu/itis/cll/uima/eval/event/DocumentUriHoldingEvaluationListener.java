package ru.kfu.itis.cll.uima.eval.event;

/**
 * 
 * @author Rinat Gareev
 * 
 */
public abstract class DocumentUriHoldingEvaluationListener implements EvaluationListener {

	protected String currentDocUri;

	@Override
	public void onDocumentChange(String docUri) {
		currentDocUri = docUri;
	}
}