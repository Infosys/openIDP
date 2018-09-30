FROM openjdk:8u151-jdk

LABEL maintainer="idpadmin@domain.com"\
      owner="Infosys Ltd."

EXPOSE 8761/tcp

ADD target/idpeureka.jar /idpeureka/idpeureka.jar
ADD idpeureka.sh /idpeureka/idpeureka.sh
RUN chmod +x /idpeureka/idpeureka.sh

ENTRYPOINT /idpeureka/idpeureka.sh