#!/bin/bash

##########################################################
# Informatica PowerCenter Installer Build Details
# This software contains confidential and proprietary
# Script Name	: INFA_EXPORT_SCRIPTS.sh
# Created by 	: Vasanthakumar_d01@domain.com
# Created Date  : 03-OCT-2017
##########################################################
export UN="$1"
export MAPP="$2"
. /home/$UN/INFA_CONFIG_EXPORT.sh 
now=$(date +%Y%m%d)

INFA_CONN_SOURCE_REPO() {
  echo "`date` :connecting to informatica source repository..."
  
  if [ $? -eq 0 ]
  then
Hostnm="$(echo "$Hostnm" | tr -d '\t\r\n')"
SRC_REPO="$(echo "$SRC_REPO" | tr -d '\t\r\n')" 
UR="$(echo "$UR" | tr -d '\t\r\n')"
PASSWD="$(echo "$PASSWD" | tr -d '\t\r\n')"
Pt="$(echo "$Pt" | tr -d '\t\r\n')"
TGT_REPO="$(echo "$TGT_REPO" | tr -d '\t\r\n')"
SRC_SHRD="$(echo "$SRC_SHRD" | tr -d '\t\r\n')"
SRC_FLD="$(echo "$SRC_FLD" | tr -d '\t\r\n')"
TGT_SHRD="$(echo "$TGT_SHRD" | tr -d '\t\r\n')"
TGT_FLD="$(echo "$TGT_FLD" | tr -d '\t\r\n')"
EXP_OBJECT_SHRD="$(echo "$EXP_OBJECT_SHRD" | tr -d '\t\r\n')"
EXP_OBJECT_XML="$(echo "$EXP_OBJECT_XML" | tr -d '\t\r\n')"
CNTL_SHRD="$(echo "$CNTL_SHRD" | tr -d '\t\r\n')"
CNTL="$(echo "$CNTL" | tr -d '\t\r\n')"
#MAPP="$(echo "$MAPNAME1" | tr -d '\t\r\n')"
#OBJ_NAME="${echo "$OBJ_NAME" | tr -d '\t\r\n'}"
##Connection to source repository
pmrep Connect -r $SRC_REPO -n $UR -x $PASSWD -h $Hostnm -o $Pt

  else
     echo "`date` :Status: DOWN . Not able to Connect."
     exit
     fi
 
}

INFA_EXP_SRC_SHRD_REPO_FLD() {
  echo "`date` :exporting informatica source Shared folder objects..."
  
  if [ $? -eq 0 ]
  then
	   
##Export Informatica Shared Folder from source repository
pmrep objectexport -f $TGT_SHRD -u $EXP_OBJECT_SHRD
	   
  else
     echo "`date` :Not able to export shared folder objects."
     exit
     fi
 
}

INFA_EXP_SRC_REPO_OBJ() {
  echo "`date` :exporting informatica source folder objects..."
  
  if [ $? -eq 0 ]
  then
  
#pmrep Connect -r $SRC_REPO -n $UR -x $PASSWD -h $Hostnm -o $Pt
IFS=","
while read f1 f2 f3
do

pmrep validate -n $f2 -o $f3 -f $f1 -s -k -m test >> log1.txt

if grep -q "Objects that are still invalid after the validation: 0" log1.txt;
 then

        echo "Mapping is        : $f2"
                f11="$f2.xml"
        echo "XML file  is  : $f11"
                echo "Object Type is : $f3"
pmrep objectexport -n $f2 -o $f3 -f $f1 -m -s -b -r -u $f11;
pmrep Connect -r $SRC_REPO -n $UR -x $PASSWD -h $Hostnm -o $Pt;
scp f11 $uname@$b_host_name:/root/idp_temp
else
echo "Invalid Folder"
fi
done < input_file.txt

  else
     echo "`date` :Not able to export folder objects."
     exit
     fi
 
}

INFA_EXP_SRC_REPO_FLD() {
  echo "`date` :exporting informatica source folder objects..."
  
  if [ $? -eq 0 ]
  then
	   
##Export Informatica Folder from source repository
pmrep objectexport -f $SRC_FLD -u $EXP_OBJECT_XML
  else
     echo "`date` :Not able to export folder objects."
     exit
     fi
 
}


INFA_EXP_PRIVILIGE() {
  echo "`date` :Assigning privilige for exported informatica objects..."
  
chmod 777 *.xml
 
}



Main() {
	  
	INFA_CONN_SOURCE_REPO 
	
	 if [ "$MAPP" = "folder" ]

        then

 INFA_EXP_SRC_SHRD_REPO_FLD

        INFA_EXP_SRC_REPO_FLD

        elif [ "$MAPP" = "object" ]
then
        INFA_EXP_SRC_REPO_OBJ
fi
        INFA_EXP_PRIVILIGE
	
	echo "`date` :Export completed successfully!!!"
   
	echo "`date` :----------------------------------------------------- "
   
}

Main | tee Infa_export_log.$now.log 


##################### END of Informatica PowerCenter export process ######################

