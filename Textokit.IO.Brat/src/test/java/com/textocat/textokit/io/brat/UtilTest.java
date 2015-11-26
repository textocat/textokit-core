
/*
 *    Copyright 2015 Textocat
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
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
