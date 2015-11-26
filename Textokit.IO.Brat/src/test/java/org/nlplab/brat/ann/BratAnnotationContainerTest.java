/**
 *
 */
package org.nlplab.brat.ann;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author Rinat Gareev
 */
public class BratAnnotationContainerTest {

    @Test
    public void testIndexedRoleParsing() {
        assertArrayEquals(null, BratAnnotationContainer.parseRoleIndex("someRole"));
        assertArrayEquals(new Object[]{"someRole", 2},
                BratAnnotationContainer.parseRoleIndex("someRole2"));
        assertArrayEquals(new Object[]{"someRole", 30},
                BratAnnotationContainer.parseRoleIndex("someRole030"));
    }

}
