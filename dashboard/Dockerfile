FROM openjdk:8u151-jre

LABEL maintainer="IDP_OSS@domain.com"\
      owner="Infosys Ltd."

EXPOSE 8184/tcp

COPY target/idpdashboard.jar /idpdashboard/idpdashboard.jar
COPY idpdashboard.sh /idpdashboard/idpdashboard.sh
RUN chmod +x /idpdashboard/idpdashboard.sh

ENTRYPOINT /idpdashboard/idpdashboard.sh
