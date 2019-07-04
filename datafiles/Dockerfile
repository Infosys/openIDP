ARG TEMP_DATAFILES_IMAGE
FROM ${TEMP_DATAFILES_IMAGE}

COPY certs/cacerts_org certs/certs_gen.sh /datafiles/certs/
COPY dsldata /datafiles/dsldata
COPY proxy /datafiles/proxy
COPY grafanadata/data/dashboards /datafiles/grafanadata/data/dashboards
COPY grafanadata/data/plugins /datafiles/grafanadata/data/plugins
COPY grafanadata/provisioning /datafiles/grafanadata/provisioning
COPY grafanadata/grafana.ini /datafiles/grafanadata/
COPY jenkinsdata/CUSTOM_TOOLS /datafiles/jenkinsdata/CUSTOM_TOOLS 
COPY jenkinsdata/nodes/TG_MASTER_SLAVE /datafiles/jenkinsdata/nodes/TG_MASTER_SLAVE
COPY jenkinsdata/plugins /datafiles/jenkinsdata/plugins
COPY jenkinsdata/userContent /datafiles/jenkinsdata/userContent
COPY jenkinsdata/users/idpadmin /datafiles/jenkinsdata/users/idpadmin
COPY jenkinsdata/com.cloudbees.jenkins.plugins.customtools.CustomTool.xml jenkinsdata/com.michelin.cio.hudson.plugins.copytoslave.CopyToSlaveBuildWrapper.xml jenkinsdata/config.xml jenkinsdata/hudson.plugins.emailext.ExtendedEmailPublisher.xml jenkinsdata/hudson.plugins.sonar.SonarRunnerInstallation.xml jenkinsdata/hudson.plugins.timestamper.TimestamperConfig.xml jenkinsdata/hudson.tasks.Ant.xml jenkinsdata/hudson.tasks.Maven.xml jenkinsdata/javaposse.jobdsl.plugin.GlobalJobDslSecurityConfiguration.xml jenkinsdata/jenkins.CLI.xml jenkinsdata/jenkins.model.JenkinsLocationConfiguration.xml jenkinsdata/jenkins.security.UpdateSiteWarningsConfiguration.xml jenkinsdata/org.jenkins.ci.plugins.xframe_filter.XFrameFilterPageDecorator.xml jenkinsdata/org.jenkinsci.plugins.p4.PerforceScm.xml jenkinsdata/org.jenkinsci.plugins.testresultsanalyzer.TestResultsAnalyzerExtension.xml jenkinsdata/org.jenkinsci.plugins.workflow.libs.GlobalLibraries.xml jenkinsdata/scriptApproval.xml /datafiles/jenkinsdata/
COPY keycloak /datafiles/keycloak
COPY postgresinit /datafiles/postgresinit
COPY ansible.cfg config.sh config.yml server.xml /datafiles/