## Python script to Start/Stop CA Virtualised Service existing in CA DevTest Server
import requests
import os
import ast
import base64
from defusedxml import ElementTree as ET

default_value = 'NA'
default_cred = 'YWRtaW46YWRtaW4='
admin='admin'
serverUrl = os.getenv('SERVER_URL',default_value)
apiUrl = 'api/Dcm/VSEs/VSE/' if (serverUrl[-1] == '/') else '/api/Dcm/VSEs/VSE/'
actionsStr = '/actions/'
startStr = 'start'
stopStr = 'stop'

# basic authorization Base64 encoding
user = os.getenv('CA_USER',admin)
passwd = os.getenv('CA_PASS',admin)
cred = user+':'+passwd
cred = base64.b64encode(cred.encode()).decode()
if(cred != default_cred):
	cred = base64.b64encode(cred.encode()).decode()
authStr = 'Basic '+cred
headers = { 'Authorization' : authStr }
action = os.getenv('ACTION_STRING',default_value)

servicesListStr = os.getenv('SERVICES_LIST',default_value)
print ('Service List str: ',servicesListStr)
servicesList = ast.literal_eval(servicesListStr)
print ('Service List: ',servicesList)

def sendRequest(actStr,serviceName):
	requestURL = serverUrl+apiUrl+serviceName+actionsStr+actStr
	print('Request URL: '+requestURL)
	response = requests.post(requestURL,headers=headers)
	print ("Response [status_code] for Service "+serviceName+": ",response.status_code)
	root = ET.fromstring(response.content)
	return root

def servicesStart(actStr):
	for service in servicesList:
		root = sendRequest(actStr,service)
		rootElementString = root.tag.split('}', 1)[1]
		if(rootElementString == 'VirtualService'):
			for child in root.iter('*'):
				elem = child.tag.split('}', 1)[1]
				txt = str(child.text)
				if (elem == 'ModelName' and txt.find(service) != -1):
					print("Started Service: ",service)
					break
		elif (rootElementString == 'Error'):
			for child in root.iter('*'):
				elem = child.tag.split('}', 1)[1]
				txt = str(child.text)
				if (elem == 'Message' and txt.find('virtual service is already running') != -1):
					print("Service '", service, "' Already Running!")
					break
	return

def servicesStop(actStr):
	for service in servicesList:
		root = sendRequest(actStr,service)
		rootElementString = root.tag.split('}', 1)[1]
		if(rootElementString == 'VirtualService'):
			for child in root.iter('*'):
				elem = child.tag.split('}', 1)[1]
				txt = str(child.text)
				if (elem == 'ModelName' and txt.find(service) != -1):
					print("Stopped Service: ",service)
					break
		elif (rootElementString == 'Error'):
			for child in root.iter('*'):
				elem = child.tag.split('}', 1)[1]
				txt = str(child.text)
				if (elem == 'Message' and txt.find('virtual service is not running') != -1):
					print("Service '", service, "' Already Stopped!")
					break
	return


# Start Execution Point
if(action == startStr):
	servicesStart(action)
elif (action == stopStr):
	servicesStop(action)


