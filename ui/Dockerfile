FROM openjdk:8u151-jre

LABEL maintainer="IDP_OSS@domain.com"\
      owner="Infosys Ltd."


EXPOSE 8080/tcp

COPY target/idpapp.jar /idpapp/idpapp.jar
COPY idpapp.sh /idpapp/idpapp.sh
RUN chmod +x /idpapp/idpapp.sh

ENTRYPOINT sh /idpapp/idpapp.sh

