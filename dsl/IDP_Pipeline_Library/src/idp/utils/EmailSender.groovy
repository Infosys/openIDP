/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package idp.utils

/**
 * This class has the methods to send emails from Jenkins
 */

class EmailSender implements Serializable {
    def script


    EmailSender(script) {
        this.script = script
    }

    /*
     * This method is used for sending the emails regarding status of each stage in pipeline
     */

    def notify(status, jsonData, buildStatus1, deployStatus, testStatus, buildSlave, testSlave, transReq, buildNumber, SAPDeployStatus) {

        if (jsonData["technology"] && jsonData["technology"].equalsIgnoreCase("SapNonCharm") && jsonData["deploy"] && SAPDeployStatus.equalsIgnoreCase("Success")) {
            notifySAPOwners(jsonData, transReq, buildNumber)
        }


        if (jsonData["pipelines"] && jsonData["pipelines"] != null && jsonData["pipelines"].size() > 0) {
            notifyMasterPipelineUsers(status, jsonData)
        } else if (jsonData["technology"] && jsonData["technology"].equalsIgnoreCase("SapNonCharm") && jsonData["notify"] && jsonData["notify"] == "on") {

            notifySAPUsers(buildStatus, jsonData)

        } else {

            notifyUsers(status, jsonData, buildStatus1, deployStatus, testStatus, buildSlave, testSlave)
            if (status.equals('SUCCESS') && jsonData["deploy"] && jsonData["deploy"]["dbDeployPipelineOwners"] && jsonData["deploy"]["dbDeployPipelineOwners"] != "") {

                notifyDBUsers('SUCCESS', jsonData)
            }
        }
    }

    /*
     * This method prepares the subject line for the email and recipients
     */

    def sendEmail(subject, details, emails, cEmails) {
        this.script.emailext(
                subject: subject,
                body: details,
                to: String.join(",", emails),
                cc: String.join(",", cEmails)

        )
    }
	

    /*
     * This method prepares the body of an email
     */

    def notifyUsers(buildStatus, jsonData, buildStatus1, deployStatus, testStatus, buildSlave, testSlave) {

        def subject = "${buildStatus}: For Application: ${jsonData.applicationName} and Pipeline: ${jsonData.pipelineName}"
        def buildSelected = "false"
        def deploySelected = "false"
        def testSelected = "false"
        def env = jsonData.envSelected
        def user = jsonData.userName
        if (env == null || env.length() < 1) {
            env = 'NA'
        }
        if (user == null || user.length() < 1) {
            user = 'None'
        }
        if (jsonData["build"]) {
            buildSelected = "true"
        }
        if (jsonData["deploy"]) {
            deploySelected = "true"
        }
        if (jsonData["testSelected"] && jsonData["testSelected"].equalsIgnoreCase("on")) {
            testSelected = "true"
        }

        def details = """<p class="MsoNormal" style="margin-bottom:12.0pt"><br>
		Dear ${jsonData.userName}, <br>
		<br>
		Thanks for using IDP-Infosys DevOps Platform. <o:p></o:p></p>
		<h3>Parameter Details:<o:p></o:p></h3>
		<table class="MsoNormalTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="width:100.0%;border-collapse:collapse">
		<tbody>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><b><span style="font-family:&quot;Arial&quot;,sans-serif">Parameter<o:p></o:p></span></b></p>
		</td>
		<td style="border:solid #DDDDDD 1.0pt;border-left:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><b><span style="font-family:&quot;Arial&quot;,sans-serif">Details<o:p></o:p></span></b></p>
		</td>
		</tr>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">Build<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">${buildSelected}<o:p></o:p></span></p>
		</td>
		</tr>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">Deploy<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">${deploySelected}<o:p></o:p></span></p>
		</td>
		</tr>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">Test<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">${testSelected}<o:p></o:p></span></p>
		</td>
		</tr>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">USER_INFO<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">${user}<o:p></o:p></span></p>
		</td>
		</tr>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">AGENT FOR BUILD and DEPLOY<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">${buildSlave}<o:p></o:p></span></p>
		</td>
		</tr>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">AGENT FOR TEST<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">${testSlave}<o:p></o:p></span></p>
		</td>
		</tr>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">ENV Selected<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">${env}<o:p></o:p></span></p>
		</td>
		</tr>

		<!--<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">Operations/Modules<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">jsonData.build.module.join(';')<o:p></o:p></span></p>
		</td>
		</tr>-->

		</tbody>
		</table>
		<!--
		<p class="MsoNormal" style="margin-bottom:12.0pt"><o:p>&nbsp;</o:p></p>
		<h3>Execution Details:<o:p></o:p></h3>
		<table class="MsoNormalTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="width:100.0%;border-collapse:collapse">
		<tbody>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><b><span style="font-family:&quot;Arial&quot;,sans-serif">Job<o:p></o:p></span></b></p>
		</td>
		<td style="border:solid #DDDDDD 1.0pt;border-left:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><b><span style="font-family:&quot;Arial&quot;,sans-serif">Status<o:p></o:p></span></b></p>
		</td>
		</tr>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">Build<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;background:lime;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">${buildStatus1}<o:p></o:p></span></p>
		</td>
		</tr>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">Deployment<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;background:lime;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">${deployStatus}<o:p></o:p></span></p>
		</td>
		</tr>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">Test<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;background:lime;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">${testStatus}<o:p></o:p></span></p>
		</td>
		</tr>
		</tbody>
		</table>-->
		<p class="MsoNormal"><br>
		<p>Check console output at "<a href="${this.script.env.BUILD_URL}">${this.script.env.JOB_NAME} [${
            this.script.env.BUILD_NUMBER
        }]</a>"</p>
		<p>Check Pipeline Dashboard "<a href="${jsonData.dashBoardLink}">here</a>"</p>
		<br>
		For any queries please contact IDP admin<br>
		<br>
		Regards,<br>
		IDP Team <o:p></o:p></p>
		</div>
		<br>
		<p style="font-size:8pt; line-height:10pt; font-family: 'Arial',serif; color:#696969;">
		This communication, including any attachments, is confidential. If you are not the intended recipient, you should not read it - please contact me immediately, destroy it, and do not copy or use any part of this communication or disclose anything about it. Thank
		 you. Please note that this communication does not designate an information system for the purposes of the Electronic Transactions Act 2002.</p>"""

        // def emails = new ArrayList<String>();

        /*File f = new File ("D:/email123.html")
        details = details +"\n"+subject
        f.write(details)*/
        def emails = []
        if (jsonData.emailed != null && jsonData.emailed.size() > 0) {
            emails = new ArrayList<String>(Arrays.asList(jsonData.emailed.split(",")))
        }

        emails.removeAll("null", "")
        sendEmail(subject, details, emails, "")

    }

    /*
     * This method is used to prepare body of an email to notify DB owners
     */

    def notifyDBUsers(buildStatus, jsonData) {
        // Default values
        def subject = "${buildStatus}: For Application: ${jsonData.applicationName} and Pipeline: ${jsonData.pipelineName}"

        def image
        if (buildStatus == 'SUCCESS') {
            image = '<img src="https://raw.githubusercontent.com/jenkinsci/jenkins/master/war/src/main/webapp/images/32x32/green.gif">'
        } else {
            image = '<img src="https://raw.githubusercontent.com/jenkinsci/jenkins/master/war/src/main/webapp/images/32x32/red.gif">'
        }
        def details = """<p>""" + image + " " + buildStatus + """: Job '${this.script.env.JOB_NAME} [${
            this.script.env.BUILD_NUMBER
        }]'</p>
		<p>Check console output at "<a href="${this.script.env.BUILD_URL}">${this.script.env.JOB_NAME} [${
            this.script.env.BUILD_NUMBER
        }]</a>"</p>
		<p>Check Pipeline Dashboard "<a href="${jsonData.dashBoardLink}">here</a>"</p>
		<p>Check Sonar Dashboard "<a href="${jsonData.sonardashBoardLink}">here</a>"</p>
		<p>Environment Selected "${jsonData.envSelected}"</p>
		<p>Release Number "${jsonData.releaseNumber}"</p>
		<p>Deployed Artifacts "${jsonData.deploy.deployArtifact.artifactName}"</p>
		<p>DBDeploy Pipelines to be Triggered "${jsonData.deploy.dbDeployPipelineList}"</p>"""

        def emails = new ArrayList<String>(Arrays.asList(jsonData.deploy.dbDeployPipelineOwners.split(",")))
        emails.removeAll("null", "")
        sendEmail(subject, details, emails, "")

    }

	def notifySAPUsers(buildStatus,jsonData) {
		def subject = "${buildStatus}: For Application: ${jsonData.applicationName} and Pipeline: ${jsonData.pipelineName}"

		def image;

		def BUILD_STATUS = 'False'
		def DEPLOY_STATUS = 'False'
		def TEST_STATUS = 'False'
		def TRIGGERED_FUNCTIONALITY =''

		if(jsonData["build"])
		{
			BUILD_STATUS = 'True'
			if(jsonData["build"]["unitTest"])
			{
				TRIGGERED_FUNCTIONALITY +='ABAP '
			}
			if(jsonData["build"]["codeAnalysis"])
			{
				TRIGGERED_FUNCTIONALITY +='SCI '
			}
		}
		if(jsonData["deploy"])
		{
			DEPLOY_STATUS = 'True'
			if(jsonData["deploy"]["deploymentType"].equalsIgnoreCase("Import"))
			{
				TRIGGERED_FUNCTIONALITY += 'Import '
			}
			if(jsonData["deploy"]["deploymentType"].equalsIgnoreCase("Release"))
			{
				TRIGGERED_FUNCTIONALITY += 'Release '
			}
		}
		if(jsonData["cast"])
		{
			TRIGGERED_FUNCTIONALITY += 'Cast '
		}
		if(jsonData["testSelected"])
		{
			TEST_STATUS = 'True'
			TRIGGERED_FUNCTIONALITY +=  'Test '
		}
		def SAPAPPLNAME =''
		SAPAPPLNAME = jsonData["systemName"]
		def WORK_ENV =''
		WORK_ENV = jsonData["envSelected"]
		def USER_INFO =''
		USER_INFO = jsonData["userName"]
		def emailContent = """<p>"""+ image +" "+ buildStatus +""": Job '${this.script.env.JOB_NAME} [${this.script.env.BUILD_NUMBER}]'</p>
		<p>Check console output at "<a href="${this.script.env.BUILD_URL}">${this.script.env.JOB_NAME} [${this.script.env.BUILD_NUMBER}]</a>"</p>"""
		if(buildStatus == 'SUCCESSFUL'){
			image = '<img src="https://raw.githubusercontent.com/jenkinsci/jenkins/master/war/src/main/webapp/images/32x32/green.gif">'
		}
		else{
			image = '<img src="https://raw.githubusercontent.com/jenkinsci/jenkins/master/war/src/main/webapp/images/32x32/red.gif">'
		}
		def details = '''&lt;html&gt;
			&lt;head&gt;&lt;style&gt;
			table {
				font-family: arial, sans-serif;
				border-collapse: collapse;
				width: 100%;
			}
			
			td, th {
				border: 1px solid #dddddd;
				text-align: left;
				padding: 8px;
			}
			
			tr:nth-child(even) {
				background-color: #dddddd;
			}
			&lt;/style&gt;
			&lt;/head&gt;
			&lt;body&gt;
			&lt;br/&gt;
			Dear User,
			&lt;br/&gt;
			&lt;br/&gt;
			
			Thanks for using IDP-Infosys DevOps Platform.
			&lt;br/&gt;
			&lt;br/&gt;
			
			&lt;h3&gt;Parameter Details:&lt;/h3&gt;
			&lt;table&gt;
			  &lt;tr&gt;
				&lt;th style="background-color: #9CB7F3"&gt;Parameter&lt;/th&gt;
				&lt;th style="background-color: #9CB7F3"&gt;Details&lt;/th&gt;
			  &lt;/tr&gt;
			  &lt;tr&gt;
				&lt;td style="background-color: #9CB7F3"&gt;Build&lt;/td&gt;
				&lt;td&gt;${BUILD_STATUS}&lt;/td&gt;
			  &lt;/tr&gt;
			  &lt;tr&gt;
				&lt;td style="background-color: #9CB7F3"&gt;Deploy&lt;/td&gt;
				&lt;td&gt;${DEPLOY_STATUS}&lt;/td&gt;
			  &lt;/tr&gt;
			  &lt;tr&gt;
				&lt;td style="background-color: #9CB7F3"&gt;Test&lt;/td&gt;
				&lt;td&gt;${TEST_STATUS}&lt;/td&gt;
			  &lt;/tr&gt;		  
			  &lt;tr&gt;
				&lt;td style="background-color: #9CB7F3"&gt;System Name&lt;/td&gt;
				&lt;td&gt;${SAPAPPLNAME}&lt;/td&gt;
			  &lt;/tr&gt;
			   &lt;tr&gt;
				&lt;td style="background-color: #9CB7F3"&gt;Transport Request&lt;/td&gt;
				&lt;td&gt;${SAPTRNSID}&lt;/td&gt;
			  &lt;/tr&gt;
			  &lt;tr&gt;
				&lt;td style="background-color: #9CB7F3"&gt;ENV Selected&lt;/td&gt;
				&lt;td&gt;${WORK_ENV}&lt;/td&gt;
			  &lt;/tr&gt;
			  &lt;tr&gt;
				&lt;td style="background-color: #9CB7F3"&gt;Operations Triggered&lt;/td&gt;
				&lt;td&gt;'''+TRIGGERED_FUNCTIONALITY+'''&lt;/td&gt;
			  &lt;/tr&gt;
			 &lt;tr&gt;
				&lt;td style="background-color: #9CB7F3"&gt;User Info&lt;/td&gt;
				&lt;td&gt;${USER_INFO}&lt;/td&gt;
			  &lt;/tr&gt;
			 $EMAILPART
			&lt;/table&gt;
			&lt;br/&gt;&lt;br/&gt;
						
			&lt;h3&gt;Execution Details:&lt;/h3&gt;
			&lt;table&gt;
			  &lt;tr&gt;
				
			  &lt;/tr&gt;'''+emailContent
		def emails = new ArrayList<String>(Arrays.asList(jsonData.deploy.envOwner.split(",")))
		emails.removeAll("null","")
		sendEmail(subject, details, emails,"")

	}
	
	def notifySAPOwners(jsonData,transReq, buildNumber) {
		def deployType = jsonData["deploy"]["deploymentType"]
		def srcLandscapeName = jsonData["lanscapeName"]
		def srcLandscapeType = jsonData["landscapeType"]
		def destLandscapeName = jsonData["deploy"]["destLandscape"]
		def copyStatus
		if(deployType.equalsIgnoreCase("release")) {
			copyStatus = jsonData["copyTR"]
		}
		else {
			copyStatus = '-'
		}
		def subject = "${jsonData.applicationName}_${jsonData.pipelineName}: #${buildNumber} : Transport Request(s) ready for import from ${srcLandscapeName}"


		def details="""<p class="MsoNormal" style="margin-bottom:12.0pt"><br>
		Dear User, <br>
		<br>
		New Transport Request(s) added to import queue. Please find the details below. <o:p></o:p></p>
		<table class="MsoNormalTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="width:100.0%;border-collapse:collapse">
		<tbody>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><b><span style="font-family:&quot;Arial&quot;,sans-serif">Parameter<o:p></o:p></span></b></p>
		</td>
		<td style="border:solid #DDDDDD 1.0pt;border-left:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><b><span style="font-family:&quot;Arial&quot;,sans-serif">Details<o:p></o:p></span></b></p>
		</td>
		</tr>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">Deploy Operation<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">${deployType}<o:p></o:p></span></p>
		</td>
		</tr>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">Transport Request(s)<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">${transReq}<o:p></o:p></span></p>
		</td>
		</tr>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">Landscape Name (Source)<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">${srcLandscapeName} (${srcLandscapeType})<o:p></o:p></span></p>
		</td>
		</tr>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">Landscape Name (Destinations)<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">${destLandscapeName}<o:p></o:p></span></p>
		</td>
		</tr>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">Transport of Copies<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">${copyStatus}<o:p></o:p></span></p>
		</td>
		</tr>
		<tr>
		<td style="border:solid #DDDDDD 1.0pt;border-top:none;background:#9CB7F3;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">User<o:p></o:p></span></p>
		</td>
		<td style="border-top:none;border-left:none;border-bottom:solid #DDDDDD 1.0pt;border-right:solid #DDDDDD 1.0pt;padding:6.0pt 6.0pt 6.0pt 6.0pt">
		<p class="MsoNormal"><span style="font-family:&quot;Arial&quot;,sans-serif">${jsonData.userName}<o:p></o:p></span></p>
		</td>
		</tr>
		</tbody>
		</table>

		<p class="MsoNormal"><br>
		<p>Check console output at "<a href="${this.script.env.BUILD_URL}">${this.script.env.JOB_NAME} [${this.script.env.BUILD_NUMBER}]</a>"</p>
		<p>Check Pipeline Dashboard "<a href="${jsonData.dashBoardLink }">here</a>"</p>
		<br>

		For any queries please contact IDP admin<br>

		<br>
		Regards,<br>
		IDP Team <o:p></o:p></p>
		</div>
		<br>
		<p style="font-size:8pt; line-height:10pt; font-family: 'Arial',serif; color:#696969;">
		This communication, including any attachments, is confidential. If you are not the intended recipient, you should not read it - please contact me immediately, destroy it, and do not copy or use any part of this communication or disclose anything about it. Thank
		 you. Please note that this communication does not designate an information system for the purposes of the Electronic Transactions Act 2002.</p>"""

		def emails = new ArrayList<String>(Arrays.asList(jsonData.deploy.destEnvOwner.split(",")))
		def cEmailIds = jsonData.deploy.allEnvOwner + jsonData.releaseManagers
		def cEmails = new ArrayList<String>(Arrays.asList(cEmailIds.split(",")))
		emails.removeAll("null","")
		cEmails.removeAll("null","")
		sendEmail(subject, details, emails, cEmails)
	}
}
