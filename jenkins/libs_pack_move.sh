#!/bin/sh

if find "/jenkinsdata/" -mindepth 1 -print -quit 2>/dev/null | grep -q .; then
	echo "jenkinsdata directory already filled. Will attempt to start jenkins with old directory"
else
	echo "New installation detected"
	
	echo "Moving Custom Tools"
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
	cp /jenkins/home_files/plugins/role-strategy.hpi ./
	rm -rf /jenkins/home_files/plugins/role-strategy.hpi
	rm -rf role
	mkdir -p role
	unzip role-strategy.hpi -d ./role
	zip -rv role/WEB-INF/lib/role-strategy.jar com/michelin/cio/hudson/plugins/rolestrategy/*.class
	cd role
	zip -r /jenkins/home_files/plugins/role-strategy.hpi ./*
	
	echo "Copying Jenkins Data folder"
	cp -rpf /jenkins/home_files/* /jenkinsdata/
fi
