/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.nexus;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Entity to store asset details of a repository
 * 
 * @author Infosys
 *
 */
public class Item {

	@SerializedName("id")
	@Expose
	private String id;
	@SerializedName("group")
	@Expose
	private String group;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("version")
	@Expose
	private String version;
	@SerializedName("repository")
	@Expose
	private String repository;
	@SerializedName("format")
	@Expose
	private String format;
	@SerializedName("assets")
	@Expose
	private List<Asset> assets = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}

}
