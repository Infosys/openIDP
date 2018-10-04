/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.acceleratest;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
	public Acceleratest createReport() {
		return new Acceleratest();
	}

	public Acceleratest.TestSuite createReportTestSuite() {
		return new Acceleratest.TestSuite();
	}

	public Acceleratest.TestSuite.TestCase createReportTestSuiteTestCase() {
		return new Acceleratest.TestSuite.TestCase();
	}

	public Acceleratest.TestSuite.TestCase.TCIteration createReportTestSuiteTestCaseTCIteration() {
		return new Acceleratest.TestSuite.TestCase.TCIteration();
	}

	public Acceleratest.TestSuite.TestCase.TCIteration.BP createReportTestSuiteTestCaseTCIterationBP() {
		return new Acceleratest.TestSuite.TestCase.TCIteration.BP();
	}

	public Acceleratest.TestSuite.TestCase.TCIteration.BP.BPIteration createReportTestSuiteTestCaseTCIterationBPBPIteration() {
		return new Acceleratest.TestSuite.TestCase.TCIteration.BP.BPIteration();
	}

	public Acceleratest.TestSuite.TestCase.TCIteration.BP.BPIteration.Result createReportTestSuiteTestCaseTCIterationBPBPIterationResult() {
		return new Acceleratest.TestSuite.TestCase.TCIteration.BP.BPIteration.Result();
	}
}
