#!/bin/sh

export ANSIBLE_CONFIG=`dirname $0`
pip install $PIP_PROXY lxml

ansible-playbook `dirname $0`/config.yml --connection=local
