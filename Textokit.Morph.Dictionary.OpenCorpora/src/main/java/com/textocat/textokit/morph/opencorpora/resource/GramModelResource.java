/**
 * 
 */
package com.textocat.textokit.morph.opencorpora.resource;

import org.apache.uima.resource.DataResource;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.SharedResourceObject;

import com.textocat.textokit.morph.dictionary.resource.GramModel;
import com.textocat.textokit.morph.dictionary.resource.GramModelHolder;

/**
 * @author Rinat Gareev
 * 
 */
public class GramModelResource implements GramModelHolder, SharedResourceObject {

	private GramModel gramModel;

	@Override
	public void load(DataResource dr) throws ResourceInitializationException {
		try {
			gramModel = GramModelDeserializer
					.from(dr.getInputStream(), String.valueOf(dr.getUrl()));
		} catch (Exception e) {
			throw new ResourceInitializationException(e);
		}
	}

	@Override
	public GramModel getGramModel() {
		return gramModel;
	}

}
