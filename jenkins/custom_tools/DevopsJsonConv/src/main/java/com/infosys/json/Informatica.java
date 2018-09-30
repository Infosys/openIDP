package com.infosys.json;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Informatica {
	 @SerializedName("infoObject")
	 @Expose
	 private List<InformaticaObject> infoObject;

	public List<InformaticaObject> getInfoObject() {
		return infoObject;
	}

	public void setInfoObject(List<InformaticaObject> infoObject) {
		this.infoObject = infoObject;
	}

}
