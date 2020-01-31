/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.ngnjson;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Entity to store Per Task Options information of Next-Gen Json
 * 
 * @author Infosys
 *
 */
public class Options {

	@SerializedName("image")
	@Expose
	private String image;
	@SerializedName("relativeDir")
	@Expose
	private String relativeDir;
	@SerializedName("custom")
	@Expose
	private String custom;
	@SerializedName("commands")
	@Expose
	private List<String> commands;

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the relativeDir
	 */
	public String getRelativeDir() {
		return relativeDir;
	}

	/**
	 * @param relativeDir the relativeDir to set
	 */
	public void setRelativeDir(String relativeDir) {
		this.relativeDir = relativeDir;
	}

	/**
	 * @return the custom
	 */
	public String getCustom() {
		return custom;
	}

	/**
	 * @param custom the custom to set
	 */
	public void setCustom(String custom) {
		this.custom = custom;
	}

	/**
	 * @return the commands
	 */
	public List<String> getCommands() {
		return commands;
	}

	/**
	 * @param commands the commands to set
	 */
	public void setCommands(List<String> commands) {
		this.commands = commands;
	}

}
