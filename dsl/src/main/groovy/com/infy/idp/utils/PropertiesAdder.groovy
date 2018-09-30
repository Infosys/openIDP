/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.utils

/**
 *
 * This class has the method for adding string and password parameters, etc
 *
 */
class PropertiesAdder {

    private static String triggerValue = 'allowMultiSelectionForConcurrentBuilds'

    /*
     * This method can be used to add git lab connection
     */

    public static void addGitLabConnection(context) {
        context.with {
            gitLabConnection {
                gitLabConnection('')
            }
        }
    }

    /*
     * This method can be used to add rebuild option
     */

    public static void addRebuild(context) {
        context.with {
            rebuild {
                autoRebuild(false)
                rebuildDisabled(false)
            }
        }
    }

    /*
     * This method is used add string parameter
     */

    public static void addStringParam(context, pName, pDefaultValue, pDescription) {
        context.with {
            'hudson.model.StringParameterDefinition' {
                name(pName)
                defaultValue(pDefaultValue)
                description(pDescription)
            }
        }
    }

    /*
     * This method is used add string parameter
     */

    public static void addPasswordParam(context, pName, pDefaultValue, pDescription) {

        context.with {

            'hudson.model.PasswordParameterDefinition' {

                delegate.name(pName)
                delegate.defaultValue(pDefaultValue)
                delegate.description(pDescription)
            }
        }
    }

    /*
     * This method is used add boolean parameter
     */

    public static void addBooleanParam(context, pName, pDefaultValue, pDescription) {
        context.with {
            'hudson.model.BooleanParameterDefinition' {
                name(pName)
                defaultValue(pDefaultValue)
                description(pDescription)
            }
        }
    }

    /*
     * This method is used add node parameter
     */

    public static void addNodeParam(context, pName, pSlaveList, pDescription) {
        context.with {
            'org.jvnet.jenkins.plugins.nodelabelparameter.NodeParameterDefinition' {
                name(pName)
                description(pDescription)
                defaultSlaves(pSlaveList)
                allowedSlaves(pSlaveList)
                triggerIfResult(triggerValue)
                allowMultiNodeSelection(false)
                triggerConcurrentBuilds(false)
                ignoreOfflineNodes(false)
                //nodeEligibility {}
            }
        }
    }

    /*
     * This method is used add label parameter
     */

    public static void addLabelParam(context, pName, pSlave, pDescription) {
        context.with {
            'org.jvnet.jenkins.plugins.nodelabelparameter.LabelParameterDefinition' {
                name(pName)
                description(pDescription)
                defaultValue(pSlave)
                allNodesMatchingLabel(false)
                triggerIfResult('allCases')
                allowMultiNodeSelection(true)
                triggerIfResult(false)
                //nodeEligibility {}
            }
        }
    }

    /*
     * This method is used add throttle parameter
     */

    public static void addThrottleJobProperty(context) {
        context.with {
            throttleJobProperty {
                maxConcurrentPerNode(0)
                maxConcurrentTotal(0)
                throttleEnabled(false)
                throttleOption('project')
                limitOneJobWithMatchingParams(false)
                paramsToUseForLimit('')
                matrixOptions {
                    throttleMatrixBuilds(false)
                    throttleMatrixConfigurations(false)
                }
            }
        }
    }

    /*
     * This method is used add build discarder
     */

    public static void addBuildDiscarder(context) {
        context.with {
            buildDiscarder {
                strategy {
                    logRotator {
                        daysToKeepStr('30')
                        numToKeepStr('30')
                        artifactDaysToKeepStr('30')
                        artifactNumToKeepStr('30')
                    }
                }
            }
        }
    }
}
