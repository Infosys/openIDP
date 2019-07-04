FROM openjdk:8u151-jre

LABEL maintainer="idpadmin@domain.com"\
      owner="Infosys Ltd."

EXPOSE 8222/tcp

COPY target/idpscheduler.jar /idpscheduler/idpscheduler.jar
COPY idpscheduler.sh /idpscheduler/idpscheduler.sh
RUN chmod +x /idpscheduler/idpscheduler.sh

ENTRYPOINT sh /idpscheduler/idpscheduler.sh