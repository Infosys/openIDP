/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/


package org.infy.idp.entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nexus {

    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("continuationToken")
    @Expose
    private Object continuationToken;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Object getContinuationToken() {
        return continuationToken;
    }

    public void setContinuationToken(Object continuationToken) {
        this.continuationToken = continuationToken;
    }

}
