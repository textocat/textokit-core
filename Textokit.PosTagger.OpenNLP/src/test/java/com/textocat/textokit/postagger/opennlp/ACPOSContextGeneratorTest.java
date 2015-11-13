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

package com.textocat.textokit.postagger.opennlp;

import opennlp.tools.postag.POSContextGenerator;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rinat Gareev
 */
public class ACPOSContextGeneratorTest {

    private static final String[] TOKENS_1 = {"What", "do", "you", "see", "?"};
    private static final String[][] AC_1 = {
            {"w=what"},
            {"w=do"},
            {"w=you"},
            {"w=see"},
            {"w=?"}
    };

    @Test
    public void testWithNullPrevTags() {
        POSContextGenerator cg = new ACPOSContextGenerator(2);
        Assert.assertArrayEquals(new String[]{"w=what"},
                cg.getContext(0, TOKENS_1, null, AC_1[0]));
    }

    @Test
    public void testWithEmptyPrevTags() {
        POSContextGenerator cg = new ACPOSContextGenerator(2);
        Assert.assertArrayEquals(new String[]{"w=what"},
                cg.getContext(0, TOKENS_1, new String[0], AC_1[0]));
    }

    @Test
    public void testWithSinglePrevTag() {
        POSContextGenerator cg = new ACPOSContextGenerator(2);
        Assert.assertArrayEquals(new String[]{"w=do", "pt1=wh"},
                cg.getContext(1, TOKENS_1, new String[]{"wh"}, AC_1[1]));
    }

    @Test
    public void testWithPrevTags2() {
        POSContextGenerator cg = new ACPOSContextGenerator(2);
        Assert.assertArrayEquals(new String[]{"w=you", "pt1=v", "pt2=wh"},
                cg.getContext(2, TOKENS_1, new String[]{"wh", "v"}, AC_1[2]));
    }

    @Test
    public void testWithPrevTags3() {
        POSContextGenerator cg = new ACPOSContextGenerator(2);
        Assert.assertArrayEquals(new String[]{"w=see", "pt1=pr", "pt2=v"},
                cg.getContext(3, TOKENS_1, new String[]{"wh", "v", "pr"}, AC_1[3]));
    }
}
