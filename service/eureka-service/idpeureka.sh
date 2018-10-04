#!/bin/bash
if [ "$SSL_ENABLED" = true ]
then
	echo "Starting IDP Eureka in SSL mode"
	if [ -z "$SSL_CA_LOC" ]	
	then
		java -jar -Djava.security.egd=file:/dev/./urandom $(dirname $0)/idpeureka.jar
	else
		java -jar -Djavax.net.ssl.trustStore=${SSL_CA_LOC} -Djavax.net.ssl.keyStorePassword=${SSL_CA_PASS} -Djava.security.egd=file:/dev/./urandom $(dirname $0)/idpeureka.jar
	fi   
else
	export SSL_ENABLED=false 
	echo "Starting IDP Eureka in non-secure mode"
	java -jar -Djava.security.egd=file:/dev/./urandom $(dirname $0)/idpeureka.jar 
fi