#!/bin/sh

rm -rf lib testlibs
mkdir -p lib testlibs

cd testlibs/
wget $WGET_PROXY http://central.maven.org/maven2/net/sourceforge/cobertura/cobertura/2.1.1/cobertura-2.1.1.jar  --no-check-certificate
wget $WGET_PROXY https://updates.jenkins.io/download/plugins/parameterized-scheduler/0.5/parameterized-scheduler.hpi  --no-check-certificate
wget $WGET_PROXY -O job-dsl-core.jar http://repo.spring.io/plugins-release/org/jenkins-ci/plugins/job-dsl-core/1.58/job-dsl-core-1.58.jar  --no-check-certificate
wget $WGET_PROXY https://updates.jenkins.io/download/plugins/credentials/2.1.9/credentials.hpi  --no-check-certificate

cd ../lib/
wget $WGET_PROXY http://central.maven.org/maven2/com/google/code/gson/gson/2.8.0/gson-2.8.0.jar  --no-check-certificate
yes | \cp -rf /entities/*.jar ./