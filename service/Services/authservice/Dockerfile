FROM openjdk:8u151-jdk

LABEL maintainer="idpadmin@domain.com"\
      owner="Infosys Ltd."

EXPOSE 8181/tcp

ADD target/idpoauth.jar /idpoauth/idpoauth.jar
ADD idpoauth.sh /idpoauth/idpoauth.sh
RUN chmod +x /idpoauth/idpoauth.sh

ENTRYPOINT /idpoauth/idpoauth.sh