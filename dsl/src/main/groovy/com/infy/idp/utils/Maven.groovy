/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.utils

import com.infy.idp.creators.*

class Maven {

	public static void invokeMaven(context, goals,localRepository,properties,mavenOpts,providedSettings) {

		context.with {

			maven {

				goals(goals)

				//properties('test.threads': 10, 'input.status': 'release')
				properties(properties)

				//localRepository('dir1/build.xml')
				localRepository(localRepository)

				//mavenOpts(['-Dprop2=value2', '-Dprop3=value3'])
				mavenOpts(mavenOpts)
				providedSettings(providedSettings)
				mavenInstallation('MAVEN')
			}
		}
	}


	public static void invokeMaven(context, String goalsArg, String pomPath, Map propertiesArg) {

		context.with {

			maven {

				// Specifies the goals to execute including other command line options.
				goals(goalsArg)

				// Specifies the path to the root POM.
				rootPOM(pomPath)

				//properties('test.threads': 10, 'input.status': 'release')
				properties(propertiesArg)

				mavenInstallation('MAVEN')
			}
		}
	}


	public static void invokeMaven(context, goals,localRepository,Iterable<String> mavenOpts,providedSettings) {

		context.with {
			maven {

				goals(goals)

				//buildFile('dir1/build.xml')
				localRepository(localRepository)

				mavenOpts(mavenOpts)
				providedSettings(providedSettings)
				mavenInstallation('MAVEN')
			}
		}
	}


	public static void invokeMaven(context, goalsArg ,pomPath) {

		context.with {
			
			maven {

				goals(goalsArg)

				// Specifies the path to the root POM.
				rootPOM(pomPath)
				
				mavenInstallation('MAVEN')
			}
		}
	}
}


