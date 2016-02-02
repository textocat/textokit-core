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

package com.textocat.textokit.corpus.statistics.app;

import com.beust.jcommander.JCommander;
import com.textocat.textokit.corpus.statistics.dao.units.InMemoryUnitsDAO;
import com.textocat.textokit.corpus.statistics.dao.units.Unit;
import com.textocat.textokit.corpus.statistics.dao.units.UnitsDAO;
import de.tudarmstadt.ukp.dkpro.statistics.agreement.AnnotationStudy;
import de.tudarmstadt.ukp.dkpro.statistics.agreement.MultiRaterPiAgreement;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;

public class UnitsTSVAgreementCalculator {

    final Logger logger = LoggerFactory
            .getLogger(UnitsTSVAgreementCalculator.class);

    private UnitsDAO dao;
    UnitsTSVAgreementCalculatorParams calculatorParams;

    public static void main(String[] args) throws IOException,
            URISyntaxException {
        UnitsTSVAgreementCalculator calculator = new UnitsTSVAgreementCalculator(
                args);
        calculator.calculate();
    }

    public UnitsTSVAgreementCalculator(String[] args) throws IOException,
            URISyntaxException {
        calculatorParams = new UnitsTSVAgreementCalculatorParams();
        new JCommander(calculatorParams, args);

        dao = new InMemoryUnitsDAO();
        Reader tsvIn = new FileReader(calculatorParams.tsv);
        dao.addUnitsFromTSV(tsvIn);
        IOUtils.closeQuietly(tsvIn);

    }

    public void calculate() {
        AnnotationStudy study = new AnnotationStudy(
                calculatorParams.annotatorCount);
        for (Unit unit : dao.getUnits()) {
            String[] classes = unit.getSortedClasses();
            if (classes.length == calculatorParams.annotatorCount) {
                study.addItemAsArray(classes);
            } else {
                logger.warn(
                        "Unit {} has wrong annotators count, so it is not included in study.",
                        unit);
            }
        }
        MultiRaterPiAgreement pi = new MultiRaterPiAgreement(study);
        System.out.println(pi.calculateAgreement());

    }
}
