/**
 *
 */
package com.textocat.textokit.commons.wfstore;

/**
 * @author Rinat Gareev
 */
public interface WordformStoreBuilder<TagType> {
    void increment(String wordString, TagType tag);

    WordformStore<TagType> build();
}