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
 * This class implements IPLuginBase interface to set the limits for code quality violations
 *
 */

class ViolationPublisher implements IPluginBase {

    private def csPattern = ''
    private def fxCopPattern = ''
    private def fbPattern = ''
    private def pmdPattern = ''

    public void setCsPattern(csPattern) {
        this.csPattern = csPattern;
    }

    public void setFxCopPattern(fxCopPattern) {
        this.fxCopPattern = fxCopPattern;
    }

    public void setFbPattern(fbPattern) {
        this.fbPattern = fbPattern;
    }

    public void setPmdPattern(pmdPattern) {
        this.pmdPattern = pmdPattern;
    }

    public def getCsPattern() {
        return csPattern;
    }

    public def getFxCopPattern() {
        return fxCopPattern;
    }

    public def getFbPattern() {
        return fbPattern;
    }

    public def getPmdPattern() {
        return pmdPattern;
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

        data.put('min', 10)
        data.put('max', 500)
        data.put('unstable', 500)
        data.put('csPattern', this.csPattern)
        data.put('fbPattern', this.fbPattern)
        data.put('pmdPattern', this.pmdPattern)
        data.put('fxCopPattern', this.fxCopPattern)
        data.put('fauxProjectPath', '')
        data.put('sourceEncoding', 'default')
        data.put('sourcePathPattern', '')
        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    @Override
    public void addOptions(context, HashMap<String, String> data) {

        context.with {

            violations {

                ViolationPublisher.addcheckstyleSettings(delegate, data);
                ViolationPublisher.addGeneralBuildSettings(delegate, data)

                ViolationPublisher.addfindbugsSettings(delegate, data);
                ViolationPublisher.addfxcopSettings(delegate, data);

                ViolationPublisher.addpmdSettings(delegate, data);

            }
        }
    }

    /*
     * This method is used to add limits for checkstyle
     */

    private static void addcheckstyleSettings(context, data) {
        context.with {
            if (data.containsKey('min') && data.containsKey('max') && data.containsKey('unstable') && data.containsKey('csPattern'))
                checkstyle(data.get('min'), data.get('max'), data.get('unstable'), data.get('csPattern'))
        }
    }

    /*
     * This method is used to add limits for findbugs
     */

    private static void addfindbugsSettings(context, data) {
        context.with {
            if (data.containsKey('min') && data.containsKey('max') && data.containsKey('unstable') && data.containsKey('fbPattern'))
                findbugs(data.get('min'), data.get('max'), data.get('unstable'), data.get('fbPattern'))
        }
    }

    /*
     * This method is used to add limits for fxcop
     */

    private static void addfxcopSettings(context, data) {
        context.with {
            if (data.containsKey('min') && data.containsKey('max') && data.containsKey('unstable') && data.containsKey('fxCopPattern'))
                fxcop(data.get('min'), data.get('max'), data.get('unstable'), data.get('fxCopPattern'))
        }
    }

    /*
     * This method is used to add limits for pmd
     */

    private static void addpmdSettings(context, data) {
        context.with {
            if (data.containsKey('min') && data.containsKey('max') && data.containsKey('unstable') && data.containsKey('pmdPattern'))
                pmd(data.get('min'), data.get('max'), data.get('unstable'), data.get('pmdPattern'))
        }
    }

    /*
     * This method is used to add general settings
     */

    private static void addGeneralBuildSettings(context, data) {
        context.with {
            if (data.containsKey('fauxProjectPath')) fauxProjectPath(data.get('fauxProjectPath'));
            if (data.containsKey('sourceEncoding')) sourceEncoding(data.get('sourceEncoding'));
            if (data.containsKey('sourcePathPattern')) sourcePathPattern(data.get('sourcePathPattern'));
        }
    }


}
