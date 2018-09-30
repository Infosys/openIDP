/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.utils

import com.infy.idp.creators.*

/**
 *
 * This class has the method to invoke ANT
 *
 */

class Ant {

    /*
     * This method is used to invokeANT file
     * These methods are overloaded to decide the call of the method based on the type and number of parameters passed
     */

    public static void invokeAnt(context, targetsArg, buildFileArg, propss, javaOptss) {

        context.with {

            ant {

                target(targetsArg)

                //props('test.threads': 10, 'input.status': 'release')
                props(propss)

                //buildFile('dir1/build.xml')
                buildFile(buildFileArg)

                /*javaOpt('-Xmx1g')
                javaOpts(['-Dprop2=value2', '-Dprop3=value3'])*/
                if (null != javaOptss) {
                    javaOpt(javaOptss)
                }

                antInstallation('ANT')
            }
        }
    }

    public static void invokeAnt(context, targetsArg, buildFileArg) {

        context.with {
            ant {

                target(targetsArg)

                //buildFile('dir1/build.xml')
                buildFile(buildFileArg)

                antInstallation('ANT')
            }
        }
    }

    public static void invokeAnt(context, targetsArg, buildFileArg, Map<String, String> propss) {

        context.with {

            ant {

                target(targetsArg)

                props(propss)

                //buildFile('dir1/build.xml')
                buildFile(buildFileArg)

                antInstallation('ANT')
            }
        }
    }


    public static void invokeAnt(context, targetsArg, buildFileArg, Iterable<String> javaOptss) {

        context.with {

            ant {

                target(targetsArg)

                //buildFile('dir1/build.xml')
                buildFile(buildFileArg)

                javaOpts(javaOptss)

                antInstallation('ANT')
            }
        }
    }
}


