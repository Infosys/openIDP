/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/



package com.infosys.utilities.acceleratest;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the acceleraTest package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: acceleraTest
     * 
     */
    

    /**
     * Create an instance of {@link Acceleratest }
     * 
     */
    public Acceleratest createReport() {
        return new Acceleratest();
    }

    /**
     * Create an instance of {@link Acceleratest.TestSuite }
     * 
     */
    public Acceleratest.TestSuite createReportTestSuite() {
        return new Acceleratest.TestSuite();
    }

    /**
     * Create an instance of {@link Acceleratest.TestSuite.TestCase }
     * 
     */
    public Acceleratest.TestSuite.TestCase createReportTestSuiteTestCase() {
        return new Acceleratest.TestSuite.TestCase();
    }

    /**
     * Create an instance of {@link Acceleratest.TestSuite.TestCase.TCIteration }
     * 
     */
    public Acceleratest.TestSuite.TestCase.TCIteration createReportTestSuiteTestCaseTCIteration() {
        return new Acceleratest.TestSuite.TestCase.TCIteration();
    }

    /**
     * Create an instance of {@link Acceleratest.TestSuite.TestCase.TCIteration.BP }
     * 
     */
    public Acceleratest.TestSuite.TestCase.TCIteration.BP createReportTestSuiteTestCaseTCIterationBP() {
        return new Acceleratest.TestSuite.TestCase.TCIteration.BP();
    }

    /**
     * Create an instance of {@link Acceleratest.TestSuite.TestCase.TCIteration.BP.BPIteration }
     * 
     */
    public Acceleratest.TestSuite.TestCase.TCIteration.BP.BPIteration createReportTestSuiteTestCaseTCIterationBPBPIteration() {
        return new Acceleratest.TestSuite.TestCase.TCIteration.BP.BPIteration();
    }

    /**
     * Create an instance of {@link Acceleratest.TestSuite.TestCase.TCIteration.BP.BPIteration.Result }
     * 
     */
    public Acceleratest.TestSuite.TestCase.TCIteration.BP.BPIteration.Result createReportTestSuiteTestCaseTCIterationBPBPIterationResult() {
        return new Acceleratest.TestSuite.TestCase.TCIteration.BP.BPIteration.Result();
    }

}
