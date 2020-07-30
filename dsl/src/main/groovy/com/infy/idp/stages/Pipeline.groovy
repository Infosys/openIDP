/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.stages

import com.infy.idp.creators.*
import com.infy.idp.tools.scm.*
import com.infy.idp.utils.*
import javaposse.jobdsl.dsl.DslFactory
import groovy.json.*
import com.infy.idp.plugins.triggers.ParameterizedScheduler



/**
 *
 * This class has the method to create the pipeline job
 *
 */

class Pipeline {

    String basepath

    /*
     * This method is used to call other child jobs to create a pipeline job with all the configuration
     */

    void run(DslFactory factory, jsonData, pipelineScriptPath) {
        basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
        pipelineJobCreation(factory, pipelineScriptPath,jsonData)
    }

    /*
     * this method is used to add string or password parameters in jenkins job
     */

    void addProperties(context) {
        context.with {
            configure {
                it / 'properties' / 'hudson.model.ParametersDefinitionProperty' / 'parameterDefinitions' {
                    PropertiesAdder.addStringParam(delegate, 'JSON_Input', 'NA', 'Json Input for details')
                    PropertiesAdder.addStringParam(delegate, 'JiraStoryID', 'NA', 'JiraStoryID for Jira Trigger')
                    PropertiesAdder.addStringParam(delegate, 'ARTIFACT_NAME', 'NA', '')
                }
            }
        }
    }

    void addBuildInterval(context, jsonData) {
        context.with {

            triggers {
                String scheduleStr = "";
                if (jsonData.basicInfo.customTriggerInterval && jsonData.basicInfo.customTriggerInterval != null && jsonData.basicInfo.customTriggerInterval.interval.size() > 0) {
                    def buildIntervalJson = jsonData.basicInfo.customTriggerInterval;
                    for (int i = 0; i < buildIntervalJson.interval.size(); i++) {
                        def slot = buildIntervalJson.interval[i]

                        String hourVal = slot.time;
                        String minVal = slot.minute;
                        String jsonInput = new JsonBuilder(slot.details).toString();

                        if (hourVal != null && hourVal.length() <= 2 && isNumeric(hourVal)) {
                            def intValue = Integer.parseInt(hourVal);
                            if (intValue >= 0 && intValue <= 23) {
                                hourVal = intValue
                            } else {
                                hourVal = "";
                            }
                        }

                        if (minVal != null && minVal.length() <= 2 && isNumeric(minVal)) {
                            def intValue = Integer.parseInt(minVal);
                            if (intValue >= 0 && intValue <= 59) {
                                minVal = intValue
                            } else {
                                minVal = "";
                            }
                        }

                        def type = slot.type
                        def listDate = slot.date
                        def listWeek = slot.week
                        scheduleStr = scheduleStr + setCustomSchedule(minVal, hourVal, type, listDate, listWeek, jsonInput);
                    }
                }
                ParameterizedScheduler para = new ParameterizedScheduler()
                para.add(delegate, scheduleStr);
            }


        }
    }

    /*
     * this method is used to add the PollScm configurations
     * It schedules the job to run on a particular time
     */

    void addPollScm(context, jsonData) {
        def buildIntervalJson = jsonData.basicInfo.buildInterval;
        //TriggersAdder triggersAdder = new TriggersAdder();
        String scheduleStr = "";
        if (buildIntervalJson.buildAtSpecificInterval != null && buildIntervalJson.buildAtSpecificInterval.equalsIgnoreCase('on')) {

            scheduleStr = setSchedule(buildIntervalJson.buildInterval, buildIntervalJson.buildIntervalValue)
        } else if (buildIntervalJson.buildAtSpecificInterval != null && buildIntervalJson.buildAtSpecificInterval.equalsIgnoreCase('off')) {
            if (buildIntervalJson.event != null && buildIntervalJson.event.size() > 0) {

                for (int i = 0; i < buildIntervalJson.event.size(); i++) {
                    def slot = buildIntervalJson.event[i];

                    String hourVal = slot.time;
                    String minVal = slot.minute;

                    if (hourVal != null && hourVal.length() <= 2 && isNumeric(hourVal)) {
                        def intValue = Integer.parseInt(hourVal);
                        if (intValue >= 0 && intValue <= 23) {
                            hourVal = intValue;
                        } else {
                            hourVal = "";
                        }
                    }

                    if (minVal != null && minVal.length() <= 2 && isNumeric(minVal)) {
                        def intValue = Integer.parseInt(minVal);
                        if (intValue >= 0 && intValue <= 59) {
                            minVal = intValue;
                        } else {
                            minVal = "";
                        }
                    }

                    def type = slot.type
                    def listDate = slot.date
                    def listWeek = slot.week

                    scheduleStr = scheduleStr + setCustomSchedule(minVal, hourVal, type, listDate, listWeek, jsonInput);
                }
            }

        }
        Boolean isPollSCM = false
        if (buildIntervalJson.pollSCM != null && buildIntervalJson.pollSCM.equalsIgnoreCase('on')) {
            isPollSCM = true
        }

        if (buildIntervalJson.buildInterval || (buildIntervalJson.event && buildIntervalJson.event != null && buildIntervalJson.event.size() > 0)) {

            triggersAdder.addTriggers(context, scheduleStr, isPollSCM)
        }

    }

    /*
     * This method is used to set the schedule option
     * Job can be scheduled to run every day, week or month at cerrtain time
     */

    public String setSchedule(String buildInterval, String buildIntervalValue) {
        String scheduleStr = 'H'
        //For handling special case of every hour build
        if ((buildInterval.toString().equalsIgnoreCase(Constants.HOURS) && Integer.parseInt(buildIntervalValue) == 1)) {
            buildIntervalValue = "60"
            buildInterval = Constants.MINUTES
        }
        if (buildInterval.toString().equalsIgnoreCase(Constants.HOURS))//Hours
            scheduleStr += ' H/' + buildIntervalValue + ' * * *'
        else if (buildInterval.toString().equalsIgnoreCase(Constants.MINUTES))//mins
            scheduleStr += '/' + buildIntervalValue + ' * * * *'
        else if (buildInterval.toString().equalsIgnoreCase(Constants.DAY_OF_MONTH))//Days of Month
            scheduleStr += ' H ' + buildIntervalValue + ' * *'
        else if (buildInterval.toString().equalsIgnoreCase(Constants.MONTH))//Months
            scheduleStr += ' H H ' + buildIntervalValue + ' *'
        else if (buildInterval.toString().equalsIgnoreCase(Constants.DAY_OF_WEEK))//day of week
            scheduleStr += ' H * * ' + buildIntervalValue

        return scheduleStr;


    }

    /*
     * This metod is used to set the day of week or month on which the job should trigger
     */

    public String setCustomSchedule(String min, String hour, String type, List<String> listDate, List<String> listWeek, String jsonInput) {
        Map<String, Integer> m = new HashMap();
        m.put("Sun", 0)
        m.put("Mon", 1)
        m.put("Tue", 2)
        m.put("Wed", 3)
        m.put("Thu", 4)
        m.put("Fri", 5)
        m.put("Sat", 6)

        String scheduleStr = '';
        //For handling special case of every hour build
        if (type != null && type.equalsIgnoreCase("month")) {
            if (listDate != null && listDate.size() > 0) {
                for (int i = 0; i < listDate.size(); i++) {
                    def val = listDate.get(i);
                    if (val != null && val.length() <= 2 && isNumeric(val)) {
                        def intValue = Integer.parseInt(val);
                        if (intValue >= 1 && intValue <= 31) {
                            scheduleStr = scheduleStr + min + ' ' + hour + ' ' + intValue + ' * * %JSON_Input=' + jsonInput + '\n';
                        }
                    }

                }

            }
        } else if (type != null && type.equalsIgnoreCase("week")) {

            if (listWeek != null && listWeek.size() > 0) {
                for (int i = 0; i < listWeek.size(); i++) {
                    def val = listWeek.get(i);
                    if (val != null && val.length() > 0 && m.containsKey(val)) {
                        def intValue = m.get(val);
                        if (intValue >= 0 && intValue <= 6) {
                            scheduleStr = scheduleStr + min + ' ' + hour + ' * * ' + intValue + ' %JSON_Input=' + jsonInput + '\n';
                        }
                    }

                }

            }
        } else if (type != null && type.equalsIgnoreCase("day")) {
            scheduleStr = scheduleStr + min + ' ' + hour + ' * * * ' + '%JSON_Input=' + jsonInput + '\n';
        }
        return scheduleStr;


    }

    /*
     * This function triggers the Jenkins pipeline from Jira status change
     */
	//Commented as this module is yet not implemented and tested for Daimler. Needs to be uncommented after implementation of webhooks in Jira
   /* void triggerJira(context, jsonData)
	{
		def projectKey = jsonData.basicInfo.buildInterval.projectKey
	
		String jiraChangeLog = 'project = "' + projectKey +'" AND issuetype = Story AND status = Done'
		context.with{
			triggers {
				jiraChangelogTrigger {
						// A build will only be triggered if the updated issues matches this JQL filter.
					jqlFilter(jiraChangeLog)
					// Maps JIRA issue attributes as Jenkins parameters.
					parameterMappings {
						
						issueAttributePathParameterMapping {
							// The Jenkins parameter name that will be filled in with the JIRA attribute value resolves from the attribute path.
							jenkinsParameter("JiraStoryID")
							issueAttributePath("id")
						}
						issueAttributePathParameterMapping {
							// The Jenkins parameter name that will be filled in with the JIRA attribute value resolves from the attribute path.
							jenkinsParameter("JSON_Input")
							issueAttributePath("id")
						}
					}
				}
			}
		}
	}*/

    /*
     * This method is used to create pipeline job in jenkins
     */

    private void pipelineJobCreation(factory, pipelineScriptPath,jsonData) {

        new BasePipelineCreator(
                pipelineName: basepath + '/' + basepath + Constants.PIPELINEJOBNAMEPOSTFIX
        ).create(factory).with {
            addProperties(delegate)

            addBuildInterval(delegate, jsonData)
            environmentVariables {
                propertiesFile('$IDP_WS/CustomJobParm.properties')
                keepBuildVariables(true)
            }
			/*if(jsonData.basicInfo.buildInterval.pollALM && jsonData.basicInfo.buildInterval.pollALM !=null && jsonData.basicInfo.buildInterval.pollALM.equalsIgnoreCase('on') && jsonData.basicInfo.buildInterval.almTool && jsonData.basicInfo.buildInterval.almTool !=null && jsonData.basicInfo.buildInterval.almTool.equalsIgnoreCase('Jira'))
			{
				triggerJira(delegate,jsonData)
			}*/
            definition {
                cps {

                    script(factory.readFileFromWorkspace(pipelineScriptPath))
                    sandbox(false)
                }

            }
        }
    }

    private static Boolean isNumeric(String str) {
        def formatter = java.text.NumberFormat.instance
        def pos = [0] as java.text.ParsePosition
        formatter.parse(str, pos)


        pos.index == str.size()
    }

}
