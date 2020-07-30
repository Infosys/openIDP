/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.interfaces;

import org.infy.idp.entities.jobs.IDPJob

/**
 *
 * This interface includes the method for adding commands, performmapping and adding options
 *
 */

public interface IPluginBase {
  public void add(context,IDPJob dataObj )
  public HashMap<String,String> performMapping(IDPJob dataObj);
  public void addOptions(context, HashMap<String,String> data); 
}
