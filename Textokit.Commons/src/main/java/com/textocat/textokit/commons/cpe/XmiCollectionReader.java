package com.textocat.textokit.commons.cpe;

import com.google.common.collect.Collections2;
import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.resource.ResourceConfigurationException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static org.apache.commons.io.filefilter.FileFilterUtils.suffixFileFilter;
import static org.apache.commons.io.filefilter.FileFilterUtils.trueFileFilter;

/**
 * A simple collection reader that reads CASes in XMI format from a directory
 * and its subdirectories in the filesystem.
 */
public class XmiCollectionReader extends XmiCollectionReaderBase {

    /**
     * Name of configuration parameter that must be set to the path of a
     * directory containing the XMI files.
     */
    public static final String PARAM_INPUTDIR = "InputDirectory";
    @ConfigurationParameter(name = PARAM_INPUTDIR, mandatory = true)
    private File inputDir;
    // derived
    private Collection<Resource> xmiResources;

    public static CollectionReaderDescription createDescription(
            File inputDir, TypeSystemDescription inputTSD) throws ResourceInitializationException {
        return CollectionReaderFactory.createReaderDescription(XmiCollectionReader.class,
                inputTSD,
                PARAM_INPUTDIR, inputDir);
    }

    @Override
    protected Iterable<Resource> getResources(UimaContext ctx) throws IOException,
            ResourceInitializationException {
        // if input directory does not exist or is not a directory, throw exception
        if (!inputDir.isDirectory()) {
            throw new ResourceInitializationException(
                    ResourceConfigurationException.DIRECTORY_NOT_FOUND,
                    new Object[]{PARAM_INPUTDIR, this.getMetaData().getName(),
                            inputDir.getPath()});
        }
        // get list of .xmi files in the specified directory
        Collection<File> mFiles = FileUtils.listFiles(inputDir,
                suffixFileFilter(".xmi"), trueFileFilter());
        xmiResources = Collections2.transform(mFiles, PUtils.file2Resource);
        return xmiResources;
    }

    @Override
    protected Integer getResourcesNumber() {
        return xmiResources.size();
    }

}