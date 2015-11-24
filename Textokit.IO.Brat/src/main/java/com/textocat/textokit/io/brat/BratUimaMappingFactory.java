/**
 * 
 */
package com.textocat.textokit.io.brat;

import org.apache.uima.cas.TypeSystem;
import org.apache.uima.resource.ResourceInitializationException;
import org.nlplab.brat.configuration.BratTypesConfiguration;

/**
 * @author Rinat Gareev
 * 
 */
public interface BratUimaMappingFactory {

	// Init
	void setTypeSystem(TypeSystem ts);

	void setBratTypes(BratTypesConfiguration btConf);

	// Then invoke
	BratUimaMapping getMapping() throws ResourceInitializationException;
}
