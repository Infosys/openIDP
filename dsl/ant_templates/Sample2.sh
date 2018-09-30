#!/bin/bash
export server="$1"
export customscript="$2"
export rollbackscript="$3"
export database="$4"
echo "Running badscript"
sqlcmd -S $1 -d $4 -I -i $2 >> out.txt
if [ "$?" -gt 0 ] 
then
	echo "Running Rollback script"
	sqlcmd -S $1 -d $4 -I -i $3 >> out1.txt
	if [ "$?" -gt 0 ]
	then
		echo "An error occurred in executing both the scripts"
		exit 1
	else
		echo "Successfully Executed Rollback script"
	fi
else
   echo "Successfully executed"
fi
echo "Success"