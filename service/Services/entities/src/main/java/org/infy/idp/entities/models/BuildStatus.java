/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.models;
/**
 * Entity to store build status
 * 
 * @author Infosys
 *
 */
public class BuildStatus {

	/**
	 * Enum State
	 */
	public enum State {
	/**
	 * Build is out of the queue and it is currently being executed.
	 */
	BUILDING,

	/**
	 * The max time to wait for the build get executed has passed. This state
	 * doesn't have to mean build is stuck or etc. It just means, the max waiting
	 * time has passed on the client side.
	 */
	TIMED_OUT,

	/**
	 * The build is cancelled in Jenkins before it started being executed.
	 */
	CANCELLED_IN_QUEUE,

	/**
	 * The build is stuck on Jenkins queue.
	 */
	STUCK_IN_QUEUE
	}

	private final String state;
	private final int buildNumber;

	/**
	 * Constructor BuildStatus
	 */
	public BuildStatus(String state, int buildNumber) {
		this.state = state;
		this.buildNumber = buildNumber;
	}

	/**
	 * @return state of the build
	 */
	public String getState() {
		return state;
	}

	public int getBuildNumber() {
		return buildNumber;
	}

	@Override
	public String toString() {
		return "BuildStatus{" + "state=" + state + ", buildNumber=" + buildNumber + '}';
	}
}
