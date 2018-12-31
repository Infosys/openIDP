/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package idp.utils

import org.jenkins.plugins.lockableresources.LockableResource
import org.jenkins.plugins.lockableresources.LockableResourcesManager

/**
 * This class has the methods to configure and execute pipelines on specified slave
 */
class IDPSlaveConfiguration implements Serializable {
    def script

    IDPSlaveConfiguration(script) {
        this.script = script
    }

    /*
     * This method is used to check the existence of node
     */

    @NonCPS
    def nodeExistance(labelName) {

        def labeledNodeList = jenkins.model.Jenkins.instance.nodes.
                findAll { node -> (node.getAssignedLabels() as List).any { it.name == labelName } }
        return labeledNodeList

    }

    // This method provides an active, idle Node map with values for key [labelName,slaveWorkspace,empty] with the labelName from the current Jenkins instance
    @NonCPS
    def checkNodeAvailability(labelName, basePath, nodeObject) {
        // check node only if Lock available
        String lockAvailable = 'NA'
        def labeledNodeList = this.nodeExistance(labelName)

        Boolean slaveActive = false
        def activeSlave = ''
        def inactiveSlaves = 0
        for (int i = 0; i < labeledNodeList.size(); ++i) {
            def selectedSlave = labeledNodeList[i]
            def slaveWorkspace = (String) selectedSlave.getWorkspaceRoot() + '/' + basePath

            Boolean executorAvailable = (selectedSlave.getChannel() != null && (selectedSlave.toComputer().countIdle() > 0))
            if (executorAvailable) {
                // check if slave is online and executor is available

                def hostName = selectedSlave.toComputer().getHostName()
                if (hostName == null) {
                    hostName = selectedSlave.getChannel().getName().tokenize('/')[1]
                }

                def lockToken = 'lock_' + hostName + '_' + slaveWorkspace

                slaveActive = true
                lockAvailable = this.checkLockAvailability(lockToken)
                if (lockAvailable == 'true') {
                    nodeObject.put('token', lockToken)
                    nodeObject.put('slaveName', selectedSlave.name)
                    nodeObject.put('slaveWorkspace', slaveWorkspace)
                    nodeObject.put('slaveActive', slaveActive)
                    break
                }
            }
            if (selectedSlave.getChannel() == null) {
                inactiveSlaves++

            } else {
                activeSlave = selectedSlave.name
            }
            if (i == (labeledNodeList.size() - 1) && slaveActive) {

                nodeObject.put('slaveActive', slaveActive)
            } else if (i == (labeledNodeList.size() - 1) && inactiveSlaves == (labeledNodeList.size())) {

                nodeObject.put('slaveActive', slaveActive)
            }
        }

    }

    // check lock availability for the generated token of a selected slave with mentioned parameter label
    @NonCPS
    def checkLockAvailability(lockToken) {
        String lockAvailable = 'NA'
        try {

            //def labeledNodeList = jenkins.model.Jenkins.instance.getPluginManager().activePlugins
            LockableResourcesManager lockManager = (LockableResourcesManager) jenkins.model.Jenkins.instance.getDescriptorOrDie(LockableResourcesManager.class)

            LockableResource resourceDetails = lockManager.fromName(lockToken)

            if (resourceDetails == null) {

                lockAvailable = 'true'
            } else if (!resourceDetails.isLocked()) {

                lockAvailable = 'true'
            } else {

                lockAvailable = 'false'
            }

        } catch (Exception e) {
            //println e
            throw e
        }
        return lockAvailable

    }

    // executes check for slave assignment and finding lock availability
    @NonCPS
    def executeAssignment(labelName, basePath, nodeObject) {

        def labeledNodeList = nodeExistance(labelName)
        if (labeledNodeList.size() <= 0) {
            def errorStr = 'No slave with Label(or Name): ' + labelName + ' defined for the pipeline'

            this.script.error(errorStr)
        }



        nodeObject = [:]
        this.checkNodeAvailability(labelName, basePath, nodeObject)
        if (nodeObject.slaveActive != null && nodeObject.slaveActive &&
                nodeObject.slaveName != null && nodeObject.slaveWorkspace != null && nodeObject.token != null) {
            //throw new RuntimeException('Slave Available')

            return nodeObject
        } else if (nodeObject.slaveActive != null && !nodeObject.slaveActive) {

        } else if (nodeObject.slaveActive != null && nodeObject.slaveActive && nodeObject.token == null) {

        }
        // sending empty nodeObject map for slave unavailability
        nodeObject = [:]

        return nodeObject

    }
}
