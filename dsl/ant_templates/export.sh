#!/bin/sh
############################################################################
# Licensed Materials - Property of IBM
#
# (C) Copyright IBM Corp. 2002, 2011 All Rights Reserved.
#
# US Government Users Restricted Rights - Use, duplication or disclosure
# restricted by GSA ADP Schedule Contract with IBM Corp.
############################################################################

#---------------------
# Set CLASSPATH
#--------------------

. /home/admin/si/install/bin/tmp.sh

#---------------------

PROP_DIR="/home/admin/si/install/properties"
CLASSPATH=${CLASSPATH}:${PROP_DIR}

# os specific stuff
sysname=$(uname -s)

case "$sysname" in
  SunOS)
	export ECHO='/usr/ucb/echo'
	export BSDFLAGS='-n'
	export NONBSDFLAGS='';;
  Linux)
	export ECHO='/bin/echo'
	export BSDFLAGS='-n'
	export NONBSDFLAGS='';;
  HP-UX|AIX) 
	export BSDFLAGS=''
	export NONBSDFLAGS='\c'
	export ECHO='/usr/bin/echo';;
  OS400|OS/390)
	export BSDFLAGS=''
	export NONBSDFLAGS='\c'
	export ECHO='/bin/echo';;
  CYGWIN_NT-*) 
	export PS="/usr/bin/ps"
	export ECHO='/usr/bin/echo'
	export NONBSDFLAGS='' ;;
  *)
    echo "cannot determine OStype, to choose echo command. please fix switch"
    exit 1;;
esac

${JAVA} -Xmx1024m -classpath /home/admin/si/install/jar/bootstrapper.jar -Dvendor=shell -DforceNoWoodstock=no -DvendorFile=${PROP_DIR}/servers.properties com.sterlingcommerce.woodstock.noapp.NoAppLoader -class com.sterlingcommerce.woodstock.profile.ie.Export -f ${PROP_DIR}/dynamicclasspath.cfg -invokeargs $* 2>/dev/null

#-input ./objectsexportall.properties -doCerts -errors ./TestAllTradingPartnerExportErrors.err -output ./TestAllTradingPartnerExport.xml -passphrase test

rtn=$?
if [ ${rtn} -ne 0 ]
then
  ${ECHO} "Error '${rtn}' during export."
  exit 1
fi

${ECHO} "Export completed."
