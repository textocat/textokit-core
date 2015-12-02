package com.textocat.textokit.eval.integration;

import com.textocat.textokit.eval.EvaluationLauncher;
import org.junit.Test;

/**
 * @author Rinat Gareev
 */
public class GSBasedEvalLauncherTest {

    @Test
    public void testLauncherUsingPropertiesFile() throws Exception {
        EvaluationLauncher.main(new String[]{"src/test/resources/eval-launch.properties"});
    }

}