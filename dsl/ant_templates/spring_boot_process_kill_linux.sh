#!/bin/sh

if [ $(sudo lsof -t -i:$PORT) ]
then
        echo "Process is running in port"
        sudo kill $(sudo lsof -t -i:$PORT)
		echo "Process killed"
else
        echo "Process is not running in port"
fi
