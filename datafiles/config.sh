#!/bin/sh

export ANSIBLE_CONFIG=$(dirname $0)

ansible-playbook $(dirname $0)/config.yml --connection=local
