#!/bin/sh
echo "Performing Health Checks .........."
status=false;
retries_allowed=10
print_status(){
	if [ "$1" != true ]
	then
		echo -en "\033[u Failed\n"
	else
		echo -en "\033[u Passed\n"
	fi
}
print_status_pos(){
	if [ "$3" != true ]
	then
		echo -en "\033[$1A \033[$2CFailed"
	else
		echo -en "\033[$1A \033[$2CPassed"
	fi
}

wait_count=$retries_allowed
echo -en " Checking Config Server:\033[s"
while [ "$status" != true ]
 do
	wget -q -O - ${PROTOCOL}://${CONFIG_HOSTNAME}:${CONFIG_PORT}/idpoauth/paas --user=${CONFIG_USERNAME} --password=${CONFIG_PASSWORD} --no-check-certificate >/dev/null 2>&1
	if [ $? -ne 0 ]
	then 
		status="false"
	else 
		status="true"
		print_status $status
	fi
	sleep 5
	wait_count=$((wait_count - 1))
	if [ "$wait_count" -eq "0" ]
	then
		print_status $status
		exit 1
	fi
done
status=false;

wait_count=$retries_allowed
echo -en " Checking Eurkea Server:\033[s"
while [ "$status" != true ]
 do
	wget -q -O - ${PROTOCOL}://${EUREKA_HOSTNAME}:${EUREKA_PORT}/ --no-check-certificate >/dev/null 2>&1
	if [ $? -ne 0 ]
	then 
		status="false"
	else 
		status="true"
		print_status $status
	fi
	sleep 5
	wait_count=$((wait_count - 1))
	if [ "$wait_count" -eq "0" ]
	then
		print_status $status
		exit 1
	fi
done
status=false;

wait_count=$retries_allowed
echo -en " Checking Keycloak Server:\033[s"
while [ "$status" != true ]
 do
	wget -q -O - ${PROTOCOL}://${KEYCLOAK_HOSTNAME}:${KEYCLOAK_PORT}/auth --no-check-certificate >/dev/null 2>&1
	if [ $? -ne 0 ]
	then 
		status="false"
	else 
		status="true"
		print_status $status
	fi
	sleep 5
	wait_count=$((wait_count - 1))
	if [ "$wait_count" -eq "0" ]
	then
		print_status $status
		exit 1
	fi
done
status=false;

wait_count=$retries_allowed
echo -en " Checking Auth Server:\033[s"
while [ "$status" != true ]
 do
	wget -q -O - ${PROTOCOL}://${OAUTH_HOSTNAME}:${OAUTH_PORT}/idp-oauth/login --no-check-certificate >/dev/null 2>&1
	if [ $? -ne 0 ]
	then 
		status="false"
	else 
		status="true"
		print_status $status
	fi
	sleep 5
	wait_count=$((wait_count - 1))
	if [ "$wait_count" -eq "0" ]
	then
		print_status $status
		exit 1
	fi
done
status=false;

wait_count=$retries_allowed
echo -en " Checking Services Server:\033[s"
while [ "$status" != true ]
 do
	wget -q -O - ${PROTOCOL}://${SERVICES_HOSTNAME}:${SERVICES_PORT}/idprest/swagger-ui.html --no-check-certificate >/dev/null 2>&1
	if [ $? -ne 0 ]
	then 
		status="false"
	else 
		status="true"
		print_status $status
	fi
	sleep 5
	wait_count=$((wait_count - 1))
	if [ "$wait_count" -eq "0" ]
	then
		print_status $status
		exit 1
	fi
done
status=false;

wait_count=$retries_allowed
echo -en " Checking Subscription Server:\033[s"
while [ "$status" != true ]
 do
	wget -q -O - ${PROTOCOL}://${SUBSCRIPTION_HOSTNAME}:${SUBSCRIPTION_PORT}/subscription/swagger-ui.html --no-check-certificate >/dev/null 2>&1
	if [ $? -ne 0 ]
	then 
		status="false"
	else 
		status="true"
		print_status $status
	fi
	sleep 5
	wait_count=$((wait_count - 1))
	if [ "$wait_count" -eq "0" ]
	then
		print_status $status
		exit 1
	fi
done
status=false;

wait_count=$retries_allowed
echo -en " Checking Dashboard Server:\033[s"
while [ "$status" != true ]
 do
	wget -q -O - ${PROTOCOL}://${DASHBOARD_HOSTNAME}:${DASHBOARD_PORT}/idpdashboard/1 --no-check-certificate >/dev/null 2>&1
	if [ $? -ne 0 ]
	then 
		status="false"
	else 
		status="true"
		print_status $status
	fi
	sleep 5
	wait_count=$((wait_count - 1))
	if [ "$wait_count" -eq "0" ]
	then
		print_status $status
		exit 1
	fi
done
status=false;

wait_count=$retries_allowed
echo -en " Checking UI Server:\033[s"
while [ "$status" != true ]
 do
	wget -q -O - ${PROTOCOL}://${IDPAPP_HOSTNAME}:${IDPAPP_PORT}/idpapp/ --no-check-certificate >/dev/null 2>&1
	if [ $? -ne 0 ]
	then 
		status="false"
	else 
		status="true"
		print_status $status
	fi
	sleep 5
	wait_count=$((wait_count - 1))
	if [ "$wait_count" -eq "0" ]
	then
		print_status $status
		exit 1
	fi
done
status=false;

wait_count=$retries_allowed
echo -en " Checking CI Server:\033[s"
while [ "$status" != true ]
 do
	wget -q -O - ${PROTOCOL}://${JENKINS_HOSTNAME}:${JENKINS_PORT}/jenkins --no-check-certificate >/dev/null 2>&1
	if [ $? -ne 0 ]
	then 
		status="false"
	else 
		status="true"
		print_status $status
	fi
	sleep 5
	wait_count=$((wait_count - 1))
	if [ "$wait_count" -eq "0" ]
	then
		print_status $status
		exit 1
	fi
done
status=false;

echo "All Health Checks Passed. IDP should be accessible at ${PROTOCOL}://${IDPAPP_HOSTNAME}:${IDPAPP_PORT}/idpapp/"