/**
 *
 */
package com.textocat.textokit.io.brat;

import org.apache.uima.cas.TypeSystem;
import org.nlplab.brat.configuration.BratTypesConfiguration;

/**
 * @author Rinat Gareev
 */
public abstract class BratUimaMappingFactoryBase implements BratUimaMappingFactory {

    protected TypeSystem ts;
    protected BratTypesConfiguration bratTypesCfg;

    @Override
    public void setTypeSystem(TypeSystem ts) {
        this.ts = ts;
    }

    @Override
    public void setBratTypes(BratTypesConfiguration btConf) {
        this.bratTypesCfg = btConf;
    }
}
