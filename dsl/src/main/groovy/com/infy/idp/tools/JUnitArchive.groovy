/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools

import com.infy.idp.creators.*
import com.infy.idp.utils.*

class JUnitArchive {

	private String pattern = ''

	public String updatePattern(pattern) {
		this.pattern += (pattern+',')
	}

	public void invokeJUnitArchive(context, jsonData) {

		context.with {

			archiveJunit(this.pattern) {

				// If set, does not fail the build on empty test results.
				allowEmptyResults(false)

				// Sets the amplification factor to apply to test failures when computing the test result contribution to the build health score.
				healthScaleFactor(1.0)

				// If set, retains any standard output or error from a test suite in the test results after the build completes.
				retainLongStdout(false)
			}
		}
	}
	
	public void setPattern(String pattern) {
		this.pattern = pattern
	}
}