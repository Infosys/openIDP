/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.models.BuildStatus;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Executable;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.QueueItem;
import com.offbytwo.jenkins.model.QueueReference;

@Component
public class BuildService {
	private static final Logger logger = LoggerFactory.getLogger(BuildService.class);

	private static final String JOB_TEMPLATE_PATH = "job.xml";
	private static final String JOB_SHARED_PATH = "SharePath";
	private static final String PIPELINE_SCRIPT_PATH = "pipelineScriptPath";
	@Autowired
	private ConfigurationManager configmanager;
	/**
	 * Default value of {@link #firstCheckDelay}
	 */
	protected static final long DEFAULT_FIRST_CHECK_DELAY = 5 * 1000L;

	/**
	 * Default value of {@link #pollPeriod}
	 */
	protected static final long DEFAULT_POLL_PERIOD = 2 * 1000L;

	private static long firstCheckDelay = 3000L;
	private static long pollPeriod = 2 * 4000L;

	/**
	 * @param firstCheckDelay how long should we wait (in milliseconds) before we
	 *                        start checking the queue item status
	 * @param pollPeriod      how long should we wait (in milliseconds) before
	 *                        checking the queue item status for next time
	 */

	BuildService() {

	}

	/**
	 * Creates the new job.
	 *
	 * @param idpjson the idpjson
	 */
	public boolean createNewJob(IDPJob idpjson) {

		try {
			logger.debug("Creating  job");
			String jenkinsurl = configmanager.getJenkinsurl();
			String userid = configmanager.getJenkinsuserid();
			String password = configmanager.getJenkinspassword();
			String sharedPath = configmanager.getSharePath();
			String pipelineScriptPath = null;

			pipelineScriptPath = configmanager.getPipelineScriptPath();

			JenkinsServer jenkinsServer = new JenkinsServer(new URI(jenkinsurl), userid, password);
			JtwigTemplate template = JtwigTemplate.classpathTemplate(JOB_TEMPLATE_PATH);

			JtwigModel model = JtwigModel.newModel().with(JOB_SHARED_PATH, sharedPath)
					.with(PIPELINE_SCRIPT_PATH, pipelineScriptPath).with("", "forIDPRearch");

			jenkinsServer.createJob(idpjson.getBasicInfo().getApplicationName() + "_"
					+ idpjson.getBasicInfo().getPipelineName() + "_Main", template.render(model), true);

		}

		catch (Exception ex) {
			logger.error("Some error occured while creating  job", ex);
			logger.info(ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Get build logs for specific job and build number
	 *
	 * @param jobName     name of the job
	 * @param buildNumber job build number
	 * @return String with file contents that can be saved or piped to socket
	 * @throws DiggerClientException when problem with fetching artifacts from
	 *                               jenkins
	 */
	public String getBuildLogs(JenkinsServer jenkins, String jobName, int buildNumber) {
		try {
			JobWithDetails job = jenkins.getJob(jobName);
			if (job == null) {
				return null;
			}
			Build build = job.getBuildByNumber(buildNumber);
			BuildWithDetails buildWithDetails = build.details();
			return buildWithDetails.getConsoleOutputText();
		} catch (IOException e) {
			logger.error("Problem when fetching logs for {0} {1}", jobName, buildNumber, e);

		} catch (NullPointerException e) {
			logger.error("Cannot fetch job from jenkins {0}", jobName, e);
		}
		return null;
	}

	/**
	 * See the documentation in {@link DiggerClient#build(String, long)}
	 *
	 * @param jenkinsServer Jenkins server client
	 * @param jobName       name of the job
	 * @param timeout       timeout
	 * @return the build status
	 * @throws IOException          if connection problems occur during connecting
	 *                              to Jenkins
	 * @throws InterruptedException if a problem occurs during sleeping between
	 *                              checks
	 * @see DiggerClient#build(String, long)
	 */
	public BuildStatus build(IDPJob idpjson, long timeout) throws IOException, InterruptedException {
		final long whenToTimeout = System.currentTimeMillis() + timeout;

		String jobName = idpjson.getBasicInfo().getApplicationName();
		logger.debug("Going to build job with name: {}", idpjson);
		logger.debug("Going to timeout in {} msecs if build didn't start executing", timeout);
		String jenkinsurl = configmanager.getJenkinsurl();
		String userid = configmanager.getJenkinsuserid();
		String password = configmanager.getJenkinspassword();

		JenkinsServer jenkinsServer = null;
		try {
			jenkinsServer = new JenkinsServer(new URI(jenkinsurl), userid, password);

		} catch (URISyntaxException e) {

			logger.error(e.getMessage(), e);
			throw new IllegalArgumentException("Unable to get the jenkins server '" + jobName + "'");
		}
		JobWithDetails job;
		try {
			job = jenkinsServer.getJob(jobName);
		} catch (NullPointerException e) {
			logger.debug("Unable to find job for name '{}'", jobName);
			throw new IllegalArgumentException("Unable to find job for name '" + jobName + "'", e);
		}

		final QueueReference queueReference = job.build(true);

		if (queueReference == null) {
			// this is probably an implementation problem we have here
			logger.debug("Queue reference cannot be null!");
			throw new IllegalStateException("Queue reference cannot be null!");
		}
		logger.debug("Build triggered; queue item reference: {}", queueReference.getQueueItemUrlPart());

		// wait for N seconds, then fetch the queue item.
		// do it until we have an executable.
		// we would have an executable when the build leaves queue and starts
		// building.

		logger.debug("Going to sleep {} msecs", firstCheckDelay);
		Thread.sleep(firstCheckDelay);

		QueueItem queueItem;
		while (true) {
			queueItem = jenkinsServer.getQueueItem(queueReference);
			logger.debug("Queue item : {}", queueItem);

			if (queueItem == null) {
				// this is probably an implementation problem we have here
				logger.debug("Queue item cannot be null!");
				throw new IllegalStateException("Queue item cannot be null!");
			}

			logger.debug("Build item cancelled:{}, blocked:{}, buildable:{}, stuck:{}", queueItem.isCancelled(),
					queueItem.isBlocked(), queueItem.isBuildable(), queueItem.isStuck());

			if (queueItem.isCancelled()) {
				logger.debug("Queue item is cancelled. Returning CANCELLED_IN_QUEUE");
				return new BuildStatus(BuildStatus.State.CANCELLED_IN_QUEUE.toString(), -1);
			} else if (queueItem.isStuck()) {
				logger.debug("Queue item is stuck. Returning STUCK_IN_QUEUE");
				return new BuildStatus(BuildStatus.State.STUCK_IN_QUEUE.toString(), -1);
			}

			// do not return -1 if blocked.
			// we will wait until it is unblocked.

			final Executable executable = queueItem.getExecutable();

			if (executable != null) {
				logger.debug("Build has an executable. Returning build number: {}", executable.getNumber());
				return getBuildStatus(jenkinsServer, jobName, executable.getNumber().intValue(), whenToTimeout);

			} else {
				logger.debug("Build did not start executing yet.");
				if (whenToTimeout > System.currentTimeMillis()) {
					logger.debug("Timeout period has not exceeded yet. Sleeping for {} msecs", pollPeriod);
					Thread.sleep(pollPeriod);
				} else {
					logger.debug("Timeout period has exceeded. Returning TIMED_OUT.");
					return new BuildStatus(BuildStatus.State.TIMED_OUT.toString(), -1);
				}
			}
		}
	}

	/**
	 * Returns build status
	 * 
	 * @param server
	 * @param jobName
	 * @param buildnumber
	 * @param whenToTimeout
	 * @return BuildStatus
	 */
	public BuildStatus getBuildStatus(JenkinsServer server, String jobName, int buildnumber, long whenToTimeout) {
		try {
			Thread.sleep(20000l);

			JobWithDetails job = server.getJob(jobName);
			Build buildByNumber = null;
			if (job.getNextBuildNumber() == 1) {
				buildByNumber = job.getFirstBuild();
				logger.info("buildByNumber.getNumber()" + buildByNumber.getNumber());
			} else {
				buildByNumber = job.getLastBuild();
				logger.info("buildByNumber.getNumber()" + buildByNumber.getNumber());
			}

			logger.debug("buildByNumber.details().isBuilding(): " + buildByNumber.details().isBuilding());
			while (buildByNumber.details().isBuilding()) {
				logger.debug("buildByNumber.details().isBuilding(): " + buildByNumber.details().isBuilding());
				logger.debug("Thread sleeps for " + pollPeriod);
				logger.debug("Thread sleeps for " + pollPeriod);
				Thread.sleep(pollPeriod);
				logger.debug("Thread woke up for " + pollPeriod);
				logger.debug("Thread woke up for " + pollPeriod);

			}
			logger.info(
					"buildByNumber.details().getResult().toString()" + buildByNumber.details().getResult().toString());
			return new BuildStatus(buildByNumber.details().getResult().toString(), buildByNumber.getNumber());
		} catch (IOException e) {

			logger.error(e.getMessage(), e);

		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);

		}
		return null;

	}

}