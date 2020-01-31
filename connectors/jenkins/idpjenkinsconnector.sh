#!/bin/bash
if [ "$SSL_ENABLED" = true ]
then
	export PROTOCOL=https
else
	export PROTOCOL=http
fi
while [ "$status" != true ]
 do
   	echo "Waiting for Kafka Server to start. Sleeping for 5 sec ....."
	sleep 5
	bash -c 'exec 3<> /dev/tcp/'${KAFKA_HOSTNAME}'/'${KAFKA_PORT}'' 2>/dev/null
	if [ $? -ne 0 ]
	then 
		status="false"
	else
		status="true"
	fi
done
if [ "$SSL_ENABLED" = true ]
then
	echo "Starting IDP Jenkins Connector in SSL mode"
	if [ -z "$SSL_CA_LOC" ]	
	then
		java -jar -Djava.security.egd=file:/dev/./urandom $(dirname $0)/idpjenkinsconnector.jar
	else
		java -jar -Djavax.net.ssl.trustStore=${SSL_CA_LOC} -Djavax.net.ssl.keyStorePassword=${SSL_CA_PASS} -Djava.security.egd=file:/dev/./urandom $(dirname $0)/idpjenkinsconnector.jar
	fi   
else
	export SSL_ENABLED=false
	echo "Starting IDP Jenkins Connector in non-secure mode"
	java -jar -Djava.security.egd=file:/dev/./urandom $(dirname $0)/idpjenkinsconnector.jar 
fi