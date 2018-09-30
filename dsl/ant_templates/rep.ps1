(get-content ant.xml) | foreach-object {$_ -replace "APPSCAN_INSTALLATION_FOLDER", "$env:APPSCAN_INSTALLATION_FOLDER"}| set-content ant.xml
(get-content build.xml) | foreach-object {$_ -replace "%20", " "}  | set-content build.xml
