FROM openjdk:8u151-jdk

LABEL maintainer="idpadmin@domain.com"\
      owner="Infosys Ltd."

EXPOSE 8080/tcp

ADD target/idpapp.jar /idpapp/idpapp.jar
ADD idpapp.sh /idpapp/idpapp.sh
RUN chmod +x /idpapp/idpapp.sh

ENTRYPOINT /idpapp/idpapp.sh