/**
 *
 */
package ru.kfu.itis.cll.uima.wfstore;

/**
 * @author Rinat Gareev
 */
public interface WordformStoreBuilder<TagType> {
    void increment(String wordString, TagType tag);

    WordformStore<TagType> build();
}