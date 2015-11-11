/**
 *
 */
package ru.kfu.itis.cll.uima.wfstore;

import java.io.File;

/**
 * @author Rinat Gareev
 */
public interface WordformStore<TagType> {

    TagType getTag(String wf);

    <T> T getProperty(String key, Class<T> valueClass);

    void setProperty(String key, Object value);

    void persist(File outFile) throws Exception;
}