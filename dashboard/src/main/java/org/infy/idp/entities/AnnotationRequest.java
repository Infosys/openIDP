/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnnotationRequest {

    @SerializedName("range")
    @Expose
    private Range range;
    @SerializedName("rangeRaw")
    @Expose
    private RangeRaw rangeRaw;
    @SerializedName("annotation")
    @Expose
    private Annotation annotation;
    
    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public RangeRaw getRangeRaw() {
        return rangeRaw;
    }

    public void setRangeRaw(RangeRaw rangeRaw) {
        this.rangeRaw = rangeRaw;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

}
