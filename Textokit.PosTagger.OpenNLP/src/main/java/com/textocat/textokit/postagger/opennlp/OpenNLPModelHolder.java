/**
 * 
 */
package com.textocat.textokit.postagger.opennlp;

import opennlp.tools.util.model.BaseModel;

/**
 * @author Rinat Gareev
 * 
 */
public interface OpenNLPModelHolder<MT extends BaseModel> {

	MT getModel();

}
