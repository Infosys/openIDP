FROM openjdk:8u151-jre

LABEL maintainer="idpadmin@domain.com"\
      owner="Infosys Ltd."

EXPOSE 8889/tcp

COPY target/idpjenkinsconnector.jar /idpjenkinsconnector/idpjenkinsconnector.jar
COPY idpjenkinsconnector.sh /idpjenkinsconnector/idpjenkinsconnector.sh
RUN chmod +x /idpjenkinsconnector/idpjenkinsconnector.sh

ENTRYPOINT sh /idpjenkinsconnector/idpjenkinsconnector.sh