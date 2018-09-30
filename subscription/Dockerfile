FROM openjdk:8u151-jdk

LABEL maintainer="idpadmin@domain.com"\
      owner="Infosys Ltd."

EXPOSE 8090/tcp

ADD target/idpsubscription.jar /idpsubscription/idpsubscription.jar
ADD idpsubscription.sh /idpsubscription/idpsubscription.sh
RUN chmod +x /idpsubscription/idpsubscription.sh

ENTRYPOINT /idpsubscription/idpsubscription.sh