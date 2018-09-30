FROM openjdk:8u151-jdk

LABEL maintainer="idpadmin@domain.com"\
      owner="Infosys Ltd."

EXPOSE 8222/tcp

ADD target/idpscheduler.jar /idpscheduler/idpscheduler.jar
ADD idpscheduler.sh /idpscheduler/idpscheduler.sh
RUN chmod +x /idpscheduler/idpscheduler.sh

ENTRYPOINT /idpscheduler/idpscheduler.sh