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

public class QueryRequest {

    @SerializedName("panelId")
    @Expose
    private Integer panelId;
    @SerializedName("range")
    @Expose
    private Range range;
    @SerializedName("rangeRaw")
    @Expose
    private RangeRaw rangeRaw;
    @SerializedName("interval")
    @Expose
    private String interval;
    @SerializedName("intervalMs")
    @Expose
    private Integer intervalMs;
    @SerializedName("targets")
    @Expose
    private List<Target> targets = null;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("maxDataPoints")
    @Expose
    private Integer maxDataPoints;

    public Integer getPanelId() {
        return panelId;
    }

    public void setPanelId(Integer panelId) {
        this.panelId = panelId;
    }

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

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Integer getIntervalMs() {
        return intervalMs;
    }

    public void setIntervalMs(Integer intervalMs) {
        this.intervalMs = intervalMs;
    }

    public List<Target> getTargets() {
        return targets;
    }

    public void setTargets(List<Target> targets) {
        this.targets = targets;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getMaxDataPoints() {
        return maxDataPoints;
    }

    public void setMaxDataPoints(Integer maxDataPoints) {
        this.maxDataPoints = maxDataPoints;
    }

}
