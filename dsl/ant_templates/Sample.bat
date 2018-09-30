@echo off  
set server=%1
set customscript=%2
set rollbackscript=%3
set username=%4
set password=%5
set database=%6


echo Running badscript.sql
sqlcmd -S %server% -d %database% -U %username% -P %password% -I -i %customscript% >> out.txt
if %errorlevel% gtr 0 (
goto next1
) else ( 
   echo successfully executed
)

:next1  

echo Running Rollback script  
sqlcmd -S %server% -d %database% -U %username% -P %password% -I -i %rollbackscript% >> out1.txt
if %errorlevel% gtr 0 (
echo An error occurred in executing both the scripts
exit 1
) else (
echo Successfully Executed Rollback script
)

:exit