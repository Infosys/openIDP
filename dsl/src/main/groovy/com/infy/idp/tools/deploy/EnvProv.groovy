/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/
package com.infy.idp.tools.deploy




/**
 *
 * This class has the method to add various scripts
 *
 */
class  EnvProv{
    /*
    * this method is used to configure conditional steps
    */
    public static void add(context, envProv) {
        switch (envProv.toolType) {
            case 'ansiblescript':
                context.with {
						/*steps {
						
							ansiblePlaybookBuilder{
								playbook(envProv.scriptFilePath)
								inventory {
									inventoryDoNotSpecify()
								}
							}
							    ansiblePlaybook(envProv.scriptFilePath) {
								inventoryPath('')

								}
						}*/
						steps {
							
							ansiblePlaybookBuilder{
								playbook(envProv.scriptFilePath)
								inventory {
									inventoryDoNotSpecify()
								}
							}
						}
                    }
                break

        }

    }
}
