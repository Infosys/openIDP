FROM openjdk:8u151-jdk

LABEL maintainer="idpadmin@domain.com"\
      owner="Infosys Ltd."

EXPOSE 8184/tcp

ADD target/idpdashboard.jar /idpdashboard/idpdashboard.jar
ADD idpdashboard.sh /idpdashboard/idpdashboard.sh
RUN chmod +x /idpdashboard/idpdashboard.sh

ENTRYPOINT /idpdashboard/idpdashboard.sh