#!/bin/sh
cp -rfp cacerts_org cacerts

#rm -rf idp.jks

#echo $SSL_KS_PASSWORD | keytool -importkeystore -deststorepass $SSL_KS_PASSWORD -destkeystore idp.jks -srckeystore $SSL_CERT_ALIAS.p12 -srcstoretype PKCS12

#echo $SSL_KS_PASSWORD | keytool -exportcert -alias $SSL_CERT_ALIAS -keypass $SSL_KS_PASSWORD -keystore idp.jks -storepass $SSL_KS_PASSWORD -file $SSL_CERT_ALIAS.der

#Check for existing Certificate-Key Pair
if [ -e idp.crt -a -e idp.key ]
then
	echo "Certificate files found. Setup will not try to generate certificate"
else
	echo "Certificate-Key Pair not found. Will be generating self signed certificate"
	openssl req -x509 -newkey rsa:4096 -keyout idp.key -out idp.crt -nodes -days 365 -subj "/CN=$STACK_HOSTNAME"
	openssl x509 -outform der -in idp.crt -out idp.der
fi

echo $SSL_CA_PASSWORD | keytool -noprompt -importcert -file idp.der -alias idp -keystore cacerts 


#openssl pkcs12 -in $SSL_CERT_ALIAS.p12  -nokeys -out $SSL_CERT_ALIAS.pem -password pass:$SSL_KS_PASSWORD

#KEY_EXT=_key.pem
#openssl pkcs12 -in $SSL_CERT_ALIAS.p12  -nodes -nocerts -out $SSL_CERT_ALIAS$KEY_EXT -password pass:$SSL_KS_PASSWORD
