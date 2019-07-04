#!/bin/sh
port_check(){ 
    netstat -ntpl | grep :$1 -q ; 
    if [ $? -eq 0 ]
    then
		echo -en "\033[u Failed\n"
		exit 1
	else
		echo -en "\033[u Passed\n"
	fi
}

echo "Starting port check ......."
if [ "$SSL_ENABLED" = true ]
then
	echo -en " Checking HTTPS Server Port ($HTTPS_PORT):\033[s"
	port_check $HTTPS_PORT
else
	echo -en " Checking HTTP Server Port ($HTTP_PORT):\033[s"
	port_check $HTTP_PORT
fi
echo -en " Checking CI Engine Client Port ($JENKINS_SLAVE_PORT):\033[s"
port_check $JENKINS_SLAVE_PORT


