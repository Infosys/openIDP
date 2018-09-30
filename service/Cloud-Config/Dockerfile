FROM openjdk:8u151-jdk

LABEL maintainer="idpadmin@domain.com"\
      owner="Infosys Ltd."

EXPOSE 8888/tcp

ADD target/idpconfig.jar /idpconfig/idpconfig.jar
ADD idpconfig.sh /idpconfig/idpconfig.sh
RUN chmod +x /idpconfig/idpconfig.sh

ENTRYPOINT /idpconfig/idpconfig.sh