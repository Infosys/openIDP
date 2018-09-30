package org.infy.subscription.entities.licencekeymanagement;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author kruti.vyas
 *
 */
public class Licenses {
	
	@SerializedName("licenses")
    @Expose
	List<License> licenseList;

	public List<License> getLicenses() {
		return licenseList;
	}

	public void setLicenses(List<License> licenses) {
		this.licenseList = licenses;
	}

	
	

}
