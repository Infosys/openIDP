/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Node {

@SerializedName("fileName")
@Expose
private String fileName;
@SerializedName("line")
@Expose
private Integer line;
@SerializedName("name")
@Expose
private String name;
@SerializedName("code")
@Expose
private String code;

public String getFileName() {
return fileName;
}

public void setFileName(String fileName) {
this.fileName = fileName;
}

public Integer getLine() {
return line;
}

public void setLine(Integer line) {
this.line = line;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

}