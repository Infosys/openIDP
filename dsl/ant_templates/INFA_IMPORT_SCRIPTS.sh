#!/bin/bash

##########################################################
# Informatica PowerCenter Installer Build Details
# This software contains confidential and proprietary
# Script Name   : INFA_IMPORT_SCRIPTS.sh
# Created by    : Vasanthakumar_d01@domain.com
# Created Date  : 03-OCT-2017
##########################################################
export UN="$1"
export MAPP="$2"
. /home/$UN/INFA_CONFIG_IMPORT.sh

now=$(date +%Y%m%d)


INFA_CONN_TGT_REPO() {
  echo "`date` :connecting to informatica Target repository..."

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
IMP_OBJECT_SHRD="$(echo "$IMP_OBJECT_SHRD" | tr -d '\t\r\n')"
IMP_OBJECT_XML="$(echo "$IMP_OBJECT_XML" | tr -d '\t\r\n')"
CNTL_SHRD="$(echo "$CNTL_SHRD" | tr -d '\t\r\n')"
CNTL="$(echo "$CNTL" | tr -d '\t\r\n')"
#MAPP="$(echo "$MAPNAME" | tr -d '\t\r\n')"
##Connection to Target repository
pmrep Connect -r $TGT_REPO -n $UR -x $PASSWD -h $Hostnm -o $Pt

  else
     echo "`date` :Status: DOWN . Not able to Connect."
     exit
     fi

}

INFA_IMP_TGT_REPO_OBJ() {
  echo "`date` :Impporting informatica Target Shared folder objects..."

  if [ $? -eq 0 ]
  then

##Import Informatica Shared Folder from Target repository
#pmrep ObjectImport -i $IMP_OBJECT_XML -c $CNTL_SHRD
IFS=","
while read f1 f2 f3
do
pmrep validate -n $f2 -o $f3 -f $f1 -s -k -m test >> log1.txt
if grep -q "Objects that are still invalid after the validation: 0" log1.txt;
 then
f11="$f2.xml"
echo "Object is : $f11"
pmrep objectimport -i $f11 -c $CNTL
else
echo "Invalid Folder"
fi
done < input_file.txt

  else
     echo "`date` :Not able to Import shared folder objects."
     exit
     fi

}

INFA_IMP_TGT_SHRD_REPO_FLD() {
  echo "`date` :Impporting informatica Target Shared folder objects..."

  if [ $? -eq 0 ]
  then

##Import Informatica Shared Folder from Target repository
pmrep ObjectImport -i $IMP_OBJECT_XML -c $CNTL_SHRD

  else
     echo "`date` :Not able to Import shared folder objects."
     exit
     fi

}

INFA_IMP_TGT_REPO_FLD() {
  echo "`date` :Importing informatica Target folder objects..."

  if [ $? -eq 0 ]
  then

##Import Informatica Folder from Target repository
pmrep ObjectImport -i $IMP_OBJECT_XML -c $CNTL

  else
     echo "`date` :Not able to export folder objects."
     exit
     fi

}



Main() {

        INFA_CONN_TGT_REPO

		if [ "$MAPP" = "folder" ]
		
		then
		
        INFA_IMP_TGT_SHRD_REPO_FLD

        INFA_IMP_TGT_REPO_FLD
		
		elif [ "$MAPP" = "object" ]
		
		then
		
		INFA_IMP_TGT_REPO_OBJ
		
		fi
        echo "`date` :Import completed successfully!!!"

        echo "`date` :----------------------------------------------------- "

}

Main | tee Infa_Import_log.$now.log


##################### END of Informatica PowerCenter Import process ######################

