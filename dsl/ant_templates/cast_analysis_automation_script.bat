@echo off

mkdir "$SchemaBackupPath\Schema_Backup"
"%CAST_STORAGE_HOME%\bin\pg_dump.exe" --host localhost --port 2280 --username "operator" --no-password  --format tar --encoding UTF8 --verbose --file "C:/test\workspace\Cast_test1_CAST\Schema_Backup\$BackupSchemaName_central.backup" --schema "$BackupSchemaName_central" "postgres"
echo " $BackupSchemaName_central backup done"

"%CAST_STORAGE_HOME%\bin\pg_dump.exe" --host localhost --port 2280 --username "operator" --no-password  --format tar --encoding UTF8 --verbose --file "C:/test\workspace\Cast_test1_CAST\Schema_Backup\$BackupSchemaName_local.backup" --schema "$BackupSchemaName_local" "postgres"
echo " $BackupSchemaName_local backup done"

"%CAST_STORAGE_HOME%\bin\pg_dump.exe" --host localhost --port 2280 --username "operator" --no-password  --format tar --encoding UTF8 --verbose --file "C:/test\workspace\Cast_test1_CAST\Schema_Backup\$BackupSchemaName_mngt.backup" --schema "$BackupSchemaName_mngt" "postgres"
echo " $BackupSchemaName_mngt backup done"

mkdir "$SchemaBackupPath\LOGS"

REM ************************Automation Script****************************
REM ************************Cast Installation Location*******************
set RUNFLATDIRNAME=%CAST_HOME%
REM ************************CAST-MS-cli START****************************
set connectionProfile=$ConnProfile
set captureDate=%1
set MAINLOG=$SchemaBackupPath\LOGS\mainlog_%captureDate%.log
set applicationName=$AppName
set dashboardServiceName=$SchemaName
set logFilePath=$SchemaBackupPath\LOGS\log_%captureDate%.txt
set oldVersion=%2
set newVersion=%3

set "CMS_CLI_OPTIONS=GenerateSnapshot -snapshot "Computed on %captureDate%" -captureDate %captureDate% -version "%newVersion%" -dashboardservice %dashboardServiceName%"

@echo Begin Source code Delivery process....................................
"%RUNFLATDIRNAME%\CAST-MS-cli.exe" AutomateDelivery -connectionProfile "%connectionProfile%" -appli "%applicationName%" -version "%newVersion%" -fromVersion "%oldVersion%" -date %captureDate%
@echo End Source code Delivery process....................................
@echo Begin Set as current version process.................................
"%RUNFLATDIRNAME%\CAST-MS-cli.exe" SetAsCurrentVersion -connectionProfile "%connectionProfile%" -appli "%applicationName%" -version "%newVersion%"
@echo End Set as current version process.................................
@echo Begin Run Analysis and Generate Snapshot step........................
"%RUNFLATDIRNAME%\CAST-MS-cli.exe" %CMS_CLI_OPTIONS% -connectionProfile "%connectionProfile%" -logFilePath "%logFilePath%" >> %MAINLOG% 2>&1
@echo End Run Analysis and Generate Snapshot step........................
pause
echo on