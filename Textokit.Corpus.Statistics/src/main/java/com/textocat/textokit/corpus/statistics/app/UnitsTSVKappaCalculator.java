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
import com.beust.jcommander.Parameter;
import com.textocat.textokit.corpus.statistics.dao.units.InMemoryUnitsDAO;
import com.textocat.textokit.corpus.statistics.dao.units.Unit;
import com.textocat.textokit.corpus.statistics.dao.units.UnitsDAO;
import de.tudarmstadt.ukp.dkpro.statistics.agreement.AnnotationStudy;
import de.tudarmstadt.ukp.dkpro.statistics.agreement.TwoRaterKappaAgreement;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;

public class UnitsTSVKappaCalculator {

    private final Logger logger = LoggerFactory
            .getLogger(UnitsTSVKappaCalculator.class);

    @Parameter(names = "-tsv", description = "Input TSV file", required = true)
    public String tsv;

    private UnitsDAO dao;

    public static void main(String[] args) throws IOException,
            URISyntaxException {
        UnitsTSVKappaCalculator calculator = new UnitsTSVKappaCalculator(args);
        calculator.calculate();
    }

    public UnitsTSVKappaCalculator(String[] args) throws IOException,
            URISyntaxException {
        new JCommander(this, args);

        dao = new InMemoryUnitsDAO();
        Reader tsvIn = new FileReader(tsv);
        dao.addUnitsFromTSV(tsvIn);
        IOUtils.closeQuietly(tsvIn);

    }

    public void calculate() {
        AnnotationStudy study = new AnnotationStudy(2);
        int units = 0;
        int unitsDoneBySingleAnnotator = 0;
        for (Unit unit : dao.getUnits()) {
            units++;
            String[] classes = unit.getSortedClasses();
            if (classes.length == 2) {
                study.addItemAsArray(classes);
            } else {
                unitsDoneBySingleAnnotator++;
            }
        }
        logger.info("Read units: {}. There are {} units covered by an only annotator",
                units, unitsDoneBySingleAnnotator);
        TwoRaterKappaAgreement kappa = new TwoRaterKappaAgreement(study);
        System.out.println(String.format("Kappa: %s\nObserved agr: %s\nExpected %s",
                kappa.calculateAgreement(),
                kappa.calculateObservedAgreement(), kappa.calculateExpectedAgreement()));

    }
}
