/**
 *
 */
package ru.kfu.itis.cll.uima.io.axml;

import org.apache.uima.UIMAException;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * @author Rinat Gareev
 */
public class AXML2XMITest {

    @Test
    public void testLauncher() throws UIMAException, IOException, SAXException {
        AXML2XMI.main(new String[]{
                "-i", "test-data",
                "-o", "target",
                "-ts", "test.entities-ts"
        });
    }

}
