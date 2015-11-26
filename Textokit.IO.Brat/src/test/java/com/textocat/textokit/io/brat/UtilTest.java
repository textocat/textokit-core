/**
 *
 */
package com.textocat.textokit.io.brat;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rinat Gareev
 */
public class UtilTest {

    @Test
    public void testBratToJavaNameConversions() {
        Assert.assertEquals("TransferOwnership", PUtils.toProperJavaName("Transfer-ownership"));
        Assert.assertEquals("TransferOwnership", PUtils.toProperJavaName("Transfer--ownership"));
        Assert.assertEquals("TransferOwnership", PUtils.toProperJavaName("Transfer-Ownership"));
        Assert.assertEquals("TransferOwnership", PUtils.toProperJavaName("Transfer--Ownership"));
        Assert.assertEquals("Ownership", PUtils.toProperJavaName("-Ownership"));
        Assert.assertEquals("Ownership", PUtils.toProperJavaName("--ownership"));
    }

}
