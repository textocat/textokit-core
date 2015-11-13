/**
 *
 */
package com.textocat.textokit.morph.opencorpora.resource;

import com.textocat.textokit.commons.util.CacheKey;

import java.net.URL;

/**
 * @author Rinat Gareev
 */
class CacheResourceKey implements CacheKey {
    private URL url;

    public CacheResourceKey(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }
}