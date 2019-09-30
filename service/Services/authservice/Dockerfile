FROM openjdk:8u151-jre

LABEL maintainer="IDP_OSS@domain.com"\
      owner="Infosys Ltd."

EXPOSE 8181/tcp

COPY target/idpoauth.jar /idpoauth/idpoauth.jar
COPY idpoauth.sh /idpoauth/idpoauth.sh
RUN chmod +x /idpoauth/idpoauth.sh

ENTRYPOINT /idpoauth/idpoauth.sh
