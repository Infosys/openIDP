FROM openjdk:8u151-jre

LABEL maintainer="IDP_OSS@domain.com"\
      owner="Infosys Ltd."

EXPOSE 8761/tcp

COPY target/idpeureka.jar /idpeureka/idpeureka.jar
COPY idpeureka.sh /idpeureka/idpeureka.sh
RUN chmod +x /idpeureka/idpeureka.sh

ENTRYPOINT /idpeureka/idpeureka.sh
