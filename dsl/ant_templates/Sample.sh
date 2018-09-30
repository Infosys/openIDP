#!/bin/bash
export server="$1"
export customscript="$2"
export rollbackscript="$3"
export username="$4"
export password="$5"
export database="$6"
echo "Running badscript"
sqlcmd -S $1 -d $6 -U $4 -P $5 -I -i $2 >> out.txt
if [ "$?" -gt 0 ] 
then
	echo "Running Rollback script"
	sqlcmd -S $1 -d $6 -U $4 -P $5 -I -i $3 >> out1.txt
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