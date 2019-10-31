#!/bin/sh
#Will abort scripts on any command failure
set -e
 
#Script Behaviour Modifying Parameters (Change after throughly reading documentation)
EXEC_DIR=${EXEC_DIR:-$PWD}
SKIP_TESTS=${SKIP_TESTS:-true}
REMOVE_OLDER=${REMOVE_OLDER:-true}
BUILD_ONLY=${BUILD_ONLY:-false}
SKIP_BUILD=${SKIP_BUILD:-false}
SKIP_UI=${SKIP_UI:-false}
SKIP_SCHEDULER=${SKIP_SCHEDULER:-false}
SKIP_DASHBOARD=${SKIP_DASHBOARD:-false}
SKIP_SUBSCRIPTION=${SKIP_SUBSCRIPTION:-false}
SKIP_DSL=${SKIP_DSL:-false}
SKIP_JENKINS=${SKIP_JENKINS:-false}
SKIP_SERVICES=${SKIP_SERVICES:-false}
SKIP_EUREKA=${SKIP_EUREKA:-false}
SKIP_CLOUD=${SKIP_CLOUD:-false}
SKIP_TOOLS_CONFIG=${SKIP_TOOLS_CONFIG:-false}
SKIP_JENKINS_COMPONENTS=${SKIP_JENKINS_COMPONENTS:-false}
SKIP_GRAFANA_PLUGINS=${SKIP_GRAFANA_PLUGINS:-false}
SKIP_DATAFILES_PACKAGE=${SKIP_DATAFILES_PACKAGE:-false}
SKIP_DATAFILES_UPDATE=${SKIP_DATAFILES_UPDATE:-false}
LOCAL_M2_CACHE=${LOCAL_M2_CACHE:-"-v /root/.m2/:/root/.m2/"}
AUTOMATED_DEPLOYMENT=${AUTOMATED_DEPLOYMENT:-false}
STACK_RELEASE_TIMEOUT=${STACK_RELEASE_TIMEOUT:-50}
export HOSTNAME=${HOSTNAME:-$(hostname)}

#Docker Images Used for Build
ANSIBLE_IMAGE=${ANSIBLE_IMAGE:-${SUPP_DOCKER_REPO}jaskirat/ansible-xml}
MAVEN_BUILD_IMAGE=${MAVEN_BUILD_IMAGE:-${SUPP_DOCKER_REPO}paasmule/java-maven-git-alpine:3.3-jdk-8-alpine }
ANGULAR_BUILD_IMAGE=${ANGULAR_BUILD_IMAGE:-${SUPP_DOCKER_REPO}alexsuch/angular-cli:6.2}
ARCHIVE_MGMT_IMAGE=${ARCHIVE_MGMT_IMAGE:-${SUPP_DOCKER_REPO}alexsuch/angular-cli:6.2}
NPM_JRE_IMANGE=${NPM_JRE_IMANGE:-${SUPP_DOCKER_REPO}loyaltyone/docker-alpine-java-node:jre-8-node-8}
ARCHIVE_CREATE_IMAGE=${ARCHIVE_CREATE_IMAGE:-${SUPP_DOCKER_REPO}kramos/alpine-zip}
WGET_IMAGE=${WGET_IMAGE:-${SUPP_DOCKER_REPO}mwendler/wget}
CERTS_IMAGE=${CERTS_IMAGE:-${SUPP_DOCKER_REPO}openjdk:8u151-jre}
NETWORK_IMAGE=${NETWORK_IMAGE:-${SUPP_DOCKER_REPO}gochain/netstats:0.0.30}
COMPOSE_IMAGE=${COMPOSE_IMAGE:-${SUPP_DOCKER_REPO}docker/compose:1.24.0}

#Variables enabling iterative compilation and Quality scanning 
FOLDER_UI=ui/
FOLDER_SCHEDULER=scheduler/SchedulerService/
FOLDER_DASHBOARD=dashboard/
FOLDER_SUBSCRIPTION=subscription/
FOLDER_DSL=dsl/
FOLDER_SERVICES=service/Services/
FOLDER_CLOUD=service/Cloud-Config/
FOLDER_JENKINS_COMPONENT=jenkins/custom_tools/
CHANGED_SET=${CHANGED_SET:-.}
QUALITY_GATE_FAILED=false
echo -n > env.properties
component=""
SONAR_DATA=""

#Build Only mode check and configuration
if [ "$BUILD_ONLY" = true ]
then 
	echo "Build only instruction received. Ignoring other stage flags."
	BUILD_PREREQ_CHECK_ONLY=true
	SSL_ENABLED=false
	SKIP_TOOLS_CONFIG=true
	REMOVE_OLDER=false
	SKIP_DEPLOYMENT=true
	SKIP_HEALTH_CHECK=true
	SKIP_TOOLS_CONFIG=true
    SKIP_JENKINS=true
    SKIP_EUREKA=true
	SKIP_GRAFANA_PLUGINS=true
	echo "Changed set is $CHANGED_SET"
	case "$CHANGED_SET" in
    *${FOLDER_UI}*) echo 'UI CHANGED' ;component+=";IDP_UI" ;;
    *)                SKIP_UI=true  ;;
    esac
    case "$CHANGED_SET" in
    *${FOLDER_SCHEDULER}*) echo 'SCHEDULER CHANGED' ;component+=";Schedulerservice" ;;
    *)                SKIP_SCHEDULER=true  ;;
    esac
    case "$CHANGED_SET" in
    *${FOLDER_DASHBOARD}*) echo 'DASHBOARD CHANGED' ;component+=";idpdashboard" ;;
    *)                SKIP_DASHBOARD=true  ;;
    esac
    case "$CHANGED_SET" in
    *${FOLDER_SUBSCRIPTION}*) echo 'SUBSCRIPTION CHANGED' ;component+=";idpsubscription" ;;
    *)                SKIP_SUBSCRIPTION=true  ;;
    esac
    case "$CHANGED_SET" in
    *${FOLDER_DSL}*) echo 'DSL CHANGED' ;component+=";dsl" ;;
    *)                SKIP_DSL=true  ;;
    esac
    case "$CHANGED_SET" in
    *${FOLDER_SERVICES}*) echo 'SERVICES CHANGED' ;component+=";idpservices" ;;
    *)                SKIP_SERVICES=true  ;;
    esac
    case "$CHANGED_SET" in
    *${FOLDER_CLOUD}*) echo 'CLOUD CONFIG CHANGED'  ;component+=";idp-config";;
    *)                SKIP_CLOUD=true  ;;
    esac
    
	case "$CHANGED_SET" in
    *${FOLDER_JENKINS_COMPONENT}*) echo 'JENKINS COMPONENTS CHANGED'  ;component+=";DevopsJsonConv;metricsprocessor;ReportFetchUtil;scheduleUtility";;
    *)                SKIP_JENKINS_COMPONENTS=true  ;;
    esac
fi

#Intializing Data directories
export MOUNT_DIR=${MOUNT_DIR:-$EXEC_DIR/datafiles}
mkdir -p $MOUNT_DIR/postgresdata/
mkdir -p $MOUNT_DIR/dsldata/
mkdir -p $MOUNT_DIR/jenkinsdata/

#Hardware Pre-requisites Check
echo "Evaluating Hardware"
echo "Clearing Memory for Setup ..."
sync && echo 3 > /proc/sys/vm/drop_caches
if [ "$AUTOMATED_DEPLOYMENT" = false ]
then
	DOCKER_DIR=$(docker info | grep "Docker Root Dir:" | awk '{print $4}')
	TOTAL_MEM=$(free -g | awk 'NR==2{print $2}')
	USED_MEM=$(free -g | awk 'NR==2{print $7}')
	top -bn1 | grep load | awk '{printf "CPU Load: %.2f\n", $(NF-2)}'
	free -g |  awk 'NR==2{printf "Memory Usage: %s/%sGB (%.2f%%)\n", $3, $2, $3*100/$2}'
	df -m $MOUNT_DIR | awk 'NR==2{printf "Disk Usage(Datafiles Directory): %d/%dGB (%s)\n", $3,$2,$5}'
	DATAFILES_DISK_SPACE=$(df -m $MOUNT_DIR | awk 'NR==2{printf "%d", $3}')
	df -m $DOCKER_DIR | awk 'NR==2{printf "Disk Usage (Docker Data Directory): %d/%dGB (%s)\n", $3,$2,$5}'
	DOCKER_DISK_SPACE=$(df -m $DOCKER_DIR | awk 'NR==2{printf "%d", $3}')

	RECC_MEM=30
	MIN_REQ_MEM=14
	MIN_REQ_DISK=40960
	MIN_REQ_DISK_DOCKER=15360

	if [ $TOTAL_MEM -lt $RECC_MEM ]
	then
	  echo "Your Setup Failed to meet recommended Memory Requirements (${RECC_MEM}GB). Continue ?"
	  select ACCEPT_FLAG in "Yes" "No"; 
	  do
		case $ACCEPT_FLAG in
			Yes ) break;;
			No ) exit;;
		esac
	  done
	fi
	if [ $USED_MEM -lt $MIN_REQ_MEM ]
	then
	   echo "Your Setup Failed to meet recommended Free Memory Requirements (${MIN_REQ_MEM}GB). Continue ?"
	   select ACCEPT_FLAG in "Yes" "No"; 
	  do
		case $ACCEPT_FLAG in
			Yes ) break;;
			No ) exit;;
		esac
	  done
	fi
	if [ $DATAFILES_DISK_SPACE -lt $MIN_REQ_DISK ]
	then
	 echo "Recommended Disk Space for Data Files not available (${MIN_REQ_DISK}MB). Continue ?"
	   select ACCEPT_FLAG in "Yes" "No"; 
	  do
		case $ACCEPT_FLAG in
			Yes ) break;;
			No ) exit;;
		esac
	  done
	fi
	if [ $DOCKER_DISK_SPACE -lt $MIN_REQ_DISK_DOCKER ]
	then
	 echo "Recommended Disk Space for Docker Images not available (${MIN_REQ_DISK_DOCKER}MB). Continue ?"
	   select ACCEPT_FLAG in "Yes" "No"; 
	  do
		case $ACCEPT_FLAG in
			Yes ) break;;
			No ) exit;;
		esac
	  done
	fi
fi

#Software Pre-requisites Check
echo "Checking Pre-requisites"
if [ "$BUILD_PREREQ_CHECK_ONLY" = true ]
then 
	echo "Checking for Docker"
	docker ps > /dev/null 2>&1
	if [ $? -ne 0 ]
	then 
		echo "Docker Check: Failed"
		echo "Either Docker Not Installed or user is not allowed to run docker commands"
		exit 1
	else
		echo "Docker Check: Passed"
	fi
else
	echo "Checking for Docker and Swarm Mode"
	docker node ls > /dev/null 2>&1
	if [ $? -ne 0 ]
	
	then 
		echo "Docker Check: Failed"
		echo "Either Docker Not Installed or this node not a master node of swarm. Also please verify if user is allowed to run docker commands"
		exit 1
	else
		echo "Docker Check: Passed"
	fi
fi

#Micro-Services Parameter
export ADMIN_USERNAME=${ADMIN_USERNAME:-idpadmin}
export ADMIN_PASSWORD=${ADMIN_PASSWORD:-idpadmin@123}

#Custom Port Set
export SSL_ENABLED=${SSL_ENABLED:-false}
if [ "$SSL_ENABLED" = true ]
then
	export CUSTOM_PORT=${CUSTOM_PORT:-443}
else
	export CUSTOM_PORT=${CUSTOM_PORT:-80}
fi	

export GRAFANA_HOSTNAME=$HOSTNAME
export DASHBOARD_HOSTNAME=$HOSTNAME
export IDPAPP_HOSTNAME=$HOSTNAME
export SUBSCRIPTION_HOSTNAME=$HOSTNAME
export SERVICES_HOSTNAME=$HOSTNAME
export SCHEDULER_HOSTNAME=$HOSTNAME
export KEYCLOAK_HOSTNAME=$HOSTNAME
export JENKINS_HOSTNAME=$HOSTNAME
export OAUTH_HOSTNAME=$HOSTNAME
export EUREKA_HOSTNAME=$HOSTNAME

if [ "$CUSTOM_PORT" != "80" -a "$CUSTOM_PORT" != "443" ]
then	
	export GRAFANA_PORT=":$CUSTOM_PORT"
	export DASHBOARD_PORT=":$CUSTOM_PORT"
	export IDPAPP_PORT=":$CUSTOM_PORT"
	export SUBSCRIPTION_PORT=":$CUSTOM_PORT"
	export SERVICES_PORT=":$CUSTOM_PORT"
	export SCHEDULER_PORT=":$CUSTOM_PORT"
	export KEYCLOAK_PORT=":$CUSTOM_PORT"
	export JENKINS_PORT=":$CUSTOM_PORT"
	export OAUTH_PORT=":$CUSTOM_PORT"
	export EUREKA_PORT=":$CUSTOM_PORT"
fi

export ZOOKEEPER_HOSTNAME=zookeeper
export ZOOKEEPER_PORT=2181

export KAFKA_HOSTNAME=kafka
export KAFKA_PORT=9092


export CONFIG_HOSTNAME=config
export CONFIG_PORT=8888
export CONFIG_USERNAME=${CONFIG_USERNAME:-idpadmin}
export CONFIG_PASSWORD=${CONFIG_PASSWORD:-idpadmin@123}


export POSTGRES_HOSTNAME=postgres
export POSTGRES_PORT=5432
export POSTGRES_DEFAULT=keycloak
export POSTGRES_USER=${POSTGRES_USER:-postgres}
export POSTGRES_PASSWORD=${POSTGRES_PASSWORD:-root}

export KEYCLOAK_USER=${KEYCLOAK_USER:-admin}
export KEYCLOAK_PASSWORD=${KEYCLOAK_PASSWORD:-admin}

export JENKINS_SLAVE_PORT=${JENKINS_SLAVE_PORT:-50000}
export JENKINS_USERNAME=idpadmin
export JENKINS_PASSWORD=650f0ef465ef16b5a8e1bdcbd461973c

export AUTH_PROVIDER=keycloak

export HIDE_JSON_CONVERT=${HIDE_JSON_CONVERT:-true}

export CLOUD_DEPLOY_FLAG=${CLOUD_DEPLOY_FLAG:-true}

export BUILD_NUM=${BUILD_NUM:-1.5.0}
export BUILD_TYPE=${BUILD_TYPE:-RELEASE}
export BUILD_NAME=$BUILD_NUM-$BUILD_TYPE

export PROFILE=${PROFILE:-paas}

#Images used for IDP stack
export IDP_PROXY_IMAGE=${IDP_PROXY_IMAGE:-${DOCKER_REPO}nginx:1.15.10}
export IDP_ZOOKEEPER_IMAGE=${IDP_ZOOKEEPER_IMAGE:-${DOCKER_REPO}zookeeper:3.5}
export IDP_KAFKA_IMAGE=${IDP_KAFKA_IMAGE:-${DOCKER_REPO}wurstmeister/kafka:0.11.0.1}
export IDP_POSTGRES_IMAGE=${IDP_POSTGRES_IMAGE:-${DOCKER_REPO}postgres:9.6.1}
export IDP_KEYCLOAK_IMAGE=${IDP_KEYCLOAK_IMAGE:-${DOCKER_REPO}jboss/keycloak:4.0.0.Final}
export IDP_GRAFANA_IMAGE=${IDP_GRAFANA_IMAGE:-${DOCKER_REPO}grafana/grafana:5.2.1}
export IDP_JENKINS_IMAGE=${IDP_JENKINS_IMAGE:-${DOCKER_REPO}idp/jenkins}:$BUILD_NAME
export IDP_CONFIG_IMAGE=${IDP_CONFIG_IMAGE:-${DOCKER_REPO}idp/idpconfig}:$BUILD_NAME
export IDP_EUREKA_IMAGE=${IDP_EUREKA_IMAGE:-${DOCKER_REPO}idp/idpeureka}:$BUILD_NAME
export IDP_OAUTH_IMAGE=${IDP_OAUTH_IMAGE:-${DOCKER_REPO}idp/idpoauth}:$BUILD_NAME
export IDP_SERVICES_IMAGE=${IDP_SERVICES_IMAGE:-${DOCKER_REPO}idp/idpservices}:$BUILD_NAME
export IDP_DASHBOARD_IMAGE=${IDP_DASHBOARD_IMAGE:-${DOCKER_REPO}idp/idpdashboard}:$BUILD_NAME
export IDP_UI_IMAGE=${IDP_UI_IMAGE:-${DOCKER_REPO}idp/idpapp}:$BUILD_NAME
export IDP_SUBSCRIPTION_IMAGE=${IDP_SUBSCRIPTION_IMAGE:-${DOCKER_REPO}idp/idpsubscription}:$BUILD_NAME
export IDP_SCHEDULER_IMAGE=${IDP_SCHEDULER_IMAGE:-${DOCKER_REPO}idp/idpscheduler}:$BUILD_NAME
export IDP_DATAFILES_IMAGE=${IDP_DATAFILES_IMAGE:-${DOCKER_REPO}idp/datafiles}:$BUILD_NAME
export TEMP_DATAFILES_IMAGE=${TEMP_DATAFILES_IMAGE:-${DOCKER_REPO}idp/temp_datafiles}:$BUILD_NAME



#Installation and Pre-requisites check

echo "Checking if interactive session"
if [ "$AUTOMATED_DEPLOYMENT" = false ]
then
	echo "Running in Interactive Session"
	export INTERACTIVE="-it"
else
	echo "Running in Non-Interactive Session"
fi

echo "Checking for selected ports open status"
if [ "$BUILD_PREREQ_CHECK_ONLY" != true ]
then 
	if !(docker stack ls | grep "\<IDP\>");
	then 
		if !(docker run --rm --network="host" -v $PWD:/health -e HTTP_PORT=$CUSTOM_PORT -e HTTPS_PORT=$CUSTOM_PORT -e JENKINS_SLAVE_PORT=$JENKINS_SLAVE_PORT -e SSL_ENABLED=$SSL_ENABLED -w=/health  --entrypoint "sh"  $NETWORK_IMAGE port_check.sh);
		then 
			echo "Port Check: Failed"
			echo "Specified ports are either occupied or not open. Please refer above error for checking which one actually is not open."
			exit 1
		else
			echo "Port Check: Passed"
		fi
	else
		echo "Older IDP Stack is running. Hence skipping Port check."
	fi
fi

#Checking SSL Preferences
export SSL_ENABLED=${SSL_ENABLED:-false}

if [ "$SSL_ENABLED" = true ]
then
	export PROTOCOL=https
	export COMPOSE_FILE="-f $EXEC_DIR/docker_compose.yml -f $EXEC_DIR/docker_compose_ssl.yml"
	export SSL_CA_PASSWORD=changeit
	export SSL_CA_LOC=/certs/cacerts
	docker run --rm $INTERACTIVE -v $MOUNT_DIR:/openidp -e STACK_HOSTNAME=$HOSTNAME -e SSL_CA_PASSWORD=$SSL_CA_PASSWORD -w=/openidp/certs $CERTS_IMAGE sh certs_gen.sh
else
	export PROTOCOL=http
	export COMPOSE_FILE="-f $EXEC_DIR/docker_compose.yml -f $EXEC_DIR/docker_compose_nonssl.yml"
#	export COMPOSE_FILE=docker_compose_nonssl.yml
fi

if [ "$BUILD_TYPE"  != "RELEASE" ]
then
	UI_ARGS="snapshot"
fi

if [ "$SKIP_BUILD" != true ]
then
	#Setting Test Status
	MAVEN_TESTS=-Dmaven.test.skip=$SKIP_TESTS
	DSL_EXEC=-Dmaven.exec.skip=$SKIP_TESTS

	#Openidp Parent pom
    echo "Compiling openidp parent pom ......."
    cd $EXEC_DIR
    docker run --rm $INTERACTIVE -v $PWD:/openidp $LOCAL_M2_CACHE -e BUILD_NAME=$BUILD_NAME -w=/openidp $MAVEN_BUILD_IMAGE  mvn clean install $MAVEN_TESTS 

	#Root pom for springboot modules
    echo "Compiling root-pom ......."
    cd $EXEC_DIR
    docker run --rm $INTERACTIVE -v $PWD:/openidp $LOCAL_M2_CACHE -e BUILD_NAME=$BUILD_NAME -w=/openidp/root-pom $MAVEN_BUILD_IMAGE  mvn clean install $MAVEN_TESTS

	#Cloud Config
	if [ "$SKIP_CLOUD" != true ]
	then
		echo "Compiling Cloud Config ......."
		cd $EXEC_DIR
		docker run --rm $INTERACTIVE -v $PWD:/openidp $LOCAL_M2_CACHE -e BUILD_NAME=$BUILD_NAME -w=/openidp/service/Cloud-Config $MAVEN_BUILD_IMAGE  mvn clean install $MAVEN_TESTS $SONAR_COMMAND
		if [ "$SKIP_DEPLOYMENT" != true ]
		then
			echo "Building Cloud Config Docker Image"
			cd $EXEC_DIR/service/Cloud-Config
			docker build -t $IDP_CONFIG_IMAGE ./
		fi
	fi
	
	
	#Eureka
	if [ "$SKIP_EUREKA" != true ]
	then
		echo "Compiling Eurkea ......."
		cd $EXEC_DIR
		docker run --rm $INTERACTIVE -v $PWD:/openidp $LOCAL_M2_CACHE -e BUILD_NAME=$BUILD_NAME -w=/openidp/service/eureka-service $MAVEN_BUILD_IMAGE mvn clean install $MAVEN_TESTS 
		if [ "$SKIP_DEPLOYMENT" != true ]
		then
			echo "Building Eureka Docker Image"
			cd $EXEC_DIR/service/eureka-service
			docker build -t $IDP_EUREKA_IMAGE ./
		fi
	fi
	
	
	#Services & OAuth
	if [ "$SKIP_SERVICES" != true ]
	then
		echo "Compiling Services & OAuth ......."
		cd $EXEC_DIR
		docker run --rm $INTERACTIVE -v $PWD:/openidp $LOCAL_M2_CACHE -e BUILD_NAME=$BUILD_NAME -w=/openidp/service/Services $MAVEN_BUILD_IMAGE mvn clean install $MAVEN_TESTS $SONAR_COMMAND
		
		if [ "$SKIP_DEPLOYMENT" != true ]
		then
			echo "Building OAuth Docker Image"
			cd $EXEC_DIR/service/Services/authservice
			docker build -t $IDP_OAUTH_IMAGE ./
			
			echo "Building Services Docker Image"
			cd $EXEC_DIR/service/Services/restapi
			docker build -t $IDP_SERVICES_IMAGE ./
		fi
	fi
	
	
	#UI
	if [ "$SKIP_UI" != true ]
	then
		echo "Compiling UI ......."
		cd $EXEC_DIR
		docker run --rm $INTERACTIVE -v $PWD:/openidp -w=/openidp/ui -e NPM_PROXY="$NPM_PROXY" $ANGULAR_BUILD_IMAGE sh node_build.sh $UI_ARGS
		echo $PWD
		case "$component" in
		*"IDP_UI"*)docker run --rm $INTERACTIVE -v $PWD:/openidp -w=/openidp/ui -e NPM_PROXY="$NPM_PROXY" $NPM_JRE_IMANGE sh compile.sh;;
		*)                 ;;
		esac
		
		docker run --rm $INTERACTIVE -v $PWD:/openidp $LOCAL_M2_CACHE -e BUILD_NAME=$BUILD_NAME -w=/openidp/ui $MAVEN_BUILD_IMAGE mvn clean install $MAVEN_TESTS 
		if [ "$SKIP_DEPLOYMENT" != true ]
		then
			echo "Building UI Docker Image"
			cd $EXEC_DIR/ui
			docker build -t $IDP_UI_IMAGE ./
		fi
	fi
	
	#Subscription
	if [ "$SKIP_SUBSCRIPTION" != true ]
	then
		echo "Compiling Subscription Module ......."
		cd $EXEC_DIR
		docker run --rm $INTERACTIVE -v $PWD:/openidp $LOCAL_M2_CACHE -e BUILD_NAME=$BUILD_NAME -w=/openidp/subscription $MAVEN_BUILD_IMAGE mvn clean install $MAVEN_TESTS $SONAR_COMMAND
		if [ "$SKIP_DEPLOYMENT" != true ]
		then
			echo "Building Subscription Docker Image"
			cd $EXEC_DIR/subscription
			docker build -t $IDP_SUBSCRIPTION_IMAGE ./
		fi
	fi
	
	#Dashboard
	if [ "$SKIP_DASHBOARD" != true ]
	then
		echo "Compiling Dashboard Module ......."
		cd $EXEC_DIR
		docker run --rm $INTERACTIVE -v $PWD:/openidp $LOCAL_M2_CACHE -e BUILD_NAME=$BUILD_NAME -w=/openidp/dashboard $MAVEN_BUILD_IMAGE mvn clean install $MAVEN_TESTS $SONAR_COMMAND
		if [ "$SKIP_DEPLOYMENT" != true ]
		then
			echo "Building Dashboard Docker Image"
			cd $EXEC_DIR/dashboard
			docker build -t $IDP_DASHBOARD_IMAGE ./
		fi
	fi
	
	#Scheduler
	if [ "$SKIP_SCHEDULER" != true ]
	then
		echo "Compiling Scheduler Module ......."
		cd $EXEC_DIR
		docker run --rm $INTERACTIVE -v $PWD:/openidp $LOCAL_M2_CACHE -e BUILD_NAME=$BUILD_NAME -w=/openidp/scheduler/SchedulerService $MAVEN_BUILD_IMAGE mvn clean install $MAVEN_TESTS $SONAR_COMMAND
		if [ "$SKIP_DEPLOYMENT" != true ]
		then
			echo "Building Scheduler Docker Image"
			cd $EXEC_DIR/scheduler/SchedulerService
			docker build -t $IDP_SCHEDULER_IMAGE ./
		fi
	fi
	
	#DSL 
	if [ "$SKIP_DSL" != true ]
	then
		echo "Compiling DSL"
		cd $EXEC_DIR
		docker run --rm $INTERACTIVE -v $PWD:/openidp -v $PWD/service/Services/entities/target/:/entities -e WGET_PROXY="$WGET_PROXY" -w=/openidp/dsl --entrypoint "sh" $WGET_IMAGE libs_download.sh
		docker run --rm $INTERACTIVE -v $PWD:/openidp $LOCAL_M2_CACHE -e BUILD_NAME=$BUILD_NAME -w=/openidp/dsl $MAVEN_BUILD_IMAGE mvn clean package $DSL_EXEC $SONAR_COMMAND
		docker run --rm $INTERACTIVE -v $MOUNT_DIR:$MOUNT_DIR -w=$MOUNT_DIR/dsldata/ $ARCHIVE_MGMT_IMAGE sh -c "rm -rf *"
		docker run --rm $INTERACTIVE -v $PWD:/openidp -v $MOUNT_DIR:$MOUNT_DIR -w=/openidp/dsl $ARCHIVE_MGMT_IMAGE unzip -o target/DSL.zip -d $MOUNT_DIR/dsldata/
	fi
	
	#Jenkins
	if [ "$SKIP_JENKINS_COMPONENTS" != true ]
	then
		echo "Building Custom Tools ......."
		cd $EXEC_DIR/jenkins
		docker run --rm $INTERACTIVE -v $PWD:/jenkins $LOCAL_M2_CACHE -e BUILD_NAME=$BUILD_NAME -w=/jenkins/custom_tools/DevopsJsonConv $MAVEN_BUILD_IMAGE mvn clean install $MAVEN_TESTS $SONAR_COMMAND
		docker run --rm $INTERACTIVE -v $PWD:/jenkins $LOCAL_M2_CACHE -e BUILD_NAME=$BUILD_NAME -w=/jenkins/custom_tools/MetricsProcessor $MAVEN_BUILD_IMAGE mvn clean install $MAVEN_TESTS $SONAR_COMMAND
		docker run --rm $INTERACTIVE -v $PWD:/jenkins $LOCAL_M2_CACHE -e BUILD_NAME=$BUILD_NAME -w=/jenkins/custom_tools/ReportFetchUtil $MAVEN_BUILD_IMAGE mvn clean install $MAVEN_TESTS $SONAR_COMMAND
		docker run --rm $INTERACTIVE -v $PWD:/jenkins $LOCAL_M2_CACHE -e BUILD_NAME=$BUILD_NAME -w=/jenkins/custom_tools/SchedulerUtility $MAVEN_BUILD_IMAGE mvn clean install $MAVEN_TESTS $SONAR_COMMAND
		if [ "$SKIP_JENKINS" != true ]
		then
			echo "Configuring Jenkins for Startup ......."
			docker run --rm $INTERACTIVE -v $PWD:/jenkins -v $MOUNT_DIR/jenkinsdata/:/jenkinsdata/ -e WGET_PROXY="$WGET_PROXY" -w=/jenkins --entrypoint "sh" $WGET_IMAGE libs_download.sh
			docker run --rm $INTERACTIVE -v $PWD:/jenkins -v $MOUNT_DIR/jenkinsdata/:/jenkinsdata/ -e WGET_PROXY="$WGET_PROXY" -w=/jenkins --entrypoint "sh" $ARCHIVE_CREATE_IMAGE libs_pack_move.sh
			
			if [ "$SKIP_DEPLOYMENT" != true ]
			then
				echo "Building Jenkins Docker Image"
				cd $EXEC_DIR/jenkins
				docker build -t $IDP_JENKINS_IMAGE ./
			fi
		fi
	fi
	
	#Grafana Plugins Download
	if [ "$SKIP_GRAFANA_PLUGINS" != true ]
	then
		cd $EXEC_DIR
		docker run --rm $INTERACTIVE -v $PWD/grafana:/grafana -e WGET_PROXY="$WGET_PROXY" -w=/grafana --entrypoint "sh" $WGET_IMAGE download.sh
		docker run --rm $INTERACTIVE -v $PWD/grafana:/grafana -v $MOUNT_DIR/grafanadata/:/grafanadata/ -w=/grafana --entrypoint "sh" $ARCHIVE_CREATE_IMAGE move.sh
	fi

		
	
	#Datafiles Sync
	cd $EXEC_DIR
	docker run --rm $INTERACTIVE -v $PWD:/openidp -v $MOUNT_DIR:/data  $ARCHIVE_MGMT_IMAGE sh -c "cp -rf /openidp/datafiles/. /data > /dev/null 2>&1" || echo "Default datafiles folder. Hence not syncing"

	## Datafiles Package
	if [ "$SKIP_DATAFILES_PACKAGE" != true ]
	then
		if [ "$SKIP_DEPLOYMENT" != true ]
		then
			cd $EXEC_DIR
			docker build -t $TEMP_DATAFILES_IMAGE ./
			cd $MOUNT_DIR
			docker build -t $IDP_DATAFILES_IMAGE ./ --build-arg TEMP_DATAFILES_IMAGE="$TEMP_DATAFILES_IMAGE"
		fi
	fi

	
else
	echo "Skipping 	Build...."
	echo "WARNING: Images are assumed to be present in local/remote repository"
	if [ "$SKIP_DATAFILES_UPDATE" != true ]
	then
		rm -rf $MOUNT_DIR/jenkinsdata/plugins
		rm -rf $MOUNT_DIR/jenkinsdata/CUSTOM_TOOLS
		docker run --rm $INTERACTIVE -v $MOUNT_DIR:/data $IDP_DATAFILES_IMAGE sh -c "cp -rf /datafiles/. /data > /dev/null 2>&1"
	fi
fi

#Configuring Tools for Access
if [ "$SKIP_TOOLS_CONFIG" != true ]
then
	cd $MOUNT_DIR
	docker run --rm $INTERACTIVE -v $PWD:/datafiles -e HOSTNAME=$HOSTNAME -e PROTOCOL=$PROTOCOL -e PORT=$CUSTOM_PORT -e KEYCLOAK_PORT=$KEYCLOAK_PORT -e DASHBOARD_PORT=$DASHBOARD_PORT -e CDSERVICE_PORT=$CDSERVICE_PORT -e GRAFANA_PORT=$GRAFANA_PORT -e JENKINS_PORT=$JENKINS_PORT -e JENKINS_USERNAME=$JENKINS_USERNAME -e JENKINS_PASSWORD=$JENKINS_PASSWORD -e JENKINS_SLAVE_PORT=$JENKINS_SLAVE_PORT -e BUILD_NAME=$BUILD_NAME -e SSL_ENABLED=$SSL_ENABLED -w=/datafiles --entrypoint "" $ANSIBLE_IMAGE sh config.sh
fi

#Remove older stack
if [ "$REMOVE_OLDER" = true ]
then 
	echo "Removing Older IDP Stack"
	docker stack rm IDP
	echo "Waiting for Stack to release resources"
	sleep $STACK_RELEASE_TIMEOUT
fi

#Docker Stack Deployment
if [ "$SKIP_DEPLOYMENT" != true ]
then
	echo "Pulling Images"
	docker pull $IDP_PROXY_IMAGE > /dev/null 2>&1 || echo "Image $IDP_PROXY_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_ZOOKEEPER_IMAGE > /dev/null 2>&1 || echo "Image $IDP_ZOOKEEPER_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_KAFKA_IMAGE > /dev/null 2>&1 || echo "Image $IDP_KAFKA_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_POSTGRES_IMAGE > /dev/null 2>&1 || echo "Image $IDP_POSTGRES_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_KEYCLOAK_IMAGE > /dev/null 2>&1 || echo "Image $IDP_KEYCLOAK_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_GRAFANA_IMAGE > /dev/null 2>&1 || echo "Image $IDP_GRAFANA_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_JENKINS_IMAGE > /dev/null 2>&1 || echo "Image $IDP_JENKINS_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_CONFIG_IMAGE > /dev/null 2>&1 || echo "Image $IDP_CONFIG_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_EUREKA_IMAGE > /dev/null 2>&1 || echo "Image $IDP_EUREKA_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_OAUTH_IMAGE > /dev/null 2>&1 || echo "Image $IDP_OAUTH_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_SERVICES_IMAGE > /dev/null 2>&1 || echo "Image $IDP_SERVICES_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_DASHBOARD_IMAGE > /dev/null 2>&1 || echo "Image $IDP_DASHBOARD_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_UI_IMAGE > /dev/null 2>&1 || echo "Image $IDP_UI_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_SUBSCRIPTION_IMAGE > /dev/null 2>&1 || echo "Image $IDP_SUBSCRIPTION_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_SCHEDULER_IMAGE > /dev/null 2>&1 || echo "Image $IDP_SCHEDULER_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_JENKINSCON_IMAGE > /dev/null 2>&1 || echo "Image $IDP_JENKINSCON_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_ORCH_IMAGE > /dev/null 2>&1 || echo "Image $IDP_ORCH_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_CDSERVICE_IMAGE > /dev/null 2>&1 || echo "Image $IDP_CDSERVICE_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	docker pull $IDP_DATAFILES_IMAGE > /dev/null 2>&1 || echo "Image $IDP_DATAFILES_IMAGE not avaialble in remote repo. Assumed to avaialble locally"
	
	
	cd $EXEC_DIR
	
	if [ "$SKIP_DATAFILES_PERMISSIONS_SET" != true ]
	then
		echo "Setting Permissions for Datafiles"
		chmod -fR 0777 $MOUNT_DIR || :
	fi
	
	echo "Deploying IDP Stack"
	env | grep '' > run.env
	docker run --rm $INTERACTIVE -v $PWD:$PWD --env-file run.env -w=$PWD --entrypoint "" $COMPOSE_IMAGE /bin/sh -c "/usr/local/bin/docker-compose $COMPOSE_FILE config > stack.yml"
	docker stack deploy -c stack.yml IDP --with-registry-auth
fi

#Health Checks
if [ "$SKIP_HEALTH_CHECK" != true ]
then
	docker run --rm $INTERACTIVE --network="host" -v $PWD:/health -e PROTOCOL=$PROTOCOL -e PORT=:$CUSTOM_PORT -e CONFIG_HOSTNAME=$HOSTNAME -e CONFIG_USERNAME=$CONFIG_USERNAME -e CONFIG_PASSWORD=$CONFIG_PASSWORD -e EUREKA_HOSTNAME=$EUREKA_HOSTNAME -e KEYCLOAK_HOSTNAME=$KEYCLOAK_HOSTNAME -e JENKINS_HOSTNAME=$JENKINS_HOSTNAME -e OAUTH_HOSTNAME=$OAUTH_HOSTNAME -e DASHBOARD_HOSTNAME=$DASHBOARD_HOSTNAME -e IDPAPP_HOSTNAME=$IDPAPP_HOSTNAME -e SUBSCRIPTION_HOSTNAME=$SUBSCRIPTION_HOSTNAME -e SERVICES_HOSTNAME=$SERVICES_HOSTNAME -e CDSERVICE_HOSTNAME=$CDSERVICE_HOSTNAME -e GRAFANA_HOSTNAME=$GRAFANA_HOSTNAME -w=/health --entrypoint "sh" $WGET_IMAGE health_check.sh
fi


