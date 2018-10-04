/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/


package org.infy.idp.entities;

/**
 * The Class ResourceResponse.
 *
 * @param <T>
 *            the generic type
 */
public class ResourceResponse<T> extends BaseResponse {

	/** The resource. */
	private T resource;

	

	/**
	 * Gets the resource.
	 *
	 * @return the resource
	 */
	public T getResource() {
		return resource;
	}

	/**
	 * Sets the resource.
	 *
	 * @param resource
	 *            the new resource
	 */
	public void setResource(T resource) {
		this.resource = resource;
	}
}
