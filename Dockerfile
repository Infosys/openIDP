FROM python:3.7.2-stretch


COPY build.sh health_check.sh port_check.sh docker_compose.yml docker_compose_ssl.yml docker_compose_nonssl.yml /buildfiles/
