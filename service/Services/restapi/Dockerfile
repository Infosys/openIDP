FROM openjdk:8u151-jdk

LABEL maintainer="idpadmin@domain.com"\
      owner="Infosys Ltd."

EXPOSE 8889/tcp

COPY target/idprest.jar /idprest/idprest.jar
COPY jco /idprest/jco
COPY idprest.sh /idprest/idprest.sh
RUN chmod +x /idprest/idprest.sh
RUN chmod -R +x /idprest/jco

ENTRYPOINT /idprest/idprest.sh
