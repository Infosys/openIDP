#!/bin/bash
export path="$1"
#export objType="$2"
if [ -f "$1" ]
then
	echo "file found"
	. $1
	source_folder=`cat $1 | grep SOURCEFOLDER | cut -d'=' -f2`
	sed -i -e 's/SRC_FOL/'${source_folder}'/g' cntl_file.txt
	source_repo=`cat $1 | grep SOURCEREPO | cut -d'=' -f2`
	sed -i -e 's/SRC_REPO/'${source_repo}'/g' cntl_file.txt
	target_fol=`cat $1 | grep TARGETFOLDER | cut -d'=' -f2`
	sed -i -e 's/TGT_FOL/'${target_fol}'/g' cntl_file.txt
	target_repo=`cat $1 | grep TARGETREPO | cut -d'=' -f2`
	sed -i -e 's/TGT_REPO/'${target_repo}'/g' cntl_file.txt
	source_shrd=`cat $1 | grep SOURCESHARED | cut -d'=' -f2`
	sed -i -e 's/SRC_SHRD/'${source_shrd}'/g' cntl_file.txt
	target_shrd=`cat $1 | grep TARGETSHARED | cut -d'=' -f2`
	sed -i -e 's/TGT_SHRD/'${target_shrd}'/g' cntl_file.txt
	echo "Control File created successfully"
	sed -i -e 's/SRC_SHRD/'${source_shrd}'/g' cntl_file_shared.txt
	sed -i -e 's/TGT_SHRD/'${target_shrd}'/g' cntl_file_shared.txt
	sed -i -e 's/SRC_REPO/'${source_repo}'/g' cntl_file_shared.txt
	sed -i -e 's/TGT_REPO/'${target_repo}'/g' cntl_file_shared.txt
	echo "Control File Shared Created Successfully"
	sed -i -e 's/SRC_SRD/'${source_shrd}'/g' INFA_CONFIG_EXPORT.sh
	sed -i -e 's/SRC_RPO/'${source_repo}'/g' INFA_CONFIG_EXPORT.sh
	sed -i -e 's/TGT_RPO/'${target_repo}'/g' INFA_CONFIG_EXPORT.sh
	sed -i -e 's/SRC_FOL/'${source_folder}'/g' INFA_CONFIG_EXPORT.sh
	sed -i -e 's/TGT_SRD/'${target_shrd}'/g' INFA_CONFIG_EXPORT.sh
	sed -i -e 's/TGT_FOL/'${target_fol}'/g' INFA_CONFIG_EXPORT.sh
	#map_name=`cat $1 | grep MAPPINGNAME | cut -d'=' -f2`
	#sed -i -e 's/MAPPI/'${objType}'/g' INFA_CONFIG_EXPORT.sh
	exp_obj_shrd=`cat $1 | grep EXPORTSHARED | cut -d'=' -f2`
	sed -i -e 's/EXP_SHRD_XML/'${exp_obj_shrd}'/g' INFA_CONFIG_EXPORT.sh
	port=`cat $1 | grep PORTT | cut -d'=' -f2`
	sed -i -e 's/PORT1/'${port}'/g' INFA_CONFIG_EXPORT.sh
	exp_obj_xml=`cat $1 | grep EXPORTXML | cut -d'=' -f2`
	sed -i -e 's/EXP_OBJ_XML/'${exp_obj_xml}'/g' INFA_CONFIG_EXPORT.sh
	echo "EXPORT CONFIG File Created Successfully"
	sed -i -e 's/SRC_SRD/'${source_shrd}'/g' INFA_CONFIG_IMPORT.sh
	sed -i -e 's/SRC_RPO/'${source_repo}'/g' INFA_CONFIG_IMPORT.sh
	sed -i -e 's/TGT_RPO/'${target_repo}'/g' INFA_CONFIG_IMPORT.sh
	sed -i -e 's/SRC_FOL/'${source_folder}'/g' INFA_CONFIG_IMPORT.sh
	sed -i -e 's/TGT_SRD/'${target_shrd}'/g' INFA_CONFIG_IMPORT.sh
	sed -i -e 's/TGT_FOL/'${target_fol}'/g' INFA_CONFIG_IMPORT.sh
	imp_obj_shrd=`cat $1 | grep IMPORTSHARED | cut -d'=' -f2`
	sed -i -e 's/IMP_SHRD_XML/'${imp_obj_shrd}'/g' INFA_CONFIG_IMPORT.sh
	port_imp=`cat $1 | grep PORTT | cut -d'=' -f2`
	sed -i -e 's/PORT/'${port_imp}'/g' INFA_CONFIG_IMPORT.sh
	imp_obj_xml=`cat $1 | grep IMPORTXML | cut -d'=' -f2`
	sed -i -e 's/IMP_OBJ_XML/'${imp_obj_xml}'/g' INFA_CONFIG_IMPORT.sh
	#sed -i -e 's/MAPPI/'${2}'/g' INFA_CONFIG_IMPORT.sh
	echo "IMPORT CONFIG File Created Successfully"
else
	echo "fail"
fi
echo "Success"
