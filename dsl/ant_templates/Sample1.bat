@echo off  
set server=%1
set customscript=%2
set rollbackscript=%3
set database=%4


echo Running badscript.sql
sqlcmd -S %server% -d %database% -I -i %customscript% >> out.txt
if %errorlevel% gtr 0 (
goto next1
) else ( 
   echo successfully executed
)

:next1  

echo Running Rollback script  
sqlcmd -S %server% -d %database% -I -i %rollbackscript% >> out1.txt
if %errorlevel% gtr 0 (
echo An error occurred in executing both the scripts
exit 1
) else (
echo Successfully Executed Rollback script
)

:exit