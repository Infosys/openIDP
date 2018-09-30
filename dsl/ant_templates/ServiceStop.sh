#!/bin/bash

name=$1

matchingStarted=$(docker ps --filter="name=$name" -q | xargs)
echo "Match Stop  "$matchingStarted;
[[ -n $matchingStarted ]] && docker stop $matchingStarted

matching=$(docker ps -a --filter="name=$name" -q | xargs)
echo "Match remove "$matching;
[[ -n $matching ]] && docker rm $matching

