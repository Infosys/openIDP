FROM tomcat:9.0.5-jre8

LABEL maintainer="idpadmin@domain.com"\
      owner="Infosys Ltd."

EXPOSE 8085/tcp
EXPOSE 50000/tcp

ADD jenkins.war /usr/local/tomcat/webapps/jenkins.war
ADD server.xml /usr/local/tomcat/conf/server.xml