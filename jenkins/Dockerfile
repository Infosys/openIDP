FROM tomcat:9.0.5-jre8

LABEL maintainer="mailid@domain.com"\
      owner="Infosys Ltd."

EXPOSE 8085/tcp
EXPOSE 50000/tcp

COPY jenkins.war /usr/local/tomcat/webapps/jenkins.war
COPY server.xml /usr/local/tomcat/conf/server.xml
