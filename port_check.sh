#/bin/sh
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
echo -en " Checking Grafana Server Port:\033[s"
port_check $GRAFANA_PORT
echo -en " Checking Zookeeper Server Port:\033[s"
port_check $ZOOKEEPER_PORT
echo -en " Checking Kafka Server Port:\033[s"
port_check $KAFKA_PORT
echo -en " Checking Config Server Port:\033[s"
port_check $CONFIG_PORT
echo -en " Checking Eureka Server Port:\033[s"
port_check $EUREKA_PORT
echo -en " Checking Scheduler Server Port:\033[s"
port_check $SCHEDULER_PORT
echo -en " Checking Postgres Server Port:\033[s"
port_check $POSTGRES_PORT
echo -en " Checking Keycloak Server Port:\033[s"
port_check $KEYCLOAK_PORT
echo -en " Checking CI Engine Port:\033[s"
port_check $JENKINS_PORT
echo -en " Checking CI Engine Client Port:\033[s"
port_check $JENKINS_SLAVE_PORT
echo -en " Checking Auth Server Port:\033[s"
port_check $OAUTH_PORT
echo -en " Checking Dashboard Server Port:\033[s"
port_check $DASHBOARD_PORT
echo -en " Checking UI Server Port:\033[s"
port_check $IDPAPP_PORT
echo -en " Checking Subscription Server Port:\033[s"
port_check $SUBSCRIPTION_PORT
echo -en " Checking Services Server Port:\033[s"
port_check $SERVICES_PORT
