#!/bin/sh
cd src/main/angular4src
$NPM_PROXY
npm install
ng build --base-href /idpapp/ --prod
cd ../resources
rm -rf static
mkdir static
cp -rp ../angular4src/dist/* static/
exit
