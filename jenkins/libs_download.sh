#!/bin/sh

smart_download(){
	md5line=$(cat checksum | grep $1)
	md5gen=$(md5sum $1)
	if [ "$md5gen" = "$md5line" ]; then
		echo "File already exists at $1"
	else
		echo "Downloading File at $1"
		wget $WGET_PROXY -O  $1 $2 --no-check-certificate
	fi
}

mkdir -p home_files/plugins home_files/CUSTOM_TOOLS
mkdir -p jenkins/WEB-INF/lib

echo "Downloading and Preparing required Jenkins version"
smart_download jenkins_org.war https://repo.jenkins-ci.org/releases/org/jenkins-ci/main/jenkins-war/2.164.3/jenkins-war-2.164.3.war
yes | \cp -rf /jenkins/custom_tools/SchedulerUtility/target/scheduleutility.jar /jenkins/jenkins/WEB-INF/lib/
	
echo "Downloading Custom Tools"
mkdir -p TestNg
smart_download TestNg/testng.jar http://central.maven.org/maven2/org/testng/testng/6.14.3/testng-6.14.3.jar

smart_download junit-4.10.jar http://central.maven.org/maven2/junit/junit/4.10/junit-4.10.jar

smart_download nuget.exe https://dist.nuget.org/win-x86-commandline/v4.4.1/nuget.exe

mkdir -p checkstyle-6.17
smart_download checkstyle-noframes-severity-sorted.xsl https://raw.githubusercontent.com/checkstyle/contribution/master/xsl/checkstyle-noframes-severity-sorted.xsl
smart_download checkstyle-6.17/antlr-2.7.7.jar http://central.maven.org/maven2/antlr/antlr/2.7.7/antlr-2.7.7.jar
smart_download checkstyle-6.17/antlr4-runtime-4.5.2-1.jar http://central.maven.org/maven2/org/antlr/antlr4-runtime/4.5.2-1/antlr4-runtime-4.5.2-1.jar
smart_download checkstyle-6.17/checkstyle-6.17.jar http://central.maven.org/maven2/com/puppycrawl/tools/checkstyle/6.17/checkstyle-6.17.jar
smart_download checkstyle-6.17/checkstyle-6.17-all.jar https://github.com/checkstyle/checkstyle/releases/download/checkstyle-6.17/checkstyle-6.17-all.jar
smart_download checkstyle-6.17/commons-beanutils-1.9.3.jar http://central.maven.org/maven2/commons-beanutils/commons-beanutils/1.9.3/commons-beanutils-1.9.3.jar
smart_download checkstyle-6.17/commons-cli-1.3.1.jar http://central.maven.org/maven2/commons-cli/commons-cli/1.3.1/commons-cli-1.3.1.jar
smart_download checkstyle-6.17/commons-collections-3.2.1.jar http://central.maven.org/maven2/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar
smart_download checkstyle-6.17/commons-logging-1.1.1.jar http://central.maven.org/maven2/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar
smart_download checkstyle-6.17/guava-19.0.jar http://central.maven.org/maven2/com/google/guava/guava/19.0/guava-19.0.jar

mkdir -p Selenium
smart_download Selenium/selenium-java-2.43.0.jar https://repo1.maven.org/maven2/org/seleniumhq/selenium/selenium-java/2.43.0/selenium-java-2.43.0.jar
smart_download Selenium/selenium-server-standalone-2.43.1.jar https://repo.jenkins-ci.org/releases/org/seleniumhq/selenium/selenium-server-standalone/2.43.1/selenium-server-standalone-2.43.1.jar

yes | \cp -rf ./custom_tools/DevopsJsonConv/target/*.zip ./home_files/CUSTOM_TOOLS
yes | \cp -rf ./custom_tools/ReportFetchUtil/target/*.zip ./home_files/CUSTOM_TOOLS
yes | \cp -rf ./custom_tools/MetricsProcessor/target/*.zip ./home_files/CUSTOM_TOOLS
smart_download home_files/CUSTOM_TOOLS/apache-ant-1.9.6.zip https://archive.apache.org/dist/ant/binaries/apache-ant-1.9.6-bin.zip 
smart_download home_files/CUSTOM_TOOLS/sonar-runner-2.4.zip http://repo1.maven.org/maven2/org/codehaus/sonar/runner/sonar-runner-dist/2.4/sonar-runner-dist-2.4.zip 
smart_download home_files/CUSTOM_TOOLS/cobertura-2.0.3.zip https://sourceforge.net/projects/cobertura/files/cobertura/2.0.3/cobertura-2.0.3-bin.zip/download 
smart_download home_files/CUSTOM_TOOLS/findbugs-3.0.1.zip http://prdownloads.sourceforge.net/findbugs/findbugs-3.0.1.zip?download

echo "Downloading Jenkins Plugins"
smart_download home_files/plugins/active-directory.hpi https://updates.jenkins-ci.org/download/plugins/active-directory/2.0/active-directory.hpi 
smart_download home_files/plugins/analysis-core.hpi https://updates.jenkins-ci.org/download/plugins/analysis-core/1.93/analysis-core.hpi 
smart_download home_files/plugins/android-emulator.hpi https://updates.jenkins-ci.org/download/plugins/android-emulator/2.15/android-emulator.hpi 
smart_download home_files/plugins/android-lint.hpi https://updates.jenkins-ci.org/download/plugins/android-lint/2.4/android-lint.hpi 
smart_download home_files/plugins/antisamy-markup-formatter.hpi https://updates.jenkins-ci.org/download/plugins/antisamy-markup-formatter/1.5/antisamy-markup-formatter.hpi 
smart_download home_files/plugins/ant.hpi https://updates.jenkins-ci.org/download/plugins/ant/1.5/ant.hpi 
smart_download home_files/plugins/artifactdeployer.hpi https://updates.jenkins-ci.org/download/plugins/artifactdeployer/0.33/artifactdeployer.hpi 
smart_download home_files/plugins/artifactory.hpi https://updates.jenkins.io/download/plugins/artifactory/2.13.1/artifactory.hpi 
smart_download home_files/plugins/authentication-tokens.hpi https://updates.jenkins-ci.org/download/plugins/authentication-tokens/1.3/authentication-tokens.hpi 
smart_download home_files/plugins/authorize-project.hpi https://updates.jenkins-ci.org/download/plugins/authorize-project/1.3.0/authorize-project.hpi 
smart_download home_files/plugins/bitbucket.hpi https://updates.jenkins-ci.org/download/plugins/bitbucket/1.1.5/bitbucket.hpi 
smart_download home_files/plugins/blueocean-autofavorite.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-autofavorite/1.0.0/blueocean-autofavorite.hpi 
smart_download home_files/plugins/blueocean-commons.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-commons/1.1.1/blueocean-commons.hpi 
smart_download home_files/plugins/blueocean-config.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-config/1.1.1/blueocean-config.hpi 
smart_download home_files/plugins/blueocean-dashboard.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-dashboard/1.1.1/blueocean-dashboard.hpi 
smart_download home_files/plugins/blueocean-display-url.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-display-url/2.0/blueocean-display-url.hpi 
smart_download home_files/plugins/blueocean-events.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-events/1.1.1/blueocean-events.hpi 
smart_download home_files/plugins/blueocean-github-pipeline.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-github-pipeline/1.1.1/blueocean-github-pipeline.hpi 
smart_download home_files/plugins/blueocean-git-pipeline.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-git-pipeline/1.1.1/blueocean-git-pipeline.hpi 
smart_download home_files/plugins/blueocean-i18n.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-i18n/1.1.1/blueocean-i18n.hpi 
smart_download home_files/plugins/blueocean.hpi https://updates.jenkins-ci.org/download/plugins/blueocean/1.1.1/blueocean.hpi 
smart_download home_files/plugins/blueocean-jwt.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-jwt/1.1.1/blueocean-jwt.hpi 
smart_download home_files/plugins/blueocean-personalization.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-personalization/1.1.1/blueocean-personalization.hpi 
smart_download home_files/plugins/blueocean-pipeline-api-impl.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-pipeline-api-impl/1.1.1/blueocean-pipeline-api-impl.hpi 
smart_download home_files/plugins/blueocean-pipeline-editor.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-pipeline-editor/0.2.0/blueocean-pipeline-editor.hpi 
smart_download home_files/plugins/blueocean-pipeline-scm-api.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-pipeline-scm-api/1.1.1/blueocean-pipeline-scm-api.hpi 
smart_download home_files/plugins/blueocean-rest-impl.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-rest-impl/1.1.1/blueocean-rest-impl.hpi 
smart_download home_files/plugins/blueocean-rest.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-rest/1.1.1/blueocean-rest.hpi 
smart_download home_files/plugins/blueocean-web.hpi https://updates.jenkins-ci.org/download/plugins/blueocean-web/1.1.1/blueocean-web.hpi 
smart_download home_files/plugins/bouncycastle-api.hpi https://updates.jenkins-ci.org/download/plugins/bouncycastle-api/2.16.1/bouncycastle-api.hpi 
smart_download home_files/plugins/branch-api.hpi https://updates.jenkins-ci.org/download/plugins/branch-api/2.0.10/branch-api.hpi 
smart_download home_files/plugins/build-monitor-plugin.hpi https://updates.jenkins-ci.org/download/plugins/build-monitor-plugin/1.12+build.201704111018/build-monitor-plugin.hpi 
smart_download home_files/plugins/build-name-setter.hpi https://updates.jenkins-ci.org/download/plugins/build-name-setter/1.6.5/build-name-setter.hpi 
smart_download home_files/plugins/build-pipeline-plugin.hpi https://updates.jenkins-ci.org/download/plugins/build-pipeline-plugin/1.5.6/build-pipeline-plugin.hpi 
smart_download home_files/plugins/build-timeout.hpi https://updates.jenkins-ci.org/download/plugins/build-timeout/1.18/build-timeout.hpi 
smart_download home_files/plugins/build-token-root.hpi https://updates.jenkins-ci.org/download/plugins/build-token-root/1.4/build-token-root.hpi 
smart_download home_files/plugins/built-on-column.hpi https://updates.jenkins-ci.org/download/plugins/built-on-column/1.1/built-on-column.hpi 
smart_download home_files/plugins/checkmarx.hpi https://updates.jenkins-ci.org/download/plugins/checkmarx/8.70.0/checkmarx.hpi 
smart_download home_files/plugins/checkstyle.hpi https://updates.jenkins-ci.org/download/plugins/checkstyle/3.48/checkstyle.hpi 
smart_download home_files/plugins/clang-scanbuild.hpi https://updates.jenkins-ci.org/download/plugins/clang-scanbuild/1.8/clang-scanbuild.hpi 
smart_download home_files/plugins/clearcase.hpi https://updates.jenkins-ci.org/download/plugins/clearcase/1.6.3/clearcase.hpi 
smart_download home_files/plugins/clearcase-ucm-baseline.hpi https://updates.jenkins-ci.org/download/plugins/clearcase-ucm-baseline/1.7.4/clearcase-ucm-baseline.hpi 
smart_download home_files/plugins/clearcase-ucm-plugin.hpi https://updates.jenkins-ci.org/download/plugins/clearcase-ucm-plugin/1.7.0/clearcase-ucm-plugin.hpi 
smart_download home_files/plugins/cloudbees-folder.hpi https://updates.jenkins-ci.org/download/plugins/cloudbees-folder/6.9/cloudbees-folder.hpi 
smart_download home_files/plugins/cobertura.hpi https://updates.jenkins-ci.org/download/plugins/cobertura/1.10/cobertura.hpi 
smart_download home_files/plugins/codesonar.hpi https://updates.jenkins-ci.org/download/plugins/codesonar/2.0.5/codesonar.hpi
smart_download home_files/plugins/command-launcher.hpi https://updates.jenkins-ci.org/download/plugins/command-launcher/1.0/command-launcher.hpi 
smart_download home_files/plugins/compatibility-action-storage.hpi https://updates.jenkins-ci.org/download/plugins/compatibility-action-storage/1.0/compatibility-action-storage.hpi 
smart_download home_files/plugins/compress-artifacts.hpi https://updates.jenkins-ci.org/download/plugins/compress-artifacts/1.10/compress-artifacts.hpi 
smart_download home_files/plugins/conditional-buildstep.hpi https://updates.jenkins-ci.org/download/plugins/conditional-buildstep/1.3.6/conditional-buildstep.hpi 
smart_download home_files/plugins/config-file-provider.hpi https://updates.jenkins-ci.org/download/plugins/config-file-provider/2.16.0/config-file-provider.hpi 
smart_download home_files/plugins/configurationslicing.hpi https://updates.jenkins-ci.org/download/plugins/configurationslicing/1.47/configurationslicing.hpi 
smart_download home_files/plugins/copyartifact.hpi https://updates.jenkins-ci.org/download/plugins/copyartifact/1.38.1/copyartifact.hpi 
smart_download home_files/plugins/copy-to-slave.hpi https://updates.jenkins-ci.org/download/plugins/copy-to-slave/1.4.4/copy-to-slave.hpi 
smart_download home_files/plugins/covcomplplot.hpi https://updates.jenkins-ci.org/download/plugins/covcomplplot/1.1.1/covcomplplot.hpi 
smart_download home_files/plugins/credentials-binding.hpi https://updates.jenkins-ci.org/download/plugins/credentials-binding/1.12/credentials-binding.hpi 
smart_download home_files/plugins/credentials.hpi https://updates.jenkins-ci.org/download/plugins/credentials/2.2.0/credentials.hpi 
smart_download home_files/plugins/custom-tools-plugin.hpi https://updates.jenkins-ci.org/download/plugins/custom-tools-plugin/0.5/custom-tools-plugin.hpi 
smart_download home_files/plugins/cvs.hpi https://updates.jenkins-ci.org/download/plugins/cvs/2.13/cvs.hpi 
smart_download home_files/plugins/dashboard-view.hpi https://updates.jenkins-ci.org/download/plugins/dashboard-view/2.9.11/dashboard-view.hpi 
smart_download home_files/plugins/deploy.hpi https://updates.jenkins-ci.org/download/plugins/deploy/1.10/deploy.hpi 
smart_download home_files/plugins/display-url-api.hpi https://updates.jenkins-ci.org/download/plugins/display-url-api/2.0/display-url-api.hpi 
smart_download home_files/plugins/docker-build-publish.hpi https://updates.jenkins-ci.org/download/plugins/docker-build-publish/1.3.2/docker-build-publish.hpi 
smart_download home_files/plugins/docker-commons.hpi https://updates.jenkins-ci.org/download/plugins/docker-commons/1.6/docker-commons.hpi 
smart_download home_files/plugins/docker-plugin.hpi https://updates.jenkins-ci.org/download/plugins/docker-plugin/0.16.2/docker-plugin.hpi 
smart_download home_files/plugins/docker-workflow.hpi https://updates.jenkins-ci.org/download/plugins/docker-workflow/1.11/docker-workflow.hpi 
smart_download home_files/plugins/durable-task.hpi https://updates.jenkins-ci.org/download/plugins/durable-task/1.14/durable-task.hpi 
smart_download home_files/plugins/email-ext.hpi https://updates.jenkins-ci.org/download/plugins/email-ext/2.57.2/email-ext.hpi 
smart_download home_files/plugins/emailext-template.hpi https://updates.jenkins-ci.org/download/plugins/emailext-template/1.0/emailext-template.hpi 
smart_download home_files/plugins/embeddable-build-status.hpi https://updates.jenkins-ci.org/download/plugins/embeddable-build-status/1.9/embeddable-build-status.hpi 
smart_download home_files/plugins/emma.hpi https://updates.jenkins-ci.org/download/plugins/emma/1.29/emma.hpi 
smart_download home_files/plugins/envinject.hpi https://updates.jenkins-ci.org/download/plugins/envinject/2.1/envinject.hpi 
smart_download home_files/plugins/extended-choice-parameter.hpi https://updates.jenkins-ci.org/download/plugins/extended-choice-parameter/0.76/extended-choice-parameter.hpi 
smart_download home_files/plugins/external-monitor-job.hpi https://updates.jenkins-ci.org/download/plugins/external-monitor-job/1.7/external-monitor-job.hpi 
smart_download home_files/plugins/favorite.hpi https://updates.jenkins-ci.org/download/plugins/favorite/2.3.0/favorite.hpi 
smart_download home_files/plugins/file-operations.hpi https://updates.jenkins-ci.org/download/plugins/file-operations/1.6/file-operations.hpi 
smart_download home_files/plugins/findbugs.hpi https://updates.jenkins-ci.org/download/plugins/findbugs/4.70/findbugs.hpi 
smart_download home_files/plugins/flexible-publish.hpi https://updates.jenkins-ci.org/download/plugins/flexible-publish/0.15.2/flexible-publish.hpi 
smart_download home_files/plugins/fxcop-runner.hpi https://updates.jenkins-ci.org/download/plugins/fxcop-runner/1.1/fxcop-runner.hpi 
smart_download home_files/plugins/git-client.hpi https://updates.jenkins-ci.org/download/plugins/git-client/2.4.6/git-client.hpi 
smart_download home_files/plugins/github-api.hpi https://updates.jenkins-ci.org/download/plugins/github-api/1.85.1/github-api.hpi 
smart_download home_files/plugins/github-branch-source.hpi https://updates.jenkins-ci.org/download/plugins/github-branch-source/2.0.6/github-branch-source.hpi 
smart_download home_files/plugins/github.hpi https://updates.jenkins-ci.org/download/plugins/github/1.27.0/github.hpi 
smart_download home_files/plugins/github-organization-folder.hpi https://updates.jenkins-ci.org/download/plugins/github-organization-folder/1.6/github-organization-folder.hpi 
smart_download home_files/plugins/git.hpi https://updates.jenkins-ci.org/download/plugins/git/3.3.0/git.hpi 
smart_download home_files/plugins/gitlab-plugin.hpi https://updates.jenkins-ci.org/download/plugins/gitlab-plugin/1.4.5/gitlab-plugin.hpi 
smart_download home_files/plugins/git-parameter.hpi https://updates.jenkins-ci.org/download/plugins/git-parameter/0.8.0/git-parameter.hpi 
smart_download home_files/plugins/global-variable-string-parameter.hpi https://updates.jenkins-ci.org/download/plugins/global-variable-string-parameter/1.2/global-variable-string-parameter.hpi 
smart_download home_files/plugins/gradle.hpi https://updates.jenkins-ci.org/download/plugins/gradle/1.26/gradle.hpi 
smart_download home_files/plugins/greenballs.hpi https://updates.jenkins-ci.org/download/plugins/greenballs/1.15/greenballs.hpi 
smart_download home_files/plugins/hp-quality-center.hpi https://updates.jenkins-ci.org/download/plugins/hp-quality-center/1.2/hp-quality-center.hpi 
smart_download home_files/plugins/htmlpublisher.hpi https://updates.jenkins-ci.org/download/plugins/htmlpublisher/1.13/htmlpublisher.hpi 
smart_download home_files/plugins/hudson-wsclean-plugin.hpi https://updates.jenkins-ci.org/download/plugins/hudson-wsclean-plugin/1.0.5/hudson-wsclean-plugin.hpi 
smart_download home_files/plugins/icon-shim.hpi https://updates.jenkins-ci.org/download/plugins/icon-shim/2.0.3/icon-shim.hpi 
smart_download home_files/plugins/jackson2-api.hpi https://updates.jenkins-ci.org/download/plugins/jackson2-api/2.7.3/jackson2-api.hpi 
smart_download home_files/plugins/jacoco.hpi https://updates.jenkins-ci.org/download/plugins/jacoco/2.2.1/jacoco.hpi 
smart_download home_files/plugins/javadoc.hpi https://updates.jenkins-ci.org/download/plugins/javadoc/1.4/javadoc.hpi 
smart_download home_files/plugins/jenkins-jira-issue-updater.hpi https://updates.jenkins-ci.org/download/plugins/jenkins-jira-issue-updater/1.18/jenkins-jira-issue-updater.hpi 
smart_download home_files/plugins/jenkins-multijob-plugin.hpi https://updates.jenkins-ci.org/download/plugins/jenkins-multijob-plugin/1.25/jenkins-multijob-plugin.hpi 
smart_download home_files/plugins/jira.hpi https://updates.jenkins-ci.org/download/plugins/jira/2.3.1/jira.hpi 
smart_download home_files/plugins/JiraTestResultReporter.hpi https://updates.jenkins-ci.org/download/plugins/JiraTestResultReporter/2.0.4/JiraTestResultReporter.hpi 
smart_download home_files/plugins/jquery.hpi https://updates.jenkins-ci.org/download/plugins/jquery/1.11.2-0/jquery.hpi 
smart_download home_files/plugins/jquery-ui.hpi https://updates.jenkins.io/download/plugins/jquery-ui/1.0.2/jquery-ui.hpi
smart_download home_files/plugins/jsch.hpi https://updates.jenkins-ci.org/download/plugins/jsch/0.1.54.2/jsch.hpi
smart_download home_files/plugins/junit.hpi https://updates.jenkins-ci.org/download/plugins/junit/1.20/junit.hpi 
smart_download home_files/plugins/ldap.hpi https://updates.jenkins-ci.org/download/plugins/ldap/1.15/ldap.hpi 
smart_download home_files/plugins/lockable-resources.hpi https://updates.jenkins-ci.org/download/plugins/lockable-resources/2.0/lockable-resources.hpi 
smart_download home_files/plugins/mailer.hpi https://updates.jenkins-ci.org/download/plugins/mailer/1.20/mailer.hpi 
smart_download home_files/plugins/mapdb-api.hpi https://updates.jenkins-ci.org/download/plugins/mapdb-api/1.0.9.0/mapdb-api.hpi 
smart_download home_files/plugins/mask-passwords.hpi https://updates.jenkins-ci.org/download/plugins/mask-passwords/2.10.1/mask-passwords.hpi 
smart_download home_files/plugins/matrix-auth.hpi https://updates.jenkins-ci.org/download/plugins/matrix-auth/2.4.2/matrix-auth.hpi 
smart_download home_files/plugins/matrix-project.hpi https://updates.jenkins-ci.org/download/plugins/matrix-project/1.12/matrix-project.hpi 
smart_download home_files/plugins/maven-plugin.hpi https://updates.jenkins-ci.org/download/plugins/maven-plugin/2.17/maven-plugin.hpi 
smart_download home_files/plugins/mercurial.hpi https://updates.jenkins-ci.org/download/plugins/mercurial/1.60/mercurial.hpi 
smart_download home_files/plugins/metrics.hpi https://updates.jenkins-ci.org/download/plugins/metrics/3.1.2.10/metrics.hpi 
smart_download home_files/plugins/msbuild.hpi https://updates.jenkins-ci.org/download/plugins/msbuild/1.27/msbuild.hpi 
smart_download home_files/plugins/mstest.hpi https://updates.jenkins-ci.org/download/plugins/mstest/0.19/mstest.hpi 
smart_download home_files/plugins/mstestrunner.hpi https://updates.jenkins-ci.org/download/plugins/mstestrunner/1.3.0/mstestrunner.hpi 
smart_download home_files/plugins/multiple-scms.hpi https://updates.jenkins-ci.org/download/plugins/multiple-scms/0.6/multiple-scms.hpi 
smart_download home_files/plugins/nexus-artifact-uploader.hpi https://updates.jenkins-ci.org/download/plugins/nexus-artifact-uploader/2.9/nexus-artifact-uploader.hpi 
smart_download home_files/plugins/nodejs.hpi https://updates.jenkins-ci.org/download/plugins/nodejs/1.2.2/nodejs.hpi 
smart_download home_files/plugins/p4.hpi https://updates.jenkins-ci.org/download/plugins/p4/1.7.0/p4.hpi 
smart_download home_files/plugins/pam-auth.hpi https://updates.jenkins-ci.org/download/plugins/pam-auth/1.3/pam-auth.hpi 
smart_download home_files/plugins/parameterized-trigger.hpi https://updates.jenkins-ci.org/download/plugins/parameterized-trigger/2.33/parameterized-trigger.hpi 
smart_download home_files/plugins/parasoft-findings.hpi https://updates.jenkins-ci.org/download/plugins/parasoft-findings/10.3.0/parasoft-findings.hpi 
smart_download home_files/plugins/persistent-parameter.hpi https://updates.jenkins-ci.org/download/plugins/persistent-parameter/1.1/persistent-parameter.hpi 
smart_download home_files/plugins/pipeline-build-step.hpi https://updates.jenkins-ci.org/download/plugins/pipeline-build-step/2.5/pipeline-build-step.hpi 
smart_download home_files/plugins/pipeline-github-lib.hpi https://updates.jenkins-ci.org/download/plugins/pipeline-github-lib/1.0/pipeline-github-lib.hpi 
smart_download home_files/plugins/pipeline-graph-analysis.hpi https://updates.jenkins-ci.org/download/plugins/pipeline-graph-analysis/1.4/pipeline-graph-analysis.hpi 
smart_download home_files/plugins/pipeline-input-step.hpi https://updates.jenkins-ci.org/download/plugins/pipeline-input-step/2.7/pipeline-input-step.hpi 
smart_download home_files/plugins/pipeline-maven.hpi https://updates.jenkins-ci.org/download/plugins/pipeline-maven/2.5.0/pipeline-maven.hpi 
smart_download home_files/plugins/pipeline-milestone-step.hpi https://updates.jenkins-ci.org/download/plugins/pipeline-milestone-step/1.3.1/pipeline-milestone-step.hpi 
smart_download home_files/plugins/pipeline-model-api.hpi https://updates.jenkins-ci.org/download/plugins/pipeline-model-api/1.1.6/pipeline-model-api.hpi 
smart_download home_files/plugins/pipeline-model-definition.hpi https://updates.jenkins-ci.org/download/plugins/pipeline-model-definition/1.1.6/pipeline-model-definition.hpi 
smart_download home_files/plugins/pipeline-model-extensions.hpi https://updates.jenkins-ci.org/download/plugins/pipeline-model-extensions/1.1.6/pipeline-model-extensions.hpi 
smart_download home_files/plugins/pipeline-rest-api.hpi https://updates.jenkins-ci.org/download/plugins/pipeline-rest-api/2.8/pipeline-rest-api.hpi 
smart_download home_files/plugins/pipeline-stage-step.hpi https://updates.jenkins-ci.org/download/plugins/pipeline-stage-step/2.2/pipeline-stage-step.hpi 
smart_download home_files/plugins/pipeline-stage-tags-metadata.hpi https://updates.jenkins-ci.org/download/plugins/pipeline-stage-tags-metadata/1.1.6/pipeline-stage-tags-metadata.hpi 
smart_download home_files/plugins/pipeline-stage-view.hpi https://updates.jenkins-ci.org/download/plugins/pipeline-stage-view/2.8/pipeline-stage-view.hpi 
smart_download home_files/plugins/plain-credentials.hpi https://updates.jenkins-ci.org/download/plugins/plain-credentials/1.4/plain-credentials.hpi 
smart_download home_files/plugins/port-allocator.hpi https://updates.jenkins-ci.org/download/plugins/port-allocator/1.8/port-allocator.hpi 
smart_download home_files/plugins/postbuildscript.hpi https://updates.jenkins-ci.org/download/plugins/postbuildscript/0.18.1/postbuildscript.hpi 
smart_download home_files/plugins/powershell.hpi https://updates.jenkins-ci.org/download/plugins/powershell/1.3/powershell.hpi 
smart_download home_files/plugins/prometheus.hpi https://updates.jenkins-ci.org/download/plugins/prometheus/1.0.6/prometheus.hpi 
smart_download home_files/plugins/promoted-builds.hpi https://updates.jenkins-ci.org/download/plugins/promoted-builds/2.28.1/promoted-builds.hpi 
smart_download home_files/plugins/publish-over.hpi https://updates.jenkins-ci.org/download/plugins/publish-over/0.21/publish-over.hpi
smart_download home_files/plugins/publish-over-ssh.hpi https://updates.jenkins-ci.org/download/plugins/publish-over-ssh/1.20.1/publish-over-ssh.hpi 
smart_download home_files/plugins/pubsub-light.hpi https://updates.jenkins-ci.org/download/plugins/pubsub-light/1.8/pubsub-light.hpi 
smart_download home_files/plugins/purge-build-queue-plugin.hpi https://updates.jenkins-ci.org/download/plugins/purge-build-queue-plugin/1.0/purge-build-queue-plugin.hpi 
smart_download home_files/plugins/quality-gates.hpi https://updates.jenkins-ci.org/download/plugins/quality-gates/2.5/quality-gates.hpi 
smart_download home_files/plugins/repo.hpi https://updates.jenkins-ci.org/download/plugins/repo/1.10.7/repo.hpi 
smart_download home_files/plugins/repository-connector.hpi https://updates.jenkins-ci.org/download/plugins/repository-connector/1.1.3/repository-connector.hpi 
smart_download home_files/plugins/resource-disposer.hpi https://updates.jenkins-ci.org/download/plugins/resource-disposer/0.6/resource-disposer.hpi 
smart_download home_files/plugins/robot.hpi https://updates.jenkins-ci.org/download/plugins/robot/1.6.4/robot.hpi 
smart_download home_files/plugins/role-strategy.hpi https://updates.jenkins-ci.org/download/plugins/role-strategy/2.13/role-strategy.hpi 
smart_download home_files/plugins/run-condition.hpi https://updates.jenkins-ci.org/download/plugins/run-condition/1.0/run-condition.hpi 
smart_download home_files/plugins/scm-api.hpi https://updates.jenkins-ci.org/download/plugins/scm-api/2.1.1/scm-api.hpi 
smart_download home_files/plugins/script-security.hpi https://updates.jenkins-ci.org/download/plugins/script-security/1.60/script-security.hpi 
smart_download home_files/plugins/selenium.hpi https://updates.jenkins-ci.org/download/plugins/selenium/3.1.0/selenium.hpi 
smart_download home_files/plugins/sonargraph-integration.hpi https://updates.jenkins-ci.org/download/plugins/sonargraph-integration/2.0.2/sonargraph-integration.hpi 
smart_download home_files/plugins/sonargraph-plugin.hpi https://updates.jenkins-ci.org/download/plugins/sonargraph-plugin/1.6.4/sonargraph-plugin.hpi 
smart_download home_files/plugins/sonar.hpi https://updates.jenkins-ci.org/download/plugins/sonar/2.6.1/sonar.hpi 
smart_download home_files/plugins/sse-gateway.hpi https://updates.jenkins-ci.org/download/plugins/sse-gateway/1.15/sse-gateway.hpi 
smart_download home_files/plugins/ssh-credentials.hpi https://updates.jenkins-ci.org/download/plugins/ssh-credentials/1.17/ssh-credentials.hpi
smart_download home_files/plugins/ssh.hpi https://updates.jenkins-ci.org/download/plugins/ssh/2.4/ssh.hpi 
smart_download home_files/plugins/ssh-slaves.hpi https://updates.jenkins-ci.org/download/plugins/ssh-slaves/1.20/ssh-slaves.hpi 
smart_download home_files/plugins/structs.hpi https://updates.jenkins-ci.org/download/plugins/structs/1.20/structs.hpi 
smart_download home_files/plugins/subversion.hpi https://updates.jenkins-ci.org/download/plugins/subversion/2.7.1/subversion.hpi 
smart_download home_files/plugins/svnmerge.hpi https://updates.jenkins-ci.org/download/plugins/svnmerge/2.6/svnmerge.hpi 
smart_download home_files/plugins/svnpublisher.hpi https://updates.jenkins-ci.org/download/plugins/svnpublisher/0.1/svnpublisher.hpi 
smart_download home_files/plugins/svn-release-mgr.hpi https://updates.jenkins-ci.org/download/plugins/svn-release-mgr/1.2/svn-release-mgr.hpi 
smart_download home_files/plugins/svn-revert-plugin.hpi https://updates.jenkins-ci.org/download/plugins/svn-revert-plugin/1.3/svn-revert-plugin.hpi 
smart_download home_files/plugins/svn-tag.hpi https://updates.jenkins-ci.org/download/plugins/svn-tag/1.18/svn-tag.hpi 
smart_download home_files/plugins/svn-workspace-cleaner.hpi https://updates.jenkins-ci.org/download/plugins/svn-workspace-cleaner/1.1/svn-workspace-cleaner.hpi 
smart_download home_files/plugins/teamconcert.hpi https://updates.jenkins-ci.org/download/plugins/teamconcert/1.2.0.1/teamconcert.hpi 
smart_download home_files/plugins/testng-plugin.hpi https://updates.jenkins-ci.org/download/plugins/testng-plugin/1.14/testng-plugin.hpi 
smart_download home_files/plugins/test-results-analyzer.hpi https://updates.jenkins-ci.org/download/plugins/test-results-analyzer/0.3.4/test-results-analyzer.hpi 
smart_download home_files/plugins/text-file-operations.hpi https://updates.jenkins-ci.org/download/plugins/text-file-operations/1.3.2/text-file-operations.hpi 
smart_download home_files/plugins/tfs.hpi https://updates.jenkins-ci.org/download/plugins/tfs/5.2.1/tfs.hpi 
smart_download home_files/plugins/throttle-concurrents.hpi https://updates.jenkins-ci.org/download/plugins/throttle-concurrents/2.0.1/throttle-concurrents.hpi 
smart_download home_files/plugins/timestamper.hpi https://updates.jenkins-ci.org/download/plugins/timestamper/1.8.8/timestamper.hpi 
smart_download home_files/plugins/token-macro.hpi https://updates.jenkins-ci.org/download/plugins/token-macro/2.1/token-macro.hpi 
smart_download home_files/plugins/toolenv.hpi https://updates.jenkins-ci.org/download/plugins/toolenv/1.1/toolenv.hpi 
smart_download home_files/plugins/translation.hpi https://updates.jenkins-ci.org/download/plugins/translation/1.15/translation.hpi 
smart_download home_files/plugins/variant.hpi https://updates.jenkins-ci.org/download/plugins/variant/1.1/variant.hpi 
smart_download home_files/plugins/versionnumber.hpi https://updates.jenkins-ci.org/download/plugins/versionnumber/1.8.1/versionnumber.hpi 
smart_download home_files/plugins/violation-columns.hpi https://updates.jenkins-ci.org/download/plugins/violation-columns/1.6/violation-columns.hpi 
smart_download home_files/plugins/violations.hpi https://updates.jenkins-ci.org/download/plugins/violations/0.7.11/violations.hpi 
smart_download home_files/plugins/warnings.hpi https://updates.jenkins-ci.org/download/plugins/warnings/4.62/warnings.hpi 
smart_download home_files/plugins/weblogic-deployer-plugin.hpi https://updates.jenkins-ci.org/download/plugins/weblogic-deployer-plugin/3.6/weblogic-deployer-plugin.hpi 
smart_download home_files/plugins/windows-slaves.hpi https://updates.jenkins-ci.org/download/plugins/windows-slaves/1.3.1/windows-slaves.hpi 
smart_download home_files/plugins/workflow-aggregator.hpi https://updates.jenkins-ci.org/download/plugins/workflow-aggregator/2.5/workflow-aggregator.hpi 
smart_download home_files/plugins/workflow-api.hpi https://updates.jenkins-ci.org/download/plugins/workflow-api/2.17/workflow-api.hpi 
smart_download home_files/plugins/workflow-basic-steps.hpi https://updates.jenkins-ci.org/download/plugins/workflow-basic-steps/2.5/workflow-basic-steps.hpi 
smart_download home_files/plugins/workflow-cps.hpi https://updates.jenkins-ci.org/download/plugins/workflow-cps/2.36/workflow-cps.hpi 
smart_download home_files/plugins/workflow-durable-task-step.hpi https://updates.jenkins-ci.org/download/plugins/workflow-durable-task-step/2.12/workflow-durable-task-step.hpi 
smart_download home_files/plugins/workflow-job.hpi https://updates.jenkins-ci.org/download/plugins/workflow-job/2.11/workflow-job.hpi 
smart_download home_files/plugins/workflow-multibranch.hpi https://updates.jenkins-ci.org/download/plugins/workflow-multibranch/2.15/workflow-multibranch.hpi 
smart_download home_files/plugins/workflow-step-api.hpi https://updates.jenkins-ci.org/download/plugins/workflow-step-api/2.16/workflow-step-api.hpi 
smart_download home_files/plugins/ws-cleanup.hpi https://updates.jenkins-ci.org/download/plugins/ws-cleanup/0.33/ws-cleanup.hpi 
smart_download home_files/plugins/xcode-plugin.hpi https://updates.jenkins-ci.org/download/plugins/xcode-plugin/2.0.2/xcode-plugin.hpi 
smart_download home_files/plugins/xframe-filter-plugin.hpi https://updates.jenkins-ci.org/download/plugins/xframe-filter-plugin/1.2/xframe-filter-plugin.hpi 
smart_download home_files/plugins/ace-editor.hpi https://updates.jenkins-ci.org/download/plugins/ace-editor/1.1/ace-editor.hpi 
smart_download home_files/plugins/audit-trail.hpi https://updates.jenkins-ci.org/download/plugins/audit-trail/2.2/audit-trail.hpi 
smart_download home_files/plugins/discard-old-build.hpi https://updates.jenkins-ci.org/download/plugins/discard-old-build/1.05/discard-old-build.hpi 
smart_download home_files/plugins/filesystem_scm.hpi https://updates.jenkins-ci.org/download/plugins/filesystem_scm/2.1/filesystem_scm.hpi 
smart_download home_files/plugins/git-server.hpi https://updates.jenkins-ci.org/download/plugins/git-server/1.7/git-server.hpi 
smart_download home_files/plugins/groovy.hpi https://updates.jenkins-ci.org/download/plugins/groovy/2.0/groovy.hpi 
smart_download home_files/plugins/handlebars.hpi https://updates.jenkins-ci.org/download/plugins/handlebars/1.1.1/handlebars.hpi 
smart_download home_files/plugins/hp-application-automation-tools-plugin.hpi https://updates.jenkins-ci.org/download/plugins/hp-application-automation-tools-plugin/5.3.3-beta/hp-application-automation-tools-plugin.hpi 
smart_download home_files/plugins/http_request.hpi https://updates.jenkins-ci.org/download/plugins/http_request/1.8.20/http_request.hpi 
smart_download home_files/plugins/jira-trigger.hpi https://updates.jenkins-ci.org/download/plugins/jira-trigger/0.4.1/jira-trigger.hpi 
smart_download home_files/plugins/job-dsl.hpi https://updates.jenkins-ci.org/download/plugins/job-dsl/1.58/job-dsl.hpi 
smart_download home_files/plugins/jquery-detached.hpi https://updates.jenkins-ci.org/download/plugins/jquery-detached/1.2.1/jquery-detached.hpi 
smart_download home_files/plugins/logstash.hpi https://updates.jenkins-ci.org/download/plugins/logstash/1.3.0/logstash.hpi 
smart_download home_files/plugins/momentjs.hpi https://updates.jenkins-ci.org/download/plugins/momentjs/1.1.1/momentjs.hpi 
smart_download home_files/plugins/nodelabelparameter.hpi https://updates.jenkins-ci.org/download/plugins/nodelabelparameter/1.7.2/nodelabelparameter.hpi 
smart_download home_files/plugins/oic-auth.hpi https://updates.jenkins-ci.org/download/plugins/oic-auth/1.6/oic-auth.hpi 
smart_download home_files/plugins/parameterized-scheduler.hpi https://updates.jenkins-ci.org/download/plugins/parameterized-scheduler/0.5/parameterized-scheduler.hpi 
smart_download home_files/plugins/performance.hpi https://updates.jenkins-ci.org/download/plugins/performance/3.3/performance.hpi 
smart_download home_files/plugins/pipeline-model-declarative-agent.hpi https://updates.jenkins-ci.org/download/plugins/pipeline-model-declarative-agent/1.1.1/pipeline-model-declarative-agent.hpi 
smart_download home_files/plugins/pmd.hpi https://updates.jenkins-ci.org/download/plugins/pmd/3.47/pmd.hpi 
smart_download home_files/plugins/rebuild.hpi https://updates.jenkins-ci.org/download/plugins/rebuild/1.25/rebuild.hpi 
smart_download home_files/plugins/rtc.hpi https://updates.jenkins-ci.org/download/plugins/rtc/0.3/rtc.hpi 
smart_download home_files/plugins/swarm.hpi https://updates.jenkins-ci.org/download/plugins/swarm/3.4/swarm.hpi 
smart_download home_files/plugins/websphere-deployer.hpi https://updates.jenkins-ci.org/download/plugins/websphere-deployer/1.3.4/websphere-deployer.hpi 
smart_download home_files/plugins/workflow-cps-global-lib.hpi https://updates.jenkins-ci.org/download/plugins/workflow-cps-global-lib/2.8/workflow-cps-global-lib.hpi 
smart_download home_files/plugins/workflow-scm-step.hpi https://updates.jenkins-ci.org/download/plugins/workflow-scm-step/2.4/workflow-scm-step.hpi 
smart_download home_files/plugins/workflow-support.hpi https://updates.jenkins-ci.org/download/plugins/workflow-support/2.14/workflow-support.hpi 
smart_download home_files/plugins/xshell.hpi https://updates.jenkins-ci.org/download/plugins/xshell/0.10/xshell.hpi 
smart_download home_files/plugins/xunit.hpi https://updates.jenkins-ci.org/download/plugins/xunit/1.102/xunit.hpi
smart_download home_files/plugins/nunit.hpi https://updates.jenkins-ci.org/download/plugins/nunit/0.23/nunit.hpi
smart_download home_files/plugins/ivy.hpi https://updates.jenkins-ci.org/download/plugins/ivy/1.28/ivy.hpi
smart_download home_files/plugins/xvfb.hpi https://updates.jenkins-ci.org/download/plugins/xvfb/1.1.3/xvfb.hpi
smart_download home_files/plugins/jdk-tool.hpi https://updates.jenkins.io/download/plugins/jdk-tool/1.2/jdk-tool.hpi
smart_download home_files/plugins/fortify.hpi https://updates.jenkins.io/download/plugins/fortify/19.1.29/fortify.hpi
smart_download home_files/plugins/ansible.hpi https://updates.jenkins.io/download/plugins/ansible/1.0/ansible.hpi
smart_download home_files/plugins/jaxb.hpi https://updates.jenkins.io/download/plugins/jaxb/2.3.0.1/jaxb.hpi
smart_download home_files/plugins/trilead-api.hpi https://updates.jenkins.io/download/plugins/trilead-api/1.0.5/trilead-api.hpi

