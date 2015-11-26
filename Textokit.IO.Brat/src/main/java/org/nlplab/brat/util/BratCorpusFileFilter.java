/**
 *
 */
package org.nlplab.brat.util;

import org.nlplab.brat.BratConstants;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Rinat Gareev
 */
public class BratCorpusFileFilter implements FileFilter {

    public static final BratCorpusFileFilter INSTANCE = new BratCorpusFileFilter();

    private BratCorpusFileFilter() {
    }

    @Override
    public boolean accept(File f) {
        if (!f.isDirectory()) {
            return false;
        }
        File annoConfFile = new File(f, BratConstants.ANNOTATION_CONF_FILE);
        return annoConfFile.isFile();
    }

}
