/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.publishers

import com.infy.idp.interfaces.IPluginBase
import org.infy.idp.entities.jobs.IDPJob


/**
 *
 * This class implements IPLuginBase interface to tag the GIT repository
 *
 */

class GitPublisher implements IPluginBase {

    private def tag;
    private def targetRepo

    public def getTag() {
        return tag;
    }

    public void setTag(tag) {
        this.tag = tag;
    }

    public def getTargetRepo() {
        return targetRepo;
    }

    public void setTargetRepo(def targetRepo) {
        this.targetRepo = targetRepo;
    }

    /*
     * This function implements add method of IPluginBase interface
     */

    @Override
    public void add(context, IDPJob dataObj) {
        this.addOptions(context, this.performMapping(dataObj));
    }

    /*
     * This function implements performMapping method of IPluginBase interface
     */

    @Override
    public HashMap<String, String> performMapping(IDPJob dataObj) {

        HashMap<String, String> data = new HashMap<String, String>()

        data.put('targetRepoName', this.targetRepo);
        data.put('tagName', this.tag);
        data.put('tagMessage', '');
        data.put('createTag', true);
        data.put('updateTag', false);
        data.put('pushOnlyIfSuccess', false);
        data.put('pushMerge', false)
        data.put('forcePush', false);

        return data
    }

    /*
    * This function implements addOptions method of IPluginBase interface
    */

    @Override
    public void addOptions(context, HashMap<String, String> data) {


        context.with {

            //Requires Jenkins Git plugin Generated
            gitPublisher {

                // Specify tags to push at the completion of the build.
                tagsToPush {

                    tagToPush {

                        if (data.containsKey('targetRepoName')) targetRepoName(data.get('targetRepoName'))
                        if (data.containsKey('tagName')) tagName(data.get('tagName'))
                        if (data.containsKey('tagMessage')) tagMessage(data.get('tagMessage'))
                        if (data.containsKey('createTag')) createTag(data.get('createTag'))
                        if (data.containsKey('updateTag')) updateTag(data.get('updateTag'))
                    }
                }

                if (data.containsKey('pushOnlyIfSuccess')) pushOnlyIfSuccess(data.get('pushOnlyIfSuccess'))

                // Push merges back to the origin specified in the pre-build merge options.
                if (data.containsKey('pushMerge')) pushMerge(data.get('pushMerge'))

                if (data.containsKey('forcePush')) forcePush(data.get('forcePush'))
            }
        }
    }
}
