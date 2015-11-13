/**
 *
 */
package com.textocat.textokit.morph.ruscorpora;

import com.textocat.textokit.morph.fs.Wordform;

/**
 * @author Rinat Gareev
 */
public interface RusCorporaTagMapper {

    void mapFromRusCorpora(RusCorporaWordform srcWf, Wordform targetWf);

}