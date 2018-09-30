#!/bin/sh
############################################################################
# Licensed Materials - Property of IBM
#
# (C) Copyright IBM Corp. 2007, 2011 All Rights Reserved.
#
# US Government Users Restricted Rights - Use, duplication or disclosure
# restricted by GSA ADP Schedule Contract with IBM Corp.
############################################################################

#---------------------
# Set CLASSPATH
#--------------------

. /home/admin/si/install/bin/tmp.sh

if [ "$VENDOR_NAME" = "" ]
then
   VENDOR_NAME=shell
fi

shutdownMySQL(){
    if [ -z "${MySQLrunning}" ]
    then
                sh /home/admin/si/install/bin/control_mysql.sh stop
        rtn=$?
        if [ ${rtn} -ne 0 ]
        then
               echo "Error '${rtn}' stopping the MySQL servers"
                exit ${rtn}
        fi
    else
        echo "MySQL servers already existed, will not attempt to stop."
    fi
}


startupMySQL(){
    test_db_if_active -noexit
    if [ "$DB_ACTIVE" -ne 0 ]
    then
       /home/admin/si/install/bin/control_mysql.sh start
        rtn=$?
        if [ ${rtn} -ne 0 ]
        then
            echo "Error '${rtn}' starting the MySQL servers"
            exit ${rtn}
        fi
    else
        echo "MySQL server already running."
        MySQLrunning="true"
    fi

}

#---------------------

PROP_DIR="/home/admin/si/install/properties"

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
  OS400)
     PS="/usr/bin/ps"
     BSDFLAGS=""
     NONBSDFLAGS="\c"
     ECHO="print";;
  OS/390)
     PS="/bin/ps"
     BSDFLAGS=""
     NONBSDFLAGS="\c"
     ECHO="print";;
  CYGWIN_NT-*) PS="/usr/bin/ps"
         ECHO='/usr/bin/echo'
         NONBSDFLAGS='' ;;
  *)
    echo "cannot determine OStype, to choose echo command. please fix switch"
    exit 1;;
esac

CLASSPATH=${CLASSPATH}:${PROP_DIR}
NOTJNI=""
if [ "$sysname" = "HP-UX" ] && [ "${JDK64BIT}" = "true" ] && [ "`uname -m`" != "ia64" ]
then
	NOTJNI="-notJNI"
fi


    ${JAVA} -Xmx1024m -classpath /home/admin/si/install/jar/bootstrapper.jar -Dvendor=shell -DforceNoWoodstock=yes -DvendorFile=${PROP_DIR}/servers.properties -DtranslatorProps=${PROP_DIR}/translator.properties -DDatabaseProps=${PROP_DIR}/jdbc.properties com.sterlingcommerce.woodstock.noapp.NoAppLoader -class com.sterlingcommerce.woodstock.profile.ie.Import -f ${PROP_DIR}/dynamicclasspath.cfg -invokeargs ${NOTJNI} $* 2>/dev/null
rtn=$?
if [ ${rtn} -ne 0 ]
then
  ${ECHO} "Error '${rtn}' during import.                                                         "
  ${ECHO} "Check system log for details.                                                         "
  exit 1
fi


${ECHO} "Import completed.                                                   "
