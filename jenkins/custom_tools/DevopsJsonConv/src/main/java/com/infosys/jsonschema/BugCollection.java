/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infosys.jsonschema;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"id",
	"bug_type",
	"bug_category",
	"priority",
	"start_line",
	"end_line"
})
public class BugCollection {
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("bug_type")
    private String bug_type;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("bug_category")
    private String bug_category;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    private String id;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("start_line")
    private long start_line;
    
    @JsonProperty("end_line")
    private long end_line;
    
    @JsonProperty("priority")
    private long priority;




    /**
     * No args constructor for use in serialization
     * 
     */
    public BugCollection() {
    	// Empty Constructor
    }

    public BugCollection(String bug_type, String bug_category, String id, long start_line, long end_line,
			long priority) {
		super();
		this.bug_type = bug_type;
		this.bug_category = bug_category;
		this.id = id;
		this.start_line = start_line;
		this.end_line = end_line;
		this.priority = priority;
	}

    @JsonProperty("bug_type")
    public String getBugType() {
        return bug_type;
    }

    /**
     * 
     * (Required)
     * 
     * @param bugs
     *     The bugs
     */
    @JsonProperty("bug_type")
    public void setBugType(String bugs) {
        this.bug_type = bugs;
    }

    public BugCollection withBugType(String bugs) {
        this.bug_type = bugs;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The _class
     */
    @JsonProperty("bug_category")
    public String getBugCategory() {
        return bug_category;
    }

    /**
     * 
     * (Required)
     * 
     * @param _class
     *     The class
     */
    @JsonProperty("bug_category")
    public void setBugCategory_(String _class) {
        this.bug_category = _class;
    }

    public BugCollection withBugCategory(String _class) {
        this.bug_category = _class;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The _interface
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * (Required)
     * 
     * @param string
     *     The interface
     */
    @JsonProperty("id")
    public void setId(String string) {
        this.id = string;
    }

    public BugCollection withId(String _interface) {
        this.id = _interface;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The size
     */
    @JsonProperty("start_line")
    public long getStartLine() {
        return start_line;
    }

    /**
     * 
     * (Required)
     * 
     * @param size
     *     The size
     */
    @JsonProperty("start_line")
    public void setStartLine(long size) {
        this.start_line = size;
    }

    public BugCollection withStartLine(long size) {
        this.start_line = size;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The sourceFile
     */
    @JsonProperty("end_line")
    public long getEndLine() {
        return end_line;
    }

    /**
     * 
     * (Required)
     * 
     * @param sourceFile
     *     The sourceFile
     */
    @JsonProperty("end_line")
    public void setEndLine(long sourceFile) {
        this.end_line = sourceFile;
    }

    public BugCollection withEndLine(long sourceFile) {
        this.end_line = sourceFile;
        return this;
    }
    /**
     * 
     * (Required)
     * 
     * @return
     *     The sourceFile
     */
    @JsonProperty("priority")
    public long getPriority() {
        return priority;
    }

    /**
     * 
     * (Required)
     * 
     * @param sourceFile
     *     The sourceFile
     */
    @JsonProperty("priority")
    public void setPriority(long sourceFile) {
        this.priority = sourceFile;
    }

    public BugCollection withPriority(long sourceFile) {
        this.priority = sourceFile;
        return this;
    }

}
