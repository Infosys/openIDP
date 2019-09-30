FROM openjdk:8u151-jre

LABEL maintainer="IDP_OSS@domain.com"\
      owner="Infosys Ltd."

EXPOSE 8889/tcp

COPY target/idprest.jar /idprest/idprest.jar
COPY idprest.sh /idprest/idprest.sh
RUN chmod +x /idprest/idprest.sh

ENTRYPOINT /idprest/idprest.sh
