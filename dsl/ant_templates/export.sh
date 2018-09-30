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
sysname=`uname -s`

case "$sysname" in
  SunOS) ECHO='/usr/ucb/echo'
     BSDFLAGS='-n'
     NONBSDFLAGS='';;
  Linux) ECHO='/bin/echo'
     BSDFLAGS='-n'
     NONBSDFLAGS='';;
  HP-UX|AIX) BSDFLAGS=''
         NONBSDFLAGS='\c'
         ECHO='/usr/bin/echo';;
  OS400|OS/390) BSDFLAGS=''
         NONBSDFLAGS='\c'
         ECHO='/bin/echo';;
  CYGWIN_NT-*) PS="/usr/bin/ps"
         ECHO='/usr/bin/echo'
         NONBSDFLAGS='' ;;
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
