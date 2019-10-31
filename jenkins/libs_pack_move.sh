#!/bin/sh

if find "/jenkinsdata/" -mindepth 1 -print -prune 2>/dev/null | grep -q .; then
	echo "jenkinsdata directory already filled. Will attempt to start jenkins with old security settings"
else
	echo "New installation detected"
	echo "Copying Default Security Configuration to Jenkins Data folder "
	cp -rpf /jenkins/home_files/config.xml /jenkinsdata/config.xml
fi

chmod -fR 0777 ./
echo "Packing Custom Tools"
cp -rpf jenkins_org.war jenkins.war
zip -rv jenkins.war jenkins/WEB-INF/lib/scheduleutility.jar

zip TestNg.zip TestNg/*
mv TestNg.zip ./home_files/CUSTOM_TOOLS/

zip junit-4.10.zip junit-4.10.jar
mv junit-4.10.zip ./home_files/CUSTOM_TOOLS/

zip nuget.zip nuget.exe
mv nuget.zip ./home_files/CUSTOM_TOOLS/

mkdir -p checkstyle-6.17/config
cp -rpf custom_tools/checkstyle/config/* checkstyle-6.17/config/
zip checkstyle-6.17-bin.zip checkstyle-6.17/* checkstyle-noframes-severity-sorted.xsl
mv checkstyle-6.17-bin.zip ./home_files/CUSTOM_TOOLS/

zip Selenium.zip Selenium/*
mv Selenium.zip ./home_files/CUSTOM_TOOLS/

cd role_mod
cp /jenkins/home_files/plugins/role-strategy.hpi.org ./role-strategy.hpi
rm -rf /jenkins/home_files/plugins/role-strategy.hpi
rm -rf role
mkdir -p role
unzip role-strategy.hpi -d ./role
zip -rv role/WEB-INF/lib/role-strategy.jar com/michelin/cio/hudson/plugins/rolestrategy/*.class
cd role
zip -r /jenkins/home_files/plugins/role-strategy.hpi ./*

echo "Copying Files to Jenkins Home Directory"

cp -rpf /jenkins/home_files/CUSTOM_TOOLS /jenkinsdata
cp -rpf /jenkins/home_files/nodes /jenkinsdata
cp -rpf /jenkins/home_files/plugins /jenkinsdata
cp -rpf /jenkins/home_files/userContent /jenkinsdata
cp -rpf /jenkins/home_files/users /jenkinsdata
cp -rpf /jenkins/home_files/com.cloudbees.jenkins.plugins.customtools.CustomTool.xml /jenkinsdata/com.cloudbees.jenkins.plugins.customtools.CustomTool.xml
cp -rpf /jenkins/home_files/com.michelin.cio.hudson.plugins.copytoslave.CopyToSlaveBuildWrapper.xml /jenkinsdata/com.michelin.cio.hudson.plugins.copytoslave.CopyToSlaveBuildWrapper.xml
cp -rpf /jenkins/home_files/hudson.plugins.emailext.ExtendedEmailPublisher.xml /jenkinsdata/hudson.plugins.emailext.ExtendedEmailPublisher.xml
cp -rpf /jenkins/home_files/hudson.plugins.sonar.SonarRunnerInstallation.xml /jenkinsdata/hudson.plugins.sonar.SonarRunnerInstallation.xml
cp -rpf /jenkins/home_files/hudson.plugins.timestamper.TimestamperConfig.xml /jenkinsdata/hudson.plugins.timestamper.TimestamperConfig.xml
cp -rpf /jenkins/home_files/hudson.tasks.Ant.xml /jenkinsdata/hudson.tasks.Ant.xml
cp -rpf /jenkins/home_files/hudson.tasks.Maven.xml /jenkinsdata/hudson.tasks.Maven.xml
cp -rpf /jenkins/home_files/javaposse.jobdsl.plugin.GlobalJobDslSecurityConfiguration.xml /jenkinsdata/javaposse.jobdsl.plugin.GlobalJobDslSecurityConfiguration.xml
cp -rpf /jenkins/home_files/jenkins.CLI.xml /jenkinsdata/jenkins.CLI.xml
cp -rpf /jenkins/home_files/jenkins.model.JenkinsLocationConfiguration.xml /jenkinsdata/jenkins.model.JenkinsLocationConfiguration.xml
cp -rpf /jenkins/home_files/jenkins.security.UpdateSiteWarningsConfiguration.xml /jenkinsdata/jenkins.security.UpdateSiteWarningsConfiguration.xml
cp -rpf /jenkins/home_files/org.jenkins.ci.plugins.xframe_filter.XFrameFilterPageDecorator.xml /jenkinsdata/org.jenkins.ci.plugins.xframe_filter.XFrameFilterPageDecorator.xml
cp -rpf /jenkins/home_files/org.jenkinsci.plugins.p4.PerforceScm.xml /jenkinsdata/org.jenkinsci.plugins.p4.PerforceScm.xml
cp -rpf /jenkins/home_files/org.jenkinsci.plugins.testresultsanalyzer.TestResultsAnalyzerExtension.xml /jenkinsdata/org.jenkinsci.plugins.testresultsanalyzer.TestResultsAnalyzerExtension.xml
cp -rpf /jenkins/home_files/org.jenkinsci.plugins.workflow.libs.GlobalLibraries.xml /jenkinsdata/org.jenkinsci.plugins.workflow.libs.GlobalLibraries.xml
cp -rpf /jenkins/home_files/scriptApproval.xml /jenkinsdata/scriptApproval.xml
cp -rpf /jenkins/home_files/config.xml /jenkinsdata/config.xml